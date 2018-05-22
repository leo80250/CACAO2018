package abstraction.eq5TRAN;

import abstraction.eq2PROD.IProducteurCacao;
import abstraction.eq4TRAN.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

import java.util.Arrays;

import static abstraction.eq5TRAN.Marchandises.*;

public class Eq5TRAN implements Acteur, ITransformateur {

    // cf Marchandises.java pour obtenir l'indexation
    private float[] productionSouhaitee;
    private float[] achatsSouhaites;
    private float facteurStock;
    private float[] margesStock; // margeStock = facteurStock * variationDeStockParIteration

    private float banque; // en milliers

    public Eq5TRAN() {
        int nbMarchandises = Marchandises.getNombreMarchandises();
        productionSouhaitee = new float[nbMarchandises];
        achatsSouhaites = new float[nbMarchandises];
        facteurStock = 3;
        margesStock = new float[nbMarchandises];

        productionSouhaitee[TABLETTES_BQ] = 345_000;
        productionSouhaitee[TABLETTES_MQ] = 575_000;
        productionSouhaitee[TABLETTES_HQ] = 115_000;
        productionSouhaitee[TABLETTES_HQ] = 115_000;
        productionSouhaitee[POUDRE_MQ] = 50_000;
        productionSouhaitee[FRIANDISES_MQ] = 115_000;

        achatsSouhaites[FEVES_BQ] = 360_000;
        achatsSouhaites[FEVES_MQ] = 840_000;
        achatsSouhaites[POUDRE_HQ] = 115_000;

        for (int i = 0; i < nbMarchandises; i++) margesStock[i] = productionSouhaitee[i] + achatsSouhaites[i];

        banque=16_000; // environ benefice 2017 sur nombre d'usines
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