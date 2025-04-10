package reactiongame.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReactionTestTest {
    private ReactionTest reactionTest;
    private ResultManager resultManager;
    private static final String TEST_FILENAME = "test_results.txt";

    @BeforeEach
    public void setUp() {
        resultManager = new ResultManager();
        // Clean up test file if it exists
        try {
            Files.deleteIfExists(Path.of(TEST_FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Test beregning av reaksjonstid
    @Test
    public void testReactionTimeCalculation() {
        reactionTest = new ReactionTest();
        // Start test
        reactionTest.showStimulus();
        long startTime = System.currentTimeMillis();
        
        // Simuler venting
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Registrer reaksjon
        reactionTest.recordReaction();
        long endTime = System.currentTimeMillis();
        long measuredTime = reactionTest.getReactionTime();
        
        // Verifiser at målt tid er innenfor rimelig grense
        assertTrue(measuredTime >= 100, "Reaksjonstiden skal være minst 100ms");
        assertTrue(measuredTime <= (endTime - startTime + 50), 
            "Målt tid skal være mindre enn eller lik faktisk tid pluss margin");
    }

    // 2. Test validering av reaksjoner
    @Test
    public void testReactionValidation() {
        reactionTest = new ReactionTest();
        // Test for tidlig klikk
        assertThrows(IllegalStateException.class, () -> {
            reactionTest.recordReaction();
        }, "Skal kaste unntak ved for tidlig klikk");

        // Test gyldig klikk
        reactionTest = new ReactionTest();
        reactionTest.showStimulus();
        assertDoesNotThrow(() -> {
            reactionTest.recordReaction();
        }, "Skal ikke kaste unntak ved gyldig klikk");

        // Test dobbelt klikk
        assertThrows(IllegalStateException.class, () -> {
            reactionTest.recordReaction();
        }, "Skal kaste unntak ved dobbelt klikk");
    }

    // 3. Test statistikkberegning
    @Test
    public void testStatisticsCalculation() {
        // Legg til testresultater
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

    // 4. Test tilfeldige intervaller
    @Test
    public void testRandomIntervals() {
        reactionTest = new ReactionTest();
        final int MIN_DELAY = 1000;
        final int MAX_DELAY = 5000;
        
        // Test 100 tilfeldige intervaller
        IntStream.range(0, 100).forEach(i -> {
            int delay = reactionTest.getRandomDelay();
            assertTrue(delay >= MIN_DELAY && delay <= MAX_DELAY, 
                "Tilfeldig forsinkelse skal være mellom " + MIN_DELAY + " og " + MAX_DELAY + "ms");
        });
    }

    // 5. Test fillagring og -innlesing
    @Test
    public void testFileStorage() {
        // Opprett testresultater
        List<TestResult> testResults = List.of(
            new TestResult(100),
            new TestResult(200),
            new TestResult(300)
        );

        // Lagre resultater
        FileStorage.saveResults(testResults);

        // Les resultater
        List<TestResult> loadedResults = FileStorage.loadResults();

        // Verifiser at data er korrekt
        assertEquals(3, loadedResults.size(), "Antall resultater skal være 3");
        assertEquals(100, loadedResults.get(0).getReactionTime(), 
            "Første resultat skal være 100ms");
        assertEquals(200, loadedResults.get(1).getReactionTime(), 
            "Andre resultat skal være 200ms");
        assertEquals(300, loadedResults.get(2).getReactionTime(), 
            "Tredje resultat skal være 300ms");
    }

    // 6. Test tilstandshåndtering
    @Test
    public void testStateManagement() {
        reactionTest = new ReactionTest();
        // Initial tilstand
        assertEquals(ReactionTest.TestStatus.WAITING, reactionTest.getStatus(), 
            "Starttilstand skal være WAITING");

        // Vis stimulus
        reactionTest.showStimulus();
        assertEquals(ReactionTest.TestStatus.SHOWING_STIMULUS, reactionTest.getStatus(), 
            "Tilstand etter showStimulus skal være SHOWING_STIMULUS");

        // Registrer reaksjon
        reactionTest.recordReaction();
        assertEquals(ReactionTest.TestStatus.COMPLETED, reactionTest.getStatus(), 
            "Tilstand etter recordReaction skal være COMPLETED");

        // Prøv å vise stimulus igjen
        assertThrows(IllegalStateException.class, () -> {
            reactionTest.showStimulus();
        }, "Skal ikke kunne vise stimulus etter at testen er fullført");
    }

    // Test sekvens av handlinger
    @Test
    public void testActionSequence() {
        reactionTest = new ReactionTest();
        // Start ny test
        assertEquals(ReactionTest.TestStatus.WAITING, reactionTest.getStatus());

        // Vis stimulus
        reactionTest.showStimulus();
        assertEquals(ReactionTest.TestStatus.SHOWING_STIMULUS, reactionTest.getStatus());

        // Vent litt
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Registrer reaksjon
        reactionTest.recordReaction();
        assertEquals(ReactionTest.TestStatus.COMPLETED, reactionTest.getStatus());
        assertTrue(reactionTest.getReactionTime() >= 100, 
            "Reaksjonstiden skal være minst 100ms");
    }
}
