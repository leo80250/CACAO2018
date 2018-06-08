package abstraction.eq3PROD;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;  
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.eq3PROD.echangesProdTransfo.IMarcheFeve;
import abstraction.eq3PROD.echangesProdTransfo.MarcheFeve;

//import abstraction.eq1DIST.IVenteConso;

public class Eq3PROD implements Acteur, abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve {
	// 0 = BQ, 1 = MQ, 2 = HQ
	private String nom;
	private Indicateur[] stockFeves;
	private Indicateur[] prixVentesFeves;
	private Indicateur ventesfines;
	private Indicateur ventesmoyennes;
	private Indicateur solde;
	private Indicateur[] productionFeves;
	private Journal journal;
	
	private int stockmoyen;
	private int stockfin;
	
	private ContratFeve[] eq4;
	private ContratFeve[] eq5;
	private ContratFeve[] eq7;
	private ContratFeve[] virtuel;
	private final double[] prix_Ventes_Feves = {1800, 2100, 2500};
	private final double[] prodFeves = {0,0,0};
	MarcheFeve marche=new MarcheFeve();
	
	/**
	 * @author Claire
	 */
	public Eq3PROD() {
		Monde.LE_MONDE.ajouterActeur(marche);
		this.stockmoyen= 75000;
		this.stockfin= 24000;
		this.nom = nom;
		this.ventesfines = new Indicateur("Vente fines de "+this.getNom(), this, 0.0);
		this.ventesmoyennes = new Indicateur("Ventes moyennes de"+this.getNom(),this, 0.0);
		this.stockFeves = new Indicateur[3];
		this.prixVentesFeves = new Indicateur[3];
		this.productionFeves = new Indicateur[3];
		
		this.solde = new Indicateur(this.getNom()+" a un solde de ", this, 0.0);
		
		for(int i = 0; i < 3; i++) {
			String s="";
			if (i==0) {
				s="basses";
			}
			else if(i==1) {
				s="moyennes";
			}
			else {
				s="fines";
			}
			this.stockFeves[i] = new Indicateur(this.getNom()+" a un stock de fèves "+s+" de ", this, 0.0);
			this.prixVentesFeves[i] = new Indicateur(this.getNom()+" a dernièrement acheté des fèves "+s+" au prix de ", this, this.prix_Ventes_Feves[i]);
			this.productionFeves[i] = new Indicateur(this.getNom()+" a dernièrement produit une quantité de feves "+s+" de", this, prodFeves[i] );
			}
		
		this.journal = new Journal("Journal de "+this.getNom());
		Monde.LE_MONDE.ajouterJournal(this.journal);
		Monde.LE_MONDE.ajouterIndicateur(this.ventesmoyennes);
		Monde.LE_MONDE.ajouterIndicateur(this.ventesfines);
		Monde.LE_MONDE.ajouterIndicateur(this.stockFeves[1]);
		Monde.LE_MONDE.ajouterIndicateur(this.stockFeves[2]);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);

	}
	
		public String getNom() {
			return "Eq3PROD";
		}
		
		public void setEq4() {
			this.eq4=eq4;
		}
		
		public void setEq5() {
			this.eq5=eq5;
		}
	
		public void setEq7() {
			this.eq7=eq7;
		}
		
		public void setVirtuel() {
			this.virtuel=virtuel;
		}
		

		public ContratFeve[] getOffrePublique() {
			ContratFeve c1=new ContratFeve((IAcheteurFeve) this, null, 1, this.stockmoyen, 0, 0, /*Prix marche*/ 0.0, 0.0, 0.0, false);
			ContratFeve c2=new ContratFeve((IAcheteurFeve) this, null, 2, this.stockfin, 0, 0, /*Prix marche*/ 0.0, 0.0, 0.0, false);

			ContratFeve[] c=new ContratFeve[2];
			c[0]=c1;
			c[1]=c2;
			return c;
		}

		public void sendDemandePrivee(ContratFeve[] demandePrivee) {
			
		}
		
		/*public ContratFeve[] getOffreFinale() {
			ContratFeve eq4m=
			return null;
		}*/
		
		public void sendResultVentes(ContratFeve[] resultVentes) {
			
		}
		/**
		 * @author Claire
		 */
		public boolean maladieAmerique() {
			double p=Math.random();
			return (p<0.008);
		}
		
		public boolean maladieIndo() {
			return (Math.random()<=0.042);
		}
		/**
		@author Claire
		**/
		public void next() {
			int x=Monde.LE_MONDE.getStep();
			//double k=MarcheFeve.getPrixMarche();
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
			System.out.println(" eq 3 production feve moyennes de "+(prodBresil+prodIndo)+" --> stockMoyen="+this.stockmoyen);
			System.out.println("eq 3 production feve fines de "+prodfin+" --> stockFin="+this.stockfin);
			
		}

		@Override
		public ContratFeve[] getOffreFinale() {
			// TODO Auto-generated method stub
			return null;
		}
	
}
