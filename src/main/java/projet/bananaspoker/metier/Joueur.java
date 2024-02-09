package projet.bananaspoker.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Joueur {
    private String nomJoueur;
    private ArrayList<Carte> mainJoueur;
    private ArrayList<Carte> combinaisonJoueur;
    private int nbJetonsJoueur;
    private PrintWriter sortie;
    private BufferedReader entree;

    public Joueur(String nomJoueur, ArrayList<Carte> mainJoueur, int nbJetonsJoueur) {
        this.nomJoueur = nomJoueur;
        this.mainJoueur = mainJoueur;
        this.combinaisonJoueur = Combinaison.setCombinaisonJoueur(this);
        this.nbJetonsJoueur = nbJetonsJoueur;
    }

    public Joueur(String nomJoueur, int nbJetonsJoueur) {
        this.nomJoueur = nomJoueur;
        this.nbJetonsJoueur = nbJetonsJoueur;
        this.entree = null;
        this.sortie = null;
    }

    public void setPorts(Socket clientSocket) throws IOException {
        this.sortie = new PrintWriter(clientSocket.getOutputStream(), true);
        this.entree = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public ArrayList<Carte> getMainJoueur() {
        return mainJoueur;
    }

    public void setMainJoueur(ArrayList<Carte> mainJoueur) {
        this.mainJoueur = mainJoueur;
    }

    public void addCarteMainJoueur(Carte c) {
        if (this.mainJoueur.size() < 2) {
            this.mainJoueur.add(c);
        }
    }

    public int getNbJetonsJoueur() {
        return nbJetonsJoueur;
    }

    public void setNbJetonsJoueur(int nbJetonsJoueur) {
        this.nbJetonsJoueur = nbJetonsJoueur;
    }

    public int enleverJetons(int nbJetons) {
        if (this.nbJetonsJoueur - nbJetons > 0) {
            this.nbJetonsJoueur -= nbJetons;
            return nbJetons;
        } else if (this.nbJetonsJoueur - nbJetons == 0) {
            this.nbJetonsJoueur = 0;
            return 0;
        } else {
            this.nbJetonsJoueur = 0;
            return -1;
        }
    }

    public void ajouterJetons(int nbJetons) {
        this.nbJetonsJoueur += nbJetons;
    }

    public ArrayList<Carte> getCombinaisonJoueur() {
        return combinaisonJoueur;
    }

    public void setCombinaisonJoueur(ArrayList<Carte> combinaisonJoueur) {
        this.combinaisonJoueur = combinaisonJoueur;
    }

    public PrintWriter getSortie() {
        return this.sortie;
    }

    public BufferedReader getEntree() {
        return this.entree;
    }

    public String toString() {
        return this.nomJoueur + ":" + this.nbJetonsJoueur;
    }
}