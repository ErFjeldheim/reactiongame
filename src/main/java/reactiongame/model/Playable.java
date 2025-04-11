package reactiongame.model;

public interface Playable {
    void start();
    void recordReaction();
    long getResponseTime();
    boolean isCompleted();
} 