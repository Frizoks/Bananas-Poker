package projet.bananaspoker.metier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Salle implements Runnable {

    private final int port;
    private final int numberOfPlayers;
    private final String password;
    private final ArrayList<GerantDeJoueur> lstConnections;

    public Salle(int port, int numberOfPlayers, String password) {
        this.port = port;
        this.numberOfPlayers = numberOfPlayers;
        this.password = password;
        this.lstConnections = new ArrayList<>();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (lstConnections.size() < numberOfPlayers) {
                Socket clientSocket = serverSocket.accept();
                GerantDeJoueur gdj = new GerantDeJoueur(clientSocket,this);
                Thread tgdc = new Thread(gdj);
                tgdc.start();
            }

            System.out.println("All lstJoueur connected. Server is closing.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connection(GerantDeJoueur gdc) {
        envoiMess("[Connection]", gdc);
        gdc.getOut().println("Bonjour");
        System.out.println(gdc.getOut());
        this.lstConnections.add(gdc);
        System.out.println("Un client est venu, " + this.lstConnections.size() + " personnes sont connectées");
    }

    public void deconnection(GerantDeJoueur gdc) {
        envoiMess("[Deconnection]", gdc);
        this.lstConnections.remove(gdc);
        System.out.println("Un client est parti, " + this.lstConnections.size() + " personnes sont connectées");
    }

    public void envoiMess(String message, GerantDeJoueur gdc) {
        for (GerantDeJoueur gerantDeClient : lstConnections) {
            if ( gerantDeClient != gdc && message != null)
            {
                gerantDeClient.getOut().println(message);
            }
        }
    }

    public String getMotDePasse() { return this.password; }
}
