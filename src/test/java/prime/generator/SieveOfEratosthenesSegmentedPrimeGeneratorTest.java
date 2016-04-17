package prime.generator;

/**
 * Tests for {@link SieveOfEratosthenesSegmentedPrimeGenerator}.
 */
public class SieveOfEratosthenesSegmentedPrimeGeneratorTest extends PrimeGeneratorTestBase {
    @Override
    protected PrimeGenerator getPrimeGenerator() {
        return new SieveOfEratosthenesSegmentedPrimeGenerator();
    }
}
