package abstraction.eq2PROD;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class Eq2PROD implements Acteur, IProducteurCacao, IVendeurFeve {
	private int stockQM;
	private int stockQB;
	private double solde;
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

	public void next() {
		this.stockQM=this.stockQM+ (int) (meteo()*MOY_QM);
		this.stockQB=this.stockQB+ (int) (meteo()*MOY_QB);
	}

	public void sell(int q) {
		this.stockQM=this.stockQM-q;
	}

	public Contrat[] getOffrePublique() {
		return null;
	}

	public void sendDemandePrivee(Contrat[] demandePrivee) {
		
	}

	public Contrat[] getOffreFinale() {
		return null;
	}

	public void sendResultVentes(Contrat[] resultVentes) {
		
	}
	public void sendCoursMarche() {
		
	}

}
