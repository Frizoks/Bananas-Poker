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
import java.util.HashMap;

public class Salle {

    private int port;
    private final String password;
    private final int nbJetonsDep;
    private final int nbJoueursTot;
    private final HashMap<Socket,Joueur> lstConnections;
    private final ArrayList<Joueur> lstJoueurs;
    private StageSalleAttente salleAttente;
    private Socket client;

    public Salle(int port, int nbJoueursTot, String password, int nbJetonsDep) {
        this.port = port;
        this.nbJoueursTot = nbJoueursTot;
        this.password = password;
        this.lstConnections = new HashMap<>();
        this.lstJoueurs = new ArrayList<>();
        this.nbJetonsDep = nbJetonsDep;
        this.salleAttente = Gestionnaire.creer("salleAttente");
        this.salleAttente.setSalle(this);
    }

    public Thread getServeur() {
        return new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is running on port " + port);

                while (this.lstConnections.size() < nbJoueursTot) {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader entreeTemp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    Thread gerant = new Thread(() -> {
                        while (true){
                            try {
                                String[] donnees = entreeTemp.readLine().split(":");
                                Joueur j = new Joueur(donnees[1], this.nbJetonsDep);

                                if (donnees[0].equals("C")) {
                                    j.setPorts(clientSocket);

                                    this.lstConnections.put(clientSocket,j);
                                    System.out.println(this.lstConnections);
                                    for (Joueur joueur : this.lstConnections.values()) {
                                        for (Joueur donneesJ : this.lstConnections.values())
                                            joueur.getSortie().println("C:" + donneesJ);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Je l'enleve : " + lstConnections.get(clientSocket) + " " + e);
                                Joueur jAEnlever = lstConnections.remove(clientSocket);
                                for (Joueur joueur : this.lstConnections.values()) {
                                    joueur.getSortie().println("D:" + jAEnlever);
                                }
                                break;
                            }
                        }
                    });
                    gerant.start();
                }

            /*while ( !partieGagne ) {
                Table table = new Table();
                table.jouer();
            }*/

                System.out.println("Game finish. Server is closing.");
			} catch (IOException e) {
                System.out.println("tkt");
            }
        });
    }

    public void connection(int port, String nomJ) {
        Thread gerant = new Thread(() -> {
			try {
				this.client = new Socket("di-715-14", port);
                BufferedReader entree = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                PrintWriter    sortie = new PrintWriter(this.client.getOutputStream(), true);

                Joueur moi = new Joueur(nomJ,this.nbJetonsDep);
                Platform.runLater(() -> salleAttente.actualiser());
                sortie.println("C:" + moi);

                // Boucle pour lire et afficher les messages du serveur en continu
                String messageFromServer;
                while ((messageFromServer = entree.readLine()) != null) {
                    String[] donnees = messageFromServer.split(":");
                    Joueur jATraiter = new Joueur(donnees[1],Integer.parseInt(donnees[2]));
                    if ( donnees[0].equals("C") ) {
                        boolean estPresent = false;
                        for ( Joueur j : lstJoueurs) {
                            if (j.getNomJoueur().equals(jATraiter.getNomJoueur())) {
                                estPresent = true;
                                break;
                            }
                        }
                        if ( !estPresent ) {
                            this.lstJoueurs.add(jATraiter);
                            Platform.runLater(() -> salleAttente.actualiser());
                        }
                    }
                    else if ( donnees[0].equals("D") ) {
                        lstJoueurs.removeIf(jAEnlever -> jAEnlever.getNomJoueur().equals(jATraiter.getNomJoueur()));
                        Platform.runLater(() -> salleAttente.actualiser());
                    }
                }
                System.out.println("c'est la fin");
			} catch (IOException ignored) { }
        });
        gerant.start();
        this.salleAttente.show();
    }

    public String getMotDePasse() { return this.password; }
    public int getNbJetonsDep() { return this.nbJetonsDep; }
    public int getNbJoueursTot() { return this.nbJoueursTot; }

    public ArrayList<Joueur> getJoueursEnLigne() { return this.lstJoueurs; }

    public AutoCloseable getClient() { return this.client; }
}
