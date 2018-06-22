package abstraction.eq1DIST;

public class Lot {
private int[] quantite;


public Lot(int[] quantite) {
	this.quantite = new int[6];
}

public int[] getQuantite() {
	return this.quantite;
}
/* quantite [TBG, TMG, THG, CBG, CMG, CHG]*/

public void setQuantite(int[] quantite) {
	this.quantite = quantite;
}

}
