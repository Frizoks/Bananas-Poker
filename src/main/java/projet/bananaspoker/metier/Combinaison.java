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
        return estQuinteFlushRoyale(main);
    }

    private static boolean estQuinteFlushRoyale(ArrayList<Carte> ensCartes)
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : ensCartes) {
            valeursCartes.add(carte.getPointCarte());
        }
        Collections.sort(valeursCartes);

        return estQuinteFlush() && valeursCartes.get(valeursCartes.size()-1).equals(14);
    }

    private static boolean estQuinteFlush()
    {
        return (estCouleur() && nbCouleur(Combinaison.couleurCarte) == 5) && estQuinte();
    }

    private static boolean estQuinteFlush(ArrayList<Carte> ensCartes)
    {
        return (estCouleur() && nbCouleur(Combinaison.couleurCarte) == 5) && estQuinte(ensCartes);
    }

    private static boolean estQuinte()
    {
        return estQuinte(main);
    }
    private static boolean estQuinte(ArrayList<Carte> ensCartes)
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : ensCartes) {
            valeursCartes.add(carte.getPointCarte());
        }
        Collections.sort(valeursCartes);

        int cptQuinte = 0;
        for (int cpt = 0; cpt < valeursCartes.size() - 1; cpt++)
        {
            if (valeursCartes.get(cpt).equals(valeursCartes.get(cpt + 1) - 1))
            {
                cptQuinte++;
            }
        }
        return cptQuinte >= 4;
    }



    private static boolean estCouleur()
    {
        // Premier test si il y a une couleur, renseigné ici un char différent de toutes couleurs disponibles
        return estCouleur('z', main);
    }
    private static boolean estCouleur(char autreCoul)
    {
        return estCouleur(autreCoul, main);
    }
    private static boolean estCouleur(ArrayList<Carte> ensCartes)
    {
        return estCouleur('z', ensCartes);
    }

    private static boolean estCouleur(char autrecoul, ArrayList<Carte> ensCartes)
    {
        // autrecoul sert à savoir si il y a une autre combinaison de couleurs dans la main
        main = ensCartes;
        for (int cpt1 = 0; cpt1 < ensCartes.size()-1; cpt1++)
        {
            for (int cpt2 = cpt1+1; cpt2 < ensCartes.size(); cpt2++)
            {
                if ( (ensCartes.get(cpt1).getCouleur() == ensCartes.get(cpt2).getCouleur()) && ensCartes.get(cpt1).getCouleur() != autrecoul )
                {
                    Combinaison.couleurCarte = ensCartes.get(cpt1).getCouleur();
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
        return estPaire(main, 50);
    }
    private static boolean estPaire(ArrayList<Carte> ensCartes)
    {
        // Premier test si il y a une couleur, renseigné ici un char différent de toutes couleurs disponibles
        return estPaire(ensCartes, 50);
    }
    private static boolean estPaire (int autreValeur)
    {
        return estPaire(main, autreValeur);
    }
    private static boolean estPaire(ArrayList<Carte> ensCartes, int autreValeur)
    {
        // autrecoul sert à savoir si il y a une autre combinaison de couleurs dans la main

        for (int cpt1 = 0; cpt1 < ensCartes.size()-1; cpt1++)
        {
            for (int cpt2 = cpt1+1; cpt2 < ensCartes.size(); cpt2++)
            {
                if ( (ensCartes.get(cpt1).getValeur() == ensCartes.get(cpt2).getValeur()) && ensCartes.get(cpt1).getValeur() != autreValeur )
                {
                    Combinaison.valeurCarte = ensCartes.get(cpt1).getValeur();
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

    private static int carteHaute() {
        return carteHaute(main);
    }
    private static int carteHaute(ArrayList<Carte> ensCartes)
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : ensCartes)
        {
            valeursCartes.add(carte.getValeur());
        }
        Collections.sort(valeursCartes);

        return valeursCartes.get(valeursCartes.size() - 1);
    }

    private static int carteHautePaire(ArrayList<Carte> ensCartes)
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();
        for (Carte carte : ensCartes)
        {
            if (carte.getValeur() == valeurCarte)
                valeursCartes.add(carte.getValeur());
        }
        Collections.sort(valeursCartes);

        return valeursCartes.get(valeursCartes.size() - 1);
    }

    private static int getAcolyte(ArrayList<Carte> ensCartes)
    {
        ArrayList<Integer> valeursCartes = new ArrayList<>();

        for (Carte carte : ensCartes)
        {
            if (carte.getValeur() != valeurCarte)
                valeursCartes.add(carte.getValeur());
        }
        Collections.sort(valeursCartes);

        return valeursCartes.get(valeursCartes.size() - 1);
    }

    public static ArrayList<Carte> determineCombinaisonJoueur(Joueur j)
    {
		//combJoueur.add(Table.getJeuTable());
		ArrayList<Carte> combJoueur = new ArrayList<>(j.getMainJoueur());

        if (estQuinteFlushRoyale(combJoueur))

            if (estQuinte(combJoueur))
            {
                return null; /* A changer */
            }

        return combJoueur;
    }

    public static Joueur quiGagne (ArrayList<Joueur> lstJoueur)
    {
		ArrayList<Carte> pireCombinaisonPoker = new ArrayList<>();
		pireCombinaisonPoker.add(new Carte ('♥',2, "2 de coeur"));
		pireCombinaisonPoker.add(new Carte ('♦',3, "3 de carreau"));
		pireCombinaisonPoker.add(new Carte ('♥',5, "5 de coeur"));
		pireCombinaisonPoker.add(new Carte ('♣',6, "6 de trefle"));
		pireCombinaisonPoker.add(new Carte ('♦',7, "7 de carreau"));

		Joueur gagnant = new Joueur("@#~¹[{#ħn€ßł¢æ",1);
		gagnant.setCombinaisonJoueur(pireCombinaisonPoker);

		for (int cpt = 0; cpt < lstJoueur.size(); cpt++)
		{
			String res = departageJoueurs(gagnant, lstJoueur.get(cpt));

			if (res.equals(lstJoueur.get(cpt).getNomJoueur()))
				gagnant = lstJoueur.get(cpt);
		}
		if (gagnant.getNomJoueur().equals("@#~¹[{#ħn€ßł¢æ"))
			return null;
		return gagnant;
    }

    private static String departageJoueurs(Joueur j1, Joueur j2)
    {
        ArrayList<Carte> combJ1 = j1.getCombinaisonJoueur();
        ArrayList<Carte> combJ2 = j2.getCombinaisonJoueur();

        //quinte flush royale
        if (estQuinteFlushRoyale(combJ1) && !estQuinteFlushRoyale(combJ2))
            return j1.getNomJoueur();
        if (estQuinteFlushRoyale(combJ2) && !estQuinteFlushRoyale(combJ1))
            return j2.getNomJoueur();
        if (estQuinteFlushRoyale(combJ1) &&  estQuinteFlushRoyale(combJ2))
            return "Egalite";

        //quinte flush
        if (estQuinteFlush(combJ1) && !estQuinteFlush(combJ2))
            return j1.getNomJoueur();
        if (estQuinteFlush(combJ2) && !estQuinteFlush(combJ1))
            return j2.getNomJoueur();
        if (estQuinteFlush(combJ1) &&  estQuinteFlush(combJ2))
            return departageCarteHaute(combJ1, combJ2, j1, j2);

        // carre (paire 4)
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 4) && !(estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 4) )
            return j1.getNomJoueur();
        if ( (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 4) && !(estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 4) )
            return j2.getNomJoueur();
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 4) &&  (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 4) )
            return departageCarteHauteCarre(j1, j2);

        // full (paire 3 + paire 2)
        int valP1J1 = 0;
        int valP2J1 = 0;
        int valP1J2 = 0;
        int valP2J2 = 0;

        if (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 3)
        {
            valP1J1 = valeurCarte;
            if (estPaire(combJ1,valP1J1) && nbCarteIdentique(valeurCarte) == 2)
            {
                valP2J1 = valeurCarte;
            }
        }
        if (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 3)
        {
            valP1J2 = valeurCarte;
            if (estPaire(combJ2,valP1J2) && nbCarteIdentique(valeurCarte) == 2)
            {
                valP2J2 = valeurCarte;
            }
        }
        if (valP1J1 != 0 && valP1J2 != 0 && valP2J1 != 0 && valP2J2 != 0)
        {
            if (valP1J1 > valP1J2)
                return j1.getNomJoueur();
            else if (valP1J2 > valP1J1)
                return j2.getNomJoueur();
            else if (valP2J1 > valP2J2)
                return j1.getNomJoueur();
            else if (valP2J2 > valP2J1)
                return j2.getNomJoueur();
            else
                return "Egalité";
        }

        // couleur 5
        if ( (estCouleur(combJ1) && nbCouleur(couleurCarte) == 5) && !(estCouleur(combJ2) && nbCouleur(couleurCarte) == 5) )
            return j1.getNomJoueur();
        if ( (estCouleur(combJ2) && nbCouleur(couleurCarte) == 5) && !(estCouleur(combJ1) && nbCouleur(couleurCarte) == 5) )
            return j2.getNomJoueur();
        if ( (estCouleur(combJ1) && nbCouleur(couleurCarte) == 5) &&  (estCouleur(combJ2) && nbCouleur(couleurCarte) == 5) )
            return departageCarteHaute(combJ1, combJ2, j1, j2);

        //quinte
        if (estQuinte(combJ1) && !estQuinte(combJ2))
            return j1.getNomJoueur();
        if (estQuinte(combJ2) && !estQuinte(combJ1))
            return j2.getNomJoueur();
        if (estQuinte(combJ1) &&  estQuinte(combJ2))
            return departageCarteHaute(combJ1, combJ2, j1, j2);

        // brelan (paire 3)
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 3) && !(estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 3) )
            return j1.getNomJoueur();
        if ( (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 3) && !(estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 3) )
            return j2.getNomJoueur();
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 3) &&  (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 3) )
            return departageCarteHaute(combJ1, combJ2, j1, j2);

        // 2 paires (paire 2 + paire 2)
        if (estPaire(combJ1) && estPaire(combJ1, valeurCarte) && estPaire(combJ2) && estPaire(combJ2, valeurCarte))
            return departageDoublePaire(combJ1, combJ2, j1, j2);

        // 1 paire (paire 2)
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 2) && !(estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 2) )
            return j1.getNomJoueur();
        if ( (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 2) && !(estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 2) )
            return j2.getNomJoueur();
        if ( (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 2) &&  (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 2) )
            return departageCarteHaute(combJ1, combJ2, j1, j2);

        return departageCarteHaute(combJ1, combJ2, j1, j2);
    }

    private static String departageCarteHaute(ArrayList<Carte> combJ1, ArrayList<Carte> combJ2, Joueur j1, Joueur j2)
    {
        if (!combJ1.isEmpty())
        {
            if (carteHaute(combJ1) > carteHaute(combJ2))
                return j1.getNomJoueur();
            else if (carteHaute(combJ1) < carteHaute(combJ2))
                return j2.getNomJoueur();
            else {
                for (int cpt = 0; cpt < combJ1.size(); cpt++)
                    if (combJ1.get(cpt).getValeur() == carteHaute(combJ1))
                        combJ1.remove(cpt);

                for (int cpt = 0; cpt < combJ2.size(); cpt++)
                    if (combJ2.get(cpt).getValeur() == carteHaute(combJ2))
                        combJ2.remove(cpt);

                return departageCarteHaute(combJ1, combJ2, j1, j2);
            }
        }
        else
        {
            return "Egalité";
        }
    }

    private static String departageCarteHauteCarre(Joueur j1, Joueur j2)
    {
        ArrayList<Carte> combJ1 = j1.getCombinaisonJoueur();
        ArrayList<Carte> combJ2 = j2.getCombinaisonJoueur();

        if (carteHautePaire(combJ1) > carteHautePaire(combJ2))
            return j1.getNomJoueur();
        else if (carteHautePaire(combJ2) > carteHautePaire(combJ1))
            return j2.getNomJoueur();
            // "Si les deux joueurs ont le même carré, le pot est remis à celui qui a la cinquième carte la plus haute, appelée aussi acolyte"
        else if (getAcolyte(combJ1) > getAcolyte(combJ2))
            return j1.getNomJoueur();
        else if (getAcolyte(combJ2) > getAcolyte(combJ1))
            return j2.getNomJoueur();
        else
            return "Egalité";
    }

    private static String departageDoublePaire(ArrayList<Carte> combJ1, ArrayList<Carte> combJ2, Joueur j1, Joueur j2)
    {
        int valP1J1 = 0;
        int valP2J1 = 0;
        int valP1J2 = 0;
        int valP2J2 = 0;

        if (estPaire(combJ1) && nbCarteIdentique(valeurCarte) == 2)
        {
            valP1J1 = valeurCarte;
            if (estPaire(combJ1,valP1J1) && nbCarteIdentique(valeurCarte) == 2)
            {
                valP2J1 = valeurCarte;
            }
        }
        if (estPaire(combJ2) && nbCarteIdentique(valeurCarte) == 2)
        {
            valP1J2 = valeurCarte;
            if (estPaire(combJ2,valP1J2) && nbCarteIdentique(valeurCarte) == 2)
            {
                valP2J2 = valeurCarte;
            }
        }
        if (valP1J1 != 0 && valP1J2 != 0 && valP2J1 != 0 && valP2J2 != 0)
        {
            if (valP1J1 > valP1J2 && valP1J1 > valP2J2)
                return j1.getNomJoueur() ;
            else if (valP1J2 > valP1J1 && valP1J2 > valP2J1)
                return j2.getNomJoueur();
            else if (valP2J1 > valP2J2 && valP2J1 > valP1J2)
                return j1.getNomJoueur();
            else if (valP2J2 > valP2J1 && valP2J2 > valP1J2)
                return j2.getNomJoueur();
        }
        return "Egalité";
    }
}
