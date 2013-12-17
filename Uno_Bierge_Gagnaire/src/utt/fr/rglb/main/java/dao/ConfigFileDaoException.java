package utt.fr.rglb.main.java.dao;

/**
 * Classe d'exception permettant de remonter les probl�mes en liaison avec l'acc�s aux donn�es (DAO = Data Access Object)
 */
public class ConfigFileDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur permettant la cr�ation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 */
	public ConfigFileDaoException(String message) {
		super(message);
	}

	/**
	 * Constructeur permettant la propagation d'une exception sans message
	 * @param e Exception � propager
	 */
	public ConfigFileDaoException(Exception e) {
		super(e);
	}

	/**
	 * Constructeur permettant la propagation d'une exception avec message
	 * @param message String contenant le message associ� � l'exception
	 * @param e Exception � propager
	 */
	public ConfigFileDaoException(String message, Exception e) {
		super(message, e);
	}
}
