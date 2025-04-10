package reactiongame.fxui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import reactiongame.model.FileStorage;
import reactiongame.model.ResultManager;
import reactiongame.model.TestResult;
import reactiongame.model.ReactionTest;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.prefs.Preferences;

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
    private Preferences prefs = Preferences.userNodeForPackage(ReactionController.class);

    // Henter innstillinger med standard verdier
    private int getTestsPerSession() {
        return prefs.getInt("testsPerSession", 5);
    }
    
    private int getMinDelay() {
        return prefs.getInt("minDelay", 1000);
    }
    
    private int getMaxDelay() {
        return prefs.getInt("maxDelay", 5000);
    }

    public void initialize(){
        reactionTest = new ReactionTest();
        random = new Random();
        reactionButton.setDisable(true);
        
        // Laster historiske resultater
        resultManager = new ResultManager();
        for (TestResult result : FileStorage.loadResults()) {
            resultManager.addResult(result);
        }
        
        updateStatistics();
    }

    @FXML
    private void startGame() {
        statusLabel.setText("Vent på signal...");
        startButton.setDisable(true);
        reactionButton.setDisable(true);

        int maxDelayOffset = getMaxDelay() - getMinDelay();
        int delay = getMinDelay();
        if (maxDelayOffset > 0) {
            delay += random.nextInt(maxDelayOffset);
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    reactionTest.showStimulus();
                    statusLabel.setText("Trykk nå!");
                    reactionButton.setDisable(false);
                });
            }
        }, delay);
    }

    @FXML
    private void recordReaction() {
        try {
            reactionTest.recordReaction();
            long time = reactionTest.getReactionTime();
            resultManager.addResult(new TestResult(time));
            currentSession++;

            statusLabel.setText("Din reaksjonstid: " + reactionTest.getReactionTime() + "ms");
            updateStatistics();
            
            // Lagrer resultater etter hver test
            FileStorage.saveResults(resultManager.getAllResults());

            if (currentSession >= getTestsPerSession()) {
                statusLabel.setText("Økt fullført! Din gjennomsnittlige reaksjonstid: " + 
                    resultManager.getAverageTime().orElse(0) + "ms");
                currentSession = 0;
            }
        } catch (IllegalStateException e) {
            statusLabel.setText("Teiting! Du trykket for tidlig!");
        }
        startButton.setDisable(false);
        reactionButton.setDisable(true);
    }

    @FXML
    private void showHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reactiongame/fxui/ReactionResults.fxml"));
            Scene scene = new Scene(loader.load());
            
            Stage stage = new Stage();
            stage.setTitle("Reaksjonstidshistorikk");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reactiongame/fxui/ReactionSettings.fxml"));
            Scene scene = new Scene(loader.load());
            
            Stage stage = new Stage();
            stage.setTitle("Innstillinger");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            // Oppdater visningen
            updateStatistics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStatistics() {
        resultManager.getBestTime().ifPresent(best -> bestTimeLabel.setText("Beste tid: " + best + "ms"));
        resultManager.getAverageTime().ifPresent(avg -> averageLabel.setText("Gjenomsnitt: " + Math.round(avg) + "ms"));
        testsRemainingLabel.setText("Tester igjen i denne økten: " + (getTestsPerSession() - currentSession));
    }
}