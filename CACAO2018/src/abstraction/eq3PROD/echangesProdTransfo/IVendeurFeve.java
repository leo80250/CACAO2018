package abstraction.eq3PROD.echangesProdTransfo;

/**
 * Interface associee aux producteurs vendeurs de fèves
 * METHODES DU POINT DE VUE DU MARCHE CENTRAL
 * @author Grégoire
 */

public interface IVendeurFeve {
	
	/**
	 * Recupere les offres publiques d'un producteur 
	 * @return une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves et les quantites et prix proposes, le reste des variables
	 * etant a null
	 * Format des contrats retournes :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], null[IAcheteurFeve], VOUS[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : renvoyez les offres que VOUS proposez au marche
	 */
	public ContratFeve[] getOffrePublique();
	
	/**
	 * Informe un producteur des demandes qui lui sont destinees de la part de tous les transformateurs
	 * @param demandePrivee une liste de tous les Contrat destines a un meme producteur
	 * @condition Les prix sont a + ou - 10% du prix du marche
	 * Format des contrats en parametre :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], ach[IAcheteurFeve], VOUS[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : stocker les ContratFeve du parametre dans les variables d'instance / les analyser
	 */
	public void sendDemandePrivee(ContratFeve[] demandePrivee);
	
	/**
	 * Recupere les offres finales d'un producteur
	 * @return une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves, les quantites et prix proposes, les deux acteurs de l'echange,
	 * la reponse etant a null
	 * @condition La somme des volumes proposes est inferieure au stock du producteur
	 * Format des contrats retournes :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], ach[IAcheteurFeve], VOUS[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : renvoyer VOS offres aux differents producteurs
	 */
	public ContratFeve[] getOffreFinale();
	
	/**
	 * Informe un producteur du resultat des ventes
	 * @param resultVentes une liste de Contrat correspondant aux Contrat de OffreFinale avec
	 * la reponse des transformateurs
	 * Format des contrats en parametre :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], ach[IAcheteurFeve], VOUS[IVendeurFeve], reponse[Reponse])
	 * IDEE DU CODE : stocker les ContratFeve du parametre dans les variables d'instance / les analyser
	 */
	public void sendResultVentes(ContratFeve[] resultVentes);
	
}
