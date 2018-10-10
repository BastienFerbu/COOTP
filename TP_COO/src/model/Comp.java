package model;

import java.util.ArrayList;

public class Comp extends AtomicComponent{
    private double seuil;
    private double output;
    private boolean sent;

    public Comp(String name, double _seuil, ArrayList<String> inputsName){
        super(name);
        seuil = _seuil;
        this.inputs.add(new Tuple<String, Double>(inputsName.get(0),0.));
        sent = false;
    }

    public void init() {
        current_state = 0;
    }

    public void delta_int(){
        if(current_state == 0){
            changeState(0);
        }
        current_state = next_state;
    }

    public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
        if(current_state == 0){
            for(Tuple<String,Double> elem : inputs) {
                if (elem.x.equals(this.inputs.get(0).x)) {
                    if (elem.y <= seuil) {
                        output = 1;
                        changeState(1);
                    } else if (elem.y > seuil) {
                        changeState(0);
                    }
                }
            }
        }

        current_state = next_state;
    }

    public void delta_con(ArrayList<Tuple<String,Double>> inputs){

        current_state = next_state;
    }

    public ArrayList<Tuple<String,Double>> lambda(){
        ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
        if(current_state == 1){
            outputs.add(new Tuple<String,Double>(this.name,this.output));
            changeState(0);
            this.output = 0;
        }
        return outputs;
    }

    public double getOutput(){
        return output;
    }

    public double getTa(){
        if(current_state == 1) {
            return 0;
        }
        return Double.POSITIVE_INFINITY;
    }
}
