package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.Labellise;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChocoBis;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

/**
 * 
 * @author Noémie Rigaut
 *
 */

public class Eq4TRAN implements Acteur { 

	public Acteur Eq4TRAN ; 

	/** Déclaration des indicateurs pour le Journal
	 *  @author Mickaël
	 */
	
	// Créer ArrayList<Indicateur> production pour factoriser notre code !!! 
	
	private SousActeur Acteur1;
	private SousActeur Acteur2;
	private SousActeur Acteur3;
	
	//Indicateurs de stock et de production
	private Indicateur stockTabBQ_Eq4;
	private Indicateur stockTabMQ_Eq4;
	private Indicateur stockTabHQ_Eq4 ;
	private Indicateur stockChocMQ_Eq4 ;
	private Indicateur stockChocHQ_Eq4;
	private Indicateur prodTabBQ_Eq4 ;
	private Indicateur prodTabMQ_Eq4 ;
	private Indicateur prodTabHQ_Eq4 ;
	private Indicateur prodChocMQ_Eq4 ;
	private Indicateur prodChocHQ_Eq4 ;
	//Indicateur de notre solde bancaire
	private Indicateur solde ; 
	//Journal rendant compte de nos activités et de l'évolution de nos indicateurs
	private Journal JournalEq4 = new Journal("JournalEq4") ;
	//Rôle de vendeur que nous incarnerons à chaque next() et qui se mettra à jour à cette même fréquence
	private Vendeur vendeur;
	//On crée une liste pour ranger nos stocks
	private ArrayList<Indicateur> Stocks;
	
	private ArrayList<ContratFeveV3> contratFevesEnCours ; 
	
	/** Initialisation des indicateurs 
	 * 0,
	 */
	public Eq4TRAN() {
		/**
		 * @Mickaël
		 * Constructeurs des 3 Sous-Acteurs 
		 */
		
		// StocksSA pour Stocks Sous-Acteurs etc..
		ArrayList<Indicateur> StocksSA = new ArrayList<>(6);
		ArrayList<Indicateur> ProductionSA = new ArrayList<>(6);
		int soldeSA=1000;
		for(int i=0;i<6;i++) {
			StocksSA.add(new Indicateur("stockProduit"+(i+1),this,1000));
			ProductionSA.add(new Indicateur("productionProduit"+(i+1),this,1000));
		}
		
		/**
		 *  On construit les 3 Sous-Acteurs 
		 */
		
		this.Acteur1 = new SousActeur(new Journal("JournalActeur1"),StocksSA,ProductionSA,soldeSA,50,Vendeur.COMMERCE_EQUITABLE);
		this.Acteur2 = new SousActeur(new Journal("JournalActeur2"),StocksSA,ProductionSA,soldeSA,135,Vendeur.PRODUCTEUR_LOCAL);
		this.Acteur3 = new SousActeur(new Journal("JournalActeur3"),StocksSA,ProductionSA,soldeSA,220,Vendeur.LUXE);
		
		/**
		 * @author Mickaël, Etienne
		 * On créé les stocks pour chaque Sous-Acteur.
		 */
				
				ArrayList<Indicateur> Stocks_Acteur1 = new ArrayList<>();
				Stocks_Acteur1.add(new Indicateur("",this.Acteur1,0));
				ArrayList<Integer> stocks_Acteur1 = new ArrayList<>();
				stocks_Acteur1.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur1.add(this.Acteur1.getStocks().get(i));
					stocks_Acteur1.add((int)this.Acteur1.getStocks().get(i).getValeur());
				}
				this.Acteur1.setVendeur(new Vendeur(stocks_Acteur1));
				
