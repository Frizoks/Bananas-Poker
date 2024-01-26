package projet.bananaspoker.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public Client() throws IOException {
        Socket socket = new Socket("localhost", 6000);

        // Crée un BufferedReader pour lire les messages du serveur
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Boucle pour lire et afficher les messages du serveur en continu
        String messageFromServer;
        while ((messageFromServer = reader.readLine()) != null) {
            System.out.println(messageFromServer);
        }
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}