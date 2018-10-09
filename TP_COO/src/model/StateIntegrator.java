package model;

import java.util.ArrayList;
import java.util.HashMap;


public class StateIntegrator extends AtomicComponent{

    private double delta_t;
    private double delta_q;
    private double q;
    private double q_dot;
    private double ql;

    public StateIntegrator(String name, double init, double _delta_q, ArrayList<String> inputsName){
        super(name);
        for (String input: inputsName)
            this.inputs.add(new Tuple<String, Double>(input,0.));
        this.outputs.add(new Tuple<String, Double>(name,0.));
        delta_q = _delta_q;
        q = init;
        delta_t = Double.POSITIVE_INFINITY;
        q_dot = 0;
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
        ql = q;
        q = q + e*q_dot;
        for(Tuple<String,Double> elem : inputs){
            if(elem.x.equals(this.inputs.get(0).x)) {
                q_dot = elem.y;
            }

            if(this.inputs.size() == 2 && elem.x.equals(this.inputs.get(1).x)){
                if(elem.y <= 0){
                    q = -q*0.8;
                    delta_t = 0;
                }
            }
        }

        if(q_dot == 0){
            delta_t = Double.POSITIVE_INFINITY;
        }else
            delta_t = (delta_q - Math.abs(q - ql)) / Math.abs(q_dot);

        changeState(0);
        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        delta_ext(inputs);
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();

        outputs.add(new Tuple<String,Double>(this.name,q + delta_q * Math.signum(q_dot)));

        return outputs;
    }

    public double getTa(){
        return delta_t;
    }

    public double getQ(){
        return q;
    }
}