package abstraction.eq6DIST; // Karel Kédémos , Victor Signes, Léopold Petitjean
import java.util.ArrayList;

import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;


public class MarcheChoco  implements Acteur{
	private ArrayList<ArrayList<Integer>> stock;
	private ArrayList<GPrix2> prix;
	private ArrayList <Acteur> distributeurs;
	private ArrayList <Acteur> transformateurs;

	
	public MarcheChoco() {
	this.transformateurs= new ArrayList<Acteur>();

	this.distributeurs= new ArrayList<Acteur>();
	for (Acteur a : Monde.LE_MONDE.getActeurs()) {
		if (a instanceof IVendeurChocoBis) {
			this.transformateurs.add(a);
		}
		if (a instanceof IAcheteurChoco) {
			this.distributeurs.add(a);
		}
	}/*
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq6DIST")));
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq1DIST")));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq4TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq5TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq7TRAN"));*/
	//this.stock= new ArrayList <GQte>();
	//this.prix= new ArrayList <GPrix>();	
	
	for (Acteur i : this.transformateurs) {
		IVendeurChocoBis ibis= (IVendeurChocoBis) i;
		this.prix.add(ibis.getPrix());
		this.stock.add(ibis.getStock());
	}	
	}
	public void next() {

		ArrayList<ArrayList<ArrayList<Integer>>> commande=  new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (Acteur i : this.distributeurs) {
			IAcheteurChocoBis ibis = (IAcheteurChocoBis) i;
			commande.add(ibis.getCommande(this.prix, this.stock));
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> livraison = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int j =0; j<3;j++) {
			ArrayList<ArrayList<Integer>> Livraisoni =new ArrayList<ArrayList<Integer>>(); 
			/*int qBonbonBQj=0;
			int qBonbonMQj=0;
			int qBonbonHQj=0;
			int qTabletteBQj=0;
			int qTabletteMQj=0;
			int qTabletteHQj=0;*/
			for (int i=0 ; i<commande.size() ; i++) {
				ArrayList<Integer> annexe= new ArrayList<Integer>();
				annexe.add(commande.get(i).get(j).get(0));
				annexe.add(commande.get(i).get(j).get(1));
				annexe.add(commande.get(i).get(j).get(2));
				annexe.add(commande.get(i).get(j).get(3));
				annexe.add(commande.get(i).get(j).get(4));
				annexe.add(commande.get(i).get(j).get(5));
				Livraisoni.add(annexe );
			}
			livraison.add(Livraisoni);		
		}
		
		int l=0;
		ArrayList<ArrayList<ArrayList<Integer>>> Delivery = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (Acteur i : this.transformateurs)	{
			IVendeurChocoBis ibis = (IVendeurChocoBis) i;
			Delivery.add(ibis.getLivraison(livraison.get(l)));
			l++;
		}
		l=0;
		ArrayList<Double> paiement=new ArrayList<Double>();
		ArrayList<ArrayList<Integer>> PourDIST=new ArrayList<ArrayList<Integer>>();
		for (int j=0;j<2;j++) {
			/*int qBonbonBQj=0;
			int qBonbonMQj=0;
			int qBonbonHQj=0;
			int qTabletteBQj=0;
			int qTabletteMQj=0;
			int qTabletteHQj=0;*/
			paiement.add(0.0);
			for (int i=0; i<Delivery.size(); i++) {
				ArrayList<Integer> Deliveryj = new ArrayList<Integer>();
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(0),1));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(1),2));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(2),3));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(3),4));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(4),5));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(5),6));
				Deliveryj.add(Delivery.get(i).get(j).get(0));
				Deliveryj.add(Delivery.get(i).get(j).get(1));
				Deliveryj.add(Delivery.get(i).get(j).get(2));
				Deliveryj.add(Delivery.get(i).get(j).get(3));
				Deliveryj.add(Delivery.get(i).get(j).get(4));
				Deliveryj.add(Delivery.get(i).get(j).get(5));
				PourDIST.add(Deliveryj);
			}
			
			}
		for (Acteur i : this.distributeurs) {
			IAcheteurChocoBis ibis = (IAcheteurChocoBis) i;
			ibis.livraison(PourDIST.get(l),paiement.get(l));
			l++;
		}
		l=0;
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "MarcheChoco";
	}

}
