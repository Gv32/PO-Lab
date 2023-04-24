package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	
	String nome = "";
	Element next1,next2;
	double Mflusso;

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		nome = name;
	}
	
	@Override
	public String getName() {
		return nome;
	}
	
	@Override
	public void connect(Element elem,int index) {
		if (index == 0) {
			next1 = elem;
			return;
		}else {
			next2 = elem;
			return;
		}
	} 
	
	
	@Override
	public Element[] getOutputs(){
		Element[] t = new Element[2];
		t[0] = next1;
		t[1] = next2;
		return t;
	}
	
	@Override
	public void recursive(double val, SimulationObserver observer) {
		next1.recursive(val/2, observer);
		next2.recursive(val/2, observer);
		observer.notifyFlow("Split", nome, val,val/2,val/2);
		return;
	}
	
	@Override
	public String StampaRecursive(String str) {
		str = str+"["+nome+"]Split +-> ";
		int ricorda = str.length()-4;
		str = next1.StampaRecursive(str);
		str = str + "\n";
		for (int i = 0; i < ricorda;i++) {
			str = str + " ";
		}
		str = str + "|\n";
		for (int i = 0; i < ricorda;i++) {
			str = str + " ";
		}
		str=str+"+->";
		str = next2.StampaRecursive(str);
		return str;
	}
	
	@Override
	public Element GetNext() {
		if(next1 != null) {
			return next1;
		}else if(next2 != null){
			return next2;
		}
		return null;
	}
	
	@Override
	public boolean stacca(String name) {
		boolean bool1 = false,bool2=false;
		if(next1 != null) {
			if (next1.getName() == name && next1.CanIDelete() == true) {
				next1 = next1.GetNext();
				return true;
			}else {
				bool1 = next1.stacca(name);
			}
		}
		if(next2 != null) {
			if (next2.getName() == name && next2.CanIDelete() == true) {
				next2 = next2.GetNext();
				return true;
			}else {
				bool2 = next2.stacca(name);
			}
		}
		if(bool1 == false && bool2 == false) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public boolean CanIDelete() {
		if(next1 == null || next2 == null) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
		Mflusso = maxFlow;
	}
	
	@Override 
	public void check(double flu, SimulationObserver observer){
		observer.notifyFlow("Split", nome, flu, flu/2,flu/2);
		if (flu > Mflusso) {
			observer.notifyFlowError("Split", nome, flu, Mflusso);
		}
		next1.check(flu/2, observer);
		next2.check(flu/2, observer);
	}
}
