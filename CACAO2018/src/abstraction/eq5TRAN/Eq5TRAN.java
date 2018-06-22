package abstraction.eq5TRAN;

import static abstraction.eq5TRAN.util.Marchandises.*;

import java.util.*;

import abstraction.eq2PROD.Eq2PROD;
import abstraction.eq3PROD.Eq3PROD;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq3PROD.echangesProdTransfo.MarcheFeve;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.eq5TRAN.util.Marchandises;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

/**
 * @author Juliette Gorline (chef)
 * @author Francois Le Guernic
 * @author Maxim Poulsen
 * @author Thomas Schillaci (lieutenant)
 */
public class Eq5TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IvendeurOccasionnelChoco, IAcheteurFeve {

	// cf Marchandises.java pour obtenir l'indexation
	private Indicateur[] productionSouhaitee; // ce qui sort de nos machines en kT
	private Indicateur[] achatsSouhaites; // ce qu'on achète aux producteurs en kT
	private float facteurStock; // facteur lié aux risques= combien d'itérations on peut tenir sans réception de feves/poudre
	private Indicateur[] stocksSouhaites; // margeStock = facteurStock * variationDeStockParIteration, en kT
	private Indicateur[] stocks; // les vrais stocks en kT
	private ContratFeve contratFeveBQEq2; // Le contrat avec l'équipe 2 pour les fèves BQ
	private ContratFeve contratFeveMQEq2; // Le contrat avec l'équipe 2 pour les fèves MQ
	private ContratFeve contratFeveMQEq3; // Le contrat avec l'équipe 3 pour les fèves MQ

	private Indicateur banque; // en milliers d'euros
	private Indicateur[] prix; // en €/T

	private final int FEVE_BQ_EQ2 = 0;
	private final int FEVE_MQ_EQ2 = 1;
	private final int FEVE_MQ_EQ3 = 2;

	private Journal journal;

