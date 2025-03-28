package reactiongame.model;

import java.time.Instant;
import java.time.Duratino;
import java.time.Duration;

public class ReactionGame {
    private Instant startTime;
    private Instant reactionTime;
    private long responseTime;
    private boolean started;

    public void start(){
        startTime = Instant.now();
        started = true;
    }

    public void recordReaction(){
        if (!started){
            throw new IllegalStateException("Teiting! Du trykket for tidlig!")
        }
        reactionTime = Instant.now();
        responseTime = Duration.between(startTime, reactionTime).toMillis();
        started = false;
    }

    public long getResponseTime(){
        if (reactionTime == null){
            throw new IllegalStateException("Reaksjonstid er ikke registrert ennå!")
        }
        return responseTime;
    }
}
