package abstraction.eq6DIST;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;

/**
 * 
 * @author Léopold Petitjean
 *
 */
public interface IAcheteurChoco {
	public ArrayList<GQte> getCommande(ArrayList<GPrix> gPrix, ArrayList<GQte> stock);
	//retourne la commande sous forme d'une liste de GQte avec comme indice 0 --> eq4, indice 1 --> eq5 et indice 2 --> eq7
	public void livraison(GQte d, double solde);
	//met à jour le stock sous forme d'un GQte
}
