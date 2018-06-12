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
	
	public MarcheFeve(String nom, IAcheteurFeve[] ach, IVendeurFeve[] ven) {
		this.contratPrecedent = new ArrayList<ContratFeve>();
		this.contratActuel = new ArrayList<ContratFeve>();
		this.nom = nom;
		this.listAcheteurs = ach;
		this.listVendeurs = ven;
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
		
		// Reception des offres publiques (Producteurs -> Marche)
		 for (IVendeurFeve vendeur : listVendeurs) {
			 for (ContratFeve contrat : vendeur.getOffrePublique()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des offres publiques (Marche -> Transformateurs)
		 for (IAcheteurFeve acheteur : listAcheteurs) {
			 acheteur.sendOffrePublique((ContratFeve[]) contratActuel.toArray());
			 acheteur.sendContratFictif((ContratFeve[]) contratPrecedent.toArray());
		 }
		 
		 // Reception des demandes privees (Transformateurs -> Marche)
		 contratActuel = new ArrayList<ContratFeve>();
		 for (IAcheteurFeve acheteur : listAcheteurs) {
			 for (ContratFeve contrat : acheteur.getDemandePrivee()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des demandes privees (Marche -> Producteurs)
		 for (IVendeurFeve vendeur : listVendeurs) { 
			 ArrayList<ContratFeve> contratsPourVendeur = new ArrayList<ContratFeve>();
			 for (ContratFeve contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) {
					contratsPourVendeur.add(contrat);
				}
			 }
			 vendeur.sendDemandePrivee((ContratFeve[]) contratsPourVendeur.toArray()); 
		 }
		 
		 // Reception des propositions (Vendeurs -> Marche)
		 contratActuel = new ArrayList<ContratFeve>();
		 for (IVendeurFeve vendeur : listVendeurs) {
			 for (ContratFeve contrat : vendeur.getOffreFinale()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des propositions (Marche -> Transformateur)
				 for (IAcheteurFeve acheteur : listAcheteurs) { 
					 ArrayList<ContratFeve> contratsPourAcheteur = new ArrayList<ContratFeve>();
					 for (ContratFeve contrat : contratActuel) {
						if (contrat.getTransformateur() == acheteur) {
							contratsPourAcheteur.add(contrat);
						}
					 }
					 acheteur.sendOffreFinale((ContratFeve[]) contratsPourAcheteur.toArray()); 
				 }
		 
		 // Reception des reponses (Transformateur -> Marche)
		 contratActuel = new ArrayList<ContratFeve>();
		 for (IAcheteurFeve acheteur : listAcheteurs) {
			 for (ContratFeve contrat : acheteur.getResultVentes()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des reponses (Marche -> Producteurs)
		 for (IVendeurFeve vendeur : listVendeurs) { 
			 ArrayList<ContratFeve> contratsPourVendeur = new ArrayList<ContratFeve>();
			 for (ContratFeve contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) {
					contratsPourVendeur.add(contrat);
				}
			 }
			 vendeur.sendResultVentes((ContratFeve[]) contratsPourVendeur.toArray()); 
		 }
	}

}
