package utt.fr.rglb.main.java.console.model;

/**
 * Classe d'exception permettant de remonter les probl�mes en liaison avec la console
 */
public class ConsoleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la cr�ation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 */
	public ConsoleException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception � propager
	 */
	public ConsoleException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 * @param e Exception � propager
	 */
	public ConsoleException(String message, Exception e) {
		super(message, e);
	}
}
