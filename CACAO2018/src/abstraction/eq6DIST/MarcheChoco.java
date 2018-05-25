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
			
		}
	}
	
}
