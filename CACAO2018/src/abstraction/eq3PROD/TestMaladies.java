package abstraction.eq3PROD;

public class TestMaladies {
	
	public static void main(String[] args) {
		Maladie maladie = new Maladie(0.042, 0.10, 4, "Flemmingite aigüe");
		
		for (int i = 0 ; i < 500 ; i++) {
			maladie.setMaladieActive();
			if (maladie.maladieActive > 0) System.out.println("Step "+i+"\n"+maladie.toString());
		}
	}

}
