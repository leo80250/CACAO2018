package abstraction.eq7TRAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import abstraction.eq2PROD.echangeProd.IVendeurFevesProd;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV2;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV4;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV2;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV4;
import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre; 
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IAcheteurFeve, IAcheteurFeveV4, IVendeurChocoBis, IvendeurOccasionnelChoco {
	
	//rajouter IAcheteurFeve à implémenter
	
	
	private Indicateur achats;
	private Indicateur ventes;
	// 0 = BQ, 1 = MQ, 2 = HQ
	private Indicateur[] stockFeves;
	private Indicateur[] stockPoudre;
	private Indicateur[] stockTablettes;
	private Indicateur solde;
	private Journal journal;
	private String nom;
	
	private Indicateur absenteisme;
	private Indicateur efficacite;
	// Historique
	private Indicateur[] prixAchatFeves;
	private Indicateur[] prixVentePoudre;
	private Indicateur[] prixVenteTablettes;
	private Indicateur[] productionPoudreReelle;
	private Indicateur[] productionTablettesReelle;
	private Indicateur[] productionPoudreAttendue;
	private Indicateur[] productionTablettesAttendue;
	
	private List<ContratFeveV3> commandesFeveEnCours;
	private ArrayList<ContratPoudre> commandesPoudreEnCours;
	private ArrayList<ArrayList<Integer>> commandesTablettesEnCours;
	private List<ContratFeveV3> livraisonsFeveEnCours;
	private ArrayList<ContratPoudre> livraisonsPoudreEnCours;
	private ArrayList<ArrayList<Integer>> livraisonsTablettesEnCours;
	private List<ContratFeveV3> offresFevesPubliquesEnCours;

	
	// En disant arbitrairement 
	private Indicateur nombreEmployes;

	private final int MOY_TAUX_EFFICACITE_EMPLOYES = 1;
	
	// en tonnes par 2 semaines
	private final int[] MOY_ACHAT_FEVES = {0, 1400, 3200};
	private final int[] MOY_ACHAT_POUDRE = {0, 0, 0};
	private final int[] MOY_VENTE_POUDRE = {0, 0, 1000};
	private final int[] MOY_VENTE_TABLETTE = {0, 1400, 2300};
	
	// en tonnes par 2 semaines
	private final double[] MOY_PROD_POUDRE_PAR_EMPLOYE = {1,1,1};
	private final double[] MOY_PROD_TABLETTE_PAR_EMPLOYE = {1,1.5,2.2};
	
	
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
	

	public Eq7TRAN(Monde monde, String nom) {
		this.nom = nom;
		this.achats = new Indicateur("Achat de "+this.getNom(), this, 0.0);
		this.ventes = new Indicateur("Vente de "+this.getNom(), this, 0.0);
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
		
		this.commandesFeveEnCours = new ArrayList<ContratFeveV3>();
		this.commandesPoudreEnCours = new ArrayList<ContratPoudre>();
		this.commandesTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.livraisonsFeveEnCours = new ArrayList<ContratFeveV3>();
		this.livraisonsPoudreEnCours = new ArrayList<ContratPoudre>();
		this.livraisonsTablettesEnCours = new ArrayList<ArrayList<Integer>>();
		this.offresFevesPubliquesEnCours = new ArrayList<ContratFeveV3>();
		
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		this.absenteisme = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 0.0);
		this.efficacite = new Indicateur(this.getNom()+" a un taux d'absenteisme de ", this, 1.0);
		
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
		
		this.journal = new Journal("Journal de "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		for(int i = 0; i < 3; i++) {
			Monde.LE_MONDE.ajouterIndicateur(this.stockFeves[i]);
			//Monde.LE_MONDE.ajouterIndicateur(this.stockPoudre[i]);
			Monde.LE_MONDE.ajouterIndicateur(this.stockTablettes[i]);
		}
		
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
		this.calculateAbsenteisme();
		this.calculateTauxEfficacite();
		this.getJournal().ajouter("ABSENTEISME = " + this.getAbsenteisme().getValeur());
		this.getJournal().ajouter("EFFICACITE = " + this.getEfficacite().getValeur());
		
		this.getJournal().ajouter("STOCK FEVES DEBUT = " + (int)this.getStockFeves()[0].getValeur()+"t, "+(int)this.getStockFeves()[1].getValeur()+"t, "+(int)this.getStockFeves()[2].getValeur()+"t");
		this.getJournal().ajouter("STOCK POUDRE DEBUT = " + (int)this.getStockPoudre()[0].getValeur()+"t, "+(int)this.getStockPoudre()[1].getValeur()+"t, "+(int)this.getStockPoudre()[2].getValeur()+"t");
		this.getJournal().ajouter("STOCK TABLETTES DEBUT = " + (int)this.getStockTablettes()[0].getValeur()+"t, "+(int)this.getStockTablettes()[1].getValeur()+"t, "+(int)this.getStockTablettes()[2].getValeur()+"t");
		
		/*
		 Pour le moment, on reçoit des commandes des distributeurs qu'on essaye un maximum de remplir avec notre stock
		 et notre production
		 Puis on fait nos commandes en fèves aux producteurs en fonction des précèdentes commandes en pensant
		 que ce seront les mêmes à la prochaine période
		 Il n'y a pas d'argent en jeu pour le moment, juste une négociation sur les quantités demandées
		 */
		
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
		//*
		commande = new GQte(0, 0, 0, 150,300,600);
		commandes[0] = commande;
		commandes[1] = commande;
		commandes[2] = commande;
<<<<<<< HEAD
		commandeLivree = this.getLivraison(commandes);*/
		
		GQte commande=new GQte(0,0,0,600,900,100);
		commandesTablettesEnCours.add(commande);
		
//<<<<<<< HEAD
		//*
=======
		commandeLivree = this.getLivraison(commandes);
>>>>>>> branch 'master' of https://github.com/leo80250/CACAO2018.git
		
		// on simule une commande de poudre
		ContratPoudre commande2 = new ContratPoudre(1,900,1.4,this,this,true);
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
		
		
		/**
		 * @author leofargeas
		 */
		
		// bug dans Eq3, il caste leur objet équipe en IAcheteurFeve alors qu'il ne l'implemente pas
		for(Acteur Acteur : Monde.LE_MONDE.getActeurs()) {
			// on récupère les commandes des distributeurs en tablettes
			
			if(Acteur instanceof IVendeurFeveV4) {
				offresPubliques = ((IVendeurFeveV4) Acteur).getOffrePubliqueV3();
				offresPubliquesRetenues = this.analyseOffresPubliquesFeves(offresPubliques);
				
				this.getJournal().ajouter(offresPubliques.size()+" "+offresPubliquesRetenues.size());
				
				if(offresPubliquesRetenues.size() > 0) {
					((IVendeurFeveV4) Acteur).sendDemandePriveeV3(offresPubliquesRetenues);
					
					offresPrivees = ((IVendeurFeveV4) Acteur).getOffreFinaleV3();
					if(offresPrivees != null && offresPrivees.size() > 0) {
						offresPriveesRetenues = this.analyseOffresPriveesFeves(offresPrivees);
						/*for(ContratFeveV3 contrat : offresPrivees) {
							
						}*/
						((IVendeurFeveV4) Acteur).sendResultVentesV3(offresPriveesRetenues);
					}
					
				}
			}
		}
		
		// Fin de la période, on supprime toutes les commandes
		this.getJournal().ajouter("COMMANDES FEVES = " +this.getQuantiteFevesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS FEVES = " +this.getQuantiteFevesLivrees()+"t");
		this.getJournal().ajouter("COMMANDES POUDRE = " +this.getQuantitePoudreCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS POUDRE = " +this.getQuantitePoudreLivrees()+"t");
		this.getJournal().ajouter("COMMANDES TABLETTES = " +this.getQuantiteTablettesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS TABLETTES = " +this.getQuantiteTablettesLivrees()+"t");
		
		this.resetCommandesEnCours();
		
		this.getJournal().ajouter(" - Estimation production poudre = " + this.getProductionPoudreAttendue(0).getValeur()+"t, "+this.getProductionPoudreAttendue(1).getValeur()+"t, "+this.getProductionPoudreAttendue(2).getValeur()+"t");
		this.getJournal().ajouter(" - Estimation production tablettes = " + this.getProductionTablettesAttendue(0).getValeur()+"t, "+this.getProductionTablettesAttendue(1).getValeur()+"t, "+this.getProductionTablettesAttendue(2).getValeur()+"t");
		this.getJournal().ajouter("PRODUCTION POUDRE REELLE = " + (int)this.getProductionPoudreReelle()[0].getValeur()+"t, "+(int)this.getProductionPoudreReelle()[1].getValeur()+"t, "+(int)this.getProductionPoudreReelle()[2].getValeur()+"t");
		this.getJournal().ajouter("PRODUCTION TABLETTES REELLE = " + (int)this.getProductionTablettesReelle()[0].getValeur()+"t, "+(int)this.getProductionTablettesReelle()[1].getValeur()+"t, "+(int)this.getProductionTablettesReelle()[2].getValeur()+"t");

		this.getJournal().ajouter("STOCK FEVES FIN = " + (int)this.getStockFeves()[0].getValeur()+"t, "+(int)this.getStockFeves()[1].getValeur()+"t, "+(int)this.getStockFeves()[2].getValeur()+"t");
		this.getJournal().ajouter("STOCK POUDRE FIN = " + (int)this.getStockPoudre()[0].getValeur()+"t, "+(int)this.getStockPoudre()[1].getValeur()+"t, "+(int)this.getStockPoudre()[2].getValeur()+"t");
		this.getJournal().ajouter("STOCK TABLETTES FIN = " + (int)this.getStockTablettes()[0].getValeur()+"t, "+(int)this.getStockTablettes()[1].getValeur()+"t, "+(int)this.getStockTablettes()[2].getValeur()+"t");
		
		this.getJournal().ajouter(" - Estimation prix achat feves = " + this.estimatePrixAchatFeves(0)+"€, "+this.estimatePrixAchatFeves(1)+"€, "+this.estimatePrixAchatFeves(2)+"€");
		this.getJournal().ajouter(" - Estimation prix vente poudre = " + this.estimatePrixVentePoudre(0)+"€, "+this.estimatePrixVentePoudre(1)+"€, "+this.estimatePrixVentePoudre(2)+"€");
		this.getJournal().ajouter("-------------------------------------------------------------");
	}
	
	
	public Indicateur getAchats() {
		return this.achats;
	}
	public void setAchats(Indicateur achats) {
		this.achats = achats;
	}
	public Indicateur getVentes() {
		return ventes;
	}
	public void setVentes(Indicateur ventes) {
		this.ventes = ventes;
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
	public Indicateur[] getStockPoudre() {
		return this.stockPoudre;
	}
	public void setStockPoudre(Indicateur[] stockPoudre) {
		this.stockPoudre = stockPoudre;
	}
	
	/**
	 * 
	 * @param value
	 * @param qualite
	 * @author boulardmaelle
	 */
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
	
	
	
	/**
	 * @author boulardmaelle, margauxgrand, bernardjoseph, leofargeas
	 * 
	 */
	public Journal getJournal() {
		return this.journal;
	}
	public void setJournal(Journal journal) {
		this.journal = journal;
	}
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
	
	
	
	/**
	 *@author margauxgrand
	 * @param offres
	 */
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
	
	/**
	 * @author leofargeas
	 */
	
	public void resetCommandesEnCours() {
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
			for(int idProduit = 1; idProduit <= 6; idProduit++) {
				quantite += commande.get(idProduit-1);
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
			for(int idProduit = 1; idProduit <= 6; idProduit++) {
				quantite += commande.get(idProduit-1);
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
	
	/** calculateAbsenteisme
	 * @author boulardmaelle, leofargeas
	 * @return le taux d'absenteisme (entre 0 et 15%)
	 */
	
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
	
	public double estimateCoutTransformationPoudre(int qualite) {
		return 0;
	}
	public double estimateMargePoudre(int qualite) {
		return this.MOY_MARGE_POUDRE;
	}
	public double estimateCoutTransformationTablette(int qualite) {
		return 0;
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
	
	
	
	//////////////////////////////////////
	// METHODES VENDEUR POUDRE&CHOCOLAT //
	/////////////////////////////////////
	
	/**
	 * @author bernardjoseph
	 * @param qualite
	 */
	
	// retourne un tableau de tableau avec le taux de transformation de tablette en indice 1 et poudre indice 0, la qualité appararait 
	public double[] getTauxProductionTablettesPoudre() {
<<<<<<< HEAD
		int sommePoudre=0;
		int sommeTablette=0;
		int sommeTotale;
=======
		int sommePoudre = this.getQuantitePoudreCommandees();
		int sommeTablette = this.getQuantiteTablettesCommandees();
		int sommeTotale=sommePoudre+sommeTablette;
>>>>>>> branch 'master' of https://github.com/leo80250/CACAO2018.git
		double tauxPoudre;
		double tauxTablette;
		double[] TauxFinauxTetP= new double[2];
<<<<<<< HEAD
		for (int i=0; i<commandesPoudreEnCours.size(); i++) {
			sommePoudre+=commandesPoudreEnCours.get(i).getQuantite();
		}
		for (int i=0; i<commandesTablettesEnCours.size(); i++) {
			sommeTablette+=commandesTablettesEnCours.get(i).getqTabletteBQ()+commandesTablettesEnCours.get(i).getqTabletteMQ()+commandesTablettesEnCours.get(i).getqTabletteHQ();
		}
		sommeTotale=sommePoudre+sommeTablette;
=======
		
>>>>>>> branch 'master' of https://github.com/leo80250/CACAO2018.git
		if (sommeTotale==0) {
			tauxPoudre=TAUX_PRODUCTION_POUDRE;
			tauxTablette=TAUX_PRODUCTION_TABLETTE;
		} else {
			tauxTablette=((double)sommeTablette)/((double)sommeTotale);
			tauxPoudre=((double)sommePoudre)/((double)sommeTotale);
		}
		TauxFinauxTetP[0]=tauxPoudre ;
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
			this.getProductionPoudreReelle()[qualite].setValeur(this, productionPossible);
		
	}
	
	public void estimateProductionTabletteAttendue(int qualite) {
		int productionAttendue = 0;
		ArrayList<ArrayList<Integer>> commandesEnCours = this.getCommandesTablettesEnCours();
		for(int i = 0; i < commandesEnCours.size(); i++) {
			for(int idDist = 0; idDist < 3; idDist++) {
				productionAttendue += commandesEnCours.get(idDist).get(4+qualite);
			}
		}
		//productionAttendue -= (int)this.getStockTablettes(qualite).getValeur();
		this.setProductionTablettesAttendue((int)(productionAttendue*(1+MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES)), qualite);
	}
	
	public void calculateProductionTablettesReelle(int qualite) {
		double productionPossible = this.getStockFeves(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite])*getTauxProductionTablettesPoudre()[1]*getEfficacite().getValeur(); 
			this.getProductionTablettesReelle()[qualite].setValeur(this,productionPossible);
	}

	
	public void produire() {
		for(int qualite = 0; qualite < 3; qualite++) {
			this.setStockTablettes((int)(this.getStockTablettes(qualite).getValeur()+this.getProductionTablettesReelle()[qualite].getValeur()), qualite);
			this.setStockPoudre((int)(this.getStockPoudre(qualite).getValeur()+this.getProductionPoudreReelle()[qualite].getValeur()), qualite);
			this.setStockFeves((int)(this.getStockFeves(qualite).getValeur()-this.getProductionTablettesReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]-this.getProductionPoudreReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]), qualite);
		}
	}
	
	/**
	 * @author leofargeas
	 * @param qualite
	 * @return
	 */
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
	public Indicateur getStockPrevisionnel(int qualite) {
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
		for(int qualite=0;qualite<3;qualite++) {
			catalogue[qualite]=new ContratPoudre(qualite,(int)this.getStockPoudre(qualite).getValeur(),
					this.prixVentePoudre[qualite].getValeur(),acheteur,(IVendeurPoudre)this,false);
		}
		return catalogue;
	}
	
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		int n = devis.length;
		for(int i = 0; i<n; i++) {
			int qualite = devis[i].getQualite();
			// Si on a pas la bonne quantité on refuse
			if(devis[i].getQuantite() > this.getStockPoudre()[qualite].getValeur()) {
				devis[i].setReponse(false);
			}
		}
		return devis;
	}
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		// est-ce qu'il a eu des probs pour la réalisation du contrat ?
		return contrat;
	}
	
	
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		// TODO Auto-generated method stub
		
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
	
	public List<ContratFeveV3> analyseOffresPubliquesFeves(List<ContratFeveV3> offresPubliques2) {
		// on boucle sur les offres publiques et on vérifie pour le 
		// moment seulement qu'on peut tout produire avec nos employés
		ArrayList<ContratFeveV3> offresPubliques = new ArrayList<>(offresPubliques2);
		ArrayList<ArrayList<ContratFeveV3>> offresRetenuesParQualite = new ArrayList<ArrayList<ContratFeveV3>>();
		for(int qualite = 0; qualite < 3; qualite++) {
			offresRetenuesParQualite.add(new ArrayList<ContratFeveV3>());
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
				if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] > getMaximumOfProduction(3,offre.getQualite())) {
					offre.setDemande_Quantite(getMaximumOfProduction(3,offre.getQualite()) - (sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] - offre.getOffrePublique_Quantite()));
					stopLoop = true;
				}
				else
					offre.setDemande_Quantite(offre.getOffrePublique_Quantite());
				
				offresRetenuesParQualite.get(offre.getQualite()).add(offre);
				n[offre.getQualite()]++;
			}
		}
		
		List<ContratFeveV3> offresRetenues = new ArrayList<ContratFeveV3>(); 
		for(int qualite = 0; qualite < 3; qualite++) {
			for(int i = 0; i < n[qualite]; i++) {
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
		
		Double[] intervalle = {0.0, Double.MAX_VALUE};
		Double[] prix;
		
		for(int idProduit = 1; idProduit <= 6; idProduit++) {
			
			if(idProduit<=3) {
				prix = new Double[1];
				prix[0] = 0.0;
			}
			else {
				prix = new Double[1];
				prix[0] = estimatePrixVenteTablette(4-idProduit);
			}
			
			intervalles.add(intervalle);
			prixs.add(prix);
		}
		return new GPrix2(intervalles, prixs);
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> getLivraison(ArrayList<ArrayList<Integer>> commandes) {
		int[] stockTablettes = new int[3];
		for(int qualite = 0; qualite<3; qualite++) {
			stockTablettes[qualite] = (int) this.getStockTablettes(qualite).getValeur();
		}
		
		ArrayList<ArrayList<Integer>> livraisons = new ArrayList<ArrayList<Integer>>();
			
		// boucle sur les distributeurs
		for(int idDist=0;idDist<3;idDist++) {
			// boucle sur les types de produits
			for(int idProduit = 1; idProduit <= 6; idProduit++) {
				if(idProduit <= 3) {
				}
				else {
					// On regarde si on a en stock ou si on peut produire ce qu'on nous demande
					if (commandes.get(idDist).get(idProduit-1) > stockTablettes[idProduit-1]) {
						/*double p = commande1[i]/(commande1[i]+commande2[i]);
						deliver1[i]=(int)(p*stock[i]);
						deliver2[i]=stock[i]-deliver1[i];*/
						
						int diff = commandes.get(idDist).get(idProduit-1)-stockTablettes[idProduit-1];
						livraisons.get(idDist).set(idProduit-1, commandes.get(idDist).get(idProduit-1)-diff);
						
						// On retire ce qu'on a utilisé de notre stock
						this.setStockTablettes((int)(this.getStockTablettes(4-idProduit).getValeur()-livraisons.get(idDist).get(idProduit-1)), 4-idProduit);
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
	 * en gros nous on est concerné que par les indices 1 (tablettes BQ) 2 (tablettes MQ) et 3 (tablettes HQ)
	 */
	
	@Override
	public double getReponse(DemandeAO d) {
		if (d.getQualite()==4 || d.getQualite()==5 || d.getQualite()==6) { //on a pas de confiseries donc on est pas interessés
			return Double.MAX_VALUE;
		} else {
			if (d.getQuantite()<stockTablettes[d.getQualite()-1].getValeur()) {
				return MOY_PRIX_VENTE_TABLETTE[d.getQualite()-1];
			} else {
				return Double.MAX_VALUE;
			}
		}
	}
	@Override
	public void envoyerReponse(double quantite, int qualite, int prix) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
		

	
	
	/**
	 * @author josephbernard  leofargeas boulardmaelle margauxgrand
	 */
	/*
	public List<GQte> getLivraison(List<GQte> commandes) {
		int[] stock= {this.getStockTablette(0).getValeur(),this.getStockTablette(1).getValeur(),this.getStockTablette(2).getValeur()};
		int[] commande1= {commandes.get(0).getqTabletteBQ(),commandes.get(0).getqTabletteMQ(),commandes.get(0).getqTabletteHQ()};
		int[] commande2= {commandes.get(1).getqTabletteBQ(),commandes.get(1).getqTabletteMQ(),commandes.get(1).getqTabletteHQ()};
		int[] deliver1= new int[3];
		int[] deliver2= new int[3];
			
		for(int i=0;i<3;i++) {
			if (commande1[i]+commande2[i]<=stock[i]) {
				deliver1[i]=commande1[i];
				deliver2[i]=commande2[i];
			}
			else {
				double p=commande1[i]/(commande1[i]+commande2[i]);
				deliver1[i]=(int)(p*stock[i]);
				deliver2[i]=stock[i]-deliver1[i];
			}
			this.stockTablettes[i].setValeur(this.getNom(),this.stockTablette [i].getValeur()-deliver1[i]-deliver2[i]);
		}
			
		//setvaleur sur le solde
		List<GQte> livraison= new ArrayList<GQte>();
		livraison.add(new GQte(0,0,0,deliver1[0],deliver1[1],deliver1[2]));
		livraison.add(new GQte(0,0,0,deliver2[0],deliver2[1],deliver2[2]));

		return livraison;
		}
	}
>>>>>>> branch 'master' of https://github.com/leo80250/CACAO2018.git
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public GQte getLivraison(GQte[] commandes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	*/
	

	//IAcheteurFeve à laisser vide
	/**
	 * code à laisser vide, correspond à la V1, doit demeurer vide, et on doit toujour implémenter IAcheteurFeve
	 * 
	 */
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		
	}
	
	public ContratFeve[] getDemandePrivee() {
		ContratFeve[] contrat= new ContratFeve[0];
		return contrat;
	}
	
	public void sendContratFictif(ContratFeve[] listContrats) {
		
	}
	
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		
	}
	public ContratFeve[] getResultVentes() {
		ContratFeve[] contrat= new ContratFeve[0];
		return contrat;
	}
	
	
}
