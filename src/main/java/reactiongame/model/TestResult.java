package reactiongame.model;

import java.time.LocalDateTime;

public class TestResult {
    private long reactionTime;
    private LocalDateTime timestamp;

    public TestResult(long reactionTime){
        this.reactionTime = reactionTime;
        this.timestamp = LocalDateTime.now();
    }

    public long getReactionTime(){
        return reactionTime;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}
