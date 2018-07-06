package abstraction.eq1DIST;

import java.util.ArrayList;
import java.util.Collections;

import abstraction.eq3PROD.echangesProdTransfo.MarcheFeve;
import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class Contrat {
	int[] demande;
	demande = new int[6];
	demande[0]=0;
	demande[1]=39834; // changer les indices
	demande[2]=17500;
	demande[3]=0;
	demande[4]=29167;
	demande[5]=12500;


public ArrayList<ArrayList<Integer>> getCommande(ArrayList<GPrix2> Prix, ArrayList<ArrayList<Integer>> Stock) {
		ArrayList<ArrayList<Integer>> commandeFinale = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> listeT = new ArrayList<Integer>() ;
		String act = "" ;
		ArrayList<Acteur> acteurs=Monde.LE_MONDE.getActeurs();
		ArrayList<IVendeurChocoBis> transfo = new ArrayList<IVendeurChocoBis>();
		for (Acteur a : acteurs) {
			if(a instanceof IVendeurChocoBis) {
				transfo.add((IVendeurChocoBis) a);
			}}
			double[] m = new double[6];
			for (int i =0;i<6;i++) {
			
			while ( m[i]!=1){
			
				ArrayList<Double> prix ;
				prix = new ArrayList<Double>();
				for (int j =0; j<transfo.size();j++) {
					prix.add(transfo.get(j).getPrix().getPrixProduit(demande[i],i));
				}
				listeT = listeTriee(prix);
				
					if(Stock.get(listeT.indexOf(0)).get(i)>= 0.6*demande[i]){
						commandeFinale.get(listeT.indexOf(0)).set(i,(((int)0.6*demande[i]))) ;
						m[i]+=0.6;
						if(Stock.get(listeT.indexOf(1)).get(i)>= 0.3*demande[i]) {
							commandeFinale.get(listeT.indexOf(1)).set(i,((int)0.3*demande[i]));
							m[i]+=0.3;
							if(Stock.get(listeT.indexOf(2)).get(i)>= 0.1*demande[i]) {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(0.1*demande[i])));
								m[i]+=0.1;
							}
							else {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(Stock.get(listeT.indexOf(2)).get(i))));
								m[i]=1;
							}
						}
						else {
							commandeFinale.get(listeT.indexOf(1)).set(i,((int)(Stock.get(listeT.indexOf(1)).get(i))));
							m[i]+=Stock.get(listeT.indexOf(1)).get(i)/demande[i];
							if(Stock.get(listeT.indexOf(2)).get(i)>= (1-m[i])*demande[i]) {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)((1-m[i])*demande[i])));
							}
							else {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(Stock.get(listeT.indexOf(2)).get(i))));
								m[i]=1;
							}
						}
					}
					else {
						commandeFinale.get(listeT.indexOf(0)).set(i,((int)(Stock.get(listeT.indexOf(0)).get(i))));
						m[i]+= Stock.get(listeT.indexOf(0)).get(i)/demande[i];
						if(Stock.get(listeT.indexOf(1)).get(i)>= (0.9-m[i])*demande[i]) {
							commandeFinale.get(listeT.indexOf(1)).set(i,((int)((0.9-m[i])*demande[i])));
							m[i] = 0.9;
							if(Stock.get(listeT.indexOf(2)).get(i)>= 0.1*demande[i]) {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(0.1*demande[i])));
								m[i]+=0.1;
							}
							else {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(Stock.get(listeT.indexOf(2)).get(i))));
								m[i]=1;
							}
						}
						else {
							commandeFinale.get(listeT.indexOf(1)).set(i,((int)(Stock.get(listeT.indexOf(1)).get(i))));
							m[i]+=Stock.get(listeT.indexOf(1)).get(i)/demande[i];
							if(Stock.get(listeT.indexOf(2)).get(i)>= (1-m[i])*demande[i]) {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)((1-m[i])*demande[i])));
							}
							else {
								commandeFinale.get(listeT.indexOf(2)).set(i,((int)(Stock.get(listeT.indexOf(2)).get(i))));
								m[i]=1;
							}
					}
					
							
						}
			}}
						
					
		return commandeFinale;
	}

public ArrayList<Integer> listeTriee(ArrayList<Double> prix){
	ArrayList<Double> copie = new ArrayList<Double>();
	for (int i=0;i<3;i++) {
		copie.add(prix.get(i));
	}
	Collections.sort(copie);
	ArrayList<Integer> min = new ArrayList<Integer>();
	min.add(prix.indexOf(copie.get(0)));
	min.add(prix.indexOf(copie.get(1)));
	min.add(prix.indexOf(copie.get(2)));
	return min;
			}



}
