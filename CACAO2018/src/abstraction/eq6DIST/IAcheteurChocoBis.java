package abstraction.eq6DIST;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix2;

public interface IAcheteurChocoBis {
	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix,ArrayList<ArrayList<Integer>> Stock);
	public void livraison(ArrayList<Integer> livraison,double paiement);
}
