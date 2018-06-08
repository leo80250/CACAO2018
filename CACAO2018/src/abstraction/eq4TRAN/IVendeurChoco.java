package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;

/**
 * 
 * @author Etienne
 *
 */
public interface IVendeurChoco {

	public GQte getStock();
	
	public GPrix getPrix();
	
	public ArrayList<GQte> getLivraison(ArrayList<GQte> commandes);
	
}
