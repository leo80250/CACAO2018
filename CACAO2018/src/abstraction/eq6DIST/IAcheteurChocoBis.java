package abstraction.eq6DIST;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix2;

public interface IAcheteurChocoBis {
	/**
	 * 
	 * @param Prix Correspond aux prix des différents transformateurs sous la forme d'une liste de GPrix2 (documentation dans le package eq4TRAN.VendeurChoco)
	 * @param Stock Correspond aux stocks des différents transformateurs sous la forme d'une liste de stock (qui est lui-même une liste d'entiers)
	 * @return La commande que le ditributeur veut passer à chacun des distributeurs, sous la forme d'une liste de commande (qui est elle même une liste d'entiers) ou chaque indice correspond à un transformateur différent
	 */
	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix,ArrayList<ArrayList<Integer>> Stock);
	/**
	 * 
	 * @param livraison Correspond à la livraison globale reçu par le distributeur sous la forme d'une liste d'entiers
	 * @param paiement Correspond à ce qui doit être déduit du solde bancaire.
	 */
	public void livraison(ArrayList<Integer> livraison,double paiement);
}
