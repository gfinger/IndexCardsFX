package de.makkiato.fx.ctrls.indexcard;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

import com.sun.javafx.scene.control.behavior.BehaviorBase;

public class IndexCardBehaviour extends BehaviorBase<IndexCardControl> {
	private IndexCardControl control;
	private boolean clickedInTheLastFewMoments;
	private Timer clickedTimer;

	public IndexCardBehaviour(final IndexCardControl control) {
		super(control);
		this.control = control;

		control.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(final MouseEvent event) {
						if (clickedInTheLastFewMoments) {
							clickedInTheLastFewMoments = false;
							clickedTimer.cancel();
							event.consume();
							doubleClick();
						} else {
							clickedInTheLastFewMoments = true;
							clickedTimer = new Timer();
							clickedTimer.schedule(new TimerTask() {
								@Override
								public void run() {
									clickedInTheLastFewMoments = false;
									event.consume();
									singleClick();
								}
							}, 270);
						}
					}
				});
	}

	protected void doubleClick() {
		control.setEditable(true);
	}

	protected void singleClick() {
		handleMouseClickOnIndexCard();
	}

	private void handleMouseClickOnIndexCard() {
		final IndexCardSkin skin = (IndexCardSkin) control.getSkin();
		final Pane card = skin.getCard();
		final Pane frontPane = skin.getFrontPage();
		final Pane backPane = skin.getBackPage();

		Rotate ry = lazyInitRotate(card, 0.0);
		card.getTransforms().setAll(ry);

		Timeline forwardAnimation = new Timeline();
		forwardAnimation.getKeyFrames().setAll(
				new KeyFrame(Duration.seconds(1), new KeyValue(
						ry.angleProperty(), 180)),
				new KeyFrame(Duration.seconds(0.5),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								frontPane.setVisible(false);
								backPane.setVisible(true);
							}
						}));

		Timeline backwardAnimation = new Timeline();
		backwardAnimation.getKeyFrames().setAll(
				new KeyFrame(Duration.seconds(1), new KeyValue(
						ry.angleProperty(), 0.0)),
				new KeyFrame(Duration.seconds(0.5),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								frontPane.setVisible(true);
								backPane.setVisible(false);
							}
						}));
		(ry.getAngle() == 0 ? forwardAnimation : backwardAnimation).play();
	}

	private Rotate lazyInitRotate(Pane page, double angle) {
		double x = control.getWidth() / 2.0;
		Rotate ry_visible = null;
		for (Transform trans : page.getTransforms()) {
			if (trans instanceof Rotate) {
				ry_visible = (Rotate) trans;
				break;
			}
		}
		if (ry_visible == null) {
			ry_visible = new Rotate(angle, x, 0, 0, Rotate.Y_AXIS);
		}
		return ry_visible;
	}
}
