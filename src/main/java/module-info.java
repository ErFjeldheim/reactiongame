module reactiongame {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;

    opens reactiongame.fxui to javafx.fxml;
    exports reactiongame.fxui;
    exports reactiongame.model;
}
