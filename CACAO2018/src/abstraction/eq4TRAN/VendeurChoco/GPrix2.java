package abstraction.eq4TRAN.VendeurChoco;

import java.util.ArrayList;


/**
 * 
 * @author Etienne
 *
 *
	*/

// Utiliser GPrix2 à la place de GPrix, GPrix2 étant la version améliorée
public class GPrix2{
	
	/* On considerera par la suite toujours l'ordre suivant des produits dans les tableaux :
	1 : StockChocBQ
	2 : StockChocMQ
	3 : StockChocHQ
	4 : StockTabBQ
	5 : StockTabMQ
	6 : StockTabHQ */
	
	private ArrayList<Double[]> intervalles; // 6 tableaux concaténés représentants les intervalles voulus de prix pour 
											 // chaque produit.
											 // ex pour un produit :
											 // Double[] interval = {10.0,50.0,100.0,250.0,500.0,750.0,1000.0};
	private ArrayList<Double[]> prix; // Les prix associés (le premier prix correspond à l'achat du produit dans une 
	// quantité appartenant à [0;10[ puis le deuxième prix à l'intervalle [10,50[ etc..
	// ex (pour un produit) : Double[] prix2 = {4.0, 3.95, 3.9, 3.875, 3.85, 3.825, 3.8};
	
	/*
	 * Classe définissant un tableau de prix étalonnés par tranche (tableau fournit par les transformateurs)
	 */
	public GPrix2(ArrayList<Double[]> intervalles, ArrayList<Double[]> prix) {
		if(intervalles.size()!=prix.size()) {
			throw new IllegalArgumentException("Les informations ne sont pas remplies pour chacune des 6 équipes");
		}
		else {
			for(int i=0; i<intervalles.size();i++) {
				if(intervalles.get(i).length!=prix.get(i).length) {
					throw new IllegalArgumentException("Le nombre d'intervalles ne correspond pas au nombre de tarifs annoncés.");
				}
			}
			this.intervalles=intervalles;
			this.prix=prix;
		}
	}
	
	public ArrayList<Double[]> getPrix() {
		return this.prix;
	}
	
	public ArrayList<Double[]> getIntervalles() {
		return this.intervalles;
	}
	
	// Permet -sans avoir à manipuler les tableaux de prix- d'accéder facilement au prix d'un proudit en indiquant
	//la quantité désirée et l'id du Produit
	
	public double getPrixProduit(int quantite, int idProduit) {
		/* idProduit est un entier compris entre 1 et 6 : 
		 * 1=BonbonsBQ
		 * 2=BonbonsMQ
		 * 3=BonbonsHQ
		 * 4=TablettesBQ
		 * 5=TablettesMQ
		 * 6=TablettesHQ
		 */
		if(getIntervalles().get(idProduit-1).length>0) {
			int j =0;
			while((j<getIntervalles().get(idProduit-1).length)&&(getIntervalles().get(idProduit-1)[j]>quantite)) {
				j++;
			}
			if(j==getIntervalles().get(idProduit-1).length) return getPrix().get(idProduit-1)[j-1];
			else return getPrix().get(idProduit-1)[j];
			
		}
		else return 0;
	}
}
