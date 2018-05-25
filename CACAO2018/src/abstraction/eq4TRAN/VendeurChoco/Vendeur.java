package abstraction.eq4TRAN.VendeurChoco;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Etienne
 *
 */
public class Vendeur {
	/*
	 * classe définissant les méthodes nécessaires à l'interface IVendeur Choco
	 */
	
	private int qBonbonBQ;
	private int qBonbonMQ;
	private int qBonbonHQ;
	private int qTabletteBQ;
	private int qTabletteMQ;
	private int qTabletteHQ;
	
	public Vendeur(int qBBQ, int qBMQ, int qBHQ, int qTBQ, int qTMQ, int qTHQ) {
		qBonbonBQ = (qBBQ>=0) ? qBBQ : 0;
		qBonbonMQ = (qBMQ>=0) ? qBMQ : 0;
		qBonbonHQ = (qBHQ>=0) ? qBHQ : 0;
		qTabletteBQ = (qTBQ>=0) ? qTBQ : 0;
		qTabletteMQ = (qTMQ>=0) ? qTMQ : 0;
		qTabletteHQ = (qTHQ>=0) ? qTHQ : 0;
	}

	public int getqBonbonBQ() {
		return qBonbonBQ;
	}

	public void setqBonbonBQ(int qBonbonBQ) {
		this.qBonbonBQ = qBonbonBQ;
	}

	public int getqBonbonMQ() {
		return qBonbonMQ;
	}

	public void setqBonbonMQ(int qBonbonMQ) {
		this.qBonbonMQ = qBonbonMQ;
	}

	public int getqBonbonHQ() {
		return qBonbonHQ;
	}

	public void setqBonbonHQ(int qBonbonHQ) {
		this.qBonbonHQ = qBonbonHQ;
	}

	public int getqTabletteBQ() {
		return qTabletteBQ;
	}

	public void setqTabletteBQ(int qTabletteBQ) {
		this.qTabletteBQ = qTabletteBQ;
	}

	public int getqTabletteMQ() {
		return qTabletteMQ;
	}

	public void setqTabletteMQ(int qTabletteMQ) {
		this.qTabletteMQ = qTabletteMQ;
	}

	public int getqTabletteHQ() {
		return qTabletteHQ;
	}

	public void setqTabletteHQ(int qTabletteHQ) {
		this.qTabletteHQ = qTabletteHQ;
	}

	public GQte getStock() {
		return new GQte(getqBonbonBQ(), getqBonbonMQ(), getqBonbonHQ(), getqTabletteBQ(), getqTabletteMQ(), getqTabletteHQ());
	}
	
	public GPrix getPrix() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nombre d'intervalles de prix que vous désirez :");
		int nbIntervalles = sc.nextInt();
		System.out.println("Rentrez succesivement les intervalles que vous désirez définir : (les intervales peuvent être des nombres réels)");
		ArrayList<Double> intervalles = new ArrayList<Double>(nbIntervalles); 
		for(int i=0; i<nbIntervalles; i++) {
			intervalles.set(i, sc.nextDouble());
		}
		ArrayList<Double[]> prix = new ArrayList<Double[]>(6); /* Stocke nos prix par produits et qualité */
		Double[] prixproduit = new Double[10];
		String[] produits = {"BonbonsBQ","BonbonsMQ","BonbonsHQ","TabletteBQ","TabletteMQ","TabletteHQ"};
		for(int j=0; j<produits.length; j++) {
			System.out.println("Indiquez votre prix pour " +produits[j]+ ": (en quantité)");
			System.out.println("(Indiquez successivement les prix pour chaque intervalle précédemment défini)");
			for(int k=0; k<nbIntervalles; k++) {
				prixproduit[k]=sc.nextDouble();
			}
			prix.set(j, prixproduit);
		}
		return new GPrix(intervalles, prix);
	}
	
	
	public ArrayList<GQte> getLivraison(ArrayList<GQte> commandes) {
		GQte commande1 = commandes.get(0);
		GQte commande2 = commandes.get(1);
		ArrayList<GQte> Livraison = new ArrayList<>(); /*insérer notre livraison effective */
		return Livraison;
	}
}
