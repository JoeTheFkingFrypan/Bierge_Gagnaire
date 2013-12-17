package utt.fr.rglb.main.java.dao;

/**
 * Classe d'exception permettant de remonter les problèmes en liaison avec l'accès aux données (DAO = Data Access Object)
 */
public class ConfigFileDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la création d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 */
	public ConfigFileDaoException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception à propager
	 */
	public ConfigFileDaoException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associé à l'exception
	 * @param e Exception à propager
	 */
	public ConfigFileDaoException(String message, Exception e) {
		super(message, e);
	}
}
