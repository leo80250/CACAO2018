package abstraction.eq6DIST; // Karel Kédémos , Victor Signes, Léopold Petitjean
import java.util.ArrayList;

import abstraction.eq4TRAN.IVendeurChoco;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GQte;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheChoco  {
	private ArrayList<GQte> stock;
	private ArrayList<GPrix> prix;
	private ArrayList <Acteur> distributeurs;
	private ArrayList <Acteur> transformateurs;
	
	public MarcheChoco() {
		
	this.distributeurs= new ArrayList<Acteur>();
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq6DIST")));
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq1DIST")));
	this.transformateurs= new ArrayList<Acteur>();
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq4TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq5TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq7TRAN"));
	this.stock= new ArrayList <GQte>();
	this.prix= new ArrayList <GPrix>();
	
	
	for (Acteur i : this.transformateurs) {
		IVendeurChoco ibis= (IVendeurChoco) i;
		this.prix.add(ibis.getPrix());
		this.stock.add(ibis.getStock());
			
	}	
	}
	public void next() {
		MarcheChoco MC = new MarcheChoco();
		ArrayList<GQte> commande=  new ArrayList<GQte>();
		for (Acteur i : MC.distributeurs) {
			IAcheteurChoco ibis = (IAcheteurChoco) i;
			commande.add(ibis.getCommande(MC.prix, MC.stock));
		}
		ArrayList<ArrayList<GQte>> livraison = new ArrayList<ArrayList<GQte>>();
		for(int j =0; j<3;j++) {
			ArrayList<GQte> Livraisoni =new ArrayList<GQte>(); 
			for (int i=0 ; i<commande.size() ; i++) {
				Livraisoni.add(commande.get(i));
			}
			livraison.add(Livraisoni);		
		}
		int l=0;
		for (Acteur i : MC.transformateurs)	{
			IVendeurChoco ibis = (IVendeurChoco) i;
			//ArrayList<GQte> =ibis.getLivraison(livraison.get(l));
			l++;
		}
	}
	
}
