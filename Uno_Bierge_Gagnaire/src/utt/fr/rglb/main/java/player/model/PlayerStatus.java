package utt.fr.rglb.main.java.player.model;

import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;

/**
 * Classe permettant de centraliser les informations requises pour la cr�ation d'un joueur
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
	 * Constructeur de PlayerStatus pour un joueur controll� par la machine (avec une stat�gie sp�cifi�e)
	 * @param alias Pseudo du joueur
	 * @param cardPickerStrategy Strat�gie associ�e
	 */
	public PlayerStatus(String alias, CardPickerStrategy cardPickerStrategy) {
		this.alias = alias;
		this.isHuman = false;
		this.cardPickerStrategy = cardPickerStrategy;
	}

	/* ========================================= GETTERS ========================================= */
	
	/**
	 * M�thode permettant de r�cup�rer le pseudo du joueur
	 * @return String correspondant au pseudo du joueur
	 */
	public String getAlias() {
		return this.alias;
	}
	
	/**
	 * M�thode permettant de savoir si le joueur est humain, ou s'l s'agit d'une IA
	 * @return <code>TRUE</code> si le joeur est humain, <code>FALSE</code> sinon
	 */
	public boolean isHuman() {
		return this.isHuman;
	}
	
	/**
	 * M�thode permettant de r�cup�rer la strat�gie de l'IA
	 * @return La strat�gie de l'IA
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
