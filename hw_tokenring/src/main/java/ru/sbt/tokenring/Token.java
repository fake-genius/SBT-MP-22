package ru.sbt.tokenring;

public class Token {
    private final int destinationId;

    public Token(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getDestinationId() {
        return destinationId;
    }
}
