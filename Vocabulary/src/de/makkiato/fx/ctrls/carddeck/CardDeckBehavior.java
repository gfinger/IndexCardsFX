package de.makkiato.fx.ctrls.carddeck;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import com.sun.javafx.scene.control.behavior.BehaviorBase;

import de.makkiato.fx.ctrls.indexcard.IndexCardData;

public class CardDeckBehavior extends BehaviorBase<CardDeckControl> {
	private CardDeckControl control;
	private double mousePressedXPosition;
	private double clipXPosition;

	public CardDeckBehavior(final CardDeckControl control) {
		super(control);
		this.control = control;

		final ContextMenu contextMenu = new ContextMenu();
		EventHandler<ActionEvent> newCardAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("new card");
				int currentIndex = control.getCurrentCardIndex();
				control.getCardDataList().add(currentIndex,
						new IndexCardData("header", "header", "text", "text"));
				control.setCurrentCardIndex(currentIndex);
			}
		};
		MenuItem newCardItem = new MenuItem("New");
		newCardItem.setOnAction(newCardAction);

		EventHandler<ActionEvent> deleteCardAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("delete card");
			}
		};
		MenuItem deleteCardItem = new MenuItem("Delete");
		deleteCardItem.setOnAction(deleteCardAction);

		contextMenu.getItems().setAll(newCardItem, deleteCardItem);

		control.addEventFilter(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton().equals(MouseButton.PRIMARY)) {
							CardDeckSkin skin = (CardDeckSkin) control
									.getSkin();
							mousePressedXPosition = event.getX();
							clipXPosition = -skin.getTranslateX();
							contextMenu.hide();
						} else if (event.getButton().equals(
								MouseButton.SECONDARY)) {
							contextMenu.show(control, event.getScreenX(),
									event.getScreenY());
							event.consume();
						}
					}
				});
		control.addEventFilter(MouseEvent.MOUSE_DRAGGED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (!event.isStillSincePress()
								&& event.getButton()
										.equals(MouseButton.PRIMARY)) {
							handleMouseDraggedOnCardDeck(event);
							event.consume();
						}
					}
				});
		control.addEventFilter(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton().equals(MouseButton.PRIMARY)) {
							if (!event.isStillSincePress()) {
								handleMouseReleasedOnCardDeck(event);
								event.consume();
							}
						} else if (event.getButton().equals(
								MouseButton.SECONDARY)) {
							event.consume();
						}
					}
				});
		control.addEventFilter(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton().equals(MouseButton.SECONDARY)) {
							event.consume();
						}
					}
				});
	}

	protected void handleMouseReleasedOnCardDeck(MouseEvent event) {
		CardDeckSkin skin = (CardDeckSkin) control.getSkin();
		if (-skin.getTranslateX() == clipXPosition)
			return;

		HBox cardDisplay = skin.getCardDisplay();

		double x = 0;
		double translate = -skin.getTranslateX();

		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(skin.getWidth());
		clipRect.setHeight(skin.getHeight());
		clipRect.setTranslateX(translate);
		cardDisplay.setClip(clipRect);

		if (translate > skin.getWidth() * 3.0 / 2.0) {
			x = 2.0 * skin.getWidth();
		} else if (translate < skin.getWidth() / 2.0) {
			x = 0;
		} else {
			x = skin.getWidth();
		}
		final int finalNextIndex = control.getCurrentCardIndex()
				+ ((clipXPosition < x) ? 1 : (x < clipXPosition) ? -1 : 0);

		double delta = Math.abs(translate - x);
		double t = 300 * 2 * delta / skin.getWidth();

		Timeline animation = new Timeline();
		animation.getKeyFrames().setAll(
				new KeyFrame(Duration.millis(t), new KeyValue(clipRect
						.translateXProperty(), x, Interpolator.EASE_BOTH),
						new KeyValue(skin.translateXProperty(), -x,
								Interpolator.EASE_BOTH)));
		animation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int currentIndex = control.getCurrentCardIndex();
				IndexCardData cardData = control.getCardDataList().get(
						currentIndex);
				if (cardData.isChanged()) {
					cardData.setChanged(false);
					control.fireEvent(new CardDataChangeEvent(currentIndex,
							cardData));
				}
				control.setCurrentCardIndex(finalNextIndex);
			}
		});
		animation.play();
	}

	protected void handleMouseDraggedOnCardDeck(MouseEvent event) {
		double x = clipXPosition + mousePressedXPosition - event.getX();
		moveClipOnSkin(x);
	}

	private void moveClipOnSkin(double x) {
		CardDeckSkin skin = (CardDeckSkin) control.getSkin();
		int numberOfCards = control.getNumberOfCardsToDisplay();
		if (x < 0 || x > (numberOfCards - 1) * skin.getWidth())
			return;
		HBox cardDisplay = skin.getCardDisplay();
		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(skin.getWidth());
		clipRect.setHeight(skin.getHeight());
		cardDisplay.setClip(clipRect);
		clipRect.setTranslateX(x);
		skin.setTranslateX(-x);
	}
}
