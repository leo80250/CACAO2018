/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

/**
 *
 * @param int[] le tableau est de taille 6 correspondant de gauche à droite: Tablette BG;Tablette MG ; Tablette HG ; Confiserie BG ; Confiserie MG ; Confiserie HG
 *           
 * @return 
 */
public class GrilleQuantite {
	private int[] quantite;
  
	public GrilleQuantite(int[] quantite) {
		this.quantite = quantite;
	}


	public void Modifier(int i, int valeur) {
		this.quantite[i] = valeur;
	}

	public int getValeur(int i) {
		return this.quantite[i];
	}

	public int[] getQuantite() {
		return this.quantite;
	}
	
	public String toString() {
		return this.getValeur(1)+" de Tablette BG ; "+ this.getValeur(2)+" de Tablette MG ; "+this.getValeur(3)+" de Tablette HG "
				+this.getValeur(4)+" de Confiserie BG ; "+this.getValeur(5)+" de Confiserie MG ; "+this.getValeur(6)+" de Confiserie HG.";
	}
	

}