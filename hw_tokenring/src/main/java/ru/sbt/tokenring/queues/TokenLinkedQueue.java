package ru.sbt.tokenring.queues;

import ru.sbt.tokenring.Token;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenLinkedQueue implements TokenQueue{

    private final ConcurrentLinkedQueue<Token> queue;

    public TokenLinkedQueue(ConcurrentLinkedQueue<Token> queue) {
        this.queue = queue;
    }

    @Override
    public void push(Token token) throws InterruptedException {
        queue.add(token);
    }

    @Override
    public Token poll() throws InterruptedException {
        return queue.poll();
    }

    @Override
    public Token peek() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }
}
