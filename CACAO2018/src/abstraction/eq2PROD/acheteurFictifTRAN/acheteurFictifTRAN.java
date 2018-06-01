package abstraction.eq2PROD.acheteurFictifTRAN;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class acheteurFictifTRAN implements Acteur, IAcheteurFeve {
	private ContratFeve[] offreProd;
	private ContratFeve[] offreFinale;
	
	public String getNom() {
		return "acheteurFictifTRAN";
	}
	/* Agathe CHEVALIER */
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		this.offreProd = offrePublique;
	}
	
	/* Agathe CHEVALIER */
	public ContratFeve[] getDemandePrivee() {
		/* basse qualité uniquement à Eq2 */
		ContratFeve cB = new ContratFeve(0,1000 /*quantité à déterminer*/,1000/*prix à déterminer*/,this,null/*mettre Eq2*/,false);
		/* moyenne qualité à Eq2 ET Eq3 */
		ContratFeve cM1 = new ContratFeve(1,1000/*quantité à déterminer*/,1000/*prix à déterminer*/,this,null/*mettre Eq2*/,false);
		ContratFeve cM2 = new ContratFeve(1,1000/*quantité à déterminer*/,1000/*prix à déterminer*/,this,null/*mettre Eq3*/,false);
		/* haute qualité uniquement à Eq3 */
		ContratFeve cH = new ContratFeve(2,1000/*quantité à déterminer*/,1000/*prix à déterminer*/,this,null/*mettre Eq3*/,false); 
		ContratFeve[] c = new ContratFeve[4];
		c[0]=cB ; c[1]=cM1 ; c[2]=cM2 ; c[3]=cH;
		return c;		
	}

	public void sendContratFictif() {
	}

	/* Agathe CHEVALIER */
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		this.offreFinale=offreFinale;
	}

	public ContratFeve[] getResultVentes() {
		return null;
	}
	
	public void next() {
		
	}
}
