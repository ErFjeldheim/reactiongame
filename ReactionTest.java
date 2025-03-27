package reactiongame;
import java.time.Instant;

import reactiongame.ReactionTest.TestStatus;


public class ReactionTest {

    public enum TestStatus { 
        WAITING,
        SHOWING_STIMULUS,
        COMPLETED,
        EARLY_CLICK

    }

    private Instant stimulusTime;
    private Instant userReactionTime;
    private long reactionTime;
    private TestStatus status;
    
    public void showStimulus() {
        if (this.status != TestStatus.WAITING) {
            throw new IllegalStateException("Test is not in waiting state");
        }
        this.stimulusTime = Instant.now();
        this.status = TestStatus.SHOWING_STIMULUS;

    }

    public void recordReaction() {

    }

    public long getReactionTime() {
        if (this.status != TestStatus.COMPLETED) {
            throw new IllegalStateException("Test is not completed yet");
        }
        return this.reactionTime;
    }

    public boolean isValidReaction() {

    }
}
