package gourd.flashcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    public static final String URL = "jdbc:sqlite:db/my.db";
    Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = mainLoader.load();

        SQL_Manager.initializeDatabase(URL);
        SQL_Manager.initializeSetsTable(URL);
        SQL_Manager.initializeCardsTable(URL);

//        Set set = new Set(0, "Set");
//        set.addCard(1, "This is the front of the card", "You flipped it :o");
//        SQL_Manager.insertSet(URL, "Just Inserted");
//        SQL_Manager.updateCard(URL, 2, "Front of card", "Back of card");
//        SQL_Manager.updateSetName(URL, 1, "Bruh");
//        SQL_Manager.deleteCard(URL, 3);

//        SQL_Manager.getSetsFromDB(URL);
        MainController controller = mainLoader.getController();
        List<Set> sets = SQL_Manager.getSetsFromDB(URL);
        controller.initialize(sets);

        window = stage;
        window.setTitle("Flashcards!");

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("main.css")));
//        VBox leftPane = (VBox) scene.lookup("#sets-pane");
//        for (Set set : sets) {
//            leftPane.getChildren().add(new Button(set.getName()));
//        }
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}