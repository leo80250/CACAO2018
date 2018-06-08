package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;

/**
 * 
 * @author Noémie Rigaut
 *
 */

public class Eq4TRAN implements Acteur, 
								ITransformateur, 
								IAcheteurFeve,
								IVendeurChoco { 

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
	
	/** Contrats en cours pour la méthode next interne
	 * 
	 */
	private ContratFeve[] contratFeveEnCours ; 
	private ArrayList<ContratPoudre> contratPoudreEnCours ;

	
 
	/** Initialisation des indicateurs 
	 * 
	 */
	public Eq4TRAN() {
		
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
			 * Le coût total de l'achat est retiré à la solde
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
		 * pour contrat poudre
		 */
		 for(int i = 0 ; i < contratPoudreEnCours.size() ; i++ ) {
			/**
			 * On récupère les qtés de poudre achetée
			 * On les transforme en produits
			 * Puis on les stocke
			 */
			
			if(contratPoudreEnCours.get(i).getReponse()) {
				if (contratPoudreEnCours.get(i).getQualite() == 1) {
					prodChocMQ.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getQuantite());
					double ancienStockChocMQ = stockChocMQ.getValeur() ;
					stockChocMQ.setValeur(Eq4TRAN, ancienStockChocMQ + prodChocMQ.getValeur());
					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());
				
				} else if (contratPoudreEnCours.get(i).getQualite() == 2 ) {
					prodChocHQ.setValeur(Eq4TRAN, contratFeveEnCours[i].getQuantite());
					double ancienStockChocHQ = stockChocHQ.getValeur() ;
					stockChocHQ.setValeur(Eq4TRAN, ancienStockChocHQ + prodChocHQ.getValeur()); 
					solde.setValeur(Eq4TRAN, contratPoudreEnCours.get(i).getPrix()*contratPoudreEnCours.get(i).getQuantite());
					}
				}
		}
		
		
		/** Màj des stocks pour les distributeurs
		 * 
		 */
	
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

	@Override
	public GQte getStock() {
		
		return null;
	}

	@Override
	public GPrix getPrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GQte getLivraison(GQte[] commandes) {
		// TODO Auto-generated method stub
		return null;
	}

}
