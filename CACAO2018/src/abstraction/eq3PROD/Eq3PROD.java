package abstraction.eq3PROD;

import abstraction.eq2PROD.IProducteurCacao;
import abstraction.fourni.Acteur;

public class Eq3PROD implements Acteur, IProducteurCacao {
private int stock;
public Eq3PROD() {
	this.stock= 1000000000;
}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq3PROD";
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		int prod = (int)(Math.random()*2000);
		this.stock = this.stock+prod;
		System.out.println(" eq 3 prod de "+prod+" --> stock="+this.stock);
	}

	@Override
	public void sell(int q) {
		// TODO Auto-generated method stub
		this.stock=this.stock-q;
		System.out.println(" eq 3 vend "+q+" --> stock="+this.stock);
	}
	
}
