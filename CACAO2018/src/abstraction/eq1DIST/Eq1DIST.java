package abstraction.eq1DIST;

import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

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
		Acteur eq5 = (Monde.LE_MONDE.getActeur("Eq5TRAN"));
	//	ITransformateur eq5v =
				((ITransformateur)eq5).sell(400);;
	//	eq5v.sell(400);
				this.stock = this.stock + 400;
			System.out.println("Le stock de l'équipe 1 : "+this.stock);
			
	}

	@Override
	public void sell(int q) {
		this.stock=this.stock-q;
		// TODO Auto-generated method stub
		
	}		
	

}
