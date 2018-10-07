package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Integrator extends AtomicComponent{

    private double hstep;
    private double vx;
    private double old_x;
    private double x;
    private double x_dot;

    public Integrator(String name, double _hstep){
        super(name);
        hstep = _hstep;
        vx = 0;
        old_x = 0;
        x = 0;
        x_dot = 0;
    }

    public void delta_int(){
        changeState(0);
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        for(Tuple<String,Double> elem : inputs){
            if(elem.x == "Adder"){
                x_dot = elem.y;
            }
        }
        x = x + x_dot*e;
        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        changeState(0);
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
        x = x + x_dot*hstep;
        outputs.add(new Tuple<String,Double>(name,x));
        return outputs;
    }

    public double getTa(){
        return hstep;
    }

    public double getX(){
        return x;
    }
}