package abstraction.eq7TRAN.echangeTRANTRAN;


public class ContratPoudre {
	// BQ = 0; MQ = 1; HQ = 2
	private int qualite;
	// en tonnes
	private int quantite;
	// en euros par tonne
	private double prix;
	private IAcheteurPoudre acheteur;
	private IVendeurPoudre vendeur;
	private boolean reponse;
	
	public ContratPoudre(int qualite, int quantite, double prix, IAcheteurPoudre acheteur,
			IVendeurPoudre vendeur, boolean reponse) {
		this.qualite = qualite;
		this.quantite = quantite;
		this.prix = prix;
		this.vendeur = vendeur;
		this.acheteur = acheteur;
		this.reponse = reponse;
	}
	public ContratPoudre() {
		this(0,0,0,null,null,false);
	}
	public ContratPoudre(ContratPoudre contrat) {
		this.qualite=contrat.getQualite();
		this.quantite=contrat.getQuantite();
		this.prix=contrat.getPrix();
		this.acheteur=contrat.getAcheteur();
		this.vendeur=contrat.getVendeur();
		this.reponse=contrat.getReponse();
	}
	
	public int getQualite() {
		return this.qualite;
	}
	public void setQualite(int qualite) {
		this.qualite = qualite;
	}
	public int getQuantite() {
		return this.quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getPrix() {
		return this.prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public IAcheteurPoudre getAcheteur() {
		return this.acheteur;
	}
	public void setAcheteur(IAcheteurPoudre acheteur) {
		this.acheteur = acheteur;
	}
	public IVendeurPoudre getVendeur() {
		return this.vendeur;
	}
	public void setVendeur(IVendeurPoudre vendeur) {
		this.vendeur = vendeur;
	}
	public boolean getReponse() {
		return this.reponse;
	}
	public boolean isReponse() {
		return this.reponse;
	}
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
	@Override
	public String toString() {
		return "ContratPoudre [qualite=" + qualite + ", quantite=" + quantite + ", prix=" + prix + ", acheteur="
				+ acheteur + ", vendeur=" + vendeur + ", reponse=" + reponse + "]";
	}
	
	public boolean equals(Object o) {
		ContratPoudre ob=(ContratPoudre)o;
		return o!=null
				&& o instanceof ContratPoudre
				&& ob.getQualite()==this.getQualite()
				&& ob.getAcheteur().equals(this.getAcheteur())
				&& ob.getVendeur().equals(this.getVendeur());
	}
	
}