	/**
	 * @author Thomas Schillaci
	 */
	public Eq5TRAN() {
		int nbMarchandises = Marchandises.getNombreMarchandises();
		productionSouhaitee = new Indicateur[nbMarchandises];
		achatsSouhaites = new Indicateur[nbMarchandises];
		facteurStock = 3;
		stocksSouhaites = new Indicateur[nbMarchandises];
		stocks = new Indicateur[nbMarchandises];
		prix = new Indicateur[nbMarchandises];

		productionSouhaitee[FEVES_BQ] = new Indicateur("Eq5 - Production souhaitee de feves BQ", this, 0);
		productionSouhaitee[FEVES_MQ] = new Indicateur("Eq5 - Production souhaitee de feves MQ", this, 0);
		productionSouhaitee[TABLETTES_BQ] = new Indicateur("Eq5 - Production souhaitee de tablettes BQ", this, 345);
		productionSouhaitee[TABLETTES_MQ] = new Indicateur("Eq5 - Production souhaitee de tablettes MQ", this, 575);
		productionSouhaitee[TABLETTES_HQ] = new Indicateur("Eq5 - Production souhaitee de tablettes HQ", this, 115);
		productionSouhaitee[POUDRE_BQ] = new Indicateur("Eq5 - Production souhaitee de poudre BQ", this, 360);
		productionSouhaitee[POUDRE_MQ] = new Indicateur("Eq5 - Production souhaitee de poudre MQ", this, 50);
		productionSouhaitee[POUDRE_HQ] = new Indicateur("Eq5 - Production souhaitee de poudre HQ", this, 0);
		productionSouhaitee[FRIANDISES_MQ] = new Indicateur("Eq5 - Production souhaitee de friandises MQ", this, 115);

		achatsSouhaites[FEVES_BQ] = new Indicateur("Eq5 - Achats souhaites de feves BQ", this, 360);
		achatsSouhaites[FEVES_MQ] = new Indicateur("Eq5 - Achats souhaites de feves MQ", this, 840);
		achatsSouhaites[TABLETTES_BQ] = new Indicateur("Eq5 - Achats souhaites de tablettes BQ", this, 0);
		achatsSouhaites[TABLETTES_MQ] = new Indicateur("Eq5 - Achats souhaites de tablettes MQ", this, 0);
		achatsSouhaites[TABLETTES_HQ] = new Indicateur("Eq5 - Achats souhaites de tablettes HQ", this, 0);
		achatsSouhaites[POUDRE_BQ] = new Indicateur("Eq5 - Achats souhaites de poudre BQ", this, 0);
		achatsSouhaites[POUDRE_MQ] = new Indicateur("Eq5 - Achats souhaites de poudre MQ", this, 0);
		achatsSouhaites[POUDRE_HQ] = new Indicateur("Eq5 - Achats souhaites de poudre HQ", this, 115);
		achatsSouhaites[FRIANDISES_MQ] = new Indicateur("Eq5 - Achats souhaites de friandises MQ", this, 0);

		prix[FEVES_BQ] = new Indicateur("Eq5 - Prix de feves BQ", this, 0);
		prix[FEVES_MQ] = new Indicateur("Eq5 - Prix de feves MQ", this, 0);
		prix[TABLETTES_BQ] = new Indicateur("Eq5 - Prix de tablettes BQ", this, 100);
		prix[TABLETTES_MQ] = new Indicateur("Eq5 - Prix de tablettes MQ", this, 100);
		prix[TABLETTES_HQ] = new Indicateur("Eq5 - Prix de tablettes HQ", this, 0);
		prix[POUDRE_BQ] = new Indicateur("Eq5 - Prix de poudre BQ", this, 0);
		prix[POUDRE_MQ] = new Indicateur("Eq5 - Prix de poudre MQ", this, 100);
		prix[POUDRE_HQ] = new Indicateur("Eq5 - Prix de poudre HQ", this, 0);
		prix[FRIANDISES_MQ] = new Indicateur("Eq5 - Prix de friandises MQ", this, 100);

		for (int i = 0; i < nbMarchandises; i++) {
			stocksSouhaites[i] = new Indicateur("Eq5 - Stocks souhaites de " + Marchandises.getMarchandise(i), this, productionSouhaitee[i].getValeur() + achatsSouhaites[i].getValeur());
			stocks[i] = new Indicateur("Eq5 - Stocks de " + Marchandises.getMarchandise(i), this, stocksSouhaites[i].getValeur()); // on initialise les vrais stocks comme étant ce que l'on souhaite avoir pour la premiere iteration
		}

		banque = new Indicateur("Eq5 - Banque", this, 16_000); // environ benefice 2017 sur nombre d'usines

		//		for (Field field : getClass().getDeclaredFields()) {
		//			if(field==null) continue;
		//			try {
		//				if(field.get(this) instanceof  Indicateur)
		//					Monde.LE_MONDE.ajouterIndicateur((Indicateur) field.get(this));
		//				else if(field.get(this) instanceof Indicateur[])
		//					for (Indicateur indicateur : (Indicateur[]) field.get(this))
		//						Monde.LE_MONDE.ajouterIndicateur(indicateur);
		//			} catch (IllegalAccessException e) {
		//				e.printStackTrace();
		//			}
		//		}

		Monde.LE_MONDE.ajouterIndicateur(banque);
		Monde.LE_MONDE.ajouterIndicateur(stocks[TABLETTES_BQ]);
		Monde.LE_MONDE.ajouterIndicateur(stocks[TABLETTES_MQ]);
		Monde.LE_MONDE.ajouterIndicateur(stocks[TABLETTES_HQ]);
		Monde.LE_MONDE.ajouterIndicateur(stocks[FRIANDISES_MQ]);

		journal = new Journal("Journal Eq5");
		Monde.LE_MONDE.ajouterJournal(journal);
		// On intialise les attributs de nos contrats qui ne varient pas... :
		contratFeveBQEq2 = new ContratFeve() ;
		contratFeveMQEq2 = new ContratFeve();
		contratFeveMQEq3 = new ContratFeve();


		contratFeveBQEq2.setTransformateur(this ) ;
		contratFeveBQEq2.setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq2PROD"));
		contratFeveBQEq2.setQualite(0);
		contratFeveMQEq2.setTransformateur(this);
		contratFeveMQEq2.setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq2PROD"));
		contratFeveMQEq2.setQualite(1);
		contratFeveMQEq3.setTransformateur(this);
		contratFeveMQEq3.setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq3PROD"));
		contratFeveMQEq3.setQualite(1);
	}


