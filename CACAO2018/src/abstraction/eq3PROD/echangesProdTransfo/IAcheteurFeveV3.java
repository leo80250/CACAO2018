package abstraction.eq3PROD.echangesProdTransfo;

import java.util.List;

/**
 * @deprecated
 * Interface associee aux transformateurs acheteurs de feves
 * METHODES DU POINT DE VUE DU MARCHE CENTRAL
 * @author Grégoire
 */

public interface IAcheteurFeveV3 {
	
	/**
	 * Informe un transformateur des offres proposees par les producteurs
	 * @param offrePublique une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves et les quantites et prix proposes
	 * Format des contrats en parametre :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], null[IAcheteurFeve], ven[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : stocker les ContratFeve du parametre dans les variables d'instance / les analyser
	 * @author Grégoire
	 */
	public void sendOffrePubliqueV2(List<ContratFeveV2> offrePublique);

	/**
	 * Recupere les demandes specifiques du transformateur a chaque producteur
	 * @return une liste de tous les Contrat destines a un meme producteur
	 * Format des Contrat retourne :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], VOUS[IAcheteurFeve], ven[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : renvoyer une liste des ContratFeve que VOUS demandez aux differents poducteurs
	 * @author Grégoire
	 */
	public List<ContratFeveV2> getDemandePriveeV2();
	
	/**
	 * Informe l'acteur fictif des contrats en cours (MODELISATION)
	 * Utiliser marche.getContrat()
	 * @author Grégoire
	 */
	
	public void sendContratFictifV2(List<ContratFeveV2> listContrats);
	
	/** 
	 * Informe un transformateur des offres finales des producteurs qui lui sont destinees
	 * @param offreFinale une liste de Contrat proposes par le producteur
	 * Chaque Contrat indique les qualites de feves, les quantites et prix proposes, les deux acteurs de l'echange
	 * Format des Contrat en parametre :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], VOUS[IAcheteurFeve], ven[IVendeurFeve], false[Reponse])
	 * IDEE DU CODE : stocker les ContratFeve du parametre dans les variables d'instance / les analyser
	 * @author Grégoire
	 */
	public void sendOffreFinaleV2(List<ContratFeveV2> offreFinale);
	
	/**
	 * Recupere les offres de ventes destinees au producteur avec la reponse fournie
	 * @return une une liste de Contrat correspondant aux Contrat de OffreFinale avec
	 * la reponse des transformateurs
	 * @condition La somme des depenses pour les contrats acceptes est inferieure au capital du transformateur
	 * Format des Contrat retourne :
	 * ContratFeve(1[qualite], 1[quantite], 1000[prix], VOUS[IAcheteurFeve], ven[IVendeurFeve], reponse [Reponse])
	 * IDEE DU CODE : renvoyer une liste des ContratFeve proposee par les differents producteurs avec VOTRE reponse
	 * @author Grégoire
	 * ## MaJ des stocks et des comptes ##
	 */
	public List<ContratFeveV2> getResultVentesV2();

}
