package abstraction.eq5TRAN.appeldOffre;

/**
 * interface demandes ponctuelles
 **/

public interface IvendeurOccasionnelChoco {

    public double getReponse(DemandeAO d); /** retourner Double.max_maxvalue si offre non intéressante, sinon retourner le prix**/

    public void envoyerReponse(double quantite, int qualite, int prix); /** choix de l'équipe à qui les distributeurs vendent**/
}

