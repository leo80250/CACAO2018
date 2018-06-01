package abstraction.eq2PROD;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class Eq2PROD implements Acteur, IVendeurFeve {
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private double coeffStock;
	private ContratFeve[] demandeTran;
	private final static int MOY_QB = 23000; 
	private final static int MOY_QM = 35000; 
	
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

	
	//services
	
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
	
	public ContratFeve[] getOffreFinale() {
		return null;
	}
	public void sendResultVentes(ContratFeve[] resultVentes) {
	}
	public void sendCoursMarche() {
	}
	
}
