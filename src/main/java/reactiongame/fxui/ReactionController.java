package reactiongame.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.java.reactiongame.model.ResultManager;
import main.java.reactiongame.model.TestResult;
import reactiongame.model.ReactionTest;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ReactionController{
    @FXML private Label statusLabel;
    @FXML private Button startButton;
    @FXML private Button reactionButton;
    @FXML private Label averageLabel;
    @FXML private Label bestTimeLabel;
    @FXML private Label testsRemainingLabel;

    private ReactionTest reactionTest;
    private Timer timer;
    private Random random;
    private ResultManager resultManager = new ResultManager();
    private int currentSession = 0;
    private static final int TESTS_PER_SESSION = 5;

    public void initialize(){
        reactionTest = new ReactionTest();
        random = new Random();
        reactionButton.setDisable(true);
    }

    @FXML
    private void startGame(){
        statusLabel.setText("Vent på signal...");
        startButton.setDisable(true);
        reactionButton.setDisable(true);

        int delay = 1000 + random.nextInt(4000); //1-5 sekunder tilfeldig forsinkelse

        timer = new Timer();
        timer.schedule(new TimerTask()){
            @Override
            public void run(){
                reactionTest.showStimulus();
                statusLabel.setText("Trykk nå!");
                reactionButton.setDisable(false);
            }
        }
    }

    @FXML
    private void recordReaction(){
        try{
            reactionTest.recordReaction();
            long time = reactionTest.getReactionTime();
            resultManager.addResult(new TestResult(time));
            currentSession++;

            statusLabel.setText("Din reaksjonstid: " + reactionTest.getReactionTime() + "ms");
            updateStatistics();

            if (currentSession >= TESTS_PER_SESSION){
                statusLabel.setText("Økt fullført! Din gjennomsnittlige reaksjonstid: " + resultManager.getAverageTime().orElse(0) + "ms");
                currentSession = 0;
            }
        } catch{
            statusLabel.setText("Teiting! Du trykket for tidlig!");
        }
        startButton.setDisable(false);
        reactionButton.setDisable(true);
    }

    private void updateStatistics() {
        resultManager.getBestTime().ifPresent(best -> bestTimeLabel.setText("Beste tid: " + best + "ms"));
        resultManager.getAverageTime().ifPresent(avg -> averageLabel.setText("Gjenomsnitt: " Math.round(avg) + "ms"));
        testsRemainingLabel.setText("Tester igjen i denne økten: " + (TESTS_PER_SESSION - currentSession));
    }
}