package com.coupang.demo.groovy

class Publisher {
    List<Subscriber> subscribers = []
    int messageCount = 0

    void send(String message) {
        subscribers*.receive(message)
        messageCount++
    }
}
