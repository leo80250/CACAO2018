package abstraction.eq6DIST;

import java.util.ArrayList;

import abstraction.eq1DIST.IVenteConso;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

 
public class Eq6DIST implements Acteur, IAcheteurChocoBis {
	private Indicateur stock_BBQ;
	private Indicateur stock_BMQ;
	private Indicateur stock_BHQ;
	private Indicateur stock_TBQ;
	private Indicateur stock_TMQ;
	private Indicateur stock_THQ;
	private Indicateur banque;
	private ArrayList<Integer> stock;
	private Journal journalEq6;
	public Eq6DIST() {
		//MarcheChoco MC = new MarcheChoco();
		//Monde.LE_MONDE.ajouterActeur(MC);
		this.banque= new Indicateur("Solde bancaire Eq6 : ",this, 120000);
		this.stock= new ArrayList<Integer>();this.stock.add(0);	this.stock.add(0);this.stock.add(0);this.stock.add(0);this.stock.add(0);this.stock.add(0);
		this.stock_BBQ= new Indicateur("Stock de bonbons BQ Eq6 :",this);
		this.stock_BMQ=new Indicateur("Stock de bonbons MQ Eq6 :",this);
		this.stock_BHQ=new Indicateur("Stock de bonbons HQ Eq6 :",this);
		this.stock_TBQ=new Indicateur("Stock de tablettes BQ Eq6 :",this);
		this.stock_TMQ=new Indicateur("Stock de tablettes MQ Eq6 :",this);
		this.stock_THQ=new Indicateur("Stock de tablettes HQ Eq6 :",this);
		Monde.LE_MONDE.ajouterIndicateur(this.banque);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BHQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_TBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_TMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_THQ);
		this.journalEq6= new Journal("Journal Equipe 6");
		Monde.LE_MONDE.ajouterJournal(journalEq6);
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub;
		return "Eq6DIST";
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub;
		this.stock_BBQ.setValeur(this, this.stock.get(0));
		this.stock_BMQ.setValeur(this, this.stock.get(1));
		this.stock_BHQ.setValeur(this, this.stock.get(2));
		this.stock_TBQ.setValeur(this, this.stock.get(3));
		this.stock_TMQ.setValeur(this, this.stock.get(4));
		this.stock_THQ.setValeur(this, this.stock.get(5));
		/** 
		 * Karel Kédémos
		 */
		journalEq6.ajouter("quantité bonbon basse qualité = " + Integer.toString(this.stock.get(0)));
		journalEq6.ajouter("quantité bonbon moyenne qualité = " + Integer.toString(this.stock.get(1)));
		journalEq6.ajouter("quantité bonbon haute qualité = " + Integer.toString(this.stock.get(2)));
		journalEq6.ajouter("quantité tablette basse qualité = " + Integer.toString(this.stock.get(3)));
		journalEq6.ajouter("quantité tablette moyenne qualité = " + Integer.toString(this.stock.get(4)));
		journalEq6.ajouter("quantité tablette haute qualité = " + Integer.toString(this.stock.get(5)));
 
	}
	
	

	public DemandeAO getAppelOffre() {
		final ArrayList<Indicateur> stock= new ArrayList<Indicateur>();
		stock.add(stock_BBQ);stock.add(stock_BMQ);stock.add(stock_BHQ);stock.add(stock_TBQ);stock.add(stock_TMQ);stock.add(stock_THQ);			
		for (Indicateur i: stock) {
			if(i.getValeur()<i.getHistorique().get(Monde.LE_MONDE.getStep()-1).getValeur()) {
				
			}
		}
		
		
		
		return null;
	}
	@Override
	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		// TODO Auto-generated method stub
		
	}
	

	
}
