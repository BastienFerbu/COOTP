package model;

import java.util.Collection;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (o == this) return true; //if both pointing towards same object on heap

        if(o instanceof String){
            return x.equals(o);
        } else  {
            Tuple<X, Y> tp = (Tuple<X, Y>) o;
            return x.equals(tp.x);
        }
    }


}