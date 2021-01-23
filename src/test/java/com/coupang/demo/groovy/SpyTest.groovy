package com.coupang.demo.groovy

import spock.lang.Specification

class SpyTest extends Specification {

    class SubscriberImpl implements Subscriber {
        @Override
        String receive(String message) {
            return null
        }

        String receive2(String message) {
            return "real receive"
        }
    }
    Publisher publisher = new Publisher()
    SubscriberImpl subscriber = Spy(constructorArgs: null)  //Spy is bad feature
    // It might be better to change the design of the code

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
    }

    def "spy is used to mock partial function in real instance"() {
        given:
        subscriber.receive(_) >> "mock receive"

        when:
        def receive = subscriber.receive()
        def receive2 = subscriber.receive2()

        then:
        receive == "mock receive"
        receive2 == "real receive"
    }
}
