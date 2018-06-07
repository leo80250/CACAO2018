package abstraction.eq2PROD;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;
import abstraction.eq2PROD.echangeProd.*;

public class Eq2PROD implements Acteur, IVendeurFeve, IVendeurFevesProd {
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private double coeffStock;
	private ContratFeve[] demandeTran;
	private final static int MOY_QB = 46000; /* pour un step = deux semaines */
	private final static int MOY_QM = 70000; /* pour un step = deux semaines */
	private final static int coutFixe = 70800000;
	private final static double prix_minQM = 1000;
	private final static double prix_minQB = 850;
	
	//constructeur
	public Eq2PROD() {
		this.stockQM=10000000;
		this.stockQB=1000000;
		this.solde = 15000.0;
		this.coeffStock = 1;
		this.demandeTran = new ContratFeve[0];
	}
	
	//accesseur
	public static int getMoyQb() {
		return MOY_QB;
	}
	public static int getMoyQm() {
		return MOY_QM;
	}	
	public int getStockQM() {
		return stockQM;
	}
	public int getStockQB() {
		return stockQB;
	}
	public double getSolde() {
		return solde;
	}
	public String getNom() {
		return "Eq2PROD";
	}
	public double getCoeffSolde() {
		return this.coeffStock;
	}
	public ContratFeve[] getDemandeTran() {
		return this.demandeTran;
	}
	
	/* Alexandre BIGOT */
	public double getPrix() {
		return /*getPrixMarche()* */this.coeffStock ;
	}
	
	

	
	//services
	/* Alexandre BIGOT+Guillaume SALLE */
	private void calculCoeffPrixVentes() {
		double coeffMeteo = meteo();
		double coeffMaladie = maladie();
		this.coeffStock = -0.2*(coeffMeteo-coeffMaladie)+1.2;
	}
	
	private double meteo() {
		/* modélisation par Guillaume SALLE+Agathe CHEVALIER+Alexandre BIGOT, code par Guillaume SALLE */
		double mini = 0.5;
		double maxi = 1.3;
		double x = Math.random();
		if (x<0.005) {
			return mini;
		} else if (x>0.995) {
			return maxi;
		} else {
			return 0.303*x+0.848;
		}
	}
	
	
	/* Modélisation par Alexandre BIGOT+Guillaume SALLE, code par Alexandre BIGOT
	 * Si plantations déjà malades alors la récolte est diminuée de 50% par rapport à la récolte
	 * déjà réduite ou augmentée par la météo et au step suivant la plantation n'est plus malade
	 * sinon il y a 0.5% que la plantation soit infectée et la récolte n'est pas diminuée par 
	 * le facteur maladie  */
	private double maladie() {
		if (this.maladie) {
			this.maladie=false;
			return 0.5;
		} else {
			double x=Math.random();
			if (x<0.005) {
				this.maladie=true;
			}
			return 0.0 ;
		}
	}
	
	public void next() {
		calculCoeffPrixVentes();
		this.stockQM=this.stockQM+ (int) (this.coeffStock*MOY_QM);
		this.stockQB=this.stockQB+ (int) (this.coeffStock*MOY_QB);
	}

	/* Code par Guillaume SALLE+Romain BERNARD+Agathe CHEVALIER */
	public ContratFeve[] getOffrePublique() {
		ContratFeve c1 = new ContratFeve(0,this.stockQB,/*getPrixMarche()* */this.coeffStock*0.85,null,this,false);
		ContratFeve c2 = new ContratFeve(1,this.stockQM,/*getPrixMarche()* */this.coeffStock,null,this,false);
		ContratFeve[] c = new ContratFeve[2];
		c[0]=c1; c[1] = c2;
		return c;
	}
	/* Code par Guillaume SALLE+Romain BERNARD+Agathe CHEVALIER */
	public void sendDemandePrivee(ContratFeve[] demandePrivee) {
		this.demandeTran = demandePrivee; 
	}
	
	/* Par Romain */
	public ContratFeve[] getOffreFinale() {
		ContratFeve[] c=new ContratFeve[demandeTran.length];
		for (int i=0;i<demandeTran.length;i++ ) {
			if (demandeTran[i].getQualite()==0) {
				if (demandeTran[i].getPrix()>=/*getPrixMarche()* */this.coeffStock*0.85) {
					c[i]=demandeTran[i];
			} 	else if (demandeTran[i].getPrix()<prix_minQB) {
				c[i]=new ContratFeve(demandeTran[i].getQualite(),demandeTran[i].getQuantite(),prix_minQB, demandeTran[i].getTransformateur(),demandeTran[i].getProducteur(),demandeTran[i].getReponse());
			}	else {
				c[i]=new ContratFeve(demandeTran[i].getQualite(),demandeTran[i].getQuantite(),0.25*prix_minQB+0.75*demandeTran[i].getPrix(), demandeTran[i].getTransformateur(),demandeTran[i].getProducteur(),demandeTran[i].getReponse());
			}
		}
			 else {
				if (demandeTran[i].getPrix()>=/*getPrixMarche()* */this.coeffStock) {
				c[i]=demandeTran[i];
				} else if (demandeTran[i].getPrix()<prix_minQM) {
				c[i]=new ContratFeve(demandeTran[i].getQualite(),demandeTran[i].getQuantite(),prix_minQM, demandeTran[i].getTransformateur(),demandeTran[i].getProducteur(),demandeTran[i].getReponse());
				} else {
					c[i]=new ContratFeve(demandeTran[i].getQualite(),demandeTran[i].getQuantite(),0.25*prix_minQM+0.75*demandeTran[i].getPrix(), demandeTran[i].getTransformateur(),demandeTran[i].getProducteur(),demandeTran[i].getReponse());
				}
		}
	} return c;
	}

	/*Agathe CHEVALIER + Alexandre BIGOT*/
    public void sendResultVentes(ContratFeve[] resultVentes) {
   	 for (int i=0; i<resultVentes.length;i++) {
   		 if (resultVentes[i].getReponse()) {
   			 
   			 if (resultVentes[i].getQualite()==0) {
   				 this.solde= this.solde + resultVentes[i].getPrix()*resultVentes[i].getQuantite() ;
   				 this.stockQB=this.stockQB - resultVentes[i].getQuantite() ;
   			 }
   			 if (resultVentes[i].getQualite()==1) {
   				 this.solde= this.solde + resultVentes[i].getPrix()*resultVentes[i].getQuantite() ;
   				 this.stockQM=this.stockQM - resultVentes[i].getQuantite() ;
   			 }
   		 }
   	 }
    }

	public void sendCoursMarche() {
	}
	
	/* Alexandre BIGOT
	 * Le cas où la quantité demandée est inférieure au stock n'est au final pas codée
	 * car il est impossible que cela arrive
	 */
	public int acheter(int quantite) {
		if (quantite >= this.stockQB) {
			this.stockQB=this.stockQB - quantite ;
			this.solde = this.solde /*+ quantite*getPrixMarche()*this.coeffStock */ ;
			return quantite ;
		} else {
			return 0 ;
		}
	}
	
}
