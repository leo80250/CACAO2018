package abstraction.eq5TRAN;

import static abstraction.eq5TRAN.Marchandises.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq5TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IvendeurOccasionnelChoco {

	// cf Marchandises.java pour obtenir l'indexation
	private Indicateur[] productionSouhaitee; // ce qui sort de nos machines en kT
	private Indicateur[] achatsSouhaites; // ce qu'on achète aux producteurs en kT
	private float facteurStock; // facteur lié aux risques= combien d'itérations on peut tenir sans réception de feves/poudre
	private Indicateur[] stocksSouhaites; // margeStock = facteurStock * variationDeStockParIteration, en kT
	private Indicateur[] stocks; // les vrais stocks en kT

	private Indicateur banque; // en milliers d'euros
	private Indicateur[] prix;

	private Journal journal;

	/**
	 * @author: Thomas Schillaci
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
		productionSouhaitee[TABLETTES_MQ] = new Indicateur("Eq5 - Production souhaitee de tablettes MQ",this,575);
		productionSouhaitee[TABLETTES_HQ] = new Indicateur("Eq5 - Production souhaitee de tablettes HQ",this,115);
		productionSouhaitee[POUDRE_MQ] = new Indicateur("Eq5 - Production souhaitee de poudre MQ",this,50);
		productionSouhaitee[POUDRE_HQ] = new Indicateur("Eq5 - Production souhaitee de poudre HQ",this,0);
		productionSouhaitee[FRIANDISES_MQ] = new Indicateur("Eq5 - Production souhaitee de friandises MQ",this,115);

		achatsSouhaites[FEVES_BQ] = new Indicateur("Eq5 - Achats souhaites de feves BQ", this, 360);
		achatsSouhaites[FEVES_MQ] = new Indicateur("Eq5 - Achats souhaites de feves MQ",this,840);
		achatsSouhaites[TABLETTES_BQ] = new Indicateur("Eq5 - Achats souhaites de tablettes BQ",this,0);
		achatsSouhaites[TABLETTES_MQ] = new Indicateur("Eq5 - Achats souhaites de tablettes MQ",this,0);
		achatsSouhaites[TABLETTES_HQ] = new Indicateur("Eq5 - Achats souhaites de tablettes HQ",this,0);
		achatsSouhaites[POUDRE_MQ] = new Indicateur("Eq5 - Achats souhaites de poudre MQ",this,0);
		achatsSouhaites[POUDRE_HQ] = new Indicateur("Eq5 - Achats souhaites de poudre HQ",this,0);
		achatsSouhaites[FRIANDISES_MQ] = new Indicateur("Eq5 - Achats souhaites de friandises MQ", this, 0);

		prix[FEVES_BQ] = new Indicateur("Eq5 - Prix de feves BQ", this, 0);
		prix[FEVES_MQ] = new Indicateur("Eq5 - Prix de feves MQ",this,0);
		prix[TABLETTES_BQ] = new Indicateur("Eq5 - Prix de tablettes BQ",this,100);
		prix[TABLETTES_MQ] = new Indicateur("Eq5 - Prix de tablettes MQ",this,100);
		prix[TABLETTES_HQ] = new Indicateur("Eq5 - Prix de tablettes HQ",this,0);
		prix[POUDRE_MQ] = new Indicateur("Eq5 - Prix de poudre MQ",this,100);
		prix[POUDRE_HQ] = new Indicateur("Eq5 - Prix de poudre HQ",this,0);
		prix[FRIANDISES_MQ] = new Indicateur("Eq5 - Prix de friandises MQ", this, 100);

		for (int i = 0; i < nbMarchandises; i++) {
			stocksSouhaites[i] = new Indicateur("Eq5 - Stocks souhaites de " + Marchandises.getMarchandise(i), this, productionSouhaitee[i].getValeur() + achatsSouhaites[i].getValeur());
			stocks[i] = new Indicateur("Eq5 - Stocks de " + Marchandises.getMarchandise(i), this, stocksSouhaites[i].getValeur()); // on initialise les vrais stocks comme étant ce que l'on souhaite avoir pour la premiere iteration
		}

		banque=new Indicateur("Eq5 - Banque",this,16_000); // environ benefice 2017 sur nombre d'usines

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

		journal = new Journal("Journal Eq5");
		Monde.LE_MONDE.ajouterJournal(journal);
	}

	@Override
	public String getNom() {
		return "Eq5TRAN";
	}

	@Override
	public void next() {

	}

	@Override
	/**
	 * @author: Juliette et Thomas
	 */
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		if (stocks[POUDRE_MQ].getValeur()==0) return new ContratPoudre[0];

		ContratPoudre[] catalogue = new ContratPoudre[1];
		catalogue[0]=new ContratPoudre(1,(int) stocks[POUDRE_MQ].getValeur(),prix[POUDRE_MQ].getValeur(),acheteur,this,false);
		return catalogue;

	}

	@Override
	/**
	 * @author Juliette
	 * V1 : on n'envoie un devis que si la qualité demandée est moyenne (la seule que nous vendons) et que nous avons assez de stocks
	 */
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur) {
		ContratPoudre[] devis = new ContratPoudre[demande.length];
		for (int i=0; i<demande.length;i++) {
			if (demande[i].getQualite()!=1 && demande[i].getQuantite()<stocks[POUDRE_MQ].getValeur()) {
				devis[i]=new ContratPoudre(0,0,0,acheteur,this,false);
			}
			else{
				devis[i]=new ContratPoudre(demande[i].getQualite(), demande[i].getQuantite(), prix[POUDRE_MQ].getValeur(), acheteur, this, false);
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
		for (int i=0; i<devis.length;i++){
			if (devis[i].getQualite()!=1 && devis[i].getQuantite() < stocks[POUDRE_MQ].getValeur() && devis[i].getPrix() == prix[POUDRE_MQ].getValeur()) {
				reponse[i] = new ContratPoudre (devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), true);
			}
			else {
				reponse[i] = new ContratPoudre (devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), false);
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
		for (int i=0;i<contrat.length;i++) {
			echangesEffectifs[i]=contrat[i];
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
			if(acteur instanceof IVendeurPoudre) {
				devis.add(((IVendeurPoudre)acteur).getDevisPoudre(demande, this));
			}
		}
		for(ContratPoudre[] contrat : devis) {
			for(int j=0;j<3;j++) {
				if(contrat[j].equals(demande[j])) {
					contrat[j].setReponse(true);
				}
			}
			contrat[0].getVendeur().sendReponsePoudre(contrat, this);
		}


	}

	@Override
	public double getReponse(DemandeAO d) {
		switch(d.getQualite()) {
		case 1: {
			journal.ajouter("Eq5 renvoie MAX_VALUE à getReponse(d)");
			return Double.MAX_VALUE;
		}
		case 2: if (d.getQuantite() < 0.2*stocks[FRIANDISES_MQ].getValeur()) {
			journal.ajouter("Eq5 renvoie"+ 1.1*prix[FRIANDISES_MQ].getValeur()*d.getQuantite() +"à getReponse(d)");
			return 1.1*prix[FRIANDISES_MQ].getValeur()*d.getQuantite();
		}
		case 3: {
			journal.ajouter("Eq5 renvoie MAX_VALUE à getReponse(d)");
			return Double.MAX_VALUE;
		}
		case 4: if (d.getQuantite() < 0.2*stocks[TABLETTES_BQ].getValeur()) {
			journal.ajouter("Eq5 renvoie"+ 1.1*prix[TABLETTES_BQ].getValeur()*d.getQuantite() +"à getReponse(d)");
			return 1.1*prix[TABLETTES_BQ].getValeur()*d.getQuantite();
		}
		case 5: if (d.getQuantite() < 0.2*stocks[TABLETTES_MQ].getValeur()) {
			journal.ajouter("Eq5 renvoie"+ 1.1*prix[TABLETTES_MQ].getValeur()*d.getQuantite() +"à getReponse(d)");
			return 1.1*prix[TABLETTES_MQ].getValeur()*d.getQuantite();
		}
		case 6: if (d.getQuantite() < 0.2*stocks[TABLETTES_HQ].getValeur()) {
			journal.ajouter("Eq5 renvoie"+ 1.1*prix[TABLETTES_HQ].getValeur()*d.getQuantite() +"à getReponse(d)");
			return 1.1*prix[TABLETTES_HQ].getValeur()*d.getQuantite();
		}
		}
		return Double.MAX_VALUE;
		/** @Author: Maxim **/
	}


}