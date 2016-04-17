package prime.generator;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import prime.PrimeController;
import prime.PrimeResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Tests for {@link prime.ConcurrentRangePrimeCache}.
 */
public class PrimeControllerTest {

    private ExecutorService executorService;
    private PrimeController controller;
    private Random random;

    @Before
    public void setup() throws Exception {
        executorService = Executors.newFixedThreadPool(5);
        controller = new PrimeController();
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void testConcurrentUpdates() {
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            long from = random.nextInt(100_000) + 1;
            long to = from + random.nextInt(1_000_000) + 10;
            Future<?> future = executorService.submit(() -> {
                PrimeResult primes = controller.getPrimes(String.valueOf(from), String.valueOf(to));
                for (Long n : primes.getPrimes()) {
                    Assert.assertTrue(PrimeGeneratorTestBase.isPrime(n));
                }
            });
            futures.add(future);
        }
        for (Future<?> future: futures) {
            try {
                future.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                Assert.fail("Exception occurred: " + e.getMessage());
            }
        }
    }
}
