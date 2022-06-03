package ru.sbt.tokenring.queues;

import ru.sbt.tokenring.Token;

import java.util.concurrent.BlockingQueue;

public class TokenBlockingQueue implements TokenQueue {
    private final BlockingQueue<Token> queue;

    public TokenBlockingQueue(BlockingQueue<Token> queue) {
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

    public Token peek() {
        return queue.peek();
    }

    public void outputInfo() {
        queue.forEach(Token::outputInfo);
    }
}
