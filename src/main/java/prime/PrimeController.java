package prime;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prime.generator.PrimeGenerator;

/**
 * Controller for the Restful service.
 */
@RestController
public class PrimeController {

    private final PrimeGenerator generator;
    private final PrimeCache cache;

    public PrimeController() throws Exception {
        Properties props = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("prime_controller.properties");
        props.load(inputStream);
        Class<?> generatorClass = Class.forName(props.getProperty("prime.generator.class"));
        generator = (PrimeGenerator) generatorClass.newInstance();
        Class<?> cacheClass = Class.forName(props.getProperty("prime.generator.cache.class"));
        cache = (PrimeCache)cacheClass.newInstance();
    }

    /**
     * Handler for 'getPrimes' request that generates and returns prime numbers in the requested range.
     * @param fromStr range start (string)
     * @param toStr range end (string)
     * @return prime number result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getPrimes")
    public PrimeResult getPrimes(
            @RequestParam(value="from", defaultValue = "1") String fromStr,
            @RequestParam(value="to", defaultValue="10000") String toStr) {

        long from = Long.parseLong(fromStr);
        long to = Long.parseLong(toStr);

        if (to < from)
            throw new IllegalArgumentException("Incorrect range");

        List<Long> primes = cache.getPrimes(from, to);
        if (primes == null) {
            primes = generator.findAll(from, to);
            cache.addAll(from, to, primes);
            return new PrimeResult(primes);
        } else {
            System.out.println(Thread.currentThread().getName() + ": Found in cache: from " + from + " to " + to);
            return new PrimeResult(primes);
        }
    }

}
