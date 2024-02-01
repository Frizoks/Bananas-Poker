package projet.bananaspoker.metier;

import javafx.application.Platform;
import projet.bananaspoker.ihm.stage.Gestionnaire;
import projet.bananaspoker.ihm.stage.StageSalleAttente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Salle {

    private int port;
    private final String password;
    private final int nbJetonsDep;
    private final int nbJoueursTot;
    private final ArrayList<Joueur> lstConnections;
    private StageSalleAttente salleAttente;

    public Salle(int port, int nbJoueursTot, String password, int nbJetonsDep) {
        this.port = port;
        this.nbJoueursTot = nbJoueursTot;
        this.password = password;
        this.lstConnections = new ArrayList<>();
        this.nbJetonsDep = nbJetonsDep;
        this.salleAttente = Gestionnaire.creer("salleAttente");
        this.salleAttente.setSalle(this);
    }

    public Thread getServeur() {
        return new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is running on port " + port);

                while (lstConnections.size() < nbJoueursTot) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader entreeTemp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String[] donnees = entreeTemp.readLine().split(":");
                    Joueur j = new Joueur(donnees[0],Integer.parseInt(donnees[1]));
                    j.setPorts(clientSocket);

                    for ( Joueur joueur : lstConnections )
                    {
                        joueur.getSortie().println(j);
                        j.getSortie().println(joueur);
                    }
                    this.lstConnections.add( j );
                }

            /*while ( !partieGagne ) {
                Table table = new Table();
                table.jouer();
            }*/

                System.out.println("Game finish. Server is closing.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void connection(int port, String nomJ) {
        Thread client = new Thread(() -> {
			Socket socket = null;
			try {
				socket = new Socket("localhost", port);
                BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter    sortie = new PrintWriter(socket.getOutputStream(), true);

                Joueur moi = new Joueur(nomJ,this.nbJetonsDep);
                Platform.runLater(() -> salleAttente.ajouterJoueur(moi.getNomJoueur()));
                sortie.println(moi);

                // Boucle pour lire et afficher les messages du serveur en continu
                String messageFromServer;
                while ((messageFromServer = entree.readLine()) != null) {
                    String[] donnees = messageFromServer.split(":");
                    Platform.runLater(() -> salleAttente.ajouterJoueur(donnees[0]));
                }
			} catch (IOException e) {
                System.out.println(e);
			}
        });
        client.start();
        this.salleAttente.show();
    }

    public void deconnection(Joueur j) {
        this.lstConnections.remove(j);
        System.out.println("Un client est parti, " + this.lstConnections.size() + " personnes sont connectées");
    }


    public String getMotDePasse() { return this.password; }
    public int getNbJetonsDep() { return this.nbJetonsDep; }
    public int getNbJoueursTot() { return this.nbJoueursTot; }
}
