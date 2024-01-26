package projet.bananaspoker.metier;

import java.util.ArrayList;

public class Joueur
{
    private String           nomJoueur;
    private ArrayList<Carte> mainJoueur;
    private ArrayList<Carte> combinaisonJoueur;
    private int              nbJetonsJoueur;

    public Joueur(String nomJoueur, ArrayList<Carte> mainJoueur, int nbJetonsJoueur)
    {
        this.nomJoueur         = nomJoueur;
        this.mainJoueur        = mainJoueur;
        this.combinaisonJoueur = Combinaison.determineCombinaisonJoueur(this);
        this.nbJetonsJoueur    = nbJetonsJoueur;
    }
/*
    public Joueur(String nomJoueur, int nbJetonsJoueur)
    {
        this.nomJoueur      = nomJoueur;
        this.mainJoueur     = méthode pour générer un jeu de carte
        this.nbJetonsJoueur = nbJetonsJoueur;
    }
*/
    public String getNomJoueur()
    {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur)
    {
        this.nomJoueur = nomJoueur;
    }

    public ArrayList<Carte> getMainJoueur()
    {
        return mainJoueur;
    }

    public void setMainJoueur(ArrayList<Carte> jeuJoueur)
    {
        this.mainJoueur = mainJoueur;
    }

    public int getNbJetonsJoueur()
    {
        return nbJetonsJoueur;
    }

    public void setNbJetonsJoueur(int nbJetonsJoueur)
    {
        this.nbJetonsJoueur = nbJetonsJoueur;
    }

    public ArrayList<Carte> getCombinaisonJoueur() {
        return combinaisonJoueur;
    }

    public void setCombinaisonJoueur(ArrayList<Carte> combinaisonJoueur) {
        this.combinaisonJoueur = combinaisonJoueur;
    }
}
