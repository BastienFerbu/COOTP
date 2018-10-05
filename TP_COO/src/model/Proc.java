package model;
import java.util.ArrayList;

public class Proc extends AtomicComponent{
	
	public Proc(String name){
		super(name);
		outputs.add(new Tuple<String,Double>("done", 0.0));
		inputs.add(new Tuple<String,Double>("req", 0.0));
	}
	
	public void init() {
		current_state = 0;		
	}

	public void delta_int(){
		if(current_state == 1)
			changeState(0);
		current_state = next_state;
	}

	public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
		if(current_state == 0 && containsInputs(inputs,"req"))
			changeState(1);
		current_state = next_state;
	}
	
	public void delta_con(ArrayList<Tuple<String,Double>> inputs){
		current_state = next_state;
	}

	public ArrayList<Tuple<String,Double>> lambda(){
		ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
		if(current_state == 1){
			outputs.add(new Tuple<String,Double>("done",0.0));
		}
		return outputs;
	}

	public double getTa(){
		if(current_state == 0){
			return Double.POSITIVE_INFINITY;
		}
		else if(current_state == 1){
			return 3.0;
		}
		return -1;
	}	
}