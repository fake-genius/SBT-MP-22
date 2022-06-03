package ru.sbt.tokenring;

import ru.sbt.tokenring.queues.TokenQueue;
import ru.sbt.tokenring.util.ThreadID;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Thread.sleep;
import static ru.sbt.tokenring.Main.enableStat;

public class Node {
    private final TokenQueue next;
    private final Consumer<Token> tokenConsumer;
    private long checkpoint = System.nanoTime();
    private long tokensCount = 0;

    private List<Long> throughputs = new ArrayList<>();
    private int internalId;

    public Node(TokenQueue next, Consumer<Token> tokenConsumer) {
        this.next = next;
        this.tokenConsumer = tokenConsumer;
    }

    public int getId() {
        return ThreadID.get();
    }

    public void outputInfo() {
        float throughputAverage = TokenRingCharacteristics.averageLong(throughputs);
        TokenRingCharacteristics.addThroughput(throughputAverage);
        System.err.println("Node " + internalId + ", throughput average " + throughputAverage);
    }

    public void receive(Token token) throws InterruptedException {
        if (getId() == token.getDestinationId()) {
            tokenConsumer.accept(token);
        }
        if (getId() == token.getStartingNode() && tokensCount > 0 && enableStat) {
            token.addLatency(token.getWayTime());
            token.updateTime();
        }

        tokensCount++;
        next.push(token);
        if (System.nanoTime() - checkpoint > 1_000_000_000 && enableStat) {
            throughputs.add(tokensCount);
            checkpoint = System.nanoTime();
            tokensCount = 0;
        }
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }
}
