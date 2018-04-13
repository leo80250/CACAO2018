package abstraction.eq4TRAN;

import abstraction.eq4TRAN.ITransformateur;

import abstraction.fourni.Acteur;

public class Eq4TRAN implements Acteur, ITransformateur{
	
	private int stock=1000;
	private float prix=1.0f;
	private float banque=0.0f;

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
		if(q>stock) return;
		stock-=q;
		banque+=q*prix;
	}

}
