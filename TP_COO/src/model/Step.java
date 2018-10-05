package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Step extends AtomicComponent{

    private double Xi;
    private double Xf;
    private double ts;

    public Step(String name, double _Xi, double _Xf, double _ts){
        super(name);
        Xi = _Xi;
        Xf =_Xf;
        ts = _ts;
    }

    public void init() {
        current_state = 0;
    }

    public void delta_int(){
        if(current_state == 0 )
            changeState(1);
        else if(current_state == 1){
            changeState(2);
        }
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
        if(current_state == 0){
            outputs.add(new Tuple<String,Double>(this.name,this.Xi));
        }
        else if(current_state == 1){
            outputs.add(new Tuple<String,Double>(this.name,this.Xf));
        }
        return outputs;
    }

    public double getTa(){
        if (current_state == 0){
            return 0.;
        }
        else if (current_state == 1){
            return ts;
        }
        else if (current_state == 2){
            return Double.POSITIVE_INFINITY;
        }
        else {
            return -1;
        }
    }
}