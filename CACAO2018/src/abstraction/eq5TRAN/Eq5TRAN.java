package abstraction.eq5TRAN;

import static abstraction.eq5TRAN.Marchandises.FEVES_BQ;
import static abstraction.eq5TRAN.Marchandises.FEVES_MQ;
import static abstraction.eq5TRAN.Marchandises.FRIANDISES_MQ;
import static abstraction.eq5TRAN.Marchandises.POUDRE_HQ;
import static abstraction.eq5TRAN.Marchandises.POUDRE_MQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_BQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_HQ;
import static abstraction.eq5TRAN.Marchandises.TABLETTES_MQ;

import abstraction.eq3PROD.echangesProdTransfo.ContratFeve;
import abstraction.eq3PROD.echangesProdTransfo.IAcheteurFeve;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.eq7TRAN.echangeTRANTRAN.ContratPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IAcheteurPoudre;
import abstraction.eq7TRAN.echangeTRANTRAN.IVendeurPoudre;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Eq5TRAN implements Acteur, ITransformateur, IAcheteurPoudre, IVendeurPoudre {

    // cf Marchandises.java pour obtenir l'indexation
    private Indicateur[] productionSouhaitee; // ce qui sort de nos machines en kT
    private Indicateur[] achatsSouhaites; // ce qu'on achète aux producteurs en kT
    private float facteurStock; // facteur lié aux risques= combien d'itérations on peut tenir sans réception de feves/poudre
    private Indicateur[] stocks; // margeStock = facteurStock * variationDeStockParIteration, en kT

    private Indicateur banque; // en milliers d'euros
    private Indicateur[] prix; 


    public Eq5TRAN() {
        int nbMarchandises = Marchandises.getNombreMarchandises();
        productionSouhaitee = new Indicateur[nbMarchandises];
        achatsSouhaites = new Indicateur[nbMarchandises];
        facteurStock = 3;
        stocks = new Indicateur[nbMarchandises];
        prix = new Indicateur[nbMarchandises];

        productionSouhaitee[FEVES_BQ] = new Indicateur("Feves BQ", this, 0);
        productionSouhaitee[FEVES_MQ] = new Indicateur("Feves MQ", this, 0);
        productionSouhaitee[TABLETTES_BQ] = new Indicateur("Tablettes BQ", this, 345);
        productionSouhaitee[TABLETTES_MQ] = new Indicateur("Tablettes MQ",this,575);
        productionSouhaitee[TABLETTES_HQ] = new Indicateur("Tablettes HQ",this,115);
        productionSouhaitee[POUDRE_MQ] = new Indicateur("Poudre MQ",this,50);
        productionSouhaitee[POUDRE_HQ] = new Indicateur("Poudre HQ",this,0);
        productionSouhaitee[FRIANDISES_MQ] = new Indicateur("Friandises MQ",this,115);

        achatsSouhaites[FEVES_BQ] = new Indicateur("Feves BQ", this, 360);
        achatsSouhaites[FEVES_MQ] = new Indicateur("Feves MQ",this,840);
        achatsSouhaites[TABLETTES_BQ] = new Indicateur("Tablettes BQ",this,0);
        achatsSouhaites[TABLETTES_MQ] = new Indicateur("Tablettes MQ",this,0);
        achatsSouhaites[TABLETTES_HQ] = new Indicateur("Tablettes HQ",this,0);
        achatsSouhaites[POUDRE_MQ] = new Indicateur("Poudre MQ",this,0);
        achatsSouhaites[POUDRE_HQ] = new Indicateur("Poudre HQ",this,0);
        achatsSouhaites[FRIANDISES_MQ] = new Indicateur("Friandises MQ", this, 0);
        
        prix[FEVES_BQ] = new Indicateur("Feves BQ", this, 0);
        prix[FEVES_MQ] = new Indicateur("Feves MQ",this,0);
        prix[TABLETTES_BQ] = new Indicateur("Tablettes BQ",this,100);
        prix[TABLETTES_MQ] = new Indicateur("Tablettes MQ",this,100);
        prix[TABLETTES_HQ] = new Indicateur("Tablettes HQ",this,0);
        prix[POUDRE_MQ] = new Indicateur("Poudre MQ",this,100);
        prix[POUDRE_HQ] = new Indicateur("Poudre HQ",this,0);
        prix[FRIANDISES_MQ] = new Indicateur("Friandises MQ", this, 100);
        
        

        
        
        for (int i = 0; i < nbMarchandises; i++)
            stocks[i] = new Indicateur("Stocks de " + Marchandises.getMarchandise(i), this, productionSouhaitee[i].getValeur() + achatsSouhaites[i].getValeur());

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
    public void sell(int q) {

    }

	@Override
	public void sendCataloguePoudre(ContratPoudre[] offres) {
		
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	// Juliette et Thomas
	public ContratPoudre[] getCataloguePoudre(IAcheteurPoudre acheteur) {
		if (stocks[POUDRE_MQ].getValeur()==0) {
			return new ContratPoudre[0];
		}
		
		ContratPoudre[] catalogue = new ContratPoudre[1];
		catalogue[0]=new ContratPoudre(1,(int)stocks[POUDRE_MQ].getValeur(),prix[POUDRE_MQ].getValeur(),acheteur,this,false);
		return catalogue;
	
	}

	@Override
	public ContratPoudre[] getDevisPoudre(ContratPoudre[] devis) {
		
		return null;
	}

	@Override
	public ContratPoudre[] sendReponseDevisPoudre(ContratPoudre[] devis) {
		
		return null;
	}

	@Override
	public ContratPoudre[] getEchangeFinalPoudre(ContratPoudre[] contrat) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}