				ArrayList<Indicateur> Stocks_Acteur2 = new ArrayList<>();
				Stocks_Acteur2.add(new Indicateur("",this.Acteur2,0));
				ArrayList<Integer> stocks_Acteur2 = new ArrayList<>();
				stocks_Acteur2.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur2.add(this.Acteur2.getStocks().get(i));
					stocks_Acteur2.add((int)this.Acteur2.getStocks().get(i).getValeur());
				}
				this.Acteur2.setVendeur(new Vendeur(stocks_Acteur2));
				
				ArrayList<Indicateur> Stocks_Acteur3 = new ArrayList<>();
				Stocks_Acteur3.add(new Indicateur("",this.Acteur3,0));
				ArrayList<Integer> stocks_Acteur3 = new ArrayList<>();
				stocks_Acteur3.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur3.add(this.Acteur3.getStocks().get(i));
					stocks_Acteur3.add((int)this.Acteur3.getStocks().get(i).getValeur());
				}
				this.Acteur3.setVendeur(new Vendeur(stocks_Acteur3));

		

				
		/**
		 * @Mickaël
		 * on initialise les contrats d'échange de POUDRE avec les autres transformateurs 
		 */
		
		/**
		 * Acteur 1 labellisé commerce équitable 
		 */
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur1 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur1 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur1[1] = new ContratPoudre(1,9000,100.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[1] = new ContratPoudre(1,1000,100.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur1.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur1);
		this.Acteur1.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur1);
		
		/**
		 * Acteur 2 labellisé Local
		 */
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur2 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur2 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur2[0] = new ContratPoudre(0,2000,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur2[1] = new ContratPoudre(1,10000,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur2[1] = new ContratPoudre(1,8000,100.0, (IAcheteurPoudre)this.Acteur2,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		
		this.Acteur2.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur2);
		this.Acteur2.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur2);
		
		/**
		 * Acteur 3 labellisé luxe, n'achète que de la qualité de luxe à l'éq 7 
		 */
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur3 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur3 = new ContratPoudre[3];
		contratPoudreEnCoursEq7TRAN_Acteur3[2] = new ContratPoudre(2,18000,100.0, (IAcheteurPoudre)this.Acteur3,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur3[1] = new ContratPoudre(1,0,100.0, (IAcheteurPoudre)this.Acteur3,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur3.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur3);
		this.Acteur3.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur3);
		
		
		/**
		 * CHARLES ICI IL FAUT CONSTRUIRE LES CONTRATS FEVES V3
		 */
		
		/**
		 
		this.contratFevesEnCours.add(new ContratFeveV3( (IAcheteurFeve)this.Acteur1 , (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD") , 0 , 0 , 0 , 0 , 0.0 , 0.0 , 0.0 ,false)) ; 	     	 	 		  			 	);
		this.contratFevesEnCours.add(new ContratFeveV3((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD"),1 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false)) ;
		contratFeveEnCours[2]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD"),2 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[3]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),0 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[4]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),1 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[5]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),2 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		*/

		//On initialise les indicateurs à 1000(arbitraire)

		stockTabBQ_Eq4 = new Indicateur("Eq4 - stockTabBQ",this,1000) ;
		stockTabMQ_Eq4 = new Indicateur("Eq4 - stockTabMQ",this,1000) ;
		stockTabHQ_Eq4 = new Indicateur("Eq4 - stockTabHQ",this,1000) ;
		stockChocMQ_Eq4 = new Indicateur("Eq4 - stockChocMQ",this,1000) ;
		stockChocHQ_Eq4 = new Indicateur("Eq4 - stockTabHQ",this,1000) ;
		prodTabBQ_Eq4 = new Indicateur("Eq4 - prodTabBQ",this,1000) ;
		prodTabMQ_Eq4 = new Indicateur("Eq4 - prodTabMQ",this,1000) ;
		prodTabHQ_Eq4 = new Indicateur("Eq4 - prodTabHQ",this,1000) ;
		prodChocMQ_Eq4 = new Indicateur("Eq4 - prodChocMQ",this,1000) ;
		prodChocHQ_Eq4 = new Indicateur("Eq4 - prodChocHQ",this,1000) ;

		solde = new Indicateur("solde",this,1000) ;
		//On crée la liste qui range nos stocks
		ArrayList<Indicateur> Stocks = new ArrayList<>();
		//On remplit cette liste avec nos stocks
		Stocks.add(new Indicateur("",this,0));
		Stocks.add(stockChocMQ_Eq4);
		Stocks.add(stockChocHQ_Eq4);
		Stocks.add(stockTabBQ_Eq4);
		Stocks.add(stockTabMQ_Eq4);
		Stocks.add(stockTabHQ_Eq4);
		this.Stocks=Stocks;
		// On s'initialise en tant que vendeur
		ArrayList<Integer> stocks1 = new ArrayList<>();
		stocks1.add(0);
		stocks1.add((int)stockChocMQ_Eq4.getValeur());
		stocks1.add((int)stockChocHQ_Eq4.getValeur());
		stocks1.add((int)stockTabBQ_Eq4.getValeur());
		stocks1.add((int)stockTabMQ_Eq4.getValeur());
		stocks1.add((int)stockTabHQ_Eq4.getValeur());
		vendeur = new Vendeur(stocks1);
		

		/**
		 * On ajoute nos indicateurs et notre journal de production et de vente dans la fenêtre principale du Monde
		 * 
		 * IL FAUT CODER UN SOMMATEUR POUR AVOIR LES STOCKS TOTAUX DE EQ4TRAN
		 * @author Mickaël, Etienne
		 */
		/*Monde.LE_MONDE.ajouterIndicateur(this.Acteur1.getStockChocMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur1.getStockChocHQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur1.getStockTabBQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur1.getStockTabMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur1.getStockTabHQ());
		Monde.LE_MONDE.ajouterJournal(this.Acteur1.getJournalEq4());
		Monde.LE_MONDE.ajouterJournal(this.Acteur1.getVendeur().ventes);
		
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur2.getStockChocMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur2.getStockChocHQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur2.getStockTabBQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur2.getStockTabMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur2.getStockTabHQ());
		Monde.LE_MONDE.ajouterJournal(this.Acteur2.getJournalEq4());
		Monde.LE_MONDE.ajouterJournal(this.Acteur2.getVendeur().ventes);
		
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur3.getStockChocMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur3.getStockChocHQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur3.getStockTabBQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur3.getStockTabMQ());
		Monde.LE_MONDE.ajouterIndicateur(this.Acteur3.getStockTabHQ());
		Monde.LE_MONDE.ajouterJournal(this.Acteur3.getJournalEq4());
		Monde.LE_MONDE.ajouterJournal(this.Acteur3.getVendeur().ventes);*/
	}

	/** Nom de l'acteur
	 */
	@Override
	public String getNom() {
		return "Eq4TRAN";
	}


	public void next() {
	
		/**
		 * pour chaque contrat on récupère prix et qté
		 */
		/**
		 * pour contrat fève 
		 */
		for(int i = 0 ; i < contratFevesEnCours.size() ; i++) {
			/**
			 * Selon la qualité
			 * On récupère les qtés de fèves achetées
			 * Elles sont transformées immédiatement en produits
			 * Les produits sont ajoutés aux stocks
			 * Le coût total de l'achat est retiré au solde
			 */
			if (contratFevesEnCours.get(i).getReponse()) {
				if(contratFevesEnCours.get(i).getQualite() == 0) {
					prodTabBQ_Eq4.setValeur(this, contratFevesEnCours.get(i).getProposition_Quantite()); 
					double ancienStockTabBQ = stockTabBQ_Eq4.getValeur() ;
					stockTabBQ_Eq4.setValeur(Eq4TRAN, ancienStockTabBQ + prodTabBQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Prix()*contratFevesEnCours.get(i).getProposition_Quantite());
				}
				else if(contratFevesEnCours.get(i).getQualite() == 1) {
					prodTabMQ_Eq4.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Quantite());
					double ancienStockTabMQ = stockTabMQ_Eq4.getValeur() ;
					stockTabMQ_Eq4.setValeur(Eq4TRAN, ancienStockTabMQ + prodTabMQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Prix()*contratFevesEnCours.get(i).getProposition_Quantite());

				}
				else if(contratFevesEnCours.get(i).getQualite() == 2) {
					prodTabHQ_Eq4.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Quantite());
					double ancienStockTabHQ = stockTabHQ_Eq4.getValeur() ;
					stockTabHQ_Eq4.setValeur(Eq4TRAN, ancienStockTabHQ + prodTabMQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Prix()*contratFevesEnCours.get(i).getProposition_Quantite());

				}
			}

		}



		/**
		 * @Mickaël
		 * on doit faire la stratégie marketing
		 */
		IVendeurPoudre Eq5TRAN = (IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq5TRAN");
		IVendeurPoudre Eq7TRAN = (IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN");
		Eq5TRAN.getDevisPoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre) this);
		Eq7TRAN.getDevisPoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this);
		for (int i=0;i<this.Acteur1.getContratPoudreEnCoursEq5TRAN().length;i++) {
			this.Acteur1.getContratPoudreEnCoursEq5TRAN()[i].setReponse(true);
			this.Acteur1.getContratPoudreEnCoursEq7TRAN()[i].setReponse(true);
		}
		if (1==1) { // Pour l'instant on accepte l'achat sans condition 
			Eq5TRAN.sendReponsePoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre) this);
			Eq7TRAN.sendReponsePoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this);
		}

		ContratPoudre[] contratfinalEq5TRAN = new ContratPoudre[3];
		ContratPoudre[] contratfinalEq7TRAN = new ContratPoudre[3];
		contratfinalEq5TRAN = Eq5TRAN.getEchangeFinalPoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre)this);
		contratfinalEq7TRAN = Eq7TRAN.getEchangeFinalPoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this);

		ArrayList<ContratPoudre> contratPoudreEnCours = new ArrayList<ContratPoudre>();
		contratPoudreEnCours.add(contratfinalEq5TRAN[0]);
		contratPoudreEnCours.add(contratfinalEq5TRAN[1]);
		contratPoudreEnCours.add(contratfinalEq5TRAN[2]);
		contratPoudreEnCours.add(contratfinalEq7TRAN[0]);
		contratPoudreEnCours.add(contratfinalEq7TRAN[1]);
		contratPoudreEnCours.add(contratfinalEq7TRAN[2]);

		for(int i = 0 ; i < contratPoudreEnCours.size() ; i++ ) {

			/**
			 * On récupère les qtés de poudre achetée
			 * On les transforme en produits
			 * Puis on les stocke
			 */

			if(contratPoudreEnCours.get(i).isReponse()) {
				if (contratPoudreEnCours.get(i).getQualite() == 1) {
					prodChocMQ_Eq4.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getQuantite());
					double ancienStockChocMQ = stockChocMQ_Eq4.getValeur() ;
					stockChocMQ_Eq4.setValeur(Eq4TRAN, ancienStockChocMQ + prodChocMQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());

				} else if (contratPoudreEnCours.get(i).getQualite() == 2 ) {
					prodChocHQ_Eq4.setValeur(Eq4TRAN, contratFevesEnCours.get(i).getProposition_Quantite());
					double ancienStockChocHQ = stockChocHQ_Eq4.getValeur() ;
					stockChocHQ_Eq4.setValeur(Eq4TRAN, ancienStockChocHQ + prodChocHQ_Eq4.getValeur()); 
					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());
				}
			}
		} 

		// On se transforme désormais en vendeur, le réapprovisionnement de nos stocks ayant été effectué
		// Nous allons alors vendre des fèves aux distributeurs par l'intermédiaire du MarchéChoco()
		ArrayList<Integer> stocks = new ArrayList<>();
		stocks.add(0);
		stocks.add((int)stockChocMQ_Eq4.getValeur());
		stocks.add((int)stockChocHQ_Eq4.getValeur());
		stocks.add((int)stockTabBQ_Eq4.getValeur());
		stocks.add((int)stockTabMQ_Eq4.getValeur());
		stocks.add((int)stockTabHQ_Eq4.getValeur());
		vendeur = new Vendeur(stocks);
		
		// On met à jour le journal des trois acteurs
		journalEq4(this.Acteur1);
		journalEq4(this.Acteur2);
		journalEq4(this.Acteur3);
	}

	public void journalEq4(SousActeur j) {
		j.getJournalEq4().ajouter("Stock des tablettes Basse Qualité = "+j.getQuantite(2));
		j.getJournalEq4().ajouter("Stock des tablettes Moyenne Qualité = "+j.getQuantite(3));
		j.getJournalEq4().ajouter("Stock des tablettes Basse Qualité = "+j.getQuantite(4));
		j.getJournalEq4().ajouter("Stock des chocolats Moyenne Qualité = "+j.getQuantite(5));
		j.getJournalEq4().ajouter("Stock des chocolats Haute Qualité = "+j.getQuantite(6));
		// Les Sous-Acteurs produisent ils vraiment des choses ? (rajouter alors getQuantitePorduite() et setter dans 
		// Sous-Acteur
		
		/*j.getJournalEq4().ajouter("Production des tablettes Basse Qualité = "+j.get);
		j.getJournalEq4().ajouter("Production des tablettes Moyenne Qualité = "+j.getProdTabBQ());
		j.getJournalEq4().ajouter("Production des tablettes Haute Qualité = "+j.getProdTabBQ());
		j.getJournalEq4().ajouter("Production de chocolats Moyenne Qualité = "+j.getProdChocMQ());
		j.getJournalEq4().ajouter("Production de chocolats Haute Qualité = "+j.getProdChocHQ()); */

	}

	public Journal getJournalVentes() {
		return vendeur.getVentes();
	}

}
