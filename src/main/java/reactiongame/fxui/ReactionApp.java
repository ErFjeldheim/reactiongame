package reactiongame.fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ReactionApp extends Application{
    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reactiongame/fxui/Reaction.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            primaryStage.setTitle("Reaction Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public static void main(String[] args){
    launch(args);
}