package abstraction.fourni;

public class Entree {
	private Acteur auteur; // l'auteur de la modification
	private double valeur; // la nouvelle valeur
	private int etape;     // l'etape a laquelle le changement a eu lieu

	public Entree(Acteur auteur, int etape, double valeur)  {
		this.auteur = auteur;
		this.etape = etape;
		this.valeur = valeur;
	}

	public Acteur getAuteur() {
		return this.auteur;
	}

	public double getValeur() {
		return this.valeur;
	}

	public int getEtape() {
		return this.etape;
	}

	public String toString() {
		return this.getAuteur().getNom()+" a l'etape "+this.getEtape()+" --> "+this.getValeur();
	}
}