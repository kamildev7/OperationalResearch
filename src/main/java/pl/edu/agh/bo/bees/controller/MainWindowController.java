package pl.edu.agh.bo.bees.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
    private Parser parser = null;
    private Algorithm algorithm;

    public MainWindowController(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void show(){
        try {
        	URL location = new URL("file:///C:/Users/Kamil/SkyDrive/Studia Semestr 6/6 BO/projektPoprawiony2/src/main/java/pl/edu/agh/bo/bees/view/MainWindow.fxml");
            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(this);
            BorderPane rootLayout = (BorderPane) loader.load();

            primaryStage.setTitle("Factories and Stock providers");
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
    private void setSliders(){

        sSitesSld.setMajorTickUnit(1);
        sSitesSld.setMinorTickCount(0);
        sSitesSld.setSnapToTicks(true);

        bSitesSld.setMajorTickUnit(1);
        bSitesSld.setMinorTickCount(0);
        bSitesSld.setSnapToTicks(true);

        scoutsSld.setMajorTickUnit(1);
        scoutsSld.setMinorTickCount(0);
        scoutsSld.setSnapToTicks(true);

        beesForSSitesSld.setMajorTickUnit(1);
        beesForSSitesSld.setMinorTickCount(0);
        beesForSSitesSld.setSnapToTicks(true);

        beesForBSitesSld.setMajorTickUnit(1);
        beesForBSitesSld.setMinorTickCount(0);
        beesForBSitesSld.setSnapToTicks(true);

        iterationsSld.setMajorTickUnit(1);
        iterationsSld.setMinorTickCount(0);
        iterationsSld.setSnapToTicks(true);

        sSitesSld.setMin(0);
        bSitesSld.setMin(0);
        scoutsSld.setMin(0);
        beesForSSitesSld.setMin(0);
        beesForBSitesSld.setMin(0);
        iterationsSld.setMin(0);

        sSitesSld.setMax(100);
        bSitesSld.setMax(100);
        scoutsSld.setMax(100);
        beesForSSitesSld.setMax(100);
        beesForBSitesSld.setMax(100);
        iterationsSld.setMax(500);

        sSitesSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                sSitesTxt.setText(String.valueOf(sSitesSld.getValue()));
            }
        });
        bSitesSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                bSitesTxt.setText(String.valueOf(bSitesSld.getValue()));
            }
        });
        scoutsSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                scoutsTxt.setText(String.valueOf(scoutsSld.getValue()));
            }
        });
        beesForSSitesSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                beesForSSitesTxt.setText(String.valueOf(beesForSSitesSld.getValue()));
            }
        });
        beesForBSitesSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                beesForBSitesTxt.setText(String.valueOf(beesForBSitesSld.getValue()));
            }
        });
        iterationsSld.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                iterationsTxt.setText(String.valueOf(iterationsSld.getValue()));
            }
        });

    }
    private void setTextFields() {
        sSitesTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sSitesSld.setValue(Double.valueOf(sSitesTxt.getText()));
            }
        });
        bSitesTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                bSitesSld.setValue(Double.valueOf(bSitesTxt.getText()));
            }
        });
        scoutsTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                scoutsSld.setValue(Double.valueOf(scoutsTxt.getText()));
            }
        });
        beesForSSitesTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                beesForSSitesSld.setValue(Double.valueOf(beesForSSitesTxt.getText()));
            }
        });
        beesForBSitesTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                beesForBSitesSld.setValue(Double.valueOf(beesForBSitesTxt.getText()));
            }
        });
        iterationsTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                iterationsSld.setValue(Double.valueOf(iterationsTxt.getText()));
            }
        });
    }

    @FXML
    private void initialize(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        startButton.setVisible(false);
        warn.setStyle("-fx-text-fill: red");
        lineChart.setTitle("");
        lineChart.setAnimated(false);
        txtArea.setEditable(false);
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
    private LineChart<Number, Number>  lineChart;
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField sSitesTxt;
    @FXML
    private Slider sSitesSld;
    @FXML
    private TextField bSitesTxt;
    @FXML
    private Slider bSitesSld;
    @FXML
    private TextField scoutsTxt;
    @FXML
    private Slider scoutsSld;
    @FXML
    private TextField beesForSSitesTxt;
    @FXML
    private Slider beesForSSitesSld;
    @FXML
    private TextField beesForBSitesTxt;
    @FXML
    private Slider beesForBSitesSld;
    @FXML
    private TextField iterationsTxt;
    @FXML
    private Slider iterationsSld;

    @FXML
    private void handleFileButtonAction(ActionEvent event){
        File file = fileChooser.showOpenDialog(primaryStage);
        if(file==null){
            return;
        }
        startButton.setVisible(true);
        try {
            parser = new Parser(file);
            input = new Input();
            parser.parse(input);
        }
        catch(NumberFormatException e){
            warn.setText("Wrong file format");
        }
        if(!input.isInputOK()) {
            warn.setText("File format error");
            startButton.setDisable(true);
        }
        else{
            startButton.setDisable(false);
        }

        try{
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

            sSitesSld.setValue(Double.valueOf(String.valueOf(algorithm.getSelectedSites())));
            bSitesSld.setValue(Double.valueOf(String.valueOf(algorithm.getBestSites())));
            scoutsSld.setValue(Double.valueOf(String.valueOf(algorithm.getScouts())));
            beesForSSitesSld.setValue(Double.valueOf(String.valueOf(algorithm.getBeesForSelectedSites())));
            beesForBSitesSld.setValue(Double.valueOf(String.valueOf(algorithm.getBeesForBestSites())));
            iterationsSld.setValue(Double.valueOf(String.valueOf(algorithm.getIterations())));

            setSliders();
            setTextFields();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void handleStartAction(ActionEvent event){
        algorithm.setSelectedSites(Double.valueOf(sSitesTxt.getText()).intValue());
        algorithm.setBestSites(Double.valueOf(bSitesTxt.getText()).intValue());
        algorithm.setScouts(Double.valueOf(scoutsTxt.getText()).intValue());
        algorithm.setBeesForSelectedSites(Double.valueOf(beesForSSitesTxt.getText()).intValue());
        algorithm.setBeesForBestSites(Double.valueOf(beesForBSitesTxt.getText()).intValue());
        algorithm.setIterations(Double.valueOf(iterationsTxt.getText()).intValue());

        lineChart.getData().clear();

        XYChart.Series<Number, Number> seriesActual = new XYChart.Series<Number, Number>();
        seriesActual.setName("Solution");
        XYChart.Series<Number, Number> seriesBest = new XYChart.Series<Number, Number>();
        seriesBest.setName("Best solution");
        lineChart.getData().add(seriesActual);
        lineChart.getData().add(seriesBest);

        long startTime = System.nanoTime();

        new Thread(() -> {
            algorithm.calculate(new SolutionCallback() {
                private int solutionId = 0;

                @Override
                public void onSolution(Solution actualSolution, Solution bestSolution) {
                    Platform.runLater(() -> {
                        ++solutionId;
                        double actualScore = actualSolution.evaluate();
                        double bestScore = bestSolution.evaluate();
                        if (actualScore < Double.MAX_VALUE)
                            seriesActual.getData().add(new XYChart.Data<Number, Number>(solutionId, actualScore));
                        if (bestScore < Double.MAX_VALUE)
                            seriesBest.getData().add(new XYChart.Data<Number, Number>(solutionId, bestScore));
                    });
                }

                @Override
                public void onAlgorithmEnd(Solution bestSolution) {
                    long estimatedTime = System.nanoTime() - startTime;
                    estimatedTime = TimeUnit.NANOSECONDS.toMillis(estimatedTime);

                    // populating the series with data
                    txtArea.setText(bestSolution.toString() + "\nEstimated time: " + estimatedTime + "ms");
                }
            });
        }).start();

    }

}
