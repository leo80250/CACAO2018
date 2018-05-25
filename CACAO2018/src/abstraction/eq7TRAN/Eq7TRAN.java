package abstraction.eq7TRAN;

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
	private Indicateur stockFeves;
	private Indicateur stockPoudre;
	private Indicateur stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;
	
	private Indicateur absenteisme;
	
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
		this.stockFeves = new Indicateur(this.getNom()+" a un stock de fèves de ", this, 0.0);
		this.stockPoudre = new Indicateur(this.getNom()+" a un stock de poudre de ", this, 0.0);
		this.stockTablettes = new Indicateur(this.getNom()+" a un stock de tablettes de ", this, 0.0);
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
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
		return achats;
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
	public Indicateur getStockFeves() {
		return stockFeves;
	}
	public void setStockFeves(Indicateur stockFeves) {
		this.stockFeves = stockFeves;
	}
	public Indicateur getStockPoudre() {
		return stockPoudre;
	}
	public void setStockPoudre(Indicateur stockPoudre) {
		this.stockPoudre = stockPoudre;
	}
	public Indicateur getStockTablettes() {
		return stockTablettes;
	}
	public void setStockTablettes(Indicateur stockTablettes) {
		this.stockTablettes = stockTablettes;
	}
	public Indicateur getSolde() {
		return solde;
	}
	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}
	public Journal getJournal() {
		return journal;
	}
	public void setJournal(Journal journal) {
		this.journal = journal;
	}
	public Indicateur getAbsenteisme() {
		return absenteisme;
	}
	public void setAbsenteisme(Indicateur absenteisme) {
		this.absenteisme = absenteisme;
	}
	@Override
	public void sendCatalogue(ContratPoudre[] offres) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getCatalogue(IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ContratPoudre[] getDevis(ContratPoudre[] devis) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ContratPoudre[] sendReponseDevis(ContratPoudre[] devis, boolean reponse) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ContratPoudre[] getEchangeFinal(ContratPoudre[] contrat) {
		// TODO Auto-generated method stub
		return null;
	}
}
