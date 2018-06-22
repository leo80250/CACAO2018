package abstraction.eq5TRAN.appeldOffre;

public class DemandeAO {
    public double quantite;
    public int qualite;

    /**
     * Les qualités de chocolat sont des int de 1 à 6 pour les différentes qualités, dans l'ordre habituel
     **/

    public DemandeAO(double quantite, int qualite) {
        if ((quantite > 0) && (qualite >= 1) && (qualite <= 6)) {
            this.qualite = qualite;
            this.quantite = quantite;
        }
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        if (quantite > 0) {
            this.quantite = quantite;
        }
    }

    public int getQualite() {
        return qualite;
    }

    public void setQualite(int qualite) {
        if ((qualite >= 1) && (qualite <= 6)) {
            this.qualite = qualite;
        }
    }
}