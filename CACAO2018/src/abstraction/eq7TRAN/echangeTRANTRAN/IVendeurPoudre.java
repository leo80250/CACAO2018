package abstraction.eq7TRAN.echangeTRANTRAN;

// Léo Fargeas, Margaux Grand, Juliette Gorline, Mickaël Abdealy, Maelle Boulard

public interface IVendeurPoudre { 
	// vendeur.nomMethode
	// L'acheteur récupère le catalogue du vendeur (offre que tout le monde voit)
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur);
	// L'acheteur récupère un devis du vendeur (négociation privée)
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis);
	// L'acheteur indique sa réponse au vendeur
	public void sendReponsePoudre(ContratPoudre[] devis);
	// L'acheteur récupère ce qu'il a réellement reçu du vendeur (peut y avoir des non livraisons etc...)
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat);
}