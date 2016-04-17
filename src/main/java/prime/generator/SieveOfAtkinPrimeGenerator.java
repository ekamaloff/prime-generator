package prime.generator;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Find primes using Sieve of Atkin algorithm.
 * This generator can only generate prime numbers in range [1..Integer.MAX_VALUE-1] due to space constraints.
 *
 * The time complexity is O(n).
 * The space complexity is O(n).
 */
public class SieveOfAtkinPrimeGenerator extends AbstractSievePrimeGenerator {
    @Override
    public List<Long> findAll(long limit) {
        if (limit <= 0 || limit > Integer.MAX_VALUE - 1)
            throw new IllegalArgumentException("Limit must be between 1 and " + (Integer.MAX_VALUE - 1));

        // Create the sieve (all numbers marked as non-prime by default)
        BitSet sieve = new BitSet((int)limit+1);

        int[] s =  {1,7,11,13,17,19,23,29,31,37,41,43,47,49,53,59};
        int[] r1 = {1,13,17,29,37,41,49,53};
        int[] r2 = {7,19,31,43};
        int[] r3 = {11,23,47,59};

        int sqrtLimit = (int)Math.sqrt((double)limit);

        // Algorithm step 3.1
        for (int x = 1; x <= sqrtLimit; x++) { // for all x's
            for (int y = 1; y <= sqrtLimit; y += 2) { // and odd y's
                int n = 4 * x * x + y * y;
                if (n <= limit && contains(r1, n % 60)) {
                    sieve.flip(n);
                }
            }
        }

        // Algorithm step 3.2
        for (int x = 1; x <= sqrtLimit; x += 2) { // for odd x's
            for (int y = 2; y <= sqrtLimit; y += 2) { // and even y's
                int n = 3 * x * x + y * y;
                if (n <= limit && contains(r2, n % 60)) {
                    sieve.flip(n);
                }
            }
        }

        // Algorithm step 3.3
        for (int x = 2; x <= sqrtLimit; x++) { // for all x's
            for (int y = 1; y < x; y++) { // for all y's where x > y
                int n = 3 * x * x - y * y;
                if (n <= limit && contains(r3, n % 60)) {
                    sieve.flip(n);
                }
            }
        }

        // Eliminate composites by sieving, only for those occurences on the wheel
        for (int w = 0; w <= sqrtLimit / 60; w++) {
            for (int x: s) {
                int n = 60 * w + x;
                if (n >= 7 && n <= sqrtLimit && sieve.get(n)) {
                    int nn = n * n;
                    if (nn <= limit) {
                        for (int w2 = 0; w2 <= limit / nn / 60; w2++) {
                            for (int x2 : s) {
                                int c = nn * (60 * w2 + x2);
                                if (c <= limit) {
                                    sieve.clear(c);
                                }
                            }
                        }
                    }
                }
            }
        }
        // Algorithm does not produce primes 2, 3, 5 so we need to concat them first
        return IntStream.concat(IntStream.of(2, 3, 5), sieve.stream())
                .mapToLong(n -> (long)n)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean contains(int[] arr, int n) {
        return Arrays.binarySearch(arr, n) >= 0;
    }

}
