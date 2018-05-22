package abstraction.eq7TRAN;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.echangeTRAN.ContratPoudre;
import abstraction.echangeTRAN.IAcheteurPoudre;
import abstraction.echangeTRAN.IVendeurPoudre;

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre {
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur stockFeves;
	private Indicateur stockPoudre;
	private Indicateur stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;

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
		int prod = (int) (Math.random()*1000);
	}
	@Override
	public ContratPoudre[] getResultVentes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendResultVentes(ContratPoudre[] contrats) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getOffresPubliques() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendOffresPubliques(ContratPoudre[] offres) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getDemandePrivee() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendDemandePrivee(ContratPoudre[] demandes) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getOffreFinale() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendOffreFinale(ContratPoudre[] contrats) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ContratPoudre[] getResultAchats() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendResultAchats(ContratPoudre[] contrats) {
		// TODO Auto-generated method stub
		
	}	
}
