package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Adder extends AtomicComponent{

    private HashMap<String,Double> X;
    private double x_sum = 0;

    public Adder(String name, ArrayList<String> inputsName){
        super(name);
        X = new HashMap<String, Double>();
        for(String n : inputsName) {
            X.put(n, 0.);
            this.inputs.add(new Tuple<String, Double>(n,0.));
        }
    }

    public void init() {
        current_state = 0;
    }

    public void delta_int(){
        if(current_state == 1 )
            changeState(0);
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        //modify X with the new(s) value
        for(Tuple<String,Double> elem : inputs){
            for(Map.Entry<String, Double> d: this.X.entrySet()){
                if(elem.x.equals(d.getKey())){
                    d.setValue(elem.y);
                }
            }
        }
        changeState(1);
        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){
        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
        if(current_state == 1){
            x_sum = 0;
            for(Map.Entry<String, Double> d: this.X.entrySet()){
                x_sum += d.getValue();
            }
            outputs.add(new Tuple<String,Double>(this.name,x_sum));
        }
        return outputs;
    }

    public double getx_sum(){
        return this.x_sum;
    }

    public double getTa(){
        if(current_state == 0)
            return Double.POSITIVE_INFINITY;
        else if(current_state == 1)
            return 0;
        else
            return -1;
    }
}