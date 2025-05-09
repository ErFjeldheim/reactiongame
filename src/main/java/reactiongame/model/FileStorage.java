package reactiongame.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILENAME = "reaction_results.txt";
    
    public static void saveResults(List<TestResult> results) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (TestResult result : results) {
                writer.println(result.getReactionTime() + "," + result.getTimestamp());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<TestResult> loadResults() {
        List<TestResult> results = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(FILENAME))) {
                List<String> lines = Files.readAllLines(Paths.get(FILENAME));
                for (String line : lines) {
                    String[] parts = line.split(",");
                    TestResult result = new TestResult(Long.parseLong(parts[0]));
                    results.add(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
