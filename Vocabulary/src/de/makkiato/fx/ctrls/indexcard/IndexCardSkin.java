package de.makkiato.fx.ctrls.indexcard;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import com.sun.javafx.scene.control.skin.SkinBase;

public class IndexCardSkin extends
		SkinBase<IndexCardControl, IndexCardBehaviour> {
	private enum Side {
		FRONT, BACK
	};

	private IndexCardControl control;
	private Pane card;
	private Pane frontPage;
	private Pane backPage;
	private TextField header;
	private TextArea text;

	public IndexCardSkin(IndexCardControl control) {
		super(control, new IndexCardBehaviour(control));
		this.control = control;

		card = new Pane();
		frontPage = new Pane();
		backPage = new Pane();
		Rotate ry = new Rotate(180.0, 0, 0, 0, Rotate.Y_AXIS);
		backPage.getTransforms().setAll(ry);
		card.getChildren().setAll(frontPage, backPage);
		backPage.toBack();
	}

	@Override
	public void dispose() {
		super.dispose();
		backPage = null;
		frontPage = null;
		card = null;
		control = null;
	}

	@Override
	public IndexCardControl getSkinnable() {
		return control;
	}

	@Override
	protected void layoutChildren() {
		drawControl();
	}

	private void drawControl() {
		Side bottomSide = (card.getTransforms().size() == 0 || ((Rotate) card
				.getTransforms().get(0)).getAngle() == 0) ? Side.BACK
				: Side.FRONT;
		card = copyPageFrom(card);
		frontPage = copyPageFrom(frontPage);
		drawPage(frontPage, Side.FRONT);
		backPage = copyPageFrom(backPage);
		drawPage(backPage, Side.BACK);
		card.getChildren().setAll(frontPage, backPage);
		(bottomSide == Side.BACK ? backPage : frontPage).toBack();
		getChildren().setAll(card);
	}

	private Pane copyPageFrom(Pane page) {
		double width = getWidth();
		ObservableList<Transform> transforms = page.getTransforms();
		double opacity = page.getOpacity();
		page = new Pane();
		for (Transform transform : transforms) {
			if (transform instanceof Rotate) {
				((Rotate) transform).setPivotX(width / 2.0);
			}
		}
		page.getTransforms().setAll(transforms);
		page.setOpacity(opacity);
		return page;
	}

	private void drawPage(Pane page, Side side) {
		Group cardLayer = drawCardLayer(side);
		VBox textLayer =drawTextLayer(side);
		page.getChildren().setAll(cardLayer, textLayer);
	}

	private VBox drawTextLayer(Side side) {
		double width = getWidth();
		double height = getHeight();
		VBox textLayer = new VBox();
		textLayer.setPrefSize(width, height);
		
		header = new TextField();
		header.setPrefWidth(width);
		header.setLayoutY(28);
		header.setAlignment(Pos.BASELINE_CENTER);
		VBox.setMargin(header, new Insets(29, 0, 2, 0));
		header.editableProperty().bindBidirectional(control.editableProperty());
		header.disableProperty().bind(control.editableProperty().not());
		textLayer.getChildren().add(header);
		
		text = new TextArea();
		text.setPrefWidth(width);
		VBox.setVgrow(text, Priority.ALWAYS);
		text.editableProperty().bindBidirectional(control.editableProperty());
		text.disableProperty().bind(control.editableProperty().not());
		textLayer.getChildren().add(text);
		
		if (side == Side.FRONT) {
			header.textProperty().bindBidirectional(
					control.headerOnFrontProperty());
			header.getStyleClass().add("index-card-front-header");
			text.textProperty()
					.bindBidirectional(control.textOnFrontProperty());
			text.getStyleClass().add("index-card-front-text");
		} else {
			header.textProperty().bindBidirectional(
					control.headerOnBackProperty());
			header.getStyleClass().add("index-card-back-header");
			text.textProperty().bindBidirectional(control.textOnBackProperty());
			text.getStyleClass().add("index-card-back-text");
		}
		
		return textLayer;
	}

	private Group drawCardLayer(Side side) {
		double width = getWidth();
		double height = getHeight();
		Group cardLayer = new Group();
		Rectangle rect = buildRect(width, height);
		
		rect.getStyleClass().add(
				(side == Side.FRONT) ? "index-card-front-rect"
						: "index-card-back-rect");
		cardLayer.getChildren().add(rect);

		Line topLine = buildHLine(50.0, width);
		topLine.getStyleClass().add(
				(side == Side.FRONT) ? "index-card-front-top-line"
						: "index-card-back-top-line");
		cardLayer.getChildren().add(topLine);

		for (double y = 50.0 + 25.0; y < height; y = y + 25.0) {
			Line line = buildHLine(y, width);
			line.getStyleClass().add(
					(side == Side.FRONT) ? "index-card-front-line"
							: "index-card-back-line");
			cardLayer.getChildren().add(line);
		}
		
		return cardLayer;
	}

	private Rectangle buildRect(double width, double hight) {
		return RectangleBuilder.create().width(width).height(hight).build();
	}

	private Line buildHLine(double y, double width) {
		return LineBuilder.create().startX(0.0).startY(y).endX(width).endY(y)
				.build();
	}

	Pane getFrontPage() {
		return frontPage;
	}

	Pane getBackPage() {
		return backPage;
	}

	Pane getCard() {
		return card;
	}
	
	TextField getHeaderField() {
		return header;
	}
	
	TextArea getTextArea() {
		return text;
	}

}
