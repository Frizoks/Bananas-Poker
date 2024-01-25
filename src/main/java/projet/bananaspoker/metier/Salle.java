package projet.bananaspoker.metier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Salle {

    private final int port;
    private final int numberOfPlayers;
    private final String password;
    private final ArrayList<String> lstJoueur;

    public Salle(int port, int numberOfPlayers, String password) {
        this.port = port;
        this.numberOfPlayers = numberOfPlayers;
        this.password = password;
        this.lstJoueur = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (lstJoueur.size() < numberOfPlayers) {
                Socket clientSocket = serverSocket.accept();
                handleClientConnection(clientSocket);
            }

            System.out.println("All lstJoueur connected. Server is closing.");
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    private void handleClientConnection(Socket clientSocket) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            if ( password != null ) {
                String clientPassword = (String) inputStream.readObject();

                // Check if the password is correct
                if (!password.equals(clientPassword)) {
                    System.out.println("Incorrect password. Connection closed.");
                    return;
                }
            }

            lstJoueur.add(clientSocket.getInetAddress().getHostAddress());
            System.out.println("Player connected");

            // Send a welcome message to the client
            outputStream.writeObject("Welcome !");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
