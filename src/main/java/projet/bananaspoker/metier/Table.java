package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private Salle salle;

    private static int nbManches = 0;
    private int blinde;
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
        //for(Joueur j : joueursQuiJoue) {j.}
        //for(Joueur j : joueursQuiJoue) {j.}
        ArrayList<Joueur> allIn = new ArrayList<Joueur>();

        //debut de la manche et repartition de la blinde
        ArrayList<Integer> mises = new ArrayList<Integer>();
        mises.add(this.blinde);
        mises.add(this.blinde * 2);
        for(int i = 2; i < joueursQuiJoue.size(); i++) {mises.add(0);}

        while(joueursQuiJoue.size() > 1/* || this.jeuTable.size() <= 5*/)
        {
            int indJoueur = 2;
            while(!touteMiseEgale(mises))
            {
                int indice = 0;
                int mise = 0;//this.salle.demanderMise(joueursQuiJoue.get(indice), maxiAl(mises) - mises.get(indice));
                switch(mise)
                {
                    case -1 : joueursQuiJoue.remove(indice);
                              mises.remove(indice);
                              break;
                    case 0  : break;
                    default : if(joueursQuiJoue.get(indice).enleverJetons(mise) <= 0) {
                                  allIn.add(joueursQuiJoue.remove(indice));
                              }
                              mises.set(indice, mises.get(indice) + mise);
                }
                if(mise != -1) {indJoueur++;}
                indJoueur = indJoueur % joueursQuiJoue.size();
            }
            if(this.jeuTable.size() < 5) {
                this.jeuTable.add(this.pioche.get((int)(Math.random() * this.pioche.size())));
                //this.salle.envoyerCarte(this.jeuTable.get(this.jeuTable.size() - 1));
            }
            else if(this.jeuTable.size() == 5) {break;}

        }

        joueurs.add(joueurs.remove(0));
    }

    public boolean touteMiseEgale(ArrayList<Integer> mises) {
        int val = mises.get(0);
        for (Integer i : mises) {if(i != val) {return false;}}
        return true;
    }

    public int maxiAl(ArrayList<Integer> mises) {
        int val = mises.get(0);
        for (Integer i : mises) {if(i > val) {val = i;}}
        return val;
    }

    public int totalAl(ArrayList<Integer> mises) {
        int val = 0;
        for (Integer i : mises) {val += i;}
        return val;
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
