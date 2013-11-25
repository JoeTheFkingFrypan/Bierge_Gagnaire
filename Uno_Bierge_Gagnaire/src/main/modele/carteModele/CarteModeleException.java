package main.modele.carteModele;

public class CarteModeleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CarteModeleException(String message) {
		throw new CarteModeleException(message);
	}

	public CarteModeleException(Exception e) {
		throw new CarteModeleException(e);
	}

	public CarteModeleException(Exception e, String message) {
		throw new CarteModeleException(e,message);
	}
}
