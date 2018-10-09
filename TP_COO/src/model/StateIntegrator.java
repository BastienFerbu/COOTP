package model;

import java.util.ArrayList;
import java.util.HashMap;


public class StateIntegrator extends AtomicComponent{

    private double delta_t;
    private double delta_q;
    private double q;
    private double q_dot;
    private double ql;

    public StateIntegrator(String name, double _delta_q){
        super(name);
        this.inputs.add(new Tuple<String, Double>("Adder",0.));
        this.outputs.add(new Tuple<String, Double>(name,0.));
        delta_q = _delta_q;
    }

    public void delta_int(){
        q = q+delta_q*Math.signum(q_dot);
        if(q_dot == 0){
            delta_t = Double.POSITIVE_INFINITY;
        }else
            delta_t = delta_q / Math.abs(q_dot);
        changeState(0);
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        for(Tuple<String,Double> elem : inputs){
            if(elem.x == "Adder"){
                q_dot = elem.y;
            }
        }
        ql = q;
        q = q + e*q_dot;

        if(q_dot == 0){
            delta_t = Double.POSITIVE_INFINITY;
        }else
            delta_t = (delta_q - Math.abs(q - ql)) / Math.abs(q_dot);

        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        changeState(0);
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
        q = q + delta_q * Math.signum(q_dot);
        outputs.add(new Tuple<String,Double>(name,q));
        return outputs;
    }

    public double getTa(){
        return delta_t;
    }

    public double getQ(){
        return q_dot;
    }
}