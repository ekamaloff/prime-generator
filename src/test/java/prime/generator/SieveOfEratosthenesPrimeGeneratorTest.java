package prime.generator;

/**
 * Tests for {@link SieveOfEratosthenesPrimeGenerator}.
 */
public class SieveOfEratosthenesPrimeGeneratorTest extends PrimeGeneratorTestBase {
    @Override
    protected PrimeGenerator getPrimeGenerator() {
        return new SieveOfEratosthenesPrimeGenerator();
    }
}
