package abstraction.eq2PROD;

import abstraction.fourni.*;
import abstraction.eq3PROD.echangesProdTransfo.*;

import java.util.ArrayList;

import abstraction.eq2PROD.echangeProd.*;

public class Eq2PROD implements Acteur, IVendeurFeve, IVendeurFevesProd {
// VARIABLES D'INSTANCE
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private double coeffStock;
	private ContratFeve[] demandeTran;
	private final static int MOY_QB = 46000; // pour un step = deux semaines
	private final static int MOY_QM = 70000; // pour un step = deux semaines
	private final static int coutFixe = 70800000; // entretien des plantations
	private final static double prix_minQM = 1000;
	private final static double prix_minQB = 850;
	private boolean quantiteEq3;
	
// CONSTRUCTEURS
	public Eq2PROD() {
		this(Monde.LE_MONDE,"Eq2Prod");
		this.nomEq = "Eq2PROD";
		this.stockQM=0;
		this.stockQB=0;
		this.solde = 0.0;
		this.maladie = false;
		this.coeffStock = 1;
		this.demandeTran = new ContratFeve[0];
		this.quantiteEq3 = false;
	}
	
// GETTEURS
	/* Guillaume Sallé (jusqu'à getNom())*/
	public int getStockQM() {
		return this.stockQM;
	}
	public int getStockQB() {
		return this.stockQB;
	}
	public double getSolde() {
		return this.solde;
	}
	public void addSolde(double s) {
		this.solde = this.solde + s;
	}
	public void retireSolde(double s) {
		this.solde = this.solde - s;
	}
	public void addStockQB(int s) {
		this.stockQB = this.stockQB + s;
	}
	public void retireStockQB(int s) {
		this.stockQB = this.stockQB - s;
	}
	public void addStockQM(int s) {
		this.stockQM = this.stockQM + s;
	}
	public void retireStockQM(int s) {
		this.stockQM = this.stockQM - s;
	}
	// Coeff pour l'augmentation du stock (en fct de meteo et maladie), calculé ligne 156
	public double getCoeffStock() {
		return this.coeffStock;
	}
	// Coeff pour le calcul des prixOffrePublique dans les contrats (plus on récolte, moins c'est cher).
	public double getCoeffSolde() {
		return 2.0-getCoeffStock();
	}
	public void setCoeffStock(double x) {
		this.coeffStock = x;
	}
	public boolean getQuantiteEq3() {
		return this.quantiteEq3;
	}
	public void setQuantiteEq3(boolean b) {
		this.quantiteEq3 = b;
	}
	/* implementé en V0 */
	public String getNom() {
		return "Eq2PROD";
	}
	/* Romain Bernard */
	public ContratFeve[] getDemandeTran() {
		return this.demandeTran;
	}
	public void setDemandeTran(ContratFeve[] demande) {
		this.demandeTran = demande;
	}
	/* Alexandre Bigot + Guillaume Sallé*/
	public double getPrix() {
		return prixMarche()*getCoeffSolde() ;
	}
	/* Alexandre Bigot + Guillaume Sallé */
	public double getCoeffMeteo() {
		return this.meteo();
	}
	/* Alexandre Bigot */
	public boolean getCoeffMaladie() {
		return this.maladie;
	}
	/* Guillaume Sallé */
	public void setCoeffMaladie(boolean b) {
		this.maladie = b;
	}
	
/* VARIABLES INDEPENDANTES DES AUTRES GROUPES
 * Ces variables nous permettent de modeliser une production de cacao
 * la plus réaliste possible par rapport à nos recherches documentaires
 * precedant la modelisation informatique
 * Nous prenons en compte la meteo et les maladies
 */
	private double meteo() {
		/* Modélisation par Guillaume Sallé + Agathe Chevalier + Alexandre Bigot
		 * Code par Guillaume Sallé */
		double mini = 0.5;
		double maxi = 1.3;
		double x = Math.random();
		if (x<0.005) {
			return mini;
		} else if (x>0.995) {
			return maxi;
		} else {
			return 0.303*x+0.848;
		}
	}
	
