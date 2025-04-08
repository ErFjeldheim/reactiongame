package reactiongame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalLong;

public class ResultManager {
    private List<TestResult> results = new ArrayList<>();

    public void addResult(TestResult result){
        results.add(result);
    }

    public List<TestResult> getAllResults(){
        return new ArrayList<>(results);
    }

    public OptionalLong getBestTime(){
        return results.stream()
            .mapToLong(TestResult::getReactionTime)
            .min();
    }

    public OptionalDouble getAverageTime(){
        return results.stream()
            .mapToLong(TestResult::getReactionTime)
            .average();
    }

    public List<TestResult> getLatestResults(int count){
        int size = results.size();
        if (size<= count){
            return new ArrayList<>(results);
        }
        return new ArrayList<>(results.subList(size - count, size));
    }
}