package ru.sbt.tokenring;

import ru.sbt.tokenring.queues.TokenQueue;


public class NodeThread implements Runnable{
    private final Node node;
    private final TokenQueue queue;

    public NodeThread(Node node, TokenQueue queue) {
        this.node = node;
        this.queue = queue;
    }

    private void generateTokens() throws InterruptedException {
        if (node.getId() == 1) {
            for (int i = 0; i <TokenRingParameters.tokensCount; ++i) {
                Token token = new Token(node.getId(), i % TokenRingParameters.threadsCount + 1);
                queue.push(token);
            }
        }
    }

    @Override
    public void run() {
        try {
            node.setInternalId(node.getId());
            generateTokens();
            while(!Thread.currentThread().isInterrupted()) {
                Token token = queue.peek();
                while (token == null) {
                    token = queue.peek();
                }
                token =  queue.poll();
                node.receive(token);
            }
        } catch (InterruptedException e) {
        }
    }
}
