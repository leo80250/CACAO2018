package abstraction.eq2PROD;

import abstraction.fourni.*;
import abstraction.eq3PROD.echangesProdTransfo.*;

import java.util.ArrayList;

import abstraction.eq2PROD.echangeProd.*;

public class Eq2PROD implements Acteur, IVendeurFeve, IVendeurFevesProd {
	private int stockQM;
	private int stockQB;
	private double solde;
	private boolean maladie;
	private double coeffStock;
	private ContratFeve[] demandeTran;
	private final static int MOY_QB = 46000; /* pour un step = deux semaines */
	private final static int MOY_QM = 70000; /* pour un step = deux semaines */
	private final static int coutFixe = 70800000; // entretien des plantations
	private final static double prix_minQM = 1000;
	private final static double prix_minQB = 850;
	
	//constructeur
	public Eq2PROD() {
		this(Monde.LE_MONDE,"Eq2Prod");
		this.stockQM=10000000;
		this.stockQB=1000000;
		this.solde = 15000.0;
		this.coeffStock = 1;
		this.demandeTran = new ContratFeve[0];
	}
	
	//accesseur
	public static int getMoyQb() {
		return MOY_QB;
	}
	public static int getMoyQm() {
		return MOY_QM;
	}	
	public int getStockQM() {
		return stockQM;
	}
	public int getStockQB() {
		return stockQB;
	}
	public double getSolde() {
		return solde;
	}
	public String getNom() {
		return "Eq2PROD";
	}
	public double getCoeffSolde() {
		return this.coeffStock;
	}
	public ContratFeve[] getDemandeTran() {
		return this.demandeTran;
	}
	
	/* Alexandre BIGOT */
	public double getPrix() {
		return /*getPrixMarche()* */this.coeffStock ;
	}
	
	public double getCoeffMeteo() {
		return this.meteo();
	}
	
	public double getCoeffMaladie() {
		return this.maladie();
	}
	
	/* Alexandre BIGOT+Guillaume SALLE */
	private void calculCoeffPrixVentes() {
		double coeffMeteo = meteo();
		double coeffMaladie = maladie();
		this.coeffStock = -0.2*(coeffMeteo-coeffMaladie)+1.2;
	}
	
