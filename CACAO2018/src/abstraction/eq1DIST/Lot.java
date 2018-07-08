package abstraction.eq1DIST;

public class Lot {
	private Type qualite;
	private int quantite;


	public Lot(int quantite, Type qualite) {
		this.quantite = quantite;
		this.qualite=qualite;
	}

	public int getQuantite() {
		return this.quantite;
	}
	
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	public Type getQualite() {
		return this.qualite;
	}
	
	public Type getType() {
		return this.qualite;
	}

}
