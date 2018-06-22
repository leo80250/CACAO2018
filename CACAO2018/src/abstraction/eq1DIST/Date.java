package abstraction.eq1DIST;



public class Date {
		private int jour;
		private int mois;
		private int annee;

		public Date(int jour, int mois, int annee) {
			if (Date.coherente(jour, mois, annee)) {
				this.jour = jour;
				this.mois = mois;
				this.annee = annee;
			} else {
				this.jour=1;
				this.mois=1;
				this.annee=1900;
			}
		}

		public Date() {
			this( 1, 1, 1900);
		}
		public int getJour() {
			return this.jour;
		}
		public int getMois() {
			return this.mois;
		}
		public int getAnnee() {
			return this.annee;
		}
		public void setJour( int jour) {
			if (Date.coherente(jour,this.getMois(),this.getAnnee())) {
				this.jour = jour;
			}
		}
		public void setMois(int mois) {
			if (Date.coherente(this.getJour(), mois, this.getAnnee())) {
				this.mois = mois;
			}
		}
		public void setAnnee(int annee) {
			if (Date.coherente(this.getJour(), this.getMois(), annee)) {
				this.annee = annee;
			}
		}
		public boolean before(Date d) {
			return this.getAnnee()<d.getAnnee() 
					|| (this.getAnnee()==d.getAnnee() 
					&& ( this.getMois()<d.getMois() 
							|| (this.getMois()==d.getMois() 
							&& this.getJour()<d.getJour())));
		}
		private static boolean bissextile(int a) {
			return  (((a%400)==0)||(((a%4)==0)&& ((a%100)!=0)));
		}
		private static int nbJoursMois(int m, int a) {
			int nbJ;
			if (m==4 || m==6 || m==9 || m==11) {
				nbJ=30;
			}
			else {
				if (m==2) {
					if (Date.bissextile(a)) {
						nbJ=29;
					}
					else {
						nbJ=28;
					}
				}
				else { //1, 3, 5, 7, 8, 10 ou 12
					nbJ=31;
				}
			}
			return nbJ;
		}
		private static boolean coherente(int j, int m, int a) {
			return (m>0
					&& m<13
					&& j>0
					&& j<=Date.nbJoursMois(m,a));
		}
		public String toString() {
			String resultat="";
			if (this.getJour()<10) {
				resultat +="0";
			}
			resultat +=this.getJour()+"/";
			if (this.getMois()<10) {
				resultat +="0";
			}
			resultat +=this.getMois()+"/";
			if (this.getAnnee()<1000) {
				resultat +="0";
			}
			if (this.getAnnee()<100) {
				resultat +="0";
			}
			if (this.getAnnee()<10) {
				resultat +="0";
			}
			resultat +=this.getAnnee()+" ";
			return resultat;

		}
		public boolean equals(Object o) {
			if (o instanceof Date) {
				Date od = (Date)o;
				return this.getJour()==od.getJour() 
						&& this.getMois()==od.getMois()
						&& this.getAnnee()==od.getAnnee();
			} else {
				return false;
			}
		}

		public static void main(String[] args) {
			Date d1 = new Date(1,2,2003);
			Date d2 = new Date(1,2,2004);
			Date d3 = new Date(1,3,2003);
			Date d4 = new Date(2,2,2003);
			Date d5 = new Date(1,2,2003);



			Date d6=new Date(0,1,2000);
			System.out.println("(0,1,2000) -->"+d6);
			d6=new Date(32,1,2000);
			System.out.println("(32,1,2000) -->"+d6);
			d6=new Date(1,0,2000);
			System.out.println("(1,0,2000) -->"+d6);
			d6=new Date(1,13,2000);
			System.out.println("(1,13,2000) -->"+d6);
			d6=new Date(29,2,2015);
			System.out.println("(29,2,2015) -->"+d6);
			d6=new Date(29,2,2016);
			System.out.println("29/2/2016 (bissextile) -->"+d6);
			d6.setJour(30);
			System.out.println("(29/2/2016).setJour(30) -->"+d6);
			d6.setJour(0);
			System.out.println("(29/2/2016).setJour(0) -->"+d6);
			d6.setMois(0);
			System.out.println("(29/2/2016).setMois(0) -->"+d6);
			d6.setMois(13);
			System.out.println("(29/2/2016).setMois(13) -->"+d6);
			d6.setJour(2);
			System.out.println("(29/2/2016).setJour(2) -->"+d6);
			d6.setMois(4);
			System.out.println("(2/2/2016).setMois(4) -->"+d6);
			
			System.out.println("d1.equals(d2) (false) -> "+d1.equals(d2));
			System.out.println("d1.equals(d3) (false) -> "+d1.equals(d3));
			System.out.println("d1.equals(d4) (false) -> "+d1.equals(d4));
			System.out.println("d1.equals(d5) (true)  -> "+d1.equals(d5));
		}



}
