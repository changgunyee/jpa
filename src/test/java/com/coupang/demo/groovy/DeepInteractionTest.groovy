package com.coupang.demo.groovy

import spock.lang.FailsWith
import spock.lang.Specification

class DeepInteractionTest extends Specification {
    
    Publisher publisher = new Publisher()
    Subscriber subscriber = Mock(Subscriber)
    Subscriber subscriber2 = Mock(Subscriber)

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
        publisher.subscribers << subscriber2
    }

    def "simple interaction"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive("hello")
    }

    def "_ can be used in object"() {
        when:
        publisher.send("hello")

        then:
        2 * _.receive("hello")
    }

    def "regex can be used"() {
        when:
        publisher.send("hello")

        then:
        2 * _./r.*e/("hello")
    }

    def "arguments contraints can be various"() {
        when:
        publisher.send("hello")

        then:
//        1 * subscriber.receive("hello")        // an argument that is equal to the String "hello"
//        1 * subscriber.receive(!"bye")       // an argument that is unequal to the String "hello"
//        1 * subscriber.receive(_)              // any single argument (including null)
//        1 * subscriber.receive(*_)             // any argument list (including the empty argument list)
//        1 * subscriber.receive(!null)          // any non-null argument
//        1 * subscriber.receive(_ as String)    // any non-null argument that is-a String
        1 * subscriber.receive({ it.size() > 3 && it.contains('hello') })
        // an argument that satisfies the given predicate, meaning that
        // code argument constraints need to return true of false
        // depending on whether they match or not
        // (here: message length is greater than 3 and contains the character a)
    }

    def "closure is like then block"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive({
            it == "hello"
        })
    }

    def "!null is useful"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive(!null)
    }

    def "use wildcard effectively"() {
        when:
        publisher.send("hello")

        then:
        _ * subscriber._                // allow any interaction with 'subscriber'
        _ * subscriber2._                // allow any interaction with 'subscriber'
        0 * _                           // don't allow any other interaction
    }

    def "mixing interaction and condition"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive("hello")
        publisher.messageCount == 1
    }

    @FailsWith(value = MissingPropertyException)
    def "spock must know all info(include interaction) before when block"() {
        when:
        publisher.send("hello")

        then: "only inters in then is moved before when"
        def message = "hello"
        1 * subscriber.receive(message)
    }

    def "be explicit by using interaction"() {
        when:
        publisher.send("hello")

        then:
        interaction {
            def message = "hello"
            1 * subscriber.receive(message)
        }
    }
}
