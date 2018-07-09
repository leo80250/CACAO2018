package abstraction.eq6DIST; // Karel Kédémos , Victor Signes, Léopold Petitjean
import java.util.ArrayList;

import abstraction.eq4TRAN.IVendeurChocoBis;
import abstraction.eq4TRAN.VendeurChoco.GPrix2;
import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import java.util.List;


public class MarcheChoco  implements Acteur{
	private ArrayList<ArrayList<Integer>> stock;
	private ArrayList<GPrix2> prix;
	private ArrayList <Acteur> distributeurs;
	private ArrayList <Acteur> transformateurs;
	private Journal Journal_Marche_choco;

	
	public MarcheChoco() {
		this.Journal_Marche_choco=new Journal("Journal Marche Choco");
	}
public void actu() {
		this.transformateurs= new ArrayList<Acteur>();

	this.distributeurs= new ArrayList<Acteur>();
	for (Acteur a : Monde.LE_MONDE.getActeurs()) {
		if (a instanceof IVendeurChocoBis) {
			//if (!a.getNom().contains("7"))
				this.transformateurs.add(a);
		}
		if (a instanceof IAcheteurChocoBis) {
			this.distributeurs.add(a);
		}
	}/*
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq6DIST")));
	this.distributeurs.add((Monde.LE_MONDE.getActeur("Eq1DIST")));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq4TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq5TRAN"));
	this.transformateurs.add( Monde.LE_MONDE.getActeur("Eq7TRAN"));*/
	this.stock= new ArrayList <ArrayList<Integer>>();
	this.prix= new ArrayList <GPrix2>();	
	
	
		
	}
	private ArrayList<ArrayList<Integer>> getStock(){
		return this.stock;
	}
	
