package ru.sbt.tokenring;

import ru.sbt.tokenring.queues.TokenBlockingQueue;
import ru.sbt.tokenring.queues.TokenLinkedQueue;
import ru.sbt.tokenring.queues.TokenQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.sleep;

public class Main {

    public static TokenRingCharacteristics characteristics;

    public static boolean enableStat = false;

    public static TokenRing prepareTokenRingNThreads(int n) {
        List<TokenQueue> queues = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            //queues.add(new TokenBlockingQueue(new ArrayBlockingQueue<>(TokenRingParameters.tokensCount)));
            queues.add(new TokenLinkedQueue(new ConcurrentLinkedQueue<>()));
        }
        return TokenRing.factory(queues);
    }

    public static void main(String[] args) throws InterruptedException {
        TokenRing tokenRing = prepareTokenRingNThreads(TokenRingParameters.threadsCount);
        tokenRing.start();
        sleep(5000);
        enableStat = true;
        sleep(10000);
        tokenRing.stop();
    }
}
