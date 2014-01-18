package utt.fr.rglb.main.java.game.model;

/**
 * Enumeration comprenant tous les états de jeu pouvant se produire lors du jeu d'une carte
 * </br>Valeurs possibles :
 * </br> - {@code NORMAL} : Dans les cas où une carte numérotée a été jouée (ou quand tous les effets ont été déclenchés)
 * </br> - {@code REVERSE} : Inversion du sens de jeu
 * </br> - {@code SKIP} : Interdiction pour le joueur suivant de jouer
 * </br> - {@code COLOR_PICK} : Le joueur en cours doit choisir une couleur
 * </br> - {@code PLUS_TWO} : Le joueur suivant doit piocher 2 cartes
 * </br> - {@code PLUS_FOUR} : Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes --CAS LEGITIME
 * </br> - {@code PLUS_FOUR_BLUFFING} : Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes --BLUFF
 */
public enum GameFlag {
	NORMAL,
	REVERSE,
	SKIP,
	COLOR_PICK,
	PLUS_TWO,
	PLUS_FOUR,
	PLUS_FOUR_BLUFFING,
	GRAPHICS_GAME_WON
}