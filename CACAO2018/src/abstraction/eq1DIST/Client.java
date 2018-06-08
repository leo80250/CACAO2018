/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

import java.util.ArrayList;

import abstraction.eq6DIST.Eq6DIST;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class Client implements Acteur {
	private double[][] PartdeMarche;
 
	public Client(double[][] P) {
		if (P.length == 3 && P[0].length == 6) {
			this.PartdeMarche = P;
		} else { 
			this.PartdeMarche = new double[3][6];
		}
	}

	public Client() {
		this.PartdeMarche = new double[3][6];
	}

	public void Modifier(int i, int j, double valeur) {
		this.PartdeMarche[i][j] = valeur;
	}

	public double getValeur(int i, int j) {
		return this.PartdeMarche[i][j];
	}

	public double[][] getPartdeMarche() {
		return this.PartdeMarche;
	}
	
	public int[][] commande(int[][] h,int i){
		int[][] cm= new int[2][3];
		for(int j=0;j<=5;j++) {
			cm[i][j]=(int)(0.7*h[i][j%3]*this.getValeur(i, j)*(Math.random()*0.3*h[i][j%3]*this.getValeur(i, j)));
		}
		return cm;
	}
	
	public void next() {
		int[][] a= {{1630,966,983,1448,1035,1035,1655,1379,862,862,862,862,862,862,862,862,862,862,1379,2414,1552,1035,1310,1931},
				{11833,12744,12971,19115,13654,13654,21846,18205,11378,11378,11378,11378,11378,11378,11378,11378,11378,11378,18205,31859,20481,13654,17295,25487},
				{1569,1689,1720,2534,1810,1810,2896,2413,1508,1508,1508,1508,1508,1508,1508,1508,1508,1508,2413,4223,2715,1810,2293,3379}};
		
		GrilleQuantite ChocolatsMois= new GrilleQuantite(a);
		
		int[][] b= {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{9683,10428,10614,15642,11173,11173,17876,14897,9311,9311,9311,9311,9311,9311,9311,9311,9311,9311,14897,26069,16759,11173,14152,20856},
				{2074,2234,2274,3351,2394,2394,3830,3191,1995,1995,1995,1995,1995,1995,1995,1995,1995,1995,3191,5585,3590,2394,3032,4468}};
		
		GrilleQuantite ConfiseriesMois= new GrilleQuantite(b);
		
		int periode=Monde.LE_MONDE.getStep()%24;	
		ArrayList<Acteur> Acteurs =Monde.LE_MONDE.getActeurs();
		ArrayList<Acteur> Distributeurs = new ArrayList<Acteur>();
		for(Acteur c: Acteurs) {
			if(c instanceof InterfaceDistributeurClient) {
				Distributeurs.add(c);
			}
		}
		
		int[][] h = {{a[0][periode],a[1][periode],a[2][periode]},{b[0][periode],b[1][periode],b[2][periode]}};
		
		int[][] cm= this.commande(h, 0);
		GrilleQuantite CommandeMousquetaire = new GrilleQuantite (cm);
		int[][] cas= this.commande(h, 1);
		GrilleQuantite CommandeCasino = new GrilleQuantite (cas);
		int[][] autre= this.commande(h, 2);
		GrilleQuantite CommandeAutre = new GrilleQuantite (autre);
		
		InterfaceDistributeurClient Mousquetaire=(InterfaceDistributeurClient)(Distributeurs.get(0));
		Mousquetaire.commander(CommandeMousquetaire);
		
		InterfaceDistributeurClient Casino=(InterfaceDistributeurClient)(Distributeurs.get(1));
		Casino.commander(CommandeCasino);
		
		InterfaceDistributeurClient Autre=(InterfaceDistributeurClient)(Distributeurs.get(2));
		Autre.commander(CommandeAutre);
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Clients finaux";
	}
}