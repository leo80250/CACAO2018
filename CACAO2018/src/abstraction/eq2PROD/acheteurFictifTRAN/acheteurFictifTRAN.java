package abstraction.eq2PROD.acheteurFictifTRAN;

import abstraction.fourni.Acteur;
import abstraction.eq3PROD.echangesProdTransfo.*;

public class acheteurFictifTRAN implements Acteur, IAcheteurFeve {
	private ContratFeve[] offreProd;
	private ContratFeve[] offreFinale;
	private ContratFeve[] contratPrecedent;
	
	public String getNom() {
		return "acheteurFictifTRAN";
	}
	/* Agathe CHEVALIER */
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		this.offreProd = offrePublique;
	}
	
	/* getContratReference() va permettre à l'acteur fictif de récupérer les contrats signés précédents
	 * Ses commandes seront celles données par le contrat précédent à un pourcentage près */
	public void getContratReference() {
		/*this.contratPrecedent = MarcheFeve.getContratPrecedent;*/
	}
	
	/* Agathe CHEVALIER */
	public ContratFeve[] getDemandePrivee() {
		/*this.contratPrecedent = MarcheFeve.getContrat();*/
		int tonnageQB = 0; double prixQB =0;
		int tonnageQM_1 = 0; double prixQM_1 = 0;
		int tonnageQM_2 = 0; double prixQM_2 = 0;
		int tonnageQH = 0; double prixQH = 0;
				
		/* basse qualité uniquement à Eq2 */
		ContratFeve cB = new ContratFeve(this, null /*eq2*/, 0,
				0, tonnageQB, 0, 
				0.0, prixQB, 0.0, true);
		/* moyenne qualité à Eq2 ET Eq3 */
		ContratFeve cM1 = new ContratFeve(this, null /*eq2*/, 0,
				0, tonnageQM_1, 0, 
				0.0, prixQM_1, 0.0, true);
		ContratFeve cM2 = new ContratFeve(this, null /*eq3*/, 0,
				0, tonnageQM_2, 0, 
				0.0, prixQM_2, 0.0, true);
		/* haute qualité uniquement à Eq3 */
		ContratFeve cH = new ContratFeve(this, null /*eq3*/, 0,
				0, tonnageQH, 0, 
				0.0, prixQH, 0.0, true);
		
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

	/* Agathe CHEVALIER */
	public ContratFeve[] getResultVentes() {
		for(int i=0; i <= offreFinale.length-1; i++) {
			offreFinale[0].setReponse(true); /* l'acheteur fictif signe tous les contrats */
		}
		return offreFinale;
	}
	
	public void next() {
	}
}
