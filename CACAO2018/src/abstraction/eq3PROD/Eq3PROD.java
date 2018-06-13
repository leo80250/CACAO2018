package abstraction.eq3PROD;

import java.util.ArrayList;
import java.util.Arrays;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq3PROD.echangesProdTransfo.MarcheFeve;

//import abstraction.eq1DIST.IVenteConso;

public class Eq3PROD implements Acteur, abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve {
	// 0 = BQ, 1 = MQ, 2 = HQ
	private String nom;
	private int stockmoyen;
	private int stockfin;
	private int solde;
	private ArrayList<ContratFeve> listeContrats ; 
	private final double[] prix_Ventes_Feves = {1800, 2100, 2500};
	private final double[] prodFeves = {0,0,0};
	public MarcheFeve marche;
	
	
	/**
	 * @author Morgane
	 */
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String s) {
		this.nom=s;
	}
	
	public int getStockMoyen() {
		return this.stockmoyen;
	}
	
	public int getStockFin() {
		return this.stockfin;
	}
	
	public int getSolde() {
		return this.solde;
	}
	
	public void setSolde(int solde) {
		this.solde=solde;
	}
	
	/**
	 * @author Claire
	 */
	public Eq3PROD() {
		
		setNom(nom);
		this.indicateurQM = new Indicateur("Stock de Eq3PROD de moyenne qualité",this,getStockMoyen());
		this.indicateurQH = new Indicateur("Stock de Eq3PROD de haute qualité",this,getStockFin());
		setStockQMoy(indicateurQM);
		setStockQHaut(indicateurQH);
		
		setJournal(new Journal("Journal de Eq3PROD"));
		Monde.LE_MONDE.ajouterJournal(getJournal());
		Monde.LE_MONDE.ajouterIndicateur(getStockQHaut());
		Monde.LE_MONDE.ajouterIndicateur(getStockQMoy());
		
		ArrayList<Acteur> listActeurs = Monde.LE_MONDE.getActeurs();
		ArrayList<Acteur> producteurs = new ArrayList<Acteur>();
		ArrayList<Acteur> transformateurs = new ArrayList<Acteur>();
		
		for (Acteur acteur : listActeurs) {
			Class[] listInterfaces = acteur.getClass().getInterfaces();
			if (Arrays.toString(listInterfaces).contains("IVendeurFeve")) {
				producteurs.add(acteur);
			} else if (Arrays.toString(listInterfaces).contains("IAcheteurFeve")) {
				transformateurs.add(acteur);
			}
		}
		
		int taille = transformateurs.size();
		IAcheteurFeve[] listTran = new IAcheteurFeve[taille];
		for (int i = 0 ; i < taille ; i++) {
			listTran[i] = (IAcheteurFeve) transformateurs.get(i);
		}
		
		int taille_bis = producteurs.size(); 	 	   						 	  	 	
		IVendeurFeve[] listProd = new IVendeurFeve[taille_bis]; 	 	   						 	  	 	
		for (int i = 0 ; i < taille_bis ; i++) { 	 	   						 	  	 	
			listProd[i] = (IVendeurFeve) producteurs.get(i); 	 	   						 	  	 	
		}		 	 	   						 	  	 	

		
		this.marche = new MarcheFeve("Marche central", listTran, listProd);
		
		Monde.LE_MONDE.ajouterActeur(marche);
		this.stockmoyen= 75000;
		this.stockfin= 24000;
		this.nom = "Eq3PROD";
	}
	
		
		public ArrayList<ContratFeve> getListeContrats(){
			return this.listeContrats;
		}
		
		/**
		 * @author Morgane et Pierre
		 */
		public ContratFeve[] getOffrePublique() { 
			ContratFeve c1=new ContratFeve(null, this, 1, this.stockmoyen, 0, 0, marche.getPrixMarche(), 0.0, 0.0, false);
			ContratFeve c2=new ContratFeve(null, this, 2, this.stockfin, 0, 0, marche.getPrixMarche(), 0.0, 0.0, false); 
			ContratFeve[] c=new ContratFeve[2]; c[0]=c1; c[1]=c2; 
			return c; } 


		/**
		 * @author Morgane et Pierre
		 */
		public void sendDemandePrivee(ContratFeve[] demandePrivee) { 
			//HashMap<Integer, HashMap<Acteur, ContratFeve>> asso = new HashMap<Integer, HashMap<Acteur, ContratFeve>>(); 
			//asso.put(demandePrivee[i].getTransformateur(), demandePrivee[i); 
			for (int i = 0; i < demandePrivee.length ; i++) { 
				if (demandePrivee[i].getDemande_Prix() >= demandePrivee[i].getOffrePublique_Prix()*0.9){ 
					listeContrats.add(demandePrivee[i]) ; 
				} 
			} 
			}  

		/**
		 * @author Morgane
		 */
		public ContratFeve[] getOffreFinale() { 	 	  	  		   		 	 	
			int quantite_1 = 0;  	 	  	  		   		 	 	
			int quantite_2 = 0; 	 	  	  		   		 	 	
			 	 	  	  		   		 	 	
			for (ContratFeve contrat : listeContrats) { 	 	  	  		   		 	 	
				if (contrat.getQualite() == 1) { 	 	  	  		   		 	 	
					quantite_1 += contrat.getDemande_Quantite() ; 	 	  	  		   		 	 	
				} else { 	 	  	  		   		 	 	
					quantite_2 += contrat.getDemande_Quantite() ; 	 	  	  		   		 	 	
				} 	 	  	  		   		 	 	
			} 	 	  	  		   		 	 	
			 	 	  	  		   		 	 	
			ArrayList<ContratFeve> listeContrats_bis = new ArrayList<ContratFeve>() ; 	 	  	  		   		 	 	
			 	 	  	  		   		 	 	
			for (ContratFeve contrat : listeContrats) {  	 	  	  		   		 	 	
				 	 	  	  		   		 	 	
				if (contrat.getQualite() == 1 ) { 	 	  	  		   		 	 	
					 	 	  	  		   		 	 	
					if(quantite_1 <= this.stockmoyen) { 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()) ; 	 	  	  		   		 	 	
						 	 	  	  		   		 	 	
					} else if (quantite_1 > this.stockmoyen) { 	 	  	  		   		 	 	
						// stock: 100  	 	  	  		   		 	 	
						// demande : 70, 40, 30  	 	  	  		   		 	 	
						// total demande : 140 	 	  	  		   		 	 	
						// répartition: 40*100/140 	 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()*this.stockmoyen/quantite_1); 	 	  	  		   		 	 	
					}	 	  	  		   		 	 	
		 	  	  		   		 	 	
				} else if (contrat.getQualite() == 2) {	 	 	  	  		   		 	 	
			   		 	 	 	 	  	  		   		 	 	
					if (quantite_2 <= this.stockfin) { 	 	 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite());	  	  		   		 	 	 	 	  	  		   		 	 	
						 	 	  	  		   		 	 	
					} else if (quantite_2 > this.stockfin) { 	 	  	  		   		 	 	
						contrat.setProposition_Quantite(contrat.getDemande_Quantite()*this.stockfin/quantite_2); 	 	  	  		   		 	 		 	 	
					} 	 	  	  		   		 	 	
					 	 	  	  		   		 	 		 	  	  		   		 	 	
				}
				
				contrat.setProposition_Prix(contrat.getDemande_Prix()); 	 	  	  		   		 	 	
				listeContrats_bis.add(contrat); 
				 	 	  	  		   		 	 	
			} 	 	  	  		   		 	 	
				 	 	  	  		   		 	 	
			return (ContratFeve[]) listeContrats_bis.toArray(); 
		}

		/**
		 * @author Morgane
		 */
		public void sendResultVentes(ContratFeve[] resultVentes) { 	 	  	  		   		 	 	
			for (ContratFeve contrat : resultVentes) {  	 	  	  		   		 	 	
				if(contrat.getQualite() == 1) { 	 	  	  		   		 	 	
					if(contrat.getReponse() == true) { 	 	  	  		   		 	 	
						this.stockmoyen -= contrat.getProposition_Quantite() ; 	 	  	  		   		 	 	
						solde += contrat.getProposition_Prix()*contrat.getProposition_Quantite() ;  	 	  	  		   		 	 	
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
			this.indicateurQM = new Indicateur("Stock de Eq3PROD de moyenne qualité",this,this.stockmoyen);
			this.indicateurQH = new Indicateur("Stock de Eq3PROD de haute qualité",this,this.stockfin);
			setStockQMoy(indicateurQM);
			setStockQHaut(indicateurQH);
			this.getJournal().ajouter("Quantité moyenne qualité = "+ getStockQMoy());
			this.getJournal().ajouter("Quantité haute qualité ="+ getStockQHaut());
			this.getJournal().ajouter("------------------------------------------------------------------------------");
			indicateurQM.setValeur(this, getStockMoyen());
			indicateurQH.setValeur(this, getStockFin());
			/*System.out.println(" eq 3 production feve moyennes de "+(prodBresil+prodIndo)+" --> stockMoyen="+this.stockmoyen);
			System.out.println("eq 3 production feve fines de "+prodfin+" --> stockFin="+this.stockfin);*/
			
		}
		
		//Journal 
		/**
		 * @author Claire
		 */
		
		private Indicateur indicateurQM;
		private Indicateur indicateurQH;
		private Journal journal;
		private String nomEq;
		private Indicateur stockQM=new Indicateur ("Stock de Eq3PROD de moyenne qualité", this, 75000);
		private Indicateur stockQH=new Indicateur ("Stock de Eq3PROD de haute qualité", this, 24000);;
		
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
		
		/*public Eq3PROD(Monde monde, String nom) {
			setNomEq(nom);
			this.indicateurQM = new Indicateur("Stock de "+getNomEq()+" de moyenne qualité",this,getStockMoyen());
			this.indicateurQH = new Indicateur("Stock de "+getNomEq()+" de haute qualité",this,getStockFin());
			setStockQMoy(indicateurQM);
			setStockQHaut(indicateurQH);
			
			setJournal(new Journal("Journal de"+getNomEq()));
			Monde.LE_MONDE.ajouterJournal(getJournal());
			Monde.LE_MONDE.ajouterIndicateur(getStockQHaut());
			Monde.LE_MONDE.ajouterIndicateur(getStockQMoy());
		}*/



	
}
