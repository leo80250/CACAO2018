package abstraction.eq1DIST;

import abstraction.fourni.Acteur;
//import abstraction.fourni.Monde;

public class Eq1DIST implements Acteur, IVenteConso {
private int stock;

public Eq1DIST() {
	this.stock=1234567;
}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq1DIST";
	}

	@Override
	public void next() {
		//Acteur eq4 = (Monde.LE_MONDE.getActeur("Eq4TRAN"));
		// IV eq4v = (IV)eq4;
		//eq4v.sell(400);
	}

	@Override
	public void sell(int q) {
		this.stock=this.stock-q;
		// TODO Auto-generated method stub
		
	}		
	

}
