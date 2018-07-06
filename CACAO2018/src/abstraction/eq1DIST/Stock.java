package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	private List<Stockintermediaire> stock;
	/*
	 * on créé un nouveau stock de facon a séparer les différents types de chocolats en 6 sous-listes et 
	 * à vendre en premier le stock le plus ancien de chacun des types
	 */
	
	public Stock (int a, int b, int c, int d, int e, int f) { // ces entiers sont les quantites initiales de notre stock
		this.stock= new ArrayList<Stockintermediaire>();
		this.stock.add(new Stockintermediaire(a));
		this.stock.add(new Stockintermediaire(b));
		this.stock.add(new Stockintermediaire(c));
		this.stock.add(new Stockintermediaire(d));
		this.stock.add(new Stockintermediaire(e));
		this.stock.add(new Stockintermediaire(f));
	}
	
	public List<Stockintermediaire> getstock(){
		return this.stock;
	}
	
	private void ajouter(Lot e) {
		int i = e.getQualite().ordinal(); //on recupere la qualite de notre lot
		this.stock.get(i).ajouter(e); // on ajoute le lot dans l'une des 6 listes attendues
	}
	
	public void ajouter(int quantite, int qualite) { //dans les faits ce sera cette fonction qui sera utilisée
		
		Lot e = new Lot(quantite,Type.values()[qualite-1]);
		this.ajouter(e);
	}
	
	public void retirer(int quantite, int qualite) { // la qualite est un entier de 1 à 6
		if(this.stock!=null) {
		if(this.stock.get(qualite-1)!=null) {
			while (this.stock.get(qualite-1).getStocki().get(0).getQuantite()<=quantite && this.stock.get(qualite-1).getStocki()!=null) {
				this.stock.get(qualite-1).getStocki().remove(0);
			}
			this.stock.get(qualite-1).getStocki().get(0).setQuantite(this.stock.get(qualite-1).getStocki().get(0).getQuantite()-quantite);
		}
		}
	}
	
	public int total() {
		int res=0;
		for (int i=0; i<7;i++) {
			res = res+this.stock.get(i).total();
		}
		return res;
	}
}
