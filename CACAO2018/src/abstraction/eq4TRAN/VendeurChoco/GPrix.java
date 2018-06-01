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
	
	private ArrayList <Double[][]> prix;
	
	public GPrix(ArrayList<Double[]> intervalles, ArrayList<Double[]> prix) {
		if(intervalles.get(0).length!=prix.get(0).length) {
			throw new IllegalArgumentException("Le nombre d'intervalles ne correspond pas au nombre de tarifs annoncés.");
		}
		if((prix.size()!=6)||(intervalles.size()!=6)) {
			throw new IllegalArgumentException("Les informations n'ont pas été fournies pour les 6 produits.");
		}
		else {
			for(int i=0;i<6;i++) {
				Double[][] element = new Double[2][intervalles.get(0).length];
				element[0] = intervalles.get(i);
				element[1] = prix.get(i);
				this.prix.set(i, element);
			}
		}
	}
	
	public Double getPrixProduit(double quantite, int idProduit) {
		/* idProduit est un entier compris entre 1 et 6 : 
		 * 1=BonbonsBQ
		 * 2=BonbonsMQ
		 * 3=BonbonsHQ
		 * 4=TablettesBQ
		 * 5=TablettesMQ
		 * 6=TablettesHQ
		 */
		int j =0;
		while((j<prix.get(0)[0].length)&&(prix.get(idProduit-1)[0][j]>quantite)) {
			j++;
		}
		return prix.get(idProduit-1)[1][j];
	}
}
