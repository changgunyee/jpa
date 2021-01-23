package com.coupang.demo.groovy

import spock.lang.Specification

class MultipleInteractionTest extends Specification {


    Publisher publisher = new Publisher()
    Subscriber subscriber = Mock(Subscriber) {
        it.receive("hello2") >> "hi"
    }
    Subscriber subscriber2 = Mock(Subscriber)

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
        publisher.subscribers << subscriber2
    }


    def "multiple interactions"() {
        when:
        publisher.send("message1")

        then:
        1 * subscriber.receive("message1")

        when:
        publisher.send("message2")

        then:
        1 * subscriber.receive("message2")
    }

    def "use multiple then"() {
        when:
        publisher.send("hello")
        publisher.send("hello")
        publisher.send("goodbye")

        then:
        2 * subscriber.receive("hello")

        then:
        1 * subscriber.receive("goodbye")
    }

    def "mocking when created"() {
        expect:
        subscriber.receive("hello2") == "hi"
    }

    def "chaining method responses"() {
        given:
        subscriber.receive(_) >>> ["ok", "fail"] >> { throw new InternalError() } >> "ok"

        when:
        def receive = subscriber.receive()
        def receive1 = subscriber.receive()
        subscriber.receive()

        then:
        receive == "ok"
        receive1 == "fail"
        thrown(InternalError)

        when:
        def receive3 = subscriber.receive()

        then:
        receive3 == subscriber.receive()
    }

    def "mocking and stubbing of the same method call has to happen in the same interaction"() {
        given:
        subscriber.receive("message1") >> "ok"

        when:
        publisher.send("message1")

        then:
        1 * subscriber.receive("message1")
    }
}
