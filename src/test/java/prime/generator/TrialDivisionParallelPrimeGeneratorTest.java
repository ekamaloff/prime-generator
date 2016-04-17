package prime.generator;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tests for {@link TrialDivisionParallelPrimeGenerator}.
 */
public class TrialDivisionParallelPrimeGeneratorTest extends PrimeGeneratorTestBase {
    @Override
    protected PrimeGenerator getPrimeGenerator() {
        return new TrialDivisionParallelPrimeGenerator();
    }

    @Test
    public void testLargePrimes() {
        PrimeGenerator generator = getPrimeGenerator();
        List<Long> list = generator.findAll(10_000_000_000L, 10_001_000_000L);

        Assert.assertTrue("must produce at least one prime", !list.isEmpty());
        for (int i = 0; i < list.size(); i += 10) {
            Assert.assertTrue("number must be prime", isPrime(list.get(i)));
        }
    }
}
