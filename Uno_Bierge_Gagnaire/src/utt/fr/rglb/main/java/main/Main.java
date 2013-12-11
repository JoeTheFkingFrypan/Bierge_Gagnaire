package utt.fr.rglb.main.java.main;

/**
 * Classe contenant le point d'entr�e du programme
 */
public class Main {
	public static void main(String[] args) {
		try {
			Server server = Server.getInstance();
			server.startPlaying();
		} catch(Exception e) {
			System.out.println("[ERROR] An unexpected error happened, here is what went wrong : " + e.getClass() + " => " + e.getMessage());
			System.out.println("========= STACK TRACE ==============");
			e.printStackTrace();
		}
	}
}