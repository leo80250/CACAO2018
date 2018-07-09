package abstraction.eq6DIST;


import abstraction.eq1DIST.GrilleQuantite;
import abstraction.eq1DIST.InterfaceDistributeurClient;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChocoTer;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

import java.util.ArrayList;

 
public class Eq6DIST implements Acteur, IAcheteurChocoBis, InterfaceDistributeurClient{
	private Indicateur stock_BBQ;
	private Indicateur stock_BMQ;
	private Indicateur stock_BHQ;
	private Indicateur stock_TBQ;
	private Indicateur stock_TMQ;
	private Indicateur stock_THQ;
	private Indicateur banque;
	private Indicateur prix_BBQ;
	private Indicateur prix_BMQ;
	private Indicateur prix_BHQ;
	private Indicateur prix_TBQ;
	private Indicateur prix_TMQ;
	private Indicateur prix_THQ;
	private Indicateur marge_BBQ;
	private Indicateur marge_BMQ;
	private Indicateur marge_BHQ;
	private Indicateur marge_TBQ;
	private Indicateur marge_TMQ;
	private Indicateur marge_THQ;
	private ArrayList<Double> marge;
	private ArrayList<Integer> stock;
	private ArrayList<Double> prix;
	private Journal journalEq6;
	public Eq6DIST() {
		//MarcheChoco MC = new MarcheChoco();
		//Monde.LE_MONDE.ajouterActeur(MC);
		this.banque= new Indicateur("Solde bancaire Eq6 : ",this, 120000);
		this.stock= new ArrayList<Integer>();this.stock.add(0);	this.stock.add(0);this.stock.add(0);this.stock.add(0);this.stock.add(0);this.stock.add(0);
		this.marge= new ArrayList<Double>();this.marge.add(0.0);	this.marge.add(0.0);this.marge.add(0.0);this.marge.add(0.0);this.marge.add(0.0);this.marge.add(0.0);
		this.stock_BBQ= new Indicateur("Stock de bonbons BQ Eq6 :",this);
		this.stock_BMQ=new Indicateur("Stock de bonbons MQ Eq6 :",this);
		this.stock_BHQ=new Indicateur("Stock de bonbons HQ Eq6 :",this);
		this.stock_TBQ=new Indicateur("Stock de tablettes BQ Eq6 :",this);
		this.stock_TMQ=new Indicateur("Stock de tablettes MQ Eq6 :",this);
		this.stock_THQ=new Indicateur("Stock de tablettes HQ Eq6 :",this);
		this.prix_BBQ=new Indicateur("Prix de bonbons BQ Eq6 :",this);
		this.prix_BMQ=new Indicateur("Prix de bonbons MQ Eq6 :",this);
		this.prix_BHQ=new Indicateur("Prix de bonbons HQ Eq6 :",this);
		this.prix_TBQ=new Indicateur("Prix de tablettes BQ Eq6 :",this);
		this.prix_TMQ=new Indicateur("Prix de tablettes MQ Eq6 :",this);
		this.prix_THQ=new Indicateur("Prix de tablettes BQ Eq6 :",this);
		this.marge_BBQ= new Indicateur("Marge sur bonbons BQ Eq6 :",this);
		this.marge_BMQ=new Indicateur("Marge sur bonbons MQ Eq6 :",this);
		this.marge_BHQ=new Indicateur("Marge sur bonbons HQ Eq6 :",this);
		this.marge_TBQ=new Indicateur("Marge sur tablettes BQ Eq6 :",this);
		this.marge_TMQ=new Indicateur("Marge sur tablettes MQ Eq6 :",this);
		this.marge_THQ=new Indicateur("Marge sur tablettes HQ Eq6 :",this);
		this.prix= new ArrayList<Double>();this.prix.add(0.0);this.prix.add(1.931666667);this.prix.add(0.0);this.prix.add(0.9942857143);this.prix.add(2.145);this.prix.add(0.0);

		Monde.LE_MONDE.ajouterIndicateur(this.banque);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_BHQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_TBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_TMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.stock_THQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_BBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_BMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_BHQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_TBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_TMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.prix_THQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_BBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_BMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_BHQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_TBQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_TMQ);
		Monde.LE_MONDE.ajouterIndicateur(this.marge_THQ);

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
		this.prix_BBQ.setValeur(this, this.prix.get(0));
		this.prix_BMQ.setValeur(this, this.prix.get(1));
		this.prix_BHQ.setValeur(this, this.prix.get(2));
		this.prix_TBQ.setValeur(this, this.prix.get(3));
		this.prix_TMQ.setValeur(this, this.prix.get(4));
		this.prix_THQ.setValeur(this, this.prix.get(5));
		this.marge_BBQ.setValeur(this, this.marge.get(0));
		this.marge_BMQ.setValeur(this, this.marge.get(1));
		this.marge_BHQ.setValeur(this, this.marge.get(2));
		this.marge_TBQ.setValeur(this, this.marge.get(3));
		this.marge_TMQ.setValeur(this, this.marge.get(4));
		this.marge_THQ.setValeur(this, this.marge.get(5));

		//Victor Signes
		//Achat occasionnel

