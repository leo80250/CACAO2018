package abstraction.eq6DIST; 
/**
 * 
 * @author Léopold Petitjean
 *
 */
public interface IAcheteurChoco {
	public double[] getCommande(double[][] gPrix, double[][] stock);
	public void livraison(double[][] d);
}
