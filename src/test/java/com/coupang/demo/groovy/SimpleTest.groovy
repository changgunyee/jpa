package com.coupang.demo.groovy

import com.coupang.demo.entity.Item
import spock.lang.Shared
import spock.lang.Specification

class SimpleTest extends Specification {

    //@Shared는 공유 되는 객체
    @Shared
    def item = new Item(0, null)

    // constant는 static final로
    static final PI = 3.14

    def setup() {
        item.plusIdOne()
    }

    def setupSpec() {
        item.plusIdOne()
    }

    def "given is actually nothing"() {
        given:
        expect:
        assert item.id == 2
    }

    def "then must have when right before"() {
        expect:
        assert item.id == 3
    }
}
