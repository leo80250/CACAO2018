package abstraction.eq7TRAN;

public interface IVendeurPoudre {
	
	public ContratPoudre[] getOffresPubliques();
	public void sendOffresPubliques(ContratPoudre[] offres);
	
	public ContratPoudre[] getDemandePrivee();
	public void sendDemandePrivee(ContratPoudre[] demandes);
	
	public ContratPoudre[] getOffreFinale();
	public void sendOffreFinale(ContratPoudre[] contrats);
	
	public ContratPoudre[] getResultVentes();
	public void sendResultVentes(ContratPoudre[] contrats);	
}