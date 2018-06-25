package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix2;

/**
 * 
 * @author Etienne
 *
 */

// Interface décrivant les échanges entre Transformateurs et Distributeurs, en se plaçant du côté Transformateur
// (vendeur de chocolats)
public interface IVendeurChocoBis {
	
	public ArrayList<Integer> getStock();
	
	public GPrix2 getPrix();
	
	public ArrayList<ArrayList<Integer>> getLivraison(ArrayList<ArrayList<Integer>> commandes);
}
