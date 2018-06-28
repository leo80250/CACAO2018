//package abstraction.eq3PROD.echangesProdTransfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author Grégoire
// */
//
//public class TestContrat {
//	public static void main(String[] args) {
//		ContratFeve Test1 = new ContratFeve();
//		System.out.println(Test1.toString()+"\n");
//		
//		AcheteurTest ach = new AcheteurTest("TransfoTest");
//		VendeurTest vend = new VendeurTest("ProdTest");
//		
//		ContratFeve Test2 = new ContratFeve(ach, vend, 1, 3000, 3000, 3000, 2300.0, 2300.0, 2300.0, true);
//		System.out.println(Test2.toString()+"\n");
//		
//		ContratFeve Test3 = new ContratFeve(ach, vend, 1, 200, 1500, 1500, 2500.0, 2500.0, 2300.0, true);
//		System.out.println(Test3.toString()+"\n");
//		
//		//String[] ta = {""};
//		//List<String> l =null; String[] t = l.toArray(ta);
//		
//		ArrayList<ContratFeve> listcontrat = new ArrayList<ContratFeve>();
//		listcontrat.add(Test1);
//		listcontrat.add(Test2);
//		listcontrat.add(Test3);
//		
//		IAcheteurFeve[] acheteurs = new IAcheteurFeve[1];
//		acheteurs[0] = ach;
//		IVendeurFeve[] vendeurs = new IVendeurFeve[1];
//		vendeurs[0] = vend;
//		
//		MarcheFeve marche = new MarcheFeve(listcontrat, listcontrat, "Marche de feves", acheteurs, vendeurs);
//		
//		System.out.println(marche.getPrixMarche());
//	}
//}
