package abstraction.eq5TRAN;

import abstraction.eq2PROD.IProducteurCacao;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class Eq5TRAN implements Acteur, ITransformateur {

	private int stock;
	private float prix=1.0f;
	private float banque=0.0f;

	public Eq5TRAN() {
		this.stock=1000;
	}
	@Override
	public String getNom() {
		return "Eq5TRAN";
	}

	@Override
	public void next() {
		IProducteurCacao p=(IProducteurCacao)(Monde.LE_MONDE.getActeur("Eq2PROD"));
		int achat = 600;
		p.sell(achat);
		this.stock += achat ;

	}

	@Override
	public void sell(int q) {
		if(q>stock) return;
		stock-=q;
		banque+=q*prix;
	}

}