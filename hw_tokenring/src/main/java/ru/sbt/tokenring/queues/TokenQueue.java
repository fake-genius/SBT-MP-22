package ru.sbt.tokenring.queues;

import ru.sbt.tokenring.Token;

public interface TokenQueue {
    void push(Token token) throws InterruptedException;

    Token poll() throws InterruptedException;

    Token peek();

    int size();
}
