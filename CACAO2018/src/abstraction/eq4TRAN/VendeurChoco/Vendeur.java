package abstraction.eq4TRAN.VendeurChoco;

/**
 * 
 * @author Etienne
 *
 */
public class Vendeur {
	/*
	 * classe définissant les méthodes nécessaires à l'interface IVendeur Choco
	 */
	
	private int qBonbonBQ;
	private int qBonbonMQ;
	private int qBonbonHQ;
	private int qTabletteBQ;
	private int qTabletteMQ;
	private int qTabletteHQ;
	
	public Vendeur(int qBBQ, int qBMQ, int qBHQ, int qTBQ, int qTMQ, int qTHQ) {
		qBonbonBQ = (qBBQ>=0) ? qBBQ : 0;
		qBonbonMQ = (qBMQ>=0) ? qBMQ : 0;
		qBonbonHQ = (qBHQ>=0) ? qBHQ : 0;
		qTabletteBQ = (qTBQ>=0) ? qTBQ : 0;
		qTabletteMQ = (qTMQ>=0) ? qTMQ : 0;
		qTabletteHQ = (qTHQ>=0) ? qTHQ : 0;
	}

	public int getqBonbonBQ() {
		return qBonbonBQ;
	}

	public void setqBonbonBQ(int qBonbonBQ) {
		this.qBonbonBQ = qBonbonBQ;
	}

	public int getqBonbonMQ() {
		return qBonbonMQ;
	}

	public void setqBonbonMQ(int qBonbonMQ) {
		this.qBonbonMQ = qBonbonMQ;
	}

	public int getqBonbonHQ() {
		return qBonbonHQ;
	}

	public void setqBonbonHQ(int qBonbonHQ) {
		this.qBonbonHQ = qBonbonHQ;
	}

	public int getqTabletteBQ() {
		return qTabletteBQ;
	}

	public void setqTabletteBQ(int qTabletteBQ) {
		this.qTabletteBQ = qTabletteBQ;
	}

	public int getqTabletteMQ() {
		return qTabletteMQ;
	}

	public void setqTabletteMQ(int qTabletteMQ) {
		this.qTabletteMQ = qTabletteMQ;
	}

	public int getqTabletteHQ() {
		return qTabletteHQ;
	}

	public void setqTabletteHQ(int qTabletteHQ) {
		this.qTabletteHQ = qTabletteHQ;
	}

	public GQte getStock() {
		return new GQte(getqBonbonBQ(), getqBonbonMQ(), getqBonbonHQ(), getqTabletteBQ(), getqTabletteMQ(), getqTabletteHQ());
	}
	
	public GPrix getPrix() {
		float[] intervalles = new float[10];
		float[] prix = new float[10];
		return new GPrix(intervalles, prix);
	}
	
	
	public GQte getLivraison(GQte[] commandes) {
		GQte commande1 = commandes[0];
		GQte commande2 = commandes[1];
		return commande1;
	}
}
