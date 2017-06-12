package pl.edu.agh.bo.bees.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.edu.agh.bo.bees.input.Input;
import pl.edu.agh.bo.bees.parser.Parser;
import pl.edu.agh.bo.bees.solution.Algorithm;
import pl.edu.agh.bo.bees.solution.Solution;
import pl.edu.agh.bo.bees.solution.SolutionCallback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MainWindowController {
    private Stage primaryStage = null;
    private FileChooser fileChooser = null;
    private Input input = null;
    private Algorithm algorithm;

    public MainWindowController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        try {
            URL location = new URL("file:///E:/VI/bo/OperationalResearch/src/main/java/pl/edu/agh/bo/bees/view/MainWindow.fxml");
            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(this);
            BorderPane rootLayout = loader.load();

            primaryStage.setTitle("Factories and Stock providers");
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        startButton.setVisible(false);
        warn.setStyle("-fx-text-fill: red");
        lineChart.setAnimated(false);
        txtArea.setEditable(false);
        yAxis.setAutoRanging(false);
        yAxisBest.setAutoRanging(false);
    }

    @FXML
    private Button fileButton;
    @FXML
    private Label factoriesLabel;
    @FXML
    private Label factories;
    @FXML
    private Label stockProviders;
    @FXML
    private Label stockProvidersLabel;
    @FXML
    private Button startButton;
    @FXML
    private Label warn;
    @FXML
    private LineChart<Number, Number> bestChart;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField sSitesTxt;
    @FXML
    private TextField bSitesTxt;
    @FXML
    private TextField scoutsTxt;
    @FXML
    private TextField beesForSSitesTxt;
    @FXML
    private TextField beesForBSitesTxt;
    @FXML
    private TextField iterationsTxt;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis yAxisBest;

    @FXML
    private void handleFileButtonAction(ActionEvent event) {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) {
            return;
        }
        startButton.setVisible(true);
        try {
            Parser parser = new Parser(file);
            input = new Input();
            parser.parse(input);
        } catch (NumberFormatException e) {
            warn.setText("Wrong file format");
        }
        if (!input.isInputOK()) {
            warn.setText("File format error");
            startButton.setDisable(true);
        } else {
            startButton.setDisable(false);
        }
        try {
            algorithm = new Algorithm(input);
            factoriesLabel.setText("factories: ");
            factories.setText(String.valueOf(input.getFactoriesAmount()));
            stockProvidersLabel.setText("stockProviders: ");
            stockProviders.setText(String.valueOf(input.getStockProvidersAmount()));

            sSitesTxt.setText(String.valueOf(algorithm.getSelectedSites()));
            bSitesTxt.setText(String.valueOf(algorithm.getBestSites()));
            scoutsTxt.setText(String.valueOf(algorithm.getScouts()));
            beesForSSitesTxt.setText(String.valueOf(algorithm.getBeesForSelectedSites()));
            beesForBSitesTxt.setText(String.valueOf(algorithm.getBeesForBestSites()));
            iterationsTxt.setText(String.valueOf(algorithm.getIterations()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStartAction(ActionEvent event) {
        algorithm.setSelectedSites(Double.valueOf(sSitesTxt.getText()).intValue());
        algorithm.setBestSites(Double.valueOf(bSitesTxt.getText()).intValue());
        algorithm.setScouts(Double.valueOf(scoutsTxt.getText()).intValue());
        algorithm.setBeesForSelectedSites(Double.valueOf(beesForSSitesTxt.getText()).intValue());
        algorithm.setBeesForBestSites(Double.valueOf(beesForBSitesTxt.getText()).intValue());
        algorithm.setIterations(Double.valueOf(iterationsTxt.getText()).intValue());

        lineChart.getData().clear();

        XYChart.Series<Number, Number> seriesActual = new XYChart.Series<>();
        seriesActual.setName("Solution");
        XYChart.Series<Number, Number> seriesBest = new XYChart.Series<>();
        seriesBest.setName("Best solution");
        lineChart.getData().add(seriesActual);
        bestChart.getData().add(seriesBest);

        long startTime = System.nanoTime();

        new Thread(() -> {
            algorithm.calculate(new SolutionCallback() {
                private int solutionId = 0;

                private double max = 0;
                private double min = Double.MAX_VALUE;

                @Override
                public void onSolution(Solution actualSolution, Solution bestSolution) {
                    Platform.runLater(() -> {
                        ++solutionId;
                        double actualScore = actualSolution.evaluate();
                        double bestScore = bestSolution.evaluate();
                        min = Math.min(min, Math.min(actualScore, bestScore));
                        max = Math.max(max, Math.max(actualScore, bestScore));
                        if (actualScore < Double.MAX_VALUE)
                            seriesActual.getData().add(new XYChart.Data<>(solutionId, actualScore));
                        if (bestScore < Double.MAX_VALUE)
                            seriesBest.getData().add(new XYChart.Data<>(solutionId, bestScore));
                    });
                }

                @Override
                public void onAlgorithmEnd(Solution bestSolution) {
                    long estimatedTime = System.nanoTime() - startTime;
                    estimatedTime = TimeUnit.NANOSECONDS.toMillis(estimatedTime);

                    Platform.runLater(() -> {
                        yAxis.setLowerBound(min * 0.9);
                        yAxis.setUpperBound(max * 1.1);
                        yAxis.setTickUnit((max - min) / 10);
                        yAxisBest.setLowerBound(min * 0.9);
                        yAxisBest.setUpperBound(max * 1.1);
                        yAxisBest.setTickUnit((max - min) / 10);
                    });
                    txtArea.setText(bestSolution.toString() + "\nEstimated time: " + estimatedTime + "ms");
                }
            });
        }).start();
    }
}
