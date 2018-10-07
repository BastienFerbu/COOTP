package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public abstract class AtomicComponent {
	protected int current_state;
	protected int next_state;
	
	protected ArrayList<Tuple<String,Double>> inputs;
	protected ArrayList<Tuple<String,Double>> outputs;
	protected HashMap<String,Integer> integer_varnames_var;

	protected String name;
	
	protected double e;
	
	public AtomicComponent(String _name) {
		name = _name;
		current_state = 0;
		next_state = 0;
		
		e = 0;
		
		inputs = new ArrayList<Tuple<String,Double>>();
		outputs = new ArrayList<Tuple<String,Double>>();
	}

	public abstract void delta_int();

	public abstract void delta_ext(ArrayList<Tuple<String,Double>> inputs);
	
	public abstract void delta_con(ArrayList<Tuple<String,Double>> inputs);

	public abstract ArrayList<Tuple<String,Double>> lambda();

	public abstract double getTa();

	public double getTr(){
		double tr = getTa()-e;
		if(tr<0){
			return 0;
		}
		else{
			return tr;
		}
	}

	/**
	 * Use only with iminent component
	 * @param _inputs
	 */
	public void delta(ArrayList<Tuple<String,Double>> _inputs){
		boolean input_for_me = false;
		for (Tuple<String,Double> s: this.inputs) {
			if(_inputs.contains(s)) { //One input is for me
				input_for_me = true;
				break;
			}
		}
		if(input_for_me) {
			this.delta_con(_inputs);
		} else {
			this.delta_int();
		}
	}

	public void tick(double dt) {
		e = e+dt;
	}

	public void reset_e(){
		e = 0;
	}

	public void changeState(int new_state) {
		next_state = new_state;
		//System.out.println(toString());
		reset_e();
	}

	public String toString(){
		String message = "Je suis le composant "+this.name+" et je passe à l'état "+this.current_state;
		return message;
	}


    public static boolean containsInputs(Collection<Tuple<String, Double>> c, String name) {
        for(Tuple<String, Double> o : c) {
            if(o != null && o.x.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
