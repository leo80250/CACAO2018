package abstraction.eq5TRAN.util;

import abstraction.eq3PROD.echangesProdTransfo.IVendeurFeve;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<IVendeurFeve> {

    private Map<IVendeurFeve, Double> base;

    public ValueComparator(Map<IVendeurFeve, Double> base) {
        this.base = base;
    }

    @Override
    public int compare(IVendeurFeve v1, IVendeurFeve v2) {
        return base.get(v1) >= base.get(v2) ? -1 : 1;
    }
}
