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
	
	public void ajouter(Lot l) {
		this.stocki.add(l);
	}
	
	public void retirer(Lot l) {
		this.stocki.remove(l);
	}
	
	public List<Lot> getStocki(){
		return this.stocki;
	}
}
