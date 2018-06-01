package abstraction.eq3PROD;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
//import abstraction.eq1DIST.IVenteConso;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Eq3PROD implements Acteur, abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve {
	
	
	private int stockmoyen;
	private int stockfin;
	private ContratFeve[] eq4;
	private ContratFeve[] eq5;
	private ContratFeve[] eq7;
	private ContratFeve[] virtuel;
	/*private int tpsnonmaladieIndo;
	private int tpsnonmaladieAmerique;*/
	
	private Journal journal;
	
	
	public Eq3PROD() {
		this.stockmoyen= 75000;
		this.stockfin= 24000;

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
			ContratFeve c1=new ContratFeve(1,this.stockmoyen,/*Prixmarche*/0,null,this,false);
			ContratFeve c2=new ContratFeve(2,this.stockfin,/*Prixmarche*/0,null,this,false);
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
	
		public boolean maladieAmerique() {
			double p=Math.random();
			return (p<0.008);
		}
		
		public boolean maladieIndo() {
			return (Math.random()<=0.042);
		}
		
		public void next() {
			int x=Monde.LE_MONDE.getStep();
			int prodBresil=0;
			int prodIndo=0;
			int prodfin=0;
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
			System.out.println(" eq 3 production feve moyennes de "+(prodBresil+prodIndo)+" --> stockMoyen="+this.stockmoyen);
			System.out.println("eq 3 production feve fines de "+prodfin+" --> stockFin="+this.stockfin);
			/*IVenteConso vendeur = (IVenteConso) (Monde.LE_MONDE.getActeur("Eq6DIST"));
			vendeur.sell(100);*/
		}

		@Override
		public ContratFeve[] getOffreFinale() {
			// TODO Auto-generated method stub
			return null;
		}
	
}