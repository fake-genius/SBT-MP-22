package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.Thread.sleep;

public class Main {

    public static TokenRingCharacteristics characteristics;
    public static TokenRing prepareTokenRingNThreads(int n) {
        List<TokenQueue> queues = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            queues.add(new TokenQueue(new ArrayBlockingQueue<Token>(2 * n)));
        }
        return TokenRing.factory(queues);
    }

    public static void main(String[] args) throws InterruptedException {
        TokenRing tokenRing = prepareTokenRingNThreads(TokenRingParameters.threadsCount);
        tokenRing.start();
        sleep(10000);
        tokenRing.stop();
    }
}
