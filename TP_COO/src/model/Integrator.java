package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Integrator extends AtomicComponent{

    private double hstep;
    private double x;
    private double x_dot;
    private double delta_t;
    private int i=0;

    public Integrator(String name, double _hstep, ArrayList<String> inputsName){
        super(name);
        this.inputs.add(new Tuple<String, Double>(inputsName.get(0),0.));
        this.outputs.add(new Tuple<String, Double>(name,0.));
        hstep = _hstep;
        delta_t = hstep;
        x = 0;
        x_dot = 0;
    }

    public void delta_int(){
        x = x + x_dot*hstep;
        delta_t = hstep;
        changeState(0);
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        x = x + x_dot*e;
        for(Tuple<String,Double> elem : inputs){
            if(elem.x.equals(this.inputs.get(0).x)){
                x_dot = elem.y;
            }
        }
        delta_t = hstep-e;
        changeState(0);
        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        delta_ext(inputs);
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();

        outputs.add(new Tuple<String,Double>(name,x + x_dot*hstep));

        return outputs;
    }

    public double getTa(){
        return delta_t;
    }

    public double getX(){
        return x;
    }
}