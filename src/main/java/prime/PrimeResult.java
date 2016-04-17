package prime;

import java.util.Collection;

/**
 * Represents a result of a prime number generation for a requested range.
 */
public class PrimeResult {
    private final Collection<Long> primes;

    /**
     * Construct result object with the given prime numbers.
     * @param primes list of prime numbers
     */
    public PrimeResult(Collection<Long> primes) {
        this.primes = primes;
    }

    /**
     * Return collection of all prime numbers
     * @return collection of prime numbers
     */
    public Collection<Long> getPrimes() {
        return primes;
    }
}
