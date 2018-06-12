package abstraction.eq4TRAN.DISTFictif;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.eq6DIST.IAcheteurChoco;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

/**
 * 
 * @author Etienne
 *
 */
public class DISTFictif implements Acteur, IAcheteurChoco{
	
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
	public ArrayList<GQte> getCommande(ArrayList<GPrix> gPrix, ArrayList<GQte> stock) {
		// On commande 15% des stocks de chaque transformateur
		GQte commande1 = new GQte(stock.get(0).getqBonbonBQ()*0.15,stock.get(0).getqBonbonMQ()*0.15, stock.get(0).getqBonbonHQ()*0.15, stock.get(0).getqTabletteBQ()*0.15,stock.get(0).getqTabletteMQ()*0.15,stock.get(0).getqTabletteHQ()*0.15);
		GQte commande2 = new GQte(stock.get(1).getqBonbonBQ()*0.15,stock.get(1).getqBonbonMQ()*0.15, stock.get(1).getqBonbonHQ()*0.15, stock.get(1).getqTabletteBQ()*0.15,stock.get(1).getqTabletteMQ()*0.15,stock.get(1).getqTabletteHQ()*0.15);
		GQte commande3 = new GQte(stock.get(2).getqBonbonBQ()*0.15,stock.get(2).getqBonbonMQ()*0.15, stock.get(2).getqBonbonHQ()*0.15, stock.get(2).getqTabletteBQ()*0.15,stock.get(2).getqTabletteMQ()*0.15,stock.get(2).getqTabletteHQ()*0.15);
		ArrayList<GQte> commande = new ArrayList<GQte>();
		commande.add(commande1);
		commande.add(commande2);
		commande.add(commande3);
		return commande;
	}

	@Override
	public void livraison(GQte livraison, double d) {
		// TODO Auto-generated method stub
		// Inutile pour un acteur qui ne tient pas à jours ses stocks et a de l'argent illimité
	}
	
	
}
