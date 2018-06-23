package abstraction.eq2PROD.acheteurFictifTRAN;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq3PROD.echangesProdTransfo.*;

public class acheteurFictifTRAN implements Acteur, IAcheteurFeveV2 {
/* VARIABLES D'INSTANCE */
	private List<ContratFeveV2> offreFinale;
	private List<ContratFeveV2> contratPrecedent;
	
	
/* GETTEURS */
	/* Romain Bernard */
	public String getNom() {
		return "acheteurFictifTRAN";
	}
	
	/* Guillaume Sallé */
	public List<IVendeurFeveV2> getVendeurs() {
		List<Acteur> acteurs = Monde.LE_MONDE.getActeurs();
		List<IVendeurFeveV2> vendeurs = new ArrayList<>();
		for (Acteur v : acteurs) {
			if (v instanceof IVendeurFeveV2) {
				vendeurs.add((IVendeurFeveV2)v);
			}
		}
		return vendeurs;
	}
	
	public List<ContratFeveV2> getContratPrecedent() {
		return this.contratPrecedent;
	}
	public void setContratPrecedent(List<ContratFeveV2> l) {
		this.contratPrecedent = l;
	}
	public List<ContratFeveV2> getOffreFinale() {
		return this.offreFinale;
	}
	public void setOffreFinale(List<ContratFeveV2> l) {
		this.offreFinale = l;
	}

/* IMPLEMENTATION DES INTERFACES 
 * Ici nous implementons IAcheteurFeve 
 * */
	/* Agathe Chevalier */
	public void sendOffrePublique(List<ContratFeveV2> offrePublique) {
		/*L'acheteur fictif n'a pas besoin de recuperer les offres publiques des producteurs aux transformateurs
		 * car aucun ne lui adresse de demande:
		 * ses commandes sont directement calculés selon un pourcentage des demandes du moins precedent */
	}
	
	/* Guillaume Sallé 
	 * a devient a*p , b devient b*p, etc. Pour pouvoir changer facilement p
	 * Appelé dans getDemandePrivee()
	 */
	public void ponderation(int a, int b, int c, int d, double p) {
		a=(int)(a*p); b=(int)(b*p); c=(int)(c*p); d=(int)(d*p);   
	}
	
	
	/* Agathe Chevalier, Guillaume Sallé */
	public List<ContratFeveV2> getDemandePrivee() {
		/*this.contratPrecedent = Monde.LE_MONDE.getActeur("Marche intermediaire").getContratPrecedent*/
		List<ContratFeveV2> c = new ArrayList<ContratFeveV2>();
		//* Pour l'acheteur ficitf :		
		int tonnageQB = 0; double prixQB =0;
		int tonnageQM_1 = 0; double prixQM_1 = 0;
		int tonnageQM_2 = 0; double prixQM_2 = 0;
		int tonnageQH = 0; double prixQH = 0;
		
		for(ContratFeveV2 contrat : getContratPrecedent()) {
			if(contrat.getQualite()==0) {
				tonnageQB += (int)(contrat.getDemande_Quantite()*0.40);
				prixQB += contrat.getDemande_Prix();
			}
			if(contrat.getQualite()==2) {
				tonnageQH += (int)(contrat.getDemande_Quantite()*0.40);
				prixQH += contrat.getDemande_Prix();
			}
			if(contrat.getQualite()==1) {
				if(contrat.getProducteur()==null // Eq2PROD
						) {
					tonnageQM_1 += (int)(contrat.getDemande_Quantite()*0.40);
					prixQM_1 += contrat.getDemande_Prix();
				} else {
					tonnageQM_2 += (int)(contrat.getDemande_Quantite()*0.40);
					prixQM_2 += contrat.getDemande_Prix();
				}
			}
		}
		// On prend 40 % des tonnages 
		ponderation(tonnageQB,tonnageQH,tonnageQM_1,tonnageQM_2,0.4);
		
		if (tonnageQB != 0) {
			c.add(new ContratFeveV2(this, null //Eq2PROD
					, 0, 0, tonnageQB, 0, 
					0, prixQB/tonnageQB, 0, false));
		}
		if (tonnageQH != 0) {
			c.add(new ContratFeveV2(this, null //Eq3PROD
					, 0, 0, tonnageQH, 0, 
					0, prixQH/tonnageQH, 0, false));
		}
		if (tonnageQM_1 != 0) {
			c.add(new ContratFeveV2(this, null //Eq2PROD
					, 0, 0, tonnageQM_1, 0, 
					0, prixQM_1/tonnageQM_1, 0, false));
		}
		if (tonnageQM_2 != 0) {
			c.add(new ContratFeveV2(this, null //Eq3PROD
					, 0, 0, tonnageQM_2, 0, 
					0, prixQM_2/tonnageQM_2, 0, false));
		}
		// */
		
		/* -> //*    :   Test pour notre Eq2PROD
		c.add(new ContratFeveV2(this, getVendeurs().get(0), 0,
				0, 1, 0, 
				0, 70800000, 0, false));
		c.add(new ContratFeveV2(this, getVendeurs().get(1), 0,
				0, 1, 0, 
				0, 70800000, 0, false)); 
		// */
		return c;
	}
	
	/* Guillaume Sallé*/
	public void sendContratFictif(List<ContratFeveV2> listContrats) {
		setContratPrecedent(listContrats);
		for (int i = 0; i<getContratPrecedent().size() ; i++) {
			if (getContratPrecedent().get(i).getTransformateur() == this) {
				getContratPrecedent().remove(i);
			}
		}
	}

	/* Agathe Chevalier */
	public void sendOffreFinale(List<ContratFeveV2> offreFinale) {
		setOffreFinale(offreFinale);
	}

	/* Agathe Chevalier */
	public List<ContratFeveV2> getResultVentes() {
		for(ContratFeveV2 c : getOffreFinale()) {
			c.setReponse(true); /* l'acheteur fictif signe tous les contrats */
		}
		return offreFinale;
	}
	
/* NEXT DE L'ACTEUR FICTIF */	
	public void next() {
		/* L'acheteur fictif n'a pas de next() car il n'a pas de stock, ni de solde */
	}
}
