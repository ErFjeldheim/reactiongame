package reactiongame.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReactionTestTest {
    private ReactionTest reactionTest;
    private ResultManager resultManager;

    @BeforeEach
    public void setUp() {
        reactionTest = new ReactionTest();
        resultManager = new ResultManager();
    }

    // Test reaksjonstidsberegning
    @Test
    public void testReactionTimeCalculation() {
        reactionTest.showStimulus();
        long startTime = System.currentTimeMillis();
        reactionTest.recordReaction();
        long endTime = System.currentTimeMillis();
        long measuredTime = reactionTest.getReactionTime();
        
        // Verifiser at målt tid er innenfor rimelig grense
        assertTrue(measuredTime >= 0, "Reaksjonstiden skal være positiv");
        assertTrue(measuredTime <= (endTime - startTime + 100), 
            "Målt tid skal være mindre enn eller lik faktisk tid pluss margin");
    }

    // Test validering av for tidlige klikk
    @Test
    public void testEarlyClickValidation() {
        // Prøv å registrere reaksjon før stimulus
        assertThrows(IllegalStateException.class, () -> {
            reactionTest.recordReaction();
        }, "Skal kaste unntak ved for tidlig klikk");

        // Prøv å registrere reaksjon etter at testen er fullført
        reactionTest.showStimulus();
        reactionTest.recordReaction();
        assertThrows(IllegalStateException.class, () -> {
            reactionTest.recordReaction();
        }, "Skal kaste unntak ved dobbelt klikk");
    }

    // Test statistikkberegning
    @Test
    public void testStatisticsCalculation() {
        // Legg til flere testresultater
        resultManager.addResult(new TestResult(100));
        resultManager.addResult(new TestResult(200));
        resultManager.addResult(new TestResult(300));

        // Test beste tid
        assertEquals(100, resultManager.getBestTime().orElseThrow(), 
            "Beste tid skal være 100ms");

        // Test gjennomsnitt
        assertEquals(200.0, resultManager.getAverageTime().orElseThrow(), 
            "Gjennomsnittlig tid skal være 200ms");
    }

    // Test tilfeldige intervaller
    @Test
    public void testRandomIntervals() {
        final int MIN_DELAY = 1000;
        final int MAX_DELAY = 5000;
        
        // Test flere tilfeldige intervaller

    }

    // Test tilstandshåndtering
    @Test
    public void testStateManagement() {
        
    }

    // Test sekvens av handlinger
    @Test
    public void testActionSequence() {

    }
}
