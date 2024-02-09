package projet.bananaspoker.metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        if (estCouleur() &&  nbCouleur(Combinaison.couleurCarte) > 5)
            return "Flush";

        return "Carte haute" + carteHaute();
    }

    public static ArrayList<Carte> setCombinaisonJoueur(Joueur j)
    {
        ArrayList<Carte> mainJoueur = new ArrayList<>(j.getMainJoueur());
        ArrayList<Carte> cartesTable = new ArrayList<>();/*Table.getCartesTable();*/
        ArrayList<Carte> combinaison = new ArrayList<>(7);

        // Combine la main du joueur et les cartes sur la table
        combinaison.addAll(mainJoueur);
        combinaison.addAll(cartesTable);

        // Trie les cartes dans l'ordre décroissant de valeur
        Collections.sort(combinaison, Collections.reverseOrder());

        // Teste chaque type de combinaison
        String res = getCombinaison(combinaison);
        if (res.equals("QuinteFlushRoyale"))
        {
            // Quinte flush royale
            combinaison = enleverDoublons(combinaison);
            return (ArrayList<Carte>) combinaison.subList(0, 5); // Retourne les 5 premières cartes pour la quinte flush royale
        }

        else if (res.equals("QuinteFlush"))
        {
            combinaison = enleverDoublons(combinaison);
            ArrayList<Carte> quinteFlush = new ArrayList<>(5);
            do
            {
                estCouleur(Combinaison.couleurCarte);
            }
            while (nbCarteIdentique(Combinaison.couleurCarte) < 5);

            for (int i = 0; i < combinaison.size() - 1; i++)
            {
                if (combinaison.get(i).getCouleur() == Combinaison.couleurCarte)
                    quinteFlush.add(combinaison.get(i));
            }

            return quinteFlush;
        }

        else if (res.equals("Quinte"))
        {
            ArrayList<Carte> quinte = new ArrayList<>(5);
            combinaison = enleverDoublons(combinaison);
            for (int i = 0; i < combinaison.size() - 4; i++)
            {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur() + 1 &&
                        combinaison.get(i + 1).getValeur() == combinaison.get(i + 2).getValeur() + 1 &&
                        combinaison.get(i + 2).getValeur() == combinaison.get(i + 3).getValeur() + 1 &&
                        combinaison.get(i + 3).getValeur() == combinaison.get(i + 4).getValeur() + 1)
                {
                    // Ajoute les cinq cartes de la quinte à la liste
                    quinte.addAll(combinaison.subList(i, i + 5));
                    return quinte;
                }
            }
        }

        else if (res.equals("Full"))
        {
            ArrayList<Carte> full = new ArrayList<>();

            // Chercher le brelan
            for (int i = 0; i < combinaison.size() - 2; i++)
            {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur() &&
                        combinaison.get(i).getValeur() == combinaison.get(i + 2).getValeur()) {
                    full.add(combinaison.remove(i));
                    full.add(combinaison.remove(i));
                    full.add(combinaison.remove(i));
                    break;
                }
            }

            // Chercher la paire
            for (int i = 0; i < combinaison.size() - 1; i++)
            {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur()) {
                    full.add(combinaison.get(i));
                    full.add(combinaison.get(i + 1));
                    break;
                }
            }
            return full;
        }

        else if (res.equals("Carré"))
        {
            for (int i = 0; i < combinaison.size() - 3; i++)
            {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur() &&
                        combinaison.get(i).getValeur() == combinaison.get(i + 2).getValeur() &&
                        combinaison.get(i).getValeur() == combinaison.get(i + 3).getValeur())
                {
                    ArrayList<Carte> carre = new ArrayList<>(5);
                    carre.add(combinaison.remove(i));
                    carre.add(combinaison.remove(i));
                    carre.add(combinaison.remove(i));
                    carre.add(combinaison.remove(i));
                    // Ajoute la dernière carte après le carré
                    carre.add(combinaison.get(0));
                    return carre;
                }
            }
        }

        else if (res.equals("Flush"))
        {
            ArrayList<Carte> flush = new ArrayList<>(5);
            do
            {
                estCouleur(Combinaison.couleurCarte);
            }
            while (nbCarteIdentique(Combinaison.couleurCarte) < 5);

            for (int i = 0; i < combinaison.size() - 1; i++)
            {
                if (combinaison.get(i).getCouleur() == Combinaison.couleurCarte)
                    flush.add(combinaison.get(i));
            }

            return flush;
        }

        else if (res.equals("Brelan"))
        {
            for (int i = 0; i < combinaison.size() - 2; i++) {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur() &&
                        combinaison.get(i).getValeur() == combinaison.get(i + 2).getValeur())
                {
                    ArrayList<Carte> brelan = new ArrayList<>(5);
                    brelan.add(combinaison.remove(i));
                    brelan.add(combinaison.remove(i));
                    brelan.add(combinaison.remove(i));
                    // Ajoute les deux dernières cartes après le brelan
                    brelan.addAll(combinaison.subList(0, 2));
                    return brelan;
                }
            }
        }
        else if (res.equals("DoublePaire"))
        {
            int nbPaires = 0;
            ArrayList<Carte> doublePaire = new ArrayList<>(5);

            for (int i = 0; i < combinaison.size() - 1; i++) {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur()) {
                    doublePaire.add(combinaison.remove(i));
                    doublePaire.add(combinaison.remove(i));
                    nbPaires++;
                    i++;

                    if (nbPaires == 2) {
                        // Ajoute la dernière carte restante après les deux paires
                        doublePaire.add(combinaison.get(0));
                        return doublePaire;
                    }
                }
            }
        }
        else if (res.equals("Paire"))
        {
            for (int i = 0; i < combinaison.size() - 1; i++)
            {
                if (combinaison.get(i).getValeur() == combinaison.get(i + 1).getValeur())
                {
                    ArrayList<Carte> paire = new ArrayList<>(5);
                    paire.add(combinaison.remove(i));
                    paire.add(combinaison.remove(i));
                    // Ajoute les trois dernières cartes après la paire détectée
                    paire.addAll(combinaison.subList(0, 5 - paire.size()));
                    return paire;
                }
            }
        }

        return (ArrayList<Carte>) combinaison.subList(0, 5); // Retourne les 5 premières cartes pour la carte haute
    }

    public static ArrayList<Carte> enleverDoublons(ArrayList<Carte> cartes)
    {
        Set<Integer> valeursUniques = new HashSet<>();
        ArrayList<Carte> cartesSansDoublons = new ArrayList<>();

        for (Carte carte : cartes)
        {
            if (valeursUniques.add(carte.getValeur()))
            {
                if (estCouleur(cartes) && nbCarteIdentique(couleurCarte) >= 5)
                    if (carte.getCouleur() != couleurCarte)
                        System.out.print("");

                cartesSansDoublons.add(carte);
            }
        }

        return cartesSansDoublons;
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
                return 5;  //full -> nbCarteIdentique Paire1 != nbCarteIdentique Paire2
            else
                return 6;  //double paire
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
        else
            return departageCarteHaute(combJ1, combJ2, j1, j2);
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
