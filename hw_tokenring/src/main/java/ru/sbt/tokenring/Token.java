package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.List;

public class Token {
    private final int startingNode;
    private final int destinationId;
    private long checkpointTime;
    private final List<Long> latencies = new ArrayList<>();

    public Token(int startingNode, int destinationId) {
        this.startingNode = startingNode;
        this.destinationId = destinationId;
        checkpointTime = System.nanoTime();
    }

    public long getWayTime() {
        return System.nanoTime() - checkpointTime;
    }

    public void updateTime() {
        checkpointTime = System.nanoTime();
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
