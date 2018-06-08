package abstraction.eq3PROD.echangesProdTransfo;

/**
 * Interface du marche
 * @author Grégoire
 */

public interface IMarcheFeve {
	
	/**
	 * Calcule le prix du marche a partir du next precedent (Moyenne des prix)
	 * @return une liste d'entiers correspondant aux prix du marche pour la qualite moyenne
	 */
	public double getPrixMarche();
	
	/**
	 * Informe l'acteur fictif des precedentes ventes 
	 * @return une liste de ContratFeve des ventes effectuees au next precedent
	 */
	 public ContratFeve[] getContratPrecedent();
}