		for(int i=0;i<6;i++) {
			if(this.stock.get(i)<200) { //hypothèse stock minimal
				DemandeAO d = new DemandeAO(500,i+1); //hypothèse achat à réaliser
				ArrayList<Double> prop = new ArrayList<Double>();
				ArrayList<Acteur> acteurs = new ArrayList<Acteur>();
				for(Acteur acteur : Monde.LE_MONDE.getActeurs()) {
					if(acteur instanceof IvendeurOccasionnelChocoTer) {
						prop.add(((IvendeurOccasionnelChocoTer)acteur).getReponseTer(d));
						acteurs.add(acteur);
					}
				}
				double p=prop.get(0);
				Acteur a=acteurs.get(0);
				for(Double j : prop) {
					 if(j<p && i<acteurs.size()) {
						 p=j; //on choisit la proposition avec le prix minimum
						 a=acteurs.get(i);
					 }
				}
				((IvendeurOccasionnelChocoTer)a).envoyerReponseTer(this, d.getQuantite(), d.getQualite(), p); //envoi de la proposition choisie

			}
		}



		/**
		 * Karel Kédémos et Victor Signes
		 */
		journalEq6.ajouter("quantité bonbon basse qualité = " + Integer.toString(this.stock.get(0)));
		journalEq6.ajouter("quantité bonbon moyenne qualité = " + Integer.toString(this.stock.get(1)));
		journalEq6.ajouter("quantité bonbon haute qualité = " + Integer.toString(this.stock.get(2)));
		journalEq6.ajouter("quantité tablette basse qualité = " + Integer.toString(this.stock.get(3)));
		journalEq6.ajouter("quantité tablette moyenne qualité = " + Integer.toString(this.stock.get(4)));
		journalEq6.ajouter("quantité tablette haute qualité = " + Integer.toString(this.stock.get(5)));
		journalEq6.ajouter("prix bonbon basse qualité = " + Double.toString(this.prix.get(0)));
		journalEq6.ajouter("prix bonbon moyenne qualité = " + Double.toString(this.prix.get(1)));
		journalEq6.ajouter("prix bonbon haute qualité = " + Double.toString(this.prix.get(2)));
		journalEq6.ajouter("prix tablette basse qualité = " + Double.toString(this.prix.get(3)));
		journalEq6.ajouter("prix tablette moyenne qualité = " + Double.toString(this.prix.get(4)));
		journalEq6.ajouter("prix tablette haute qualité = " + Double.toString(this.prix.get(5)));
		journalEq6.ajouter("marge sur bonbons basse qualité = " + Double.toString(this.marge.get(0)));
		journalEq6.ajouter("marge sur bonbons moyenne qualité = " + Double.toString(this.marge.get(1)));
		journalEq6.ajouter("marge sur bonbons haute qualité = " + Double.toString(this.marge.get(2)));
		journalEq6.ajouter("marge sur tablettes basse qualité = " + Double.toString(this.marge.get(3)));
		journalEq6.ajouter("marge sur tablettes moyenne qualité = " + Double.toString(this.marge.get(4)));
		journalEq6.ajouter("marge sur tablettes haute qualité = " + Double.toString(this.marge.get(5)));

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


		int nombre_transfo=Prix.size();
		ArrayList<ArrayList<Integer>> commande = new ArrayList<ArrayList<Integer>>();
		for (int i=0;i<nombre_transfo;i++) {
			commande.add(new ArrayList<Integer>());
			for (int j=0;j<6;j++) {
				commande.get(i).add(1000);
			}
		}


		return commande;
	}
	@Override
	// Léopold Petitjean
	public void livraison(ArrayList<Integer> livraison, double paiement) {
 		// TODO Auto-generated method stub
		for (int i=0;i<livraison.size();i++) {
			this.stock.set(i, this.stock.get(i)+livraison.get(i));
		}
		this.banque.setValeur(this, this.banque.getValeur()-paiement);
	}

	// Victor Signes
	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		int[] res = new int[6];
		for (int i=0;i<6;i++) {
			int c = this.stock.get(i) - Q.getValeur( i);
			if (c>=0) {
				res[i]=(Q.getValeur(i));
			}else {
				res[i]=(this.stock.get(i));
			}

			this.banque.setValeur(this, this.banque.getValeur() + this.prix.get(i)*res[i]);		

		}
		
		return new GrilleQuantite(res);
	}
	
	//Victor Signes

	public void modifPrix(GrilleQuantite Q) {
		double p=0.2; //pourcentage de marge
		ArrayList<Double> prixMax= new ArrayList<Double>();
		prixMax.add(0.0); prixMax.add(2.66); prixMax.add(0.0); prixMax.add(1.3); prixMax.add(3.08); prixMax.add(0.0);
		ArrayList<Double> prixMin= new ArrayList<Double>();
		prixMin.add(0.0); prixMin.add(1.33); prixMin.add(0.0); prixMin.add(0.51); prixMin.add(1.7); prixMin.add(0.0);
		double Nmarge= 0; //nouvelle marge
		double margeUnitaire=0; //marge sur une tablette

		for (int i=0;i<6;i++) {  //on calcule la marge pour chaque type de chocolat

			
			Nmarge=this.prix.get(i)*Q.getValeur(i)*p;

			margeUnitaire=this.prix.get(i)*p;
			if(Nmarge<this.marge.get(i) && (this.prix.get(i)+margeUnitaire)<=prixMax.get(i)) {
				this.prix.set(i, this.prix.get(i)+margeUnitaire); //on définit le nouveau prix
			}
			if(Nmarge>this.marge.get(i) && (this.prix.get(i)-margeUnitaire)>=prixMin.get(i)) {
				this.prix.set(i, this.prix.get(i)-margeUnitaire); //on définit le nouveau prix
			}
			this.marge.set(i, Nmarge);
		}
	}

	@Override
	public double[] getPrix() {
		// TODO Auto-generated method stub
		return new double[] {this.prix_TBQ.getValeur(),this.prix_TMQ.getValeur(),this.prix_THQ.getValeur(),
				this.prix_BBQ.getValeur(),this.prix_BMQ.getValeur(),this.prix_BHQ.getValeur()};
		}
	}

