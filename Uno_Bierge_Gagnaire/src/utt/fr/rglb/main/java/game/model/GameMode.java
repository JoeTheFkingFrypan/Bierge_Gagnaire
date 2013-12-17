package utt.fr.rglb.main.java.game.model;

/**
 * Enumeration définissant les divers modes de jeu
 * [NORMAL] Mode de jeu classique
 * [TwoPlayers] Mode 2 joueurs où les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte immédiatement
 * [TeamPlay] Mode en équipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) où le score est calculé par équipe plutôt qu'individuellement
 * [UnoChallenge] Mode "Last-Man Standing" se jouant par élminiations successives
 */
public enum GameMode {
	Normal,				//Mode de jeu classique
	TwoPlayers,			//Mode 2 joueurs où les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte immédiatement
	TeamPlay,			//Mode en équipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) où le score est calculé par équipe plutôt qu'individuellement
	UnoChallenge		//Mode "Last-Man Standing" se jouant par élminiations successives
}
