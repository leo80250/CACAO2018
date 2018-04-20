package abstraction.eq4TRAN;

import abstraction.eq4TRAN.ITransformateur;

import abstraction.fourni.Acteur;

public class Eq4TRAN implements Acteur, ITransformateur{
	
	private int stock;
<<<<<<< HEAD
	
	public Eq4TRAN() {
		this.stock=1000;
=======

	public Eq4TRAN() {
		this.stock=1000 ;
>>>>>>> branch 'master' of https://github.com/Carlivoix/CACAO2018.git
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq4TRAN";
	}

	@Override
	public void next() {
		System.out.println("L'équipe 4 est présente");		
		
	}
	@Override
	public void sell(int q) {
<<<<<<< HEAD
		this.stock=this.stock-q;
=======
		if(q>stock) return;
		stock-=q;
>>>>>>> branch 'master' of https://github.com/Carlivoix/CACAO2018.git
	}

}
