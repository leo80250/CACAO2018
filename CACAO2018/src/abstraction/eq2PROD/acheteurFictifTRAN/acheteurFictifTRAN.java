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
	public ArrayList<Acteur> getVendeurs() {
		ArrayList<Acteur> vendeurs = Monde.LE_MONDE.getActeurs();
		for (Acteur v : vendeurs) {
			if (!(v instanceof IVendeurFeve)) {
				vendeurs.remove(v);
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
		int tonnageQB = 0; int tonnageOffreQB = 0; double prixOffreQB = 0; double prixQB =0;
		int tonnageQM_1 = 0; int tonnageOffreQM_1 = 0; double prixOffreQM_1 = 0; double prixQM_1 = 0;
		int tonnageQM_2 = 0; int tonnageOffreQM_2 = 0; double prixOffreQM_2 = 0; double prixQM_2 = 0;
		int tonnageQH = 0; int tonnageOffreQH = 0; double prixOffreQH = 0; double prixQH = 0;
		
		for(int i=0; i<this.contratPrecedent.length; i++) {
			if(this.contratPrecedent[i].getQualite()==0) {
				tonnageOffreQB = this.contratPrecedent[i].getOffrePublique_Quantite();
				prixOffreQB = this.contratPrecedent[i].getOffrePublique_Prix();
				tonnageQB = this.contratPrecedent[i].getProposition_Quantite();
				prixQB = this.contratPrecedent[i].getProposition_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==2) {
				tonnageOffreQH = this.contratPrecedent[i].getOffrePublique_Quantite();
				prixOffreQH = this.contratPrecedent[i].getOffrePublique_Prix();
				tonnageQH = this.contratPrecedent[i].getProposition_Quantite();
				prixQH = this.contratPrecedent[i].getProposition_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==1) {
				if(this.contratPrecedent[i].getProducteur()==null /*Eq2PROD*/) {
					tonnageOffreQM_1 = this.contratPrecedent[i].getOffrePublique_Quantite();
					prixOffreQM_1 = this.contratPrecedent[i].getOffrePublique_Prix();
					tonnageQM_1 = this.contratPrecedent[i].getProposition_Quantite();
					prixQM_1 = this.contratPrecedent[i].getProposition_Prix();
				} else {
					tonnageOffreQM_2 = this.contratPrecedent[i].getOffrePublique_Quantite();
					prixOffreQM_2 = this.contratPrecedent[i].getOffrePublique_Prix();
					tonnageQM_2 = this.contratPrecedent[i].getProposition_Quantite();
					prixQM_2 = this.contratPrecedent[i].getProposition_Prix();
				}
			}
		}
						
		/* basse qualite uniquement a l'equipe 2 */
		ContratFeve cB = new ContratFeve(this,null/*Eq2PROD*/, 0, 
				tonnageOffreQB, tonnageQB, 0, 
				prixOffreQB, prixQB, 0, 
				true);
		/* moyenne qualite a l'equipe 2 ET l'equipe 3 */
		ContratFeve cM1 = new ContratFeve(this, null /*Eq2PROS*/, 1, 
				tonnageOffreQM_1, tonnageQM_1, 0, 
				prixOffreQM_1, prixQM_1, 0, 
				true);
		ContratFeve cM2 = new ContratFeve(this, null /*Eq3PROD*/, 1, 
				tonnageOffreQM_2, tonnageQM_2, 0, 
				prixOffreQM_2, prixQM_2, 0, 
				true);
		/* haute qualite uniquement a l'equipe 3 */
		ContratFeve cH = new ContratFeve(this, null /*Eq3PROD*/, 2, 
				tonnageOffreQH, tonnageQH, 0, 
				prixOffreQH, prixQH, 0, 
				true);
		
		ContratFeve[] c = new ContratFeve[4];
		c[0]=cB ; c[1]=cM1 ; c[2]=cM2 ; c[3]=cH;
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
		for(int i=0; i <= offreFinale.length-1; i++) {
			offreFinale[0].setReponse(true); /* l'acheteur fictif signe tous les contrats */
		}
		return offreFinale;
	}
	
/* NEXT DE L'ACTEUR FICTIF */	
	public void next() {
		/* L'acheteur fictif n'a pas de next() car il n'a pas de stock, ni de solde */
	}
}
