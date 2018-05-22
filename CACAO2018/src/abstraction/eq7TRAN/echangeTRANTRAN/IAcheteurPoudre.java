package abstraction.eq7TRAN.echangeTRANTRAN;

// Léo Fargeas, Maelle Boulard

public interface IAcheteurPoudre {	
	// Récupérer qui vend quoi, en quelle quantité et qualité et à quel prix auprès du régulateur 
	public ContratPoudre[] getOffresPubliques();
	// Pour envoyer ce que l'on souhaite acheter, en quelle quantité... au régulateur
	public void sendOffresPubliques(ContratPoudre[] offres);
	
	// Pour récupérer un devis auprès d'un autre TRAN
	public ContratPoudre[] getDemandePrivee();
	// Pour envoyer un devis
	public void sendDemandePrivee(ContratPoudre[] demandes);
	
	// Pour récupérer le contrat final
	public ContratPoudre[] getOffreFinale();
	public void sendOffreFinale(ContratPoudre[] contrats);
	
	// Pour récupérer le résultat de l'échange
	public ContratPoudre[] getResultAchats();
	public void sendResultAchats(ContratPoudre[] contrats);	
}