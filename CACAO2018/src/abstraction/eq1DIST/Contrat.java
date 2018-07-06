package abstraction.eq1DIST;

import java.util.ArrayList;

import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.fourni.Acteur;

public class Contrat {

public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix,ArrayList<ArrayList<Integer>> Stock) {
		ArrayList<ArrayList<Integer>> commandeFinale = ArrayList<ArrayList<Integer>>();
		int min =0 ;
		String act = "" ;
		for (int i=0 ; i<6;i++){
			while (stock[i]!=0 || demande[i]!=0){
				for (int j =0; j<acteur.size();j++) {
					if(stock[m][i]>= 0.6*demande[i]){
						commandeFinale.get(m).get(i).add(0.6*demande[i]) ;
					if(stock[]
				else{
					
					
		
	}

public int[] minPrix(ArrayList<Double> gPrix){
	 int[] ordreActeur = new int[gPrix.size()];
	 ordreActeur[0]=0;
	 double min1 = Integer.MAX_VALUE;
	 //faire liste vendeurs choco voir avec rom
	 //écrire demande en dehors de la fonction
	 // sommer la somme
	 for(int j=0;j<gPrix.size();j++) {
			 if (gPrix.get(j)<min1) {
				 min1=gPrix.get(j);
				 }
			 }
	 ordreActeur[gPrix.indexOf(min1)]=0;
	 double min2 = Integer.MAX_VALUE;
	 for(int j=0;j<gPrix.size();j++) {
		 if (gPrix.get(j)<min1 && gPrix.get(j)!=min1) {
			 min2=gPrix.get(j);
			 }
		 }
	 ordreActeur[gPrix.indexOf(min2)]=1;
		return ordreActeur;
		
	 }
		 if (acteur)
	 }
		if((acteur.getPrixProduit(demande[i],i)<min){
			min = getPrixProduit(demande[i],i)
			act = acteur ;
			}

public int  acteur(Acteur a ){
				if(Acteur.getNom() == "E4TRAN")
					n=0 ;
				else if(Acteur.getNom()== "E5TRAN")
					n=1 ;
				else if(Acteur.getNom()=="E7TRAN")
					n=2 ;
}


}
