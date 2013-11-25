package main.modele.joueurModele;

public class PlayerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public PlayerException(String message) {
		throw new PlayerException(message);
	}
	
	public PlayerException(Exception e) {
		throw new PlayerException(e);
	}
	
	public PlayerException(Exception e, String message) {
		throw new PlayerException(e,message);
	}
}
