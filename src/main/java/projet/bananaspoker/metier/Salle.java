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
    private Thread client;

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

                    this.lstConnections.add( j );
                    for ( Joueur joueur : lstConnections )
                    {
                        for ( Joueur donneesJ : lstConnections )
                            joueur.getSortie().println("C:" + donneesJ);
                    }


                    Thread detecteurDeco = new Thread(() -> {
                        try {
                            String[] deco = entreeTemp.readLine().split(":");
                            if ( deco[0].equals("D") )
                                this.lstConnections.remove(j);
                            for ( Joueur joueur : lstConnections )
                            {
                                joueur.getSortie().println("D:" + j);
                            }
                        } catch (IOException e) { this.lstConnections.remove(j); }
                    });
                    detecteurDeco.start();
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
        this.client = new Thread(() -> {
			Socket socket = null;
			try {
				socket = new Socket("c-di-722-13", port);
                BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter    sortie = new PrintWriter(socket.getOutputStream(), true);

                Joueur moi = new Joueur(nomJ,this.nbJetonsDep);
                Platform.runLater(() -> salleAttente.actualiser());
                sortie.println(moi);

                // Boucle pour lire et afficher les messages du serveur en continu
                String messageFromServer;
                while ((messageFromServer = entree.readLine()) != null) {
                    String[] donnees = messageFromServer.split(":");
                    Joueur jATraiter = new Joueur(donnees[1],Integer.parseInt(donnees[2]));
                    if ( donnees[0].equals("C") ) {
                        boolean estPresent = false;
                        for ( Joueur j : lstConnections) {
                            if ( j.getNomJoueur().equals(jATraiter.getNomJoueur()))
                                estPresent = true;
                        }
                        if ( !estPresent ) {
                            this.lstConnections.add(jATraiter);
                            Platform.runLater(() -> salleAttente.actualiser());
                        }
                    }
                    else if ( donnees[0].equals("D") ) {
                        lstConnections.removeIf(jAEnlever -> jAEnlever.getNomJoueur().equals(jATraiter.getNomJoueur()));
                        Platform.runLater(() -> salleAttente.actualiser());
                    }
                }
			} catch (IOException e) {
                System.out.println(e);
			}
        });
        client.start();
        this.salleAttente.show();
    }

    public void deconnection(String nomJ) {
        for ( Joueur j : lstConnections )
            if ( j.getNomJoueur().equals(nomJ) )
                j.getSortie().println("D:" + j);
        this.client.interrupt();
    }


    public String getMotDePasse() { return this.password; }
    public int getNbJetonsDep() { return this.nbJetonsDep; }
    public int getNbJoueursTot() { return this.nbJoueursTot; }

    public ArrayList<Joueur> getConnections() { return this.lstConnections; }
}
