package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

public class ContratFeve {
	
	private int qualite;
	private int quantite;
	private double prix;
	private IAcheteurFeve transformateur;
	private IVendeurFeve producteur;
	private boolean reponse;
	
	/**
	 * Constructeurs d'un Contrat de vente de feves
	 * @param qualite la qualite des feves du contrat (0 = basse, 1 = moyenne, 2 = haute)
	 * @param quantite la quantite de feves du contrat (en tonne)
	 * @param prix le prix du contrat (a la tonne)
	 * @param transformateur le transformateur qui achete les feves
	 * @param producteur le producteur qui vend les feves
	 * @param reponse la reponse confirmant ou infirmant le contrat
	 */
	public ContratFeve(int qualite, int quantite, double prix, IAcheteurFeve transformateur, IVendeurFeve producteur, boolean reponse) {
		this.qualite = qualite;
		this.quantite = quantite;
		this.prix = prix;
		this.transformateur = transformateur;
		this.producteur = producteur;
		this.reponse = reponse;
	}
	public ContratFeve() {
		this.qualite = 0;
		this.quantite = 0;
		this.prix = 0.0;
		this.transformateur = null;
		this.producteur = null;
		this.reponse = false;
	}
	
	/**
	 * Getes et Seters des differentes variables
	 */
	
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
	
	public IAcheteurFeve getTransformateur() {
		return this.transformateur;
	}
	public void setTransformateur(IAcheteurFeve transformateur) {
		this.transformateur = transformateur;
	}
	
	public IVendeurFeve getProducteur() {
		return this.producteur;
	}
	public void setProducteur(IVendeurFeve producteur) {
		this.producteur = producteur;
	}
	
	public boolean getReponse() {
		return this.reponse;
	}
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
	
	public String toString() {
		String [] strqualite = {"basse", "moyenne", "haute"};
		String rep = "";
		if (this.reponse) {
			rep += "\nOffre acceptée";
		}
		String acheteur = "";
		if(this.transformateur != null) {
			acheteur += ((Acteur)(this.transformateur)).getNom();
		}
		String vendeur = "";
		if(this.producteur != null) {
			vendeur += ((Acteur)(this.producteur)).getNom();
		}
		
		return "Contrat : "+this.quantite+" tonnes de feve de "+strqualite[this.qualite]+" qualité, à "+this.prix
				+"€ la tonne, soit un total de "+this.quantite*this.prix+"€.\nAcheteur : "+acheteur+" | Vendeur : "+vendeur+rep;
	}
}
