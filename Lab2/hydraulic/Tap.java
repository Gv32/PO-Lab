package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	String nome = "";
	boolean opened;
	double flusso = 0;
	double Mflusso = 0;
	Element next;
	
	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		nome = name;
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		opened = open;
	}
	
	
	@Override
	public String getName() {
		return nome;
	}
	
	@Override
	public void connect(Element elem) {
		next = elem;
	}
	
	@Override
	public Element getOutput(){
		return next;
	}
	
	@Override
	public void recursive(double val, SimulationObserver observer) {
		if (opened == true) {
			next.recursive(val, observer);
			observer.notifyFlow("Tap", nome, val, val);
			return;
		}
		else {
			next.recursive(0, observer);
			observer.notifyFlow("Tap", nome, val, 0);
			return;
		}
	}
	
	@Override
	public String StampaRecursive(String str) {
			str = str+"["+nome+"]Tap ->";
			if(next != null) {
				str = next.StampaRecursive(str);
			}else {
				str = str + "*";
			}
			return str;
	}
	
	@Override
	public Element GetNext() {
		return next;
	}
	
	@Override
	public boolean stacca(String name) {
		boolean bool;
		if (next.getName() == name && next.CanIDelete() == true) {
			next = next.GetNext();
			return true;
		}else {
			bool = next.stacca(name);
			return bool;
		}
	}
	
	@Override
	public boolean CanIDelete() {
		return true;
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
		Mflusso = maxFlow;
	}
	
	@Override 
	public void check(double flu, SimulationObserver observer){
		if (flu > Mflusso) {
			observer.notifyFlow("Tap", nome, flu, observer.NO_FLOW);
			observer.notifyFlowError("Tap", nome, flu, Mflusso);
		}
		if (opened == true) {
			next.check(flu, observer);
		}else {
			next.check(0, observer);
		}
	}
}