	/* Modélisation par Alexandre Bigot + Guillaume Sallé, 
	 * Code par Alexandre Bigot
	 * Si plantations déjà malades alors la récolte est diminuée de 50% par rapport à la récolte
	 * déjà réduite ou augmentée par la météo et au step suivant la plantation n'est plus malade
	 * sinon il y a 0.5% que la plantation soit infectée et la récolte n'est pas diminuée par 
	 * le facteur maladie  */
	private double maladie() {
		if (getCoeffMaladie()) {
			setCoeffMaladie(false);
			return 0.5;
		} else {
			double x=Math.random();
			if (x<0.005) {
				setCoeffMaladie(true);
			}
			return 0.0 ;
		}
	}
	
	/* Alexandre Bigot + Guillaume Sallé */
	private void calculCoeffPrixVentes() {
		double coeffMeteo = meteo();
		double coeffMaladie = maladie();
		setCoeffStock(-0.2*(coeffMeteo-coeffMaladie)+1.2);
	}
	
/* IMPLEMENTATION DES DIFFERENTES INTERFACES UTILES A NOTRE ACTEUR
 * Ici nous avons implemente IVendeurFeve et IVendeurFevesProd
 */
	
	/* Code par Guillaume Sallé + Romain Bernard + Agathe Chevalier */
	public ContratFeve[] getOffrePublique() {
		ContratFeve c1 = new ContratFeve(null, this, 0, getStockQB(), 0, 0, 
				prixMarche()*getCoeffSolde()*0.85, 0.0, 0.0, false);
		ContratFeve c2 =  new ContratFeve(null, this, 1, getStockQM(), 0, 0, 
				prixMarche()*getCoeffSolde(), 0.0, 0.0, false);
		ContratFeve[] c = new ContratFeve[2];
		c[0]=c1; c[1] = c2;
		return c;
	}
	
	/* Code par Guillaume Sallé + Romain Bernard + Agathe Chevalier */
	public void sendDemandePrivee(ContratFeve[] demandePrivee) {
		setDemandeTran(demandePrivee); 
	}
	
	/* Modélisation par Romain Bernard + Guillaume Sallé
	 * Code par Romain Bernard */
	public ContratFeve[] getOffreFinale() {
		ContratFeve[] c=new ContratFeve[demandeTran.length];
		for (int i=0;i<demandeTran.length;i++ ) {
			c[i]=demandeTran[i];
			if (demandeTran[i].getQualite()==0) {
				if (demandeTran[i].getDemande_Prix()>=demandeTran[i].getOffrePublique_Prix()) {
				c[i].setProposition_Prix(demandeTran[i].getDemande_Prix());
			} 	else if (demandeTran[i].getDemande_Prix()<prix_minQB) {
				c[i].setProposition_Prix(prix_minQB);
			}	else {
				c[i].setProposition_Prix(0.25*prix_minQB+0.75*demandeTran[i].getDemande_Prix());
			}
		}
			 else {
				if (demandeTran[i].getDemande_Prix()>=demandeTran[i].getOffrePublique_Prix()) {
					c[i].setProposition_Prix(demandeTran[i].getDemande_Prix());
				} else if (demandeTran[i].getDemande_Prix()<prix_minQM) {
					c[i].setProposition_Prix(prix_minQM);
				} else {
					c[i].setProposition_Prix(0.25*prix_minQM+0.75*demandeTran[i].getDemande_Prix());
				}
		}
	} return c;
	}

	/* Agathe Chevalier + Alexandre Bigot + Romain Bernard */
    public void sendResultVentes(ContratFeve[] resultVentes) {
    double chiffreDAffaire=0;
   	 for (int i=0; i<resultVentes.length;i++) {
   		 if (resultVentes[i].getReponse()) {
   			 
   			 if (resultVentes[i].getQualite()==0) {
   				 addSolde(resultVentes[i].getProposition_Prix()*resultVentes[i].getProposition_Quantite()) ;
   				 retireStockQB(resultVentes[i].getProposition_Quantite()) ;
   				 chiffreDAffaire+=resultVentes[i].getProposition_Prix()*resultVentes[i].getProposition_Quantite();
   			 }
   			 if (resultVentes[i].getQualite()==1) {
   				 addSolde(resultVentes[i].getProposition_Prix()*resultVentes[i].getProposition_Quantite()) ;
   				 retireStockQM(resultVentes[i].getProposition_Quantite()) ;
   				 chiffreDAffaire+=resultVentes[i].getProposition_Prix()*resultVentes[i].getProposition_Quantite();
   			 } 
   		 } 
   	 } retireSolde(0.35*chiffreDAffaire); // Salaires = 35% du CA
    }
	
