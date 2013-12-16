package utt.fr.rglb.main.java.turns.model;

/**
 * Enumeration comprenant tous les états de jeu pouvant se produire lors du jeu d'une carte
 */
public enum GameFlag {
	NORMAL,					//Dans les cas où une carte numérotée a été jouée (ou quand tous les effets ont été déclenchés)
	REVERSE,				//Inversion du sens de jeu
	SKIP,					//Interdiction pour le joueur suivant de jouer
	COLOR_PICK,				//Le joueur en cours doit choisir une couleur
	PLUS_TWO,				//Le joueur suivant doit piocher 2 cartes
	PLUS_FOUR,				//Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes --CAS LEGITIME
	PLUS_FOUR_BLUFFING		//Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes --BLUFF
}