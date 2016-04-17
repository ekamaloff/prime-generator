package prime.generator;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of simple Sieve of Eratosthenes.
 * This generator can only generate prime numbers in range [1..Integer.MAX_VALUE-1] due to space constraints.
 *
 * The time complexity is O(n log log n).
 * The space complexity is O(n).
 */
public class SieveOfEratosthenesPrimeGenerator extends AbstractSievePrimeGenerator {
    @Override
    public List<Long> findAll(long limit) {
        if (limit <= 0 || limit > Integer.MAX_VALUE - 1)
            throw new IllegalArgumentException("Limit must be between 1 and " + (Integer.MAX_VALUE - 1));

        BitSet sieve = new BitSet((int)limit+1);
        findAll(sieve, (int)limit);
        return sieve.stream()
                .mapToLong(n -> (long)n)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Populates the given sieve with prime numbers.
     * @param sieve prime sieve
     * @param limit limit
     */
    public static void findAll(BitSet sieve, int limit) {
        // Mark all numbers except 0 and 1 as prime initially
        sieve.clear(0, 2);
        sieve.set(2, limit+1);

        int sqrtLimit = (int)Math.sqrt((double)limit);

        // Sieve out all multiples of consecutive primes
        for (int i = 2; i <= sqrtLimit; i++) {
            if (sieve.get(i)) {
                for (int j = i * i; j <= limit; j += i) {
                    sieve.clear(j);
                }
            }
        }
    }

}
