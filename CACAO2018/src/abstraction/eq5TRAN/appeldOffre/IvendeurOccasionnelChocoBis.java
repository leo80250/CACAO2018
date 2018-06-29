package abstraction.eq5TRAN.appeldOffre;

/**
 * interface demandes ponctuelles
 **/

public interface IvendeurOccasionnelChocoBis {
	
    public int getReponseBis(DemandeAO d); /**  retourner Double.max_maxvalue si offre non intéressante, sinon retourner le prix**/
	
    public void envoyerReponseBis(int quantite, int qualite, int prix); /** choix de l'équipe à qui les distributeurs vendent**/
}

