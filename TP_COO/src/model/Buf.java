package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Buf extends AtomicComponent{
	
	private int q;
	
	public Buf(String name){
		super(name);
		
		integer_varnames_var = new HashMap<String,Integer>();
		outputs.add(new Tuple<String,Double>("req",0.0));
		inputs.add(new Tuple<String,Double>("done",0.0));
		inputs.add(new Tuple<String,Double>("job",0.0));
	}
	
	public void init() {
		q = 0;
		integer_varnames_var.put("q",q);
		current_state = 0;
	}

	public void delta_int(){
		if(current_state == 1){
			q--;
			integer_varnames_var.put("q",q);
			changeState(2);
		}
		current_state = next_state;
	}

	public void delta_ext(ArrayList<Tuple<String,Double>> inputs){
		if(current_state == 0 && containsInputs(inputs,"job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(1);
		}
		else if(current_state == 1 && containsInputs(inputs,"job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(1);
		}
		else if(current_state == 2 && containsInputs(inputs,"job")){
			q++;
			integer_varnames_var.put("q",q);
			changeState(2);
		}
		else if(current_state == 2 && containsInputs(inputs,"done")){
			if(q>0)
				changeState(1);
			if(q==0)
				changeState(0);
		}
		
		current_state = next_state;
	}
	
	public void delta_con(ArrayList<Tuple<String,Double>> inputs){
		current_state = next_state;
	}

	public ArrayList<Tuple<String,Double>> lambda(){
		ArrayList<Tuple<String,Double>> outputs = new ArrayList<Tuple<String,Double>>();
		if(current_state == 1){
			outputs.add(new Tuple<String,Double>("req",0.0));
		}
		return outputs;
	}

	public double getTa(){

		if(current_state == 0){
			return Double.POSITIVE_INFINITY;
		}
		else if(current_state == 1){
			return 0.0;
		}
		else if(current_state == 2){
			return Double.POSITIVE_INFINITY;
		}
		
		return 0;
	}

	public int getQ(){
		return q;
	}
}