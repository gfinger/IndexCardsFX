package de.makkiato.fx.ctrls.indexcard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

public class IndexCardControl extends Control {
	private final String STYLE_SHEET = "IndexCard.css";
	private StringProperty headerOnFront = new SimpleStringProperty();
	private StringProperty headerOnBack = new SimpleStringProperty();
	private StringProperty textOnFront = new SimpleStringProperty();
	private StringProperty textOnBack = new SimpleStringProperty();
	private BooleanProperty editable = new SimpleBooleanProperty(false);

	public IndexCardControl() {
		getStyleClass().add("index-card");
	}

	@Override
	protected String getUserAgentStylesheet() {
		return getClass().getResource(STYLE_SHEET).toExternalForm();
	}

	public String getTextOnFront() {
		return textOnFront.getValue();
	}

	public void setTextOnFront(String textOnFront) {
		this.textOnFront.setValue(textOnFront);
	}

	public StringProperty textOnFrontProperty() {
		return textOnFront;
	}

	public String getTextOnBack() {
		return textOnBack.getValue();
	}

	public void setTextOnBack(String textOnBack) {
		this.textOnBack.setValue(textOnBack);
	}

	public StringProperty textOnBackProperty() {
		return textOnBack;
	}

	public String getHeaderOnBack() {
		return headerOnBack.getValue();
	}

	public void setHeaderOnBack(String headerOnBack) {
		this.headerOnBack.setValue(headerOnBack);
	}

	public StringProperty headerOnBackProperty() {
		return headerOnBack;
	}

	public String getHeaderOnFront() {
		return headerOnFront.getValue();
	}

	public void setHeaderOnFront(String headerOnFront) {
		this.headerOnFront.setValue(headerOnFront);
	}

	public StringProperty headerOnFrontProperty() {
		return headerOnFront;
	}

	public Boolean getEditable() {
		return editable.getValue();
	}

	public void setEditable(Boolean editable) {
		this.editable.setValue(editable);
	}

	public BooleanProperty editableProperty() {
		return editable;
	}
}
