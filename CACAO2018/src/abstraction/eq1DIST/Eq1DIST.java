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
	private Stock stock;
	private Journal journal;
	private Indicateur[] stocks;
	private Indicateur solde;
	private String nom;
	private Indicateur[] nombreVentes;
	private Indicateur[] nombreAchatsOccasionnels;
	private Indicateur[] nombreAchatsContrat;
	private Indicateur efficacite;
	
	private Indicateur PrixChocoMdG;
	private Indicateur PrixChocoHdG;
	private Indicateur PrixConfMdG;
	private Indicateur PrixConfHdG;


public Eq1DIST()  {
	double[][] PartsdeMarche= {{0.7,0.49,0,0,0.42,0},
			                   {0,0.21,0.7,0,0.28,0.7},
			                   {0.3,0.3,0.3,0,0.3,0.3}};
	Journal client = new Journal("Clients Finaux");
	Monde.LE_MONDE.ajouterJournal(client);
	Monde.LE_MONDE.ajouterActeur(new Client(PartsdeMarche,client));
	this.stock = new Stock(0,50000,25000,0,35000,15000); 
	this.nombreAchatsOccasionnels = new Indicateur[6];
	this.nombreAchatsContrat = new Indicateur[6];
	this.nombreVentes = new Indicateur[6];
	this.stocks = new Indicateur[6];
	this.solde = new Indicateur("Solde de "+ this.getNom(), this,0);
	this.efficacite = new Indicateur("Efficacité de "+ this.getNom(), this,0);
	this.PrixChocoMdG=new Indicateur("Prix Choco MdG de "+this.getNom(),this,1.5);
	this.PrixChocoHdG=new Indicateur("Prix Choco HdG de "+this.getNom(),this,3.0);
	this.PrixConfMdG=new Indicateur("Prix Confiseries MdG de "+this.getNom(),this,2.6);
	this.PrixConfHdG=new Indicateur("Prix Confiseries HdG de "+this.getNom(),this,4.1);
		
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
		// on fait une demande occasionnelle si on dépasse un seuil limite de sotck
		int[] stocklim = {0,120000,30000,0,40000,20000};
		List<IvendeurOccasionnelChocoBis> vendeursOcca = new ArrayList<IvendeurOccasionnelChocoBis>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IvendeurOccasionnelChocoBis) {
			vendeursOcca.add((IvendeurOccasionnelChocoBis) a);
			}
		}
		
		for (int i =0; i<this.stock.getstock().size();i++) {
				if (this.stock.getstock().get(i).total()<stocklim[i]) {
					DemandeAO d= new DemandeAO(stocklim[i]-this.stock.getstock().get(i).total(),i+1);
					ArrayList<Integer> prop= new ArrayList<Integer>();
					for (IvendeurOccasionnelChocoBis v : vendeursOcca) {
						prop.add(v.getReponseBis(d));
					}
					int a=Integer.MAX_VALUE;
					int n=0;
					for(int ind=0; ind<prop.size(); ind++){
					  		if(a>prop.get(ind)){
					  			a=prop.get(ind);
					  			n=ind;
					  		}
					  }
					 if (a!=Double.MAX_VALUE){
					  	this.stock.ajouter(d.getQuantite(),i);
					  	solde.setValeur(this, solde.getValeur()-a);
					  	vendeursOcca.get(n).envoyerReponseBis(d.getQuantite(),d.getQualite(),a);
					  } 
					 
				}
					
			
		}	
		if(Monde.LE_MONDE.getStep()%12==2
				||Monde.LE_MONDE.getStep()%12==3
				||Monde.LE_MONDE.getStep()%12==4
				||Monde.LE_MONDE.getStep()%12==5
				||Monde.LE_MONDE.getStep()%12==18
				||Monde.LE_MONDE.getStep()%12==19
				||Monde.LE_MONDE.getStep()%12==20
				||Monde.LE_MONDE.getStep()%12==21) {
			int[] stockspe = {0,29877,13125,0,21875,9375};
			for (int i =0; i<this.stock.getstock().size();i++) {
					DemandeAO d= new DemandeAO(stockspe[i],i+1);
					ArrayList<Integer> prop= new ArrayList<Integer>();
					for (IvendeurOccasionnelChocoBis v : vendeursOcca) {
						prop.add(v.getReponseBis(d));
					}
					int a=Integer.MAX_VALUE;
					int n=0;
					for(int ind=0; ind<prop.size(); ind++){
					  		if(a>prop.get(ind)){
					  			a=prop.get(ind);
					  			n=ind;
					  		}
					  }
					 if (a!=Double.MAX_VALUE){
					  	this.stock.ajouter(d.getQuantite(),i);
					  	solde.setValeur(this, solde.getValeur()-a);
					  	vendeursOcca.get(n).envoyerReponseBis(d.getQuantite(),d.getQualite(),a);
					  } 
					 
			}
				}
			}

	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		int[] res = new int[6];
		double[] prix = {Double.MAX_VALUE,this.PrixChocoMdG.getValeur(),this.PrixChocoHdG.getValeur(),Double.MAX_VALUE,this.PrixConfMdG.getValeur(),this.PrixConfHdG.getValeur()};
		for (int i =0; i <6;i++) {
			int f = this.stock.getstock().get(i).total()-Q.getValeur(i);
			if (f>=0) {
				res[i] = Q.getValeur(i);
				this.stock.retirer(Q.getValeur(i),i+1);
			}
			else {
				res[i] = this.stock.getstock().get(i).total();
				this.stock.retirer(this.stock.getstock().get(i).total(),i+1);
				}
				solde.setValeur(this, solde.getValeur()+res[i]*prix[i]);
			}
		return new GrilleQuantite(res);
	}
	
	

	 public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		ArrayList<ArrayList<Integer>> commandeFinale;
		commandeFinale = new ArrayList<ArrayList<Integer>>();
		/*
		 * System.out.println("appelee ...");
		 * double[]demande;
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
		 */
		return commandeFinale;
		}
	 
	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		for (int i=0;i<livraison.size();i++) {
			this.stock.retirer(livraison.get(i), i+1);
			stocks[i].setValeur(this, this.stock.getstock().get(i).total());
			solde.setValeur(this,solde.getValeur()+paiement);
		}
		
	}
	
	@Override
	public double[] getPrix() {
		// TODO Auto-generated method stub
		return new double[] {0,this.PrixChocoMdG.getValeur(),this.PrixChocoHdG.getValeur(),
				0,this.PrixConfMdG.getValeur(),this.PrixConfHdG.getValeur()};
	}
}
