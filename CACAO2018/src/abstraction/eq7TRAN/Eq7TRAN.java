package abstraction.eq7TRAN;

import java.util.Arrays;

import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre {
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur[] stockFeves;
	private Indicateur[] stockPoudre;
	private Indicateur[] stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;
	
	private Indicateur absenteisme;
	
	private ContratPoudre[] cataloguePoudre;
	
	// en tonnes par 2 semaines
	private static final int MOY_ACHAT_FEVES_MQ = 1400;
	private static final int MOY_ACHAT_FEVES_HQ = 3200;
	private static final int MOY_VENTE_POUDRE_MQ = 0;
	private static final int MOY_VENTE_POUDRE_HQ = 1000;
	private static final int MOY_VENTE_TABLETTE_MQ = 1400;
	private static final int MOY_VENTE_TABLETTE_HQ = 2300;
	private static final int MOY_STOCK_POUDRE_MQ = 0;
	private static final int MOY_STOCK_POUDRE_HQ = 0;
	private static final int MOY_STOCK_TABLETTE_MQ = 0;
	private static final int MOY_STOCK_TABLETTE_HQ = 0;

	public Eq7TRAN(Monde monde, String nom) {
		this.nom = nom;
		this.achats = new Indicateur("Achat de "+this.getNom(), this, 0.0);
		this.ventes = new Indicateur("Vente de "+this.getNom(), this, 0.0);
		this.stockFeves = new Indicateur[3];
		this.stockPoudre = new Indicateur[3];
		this.stockTablettes = new Indicateur[3];
		this.cataloguePoudre = new ContratPoudre[3];
		for(int i = 0; i < 3; i++) {
			this.stockFeves[i] = new Indicateur(this.getNom()+" a un stock de fèves de ", this, 0.0);
			this.stockPoudre[i] = new Indicateur(this.getNom()+" a un stock de poudre de ", this, 0.0);
			this.stockTablettes[i] = new Indicateur(this.getNom()+" a un stock de tablettes de ", this, 0.0);
			this.cataloguePoudre[i] = new ContratPoudre();
		}
		
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		this.absenteisme = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0);
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
		this.getJournal().ajouter("Absenteisme de l'eq7 = " + this.getAbsenteisme().getValeur());
	}
	
	
	public void calculateAbsenteisme() {
		double oldAbsenteisme = this.getAbsenteisme().getValeur();
		double newAbsenteisme = this.getAbsenteisme().getValeur() + Math.pow(-1, Math.round(Math.random()))*0.5*Math.random();
		if(newAbsenteisme>0.15)
			this.getAbsenteisme().setValeur(this, oldAbsenteisme);
		else 
			this.getAbsenteisme().setValeur(this, newAbsenteisme);
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
	
	
	/////////////////////////////
	// METHODES VENDEUR POUDRE //
	/////////////////////////////
	
	public void sendCataloguePoudre(ContratPoudre[] offres) {
		this.cataloguePoudre = offres;
	}
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		return this.cataloguePoudre;
	}
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis) {
		// paramètres pour évaluer combien à combien on vend, notre stock etc...
		return devis;
	}
	public ContratPoudre[] sendReponseDevisPoudre(ContratPoudre[] devis) {
		// inutile ?
		return devis;
	}
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat) {
		// est-ce qu'il a eu des probs pour la réalisation du contrat ?
		return contrat;
	}
}
