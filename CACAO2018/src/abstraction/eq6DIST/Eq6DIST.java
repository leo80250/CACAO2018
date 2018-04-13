package abstraction.eq6DIST;

import abstraction.eq1DIST.IVenteConso;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class Eq6DIST implements Acteur, IVenteConso {

	private int stock;
	public Eq6DIST() {
		this.stock=75412;
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq6DIST";
	}

	@Override
	public void next() {
		ITransformateur eq4 = (ITransformateur)(Monde.LE_MONDE.getActeur("Eq4TRAN"));
		eq4.sell(600);
		this.stock=this.stock +600;
		System.out.println("eq6 : achat de 600 -- stock = -->"+this.stock);
		// TODO Auto-generated method stub;
		
	}
	@Override
	public void sell(int q) {
		this.stock = this.stock - q;
		System.out.println("eq6 : vente de "+q+" --stock = -->"+this.stock);
	}

}
