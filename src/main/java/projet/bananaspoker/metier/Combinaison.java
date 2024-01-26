package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.Collections;

public class Combinaison
{
    private static ArrayList<Carte> main;
    private static char             couleurCarte;
    private static int              valeurCarte;

    public static String getCombinaison (ArrayList<Carte> main)
    {
        Combinaison.main = main;
        if (estQuinteFlushRoyale())
            return "QuinteFlushRoyale";

        if (estQuinteFlush())
            return "QuinteFlush";
        if (estQuinte())
            return "Quinte";

        if (estPaire())
        {
            switch (nbCarteIdentique(Combinaison.valeurCarte))
            {
                case 2 : return "Paire";
                case 3 : return "Brelan";
                case 4 : return "Carré";
                case 5 : return "Full";
                case 6 : return "DoublePaire";
            }
        }

        if (estCouleur())
            return "Couleur " + nbCouleur(Combinaison.couleurCarte);

        return "Carte haute" + carteHaute();
    }

    private static boolean estQuinteFlushRoyale()
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : main) {
            valeursCartes.add(carte.getPointCarte());
        }
        Collections.sort(valeursCartes);

        return estQuinteFlush() && valeursCartes.get(valeursCartes.size()-1).equals(14);
    }

    private static boolean estQuinteFlush()
    {
        return (estCouleur() && nbCouleur(Combinaison.couleurCarte) == 5) && estQuinte();
    }

    private static boolean estQuinte()
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : main) {
            valeursCartes.add(carte.getPointCarte());
        }
        Collections.sort(valeursCartes);

        for (int cpt = 0; cpt < valeursCartes.size() - 1; cpt++)
        {
            if (!valeursCartes.get(cpt).equals(valeursCartes.get(cpt + 1) - 1))
                return false;
        }

        return true;
    }

    private static boolean estCouleur()
    {
        // Premier test si il y a une couleur, renseigné ici un char différent de toutes couleurs disponibles
        return estCouleur('z');
    }
    private static boolean estCouleur(char autrecoul)
    {
        // autrecoul sert à savoir si il y a une autre combinaison de couleurs dans la main

        for (int cpt1 = 0; cpt1 < main.size()-1; cpt1++)
        {
            for (int cpt2 = cpt1+1; cpt2 < main.size(); cpt2++)
            {
                if ( (main.get(cpt1).getCouleur() == main.get(cpt2).getCouleur()) && main.get(cpt1).getCouleur() != autrecoul )
                {
                    Combinaison.couleurCarte = main.get(cpt1).getCouleur();
                    return true;
                }
            }
        }
        return false;
    }

    private static int nbCouleur(char couleurCarte)
    {
        int res = 0;
        for (Carte c : main)
        {
            if (c.getCouleur() == couleurCarte)
                res++;
        }

        if ( estCouleur(couleurCarte) )
        {
            int val = 0;

            for (Carte c : main)
            {
                if (c.getCouleur() == Combinaison.couleurCarte)
                    val++;
            }

            if (res < val)
                return val;
        }

        return res;
    }

    private static boolean estPaire()
    {
        // Premier test si il y a une couleur, renseigné ici un char différent de toutes couleurs disponibles
        return estPaire(50);
    }
    private static boolean estPaire(int autreValeur)
    {
        // autrecoul sert à savoir si il y a une autre combinaison de couleurs dans la main

        for (int cpt1 = 0; cpt1 < main.size()-1; cpt1++)
        {
            for (int cpt2 = cpt1+1; cpt2 < main.size(); cpt2++)
            {
                if ( (main.get(cpt1).getValeur() == main.get(cpt2).getValeur()) && main.get(cpt1).getValeur() != autreValeur )
                {
                    Combinaison.valeurCarte = main.get(cpt1).getValeur();
                    return true;
                }
            }
        }
        return false;
    }

    private static int nbCarteIdentique (int valeurCarte)
    {
        int res = 0;
        for (Carte c : main)
        {
            if (c.getValeur() == valeurCarte)
                res++;
        }
        System.out.println(res);

        if ( estPaire(valeurCarte) )
        {
            int val = 0;

            for (Carte c : main)
            {
                if (c.getValeur() == Combinaison.valeurCarte)
                    val++;
            }

            if (res != val)
                return 5;
            else
                return 6;
        }

        return res;
    }

    private static int carteHaute()
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : main) {
            valeursCartes.add(carte.getValeur());
        }
        Collections.sort(valeursCartes);

        return valeursCartes.get(valeursCartes.size() - 1);
    }
}
