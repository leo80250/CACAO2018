package abstraction.eq5TRAN.util;

import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeveV4;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<IVendeurFeveV4> {

    private Map<IVendeurFeveV4, Double> base;

    public ValueComparator(Map<IVendeurFeveV4, Double> base) {
        this.base = base;
    }

    @Override
    public int compare(IVendeurFeveV4 v1, IVendeurFeveV4 v2) {
        return base.get(v1) >= base.get(v2) ? -1 : 1;
    }
}
