package abstraction.eq4TRAN;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import java.util.ArrayList;


import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;


public class Eq4TRAN implements IAcheteurFeve, Acteur, ITransformateur, IVendeurChoco {


	
	public Indicateur stockTabBQ ;
	public Indicateur stockTabMQ ;
	public Indicateur stockTabHQ ;
	public Indicateur stockChocMQ ;
	public Indicateur stockChocHQ ;
	public Indicateur prodTabBQ ;
	public Indicateur prodTabMQ ;
	public Indicateur prodTabHQ ;
	public Indicateur prodChocMQ ;
	public Indicateur prodChocHQ ;
	public Indicateur solde ; 
	public Journal JournalEq4 = new Journal("JournalEq4") ;
	private Vendeur vendeur;

	public Eq4TRAN() {

		stockTabBQ = new Indicateur("stockTabBQ",this,1000) ;
		stockTabMQ = new Indicateur("stockTabMQ",this,1000) ;
		stockTabHQ = new Indicateur("stockTabHQ",this,1000) ;
		stockChocMQ = new Indicateur("stockChocMQ",this,1000) ;
		stockChocHQ = new Indicateur("stockTabHQ",this,1000) ;
		prodTabBQ = new Indicateur("prodTabBQ",this,1000) ;
		prodTabMQ = new Indicateur("prodTabMQ",this,1000) ;
		prodTabHQ = new Indicateur("prodTabHQ",this,1000) ;
		prodChocMQ = new Indicateur("prodChocMQ",this,1000) ;
		prodChocHQ = new Indicateur("prodChocHQ",this,1000) ;
		solde = new Indicateur("solde",this,1000) ;
		vendeur = new Vendeur(0.0,stockChocMQ.getValeur(),stockChocHQ.getValeur(),stockTabBQ.getValeur(),stockTabMQ.getValeur(),stockTabHQ.getValeur());
}
		

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq4TRAN";
	}

	@Override
	public void next() {
		//Achat Poudre à un transformateur
		
		System.out.println("L'équipe 4 est présente");		
		
	}
	
	public void journalEq4() {
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabBQ.getValeur());
		JournalEq4.ajouter("Stock des tablettes Moyenne Qualité = "+stockTabMQ.getValeur());
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabHQ.getValeur());
		JournalEq4.ajouter("Stock des chocolats Moyenne Qualité = "+stockChocMQ.getValeur());
		JournalEq4.ajouter("Stock des chocolats Haute Qualité = "+stockChocHQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Basse Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Moyenne Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Haute Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production de chocolats Moyenne Qualité = "+prodChocMQ.getValeur());
		JournalEq4.ajouter("Production de chocolats Haute Qualité = "+prodChocHQ.getValeur());

	}

	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		// TODO Auto-generated method stub
		for (int i=0;i<offrePublique.length;i++) {
			System.out.println(offrePublique[i]);
		}
	}


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
		for (int i=0;i<offreFinale.length;i++) {
			System.out.println(offreFinale[i]);
		}
	}


	@Override
	public ContratFeve[] getResultVentes() {
		// TODO Auto-generated method stub
		return null;
	}

	public GQte getStock() {
		// TODO Auto-generated method stub
		return this.vendeur.getStock();
	}


	@Override
	public GPrix getPrix() {
		// TODO Auto-generated method stub
		return this.vendeur.getPrix();
	}


	@Override
	public ArrayList<GQte> getLivraison(ArrayList<GQte> commandes) {
		// TODO Auto-generated method stub
		return this.vendeur.getLivraison(commandes);
	}
}
