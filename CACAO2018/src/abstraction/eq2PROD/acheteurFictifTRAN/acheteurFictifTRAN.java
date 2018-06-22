package abstraction.eq2PROD.acheteurFictifTRAN;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

import java.util.ArrayList;

import abstraction.eq3PROD.echangesProdTransfo.*;

public class acheteurFictifTRAN implements Acteur, IAcheteurFeve {
/* VARIABLES D'INSTANCE */
	private ContratFeve[] offreFinale;
	private ContratFeve[] contratPrecedent;
	
	
/* GETTEURS */
	/* Romain Bernard */
	public String getNom() {
		return "acheteurFictifTRAN";
	}
	
	/* Guillaume Sallé */
	public ArrayList<IVendeurFeve> getVendeurs() {
		ArrayList<Acteur> acteurs = Monde.LE_MONDE.getActeurs();
		ArrayList<IVendeurFeve> vendeurs = new ArrayList<>();
		for (Acteur v : acteurs) {
			if (v instanceof IVendeurFeve) {
				vendeurs.add((IVendeurFeve)v);
			}
		}
		return vendeurs;
	}

/* IMPLEMENTATION DES INTERFACES 
 * Ici nous implementons IAcheteurFeve 
 * */
	/* Agathe Chevalier */
	public void sendOffrePublique(ContratFeve[] offrePublique) {
		/*L'acheteur fictif n'a pas besoin de recuperer les offres publiques des producteurs aux transformateurs
		 * car aucun ne lui adresse de demande:
		 * ses commandes sont directement calculés selon un pourcentage des demandes du moins precedent */
	}
	
	/* Agathe CHEVALIER */
	public ContratFeve[] getDemandePrivee() {
		/*this.contratPrecedent = MarcheFeve.getContratPrecedent();*/
		//int tonnageQB = 0; double prixQB =0;
		//int tonnageQM_1 = 0; double prixQM_1 = 0;
		//int tonnageQM_2 = 0; double prixQM_2 = 0;
		//int tonnageQH = 0; double prixQH = 0;
		
		/* for(int i=0; i<this.contratPrecedent.length; i++) {
			if(this.contratPrecedent[i].getQualite()==0) {
				tonnageQB = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
				prixQB = this.contratPrecedent[i].getDemande_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==2) {
				tonnageQH = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
				prixQH = this.contratPrecedent[i].getDemande_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==1) {
				if(this.contratPrecedent[i].getProducteur()==null Eq2PROD) {
					tonnageQM_1 = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
					prixQM_1 = this.contratPrecedent[i].getDemande_Prix();
				} else {
					tonnageQM_2 = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
					prixQM_2 = this.contratPrecedent[i].getDemande_Prix();
				}
			}
		} */
		
		// Test pour notre Eq2PROD
		ContratFeve[] c = new ContratFeve[2];
		ContratFeve c1 = new ContratFeve(this, getVendeurs().get(0), 0,
				0, 1, 0, 
				0, 70800000, 0, false);
		ContratFeve c2 = new ContratFeve(this, getVendeurs().get(1), 0,
				0, 1, 0, 
				0, 70800000, 0, false);
		c[0] = c1; c[1] = c2;

		
		return c;		
	}
	
	/* Guillaume Sallé*/
	public void sendContratFictif(ContratFeve[] listContrats) {
		this.contratPrecedent = listContrats; 
	}

	/* Agathe Chevalier */
	public void sendOffreFinale(ContratFeve[] offreFinale) {
		this.offreFinale=offreFinale;
	}

	/* Agathe Chevalier */
	public ContratFeve[] getResultVentes() {
		for(ContratFeve c : this.offreFinale) {
			c.setReponse(true); /* l'acheteur fictif signe tous les contrats */
		}
		return offreFinale;
	}
	
/* NEXT DE L'ACTEUR FICTIF */	
	public void next() {
		/* L'acheteur fictif n'a pas de next() car il n'a pas de stock, ni de solde */
	}
}
