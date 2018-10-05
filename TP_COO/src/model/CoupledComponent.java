package model;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class CoupledComponent {
	protected ArrayList<AtomicComponent> components;
	protected ArrayList<String> inputs;
	protected ArrayList<String> outputs;
	protected HashMap<String,String> couple_function;

	protected String name;
	
	
	public CoupledComponent(String _name) {
		name = _name;
		components = new ArrayList<AtomicComponent>();
		
		inputs = new ArrayList<String>();
		outputs = new ArrayList<String>();
		couple_function = new HashMap<String,String>();
	}
}