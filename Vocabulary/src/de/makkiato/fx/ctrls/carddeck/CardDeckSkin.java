package de.makkiato.fx.ctrls.carddeck;

import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;

import com.sun.javafx.scene.control.skin.SkinBase;

import de.makkiato.fx.ctrls.indexcard.IndexCardControl;
import de.makkiato.fx.ctrls.indexcard.IndexCardData;

public class CardDeckSkin extends SkinBase<CardDeckControl, CardDeckBehavior> {
	private CardDeckControl control;
	private HBox cardDisplay;

	public CardDeckSkin(CardDeckControl control) {
		super(control, new CardDeckBehavior(control));
		this.control = control;
	}

	@Override
	public CardDeckControl getSkinnable() {
		return control;
	}

	@Override
	protected void layoutChildren() {
		drawControl();
	}

	private void drawControl() {
		int currentIndex = control.getCurrentCardIndex();
		int numberOfCards = control.getNumberOfCardsToDisplay();
		int firstIndex = control.getFirstCardIndexToDisplay();
		int lastIndex = control.getLastCardIndexToDisplay();
		
		cardDisplay = new HBox();
		cardDisplay.setPrefSize(getWidth() * numberOfCards, getHeight());
		
		ObservableList<IndexCardData> cardDataList = control.getCardDataList();
		for(int i=firstIndex; i<=lastIndex; i++) {
			addCard(cardDisplay, cardDataList.get(i));
		}

		Rectangle clipRect = new Rectangle();
		double x = (currentIndex-firstIndex)*getWidth();
		clipRect.setWidth(getWidth());
		clipRect.setHeight(getHeight());
		clipRect.setTranslateX(x);
		cardDisplay.setClip(clipRect);
		getChildren().setAll(cardDisplay);
		setTranslateX(-x);
	}

	private void addCard(HBox hbox, IndexCardData model) {
		IndexCardControl card = new IndexCardControl();
		card.headerOnFrontProperty().bindBidirectional(model.frontHeaderProperty());
		card.headerOnBackProperty().bindBidirectional(model.backHeaderProperty());
		card.textOnFrontProperty().bindBidirectional(model.frontTextProperty());
		card.textOnBackProperty().bindBidirectional(model.backTextProperty());
		
		card.setPrefSize(getWidth(), getHeight());
		card.setMinSize(getWidth(), getHeight());
		HBox.setHgrow(card, Priority.ALWAYS);
		hbox.getChildren().add(card);
	}
	
	protected HBox getCardDisplay() {
		return cardDisplay;
	}

}
