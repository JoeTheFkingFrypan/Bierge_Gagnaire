package utt.fr.rglb.main.java.game.model;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * Classe dont le rôle est d'encapsuler le mode de jeu et l'état actuel de la partie
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
	
	public GameMode getGameMode() {
		return this.gameMode;
	}
	
	/**
	 * Méthode permettant de savoir si le mode 2 joueurs a été choisi
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean indicatesTwoPlayersMode() {
		return this.gameMode.equals(GameMode.TWO_PLAYERS);
	}
	
	/**
	 * Méthode permettant de savoir si le mode par équipe a été choisi
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean indicatesTeamPlayScoring() {
		return this.gameMode.equals(GameMode.TEAM_PLAY);
	}

	/* ========================================= GAME FLAG - SETTERS ========================================= */
	
	/**
	 * Méthode permettant de remettre l'état du jeu à son état par défaut
	 */
	public void resetFlag() {
		this.gameFlag = GameFlag.NORMAL;
	}

	/**
	 * Méthode permettant de mettre à jour l'état acutel de jeu
	 * @param flag Nouvel état
	 */
	public void setFlag(GameFlag flag) {
		Preconditions.checkNotNull(flag,"[ERROR] Impossible to set current game state : provided game flag is null");
		this.gameFlag = flag;
	}
	
	/* ========================================= GAME FLAG - GETTERS ========================================= */
	
	/**
	 * Méthode permettant de déterminer s'il faut inverser l'ordre de jeu
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldReverseTurn() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.REVERSE);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode permettant de déterminer s'il faut passer le tour du joueur suivant
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldSkipNextPlayerTurn() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.SKIP);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode permettant de déterminer si le joueur en cours doit choisir une couleur
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldPickColor() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.COLOR_PICK);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode permettant de déterminer s'il faut donner une pénalité de 2 cartes au joueur suivant
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldGivePlus2CardPenalty() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.PLUS_TWO);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode permettant de déterminer si le joueur en cours doit choisir une couleur et donner une pénalité de 4 cartes au joueur suivant
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldGivePlus4CardPenalty() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.PLUS_FOUR);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode permettant de déterminer si le joueur en cours doit choisir une couleur et donner une pénalité de 4 cartes au joueur suivant
	 * L'état de jeu sera remis à son état par défaut (NORMAL) si jamais la méthode renvoie <code>TRUE</code>
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean shouldGivePlus4CardPenaltyWhileBluffing() {
		GameFlag currentFlag = this.gameFlag;
		boolean bothAreTheSame = currentFlag.equals(GameFlag.PLUS_FOUR_BLUFFING);
		return resetGameFlagIfNeeded(bothAreTheSame,currentFlag);
	}
	
	/**
	 * Méthode privée permettant de ré-initialiser l'état de jeu uniquement dans le cas où l'effet testé est bien celui attendu
	 * Ceci permet d'éviter un appel de méthode supplémentaire après avoir appliquer ledit effet
	 * Il est ré-initialiser en amont plutôt qu'à postériori
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
