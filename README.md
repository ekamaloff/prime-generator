# prime-generator
Prime number generator

This is a RESTful service that can generate prime numbers.

In order to build and start the service run the following command from the project directory

    mvn spring-boot:run


The service supports a single request "getPrimes", that accepts two parameters 'from' and 'to'. Both parameters
must be positive long numbers and 'to' must be greater than or equal to 'from' or an exception will be generated.
The default values for these parameters are 1 and 10,000. Subsequently if 'from' parameter is omitted from the
request the service will generate all prime numbers up to the value of 'to' (limit).

See below sample requests:

    // Generate all prime numbers below 10,000
    http://localhost:8080/getPrimes

    // Generate all prime numbers below 100,000
    http://localhost:8080/getPrimes?to=100000

    // Generate all prime numbers between 10,000,000,000 and 10,001,000,000
    http://localhost:8080/getPrimes?from=10000000000&to=10001000000


When using the service to generate prime numbers >= Integer.MAX_VALUE make sure to use the
appropriate implementation (i.e. segmented sieve of Eratosthenes or the trial division) that can handle large
numbers. The implementation class can be specified in prime_controller.properties.

