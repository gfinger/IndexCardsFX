package de.makkiato.fx.ctrls.carddeck;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import de.makkiato.fx.ctrls.indexcard.IndexCardData;

public class CardDeckControl extends Control {
	private final String STYLE_SHEET = "CardDeck.css";
	private ListProperty<IndexCardData> cardDataList = new SimpleListProperty<>(
			FXCollections.observableArrayList(new ArrayList<IndexCardData>()));
	private IntegerProperty currentCardIndex = new SimpleIntegerProperty();
	private IntegerProperty firstCardIndexToDisplay = new SimpleIntegerProperty();
	private IntegerProperty lastCardIndexToDisplay = new SimpleIntegerProperty();
	private IntegerProperty numberOfCardsToDisplay = new SimpleIntegerProperty();

	public CardDeckControl() {
		getStyleClass().add("card-deck");
		firstCardIndexToDisplay.bind(Bindings.max(0,
				currentCardIndex.subtract(1)));
		lastCardIndexToDisplay.bind(Bindings.min(cardDataList.sizeProperty()
				.subtract(1), currentCardIndex.add(1)));
		numberOfCardsToDisplay.bind(lastCardIndexToDisplay.subtract(
				firstCardIndexToDisplay).add(1));
		currentCardIndex.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				CardDeckSkin skin = (CardDeckSkin) getSkin();
				if (skin != null) {
					System.out.println(cardDataList.size());
					skin.requestLayout();
				}
			}
		});
	}

	@Override
	protected String getUserAgentStylesheet() {
		return getClass().getResource(STYLE_SHEET).toExternalForm();
	}

	public ObservableList<IndexCardData> getCardDataList() {
		return cardDataList.get();
	}

	public void setCardDataList(ObservableList<IndexCardData> cardDataList) {
		this.cardDataList.set(cardDataList);
	}
	
	public ListProperty<IndexCardData> cardDataListProperty() {
		return cardDataList;
	}

	public IntegerProperty getCurrentCardIndexProperty() {
		return currentCardIndex;
	}

	public int getCurrentCardIndex() {
		return currentCardIndex.get();
	}

	public void setCurrentCardIndex(int idx) {
		currentCardIndex.set(idx);
	}

	protected int getFirstCardIndexToDisplay() {
		return firstCardIndexToDisplay.get();
	}

	protected int getLastCardIndexToDisplay() {
		return lastCardIndexToDisplay.get();
	}

	protected int getNumberOfCardsToDisplay() {
		return numberOfCardsToDisplay.get();
	}
}