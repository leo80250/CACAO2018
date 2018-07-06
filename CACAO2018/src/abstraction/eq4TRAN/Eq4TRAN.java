package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV4;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.Labellise;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.eq5TRAN.Eq5TRAN;
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
	private Indicateur stockTabHQ_Eq4;
	private Indicateur stockChocMQ_Eq4;
	private Indicateur stockChocHQ_Eq4;
	private Indicateur prodTabBQ_Eq4 ;
	private Indicateur prodTabMQ_Eq4 ;
	private Indicateur prodTabHQ_Eq4 ;
	private Indicateur prodChocMQ_Eq4;
	private Indicateur prodChocHQ_Eq4;
	//Indicateur de notre solde bancaire
	private Indicateur solde ; 
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
		
		int soldeSA=1000;
		ArrayList<Indicateur> Prod_Acteur1=new ArrayList<>();
		ArrayList<Indicateur> Stocks_Acteur1 = new ArrayList<>();
		ArrayList<Indicateur> Prod_Acteur2 = new ArrayList<>();
		ArrayList<Indicateur> Stocks_Acteur2 = new ArrayList<>();
		ArrayList<Indicateur> Prod_Acteur3 = new ArrayList<>();
		ArrayList<Indicateur> Stocks_Acteur3 = new ArrayList<>();
		
		/**
		 *  On construit les 3 Sous-Acteurs 
		 */
		
		this.Acteur1 = new SousActeur(new Journal("JournalActeur1"),Stocks_Acteur1,Prod_Acteur1,soldeSA,50,Vendeur.COMMERCE_EQUITABLE);
		this.Acteur2 = new SousActeur(new Journal("JournalActeur2"),Stocks_Acteur2,Prod_Acteur2,soldeSA,135,Vendeur.PRODUCTEUR_LOCAL);
		this.Acteur3 = new SousActeur(new Journal("JournalActeur3"),Stocks_Acteur3,Prod_Acteur3,soldeSA,220,Vendeur.LUXE);
		
		/**
		 * @author Mickaël, Etienne
		 * On initialise les stocks ainsi que la production pour chaque Sous-Acteur.
		 */
				Prod_Acteur1.add(new Indicateur("",this.Acteur1,0));
				Stocks_Acteur1.add(new Indicateur("",this.Acteur1,0));
				ArrayList<Integer> stocks_Acteur1 = new ArrayList<>();
				stocks_Acteur1.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur1.add(new Indicateur("Acteur 1 - stockProduit"+(i+1),this.Acteur1,1000));
					Prod_Acteur1.add(new Indicateur("Acteur 1 - prodProduit"+(i+1),this.Acteur1,1000));
					stocks_Acteur1.add((int)(new Indicateur("Acteur 1 - stockProduit"+(i+1),this.Acteur1,1000).getValeur()));
				}
				this.Acteur1.setVendeur(new Vendeur(stocks_Acteur1));
				
				
			    Prod_Acteur2.add(new Indicateur("",this.Acteur2,0));
				Stocks_Acteur2.add(new Indicateur("",this.Acteur2,0));
				ArrayList<Integer> stocks_Acteur2 = new ArrayList<>();
				stocks_Acteur2.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur2.add(new Indicateur("Acteur 2 - stockProduit"+(i+1),this.Acteur2,1000));
					Prod_Acteur2.add(new Indicateur("Acteur 2 - prodProduit"+(i+1),this.Acteur2,1000));
					stocks_Acteur2.add((int)(new Indicateur("Acteur 2 - stockProduit"+(i+1),this.Acteur2,1000).getValeur()));
				}
				this.Acteur2.setVendeur(new Vendeur(stocks_Acteur2));
				
				
				Prod_Acteur3.add(new Indicateur("",this.Acteur3,0));
				Stocks_Acteur3.add(new Indicateur("",this.Acteur3,0));
				ArrayList<Integer> stocks_Acteur3 = new ArrayList<>();
				stocks_Acteur3.add(0);
				for(int i=1;i<6;i++) {
					Stocks_Acteur3.add(new Indicateur("Acteur 3 - stockProduit"+(i+1),this.Acteur3,1000));
					Prod_Acteur3.add(new Indicateur("Acteur 3 - prodProduit"+(i+1),this.Acteur3,1000));
					stocks_Acteur3.add((int)(new Indicateur("Acteur 3 - stockProduit"+(i+1),this.Acteur3,1000).getValeur()));
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
		contratPoudreEnCoursEq5TRAN_Acteur1[0] = new ContratPoudre(0,0,0.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[0] = new ContratPoudre(0,0,0.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur1[2] = new ContratPoudre(2,0,0.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[2] = new ContratPoudre(2,0,0.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur1.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur1);
		this.Acteur1.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur1);
		
		/**
		 * Acteur 2 labellisé Local
		 */
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur2 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur2 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur2[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
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
		
		//**
		this.contratFevesEnCours=new ArrayList<ContratFeveV3>();
		for(int i=0;i<3;i++) {
			this.contratFevesEnCours.add(new ContratFeveV3((IAcheteurFeveV4)this.Acteur1 , "Eq2PROD" , i )) ;
			this.contratFevesEnCours.add(new ContratFeveV3((IAcheteurFeveV4)this.Acteur2, "Eq3PROD",i));
			}
	
		//On initialise les indicateurs à 1000(arbitraire)

		stockTabBQ_Eq4 = new Indicateur("Eq4 - stockTabBQ",this,this.Acteur1.getStock().get(3)+this.Acteur2.getStock().get(3)+this.Acteur3.getStock().get(3)) ;
		stockTabMQ_Eq4 = new Indicateur("Eq4 - stockTabMQ",this,this.Acteur1.getStock().get(4)+this.Acteur2.getStock().get(4)+this.Acteur3.getStock().get(4)) ;
		stockTabHQ_Eq4 = new Indicateur("Eq4 - stockTabHQ",this,this.Acteur1.getStock().get(5)+this.Acteur2.getStock().get(5)+this.Acteur3.getStock().get(5)) ;
		stockChocMQ_Eq4 = new Indicateur("Eq4 - stockChocMQ",this,this.Acteur1.getStock().get(1)+this.Acteur2.getStock().get(1)+this.Acteur3.getStock().get(1)) ;
		stockChocHQ_Eq4 = new Indicateur("Eq4 - stockChocHQ",this,this.Acteur1.getStock().get(2)+this.Acteur2.getStock().get(2)+this.Acteur3.getStock().get(2)) ;
		prodTabBQ_Eq4 = new Indicateur("Eq4 - prodTabBQ",this,this.Acteur1.getProduction().get(3).getValeur()+this.Acteur2.getProduction().get(3).getValeur()+this.Acteur3.getProduction().get(3).getValeur()) ;
		prodTabMQ_Eq4 = new Indicateur("Eq4 - prodTabMQ",this,this.Acteur1.getProduction().get(4).getValeur()+this.Acteur2.getProduction().get(4).getValeur()+this.Acteur3.getProduction().get(4).getValeur()) ;
		prodTabHQ_Eq4 = new Indicateur("Eq4 - prodTabHQ",this,this.Acteur1.getProduction().get(5).getValeur()+this.Acteur2.getProduction().get(5).getValeur()+this.Acteur3.getProduction().get(5).getValeur()) ;
		prodChocMQ_Eq4 = new Indicateur("Eq4 - prodChocMQ",this,this.Acteur1.getProduction().get(1).getValeur()+this.Acteur2.getProduction().get(1).getValeur()+this.Acteur3.getProduction().get(1).getValeur()) ;
		prodChocHQ_Eq4 = new Indicateur("Eq4 - prodChocHQ",this,this.Acteur1.getProduction().get(2).getValeur()+this.Acteur2.getProduction().get(2).getValeur()+this.Acteur3.getProduction().get(2).getValeur()) ;

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
		Monde.LE_MONDE.ajouterIndicateur(stockChocMQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockChocHQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabBQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabMQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabHQ_Eq4);
		// Journal pour l'acteur global Eq4Tran ?
		Monde.LE_MONDE.ajouterJournal(this.Acteur1.getJournalEq4());
		Monde.LE_MONDE.ajouterJournal(this.Acteur2.getJournalEq4());
		Monde.LE_MONDE.ajouterJournal(this.Acteur3.getJournalEq4());
		
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
		Eq5TRAN.getDevisPoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre)this.Acteur1);
		Eq7TRAN.getDevisPoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this.Acteur1);
		for (int i=0;i<this.Acteur1.getContratPoudreEnCoursEq5TRAN().length;i++) {
			this.Acteur1.getContratPoudreEnCoursEq5TRAN()[i].setReponse(true);
			this.Acteur1.getContratPoudreEnCoursEq7TRAN()[i].setReponse(true);
		}
		if (1==1) { // Pour l'instant on accepte l'achat sans condition 
			Eq5TRAN.sendReponsePoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre)this.Acteur1);
			Eq7TRAN.sendReponsePoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this.Acteur1);
		}

		ContratPoudre[] contratfinalEq5TRAN = new ContratPoudre[3];
		ContratPoudre[] contratfinalEq7TRAN = new ContratPoudre[3];
		contratfinalEq5TRAN = Eq5TRAN.getEchangeFinalPoudre(this.Acteur1.getContratPoudreEnCoursEq5TRAN(), (IAcheteurPoudre)this.Acteur1);
		contratfinalEq7TRAN = Eq7TRAN.getEchangeFinalPoudre(this.Acteur1.getContratPoudreEnCoursEq7TRAN(), (IAcheteurPoudre)this.Acteur1);

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
		
		//On prend en compte les coûts supplémentaires
		Acteur1.coutsSupplementaires();
		Acteur2.coutsSupplementaires();
		Acteur3.coutsSupplementaires();
		
		// On met à jour le journal des trois acteurs
		journalEq4(this.Acteur1);
		journalEq4(this.Acteur2);
		journalEq4(this.Acteur3);
	}

	public void journalEq4(SousActeur j) {
		j.getJournalEq4().ajouter("Stock des tablettes Basse Qualité = "+j.getQuantite(6));
		j.getJournalEq4().ajouter("Stock des tablettes Moyenne Qualité = "+j.getQuantite(5));
		j.getJournalEq4().ajouter("Stock des tablettes Basse Qualité = "+j.getQuantite(4));
		j.getJournalEq4().ajouter("Stock des chocolats Moyenne Qualité = "+j.getQuantite(3));
		j.getJournalEq4().ajouter("Stock des chocolats Haute Qualité = "+j.getQuantite(2));
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
