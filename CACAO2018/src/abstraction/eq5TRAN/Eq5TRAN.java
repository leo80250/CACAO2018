package abstraction.eq5TRAN;

import static abstraction.eq5TRAN.Marchandises.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq2PROD.Eq2PROD;
import abstraction.eq3PROD.Eq3PROD;
import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Eq5TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre, IAcheteurFeve	 {

    
	// cf Marchandises.java pour obtenir l'indexation
    private Indicateur[] productionSouhaitee; // ce qui sort de nos machines en kT
    private Indicateur[] achatsSouhaites; // ce qu'on achète aux producteurs en kT
    private float facteurStock; // facteur lié aux risques= combien d'itérations on peut tenir sans réception de feves/poudre
    private Indicateur[] stocksSouhaites; // margeStock = facteurStock * variationDeStockParIteration, en kT
    private Indicateur[] stocks; // les vrais stocks en kT
    private ContratFeve[] offrePublique;

    private Indicateur banque; // en milliers d'euros
    private Indicateur[] prix;
    
    int FEVE_BQ_EQ2 = 0 ;
    int FEVE_MQ_EQ2 = 1 ;
    int FEVE_MQ_EQ3 = 2 ;

    /**
     * @author: Thomas Schillaci
     */
    public Eq5TRAN() {
        int nbMarchandises = Marchandises.getNombreMarchandises();
        productionSouhaitee = new Indicateur[nbMarchandises];
        achatsSouhaites = new Indicateur[nbMarchandises];
        facteurStock = 3;
        stocksSouhaites = new Indicateur[nbMarchandises];
        stocks = new Indicateur[nbMarchandises];
        prix = new Indicateur[nbMarchandises];

        productionSouhaitee[FEVES_BQ] = new Indicateur("Production souhaitee de feves BQ", this, 0);
        productionSouhaitee[FEVES_MQ] = new Indicateur("Production souhaitee de feves MQ", this, 0);
        productionSouhaitee[TABLETTES_BQ] = new Indicateur("Production souhaitee de tablettes BQ", this, 345);
        productionSouhaitee[TABLETTES_MQ] = new Indicateur("Production souhaitee de tablettes MQ",this,575);
        productionSouhaitee[TABLETTES_HQ] = new Indicateur("Production souhaitee de tablettes HQ",this,115);
        productionSouhaitee[POUDRE_MQ] = new Indicateur("Production souhaitee de poudre MQ",this,50);
        productionSouhaitee[POUDRE_HQ] = new Indicateur("Production souhaitee de poudre HQ",this,0);
        productionSouhaitee[FRIANDISES_MQ] = new Indicateur("Production souhaitee de friandises MQ",this,115);

        achatsSouhaites[FEVES_BQ] = new Indicateur("Achats souhaites de feves BQ", this, 360);
        achatsSouhaites[FEVES_MQ] = new Indicateur("Achats souhaites de feves MQ",this,840);
        achatsSouhaites[TABLETTES_BQ] = new Indicateur("Achats souhaites de tablettes BQ",this,0);
        achatsSouhaites[TABLETTES_MQ] = new Indicateur("Achats souhaites de tablettes MQ",this,0);
        achatsSouhaites[TABLETTES_HQ] = new Indicateur("Achats souhaites de tablettes HQ",this,0);
        achatsSouhaites[POUDRE_MQ] = new Indicateur("Achats souhaites de poudre MQ",this,0);
        achatsSouhaites[POUDRE_HQ] = new Indicateur("Achats souhaites de poudre HQ",this,0);
        achatsSouhaites[FRIANDISES_MQ] = new Indicateur("Achats souhaites de friandises MQ", this, 0);
        
        prix[FEVES_BQ] = new Indicateur("Prix de feves BQ", this, 0);
        prix[FEVES_MQ] = new Indicateur("Prix de feves MQ",this,0);
        prix[TABLETTES_BQ] = new Indicateur("Prix de tablettes BQ",this,100);
        prix[TABLETTES_MQ] = new Indicateur("Prix de tablettes MQ",this,100);
        prix[TABLETTES_HQ] = new Indicateur("Prix de tablettes HQ",this,0);
        prix[POUDRE_MQ] = new Indicateur("Prix de poudre MQ",this,100);
        prix[POUDRE_HQ] = new Indicateur("Prix de poudre HQ",this,0);
        prix[FRIANDISES_MQ] = new Indicateur("Prix de friandises MQ", this, 100);
        
     
        
        for (int i = 0; i < nbMarchandises; i++) {
            stocksSouhaites[i] = new Indicateur("Stocks souhaites de " + Marchandises.getMarchandise(i), this, productionSouhaitee[i].getValeur() + achatsSouhaites[i].getValeur());
            stocks[i] = new Indicateur("Stocks de " + Marchandises.getMarchandise(i), this, stocksSouhaites[i].getValeur()); // on initialise les vrais stocks comme étant ce que l'on souhaite avoir pour la premiere iteration
        }

        banque=new Indicateur("Banque",this,16_000); // environ benefice 2017 sur nombre d'usines

        for (Field field : getClass().getDeclaredFields()) {
            if(field==null) continue;
            try {
                if(field.get(this) instanceof  Indicateur)
                    Monde.LE_MONDE.ajouterIndicateur((Indicateur) field.get(this));
                else if(field.get(this) instanceof Indicateur[])
                    for (Indicateur indicateur : (Indicateur[]) field.get(this))
                        Monde.LE_MONDE.ajouterIndicateur(indicateur);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getNom() {
        return "Eq5TRAN";
    }

    @Override
    public void next() {

    }

	@Override
	/**
	 * @author: Juliette et Thomas
	 */
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		if (stocks[POUDRE_MQ].getValeur()==0) return new ContratPoudre[0];
		
		ContratPoudre[] catalogue = new ContratPoudre[1];
		catalogue[0]=new ContratPoudre(1,(int) stocks[POUDRE_MQ].getValeur(),prix[POUDRE_MQ].getValeur(),acheteur,this,false);
		return catalogue;
	
	}

    @Override
    /**
     * @author Juliette
     */
    public ContratPoudre[] getDevisPoudre(ContratPoudre[] demande, IAcheteurPoudre acheteur) {
        ContratPoudre[] devis = new ContratPoudre[demande.length];
        for (int i=0; i<demande.length;i++) {
            if (demande[i].getQualite()!=1) {
                devis[i]=new ContratPoudre(0,0,0,acheteur,this,false);
            }
            else{
                devis[i]=new ContratPoudre(demande[i].getQualite(), demande[i].getQuantite(), prix[POUDRE_MQ].getValeur(), acheteur, this, false);
            }
        }

        return devis;
    }

    @Override
    /**
     * @author Juliette
     */
    public void sendReponsePoudre(ContratPoudre[] devis, IAcheteurPoudre acheteur) {
        ContratPoudre[] reponse = new ContratPoudre[devis.length];
        for (int i=0; i<devis.length;i++){
            if (devis[i].getQualite()!=1 && devis[i].getQuantite() < stocks[POUDRE_MQ].getValeur() && devis[i].getPrix() == prix[POUDRE_MQ].getValeur()) {
                reponse[i] = new ContratPoudre (devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), true);
            }
            else {
                reponse[i] = new ContratPoudre (devis[i].getQualite(), devis[i].getQuantite(), devis[i].getPrix(), devis[i].getAcheteur(), devis[i].getVendeur(), false);
            }
        }
    }

    @Override
    public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat, IAcheteurPoudre acheteur) {
        return new ContratPoudre[0];
    }
    
    /**
     * @author Juliette
     * Dans cette méthode, nous sommes ACHETEURS
     * Methode permettant de récupérer les devis de poudre correspondant à nos demandes et de décider si on les accepte ou non
     */    
    private void getTousLesDevisPoudre(ContratPoudre[] demande) {
    	List<Acteur> listeActeurs = Monde.LE_MONDE.getActeurs();

    	List<ContratPoudre[]> devis = new ArrayList<ContratPoudre[]>();

    	for (Acteur acteur : listeActeurs) {
    		if(acteur instanceof IVendeurPoudre) {
    			devis.add(((IVendeurPoudre)acteur).getDevisPoudre(demande, this));
    		}
    	}
    	for(ContratPoudre[] contrat : devis) {
    		for(int j=0;j<3;j++) {
    			if(contrat[j].equals(demande[j])) {
    				contrat[j].setReponse(true);
    			}
    		}
    		contrat[0].getVendeur().sendReponsePoudre(contrat, this);
    	}
    						
    					
   }

	@Override
	public void sendContratFictif() {
		// TODO Auto-generated method stub
		
	}

	

	
	
	// Méthodes de l'interface producteur - transformateur ( François )
	
	/*
	 * François Le Guernic
	 * 
	 **/

		@Override
		public void sendOffrePublique(ContratFeve[] offrePublique) {
			/* On achète des fèves de BQ ( seulement à équipe 2 ) et de MQ ( à équipes 2 et 3 ) aux équipes de producteur 
			 * 
			 */
			
			ContratFeve[] c1 = new ContratFeve[3] ; 
			
			// Pour récupérer les offres qui nous intéressent
			int i = 0 ;
			for ( ContratFeve c : offrePublique) { 
				if (  (((Eq2PROD)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==0)
				
						|| (((Eq2PROD)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==1)
						|| (((Eq3PROD)c.getProducteur()).getNom()=="Eq3PROD" && c.getQualite()==1))
					{ c1[i] = c ; i ++ ; } 
				
			}
			
			this.offrePublique= new ContratFeve[3] ;
					for (int j = 0 ; j < 3 ; j ++) { 
						
					
					if (  ((Eq2PROD)c1[j].getProducteur()).getNom()=="Eq2PROD" ) { this.offrePublique[0]= c1[j] ; };	
					if (  ((Eq2PROD)c1[j].getProducteur()).getNom()=="Eq2PROD" && c1[j].getQualite() == 1 )
					{ this.offrePublique[1]= c1[j] ; }
					else { this.offrePublique[2] = c1[j] ; } }
		}

		/**
		 * Francois Le Guernic
		 */
		@Override
		public ContratFeve[] getDemandePrivee() {
			
			/*Par convention, dans la liste de deux contrat, on aura dans l'ordre :
			 * - le contrat pour les fèves BQ à l'équipe 2
			 * - le contart pour les fèves MQ à l'équipe 2
			 * - le contrat pour les fèves MQ à l'équipe 3
			 */
			
			ContratFeve[] demandes = new ContratFeve[3] ;
			for ( int i = 0 ; i < 3 ; i ++ ) { 
				demandes[i].setTransformateur(this) ;
				demandes[i].setReponse(false);
			}
			demandes[FEVE_BQ_EQ2].setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq2PROD"));
			demandes[FEVE_BQ_EQ2].setQualite(0);
			demandes[FEVE_BQ_EQ2].setDemande_Quantite((int) ( this.achatsSouhaites[FEVES_BQ].getValeur()) );
			demandes[FEVE_BQ_EQ2].setDemande_Prix(this.offrePublique[0].getPrix()) ;
			demandes[FEVE_MQ_EQ2].setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq2PROD"));
			demandes[FEVE_MQ_EQ2].setQualite(1) ;
			demandes[FEVE_MQ_EQ2].setDemande_Quantite((int)(0.3*this.achatsSouhaites[FEVES_MQ].getValeur())) ;
			demandes[FEVE_MQ_EQ2].setDemande_Prix(this.offrePublique[1].getPrix());
			demandes[FEVE_MQ_EQ3].setProducteur((IVendeurFeve) Monde.LE_MONDE.getActeur("Eq3PROD"));
			demandes[FEVE_MQ_EQ3].setQualite(1); 
			demandes[FEVE_MQ_EQ3].setDemande_Quantite((int) (0.3*this.achatsSouhaites[FEVES_MQ].getValeur())) ;
			demandes[FEVE_MQ_EQ3].setDemande_Prix(this.offrePublique[2].getPrix());
			
			return demandes ;
			
		} 
		
		@Override
		public void sendOffreFinale(ContratFeve[] offreFinale) {
	ContratFeve[] c1 = new ContratFeve[3] ; 
			
			// Pour récupérer les offres qui nous intéressent
			int i = 0 ;
			for ( ContratFeve c : offreFinale) { 
				if (  (((Eq2PROD)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==0)
				
						|| (((Eq2PROD)c.getProducteur()).getNom()=="Eq2PROD" && c.getQualite()==1)
						|| (((Eq3PROD)c.getProducteur()).getNom()=="Eq3PROD" && c.getQualite()==1))
					{ c1[i] = c ; i ++ ; } 
				
			}
			
			this.offrePublique= new ContratFeve[3] ;
					for (int j = 0 ; j < 3 ; j ++) { 
						
					
					if (  ((Eq2PROD)c1[j].getProducteur()).getNom()=="Eq2PROD" ) { this.offrePublique[0]= c1[j] ; };	
					if (  ((Eq2PROD)c1[j].getProducteur()).getNom()=="Eq2PROD" && c1[j].getQualite() == 1 )
					{ this.offrePublique[1]= c1[j] ; }
					else { this.offrePublique[2] = c1[j] ; } }
		}
		
		@Override
		public ContratFeve[] getResultVentes() {
			// T
			return null ; 
		}
		
		
		
			
			
		}
		
		

	
