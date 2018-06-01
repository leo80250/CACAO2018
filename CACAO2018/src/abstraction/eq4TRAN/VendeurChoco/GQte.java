package abstraction.eq4TRAN.VendeurChoco;
/**
 * 
 * @author Etienne
 *
 */
public class GQte {
	
	/**
	 * La classe GQte représente un nouveau type -un tableau à deux dimensions-
	 * étant utilisé pour tranmettre les quantités par produits : Bonbons puis tablettes
	 * triés par qualité croissante
	 */
	
	private int qBonbonBQ;
	private int qBonbonMQ;
	private int qBonbonHQ;
	private int qTabletteBQ;
	private int qTabletteMQ;
	private int qTabletteHQ;
	
	
	public GQte(int qBonbonBQ, int qBonbonMQ, int qBonbonHQ, int qTabletteBQ, int qTabletteMQ, int qTabletteHQ) {
		this.qBonbonBQ = (qBonbonBQ>=0) ? qBonbonBQ : 0;
		this.qBonbonMQ=(qBonbonMQ>=0) ? qBonbonMQ : 0;
		this.qBonbonHQ=(qBonbonHQ>=0) ? qBonbonHQ : 0;
		this.qTabletteBQ=(qTabletteBQ>=0) ? qTabletteBQ : 0;
		this.qTabletteMQ=(qTabletteMQ>=0) ? qTabletteMQ : 0;
		this.qTabletteHQ=(qTabletteHQ>=0) ? qTabletteHQ : 0;
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
	
	
}
