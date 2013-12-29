package utt.fr.rglb.main.java.console.model;

/**
 * Classe d'exception permettant de remonter les problèmes en liaison avec la console
 */
public class ConsoleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la création d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 */
	public ConsoleException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception à propager
	 */
	public ConsoleException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 * @param e Exception à propager
	 */
	public ConsoleException(String message, Exception e) {
		super(message, e);
	}
}
