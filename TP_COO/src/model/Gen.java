package model;
import java.util.ArrayList;

public class Gen  extends AtomicComponent{

	public Gen(String name){
		super(name);
		outputs.add(new Tuple<String,Double>("job",0.0));

	}

	public void init() {
		current_state = 0;
	}

	public void delta_int(){
		if(current_state == 0 )
			changeState(0);
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
			outputs.add(new Tuple<String,Double>("job",0.0));
		}
		return outputs;
	}

	public double getTa(){
		if(current_state == 0){
			return 2.0;
		}
		return -1;
	}
}