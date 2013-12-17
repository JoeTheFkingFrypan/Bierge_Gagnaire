package utt.fr.rglb.main.java.game.model;

/**
 * Enumeration d�finissant les divers modes de jeu
 * [NORMAL] Mode de jeu classique
 * [TwoPlayers] Mode 2 joueurs o� les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte imm�diatement
 * [TeamPlay] Mode en �quipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) o� le score est calcul� par �quipe plut�t qu'individuellement
 * [UnoChallenge] Mode "Last-Man Standing" se jouant par �lminiations successives
 */
public enum GameMode {
	Normal,				//Mode de jeu classique
	TwoPlayers,			//Mode 2 joueurs o� les cartes invertions/interdiction permettent au joueur actuel de jouer une nouvelle carte imm�diatement
	TeamPlay,			//Mode en �quipe de 2 (valide uniquement s'il y a 4 ou 6 joueurs) o� le score est calcul� par �quipe plut�t qu'individuellement
	UnoChallenge		//Mode "Last-Man Standing" se jouant par �lminiations successives
}
