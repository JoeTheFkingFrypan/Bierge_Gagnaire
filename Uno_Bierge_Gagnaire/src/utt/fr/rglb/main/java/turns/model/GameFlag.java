package utt.fr.rglb.main.java.turns.model;

/**
 * Enumeration comprenant tous les états de jeu pouvant se produire lors du jeu d'une carte
 * @see [NORMAL] Dans les cas où une carte numérotée a été jouée (ou quand tous les effets ont été déclenchés)
 * @see [REVERSE] Inversion du sens de jeu
 * @see [SKIP] Interdiction pour le joueur suivant de jouer
 * @see [COLOR_PICK] Le joueur en cours doit choisir une couleur
 * @see [PLUS_TWO] Le joueur suivant doit piocher 2 cartes
 * @see [PLUS_FOUR] Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes // CAS LEGITIME
 * @see [PLUS_FOUR_BLUFFING] Le joueur en cours doit choisir une couleur, le joueur suivant doit piocher 2 cartes // BLUFF
 */
public enum GameFlag {
	NORMAL,
	REVERSE,
	SKIP,
	COLOR_PICK,
	PLUS_TWO,
	PLUS_FOUR,
	PLUS_FOUR_BLUFFING
}