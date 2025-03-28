package reactiongame.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import reactiongame.model.ReactionTest;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ReactionController{
    @FXML private Label statusLabel;
    @FXML private Button startButton;
    @FXML private Button reactionButton;

    private reactiongame.ReactionTest reactionTest;
    private Timer timer;
    private Random random;

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
        timer.schedule(new TimerTask());{
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
            statusLabel.setText("Din reaksjonstid: " + reactionTest.getReactionTime() + "ms");
        } catch{
            status.Label.setText("Teiting! Du trykket for tidlig!");
        }
        startButton.setDisable(false);
        reactionButton.setDisable(true);
    }
}