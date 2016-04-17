package prime.generator;

/**
 * Tests for {@link SieveOfAtkinPrimeGenerator}.
 */
public class SieveOfAtkinPrimeGeneratorTest extends PrimeGeneratorTestBase {
    @Override
    protected PrimeGenerator getPrimeGenerator() {
        return new SieveOfAtkinPrimeGenerator();
    }
}
