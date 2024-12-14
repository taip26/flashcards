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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = mainLoader.load();
        String url = "jdbc:sqlite:db/my.db";

        SQL_Manager.initializeDatabase(url);
        SQL_Manager.initializeSetsTable(url);
        SQL_Manager.initializeCardsTable(url);

//        Set set = new Set(0, "Set");
//        set.addCard(1, "This is the front of the card", "You flipped it :o");

//        SQL_Manager.insertSet(url, "Hi");
//        SQL_Manager.insertSet(url, "Just Inserted");
//        SQL_Manager.updateCard(url, 2, "Front of card", "Back of card");
//        SQL_Manager.updateSetName(url, 1, "Bruh");
//        SQL_Manager.deleteSet(url, 2);
        SQL_Manager.deleteCard(url, 3);

//        SQL_Manager.getSetsFromDB(url);
        System.out.println(SQL_Manager.getCardsFromSetID(url,1));
        MainController controller = mainLoader.getController();
        ArrayList<Set> sets = SQL_Manager.getSetsFromDB(url);
        System.out.println(sets);
        controller.initialize(sets);

        window = stage;
        window.setTitle("Flashcards!");

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("main.css")));
        VBox leftPane = (VBox) scene.lookup("#sets-pane");
        for (Set set : sets) {
            System.out.println("CARS");
            System.out.println(SQL_Manager.getCardsFromSetID(url, set.getId()));
            set.setCardList(SQL_Manager.getCardsFromSetID(url, set.getId()));
            leftPane.getChildren().add(new Button(set.getName()));
        }
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}