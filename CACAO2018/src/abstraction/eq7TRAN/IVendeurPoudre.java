package abstraction.eq7TRAN;

public interface IVendeurPoudre {
	
	public ContratVendeurPoudre[] getOffresPubliques();
	public void sendOffresPubliques(ContratVendeurPoudre[] offres);
	
	public ContratVendeurPoudre[] getDemandePrivee();
	public void sendDemandePrivee(ContratVendeurPoudre[] demandes);
	
	public ContratVendeurPoudre[] getOffreFinale();
	public void sendOffreFinale(ContratVendeurPoudre[] contrats);
	
	public ContratVendeurPoudre[] getResultVentes();
	public void sendResultVentes(ContratVendeurPoudre[] contrats);
	
}
