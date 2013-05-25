package de.makkiato.fx.ctrls.carddeck;

import de.makkiato.fx.ctrls.indexcard.IndexCardData;
import javafx.event.Event;
import javafx.event.EventType;

public class CardDataChangeEvent extends Event {
	public static final EventType<CardDataChangeEvent> DATA_CHANGE = new EventType<>(
			ANY, "DATA_CHANGED");
	public int index;
	public IndexCardData cardData;
	
	public CardDataChangeEvent(int index, IndexCardData cardData) {
		super(DATA_CHANGE);
		this.index = index;
		this.cardData = cardData;
	}

	private static final long serialVersionUID = 1356861728581587594L;

}