	/* Alexandre Bigot
	 * Le cas où la quantité demandée est inférieure au stock n'est au final pas codée
	 * car il est impossible que cela arrive
	 */
	public int acheter(int quantite) {
		if (quantite <= getStockQM()) {
			retireStockQM(quantite);
			addSolde(quantite*getPrix()) ;
			setQuantiteEq3(true);
			return quantite ;
		} else {
			setQuantiteEq3(false);
			return 0 ;
		}
	}
	
	/* Romain Bernard */
	private double prixMarche() {
		ArrayList<Acteur> acteurs=Monde.LE_MONDE.getActeurs();
		for (Acteur a : acteurs) {
			if(a instanceof MarcheFeve) {
				return ((MarcheFeve) a).getPrixMarche();
			} 
		} return 0;
	}  
		
/* INDICATEURS ET JOURNAUX
 * Les indicateurs que nous affichons sont le stock de basse qualite et le stock de moyenne qualite
 * Nous avons 2 journaux: un pour l'historique des stocks, la meteo et les maladies
 * et un autre pour l'historique des echanges avec les autres producteurs
 */
	/* Agathe Chevalier + Alexandre Bigot */
	private Journal journal;
	private Journal ventesOccasionnelles;
	private String nomEq;
	private Indicateur stockQMoy;
	private Indicateur stockQBas;
	
	/* Agathe Chevalier */
	public Journal getJournal() {
		return this.journal;
	}
	public String getNomEq() {
		return this.nomEq;
	}
	public void setNomEq(String s) {
		this.nomEq = s;
	}
	/* Alexandre Bigot */
	public Journal getJournalOccasionel() {
		return this.ventesOccasionnelles;
	}
	public void setJournalOccasionel(Journal j) {
		this.ventesOccasionnelles = j;
	}
	/* Guillaume Sallé */
	public void setJournal(Journal j) {
		this.journal = j;
	}
	public Indicateur getStockQBas() {
		return this.stockQBas;
	}
	public void setStockQBas(Indicateur i) {
		this.stockQBas = i;
	}
	public Indicateur getStockQMoy() {
		return this.stockQMoy;
	}
	public void setStockQMoy(Indicateur i) {
		this.stockQMoy = i;
	}
	/* Agathe Chevalier + Alexandre Bigot */
	public Eq2PROD(Monde monde, String nom) {
		setNomEq(nom);
		setStockQBas(new Indicateur("Stock de "+getNomEq()+" de basse qualité",this,getStockQB()));
		setStockQMoy(new Indicateur("Stock de "+getNomEq()+" de moyenne qualité",this,getStockQM()));
		
		setJournal(new Journal("Journal de"+getNomEq()));
		setJournalOccasionel(new Journal("Journal de ventes occasionnelles de"+getNomEq()));
		Monde.LE_MONDE.ajouterJournal(getJournal());
		Monde.LE_MONDE.ajouterJournal(getJournalOccasionel());
		Monde.LE_MONDE.ajouterIndicateur(getStockQBas());
		Monde.LE_MONDE.ajouterIndicateur(getStockQMoy());
	}
	
// NEXT DE NOTRE ACTEUR
	/* Code par Guillaume SALLE + Agathe CHEVALIER */
	public void next() {
		calculCoeffPrixVentes();
		addStockQM( (int) (getCoeffStock()*MOY_QM));
		addStockQB( (int) (getCoeffStock()*MOY_QB));
		retireSolde(coutFixe);
		this.getJournal().ajouter("Quantité basse qualité = "+ getStockQB());
		this.getJournal().ajouter("Quantité moyenne qualité ="+ getStockQM());
		this.getJournal().ajouter("Coefficient de la météo ="+ getCoeffMeteo());
		if(!(getCoeffMaladie())) {
			this.getJournal().ajouter("Aucune maladie n'a frappé les plantations");
		} else {
			this.getJournal().ajouter("Une maladie a frappé les plantations");
		}
		this.getJournal().ajouter("------------------------------------------------------------------------------");
		if(!(getQuantiteEq3())) {
			this.getJournalOccasionel().ajouter("Une transaction a été réalisée avec l'équipe 3");
		} else {
			this.getJournalOccasionel().ajouter("Aucune transaction n'a été réalisée avec l'équipe3");
		}
		this.getJournalOccasionel().ajouter("------------------------------------------------------------------------------");
	}
}
