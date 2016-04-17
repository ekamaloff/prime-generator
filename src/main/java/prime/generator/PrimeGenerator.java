package prime.generator;

import java.util.List;

/**
 * Defines an interface for generating all prime numbers in given range.
 */
public interface PrimeGenerator {
    /**
     * Find all prime numbers up to the given limit.
     * The limit can be any positive long and does not have to be a prime number.
     * @param limit upper limit
     * @return list of primes or empty list if none found
     */
    List<Long> findAll(long limit);

    /**
     * Find all prime numbers in the given range.
     * I.e. this method will return all prime numbers greater or equal to 'from' but less than or equal to 'limit'.
     * @param from range start
     * @param limit range end
     * @return list of primes
     */
    List<Long> findAll(long from, long limit);
}
