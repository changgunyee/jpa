package com.coupang.demo.groovy

import org.opentest4j.MultipleFailuresError
import spock.lang.FailsWith
import spock.lang.Specification

class InteractionTest extends Specification {

    Publisher publisher = new Publisher()
    Subscriber subscriber = Mock(Subscriber) // Mock should not be @Shared
    Subscriber subscriber2 = Mock(Subscriber)

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
        publisher.subscribers << subscriber2
    }

    def "interface can be mock"() {
        when:
        publisher.send("a")

        then:
        1 * sub1.receive("a")
        1 * sub2.receive("a")
    }

    def "where is evaluated before the feature"() {
        expect:
        Math.max(a as int, b as int) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    class PC {
        public String vendor = "SAMSUNG"
        public String os = "LINUX"
    }

    def "can use helper method"() {
        when:
        def pc = new PC()

        then: "helper must return void using assert"
        matchPC(pc)
    }

    void matchPC(PC pc) {
        assert pc.vendor == "SAMSUNG"
    }

    def "using with for expectations"() {
        when:
        def pc = new PC()

        then:
        with(pc) {
            vendor == "SAMSUNG"
            os == "LINUX"
        }
    }

    def "with is effective when mocking"() {
        given:

        when:
        publisher.send("a")

        then:
        with(sub1) {
            1 * receive("a")
        }
    }

    @FailsWith(value = MultipleFailuresError, reason = "unknown")
    def "verifyAll show all errors"() {
        expect:
        verifyAll {
            1 == 2
            2 == 3
        }
    }
}

