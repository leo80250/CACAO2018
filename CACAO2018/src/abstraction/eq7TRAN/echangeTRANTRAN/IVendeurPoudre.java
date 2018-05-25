package abstraction.eq7TRAN.echangeTRANTRAN;

// Léo Fargeas, Margaux Grand, Juliette Gorline, Mickaël Abdealy, Maelle Boulard

public interface IVendeurPoudre { 
	// this.nomMethode
	// Le vendeur partage à tout le monde son catalogue
	public void sendCataloguePoudre(ContratPoudre[] offres);
	
	
	
	// vendeur.nomMethode
	// L'acheteur récupère le catalogue du vendeur (offre que tout le monde voit)
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur);
	// L'acheteur récupère un devis du vendeur (négociation privée)
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis);
	// L'acheteur envoie au vendeur s'il signe ou pas le devis
	// Si le devis est renvoyé en négociation, on change le prix et on laisse la réponse nulle
	// Si le devis est signé, on met true
	// Sinon, on met false
	public ContratPoudre[] sendReponseDevisPoudre(ContratPoudre[] devis);
	// L'acheteur récupère ce qu'il a réellement reçu du vendeur (peut y avoir des non livraisons etc...)
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat);
}