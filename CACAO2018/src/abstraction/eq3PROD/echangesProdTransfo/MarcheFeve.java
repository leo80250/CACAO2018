package abstraction.eq3PROD.echangesProdTransfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class MarcheFeve implements IMarcheFeve, Acteur {
	
	private ArrayList<ContratFeveV3> contratPrecedent;
	private ArrayList<ContratFeveV3> contratActuel;
	private String nom;
	private List<IAcheteurFeveV4> listAcheteurs;
	private List<IVendeurFeveV4> listVendeurs;
	

	
	public MarcheFeve(String nom, List<IAcheteurFeveV4> ach, List<IVendeurFeveV4> ven) {
		this.contratPrecedent = new ArrayList<ContratFeveV3>();
		this.contratActuel = new ArrayList<ContratFeveV3>();
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
	public ArrayList<ContratFeveV3> getContratPrecedent() {
		return this.contratPrecedent;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void next() {
		
		// Reception des offres publiques (Producteurs -> Marche)
		 for (IVendeurFeveV4 vendeur : listVendeurs) {
			 for (ContratFeveV3 contrat : vendeur.getOffrePubliqueV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des offres publiques (Marche -> Transformateurs)
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
				acheteur.sendOffrePubliqueV3(contratActuel);
				acheteur.sendContratFictifV3(contratPrecedent);
			 }
			
		 
		 // Reception des demandes privees (Transformateurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
			 for (ContratFeveV3 contrat : acheteur.getDemandePriveeV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des demandes privees (Marche -> Producteurs)
		 for (IVendeurFeveV4 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV3> contratsPourVendeur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) {
					contratsPourVendeur.add(contrat);
				}
			 }
			vendeur.sendDemandePriveeV3(contratsPourVendeur); 
		 }
		 
		 // Reception des propositions (Vendeurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IVendeurFeveV4 vendeur : listVendeurs) {
			 for (ContratFeveV3 contrat : vendeur.getOffreFinaleV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des propositions (Marche -> Transformateur)
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) { 
			 ArrayList<ContratFeveV3> contratsPourAcheteur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getTransformateur() == acheteur) contratsPourAcheteur.add(contrat);
			 }
			acheteur.sendOffreFinaleV3(contratsPourAcheteur);
		 }
		 
		 // Reception des reponses (Transformateur -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
			 for (ContratFeveV3 contrat : acheteur.getResultVentesV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des reponses (Marche -> Producteurs)
		 for (IVendeurFeveV4 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV3> contratsPourVendeur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) contratsPourVendeur.add(contrat);
			 }
			vendeur.sendResultVentesV3(contratsPourVendeur);
		 }
	}
}
