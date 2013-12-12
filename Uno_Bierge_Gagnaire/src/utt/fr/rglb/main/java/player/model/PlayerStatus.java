package utt.fr.rglb.main.java.player.model;

import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;

/**
 * Classe permettant de centraliser les informations requises pour la création d'un joueur
 */
public class PlayerStatus {
	private String alias;
	private boolean isHuman;
	private CardPickerStrategy cardPickerStrategy;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de PlayerStatus pour un joueur humain
	 * @param alias Pseudo du joueur
	 */
	public PlayerStatus(String alias) {
		this.alias = alias;
		this.isHuman = true;
		this.cardPickerStrategy = null;
	}
	
	/**
	 * Constructeur de PlayerStatus pour un joueur controllé par la machine (avec une statégie spécifiée)
	 * @param alias Pseudo du joueur
	 * @param cardPickerStrategy Stratégie associée
	 */
	public PlayerStatus(String alias, CardPickerStrategy cardPickerStrategy) {
		this.alias = alias;
		this.isHuman = false;
		this.cardPickerStrategy = cardPickerStrategy;
	}

	/* ========================================= GETTERS ========================================= */
	
	/**
	 * Méthode permettant de récupérer le pseudo du joueur
	 * @return String correspondant au pseudo du joueur
	 */
	public String getAlias() {
		return this.alias;
	}
	
	/**
	 * Méthode permettant de savoir si le joueur est humain, ou s'l s'agit d'une IA
	 * @return <code>TRUE</code> si le joeur est humain, <code>FALSE</code> sinon
	 */
	public boolean isHuman() {
		return this.isHuman;
	}
	
	/**
	 * Méthode permettant de récupérer la stratégie de l'IA
	 * @return La stratégie de l'IA
	 */
	public CardPickerStrategy getStrategy() {
		return this.cardPickerStrategy;
	}
	
	@Override 
	public String toString() {
		return this.alias;
	}
	
	/* ========================================= COMPARAISON ========================================= */
	
	@Override
	public boolean equals(Object other) {
		if(!other.getClass().equals(PlayerStatus.class)) {
			return false;
		} else {
			PlayerStatus otherPlayer = (PlayerStatus)other;
			return this.alias.equals(otherPlayer.getAlias());
		}
	}
}
