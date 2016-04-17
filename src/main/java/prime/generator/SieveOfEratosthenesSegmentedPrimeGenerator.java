package prime.generator;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of segmented Sieve of Eratosthenes.
 * This generator can generate prime numbers in range [1..Long.MAX_VALUE] (only limited by the size of
 * the available heap required to hold found prime numbers).
 *
 * The time complexity is O(n log log n).
 * The space complexity is O(sqrt(n)).
 */
public class SieveOfEratosthenesSegmentedPrimeGenerator extends AbstractSievePrimeGenerator {

    private static final int SEGMENT_SIZE = 32768;

    @Override
    public List<Long> findAll(long limit) {
        if (limit <= 0)
            throw new IllegalArgumentException("Limit must be greater than zero");

        int sqrtLimit = (int)Math.sqrt((double)limit);

        int s = 2;
        long n = 3;

        BitSet sieve = new BitSet(SEGMENT_SIZE);

        // generate small primes <= sqrt
        BitSet isPrime = new BitSet(sqrtLimit + 1);
        // Initialise the sieve using standard Sieve of Eratosthenes first
        SieveOfEratosthenesPrimeGenerator.findAll(isPrime, sqrtLimit);

        List<Long> result = new LinkedList<>();
        result.add(2L);

        List<Integer> smallPrimes = new LinkedList<>();
        List<Integer> next = new LinkedList<>();
        // Process each segment in loop
        for (long low = 0; low <= limit; low += SEGMENT_SIZE)
        {
            sieve.set(0, SEGMENT_SIZE);

            // current segment = interval [low, high]
            long high = Math.min(low + SEGMENT_SIZE - 1, limit);

            // store small primes needed to cross off multiples
            for (; s * s <= high; s++)
            {
                if (isPrime.get(s))
                {
                    smallPrimes.add(s);
                    next.add((int)(s * s - low));
                }
            }
            // sieve the current segment
            for (int i = 1; i < smallPrimes.size(); i++)
            {
                int j = next.get(i);
                for (int k = smallPrimes.get(i) * 2; j < SEGMENT_SIZE; j += k)
                    sieve.clear(j);
                next.set(i, j - SEGMENT_SIZE);
            }

            for (; n <= high; n += 2)
                if (sieve.get((int)(n - low))) { // n is a prime
                    result.add(n);
                }
        }
        return result;
    }
}
