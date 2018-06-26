package abstraction.eq3PROD.echangesProdTransfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.fourni.Acteur;

/**
 * @author Grégoire
 */

public class MarcheFeve implements IMarcheFeve, Acteur {
	
	private ArrayList<ContratFeveV2> contratPrecedent;
	private ArrayList<ContratFeveV2> contratActuel;
	private String nom;
	private List<IAcheteurFeveV2> listAcheteurs;
	private List<IVendeurFeveV2> listVendeurs;
	
	public MarcheFeve() {
		this.contratPrecedent = new ArrayList<ContratFeveV2>();
		this.contratActuel = new ArrayList<ContratFeveV2>();
		this.nom = "Marche intermediaire";
		this.listAcheteurs = new ArrayList<IAcheteurFeveV2>(0);
		this.listVendeurs = new ArrayList<IVendeurFeveV2>(0);
	}
	
	public MarcheFeve(String nom, List<IAcheteurFeveV2> ach, List<IVendeurFeveV2> ven) {
		this.contratPrecedent = new ArrayList<ContratFeveV2>();
		this.contratActuel = new ArrayList<ContratFeveV2>();
		this.nom = nom;
		this.listAcheteurs = ach;
		this.listVendeurs = ven;
	}
	
	public MarcheFeve(ArrayList<ContratFeveV2> contratPrecedent, ArrayList<ContratFeveV2> contratActuel, String nom, List<IAcheteurFeveV2> ach, List<IVendeurFeveV2> ven) {
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
	public ArrayList<ContratFeveV2> getContratPrecedent() {
		return this.contratPrecedent;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void next() {
		
		// Reception des offres publiques (Producteurs -> Marche)
		 for (IVendeurFeveV2 vendeur : listVendeurs) {
			 for (ContratFeveV2 contrat : vendeur.getOffrePublique()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des offres publiques (Marche -> Transformateurs)
		 for (IAcheteurFeveV2 acheteur : listAcheteurs) {
				acheteur.sendOffrePublique(contratActuel);
				acheteur.sendContratFictif(contratPrecedent);
			 }
			
		 
		 // Reception des demandes privees (Transformateurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV2>();
		 for (IAcheteurFeveV2 acheteur : listAcheteurs) {
			 for (ContratFeveV2 contrat : acheteur.getDemandePrivee()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des demandes privees (Marche -> Producteurs)
		 for (IVendeurFeveV2 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV2> contratsPourVendeur = new ArrayList<ContratFeveV2>();
			 for (ContratFeveV2 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) {
					contratsPourVendeur.add(contrat);
				}
			 }
			vendeur.sendDemandePrivee(contratsPourVendeur); 
		 }
		 
		 // Reception des propositions (Vendeurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV2>();
		 for (IVendeurFeveV2 vendeur : listVendeurs) {
			 for (ContratFeveV2 contrat : vendeur.getOffreFinale()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		// Envoi des propositions (Marche -> Transformateur)
		 for (IAcheteurFeveV2 acheteur : listAcheteurs) { 
			 ArrayList<ContratFeveV2> contratsPourAcheteur = new ArrayList<ContratFeveV2>();
			 for (ContratFeveV2 contrat : contratActuel) {
				if (contrat.getTransformateur() == acheteur) contratsPourAcheteur.add(contrat);
			 }
			acheteur.sendOffreFinale(contratsPourAcheteur);
		 }
		 
		 // Reception des reponses (Transformateur -> Marche)
		 contratActuel = new ArrayList<ContratFeveV2>();
		 for (IAcheteurFeveV2 acheteur : listAcheteurs) {
			 for (ContratFeveV2 contrat : acheteur.getResultVentes()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 // Envoi des reponses (Marche -> Producteurs)
		 for (IVendeurFeveV2 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV2> contratsPourVendeur = new ArrayList<ContratFeveV2>();
			 for (ContratFeveV2 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) contratsPourVendeur.add(contrat);
			 }
			vendeur.sendResultVentes(contratsPourVendeur);
		 }
	}
}
