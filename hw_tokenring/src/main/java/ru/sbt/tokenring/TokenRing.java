package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class TokenRing {
    private final int size;
    private final List<TokenQueue> queues;
    private final List<Node> nodes;
    private final List<Thread> threads;

    public TokenRing(int size, List<TokenQueue> queues, List<Node> nodes, List<Thread> threads) {
        this.size = size;
        this.queues = queues;
        this.nodes = nodes;
        this.threads = threads;
    }

    public static TokenRing factory(List<TokenQueue> queues) {
        int count = queues.size();

        Consumer<Token> tokenConsumer = token -> {};

        List<Node> nodes = queues.stream()
                .map(queue -> new Node(queue, tokenConsumer))
                .peek(Node::getId).toList();

        List<Thread> threads = nodes.stream()
                .map(n -> {
                    int prevId = nodes.indexOf(n) - 1;
                    if (prevId < 0) {
                        prevId = count - 1;
                    }
                    //System.out.println("Thread " + n.getId() + ", queue " + queues.get(prevId));
                    return new NodeThread(n, queues.get(prevId));
                }).map(Thread::new)
                .toList();

        return new TokenRing(count, queues, nodes, threads);
    }

    public int size() {
        return size;
    }

    public void start() {
        System.out.println("Starting with " + TokenRingParameters.threadsCount + " threads, " +
                TokenRingParameters.tokensCount + " tokens");
        threads.forEach(Thread::start);
    }

    public void stop() {
        System.out.println("Terminating");
        outputInfo();
        threads.stream()
                .peek(Thread::interrupt)
                .forEach(thread -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void outputInfo() {
        System.err.println("Throughput info in tokens per second");
        nodes.forEach(Node::outputInfo);
    }
}
