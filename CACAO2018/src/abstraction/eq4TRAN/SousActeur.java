package abstraction.eq4TRAN;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV4;
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

public class SousActeur implements Acteur, 
ITransformateur, IAcheteurFeveV4,
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
	private Indicateur chiffreDAffaire ; 
	//Indicateur de notre solde bancaire
	private Indicateur solde ; 
	//Journal rendant compte de nos activités et de l'évolution de nos indicateurs
	private Journal JournalEq4 = new Journal("JournalEq4") ;
	//Rôle de vendeur que nous incarnerons à chaque next() et qui se mettra à jour à cette même fréquence
	private Vendeur vendeur;
	//On crée une liste pour ranger nos stocks
	private ArrayList<Indicateur> Stocks;
	private List<ContratFeveV3> contratFeveEnCours ; 
	private ContratPoudre[] contratPoudreEnCoursEq7TRAN ;
	private ContratPoudre[] contratPoudreEnCoursEq5TRAN;
	private int taillePME ;
	

	public SousActeur(Journal JournalEq4, Indicateur solde, Indicateur stockTabBQ, Indicateur stockTabMQ, Indicateur stockTabHQ, Indicateur stockChocMQ, Indicateur stockChocHQ, Indicateur prodTabBQ, Indicateur prodTabMQ, Indicateur prodTabHQ,  Indicateur prodChocMQ , Indicateur prodChocHQ) {
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
		this.taillePME = this.taillePME = (int)(11 + (Math.random() * (250 - 11))) ;	
		
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
	public List<ContratFeveV3> getContratFeveEnCours() {
		return this.contratFeveEnCours ;
	}
	public void setContratFeveEnCours(List<ContratFeveV3> contratFeveEnCours) {
		this.contratFeveEnCours = contratFeveEnCours;
	}
	public ContratPoudre[] getContratPoudreEnCoursEq7TRAN() {
		return contratPoudreEnCoursEq7TRAN ;
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
	public int getTaillePME() {
		return this.taillePME ;
	}

	// Etienne Raveau
	// Getter pour accéder à la liste de nos stocks
	public ArrayList<Indicateur> getStocks(){
		return Stocks;
	}

	public void setStocks(ArrayList<Indicateur> l ) {
		this.Stocks=l;
	}
	/*
	 * @author Noémie 
	 * Implémentation des méthodes de l'interface IAcheteurFeveV4
	 */
	
	// Avant il faut,  récupérer offre publique & discuter de ce que l'on veut
	
	@Override 
	public void sendOffrePubliqueV3(List<ContratFeveV3> offrePublique) {
		this.contratFeveEnCours = offrePublique ; 
	}
	
	
	/*
	 * @author Charles, Noémie 
	 */
	@Override
	public List<ContratFeveV3> getDemandePriveeV3() {
		int[] demande= {13000,70000,25000};
		 
		double[] prixMin= { 100000.0 , 100000.0 , 100000.0 } ;
		int[] min= {-1,-1,-1};
		int[] max= {-1,-1,-1};
		for (int i=0;i<this.contratFeveEnCours.size();i++) {
			int qualite=this.contratFeveEnCours.get(i).getOffrePublique_Quantite();
			if (this.contratFeveEnCours.get(i).getOffrePublique_Prix()<prixMin[qualite]) {
				prixMin[qualite]=this.contratFeveEnCours.get(i).getOffrePublique_Prix();
				if (min[i]!=-1) {
					max[qualite]=i;
				}
				min[i]=this.contratFeveEnCours.get(i).getQualite();
			}
		}
		for (int j=0;j<3;j++) {
			this.contratFeveEnCours.get(min[j])
			.setDemande_Quantite(Math.min(demande[min[j]],this.contratFeveEnCours.get(min[j]).getOffrePublique_Quantite()/3));
			if (max[j]!=-1) {
				this.contratFeveEnCours.get(max[j]).setDemande_Quantite(demande[min[j]]-Math.min(demande[min[j]],this.contratFeveEnCours.get(min[j]).getOffrePublique_Quantite()/3));
			}
		}
		return this.contratFeveEnCours;
	}
	
	/*
	 * @author Noémie 
	 * Rien car concerne l'acteur fictif
	 */
	@Override
	public void sendContratFictifV3(List<ContratFeveV3> listContrats) {
		
	}


	/*
	 * @author Noémie , Charles 
	 */
	@Override
	public void sendOffreFinaleV3(List<ContratFeveV3> offreFinale) {
		this.contratFeveEnCours = offreFinale ; 
	}
	

	/*
	 * @author Charles, Noémie
	 */
	@Override
	/*
	 *  Stratégie à réécrire pour l'acceptation ou non des contrats 
	 *  et revoir comment ranger les contrats selon le producteur 
	 */
	public List<ContratFeveV3> getResultVentesV3() {
		for (ContratFeveV3 contrat : this.contratFeveEnCours) {
			if ( contrat.getReponse() ) {
			double coutTotal = contrat.getProposition_Prix()*contrat.getProposition_Quantite() ;
				if (coutTotal < this.solde.getValeur()) {
					contrat.setReponse(true);
				}
			}
		}
		return null ; 
		
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


	@Override
	public void next() {
		// NE RIEN CODER, VOIR EQ4TRAN 
		
	}
	

	
	/*
	 * Calcule puis débite les coûts fixes et variables  la solde du SousActeur
	 * 
	 * Coûts fixes: 
	 * Salaires, coûts de maintenance
	 * Plus une PME est grande, plus ses charges fixes sont 
	 * élevées et moins ses charges variables sont élevées
	 */
	
	public void coutsSupplementaires() {
		double CA = this.chiffreDAffaire.getValeur() ; 
		double soldeActuelle = this.solde.getValeur() ;
		double chargesFixes = 0 ;
		double chargesVariables = 0 ; 
		if (this.taillePME < 50 ) {
			chargesFixes = 0 ;
			chargesVariables = 0.4*CA ; 
		} else if ((50 <=this.taillePME)&&(this.taillePME < 150 )) {
			chargesFixes = 0 ;
			chargesVariables = 0.35*CA ;
		} else {
			chargesFixes = 0 ; 
			chargesVariables = 0.3*CA ; 
		}
		this.solde.setValeur(this, soldeActuelle - chargesFixes - chargesVariables);
	}
 }
