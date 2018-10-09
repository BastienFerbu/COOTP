package model;

import java.util.ArrayList;

public class Constant extends AtomicComponent{
    private double constant;
    private double Xf;
    private double ts;

    public Constant(String name, double _constant){
        super(name);
        current_state = 0;
        constant = _constant;
    }

    public void init() {
        current_state = 0;
    }

    public void delta_int(){
        if(current_state == 0 )
            changeState(1);
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
            outputs.add(new Tuple<String,Double>(this.name,this.constant));
        }
        return outputs;
    }

    public double getTa(){
        if (current_state == 0){
            return 0.;
        }
        else if (current_state == 1){
            return Double.POSITIVE_INFINITY;
        }
        else {
            return -1;
        }
    }
}
