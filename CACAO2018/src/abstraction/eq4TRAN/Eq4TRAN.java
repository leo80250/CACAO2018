package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
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
IVendeurChoco,
IAcheteurPoudre,
IVendeurPoudre{ 

	public Acteur Eq4TRAN ; 

	/** Déclaration des indicateurs pour le Journal
	 *  
	 */
	private Indicateur stockTabBQ ;
	private Indicateur stockTabMQ ;
	private Indicateur stockTabHQ ;
	private Indicateur stockChocMQ ;
	private Indicateur stockChocHQ ;
	private Indicateur prodTabBQ ;
	private Indicateur prodTabMQ ;
	private Indicateur prodTabHQ ;
	private Indicateur prodChocMQ ;
	private Indicateur prodChocHQ ;
	private Indicateur solde ; 
	private Journal JournalEq4 = new Journal("JournalEq4") ;
	private Vendeur vendeur;

	/** Contrats en cours pour la méthode next interne
	 * 
	 */
	private ContratFeve[] contratFeveEnCours ; 
	private ContratPoudre[] contratPoudreEnCoursEq7TRAN ;
	private ContratPoudre[] contratPoudreEnCoursEq5TRAN;
	/** Initialisation des indicateurs 
	 * 
	 */
	public Eq4TRAN() {

		/**@Mickaël
		 */
		contratPoudreEnCoursEq7TRAN = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN = new ContratPoudre[3];
		contratPoudreEnCoursEq5TRAN[0] = null;
		contratPoudreEnCoursEq5TRAN[1] = new ContratPoudre(1,27000,100.0, (IAcheteurPoudre)this, (IVendeurPoudre)Monde.LE_MONDE.getActeur("Eq5TRAN"),false);
		contratPoudreEnCoursEq5TRAN[2] = null;
		contratPoudreEnCoursEq7TRAN[0] = null;
		contratPoudreEnCoursEq7TRAN[2] = new ContratPoudre(2,18000,100.0, (IAcheteurPoudre)this,(IVendeurPoudre) Monde.LE_MONDE.getActeur("Eq7TRAN"),false);
		contratPoudreEnCoursEq7TRAN[1] = null;

		stockTabBQ = new Indicateur("stockTabBQ",this,1000) ;
		stockTabMQ = new Indicateur("stockTabMQ",this,1000) ;
		stockTabHQ = new Indicateur("stockTabHQ",this,1000) ;
		stockChocMQ = new Indicateur("stockChocMQ",this,1000) ;
		stockChocHQ = new Indicateur("stockTabHQ",this,1000) ;
		prodTabBQ = new Indicateur("prodTabBQ",this,1000) ;
		prodTabMQ = new Indicateur("prodTabMQ",this,1000) ;
		prodTabHQ = new Indicateur("prodTabHQ",this,1000) ;
		prodChocMQ = new Indicateur("prodChocMQ",this,1000) ;
		prodChocHQ = new Indicateur("prodChocHQ",this,1000) ;
		solde = new Indicateur("solde",this,1000) ;
		vendeur = new Vendeur(0.0, stockChocMQ.getValeur(), stockChocHQ.getValeur(), stockTabBQ.getValeur(), stockTabMQ.getValeur(), stockTabHQ.getValeur());
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
		
		//contratFeveEnCours.add(null) ;
		

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
					prodTabBQ.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite()); 
					double ancienStockTabBQ = stockTabBQ.getValeur() ;
					stockTabBQ.setValeur(Eq4TRAN, ancienStockTabBQ + prodTabBQ.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());
				}
				else if(contratFeveEnCours[i].getQualite() == 1) {
					prodTabMQ.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
					double ancienStockTabMQ = stockTabMQ.getValeur() ;
					stockTabMQ.setValeur(Eq4TRAN, ancienStockTabMQ + prodTabMQ.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());

				}
				else if(contratFeveEnCours[i].getQualite() == 2) {
					prodTabHQ.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
					double ancienStockTabHQ = stockTabHQ.getValeur() ;
					stockTabHQ.setValeur(Eq4TRAN, ancienStockTabHQ + prodTabMQ.getValeur());
					solde.setValeur(Eq4TRAN, contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite());

				}
			}

		}



		/**
		 * @Mickaël
		 */