	public void next() {
		
		actu();
	
		for (Acteur i : this.transformateurs) {
		IVendeurChocoBis ibis= (IVendeurChocoBis) i;
		if (this.Prix_correct(ibis.getPrix())){
			System.out.println(" prix ="+prix+" ibis="+ibis); //prix = null
			this.prix.add(ibis.getPrix());
			this.Journal_Marche_choco.ajouter("Prix de "+i.getNom()+"correct et ajouté");
		}else {
			this.Journal_Marche_choco.ajouter("Prix de "+i.getNom()+"incorrect");
		}
		if (this.Stock_correct((ibis.getStock()))){
			
		this.stock.add(ibis.getStock());
		this.Journal_Marche_choco.ajouter("Stock de "+i.getNom()+"correct et ajouté");
		}else {
			this.Journal_Marche_choco.ajouter("Stock de "+i.getNom()+"incorrect");
		}
		}		

		
		
		/*boolean annexe_stock_vide =true; 
		for (int i=0;i<this.getStock().size();i++) {
			ArrayList<Integer> Stock_vide= new ArrayList<Integer>();//6);
			for (int ii=1; ii<=6; ii++) {
				Stock_vide.add(0);
			}
			if (!this.getStock().get(i).equals(Stock_vide)) {
				annexe_stock_vide=false;
			}
		}*/
		/*if (!annexe_stock_vide) {
			boolean annexe_commande_nulle=true;
			ArrayList<ArrayList<Integer>> Commande_nulle= new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> a=new ArrayList<Integer>(6);
			for (int i=0; i<this.distributeurs.size();i++) {
				Commande_nulle.add(a);				
			}*/
			ArrayList<ArrayList<ArrayList<Integer>>> commande=  new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (Acteur i : this.distributeurs) {
			IAcheteurChocoBis ibis = (IAcheteurChocoBis) i;
			/*if(!ibis.getCommande(this.prix, this.stock).equals(Commande_nulle)) {
				annexe_commande_nulle= false;
				Commande_nulle.
			}*/
			if (this.Commande_correct(ibis.getCommande(this.prix,this.stock))) {
				commande.add(ibis.getCommande(this.prix, this.stock));
				this.Journal_Marche_choco.ajouter("Commande de "+i.getNom()+"correcte et ajoutée");
			}else {
				this.Journal_Marche_choco.ajouter("Commande de "+i.getNom()+"incorrecte");
			}
			
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> livraison = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int j =0; j<commande.get(0).size();j++) {
			ArrayList<ArrayList<Integer>> Livraisoni =new ArrayList<ArrayList<Integer>>(); 
			/*int qBonbonBQj=0;
			int qBonbonMQj=0;
			int qBonbonHQj=0;
			int qTabletteBQj=0;
			int qTabletteMQj=0;
			int qTabletteHQj=0;*/
			for (int i=0 ; i<commande.size() ; i++) {
				ArrayList<Integer> annexe= new ArrayList<Integer>();
				annexe.add(commande.get(i).get(j).get(0));
				annexe.add(commande.get(i).get(j).get(1));
				annexe.add(commande.get(i).get(j).get(2));
				annexe.add(commande.get(i).get(j).get(3));
				annexe.add(commande.get(i).get(j).get(4));
				annexe.add(commande.get(i).get(j).get(5));
				Livraisoni.add(annexe );
			}
			livraison.add(Livraisoni);		
		}
		
		int l=0;
		ArrayList<ArrayList<ArrayList<Integer>>> Delivery = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (Acteur i : this.transformateurs)	{
			if (l<livraison.size()) {
			IVendeurChocoBis ibis = (IVendeurChocoBis) i;
			if (livraison.get(l).size()>=3&&this.Livraison_correct(ibis.getLivraison(livraison.get(l)))) {
				Delivery.add(ibis.getLivraison(livraison.get(l)));
				this.Journal_Marche_choco.ajouter("getLivraison() de "+i.getNom()+"correcte et ajoutée");
			}else {
				this.Journal_Marche_choco.ajouter("getLivraison() de "+i.getNom()+"incorrecte");
			}
			l++;
			}
		}
		l=0;
		ArrayList<Double> paiement=new ArrayList<Double>();
		ArrayList<ArrayList<Integer>> PourDIST=new ArrayList<ArrayList<Integer>>();
		for (int j=0;j<2;j++) {
			/*int qBonbonBQj=0;
			int qBonbonMQj=0;
			int qBonbonHQj=0;
			int qTabletteBQj=0;
			int qTabletteMQj=0;
			int qTabletteHQj=0;*/
			paiement.add(0.0);
			for (int i=0; i<Delivery.size(); i++) {
				ArrayList<Integer> Deliveryj = new ArrayList<Integer>();
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(0),1));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(1),2));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(2),3));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(3),4));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(4),5));
				paiement.set(j,paiement.get(j)+this.prix.get(i).getPrixProduit(Delivery.get(i).get(j).get(5),6));
				Deliveryj.add(Delivery.get(i).get(j).get(0));
				Deliveryj.add(Delivery.get(i).get(j).get(1));
				Deliveryj.add(Delivery.get(i).get(j).get(2));
				Deliveryj.add(Delivery.get(i).get(j).get(3));
				Deliveryj.add(Delivery.get(i).get(j).get(4));
				Deliveryj.add(Delivery.get(i).get(j).get(5));
				PourDIST.add(Deliveryj);
			}
			
			}
		l=0;
		if (PourDIST.size()!=0) {
		for (Acteur i : this.distributeurs) {
			IAcheteurChocoBis ibis = (IAcheteurChocoBis) i;
			this.Journal_Marche_choco.ajouter("Envoie de "+PourDIST.get(l).toString()+" à "+i.getNom()+"et ce dernier doit payer "+paiement.get(l));
			ibis.livraison(PourDIST.get(l),paiement.get(l));
			l++;
			System.out.println("Le distributeur est"+((Acteur) ibis).getNom());
		}
		}
		l=0;
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return "MarcheChoco";
	}
	private boolean Prix_correct(GPrix2 P) {
		if (P==null) {
			return false;
		}
		boolean res=true;
		ArrayList<Double[]> Prix =P.getPrix();
		for(Double[] i :Prix) {
			for (int j=0;j<i.length;j++) {
				if(i[j]<0) {
					res= false;
				}
			}
		}
		if(!res) {return res;}
		res =true;
		ArrayList<Double[]> Intervalles =P.getIntervalles();
		for(Double[] i :Intervalles) {
			for (int j=0;j<i.length;j++) {
				if(i[j]<0) {
					res= false;
				}
			}
		}
		return res;
	}
	private boolean Stock_correct(ArrayList<Integer> Stock) {
		if (Stock==null) {
			return false;
		}
		for (Integer i:Stock) {
			if (i<0) {
				return false;
			}
		}
		return true ;
		}
	private boolean Commande_correct(ArrayList<ArrayList<Integer>> C) {
		if (C==null) {
			return false;
		}
		for(ArrayList<Integer> c:C) {
			for (Integer i:c) {
				if (i<0) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean Livraison_correct(ArrayList<ArrayList<Integer>> C) {
		if (C==null) {
			return false;
		}
		for(ArrayList<Integer> c:C) {
			for (Integer i:c) {
				if (i<0) {
					return false;
				}
			}
		}
		return true;
	}
}
