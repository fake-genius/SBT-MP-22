package ru.sbt.tokenring;

import ru.sbt.tokenring.util.ThreadID;

import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Thread.sleep;

public class Node {
    private final TokenQueue next;
    private final Consumer<Token> tokenConsumer;
    private long checkpoint = System.nanoTime();
    private long tokensCount = 0;
    private long throuputMin = 1_000_000_000_000L;
    private long throuputMax = 0;
    private int internalId;

    public Node(TokenQueue next, Consumer<Token> tokenConsumer) {
        this.next = next;
        this.tokenConsumer = tokenConsumer;
    }

    public int getId() {
        return ThreadID.get();
    }

    public void outputInfo() {
        System.err.println("Node " + internalId + ", throughput min " + throuputMin);
        System.err.println("Node " + internalId + ", throughput max " + throuputMax);
    }

    public void receive(Token token) throws InterruptedException {
        if (getId() == token.getDestinationId()) {
            //System.out.println("Node " + getId() + " accepting token with dest id " + token.getDestinationId());
            tokenConsumer.accept(token);
            //sleep(20);
            //System.out.println("Node " + getId() + ", latency " + token.getWayTime());
            token.addLatency(token.getWayTime());
        }
        tokensCount++;
        next.push(token);
       // System.out.println("Node " + getId() + " received a token with dest id " + token.getDestinationId() +
                //" and pushed it to " + next.size() + " queue " + next);
        //sleep(20);
        if (System.nanoTime() - checkpoint > 1_000_000_000) {
            throuputMin = min(throuputMin, tokensCount);
            throuputMax = max(throuputMax, tokensCount);
            checkpoint = System.nanoTime();
        }
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }
}
