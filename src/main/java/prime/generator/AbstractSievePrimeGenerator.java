package prime.generator;

import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for sieve-based prime generators.
 */
abstract class AbstractSievePrimeGenerator implements PrimeGenerator {
    @Override
    public List<Long> findAll(long from, long limit) {
        if (limit < from)
            throw new IllegalArgumentException("Limit must be greater or equal to 'from'");

        List<Long> list = findAll(limit);
        int idx = Collections.binarySearch(list, from);
        if (idx >= 0) {
            return list.subList(idx, list.size());
        } else {
            return list.subList(-idx - 1, list.size());
        }
    }
}
