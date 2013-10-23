package carteModeleTest;

import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;

import carteModele.Carte;
import carteModele.CarteSpeciale;
import carteModele.Couleur;
import carteModele.Effet;
import carteModele.JoueurDeCarte;

public class JoueurDeCarteTest {
	private JoueurDeCarte j2c;
	private Carte carteNonSpeciale;
	private Carte carteSpecialeImplicite;
	private CarteSpeciale carteSpecialeExplicite;
	private Effet effet;
	
	@Before
	public void setup() {
		final int valeur = 0;
		final Couleur couleur = Couleur.BLEUE;
		
		this.effet = mock(Effet.class);
		this.j2c = new JoueurDeCarte();
		this.carteNonSpeciale = new Carte(valeur, couleur);
		this.carteSpecialeImplicite = new CarteSpeciale(valeur, couleur, effet);
		this.carteSpecialeExplicite = new CarteSpeciale(valeur, couleur, effet);
	}
	
	@Test
	public void testJouerCarteNonSpeciale() {
		this.j2c.jouerCarte(carteNonSpeciale);
	}
	
	@Test
	public void testJouerCarteSpecialeImplicite() {
		this.j2c.jouerCarte(carteSpecialeImplicite);
	}
	
	@Test
	public void testJouerCarteSpecialeExplicite() {
		this.j2c.jouerCarte(carteSpecialeExplicite);
	}
}
