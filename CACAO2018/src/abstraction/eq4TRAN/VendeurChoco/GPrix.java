package abstraction.eq4TRAN.VendeurChoco;

import java.util.ArrayList;

/**
 * 
 * @author Etienne
 *
 */
public class GPrix {
	
	/*
	 * Classe définissant un tableau de prix étalonnés par tranche (tableau fournit par les transformateurs)
	 */
	
	private float[] intervalles;
	private float[] prix;
	
	public GPrix(float[] intervalles, float[] prix) {
		if(intervalles.length!=prix.length) {
			throw new IllegalArgumentException("Le nombre d'intervalles ne correspond pas au nombre de tarifs annoncés.");
		}
		else {
			this.intervalles=intervalles;
			this.prix=prix;
		}
	}
	
	public float[] getPrix2() {
		return this.prix;
	}
	
	public float[] getIntervalles() {
		return this.intervalles;
	}
	
	public float getPrix(float quantite) {
		/* idProduit est un entier compris entre 1 et 6 : 
		 * 1=BonbonsBQ
		 * 2=BonbonsMQ
		 * 3=BonbonsHQ
		 * 4=TablettesBQ
		 * 5=TablettesMQ
		 * 6=TablettesHQ
		 */
		int j =0;
		while((j<getIntervalles().length)&&(prix[j]>quantite)) {
			j++;
		}
		return prix[j];
	}
}
