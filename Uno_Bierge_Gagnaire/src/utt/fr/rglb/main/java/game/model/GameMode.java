package utt.fr.rglb.main.java.game.model;

/**
 * Enumeration définissant les divers modes de jeu
 * </br>Valeurs possibles :
 * </br> - {@code NORMAL} : Mode de jeu classique
 * </br> - {@code TWO_PLAYERS} : Mode 2 joueurs où les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte immédiatement
 * </br> - {@code TEAM_PLAY} : Mode en équipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) où le score est calculé par équipe plutôt qu'individuellement
 * </br> - {@code UNO_CHALLENGE} : Mode "Last-Man Standing" se jouant par élminiations successives
 */
public enum GameMode {
	NORMAL,
	TWO_PLAYERS,
	TEAM_PLAY,
	UNO_CHALLENGE
}
