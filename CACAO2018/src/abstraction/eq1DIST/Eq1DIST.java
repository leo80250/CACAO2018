package abstraction.eq1DIST;

import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;



  
public class Eq1DIST implements Acteur, InterfaceDistributeurClient {
private Lot[] stock;


public Eq1DIST()  {
	this.stock = stock;
	double[][] PartsdeMarche= {};
	Monde.LE_MONDE.ajouterActeur(new Client(PartsdeMarche));
}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq1DIST";
	}

	@Override
	public void next() {
		Acteur eq5 = (Monde.LE_MONDE.getActeur("Eq5TRAN"));
	//	ITransformateur eq5v =
				((ITransformateur)eq5).sell(400);;
	//	eq5v.sell(400);
				
			System.out.println("Le stock de l'équipe 1 : "+this.stock);
			
	}
	
	public double[][] getCommande(){
		// Alice Gauthier Elisa Gressier-Monard
		double[] commandeEq4 = {0,7500,7500,0,29167,12500} ;
		double[] commandeEq5 = {0,16167,5000,0,0,0};
		double[] commandeEq7 = {0,16167,5000,0,0,0};
		double[][] commande= new double[3][6];
		commande[0] = commandeEq4;
		commande[1] = commandeEq5;
		commande[2] = commandeEq7;
		return(commande);
		
		
	}
	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		// TODO Auto-generated method stub
		return null ;
	}
		
	

}
