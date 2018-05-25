package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

public class VendeurTest implements Acteur, IVendeurFeve {
	
	private String nom;
	
	@Override
	public void sendCoursMarche() {
		// TODO Auto-generated method stub
		
	}
	
	public VendeurTest(String nom) {
		this.nom = nom;
	}
	
	@Override
	public Contrat[] getOffrePublique() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDemandePrivee(Contrat[] demandePrivee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contrat[] getOffreFinale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendResultVentes(Contrat[] resultVentes) {
		// TODO Auto-generated method stub
		
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
