package abstraction.eq5TRAN;

import static abstraction.eq5TRAN.Marchandises.FEVES_BQ;
import static abstraction.eq5TRAN.Marchandises.FEVES_MQ;
import static abstraction.eq5TRAN.Marchandises.FRIANDISES_MQ;
import static abstraction.eq5TRAN.Marchandises.POUDRE_HQ;
import static abstraction.eq5TRAN.Marchandises.POUDRE_MQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_BQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_HQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_MQ;

import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Eq5TRAN implements Acteur, IAcheteurPoudre, IVendeurPoudre {

    // cf Marchandises.java pour obtenir l'indexation
    private Indicateur[] productionSouhaitee; // ce qui sort de nos machines en kT
    private Indicateur[] achatsSouhaites; // ce qu'on achète aux producteurs en kT
    private float facteurStock; // facteur lié aux risques= combien d'itérations on peut tenir sans réception de feves/poudre
    private Indicateur[] stocksSouhaites; // margeStock = facteurStock * variationDeStockParIteration, en kT
    private Indicateur[] stocks; // les vrais stocks en kT

    private Indicateur banque; // en milliers d'euros
    private Indicateur[] prix;

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
        
        for (int i = 0; i < nbMarchandises; i++)
            stocksSouhaites[i] = new Indicateur("Stocks souhaites de " + Marchandises.getMarchandise(i), this, productionSouhaitee[i].getValeur() + achatsSouhaites[i].getValeur());

        for (int i = 0; i < nbMarchandises; i++)
            stocks[i] = new Indicateur("Stocks de " + Marchandises.getMarchandise(i), this, stocksSouhaites[i].getValeur()); // on initialise les vrais stocks comme étant ce que l'on souhaite avoir pour la premiere iteration

        banque=new Indicateur("Banque",this,16_000); // environ benefice 2017 sur nombre d'usines
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


	
	
}