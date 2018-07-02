package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq4TRAN.Eq4TRAN;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq5TRAN.Eq5TRAN;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChoco;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChocoBis;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.eq6DIST.IAcheteurChocoBis;
import abstraction.eq7TRAN.Eq7TRAN;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;



  
public class Eq1DIST implements Acteur, InterfaceDistributeurClient, IAcheteurChocoBis {
	private int[][] stock;
	private Journal journal;
	private Indicateur[] stocks;
	private Indicateur solde;
	private String nom;
	private Indicateur[] nombreVentes;
	private Indicateur[] nombreAchatsOccasionnels;
	private Indicateur[] nombreAchatsContrat;
	private Indicateur efficacite;


public Eq1DIST()  {
	double[][] PartsdeMarche= {{0.7,0.49,0,0,0.42,0},
			                   {0,0.21,0.7,0,0.28,0.7},
			                   {0.3,0.3,0.3,0,0.3,0.3}};
	Journal client = new Journal("Clients Finaux");
	Monde.LE_MONDE.ajouterJournal(client);
	Monde.LE_MONDE.ajouterActeur(new Client(PartsdeMarche,client));
	int[][] stock= new int[2][3];
	stock[0][0] = 0 ;
	stock[0][1] = 50000 ;
	stock[0][2] = 25000;
	stock[1][0] = 0 ;
	stock[1][1] = 35000;
	stock[1][2] = 15000;
	this.nombreAchatsOccasionnels = new Indicateur[6];
	this.nombreAchatsContrat = new Indicateur[6];
	this.nombreVentes = new Indicateur[6];
	this.stocks = new Indicateur[6];
	this.solde = new Indicateur("Solde de "+ this.getNom(), this,0);
	this.efficacite = new Indicateur("Efficacité de "+ this.getNom(), this,0);
	this.stock=stock;
		
		this.journal= new Journal("Journal de Eq1DIST");
		journal.ajouter("Absentéisme");
		Monde.LE_MONDE.ajouterJournal(this.journal);
}
	@Override
	public String getNom() {
		return "Eq1DIST";
	}

