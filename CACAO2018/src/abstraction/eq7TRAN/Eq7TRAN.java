package abstraction.eq7TRAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import abstraction.eq2PROD.echangeProd.IVendeurFevesProd;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre; 
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq7TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IAcheteurFeve, IVendeurChoco {
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
	
	private ContratFeve[] commandesFeveEnCours;
	private ArrayList<ContratPoudre> commandesPoudreEnCours;
	private ArrayList<GQte> commandesTablettesEnCours;
	private ContratFeve[] livraisonsFeveEnCours;
	private ArrayList<ContratPoudre> livraisonsPoudreEnCours;
	private ArrayList<GQte> livraisonsTablettesEnCours;
	private ContratFeve[] offresFevesPubliquesEnCours;

	
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
	
	private final int SUM_MOY_VENTE_POUDRE = MOY_VENTE_POUDRE[0]+MOY_VENTE_POUDRE[1]+MOY_VENTE_POUDRE[2];
	private final int SUM_MOY_VENTE_TABLETTES = MOY_VENTE_TABLETTE[0]+MOY_VENTE_TABLETTE[1]+MOY_VENTE_TABLETTE[2];
	
	// en €/tonne
	private final double[] MOY_PRIX_ACHAT_FEVES = {1800, 2100, 2500};
	
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
		
		this.commandesFeveEnCours = new ContratFeve[0];
		this.commandesPoudreEnCours = new ArrayList<ContratPoudre>();
		this.commandesTablettesEnCours = new ArrayList<GQte>();
		this.livraisonsFeveEnCours = new ContratFeve[0];
		this.livraisonsPoudreEnCours = new ArrayList<ContratPoudre>();
		this.livraisonsTablettesEnCours = new ArrayList<GQte>();
		this.offresFevesPubliquesEnCours = new ContratFeve[0];
		
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
		
		GQte[] commandes = new GQte[3];
		GQte commande;
		GQte commandeLivree;
		// Pour le moment le code des autres équipes ne nous fait aucune commande !
		/*
		for(Acteur Acteur : Monde.LE_MONDE.getActeurs()) {
			// on récupère les commandes des distributeurs en tablettes
			
			if(Acteur instanceof IAcheteurChoco) {
				commande = ((IAcheteurChoco) Acteur).getCommande(null,null).get(2);
				
				// maintenant on livre ces commandes en fonction de notre stock
				// interface bizarre pour le coup
				commandes[0] = commande;
				commandes[1] = commande;
				commandes[2] = commande;
				commandeLivree = this.getLivraison(commandes);
			}
		}*/
		// on simule une commande régulière ici
		commande = new GQte(0, 0, 0, 150,300,600);
		commandes[0] = commande;
		commandes[1] = commande;
		commandes[2] = commande;
		commandeLivree = this.getLivraison(commandes);
		
		this.getJournal().ajouter("COMMANDES FEVES = " +this.getQuantiteFevesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS FEVES = " +this.getQuantiteFevesLivrees()+"t");
		this.getJournal().ajouter("COMMANDES POUDRE = " +this.getQuantitePoudreCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS POUDRE = " +this.getQuantitePoudreLivrees()+"t");
		this.getJournal().ajouter("COMMANDES TABLETTES = " +this.getQuantiteTablettesCommandees()+"t");
		this.getJournal().ajouter("LIVRAISONS TABLETTES = " +this.getQuantiteTablettesLivrees()+"t");
		
		
		// Pas d'échange entre transformateur pour le moment
		
		// pour le moment on produit les commandes actuelles, à l'avenir il faudra prévoir les commandes qui suivent
		// maintenant on s'occupe de notre production
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
		ContratFeve[] offresPubliques;
		ContratFeve[] offresPubliquesRetenues;
		ContratFeve[] offresPrivees;
		ContratFeve[] offresPriveesRetenues;
		
		// bug dans Eq3, il caste leur objet équipe en IAcheteurFeve alors qu'il ne l'implemente pas
		for(Acteur Acteur : Monde.LE_MONDE.getActeurs()) {
			// on récupère les commandes des distributeurs en tablettes
			
			if(Acteur instanceof IVendeurFeve) {
				offresPubliques = ((IVendeurFeve) Acteur).getOffrePublique();
				offresPubliquesRetenues = this.analyseOffresPubliquesFeves(offresPubliques);
				
				if(offresPubliquesRetenues.length > 0) {
					((IVendeurFeve) Acteur).sendDemandePrivee(offresPubliquesRetenues);
					// BUG AVEC L'EQUIPE 3
					/*
					offresPrivees = ((IVendeurFeve) Acteur).getOffreFinale();
					if(offresPrivees.length > 0) {
						offresPriveesRetenues = this.analyseOffresPriveesFeves(offresPrivees);
						System.out.println(offresPriveesRetenues);
					}
					*/
				}
			}
		}
		
		// Fin de la période, on supprime toutes les commandes
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
	public void setOffresFevesPubliquesEnCours(ContratFeve[] offres) {
		this.offresFevesPubliquesEnCours = offres;
	}
	public ContratFeve[] getOffresFevesPubliquesEnCours() {
		return this.offresFevesPubliquesEnCours;
	}
	public Indicateur getNombreEmployes() {
		return this.nombreEmployes;
	}
	public void setNombreEmployes(int n) {
		this.nombreEmployes.setValeur(this, (double)n);
	}
	public ArrayList<ContratPoudre> getCommandesPoudreEnCours() {
		return this.commandesPoudreEnCours;
	}
	public ArrayList<GQte> getCommandesTablettesEnCours() {
		return this.commandesTablettesEnCours;
	}
	public ContratFeve[] getCommandesFeveEnCours() {
		return this.commandesFeveEnCours;
	}
	public void setCommandesFeveEnCours(ContratFeve[] contrats) {
		this.commandesFeveEnCours = contrats;
	}
	public void setCommandesTablettesEnCours(ArrayList<GQte> contrats) {
		this.commandesTablettesEnCours = contrats;
	}
	public void setCommandesPoudreEnCours(ArrayList<ContratPoudre> contrats) {
		this.commandesPoudreEnCours = contrats;
	}
	public ArrayList<GQte> getLivraisonsTablettesEnCours() {
		return this.livraisonsTablettesEnCours;
	}
	public ContratFeve[] getLivraisonsFeveEnCours() {
		return this.livraisonsFeveEnCours;
	}
	public void setLivraisonsTablettesEnCours(ArrayList<GQte> contrats) {
		this.livraisonsTablettesEnCours = contrats;
	}
	public ArrayList<ContratPoudre> getLivraisonsPoudreEnCours() {
		return this.livraisonsPoudreEnCours;
	}
	public void setLivraisonsPoudreEnCours(ArrayList<ContratPoudre> contrats) {
		this.livraisonsPoudreEnCours = contrats;
	}
	public void setLivraisonsFevesEnCours(ContratFeve[] contrats) {
		this.livraisonsFeveEnCours = contrats;
	}
	
	public void resetCommandesEnCours() {
		this.commandesFeveEnCours = new ContratFeve[0];
		this.commandesTablettesEnCours = new ArrayList<GQte>();
		this.commandesPoudreEnCours = new ArrayList<ContratPoudre>();
		this.livraisonsFeveEnCours = new ContratFeve[0];
		this.livraisonsTablettesEnCours = new ArrayList<GQte>();
		this.livraisonsPoudreEnCours = new ArrayList<ContratPoudre>();
	}
	public int getQuantiteTablettesCommandees() {
		ArrayList<GQte> commandes = this.getCommandesTablettesEnCours();
		int quantite = 0;
		for(GQte commande : commandes) {
			quantite += commande.getqTabletteBQ() + commande.getqTabletteMQ() + commande.getqTabletteHQ();
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
		ContratFeve[] commandes = this.getCommandesFeveEnCours();
		int quantite = 0;
		for(ContratFeve commande : commandes) {
			quantite += commande.getProposition_Quantite();
		}
		return quantite;
	}
	public int getQuantiteTablettesLivrees() {
		ArrayList<GQte> commandes = this.getLivraisonsTablettesEnCours();
		int quantite = 0;
		for(GQte commande : commandes) {
			quantite += commande.getqTabletteBQ() + commande.getqTabletteMQ() + commande.getqTabletteHQ();
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
		ContratFeve[] commandes = this.getLivraisonsFeveEnCours();
		int quantite = 0;
		for(ContratFeve commande : commandes) {
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
	
	//Joseph Bernard
	
	//////////////////////////////////////
	// METHODES VENDEUR POUDRE&CHOCOLAT //
	/////////////////////////////////////
	public void calculateProductionPoudreReelle(int qualite) {
		double productionReelle = (1.0 - this.getAbsenteisme().getValeur())*this.getEfficacite().getValeur()*this.getProductionPoudreAttendue(qualite).getValeur();
		double reste = this.getStockFeves(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]) - productionReelle;
		if(reste > 0)	
			this.getProductionPoudreReelle()[qualite].setValeur(this, productionReelle);
		else
			this.getProductionPoudreReelle()[qualite].setValeur(this, productionReelle+reste);
	}
	public void calculateProductionTablettesReelle(int qualite) {
		double productionReelle = (1.0 - this.getAbsenteisme().getValeur())*this.getEfficacite().getValeur()*this.getProductionTablettesAttendue(qualite).getValeur();
		double reste = this.getStockFeves(qualite).getValeur()*(2-this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]) - productionReelle;
		if(reste > 0)	
			this.getProductionTablettesReelle()[qualite].setValeur(this,productionReelle);
		else
			this.getProductionTablettesReelle()[qualite].setValeur(this,productionReelle+reste);
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
	public void estimateProductionTabletteAttendue(int qualite) {
		int productionAttendue = 0;
		ArrayList<GQte> commandesEnCours = this.getCommandesTablettesEnCours();
		for(int i = 0; i < commandesEnCours.size(); i++) {
			if(qualite == 0)
				productionAttendue += commandesEnCours.get(i).getqTabletteBQ();
			else if(qualite == 1)
				productionAttendue += commandesEnCours.get(i).getqTabletteMQ();
			else if(qualite == 2)
				productionAttendue += commandesEnCours.get(i).getqTabletteHQ();
		}
		//productionAttendue -= (int)this.getStockTablettes(qualite).getValeur();
		this.setProductionTablettesAttendue((int)(productionAttendue*(1+MOY_STOCK_TABLETTES_SUR_ENSEMBLE_COMMANDES)), qualite);
	}
	public void produire() {
		System.out.println(this.getProductionPoudreAttendue()[0].getValeur());
		for(int qualite = 0; qualite < 3; qualite++) {
			this.setStockTablettes((int)(this.getStockTablettes(qualite).getValeur()+this.getProductionTablettesReelle()[qualite].getValeur()), qualite);
			this.setStockPoudre((int)(this.getStockPoudre(qualite).getValeur()+this.getProductionPoudreReelle()[qualite].getValeur()), qualite);
			this.setStockFeves((int)(this.getStockFeves(qualite).getValeur()-this.getProductionTablettesReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]-this.getProductionPoudreReelle()[qualite].getValeur()*this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]), qualite);
		}
	}
	
	// Léo Fargeas
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
	 * @author @boulardmaelle @margauxgrand @josephbernard
	 */
	
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		return new ContratPoudre[0];
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
	
	public ContratFeve[] analyseOffresPubliquesFeves(ContratFeve[] offresPubliques2) {
		// on boucle sur les offres publiques et on vérifie pour le 
		// moment seulement qu'on peut tout produire avec nos employés
		ArrayList<ContratFeve> offresPubliques = new ArrayList<>(Arrays.asList(offresPubliques2));
		ArrayList<ArrayList<ContratFeve>> offresRetenuesParQualite = new ArrayList<ArrayList<ContratFeve>>();
		
		// A mettre dans des méthodes séparées
		int[] productionTotaleMax = new int[3];
		int[] productionPoudreMax = new int[3];
		int[] productionTabletteMax = new int[3];
		int[] productionTotaleMin = new int[3];
		int[] productionPoudreMin = new int[3];
		int[] productionTabletteMin = new int[3];
		for(int qualite = 0; qualite<3; qualite++) {
			productionPoudreMax[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_POUDRE[qualite]*this.MOY_PROD_POUDRE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
			productionTabletteMax[qualite] = (int) (this.TAUX_TRANSFORMATION_FEVES_TABLETTES[qualite]*this.MOY_PROD_TABLETTE_PAR_EMPLOYE[qualite]*this.getNombreEmployes().getValeur()*this.getEfficacite().getValeur());
			productionTotaleMax[qualite] = Math.max(productionPoudreMax[qualite], productionTabletteMax[qualite]);
			productionPoudreMin[qualite] = (int) this.getProductionPoudreAttendue(qualite).getValeur();
			productionTabletteMin[qualite] = (int) this.getProductionTablettesAttendue(qualite).getValeur();
			productionTotaleMin[qualite] = productionPoudreMin[qualite] + productionTabletteMin[qualite];
			
			offresRetenuesParQualite.add(new ArrayList<ContratFeve>());
		}

		Collections.sort(offresPubliques, new Comparator<ContratFeve>() {
	        public int compare(ContratFeve contrat1, ContratFeve contrat2)
	        {
	            double resultat = contrat1.getOffrePublique_Prix()/contrat1.getOffrePublique_Quantite() - contrat2.getOffrePublique_Prix()/contrat2.getOffrePublique_Quantite();
	            return (int) ((resultat > 0) ? Math.ceil(resultat) : Math.floor(resultat));
	        }
	    });
		
		int[] sommeQuantiteOffresRetenuesParQualite = new int[3];
		int[] n = new int[3];
		for(ContratFeve offre : offresPubliques) {			
			if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] < productionTotaleMin[offre.getQualite()]) {
				sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] += offre.getOffrePublique_Quantite();
				if(sommeQuantiteOffresRetenuesParQualite[offre.getQualite()] < productionTotaleMax[offre.getQualite()]) {
					offre.setDemande_Quantite(offre.getOffrePublique_Quantite() - (productionTotaleMin[offre.getQualite()] - offre.getOffrePublique_Quantite()));
				}
				offresRetenuesParQualite.get(offre.getQualite()).add(offre);
				n[offre.getQualite()]++;
			}
		}
		
		ContratFeve[] offresRetenues = new ContratFeve[n[0]+n[1]+n[2]];
		for(int qualite = 0; qualite < 3; qualite++) {
			for(int i = 0; i < n[qualite]; i++) {
				if(qualite == 0)
					offresRetenues[i] = offresRetenuesParQualite.get(qualite).get(i);
				if(qualite == 1)
					offresRetenues[n[0] + i] = offresRetenuesParQualite.get(qualite).get(i);
				if(qualite == 2)
					offresRetenues[n[0] + n[1] + i] = offresRetenuesParQualite.get(qualite).get(i);
			}
		}
		
		return offresRetenues;
	}
	
	public ContratFeve[] analyseOffresPriveesFeves(ContratFeve[] offresPrivees) {		
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
		for(ContratFeve offre : offresPrivees) {			
			// Pour le moment on accepte tout
			offre.setReponse(true);
		}
		this.setCommandesFeveEnCours(offresPrivees);
		return offresPrivees;
	}
		
	/**
	 * Interface IAcheteurFeve
	 * @author boulardmaelle, margauxgrand
	 */
	
	@Override
	public void sendOffrePublique(ContratFeve[] offresPubliques) {
		// On garde les anciennes offres ?
		/*
		ContratFeve[] oldOffres = this.getOffresFevesPubliquesEnCours();
		int n = oldOffres.length;
		*/
		this.setOffresFevesPubliquesEnCours(offresPubliques);
	}
	
	@Override
	public ContratFeve[] getDemandePrivee() {
		ContratFeve[] offresPubliques = this.getOffresFevesPubliquesEnCours();
		return this.analyseOffresPubliquesFeves(offresPubliques);
	}
	@Override
	public void sendOffreFinale(ContratFeve[] offresFinales) {
		this.setCommandesFeveEnCours(this.analyseOffresPriveesFeves(offresFinales));
	}
	@Override
	public ContratFeve[] getResultVentes() {
		return this.getCommandesFeveEnCours();
	}
	
	/** Interface IVendeurChoco
	 * 
	 */
	
	//Joseph Bernard
	
	public GQte getStock() {
		return new GQte(0,0,0,(int)this.getStockTablettes(0).getValeur(),(int)this.getStockTablettes(1).getValeur(),(int)this.getStockTablettes(2).getValeur());
	}
	
	//tableau de GPrix pour les trois qualites
	/*public GPrix getPrix() {
		GPrix[] array = new GPrix[3];
		array[0] = new GPrix({0.0,Float.MAX_VALUE},{(float)this.estimatePrixVenteTablette(0)});
		array[1] = new GPrix({0.0,Float.MAX_VALUE},{(float)this.estimatePrixVenteTablette(1)});
		array[2] = new GPrix({0.0,Float.MAX_VALUE},{(float)this.estimatePrixVenteTablette(2)});
		return array;
	}*/
	
	public GPrix getPrix() {
		float[] intervalles = new float[10];
		float[] prix = new float[10];
		prix[0] = (float) estimatePrixVenteTablette(0);
		prix[1] = (float) estimatePrixVenteTablette(1);
		prix[2] = (float) estimatePrixVenteTablette(2);
		return new GPrix(intervalles, prix);
	}
	
	@Override
	public GQte getLivraison(GQte[] commandes) {
		int[] stockTablettes = new int[3];
		for(int qualite = 0; qualite<3; qualite++) {
			stockTablettes[qualite] = (int) this.getStockTablettes(qualite).getValeur();
		}
		
		int[] commande1 = {commandes[0].getqTabletteBQ(),commandes[0].getqTabletteMQ(),commandes[0].getqTabletteHQ()};
		int[] deliver1 = new int[3];
			
		for(int i=0;i<3;i++) {
			deliver1[i] = commande1[i];
			// On regarde si on a en stock ou si on peut produire ce qu'on nous demande
			if (commande1[i] > stockTablettes[i]) {
				/*double p = commande1[i]/(commande1[i]+commande2[i]);
				deliver1[i]=(int)(p*stock[i]);
				deliver2[i]=stock[i]-deliver1[i];*/
				
				int diff = commande1[i]-stockTablettes[i];
				deliver1[i] -= diff;
			}
			
			// On retire ce qu'on a utilisé de notre stock
			this.setStockTablettes((int)(this.getStockTablettes(i).getValeur()-deliver1[i]), i);
		}
		
		GQte commande = new GQte(0,0,0,commande1[0],commande1[1],commande1[2]); 
		GQte livraison = new GQte(0,0,0,deliver1[0],deliver1[1],deliver1[2]); 
		this.getCommandesTablettesEnCours().add(commande);
		this.getLivraisonsTablettesEnCours().add(livraison);
		return livraison;
	}
	@Override
	public void sendContratFictif(ContratFeve[] listContrats) {
		// TODO Auto-generated method stub
		
	}
	
	
	//Joseph Bernard
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
	
	
	
}
