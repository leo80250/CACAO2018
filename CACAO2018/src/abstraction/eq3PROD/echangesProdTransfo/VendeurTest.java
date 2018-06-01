package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

public class VendeurTest implements Acteur, IVendeurFeve {
	
	private String nom;
	
	
	public VendeurTest(String nom) {
		this.nom = nom;
	}
	
	@Override
	public ContratFeve[] getOffrePublique() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDemandePrivee(ContratFeve[] demandePrivee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContratFeve[] getOffreFinale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendResultVentes(ContratFeve[] resultVentes) {
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
