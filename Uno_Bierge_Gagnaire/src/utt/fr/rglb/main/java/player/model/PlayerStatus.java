package utt.fr.rglb.main.java.player.model;

import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;

public class PlayerStatus {
	private String alias;
	private boolean isHuman;
	private CardPickerStrategy cardPickerStrategy;
	
	public PlayerStatus(String alias) {
		this.alias = alias;
		this.isHuman = true;
		this.cardPickerStrategy = null;
	}
	
	public PlayerStatus(String alias, CardPickerStrategy cardPickerStrategy) {
		this.alias = alias;
		this.isHuman = false;
		this.cardPickerStrategy = cardPickerStrategy;
	}

	public String getAlias() {
		return this.alias;
	}
	
	public boolean isHuman() {
		return this.isHuman;
	}
	
	public CardPickerStrategy getStrategy() {
		return this.cardPickerStrategy;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!other.getClass().equals(PlayerStatus.class)) {
			return false;
		} else {
			PlayerStatus otherPlayer = (PlayerStatus)other;
			return this.alias.equals(otherPlayer.getAlias());
		}
	}
	
	@Override 
	public String toString() {
		return this.alias;
	}
}
