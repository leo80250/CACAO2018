package abstraction.eq3PROD;

import java.util.Random;

public class Maladie {
	
	public double probaD;
	public double pertesProd;
	public int maladieActive;
	public int dureeMax;
	
	public Maladie(double probaD, double pertesProd, int dureeMax) {
		this.probaD = probaD;
		this.pertesProd = pertesProd;
		this.dureeMax = dureeMax;
		this.maladieActive = 0;
	}
	
	public void setMaladieActive() {
		Random r = new Random();
		this.maladieActive = r.nextInt(dureeMax);
	}
	
	public boolean declencherMaladie() {
		return (this.maladieActive == 0 && Math.random() < this.probaD);
	}
	
	public double pertesMaladie() {
		if (this.maladieActive > 0) {
			this.maladieActive -= 1;
			return (1.0-this.pertesProd);
		}
		return 1.0;
	}
	
}
