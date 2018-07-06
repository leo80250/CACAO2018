/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

import abstraction.fourni.Acteur;

public interface InterfaceDistributeurClient extends Acteur {
	/**
	 *
	 * @param Q
	 *            : tableau d'entier (1x6) : {ChocolatBdG ; ChocolatMdG ;
	 *            ChocolatHdG  ConfiserieBdG ; ConfiserieMdG ; ConfiserieHdG}
	 * @return tableau d'entier (1x6) : {ChocolatBdG ; ChocolatMdG ;
	 *            ChocolatHdG ; ConfiserieBdG ; ConfiserieMdG ; ConfiserieHdG}
	 *            qui correspond à ce que le distributeur vend aux clients.
	 *            Le distributeur prendra donc soin d'actualiser ses stocks et son argent en fonction
	 */
	public GrilleQuantite commander(GrilleQuantite Q);
	
	/**
	 *
	 * @param 
	 *          
	 * @return tableau de doubles (1x6) : {PrixChocolatBdG ; PrixChocolatMdG ;
	 *            PrixChocolatHdG ; PrixConfiserieBdG ; PrixConfiserieMdG ; PrixConfiserieHdG}
	 *            le getPrix servira à comparer les prix des distributeurs et de modifier en conséquances
	 *            les parts de marché de façon à créer une "guerre des prix".
	 *            NB: mettre des prix = 0.0 si le Distributeur ne propose pas ces produits.
	 */
	public double[] getPrix();
	

}
