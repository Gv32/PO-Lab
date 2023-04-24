package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	String nome = "";
	int Num;
	Element[] next;
	double[] flussi;
	double Mflusso;
	
	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		nome = name;
		Num = numOutput;
		next = new Element[Num];
	}
	
	@Override
	public String getName() {
		return nome;
	}
	
	@Override
	public void connect(Element elem,int index) {
		next[index] = elem;
		return;
	} 
	
	@Override
	public Element[] getOutputs(){
		Element[] t = new Element[Num];
		for (int i = 0; i< Num;i++) {
			if(next[i] != null) {
				t[i] = next[i];
			}
		}
		return t;
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		flussi = new double[Num];
		int i = 0;
		for(double elem : proportions) {
			flussi[i] = elem;
			i++;
		}
	}
	
	@Override
	public void recursive(double val, SimulationObserver observer) {
		int i = 0;
		double[] vet = new double[Num];
		for(i= 0;i<Num;i++) {
			if (next[i] != null) {
				next[i].recursive(val*flussi[i], observer);
				vet[i] = val*flussi[i];
			}
		}
		observer.notifyFlow("MultiSplit", nome, val, vet);
		return;
	}
	
	@Override
	public String StampaRecursive(String str) {
		str = str+"["+nome+"]Multisplit +-> ";
		int ricorda = str.length()-4;
		int i = 0;
		for (i = 0;i<Num;i++) {
			if (next[i] != null) {
				str = next[i].StampaRecursive(str);
			}else {
				str = str + "*";
			}
			str = str + "\n";
			for (int j = 0; j < ricorda;j++) {
				str = str + " ";
			}
			if(i < Num-1) {
				str = str + "|\n";
				for (int j = 0; j < ricorda;j++) {
					str = str + " ";
				}
				str=str+"+-> ";
			}
		}
		return str;
	}
	
	@Override
	public Element GetNext() {
		int i = 0;
		for(i = 0 ; i < Num;i++) {
			if(next[i] != null) {
				return next[i];
			}
		}
		return null;
	}
	
	@Override
	public boolean stacca(String name) {
		boolean bool;
		for(int i = 0;i<Num;i++) {
			if (next[i].getName() == name && next[i].CanIDelete() == true) {
				next[i] = next[i].GetNext();
				return true;
			}else {
				bool = next[i].stacca(name);
				return bool;
			}
		}
		return false;
	}
	
	@Override
	public boolean CanIDelete() {
		int i = 0;
		int conta = 0;
		for(i = 0;i<Num;i++) {
			if(next[i]!=null) {
				conta = conta + 1;
			}
		}
		if (conta > 1) {
			return false;
		}
		return true;
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
		Mflusso = maxFlow;
	}
	
	@Override 
	public void check(double flu, SimulationObserver observer){
		int i = 0;
		double[] vet = new double[Num];
		for(i = 0;i<Num;i++) {
			vet[i] = flu*flussi[i];
		}
		observer.notifyFlow("MultiSplit", nome, flu, vet);
		if(flu > Mflusso) {
			observer.notifyFlowError("MultiSplit", nome, flu, Mflusso);
		}
		for(i= 0;i<Num;i++) {
			if (next[i] != null) {
				next[i].check(flu*flussi[i], observer);
			}
		}
		return;
	}
}
