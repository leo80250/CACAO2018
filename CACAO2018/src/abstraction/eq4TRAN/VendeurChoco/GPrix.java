package abstraction.eq4TRAN.VendeurChoco;
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
	private float[][] prix;
	
	public GPrix(float[] intervalles, float[][] prix) {
		if(intervalles.length!=prix[0].length) {
			throw new IllegalArgumentException("Le nombre d'intervalles ne correspond pas au nombre de tarifs annoncés.");
		}
		if(prix.length!=6) {
			throw new IllegalArgumentException("Le nombre de tableaux de prix annoncés ne correspond pas au nombre de produits mis en vente (6)");
		}
		else {
			this.intervalles=intervalles;
			this.prix=prix;
		}
	}
	
	public float getPrix(float quantite, float[] prix) {
		int i =0;
		while((i<intervalles.length-1)&&(intervalles[i]>quantite)) {
			i++;
		}
		return prix[i];
	}
}
