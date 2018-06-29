package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	private List<Stockintermediaire> stock;
	
	public Stock(Stockintermediaire a,Stockintermediaire b, Stockintermediaire c, Stockintermediaire d, Stockintermediaire e,Stockintermediaire f) {
		this.stock= new ArrayList<Stockintermediaire>();
		this.stock.add(a);
		this.stock.add(b);
		this.stock.add(c);
		this.stock.add(d);
		this.stock.add(e);
		this.stock.add(f);
	}
	
	public void ajouter(Lot e) {
		int i = e.getQualite().ordinal(); //on recupere la qualite de notre lot
		this.stock.get(i).ajouter(e); // on ajoute le lot dans l'une des 6 listes attendues
	}
	
	public void ajouter(int quantite, int qualite) {
		
		Lot e = new Lot(quantite,Type.values()[qualite-1]);
		this.ajouter(e);
	}
	
	public void retirer(int qualite, int quantite) {
		if(this.stock.get(qualite-1)!=null) {
			while (this.stock.get(qualite-1).getStocki().get(0).getQuantite()<=quantite) {
				this.stock.get(qualite-1).getStocki().remove(0);
			}
			this.stock.get(qualite-1).getStocki().get(0).setQuantite(this.stock.get(qualite-1).getStocki().get(0).getQuantite()-quantite);
		}
	}
	
}