	@Override
	public String getNom() {
		return "Eq5TRAN";
	}

	@Override
	public void next() {
		achatAuxProducteurs();
		//        achatAuxTransformateurs(); // inutilisable pour l'instant par manque de doc
		//        venteAuxTransformateurs(); // idem
		//        venteAuxDistributeurs(); // pas encore implémenté
		production();
	}

	/**
	 * @author Thomas Schillaci
	 */
	public void achatAuxProducteurs() {
		MarcheFeve marche = (MarcheFeve) Monde.LE_MONDE.getActeur("Marche intermediaire");
	}

	/**
	 * @author Thomas Schillaci
	 * N.B. On travaille plus souplement qu'avec les producteurs
	 */
	public void achatAuxTransformateurs() {
		// On achete de la poudre HQ a l'eq. 7
		IVendeurPoudre vendeur = (IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN");
		ContratPoudre contrat = null;
		for (ContratPoudre c : vendeur.getCataloguePoudre(this)) {
			if (c.getQualite() == 2) {
				contrat = c;
				break;
			}
		}
		if (contrat == null) {
			journal.ajouter("L'eq 5 n'a pas pu acheter de poudre HQ a l'equipe 7 comme convenu car celle-ci n'en vend pas");
			return;
		}
	}

	public void venteAuxTransformateurs() {

	}

	public void venteAuxDistributeurs() {

	}

	/**
	 * @author Thomas Schillaci
	 */
	public void production() {
		production(POUDRE_BQ, TABLETTES_BQ);
		production(POUDRE_MQ, TABLETTES_MQ);
		production(POUDRE_HQ, TABLETTES_HQ);
		production(POUDRE_MQ, FRIANDISES_MQ);
		production(FEVES_BQ, POUDRE_BQ);
		production(FEVES_MQ, POUDRE_MQ);
	}

	/**
	 * @author Thomas Schillaci
	 * Transforme la merch1 en merch2
	 */
	public void production(int merch1, int merch2) {
		double quantite = Math.min(stocks[merch1].getValeur(), productionSouhaitee[merch2].getValeur());
		if (quantite < productionSouhaitee[merch2].getValeur())
			journal.ajouter("L'eq. 5 n'a pas pu produire assez de " + Marchandises.getMarchandise(merch2) + " par manque de stock de " + Marchandises.getMarchandise(merch1));
		stocks[merch1].setValeur(this, stocks[merch1].getValeur() - quantite);
		stocks[merch2].setValeur(this, stocks[merch2].getValeur() + quantite);
	}

	/**
	 * @author Thomas Schillaci
	 */
	public void depenser(double depense) {
		double resultat = banque.getValeur() - depense;
		banque.setValeur(this, resultat);
		if (resultat < 0)
			journal.ajouter("L'equipe 5 est a decouvert !\nCompte en banque: " + banque.getValeur() + "€");
	}

	@Override
	/**
	 * @author Juliette et Thomas
	 */
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		if (stocks[POUDRE_MQ].getValeur() == 0) return new ContratPoudre[0];

		ContratPoudre[] catalogue = new ContratPoudre[1];
		catalogue[0] = new ContratPoudre(1, (int) stocks[POUDRE_MQ].getValeur(), prix[POUDRE_MQ].getValeur(), acheteur, this, false);
		return catalogue;

	}

