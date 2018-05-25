package abstraction.eq3PROD.echangesProdTransfo;

/**
 * Interface associee aux transformateurs acheteurs de feves
 * @author Grégoire
 */

public interface IAcheteurFeve {
	
	/**
	 * Informe un transformateur des offres proposees par les producteurs
	 * @param offrePublique une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves et les quantites et prix proposes, le reste des variables
	 * etant a null
	 */
	public void sendOffrePublique(Contrat[] offrePublique);

	/**
	 * Recupere les demandes specifiques du transformateur a chaque producteur
	 * @return une liste de tous les Contrat destines a un meme producteur
	 */
	public Contrat[] getDemandePrivee();
	
	/**
	 * Informe l'acteur fictif des contrats en cours (MODELOISATION)
	 */
	public void sendContratFictif();
	
	/** 
	 * Informe un transformateur des offres finales des producteurs qui lui sont destinees
	 * @param offreFinale une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves, les quantites et prix proposes, les deux acteurs de l'echange,
	 * la reponse etant a null
	 */
	public void sendOffreFinale(Contrat[] offreFinale);
	
	/**
	 * Recupere les offres de ventes destinees au producteur avec la reponse fournie
	 * @returnune une liste de Contrat correspondant aux Contrat de OffreFinale avec
	 * la reponse des transformateurs
	 * @condition La somme des depenses pour les contrats acceptes est inferieure au capital du transformateur
	 */
	public Contrat[] getResultVentes();

}
