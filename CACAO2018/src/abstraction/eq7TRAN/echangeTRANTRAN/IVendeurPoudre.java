package abstraction.eq7TRAN.echangeTRANTRAN;

/**
 * Interface IAcheteurFeve
 * @author Léo Fargeas, Margaux Grand, Juliette Gorline, Mickaël Abdealy, Maelle Boulard
 */

public interface IVendeurPoudre { 
	// vendeur.nomMethode => c'est l'acheteur qui appelle les méthodes du vendeur
	
	// L'acheteur récupère le catalogue du vendeur (offre que tout le monde voit)
	// L'acheteur regarde si des produits l'intéresse puis formule sa demande dans un nouveau ContratPoudre
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur);
	
	// L'acheteur envoie sa demande créée juste avant au vendeur. L'acheteur reçoit la réponse du vendeur (le devis)
	// Si le devis ne convient pas, on peut entrer en phase de négociation en réenvoyant une demande
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur);
	
	// Une fois que la phase de négociation est terminée et que l'acheteur a reçu un devis qui lui convient (ou non),
	// l'acheteur indique au vendeur qu'il passe commande en passant l'attribut "reponse" du devis à true (ou alors 
	// il abandonne et passe à false)
	// CODE VENDEUR (dans la méthode) : si la réponse est true, inscrire l'objet "devis" comme une nouvelle commande
	// CODE ACHETEUR : avant d'appeler la méthode, 
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur);
	// L'acheteur récupère ce qu'il a réellement reçu du vendeur (peut y avoir des non livraisons etc...)
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur);
}