	@Override
	/**
	 * @author Juliette
	 * V1 : on n'envoie un devis que si la qualité demandée est moyenne (la seule que nous vendons) et que nous avons assez de stocks
	 */
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur) {
		ContratPoudre[] devis = new ContratPoudre[demande.length];
		for (int i = 0; i < demande.length; i++) {
			if (demande[i].getQualite() != 1 && demande[i].getQuantite() < stocks[POUDRE_MQ].getValeur()) {
				devis[i] = new ContratPoudre(0, 0, 0, acheteur, this, false);
			} else {
				devis[i] = new ContratPoudre(demande[i].getQualite(), demande[i].getQuantite(), prix[POUDRE_MQ].getValeur(), acheteur, this, false);
			}
		}

		return devis;
	}

	@Override
	/**
	 * @author Juliette
	 * V1 : si la réponse est cohérente avec la demande initiale, nos stocks et nos prix, on répond oui
	 */
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
		ContratPoudre[] reponse = new ContratPoudre[devis.length];
		for (int i = 0; i < devis.length; i++) {
			if (devis[i].getQualite() == 1 && devis[i].getQuantite() < stocks[POUDRE_MQ].getValeur() && devis[i].getPrix() == prix[POUDRE_MQ].getValeur()) {
				reponse[i] = new ContratPoudre(devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), true);
			} else {
				reponse[i] = new ContratPoudre(devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), false);
			}
		}
	}

	@Override
	/**
	 * @author Juliette
	 * Pour la V1 on suppose que le contrat est entièrement honnoré
	 */
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
		ContratPoudre[] echangesEffectifs = new ContratPoudre[contrat.length];
		for (int i = 0; i < contrat.length; i++) {
			echangesEffectifs[i] = contrat[i];
		}
		return echangesEffectifs;
	}

	/**
	 * @author Juliette
	 * Dans cette méthode, nous sommes ACHETEURS
	 * Methode permettant de récupérer les devis de poudre correspondant à nos demandes et de décider si on les accepte ou non
	 */
	private void getTousLesDevisPoudre(ContratPoudre[] demande) {
		List<Acteur> listeActeurs = Monde.LE_MONDE.getActeurs();

		List<ContratPoudre[]> devis = new ArrayList<ContratPoudre[]>();

		for (Acteur acteur : listeActeurs) {
			if (acteur instanceof IVendeurPoudre) {
				devis.add(((IVendeurPoudre) acteur).getDevisPoudre(demande, this));
			}
		}
		for (ContratPoudre[] contrat : devis) {
			for (int j = 0; j < 3; j++) {
				if (contrat[j].equals(demande[j])) {
					contrat[j].setReponse(true);
				}
			}
			contrat[0].getVendeur().sendReponsePoudre(contrat, this);
		}


	}

	/**
	 * @author François Le Guernic
	 */
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		/* On achète des fèves de BQ ( seulement à équipe 2 ) et de MQ ( à équipes 2 et 3 ) aux équipes de producteur 
		 * 
		 */



		// Pour récupérer les offres qui nous intéressent, on stockent les informations en mémoire dans les variables
		// d'instance

		for ( ContratFeve c : offrePublique) { 
			if   ( ((Acteur)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==0)
			{ contratFeveBQEq2.setOffrePublique_Quantite(c.getOffrePublique_Quantite()) ;
			contratFeveBQEq2.setOffrePublique_Prix(c.getOffrePublique_Prix()) ;}

			if (((Acteur)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==1) {
				contratFeveMQEq2.setOffrePublique_Quantite(c.getOffrePublique_Quantite());
				contratFeveMQEq2.setOffrePublique_Prix(c.getOffrePublique_Prix());
			}
			if  (((Acteur)c.getProducteur()).getNom()=="Eq3PROD" && c.getQualite()==1) {
				contratFeveMQEq3.setOffrePublique_Quantite(c.getOffrePublique_Quantite());
				contratFeveMQEq3.setOffrePublique_Prix(c.getOffrePublique_Prix());
			}


		}


	}


	/**
	 * Francois Le Guernic
	 */
	@Override
	public ContratFeve[] getDemandePrivee() {

		/*Par convention, dans la liste de  contrats, on aura dans l'ordre :
		 * - le contrat pour les fèves BQ à l'équipe 2
		 * - le contart pour les fèves MQ à l'équipe 2
		 * - le contrat pour les fèves MQ à l'équipe 3
		 */


		ContratFeve[] demandesPrivee = { this.contratFeveBQEq2,this.contratFeveMQEq2, this.contratFeveMQEq3} ;
		this.contratFeveBQEq2.setDemande_Prix(contratFeveBQEq2.getOffrePublique_Prix());
		this.contratFeveBQEq2.setDemande_Quantite((int) achatsSouhaites[FEVES_BQ].getValeur()) ;
		this.contratFeveMQEq2.setDemande_Prix(contratFeveBQEq2.getOffrePublique_Prix());
		this.contratFeveMQEq2.setDemande_Quantite((int) (achatsSouhaites[FEVES_MQ].getValeur()*0.3));
		// On répartit nos achats de MQ en 30 % à l'équipe 2 et 70 % à l'équipe 3
		this.contratFeveMQEq3.setDemande_Prix(contratFeveMQEq3.getOffrePublique_Prix());
		this.contratFeveMQEq3.setDemande_Quantite((int) (achatsSouhaites[FEVES_MQ].getValeur()*0.7));

		return demandesPrivee ;

	} 

	@Override
	public void sendContratFictif(ContratFeve[] listContrats) {
	}

	@Override
	/*
	 * François Le Guernic
	 * @see abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve#sendOffreFinale(abstraction.eq3PROD.echangesProdTransfo.ContratFeve[])
	 */
	public void sendOffreFinale(ContratFeve[] offreFinale) {


		// On actualise nos trois variables d'instance avec les attributs QuantiteProposition et PrixProposition

		for ( ContratFeve c : offreFinale) { 
			if   (((Acteur)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==0)
			{ contratFeveBQEq2.setProposition_Quantite(c.getProposition_Quantite());
			contratFeveBQEq2.setProposition_Prix(c.getProposition_Prix()) ;
			}
			if (((Acteur)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==1) {
				contratFeveMQEq2.setProposition_Quantite(c.getProposition_Quantite());
				contratFeveMQEq2.setProposition_Prix(c.getProposition_Prix());
			}

			if (((Acteur)c.getProducteur()).getNom()=="Eq3PROD" && c.getQualite()==1) {
				contratFeveMQEq3.setProposition_Quantite(c.getProposition_Quantite());
				contratFeveMQEq3.setDemande_Prix(c.getProposition_Prix());
			}

		}
	}
	/*
	 * François Le Guernic
	 * @see abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve#getResultVentes()
	 */
	public ContratFeve[] getResultVentes() {

		ContratFeve[] liste_Contrat = {contratFeveBQEq2, contratFeveMQEq2, contratFeveMQEq3} ;
		for ( ContratFeve c : liste_Contrat) { 
			if ((c.getProposition_Prix()==c.getDemande_Prix()) && c.getProposition_Quantite()==c.getDemande_Quantite())
			{ c.setReponse(true);
			}
			else { c.setReponse(false); }
		}

		return liste_Contrat ;}
	/**
	 * @author Maxim
	 */
	@Override
	public double getReponse(DemandeAO d) {
		switch (d.getQualite()) {
		case 1: {
			journal.ajouter("Eq5 renvoie MAX_VALUE à getReponse(d)");
			return Double.MAX_VALUE;
		}
		case 2:
			if (d.getQuantite() < 0.2 * stocks[FRIANDISES_MQ].getValeur()) {
				journal.ajouter("Eq5 renvoie" + 1.1 * prix[FRIANDISES_MQ].getValeur() * d.getQuantite() + "à getReponse(d)");
				return 1.1 * prix[FRIANDISES_MQ].getValeur() * d.getQuantite();
			}
		case 3: {
			journal.ajouter("Eq5 renvoie MAX_VALUE à getReponse(d)");
			return Double.MAX_VALUE;
		}
		case 4:
			if (d.getQuantite() < 0.2 * stocks[TABLETTES_BQ].getValeur()) {
				journal.ajouter("Eq5 renvoie" + 1.1 * prix[TABLETTES_BQ].getValeur() * d.getQuantite() + "à getReponse(d)");
				return 1.1 * prix[TABLETTES_BQ].getValeur() * d.getQuantite();
			}
		case 5:
			if (d.getQuantite() < 0.2 * stocks[TABLETTES_MQ].getValeur()) {
				journal.ajouter("Eq5 renvoie" + 1.1 * prix[TABLETTES_MQ].getValeur() * d.getQuantite() + "à getReponse(d)");
				return 1.1 * prix[TABLETTES_MQ].getValeur() * d.getQuantite();
			}
		case 6:
			if (d.getQuantite() < 0.2 * stocks[TABLETTES_HQ].getValeur()) {
				journal.ajouter("Eq5 renvoie" + 1.1 * prix[TABLETTES_HQ].getValeur() * d.getQuantite() + "à getReponse(d)");
				return 1.1 * prix[TABLETTES_HQ].getValeur() * d.getQuantite();
			}
		}
		return Double.MAX_VALUE;
	}


}
