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
	
	private double qBonbonBQ;
	private double qBonbonMQ;
	private double qBonbonHQ;
	private double qTabletteBQ;
	private double qTabletteMQ;
	private double qTabletteHQ;
	
	
	public GQte(double qBonbonBQ, double qBonbonMQ, double qBonbonHQ, double qTabletteBQ, double qTabletteMQ, double qTabletteHQ) {
		this.qBonbonBQ = (qBonbonBQ>=0) ? qBonbonBQ : 0;
		this.qBonbonMQ=(qBonbonMQ>=0) ? qBonbonMQ : 0;
		this.qBonbonHQ=(qBonbonHQ>=0) ? qBonbonHQ : 0;
		this.qTabletteBQ=(qTabletteBQ>=0) ? qTabletteBQ : 0;
		this.qTabletteMQ=(qTabletteMQ>=0) ? qTabletteMQ : 0;
		this.qTabletteHQ=(qTabletteHQ>=0) ? qTabletteHQ : 0;
	}

	public double getqBonbonBQ() {
		return qBonbonBQ;
	}

	public void setqBonbonBQ(double qBonbonBQ) {
		this.qBonbonBQ = qBonbonBQ;
	}

	public double getqBonbonMQ() {
		return qBonbonMQ;
	}

	public void setqBonbonMQ(double qBonbonMQ) {
		this.qBonbonMQ = qBonbonMQ;
	}

	public double getqBonbonHQ() {
		return qBonbonHQ;
	}

	public void setqBonbonHQ(double qBonbonHQ) {
		this.qBonbonHQ = qBonbonHQ;
	}

	public double getqTabletteBQ() {
		return qTabletteBQ;
	}

	public void setqTabletteBQ(double qTabletteBQ) {
		this.qTabletteBQ = qTabletteBQ;
	}

	public double getqTabletteMQ() {
		return qTabletteMQ;
	}

	public void setqTabletteMQ(double qTabletteMQ) {
		this.qTabletteMQ = qTabletteMQ;
	}

	public double getqTabletteHQ() {
		return qTabletteHQ;
	}

	public void setqTabletteHQ(double qTabletteHQ) {
		this.qTabletteHQ = qTabletteHQ;
	}
	
	public double getqTot() {
		return getqBonbonBQ()+getqBonbonMQ()+getqBonbonHQ()+getqTabletteBQ()+getqTabletteMQ()+getqTabletteHQ();
	}
}