//		Monde.LE_MONDE.getActeur("Eq5TRAN").getDevisPoudre(contratPoudreEnCoursEq5TRAN, (IAcheteurPoudre) this);
//		Monde.LE_MONDE.getActeur("Eq7TRAN").getDevisPoudre(contratPoudreEnCoursEq7TRAN, (IAcheteurPoudre)this);
//		for (int i=0;i<contratPoudreEnCoursEq5TRAN.length;i++) {
//			contratPoudreEnCoursEq5TRAN[i].setReponse(true);
//			contratPoudreEnCoursEq7TRAN[i].setReponse(true);
//		}
//		if (1==1) { /* Pour l'instant on accepte l'achat sans condition */
//			Monde.LE_MONDE.getActeur("Eq5TRAN").sendReponsePoudre(contratPoudreEnCoursEq5TRAN, (IAcheteurPoudre) this);
//			Monde.LE_MONDE.getActeur("Eq7TRAN").sendReponsePoudre(contratPoudreEnCoursEq7TRAN, (IAcheteurPoudre)this);
//		}
//		
//		ContratPoudre[] contratfinalEq5TRAN = new ContratPoudre[3];
//		ContratPoudre[] contratfinalEq7TRAN = new ContratPoudre[3];
//		contratfinalEq5TRAN = Monde.LE_MONDE.getActeur("Eq5TRAN").getEchangeFinalPoudre(contratPoudreEnCoursEq5TRAN, (IAcheteurPoudre)this);
//		contratfinalEq7TRAN = Monde.LE_MONDE.getActeur("Eq7TRAN").getEchangeFinalPoudre(contratPoudreEnCoursEq7TRAN, (IAcheteurPoudre)this);
//		
//		ArrayList<ContratPoudre> contratPoudreEnCours = null;
//		contratPoudreEnCours.add(contratfinalEq5TRAN[0]);
//		contratPoudreEnCours.add(contratfinalEq5TRAN[1]);
//		contratPoudreEnCours.add(contratfinalEq5TRAN[2]);
//		contratPoudreEnCours.add(contratfinalEq7TRAN[0]);
//		contratPoudreEnCours.add(contratfinalEq7TRAN[1]);
//		contratPoudreEnCours.add(contratfinalEq7TRAN[2]);
//
//		for(int i = 0 ; i < contratPoudreEnCours.size() ; i++ ) {
//
//			/**
//			 * On récupère les qtés de poudre achetée
//			 * On les transforme en produits
//			 * Puis on les stocke
//			 */
//
//			if(contratPoudreEnCours.get(i).getReponse()) {
//				if (contratPoudreEnCours.get(i).getQualite() == 1) {
//					prodChocMQ.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getQuantite());
//					double ancienStockChocMQ = stockChocMQ.getValeur() ;
//					stockChocMQ.setValeur(Eq4TRAN, ancienStockChocMQ + prodChocMQ.getValeur());
//					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());
//
//				} else if (contratPoudreEnCours.get(i).getQualite() == 2 ) {
//					prodChocHQ.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
//					double ancienStockChocHQ = stockChocHQ.getValeur() ;
//					stockChocHQ.setValeur(Eq4TRAN, ancienStockChocHQ + prodChocHQ.getValeur()); 
//					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());
//				}
//			}
//		} 


	}

	public void journalEq4() {
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabBQ.getValeur());
		JournalEq4.ajouter("Stock des tablettes Moyenne Qualité = "+stockTabMQ.getValeur());
		JournalEq4.ajouter("Stock des tablettes Basse Qualité = "+stockTabHQ.getValeur());
		JournalEq4.ajouter("Stock des chocolats Moyenne Qualité = "+stockChocMQ.getValeur());
		JournalEq4.ajouter("Stock des chocolats Haute Qualité = "+stockChocHQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Basse Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Moyenne Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production des tablettes Haute Qualité = "+prodTabBQ.getValeur());
		JournalEq4.ajouter("Production de chocolats Moyenne Qualité = "+prodChocMQ.getValeur());
		JournalEq4.ajouter("Production de chocolats Haute Qualité = "+prodChocHQ.getValeur());

	}


	@Override
	public void sell(int q) {
		// TODO Auto-generated method stub

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
	public void sendContratFictif() {
		// TODO Auto-generated method stub

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

	/*
	 * @Etienne
	 */
	@Override
	public GQte getStock() {

		return vendeur.getStock();
	}

	//Etienne
	@Override
	public GPrix getPrix() {

		return vendeur.getPrix();
	}

	//Etienne
	@Override
	public ArrayList<GQte> getLivraison(ArrayList<GQte> commandes) {
		ArrayList<GQte> livraison = new ArrayList<GQte>();
		livraison.addAll(vendeur.getLivraison(commandes));
		stockChocMQ.setValeur(Eq4TRAN,stockChocMQ.getValeur()-livraison.get(0).getqBonbonMQ()-livraison.get(1).getqBonbonMQ());
		stockChocHQ.setValeur(Eq4TRAN, stockChocHQ.getValeur()-livraison.get(0).getqBonbonHQ()-livraison.get(1).getqBonbonHQ());
		stockTabBQ.setValeur(Eq4TRAN, stockTabBQ.getValeur()-livraison.get(0).getqTabletteBQ()-livraison.get(1).getqTabletteBQ());
		stockTabMQ.setValeur(Eq4TRAN, stockTabMQ.getValeur()-livraison.get(0).getqTabletteMQ()-livraison.get(1).getqTabletteMQ());
		stockTabHQ.setValeur(Eq4TRAN, stockTabHQ.getValeur()-livraison.get(0).getqTabletteHQ()-livraison.get(1).getqTabletteHQ());
		double s = 0.0;
		for(int i=0;i<2;i++) {
			s+=livraison.get(i).getqBonbonBQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqBonbonBQ(), 1);
			s+=livraison.get(i).getqBonbonMQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqBonbonMQ(), 2);
			s+=livraison.get(i).getqBonbonHQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqBonbonHQ(), 3);
			s+=livraison.get(i).getqTabletteBQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqTabletteBQ(), 4);
			s+=livraison.get(i).getqTabletteMQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqTabletteMQ(), 5);
			s+=livraison.get(i).getqTabletteHQ()*vendeur.getPrix().getPrixProduit(livraison.get(i).getqTabletteHQ(), 6);
		}
		solde.setValeur(Eq4TRAN, s);
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

}
