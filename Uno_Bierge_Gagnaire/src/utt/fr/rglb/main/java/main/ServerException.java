package utt.fr.rglb.main.java.main;

/**
 * Classe d'exception permettant de remonter les problèmes en liaison avec le serveur
 */
public class ServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la création d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 */
	public ServerException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception à propager
	 */
	public ServerException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 * @param e Exception à propager
	 */
	public ServerException(String message, Exception e) {
		super(message, e);
	}
}
