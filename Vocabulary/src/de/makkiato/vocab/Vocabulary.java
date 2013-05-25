package de.makkiato.vocab;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.makkiato.fx.ctrls.carddeck.CardDataChangeEvent;
import de.makkiato.fx.ctrls.carddeck.CardDeckControl;
import de.makkiato.fx.ctrls.indexcard.IndexCardData;

public class Vocabulary extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		CardDeckControl cardDeck = new CardDeckControl();
		cardDeck.getCardDataList().setAll(
				new IndexCardData("1", "1",
						"Qu'est-ce que vous avez fait hier?",
						"Was haben Sie gestern gemacht?"),
				new IndexCardData("2", "2", "J'ai fait les courses hier",
						"Ich habe gestern die Einnkäufe gemacht"),
				new IndexCardData("3", "3", "Qu'est-ce que vouz avez acheté?",
						"Was haben Sie eingekauft?"),
				new IndexCardData("4", "4", "L'addition, s'ill vous plait!",
						"Die Rechnung bitte!"));
		cardDeck.setCurrentCardIndex(2);
		
		cardDeck.addEventHandler(CardDataChangeEvent.DATA_CHANGE, new EventHandler<CardDataChangeEvent>() {
			@Override
			public void handle(CardDataChangeEvent event) {
				System.out.println("changed: " + event.index);
			}
		});

		Scene scene = new Scene(cardDeck, 500, 300);
		PerspectiveCamera camera = new PerspectiveCamera();
		scene.setCamera(camera);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}