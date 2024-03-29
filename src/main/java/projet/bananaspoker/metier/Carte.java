package projet.bananaspoker.metier;


import java.util.ArrayList;
import java.util.List;

public class Carte implements Comparable {

	public final static char COEUR = '♥';
	public final static char CARREAU = '♦';
	public final static char TREFLE = '♣';
	public final static char PIQUE = '♠';

	private char couleur;
	private int valeur;
	private String nom;

	public Carte(char couleur, int valeur, String nom) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.nom = nom;
	}

	public static ArrayList<Carte> genererJeu() {
		ArrayList<Carte> jeu = new ArrayList<Carte>();
		int[] ensValeurs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		String[] ensNomsValeurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
		char[] ensCouleurs = {COEUR, CARREAU, TREFLE, PIQUE};
		String[] ensNomsCouleurs = {"coeur", "carreau", "trefle", "pique"};


		for (int cptCouleur = 0; cptCouleur < 4; cptCouleur++) {
			for (int cptValeur = 0; cptValeur < 13; cptValeur++) {
				int val = ensValeurs[cptValeur];
				char coul = ensCouleurs[cptCouleur];
				String nom = ensNomsValeurs[cptValeur] + " de " + ensNomsCouleurs[cptCouleur];
				jeu.add(new Carte(coul, val, nom));
			}
		}

		return jeu;
	}

	public static String getImageCarte (Carte aTrouver){

		String[] ensNomsValeurs = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

		if (aTrouver == null) return "Carte inexistante";

		char coul = ' ';
		switch (aTrouver.getCouleur()){
			case Carte.CARREAU -> coul = 'd';
			case Carte.PIQUE   -> coul = 's';
			case Carte.COEUR   -> coul = 'h';
			case Carte.TREFLE  -> coul = 'c';
			default ->  coul = 'p'; //probleme
		}
		if (coul == 'p' || coul == ' ') return "probleme de couleur";

		return "@../images/cartes/" + ensNomsValeurs[aTrouver.getValeur()] + coul + ".gif" ;
	}

	public char getCouleur() {
		return couleur;
	}

	public void setCouleur(char coul) {
		this.couleur = coul;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPointCarte() {
		int[] ensPoints = {14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		return ensPoints[this.getValeur() - 1];
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Carte carte = (Carte) object;
		return couleur == carte.couleur && valeur == carte.valeur;
	}

	public java.lang.String toString() {
		return this.nom;
	}

	@Override
	public int compareTo(Object o) {
		int[] ensPoints = {14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		Carte autreCarte = (Carte) o;
		return ensPoints[this.valeur - 1] - ensPoints[autreCarte.getValeur() - 1];
	}
}
