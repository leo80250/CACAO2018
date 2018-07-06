package abstraction.eq5TRAN.appeldOffre;

import abstraction.fourni.Acteur;

/**
 * interface demandes ponctuelles
 **/

public interface IvendeurOccasionnelChocoTer {

    public double getReponseTer(DemandeAO d); /**  retourner Double.max_maxvalue si offre non intéressante, sinon retourner le prix**/

    public void envoyerReponseTer(Acteur acteur, int quantite, int qualite, double prix); /** choix de l'équipe à qui les distributeurs vendent
     ajouter votre nom d'équipe pour le journal, quantité en nombre de barres et prix en euros**/
}

