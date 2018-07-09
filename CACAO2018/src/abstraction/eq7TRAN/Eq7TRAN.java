package abstraction.eq7TRAN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV4;
import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChocoTer;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre; 
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

 
public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IAcheteurFeveV4, IVendeurChocoBis, IvendeurOccasionnelChocoTer {
	
	// 0 = BQ, 1 = MQ, 2 = HQ
	private Indicateur[] stockFeves;
	private Indicateur[] stockPoudre;
	private Indicateur[] stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;
	
	//V2 on est 10 chocolatiers regroupés : on fait des listes d'indicateurs
	
	private List<Indicateur[]> stockFeves2;
	private List<Indicateur[]> stockPoudre2;
	private List<Indicateur[]> stockTablettes2;
	private List<Indicateur> solde2;
	
	
	private Indicateur absenteisme;
	private Indicateur efficacite;
	
	private List<Indicateur> absenteisme2;
	private List<Indicateur> efficacite2;
	
	// Historique
	private int nbNext = 0;
	
	private Indicateur[] prixAchatFeves;
	private Indicateur[] prixVentePoudre;
	private Indicateur[] prixVenteTablettes;
	private Indicateur[] productionPoudreReelle;
	private Indicateur[] productionTablettesReelle;
	private Indicateur[] productionPoudreAttendue;
	private Indicateur[] productionTablettesAttendue;
	
	private List<Indicateur[]> prixAchatFeves2;
	private List<Indicateur[]> prixVentePoudre2;
	private List<Indicateur[]> prixVenteTablettes2;
	private List<Indicateur[]> productionPoudreReelle2;
	private List<Indicateur[]> productionTablettesReelle2;
	private List<Indicateur[]> productionPoudreAttendue2;
	private List<Indicateur[]> productionTablettesAttendue2;
	
	private List<ContratFeveV3> commandesFevePassees;
	private ArrayList<ContratPoudre> commandesPoudrePassees;
	private ArrayList<ArrayList<Integer>> commandesTablettesPassees;
	private List<ContratFeveV3> livraisonsFevePassees;
	private ArrayList<ContratPoudre> livraisonsPoudrePassees;
	private ArrayList<ArrayList<Integer>> livraisonsTablettesPassees;
	
	private List<ContratFeveV3> commandesFeveEnCours;
	private ArrayList<ContratPoudre> commandesPoudreEnCours;
	private ArrayList<ArrayList<Integer>> commandesTablettesEnCours;
	private List<ContratFeveV3> livraisonsFeveEnCours;
	private ArrayList<ContratPoudre> livraisonsPoudreEnCours;
	private ArrayList<ArrayList<Integer>> livraisonsTablettesEnCours;
	private List<ContratFeveV3> offresFevesPubliquesEnCours;
	
	private double[] coutTransformationTablette;
	private double[] coutTransformationPoudre;
	
	private List<double[]> coutTransformationTablette2;
	private List<double[]> coutTransformationPoudre2;
	
	private List<List<ContratFeveV3>> commandesFeveEnCours2;
	private List<ArrayList<ContratPoudre>> commandesPoudreEnCours2;
	private List<ArrayList<ArrayList<Integer>>> commandesTablettesEnCours2;
	private List<List<ContratFeveV3>> livraisonsFeveEnCours2;
	private List<ArrayList<ContratPoudre>> livraisonsPoudreEnCours2;
	private List<ArrayList<ArrayList<Integer>>> livraisonsTablettesEnCours2;
	private List<List<ContratFeveV3>> offresFevesPubliquesEnCours2;

	
	// En disant arbitrairement 
	private Indicateur nombreEmployes;
	
	//Salaire moyen des employes
	private final int SALAIRE_MOYEN = 1500;
	
	//Variable pour les couts transfo
	double cout_transfo;
	
	//facteur proportionnel pour les couts de transformation
	private final double FACTEUR_COUT_TRANSFO = 1;
	private List<Indicateur> nombreEmployes2;

	private final int MOY_TAUX_EFFICACITE_EMPLOYES = 1;
	
	// en tonnes par 2 semaines
	private final int[] MOY_ACHAT_FEVES = {0, 1400, 3200};
	private final int[] MOY_ACHAT_POUDRE = {0, 0, 0};
	private final int[] MOY_VENTE_POUDRE = {0, 0, 1000};
	private final int[] MOY_VENTE_TABLETTE = {0, 1400, 2300};
	
	// en tonnes par 2 semaines
	private final double[] MOY_PROD_POUDRE_PAR_EMPLOYE = {2,2,2};
	private final double[] MOY_PROD_TABLETTE_PAR_EMPLOYE = {2,3,4.4};
	
	
	private final double[] TAUX_TRANSFORMATION_FEVES_POUDRE = {1,1,1};
	private final double[] TAUX_TRANSFORMATION_FEVES_TABLETTES = {0.6, 0.75, 0.95};
	
	private final double TAUX_PRODUCTION_POUDRE = 0.3;
	private final double TAUX_PRODUCTION_TABLETTE = 0.7;
	
	private final int SUM_MOY_VENTE_POUDRE = MOY_VENTE_POUDRE[0]+MOY_VENTE_POUDRE[1]+MOY_VENTE_POUDRE[2];
	private final int SUM_MOY_VENTE_TABLETTES = MOY_VENTE_TABLETTE[0]+MOY_VENTE_TABLETTE[1]+MOY_VENTE_TABLETTE[2];
	
	// en €/tonne
	private final double[] MOY_PRIX_ACHAT_FEVES = {1800, 2100, 2500};
	private final double[] MOY_PRIX_VENTE_POUDRE = {2000,2300,2700}; //en tonnes totalement arbitraire
	private final double[] MOY_PRIX_VENTE_TABLETTE = {3000,3300,3800}; //en tonnes totalement arbitraire
	
	// Stratégie
	private final double MOY_PRIX_FRAIS_ACHAT_FEVES = 0;
	private final double MOY_PRIX_FRAIS_VENTE_FEVES = 0;
	private final double MOY_MARGE_POUDRE = 0.2;
	private final double MOY_MARGE_TABLETTE = 0.2;
	private final double MOY_STOCK_POUDRE_SUR_ENSEMBLE_COMMANDES = 0.1;
	private final double MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES = 0.1;
	
	
	
	private final double[] MOY_PRIX_FRAIS_ACHAT_FEVES2 = {0,0,0,0,0,0,0,0,0,0};
	private final double[] MOY_PRIX_FRAIS_VENTE_FEVES2 = {0,0,0,0,0,0,0,0,0,0};
	private final double[] MOY_MARGE_POUDRE2 = {0.2,0.4,0.35,0.37,0.24,0.29,0.43,0.41,0.37,0.36};
	private final double[] MOY_MARGE_TABLETTE2 = {0.3,0.46,0.45,0.43,0.29,0.36,0.45,0.3,0.45,0.48};
	private final double[] MOY_STOCK_POUDRE_SUR_ENSEMBLE_COMMANDES2 = {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
	private final double[] MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES2 = {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
	

	public Eq7TRAN(Monde monde, String nom) {
		this.nom = nom;
		this.nombreEmployes = new Indicateur("Nombre d'employés : ", this, 1000.0);
		this.stockFeves = new Indicateur[3];
		this.stockPoudre = new Indicateur[3];
		this.stockTablettes = new Indicateur[3];
		this.prixAchatFeves = new Indicateur[3];
		this.prixVentePoudre = new Indicateur[3];
		this.prixVenteTablettes = new Indicateur[3];
		this.productionPoudreReelle = new Indicateur[3];
		this.productionTablettesReelle = new Indicateur[3];
		this.productionPoudreAttendue = new Indicateur[3];
		this.productionTablettesAttendue = new Indicateur[3];
		
		this.coutTransformationPoudre = new double[3];
		this.coutTransformationTablette = new double[3];
		
		this.coutTransformationPoudre2 = new ArrayList<double[]>();
		this.coutTransformationTablette2 = new ArrayList<double[]>();

		this.stockFeves2 = new ArrayList<Indicateur[]>();
		this.stockPoudre2 = new ArrayList<Indicateur[]>();
		this.stockTablettes2 = new ArrayList<Indicateur[]>();
		this.prixAchatFeves2 = new ArrayList<Indicateur[]>();
		this.prixVentePoudre2 = new ArrayList<Indicateur[]>();
		this.prixVenteTablettes2 = new ArrayList<Indicateur[]>();
		this.productionPoudreReelle2 = new ArrayList<Indicateur[]>();
		this.productionTablettesReelle2 = new ArrayList<Indicateur[]>();
		this.productionPoudreAttendue2 = new ArrayList<Indicateur[]>();
		this.productionTablettesAttendue2 = new ArrayList<Indicateur[]>();
		
		this.nombreEmployes2 = new ArrayList<Indicateur>();
		for (int i=0; i<10; i++) {
			nombreEmployes2.add(new Indicateur("Nombre d'employés : ", this, 100.0+Math.pow(-1, Math.round(Math.random()))*((int)Math.random()*40)));
		}
		
		this.commandesFevePassees = new ArrayList<ContratFeveV3>();
		this.commandesPoudrePassees = new ArrayList<ContratPoudre>();
		this.commandesTablettesPassees = new ArrayList<ArrayList<Integer>>();
		this.livraisonsFevePassees = new ArrayList<ContratFeveV3>();
		this.livraisonsPoudrePassees = new ArrayList<ContratPoudre>();
		this.livraisonsTablettesPassees = new ArrayList<ArrayList<Integer>>();
		
		this.commandesFeveEnCours = new ArrayList<ContratFeveV3>();
		this.commandesPoudreEnCours = new ArrayList<ContratPoudre>();
		this.commandesTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.livraisonsFeveEnCours = new ArrayList<ContratFeveV3>();
		this.livraisonsPoudreEnCours = new ArrayList<ContratPoudre>();
		this.livraisonsTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.offresFevesPubliquesEnCours = new ArrayList<ContratFeveV3>();
	
		this.commandesFeveEnCours2 = new ArrayList<List<ContratFeveV3>>(Collections.nCopies(10, new ArrayList<ContratFeveV3>()));
		this.commandesPoudreEnCours2 = new ArrayList<ArrayList<ContratPoudre>>(Collections.nCopies(10, new ArrayList<ContratPoudre>()));
		this.commandesTablettesEnCours2 = new ArrayList<ArrayList<ArrayList<Integer>>>(Collections.nCopies(10, new ArrayList<ArrayList<Integer>>()));
		this.livraisonsFeveEnCours2 = new ArrayList<List<ContratFeveV3>>(Collections.nCopies(10, new ArrayList<ContratFeveV3>()));
		this.livraisonsPoudreEnCours2 = new ArrayList<ArrayList<ContratPoudre>>(Collections.nCopies(10, new ArrayList<ContratPoudre>()));
		this.livraisonsTablettesEnCours2 = new ArrayList<ArrayList<ArrayList<Integer>>>(Collections.nCopies(10, new ArrayList<ArrayList<Integer>>()));
		this.offresFevesPubliquesEnCours2 = new ArrayList<List<ContratFeveV3>>();
		
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		this.absenteisme = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0);
		this.efficacite = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 1.0);
		
		this.solde2 = new ArrayList<Indicateur>(Collections.nCopies(10, new Indicateur("",this)));
		this.absenteisme2 = new ArrayList<Indicateur>(Collections.nCopies(10, new Indicateur("",this)));
		this.efficacite2 = new ArrayList<Indicateur>(Collections.nCopies(10, new Indicateur("",this)));
		
		Indicateur[] stockFeves2i, stockPoudre2i, stockTablettes2i, prixAchatFeves2i, prixVentePoudre2i, prixVenteTablettes2i, productionPoudreReelle2i, productionTablettesReelle2i, productionPoudreAttendue2i, productionTablettesAttendue2i;
		double[] coutTransformationPoudre2i, coutTransformationTablette2i;
		for (int i=0; i<10; i++) {
			solde2.add(new Indicateur(this.getNom()+" a un solde de ", this, 200000.0));
			absenteisme2.add(new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0));
			efficacite2.add(new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 1.0));
			
			coutTransformationPoudre2i = new double[3];
			coutTransformationTablette2i = new double[3];
			
			stockFeves2i = new Indicateur[3];
			stockPoudre2i = new Indicateur[3];
			stockTablettes2i = new Indicateur[3];
			prixAchatFeves2i = new Indicateur[3];
			prixVentePoudre2i = new Indicateur[3];
			prixVenteTablettes2i = new Indicateur[3];
			productionPoudreReelle2i = new Indicateur[3];
			productionTablettesReelle2i = new Indicateur[3];
			productionPoudreAttendue2i = new Indicateur[3];
			productionTablettesAttendue2i = new Indicateur[3];
			for(int j = 0; j < 3; j++) {
				stockFeves2i[j] = new Indicateur(this.getNom()+"."+i+" - STOCK FEVES "+j+" = ", this, 500.0);
				stockPoudre2i[j] = new Indicateur(this.getNom()+"."+i+" - STOCK FEVES "+j+" = ", this, 500.0);
				stockTablettes2i[j] = new Indicateur(this.getNom()+"."+i+" - STOCK FEVES "+j+" = ", this, 500.0);
				prixAchatFeves2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement acheté des fèves au prix de ", this, this.MOY_PRIX_ACHAT_FEVES[j]);
				prixVentePoudre2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement vendu de la poudre au prix de ", this, 0);
				prixVenteTablettes2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement vendu des tablettes au prix de ", this, 0);
				productionPoudreReelle2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement produit une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
				productionTablettesReelle2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement produit une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
				productionPoudreAttendue2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement souhaité produire une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
				productionTablettesAttendue2i[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement souhaité produire une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
				/*this.stockPoudre2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" - STOCK POUDRE "+j+" = ", this, 500.0);
				this.stockTablettes2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" - STOCK TABLETTES "+j+" = ", this, 500.0);
				this.prixAchatFeves2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement acheté des fèves au prix de ", this, this.MOY_PRIX_ACHAT_FEVES[i]);
				this.prixVentePoudre2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement vendu de la poudre au prix de ", this, 0);
				this.prixVenteTablettes2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement vendu des tablettes au prix de ", this, 0);
				this.productionPoudreReelle2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement produit une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
				this.productionTablettesReelle2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement produit une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
				this.productionPoudreAttendue2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement souhaité produire une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
				this.productionTablettesAttendue2.get(i)[j] = new Indicateur(this.getNom()+"."+i+" a dernièrement souhaité produire une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
				*/
			}
			coutTransformationPoudre2.add(coutTransformationPoudre2i);
			coutTransformationTablette2.add(coutTransformationTablette2i);
			stockFeves2.add(stockFeves2i);
			stockPoudre2.add(stockPoudre2i);
			stockTablettes2.add(stockTablettes2i);
			productionPoudreReelle2.add(productionPoudreReelle2i);
			productionTablettesReelle2.add(productionTablettesReelle2i);
			productionPoudreAttendue2.add(productionPoudreAttendue2i);
			productionTablettesAttendue2.add(productionTablettesAttendue2i);
			prixAchatFeves2.add(prixAchatFeves2i);
			prixVentePoudre2.add(prixVentePoudre2i);
			prixVenteTablettes2.add(prixVenteTablettes2i);
			/*
			*/
		}
		
		for(int i = 0; i < 3; i++) {
			this.stockFeves[i] = new Indicateur(this.getNom()+" - STOCK FEVES "+i+" = ", this, 500.0);
			this.stockPoudre[i] = new Indicateur(this.getNom()+" - STOCK POUDRE "+i+" = ", this, 500.0);
			this.stockTablettes[i] = new Indicateur(this.getNom()+" - STOCK TABLETTES "+i+" = ", this, 500.0);
			this.prixAchatFeves[i] = new Indicateur(this.getNom()+" a dernièrement acheté des fèves au prix de ", this, this.MOY_PRIX_ACHAT_FEVES[i]);
			this.prixVentePoudre[i] = new Indicateur(this.getNom()+" a dernièrement vendu de la poudre au prix de ", this, 0);
			this.prixVenteTablettes[i] = new Indicateur(this.getNom()+" a dernièrement vendu des tablettes au prix de ", this, 0);
			this.productionPoudreReelle[i] = new Indicateur(this.getNom()+" a dernièrement produit une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
			this.productionTablettesReelle[i] = new Indicateur(this.getNom()+" a dernièrement produit une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
			this.productionPoudreAttendue[i] = new Indicateur(this.getNom()+" a dernièrement souhaité produire une quantité de poudre de", this, SUM_MOY_VENTE_POUDRE);
			this.productionTablettesAttendue[i] = new Indicateur(this.getNom()+" a dernièrement souhaité produire une quantité de tablettes de", this, SUM_MOY_VENTE_TABLETTES);
		
		}
		
		for(int i = 0; i < 3; i++) {
			Monde.LE_MONDE.ajouterIndicateur(this.stockFeves[i]);
			//Monde.LE_MONDE.ajouterIndicateur(this.stockPoudre[i]);
			Monde.LE_MONDE.ajouterIndicateur(this.stockTablettes[i]);
		}
		
		Indicateur stockFeveTotal= new Indicateur(this.getNom()+" - STOCK FEVES  = ", this, 0);
		Indicateur stockTablettesTotal = new Indicateur(this.getNom()+" - STOCK TABLETTES  = ", this, 0);
		Indicateur stockPoudreTotal = new Indicateur(this.getNom()+" - STOCK POUDRE  = ", this, 0);
		for (int i=0; i<10; i++) {
			for(int j = 0; j < 3; j++) {
			
				stockFeveTotal.setValeur(this,stockFeveTotal.getValeur()+this.stockFeves2.get(i)[j].getValeur());
				stockTablettesTotal.setValeur(this,stockTablettesTotal.getValeur()+this.stockTablettes2.get(i)[j].getValeur());
				//stockPoudreTotal.setValeur(this,stockPoudreTotal.getValeur()+this.stockPoudre2.get(i)[j].getValeur());
			}
		}
		Monde.LE_MONDE.ajouterIndicateur(stockFeveTotal);
		Monde.LE_MONDE.ajouterIndicateur(stockPoudreTotal);
		Monde.LE_MONDE.ajouterIndicateur(stockTablettesTotal);
		
		
		
		this.journal = new Journal("Journal de "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		
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
		this.nbNext++;
		this.calculateAbsenteisme();
		this.calculateTauxEfficacite();
		this.getJournal().ajouter("ABSENTEISME = " + this.getAbsenteisme().getValeur());
		this.getJournal().ajouter("EFFICACITE = " + this.getEfficacite().getValeur());
		
		for (int entrep=0; entrep<10; entrep++) {
			this.calculateAbsenteismeParEntrep(entrep);
			this.calculateTauxEfficaciteParEntrep(entrep);
		}
		
		
		int stockFevesTotal=0;
		int stockPoudreTotal=0;
		int stockTablettesTotal =0;
		for (int entrep=0; entrep<10; entrep++) {
			stockFevesTotal=(int)this.getStockFevesParEntrep(0,entrep).getValeur()+(int)this.getStockFevesParEntrep(1,entrep).getValeur()+(int)this.getStockFevesParEntrep(2,entrep).getValeur();
			stockPoudreTotal=(int)this.getStockPoudreParEntrep(0,entrep).getValeur()+(int)this.getStockPoudreParEntrep(1,entrep).getValeur()+(int)this.getStockPoudreParEntrep(2,entrep).getValeur();
			stockTablettesTotal=(int)this.getStockTablettesParEntrep(0,entrep).getValeur()+(int)this.getStockTablettesParEntrep(1,entrep).getValeur()+(int)this.getStockTablettesParEntrep(2,entrep).getValeur();
		}
		
		this.getJournal().ajouter("STOCK FEVES DEBUT ="+stockFevesTotal+"t");
		this.getJournal().ajouter("STOCK POUDRE DEBUT = " + stockPoudreTotal+"t");
		this.getJournal().ajouter("STOCK TABLETTES DEBUT = " + stockTablettesTotal+"t");
		
		/*
		 Pour le moment, on reçoit des commandes des distributeurs qu'on essaye un maximum de remplir avec notre stock
		 et notre production
		 Puis on fait nos commandes en fèves aux producteurs en fonction des précèdentes commandes en pensant
		 que ce seront les mêmes à la prochaine période
		 Il n'y a pas d'argent en jeu pour le moment, juste une négociation sur les quantités demandées
		 */
		
		// ECHANGE POUDRE
		
		/*GQte[] commandes = new GQte[3];
		GQte commande;
		GQte commandeLivree;
		// Pour le moment le code des autres équipes ne nous fait aucune commande !
		
		for(Acteur Acteur : Monde.LE_MONDE.getActeurs()) {
			// on récupère les commandes des distributeurs en tablettes
			
			if(Acteur instanceof IAcheteurChoco) {
				commande = ((IAcheteurChoco) Acteur).getCommande(null,null).get(2);
				
				// maintenant on livre ces commandes en fonction de notre stock
				// interface bizarre pour le coup
				commandes[0] = commande;
				commandes[1] = commande;
				commandes[2] = commande;
				//commandeLivree = this.getLivraison(commandes);
			}
		}
		// on simule une commande régulière ici
		/*
		commande = new GQte(0, 0, 0, 150,300,600);
		commandes[0] = commande;
		commandes[1] = commande;
		commandes[2] = commande;
		commandeLivree = this.getLivraison(commandes);
		
		// on simule une commande de poudre
		ContratPoudre commande2 = new ContratPoudre(1,300,1.4,this,this,true);
		commandesPoudreEnCours.add(commande2);
		
		this.getJournal().ajouter("COMMANDES FEVES = " +this.getQuantiteFevesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS FEVES = " +this.getQuantiteFevesLivrees()+"t");
		this.getJournal().ajouter("COMMANDES POUDRE = " +this.getQuantitePoudreCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS POUDRE = " +this.getQuantitePoudreLivrees()+"t");
		this.getJournal().ajouter("COMMANDES TABLETTES = " +this.getQuantiteTablettesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS TABLETTES = " +this.getQuantiteTablettesLivrees()+"t");
		this.getJournal().ajouter("TAUX DE PROD POUDRE = " +this.getTauxProductionTablettesPoudre()[0]+"");
		this.getJournal().ajouter("TAUX DE PROD TABLETTES = " +this.getTauxProductionTablettesPoudre()[1]+"");
		
		// Pas d'échange entre transformateur pour le moment
		
		// pour le moment on produit les commandes actuelles, à l'avenir il faudra prévoir les commandes qui suivent
		// maintenant on s'occupe de notre production
	/**
	 * @author boulardmaelle
	 */
		for(int qualite = 0; qualite < 3; qualite++) {
			this.estimateProductionPoudreAttendue(qualite);
			this.estimateProductionTabletteAttendue(qualite);
			this.calculateProductionPoudreReelle(qualite);
			this.calculateProductionTablettesReelle(qualite);
			// production poudre absente pour le moment
		}	
		// on ajoute notre prodution à notre stock
		this.produire();
		
		// maintenant on fait nos commandes aux producteurs
		List<ContratFeveV3> offresPubliques;
		List<ContratFeveV3> offresPubliquesRetenues;
		List<ContratFeveV3> offresPrivees;
		List<ContratFeveV3> offresPriveesRetenues;
		
		List<List<ContratFeveV3>> offresPubliques2;
		List<List<ContratFeveV3>> offresPubliquesRetenues2;
		List<List<ContratFeveV3>> offresPrivees2;
		List<List<ContratFeveV3>> offresPriveesRetenues2;
		/**
		 * @author leofargeas
		 */
		
		// bug dans Eq3, il caste leur objet équipe en IAcheteurFeve alors qu'il ne l'implemente pas
		
		/*for(Acteur Acteur : Monde.LE_MONDE.getActeurs()) {
			// on récupère les commandes des distributeurs en tablettes
			
			if(Acteur instanceof IVendeurFeveV4) {
				offresPubliques = ((IVendeurFeveV4) Acteur).getOffrePubliqueV3();
				if(offresPubliques != null) {
					offresPubliquesRetenues = this.analyseOffresPubliquesFeves(offresPubliques);
					
					if(offresPubliquesRetenues.size() > 0) {
						((IVendeurFeveV4) Acteur).sendDemandePriveeV3(offresPubliquesRetenues);
						
						offresPrivees = ((IVendeurFeveV4) Acteur).getOffreFinaleV3();
						if(offresPrivees != null && offresPrivees.size() > 0) {
							offresPriveesRetenues = this.analyseOffresPriveesFeves(offresPrivees);
							for(ContratFeveV3 contrat : offresPrivees) {
								System.out.println(contrat);
							}
							((IVendeurFeveV4) Acteur).sendResultVentesV3(offresPriveesRetenues);
						}
						
					}
				}
			}
		}*/
		
		//Mise à jour du solde pour les salaires
		
		double totalSalaire=0;
		for (int entrep=0; entrep<10; entrep++) {
			this.getSoldeParEntrep(entrep).setValeur(this, this.getSoldeParEntrep(entrep).getValeur()-(1-this.getAbsenteismeParEntrep(entrep).getValeur()*this.getNombreEmployesParEntrep(entrep).getValeur()*this.SALAIRE_MOYEN));
			totalSalaire+=this.getSoldeParEntrep(entrep).getValeur()-(1-this.getAbsenteismeParEntrep(entrep).getValeur()*this.getNombreEmployesParEntrep(entrep).getValeur()*this.SALAIRE_MOYEN);
		}
		this.getJournal().ajouter("Versement des salaires = "+totalSalaire); 
		
		//Mise à jour du solde pour les coûts de transformation
		cout_transfo=0;
		double cout_total_transfo=0;
		for (int entrep=0; entrep<10; entrep++) {
			for(int qualite=0;qualite<3;qualite++){
				this.calculateCoutTransformationPoudreParEntrep(qualite, entrep);
				this.calculateCoutTransformationTabletteParEntrep(qualite, entrep);
				cout_transfo+=this.getCoutTransformationPoudreParQualite(qualite, entrep)+this.getCoutTransformationTabletteParQualite(qualite,entrep);				
			}
			this.getSoldeParEntrep(entrep).setValeur(this,this.getSoldeParEntrep(entrep).getValeur()-cout_transfo);
			cout_total_transfo+=cout_transfo;
		}
		this.getJournal().ajouter("COUTS TRANSFORMATION = "+cout_total_transfo);
		
		// Fin de la période, on supprime toutes les commandes
		this.getJournal().ajouter("COMMANDES FEVES = " +this.getQuantiteFevesCommandees()+"t");
		this.getJournal().ajouter("COMMANDES POUDRE = " +this.getQuantitePoudreCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS POUDRE = " +this.getQuantitePoudreLivrees()+"t");
		this.getJournal().ajouter("COMMANDES TABLETTES = " +this.getQuantiteTablettesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS TABLETTES = " +this.getQuantiteTablettesLivrees()+"t");
		
		//Affichage du solde
		
		this.getJournal().ajouter("Nouveau solde = "+this.getSolde().getValeur()+"€");
		
		this.resetCommandesEnCours();
	
		this.getJournal().ajouter(" - Estimation production poudre = " + this.getProductionPoudreAttendue(0).getValeur()+"t, "+this.getProductionPoudreAttendue(1).getValeur()+"t, "+this.getProductionPoudreAttendue(2).getValeur()+"t");
		this.getJournal().ajouter(" - Estimation production tablettes = " + this.getProductionTablettesAttendue(0).getValeur()+"t, "+this.getProductionTablettesAttendue(1).getValeur()+"t, "+this.getProductionTablettesAttendue(2).getValeur()+"t");
		this.getJournal().ajouter("PRODUCTION POUDRE REELLE = " + (int)this.getProductionPoudreReelle()[0].getValeur()+"t, "+(int)this.getProductionPoudreReelle()[1].getValeur()+"t, "+(int)this.getProductionPoudreReelle()[2].getValeur()+"t");
		this.getJournal().ajouter("PRODUCTION TABLETTES REELLE = " + (int)this.getProductionTablettesReelle()[0].getValeur()+"t, "+(int)this.getProductionTablettesReelle()[1].getValeur()+"t, "+(int)this.getProductionTablettesReelle()[2].getValeur()+"t");

		this.getJournal().ajouter("STOCK FEVES FIN = " + (int)this.getStockFeves(0).getValeur()+"t, "+(int)this.getStockFeves(1).getValeur()+"t, "+(int)this.getStockFeves(2).getValeur()+"t");
		this.getJournal().ajouter("STOCK POUDRE FIN = " + (int)this.getStockPoudre(0).getValeur()+"t, "+(int)this.getStockPoudre(1).getValeur()+"t, "+(int)this.getStockPoudre(2).getValeur()+"t");
		this.getJournal().ajouter("STOCK TABLETTES FIN = " + (int)this.getStockTablettes(0).getValeur()+"t, "+(int)this.getStockTablettes(1).getValeur()+"t, "+(int)this.getStockTablettes(2).getValeur()+"t");
	
		this.getJournal().ajouter(" - Estimation prix achat feves = " + this.estimatePrixAchatFeves(0)+"€, "+this.estimatePrixAchatFeves(1)+"€, "+this.estimatePrixAchatFeves(2)+"€");
		this.getJournal().ajouter(" - Estimation prix vente poudre = " + this.estimatePrixVentePoudre(0)+"€, "+this.estimatePrixVentePoudre(1)+"€, "+this.estimatePrixVentePoudre(2)+"€");
		
		/*for (int entrep=0; entrep<10; entrep++) {
			this.getJournal().ajouter("Nouveau solde = "+this.getSoldeParEntrep(entrep).getValeur()+"€");
		
			this.resetCommandesEnCours();
		
			this.getJournal().ajouter(" - Estimation production poudre = " + this.getProductionPoudreAttendueParEntrep(0,entrep).getValeur()+"t, "+this.getProductionPoudreAttendueParEntrep(1,entrep).getValeur()+"t, "+this.getProductionPoudreAttendueParEntrep(2,entrep).getValeur()+"t");
			this.getJournal().ajouter(" - Estimation production tablettes = " + this.getProductionTablettesAttendueParEntrep(0,entrep).getValeur()+"t, "+this.getProductionTablettesAttendueParEntrep(1,entrep).getValeur()+"t, "+this.getProductionTablettesAttendueParEntrep(2,entrep).getValeur()+"t");
			this.getJournal().ajouter("PRODUCTION POUDRE REELLE = " + (int)this.getProductionPoudreReelle2().get(entrep)[0].getValeur()+"t, "+(int)this.getProductionPoudreReelle2().get(entrep)[1].getValeur()+"t, "+(int)this.getProductionPoudreReelle2().get(entrep)[2].getValeur()+"t");
			this.getJournal().ajouter("PRODUCTION TABLETTES REELLE = " + (int)this.getProductionTablettesReelle2().get(entrep)[0].getValeur()+"t, "+(int)this.getProductionTablettesReelle2().get(entrep)[1].getValeur()+"t, "+(int)this.getProductionTablettesReelle2().get(entrep)[2].getValeur()+"t");

			this.getJournal().ajouter("STOCK FEVES FIN = " + (int)this.getStockFevesParEntrep(0,entrep).getValeur()+"t, "+(int)this.getStockFevesParEntrep(1,entrep).getValeur()+"t, "+(int)this.getStockFevesParEntrep(2,entrep).getValeur()+"t");
			this.getJournal().ajouter("STOCK POUDRE FIN = " + (int)this.getStockPoudreParEntrep(0,entrep).getValeur()+"t, "+(int)this.getStockPoudreParEntrep(1,entrep).getValeur()+"t, "+(int)this.getStockPoudreParEntrep(2,entrep).getValeur()+"t");
			this.getJournal().ajouter("STOCK TABLETTES FIN = " + (int)this.getStockTablettesParEntrep(0,entrep).getValeur()+"t, "+(int)this.getStockTablettesParEntrep(1,entrep).getValeur()+"t, "+(int)this.getStockTablettesParEntrep(2,entrep).getValeur()+"t");
		
			this.getJournal().ajouter(" - Estimation prix achat feves = " + this.estimatePrixAchatFevesParEntrep(0,entrep)+"€, "+this.estimatePrixAchatFevesParEntrep(1,entrep)+"€, "+this.estimatePrixAchatFevesParEntrep(2,entrep)+"€");
			this.getJournal().ajouter(" - Estimation prix vente poudre = " + this.estimatePrixVentePoudreParEntrep(0,entrep)+"€, "+this.estimatePrixVentePoudreParEntrep(1,entrep)+"€, "+this.estimatePrixVentePoudreParEntrep(2,entrep)+"€");
			this.getJournal().ajouter("-----------------Entreprise suivante-----------------");
		}*/
		this.getJournal().ajouter("-------------------------------------------------------------");
	}

	
	public Indicateur[] getStockFeves() {
		return this.stockFeves;
	}
	public void setStockFeves(Indicateur[] stockFeves) {
		this.stockFeves = stockFeves;
	}
	public void setStockFeves(int value, int qualite) {
		Indicateur[] stockFeves = this.getStockFeves();
		stockFeves[qualite].setValeur(this, value);
		this.setStockFeves(stockFeves);
	}
	
	//Changement dans getStockPoudre pour les 10 acteurs
	public Indicateur[] getStockPoudre() {
		return this.total_quantite(this.getStockPoudre2());
	}
	
	
	public void setStockPoudre(Indicateur[] stockPoudre) {
		this.stockPoudre = stockPoudre;
	}
	/*nouveau code*/
	public List<Indicateur[]> getStockFeves2() { 	  	   		 			 			 	
		return this.stockFeves2; 	  	   		 			 			 	
	} 
	public Indicateur[] getStockFevesParEntrep(int entrep) {
		return this.stockFeves2.get(entrep);
	}
	public void setStockFeves2(List<Indicateur[]> stockFeves) { 	  	   		 			 			 	
		this.stockFeves2 = stockFeves; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setStockFevesParEntrep(int value, int qualite, int entrep) {
		List<Indicateur[]> stockFeves = this.getStockFeves2(); 
		stockFeves.get(entrep)[qualite].setValeur(this, value); 	  	   		 			 			 	
		this.setStockFeves2(stockFeves); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<Indicateur[]> getStockPoudre2() { 	  	   		 			 			 	
		return this.stockPoudre2; 	  	   		 			 			 	
	} 
	public Indicateur[] getStockPoudreParEntrep(int entrep) { 	  	   		 			 			 	
		return this.stockPoudre2.get(entrep); 	  	   		 			 			 	
	}  	  	   		 			 			 	

	public void setStockPoudre2(List<Indicateur[]> stockPoudre) { 	  	   		 			 			 	
		this.stockPoudre2 = stockPoudre; 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	/**
	 * 
	 * @param value
	 * @param qualite
	 * @author boulardmaelle
	 */
	//ancien code//
	public void setStockPoudre(int value, int qualite) {
		Indicateur[] stockPoudre = this.getStockPoudre();
		stockPoudre[qualite].setValeur(this, value);
		this.setStockPoudre(stockPoudre);
	}
	public Indicateur[] getStockTablettes() {
		return this.stockTablettes;
	}
	 	  	   		 			 			 	

	public void setStockTablettes(Indicateur[] stockTablettes) {
		this.stockTablettes = stockTablettes;
	}
	public void setStockTablettes(int value, int qualite) {
		Indicateur[] stockTablettes = this.getStockTablettes();
		stockTablettes[qualite].setValeur(this, value);
		this.setStockTablettes(stockTablettes);
	}
	public Indicateur getSolde() {
		return this.solde;
	}
	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}
	
	/*nouveau code*/
	public void setStockPoudre2(int value, int qualite, int entrep) { 	  	   		 			 			 	
		List<Indicateur[]> stockPoudre = this.getStockPoudre2(); 	  	   		 			 			 	
		stockPoudre.get(entrep)[qualite].setValeur(this, value); 	  	   		 			 			 	
		this.setStockPoudre2(stockPoudre); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<Indicateur[]> getStockTablettes2() { 	  	   		 			 			 	
		return this.stockTablettes2; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setStockTablettes2(List<Indicateur[]> stockTablettes) { 	  	   		 			 			 	
		this.stockTablettes2 = stockTablettes; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setStockTablettes2(int value, int qualite, int entrep) { 	  	   		 			 			 	
		List<Indicateur[]> stockTablettes = this.getStockTablettes2(); 	  	   		 			 			 	
		stockTablettes.get(entrep)[qualite].setValeur(this, value); 	  	   		 			 			 	
		this.setStockTablettes2(stockTablettes); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<Indicateur> getSolde2() { 	  	   		 			 			 	
		return this.solde2; 	  	   		 			 			 	
	}
	public Indicateur getSoldeParEntrep(int entrep) { 	  	   		 			 			 	
		return this.solde2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	public void setSolde2(List<Indicateur> solde) { 	  	   		 			 			 	
		this.solde2 = solde; 	  	   		 			 			 	
	}
	public void setSoldeParEntrep(Indicateur solde, int entrep) { 	  	   		 			 			 	
		this.solde2.set(entrep, solde); 	  	   		 			 			 	
	} 	  	   		 			 			 	


	
	/**
	 * @author boulardmaelle, margauxgrand, bernardjoseph, leofargeas
	 * 
	 */
	/*nouveau code*/
	public Journal getJournal() {
		return this.journal;
	}
	public void setJournal(Journal journal) {
		this.journal = journal;
	}
	public List<Indicateur> getAbsenteisme2() {
		return this.absenteisme2;
	}
	public Indicateur getAbsenteismeParEntrep(int entrep) { 	  	   		 			 			 	
		return this.absenteisme2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	public void setAbsenteisme2(List<Indicateur> absenteisme) {
		this.absenteisme2 = absenteisme;
	}
	public void setAbsenteismeParEntrep(Indicateur absenteisme, int entrep) { 	  	   		 			 			 	
		this.absenteisme2.set(entrep, absenteisme); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	public List<Indicateur> getEfficacite2() {
		return this.efficacite2;
	}
	public void setEfficacite2(List<Indicateur> efficacite) {
		this.efficacite2 = efficacite;
	}
	public Indicateur getEfficaciteParEntrep(int entrep) { 	  	   		 			 			 	
		return this.efficacite2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setEfficaciteParEntrep(Indicateur efficacite, int entrep) { 	  	   		 			 			 	
		this.efficacite2.set(entrep, efficacite); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	public List<Indicateur[]> getPrixVenteTablettes2() {
		return prixVenteTablettes2;
	}
	public void setPrixVenteTablettes2(List<Indicateur[]> prixVenteTablettes) {
		this.prixVenteTablettes2 = prixVenteTablettes;
	}
	public List<Indicateur[]> getPrixVentePoudre2() {
		return prixVentePoudre2;
	}
	public void setPrixVentePoudre2(Indicateur[] prixVentePoudre) {
		this.prixVentePoudre = prixVentePoudre;
	}
	public List<Indicateur[]> getProductionPoudreReelle2() {
		return productionPoudreReelle2;
	}
	public void setProductionPoudreReelle2(List<Indicateur[]> productionPoudreReelle) {
		this.productionPoudreReelle2 = productionPoudreReelle;
	}
	public void setProductionPoudreReelleParEntrep(int productionPoudreReelle, int qualite, int entrep) {
		List<Indicateur[]> productionsReelle = this.getProductionPoudreReelle2();
		productionsReelle.get(entrep)[qualite].setValeur(this, productionPoudreReelle);
	}
	public List<Indicateur[]> getProductionTablettesReelle2() {
		return productionTablettesReelle2;
	}
	public void setProductionTablettesReelle2(List<Indicateur[]> productionTablettesReelle) {
		this.productionTablettesReelle2 = productionTablettesReelle;
	}
	public void setProductionTablettesReelle(int productionTablettesReelle, int qualite, int entrep) {
		List<Indicateur[]> productionsReelle2 = this.getProductionTablettesReelle2();
		productionsReelle2.get(entrep)[qualite].setValeur(this, productionTablettesReelle);
	}
	public List<Indicateur[]> getProductionPoudreAttendue2() {
		return productionPoudreAttendue2;
	}
	/*ancien code*/
	 	  	   		 			 			 	
	public Indicateur getAbsenteisme() { 	  	   		 			 			 	
		return this.absenteisme; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setAbsenteisme(Indicateur absenteisme) { 	  	   		 			 			 	
		this.absenteisme = absenteisme; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur getEfficacite() { 	  	   		 			 			 	
		return this.efficacite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setEfficacite(Indicateur efficacite) { 	  	   		 			 			 	
		this.efficacite = efficacite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur[] getPrixVenteTablettes() { 	  	   		 			 			 	
		return prixVenteTablettes; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setPrixVenteTablettes(Indicateur[] prixVenteTablettes) { 	  	   		 			 			 	
		this.prixVenteTablettes = prixVenteTablettes; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur[] getPrixVentePoudre() { 	  	   		 			 			 	
		return prixVentePoudre; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setPrixVentePoudre(Indicateur[] prixVentePoudre) { 	  	   		 			 			 	
		this.prixVentePoudre = prixVentePoudre; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur[] getProductionPoudreReelle() { 	  	   		 			 			 	
		return productionPoudreReelle; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionPoudreReelle(Indicateur[] productionPoudreReelle) { 	  	   		 			 			 	
		this.productionPoudreReelle = productionPoudreReelle; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionPoudreReelle(int productionPoudreReelle, int qualite) { 	  	   		 			 			 	
		Indicateur[] productionsReelle = this.getProductionPoudreReelle(); 	  	   		 			 			 	
		productionsReelle[qualite].setValeur(this, productionPoudreReelle); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur[] getProductionTablettesReelle() { 	  	   		 			 			 	
		return productionTablettesReelle; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionTablettesReelle(Indicateur[] productionTablettesReelle) { 	  	   		 			 			 	
		this.productionTablettesReelle = productionTablettesReelle; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionTablettesReelle(int productionTablettesReelle, int qualite) { 	  	   		 			 			 	
		Indicateur[] productionsReelle = this.getProductionTablettesReelle(); 	  	   		 			 			 	
		productionsReelle[qualite].setValeur(this, productionTablettesReelle); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur[] getProductionPoudreAttendue() { 	  	   		 			 			 	
		return productionPoudreAttendue; 	  	   		 			 			 	
	} 	  	   		 			 			 	

	/**
	 * toutes les productiosn attentues
	 * @param productionPoudreAttendue
	 * @author leofargeas
	 */
	public void setProductionPoudreAttendue(Indicateur[] productionPoudreAttendue) {
		this.productionPoudreAttendue = productionPoudreAttendue;
	}
	public void setProductionPoudreAttendue(int productionPoudreAttendue, int qualite) {
		Indicateur[] productionsAttendue = this.getProductionPoudreAttendue();
		productionsAttendue[qualite].setValeur(this, productionPoudreAttendue);
	}
	public Indicateur[] getProductionTablettesAttendue() {
		return productionTablettesAttendue;
	}
	public void setProductionTablettesAttendue(Indicateur[] productionTablettesAttendue) {
		this.productionTablettesAttendue = productionTablettesAttendue;
	}
	public void setProductionTablettesAttendue(int productionTablettesAttendue, int qualite) {
		Indicateur[] productionsAttendue = this.getProductionTablettesAttendue();
		productionsAttendue[qualite].setValeur(this, productionTablettesAttendue);
		setProductionTablettesAttendue(productionsAttendue);
	}
	public Indicateur getProductionPoudreAttendue(int qualite) {
		return this.getProductionPoudreAttendue()[qualite];
	}
	public Indicateur getProductionTablettesAttendue(int qualite) {
		return this.getProductionTablettesAttendue()[qualite];
	}
	
	/*nouveau code*/
	public void setProductionPoudreAttendue2(List<Indicateur[]> productionPoudreAttendue) { 	  	   		 			 			 	
		this.productionPoudreAttendue2 = productionPoudreAttendue; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionPoudreAttendueParEntrep(int productionPoudreAttendue, int qualite, int entrep) { 	  	   		 			 			 	
		List<Indicateur[]> productionsAttendue = this.getProductionPoudreAttendue2(); 	  	   		 			 			 	
		productionsAttendue.get(entrep)[qualite].setValeur(this, productionPoudreAttendue); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<Indicateur[]> getProductionTablettesAttendue2() { 	  	   		 			 			 	
		return productionTablettesAttendue2; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionTablettesAttendue2(List<Indicateur[]> productionTablettesAttendue) { 	  	   		 			 			 	
		this.productionTablettesAttendue2 = productionTablettesAttendue; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setProductionTablettesAttendueParEntrep(int productionTablettesAttendue, int qualite, int entrep) { 	  	   		 			 			 	
		List<Indicateur[]> productionsAttendue = this.getProductionTablettesAttendue2(); 	  	   		 			 			 	
		productionsAttendue.get(entrep)[qualite].setValeur(this, productionTablettesAttendue); 	  	   		 			 			 	
		 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur getProductionPoudreAttendueParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return this.productionPoudreAttendue2.get(entrep)[qualite]; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public Indicateur getProductionTablettesAttendueParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return this.productionTablettesAttendue2.get(entrep)[qualite]; 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	
	/**
	 *@author margauxgrand
	 * @param offres
	 */
	
	//ancien code
	public void setOffresFevesPubliquesEnCours(List<ContratFeveV3> offres) {
		this.offresFevesPubliquesEnCours = offres;
	}
	public List<ContratFeveV3> getOffresFevesPubliquesEnCours() {
		return this.offresFevesPubliquesEnCours;
	}
	public Indicateur getNombreEmployes() {
		return this.nombreEmployes;
	}
	public void setNombreEmployes(int n) {
		this.nombreEmployes.setValeur(this, (double)n);
	}
	
	public double[] getCoutTransformationTablette() {
		return this.coutTransformationTablette;
	}
	public double getCoutTransformationTablette(int qualite) {
		return this.coutTransformationTablette[qualite];
	}
	public void setCoutTransformationTablette(double[] cout) {
		this.coutTransformationTablette=cout;
	}
	public void setCoutTransformationTablette(double cout, int qualite) {
		double[] couts = this.coutTransformationTablette;
		couts[qualite] = cout;
		this.setCoutTransformationTablette(couts);
	}
	public double[] getCoutTransformationPoudre() {
		return this.coutTransformationPoudre;
	}
	public double getCoutTransformationPoudre(int qualite) {
		return this.coutTransformationPoudre[qualite];
	}
	public void setCoutTransformationPoudre(double[] cout) {
		this.coutTransformationPoudre=cout;
	}
	public void setCoutTransformationPoudre(double cout, int qualite) {
		double[] couts = this.coutTransformationPoudre;
		couts[qualite] = cout;
		this.setCoutTransformationPoudre(couts);
	}

	/*nouveau code*/
	public void setOffresFevesPubliquesEnCours2(List<List<ContratFeveV3>> offres) { 	  	   		 			 			 	
		this.offresFevesPubliquesEnCours2 = offres; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<List<ContratFeveV3>> getOffresFevesPubliquesEnCours2() { 	  	   		 			 			 	
		return this.offresFevesPubliquesEnCours2; 	  	   		 			 			 	
	} 	
	
	public Indicateur getNombreEmployesParEntrep(int entrep) { 	  	   		 			 			 	
		return this.nombreEmployes2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setNombreEmployesParEntrep(int n, int entrep) { 	  	   		 			 			 	
		this.nombreEmployes2.get(entrep).setValeur(this, (double)n); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	 	  	 
	public int getNombreEmployeTotal() {
		int total=0;
		for (int i=0; i<10; i++) {
			total+=this.getNombreEmployesParEntrep(i).getValeur();
		}
		return total;
	}
	 	 

	public double[] getCoutTransformationTabletteParEntrep(int entrep) {
		return this.coutTransformationTablette2.get(entrep);
	}
	public double getCoutTransformationTabletteParQualite(int qualite, int entrep){
		return this.getCoutTransformationTabletteParEntrep(entrep)[qualite];
	}
	public void setCoutTransformationTabletteParEntrep(double[] cout, int entrep) {
		this.getCoutTransformationTabletteParEntrep(entrep)[0]=cout[0];
		this.getCoutTransformationTabletteParEntrep(entrep)[1]=cout[1];
		this.getCoutTransformationTabletteParEntrep(entrep)[2]=cout[2];
	}
	
	public void setCoutTransformationTabletteParQualite(double cout, int qualite, int entrep) {
		double[] couts = this.coutTransformationTablette2.get(entrep);
		couts[qualite] = cout;
		this.setCoutTransformationTabletteParEntrep(couts, entrep);
	}
	
	public double[] getCoutTransformationPoudreParEntrep(int entrep) {
		return this.coutTransformationPoudre2.get(entrep);
	}
	public double getCoutTransformationPoudreParQualite(int qualite, int entrep) {
		return this.coutTransformationPoudre2.get(entrep)[qualite];
	}
	public void setCoutTransformationPoudreParEntrep(double[] cout, int entrep) {
		this.getCoutTransformationPoudreParEntrep(entrep)[0]=cout[0];
		this.getCoutTransformationPoudreParEntrep(entrep)[1]=cout[1];
		this.getCoutTransformationPoudreParEntrep(entrep)[2]=cout[2];
	}
	public void setCoutTransformationPoudreParQualite(double cout, int qualite, int entrep) {
		double[] couts = this.coutTransformationPoudre2.get(entrep);
		couts[qualite] = cout;
		this.setCoutTransformationPoudreParEntrep(couts, entrep);
	}
	

	
	
	

	/**
	 * @author boulardmaelle
	 * @return
	 */
	public ArrayList<ContratPoudre> getCommandesPoudreEnCours() {
		return this.commandesPoudreEnCours;
	}
	public ArrayList<ArrayList<Integer>> getCommandesTablettesEnCours() {
		return this.commandesTablettesEnCours;
	}
	public List<ContratFeveV3> getCommandesFeveEnCours() {
		return this.commandesFeveEnCours;
	}
	public void setCommandesFeveEnCours(List<ContratFeveV3> offresPrivees) {
		this.commandesFeveEnCours = offresPrivees;
	}
	public void setCommandesTablettesEnCours(ArrayList<ArrayList<Integer>> contrats) {
		this.commandesTablettesEnCours = contrats;
	}
	public void setCommandesPoudreEnCours(ArrayList<ContratPoudre> contrats) {
		this.commandesPoudreEnCours = contrats;
	}
	public ArrayList<ArrayList<Integer>> getLivraisonsTablettesEnCours() {
		return this.livraisonsTablettesEnCours;
	}
	public List<ContratFeveV3> getLivraisonsFeveEnCours() {
		return this.livraisonsFeveEnCours;
	}
	public void setLivraisonsTablettesEnCours(ArrayList<ArrayList<Integer>> contrats) {
		this.livraisonsTablettesEnCours = contrats;
	}
	public ArrayList<ContratPoudre> getLivraisonsPoudreEnCours() {
		return this.livraisonsPoudreEnCours;
	}
	public void setLivraisonsPoudreEnCours(ArrayList<ContratPoudre> contrats) {
		this.livraisonsPoudreEnCours = contrats;
	}
	public void setLivraisonsFevesEnCours(List<ContratFeveV3> contrats) {
		this.livraisonsFeveEnCours = contrats;
	}
	
	/*nouveau code*/
	public ArrayList<ContratPoudre> getCommandesPoudreEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.commandesPoudreEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public ArrayList<ArrayList<Integer>> getCommandesTablettesEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.commandesTablettesEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<ContratFeveV3> getCommandesFeveEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.commandesFeveEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setCommandesFeveEnCours2(List<List<ContratFeveV3>> offresPrivees) { 	  	   		 			 			 	
		this.commandesFeveEnCours2 = offresPrivees; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setCommandesTablettesEnCoursParEntrep(ArrayList<ArrayList<Integer>> contrats, int entrep) { 	  	   		 			 			 	
		this.commandesTablettesEnCours2.set(entrep, contrats); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setCommandesPoudreEnCoursParEntrep(ArrayList<ContratPoudre> contrats, int entrep) { 	  	   		 			 			 	
		this.commandesPoudreEnCours2.set(entrep, contrats); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public ArrayList<ArrayList<Integer>> getLivraisonsTablettesEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.livraisonsTablettesEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public List<ContratFeveV3> getLivraisonsFeveEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.livraisonsFeveEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setLivraisonsTablettesEnCoursParEntrep(ArrayList<ArrayList<Integer>> contrats, int entrep) { 	  	   		 			 			 	
		this.livraisonsTablettesEnCours2.set(entrep, contrats); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public ArrayList<ContratPoudre> getLivraisonsPoudreEnCoursParEntrep(int entrep) { 	  	   		 			 			 	
		return this.livraisonsPoudreEnCours2.get(entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setLivraisonsPoudreEnCoursParEntrep(ArrayList<ContratPoudre> contrats, int entrep) { 	  	   		 			 			 	
		this.livraisonsPoudreEnCours2.set(entrep, contrats); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void setLivraisonsFevesEnCoursParEntrep(List<ContratFeveV3> contrats, int entrep) { 	  	   		 			 			 	
		this.livraisonsFeveEnCours2.set(entrep, contrats); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	
	
	
	
	/**
	 * @author leofargeas
	 */
	
	public void resetCommandesEnCours() {
		this.commandesFevePassees.addAll(this.commandesFeveEnCours);
		this.commandesTablettesPassees.addAll(this.commandesTablettesEnCours);
		this.commandesPoudrePassees.addAll(this.commandesPoudreEnCours);
		this.livraisonsFevePassees.addAll(this.livraisonsFeveEnCours);
		this.livraisonsPoudrePassees.addAll(this.livraisonsPoudreEnCours);
		this.livraisonsTablettesPassees.addAll(this.livraisonsTablettesEnCours);
		
		this.commandesFeveEnCours = new ArrayList<ContratFeveV3>();;
		this.commandesTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.commandesPoudreEnCours = new ArrayList<ContratPoudre>();
		this.livraisonsFeveEnCours =new ArrayList<ContratFeveV3>();;
		this.livraisonsTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.livraisonsPoudreEnCours = new ArrayList<ContratPoudre>();
	}
	public int getQuantiteTablettesCommandees() {
		ArrayList<ArrayList<Integer>> commandes = this.getCommandesTablettesEnCours();
		int quantite = 0;
		for(ArrayList<Integer> commande : commandes) {
			for(int idProduit = 4; idProduit <= 6; idProduit++) {
				quantite += commande.get(idProduit-4); 
			}
		} 
		return quantite;
	}
	public int getQuantitePoudreCommandees() {
		ArrayList<ContratPoudre> commandes = this.getCommandesPoudreEnCours();
		int quantite = 0;
		for(ContratPoudre commande : commandes) {
			quantite += commande.getQuantite();
		}
		return quantite;
	}
	public int getQuantiteFevesCommandees() {
		List<ContratFeveV3> commandes = this.getCommandesFeveEnCours();
		int quantite = 0;
		for(ContratFeveV3 commande : commandes) {
			quantite += commande.getProposition_Quantite();
		}
		return quantite;
	}
	public int getQuantiteTablettesLivrees() {
		ArrayList<ArrayList<Integer>> commandes = this.getLivraisonsTablettesEnCours();
		int quantite = 0;
		for(ArrayList<Integer> commande : commandes) {
			for(int idProduit = 4; idProduit <= 6; idProduit++) {
				quantite += commande.get(idProduit-4);
			}
		}
		return quantite;
	}
	public int getQuantitePoudreLivrees() {
		ArrayList<ContratPoudre> commandes = this.getLivraisonsPoudreEnCours();
		int quantite = 0;
		for(ContratPoudre commande : commandes) {
			quantite += commande.getQuantite();
		}
		return quantite;
	}
	public int getQuantiteFevesLivrees() {
		List<ContratFeveV3> commandes = this.getLivraisonsFeveEnCours();
		int quantite = 0;
		for(ContratFeveV3 commande : commandes) {
			quantite += commande.getProposition_Quantite();
		}
		return quantite;
	}
	/*nouveau code*/
	public void resetCommandesEnCours2() { 	  	   		 			 			 	
		this.commandesFeveEnCours2 = new ArrayList<List<ContratFeveV3>>(); 	  	   		 			 			 	
		this.commandesTablettesEnCours2 = new ArrayList<ArrayList<ArrayList<Integer>>>(); 	  	   		 			 			 	
		this.commandesPoudreEnCours2 = new ArrayList<ArrayList<ContratPoudre>>(); 	  	   		 			 			 	
		this.livraisonsFeveEnCours2 =new ArrayList<List<ContratFeveV3>>();; 	  	   		 			 			 	
		this.livraisonsTablettesEnCours2 = new ArrayList<ArrayList<ArrayList<Integer>>>(); 	  	   		 			 			 	
		this.livraisonsPoudreEnCours2 = new ArrayList<ArrayList<ContratPoudre>>(); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantiteTablettesCommandeesParEntrep(int entrep) { 	  	   		 			 			 	
		ArrayList<ArrayList<Integer>> commandes = this.getCommandesTablettesEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0; 	  	   		 			 			 	 
		for(ArrayList<Integer> commande : commandes) {
			for(int idProduit = 4; idProduit <= 6; idProduit++) { 	  	   		 			 			 	
				quantite += commande.get(idProduit-4); 	  	   		 			 			 	
				} 	
			}
		 	  	   		 			 			 	
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantitePoudreCommandeesParEntrep(int entrep) { 	  	   		 			 			 	
		ArrayList<ContratPoudre> commandes = this.getCommandesPoudreEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0; 	  	   		 			 			 	 
		for (ContratPoudre commande : commandes ) {
			quantite += commande.getQuantite(); 	 
			}
		 	  	   		 			 			 	
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantiteFevesCommandeesParEntrep(int entrep) { 	  	   		 			 			 	
		List<ContratFeveV3> commandes = this.getCommandesFeveEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0; 	  	   		 			 			 	 
		for (ContratFeveV3 commande : commandes) {
			quantite += commande.getProposition_Quantite();
			}
		 	  	   		 			 			 	
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantiteTablettesLivreesParEntrep(int entrep) { 	  	   		 			 			 	
		ArrayList<ArrayList<Integer>> commandes = this.getLivraisonsTablettesEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0; 	  	   		 			 			 	 
		for(ArrayList<Integer> commande:commandes) {
			for(int idProduit = 4; idProduit <= 6; idProduit++) { 	  	   		 			 			 	
				quantite += commande.get(idProduit-4); 	  	   		 			 			 	
			}
			}
		 	  	   		 			 			 	
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantitePoudreLivreesParEntrep(int entrep) { 	  	   		 			 			 	
		ArrayList<ContratPoudre> commandes = this.getLivraisonsPoudreEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0; 
		for(ContratPoudre commande : commandes) { 	  	   		 			 			 	
				quantite += commande.getQuantite(); 	  	   		 			 			 	
		}
		
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public int getQuantiteFevesLivreesParEntrep(int entrep) { 	  	   		 			 			 	
		List<ContratFeveV3> commandes = this.getLivraisonsFeveEnCoursParEntrep(entrep); 	  	   		 			 			 	
		int quantite = 0;
		for(ContratFeveV3 commande : commandes) { 	  	   		 			 			 	
				quantite += commande.getProposition_Quantite(); 	  	   		 			 			 	
		} 
		
		return quantite; 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	/** calculateAbsenteisme
	 * @author boulardmaelle, leofargeas
	 * @return le taux d'absenteisme (entre 0 et 15%)
	 */
	
	public void calculateAbsenteismeParEntrep(int entrep) {
		double oldAbsenteisme = this.getAbsenteisme2().get(entrep).getValeur();
		double newAbsenteisme = this.getAbsenteisme2().get(entrep).getValeur() + Math.pow(-1, Math.round(Math.random()))*0.05*Math.random();
		if(newAbsenteisme < 0 || newAbsenteisme>0.15)
			this.getAbsenteisme2().get(entrep).setValeur(this, oldAbsenteisme);
		else 
			this.getAbsenteisme2().get(entrep).setValeur(this, newAbsenteisme);
	}
	
	public void calculateAbsenteisme() { 	  	   		 			 			 	
		double oldAbsenteisme = this.getAbsenteisme().getValeur(); 	  	   		 			 			 	
		double newAbsenteisme = this.getAbsenteisme().getValeur() + Math.pow(-1, Math.round(Math.random()))*0.05*Math.random(); 	  	   		 			 			 	
		if(newAbsenteisme < 0 || newAbsenteisme>0.15) 	  	   		 			 			 	
			this.getAbsenteisme().setValeur(this, oldAbsenteisme); 	  	   		 			 			 	
		else  	  	   		 			 			 	
			this.getAbsenteisme().setValeur(this, newAbsenteisme); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	/** calculateEfficacite  
	 * @author margauxgrand, boulardmaelle
	 * @return le taux d'efficacite entre 70 et 120
	 */
		  	   		 			 			 	

	
	/** calculateEfficacite  
	 * @author margauxgrand, boulardmaelle
	 * @return le taux d'efficacite entre 70 et 120
	 */

	public void calculateTauxEfficaciteParEntrep(int entrep) { 	  	   		 			 			 	
		double abs = this.getAbsenteisme2().get(entrep).getValeur(); 	  	   		 			 			 	
		int emplPresents= (int) (this.getNombreEmployesParEntrep(entrep).getValeur()*(1-abs)*100/this.getNombreEmployesParEntrep(entrep).getValeur()); 	  	   		 			 			 	
		double efficaciteAbs = emplPresents/3; 	  	   		 			 			 	
		double chancebeautemps = Math.random(); 	  	   		 			 			 	
		double efficaciteTemps; 	  	   		 			 			 	
		double efficaciteJourFerie; 	  	   		 			 			 	
		double efficaciteFinale; 	  	   		 			 			 	
		if (chancebeautemps > 0.5) { 	  	   		 			 			 	
			efficaciteTemps=21; 	  	   		 			 			 	
		} else if (chancebeautemps<0.1) { 	  	   		 			 			 	
			efficaciteTemps=-8; 	  	   		 			 			 	
		} else { 	  	   		 			 			 	
			efficaciteTemps=4; 	  	   		 			 			 	
		} 	  	   		 			 			 	
		int jourFerie=(int) Math.ceil(Math.random()*3); //maximum 3 jours feries 	  	   		 			 			 	
		efficaciteJourFerie= -jourFerie; 	  	   		 			 			 	
		efficaciteFinale= 110-efficaciteAbs+efficaciteTemps+efficaciteJourFerie; //Disons que de base, on a une  	  	   		 			 			 	
		//efficacite de 110%, parce que nos employes sont bons. 	  	   		 			 			 	
		this.getEfficacite2().get(entrep).setValeur(this, efficaciteFinale/100); 	  	   		 			 			 	
		if (efficaciteFinale<60.0 || efficaciteFinale>120.0) { 	  	   		 			 			 	
			this.getEfficacite2().get(entrep).setValeur(this, 95/100); //on met 95% d'efficacite pour repartir sur de bonnes bases 	  	   		 			 			 	
		} 	  	   		 			 			 	
	} 	  	   		 
	
	public void calculateTauxEfficacite() {
		double abs = this.getAbsenteisme().getValeur();
		int emplPresents= (int) (this.getNombreEmployes().getValeur()*(1-abs)*100/this.getNombreEmployes().getValeur());
		double efficaciteAbs = emplPresents/3;
		double chancebeautemps = Math.random();
		double efficaciteTemps;
		double efficaciteJourFerie;
		double efficaciteFinale;
		if (chancebeautemps > 0.5) {
			efficaciteTemps=21;
		} else if (chancebeautemps<0.1) {
			efficaciteTemps=-8;
		} else {
			efficaciteTemps=4;
		}
		int jourFerie=(int) Math.ceil(Math.random()*3); //maximum 3 jours feries
		efficaciteJourFerie= -jourFerie;
		efficaciteFinale= 110-efficaciteAbs+efficaciteTemps+efficaciteJourFerie; //Disons que de base, on a une 
		//efficacite de 110%, parce que nos employes sont bons.
		this.getEfficacite().setValeur(this, efficaciteFinale/100);
		if (efficaciteFinale<60.0 || efficaciteFinale>120.0) {
			this.getEfficacite().setValeur(this, 95/100); //on met 95% d'efficacite pour repartir sur de bonnes bases
		}
	}

	
	/**
	 * @author bernardjoseph margauxgrand
	 * @param qualite
	 * @return
	 */
	
	//Cout_transformation=facteur_cout_transfo*qualite*production
	public double estimateCoutTransformationPoudre(int qualite) {
		return this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionPoudreAttendue()[qualite].getValeur();
	}
	public void calculateCoutTransformationPoudre(int qualite) {
		this.setCoutTransformationPoudre(this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionPoudreReelle()[qualite].getValeur(), qualite);
	}
	public double estimateMargePoudre(int qualite) {
		return this.MOY_MARGE_POUDRE;
	}
	public double estimateCoutTransformationTablette(int qualite) {
		return this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionTablettesAttendue()[qualite].getValeur();
	}
	public void calculateCoutTransformationTablette(int qualite) {
		this.setCoutTransformationTablette(this.FACTEUR_COUT_TRANSFO*(qualite+1)*getProductionTablettesReelle()[qualite].getValeur(), qualite);
	}
	public double estimateMargeTablette(int qualite) {
		return this.MOY_MARGE_TABLETTE;
	}
	public double estimatePrixAchatFeves(int qualite) {
		return this.prixAchatFeves[qualite].getValeur();
	}	
	public double estimatePrixVentePoudre(int qualite) {
		return (estimatePrixAchatFeves(qualite)+estimateCoutTransformationPoudre(qualite))*(1+estimateMargePoudre(qualite));
	}
	public double estimatePrixVenteTablette(int qualite) {
		return (estimatePrixAchatFeves(qualite)+estimateCoutTransformationTablette(qualite))*(1+estimateMargeTablette(qualite));
	}
	
	/*nouveau code*/
	public double estimateCoutTransformationPoudreParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionPoudreAttendue2().get(entrep)[qualite].getValeur(); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void calculateCoutTransformationPoudreParEntrep(int qualite, int entrep) {
		this.setCoutTransformationPoudreParQualite(this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionPoudreReelle2().get(entrep)[qualite].getValeur(), qualite, entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
		  	   		 			 			 	
	public double estimateCoutTransformationTabletteParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return this.FACTEUR_COUT_TRANSFO*(qualite+1)*this.getProductionTablettesAttendue2().get(entrep)[qualite].getValeur(); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public void calculateCoutTransformationTabletteParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		this.setCoutTransformationTabletteParQualite(this.FACTEUR_COUT_TRANSFO*(qualite+1)*getProductionTablettesReelle2().get(entrep)[qualite].getValeur(), qualite, entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	 	  	   		 			 			 	
	public double estimatePrixAchatFevesParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return this.prixAchatFeves2.get(entrep)[qualite].getValeur(); 	  	   		 			 			 	
	}	 	  	   		 			 			 	
	public double estimatePrixVentePoudreParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return (estimatePrixAchatFevesParEntrep(qualite, entrep)+estimateCoutTransformationPoudreParEntrep(qualite, entrep))*(1+estimateMargePoudreParEntrep(qualite,entrep)); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	public double estimatePrixVenteTabletteParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		return (estimatePrixAchatFevesParEntrep(qualite, entrep)+estimateCoutTransformationTabletteParEntrep(qualite, entrep))*(1+estimateMargeTabletteParEntrep(qualite,entrep)); 	  	   		 			 			 	
	} 	  	   		 			 			 	

	public double estimateMargePoudreParEntrep(int qualite, int entrep) {
		return this.MOY_MARGE_POUDRE2[entrep];
	}
	
	public double estimateMargeTabletteParEntrep(int qualite, int entrep) {
		return this.MOY_MARGE_TABLETTE2[entrep];
	}
	
	
	
	//////////////////////////////////////
	// METHODES VENDEUR POUDRE&CHOCOLAT //
	/////////////////////////////////////
	
	/**
	 * 
	 * 
	 * @author bernardjoseph  
	 * @param qualite
	 */
	
	// retourne un tableau de tableau avec le taux de transformation de tablette en indice 1 et poudre indice 0, la qualité appararait 
	public double[] getTauxProductionTablettesPoudre() {
		int sommePoudre = this.getQuantitePoudreCommandees();
		int sommeTablette = this.getQuantiteTablettesCommandees();
		int sommeTotale=sommePoudre+sommeTablette;
		double tauxPoudre;
		double tauxTablette;
		double[] TauxFinauxTetP= new double[2];
		
		if (sommeTotale==0) {
			tauxPoudre=TAUX_PRODUCTION_POUDRE;
			tauxTablette=TAUX_PRODUCTION_TABLETTE;
		} else {
			tauxTablette=((double)sommeTablette)/((double)sommeTotale);
			tauxPoudre=((double)sommePoudre)/((double)sommeTotale);
		}
		TauxFinauxTetP[0]=tauxPoudre;
		TauxFinauxTetP[1]=tauxTablette;
		
		return TauxFinauxTetP;
	}
	
	public void estimateProductionPoudreAttendue(int qualite) {
		int productionAttendue = 0;
		ArrayList<ContratPoudre> commandesEnCours = this.getCommandesPoudreEnCours();
		for(int i = 0; i < commandesEnCours.size(); i++) {
			if(commandesEnCours.get(i).getQualite() == qualite) {
				productionAttendue += commandesEnCours.get(i).getQuantite();
			}
		}
		this.setProductionPoudreAttendue((int)(productionAttendue*(1+MOY_STOCK_POUDRE_SUR_ENSEMBLE_COMMANDES)), qualite);
	}
	
	public void calculateProductionPoudreReelle(int qualite) {
		double productionPossible = this.getStockFeves(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite])*getTauxProductionTablettesPoudre()[0]*getEfficacite().getValeur();
		double productionAttendue = this.getProductionPoudreAttendue(qualite).getValeur();
		this.getProductionPoudreReelle()[qualite].setValeur(this, (productionAttendue < productionPossible) ? productionAttendue : productionPossible);
	}
	
	public void estimateProductionTabletteAttendue(int qualite) {
		int productionAttendue = 0;
		ArrayList<ArrayList<Integer>> commandesEnCours = this.getCommandesTablettesEnCours();
		for(int i = 0; i < commandesEnCours.size(); i++) {
			for(int idDist = 0; idDist < 2; idDist++) {
				productionAttendue += commandesEnCours.get(idDist).get(3+qualite);
			}
		}
		//productionAttendue -= (int)this.getStockTablettes(qualite).getValeur();
		this.setProductionTablettesAttendue((int)(productionAttendue*(1+MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES)), qualite);
	}
	 
	public void calculateProductionTablettesReelle(int qualite) {
		double productionPossible = this.getStockFeves(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite])*getTauxProductionTablettesPoudre()[1]*getEfficacite().getValeur(); 
		double productionAttendue = this.getProductionTablettesAttendue(qualite).getValeur();
		this.getProductionTablettesReelle()[qualite].setValeur(this,(productionAttendue < productionPossible) ? productionAttendue : productionPossible);
	}

	
	public void produire() {
		for(int qualite = 0; qualite < 3; qualite++) {
			this.setStockTablettes((int)(this.getStockTablettes(qualite).getValeur()+this.getProductionTablettesReelle()[qualite].getValeur()), qualite);
			this.setStockPoudre((int)(this.getStockPoudre(qualite).getValeur()+this.getProductionPoudreReelle()[qualite].getValeur()), qualite);
			this.setStockFeves((int)(this.getStockFeves(qualite).getValeur()-this.getProductionTablettesReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]-this.getProductionPoudreReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]), qualite);
		}
	}
	/*nouveau code*/
	public double[] getTauxProductionTablettesPoudreParEntrep(int entrep) { 	  	   		 			 			 	
		int sommePoudre = this.getQuantitePoudreCommandeesParEntrep(entrep); 	  	   		 			 			 	
		int sommeTablette = this.getQuantiteTablettesCommandeesParEntrep(entrep); 	  	   		 			 			 	
		int sommeTotale=sommePoudre+sommeTablette; 	  	   		 			 			 	
		double tauxPoudre; 	  	   		 			 			 	
		double tauxTablette; 	  	   		 			 			 	
		double[] TauxFinauxTetP= new double[2]; 	  	   		 			 			 	
		 	  	   		 			 			 	
		if (sommeTotale==0) { 	  	   		 			 			 	
			tauxPoudre=TAUX_PRODUCTION_POUDRE; 	  	   		 			 			 	
			tauxTablette=TAUX_PRODUCTION_TABLETTE; 	  	   		 			 			 	
		} else { 	  	   		 			 			 	
			tauxTablette=((double)sommeTablette)/((double)sommeTotale); 	  	   		 			 			 	
			tauxPoudre=((double)sommePoudre)/((double)sommeTotale); 	  	   		 			 			 	
		} 	  	   		 			 			 	
		TauxFinauxTetP[0]=tauxPoudre; 	  	   		 			 			 	
		TauxFinauxTetP[1]=tauxTablette; 	  	   		 			 			 	
		return TauxFinauxTetP; 	  	   		 			 			 	
	} 	  	   		 			 			 	
	 	  	   		 			 			 	
	public void estimateProductionPoudreAttendueParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		int productionAttendue = 0; 	  	   		 			 			 	
		ArrayList<ContratPoudre> commandesEnCours = this.getCommandesPoudreEnCoursParEntrep(entrep); 	  	   		 			 			 	
		for(int i = 0; i < commandesEnCours.size(); i++) { 	  	   		 			 			 	
			if(commandesEnCours.get(i).getQualite() == qualite) { 	  	   		 			 			 	
				productionAttendue += commandesEnCours.get(i).getQuantite(); 	  	   		 			 			 	
			} 	  	   		 			 			 	
		} 	  	   		 			 			 	
		this.setProductionPoudreAttendueParEntrep((int)(productionAttendue*(1+MOY_STOCK_POUDRE_SUR_ENSEMBLE_COMMANDES)), qualite, entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	 	  	   		 			 			 	
	public void calculateProductionPoudreReelleParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		double productionPossible = this.getStockFeves2().get(entrep)[qualite].getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite])*getTauxProductionTablettesPoudreParEntrep(entrep)[0]*getEfficaciteParEntrep(entrep).getValeur(); 	  	   		 			 			 	
			this.getProductionPoudreReelle2().get(entrep)[qualite].setValeur(this, productionPossible); 	  	   		 			 			 	
		 	  	   		 			 			 	
	} 	  	   		 			 			 	
	 	  	   		 			 			 	
	public void estimateProductionTabletteAttendueParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		int productionAttendue = 0; 	  	   		 			 			 	
		ArrayList<ArrayList<Integer>> commandesEnCours = this.getCommandesTablettesEnCoursParEntrep(entrep); 	  	   		 			 			 	
		for(int i = 0; i < commandesEnCours.size(); i++) { 	  	   		 			 			 	
			for(int idDist = 0; idDist < 2; idDist++) { 	  	   		 			 			 	
				productionAttendue += commandesEnCours.get(idDist).get(4+qualite); 	  	   		 			 			 	
			} 	  	   		 			 			 	
		} 	  	   		 			 			 	
		//productionAttendue -= (int)this.getStockTablettes(qualite).getValeur(); 	  	   		 			 			 	
		this.setProductionTablettesAttendueParEntrep((int)(productionAttendue*(1+MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES)), qualite, entrep); 	  	   		 			 			 	
	} 	  	   		 			 			 	
	  	  	   		 			 			 	
	public void calculateProductionTablettesReelleParEntrep(int qualite, int entrep) { 	  	   		 			 			 	
		double productionPossible = this.getStockFeves2().get(entrep)[qualite].getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite])*getTauxProductionTablettesPoudreParEntrep(entrep)[1]*getEfficaciteParEntrep(entrep).getValeur();  	  	   		 			 			 	
			this.getProductionTablettesReelle2().get(entrep)[qualite].setValeur(this,productionPossible); 	  	   		 			 			 	
	} 	  	   		 			 			 	
 	  	   		 			 			 	
	 	  	   		 			 			 	
	public void produireParEntrep(int entrep) { 	  	   		 			 			 	
		for(int qualite = 0; qualite < 3; qualite++) { 	  	   		 			 			 	
			this.setStockTablettes2((int)(this.getStockTablettes2().get(entrep)[qualite].getValeur()+this.getProductionTablettesReelle2().get(entrep)[qualite].getValeur()), qualite, entrep); 	  	   		 			 			 	
			this.setStockPoudre2((int)(this.getStockPoudre2().get(entrep)[qualite].getValeur()+this.getProductionPoudreReelle2().get(entrep)[qualite].getValeur()), qualite, entrep); 	  	   		 			 			 	
			this.setStockFevesParEntrep((int)(this.getStockFeves2().get(entrep)[qualite].getValeur()-this.getProductionTablettesReelle2().get(entrep)[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]-this.getProductionPoudreReelle2().get(entrep)[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]), qualite, entrep); 	  	   		 			 			 	
		} 	  	   		 			 			 	
	} 	  	   		 			 			 	

	
	/**
	 * @author leofargeas
	 * @param qualite
	 * @return
	 */
	//ancien code//
	public Indicateur getStockFeves(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockFeves()[qualite];
	} 
	public Indicateur getStockPoudre(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockPoudre()[qualite];
	}
	public Indicateur getStockTablettes(int qualite) {
		if(qualite < 0 || qualite > 3) 
			return null; 
		return this.getStockTablettes()[qualite];
	}
	
	//à écrire plus tard (faire version 2 par entreprise en dessous)
	public Indicateur getStockPrevisionnel(int qualite) {
		int tonnes = 0;
		return absenteisme;
	}
	
	//nouveau code//
	public Indicateur getStockFevesParEntrep(int qualite, int entrep) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockFeves2().get(entrep)[qualite];
	} 
	public Indicateur getStockPoudreParEntrep(int qualite, int entrep) {
		if(qualite < 0 || qualite > 3) 
			return null;
		return this.getStockPoudre2().get(entrep)[qualite];
	}
	public Indicateur getStockTablettesParEntrep(int qualite, int entrep) {
		if(qualite < 0 || qualite > 3) 
			return null; 
		return this.getStockTablettes2().get(entrep)[qualite];
	}
	
	// à écrire 
	public Indicateur getStockPrevisionnelParEntrep(int qualite, int entrep) {
		int tonnes = 0;
		return absenteisme;
	}
	
	/////////////////////////////
	// METHODES VENDEUR POUDRE //
	/////////////////////////////
	
	/** 
	 * Interface IVendeurPoudre
	 * @author boulardmaelle margauxgrand bernardjoseph
	 */
	
	
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		ContratPoudre[] catalogue=new ContratPoudre[3];
		for(Indicateur[] prix_vente_entrep:this.prixVentePoudre2) {
			for(int qualite=0;qualite<3;qualite++) {
				catalogue[qualite] = new ContratPoudre(qualite,(int)this.getStockPoudre(qualite).getValeur(), prix_vente_entrep[qualite].getValeur(),acheteur,(IVendeurPoudre)this,false);
			}
		}
		return catalogue;
	}
	
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur) {
		int n = demande.length;
		for(int i = 0; i<n; i++) {
			if(demande[i] != null) {
				int qualite = demande[i].getQualite();
				// Si on a pas la bonne quantité on refuse
				if(demande[i].getQuantite() > this.getStockPoudre()[qualite].getValeur()) {
					demande[i].setQuantite((int)this.getStockPoudre()[qualite].getValeur());
					//demande[i].setReponse(false);
				}
		/*
		for(ContratPoudre demande_i:demande) {
			int qualite=demande_i.getQualite();
			double prix=demande_i.getPrix();
			List<Integer> demande_i_parEntrep=this.repart(demande_i.getQuantite());
			int total=0;
			for(int acteur10=0;acteur10<10;acteur10++) {
				if (demande_i_parEntrep.get(acteur10)>this.getStockPoudre2().get(acteur10)[qualite].getValeur())
					demande_i_parEntrep.set(acteur10, (int)(this.getStockPoudre2().get(acteur10)[qualite].getValeur()));
				this.getCommandesPoudreEnCoursParEntrep(acteur10).add(new ContratPoudre(qualite,
						demande_i_parEntrep.get(acteur10),prix,acheteur,this,false));
				total+=demande_i_parEntrep.get(acteur10);
			
			}
			demande_i=new ContratPoudre(qualite,total,prix,acheteur,this,false);
		*/
			}
		}
		return demande;
	}
	
	//on ne peut pas mettre à jour chacune des 10 commandes en cours sans connaitre la répartition
	public void sendReponsePoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		for(int qualite = 0; qualite<3; qualite++) {
			if(contrat[qualite].getReponse())
				this.getCommandesPoudreEnCours().add(contrat[qualite]);
		}
	}
	
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		// est-ce qu'il a eu des probs pour la réalisation du contrat ?
		//Mise à jour du solde
		for(ContratPoudre livraison:contrat) {
			if(livraison != null) {
				if (livraison.getReponse()==true) {
					this.getJournal().ajouter(livraison.toString());
					this.getSolde().setValeur(this, this.getSolde().getValeur()+this.getPrixVentePoudre()[livraison.getQualite()].getValeur()*livraison.getQuantite());
					this.getStockPoudre(livraison.getQualite()).setValeur(this, this.getStockPoudre(livraison.getQualite()).getValeur()-livraison.getQuantite());
					this.getLivraisonsPoudreEnCours().add(livraison);
				}
			}
		}
		
		return contrat;
	} 
	
	////////////////////////////
	// METHODES ACHETEUR FEVE //
	////////////////////////////
	
	/**
	 * @author margauxgrand (+ boulardmaelle et leofargeas)
	 * @param offresPubliques2
	 * @return
	 */
	
	public int getMaximumOfProduction(int type, int qualite) {
		int[] productionTotaleMax = new int[3];
		int[] productionPoudreMax = new int[3];
		int[] productionTabletteMax = new int[3];
		//int[] productionTotaleMin = new int[3];
		//int[] productionPoudreMin = new int[3];
		//int[] productionTabletteMin = new int[3];
			
		productionPoudreMax[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]*this.MOY_PROD_POUDRE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
		productionTabletteMax[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]*this.MOY_PROD_TABLETTE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
		productionTotaleMax[qualite] = Math.max(productionPoudreMax[qualite], productionTabletteMax[qualite]);
		//productionPoudreMin[qualite] = (int) this.getProductionPoudreAttendue(qualite).getValeur();
		//productionTabletteMin[qualite] = (int) this.getProductionTablettesAttendue(qualite).getValeur();
		//productionTotaleMin[qualite] = productionPoudreMin[qualite] + productionTabletteMin[qualite];
		
		if(type == 0)
			return productionPoudreMax[qualite];
		else if(type == 1)
			return productionTabletteMax[qualite];
		else
			return productionTotaleMax[qualite];
	}
	
	public int getMaximumOfProductionParEntrep(int type, int qualite, int entrep) {
		List<int[]> productionTotaleMax = new ArrayList<int[]>(3);
		List<int[]> productionPoudreMax = new ArrayList<int[]>(3);
		List<int[]> productionTabletteMax = new ArrayList<int[]>(3);
		//int[] productionTotaleMin = new int[3];
		//int[] productionPoudreMin = new int[3];
		//int[] productionTabletteMin = new int[3];
		
			productionPoudreMax.get(entrep)[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]*this.MOY_PROD_POUDRE_PAR_EMPLOYE[qualite]*this.getNombreEmployesParEntrep(entrep).getValeur()*this.getEfficaciteParEntrep(entrep).getValeur());
			productionTabletteMax.get(entrep)[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]*this.MOY_PROD_TABLETTE_PAR_EMPLOYE[qualite]*this.getNombreEmployesParEntrep(entrep).getValeur()*this.getEfficaciteParEntrep(entrep).getValeur());
			productionTotaleMax.get(entrep)[qualite] = Math.max(productionPoudreMax.get(entrep)[qualite], productionTabletteMax.get(entrep)[qualite]);
			//productionPoudreMin[qualite] = (int) this.getProductionPoudreAttendue(qualite).getValeur();
			//productionTabletteMin[qualite] = (int) this.getProductionTablettesAttendue(qualite).getValeur();
			//productionTotaleMin[qualite] = productionPoudreMin[qualite] + productionTabletteMin[qualite];
			
			if(type == 0)
				return productionPoudreMax.get(entrep)[qualite];
			else if(type == 1)
				return productionTabletteMax.get(entrep)[qualite];
			else
				return productionTotaleMax.get(entrep)[qualite];
		
	}
	
	
	

	
	
	public List<ContratFeveV3> analyseOffresPubliquesFeves(List<ContratFeveV3> offresPubliques2) {
		// on boucle sur les offres publiques et on vérifie pour le 
		// moment seulement qu'on peut tout produire avec nos employés
		List<ContratFeveV3> offresPubliques = new ArrayList<ContratFeveV3>(offresPubliques2);
		List<ArrayList<ContratFeveV3>> offresRetenuesParQualite = new ArrayList<ArrayList<ContratFeveV3>>();
		int[] quantitesAttenduesParQualite = new int[3];
		for(int qualite = 0; qualite < 3; qualite++) {
			offresRetenuesParQualite.add(new ArrayList<ContratFeveV3>());
			quantitesAttenduesParQualite[qualite] = (int) (this.getProductionPoudreAttendue(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]) + this.getProductionTablettesAttendue(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]));
 		}
		
		Collections.sort(offresPubliques, new Comparator<ContratFeveV3>() {
	        public int compare(ContratFeveV3 contrat1, ContratFeveV3 contrat2)
	        {
	            double resultat = contrat1.getOffrePublique_Prix()/contrat1.getOffrePublique_Quantite() - contrat2.getOffrePublique_Prix()/contrat2.getOffrePublique_Quantite();
	            return (int) ((resultat > 0) ? Math.ceil(resultat) : Math.floor(resultat));
	        }
	    });
		
		
		int[] sommeQuantiteOffresRetenuesParQualite = new int[3];
		int[] n = new int[3];
		boolean stopLoop = false;
		for(ContratFeveV3 offre : offresPubliques) {
			if(!stopLoop) {
				offre.setTransformateur(this);
				
				/*
				if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] >= productionTotaleMin[offre.getQualite()]) {
					sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getOffrePublique_Quantite();
					System.out.println(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()]+" "+productionTotaleMax[offre.getQualite()]);
					if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] < productionTotaleMax[offre.getQualite()]) {
						offre.setDemande_Quantite(offre.getOffrePublique_Quantite() - (productionTotaleMin[offre.getQualite()] - offre.getOffrePublique_Quantite()));
					}
					offresRetenuesParQualite.get(offre.getQualite()).add(offre);
					n[offre.getQualite()]++;
				}*/
				sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getOffrePublique_Quantite();
				if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] > quantitesAttenduesParQualite[offre.getQualite()]) {
					offre.setDemande_Quantite(offre.getOffrePublique_Quantite() + quantitesAttenduesParQualite[offre.getQualite()] - sommeQuantiteOffresRetenuesParQualite[offre.getQualite()]);
					sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getDemande_Quantite() - offre.getOffrePublique_Quantite();
					if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] > getMaximumOfProduction(3,offre.getQualite())) {
						offre.setDemande_Quantite(offre.getDemande_Quantite() + getMaximumOfProduction(3,offre.getQualite()) - sommeQuantiteOffresRetenuesParQualite[offre.getQualite()]);
						sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getDemande_Quantite() - offre.getOffrePublique_Quantite();
						stopLoop = true;
					}
				}
				else if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] > getMaximumOfProduction(3,offre.getQualite())) {
					offre.setDemande_Quantite(offre.getOffrePublique_Quantite() + getMaximumOfProduction(3,offre.getQualite()) - sommeQuantiteOffresRetenuesParQualite[offre.getQualite()]);
					sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getDemande_Quantite() - offre.getOffrePublique_Quantite();
					stopLoop = true;
				}
				else {
					offre.setDemande_Quantite(offre.getOffrePublique_Quantite());
					sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getDemande_Quantite() - offre.getOffrePublique_Quantite();
				}
				
				offresRetenuesParQualite.get(offre.getQualite()).add(offre);
				n[offre.getQualite()]++;
			}
		}
		
		List<ContratFeveV3> offresRetenues = new ArrayList<ContratFeveV3>(); 
		for(int qualite = 0; qualite < 3; qualite++) {
			for(int i = 0; i < n[qualite]; i++) {
				if(offresRetenuesParQualite.get(qualite).get(i).getDemande_Quantite() > 0)
					offresRetenues.add(offresRetenuesParQualite.get(qualite).get(i));
			} 
		}
		
		
		return offresRetenues;
	}
	
	public List<ContratFeveV3> analyseOffresPriveesFeves(List<ContratFeveV3> offresPrivees) {		
		// A mettre dans des méthodes séparées
		int[] productionTotaleMax = new int[3];
		int[] productionPoudreMax = new int[3];
		int[] productionTabletteMax = new int[3];
		int[] productionTotaleMin = new int[3];
		int[] productionPoudreMin = new int[3];
		int[] productionTabletteMin = new int[3];
		for(int qualite = 0; qualite<3; qualite++) {
			productionPoudreMax[qualite] = (int) (this.MOY_PROD_POUDRE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
			productionTabletteMax[qualite] = (int) (this.MOY_PROD_TABLETTE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
			productionTotaleMax[qualite] = Math.max(productionPoudreMax[qualite], productionTabletteMax[qualite]);
			productionPoudreMin[qualite] = (int) this.getProductionPoudreAttendue(qualite).getValeur();
			productionTabletteMin[qualite] = (int) this.getProductionTablettesAttendue(qualite).getValeur();
			productionTotaleMin[qualite] = productionPoudreMin[qualite] + productionTabletteMin[qualite];
		}
		
		int[] sommeQuantiteOffresPriveesParQualite = new int[3];
		int[] n = new int[3];
		for(ContratFeveV3 offre : offresPrivees) {			
			// Pour le moment on accepte tout
			offre.setReponse(true);
		}
		this.setCommandesFeveEnCours(offresPrivees);
		return offresPrivees;
	}
		
	/**
	 * Interface IAcheteurFeve
	 * @author boulardmaelle
	 */
	
	@Override
	public void sendOffrePubliqueV3(List<ContratFeveV3> offresPubliques) {
		// On garde les anciennes offres ?
		/*
		ContratFeve[] oldOffres = this.getOffresFevesPubliquesEnCours();
		int n = oldOffres.length;
		*/
		this.setOffresFevesPubliquesEnCours(offresPubliques);
	}
	
	/**
	 * Code à recopier dans la V4
	 */
	@Override
	public List<ContratFeveV3> getDemandePriveeV3() {
		List<ContratFeveV3> offresPubliques = this.getOffresFevesPubliquesEnCours();
		return this.analyseOffresPubliquesFeves(offresPubliques);
	}
	
	@Override
	public void sendContratFictifV3(List<ContratFeveV3> listContrats) {
	
	}
	
	@Override
	public void sendOffreFinaleV3(List<ContratFeveV3> offresFinales) {
		this.setCommandesFeveEnCours(this.analyseOffresPriveesFeves(offresFinales));
	}
	@Override
	public List<ContratFeveV3> getResultVentesV3() {
		
		//On met à jour le solde dès que cette methode est appelee
		for(ContratFeveV3 contrat:this.getCommandesFeveEnCours()) {
			if (contrat.getReponse()==true) {
				this.getSolde().setValeur(this,this.getSolde().getValeur()-contrat.getProposition_Prix()*contrat.getProposition_Quantite());
				this.setStockFeves((int)this.getStockFeves(contrat.getQualite()).getValeur()+contrat.getProposition_Quantite(), contrat.getQualite());
				this.getJournal().ajouter(contrat.toString());
			}
		}
		
		return this.getCommandesFeveEnCours();
	}
	
	
	
	
	
	/** Interface IVendeurChoco
	 * 
	 */
	
	/**
	 * @author bernardjoseph
	 */
	
	public ArrayList<Integer> getStock() {
		ArrayList<Integer> stocks = new ArrayList<Integer>();
		stocks.add(0);
		stocks.add(0);
		stocks.add(0);
		stocks.add((int)this.getStockTablettes(0).getValeur());
		stocks.add((int)this.getStockTablettes(1).getValeur());
		stocks.add((int)this.getStockTablettes(2).getValeur());
		return stocks;
	}
	
	public GPrix2 getPrix() {
		ArrayList<Double[]> intervalles = new ArrayList<Double[]>();
		ArrayList<Double[]> prixs = new ArrayList<Double[]>();
		
		Double[] intervalle = {Double.MAX_VALUE};
		Double[] prix;
		
		for(int idProduit = 1; idProduit <= 6; idProduit++) {
			
			if(idProduit<=3) {
				prix = new Double[1];
				prix[0] = 0.0;
			}
			else {
				prix = new Double[1];
				prix[0] = estimatePrixVenteTablette(idProduit-4);
			}
			
			intervalles.add(intervalle);
			prixs.add(prix);
		}
		return new GPrix2(intervalles, prixs);
	}
	
	public ArrayList<ArrayList<Integer>> getLivraison(ArrayList<ArrayList<Integer>> commandes) {
		int[] stockTablettes = new int[3];
		for(int qualite = 0; qualite<3; qualite++) {
			stockTablettes[qualite] = (int) this.getStockTablettes(qualite).getValeur();
		}
		
		ArrayList<ArrayList<Integer>> livraisons = new ArrayList<ArrayList<Integer>>();
			
		// boucle sur les distributeurs
		for(int idDist=0;idDist<2;idDist++) {
			livraisons.add(new ArrayList<Integer>());
			// boucle sur les types de produits
			for(int idProduit = 1; idProduit <= 6; idProduit++) {
				livraisons.get(idDist).add(0);
				if(idProduit <= 3) {
				}
				else {
					// On regarde si on a en stock ou si on peut produire ce qu'on nous demande
					if (commandes.get(idDist).get(idProduit-4) < stockTablettes[idProduit-4]) {
						/*double p = commande1[i]/(commande1[i]+commande2[i]);
						deliver1[i]=(int)(p*stock[i]);
						deliver2[i]=stock[i]-deliver1[i];*/
						
						int diff = commandes.get(idDist).get(idProduit-1)-stockTablettes[idProduit-4];
						livraisons.get(idDist).set(idProduit-1, commandes.get(idDist).get(idProduit-1)-diff);
						
						// On retire ce qu'on a utilisé de notre stock
						this.setStockTablettes((int)(this.getStockTablettes(idProduit-4).getValeur()-livraisons.get(idDist).get(idProduit-1)), idProduit-4);
						
						//on met à jour le solde
						this.getSolde().setValeur(this,this.getSolde().getValeur()+livraisons.get(idDist).get(idProduit-1)*this.getPrixVenteTablettes()[idProduit-4].getValeur());
					}
				}
			}
			
		}

		this.setCommandesTablettesEnCours(commandes);
		this.setLivraisonsTablettesEnCours(livraisons);
		return livraisons;
	}
	
	
	/** Interface IvendeurOccasionnelChoco
	 * @author boulardmaelle
	 * en gros nous on est concerné que par les indices 4 (tablettes BQ) 5 (tablettes MQ) et 6 (tablettes HQ)
	 */
	
	@Override
	public double getReponseTer(DemandeAO d) {
		if (d.getQualite()==1 || d.getQualite()==2 || d.getQualite()==3) { //on a pas de confiseries donc on est pas interessés
			return Double.MAX_VALUE;
		} else {
			return this.estimatePrixVenteTablette(d.getQualite()-4);
			/*if (d.getQuantite()<stockTablettes[d.getQualite()-4].getValeur()) {
				return this.estimatePrixVenteTablette(d.getQualite()-4);
			} else {
				return Double.MAX_VALUE;
			}*/
		}
	}
	@Override
	public void envoyerReponseTer(Acteur acteur, int quantite, int qualite, double prix) {
		if(qualite >= 4) {
			this.stockTablettes[qualite-4].setValeur(this, this.stockTablettes[qualite-4].getValeur()-quantite/10/1000);
			this.getSolde().setValeur(this, this.getSolde().getValeur()+prix*quantite);
		}
	}
	
	/** 
	 * @author bernardjoseph
	 * @param var2 une variable de V2
	 * @return Total des 10 acteurs, pour une quantite (non applicable à un prix par exemple)
	 */
	public Indicateur[] total_quantite(List<Indicateur[]> var2) {
		Indicateur[] total=new Indicateur[3];
		total[0]=new Indicateur("var20",this);
		total[1]=new Indicateur("var21",this);
		total[2]=new Indicateur("var23",this);
		for(Indicateur[] i:var2) {
			total[0].setValeur(this, total[0].getValeur()+i[0].getValeur());
			total[1].setValeur(this, total[1].getValeur()+i[1].getValeur());
			total[2].setValeur(this, total[2].getValeur()+i[2].getValeur());
		}
		return total;
	}
	
	public List<Integer> repart(int valeur) {
		int total=0;
		int val;
		List<Integer> repartition=new ArrayList<Integer>();
		for(int i=0;i<10;i++) {
			val=(int)(.2*(valeur-total)*Math.random());
			repartition.add(val);
			total+=val;
		}
		repartition.add(valeur-total);
		Collections.shuffle(repartition);
		return repartition;
	}
	
	
}
