package utt.fr.rglb.main.java.main;

/**
 * Classe contenant le point d'entrée du programme
 */
public class Main {
	public static void main(String[] args) {
		try {
			System.setProperty("jansi.passthrough", "true"); // jansi Workaround : jansi detects when the standard output is redirected and does not output the escape sequences anymore.
			Server server = Server.getInstance();
			server.startPlaying();
		} catch(Exception e) {
			System.out.println("[ERROR] An unexpected error happened, here is what went wrong : " + e.getClass() + " => " + e.getMessage());
			System.out.println("========= STACK TRACE ==============");
			e.printStackTrace();
		}
	}
}