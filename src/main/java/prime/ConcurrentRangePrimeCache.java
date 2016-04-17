package prime;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implements a cache of prime numbers using a {@link java.util.concurrent.ConcurrentSkipListMap}.
 *
 * The cache is a continuously growing set of prime numbers. The cache maintains prime numbers for multiple ranges.
 * Read access is fully concurrent, cache updates are mutually exclusive to ensure data integrity. In order to
 * maintain sets of prime numbers for unique ranges only each cache update involves cache normalisation which
 * involves merging overlapping ranges of prime numbers.
 *
 * This is an imperfect implementation that requires data copy during cache normalization.
 *
 * This class is thread-safe.
 */
public class ConcurrentRangePrimeCache implements PrimeCache {
    private static final int MAX_CACHE_SIZE = 1_000_000;

    private final Map<Range, SortedSet<Long>> ranges = new ConcurrentSkipListMap<>();
    private final Object rangeUpdateLock = new Object();
    private int currentCacheSize;


    @Override
    public void addAll(long from, long to, List<Long> primes) {
        if (primes.isEmpty())
            return;

        SortedSet<Long> primeSet = new TreeSet<>(primes);
        synchronized (rangeUpdateLock) {
            if (primes.size() + currentCacheSize > MAX_CACHE_SIZE)
                return;

            ranges.put(new Range(from, to), primeSet);
            if (ranges.size() > 1)
                normalizeRanges(); // merge overlapping ranges
            else
                currentCacheSize = primeSet.size();
        }
    }

    private void normalizeRanges() {
        List<Range> rangeList = new ArrayList<>(ranges.keySet());
        Map<Range, SortedSet<Long>> newMap = new TreeMap<>();
        currentCacheSize = 0;
        for (int i = 0; i < rangeList.size(); ) {
            Range left = rangeList.get(i);
            long rangeEnd = left.getTo();
            SortedSet<Long> rangePrimes = new TreeSet<>(ranges.get(left));
            if (i < rangeList.size() - 1) {
                for (int j = i + 1; j < rangeList.size(); j++) {
                    Range right = rangeList.get(j);
                    if (right.getFrom() <= rangeEnd) { // merge overlapping ranges
                        if (right.getTo() > rangeEnd)
                            rangePrimes.addAll(ranges.get(right));
                        rangeEnd = right.getTo();
                        i = j + 1;
                    } else {
                        i = j;
                        break;
                    }
                }
            } else {
                ++i;
            }
            newMap.put(new Range(left.getFrom(), rangeEnd), rangePrimes);
            currentCacheSize += rangePrimes.size();
        }
        ranges.clear();
        ranges.putAll(newMap);
    }


    @Override
    public List<Long> getPrimes(long from, long to) {
        Stream<Long> stream = getPrimeNumberStream(from, to);
        return stream == null ? null : stream.collect(Collectors.toList());
    }

    private Stream<Long> getPrimeNumberStream(long from, long to) {
        long rangeStart = from;
        Stream<Long> primes = Stream.empty();
        for (Map.Entry<Range, SortedSet<Long>> e: ranges.entrySet()) {
            Range range = e.getKey();
            if (rangeStart >= range.getFrom() && rangeStart <= range.getTo()) {
                long rangeTo = Math.min(to, range.getTo());
                Stream<Long> subSet = e.getValue().subSet(rangeStart, rangeTo).stream();
                primes = Stream.concat(primes, subSet);
                if (to <= range.getTo())
                    return primes; // all primes found in cache, return success
                rangeStart = range.getTo() + 1;
            }
        }
        return null;
    }

}
