package projet.bananaspoker.metier;

import java.util.*;

public class Table {
    private Salle salle;

    private static int nbManches = 0;
    private int blinde;
    private ArrayList<Carte>  pioche;
    private ArrayList<Carte>  jeuTable;
    private ArrayList<Joueur> joueurs;


    public Table(Salle salle, ArrayList<Joueur> joueurs) {
        this.salle = salle;

        this.pioche   = Carte.genererJeu();
        Collections.shuffle(this.pioche);
        this.jeuTable = new ArrayList<Carte>();

        this.joueurs  = joueurs;
        Table.nbManches++;

        //int blindeTemp = salle.getNbJoueursTot() - this.joueurs.size();
        this.blinde = 5;//(salle.getNbJetonsDep() / 50) * blindeTemp;
    }

    public Table(ArrayList<Joueur> joueurs) {
        this(null, joueurs);
    }

    public void jouer() {
        Scanner scanner = new Scanner(System.in);
        int decalage = Table.nbManches % joueurs.size();
        decalage(joueurs, decalage);

        for(Joueur j : joueurs) {j.addCarteMainJoueur(this.pioche.remove(0));}
        for(Joueur j : joueurs) {j.addCarteMainJoueur(this.pioche.remove(0));}
        ArrayList<Joueur> allIn = new ArrayList<Joueur>();
        //this.salle.afficherCarteJoueurs();
        int indJoueur = 2;

        //debut de la manche et repartition des blindes
        ArrayList<Integer> mises = new ArrayList<Integer>();
        ArrayList<Integer> misesAllIn = new ArrayList<Integer>();
        mises.add(this.blinde);
        if(this.joueurs.get(0).enleverJetons(this.blinde) <= 0) {
            misesAllIn.add(mises.remove(0));
            allIn.add(joueurs.remove(0));

            indJoueur = 1;
            mises.add(this.blinde * 2);
            if(this.joueurs.get(1).enleverJetons(this.blinde*2) <= 0) {
                misesAllIn.add(mises.remove(0));
                allIn.add(joueurs.remove(0));
                indJoueur = 0;
            }
        }
        else {
            mises.add(this.blinde * 2);
            if(this.joueurs.get(1).enleverJetons(this.blinde*2) <= 0) {
                misesAllIn.add(mises.remove(1));
                allIn.add(joueurs.remove(1));
                indJoueur = 1;
            }
        }
        for(int i = 2; i < joueurs.size(); i++) {mises.add(0);}

        while(!(joueurs.size() <= 1 && allIn.size() == 0))
        {
            for (Joueur j : joueurs)
                System.out.println(j.getNomJoueur() + "--> " + j.getNbJetonsJoueur());
            while(!touteMiseEgale(mises))
            {
                System.out.println("C'est au tour de " + this.joueurs.get(indJoueur).getNomJoueur() + this.joueurs.get(indJoueur).getMainJoueur());
                System.out.println("Quelles somme voulez vous misez ? (" + this.joueurs.get(indJoueur).getNbJetonsJoueur() + ")");
                int mise = Integer.parseInt(scanner.nextLine());//0;//this.salle.demanderMise(joueurs.get(indJouer), maxiAl(mises) - mises.get(indJouer));
                switch(mise)
                {
                    case -1 : joueurs.remove(indJoueur);
                              mises.remove(indJoueur);
                              break;
                    case 0  : break;
                    default : if(joueurs.get(indJoueur).enleverJetons(mise) == 0) {
                                  mises.set(indJoueur, mises.get(indJoueur) + mise);
                                  allIn.add(joueurs.remove(indJoueur));
                                  misesAllIn.add(mises.remove(indJoueur));
                              }
                              else {
                                  mises.set(indJoueur, mises.get(indJoueur) + mise);
                              }

                }
                if(mise != -1) {indJoueur++;}
                indJoueur = indJoueur % joueurs.size();
            }
            if(this.jeuTable.size() < 5) {
                this.jeuTable.add(this.pioche.remove(0));
                //this.salle.afficherCarteJeu(this.jeuTable.get(this.jeuTable.size() - 1));
            }
            else if(this.jeuTable.size() == 5) {break;}
        }
        if(joueurs.size() == 1 && allIn.size() == 0) {
            joueurs.get(0).ajouterJetons(totalAl(mises));
        }
        else {
            ArrayList<Joueur> verifCombi = (ArrayList<Joueur>) joueurs.clone();
            for (Joueur j : allIn) {verifCombi.add(j);}
            Joueur j = Combinaison.quiGagne(verifCombi);
            if(allIn.contains(j)) {
                j.ajouterJetons(misesAllIn.get(allIn.indexOf(j)) * this.joueurs.size());
                for(Joueur joueur : joueurs) {
                    joueur.ajouterJetons(mises.get(joueurs.indexOf(joueur)) - misesAllIn.get(allIn.indexOf(j)));
                }
            }
            else {
                j.ajouterJetons(totalAl(mises));
            }
        }
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

    public void decalage(ArrayList<Joueur> al, int decalage) {
        for (int i = 0; i < decalage; i++) {
            al.add(al.remove(0));
        }
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

    public static void main(String[] args) {
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>((Arrays.asList(new Joueur("Antoine", 5000), new Joueur("Trystan", 5000), new Joueur("Mathys", 5000), new Joueur("Luc", 5000))));
        Table table = new Table(joueurs);
        table.jouer();
    }
}
