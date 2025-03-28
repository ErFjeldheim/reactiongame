package reactiongame.model;

import java.time.Instant;
import java.time.Duration;


public class ReactionTest {

    public enum TestStatus { 
        WAITING,            //Venter på at knapp skal vises
        SHOWING_STIMULUS,   //Knapp vises, bruker kan registrere trykk
        COMPLETED,          //Bruker trykker mellomrom
        EARLY_CLICK         //Bruker trykker for tidlig
    }

    private Instant stimulusTime;
    private Instant userReactionTime;
    private long reactionTime;
    private TestStatus status;

    public ReactionTest(){ //Initialiserer statusobjekt
        this.status = TestStatus.WAITING;
    }
    
    public void showStimulus() {
        if (this.status != TestStatus.WAITING) {
            throw new IllegalStateException("Test is not in waiting state");
        }
        this.stimulusTime = Instant.now();
        this.status = TestStatus.SHOWING_STIMULUS;
    }

    public void recordReaction() {
        if (this.status != TestStatus.SHOWING_STIMULUS){
            this.status = TestStatus.EARLY_CLICK;
            throw new IllegalStateException("Teiting! Du trykker for tidlig");
        }
        this.userReactionTime = Instant.now();
        this.reactionTime = Duration.between(stimulusTime, userReactionTime).toMillis();
        this.status = TestStatus.COMPLETED;
    }

    public long getReactionTime() {
        if (this.status != TestStatus.COMPLETED) {
            throw new IllegalStateException("Test is not completed yet");
        }
        return this.reactionTime;
    }

    public boolean isValidReaction() {
        //Sjekker at brukeren trykker etter rød knapp vises
        return this.status == TestStatus.COMPLETED;
    }

    public TestStatus getStatus(){
        return this.status;
    }
}
