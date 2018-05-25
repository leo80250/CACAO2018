package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

public class AcheteurTest implements Acteur, IAcheteurFeve {
	
	private String nom;
	
	public AcheteurTest(String nom) {
		this.nom = nom;
	}
	
	@Override
	public void sendOffrePublique(Contrat[] offrePublique) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contrat[] getDemandePrivee() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendContratFictif() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOffreFinale(Contrat[] offreFinale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contrat[] getResultVentes() {
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
