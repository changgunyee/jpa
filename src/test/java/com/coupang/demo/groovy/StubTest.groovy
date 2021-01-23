package com.coupang.demo.groovy

import spock.lang.Specification

class StubTest extends Specification {

    Publisher publisher = new Publisher()
    Subscriber subscriber = Stub {  //Stub은 shorthand mock->stub
        receive("message1") >> "ok"
        receive("message2") >> "fail"
    }

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
    }

    def "simple stub test"() {
        when:
        def ok = subscriber.receive("message1")
        def fail = subscriber.receive("message2")

        then:
        ok == "ok"
        fail == "fail"
    }
}
