package projet.bananaspoker.metier;


public class Carte {

	private char coul;
	private char valeur;
	private char signe;

	public Carte(char coul, char valeur, char signe) {
		this.coul = coul;
		this.valeur = valeur;
		this.signe = signe;
	}

	public char getCoul() {
		return coul;
	}

	public void setCoul(char coul) {
		this.coul = coul;
	}

	public char getValeur() {
		return valeur;
	}

	public void setValeur(char valeur) {
		this.valeur = valeur;
	}

	public char getSigne() {
		return signe;
	}

	public void setSigne(char signe) {
		this.signe = signe;
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Carte carte = (Carte) object;
		return coul == carte.coul && valeur == carte.valeur && signe == carte.signe;
	}

	public java.lang.String toString() {
		return "Carte{" +
				"coul=" + coul +
				", valeur=" + valeur +
				", signe=" + signe +
				"}\n";
	}
}
