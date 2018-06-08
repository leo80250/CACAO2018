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

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IAcheteurFeve, IVendeurChoco {
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
	private Indicateur efficacite;
	// Historique
	private Indicateur[] prixAchatFeves;
	private Indicateur[] prixVentePoudre;
	private Indicateur[] prixVenteTablettes;
	private Indicateur[] productionPoudreReelle;
	private Indicateur[] productionTablettesReelle;
	private Indicateur[] productionPoudreAttendue;
	private Indicateur[] productionTablettesAttendue;
	
	private ContratPoudre[] commandesEnCours;

	private int MOY_TAUX_EFFICACITE_EMPLOYES = 1;
	
	// en tonnes par 2 semaines
	private final int[] MOY_ACHAT_FEVES = {0, 1400, 3200};
	private final int[] MOY_ACHAT_POUDRE = {0, 0, 0};
	private final int[] MOY_VENTE_POUDRE = {0, 0, 1000};
	private final int[] MOY_VENTE_TABLETTE = {0, 1400, 2300};
	
	// en €/tonne
	private final int SUM_MOY_VENTE_POUDRE = MOY_VENTE_POUDRE[0]+MOY_VENTE_POUDRE[1]+MOY_VENTE_POUDRE[2];
	private final int SUM_MOY_VENTE_TABLETTES = MOY_VENTE_TABLETTE[0]+MOY_VENTE_TABLETTE[1]+MOY_VENTE_TABLETTE[2];
	
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
		this.prixVentePoudre = new Indicateur[3];
		this.prixVenteTablettes = new Indicateur[3];
		this.productionPoudreReelle = new Indicateur[3];
		this.productionTablettesReelle = new Indicateur[3];
		
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		this.absenteisme = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0);
		this.efficacite = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 1.0);
		
		for(int i = 0; i < 3; i++) {
			this.stockFeves[i] = new Indicateur(this.getNom()+" a un stock de fÃ¨ves de ", this, 0.0);
			this.stockPoudre[i] = new Indicateur(this.getNom()+" a un stock de poudre de ", this, 0.0);
			this.stockTablettes[i] = new Indicateur(this.getNom()+" a un stock de tablettes de ", this, 0.0);
			this.prixAchatFeves[i] = new Indicateur(this.getNom()+" a dernièrement acheté des fèves au prix de ", this, this.MOY_PRIX_ACHAT_FEVES[i]);
			this.prixVentePoudre[i] = new Indicateur(this.getNom()+" a dernièrement vendu de la poudre au prix de ", this, 0);
			this.prixVenteTablettes[i] = new Indicateur(this.getNom()+" a dernièrement vendu des tablettes au prix de ", this, 0);
			this.productionPoudreReelle[i] = new Indicateur(this.getNom()+" a dernièrement produit une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
			this.productionTablettesReelle[i] = new Indicateur(this.getNom()+" a dernièrement produit une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
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
	public Indicateur getEfficacite() {
		return this.efficacite;
	}
	public void setEfficacite(Indicateur efficacite) {
		this.efficacite = efficacite;
	}
	public Indicateur[] getPrixVenteTablettes() {
		return prixVenteTablettes;
	}
	public void setPrixVenteTablettes(Indicateur[] prixVenteTablettes) {
		this.prixVenteTablettes = prixVenteTablettes;
	}
	public Indicateur[] getProductionPoudreReelle() {
		return productionPoudreReelle;
	}
	public void setProductionPoudreReelle(Indicateur[] productionPoudreReelle) {
		this.productionPoudreReelle = productionPoudreReelle;
	}
	public void setProductionPoudreReelle(int productionPoudreReelle, int qualite) {
		Indicateur[] productionsReelle = this.getProductionPoudreReelle();
		productionsReelle[qualite].setValeur(this, productionPoudreReelle);
	}
	public Indicateur[] getProductionTablettesReelle() {
		return productionTablettesReelle;
	}
	public void setProductionTablettesReelle(Indicateur[] productionTablettesReelle) {
		this.productionTablettesReelle = productionTablettesReelle;
	}
	public void setProductionTablettesReelle(int productionTablettesReelle, int qualite) {
		Indicateur[] productionsReelle = this.getProductionTablettesReelle();
		productionsReelle[qualite].setValeur(this, productionTablettesReelle);
	}
	public Indicateur[] getProductionPoudreAttendue() {
		return productionPoudreAttendue;
	}
	public void setProductionPoudreAttendue(Indicateur[] productionPoudreAttendue) {
		this.productionPoudreAttendue = productionPoudreAttendue;
	}
	public void setProductionPoudreAttendue(int productionPoudreAttendue, int qualite) {
		Indicateur[] productionsAttendue = this.getProductionPoudreAttendue();
		productionsAttendue[qualite].setValeur(this, productionPoudreAttendue);
	}
	public Indicateur[] getProductionTablettesAttendue() {
		return productionTablettesAttendue;
	}
	public void setProductionTablettesAttendue(Indicateur[] productionTablettesAttendue) {
		this.productionTablettesAttendue = productionTablettesAttendue;
	}
	public void setProductionTablettesAttendue(int productionTablettesAttendue, int qualite) {
		Indicateur[] productionsAttendue = this.getProductionTablettesAttendue();
		productionsAttendue[qualite].setValeur(this, productionTablettesAttendue);
	}
	public Indicateur getProductionPoudreAttendue(int qualite) {
		return this.getProductionPoudreAttendue()[qualite];
	}
	public Indicateur getProductionTablettesAttendue(int qualite) {
		return this.getProductionPoudreAttendue()[qualite];
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
	public void calculateTauxEfficacite() {
		
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
	public void calculateProductionPoudreReelle(int qualite) {
		this.getProductionPoudreReelle()[qualite].setValeur(this,(1.0 - this.getAbsenteisme().getValeur())*this.getEfficacite().getValeur()*this.getProductionPoudreAttendue(qualite).getValeur());
	}
	public void calculateProductionTablettesReelle(int qualite) {
		this.getProductionTablettesReelle()[qualite].setValeur(this,(1.0 - this.getAbsenteisme().getValeur())*this.getEfficacite().getValeur()*this.getProductionTablettesAttendue(qualite).getValeur());
	}
	
	// LÃ©o Fargeas
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
	public Indicateur getStockPrevisionnel(int qualite) {
		int tonnes = 0;
		return absenteisme;
	}
	
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
			// Si on a pas la bonne quantitÃ© on refuse
			if(devis[i].getQuantite() > this.getStockPoudre()[qualite].getValeur()) {
				devis[i].setReponse(false);
			}
		}
		return devis;
	}
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		// est-ce qu'il a eu des probs pour la rÃ©alisation du contrat ?
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
	}
	@Override
	public ContratFeve[] getResultVentes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**Interface IMarcheFeve
	 * 
	 */
	
	public double getPrixMarche() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** Interface IVendeurChoco
	 * 
	 */
	
	//Joseph Bernard
	public GQte getStock() {
		return new GQte(0,0,0,(int)this.getStockTablette(0).getValeur(),(int)this.getStockTablette(1).getValeur(),(int)this.getStockTablette(2).getValeur());
	}
	
	//tableau de GPrix pour les trois qualites
	//public GPrix[] getPrix() {
		//GPrix[] array = new GPrix[3];
		//array[0] = new GPrix({0.0,Float.MAX_VALUE},this.estimatePrixVenteTablette(0));
		//array[1] = new GPrix({0.0,Float.MAX_VALUE},this.estimatePrixVenteTablette(1));
		//array[2] = new GPrix({0.0,Float.MAX_VALUE},this.estimatePrixVenteTablette(2));
		//return new GPrix[3];
	//}
	/*
	public GQte[] getLivraison(GQte[] commandes) {
		int[] stock= {this.getStockTablette(0).getValeur(),this.getStockTablette(1).getValeur(),this.getStockTablette(2).getValeur()};
		int[] commande1= {commandes[0].getqTabletteBQ(),commandes[0].getqTabletteMQ(),commandes[0].getqTabletteHQ()};
		int[] commande2= {commandes[1].getqTabletteBQ(),commandes[1].getqTabletteMQ(),commandes[1].getqTabletteHQ()};
		
		int[] deliver1= {0,0,0};
		int[] deliver2= {0,0,0};
		
		for (int i=0;i<3;i++) {
			int stock_2;
			int stock_i=stock[i];
			int deliver_1=0;
			int deliver_2=0;
			while ((int)stock_i!=0) {
				stock_2=stock_i/2;
				if ((stock_2<=commande1[i])&&(stock_2<=commande2[i])) {
					commande1[i]-=stock_2;
					deliver_1+=stock_2;
					commande2[i]-=stock_2;
					deliver_2+=stock_2;
					stock_i=stock_2;
				}
			}
			deliver1[i]=deliver_1;
			deliver2[i]=deliver_2;
		}
		
		return {new GQte(0,0,0,deliver1[0],deliver1[1],deliver1[2]),new GQte(0,0,0,deliver2[0],deliver2[1],deliver2[2])};
	}*/
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		// TODO Auto-generated method stub
		
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
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendReponsePoudre(ContratPoudre[] devis) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat) {
		// TODO Auto-generated method stub
		return null;
	}
}
