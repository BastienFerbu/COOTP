
import model.*;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simu s = new Simu(5.0);
		Gen gen = new Gen("gen");
		Buf buf = new Buf("buf");
		Proc proc = new Proc("proc");

		s.add(gen);
		s.add(buf);
		s.add(proc);

		s.run();

	}

}
