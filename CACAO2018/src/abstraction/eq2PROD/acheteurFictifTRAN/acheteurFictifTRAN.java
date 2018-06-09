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
		int tonnageQB = 0; double prixQB =0;
		int tonnageQM_1 = 0; double prixQM_1 = 0;
		int tonnageQM_2 = 0; double prixQM_2 = 0;
		int tonnageQH = 0; double prixQH = 0;
		
		for(int i=0; i<this.contratPrecedent.length; i++) {
			if(this.contratPrecedent[i].getQualite()==0) {
				tonnageQB = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
				prixQB = this.contratPrecedent[i].getDemande_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==2) {
				tonnageQH = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
				prixQH = this.contratPrecedent[i].getDemande_Prix();
			}
			if(this.contratPrecedent[i].getQualite()==1) {
				if(this.contratPrecedent[i].getProducteur()==null /*Eq2PROD*/) {
					tonnageQM_1 = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
					prixQM_1 = this.contratPrecedent[i].getDemande_Prix();
				} else {
					tonnageQM_2 = (int)(this.contratPrecedent[i].getDemande_Quantite()*0.40);
					prixQM_2 = this.contratPrecedent[i].getDemande_Prix();
				}
			}
		}
						
		/* basse qualite uniquement a l'equipe 2 */
		ContratFeve cB = new ContratFeve(this,null/*Eq2PROD*/, 0, 
				0, tonnageQB, 0, 
				0, prixQB, 0, 
				false);
		/* moyenne qualite a l'equipe 2 ET l'equipe 3 */
		ContratFeve cM1 = new ContratFeve(this, null /*Eq2PROS*/, 1, 
				0, tonnageQM_1, 0, 
				0, prixQM_1, 0, 
				false);
		ContratFeve cM2 = new ContratFeve(this, null /*Eq3PROD*/, 1, 
				0, tonnageQM_2, 0, 
				0, prixQM_2, 0, 
				false);
		/* haute qualite uniquement a l'equipe 3 */
		ContratFeve cH = new ContratFeve(this, null /*Eq3PROD*/, 2, 
				0, tonnageQH, 0, 
				0, prixQH, 0, 
				false);
		
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
		for(int i=0; i < offreFinale.length; i++) {
			offreFinale[i].setReponse(true); /* l'acheteur fictif signe tous les contrats */
		}
		return offreFinale;
	}
	
/* NEXT DE L'ACTEUR FICTIF */	
	public void next() {
		/* L'acheteur fictif n'a pas de next() car il n'a pas de stock, ni de solde */
	}
}
