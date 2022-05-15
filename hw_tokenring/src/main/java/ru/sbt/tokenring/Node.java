package ru.sbt.tokenring;

import ru.sbt.tokenring.util.ThreadID;

import java.util.function.Consumer;

import static java.lang.Thread.sleep;

public class Node {
    private final TokenQueue next;
    private final Consumer<Token> tokenConsumer;

    public Node(TokenQueue next, Consumer<Token> tokenConsumer) {
        this.next = next;
        this.tokenConsumer = tokenConsumer;
    }

    public int getId() {
        return ThreadID.get();
    }

    public void receive(Token token) throws InterruptedException {
        if (getId() == token.getDestinationId()) {
            System.out.println("Node " + getId() + " accepting token with dest id " + token.getDestinationId());
            tokenConsumer.accept(token);
            sleep(20);
        }
        next.push(token);
        System.out.println("Node " + getId() + " received a token with dest id " + token.getDestinationId() +
                " and pushed it to " + next.size() + " queue " + next);
        sleep(20);
    }
}
