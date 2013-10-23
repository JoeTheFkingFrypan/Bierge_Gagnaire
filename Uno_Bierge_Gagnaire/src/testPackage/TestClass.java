package testPackage;

import carteModele.Carte;
import carteModele.CarteSpeciale;
import carteModele.Couleur;
import carteModele.EffetPasserTour;
import carteModele.EffetPiocherCarte;
import carteModele.JoueurDeCarte;

public class TestClass {
	private static void blankLine() {
		System.out.println("");
	}

	public static void main(String[] args) {
		JoueurDeCarte j2c = new JoueurDeCarte();
		
		//Création d'une carte numérotée
		Carte c1 = new Carte(3, Couleur.ROUGE);
		c1.afficherNombre();
		c1.afficherCouleur();
		c1.afficherValeur();
		j2c.jouerCarte(c1);
		
		//Ligne vide
		blankLine();

		//Création d'une carte spéciale passer tour
		Carte c2 = new CarteSpeciale(10, Couleur.JAUNE, new EffetPasserTour());
		c2.afficherNombre();
		c2.afficherCouleur();
		c2.afficherValeur();
		j2c.jouerCarte(c2);

		//Ligne vide
		blankLine();

		//Création d'une carte spéciale +2
		Carte c3 = new CarteSpeciale(25, Couleur.BLEUE, new EffetPiocherCarte(2));
		c3.afficherNombre();
		c3.afficherCouleur();
		c3.afficherValeur();
		j2c.jouerCarte(c3);

		//Ligne vide
		blankLine();

		//Création d'une carte spéciale +4
		Carte c4 = new CarteSpeciale(50, Couleur.JOKER, new EffetPiocherCarte(4));
		c4.afficherNombre();
		c4.afficherCouleur();
		c4.afficherValeur();
		j2c.jouerCarte(c4);
	}
}