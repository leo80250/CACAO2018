package abstraction.eq2PROD;

import java.util.ArrayList;
import java.util.List;

public class ContextePolitique  {
	public final static double ponderation = 0.1;
	public final static Pays[] listPays = { Pays.COTE_IVOIRE, Pays.GHANA, Pays.NIGERIA, Pays.CAMEROUN, 
			Pays.OUGANDA, Pays.TOGO, Pays.SIERRA_LEONE, Pays.MADAGASCAR, Pays.LIBERIA, Pays.TANZANIE }  ;
	public final static double[]  partProd = { 0.48, 0.28, 0.12, 0.09, 0.006, 0.005, 0.005, 0.003, 0.003,
			0.002} ;
	public final static double[] coeffInstable = {0.36, 0.68, 0.05, 0.52, 0.42, 0.55, 0.63, 0.46, 0.54, 0.63};
	public final static int[][] paysFront = { {/*Pays.LIBERIA*/ 8 , /*Pays.GHANA*/ 1},
											{/*Pays.COTE_IVOIRE*/0, /*Pays.TOGO*/5}, 
											{/*Pays.CAMEROUN*/3, /*Pays.TOGO*/6},
											{/*Pays.NIGERIA*/2},
											{/*Pays.TANZANIE*/9},
											{/*Pays.GHANA*/1, /*Pays.NIGERIA*/2},
											{/*Pays.LIBERIA*/8},
											{/*Pays.TANZANIE*/9},
											{/*Pays.SIERRA_LEONE*/6, /*Pays.COTE_IVOIRE*/0},
											{/*Pays.OUGANDA*/4, /*Pays.MADAGASCAR*/7}};
	
	
	private boolean[] estInstable;
	private double[] coeffDeficitProd;
	
	
	public ContextePolitique(boolean[] estInstable, double[] coeffDeficitProd) {
		this.estInstable = estInstable;
		this.coeffDeficitProd = coeffDeficitProd ;
	}




	public void chgmtInstable(int p) { //* p est un entier entre 0 et 9
		double proba = coeffInstable[p]*ponderation ;
		if (estInstable[p]) {
			proba=proba*2 ;     /*Si le pays est déjà instable, il a plus de chance de rester instable (2x plus) */
		}
		if (Math.random()<proba) {
			estInstable[p]=true ;
		} else {
			estInstable[p]=false ;
		}
	}
	
	public void propageInstable(int p) {
		for (int i=0; i<paysFront.length;i++) {
			double proba = coeffInstable[i]*ponderation*2 ;
			if (Math.random()<proba) {
				estInstable[paysFront[p][i]] = true ;
			} else {
				estInstable[paysFront[p][i]] = false ;
			}
		}
	}
	
	public void  majStabilite() { /* maj de la stabilité des 10 pays par chgt spontanée et propagation
	et maj le coefficient qui réduit la production quand la stabilité est mauvaise */
		for (int i=0; i<10;i++) {
			chgmtInstable(i);
		}
		for (int i=0; i<10; i++) {
			propageInstable(i);
		}
		for (int i=0;i<10;i++) {
			 if (estInstable[i]) {
				 coeffDeficitProd[i]=0.35 ;
			 }
		}
		}

	public double coeffFinale() {
		double c = 0. ;
		for (int i=0;i<10;i++) {
			c=c+coeffDeficitProd[i]*partProd[i] ;
		}
		return c ;
	}


	



	
	
	
	
	
	
	
	

}
