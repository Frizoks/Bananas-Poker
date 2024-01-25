package projet.bananaspoker.metier;

public class Jeton {

    private int valeur;

    public Jeton(int val) {
        this.valeur = val;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Jeton jeton = (Jeton) object;
        return valeur == jeton.valeur;
    }

    public java.lang.String toString() {
        return "Jeton{" +
                "valeur=" + valeur +
                "}\n";
    }
}
