package utt.fr.rglb.main.java.game.model;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * Classe dont le r�le est d'encapsuler le mode de jeu et l'�tat actuel de la partie
 */
public class GameRule implements Serializable {
	private static final long serialVersionUID = 1L;
	private GameFlag gameFlag;
	private GameMode gameMode;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GameRule(GameMode gameMode) {
		Preconditions.checkNotNull(gameMode,"[ERROR] Impossible to create game rules : provided game mode is null");
		this.gameFlag = GameFlag.NORMAL;
		this.gameMode = gameMode;
	}
	
	/* ========================================= GAME MODE ========================================= */
	
	/**
	 * M�thode permettant de savoir si le mode 2 joueurs a �t� choisi
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean indicatesTwoPlayersMode() {
		return this.gameMode.equals(GameMode.TwoPlayers);
	}
	
	/**
	 * M�thode permettant de savoir si le mode par �quipe a �t� choisi
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean indicatesTeamPlayScoring() {
		return this.gameMode.equals(GameMode.TeamPlay);
	}

	/* ========================================= GAME FLAG - SETTERS ========================================= */
	
	/**
	 * M�thode permettant de remettre l'�tat du jeu � son �tat par d�faut
	 */
	public void resetFlag() {
		this.gameFlag = GameFlag.NORMAL;
	}

	/**
	 * M�thode permettant de mettre � jour l'�tat acutel de jeu
	 * @param flag Nouvel �tat
	 */
	public void setFlag(GameFlag flag) {
		Preconditions.checkNotNull(flag,"[ERROR] Impossible to set current game state : provided game flag is null");
		this.gameFlag = flag;
	}
	
	/* ========================================= GAME FLAG - GETTERS ========================================= */
	
	/**
	 * M�thode permettant de d�terminer s'il faut inverser l'ordre de jeu
	 * L'�tat de jeu sera remis � son �tat par d�faut (NORMAL) si jamais la m�thode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldReverseTurn() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.REVERSE);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * M�thode permettant de d�terminer s'il faut passer le tour du joueur suivant
	 * L'�tat de jeu sera remis � son �tat par d�faut (NORMAL) si jamais la m�thode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldSkipNextPlayerTurn() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.SKIP);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * M�thode permettant de d�terminer si le joueur en cours doit choisir une couleur
	 * L'�tat de jeu sera remis � son �tat par d�faut (NORMAL) si jamais la m�thode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldPickColor() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.COLOR_PICK);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * M�thode permettant de d�terminer s'il faut donner une p�nalit� de 2 cartes au joueur suivant
	 * L'�tat de jeu sera remis � son �tat par d�faut (NORMAL) si jamais la m�thode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldGivePlus2CardPenalty() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.PLUS_TWO);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * M�thode permettant de d�terminer si le joueur en cours doit choisir une couleur et donner une p�nalit� de 4 cartes au joueur suivant
	 * L'�tat de jeu sera remis � son �tat par d�faut (NORMAL) si jamais la m�thode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldGivePlus4CardPenalty() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.PLUS_FOUR);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * M�thode priv�e permettant de r�-initialiser l'�tat de jeu uniquement dans le cas o� l'effet test� est bien celui attendu
	 * Ceci permet d'�viter un appel de m�thode suppl�mentaire apr�s avoir appliquer ledit effet
	 * Il est r�-initialiser en amont plut�t qu'� post�riori
	 */
	private boolean resetGameFlagIfNeeded(boolean resetNeeded, GameFlag currentFlag) {
		Preconditions.checkNotNull(resetNeeded,"[ERROR] Impossible to find if a reset is required : provided reset flag is null");
		Preconditions.checkNotNull(currentFlag,"[ERROR] Impossible to find if a reset is required : provided game flag is null");
		if(resetNeeded) {
			this.gameFlag = GameFlag.NORMAL;
		}
		return resetNeeded;
	}
}
