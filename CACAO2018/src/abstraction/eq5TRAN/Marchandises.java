package abstraction.eq5TRAN;

public class Marchandises {

    public static final int FEVES_BQ = 0;
    public static final int FEVES_MQ = 1;
    public static final int TABLETTES_BQ = 2;
    public static final int TABLETTES_MQ = 3;
    public static final int TABLETTES_HQ = 4;
    public static final int FRIANDISES_MQ = 5;
    public static final int POUDRE_MQ = 6;
    public static final int POUDRE_HQ = 7;

    public static int getNombreMarchandises() {
        return Marchandises.class.getDeclaredFields().length;
    }

}
