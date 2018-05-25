package abstraction.eq6DIST; // Karel Kédémos , Victor Signes, Léopold Petitjean
import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheChoco {
	private double[][] stock;
	private double[][] prix;
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
	
	for (Acteur i : this.distributeurs) {
		
	}	
	}
	
}
