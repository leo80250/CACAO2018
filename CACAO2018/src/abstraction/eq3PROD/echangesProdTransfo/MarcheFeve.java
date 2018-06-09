package abstraction.eq3PROD.echangesProdTransfo;

import java.util.ArrayList;

import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class MarcheFeve implements IMarcheFeve, Acteur {
	
	private ArrayList<ContratFeve> contratPrecedent;
	private ArrayList<ContratFeve> contratActuel;
	private String nom;
	private IAcheteurFeve[] listAcheteurs;
	private IVendeurFeve[] listVendeurs;
	
	public MarcheFeve() {
		this.contratPrecedent = new ArrayList<ContratFeve>();
		this.contratActuel = new ArrayList<ContratFeve>();
		this.nom = "Marche intermediaire";
		this.listAcheteurs = new IAcheteurFeve[0];
		this.listVendeurs = new IVendeurFeve[0];
	}
	
	public MarcheFeve(ArrayList<ContratFeve> contratPrecedent, ArrayList<ContratFeve> contratActuel, String nom, IAcheteurFeve[] ach, IVendeurFeve[] ven) {
		this.contratPrecedent = contratPrecedent;
		this.contratActuel = contratActuel;
		this.nom = nom;
		this.listAcheteurs = ach;
		this.listVendeurs = ven;
	}

	@Override
	public double getPrixMarche() {
		// Rappel : qualite moyenne de feves
		double ventes = 0;
		double quantite = 0;
		for(int i = 0; i < this.contratPrecedent.size(); i++) {
			if (this.contratPrecedent.get(i).getQualite() == 1) {
				ventes += this.contratPrecedent.get(i).getProposition_Quantite()*this.contratPrecedent.get(i).getProposition_Prix();
				quantite += this.contratPrecedent.get(i).getProposition_Quantite();
			}
		}
		return ventes/quantite;
	}

	@Override
	public ContratFeve[] getContratPrecedent() {
		return (ContratFeve[]) this.contratPrecedent.toArray();
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void next() {

		// Liste d'offres publiques
		 for (IVendeurFeve vendeur : listVendeurs) {
			 for (ContratFeve contrat : vendeur.getOffrePublique()) {
				contratActuel.add(contrat);
			 }
		 }
	}

}
