package abstraction.eq2PROD;

import abstraction.fourni.Acteur;

public class Eq2PROD implements Acteur, IProducteurCacao {
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private final static int MOY_QB = 23000; 
	private final static int MOY_QM = 35000; 
	
	private double meteo() {
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
	
	
	public Eq2PROD() {
		this.stockQM=10000000;
		this.stockQB=1000000;
		this.solde = 15000.0;
	}
	
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq2PROD";
	}

	public void next() {
		this.stockQM=this.stockQM+ (int) meteo()*MOY_QM;
		this.stockQB=this.stockQB+ (int) meteo()*MOY_QB;
		// TODO Auto-generated method stub
		
	}

	public void sell(int q) {
		this.stockQM=this.stockQM-q;
	}

}
