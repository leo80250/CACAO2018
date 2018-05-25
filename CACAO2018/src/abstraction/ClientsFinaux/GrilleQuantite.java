/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.ClientsFinaux;

public class GrilleQuantite {
	private int[][] quantite;
  
	public GrilleQuantite(int[][] quantite) {
		if (quantite.length == 2 && quantite[0].length == 3) {
			this.quantite = quantite;
		} else {
			this.quantite = new int[2][3];
		}
	}

	public GrilleQuantite() {
		this(new int[2][3]);
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