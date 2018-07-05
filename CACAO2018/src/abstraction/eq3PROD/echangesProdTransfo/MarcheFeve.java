package abstraction.eq3PROD.echangesProdTransfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

/**
 * @author Grégoire
 */

public class MarcheFeve implements IMarcheFeve, Acteur {
	
	private ArrayList<ContratFeveV3> contratPrecedent;
	private ArrayList<ContratFeveV3> contratActuel;
	private String nom;
	private List<IAcheteurFeveV4> listAcheteurs;
	private List<IVendeurFeveV4> listVendeurs;
	
	private Journal journal;
	private String nomEq;
	
	public String Producteurs() {
		String s="";
		ArrayList<Acteur> listActeurs = Monde.LE_MONDE.getActeurs();
		for (Acteur a : listActeurs) {
			if (a instanceof IVendeurFeveV4) {
				s+=a.getNom()+" ";
			}
		}
		return s;
	}
	
	public String Acheteurs() {
		String s="";
		ArrayList<Acteur> listActeurs = Monde.LE_MONDE.getActeurs();
		for (Acteur a : listActeurs) {
			if (a instanceof IAcheteurFeveV4) {
				s+=a.getNom()+" ";
			}
		}
		return s;
	}
	

	public String toStringListeActeur(List<Acteur>l) {
		String s="";
		for (Acteur a : l) {
			s=s+a.getNom()+" ";
		}
		return s;
	}
	
	//faire un update de la liste d'acteur pour l'appeler en début de chaque next
	public MarcheFeve(String nom) {
		this.contratPrecedent = new ArrayList<ContratFeveV3>();
		this.contratActuel = new ArrayList<ContratFeveV3>();
		this.nom = nom;
		this.listVendeurs =new ArrayList<>();
		ArrayList<Acteur> listActeurs = Monde.LE_MONDE.getActeurs();
		//System.out.println(this.toStringListeActeur(listActeurs));;
		for (Acteur a : listActeurs) {
			if (a instanceof IVendeurFeveV4) {
				listVendeurs.add((IVendeurFeveV4) a);
				//System.out.println(listVendeurs);
			}
		}
		this.listAcheteurs = new ArrayList<IAcheteurFeveV4>(); 
		for (Acteur a : listActeurs) {
			if (a instanceof IAcheteurFeveV4) {
				listAcheteurs.add((IAcheteurFeveV4) a);
				}
		}
			
		
		setNom("Marche");
		
		setJournal(new Journal("Journal du Marche"));
		Monde.LE_MONDE.ajouterJournal(getJournal());
	}
	

	

	@Override
	public double getPrixMarche() {
		// Rappel : qualite moyenne de feves
		int x=Monde.LE_MONDE.getStep();
		if (x==1) {
			return 2100;
		}
		else {
		double ventes = 0;
		double quantite = 0;
		for(int i = 0; i < this.contratPrecedent.size(); i++) {
			if (this.contratPrecedent.get(i).getQualite() == 1) {
				ventes += this.contratPrecedent.get(i).getProposition_Quantite()*this.contratPrecedent.get(i).getProposition_Prix();
				quantite += this.contratPrecedent.get(i).getProposition_Quantite();
			}
		}
		return ventes/quantite;
		}
	}
	
	public void setNom(String s) {
		this.nom=s;
	}
	
	public Journal getJournal() {
		return this.journal;
	}
	public String getNomEq() {
		return this.nomEq;
	}
	public void setNomEq(String s) {
		this.nomEq = s;
	}
	public void setJournal(Journal j) {
		this.journal = j;
	}

