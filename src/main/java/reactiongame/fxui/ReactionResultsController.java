package reactiongame.fxui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import reactiongame.model.FileStorage;
import reactiongame.model.TestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReactionResultsController {
    @FXML private TableView<TestResult> resultsTable;
    @FXML private TableColumn<TestResult, Long> reactionTimeColumn;
    @FXML private TableColumn<TestResult, String> timestampColumn;
    @FXML private LineChart<Number, Number> progressChart;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void initialize() {
        // Konfigurerer tabell
        reactionTimeColumn.setCellValueFactory(new PropertyValueFactory<>("reactionTime"));
        timestampColumn.setCellValueFactory(cellData -> {
            LocalDateTime timestamp = cellData.getValue().getTimestamp();
            return javafx.beans.binding.Bindings.createStringBinding(() -> timestamp.format(formatter));
        });

        // Laster inn data
        List<TestResult> results = FileStorage.loadResults();
        ObservableList<TestResult> observableResults = FXCollections.observableArrayList(results);
        resultsTable.setItems(observableResults);

        // Oppretter graf
        if (!results.isEmpty()) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Reaksjonstider");
            
            for (int i = 0; i < results.size(); i++) {
                series.getData().add(new XYChart.Data<>(i + 1, results.get(i).getReactionTime()));
            }
            
            progressChart.getData().add(series);
        }
    }

    @FXML
    private void closeWindow() {
        resultsTable.getScene().getWindow().hide();
    }
}
