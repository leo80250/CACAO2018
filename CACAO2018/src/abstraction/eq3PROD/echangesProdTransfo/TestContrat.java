package abstraction.eq3PROD.echangesProdTransfo;

public class TestContrat {
	public static void main(String[] args) {
		ContratFeve Test1 = new ContratFeve();
		System.out.println(Test1.toString()+"\n");
		
		AcheteurTest ach = new AcheteurTest("TransfoTest");
		VendeurTest vend = new VendeurTest("ProdTest");
		
		ContratFeve Test2 = new ContratFeve(2, 1250, 10.0, ach, vend, false);
		System.out.println(Test2.toString()+"\n");
		
		ContratFeve Test3 = new ContratFeve(1, 1400, 50.0, ach, vend, true);
		System.out.println(Test3.toString()+"\n");
	}
}
