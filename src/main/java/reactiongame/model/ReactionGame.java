package reactiongame.model;

import java.time.Duration;
import java.time.Instant;

public class ReactionGame implements Playable {
    private Instant startTime;
    private Instant reactionTime;
    private long responseTime;
    private boolean started;

    @Override
    public void start(){
        startTime = Instant.now();
        started = true;
    }

    @Override
    public void recordReaction(){
        if (!started){
            throw new IllegalStateException("Teiting! Du trykket for tidlig!");
        }
        reactionTime = Instant.now();
        responseTime = Duration.between(startTime, reactionTime).toMillis();
        started = false;
    }

    @Override
    public long getResponseTime(){
        if (reactionTime == null){
            throw new IllegalStateException("Reaksjonstid er ikke registrert ennå!");
        }
        return responseTime;
    }

    @Override
    public boolean isCompleted() {
        return !started && reactionTime != null;
    }
}
