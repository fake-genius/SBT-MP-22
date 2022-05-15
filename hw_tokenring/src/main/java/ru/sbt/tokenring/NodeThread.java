package ru.sbt.tokenring;

import static java.lang.Thread.sleep;

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
            //System.out.println("Thread " + node.getId() + " queue is " + queue);
            while(!Thread.currentThread().isInterrupted()) {
                //sleep(20);
                //System.out.println("Thread " + node.getId() + " trying to receive, current queue size " + queue.size());
                Token token = queue.poll();
                node.receive(token);
            }
        } catch (InterruptedException e) {
            queue.outputInfo();

            //System.err.println("Node " + node.getId() + " was interrupted");
        }
    }
}
