
import model.*;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*Simu s = new Simu(18.0);
		Gen gen = new Gen("gen");
		Buf buf = new Buf("buf");
		Proc proc = new Proc("proc");

		s.add(gen);
		s.add(buf);
		s.add(proc);

		s.run();*/
		Simu s = new Simu(20.0);

//		s.add(new Step("Step1",1,-3,0.65));
//		s.add(new Step("Step2",0,1,0.35));
//		s.add(new Step("Step3",0,1,1));
//		s.add(new Step("Step4",0,4,1.5));
//
//		ArrayList<String> inputs_name = new ArrayList<String>();
//		inputs_name.add("Step1");
//		inputs_name.add("Step2");
//		inputs_name.add("Step3");
//		inputs_name.add("Step4");
//
//		s.add(new Adder("Adder",inputs_name));
//
//		inputs_name.clear();
//        inputs_name.add("Adder");
//		s.add(new Integrator("Integrator",10e-4, inputs_name));
//		s.add(new StateIntegrator("StateIntegrator", 0,10e-4, inputs_name));
//
//		s.run();

		s.add(new Constant("gravity", -9.81));
        ArrayList<String> inputs_name = new ArrayList<String>();
        inputs_name.add("gravity");
        inputs_name.add("comp");
		s.add(new StateIntegrator("integratorV", 0,10e-1, inputs_name));

        inputs_name = new ArrayList<String>();
		inputs_name.add("integratorV");
		s.add(new StateIntegrator("integratorH", 10,10e-3, inputs_name));

		inputs_name = new ArrayList<String>();
		inputs_name.add("integratorH");
		s.add(new Comp("comp", 0, inputs_name));
//
//
		s.run();

	}

}
