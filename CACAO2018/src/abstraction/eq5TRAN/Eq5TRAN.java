package abstraction.eq5TRAN;

import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;

public class Eq5TRAN implements Acteur, ITransformateur {

	private int stock=1000;
	private float prix=1.0f;
	private float banque=0.0f;
	
	@Override
	public String getNom() {
		return "Eq5TRAN";
	}

	@Override
	public void next() {
		System.out.println("L'équipe 5 est présente");		
	}

	@Override
	public void sell(int q) {
		if(q>stock) return;
		stock-=q;
		banque+=q*prix;
	}

}