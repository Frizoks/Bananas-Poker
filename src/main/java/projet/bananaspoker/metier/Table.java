package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private static int nbManches = 0;
    private static int blinde = 0;
    private Joueur premierJoueur = null;
    private List<Carte>  pioche;
    private List<Carte>  jeuTable;
    private List<Joueur> joueurs;
    private int nbTours;


    public Table(/*ArrayList<Joueur> joueurs*/) {
        this.pioche   = Carte.genererJeu();
        this.jeuTable = new ArrayList<Carte>();

        this.joueurs  = joueurs;

        this.nbTours  = 0;
        Table.nbManches++;
    }

    public void jouer() {
        while(this.joueurs.size() > 1) {
            if(nbManches == 1) {this.premierJoueur = this.joueurs.get(0);}
            else {this.premierJoueur = this.getJoueurSuivant(this.premierJoueur);}

            this.joueurs.get(this.joueurs.indexOf(premierJoueur)).enleverJetons(this.blinde);
            if(this.joueurs.indexOf(premierJoueur) < this.joueurs.size()) {this.joueurs.get(this.joueurs.indexOf(premierJoueur) + 1).enleverJetons(this.blinde);}
            else {this.joueurs.get(0).enleverJetons(this.blinde * 2);}



        }
    }

    public Joueur getJoueurSuivant(Joueur joueur) {
        if(this.joueurs.get(this.joueurs.size() - 1) == joueur) {return this.joueurs.get(0);}
        return this.joueurs.get(this.joueurs.indexOf(joueur) + 1);
    }


    /*
     ********************
     *Getters et setters*
     ********************
     */

    public List<Carte> getPioche() {
        return pioche;
    }
    public void setPioche(List<Carte> pioche) {
        this.pioche = pioche;
    }
    public void addPioche(Carte aAjouter) {
        this.pioche.add(aAjouter);
    }
    public void retirerDePioche(Carte aRetirer) {
        this.pioche.remove(aRetirer);
    }
    public Carte piocher() {
        return this.pioche.remove(0);
    }

    public List<Carte> getJeuTable() {
        return jeuTable;
    }

    public void setJeuTable(List<Carte> jeuTable) {
        this.jeuTable = jeuTable;
    }

    public void setJeuTable() {
        ArrayList<Carte> jeuTableTemp = new ArrayList<Carte>();
        for (int i = 0; i < 5; i++) {
            jeuTableTemp.add(this.pioche.get(0));
        }
        setJeuTable(jeuTableTemp);
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }
    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
    public void addJoueur(Joueur joueur) {
        this.joueurs.add(joueur);
    }

    public int getNbTours() {
        return nbTours;
    }
    public void setNbTours(int nbTours) {
        this.nbTours = nbTours;
    }
}
