package abstraction.eq7TRAN.echangeTRANTRAN;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author bernardjoseph
 * 
 * Le but de cet objet est d'avoir un équivalent de ContratPoudre, tout en connaissant sa répartition entre les 10 transformateurs
 *
 */

public class ContratPoudre10 extends ContratPoudre{
	private List<Integer> repart;
	
	public ContratPoudre10(ContratPoudre contrat,List<Integer> repart) {
		super(contrat);
		this.repart=repart;
	}
	
	public List<Integer> getRepart(){
		return this.repart;
	}
	
	public void setRepart(List<Integer> repart) {
		this.repart=new ArrayList<Integer>(repart);
	}
	
	public List<ContratPoudre> getContrats(){
		List<ContratPoudre> lc=new ArrayList<ContratPoudre>(10);
		for(int q:this.getRepart()) {
			lc.add(new ContratPoudre(super.getQualite(),q,super.getPrix(),super.getAcheteur(),super.getVendeur(),super.getReponse()));
		}
		return lc;
	}
	
	public int getQteActeur(int acteur10) {
		return this.getRepart().get(acteur10);
	}
	
	public void setQteActeur(int acteur10,int q) {
		this.getRepart().set(acteur10, q);
	}

}
