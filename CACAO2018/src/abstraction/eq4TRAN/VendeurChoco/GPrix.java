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
	
	private ArrayList <Double> intervalles;
	private ArrayList <Double[]> prix;
	
	public GPrix(ArrayList<Double> intervalles, ArrayList<Double[]> prix) {
		if(intervalles.size()!=prix.get(0).length) {
			throw new IllegalArgumentException("Le nombre d'intervalles ne correspond pas au nombre de tarifs annoncés.");
		}
		if(prix.size()!=6) {
			throw new IllegalArgumentException("Le nombre de tableaux de prix annoncés ne correspond pas au nombre de produits mis en vente (6)");
		}
		else {
			this.intervalles=intervalles;
			this.prix=prix;
		}
	}
	
	public Double getPrix(double quantite, ArrayList<Double> prix) {
		int i =0;
		while((i<intervalles.size()-1)&&(intervalles.get(i)>quantite)) {
			i++;
		}
		return prix.get(i);
	}
}
