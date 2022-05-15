package ru.sbt.tokenring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Main {
    public static TokenRing prepareTokenRingNThreads(int n) {
        List<TokenQueue> queues = new ArrayList<>();
        //queues = Arrays.asList(new TokenQueue[n]);
        for (int i = 0; i < n; ++i) {
            queues.add(new TokenQueue(new ArrayBlockingQueue<Token>(2 * n)));
        }
        return TokenRing.factory(queues);
    }

    public static void main(String[] args) throws InterruptedException {
        TokenRing tokenRing = prepareTokenRingNThreads(3);
        tokenRing.start();
        sleep(200);
        tokenRing.stop();
    }
}
