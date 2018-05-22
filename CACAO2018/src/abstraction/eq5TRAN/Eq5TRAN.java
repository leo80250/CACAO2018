package abstraction.eq5TRAN;

import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

import static abstraction.eq5TRAN.Marchandises.*;

public class Eq5TRAN implements Acteur, ITransformateur {

    // cf Marchandises.java pour obtenir l'indexation
    private Indicateur[] productionSouhaitee; // en kT
    private Indicateur[] achatsSouhaites; // en kT
    private float facteurStock;
    private Indicateur[] stocks; // margeStock = facteurStock * variationDeStockParIteration, en kT

    private Indicateur banque; // en milliers d'euros


    public Eq5TRAN() {
        int nbMarchandises = Marchandises.getNombreMarchandises();
        productionSouhaitee = new Indicateur[nbMarchandises];
        achatsSouhaites = new Indicateur[nbMarchandises];
        facteurStock = 3;
        stocks = new Indicateur[nbMarchandises];

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

}