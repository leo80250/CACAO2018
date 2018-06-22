/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

public interface InterfaceDistributeurClient {
	/**
	 *
	 * @param Q
	 *            : tableau d'entier (1x6) : {ChocolatBdG ; ChocolatMdG ;
	 *            ChocolatHdG  ConfiserieBdG ; ConfiserieMdG ; ConfiserieHdG}
	 * @return tableau d'entier (1x6) : {ChocolatBdG ; ChocolatMdG ;
	 *            ChocolatHdG  ConfiserieBdG ; ConfiserieMdG ; ConfiserieHdG}
	 *            qui correspond à ce que le distributeur vend aux clients.
	 *            Le distributeur prendra donc soin d'actualiser ses stocks et son argent en fonction
	 */
	public GrilleQuantite commander(GrilleQuantite Q);
	

}
