package com.coupang.demo.groovy

import spock.lang.Specification

class BlockTest extends Specification {

    def stack = new Stack()

    def "simple when then test"() {
        when:
        stack.push(1)

        then: "condition has no need to have assert"
        assert stack.peek() == 1
    }

    def "exception test"() {
        when:
        stack.pop()

        then: "no need to use class annotation"
        def e = thrown(EmptyStackException)
        e.cause == null
    }

    def "not Thrown is possible"() {
        given:
        def map = new HashMap()

        when:
        map.put(null, "element")

        then:
        notThrown(NullPointerException)
    }
}
