package utt.fr.rglb.main.java.view.graphics.fxml;

public class FXMLControllerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la création d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 */
	public FXMLControllerException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception à propager
	 */
	public FXMLControllerException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 * @param e Exception à propager
	 */
	public FXMLControllerException(String message, Exception e) {
		super(message, e);
	}
}
