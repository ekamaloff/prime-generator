package prime.generator;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Implementation of prime number generator using trial division method with parallel computation.
 *
 * This generator can generate prime numbers in range [1..Long.MAX_VALUE].
 *
 */
public class TrialDivisionParallelPrimeGenerator implements PrimeGenerator {
    @Override
    public List<Long> findAll(long limit) {
        if (limit <= 0)
            throw new IllegalArgumentException("Limit must be greater than zero");

        return findAll(1, limit);
    }

    @Override
    public List<Long> findAll(long from, long limit) {
        if (limit < from)
            throw new IllegalArgumentException("Limit must be greater or equal to 'from'");

        int sqrtLimit = (int)Math.sqrt((double)limit);
        BitSet sieve = new BitSet(sqrtLimit + 1);
        SieveOfEratosthenesPrimeGenerator.findAll(sieve, sqrtLimit);

        // Test only odd numbers in the given range
        return getTestRange(from, limit)
                .parallel()
                .filter(n -> isPrime(sieve, n))
                .boxed()
                .collect(Collectors.toList());
    }

    private LongStream getTestRange(long from, long limit) {
        LongStream first = LongStream.empty();
        // Since we only test odd numbers (below) we need to include prime 2 in the result if it's in range
        if (from <= 2)
            first = LongStream.of(2);
        // Start with an odd number
        if (from % 2 == 0)
            ++from;
        // Include all odd numbers in range [from..limit] as candidates for primes
        return LongStream.concat(first, LongStream.iterate(from, n -> n += 2).limit((limit - from) / 2 + 1));
    }

    private boolean isPrime(BitSet sieve, long n) {
        if (n == 1)
            return false;
        int sqrt = (int)Math.sqrt((double)n);
        return !sieve.get(0, sqrt+1).stream().anyMatch(p -> n % p == 0);
    }
}
