package abstraction.eq7TRAN.echangeTRANTRAN;

// Léo Fargeas, Margaux Grand, Juliette Gorline, Mickaël Abdealy, Maelle Boulard

public interface IVendeurPoudre { 
	// Le vendeur partage à tout le monde son catalogue
	public void sendCatalogue(ContratPoudre[] offres);
	// L'acheteur récupère le catalogue du vendeur (offre que tout le monde voit)
	public ContratPoudre[] getCatalogue(IAcheteurPoudre acheteur);
	// L'acheteur récupère un devis du vendeur (négociation privée)
	public ContratPoudre[] getDevis(ContratPoudre[] devis);
	// L'acheteur envoie au vendeur s'il signe ou pas le devis
	public ContratPoudre[] sendReponseDevis(ContratPoudre[] devis, boolean reponse);
	// L'acheteur récupère ce qu'il a réellement reçu du vendeur (peut y avoir des non livraisons etc...)
	public ContratPoudre[] getEchangeFinal(ContratPoudre[] contrat);
}