	@Override
	public void next() {
		int[][] stocklim = new int[][] {
			{0,120000,30000},
			{0,40000,20000}
		};
		List<IvendeurOccasionnelChocoBis>vendeursOcca = new ArrayList<IvendeurOccasionnelChocoBis>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IvendeurOccasionnelChocoBis) {
			vendeursOcca.add((IvendeurOccasionnelChocoBis) a);
			}
		}
		
		for (int i =0; i<stock.length;i++) {//3;i++) {
			for (int j=0;j<stock[0].length; j++) {//3;j++) {
				if (stock[i][j]<stocklim[i][j]) {
					DemandeAO d= new DemandeAO(stocklim[i][j]-stock[i][j],i*j);
					ArrayList<Integer> prop= new ArrayList<Integer>();
					for (IvendeurOccasionnelChocoBis v : vendeursOcca) {
						prop.add(v.getReponseBis(d));
					}/*
					prop.add(((Eq4TRAN) Monde.LE_MONDE.getActeur("Eq4TRAN")).getReponseBis(d));
					prop.add((int) 4);
					prop.add(((Eq5TRAN) Monde.LE_MONDE.getActeur("Eq5TRAN")).getReponseBis(d));
					prop.add((int) 5);
					prop.add(((Eq7TRAN) Monde.LE_MONDE.getActeur("Eq7TRAN")).getReponseBis(d));
					prop.add((int) 7);
					*/
					/*int a=prop.get(0);
					double n= (prop.get(1));
					 for(int ind=1; ind<3; ind++){
					  		if(a>prop.get(ind+2)){
					  			a=prop.get(ind+2);
					  			n=prop.get(ind+3);
					  		}
					  }*/
					int n=0;
					 int a = Integer.MAX_VALUE;
					 for (int in=0;in<prop.size(); in++) {
						 if (a>prop.get(in)) {
							 a= prop.get(in);
							 n = in;
						 }
					 }
					 if (a!=Double.MAX_VALUE){
					  		this.stock[i][j]+=d.getQuantite();
					  		solde.setValeur(this, solde.getValeur()-a);
					  		String l = "Eq"+((int) n)+"TRAN";
					  		//((IvendeurOccasionnelChoco) Monde.LE_MONDE.getActeur(l)).
					  		vendeursOcca.get(n).envoyerReponseBis(d.getQuantite(),d.getQualite(),a);
					  } 
					 
				}
					
			}
		}	
		if(Monde.LE_MONDE.getStep()%12==2
				||Monde.LE_MONDE.getStep()%12==2
				||Monde.LE_MONDE.getStep()%12==3
				||Monde.LE_MONDE.getStep()%12==4
				||Monde.LE_MONDE.getStep()%12==5
				||Monde.LE_MONDE.getStep()%12==18
				||Monde.LE_MONDE.getStep()%12==19
				||Monde.LE_MONDE.getStep()%12==20
				||Monde.LE_MONDE.getStep()%12==21) {
			int[][] stockspe= new int[][] {
				{0,29877,13125},
				{0,21875,9375}
			};
			for(int i=0;i<stockspe.length;i++) {//3;i++) {
				for(int j=0;j<stockspe[0].length;j++) {//3;j++) {
					DemandeAO d= new DemandeAO(stockspe[i][j],i*j);
					ArrayList<Integer> prop= new ArrayList<Integer>();
					for (IvendeurOccasionnelChocoBis v : vendeursOcca) {
						prop.add(v.getReponseBis(d));
					}/*
					ArrayList<Double> prop= new ArrayList<Double>();
					prop.add(((Eq4TRAN) Monde.LE_MONDE.getActeur("Eq4TRAN")).getReponseBis(d));
					prop.add(((Eq5TRAN) Monde.LE_MONDE.getActeur("Eq5TRAN")).getReponseBis(d));
					prop.add(((Eq7TRAN) Monde.LE_MONDE.getActeur("Eq7TRAN")).getReponseBis(d));
					*/
				}
			}
		}
	}

	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		int[] res = new int[6];
		double[][] prix = new double[][] {
			{0.9,1.5,3.0},
			{1.0,2.6,4.1}
		};
		for (int i =0; i <2;i++) {
			for (int j = 0; j <3;j++) {
				int f = this.stock[i][j]-Q.getValeur(3*i+j);
				if (f>=0) {
					res[3*i+j] = Q.getValeur(3*i+j);
					this.stock[i][j]-=Q.getValeur(3*i+j);
				}
				else {
					res[3*i+j] = stock[i][j];
					this.stock[i][j]=0;
					}
				solde.setValeur(this, solde.getValeur()+res[3*i+j]*prix[i][j]);
			}
		}
		return new GrilleQuantite(res);
	}
	
	

	@Override
	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		System.out.println("appelee ...");
		double[]demande;
		demande = new double[6];
		demande[0]=0;
		demande[1]=39834;
		demande[2]=17500;
		demande[3]=0;
		demande[4]=29167;
		demande[5]=12500;
		double[] p;
		p= new double[3];
		double somme;
		ArrayList<ArrayList<Integer>> commandeFinale;
		commandeFinale = new ArrayList<ArrayList<Integer>>();
		for (int i=0;i<6;i++){
			somme = 0;
			for (int h=0;h<3;h++) {
				somme += stock[h][i]; 
			}
			for (int j=0;j<3;j++) {			
			p[i]=stock[j][i]/somme;
			if(p[i]*demande[i]<= stock[j][i]){
				commandeFinale.get(j).set(i, ((int)(p[i]*demande[i])));
			}
			else {
				commandeFinale.get(j).set(i,((int)(stock[0][i])));
			}

	}
			}
		return commandeFinale;
		}
	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		for (int i=0;i<6;i++) {
			stocks[i].setValeur(this, livraison.get(i));
			solde.setValeur(this,solde.getValeur()+paiement);
		}
		
	}
	@Override
	public double[] getPrix() {
		// TODO Auto-generated method stub
		return null;
	}
}
