package Cartes;

public class EffetAucun implements Effet {
	
	@Override
	public void declencherEffet() {
		//Aucun effet
	}

	@Override
	public String toString() {
		return "Cette carte n'a pas d'effet special";
	}
}
