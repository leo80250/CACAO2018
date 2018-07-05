package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.eq2PROD.acheteurFictifTRAN.acheteurFictifTRAN;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

/**
 * @author Claire
 */

public class ContratFeveV3 {
	
	private String nomTamponProducteur="";
	private IAcheteurFeveV4 transformateur;
	private IVendeurFeveV4 producteur;
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
	 *  
	 *  @Unites :
	 *  Quantites : tonne
	 *  Prix : €/tonne
	 */
	
		// Constructeurs //

	/**
	 * Version pour contrer le fait que dans le constructeur d'une equipe
	 * Tous les acteurs ne sont pas forcement initialises
	 * @param producteur le nom du producteur que retournerait sa methode getNom() si il existait
	 */

	public ContratFeveV3(IAcheteurFeveV4 transformateur, String producteur, int qualite) {
		this.transformateur = transformateur;
		this.qualite = qualite;

		this.quantiteOffrePublique = 0;
		this.quantiteDemande = 0;
		this.quantiteProposition = 0;

		this.prixOffrePublique = 0;
		this.prixDemande = 0;
		this.prixProposition = 0;

		this.reponse = false;

	}
	
	public ContratFeveV3(IAcheteurFeveV4 transformateur, IVendeurFeveV4 producteur, int qualite,
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
	public ContratFeveV3(IAcheteurFeveV4 transformateur, IVendeurFeveV4 producteur, int qualite) {
		
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
	public ContratFeveV3() {
		
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
	public ContratFeveV3(int qualite2, int quantite, double prixMinqb, IAcheteurFeveV4 transformateur2,
			IVendeurFeveV4 producteur2, boolean reponse2) {
		// TODO Auto-generated constructor stub
	}
	public IAcheteurFeveV4 getTransformateur() {
		return this.transformateur;
	}
	public IVendeurFeveV4 getProducteur() {
		if(!nomTamponProducteur.equals("")) {
			nomTamponProducteur="";
			producteur = (IVendeurFeveV4) Monde.LE_MONDE.getActeur(nomTamponProducteur);
		}
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
	
	public void setTransformateur(IAcheteurFeveV4 transformateur) {
		this.transformateur = transformateur;
	}
	public void setProducteur(IVendeurFeveV4 producteur) {
		nomTamponProducteur="";
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
		
		if (this.reponse) {
			return "Acheteur  : "+acheteur+"// Vendeur : "+vendeur+" // Quantite = "+this.getProposition_Quantite()+" // Qualite = "+strqualite[this.getQualite()]+" // Prix total"
			+this.getProposition_Quantite()*this.getProposition_Prix()+"€";
		}
		else if (this.getReponse()==false && this.getProposition_Quantite()!=0) {
			return "L'échange entre acheteur : "+acheteur+" et vendeur : "+vendeur+ " de feves "+strqualite[this.getQualite()]+" à été refusé";
		}
		else {
			return "Pas d'échanges entre "+acheteur+" et "+vendeur;
		}


}
	
	public String toString2() {
		return "[Transformateur : "+transformateur+" // producteur : "+producteur+ "// Qualite : "+qualite+
				"// QuantiteOffrePublique : "+quantiteOffrePublique+" // Quantite demande : "+quantiteDemande+
				" // QuantiteProposition : "+quantiteProposition+
				" PrixOffrePublique : "+prixOffrePublique+ " PrixDemande : "+prixDemande+ " // PrixProposition : "+prixProposition+"]";
		}
	}
