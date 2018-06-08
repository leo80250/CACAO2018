package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class AcheteurTest implements Acteur, IAcheteurFeve {
	
	private String nom;
	
	public AcheteurTest(String nom) {
		this.nom = nom;
	}
	
	@Override
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContratFeve[] getDemandePrivee() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendContratFictif() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContratFeve[] getResultVentes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

}
