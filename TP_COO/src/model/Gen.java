package model;
import java.util.ArrayList;

public class Gen  extends AtomicComponent{

	public Gen(String name){
		super(name);
		outputs.add("job");

	}
	
	public void init() {
		current_state = 0;
	}
	
	public void delta_int(){
		if(current_state == 0 )
			changeState(0);
		current_state = next_state;
	}
	
	public void delta_ext(ArrayList<String> inputs){
		current_state = next_state;
	}
	
	public void delta_con(ArrayList<String> inputs){
		current_state = next_state;
	}

	public ArrayList<String> lambda(){
		ArrayList<String> outputs = new ArrayList<String>();
		if(current_state == 0){
			outputs.add("job");
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