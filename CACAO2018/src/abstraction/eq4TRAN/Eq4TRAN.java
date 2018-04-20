package abstraction.eq4TRAN;

import abstraction.eq4TRAN.ITransformateur;

import abstraction.fourni.Acteur;

public class Eq4TRAN implements Acteur, ITransformateur{
	
	private int stock;


	public Eq4TRAN() {
		this.stock=1000 ;
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
		if(q<=this.stock) {
			this.stock -= q;
		}
		else {
			System.out.println("Le stock n'est pas suffisant pout satisfaire la demande");
		}
	}

}
