package abstraction.eq6DIST;


import abstraction.eq1DIST.GrilleQuantite;
import abstraction.eq1DIST.IVenteConso;
import abstraction.eq1DIST.InterfaceDistributeurClient;
import abstraction.eq4TRAN.ITransformateur;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;


public class Eq6DIST implements Acteur, InterfaceDistributeurClient {

 //IAcheteurChoco {
	
	//private double quantité_demandée_transfo1_CMG;
	//private double quantité_demandée_transfo1_TBG;
	//private double quantité_demandée_transfo1_TMG;
	//private double quantité_demandée_transfo2_CMG;
	//private double quantité_demandée_transfo2_TBG;
	//private double quantité_demandée_transfo2_TMG;
	//private double quantité_demandée_transfo3_CMG;
	//private double quantité_demandée_transfo3_TBG;
	//private double quantité_demandée_transfo3_TMG;
	//private double[] commande;
	private double stock;
	public Eq6DIST() {
		this.stock= 7484;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub;
		return "Eq6DIST";
	}
  
	@Override
	public void next() {
		ITransformateur eq4 = (ITransformateur)(Monde.LE_MONDE.getActeur("Eq4TRAN"));
		eq4.sell(600);
		this.stock=this.stock +600;
		System.out.println("eq6 : achat de 600 -- stock = -->"+this.stock);
		// TODO Auto-generated method stub;
		
	}

	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		// TODO Auto-generated method stub
		return null;
	}

	


	public void sell(int q) {
		this.stock = this.stock - q;
		System.out.println("eq6 : vente de "+q+" --stock = -->"+this.stock);
	}
	
	public double[][] getCommande(double[][] gPrix, double[][] stock) {
		/**
		 * Léopold Petitjean
		 */
		final double quantité_demandée_transfo1_CMG=0.0;
		final double quantité_demandée_transfo1_TBG=0.0;
		final double quantité_demandée_transfo1_TMG=3000;
		final double quantité_demandée_transfo2_CMG=24650;
	 	final double quantité_demandée_transfo2_TBG=4000;
		final double quantité_demandée_transfo2_TMG=48000;
		final double quantité_demandée_transfo3_CMG=5800;
		final double quantité_demandée_transfo3_TBG=200;
		final double quantité_demandée_transfo3_TMG=6750;
		
		double [][] commande = {{quantité_demandée_transfo1_TBG,quantité_demandée_transfo1_TMG,0.0,0.0,quantité_demandée_transfo1_CMG,0.0},{quantité_demandée_transfo2_TBG,quantité_demandée_transfo2_TMG,0.0,0.0,quantité_demandée_transfo2_CMG,0.0},{quantité_demandée_transfo3_TBG,quantité_demandée_transfo3_TMG,0.0,0.0,quantité_demandée_transfo3_CMG,0.0}};
		return(commande);
	}
	public void livraison(double[][] d) {
		//this.stock=this.stock+d;
		
	}
	

}
