/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

public class GrilleQuantite {
	private int[][] quantite;
  
	public GrilleQuantite(int[][] quantite) {
		this.quantite = quantite;
	}


	public void Modifier(int i, int j, int valeur) {
		this.quantite[i][j] = valeur;
	}

	public int getValeur(int i, int j) {
		return this.quantite[i][j];
	}

	public int[][] getQuantite() {
		return this.quantite;
	}
	
	public GrilleQuantite somme(GrilleQuantite Q) {
		for(int i=0;i<Q.getQuantite().length;i++) {
			for(int j=0;j<Q.getQuantite().length;j++) {
				Q.Modifier(i,j,Q.getValeur(i,j)+this.getValeur(i,j));
			}
		}
		return Q;
	}

}