	@Override
	public ArrayList<ContratFeveV3> getContratPrecedent() {
		return this.contratPrecedent;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void next() {
		
		this.getJournal().ajouter("Producteurs presents : "+ this.Producteurs());
		this.getJournal().ajouter("Acheteurs presents : "+this.Acheteurs());
		this.getJournal().ajouter("Contrats Precedents : "+this.contratPrecedent.toString());
		
		// Reception des offres publiques (Producteurs -> Marche)
		contratPrecedent=new ArrayList<ContratFeveV3>();
		for (ContratFeveV3 c : contratActuel) {
			contratPrecedent.add(c);
		}
		contratActuel = new ArrayList<ContratFeveV3>();
		 for (IVendeurFeveV4 vendeur : listVendeurs) {
			 //System.out.println(listVendeurs);
			 List<ContratFeveV3> cop = vendeur.getOffrePubliqueV3();
			 for (ContratFeveV3 contrat : cop) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 this.getJournal().ajouter("Offres Publiques en cours : "+contratActuel.toString());
		 
		// System.out.println(" contrats actuels 1:");
		 //for (ContratFeveV3 c : contratActuel) {
			 //System.out.println(c);
		 //}
		 //System.out.println("------");
		 
		 List<ContratFeveV3> ca2 = new ArrayList<ContratFeveV3>();
		 for (ContratFeveV3 c : contratActuel) {
			 if (c.getProducteur()!=null) {
				 ca2.add(c);
			 } else {
				 this.getJournal().ajouter("contrat "+c+" n'a pas de producteur --> enleve");
			 }
		 }
		// Envoi des offres publiques (Marche -> Transformateurs)
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
				acheteur.sendOffrePubliqueV3(ca2);//contratActuel);
				acheteur.sendContratFictifV3(contratPrecedent);
			 }
			
		 
		 // Reception des demandes privees (Transformateurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
			 for (ContratFeveV3 contrat : acheteur.getDemandePriveeV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 /*System.out.println(" contrats actuels 2:");
		 for (ContratFeveV3 c : contratActuel) {
			 System.out.println(c);
		 }
		 System.out.println("------");
		 
		 this.getJournal().ajouter("Demandes privee en cours : "+contratActuel.toString());
		*/	
		 
		 // Envoi des demandes privees (Marche -> Producteurs)
		 for (IVendeurFeveV4 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV3> contratsPourVendeur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) {
					contratsPourVendeur.add(contrat);
				}
			 }
			vendeur.sendDemandePriveeV3(contratsPourVendeur); 
		 }
		 
		 // Reception des propositions (Vendeurs -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IVendeurFeveV4 vendeur : listVendeurs) {
			 for (ContratFeveV3 contrat : vendeur.getOffreFinaleV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 this.getJournal().ajouter("Propositions en cours : "+contratActuel.toString());
			
		 
		// Envoi des propositions (Marche -> Transformateur)
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) { 
			 ArrayList<ContratFeveV3> contratsPourAcheteur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getTransformateur() == acheteur) contratsPourAcheteur.add(contrat);
			 }
			acheteur.sendOffreFinaleV3(contratsPourAcheteur);
		 }
		 
		 // Reception des reponses (Transformateur -> Marche)
		 contratActuel = new ArrayList<ContratFeveV3>();
		 for (IAcheteurFeveV4 acheteur : listAcheteurs) {
			 for (ContratFeveV3 contrat : acheteur.getResultVentesV3()) {
				contratActuel.add(contrat); 
			 }
		 }
		 
		 this.getJournal().ajouter("Reponses en cours : "+contratActuel.toString());
			
		 
		 // Envoi des reponses (Marche -> Producteurs)
		 for (IVendeurFeveV4 vendeur : listVendeurs) { 
			 ArrayList<ContratFeveV3> contratsPourVendeur = new ArrayList<ContratFeveV3>();
			 for (ContratFeveV3 contrat : contratActuel) {
				if (contrat.getProducteur() == vendeur) contratsPourVendeur.add(contrat);
			 }
			vendeur.sendResultVentesV3(contratsPourVendeur);
		 }
		 this.getJournal().ajouter("------------------------------------------------------------------------------");
			
	}
}
