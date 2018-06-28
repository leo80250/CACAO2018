package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq4TRAN.VendeurChoco.Vendeur;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;

public abstract class SousActeur implements Acteur, 
ITransformateur, 
IAcheteurFeve,
IVendeurChocoBis,
IAcheteurPoudre,
IVendeurPoudre {
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
	//Indicateur de notre solde bancaire
	private Indicateur solde ; 
	//Journal rendant compte de nos activités et de l'évolution de nos indicateurs
	private Journal JournalEq4 = new Journal("JournalEq4") ;
	//Rôle de vendeur que nous incarnerons à chaque next() et qui se mettra à jour à cette même fréquence
	private Vendeur vendeur;
	//On crée une liste pour ranger nos stocks
	private ArrayList<Indicateur> Stocks;
	private ContratFeve[] contratFeveEnCours ; 
	private ContratPoudre[] contratPoudreEnCoursEq7TRAN ;
	private ContratPoudre[] contratPoudreEnCoursEq5TRAN;

	public SousActeur(Indicateur stockTabBQ, Indicateur stockTabMQ, Indicateur stockTabHQ, Indicateur stockChocMQ, Indicateur stockChocHQ, Indicateur prodTabBQ, Indicateur prodTabMQ, Indicateur prodTabHQ,  Indicateur prodChocMQ , Indicateur prodChocHQ, ContratFeve[] contratFeveEnCours, ContratPoudre[] contratPoudreEnCoursEq7TRAN, ContratPoudre[] contratPoudreEnCoursEq5TRAN) {
		this.JournalEq4 = JournalEq4;
		this.prodChocHQ = prodChocHQ;
		this.prodChocMQ = prodChocMQ;
		this.prodTabBQ = prodTabBQ;
		this.prodTabHQ = prodTabHQ;
		this.prodTabMQ = prodTabMQ;
		this.solde = solde;
		this.stockChocHQ = stockChocHQ;
		this.stockChocMQ = stockChocMQ;
		this.stockTabBQ = stockTabBQ;
		this.stockTabHQ = stockTabHQ;
		this.stockTabMQ = stockTabMQ;
		this.vendeur = vendeur;
		this.contratFeveEnCours = contratFeveEnCours;
		this.contratPoudreEnCoursEq5TRAN = contratPoudreEnCoursEq5TRAN;
		this.contratPoudreEnCoursEq7TRAN = contratPoudreEnCoursEq7TRAN;
	}

	public String getNom() {
		return "Eq4TRAN";
	}

	public void sell(int q) {
		// TODO Auto-generated method stub

	}

	public Indicateur getStockTabBQ() {
		return stockTabBQ;
	}
	public void setStockTabBQ(Indicateur stockTabBQ) {
		this.stockTabBQ = stockTabBQ;
	}
	public Indicateur getStockTabMQ() {
		return stockTabMQ;
	}
	public void setStockTabMQ(Indicateur stockTabMQ) {
		this.stockTabMQ = stockTabMQ;
	}
	public Indicateur getStockTabHQ() {
		return stockTabHQ;
	}
	public void setStockTabHQ(Indicateur stockTabHQ) {
		this.stockTabHQ = stockTabHQ;
	}
	public Indicateur getStockChocMQ() {
		return stockChocMQ;
	}
	public void setStockChocMQ(Indicateur stockChocMQ) {
		this.stockChocMQ = stockChocMQ;
	}
	public Indicateur getStockChocHQ() {
		return stockChocHQ;
	}
	public void setStockChocHQ(Indicateur stockChocHQ) {
		this.stockChocHQ = stockChocHQ;
	}
	public Indicateur getProdTabBQ() {
		return prodTabBQ;
	}
	public void setProdTabBQ(Indicateur prodTabBQ) {
		this.prodTabBQ = prodTabBQ;
	}
	public Indicateur getProdTabMQ() {
		return prodTabMQ;
	}
	public void setProdTabMQ(Indicateur prodTabMQ) {
		this.prodTabMQ = prodTabMQ;
	}
	public Indicateur getProdTabHQ() {
		return prodTabHQ;
	}
	public void setProdTabHQ(Indicateur prodTabHQ) {
		this.prodTabHQ = prodTabHQ;
	}
	public Indicateur getProdChocMQ() {
		return prodChocMQ;
	}
	public void setProdChocMQ(Indicateur prodChocMQ) {
		this.prodChocMQ = prodChocMQ;
	}
	public Indicateur getProdChocHQ() {
		return prodChocHQ;
	}
	public void setProdChocHQ(Indicateur prodChocHQ) {
		this.prodChocHQ = prodChocHQ;
	}
	public Indicateur getSolde() {
		return solde;
	}
	public void setSolde(Indicateur solde) {
		this.solde = solde;
	}
	public Journal getJournalEq4() {
		return JournalEq4;
	}
	public void setJournalEq4(Journal journalEq4) {
		JournalEq4 = journalEq4;
	}
	public Vendeur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Vendeur vendeur) {
		this.vendeur = vendeur;
	}
	public ContratFeve[] getContratFeveEnCours() {
		return contratFeveEnCours;
	}
	public void setContratFeveEnCours(ContratFeve[] contratFeveEnCours) {
		this.contratFeveEnCours = contratFeveEnCours;
	}
	public ContratPoudre[] getContratPoudreEnCoursEq7TRAN() {
		return contratPoudreEnCoursEq7TRAN;
	}
	public void setContratPoudreEnCoursEq7TRAN(ContratPoudre[] contratPoudreEnCoursEq7TRAN) {
		this.contratPoudreEnCoursEq7TRAN = contratPoudreEnCoursEq7TRAN;
	}
	public ContratPoudre[] getContratPoudreEnCoursEq5TRAN() {
		return contratPoudreEnCoursEq5TRAN;
	}
	public void setContratPoudreEnCoursEq5TRAN(ContratPoudre[] contratPoudreEnCoursEq5TRAN) {
		this.contratPoudreEnCoursEq5TRAN = contratPoudreEnCoursEq5TRAN;
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
		solde.setValeur(this, s); //On met à jour notre solde bancaire
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
	
	/*
	 * @Noémie 
	 * 
	 * Méthodes pour prendre en compte les coûts fixes et variables 
	 */
	
	/*
	 * Méthode qui affiche le CA du dernier next en récupérant ce qu'on a payé
	 * dans les contrats fèves et contrats poudres et ce qu'on a vendu en contrats
	 * chocolats 
	 */
	public double getCA() { 
		double CA = 0 ;
		// Achat de fèves aux équipes 2 et 3
		for (int  i = 0 ; i < this.contratFeveEnCours.length ; i++ ) {
			if (contratFeveEnCours[i].getReponse()) {
				/*
				 * à changer car Interface ContratFeve deprecated
				 */
				CA -= contratFeveEnCours[i].getPrix()*contratFeveEnCours[i].getQuantite() ; 
			}
		}
		// Achat de poudre à l'équipe 5 
		for (int i = 0 ; i < this.contratPoudreEnCoursEq5TRAN.length ; i++) {
			if (contratPoudreEnCoursEq5TRAN[i].isReponse()) {
				CA -= contratPoudreEnCoursEq5TRAN[i].getPrix()*contratPoudreEnCoursEq5TRAN[i].getQuantite() ;
			}
		}
		// Achat de poudre à l'équipe 7
		for (int i = 0 ; i < this.contratPoudreEnCoursEq7TRAN.length ; i++) {
			if (contratPoudreEnCoursEq7TRAN[i].isReponse()) {
					CA -= contratPoudreEnCoursEq7TRAN[i].getPrix()*contratPoudreEnCoursEq7TRAN[i].getQuantite() ;
				}
		}
		// Vente de chocolats aux distributeurs
		
		
		return CA ; 
		}
 }
