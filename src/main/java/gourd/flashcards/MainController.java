package gourd.flashcards;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static gourd.flashcards.Main.URL;

public class MainController {

    private List<Set> sets; // TODO load individual sets into memory as needed
    private Set currentSet;
    private Card currentCard;

    @FXML
    private Label cardText;

    @FXML
    private VBox setsPane;

    public void initialize(List<Set> sets) {
        // add db initialization here instead
        this.sets = sets;
        currentSet = sets.get(0); // TODO remember last opened if wanted
        currentSet.setCardList(SQL_Manager.getCardsFromSetID(URL, currentSet.getId()));

        for (Set set : sets) {
            Button setButton = new Button(set.getName());
            setButton.setOnMouseClicked(e -> {
                currentSet = set;
                updateView();
            });
            setsPane.getChildren().add(setButton);
        }
        updateView();
    }

    private void updateView() {
        if (currentSet.isEmpty()) {
            currentCard = new Card(-1, -1, "No cards in this set, click there to add some!", "No cards in this set, click there to add some!");
        } else {
            currentCard = currentSet.getCurrentCard();
        }
        cardText.setText(currentCard.show());
    }

    @FXML
    protected void onCardClick() {
        cardText.setText(currentCard.flip());
    }

    @FXML
    protected void onEditClick() {
        cardText.setText("Edit");
    }

    @FXML
    protected void onFavoriteClick() {
        cardText.setText("Favorite");
    }
}