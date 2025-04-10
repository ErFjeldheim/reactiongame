package reactiongame.model;

import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ReactionEntry {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        ResultManager resultManager = new ResultManager();
        
        System.out.println("Velkommen til Reaksjonstesten!");
        System.out.println("Trykk ENTER når du er klar for å begynne en test.");
        
        int testsCompleted = 0;
        final int TESTS_PER_SESSION = 5;
        
        while (testsCompleted < TESTS_PER_SESSION) {
            scanner.nextLine(); // Vent på at brukeren trykker ENTER
            
            System.out.println("Vent på signal...");
            
            try {
                // Ventetid mellom 1-5 sekunder
                TimeUnit.MILLISECONDS.sleep(1000 + random.nextInt(4000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            
            System.out.println("TRYKK ENTER NÅ!");
            
            ReactionTest test = new ReactionTest();
            test.showStimulus();
            
            // Vent på at brukeren trykker ENTER
            scanner.nextLine();
            
            try {
                test.recordReaction();
                long reactionTime = test.getReactionTime();
                resultManager.addResult(new TestResult(reactionTime));
                
                System.out.println("Din reaksjonstid: " + reactionTime + "ms");
                testsCompleted++;
                
                // Vis statistikk
                System.out.println("Test " + testsCompleted + " av " + TESTS_PER_SESSION + " fullført.");
                resultManager.getBestTime().ifPresent(best -> 
                    System.out.println("Beste tid så langt: " + best + "ms"));
                resultManager.getAverageTime().ifPresent(avg -> 
                    System.out.println("Gjennomsnittlig tid: " + Math.round(avg) + "ms"));
                
                if (testsCompleted < TESTS_PER_SESSION) {
                    System.out.println("\nTrykk ENTER for å fortsette til neste test.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Feil: " + e.getMessage());
                System.out.println("La oss prøve igjen. Trykk ENTER for å starte på nytt.");
            }
        }
        
        // Fullfør økten og vis statistikk
        System.out.println("\nØkten er fullført!");
        System.out.println("Oppsummering av resultater:");
        resultManager.getBestTime().ifPresent(best -> 
            System.out.println("Beste tid: " + best + "ms"));
        resultManager.getAverageTime().ifPresent(avg -> 
            System.out.println("Gjennomsnittlig tid: " + Math.round(avg) + "ms"));
        
        // Lagre resultater
        FileStorage.saveResults(resultManager.getAllResults());
        System.out.println("Resultatene har blitt lagret.");
        
        scanner.close();
    }
}
