package abstraction.eq2PROD;

import abstraction.fourni.Acteur;

public class Eq2PROD implements Acteur, IProducteurCacao {
	private int stock;
	public Eq2PROD() {
		this.stock=10000000;
	}
	
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq2PROD";
	}

	public void next() {
		int prod = (int) (Math.random()*1000);
		this.stock=this.stock+prod;
		System.out.println("production de "+prod+" : le stock de eq2 est : "+this.stock);
		// TODO Auto-generated method stub
		
	}

	public void sell(int q) {
		this.stock=this.stock-q;
		System.out.println("eq2 sell ( "+q+") --> "+this.stock);
	}

}
