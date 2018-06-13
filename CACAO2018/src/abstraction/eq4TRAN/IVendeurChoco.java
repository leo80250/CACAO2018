package abstraction.eq4TRAN;

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
	
	public GQte getLivraison(GQte[] commandes);
	
}
