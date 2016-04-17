package prime;

import java.util.List;

/**
 * Defines interface for cache capable of storing prime numbers.
 */
public interface PrimeCache {
    /**
     * Add all primes for the given range into the cache. Ignores the primes that are already in the cache.
     * The list must be ordered or the result of the operation is undefined.
     * @param from range start
     * @param to range end
     * @param primes list of primes
     */
    void addAll(long from, long to, List<Long> primes);

    /**
     * Get all primes in cache in the range given by [from..to] (both inclusive).
     * If the full range is not available in cache, return null.
     * @param from range start
     * @param to range end
     * @return list of primes or null
     */
    List<Long> getPrimes(long from, long to);

}
