package abstraction.eq4TRAN;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import 

abstraction.eq4TRAN.VendeurChoco.GQte;

/**
 * 
 * @author Etienne
 *
 */
@Deprecated
public interface IVendeurChoco {
@Deprecated
	public GQte getStock(); // GQte ne sert à rien, on remplace par une liste d'entiers (on fournit les quantités en
	// nombre d'unités) ArrayList<Integer> 
@Deprecated
	public GPrix getPrix(); // Gprix amélioré en GPrix2
@Deprecated	
	public GQte getLivraison(GQte[] commandes);
	
}
