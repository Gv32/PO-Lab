package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	String nome = "";
	double flusso = 0;
	Element next;
	
	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		nome = name;
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		flusso = flow;
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
		next.recursive(flusso, observer);
		observer.notifyFlow("Source", nome, observer.NO_FLOW, flusso);
		return;
	}
	
	@Override
	public String StampaRecursive(String str) {
		str = str+"["+nome+"]Source -> ";
		if(next != null) {
			str = next.StampaRecursive(str);
		}
		else {
			str = str + "*";
		}
		return str;
	}
	
	@Override
	public boolean stacca(String name) {
		boolean bool = false;
		if (next.getName() == name && next.CanIDelete() == true) {
			next = next.GetNext();
			return true;
		}else {
			bool = next.stacca(name);
			return bool;
		}
	}
	
	@Override 
	public void check(double flu, SimulationObserver observer){
		observer.notifyFlow("Source", nome, observer.NO_FLOW, flusso);
		next.check(flusso, observer);
	}
}
