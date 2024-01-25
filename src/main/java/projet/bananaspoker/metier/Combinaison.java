package projet.bananaspoker.metier;

public class Combinaison
{
    private Carte[] main;
    private char couleurCarte;
    public String getCombinaison (Carte[] main)
    {
        this.main = main;
        if (estCouleur())
            return "Couleur " + nbCouleur(this.couleurCarte);

        return "";
    }

    public boolean estCouleur()
    {
        // Premier test si il y a une couleur, renseigné ici un char différent de toutes couleurs disponibles
        return estCouleur('z');
    }
    public boolean estCouleur (char autrecoul)
    {
        // autrecoul sert à savoir si il y a une autre combinaison de couleurs dans la main

        // Faire conditions de vérification si ce n'est pas une autre combinaison

        for (int cpt1 = 0; cpt1 < this.main.length-1; cpt1++)
        {
            for (int cpt2 = cpt1+1; cpt2 < this.main.length; cpt2++)
            {
                if ( (this.main[cpt1].getCoul() == this.main[cpt2].getCoul()) && this.main[cpt1].getCoul() != autrecoul )
                {
                    this.couleurCarte = this.main[cpt1].getCoul();
                    return true;
                }
            }
        }
        return false;
    }

    private int nbCouleur(char couleurCarte)
    {
        int res = 0;
        for (Carte c : this.main)
        {
            if (c.getCoul() == couleurCarte)
                res++;
        }

        if ( estCouleur(couleurCarte) && res < nbCouleur(this.couleurCarte))
        // estCouleur change la variable couleurCarte, dans couleurCarte se trouve la couleur de l'autre paire
        {
            return nbCouleur(this.couleurCarte);
        }
        return res;
    }
}
