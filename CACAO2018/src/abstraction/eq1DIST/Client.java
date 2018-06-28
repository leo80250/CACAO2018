/**
 * @author Leo Vuylsteker / Axelle Hermelin 
 */

package abstraction.eq1DIST;

import java.util.ArrayList;

import abstraction.eq6DIST.Eq6DIST;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Client implements Acteur {
	private double[][] PartdeMarche;
	private Journal journal;
	
	private Indicateur DemandeChocoBdG;
	private Indicateur DemandeChocoMdG;
	private Indicateur DemandeChocoHdG;
	private Indicateur DemandeConfBdG;
	private Indicateur DemandeConfMdG;
	private Indicateur DemandeConfHdG;
	
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
		this.DemandeChocoBdG=new Indicateur("DemandeChocoBdG", this,0.0);
		this.DemandeChocoMdG=new Indicateur("DemandeChocoMdG", this,0.0);
		this.DemandeChocoHdG=new Indicateur("DemandeChocoHdG", this,0.0);
		this.DemandeConfBdG=new Indicateur("DemandeConfBdG", this,0.0);
		this.DemandeConfMdG=new Indicateur("DemandeConfMdG", this,0.0);
		this.DemandeConfHdG=new Indicateur("DemandeConfHdG", this,0.0);
	}
	

	public Client() {
		this.PartdeMarche = new double[3][6];
	}

	
	
	/**
	 *
	 * @param int i (ligne), int j (colonne), double commande envoyé pour 1 catégorie >= double réponse du distributeur pour 1 carégorie
	 *           
	 * @return modifie le tableau des parts de marché : la part de marché [i][j] prend la valeur "valeur"
	 *            
	 */

	public void ModifierPartsDeMarche(int i, int j, double commande, double reponse) {
		if (commande!=0) {
			double valeur = this.getValeur(i, j)*(1-(commande-reponse)/commande);
			if(valeur<0) {this.PartdeMarche[i][j]=0;}
			else {this.PartdeMarche[i][j]=(double)Math.round(valeur * 1000) / 1000;}
		}
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
	
	public int[] commande(int[] h,int i){
		int[] cm= new int[6];
		for(int j=0;j<=5;j++) {
			cm[j]=(int)(0.7*h[j]*this.getValeur(i, j)+(Math.random()*0.6*h[j]*this.getValeur(i, j)));
		}
		return cm;
	}
	
	public void next() {
		this.journal.ajouter("------------------------------ Période n°"+Monde.LE_MONDE.getStep()+" ------------------------------");
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
		int[] h = {a[0][periode],a[1][periode],a[2][periode],b[0][periode],b[1][periode],b[2][periode]};
		
		int[] cm= this.commande(h, 0);
		GrilleQuantite CommandeMousquetaire = new GrilleQuantite (cm);
		int[] cas= this.commande(h, 1);
		GrilleQuantite CommandeCasino = new GrilleQuantite (cas);
		int[] autre= this.commande(h, 2);
		
		
		DemandeChocoBdG.setValeur(this,cm[0]+cas[0]+autre[0]+0.0);
		DemandeChocoMdG.setValeur(this,cm[1]+cas[1]+autre[1]+0.0);
		DemandeChocoHdG.setValeur(this,cm[2]+cas[2]+autre[2]+0.0);
		DemandeConfBdG.setValeur(this,cm[3]+cas[3]+autre[3]+0.0);
		DemandeConfMdG.setValeur(this,cm[4]+cas[4]+autre[4]+0.0);
		DemandeConfHdG.setValeur(this,cm[5]+cas[5]+autre[5]+0.0);
		
		
		for(int i=0;i<Distributeurs.size();i++) {
			if(Distributeurs.get(i).getNom()=="Eq6DIST") {
				this.journal.ajouter("");
				this.journal.ajouter("• Les Mousquetaires •");
				this.journal.ajouter("");
				this.journal.ajouter("- La demande pour le distributeur Mousquetaire est : "+CommandeMousquetaire.toString()+"\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Mousquetaire=(InterfaceDistributeurClient)(Distributeurs.get(i));
				GrilleQuantite ReponseMousquetaire = Mousquetaire.commander(CommandeMousquetaire);
				this.journal.ajouter("- Les magasins Mousquetaire ont vendu (effectivement) : "+ReponseMousquetaire.toString()+"\n");
				this.journal.ajouter("");
				
				this.ModifierPartsDeMarche(0, 0, CommandeMousquetaire.getValeur(0), ReponseMousquetaire.getValeur(0));
				this.ModifierPartsDeMarche(0, 1, CommandeMousquetaire.getValeur(1), ReponseMousquetaire.getValeur(1));
				this.ModifierPartsDeMarche(0, 2, CommandeMousquetaire.getValeur(2), ReponseMousquetaire.getValeur(2));
				this.ModifierPartsDeMarche(0, 3, CommandeMousquetaire.getValeur(3), ReponseMousquetaire.getValeur(3));
				this.ModifierPartsDeMarche(0, 4, CommandeMousquetaire.getValeur(4), ReponseMousquetaire.getValeur(4));
				this.ModifierPartsDeMarche(0, 5, CommandeMousquetaire.getValeur(5), ReponseMousquetaire.getValeur(5));
				
				this.journal.ajouter("");
				this.journal.ajouter("- Les parts de marché des magasins Mousquetaire sont désormais : "+this.getValeur(0,0)+"% sur les Tablettes BG ; "+ this.getValeur(0,1)+"% sur les Tablettes MG ; "+this.getValeur(0,2)+"% sur les Tablettes HG ; "
						+this.getValeur(0,3)+"% sur les Confiseries BG ; "+this.getValeur(0,4)+"% sur les Confiseries MG ; "+this.getValeur(0,5)+"% sur les Confiseries HG.");
				this.journal.ajouter("");
			}
			else if(Distributeurs.get(i).getNom()=="Eq1DIST") {
				this.journal.ajouter("");
				this.journal.ajouter("• Casino •");
				this.journal.ajouter("");
				this.journal.ajouter("- La demande pour le distributeur Casino est : "+CommandeCasino.toString()+"\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Casino=(InterfaceDistributeurClient)(Distributeurs.get(i));
				GrilleQuantite ReponseCasino = Casino.commander(CommandeCasino);
				this.journal.ajouter("- Les magasins Casino ont vendu (effectivement) : "+ReponseCasino.toString()+"\n");
				this.journal.ajouter("");
				
				this.ModifierPartsDeMarche(1, 0, CommandeCasino.getValeur(0), ReponseCasino.getValeur(0));
				this.ModifierPartsDeMarche(1, 1, CommandeCasino.getValeur(1), ReponseCasino.getValeur(1));
				this.ModifierPartsDeMarche(1, 2, CommandeCasino.getValeur(2), ReponseCasino.getValeur(2));
				this.ModifierPartsDeMarche(1, 3, CommandeCasino.getValeur(3), ReponseCasino.getValeur(3));
				this.ModifierPartsDeMarche(1, 4, CommandeCasino.getValeur(4), ReponseCasino.getValeur(4));
				this.ModifierPartsDeMarche(1, 5, CommandeCasino.getValeur(5), ReponseCasino.getValeur(5));
				
				this.journal.ajouter("");
				this.journal.ajouter("- Les parts de marché des magasins Casino sont désormais : "+this.getValeur(1,0)+"% sur les Tablettes BG ; "+ this.getValeur(1,1)+"% sur les Tablettes MG ; "+this.getValeur(1,2)+"% sur les Tablettes HG ; "
						+this.getValeur(1,3)+"% sur les Confiseries BG ; "+this.getValeur(1,4)+"% sur les Confiseries MG ; "+this.getValeur(1,5)+"% sur les Confiseries HG.");
				this.journal.ajouter("");
			}
			else {
				GrilleQuantite CommandeAutre = new GrilleQuantite (autre);
				this.journal.ajouter("• Autres distributeurs •");
				this.journal.ajouter("");
				this.journal.ajouter("- La demande pour les autres distributeurs est : "+CommandeAutre.toString()+"\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Autre=(InterfaceDistributeurClient)(Distributeurs.get(i));
				GrilleQuantite ReponseAutre = Autre.commander(CommandeAutre);
				this.journal.ajouter("- Les autres magasins ont vendu (effectivement) : "+ReponseAutre.toString()+"\n");
				this.journal.ajouter("");
				
				this.ModifierPartsDeMarche(2, 0, CommandeAutre.getValeur(0), ReponseAutre.getValeur(0));
				this.ModifierPartsDeMarche(2, 1, CommandeAutre.getValeur(1), ReponseAutre.getValeur(1));
				this.ModifierPartsDeMarche(2, 2, CommandeAutre.getValeur(2), ReponseAutre.getValeur(2));
				this.ModifierPartsDeMarche(2, 3, CommandeAutre.getValeur(3), ReponseAutre.getValeur(3));
				this.ModifierPartsDeMarche(2, 4, CommandeAutre.getValeur(4), ReponseAutre.getValeur(4));
				this.ModifierPartsDeMarche(2, 5, CommandeAutre.getValeur(5), ReponseAutre.getValeur(5));
				
				this.journal.ajouter("");
				this.journal.ajouter("Les parts de marché des autres magasins sont désormais : "+ this.getValeur(2,0)+"% sur les Tablettes BG ; "+ this.getValeur(2,1)+"% sur les Tablettes MG ; "+this.getValeur(2,2)+"% sur les Tablettes HG ; "
						+this.getValeur(2,3)+"% sur les Confiseries BG ; "+this.getValeur(2,4)+"% sur les Confiseries MG ; "+this.getValeur(2,5)+"% sur les Confiseries HG.");
				this.journal.ajouter("");
			}
		}
		
		//modification des parts de marché
	
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Clients finaux";
	}
	
//	public static void main(String[] args) {
//		double[][] PartsdeMarche= {{0.7,0.49,0,0.42,0,0},
//                {0,0.21,0.7,0,0.7,0},
//                {0.3,0.3,0.3,0.58,0.3,0}};
//		Journal clientj = new Journal("Clients Finaux");
//		Client client=new Client(PartsdeMarche,clientj);
//		
//		int[] h= {1000,1000,0,1000,0,0};
//		int[] h0=client.commande(h, 0);
//		int[] h1=client.commande(h, 1);
//		int[] h2=client.commande(h, 2);
//		System.out.println(h0[0]+" "+h0[1]+" "+h0[2]+" "+h0[3]+" "+h0[4]+" "+h0[5]);
//		System.out.println(h1[0]+" "+h1[1]+" "+h1[2]+" "+h1[3]+" "+h1[4]+" "+h1[5]);
//		System.out.println(h2[0]+" "+h2[1]+" "+h2[2]+" "+h2[3]+" "+h2[4]+" "+h2[5]);
//	}
}