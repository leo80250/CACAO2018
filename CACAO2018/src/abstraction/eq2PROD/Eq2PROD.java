package abstraction.eq2PROD;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class Eq2PROD implements Acteur, IProducteurCacao, IVendeurFeve {
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private final static int MOY_QB = 23000; 
	private final static int MOY_QM = 35000; 
	
	//constructeur
	public Eq2PROD() {
		this.stockQM=10000000;
		this.stockQB=1000000;
		this.solde = 15000.0;
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

	
	//services
	
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
	
	/* Modélisation par Guillaume SALLE+Romain BERNARD+Agathe CHEVALIER, code par Romain BERNARD+Agathe CHEVALIER*/
	private double CoeffPrixVente(double coeffmeteo) {
		return( -0.2*coeffmeteo + 1.2 );
	}

	
	/* Modélisation par Alexandre BIGOT+Guillaume SALLE, code par Alexandre BIGOT */
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
		double CoeffMeteo = meteo();
		this.stockQM=this.stockQM+ (int) (CoeffMeteo*MOY_QM);
		this.stockQB=this.stockQB+ (int) (CoeffMeteo*MOY_QB);
		double CoeffPrixVente = CoeffPrixVente(CoeffMeteo);
		double PrixVenteQM = getPrixMarche()*CoeffPrixVente;
		double PrixVenteQB = getPrixMarche()*CoeffPrixVente*0.85;
	}

	public void sell(int q) {
		this.stockQM=this.stockQM-q;
	}

	public ContratFeve[] getOffrePublique() {
		return null;
	}

	public void sendDemandePrivee(ContratFeve[] demandePrivee) {
		
	}

	public ContratFeve[] getOffreFinale() {
		return null;
	}

	public void sendResultVentes(ContratFeve[] resultVentes) {
	}
	
	public void sendCoursMarche() {
	}
	
}
