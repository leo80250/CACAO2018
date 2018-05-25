package abstraction.eq7TRAN.echangeTRANTRAN;


public class ContratPoudre {
	private int qualite;
	private int quantite;
	private double prix;
	private IAcheteurPoudre acheteur;
	private IVendeurPoudre vendeur;
	private boolean reponse;
	
	public ContratPoudre(int qualite, int quantite, double prix, IAcheteurPoudre acheteur,
			IVendeurPoudre vendeur, boolean reponse) {
		this.qualite = qualite;
		this.quantite = quantite;
		this.prix = prix;
		this.vendeur = vendeur;
		this.acheteur = acheteur;
		this.reponse = reponse;
	}
	public ContratPoudre() {
		this(0,0,0,null,null,false);
	}
}