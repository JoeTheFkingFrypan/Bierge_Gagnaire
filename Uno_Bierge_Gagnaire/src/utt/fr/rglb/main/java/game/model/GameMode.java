package utt.fr.rglb.main.java.game.model;

/**
 * Enumeration d�finissant les divers modes de jeu
 * </br>Valeurs possibles :
 * </br> - {@code NORMAL} : Mode de jeu classique
 * </br> - {@code TWO_PLAYERS} : Mode 2 joueurs o� les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte imm�diatement
 * </br> - {@code TEAM_PLAY} : Mode en �quipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) o� le score est calcul� par �quipe plut�t qu'individuellement
 * </br> - {@code UNO_CHALLENGE} : Mode "Last-Man Standing" se jouant par �lminiations successives
 */
public enum GameMode {
	NORMAL,
	TWO_PLAYERS,
	TEAM_PLAY,
	UNO_CHALLENGE
}
