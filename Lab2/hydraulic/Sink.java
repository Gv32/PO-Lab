package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	
	String nome;
	double Mflusso;
	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		nome = name;
	}
	
	@Override
	public String getName() {
		return nome;
	}
	
	@Override
	public void recursive(double val, SimulationObserver observer) {
		observer.notifyFlow("Sink", nome, val, observer.NO_FLOW);
		return;
	}
	
	@Override
	public String StampaRecursive(String str) {
		str = str+"["+nome+"]Sink";
		return str;
	}
	
	@Override
	public Element GetNext() {
		return null;
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
		observer.notifyFlow("Sink", nome, flu, observer.NO_FLOW);
		if (flu > Mflusso) {
			observer.notifyFlowError("Sink", nome, flu, Mflusso);
		}
	}
}
