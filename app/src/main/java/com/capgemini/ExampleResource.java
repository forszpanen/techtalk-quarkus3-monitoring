package com.capgemini;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.quarkus.scheduler.Scheduled;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.jboss.logging.Logger;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

@Path("/example")
@Produces("text/plain")
public class ExampleResource {
    private static final Logger LOG = Logger.getLogger(ExampleResource.class);

    private final LinkedList<Long> list = new LinkedList<>();
    private final MeterRegistry registry;
    private final Random random;

    ExampleResource(MeterRegistry registry) {
        this.registry = registry;
        registry.gaugeCollectionSize("techtalk.example.list.size", Tags.empty(), list);
        random = new Random();
    }

    @GET
    @Path("gauge/{number}")
    public Long checkListSize(@PathParam("number") long number) {
        if (number == 2 || number % 2 == 0) {
            // add even numbers to the list
            list.add(number);
            LOG.infof("Added even number: {}", number);
        } else {
            // remove items from the list for odd numbers
            try {
                number = list.removeFirst();
                LOG.infof("Added odd number: {}", number);
            } catch (NoSuchElementException nse) {
                LOG.errorf("Number: {} not found in a list", number);

                number = 0;
            }
        }
        return number;
    }

    @Scheduled(every = "1s")
    void runCheckListSize() {
        long randomIterationNumber = random.nextInt(1000); // generates a random number between 0 and 1,000,000
        LOG.infof("Generated random iteration number: {}", randomIterationNumber);

        for (int i = 0; i < randomIterationNumber; i++) {
            int number = random.nextInt(1_000_001);
            checkListSize(number);
            LOG.infof("Generated random number: {}", number);
        }
    }

    @Scheduled(every = "1s")
    void runCheckIfPrime() {
        long randomIterationNumber = random.nextInt(1000); // generates a random number between 0 and 1,000,000
        LOG.infof("Generated random iteration number: {}", randomIterationNumber);

        for (int i = 0; i < randomIterationNumber; i++) {
            int number = random.nextInt(1_000_001);
            checkIfPrime(number);
            LOG.infof("Generated random number: {}", number);
        }
    }

    @GET
    @Path("prime/{number}")
    public String checkIfPrime(@PathParam("number") long number) {
        if (number < 1) {
            registry.counter("techtalk.example.prime.number", "type", "not-natural").increment();
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            registry.counter("techtalk.example.prime.number", "type", "one").increment();
            return number + " is not prime.";
        }
        if (number == 2 || number % 2 == 0) {
            registry.counter("techtalk.example.prime.number", "type", "even").increment();
            return number + " is not prime.";
        }
        if (timedTestPrimeNumber(number)) {
            registry.counter("techtalk.example.prime.number", "type", "prime").increment();
            return number + " is prime.";
        } else {
            registry.counter("techtalk.example.prime.number", "type", "not-prime").increment();
            return number + " is not prime.";
        }
    }

    protected boolean testPrimeNumber(long number) {
        for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    protected boolean timedTestPrimeNumber(long number) {
        Timer.Sample sample = Timer.start(registry);
        boolean result = testPrimeNumber(number);
        sample.stop(registry.timer("techtalk.example.prime.number.test", "prime", result + ""));
        return result;
    }
}