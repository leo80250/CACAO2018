package abstraction.eq3PROD.echangesProdTransfo;

import java.util.List;

public class TestContrat {
	public static void main(String[] args) {
		ContratFeve Test1 = new ContratFeve();
		System.out.println(Test1.toString()+"\n");
		
		AcheteurTest ach = new AcheteurTest("TransfoTest");
		VendeurTest vend = new VendeurTest("ProdTest");
		
		ContratFeve Test2 = new ContratFeve(1, 1250, 2300.0, ach, vend, false);
		System.out.println(Test2.toString()+"\n");
		
		ContratFeve Test3 = new ContratFeve(1, 1400, 2250.0, ach, vend, true);
		System.out.println(Test3.toString()+"\n");
		
		//String[] ta = {""};
		//List<String> l =null; String[] t = l.toArray(ta);
		
		ContratFeve[] listcontrat = new ContratFeve[3];
		listcontrat[0] = Test1;
		listcontrat[1] = Test2;
		listcontrat[2] = Test3;
		
		MarcheFeve marche = new MarcheFeve(listcontrat, listcontrat);
		
		System.out.println(marche.getPrixMarche());
	}
}
