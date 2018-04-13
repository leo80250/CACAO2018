package abstraction.eq5TRAN;

import abstraction.fourni.Acteur;

public class Eq5TRAN implements Acteur {

	@Override
	public String getNom() {
		return "Eq5TRAN";
	}

	@Override
	public void next() {
		System.out.println("L'équipe 5 est présente");		
	}

}
