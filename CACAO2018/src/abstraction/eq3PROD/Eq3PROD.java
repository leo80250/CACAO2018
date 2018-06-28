package abstraction.eq3PROD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV2;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeveV3;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV2;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeveV4;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV2;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV4;
import abstraction.eq3PROD.echangesProdTransfo.MarcheFeve;

//import abstraction.eq1DIST.IVenteConso;

public class Eq3PROD implements Acteur, abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV4 {
	// 0 = BQ, 1 = MQ, 2 = HQ
	private String nom;
	private List<List<Integer>> stockmoyen;
	private List<List<Integer>> stockfin;
	private double solde;
	private ArrayList<ContratFeveV3> listeContrats ; 
	private final double[] prix_Ventes_Feves = {1800, 2100, 2500};
	private final double[] prodFeves = {0,0,0};
	public MarcheFeve marche;
	
	private Journal journal;
	private String nomEq;
	private Indicateur stockQM=new Indicateur ("Stock de Eq3PROD de moyenne qualité", this, 75000);
	private Indicateur stockQH=new Indicateur ("Stock de Eq3PROD de haute qualité", this, 24000);;
	
	
	
	/**
	 * @author Morgane
	 */
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String s) {
		this.nom=s;
	}
	

	public int getSolde() {
		return this.solde;
	}
	
	public void setSolde(int solde) {
		this.solde=solde;
	}
	
	
	/**
	 * @author Pierre
	 */
	public List<List<Integer>> getStockmoyen() {
		return stockmoyen;
	}

	public List<List<Integer>> getStockfin() {
		return stockfin;
	}
	
	public int quantiteStockMoyen() {
		int stockm = 0;
		for(int i=0; i<this.stockmoyen.size(); i++) {
			stockm+=stockmoyen.get(i).get(0);
		}
		return stockm;
		
	}
	
	public int quantiteStockFin() {
		int stockf = 0;
		for(int i=0; i<this.stockfin.size(); i++) {
			stockf+=stockfin.get(i).get(0);
		}		
		return stockf;
	}
	
	
	public void ajouterStockMoyen(int stock) {	
		List<Integer> stockm = new ArrayList<Integer>(2);
		stockm.set(0, stock);
		stockm.set(1, 0);
		this.stockmoyen.add(stockm);
	}
	
	public void ajouterStockFin(int stock) {	
		List<Integer> stockf = new ArrayList<Integer>(2);
		stockf.set(0, stock);
		stockf.set(1, 0);
		this.stockfin.add(stockf);
	}
	

	
	/**
	 * @author Claire
	 */
	public Eq3PROD() {
		
		setNom("Eq3PROD");
		
		setJournal(new Journal("Journal de Eq3PROD"));
		Monde.LE_MONDE.ajouterJournal(getJournal());
		Monde.LE_MONDE.ajouterIndicateur(getStockQHaut());
		Monde.LE_MONDE.ajouterIndicateur(getStockQMoy());
		
		ArrayList<Acteur> listActeurs = Monde.LE_MONDE.getActeurs();
		ArrayList<IVendeurFeveV4> producteurs = new ArrayList<IVendeurFeveV4>();
		ArrayList<IAcheteurFeveV4> transformateurs = new ArrayList<IAcheteurFeveV4>();
		
		for (Acteur acteur : listActeurs) {
			Class[] listInterfaces = acteur.getClass().getInterfaces();
			if (Arrays.toString(listInterfaces).contains("IVendeurFeveV4")) {
				producteurs.add((IVendeurFeveV4) acteur);
			} else if (Arrays.toString(listInterfaces).contains("IAcheteurFeveV4")) {
				transformateurs.add((IAcheteurFeveV4) acteur);
			}
		}
		
		this.marche = new MarcheFeve("Marche central", transformateurs, producteurs);
		
		Monde.LE_MONDE.ajouterActeur(marche);
		
		this.stockmoyen = new ArrayList<List<Integer>>();
		this.stockfin = new ArrayList<List<Integer>>();
		this.ajouterStockMoyen(75000);
		this.ajouterStockFin(24000);
		
		this.nom = "Eq3PROD";
	}
	
		
		public ArrayList<ContratFeveV3> getListeContrats(){
			return this.listeContrats;
		}
		
		/**
		 * @author Morgane et Pierre
		 */
		public List<ContratFeveV3> getOffrePublique() { 
			ContratFeveV3 c1=new ContratFeveV3(null, this, 1, this.quantiteStockMoyen(), 0, 0, marche.getPrixMarche(), 0.0, 0.0, false);
			ContratFeveV3 c2=new ContratFeveV3(null, this, 2, this.quantiteStockFin(), 0, 0, marche.getPrixMarche(), 0.0, 0.0, false); 
			List<ContratFeveV3> c= new ArrayList<ContratFeveV3>() ; 
			c.add(c1); 
			c.add(c2); 
			return c; 
		} 


		/**
		 * @author Morgane et Pierre
		 */
		public void sendDemandePrivee(List<ContratFeveV3> demandePrivee) { 
			//HashMap<Integer, HashMap<Acteur, ContratFeve>> asso = new HashMap<Integer, HashMap<Acteur, ContratFeve>>(); 
			//asso.put(demandePrivee[i].getTransformateur(), demandePrivee[i); 
			for (int i = 0; i < demandePrivee.size(); i++) { 
				if(demandePrivee.get(i).getQualite() == 2) {
					if (demandePrivee.get(i).getDemande_Prix() >= demandePrivee.get(i).getOffrePublique_Prix()*0.9){  	 	  	  		   		 	 	
						listeContrats.add(demandePrivee.get(i)) ; 
					}
				} else {
					if (demandePrivee.get(i).getDemande_Prix() >= 1000){  	 	  	  		   		 	 	
						listeContrats.add(demandePrivee.get(i)) ;  
					}
				} 
			} 
		}  

		/**
		 * @author Morgane
		 */
		public List<ContratFeveV3> getOffreFinale() { 

			int quantite_1 = 0;  	 	  	  		   		 	 	
			int quantite_2 = 0; 	 	  	  		   		 	 	
			
			//quantité totale demandée 
			for (ContratFeveV3 contrat : listeContrats) { 	 	  	  		   		 	 	
				if (contrat.getQualite() == 1) { 	 	  	  		   		 	 	
					quantite_1 += contrat.getDemande_Quantite() ; 	 	  	  		   		 	 	
				} else { 	 	  	  		   		 	 	
					quantite_2 += contrat.getDemande_Quantite() ; 	 	  	  		   		 	 	
				} 	 	  	  		   		 	 	
			} 	 	  	  		   		 	 	
			 	 	  	  		   		 	 	
			ArrayList<ContratFeveV3> listeContrats_bis = new ArrayList<ContratFeveV3>() ; 	
			
			//répartition quantité demandée en fonction stocks 	 	  	  		   		 	 	
			for (ContratFeveV3 contrat : listeContrats) {  	 	  	  		   		 	 	
				 	 	  	  		   		 	 	
				if (contrat.getQualite() == 1 ) { 	 	  	  		   		 	 	
					 	 	  	  		   		 	 	
					if(quantite_1 <= this.quantiteStockMoyen()) { 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()) ; 	 	  	  		   		 	 	
						 	 	  	  		   		 	 	
					} else { 	 	  	  		   		 	 	
						// stock: 100  	 	  	  		   		 	 	
						// demande : 70, 40, 30  	 	  	  		   		 	 	
						// total demande : 140 	 	  	  		   		 	 	
						// répartition: 40*100/140 	 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()*this.quantiteStockMoyen()/quantite_1); 	 	  	  		   		 	 	
					}	 	  	  		   		 	 	
		 	  	  		   		 	 	
				} else if (contrat.getQualite() == 2) {	 	 	  	  		   		 	 	
			   		 	 	 	 	  	  		   		 	 	
					if (quantite_2 <= this.quantiteStockFin()) { 	 	 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite());	  	  		   		 	 	 	 	  	  		   		 	 	
						 	 	  	  		   		 	 	
					} else { 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()*this.quantiteStockFin()/quantite_2); 	 	  	  		   		 	 		 	 	
					} 	 	  	  		   		 	 	
					 	 	  	  		   		 	 		 	  	  		   		 	 	
				}
				
				//Proposition_Prix
				//Haute qualité: +/- 10% de l'offre publique
				//Moyenne qualité: allignement par rapport aux producteurs 2
				if (contrat.getQualite() == 2) {
					contrat.setProposition_Prix(contrat.getDemande_Prix());
				} else {
					if (contrat.getDemande_Prix() >= contrat.getOffrePublique_Prix()) {
						contrat.setProposition_Prix(contrat.getDemande_Prix());
					} else if (contrat.getDemande_Prix() > 1000 && contrat.getDemande_Prix() < contrat.getOffrePublique_Prix()) {
						contrat.setProposition_Prix(contrat.getDemande_Prix()*0.67 + contrat.getOffrePublique_Prix()*0.33);
					}
				}
				
				 	 	  	  		   		 	 	
				listeContrats_bis.add(contrat); 
				 	 	  	  		   		 	 	
			} 	 	  	  		   		 	 	
			return listeContrats_bis;
		}

		/**
		 * @author Morgane  solde: chiffre d'affaire  (prix*qtite) - charges salariales - charges fixes (
		 */
		public void sendResultVentes(List<ContratFeveV3> resultVentes) { 	 	  	  		   		 	 	
			for (ContratFeveV3 contrat : resultVentes) {  	 	  	  		   		 	 	
				if(contrat.getQualite() == 1) { 	 	  	  		   		 	 	
					if(contrat.getReponse() == true) { 	 	  	  		   		 	 	
						this.stockmoyen -= contrat.getProposition_Quantite() ; 	
						
						double masse_salariale_Indo = contrat.getProposition_Quantite()* ; 
						double masse_salariale_Bresil ; 
						double masse_salariale_Equateur ; 
						
						solde += contrat.getProposition_Prix()*contrat.getProposition_Quantite() - 
									contrat.getProposition_Quantite()* - 
									contrat.getProposition_Quantite()*;  	 	  	  		   		 	 	
					}  	 	  	  		   		 	 	
				} else { 	 	  	  		   		 	 	
					this.stockfin -= contrat.getProposition_Quantite() ;  	 	  	  		   		 	 	
					solde += contrat.getProposition_Prix()*contrat.getProposition_Quantite() ; 	 	  	  		   		 	 	
				} 	 	  	  		   		 	 	
			} 	 	  	  		   		 	 	
		}

		/**
		 * @author Claire
		 */
		public boolean maladieAmerique() {
			return (Math.random()<0.008);
		}
		
		public boolean maladieIndo() {
			return (Math.random()<=0.042);
		}
		/**
		@author Claire
		**/
		public void next() {
			int x=Monde.LE_MONDE.getStep();
			int prodBresil=0;
			int prodIndo=0;
			int prodfin=0;
			prix_Ventes_Feves[1]=marche.getPrixMarche();
			if (x%12<=3) {               /*Janvier;Fevrier*/
				prodBresil=30000;
				prodfin=24000;
			}
			if (x%24==4 || x%24==5 || x%24==10 || x%24==11) {    /*Mars;Juin*/
				prodBresil=30000;
				prodIndo=24000;
				prodfin=45000;
			}
			if (x%24>5 && x%24<=9) {      /*Avril;Mai*/         
				prodIndo=24000;
				prodfin=45000;
			}
			if (x%24==12 || x%24==13) {   /*Juillet*/
				prodBresil=30000;
				prodIndo=24000;
			}
			if (x%24>13 && x%24<=15) {    /*Aout*/
				prodBresil=30000;
			}
			if (x%24>15 && x%24<=17) {    /*Septembre*/      
				prodBresil=30000;
				prodIndo=45000;
			}
			if (x%24>17 && x%24<=23) {             /*Octobre;Novembre;Decembre*/
				prodBresil=30000;
				prodIndo=45000;
				prodfin=24000;
			}
			if (this.maladieIndo()) {
				prodIndo=(int)(prodIndo*0.9);
			}
			
			if (this.maladieAmerique()) {
				prodBresil=(int)(prodIndo*0.4);
				prodfin=(int)(prodfin*0.4);
			}
			this.stockmoyen = this.stockmoyen+prodBresil+prodIndo;
			this.stockfin = this.stockfin+prodfin;
			this.prodFeves[1]=prodBresil+prodIndo;
			this.prodFeves[2]=prodfin;
			this.stockQH.setValeur(this,this.stockfin);
			this.stockQM.setValeur(this, this.stockmoyen);
			this.getJournal().ajouter("Quantité moyenne qualité = "+ getStockQMoy().getValeur());
			this.getJournal().ajouter("Quantité haute qualité ="+ getStockQHaut().getValeur());
			this.getJournal().ajouter("------------------------------------------------------------------------------");
	
		}
		
		//Journal 
		/**
		 * @author Claire
		 */
		
	
		public Journal getJournal() {
			return this.journal;
		}
		public String getNomEq() {
			return this.nomEq;
		}
		public void setNomEq(String s) {
			this.nomEq = s;
		}
		public void setJournal(Journal j) {
			this.journal = j;
		}
		public Indicateur getStockQHaut() {
			return this.stockQH;
		}
		public void setStockQHaut(Indicateur i) {
			this.stockQH = i;
		}
		public Indicateur getStockQMoy() {
			return this.stockQM;
		}
		public void setStockQMoy(Indicateur i) {
			this.stockQM = i;
		}

		@Override
		public List<ContratFeveV3> getOffrePubliqueV3() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendDemandePriveeV3(List<ContratFeveV3> demandePrivee) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<ContratFeveV3> getOffreFinaleV3() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendResultVentesV3(List<ContratFeveV3> resultVentes) {
			// TODO Auto-generated method stub
			
		}
		



	
}
