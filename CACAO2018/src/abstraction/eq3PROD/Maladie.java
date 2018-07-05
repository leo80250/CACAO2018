package abstraction.eq3PROD;

import java.util.Random;

/**
 * Implémentation d'une Maladie
 * @author Grégoire
 */

public class Maladie {
	
	public double probaD;
	public double pertesProd;
	public int maladieActive;
	public int dureeMax;
	public String nom;
	
	public Maladie(double probaD, double pertesProd, int dureeMax, String nom) {
		this.probaD = probaD;
		this.pertesProd = pertesProd;
		this.dureeMax = dureeMax;
		this.maladieActive = 0;
		this.nom = nom;
	}

	public boolean declencherMaladie() {
		return (this.maladieActive == 0 && Math.random() < this.probaD);
	}
	
	public void setMaladieActive() {
		if (this.declencherMaladie()) {
			Random r = new Random();
			this.maladieActive = r.nextInt(dureeMax+1);
		}
	}
	
	
	public double pertesMaladie() {
		if (this.maladieActive > 0) {
			this.maladieActive -= 1;
			return (1.0-this.pertesProd);
		}
		return 1.0;
	}
	
	public String toString() {
		return "Nom : "+this.nom+"\nMaladie active pour "+this.maladieActive+" steps\n"
				+"Pertes : "+this.pertesMaladie()+"\n--------------";
	}
	
}
