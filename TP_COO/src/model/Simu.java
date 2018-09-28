package model;

import java.util.ArrayList;

public class Simu {
	protected ArrayList<AtomicComponent> components;
	protected ArrayList<AtomicComponent> imminents;
	protected double t;
	protected double tfin;

	public Simu(ArrayList<AtomicComponent> _components){
		t=0;
		tfin=5;
		components = _components;
		imminents = new ArrayList<AtomicComponent>();
	}

	@Override
	public String toString() {
		String message = "t = "+t+"\n";
		for(AtomicComponent cp : components){
			message += "tr"+cp.name+" = "+cp.getTr()+"\n";
			/*message += "\tIl va générer "+cp.outputs.toString()+"\n";
			message += "\tIl va consommer "+cp.inputs.toString()+"\n";*/
		}
		message += "Imminents = ";
		for(AtomicComponent cp : imminents){
			message += cp.name+", ";
		}
		message += "\n***************************************************************\n";
		return message;
	}

	public static void main(String[] args) {
		AtomicComponent gen, buf, proc;
		ArrayList<AtomicComponent> components = new ArrayList<AtomicComponent>();
		
		gen = new Gen("gen");
		buf = new Buf("buf");
		proc = new Proc("proc");

		gen.init();
		buf.init();
		proc.init();

		components.add(gen);
		components.add(buf);
		components.add(proc);

		Simu simu = new Simu(components);

		while (simu.t < simu.tfin) {


			double t_min = Double.POSITIVE_INFINITY;
			ArrayList<String> current_outputs = new ArrayList<String>();

			for(AtomicComponent cp : components) {
				t_min = Math.min(t_min,cp.getTr());
			}

			System.out.println(t_min);
			System.out.println(simu.toString());
			for(AtomicComponent cp : components) {
				//TODO == t_min ou 0 ?
				if (cp.getTr() == t_min) {
					simu.imminents.add(cp);
				}
			}

			for(AtomicComponent cp : components) {
				cp.tick(t_min);
			}

			for(AtomicComponent im : simu.imminents){
				 current_outputs.addAll(im.lambda());
			}
			for(AtomicComponent cp : components){
				if(simu.imminents.contains(cp)){
					cp.delta(current_outputs);
				}
				else {
					cp.delta_ext(current_outputs);
				}
			}



			simu.t = simu.t + t_min;
			simu.imminents.clear();
		}
	}

}
