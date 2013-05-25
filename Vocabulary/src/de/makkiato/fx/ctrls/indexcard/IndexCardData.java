package de.makkiato.fx.ctrls.indexcard;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class IndexCardData {
	private StringProperty frontHeader = new SimpleStringProperty();
	private StringProperty backHeader = new SimpleStringProperty();
	private StringProperty frontText = new SimpleStringProperty();
	private StringProperty backText = new SimpleStringProperty();
	private BooleanProperty changed = new SimpleBooleanProperty(false);
	
	private ChangeListener<String> changeListener = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			if (!oldValue.equals(newValue)) {
				changed.set(true);
			}
		}
	};

	public IndexCardData(String frontHeader, String backHeader,
			String frontText, String backText) {
		this.frontHeader.setValue(frontHeader);
		this.backHeader.setValue(backHeader);
		this.frontText.setValue(frontText);
		this.backText.setValue(backText);
		init();
	}
	
	public IndexCardData() {
		init();
	}

	private void init() {
		frontHeader.addListener(changeListener);
		backHeader.addListener(changeListener);
		frontText.addListener(changeListener);
		backText.addListener(changeListener);	
	}

	public String getFrontHeader() {
		return frontHeader.getValue();
	}

	public void setFrontHeader(String frontHeader) {
		this.frontHeader.setValue(frontHeader);
	}

	public StringProperty frontHeaderProperty() {
		return frontHeader;
	}

	public String getBackHeader() {
		return backHeader.getValue();
	}

	public void setBackHeader(String backHeader) {
		this.backHeader.setValue(backHeader);
	}

	public StringProperty backHeaderProperty() {
		return backHeader;
	}

	public String getFrontText() {
		return frontText.getValue();
	}

	public void setFrontText(String frontText) {
		this.frontText.setValue(frontText);
	}

	public StringProperty frontTextProperty() {
		return frontText;
	}

	public String getBackText() {
		return backText.getValue();
	}

	public void setBackText(String backText) {
		this.backText.setValue(backText);
	}

	public StringProperty backTextProperty() {
		return backText;
	}
	
	public boolean isChanged() {
		return changed.get();
	}
	
	public void setChanged(boolean changed) {
		this.changed.set(changed);
	}

}
