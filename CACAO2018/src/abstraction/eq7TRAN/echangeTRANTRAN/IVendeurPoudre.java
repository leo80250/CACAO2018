package abstraction.eq7TRAN.echangeTRANTRAN;

/**
 * Interface IAcheteurFeve
 * @author Léo Fargeas, Margaux Grand, Juliette Gorline, Mickaël Abdealy, Maelle Boulard
 */

public interface IVendeurPoudre { 
	// vendeur.nomMethode
	// L'acheteur récupère le catalogue du vendeur (offre que tout le monde voit)
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur);
	// L'acheteur récupère un devis du vendeur (négociation privée)
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur);
	// L'acheteur indique sa réponse au vendeur
	public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur);
	// L'acheteur récupère ce qu'il a réellement reçu du vendeur (peut y avoir des non livraisons etc...)
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur);
}