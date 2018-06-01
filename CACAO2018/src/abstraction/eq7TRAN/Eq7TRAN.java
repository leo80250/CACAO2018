package abstraction.eq7TRAN;

import java.util.Arrays;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre; 
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IMarcheFeve, IAcheteurFeve, IVendeurChoco {
	private Indicateur achats;
	private Indicateur ventes;
	// 0 = BQ, 1 = MQ, 2 = HQ
	private Indicateur[] stockFeves;
	private Indicateur[] stockPoudre;
	private Indicateur[] stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;
	
	private Indicateur absenteisme;
	private Indicateur[] prixAchatFeves;
	private Indicateur[] prixVenteFeves;
	private ContratPoudre[] commandesEnCours;
	
	// en tonnes par 2 semaines
	private final int[] MOY_ACHAT_FEVES = {0, 1400, 3200};
	private final int[] MOY_ACHAT_POUDRE = {0, 0, 0};
	private final int[] MOY_VENTE_POUDRE = {0, 0, 1000};
	private final int[] MOY_VENTE_TABLETTE = {0, 1400, 2300};
	
	// en €/tonne
	private final double[] MOY_PRIX_ACHAT_FEVES = {1800, 2100, 2500};
	
	private final double MOY_PRIX_FRAIS_ACHAT_FEVES = 0;
	private final double MOY_PRIX_FRAIS_VENTE_FEVES = 0;
	private final double MOY_MARGE_POUDRE = 0.2;
	private final double MOY_MARGE_TABLETTE = 0.2;

	public Eq7TRAN(Monde monde, String nom) {
		this.nom = nom;
		this.achats = new Indicateur("Achat de "+this.getNom(), this, 0.0);
		this.ventes = new Indicateur("Vente de "+this.getNom(), this, 0.0);
		this.stockFeves = new Indicateur[3];
		this.stockPoudre = new Indicateur[3];
		this.stockTablettes = new Indicateur[3];
		this.prixAchatFeves = new Indicateur[3];
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		this.absenteisme = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0);
		for(int i = 0; i < 3; i++) {
			this.stockFeves[i] = new Indicateur(this.getNom()+" a un stock de fèves de ", this, 0.0);
			this.stockPoudre[i] = new Indicateur(this.getNom()+" a un stock de poudre de ", this, 0.0);
			this.stockTablettes[i] = new Indicateur(this.getNom()+" a un stock de tablettes de ", this, 0.0);
			this.prixAchatFeves[i] = new Indicateur(this.getNom()+" a dernièrement acheté des fèves au prix de ", this, this.MOY_PRIX_ACHAT_FEVES[i]);
		}
		
		this.journal = new Journal("Journal de "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		Monde.LE_MONDE.ajouterIndicateur(this.achats);
		Monde.LE_MONDE.ajouterIndicateur(this.ventes);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);
		
	}
	public Eq7TRAN(Monde monde) {
		this(monde, "Eq7TRAN");
	}
	public Eq7TRAN() {
		this(Monde.LE_MONDE, "Eq7TRAN");
	}

	public String getNom() {
		return this.nom;
	}

	public void next() {
		this.calculateAbsenteisme();
		this.getJournal().ajouter("Absenteisme = " + this.getAbsenteisme().getValeur());
		//this.getJournal().ajouter("Estimation prix achat feves = " + this.estimatePrixAchatFeves(0));
		//this.getJournal().ajouter("Estimation prix vente poudre BQ = " + this.estimatePrixVentePoudre(0));
	}
	
	
	public Indicateur getAchats() {
		return this.achats;
	}
	public void setAchats(Indicateur achats) {
		this.achats = achats;
	}
	public Indicateur getVentes() {
		return ventes;
	}
	public void setVentes(Indicateur ventes) {
		this.ventes = ventes;
	}
	public Indicateur[] getStockFeves() {
		return this.stockFeves;
	}
	public void setStockFeves(Indicateur[] stockFeves) {
		this.stockFeves = stockFeves;
	}
	public Indicateur[] getStockPoudre() {
		return this.stockPoudre;
	}
	public void setStockPoudre(Indicateur[] stockPoudre) {
		this.stockPoudre = stockPoudre;
	}
	public Indicateur[] getStockTablettes() {
		return this.stockTablettes;
	}
	public void setStockTablettes(Indicateur[] stockTablettes) {
		this.stockTablettes = stockTablettes;
	}
	public Indicateur getSolde() {
		return this.solde;
	}
	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}
	public Journal getJournal() {
		return this.journal;
	}
	public void setJournal(Journal journal) {
		this.journal = journal;
	}
	public Indicateur getAbsenteisme() {
		return this.absenteisme;
	}
	public void setAbsenteisme(Indicateur absenteisme) {
		this.absenteisme = absenteisme;
	}
	
	/** calculateAbsenteisme
	 * @author boulardmaelle, leofargeas
	 * @return le taux d'absenteisme (entre 0 et 15%)
	 */
	
	public void calculateAbsenteisme() {
		double oldAbsenteisme = this.getAbsenteisme().getValeur();
		double newAbsenteisme = this.getAbsenteisme().getValeur() + Math.pow(-1, Math.round(Math.random()))*0.05*Math.random();
		if(newAbsenteisme < 0 || newAbsenteisme>0.15)
			this.getAbsenteisme().setValeur(this, oldAbsenteisme);
		else 
			this.getAbsenteisme().setValeur(this, newAbsenteisme);
	}
	
	public double estimateCoutTransformationPoudre(int qualite) {
		return 0;
	}
	public double estimateMargePoudre(int qualite) {
		return this.MOY_MARGE_POUDRE;
	}
	public double estimateCoutTransformationTablette(int qualite) {
		return 0;
	}
	public double estimateMargeTablette(int qualite) {
		return this.MOY_MARGE_TABLETTE;
	}
	public double estimatePrixAchatFeves(int qualite) {
		return this.prixAchatFeves[qualite].getValeur();
	}	
	public double estimatePrixVentePoudre(int qualite) {
		return (estimatePrixAchatFeves(qualite)+estimateCoutTransformationPoudre(qualite))*(1+estimateMargePoudre(qualite));
	}
	public double estimatePrixVenteTablette(int qualite) {
		return (estimatePrixAchatFeves(qualite)+estimateCoutTransformationTablette(qualite))*(1+estimateMargeTablette(qualite));
	}
	
	//Joseph Bernard
	
	//////////////////////////////////////
	// METHODES VENDEUR POUDRE&CHOCOLAT //
	/////////////////////////////////////
	
	// Joseph Bernard
	public static int nb_employes=100; //a modifier
	// On considere que nb_employes permettent d'assurer la totalite de la production
	int sum_moy_vente_poudre=MOY_VENTE_POUDRE[0]+MOY_VENTE_POUDRE[1]+MOY_VENTE_POUDRE[2];
	int sum_moy_vente_tablette=MOY_VENTE_TABLETTE[0]+MOY_VENTE_TABLETTE[1]+MOY_VENTE_TABLETTE[2];
	

	public int getProductionPoudre(int qualite) {
		return ((int)(1.0 - this.getAbsenteisme().getValeur()))*sum_moy_vente_poudre/(sum_moy_vente_poudre+sum_moy_vente_tablette);
	}
	
	public int getProductionTablette(int qualite) {
		return ((int)(1.0 - this.getAbsenteisme().getValeur()))*sum_moy_vente_tablette/(sum_moy_vente_poudre+sum_moy_vente_tablette);
	}
	
	// Léo Fargeas
	public Indicateur getStockFeves(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockFeves()[qualite];
	}
	public Indicateur getStockPoudre(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockPoudre()[qualite];
	}
	public Indicateur getStockTablette(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null; 
		return this.getStockTablettes()[qualite];
	}
	/*public Indicateur getStockPrevisionnel(int qualite) {
		return 0;
	}*/
	
	/////////////////////////////
	// METHODES VENDEUR POUDRE //
	/////////////////////////////
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		return new ContratPoudre[0];
	}
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		int n = devis.length;
		for(int i = 0; i<n; i++) {
			int qualite = devis[i].getQualite();
			// Si on a pas la bonne quantité on refuse
			if(devis[i].getQuantite() > this.getStockPoudre()[qualite].getValeur()) {
				devis[i].setReponse(false);
			}
		}
		return devis;
	}
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		// est-ce qu'il a eu des probs pour la réalisation du contrat ?
		return contrat;
	}
	
	
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Interface IAcheteurFeve
	 * @author boulardmaelle, margauxgrand
	 */
	
	
	@Override
	public ContratFeve[] getDemandePrivee() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendContratFictif() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratFeve[] getResultVentes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**Interface IMarcheFeve
	 * 
	 */
	
	@Override
	public double getPrixMarche() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ContratFeve[] getContrat() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** Interface IVendeurChoco
	 * 
	 */
	
	@Override
	public GQte getStock() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GPrix getPrix() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GQte getLivraison(GQte[] commandes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		// TODO Auto-generated method stub
		
	}
}
