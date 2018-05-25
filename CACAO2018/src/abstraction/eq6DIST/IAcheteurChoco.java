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
	public GQte getCommande(ArrayList<GPrix> gPrix, ArrayList<GQte> stock);
	public void livraison(double[][] d);
}
