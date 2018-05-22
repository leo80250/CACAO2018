/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.ClientsFinaux;

public class GrilleQuantite {
	private double[][] quantite;

	public GrilleQuantite(double[][] quantite) {
		if (quantite.length == 2 && quantite[0].length == 3) {
			this.quantite = quantite;
		} else {
			this.quantite = new double[2][3];
		}
	}

	public GrilleQuantite() {
		this(new double[2][3]);
	}

	public void Modifier(int i, int j, double valeur) {
		this.quantite[i][j] = valeur;
	}

	public double getValeur(int i, int j) {
		return this.quantite[i][j];
	}

	public double[][] getQuantite() {
		return this.quantite;
	}

}
