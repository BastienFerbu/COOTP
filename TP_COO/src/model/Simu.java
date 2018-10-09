package model;

import chart.Chart;
import chart.ChartFrame;

import java.util.ArrayList;

public class Simu {
	protected ArrayList<AtomicComponent> components;
	protected ArrayList<AtomicComponent> imminents;
    protected ArrayList<Tuple<String,Double>> current_outputs;
	protected double t;
	protected double tfin;

	protected ChartFrame cf;
	protected Chart c;
	protected Chart c2;
    protected Chart c3;

	public Simu(double _tfin){

		t=0;
		tfin=_tfin;
		components = new ArrayList<AtomicComponent>();
		imminents = new ArrayList<AtomicComponent>();
        current_outputs = new ArrayList<Tuple<String,Double>>();

        cf = new ChartFrame("GBG", "GBG");
        c = new Chart("V");
		c2 = new Chart("H");
        c3 = new Chart("integrator");
        cf.addToLineChartPane(c);
        cf.addToLineChartPane(c2);
        cf.addToLineChartPane(c3);
        c3.setIsVisible(true);
        c.setIsVisible(true);
        c2.setIsVisible(true);

	}

	public void add(AtomicComponent ac){
		components.add(ac);
	}

	public void run() {
		while (t < tfin) {


			double t_min = Double.POSITIVE_INFINITY;
			current_outputs = new ArrayList<Tuple<String,Double>>();

			for(AtomicComponent cp : components) {
				t_min = Math.min(t_min,cp.getTr());
			}

			//Retrieve imminent components
			for(AtomicComponent cp : components) {
				if (cp.getTr() == t_min) {
					imminents.add(cp);
				}
			}

            //System.out.println("t_min : " + t_min);
            //Create output from components
            for(AtomicComponent im : imminents){
                current_outputs.addAll(im.lambda());
            }

            System.out.println(this.toString());

			// Tick before reset e from changing state
			for(AtomicComponent cp : components) {
				cp.tick(t_min);
			}


			for(AtomicComponent cp : components){
			    if(cp instanceof StateIntegrator && cp.getName().equals("integratorV")){
                    StateIntegrator b = (StateIntegrator) cp;
                    c.addDataToSeries(t, b.getQ());

                }
                if(cp instanceof StateIntegrator && cp.getName().equals("integratorH")){
                    StateIntegrator b = (StateIntegrator) cp;
                    c2.addDataToSeries(t, b.getQ());
                }



				if(imminents.contains(cp)){
					//Delta will choose between d_int and d_conf
					cp.delta(current_outputs);
				}
				else if(!imminents.contains(cp)) {
					cp.delta_ext(current_outputs);
				}
			}
			t = t + t_min;
			imminents.clear();
		}
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
		message += "Output = ";
        for(Tuple<String,Double> output : current_outputs){
            message += output+", ";
        }
		message += "\n***************************************************************\n";
		return message;
	}
}
