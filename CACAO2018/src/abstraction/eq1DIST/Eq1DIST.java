package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import abstraction.eq4TRAN.Eq4TRAN;
import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq5TRAN.Eq5TRAN;
import abstraction.eq5TRAN.appeldOffre.DemandeAO;
import abstraction.eq5TRAN.appeldOffre.IvendeurOccasionnelChocoTer;
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

	public Eq1DIST() {
		double[][] PartsdeMarche = { { 0.7, 0.49, 0, 0, 0.42, 0 }, { 0, 0.21, 0.7, 0, 0.28, 0.7 },
				{ 0.3, 0.3, 0.3, 0, 0.3, 0.3 } };
		Journal client = new Journal("Clients Finaux");
		Monde.LE_MONDE.ajouterJournal(client);
		Monde.LE_MONDE.ajouterActeur(new Client(PartsdeMarche, client));

		this.stock = new Stock(0, 50000, 25000, 0, 35000, 15000);

		this.nombreAchatsOccasionnels = new Indicateur[6];
		for (int i = 0; i < 6; i++) {
			this.nombreAchatsOccasionnels[i] = new Indicateur("echanges occasionnels en " + Type.values()[i] + " eq1",
					this, 0);
			if (i != 0 && i != 3) { // on enlevera cette condition si un jour on choisit de faire du basse gamme
				Monde.LE_MONDE.ajouterIndicateur(this.nombreAchatsOccasionnels[i]);
			}
		}

		this.nombreAchatsContrat = new Indicateur[6];
		for (int i = 0; i < 6; i++) {
			this.nombreAchatsContrat[i] = new Indicateur("echanges contrat en " + Type.values()[i] + " eq1", this, 0);
			if (i != 0 && i != 3) {
				Monde.LE_MONDE.ajouterIndicateur(this.nombreAchatsContrat[i]);
			}
		}

		this.stocks = new Indicateur[6];
		this.stocks[1] = new Indicateur("stock" + Type.values()[1] + " eq1", this, 50000);
		Monde.LE_MONDE.ajouterIndicateur(this.stocks[1]);
		this.stocks[2] = new Indicateur("stock" + Type.values()[2] + " eq1", this, 25000);
		Monde.LE_MONDE.ajouterIndicateur(this.stocks[2]);
		this.stocks[4] = new Indicateur("stock" + Type.values()[4] + " eq1", this, 35000);
		Monde.LE_MONDE.ajouterIndicateur(this.stocks[4]);
		this.stocks[5] = new Indicateur("stock" + Type.values()[5] + " eq1", this, 15000);
		Monde.LE_MONDE.ajouterIndicateur(this.stocks[5]);

		this.nombreVentes = new Indicateur[6];
		for (int i = 0; i < 6; i++) {
			this.nombreVentes[i] = new Indicateur("nombre vente en " + Type.values()[i] + " eq1", this, 0);
			if (i != 0 && i != 3) {
				Monde.LE_MONDE.ajouterIndicateur(this.nombreVentes[i]);
			}
		}

		this.solde = new Indicateur("solde eq1", this, 500000);
		Monde.LE_MONDE.ajouterIndicateur(this.solde);
		this.efficacite = new Indicateur("efficacite eq1", this, 0);
		Monde.LE_MONDE.ajouterIndicateur(this.efficacite);

		this.PrixChocoMdG = new Indicateur("Prix Choco MdG eq1", this, 1.5);
		Monde.LE_MONDE.ajouterIndicateur(this.PrixChocoMdG);
		this.PrixChocoHdG = new Indicateur("Prix Choco HdG eq1", this, 3.0);
		Monde.LE_MONDE.ajouterIndicateur(this.PrixChocoHdG);
		this.PrixConfMdG = new Indicateur("Prix Confiseries MdG eq1", this, 2.6);
		Monde.LE_MONDE.ajouterIndicateur(this.PrixConfMdG);
		this.PrixConfHdG = new Indicateur("Prix Confiseries HdG eq1", this, 4.1);
		Monde.LE_MONDE.ajouterIndicateur(this.PrixConfHdG);

		this.journal = new Journal("Journal de Eq1DIST");
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}

	@Override
	public String getNom() {
		return "Eq1DIST";
	}

	@Override
	public void next() {
		this.journal.ajouter("Periode "+Monde.LE_MONDE.getStep());
		this.journal.ajouter("");
		
		for (int i = 0; i < 6; i++) {
			this.nombreAchatsOccasionnels[i].setValeur(this, 0);
		}
		this.venteOccalim();
		this.venteOccaspe();

		this.salaires();
		
		this.journal.ajouter("");
	}

	public void venteOccalim() {
		// on fait une demande occasionnelle si on dépasse un seuil limite de stock
		int[] stocklim = { 0, 120000, 30000, 0, 40000, 20000 };
		List<IvendeurOccasionnelChocoTer> vendeursOcca = new ArrayList<IvendeurOccasionnelChocoTer>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IvendeurOccasionnelChocoTer) {
				vendeursOcca.add((IvendeurOccasionnelChocoTer) a);
			}
		}
		for (int i = 0; i < this.stock.getstock().size(); i++) {
			if (this.stock.getstock().get(i).total() < stocklim[i]) {
				DemandeAO d = new DemandeAO(stocklim[i] - this.stock.getstock().get(i).total(), i + 1);
				ArrayList<Double> prop = new ArrayList<Double>();
				for (IvendeurOccasionnelChocoTer v : vendeursOcca) {
					prop.add(v.getReponseTer(d));
				}
				double a = Double.MAX_VALUE;
				int n = 0;
				for (int ind = 0; ind < prop.size(); ind++) {
					if (a > prop.get(ind)) {
						a = prop.get(ind);
						n = ind;
					}
				}
				if (a != Double.MAX_VALUE) {
					this.stock.ajouter(d.getQuantite(), i + 1);
					solde.setValeur(this, solde.getValeur() - a);
					vendeursOcca.get(n).envoyerReponseTer(this, d.getQuantite(), d.getQualite(), a);
					this.stocks[i].setValeur(this, this.stocks[i].getValeur() + d.getQuantite());
					this.nombreAchatsOccasionnels[i].setValeur(this,
							this.nombreAchatsOccasionnels[i].getValeur() + d.getQuantite());
					this.journal.ajouter("ACHAT OCCASIONNEL : L'équipe 1 a acheté " + d.getQuantite() + " unités de "
							+ Type.values()[i] + " à l'équipe " + ((Acteur) vendeursOcca.get(n)).getNom());
				}

			}
		}
	}

	public void venteOccaspe() {
		// on fait une demande occasionnelle en prevision des mois de forte consommation
		if (Monde.LE_MONDE.getStep() % 12 == 2 || Monde.LE_MONDE.getStep() % 12 == 3
				|| Monde.LE_MONDE.getStep() % 12 == 4 || Monde.LE_MONDE.getStep() % 12 == 5
				|| Monde.LE_MONDE.getStep() % 12 == 18 || Monde.LE_MONDE.getStep() % 12 == 19
				|| Monde.LE_MONDE.getStep() % 12 == 20 || Monde.LE_MONDE.getStep() % 12 == 21) {
			int[] stockspe = { 0, 29877, 13125, 0, 21875, 9375 };
			List<IvendeurOccasionnelChocoTer> vendeursOcca = new ArrayList<IvendeurOccasionnelChocoTer>();
			for (int i = 0; i < this.stock.getstock().size(); i++) {
				DemandeAO d = new DemandeAO(stockspe[i], i + 1);
				ArrayList<Double> prop = new ArrayList<Double>();
				for (IvendeurOccasionnelChocoTer v : vendeursOcca) {
					prop.add(v.getReponseTer(d));
				}
				double a = Double.MAX_VALUE;
				int n = 0;
				for (int ind = 0; ind < prop.size(); ind++) {
					if (a > prop.get(ind)) {
						a = prop.get(ind);
						n = ind;
					}
				}
				if (a != Double.MAX_VALUE) {
					this.stock.ajouter(d.getQuantite(), i);
					solde.setValeur(this, solde.getValeur() - a);
					vendeursOcca.get(n).envoyerReponseTer(this, d.getQuantite(), d.getQualite(), a);
					this.nombreAchatsOccasionnels[i].setValeur(this,
							this.nombreAchatsOccasionnels[i].getValeur() + d.getQuantite());
					this.stocks[i].setValeur(this, this.stocks[i].getValeur() + d.getQuantite());
					this.journal.ajouter("ACHAT OCCASIONNEL : L'équipe 1 a acheté " + d.getQuantite() + " unités de "
							+ Type.values()[i] + " à l'équipe " + ((Acteur) vendeursOcca.get(n)).getNom());
				}

			}
		}
	}

	public void salaires() {
		solde.setValeur(this, solde.getValeur() - 200000);
	}

	@Override
	public GrilleQuantite commander(GrilleQuantite Q) {
		int[] res = new int[6];
		double[] prix = { Double.MAX_VALUE, this.PrixChocoMdG.getValeur(), this.PrixChocoHdG.getValeur(),
				Double.MAX_VALUE, this.PrixConfMdG.getValeur(), this.PrixConfHdG.getValeur() };
		for (int i = 0; i < 6; i++) {
			int f = this.stock.getstock().get(i).total() - Q.getValeur(i);
			if (f >= 0) {
				res[i] = Q.getValeur(i);
				this.stock.retirer(Q.getValeur(i), i + 1);
			} else {
				res[i] = this.stock.getstock().get(i).total();
				this.stock.retirer(this.stock.getstock().get(i).total(), i + 1);
			}
			solde.setValeur(this, solde.getValeur() + res[i] * prix[i]);
			stocks[i].setValeur(this, stock.getstock().get(i).total());
			this.nombreVentes[i].setValeur(this, res[i]);
			this.journal.ajouter("l'équipe 1 a vendu " + res[i] + " unités de " + Type.values()[i]);
		}
		// mise a jour de l'efficacite en fonction de ce qu'on a pu vendre
		// selon ce qu'on nous avait demande
		double somme = 0;
		double a = 0;
		for (int i = 0; i < res.length; i++) {
			a = a + (Q.getValeur(i) - res[i]);
			somme = somme + Q.getValeur(i);
		}
		efficacite.setValeur(this, 1 - (double) a / somme);

		return new GrilleQuantite(res);

	}

	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		int[] demande;
		demande = new int[6];
		demande[3] = 0;
		demande[4] = 39834;
		demande[5] = 17500;
		demande[1] = 0;
		demande[2] = 29167;
		demande[3] = 12500;
		double[][] PrixVentes = new double[3][6];
		ArrayList<ArrayList<Integer>> commandeFinale = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> listeT = new ArrayList<Integer>();
		String act = "";
		ArrayList<Acteur> acteurs = Monde.LE_MONDE.getActeurs();
		ArrayList<IVendeurChocoBis> transfo = new ArrayList<IVendeurChocoBis>();
		Double[][] PrixVente = new Double[3][6];
		for (Acteur a : acteurs) {
			if (a instanceof IVendeurChocoBis) {
				transfo.add((IVendeurChocoBis) a);

			}
		}
		double[] m = new double[6];
		for (int i = 0; i < 6; i++) {
			while (m[i] != 1) {

				ArrayList<Double> prix;
				prix = new ArrayList<Double>();
				for (int j = 0; j < transfo.size(); j++) {
					prix.add(transfo.get(j).getPrix().getPrixProduit(demande[i], i));
					PrixVente[j][i] = transfo.get(j).getPrix().getPrixProduit(demande[i], i);
				}

				listeT = listeTriee(prix);

				if (Stock.get(listeT.indexOf(0)).get(i) >= 0.6 * demande[i]) {
					commandeFinale.get(listeT.indexOf(0)).set(i, (((int) 0.6 * demande[i])));
					m[i] += 0.6;
					if (Stock.get(listeT.indexOf(1)).get(i) >= 0.3 * demande[i]) {
						commandeFinale.get(listeT.indexOf(1)).set(i, ((int) 0.3 * demande[i]));
						m[i] += 0.3;
						if (Stock.get(listeT.indexOf(2)).get(i) >= 0.1 * demande[i]) {
							commandeFinale.get(listeT.indexOf(2)).set(i, ((int) (0.1 * demande[i])));
							m[i] += 0.1;
						} else {
							commandeFinale.get(listeT.indexOf(2)).set(i, ((int) (Stock.get(listeT.indexOf(2)).get(i))));
							m[i] = 1;
						}
					} else {
						commandeFinale.get(listeT.indexOf(1)).set(i, ((int) (Stock.get(listeT.indexOf(1)).get(i))));
						m[i] += Stock.get(listeT.indexOf(1)).get(i) / demande[i];
						if (Stock.get(listeT.indexOf(2)).get(i) >= (1 - m[i]) * demande[i]) {
							commandeFinale.get(listeT.indexOf(2)).set(i, ((int) ((1 - m[i]) * demande[i])));
						} else {
							commandeFinale.get(listeT.indexOf(2)).set(i, ((int) (Stock.get(listeT.indexOf(2)).get(i))));
							m[i] = 1;
						}
					}
				}
			}
		}
		this.journal.ajouter("CONTRAT :");
		this.journal.ajouter("");
		for (ArrayList<Integer> l : commandeFinale) {
			this.journal.ajouter("Tablettes MQ : " + l.get(4) + "; Tablettes HQ : " + l.get(5) + "; Confiseries MQ : "
					+ l.get(1) + "; Confiseries MQ : " + l.get(2));
			this.journal.ajouter("");
			this.stocks[1].setValeur(this, this.stocks[1].getValeur() + l.get(4));
			this.stocks[2].setValeur(this, this.stocks[2].getValeur() + l.get(5));
			this.stocks[4].setValeur(this, this.stocks[4].getValeur() + l.get(1));
			this.stocks[5].setValeur(this, this.stocks[5].getValeur() + l.get(2));
			this.solde.setValeur(this,
					this.solde.getValeur() - Prix.get(4).getPrixProduit(l.get(4), 4)
							- Prix.get(5).getPrixProduit(l.get(5), 5) - Prix.get(1).getPrixProduit(l.get(1), 1)
							- Prix.get(2).getPrixProduit(l.get(2), 2));
			stock.ajouter(l.get(4), 1);
			stock.ajouter(l.get(5), 2);
			stock.ajouter(l.get(1), 4);
			stock.ajouter(l.get(2), 5);
		}
		double[] PrixMoyenVente = new double[6];
		for (int i = 0; i < 6; i++) {
			PrixMoyenVente[i] = (PrixVente[0][i] + PrixVente[1][i] + PrixVente[3][i]) / 3;
		}
		this.changerPrix(PrixMoyenVente);
		return commandeFinale;
	}

	public ArrayList<Integer> listeTriee(ArrayList<Double> prix) {
		ArrayList<Double> copie = new ArrayList<Double>();
		for (int i = 0; i < 3; i++) {
			copie.add(prix.get(i));
		}
		Collections.sort(copie);
		ArrayList<Integer> min = new ArrayList<Integer>();
		min.add(prix.indexOf(copie.get(0)));
		min.add(prix.indexOf(copie.get(1)));
		min.add(prix.indexOf(copie.get(2)));
		return min;
	}

	public void main(String[] Args) {
		ArrayList<GPrix2> Prix = new ArrayList<GPrix2>();
		ArrayList<ArrayList<Integer>> Stock = new ArrayList<ArrayList<Integer>>();
		Double[] interval = { 0.0, 10.0, 50.0, 100.0, 250.0, 500.0, 750.0, 1000.0 };
		Double[] prix2 = { 4.0, 3.975, 3.95, 3.9, 3.875, 3.85, 3.825, 3.8 };
		Double[] prix3 = { 4.5, 3.975, 3.55, 3.2, 3.125, 3.12, 3.36, 3.7 };
		Double[] prix4 = { 4.7, 3.12, 3.74, 3.3, 3.147, 3.85, 3.52, 3.82 };
		ArrayList<Double[]> p = new ArrayList<Double[]>();
		p.add(prix2);
		p.add(prix3);
		p.add(prix4);
		p.add(prix2);
		p.add(prix3);
		p.add(prix4);
		ArrayList<Double[]> i = new ArrayList<Double[]>();
		i.add(interval);
		i.add(interval);
		i.add(interval);
		i.add(interval);
		i.add(interval);
		i.add(interval);
		GPrix2 prix = new GPrix2(i, p);

	}

	/*
	 * public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix,
	 * ArrayList<ArrayList<Integer>> Stock) { ArrayList<ArrayList<Integer>>
	 * commandeFinale; commandeFinale = new ArrayList<ArrayList<Integer>>();
	 * 
	 * System.out.println("appelee ..."); double[]demande; demande = new double[6];
	 * demande[0]=0; demande[1]=39834; demande[2]=17500; demande[3]=0;
	 * demande[4]=29167; demande[5]=12500; double[] p; p= new double[3]; double
	 * somme;
	 * 
	 * for (int i=0;i<6;i++){ somme = 0; for (int h=0;h<3;h++) { somme +=
	 * stock[h][i]; } for (int j=0;j<3;j++) { p[i]=stock[j][i]/somme;
	 * if(p[i]*demande[i]<= stock[j][i]){ commandeFinale.get(j).set(i,
	 * ((int)(p[i]*demande[i]))); } else {
	 * commandeFinale.get(j).set(i,((int)(stock[0][i]))); }
	 * 
	 * } }
	 * 
	 * return commandeFinale; }
	 */

	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		//ATENTION ORDRE DE LIVRAISON DIFFERENT A REVOIR
		this.stocks[1].setValeur(this, this.stocks[1].getValeur()+livraison.get(4));
		this.stocks[2].setValeur(this, this.stocks[2].getValeur()+livraison.get(5));
		this.stocks[4].setValeur(this, this.stocks[4].getValeur()+livraison.get(1));
		this.stocks[5].setValeur(this, this.stocks[5].getValeur()+livraison.get(2)); 
		stock.ajouter(livraison.get(4),1);
		stock.ajouter(livraison.get(5),2);
		stock.ajouter(livraison.get(1),4);
		stock.ajouter(livraison.get(2),5);			
		solde.setValeur(this,solde.getValeur()+paiement);
		if(Monde.LE_MONDE.getStep()%12==0) {
			this.journal.ajouter("L'équipe 1 a acheté :"+ livraison.get(4) + "TM");
			this.journal.ajouter("L'équipe 1 a acheté :"+ livraison.get(5) + "TH");
			this.journal.ajouter("L'équipe 1 a acheté :"+ livraison.get(1) + "CM");
			this.journal.ajouter("L'équipe 1 a acheté :"+ livraison.get(2) + "CH");
		}

	}

	@Override
	public double[] getPrix() {
		// TODO Auto-generated method stub
		return new double[] { 0, this.PrixChocoMdG.getValeur(), this.PrixChocoHdG.getValeur(), 0,
				this.PrixConfMdG.getValeur(), this.PrixConfHdG.getValeur() };
	}

	/**
	 *
	 * @param double[]
	 *            PrixAchat (tableau des prix d'achats (1x6)
	 *            chocoBdG,chocoMdG,chocoHdG,confBdG,confMdG,confHdG )
	 * 
	 * @return change les prix de ventes de façon à avoir une marge de 16%
	 */
	private void changerPrix(double[] PrixAchat) {
		this.PrixChocoMdG.setValeur(this, PrixAchat[1] * 1.16);
		this.PrixChocoHdG.setValeur(this, PrixAchat[2] * 1.16);
		this.PrixConfMdG.setValeur(this, PrixAchat[4] * 1.16);
		this.PrixConfHdG.setValeur(this, PrixAchat[5] * 1.16);
		this.journal
				.ajouter("Changement des prix : \n" + "Prix chocolat milieu de gamme = " + this.PrixChocoMdG.getValeur()
						+ "\n" + "Prix chocolat haut de gamme = " + this.PrixChocoHdG.getValeur() + "\n"
						+ "Prix confiseries milieu de gamme = " + this.PrixConfMdG.getValeur() + "\n"
						+ "Prix confiseries haut de gamme = " + this.PrixConfHdG.getValeur() + "\n");
	}
}
