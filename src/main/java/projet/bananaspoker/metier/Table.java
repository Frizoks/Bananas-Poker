package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<Carte>  pioche;
    private List<Carte>  jeuTable;

    private List<Joueur> joueurs;

    private int nbTours;


    public Table() {
        this.pioche   = Carte.genererJeu();
        this.jeuTable = new ArrayList<Carte>();

        this.joueurs  = new ArrayList<Joueur>();

        this.nbTours  = 0;
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
