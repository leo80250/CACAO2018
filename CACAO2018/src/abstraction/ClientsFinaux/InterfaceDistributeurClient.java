/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.ClientsFinaux;
 
public interface InterfaceDistributeurClient {
	/**
	 *
	 * @param Q
	 *            : tableau d'entier (2x3) ligne 1: ChocolatBdG ; ChocolatMdG ;
	 *            ChocolatHdG ligne 2: ConfiserieBdG ; ConfiserieMdG ; ConfiserieHdG
	 * @return tableau d'entier du même modèle qui correspond à ce que le
	 *         distributeur vend aux clients.
	 */
	public GrilleQuantite commander(GrilleQuantite Q);
}
