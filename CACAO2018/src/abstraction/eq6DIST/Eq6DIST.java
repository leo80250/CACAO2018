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
		/**
		 * Karel Kédémos
		 */
		
		int quantité_demandée_transfo5_CMG=0;
		int quantité_demandée_transfo5_TBG=0;
		int quantité_demandée_transfo5_TMG=3000;
		int quantité_demandée_transfo7_CMG=24650;
	 	int quantité_demandée_transfo7_TBG=4000;
		int quantité_demandée_transfo7_TMG=48000;
		int quantité_demandée_transfo4_CMG=5800;
		int quantité_demandée_transfo4_TBG=200;
		int quantité_demandée_transfo4_TMG=6750;	
		int nombre_transfo=Prix.size();
		ArrayList<ArrayList<Integer>> commande = new ArrayList<ArrayList<Integer>>();
		for (int i=0;i<nombre_transfo;i++) {
			commande.add(new ArrayList<Integer>());
			for (int j=0;j<6;j++) {
				commande.get(i).add(0);
		}
		}
		
		// Commande de TMG
		
		double stock_TMG_min=10000000.0; // calcul le stock min en TMG des 3 transfos et l'indice de l'équipe avec le plus petit stock;
		int equipe_stock_TMG_min=0;
		for (int i=0; i<3; i++) {
			if (stock_TMG_min>=Stock.get(i).get(4)&&Stock.get(i).get(4)!=0) {
				stock_TMG_min=Stock.get(i).get(4);
				equipe_stock_TMG_min=i;
			}
		}
		int indice_equipe_moins_chere_TMG=0 ; //renvoie l'indice du transfo le moins chere pour le stock min et renvoie le prix
		double prix_moins_chere_TMG=10000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int)stock_TMG_min-1,5)<=prix_moins_chere_TMG) {
				indice_equipe_moins_chere_TMG=i;
				prix_moins_chere_TMG=Prix.get(i).getPrixProduit((int)stock_TMG_min-1,5);
			}
		}
		int stock_TMG1=0;
		if (equipe_stock_TMG_min==indice_equipe_moins_chere_TMG) {
			stock_TMG1= (int) (0.8*stock_TMG_min);
		} else {
			stock_TMG1= (int) (0.2*stock_TMG_min);
		}
		commande.get(equipe_stock_TMG_min).set(4, stock_TMG1);
		// calcule le 2e plus petit stock de TMG et l'indice du transfo associé 
		double stock_TMG_min2=10000000.0;
		int equipe_stock_TMG_min2=0;
		for (int k=0; k<3; k++) {
			if (stock_TMG_min2>=Stock.get(k).get(4) && stock_TMG_min<Stock.get(k).get(4)) {
				stock_TMG_min2=Stock.get(k).get(4);
				equipe_stock_TMG_min2=k;
			}
		}
		// renvoie l'indice du transfo le moins chere pour le 2e plus petit stock
		int indice_equipe_moins_chere_TMG2=0;
		double prix_moins_chere_TMG2=100000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int) stock_TMG_min2-1, 5) <= prix_moins_chere_TMG2
					&& i!=indice_equipe_moins_chere_TMG) {
				indice_equipe_moins_chere_TMG2=i;
				prix_moins_chere_TMG2=Prix.get(i).getPrixProduit((int)stock_TMG_min2-1,5);
			}
		}
		int stock_TMG2;
		if (equipe_stock_TMG_min2==indice_equipe_moins_chere_TMG2) {
			stock_TMG2=(int) (0.8*stock_TMG_min2);
		} else {
			stock_TMG2=(int) (0.2*stock_TMG_min2);
		}
		commande.get(equipe_stock_TMG_min2).set(4, stock_TMG2);
		// calcule l'indice du transfo avec le plus grand stock
		int equipe_stock_TMG_max=3-equipe_stock_TMG_min-equipe_stock_TMG_min2;
		commande.get(equipe_stock_TMG_max).set(4, 22220-stock_TMG1-stock_TMG2);
		
		
		//Commande de TBG
		
		double stock_TBG_min=10000000.0; // calcul le stock min en TBG des 3 transfos et l'indice de l'équipe avec le plus petit stock;
		int equipe_stock_TBG_min=0;
		for (int i=0; i<3; i++) {
			if (stock_TBG_min>=Stock.get(i).get(3)&&Stock.get(i).get(3)!=0) {
				stock_TBG_min=Stock.get(i).get(3);
				equipe_stock_TBG_min=i;
			}
		}
		int indice_equipe_moins_chere_TBG=0 ; //renvoie l'indice du transfo le moins chere pour le stock min et renvoie le prix
		double prix_moins_chere_TBG=10000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int)stock_TBG_min-1,4)<=prix_moins_chere_TBG&&Prix.get(i).getPrixProduit((int)stock_TBG_min-1,4)!=0) {
				indice_equipe_moins_chere_TBG=i;
				prix_moins_chere_TBG=Prix.get(i).getPrixProduit((int)stock_TBG_min-1,4);
			}
		}
		int stock_TBG1=0;
		if (equipe_stock_TBG_min==indice_equipe_moins_chere_TBG) {
			stock_TBG1= (int) (0.8*stock_TBG_min);
		} else {
			stock_TBG1= (int) (0.2*stock_TBG_min);
		}
		commande.get(equipe_stock_TBG_min).set(3, stock_TBG1);
		// calcule le 2e plus petit stock de TBG et l'indice du transfo associé 
		double stock_TBG_min2=10000000.0;
		int equipe_stock_TBG_min2=0;
		for (int k=0; k<3; k++) {
			if (stock_TBG_min2>=Stock.get(k).get(3) && stock_TBG_min<Stock.get(k).get(3)) {
				stock_TBG_min2=Stock.get(k).get(3);
				equipe_stock_TBG_min2=k;
			}
		}
		// renvoie l'indice du transfo le moins chere pour le 2e plus petit stock
		int indice_equipe_moins_chere_TBG2=0;
		double prix_moins_chere_TBG2=100000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int) stock_TBG_min2-1, 4) <= prix_moins_chere_TBG2
					&& i!=indice_equipe_moins_chere_TBG) {
				indice_equipe_moins_chere_TBG2=i;
				prix_moins_chere_TBG2=Prix.get(i).getPrixProduit((int)stock_TBG_min2-1,4);
			}
		}
		int stock_TBG2;
		if (equipe_stock_TBG_min2==indice_equipe_moins_chere_TBG2) {
			stock_TBG2=(int) (0.8*stock_TBG_min2);
		} else {
			stock_TBG2=(int) (0.2*stock_TBG_min2);
		}
		commande.get(equipe_stock_TBG_min2).set(3, stock_TBG2+1615-stock_TBG1);
		
		//Commande de CMG
		
		double stock_CMG_min=10000000.0; // calcul le stock min en CMG des 3 transfos et l'indice de l'équipe avec le plus petit stock;
		int equipe_stock_CMG_min=0;
		for (int i=0; i<3; i++) {
			if (stock_CMG_min>=Stock.get(i).get(1)) {
				stock_CMG_min=Stock.get(i).get(1);
				equipe_stock_CMG_min=i;
			}
		}
		int indice_equipe_moins_chere_CMG=0 ; //renvoie l'indice du transfo le moins chere pour le stock min et renvoie le prix
		double prix_moins_chere_CMG=10000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int)stock_CMG_min-1,2)<=prix_moins_chere_CMG) {
				indice_equipe_moins_chere_CMG=i;
				prix_moins_chere_CMG=Prix.get(i).getPrixProduit((int)stock_CMG_min-1,2);
			}
		}
		int stock_CMG1=0;
		if (equipe_stock_CMG_min==indice_equipe_moins_chere_CMG) {
			stock_CMG1= (int) (0.8*stock_CMG_min);
		} else {
			stock_CMG1= (int) (0.2*stock_CMG_min);
		}
		commande.get(equipe_stock_CMG_min).set(1, stock_CMG1);
		// calcule le 2e plus petit stock de CMG et l'indice du transfo associé 
		double stock_CMG_min2=10000000.0;
		int equipe_stock_CMG_min2=0;
		for (int k=0; k<3; k++) {
			if (stock_CMG_min2>=Stock.get(k).get(1) && stock_CMG_min<Stock.get(k).get(1)) {
				stock_CMG_min2=Stock.get(k).get(1);
				equipe_stock_CMG_min2=k;
			}
		}
		// renvoie l'indice du transfo le moins chere pour le 2e plus petit stock
		int indice_equipe_moins_chere_CMG2=0;
		double prix_moins_chere_CMG2=100000000.0;
		for (int i=0; i<3; i++) {
			if (Prix.get(i).getPrixProduit((int) stock_CMG_min2-1, 2) <= prix_moins_chere_CMG2
					&& i!=indice_equipe_moins_chere_CMG) {
				indice_equipe_moins_chere_CMG2=i;
				prix_moins_chere_CMG2=Prix.get(i).getPrixProduit((int)stock_CMG_min2-1,2);
			}
		}
		int stock_CMG2;
		if (equipe_stock_CMG_min2==indice_equipe_moins_chere_CMG2) {
			stock_CMG2=(int) (0.8*stock_CMG_min2);
		} else {
			stock_CMG2=(int) (0.2*stock_CMG_min2);
		}
		commande.get(equipe_stock_CMG_min2).set(1, stock_CMG2+11712-stock_CMG1);

		
		return commande;
	}
	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		// TODO Auto-generated method stub
		
	}
	
  
	
}
