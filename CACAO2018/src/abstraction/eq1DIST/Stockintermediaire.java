package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.List;

public class Stockintermediaire {
	private List<Lot> stocki;
	
	public Stockintermediaire (List<Lot> liste) {
		this.stocki=new ArrayList<Lot>();
		for (int i=0; i<liste.size(); i++) {
			this.stocki.add(liste.get(i));
		}
	}
	
	public Stockintermediaire (int a, int n) {
		List<Lot> liste = new ArrayList<Lot>();
		liste.add(new Lot(a,Type.values()[n]));
		this.stocki=liste;
	}
	
	public void ajouter(Lot l) {
		this.stocki.add(l);
	}
	
	public int total() {
		int res=0;
		for(int i=0; i<this.stocki.size();i++) {
			res = res+this.stocki.get(i).getQuantite();
		}
		return res;
	}
	
	public void retirer(Lot l) {
		this.stocki.remove(l);
	}
	
	public List<Lot> getStocki(){
		return this.stocki;
	}
}
