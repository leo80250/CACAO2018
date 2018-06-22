/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

import java.util.ArrayList;

import abstraction.eq6DIST.Eq6DIST;
import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Client implements Acteur {
	private double[][] PartdeMarche;
	private Journal journal;
	
	//Monde.LE_MONDE.ajouterActeur(new Client(PartsdeMarche,client)); penser à rajouter dans EQ1
	/**
	 *
	 * @param double[][] P 
	 *           
	 * @return  tableau des parts de marché pour chaque type de chocolat en fonction du distributeur
	 *            Les lignes correspondent aux différents distributeurs.
	 *            les colonnes correpondent de gauche à droite: Tablette BQ ; Tablette MQ ; Tablette HQ ; Confiserie BQ ; Confiserie MQ ; Confiserie HQ
	 *            
	 */
	public Client(double[][] P, Journal journal) {
		if (P.length == 3 && P[0].length == 6) {
			this.PartdeMarche = P;
		} else { 
			this.PartdeMarche = new double[3][6];
		}
		this.journal=journal;
	}
	

	public Client() {
		this.PartdeMarche = new double[3][6];
	}

	
	
	/**
	 *
	 * @param int i (ligne), int j (colonne), double valeur
	 *           
	 * @return modifie le tableau des parts de marché : la part de marché [i][j] prend la valeur "valeur"
	 *            
	 */

	public void Modifier(int i, int j, double valeur) {
		this.PartdeMarche[i][j] = valeur;
	}

	/**
	 *
	 * @param int i (ligne), int j (colonne)
	 *           
	 * @return donne la valeur de la part de marché [i]{j]
	 *            
	 */
	
	public double getValeur(int i, int j) {
		return this.PartdeMarche[i][j];
	}
	
	/**
	 *
	 * @param 
	 *           
	 * @return donne le tableau des parts de marché
	 *            
	 */

	public double[][] getPartdeMarche() {
		return this.PartdeMarche;
	}
	
	/**
	 *
	 * @param int[][] h,int i
	 *           
	 * @return donne la commande correspondant au distributeur i à partir du tableau des commandes totales
	 *         la valeur de la commande est un random de médiane la commande de ce next contenue dans h   
	 *         et de min/max égaux à +/- 30% de la valeur de h
	 *         la première ligne correspond au chocolat
	 *         la deuxième ligne correspond aux confiseries
	 */
	
	public int[] commande(int[][] h,int i){
		int[] cm= new int[6];
		for(int j=0;j<=5;j++) {
			cm[j]=(int)(0.7*h[i][j%3]*this.getValeur(i, j)*(Math.random()*0.3*h[i][j%3]*this.getValeur(i, j)));
		}
		return cm;
	}
	
	public void next() {
		// a = tableau de la demande en chocolat par rapport à la période de l'année
		// ligne 1 : choco bas de gamme
		// ligne 2 : choco milieu de gamme
		// ligne 3 : choco haut de gamme
		int[][] a= {{1630,966,983,1448,1035,1035,1655,1379,862,862,862,862,862,862,862,862,862,862,1379,2414,1552,1035,1310,1931},
				{11833,12744,12971,19115,13654,13654,21846,18205,11378,11378,11378,11378,11378,11378,11378,11378,11378,11378,18205,31859,20481,13654,17295,25487},
				{1569,1689,1720,2534,1810,1810,2896,2413,1508,1508,1508,1508,1508,1508,1508,1508,1508,1508,2413,4223,2715,1810,2293,3379}};
		
		// b = tableau de la demande en confiseries par rapport à la période de l'année
				// ligne 1 : confiseries bas de gamme
				// ligne 2 : confiseries milieu de gamme
				// ligne 3 : confiseries haut de gamme
		int[][] b= {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{9683,10428,10614,15642,11173,11173,17876,14897,9311,9311,9311,9311,9311,9311,9311,9311,9311,9311,14897,26069,16759,11173,14152,20856},
				{2074,2234,2274,3351,2394,2394,3830,3191,1995,1995,1995,1995,1995,1995,1995,1995,1995,1995,3191,5585,3590,2394,3032,4468}};
		
		
		int periode=Monde.LE_MONDE.getStep()%24;	// on récupère la période
		ArrayList<Acteur> Acteurs =Monde.LE_MONDE.getActeurs(); // récupération des acteurs
		ArrayList<Acteur> Distributeurs = new ArrayList<Acteur>();
		//récupération des instances de distributeurs
		for(Acteur c: Acteurs) {
			if(c instanceof InterfaceDistributeurClient) {
				Distributeurs.add(c);
			}
		}
		//h = tableau de lacommande totale de la période
		//ligne 1 : choco BdG , choco MdG, choco HdG
		//ligne 2 : confiseries BdG , confiseries MdG, confiseries HdG
		int[][] h = {{a[0][periode],a[1][periode],a[2][periode]},{b[0][periode],b[1][periode],b[2][periode]}};
		
		int[] cm= this.commande(h, 0);
		GrilleQuantite CommandeMousquetaire = new GrilleQuantite (cm);
		this.journal.ajouter("La demande pour le distributeur Mousquetaire est : "+CommandeMousquetaire.toString());
		int[] cas= this.commande(h, 1);
		GrilleQuantite CommandeCasino = new GrilleQuantite (cas);
		int[] autre= this.commande(h, 2);
		GrilleQuantite CommandeAutre = new GrilleQuantite (autre);
		
		for(int i=0;i<Distributeurs.size();i++) {
			if(Distributeurs.get(i).getNom()=="Eq1DIST") {
				InterfaceDistributeurClient Mousquetaire=(InterfaceDistributeurClient)(Distributeurs.get(i));
				Mousquetaire.commander(CommandeMousquetaire);
			}
			if(Distributeurs.get(i).getNom()=="Eq6DIST") {
				InterfaceDistributeurClient Casino=(InterfaceDistributeurClient)(Distributeurs.get(i));
				Casino.commander(CommandeCasino);
			}
			else {
				InterfaceDistributeurClient Autre=(InterfaceDistributeurClient)(Distributeurs.get(i));
				Autre.commander(CommandeAutre);
			}
		}
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Clients finaux";
	}
}