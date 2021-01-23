package com.coupang.demo.groovy

import spock.lang.Specification
import spock.lang.Unroll

class DataDrivenTest extends Specification {


    def "data tables is efficient"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }

    def "|| visually split input and output"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
    }

    @Unroll
    def "@unroll reports iterations independently"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
    }

    def "combining data tables, data pipes"() {
        expect:
        Math.max(a, b) == c

        where:
        a | c
        3 | 5
        7 | 7
        0 | 0

        b << [5, 0, 0]
    }

    def "#name"() {
        expect:
        name == "changgunyee"

        where: "where-blocks may only contain parameterizations (e.g. 'salary << [1000, 5000, 9000];"
        name << ["changgunyee"]
    }
}
