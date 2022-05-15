package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.List;

public class Token {
    private final int startingNode;
    private final int destinationId;
    private final long startingTime;
    private final List<Long> latencies = new ArrayList<>();

    public Token(int startingNode, int destinationId) {
        this.startingNode = startingNode;
        this.destinationId = destinationId;
        startingTime = System.nanoTime();
    }

    public long getWayTime() {
        return System.nanoTime() - startingTime;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void addLatency(long latency) {
        latencies.add(latency);
        TokenRingCharacteristics.addLatency(latency);
    }

    public void outputInfo() {
        System.err.println("Token with dest " + destinationId + ", average latency "
                + TokenRingCharacteristics.averageLong(latencies) / 1_000_000f);
    }

    public int getStartingNode() {
        return startingNode;
    }
}
