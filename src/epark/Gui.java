package epark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.*;

public class Gui extends Application {

    private Controller theController;
    private BorderPane root;
    private Popup descriptionPane;
    private Stage primaryStage;

    @Override
    public void start(Stage givenStage) throws Exception{
        theController = new Controller(this);
        primaryStage = givenStage;
        root = setUpRoot();
        primaryStage.setTitle("Dungeon Generator 4.0");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.setTop(new Label("yes"));
        return temp;
    }

    /**
     * Main method of gui class.
     * @param args arguments provided when program is initiated
     */
    public static void main(String[] args) {
        launch(args);
    }

}
