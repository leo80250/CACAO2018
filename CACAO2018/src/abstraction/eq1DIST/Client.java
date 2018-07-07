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

	public Client(double[][] P, Journal journal) {
		if (P.length == 3 && P[0].length == 6) {
			this.PartdeMarche = P;
		} else {
			this.PartdeMarche = new double[3][6];
		}
		this.journal = journal;
		this.DemandeChocoBdG = new Indicateur("DemandeChocoBdG", this, 0.0);
		this.DemandeChocoMdG = new Indicateur("DemandeChocoMdG", this, 0.0);
		this.DemandeChocoHdG = new Indicateur("DemandeChocoHdG", this, 0.0);
		this.DemandeConfBdG = new Indicateur("DemandeConfBdG", this, 0.0);
		this.DemandeConfMdG = new Indicateur("DemandeConfMdG", this, 0.0);
		this.DemandeConfHdG = new Indicateur("DemandeConfHdG", this, 0.0);
		Monde.LE_MONDE.ajouterIndicateur(DemandeChocoBdG);
		Monde.LE_MONDE.ajouterIndicateur(DemandeChocoMdG);
		Monde.LE_MONDE.ajouterIndicateur(DemandeChocoHdG);
		Monde.LE_MONDE.ajouterIndicateur(DemandeConfBdG);
		Monde.LE_MONDE.ajouterIndicateur(DemandeConfMdG);
		Monde.LE_MONDE.ajouterIndicateur(DemandeConfHdG);
	}

	/**
	 * Méthode à appeler après un ModifierPartsDeMarche ou ModifierPartsDeMarchePrix
	 * 
	 * @param int
	 *            j (colonne), double (Valeur de la part de marché avant
	 *            modification), double (Valeur de la part de marché après
	 *            modification), ArrayList (les distributeurs)
	 * 
	 * @return modifie le tableau des parts de marché: la part de marché qui a
	 *         diminué chez un acteur lors de la modification est répartie entre
	 *         tous les autres, de facon à garder un total égal à 1.
	 * 
	 */

	private void EquilibrerPartsDeMarche(int j, double ValeurInitiale, double ValeurFinale,
			ArrayList<InterfaceDistributeurClient> Distributeurs) {
		double ecart = (ValeurInitiale - ValeurFinale) / Distributeurs.size();
		for (int i = 0; i < Distributeurs.size(); i++) {
			this.PartdeMarche[i][j] += (double) Math.round(ecart * 1000) / 1000;
		}
	}

	/**
	 *
	 * @param int
	 *            i (ligne), int j (colonne), double (commande envoyé pour 1
	 *            catégorie de produit), double (réponse à cette commande par le
	 *            distributeur)
	 * 
	 * @return modifie le tableau des parts de marché : la part de marché [i][j]
	 *         prend la valeur "valeur"
	 * 
	 */

	private void ModifierPartsDeMarche(int i, int j, double commande, double reponse) {
		if (commande != 0) {
			double valeur = this.getValeur(i, j) * (1 - (commande - reponse) / commande);
			if (valeur < 0) {
				this.PartdeMarche[i][j] = 0;
			} else {
				this.PartdeMarche[i][j] = (double) Math.round(valeur * 1000) / 1000;
			}
		}
	}

	/**
	 *
	 * @param int
	 *            i (ligne), int j (colonne), double (prix pour 1 catégorie de
	 *            produit) , ArrayList (les distributeurs)
	 * 
	 * @return modifie le tableau des parts de marché en fonction par comparaison
	 *         des prix proposés. Calcule le prix moyens en fonction du nombre
	 *         d'acteurs et des prix proposés par chaque, puis vérifie si le prix
	 *         proposé par un acteur est supérieur ou non à ce prix moyen Si le prix
	 *         est supérieur, la part de marché diminue selon des formules
	 *         consultables dans le corps de la fonction.
	 * 
	 */
	private void ModifierPartsDeMarchePrix(int i, int j, double prix,
			ArrayList<InterfaceDistributeurClient> Distributeurs) {
		double PrixMoyen = 0;
		for (InterfaceDistributeurClient a : Distributeurs) {
			PrixMoyen += a.getPrix()[j];
		}
		PrixMoyen = PrixMoyen / Distributeurs.size();
		double facteur = (Distributeurs.get(i).getPrix()[j] - PrixMoyen)
				/ (Distributeurs.get(i).getPrix()[j] + PrixMoyen);
		if (facteur < 0) {
			facteur = 0;
		}
		this.PartdeMarche[i][j] = (double) Math.round((1 - 0.3 * facteur) * this.PartdeMarche[i][j] * 1000) / 1000;

	}
	
	/**
	 *
	 * @param InterfaceDistributeurClient Distributeur, int elt (position dans la liste des distributeurs récupérés)
	 * 
	 * @return Modifie l'ordre des parts de marchés pour éviter les conflits
	 * 
	 */
	private void OrdonnerPartsDeMarche(InterfaceDistributeurClient Distributeur, int elt) {
		if (Distributeur.getNom()=="Eq1DIST") {
			double[] copie1 = new double[6];
			copie1 = this.PartdeMarche[elt];
			this.PartdeMarche[elt]= this.PartdeMarche[0];
			this.PartdeMarche[0]=copie1;
		}
		else if (Distributeur.getNom()=="Eq6DIST") {
			double[] copie1 = new double[6];
			copie1 = this.PartdeMarche[elt];
			this.PartdeMarche[elt]= this.PartdeMarche[1];
			this.PartdeMarche[1]=copie1;
		}
		else {
			double[] copie1 = new double[6];
			copie1 = this.PartdeMarche[elt];
			this.PartdeMarche[elt]= this.PartdeMarche[2];
			this.PartdeMarche[2]=copie1;
			}
		}

	/**
	 *
	 * @param int
	 *            i (ligne), int j (colonne)
	 * 
	 * @return donne la valeur de la part de marché [i]{j]
	 * 
	 */

	public double getValeur(int i, int j) {

		return this.PartdeMarche[i][j];
	}

	private void ChangementPartdeMarche(int i, int j, GrilleQuantite commande, GrilleQuantite reponse,
			InterfaceDistributeurClient acteur, ArrayList<InterfaceDistributeurClient> Distributeurs) {
		// modification des parts de marché en fonction des commandes
		double ValeurInitiale0 = this.getValeur(i, j);
		this.ModifierPartsDeMarche(i, j, commande.getValeur(j), reponse.getValeur(j));
		this.EquilibrerPartsDeMarche(j, ValeurInitiale0, this.getValeur(i, j), Distributeurs);
		// modification des parts de marché en fonction des prix
		ValeurInitiale0 = this.getValeur(i, j);
		this.ModifierPartsDeMarchePrix(i, j, acteur.getPrix()[j], Distributeurs);
		this.EquilibrerPartsDeMarche(j, ValeurInitiale0, this.getValeur(i, j), Distributeurs);
	}

	/**
	 *
	 * @param int[]
	 *            h (tableau de la demande totale théorique de la période
	 *            considérée) ,int i (ligne)
	 * 
	 * @return donne la commande réelle correspondant au distributeur i à partir du
	 *         tableau des demandes totales théoriques les valeurs de la commande
	 *         réelle sont des random de +/- 30% des valeurs théoriques contenues
	 *         dans h
	 */

	private int[] commande(int[] h, int i) {
		int[] cm = new int[6];
		for (int j = 0; j <= 5; j++) {
			cm[j] = (int) (0.7 * h[j] * this.getValeur(i, j) + (Math.random() * 0.6 * h[j] * this.getValeur(i, j)));
		}
		return cm;
	}

	public void next() {
		this.journal.ajouter("------------------------------ Période n°" + Monde.LE_MONDE.getStep()
				+ " ------------------------------");
		// a = tableau de la demande théorique en chocolat par rapport aux périodes de
		// l'année
		// ligne 1 : choco bas de gamme
		// ligne 2 : choco milieu de gamme
		// ligne 3 : choco haut de gamme
		int[][] a = {
				{ 1630, 966, 983, 1448, 1035, 1035, 1655, 1379, 862, 862, 862, 862, 862, 862, 862, 862, 862, 862, 1379,
						2414, 1552, 1035, 1310, 1931 },
				{ 11833, 12744, 12971, 19115, 13654, 13654, 21846, 18205, 11378, 11378, 11378, 11378, 11378, 11378,
						11378, 11378, 11378, 11378, 18205, 31859, 20481, 13654, 17295, 25487 },
				{ 1569, 1689, 1720, 2534, 1810, 1810, 2896, 2413, 1508, 1508, 1508, 1508, 1508, 1508, 1508, 1508, 1508,
						1508, 2413, 4223, 2715, 1810, 2293, 3379 } };

		// b = tableau de la demande théorique en confiseries par rapport aux périodes
		// de l'année
		// ligne 1 : confiseries bas de gamme
		// ligne 2 : confiseries milieu de gamme
		// ligne 3 : confiseries haut de gamme
		int[][] b = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 9683, 10428, 10614, 15642, 11173, 11173, 17876, 14897, 9311, 9311, 9311, 9311, 9311, 9311, 9311, 9311,
						9311, 9311, 14897, 26069, 16759, 11173, 14152, 20856 },
				{ 2074, 2234, 2274, 3351, 2394, 2394, 3830, 3191, 1995, 1995, 1995, 1995, 1995, 1995, 1995, 1995, 1995,
						1995, 3191, 5585, 3590, 2394, 3032, 4468 } };

		int periode = Monde.LE_MONDE.getStep() % 24; // on récupère la période modulo 24 pour récupérer les valeurs
														// théoriques dans a et b
		// h = tableau de la commande totale théorique de la période
		// ligne 1 : choco BdG , choco MdG, choco HdG
		// ligne 2 : confiseries BdG , confiseries MdG, confiseries HdG
		int[] h = { a[0][periode], a[1][periode], a[2][periode], b[0][periode], b[1][periode], b[2][periode] };
		

		ArrayList<Acteur> Acteurs = Monde.LE_MONDE.getActeurs();
		ArrayList<InterfaceDistributeurClient> Distributeurs = new ArrayList<InterfaceDistributeurClient>();
		// récupération des instances de distributeurs
		int elt =0;
		for (Acteur c : Acteurs) {
			if (c instanceof InterfaceDistributeurClient) {
				Distributeurs.add((InterfaceDistributeurClient) c);
//				if (Monde.LE_MONDE.getStep()==1) {
//					System.out.println("c");
//					this.OrdonnerPartsDeMarche((InterfaceDistributeurClient)c , elt);
//				}
				elt+=1;
			}
		}

		int[] cm = this.commande(h, 0); // commande pour l'eq6 (Mousquetaire)
		GrilleQuantite CommandeMousquetaire = new GrilleQuantite(cm);
		int[] cas = this.commande(h, 1); // commande pour l'eq1 (Casino)
		GrilleQuantite CommandeCasino = new GrilleQuantite(cas);
		int[] autre = this.commande(h, 2); // commande pour le distributeur fictif

		DemandeChocoBdG.setValeur(this, cm[0] + cas[0] + autre[0] + 0.0);
		DemandeChocoMdG.setValeur(this, cm[1] + cas[1] + autre[1] + 0.0);
		DemandeChocoHdG.setValeur(this, cm[2] + cas[2] + autre[2] + 0.0);
		DemandeConfBdG.setValeur(this, cm[3] + cas[3] + autre[3] + 0.0);
		DemandeConfMdG.setValeur(this, cm[4] + cas[4] + autre[4] + 0.0);
		DemandeConfHdG.setValeur(this, cm[5] + cas[5] + autre[5] + 0.0);
		
		for (int i = 0; i < Distributeurs.size(); i++) {
			if (Distributeurs.get(i).getNom() == "Eq6DIST") {
				this.journal.ajouter("");
				this.journal.ajouter("• Les Mousquetaires •");
				this.journal.ajouter("");
				this.journal.ajouter("- La demande pour le distributeur Mousquetaire est : "
						+ CommandeMousquetaire.toString() + "\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Mousquetaire = Distributeurs.get(i);
				GrilleQuantite ReponseMousquetaire = Mousquetaire.commander(CommandeMousquetaire);
				this.journal.ajouter("- Les magasins Mousquetaire ont vendu (effectivement) : "
						+ ReponseMousquetaire.toString() + "\n");
				this.journal.ajouter("");

				for (int j = 0; j <= 5; j++) {
					this.ChangementPartdeMarche(0, j, CommandeMousquetaire, ReponseMousquetaire, Mousquetaire,
							Distributeurs);
				}

				this.journal.ajouter("- Les parts de marché des magasins Mousquetaire sont désormais : "
						+ this.getValeur(0, 0) + "% sur les Tablettes BG ; " + this.getValeur(0, 1)
						+ "% sur les Tablettes MG ; " + this.getValeur(0, 2) + "% sur les Tablettes HG ; "
						+ this.getValeur(0, 3) + "% sur les Confiseries BG ; " + this.getValeur(0, 4)
						+ "% sur les Confiseries MG ; " + this.getValeur(0, 5) + "% sur les Confiseries HG.");
				this.journal.ajouter("");
			} else if (Distributeurs.get(i).getNom() == "Eq1DIST") {
				this.journal.ajouter("");
				this.journal.ajouter("• Casino •");
				this.journal.ajouter("");
				this.journal
						.ajouter("- La demande pour le distributeur Casino est : " + CommandeCasino.toString() + "\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Casino = Distributeurs.get(i);
				GrilleQuantite ReponseCasino = Casino.commander(CommandeCasino);
				this.journal.ajouter(
						"- Les magasins Casino ont vendu (effectivement) : " + ReponseCasino.toString() + "\n");
				this.journal.ajouter("");

				for (int j = 0; j <= 5; j++) {
					this.ChangementPartdeMarche(1, j, CommandeCasino, ReponseCasino, Casino, Distributeurs);
				}

				this.journal.ajouter("- Les parts de marché des magasins Casino sont désormais : "
						+ this.getValeur(1, 0) + "% sur les Tablettes BG ; " + this.getValeur(1, 1)
						+ "% sur les Tablettes MG ; " + this.getValeur(1, 2) + "% sur les Tablettes HG ; "
						+ this.getValeur(1, 3) + "% sur les Confiseries BG ; " + this.getValeur(1, 4)
						+ "% sur les Confiseries MG ; " + this.getValeur(1, 5) + "% sur les Confiseries HG.");
				this.journal.ajouter("");
			} else {
				GrilleQuantite CommandeAutre = new GrilleQuantite(autre);
				this.journal.ajouter("• Autres distributeurs •");
				this.journal.ajouter("");
				this.journal
						.ajouter("- La demande pour les autres distributeurs est : " + CommandeAutre.toString() + "\n");
				this.journal.ajouter("");
				InterfaceDistributeurClient Autre = Distributeurs.get(i);
				GrilleQuantite ReponseAutre = Autre.commander(CommandeAutre);
				this.journal
						.ajouter("- Les autres magasins ont vendu (effectivement) : " + ReponseAutre.toString() + "\n");
				this.journal.ajouter("");

				for (int j = 0; j <= 5; j++) {
					this.ChangementPartdeMarche(2, j, CommandeAutre, ReponseAutre, Autre, Distributeurs);
				}

				this.journal.ajouter("Les parts de marché des autres magasins sont désormais : " + this.getValeur(2, 0)
						+ "% sur les Tablettes BG ; " + this.getValeur(2, 1) + "% sur les Tablettes MG ; "
						+ this.getValeur(2, 2) + "% sur les Tablettes HG ; " + this.getValeur(2, 3)
						+ "% sur les Confiseries BG ; " + this.getValeur(2, 4) + "% sur les Confiseries MG ; "
						+ this.getValeur(2, 5) + "% sur les Confiseries HG.");
				this.journal.ajouter("");
			}
		}
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "Clients finaux";
	}

	// public static void main(String[] args) {
	// double[][] PartsdeMarche= {{0.7,0.49,0,0.42,0,0},
	// {0,0.21,0.7,0,0.7,0},
	// {0.3,0.3,0.3,0.58,0.3,0}};
	// Journal clientj = new Journal("Clients Finaux");
	// Client client=new Client(PartsdeMarche,clientj);
	//
	// int[] h= {1000,1000,0,1000,0,0};
	// int[] h0=client.commande(h, 0);
	// int[] h1=client.commande(h, 1);
	// int[] h2=client.commande(h, 2);
	// System.out.println(h0[0]+" "+h0[1]+" "+h0[2]+" "+h0[3]+" "+h0[4]+" "+h0[5]);
	// System.out.println(h1[0]+" "+h1[1]+" "+h1[2]+" "+h1[3]+" "+h1[4]+" "+h1[5]);
	// System.out.println(h2[0]+" "+h2[1]+" "+h2[2]+" "+h2[3]+" "+h2[4]+" "+h2[5]);
	// }
}