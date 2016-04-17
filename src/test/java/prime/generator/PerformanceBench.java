package prime.generator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Performance test bench for all generators.
 */
public class PerformanceBench {

    private static final long TEST_LIMIT = 1_000_000L;

    private static final long TEST_RANGE_START = 10_000_000_000L;
    private static final long TEST_RANGE_END = 10_001_000_000L;

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     * Please make sure to build maven project first before running this test.
     * From the project directory run:
     *
     *    mvn clean install
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar PerformanceBench -wi 5 -i 5 -f 1
     *    (we requested 5 warmup/measurement iterations, single fork)
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(PerformanceBench.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opts).run();
    }

    @Benchmark
    @Group("LimitOnly")
    @BenchmarkMode(Mode.AverageTime)
    public void runSieveOfAtkinGenerator() {
        new SieveOfAtkinPrimeGenerator().findAll(TEST_LIMIT);
    }

    @Benchmark
    @Group("LimitOnly")
    @BenchmarkMode(Mode.AverageTime)
    public void runSieveOfEratosthenesGenerator() {
        new SieveOfEratosthenesPrimeGenerator().findAll(TEST_LIMIT);
    }

    @Benchmark
    @Group("LimitOnly")
    @BenchmarkMode(Mode.AverageTime)
    public void runSieveOfEratosthenesSegmentedGenerator() {
        new SieveOfEratosthenesSegmentedPrimeGenerator().findAll(TEST_LIMIT);
    }

    @Benchmark
    @Group("LimitOnly")
    @BenchmarkMode(Mode.AverageTime)
    public void runTrialDivisionPrimeGenerator() {
        new TrialDivisionParallelPrimeGenerator().findAll(TEST_LIMIT);
    }

    @Benchmark
    @Group("GenerateRange")
    @BenchmarkMode(Mode.AverageTime)
    public void runTrialDivisionPrimeGeneratorInRange() {
        new TrialDivisionParallelPrimeGenerator().findAll(TEST_RANGE_START, TEST_RANGE_END);
    }

    @Benchmark
    @Group("LimitOnly")
    @BenchmarkMode(Mode.AverageTime)
    public void runSieveOfSundaramPrimeGenerator() {
        new SieveOfSundaramPrimeGenerator().findAll(TEST_LIMIT);
    }


}
