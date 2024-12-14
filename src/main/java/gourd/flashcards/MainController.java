package gourd.flashcards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController {

    private ArrayList<Set> sets; // TODO load individual sets into memory as needed
    private Set currentSet;
    private Card currentCard;

    public void initialize(ArrayList<Set> sets) {
        // add db initialization here instead
        this.sets = sets;
        currentSet = sets.get(0); // TODO remember last opened if wanted
        System.out.println(currentSet.getCardList());
        currentCard = currentSet.getCurrentCard();
        cardText.setText(currentCard.show());
    }

    @FXML
    private Label cardText;

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