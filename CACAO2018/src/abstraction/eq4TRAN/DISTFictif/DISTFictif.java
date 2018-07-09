package abstraction.eq4TRAN.DISTFictif;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq6DIST.IAcheteurChocoBis;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

/**
 * 
 * @author Etienne
 *
 */
public class DISTFictif implements Acteur, IAcheteurChocoBis{
	
	public DISTFictif() {
		Monde.LE_MONDE.ajouterActeur(this);
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "DISTFictif";
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		// Next inutile pour un acteur fictif
	}

	@Override
	public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		ArrayList<ArrayList<Integer>> commande = new ArrayList<>(3);
		ArrayList<Integer> commande1 = new ArrayList<>(Stock.get(0).size()); // en fait Stock.get(0).size()=6
		ArrayList<Integer> commande2 = new ArrayList<>(Stock.get(0).size());
		ArrayList<Integer> commande3 = new ArrayList<>(Stock.get(0).size());
		for(int i=0;i<6;i++) {
			// On achete 15% de la production de chaque transformateur
			commande1.add(i, (int)0.15*Stock.get(0).get(i));
			commande2.add(i, (int)0.15*Stock.get(1).get(i));
			commande3.add(i, (int)0.15*Stock.get(2).get(i));
		}
		commande.add(commande1);
		commande.add(commande2);
		commande.add(commande3);
		return commande;
	}

	@Override
	public void livraison(ArrayList<Integer> livraison, double paiement) {
		// TODO Auto-generated method stub
		
	}
	
}
