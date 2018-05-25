package abstraction.eq4TRAN;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;

/**
 * 
 * @author Etienne
 *
 */
public interface IVendeurChoco {
	
	/* Méthode renvoyant les stocks que nous possédons, et ce pour chacun de nos produits
	 * et dans chacune des qualités
	 */
	public GQte getStock();
	
	/* Méthode renvoyant un tableau de prix échelonné par quantités commandées */
	public GPrix getPrix();
	
	/* Renvoie sous forme de tableau par type de produit et qualité, la livraison
	 * effective aux distributeurs
	 */
	public GQte getLivraison(GQte[] commandes);
	
}
