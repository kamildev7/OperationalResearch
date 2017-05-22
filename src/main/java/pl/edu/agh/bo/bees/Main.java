package pl.edu.agh.bo.bees;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.bo.bees.controller.MainWindowController;

public class Main extends Application{
    MainWindowController mainWindowController = null;
    public static void main(String[] args) {
        System.out.println("Factories and StockProviders");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindowController = new MainWindowController(primaryStage);
        mainWindowController.show();
    }
}
