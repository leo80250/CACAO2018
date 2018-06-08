package abstraction.DISTFictif;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

/**
 * 
 * @author Etienne
 *
 */
public class DISTFictif implements Acteur, IAcheteurChoco{

	private Indicateur stockTabBQ ;
	private Indicateur stockTabMQ ;
	private Indicateur stockTabHQ ;
	private Indicateur stockChocBQ ;
	private Indicateur stockChocMQ ;
	private Indicateur stockChocHQ ;
	private Indicateur solde;
	
	public DISTFictif() {
		stockTabBQ = new Indicateur("stockTabBQ",this,1000) ;
		stockTabMQ = new Indicateur("stockTabMQ",this,1000) ;
		stockTabHQ = new Indicateur("stockTabHQ",this,1000) ;
		stockChocBQ = new Indicateur("stockChocBQ",this,1000);
		stockChocMQ = new Indicateur("stockChocMQ",this,1000) ;
		stockChocHQ = new Indicateur("stockTabHQ",this,1000) ;
		solde = new Indicateur("solde",this,1000000);
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "DISTFictif";
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	public void buy(GQte quantites, GPrix prix) {
		setStockChocBQ(getStockChocBQ().getValeur() + quantites.getqBonbonBQ());
		setSolde(prix.getPrixProduit(quantites.getqBonbonBQ(), 1));
		setStockChocMQ(getStockChocMQ().getValeur() + quantites.getqBonbonMQ());
		setSolde(prix.getPrixProduit(quantites.getqBonbonMQ(), 2));
		setStockChocHQ(getStockChocHQ().getValeur() + quantites.getqBonbonHQ());
		setSolde(prix.getPrixProduit(quantites.getqBonbonHQ(), 3));
		setStockTabBQ(getStockTabBQ().getValeur() + quantites.getqTabletteBQ());
		setSolde(prix.getPrixProduit(quantites.getqTabletteBQ(), 4));
		setStockTabMQ(getStockTabMQ().getValeur() + quantites.getqTabletteMQ());
		setSolde(prix.getPrixProduit(quantites.getqTabletteMQ(), 5));
		setStockTabHQ(getStockTabHQ().getValeur() + quantites.getqTabletteHQ());
		setSolde(prix.getPrixProduit(quantites.getqTabletteHQ(), 6));
		
	}
	
	public Indicateur getStockTabBQ() {
		return stockTabBQ;
	}

	public void setStockTabBQ(double stockTabBQ) {
		this.stockTabBQ.setValeur(this, stockTabBQ);
	}

	public Indicateur getStockTabMQ() {
		return stockTabMQ;
	}

	public void setStockTabMQ(double stockTabMQ) {
		this.stockTabMQ.setValeur(this, stockTabMQ);
	}

	public Indicateur getStockTabHQ() {
		return stockTabHQ;
	}

	public void setStockTabHQ(double stockTabHQ) {
		this.stockTabHQ.setValeur(this, stockTabHQ);
	}

	public Indicateur getStockChocBQ() {
		return stockChocBQ;
	}

	public void setStockChocBQ(double stockChocBQ) {
		this.stockChocBQ.setValeur(this, stockChocBQ);
	}

	public Indicateur getStockChocMQ() {
		return stockChocMQ;
	}

	public void setStockChocMQ(double stockChocMQ) {
		this.stockChocMQ.setValeur(this, stockChocMQ);
	}

	public Indicateur getStockChocHQ() {
		return stockChocHQ;
	}

	public void setStockChocHQ(double stockChocHQ) {
		this.stockChocHQ.setValeur(this, stockChocHQ);
	}

	public Indicateur getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde.setValeur(this, solde);
	}

	@Override
	public ArrayList<GQte> getCommande(ArrayList<GPrix> gPrix, ArrayList<GQte> stock) {
		// On commande 15% des stocks de chaque transformateur
		GQte commande1 = new GQte(stock.get(0).getqBonbonBQ()*0.15,stock.get(0).getqBonbonMQ()*0.15, stock.get(0).getqBonbonHQ()*0.15, stock.get(0).getqTabletteBQ()*0.15,stock.get(0).getqTabletteMQ()*0.15,stock.get(0).getqTabletteHQ()*0.15);
		GQte commande2 = new GQte(stock.get(1).getqBonbonBQ()*0.15,stock.get(1).getqBonbonMQ()*0.15, stock.get(1).getqBonbonHQ()*0.15, stock.get(1).getqTabletteBQ()*0.15,stock.get(1).getqTabletteMQ()*0.15,stock.get(1).getqTabletteHQ()*0.15);
		GQte commande3 = new GQte(stock.get(2).getqBonbonBQ()*0.15,stock.get(2).getqBonbonMQ()*0.15, stock.get(2).getqBonbonHQ()*0.15, stock.get(2).getqTabletteBQ()*0.15,stock.get(2).getqTabletteMQ()*0.15,stock.get(2).getqTabletteHQ()*0.15);
		ArrayList<GQte> commande = new ArrayList<GQte>();
		commande.add(commande1);
		commande.add(commande2);
		commande.add(commande3);
		return commande;
	}

	@Override
	public void livraison(GQte livraison, double d) {
		// TODO Auto-generated method stub
		
	}
	
	
}
