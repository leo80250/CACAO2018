package abstraction.eq3PROD.echangesProdTransfo;

public class TestContrat {
	public static void main(String[] args) {
		Contrat Test1 = new Contrat();
		System.out.println(Test1.toString()+"\n");
		
		AcheteurTest ach = new AcheteurTest("TransfoTest");
		VendeurTest vend = new VendeurTest("ProdTest");
		
		Contrat Test2 = new Contrat(2, 1250, 10.0, ach, vend, false);
		System.out.println(Test2.toString()+"\n");
		
		Contrat Test3 = new Contrat(1, 1400, 50.0, ach, vend, true);
		System.out.println(Test3.toString()+"\n");
	}
}
