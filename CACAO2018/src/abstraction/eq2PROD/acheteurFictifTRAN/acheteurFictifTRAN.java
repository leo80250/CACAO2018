package abstraction.eq2PROD.acheteurFictifTRAN;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class acheteurFictifTRAN implements Acteur, IAcheteurFeve {
	
	public String getNom() {
		return "acheteurFictifTRAN";
	}

	public void sendOffrePublique(ContratFeve[] offrePublique) {
	}

	public ContratFeve[] getDemandePrivee() {
		return null;
	}

	public void sendContratFictif() {		
	}

	public void sendOffreFinale(ContratFeve[] offreFinale) {
	}

	public ContratFeve[] getResultVentes() {
		return null;
	}
	
	public void next() {
		
	}
}
