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
		return this.getValeur(0)+" Tablette BG ; "+ this.getValeur(1)+" Tablette MG ; "+this.getValeur(2)+" Tablette HG ; "
				+this.getValeur(3)+" Confiserie BG ; "+this.getValeur(4)+" Confiserie MG ; "+this.getValeur(5)+" Confiserie HG.";
	}
	

}