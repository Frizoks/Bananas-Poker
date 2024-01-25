package projet.bananaspoker.metier;

public class Joueur
{
    private String  nomJoueur;
    private Carte[] jeuJoueur;
    private int     nbJetonsJoueur;

    public Joueur(String nomJoueur, Carte[] jeuJoueur, int nbJetonsJoueur)
    {
        this.nomJoueur      = nomJoueur;
        this.jeuJoueur      = jeuJoueur;
        this.nbJetonsJoueur = nbJetonsJoueur;
    }
/*
    public Joueur(String nomJoueur, int nbJetonsJoueur)
    {
        this.nomJoueur      = nomJoueur;
        this.jeuJoueur      = méthode pour générer un jeu de carte
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

    public Carte[] getJeuJoueur()
    {
        return jeuJoueur;
    }

    public void setJeuJoueur(Carte[] jeuJoueur)
    {
        this.jeuJoueur = jeuJoueur;
    }

    public int getNbJetonsJoueur()
    {
        return nbJetonsJoueur;
    }

    public void setNbJetonsJoueur(int nbJetonsJoueur)
    {
        this.nbJetonsJoueur = nbJetonsJoueur;
    }
}
