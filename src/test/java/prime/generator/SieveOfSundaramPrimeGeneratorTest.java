package prime.generator;

/**
 * Tests for {@link SieveOfSundaramPrimeGenerator}.
 */
public class SieveOfSundaramPrimeGeneratorTest extends PrimeGeneratorTestBase {
    @Override
    protected PrimeGenerator getPrimeGenerator() {
        return new SieveOfSundaramPrimeGenerator();
    }
}
