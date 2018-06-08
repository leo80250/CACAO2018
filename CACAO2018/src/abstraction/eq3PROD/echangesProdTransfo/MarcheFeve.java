package abstraction.eq3PROD.echangesProdTransfo;

import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class MarcheFeve implements IMarcheFeve, Acteur {
	
	private ContratFeve[] contratPrecedent;
	private ContratFeve[] contratActuel;
	private String nom;
	
	public MarcheFeve() {
		this.contratPrecedent = new ContratFeve[1]; 
		contratPrecedent[0] = new ContratFeve(null, null, 1);
		this.contratActuel = new ContratFeve[1];
		contratActuel[0] = new ContratFeve();
		this.nom = "Marche intermediaire";
	}
	
	public MarcheFeve(ContratFeve[] contratPrecedent, ContratFeve[] contratActuel) {
		this.contratPrecedent = contratPrecedent;
		this.contratActuel = contratActuel;
	}

	@Override
	public double getPrixMarche() {
		// Rappel : qualite moyenne de feves
		double ventes = 0;
		double quantite = 0;
		for(int i = 0; i < this.contratPrecedent.length; i++) {
			if (this.contratPrecedent[i].getQualite() == 1) {
				ventes += this.contratPrecedent[i].getProposition_Quantite()*this.contratPrecedent[i].getProposition_Prix();
				quantite += this.contratPrecedent[i].getProposition_Quantite();
			}
		}
		return ventes/quantite;
	}

	@Override
	public ContratFeve[] getContratPrecedent() {
		return this.contratPrecedent;
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
