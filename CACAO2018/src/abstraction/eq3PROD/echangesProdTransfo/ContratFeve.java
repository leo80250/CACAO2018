package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.eq2PROD.acheteurFictifTRAN.acheteurFictifTRAN;
import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class ContratFeve {
	
	private IAcheteurFeve transformateur;
	private IVendeurFeve producteur;
	private int qualite;
	
	private int quantiteOffrePublique;
	private double prixOffrePublique;
	
	private int quantiteDemande;
	private double prixDemande;
	
	private int quantiteProposition;
	private double prixProposition;
	
	private boolean reponse;
	
	/**
	 * EXPLICATION DES ECHANGES
	 *  OffrePublique_ : Offre initiale du producteur sur le marche
	 *  Demande_ : Demande du transformateur au producteur
	 *  Proposition_ : Proposition du producteur au transformateur
	 */
	
		// Constructeurs //
	
	public ContratFeve(IAcheteurFeve transformateur, IVendeurFeve producteur, int qualite,
			int quantiteOffrePublique, int quantiteDemande, int quantiteProposition,
			double prixOffrePublique, double prixDemande, double prixProposition, boolean reponse) {
		
		this.transformateur = transformateur;
		this.producteur = producteur;
		this.qualite = qualite;
		
		this.quantiteOffrePublique = quantiteOffrePublique;
		this.quantiteDemande = quantiteDemande;
		this.quantiteProposition = quantiteProposition;
		
		this.prixOffrePublique = prixOffrePublique;
		this.prixDemande = prixDemande;
		this.prixProposition = prixProposition;
		
		this.reponse = reponse;
		
	}
	public ContratFeve(IAcheteurFeve transformateur, IVendeurFeve producteur, int qualite) {
		
		this.transformateur = transformateur;
		this.producteur = producteur;
		this.qualite = qualite;
		
		this.quantiteOffrePublique = 0;
		this.quantiteDemande = 0;
		this.quantiteProposition = 0;
		
		this.prixOffrePublique = 0;
		this.prixDemande = 0;
		this.prixProposition = 0;
		
		this.reponse = false;
	}
	public ContratFeve() {
		
		this.transformateur = null;
		this.producteur = null;
		this.qualite = 0;
		
		this.quantiteOffrePublique = 0;
		this.quantiteDemande = 0;
		this.quantiteProposition = 0;
		
		this.prixOffrePublique = 0;
		this.prixDemande = 0;
		this.prixProposition = 0;
		
		this.reponse = false;
	}
	
		// Getters //

	@Deprecated
	public ContratFeve(int qualite2, int quantite, double prixMinqb, IAcheteurFeve transformateur2,
			IVendeurFeve producteur2, boolean reponse2) {
		// TODO Auto-generated constructor stub
	}
	public IAcheteurFeve getTransformateur() {
		return this.transformateur;
	}
	public IVendeurFeve getProducteur() {
		return this.producteur;
	}
	public int getQualite() {
		return this.qualite;
	}

	public int getOffrePublique_Quantite() {
		return this.quantiteOffrePublique;
	}
	public int getDemande_Quantite() {
		return this.quantiteDemande;
	}
	public int getProposition_Quantite() {
		return this.quantiteProposition;
	}
	public double getOffrePublique_Prix() {
		return this.prixOffrePublique;
	}
	public double getDemande_Prix() {
		return this.prixDemande;
	}
	public double getProposition_Prix() {
		return this.prixProposition;
	}
	
	public boolean getReponse() {
		return this.reponse;
	}

		// Setters //
	
	public void setTransformateur(IAcheteurFeve transformateur) {
		this.transformateur = transformateur;
	}
	public void setProducteur(IVendeurFeve producteur) {
		this.producteur = producteur;
	}
	public void setQualite(int qualite) {
		this.qualite = qualite;
	}
	
	public void setOffrePublique_Quantite(int quantiteOffrePublique) {
		this.quantiteOffrePublique = quantiteOffrePublique;
	}
	public void setDemande_Quantite(int quantiteDemande) {
		this.quantiteDemande = quantiteDemande;
	}
	public void setProposition_Quantite(int quantiteProposition) {
		this.quantiteProposition = quantiteProposition;
	}
	public void setOffrePublique_Prix(double prixOffrePublique) {
		this.prixOffrePublique = prixOffrePublique;
	}
	public void setDemande_Prix(double prixDemande) {
		this.prixDemande = prixDemande;
	}
	public void setProposition_Prix(double prixProposition) {
		this.prixProposition = prixProposition;
	}
	
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
	
	@Override
	public String toString() {
		return "ContratFeve [transformateur=" + transformateur + ", producteur=" + producteur + ", qualite=" + qualite
				+ ", quantiteOffrePublique=" + quantiteOffrePublique + ", prixOffrePublique=" + prixOffrePublique
				+ ", quantiteDemande=" + quantiteDemande + ", prixDemande=" + prixDemande + ", quantiteProposition="
				+ quantiteProposition + ", prixProposition=" + prixProposition + ", reponse=" + reponse + "]";
	}
	@Deprecated
	public double getPrix() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Deprecated
	public int getQuantite() {
		// TODO Auto-generated method stub
		return 0;
	}
}
