package abstraction.eq3PROD.echangesProdTransfo;

public class MarcheFeve implements IMarcheFeve {
	
	private ContratFeve[] contratPrecedent;
	private ContratFeve[] contratActuel;
	
	public MarcheFeve() {
		this.contratPrecedent = new ContratFeve[1]; 
		contratPrecedent[0] = new ContratFeve(1, 1, 2300, null, null, true);
		this.contratActuel = new ContratFeve[1];
		contratActuel[0] = new ContratFeve();
	}

	@Override
	public double getPrixMarche() {
		// Rappel : qualite moyenne de feves
		double ventes = 0;
		double quantite = 0;
		for(int i = 0; i < this.contratPrecedent.length; i++) {
			if (this.contratPrecedent[i].getQualite() == 1) {
				ventes += this.contratPrecedent[i].getQuantite()*this.contratPrecedent[i].getPrix();
				quantite += this.contratPrecedent[i].getQuantite();
			}
		}
		return ventes/quantite;
	}

	@Override
	public ContratFeve[] getContrat() {
		return this.contratActuel;
	}

}
