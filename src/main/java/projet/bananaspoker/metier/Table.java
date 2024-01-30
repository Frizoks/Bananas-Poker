package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private Salle salle;

    private static int nbManches = 0;
    private int blinde;
    private Joueur premierJoueur = null;
    private ArrayList<Carte>  pioche;
    private ArrayList<Carte>  jeuTable;
    private ArrayList<Joueur> joueurs;
    private int nbTours;


    public Table(Salle salle, ArrayList<Joueur> joueurs) {
        this.salle = salle;

        this.pioche   = Carte.genererJeu();
        this.jeuTable = new ArrayList<Carte>();

        this.joueurs  = joueurs;

        this.nbTours  = 0;
        Table.nbManches++;

        int blindeTemp = salle.getNbJoueursTot() - this.joueurs.size();
        this.blinde = salle.getNbJetonsDep() * blindeTemp;
    }

    public void jouer() {
        ArrayList<Joueur> joueursQuiJoue = (ArrayList<Joueur>) this.joueurs.clone();

        //debut de la manche et repartition de la blinde
        joueursQuiJoue.get(0).enleverJetons(this.blinde);
        joueursQuiJoue.get(joueursQuiJoue.size()-1).enleverJetons(this.blinde * 2);

        while(joueursQuiJoue.size() > 1) {

            System.out.print("luc gros nul, t pas bo");

        }

        joueurs.add(joueurs.remove(0));
    }


    /*
     ********************
     *Getters et setters*
     ********************
     */

    public ArrayList<Carte> getPioche() {
        return pioche;
    }
    public void setPioche(ArrayList<Carte> pioche) {
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

    public ArrayList<Carte> getJeuTable() {
        return jeuTable;
    }

    public void setJeuTable(ArrayList<Carte> jeuTable) {
        this.jeuTable = jeuTable;
    }

    public void setJeuTable() {
        ArrayList<Carte> jeuTableTemp = new ArrayList<Carte>();
        for (int i = 0; i < 5; i++) {
            jeuTableTemp.add(this.pioche.get(0));
        }
        setJeuTable(jeuTableTemp);
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }
    public void setJoueurs(ArrayList<Joueur> joueurs) {
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
