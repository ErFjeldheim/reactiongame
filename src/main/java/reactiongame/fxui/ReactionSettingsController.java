package reactiongame.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

public class ReactionSettingsController {
    @FXML private Slider testsPerSessionSlider;
    @FXML private Slider minDelaySlider;
    @FXML private Slider maxDelaySlider;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private Preferences prefs = Preferences.userNodeForPackage(ReactionSettingsController.class);
    
    public void initialize() {
        // Last innstillinger
        testsPerSessionSlider.setValue(prefs.getInt("testsPerSession", 5));
        minDelaySlider.setValue(prefs.getInt("minDelay", 1000));
        maxDelaySlider.setValue(prefs.getInt("maxDelay", 5000));
        
        // Sørg for at min alltid er mindre enn max
        minDelaySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= maxDelaySlider.getValue()) {
                maxDelaySlider.setValue(newVal.doubleValue() + 1000);
            }
        });
        
        maxDelaySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() <= minDelaySlider.getValue()) {
                minDelaySlider.setValue(newVal.doubleValue() - 1000);
            }
        });
    }
    
    @FXML
    private void saveSettings() {
        // Lagre innstillinger til preferences
        prefs.putInt("testsPerSession", (int) testsPerSessionSlider.getValue());
        prefs.putInt("minDelay", (int) minDelaySlider.getValue());
        prefs.putInt("maxDelay", (int) maxDelaySlider.getValue());
        
        closeWindow();
    }
    
    @FXML
    private void cancel() {
        closeWindow();
    }
    
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}