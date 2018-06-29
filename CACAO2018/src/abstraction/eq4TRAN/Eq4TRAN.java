package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
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

public class Eq4TRAN implements Acteur, 
ITransformateur, 
IAcheteurFeve,
IVendeurChocoBis,
IAcheteurPoudre,
IVendeurPoudre,
IvendeurOccasionnelChoco{ 

	public Acteur Eq4TRAN ; 

	/** Déclaration des indicateurs pour le Journal
	 *  @author Mickaël
	 */
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
	/** Contrats en cours pour la méthode next interne
	 * 
	 */
	private ContratFeve[] contratFeveEnCours ; 
	/** Initialisation des indicateurs 
	 * 0,
	 */
	public Eq4TRAN() {
		/**
		 * @Mickaël
		 * on construit les acteurs
		 */
		

		this.Acteur1 = new SousActeur(new Journal("JournalActeur1"),
				new Indicateur("solde", this.Acteur1,1000),
				new Indicateur("stockTabBQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("stockTabMQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("stockChocMQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("prodTabBQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("prodTabMQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("prodTabHQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("prodChocMQ_Eq4_Acteur1",this.Acteur1,1000),
				new Indicateur("prodChocHQ_Eq4_Acteur1",this.Acteur1,1000));

		this.Acteur2 = new SousActeur(new Journal("JournalActeur2"),
				new Indicateur("solde_Acteur2", this.Acteur1,1000),
				new Indicateur("stockTabBQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("stockTabMQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("stockChocMQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("prodTabBQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("prodTabMQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("prodTabHQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("prodChocMQ_Eq4_Acteur2",this.Acteur2,1000),
				new Indicateur("prodChocHQ_Eq4_Acteur2",this.Acteur2,1000));

		this.Acteur3 = new SousActeur(new Journal("JournalActeur3"),
				new Indicateur("solde_Acteur3", this.Acteur3,1000),
				new Indicateur("stockTabBQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("stockTabMQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("stockChocMQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("stockTabHQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("prodTabBQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("prodTabMQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("prodTabHQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("prodChocMQ_Eq4_Acteur3",this.Acteur3,1000),
				new Indicateur("prodChocHQ_Eq4_Acteur3",this.Acteur3,1000));
		
		/**
		 * @author Mickaël, Etienne
		 * on initialise le stock total
		 */
		//On crée la liste qui range nos stocks pour chaque acteur.
				ArrayList<Indicateur> Stocks_Acteur1 = new ArrayList<>();
				//On remplit cette liste avec nos stocks
				Stocks_Acteur1.add(new Indicateur("",this.Acteur1,0));
				Stocks_Acteur1.add(this.Acteur1.getStockChocMQ());
				Stocks_Acteur1.add(this.Acteur1.getStockChocHQ());
				Stocks_Acteur1.add(this.Acteur1.getStockTabBQ());
				Stocks_Acteur1.add(this.Acteur1.getStockTabMQ());
				Stocks_Acteur1.add(this.Acteur1.getStockTabHQ());
				this.Acteur1.setStocks(Stocks_Acteur1);;
				// On s'initialise en tant que vendeur
				ArrayList<Integer> stocks = new ArrayList<>();
				stocks.add(0);
				stocks.add((int)this.Acteur1.getStockChocMQ().getValeur());
				stocks.add((int)this.Acteur1.getStockChocHQ().getValeur());
				stocks.add((int)this.Acteur1.getStockTabBQ().getValeur());
				stocks.add((int)this.Acteur1.getStockTabMQ().getValeur());
				stocks.add((int)this.Acteur1.getStockTabHQ().getValeur());
				this.Acteur1.setVendeur(new Vendeur(stocks));
				
				ArrayList<Indicateur> Stocks_Acteur2 = new ArrayList<>();
				//On remplit cette liste avec nos stocks
				Stocks_Acteur1.add(new Indicateur("",this.Acteur2,0));
				Stocks_Acteur1.add(this.Acteur2.getStockChocMQ());
				Stocks_Acteur1.add(this.Acteur2.getStockChocHQ());
				Stocks_Acteur1.add(this.Acteur2.getStockTabBQ());
				Stocks_Acteur1.add(this.Acteur2.getStockTabMQ());
				Stocks_Acteur1.add(this.Acteur2.getStockTabHQ());
				this.Acteur2.setStocks(Stocks_Acteur2);;
				// On s'initialise en tant que vendeur
				ArrayList<Integer> stocks2 = new ArrayList<>();
				stocks.add(0);
				stocks.add((int)this.Acteur2.getStockChocMQ().getValeur());
				stocks.add((int)this.Acteur2.getStockChocHQ().getValeur());
				stocks.add((int)this.Acteur2.getStockTabBQ().getValeur());
				stocks.add((int)this.Acteur2.getStockTabMQ().getValeur());
				stocks.add((int)this.Acteur2.getStockTabHQ().getValeur());
				this.Acteur2.setVendeur(new Vendeur(stocks2));
				
				ArrayList<Indicateur> Stocks_Acteur3 = new ArrayList<>();
				//On remplit cette liste avec nos stocks
				Stocks_Acteur1.add(new Indicateur("",this.Acteur3,0));
				Stocks_Acteur1.add(this.Acteur3.getStockChocMQ());
				Stocks_Acteur1.add(this.Acteur3.getStockChocHQ());
				Stocks_Acteur1.add(this.Acteur3.getStockTabBQ());
				Stocks_Acteur1.add(this.Acteur3.getStockTabMQ());
				Stocks_Acteur1.add(this.Acteur3.getStockTabHQ());
				this.Acteur3.setStocks(Stocks_Acteur3);;
				// On s'initialise en tant que vendeur
				ArrayList<Integer> stocks3 = new ArrayList<>();
				stocks.add(0);
				stocks.add((int)this.Acteur3.getStockChocMQ().getValeur());
				stocks.add((int)this.Acteur3.getStockChocHQ().getValeur());
				stocks.add((int)this.Acteur3.getStockTabBQ().getValeur());
				stocks.add((int)this.Acteur3.getStockTabMQ().getValeur());
				stocks.add((int)this.Acteur3.getStockTabHQ().getValeur());
				this.Acteur3.setVendeur(new Vendeur(stocks3));

		
				
				
				
		/**
		 * @Mickaël
		 * on initialise les contrats
		 */
				
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur1 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur1 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur1[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur1[1] = new ContratPoudre(1,27000,100.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur1[2] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur1, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[2] = new ContratPoudre(2,18000,100.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur1[1] = new ContratPoudre(1,0,100.0, (IAcheteurPoudre)this.Acteur1,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur1.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur1);
		this.Acteur1.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur1);
		
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur2 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur2 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur2[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur2[1] = new ContratPoudre(1,27000,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur2[2] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur2, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur2[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur2,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur2[2] = new ContratPoudre(2,18000,100.0, (IAcheteurPoudre)this.Acteur2,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur2[1] = new ContratPoudre(1,0,100.0, (IAcheteurPoudre)this.Acteur2,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur2.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur2);
		this.Acteur2.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur2);
		
		ContratPoudre[] contratPoudreEnCoursEq7TRAN_Acteur3 = new ContratPoudre[3];
		ContratPoudre[] contratPoudreEnCoursEq5TRAN_Acteur3 = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN_Acteur3[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur3, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur3[1] = new ContratPoudre(1,27000,100.0, (IAcheteurPoudre)this.Acteur3, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN_Acteur3[2] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur3, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur3[0] = new ContratPoudre(0,0,100.0, (IAcheteurPoudre)this.Acteur3,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur3[2] = new ContratPoudre(2,18000,100.0, (IAcheteurPoudre)this.Acteur3,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN_Acteur3[1] = new ContratPoudre(1,0,100.0, (IAcheteurPoudre)this.Acteur3,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);

		this.Acteur3.setContratPoudreEnCoursEq5TRAN(contratPoudreEnCoursEq5TRAN_Acteur3);
		this.Acteur3.setContratPoudreEnCoursEq7TRAN(contratPoudreEnCoursEq7TRAN_Acteur3);
		
		contratFeveEnCours = new ContratFeve[6];
		contratFeveEnCours[0]=new ContratFeve( (IAcheteurFeve)this , (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD") , 0 , 0 , 0 , 0 , 0.0 , 0.0 , 0.0 ,false);
		contratFeveEnCours[1]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD"),1 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[2]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq2PROD"),2 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[3]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),0 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[4]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),1 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);
		contratFeveEnCours[5]=new ContratFeve((IAcheteurFeve)this, (IVendeurFeve)Monde.LE_MONDE.getActeur("Eq3PROD"),2 ,0 ,0 ,0 ,0.0 ,0.0 ,0.0 ,false);


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
		

		// On ajoute nos indicateurs et notre journal de production et de vente dans la fenêtre principale du Monde
		Monde.LE_MONDE.ajouterIndicateur(stockChocMQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockChocHQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabBQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabMQ_Eq4);
		Monde.LE_MONDE.ajouterIndicateur(stockTabHQ_Eq4);
		Monde.LE_MONDE.ajouterJournal(JournalEq4);
		Monde.LE_MONDE.ajouterJournal(getJournalVentes());
	}

	/** Nom de l'acteur
	 */
	@Override
	public String getNom() {
		return "Eq4TRAN";
	}


	public void next() {
		/**
		 *  On récupère les contrats màj par la méthode next du marché
		 *  ?????????????????????????
		 */




		/**
		 * pour chaque contrat on récupère prix et qté
		 */
		/**
		 * pour contrat fève 
		 */
		for(int i = 0 ; i < contratFeveEnCours.length ; i++) {
			/**
			 * Selon la qualité
			 * On récupère les qtés de fèves achetées
			 * Elles sont transformées immédiatement en produits
			 * Les produits sont ajoutés aux stocks
			 * Le coût total de l'achat est retiré au solde
			 */
			if (contratFeveEnCours[i].getReponse()) {
				if(contratFeveEnCours[i].getQualite() == 0) {
					prodTabBQ_Eq4.setValeur(this, contratFeveEnCours[i].getQuantite()); 
					double ancienStockTabBQ = stockTabBQ_Eq4.getValeur() ;
					stockTabBQ_Eq4.setValeur(Eq4TRAN, ancienStockTabBQ + prodTabBQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());
				}
				else if(contratFeveEnCours[i].getQualite() == 1) {
					prodTabMQ_Eq4.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
					double ancienStockTabMQ = stockTabMQ_Eq4.getValeur() ;
					stockTabMQ_Eq4.setValeur(Eq4TRAN, ancienStockTabMQ + prodTabMQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());

				}
				else if(contratFeveEnCours[i].getQualite() == 2) {
					prodTabHQ_Eq4.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
					double ancienStockTabHQ = stockTabHQ_Eq4.getValeur() ;
					stockTabHQ_Eq4.setValeur(Eq4TRAN, ancienStockTabHQ + prodTabMQ_Eq4.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());

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
					prodChocHQ_Eq4.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
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
		
		// On met à jour le journal
		journalEq4();
	}

	public void journalEq4() {
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabBQ_Eq4.getValeur());
		JournalEq4.ajouter("Stock des tablettes Moyenne Qualité = "+stockTabMQ_Eq4.getValeur());
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabHQ_Eq4.getValeur());
		JournalEq4.ajouter("Stock des chocolats Moyenne Qualité = "+stockChocMQ_Eq4.getValeur());
		JournalEq4.ajouter("Stock des chocolats Haute Qualité = "+stockChocHQ_Eq4.getValeur());
		JournalEq4.ajouter("Production des tablettes Basse Qualité = "+prodTabBQ_Eq4.getValeur());
		JournalEq4.ajouter("Production des tablettes Moyenne Qualité = "+prodTabBQ_Eq4.getValeur());
		JournalEq4.ajouter("Production des tablettes Haute Qualité = "+prodTabBQ_Eq4.getValeur());
		JournalEq4.ajouter("Production de chocolats Moyenne Qualité = "+prodChocMQ_Eq4.getValeur());
		JournalEq4.ajouter("Production de chocolats Haute Qualité = "+prodChocHQ_Eq4.getValeur());

	}

	public Journal getJournalVentes() {
		return vendeur.ventes;
	}

	@Override
	public void sell(int q) {
		// TODO Auto-generated method stub

	}

	// Etienne Raveau
	// Getter pour accéder à la liste de nos stocks
	public ArrayList<Indicateur> getStocks(){
		return Stocks;
	}

	//Charles
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		this.contratFeveEnCours=offrePublique;
	}

	//Charles
	@Override
	public ContratFeve[] getDemandePrivee() {
		int[] demande= {13000,70000,25000};
		double[] prixMin= { 100000.0 , 100000.0 , 100000.0 } ;
		int[] min= {-1,-1,-1};
		int[] max= {-1,-1,-1};
		for (int i=0;i<this.contratFeveEnCours.length;i++) {
			int qualite=this.contratFeveEnCours[i].getOffrePublique_Quantite();
			if (this.contratFeveEnCours[i].getOffrePublique_Prix()<prixMin[qualite]) {
				prixMin[qualite]=this.contratFeveEnCours[i].getOffrePublique_Prix();
				if (min[i]!=-1) {
					max[qualite]=i;
				}
				min[i]=this.contratFeveEnCours[i].getQualite();
			}
		}
		for (int j=0;j<3;j++) {
			this.contratFeveEnCours[min[j]].setDemande_Quantite(Math.min(demande[min[j]],this.contratFeveEnCours[min[j]].getOffrePublique_Quantite()/3));
			if (max[j]!=-1) {
				this.contratFeveEnCours[max[j]].setDemande_Quantite(demande[min[j]]-Math.min(demande[min[j]],this.contratFeveEnCours[min[j]].getOffrePublique_Quantite()/3));
			}
		}
		return this.contratFeveEnCours ;
	}


	@Override
	public void sendContratFictif(ContratFeve[] listContrats) {
	}


	//Charles
	@Override
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		this.contratFeveEnCours=offreFinale;
		// TODO Auto-generated method stub

	}

	//Charles
	@Override
	public ContratFeve[] getResultVentes() {
		for (int i=0;i<this.contratFeveEnCours.length;i++) {
			if (this.contratFeveEnCours[i].getProposition_Prix()*this.contratFeveEnCours[i].getProposition_Quantite()<this.solde.getValeur()) {
				this.contratFeveEnCours[i].setReponse(true);
			}
		}
		return this.contratFeveEnCours;
	}

	// Etienne Raveau
	// Getter permettant d'accéder à la quantité disponible d'un produit 
	public int getQuantite(int IDProduit) {
		return vendeur.getQte(IDProduit);
	}

	//Etienne Raveau
	// Setter qui met à jour  l'indicateur de stock pour un certain produit
	public void setQuantite(int IDProduit, int quantite) {
		getStocks().get(IDProduit-1).setValeur(this, quantite);
	}

	/*
	 * @Etienne
	 */
	@Override
	public ArrayList<Integer> getStock() {
		return vendeur.getStock();
	}

	// Etienne Raveau
	@Override
	public GPrix2 getPrix() {
		return vendeur.getPrix();
	}


	//Etienne Raveau
	@Override
	public ArrayList<ArrayList<Integer>> getLivraison(ArrayList<ArrayList<Integer>> commandes) {
		ArrayList<ArrayList<Integer>> livraison = new ArrayList<ArrayList<Integer>>();
		livraison.addAll(vendeur.getLivraison(commandes));  //On remplit notre livraison selon la méthode implémentée dans Vendeur
		//On met à jour nos stocks en fonction de la livraison effectuée
		for(int i=1;i<6;i++) {
			int quantite = getQuantite(i+1)-livraison.get(0).get(i)-livraison.get(1).get(i);
			setQuantite(i+1,quantite);
		}
		double s = 0.0;
		//On calcule la valeur du chiffre d'affaire généré par cette livraison
		for(int i=0;i<2;i++) {
			for(int j=0;j<6;j++) {
				s+=livraison.get(i).get(j)*vendeur.getPrix().getPrixProduit(livraison.get(i).get(j), j+1);
			}
		}
		solde.setValeur(this, s);
		//On met à jour notre solde bancaire
		return livraison;
	}

	@Override
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub

	}

	@Override
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getReponse(DemandeAO d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void envoyerReponse(double quantite, int qualite, int prix) {
		// TODO Auto-generated method stub
		
	}

}