	private double meteo() {
		/* modélisation par Guillaume SALLE+Agathe CHEVALIER+Alexandre BIGOT, code par Guillaume SALLE */
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
	//Romain BERNARD
	private double prixMarche() {
		ArrayList<Acteur> acteurs=Monde.LE_MONDE.getActeurs();
		for (Acteur a : acteurs) {
			if(a instanceof MarcheFeve) {
				return ((MarcheFeve) a).getPrixMarche();
			} 
		} return 0;
	}
	
	/* Modélisation par Alexandre BIGOT+Guillaume SALLE, code par Alexandre BIGOT
	 * Si plantations déjà malades alors la récolte est diminuée de 50% par rapport à la récolte
	 * déjà réduite ou augmentée par la météo et au step suivant la plantation n'est plus malade
	 * sinon il y a 0.5% que la plantation soit infectée et la récolte n'est pas diminuée par 
	 * le facteur maladie  */
	private double maladie() {
		if (this.maladie) {
			this.maladie=false;
			return 0.5;
		} else {
			double x=Math.random();
			if (x<0.005) {
				this.maladie=true;
			}
			return 0.0 ;
		}
	}
	
	/* Code par Guillaume SALLE + Agathe CHEVALIER */
	public void next() {
		calculCoeffPrixVentes();
		this.stockQM=this.stockQM+ (int) (this.coeffStock*MOY_QM);
		this.stockQB=this.stockQB+ (int) (this.coeffStock*MOY_QB);
		this.solde=this.solde-coutFixe;
		this.getJournal().ajouter("Quantité basse qualité = "+ this.getStockQB());
		this.getJournal().ajouter("Quantité moyenne qualité ="+ this.getStockQM());
		this.getJournal().ajouter("Coefficient de la météo ="+ this.getCoeffMeteo());
		this.getJournal().ajouter("Coefficient des maladies ="+ this.getCoeffMaladie());
	}

	/* Code par Guillaume SALLE+Romain BERNARD+Agathe CHEVALIER */
	public ContratFeve[] getOffrePublique() {
		ContratFeve c1 = new ContratFeve(null, this, 0, this.stockQB, 0, 0, 
				/*MarcheFeve.getPrixMarche()* */this.coeffStock*0.85, 0.0, 0.0, false);
		ContratFeve c2 =  new ContratFeve(null, this, 1, this.stockQM, 0, 0, 
				/*MarcheFeve.getPrixMarche()* */this.coeffStock, 0.0, 0.0, false);
		ContratFeve[] c = new ContratFeve[2];
		c[0]=c1; c[1] = c2;
		return c;
	}
	/* Code par Guillaume SALLE+Romain BERNARD+Agathe CHEVALIER */
	public void sendDemandePrivee(ContratFeve[] demandePrivee) {
		this.demandeTran = demandePrivee; 
	}
	
	
	/* Modélisation par Romain BERNARD+Guillaume SALLE, Code par Romain BERNARD*/
	public ContratFeve[] getOffreFinale() {
		ContratFeve[] c=new ContratFeve[demandeTran.length];
		for (int i=0;i<demandeTran.length;i++ ) {
			c[i]=demandeTran[i];
			if (demandeTran[i].getQualite()==0) {
				if (demandeTran[i].getDemande_Prix()>=prixMarche()*this.coeffStock*0.85) {
					
			} 	else if (demandeTran[i].getDemande_Prix()<prix_minQB) {
				c[i].setProposition_Prix(prix_minQB);
			}	else {
				c[i].setProposition_Prix(0.25*prix_minQB+0.75*demandeTran[i].getDemande_Prix());
			}
		}
			 else {
				if (demandeTran[i].getDemande_Prix()>=prixMarche()* this.coeffStock) {
				
				} else if (demandeTran[i].getDemande_Prix()<prix_minQM) {
					
					c[i].setProposition_Prix(prix_minQM);
				} else {
					
					c[i].setProposition_Prix(0.25*prix_minQM+0.75*demandeTran[i].getDemande_Prix());
				}
		}
	} return c;
	}

	/*Agathe CHEVALIER + Alexandre BIGOT + Romain BERNARD*/
    public void sendResultVentes(ContratFeve[] resultVentes) {
    double chiffreDAffaire=0;
   	 for (int i=0; i<resultVentes.length;i++) {
   		 if (resultVentes[i].getReponse()) {
   			 
   			 if (resultVentes[i].getQualite()==0) {
   				 this.solde= this.solde + resultVentes[i].getPrix()*resultVentes[i].getQuantite() ;
   				 this.stockQB=this.stockQB - resultVentes[i].getQuantite() ;
   				 chiffreDAffaire+=resultVentes[i].getPrix()*resultVentes[i].getQuantite();
   			 }
   			 if (resultVentes[i].getQualite()==1) {
   				 this.solde= this.solde + resultVentes[i].getPrix()*resultVentes[i].getQuantite() ;
   				 this.stockQM=this.stockQM - resultVentes[i].getQuantite() ;
   				 chiffreDAffaire+=resultVentes[i].getPrix()*resultVentes[i].getQuantite();
   			 }
   		 }
   	 } this.solde=this.solde-0.35*chiffreDAffaire; // paiement des salaires à 35% du CA
    }
	
	/* Alexandre BIGOT
	 * Le cas où la quantité demandée est inférieure au stock n'est au final pas codée
	 * car il est impossible que cela arrive
	 */
	public int acheter(int quantite) {
		if (quantite <= this.stockQB) {
			this.stockQB=this.stockQB - quantite ;
			this.solde = this.solde /*+ quantite*MarcheFeve.getPrixMarche()*this.coeffStock */ ;
			return quantite ;
		} else {
			return 0 ;
		}
	}
	
	/*Agathe CHEVALIER + Alexandre BIGOT*/
	private Journal journal;
	private Journal ventesOccasionnelles;
	private String nom;
	private Indicateur stockQMoy;
	private Indicateur stockQBas;
	
	public Journal getJournal() {
		return this.journal;
	}

	public Journal getJournalOccasionel() {
		return this.ventesOccasionnelles;
	}
	
	public Eq2PROD(Monde monde, String nom) {
		this.nom = nom;
		this.stockQBas = new Indicateur("Stock de"+this.nom+"de basse qualité",this,0.0);
		this.stockQMoy = new Indicateur("Stock de"+this.nom+"de moyenne qualité",this,0.0);
		
		this.journal= new Journal("Journal de"+this.nom);
		this.ventesOccasionnelles = new Journal("Journal de ventes occasionnelles de"+this.nom);
		Monde.LE_MONDE.ajouterJournal(this.journal);
		Monde.LE_MONDE.ajouterJournal(this.ventesOccasionnelles);
		Monde.LE_MONDE.ajouterIndicateur(this.stockQBas);
		Monde.LE_MONDE.ajouterIndicateur(this.stockQMoy);
	}
	
}
