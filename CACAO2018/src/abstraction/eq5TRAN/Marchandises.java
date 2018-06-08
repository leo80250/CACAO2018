package abstraction.eq5TRAN;

import java.lang.reflect.Field;

/**
 * @author: Thomas Schillaci
 */
public class Marchandises {

    public static final int FEVES_BQ = 0;
    public static final int FEVES_MQ = 1;
    public static final int TABLETTES_BQ = 2;
    public static final int TABLETTES_MQ = 3;
    public static final int TABLETTES_HQ = 4;
    public static final int FRIANDISES_MQ = 5;
    public static final int POUDRE_MQ = 6;
    public static final int POUDRE_HQ = 7;

    public static String getMarchandise(int index) {
        for(Field field : Marchandises.class.getDeclaredFields()) {
            try {
                if (index == field.getInt(null)) return field.getName();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getNombreMarchandises() {
        return Marchandises.class.getDeclaredFields().length;
    }

}
