package projet.bananaspoker.metier;


public class Carte implements Comparable{

	public final static char COEUR   = '♥';
	public final static char CARREAU = '♦';
	public final static char TREFLE  = '♣';
	public final static char PIQUE   = '♠';

	private char   couleur;
	private int    valeur;
	private String nom;

	public Carte(char couleur, int valeur, String nom) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.nom = nom;
	}

	public static Carte[] genererJeu()
	{
		Carte[] jeu = new Carte[52];
		int[] ensValeurs = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		String[] ensNomsValeurs = {"As","2","3","4","5","6","7","8","9","10","Valet","Dame","Roi"};
		char[] ensCouleurs = {COEUR,CARREAU,TREFLE,PIQUE};
		String[] ensNomsCouleurs = {"coeur","carreau","trefle","pique"};

		int nbCartes = 0;
		for (int cptCouleur = 0; cptCouleur < 4; cptCouleur++)
		{
			for (int cptValeur = 0; cptValeur < 13; cptValeur++)
			{
				int    val  = ensValeurs[cptValeur];
				char   coul = ensCouleurs[cptCouleur];
				String nom  = ensNomsValeurs[cptValeur] + " de " + ensNomsCouleurs[cptCouleur];
				jeu[nbCartes] = new Carte(coul, val, nom);
				nbCartes++;
			}
		}

		return jeu;
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
		int[] ensPoints = {13,1,2,3,4,5,6,7,8,9,10,11,12};
		Carte autreCarte = (Carte) o;
		return ensPoints[this.valeur-1] - ensPoints[autreCarte.getValeur()-1];
	}
}
