package abstraction.eq1DIST;

import java.util.ArrayList;

import abstraction.eq4TRAN.Eq4TRAN;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq5TRAN.Eq5TRAN;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.eq7TRAN.Eq7TRAN;
import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;



  
public class Eq1DIST implements Acteur, InterfaceDistributeurClient, IAcheteurChoco {
	private int[][] stock;
	private double banque;
	private Journal journal;


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
	this.stock=stock;
		
		this.journal= new Journal("Journal de Eq1DIST");
		journal.ajouter("Absentéisme");
		Monde.LE_MONDE.ajouterJournal(this.journal);
}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Eq1DIST";
	}

	@Override
	public void next() {
		int[][] stocklim = new int[][] {
			{0,120000,30000},
			{0,40000,20000}
		};
		
		for (int i =0; i<3;i++) {
			for (int j=0;j<3;j++) {
				if (stock[i][j]<stocklim[i][j]) {
					DemandeAO d= new DemandeAO(stocklim[i][j]-stock[i][j],i*j);
					ArrayList<Double> prop= new ArrayList<Double>();
					//prop.add(((Eq4TRAN) Monde.LE_MONDE.getActeur("Eq4TRAN")).getReponseBis(d));
					prop.add(((Eq5TRAN) Monde.LE_MONDE.getActeur("Eq5TRAN")).getReponseBis(d));
					//prop.add(((Eq7TRAN) Monde.LE_MONDE.getActeur("Eq7TRAN")).getReponseBis(d));
					 int a=prop.get(0);
					 int n=0;
					 for(int ind=1; ind<3; ind++){
					  		if(a>prop.get(ind)){
					  			a=prop.get(ind);
					  			n=ind;
					  		}
					  }
					 if (a!=Double.MAX_VALUE){
					  		this.stock[i][j]+=d.getQuantite();
					  		this.banque-=a;
					  		String l = "Eq"+n+"TRAN";
					  		((Eq5TRAN) Monde.LE_MONDE.getActeur("Eq5TRAN")).envoyerReponseBis(d.getQuantite(),d.getQualite(),a);
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
				}
				else {
					res[3*i+j] = stock[i][j];
					}
				this.banque += res[3*i+j]*prix[i][j];
			}
		}
		return new GrilleQuantite(res);
	}
	@Override
	public ArrayList<GQte> getCommande(ArrayList<GPrix> gPrix, ArrayList<GQte> stock) {
		ArrayList<GQte> l = new ArrayList<GQte>();
		l.add(new GQte(0,7500,7500,0,29167,12500));
		l.add(new GQte(0,16167,5000,0,0,0));
		l.add(new GQte(0,16167,5000,0,0,0));
		return l;
	}
	
	public void livraison(GQte d,double solde) {
		for(int i=0; i<3;i++) {
			stock[0][0] += d.getqTabletteBQ();
			stock[0][1] += d.getqTabletteMQ();
			stock[0][2] += d.getqTabletteHQ();
			stock[1][0] += d.getqBonbonBQ();
			stock[1][1] += d.getqBonbonMQ();
			stock[1][2] += d.getqBonbonHQ();
		}
		this.banque -= solde;
	}


	

	

}
