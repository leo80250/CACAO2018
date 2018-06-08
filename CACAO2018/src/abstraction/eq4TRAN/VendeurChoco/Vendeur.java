package abstraction.eq4TRAN.VendeurChoco;

import java.util.ArrayList;
import java.util.Scanner;

import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.fourni.Journal;

/**
 * 
 * @author Etienne
 *
 */
public class Vendeur implements IVendeurChoco{
	/*
	 * classe définissant les méthodes nécessaires à l'interface IVendeur Choco
	 */
	
	private double qBonbonBQ;
	private double qBonbonMQ;
	private double qBonbonHQ;
	private double qTabletteBQ;
	private double qTabletteMQ;
	private double qTabletteHQ;
	public Journal ventes = new Journal("ventes");
	
	public Vendeur(double qBBQ, double qBMQ, double qBHQ, double qTBQ, double qTMQ, double qTHQ) {
		qBonbonBQ = (qBBQ>=0) ? qBBQ : 0;
		qBonbonMQ = (qBMQ>=0) ? qBMQ : 0;
		qBonbonHQ = (qBHQ>=0) ? qBHQ : 0;
		qTabletteBQ = (qTBQ>=0) ? qTBQ : 0;
		qTabletteMQ = (qTMQ>=0) ? qTMQ : 0;
		qTabletteHQ = (qTHQ>=0) ? qTHQ : 0;
	}
	
	public Vendeur() {
		qBonbonBQ=0;
		qBonbonMQ=0;
		qBonbonHQ=0;
		qTabletteBQ=0;
		qTabletteMQ=0;
		qTabletteHQ=0;
	}

	public double getqBonbonBQ() {
		return qBonbonBQ;
	}

	public void setqBonbonBQ(int qBonbonBQ) {
		this.qBonbonBQ = qBonbonBQ;
	}

	public double getqBonbonMQ() {
		return qBonbonMQ;
	}

	public void setqBonbonMQ(int qBonbonMQ) {
		this.qBonbonMQ = qBonbonMQ;
	}

	public double getqBonbonHQ() {
		return qBonbonHQ;
	}

	public void setqBonbonHQ(int qBonbonHQ) {
		this.qBonbonHQ = qBonbonHQ;
	}

	public double getqTabletteBQ() {
		return qTabletteBQ;
	}

	public void setqTabletteBQ(int qTabletteBQ) {
		this.qTabletteBQ = qTabletteBQ;
	}

	public double getqTabletteMQ() {
		return qTabletteMQ;
	}

	public void setqTabletteMQ(int qTabletteMQ) {
		this.qTabletteMQ = qTabletteMQ;
	}

	public double getqTabletteHQ() {
		return qTabletteHQ;
	}

	public void setqTabletteHQ(int qTabletteHQ) {
		this.qTabletteHQ = qTabletteHQ;
	}

	public GQte getStock() {
		return new GQte(getqBonbonBQ(), getqBonbonMQ(), getqBonbonHQ(), getqTabletteBQ(), getqTabletteMQ(), getqTabletteHQ());
	}
	
	public GPrix getPrix() {
		ArrayList<Double[]> intervalles = new ArrayList<>();
		Double[] interval = {0.0,10.0,50.0,100.0,250.0,500.0,750.0,1000.0};
		for(int i=0;i<6;i++) {
			intervalles.add(interval);
		}
		ArrayList<Double[] > prix = new ArrayList<>();
		//Discuter de la stratégie d'etagement des prix
		Double[] prix4 = {0.9, 0.875, 0.85, 0.8, 0.775, 0.75, 0.725, 0.7};
		Double[] prix5 = {1.5, 1.475, 1.45, 1.4, 1.375, 1.35, 1.325, 1.3};
		Double[] prix6 = {3.0, 2.975, 2.95, 2.9, 2.875, 2.85, 2.825, 2.8};
		Double[] prix1 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		Double[] prix2 = {7.0, 6.975, 6.95, 6.9, 6.875, 6.85, 6.825, 6.8};
		Double[] prix3 = {11.0, 10.975, 10.95, 10.9, 10.875, 10.85, 10.825, 10.8};
		prix.add(prix1);
		prix.add(prix2);
		prix.add(prix3);
		prix.add(prix4);
		prix.add(prix5);
		prix.add(prix6);
		return new GPrix(intervalles, prix);
	}
	
	
	public ArrayList<GQte> getLivraison(ArrayList<GQte> commandes) {
		GQte commande1 = commandes.get(0);
		// On considère que commande1 correspond à la commande de l'équipe eq1DIST
		GQte commande2 = commandes.get(1);
		ArrayList<GQte> Livraison = new ArrayList<>(); /*insérer notre livraison effective */
		commande2.setqBonbonBQ(this.getqBonbonBQ()-commande1.getqBonbonBQ());
		commande2.setqBonbonMQ(this.getqBonbonMQ()-commande1.getqBonbonMQ());
		commande2.setqBonbonHQ(this.getqBonbonHQ()-commande1.getqBonbonHQ());
		commande2.setqTabletteBQ(this.getqTabletteBQ()-commande2.getqTabletteBQ());
		commande2.setqTabletteMQ(this.getqTabletteMQ()-commande2.getqTabletteMQ());
		commande2.setqTabletteHQ(this.getqTabletteHQ()-commande2.getqTabletteHQ());
		Livraison.add(commande1);
		Livraison.add(commande2);
		ventes.ajouter("Livraison : " + Livraison);
		return Livraison;
	}

}
