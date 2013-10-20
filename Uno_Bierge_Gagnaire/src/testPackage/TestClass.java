package testPackage;

import Cartes.Carte;
import Cartes.CarteSpeciale;
import Cartes.Couleur;
import Cartes.Effet;
import Cartes.EffetAucun;
import Cartes.EffetPasserTour;
import Cartes.EffetPiocherCarte;

public class TestClass {
	public static void main(String[] args) {

		//Création d'une carte numérotée
		Carte c1 = new Carte(3, Couleur.ROUGE, new EffetAucun());
		c1.afficherNombre();
		c1.afficherCouleur();
		c1.afficherValeur();
		c1.afficherEffet();
		c1.declencherEffet();

		//Ligne vide
		blankLine();

		//Création d'une carte spéciale +4
		CarteSpeciale c2 = new CarteSpeciale(10, Couleur.JAUNE, new EffetPasserTour());
		c2.afficherNombre();
		c2.afficherCouleur();
		c2.afficherValeur();
		c2.afficherEffet();
		c2.declencherEffet();

		//Ligne vide
		blankLine();

		//Création d'une carte spéciale +4
		CarteSpeciale c3 = new CarteSpeciale(25, Couleur.BLEU, new EffetPiocherCarte(2));
		c3.afficherNombre();
		c3.afficherCouleur();
		c3.afficherValeur();
		c3.afficherEffet();
		c3.declencherEffet();

		//Ligne vide
		blankLine();

		//Création d'une carte spéciale +4
		CarteSpeciale c4 = new CarteSpeciale(50, Couleur.JOKER, new EffetPiocherCarte(4));
		c4.afficherNombre();
		c4.afficherCouleur();
		c4.afficherValeur();
		c4.afficherEffet();
		c4.declencherEffet();
	}

	private static void blankLine() {
		System.out.println("");
	}
}
