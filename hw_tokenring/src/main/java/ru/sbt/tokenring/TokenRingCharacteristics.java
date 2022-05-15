package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.List;

public class TokenRingCharacteristics {
    private static final List<Long> throughputs = new ArrayList<>();
    private static final List<Long> latencies = new ArrayList<>();

    public static void addLatency(Long latency) {
        latencies.add(latency);
    }

    public static void addThroughput(Long throughput) {
        throughputs.add(throughput);
    }

    public static float averageLong(List<Long> source) {
        float sum = 0f;
        for (Long value : source) {
            sum += value;
        }
        return sum / (float) source.size();
    }

    private static float averageLatency() {
        return averageLong(latencies) / 1_000_000f;
    }

    private static float averageThroughput() {
        return averageLong(throughputs);
    }

    public static void outputInfo() {
        System.out.println("Average throughput in tokens per second on one node: " + averageThroughput());
        System.out.println("Average latency in millis: " + averageLatency());
    }
}
