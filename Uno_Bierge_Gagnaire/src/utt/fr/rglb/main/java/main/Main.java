package utt.fr.rglb.main.java.main;

/**
 * Classe contenant le point d'entrée du programme
 */
public class Main {
	public static void main(String[] args) {
		try {
			System.setProperty("jansi.passthrough", "true");
			Server server = Server.getInstance();
			server.startPlaying();
		} catch(Exception e) {
			System.out.println("");
			System.out.println("[ERROR] An unexpected error happened");
			System.out.println("Class involved : " + e.getClass());
			System.out.println("");
			System.out.println("Message :" + e.getMessage());
			System.out.println("");
			System.out.println("========= STACK TRACE ==============");
			e.printStackTrace();
			System.out.println("");
		}
	}
}