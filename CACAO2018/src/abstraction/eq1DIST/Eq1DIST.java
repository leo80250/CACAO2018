package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.List;

import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;



  
public class Eq1DIST implements Acteur, InterfaceDistributeurClient, IAcheteurChoco {
	private int[][] stock;
	private double banque;
	private Journal journal;


	public Eq1DIST()  {
		this.stock[0][0] = 0 ;
		this.stock[0][1] = 50000 ;
		this.stock[0][2] = 25000;
		this.stock[1][0] = 0 ;
		this.stock[1][1] = 35000;
		this.stock[1][2] = 15000;
		this.journal= new Journal("equipe1");
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
			{0,0,0},
			{0,0,0}
		};
		for (int i =0; i<3;i++) {
			for (int j=0;j<3;j++) {
				if (stock[i][j]<stocklim[i][j]) {
					
				}
					
			}
		}
			
	}
	
	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		int[][] res = new int[2][3];
		double[][] prix = new double[][] {
			{0.9,1.5,3.0},
			{1.0,2.6,4.1}
		};
		for (int i =0; i <3;i++) {
			for (int j = 0; j <3;j++) {
				int f = this.stock[i][j]-Q.getValeur(i, j);
				if (f>=0) {
					res[i][j] = Q.getValeur(i, j);
				}
				else {
					res[i][j] = stock[i][j];
					}
				this.banque += res[i][j]*prix[i][j];
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
	
	public void livraison(GQte d) {
		for(int i=0; i<3;i++) {
			stock[0][0] += d.getqTabletteBQ();
			stock[0][1] += d.getqTabletteMQ();
			stock[0][2] += d.getqTabletteHQ();
			stock[1][0] += d.getqBonbonBQ();
			stock[1][1] += d.getqBonbonMQ();
			stock[1][2] += d.getqBonbonHQ();
		}
	}
	

	

}
