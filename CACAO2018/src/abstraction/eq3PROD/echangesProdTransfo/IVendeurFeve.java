package abstraction.eq3PROD.echangesProdTransfo;

/**
 * Interface associee aux producteurs vendeurs de fèves
 * @author Grégoire
 */

public interface IVendeurFeve {
	
	/**
	 * Recupere les offres publiques d'un producteur 
	 * @return une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves et les quantites et prix proposes, le reste des variables
	 * etant a null
	 */
	public Contrat[] getOffrePublique();
	
	/**
	 * Informe un producteur des demandes qui lui sont destinees de la part de tous les transformateurs
	 * @param demandePrivee une liste de tous les Contrat destines a un meme producteur
	 * @condition Les prix sont a + ou - 10% du prix du marche
	 */
	public void sendDemandePrivee(Contrat[] demandePrivee);
	
	/**
	 * Recupere les offres finales d'un producteur
	 * @return une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves, les quantites et prix proposes, les deux acteurs de l'echange,
	 * la reponse etant a null
	 * @condition La somme des volumes proposes est inferieure au stock du producteur
	 */
	public Contrat[] getOffreFinale();
	
	/**
	 * Informe un producteur du resultat des ventes
	 * @param resultVentes une liste de Contrat correspondant aux Contrat de OffreFinale avec
	 * la reponse des transformateurs
	 */
	public void sendResultVentes(Contrat[] resultVentes);
	
}
