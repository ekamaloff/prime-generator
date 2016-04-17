package prime.generator;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Implementation of Sieve of Sundaram algorithm for prime number generation.
 *
 * This generator can only generate prime numbers in range [1..Integer.MAX_VALUE-1] due to space constraints.
 */
public class SieveOfSundaramPrimeGenerator extends AbstractSievePrimeGenerator {
    @Override
    public List<Long> findAll(long limit) {
        if (limit <= 0 || limit > Integer.MAX_VALUE - 1)
            throw new IllegalArgumentException("Limit must be between 1 and " + (Integer.MAX_VALUE - 1));

        int n = (int)limit/2;
        // Create and initialise the sieve with 'true' values
        BitSet prime = new BitSet((int)limit);
        prime.set(0, (int)limit);

        // Reset all composite numbers in the sieve using Sundaram's algorithm
        for (int i = 1; i < n; i++)
            for (int j = i; j <= (n - i) / (2 * i + 1); j++)
                prime.clear(i + j + 2 * i * j);

        // Sieve of Sundaram does not produce prime 2 so we need to concat to get a complete list
        return LongStream.concat(
                    LongStream.of(2),
                    IntStream.range(1, prime.length() / 2 + 1)
                        .filter(prime::get)
                        .mapToLong(i -> 2 * i + 1))
                .boxed()
                .collect(Collectors.toList());
    }
}
