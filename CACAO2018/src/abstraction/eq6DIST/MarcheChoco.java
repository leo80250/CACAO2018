package abstraction.eq6DIST; // Karel Kédémos , Victor Signes

public class MarcheChoco {
	private double[][] stock;
	private double[][] prix;
	private double[][] commande;
	
	
	public MarcheChoco(int[] stock, double[][] prix) {
		this.stock = ITransformateur.getStock();
		this.prix = ITransformateur.getPrix();
		this.commande = IAcheteurChoco.getCommande(this.prix,this.stock);
		
		final int i=0;
		
		while (this.stock[i]==0 && i<this.stock.length) {
			i++;
		}
		
		if (i!=this.stock.length) {
			
		}
		
		for (int i=0;i<this.stock.length;i++) {
			if (this.stock[i]!=0) {
				for 
				
			}
		}
	}
}	
