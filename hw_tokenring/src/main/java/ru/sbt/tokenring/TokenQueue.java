package ru.sbt.tokenring;

import java.util.concurrent.BlockingQueue;

public class TokenQueue {
    private final BlockingQueue<Token> queue;

    public TokenQueue(BlockingQueue<Token> queue) {
        this.queue = queue;
    }

    public void push(Token token) throws InterruptedException {
        queue.put(token);
    }

    public int size() {
        return queue.size();
    }

    public Token poll() throws InterruptedException {
        return queue.take();
    }
}
