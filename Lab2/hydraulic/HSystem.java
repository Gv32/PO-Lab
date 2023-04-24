package hydraulic;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	Element[] elementi = new Element[100];
	int IdElemento = 0;
	
// R1
	public void addElement(Element elem){
		elementi[IdElemento] = new Source(elem.nome);
		elementi[IdElemento] = elem;
		IdElemento++;
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		Element[] usati = new Element[IdElemento];
		int i = 0;
		for(i = 0 ; i < IdElemento; i++) {
			usati[i] = elementi[i];
		}
		return usati;
	}

// R4
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for (int i = 0;i < IdElemento; i++) {
			if(elementi[i] instanceof Source) {
				elementi[i].recursive(0, observer);
			}
		}
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String stringa = "";
		for (int i = 0;i < IdElemento; i++) {
			if(elementi[i] instanceof Source) {
				stringa = elementi[i].StampaRecursive(stringa);
			}
		}
		return stringa;
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		boolean eliminato = false;
		for (int i = 0;i<IdElemento;i++) {
			if (elementi[i] instanceof Source) {
				eliminato = elementi[i].stacca(name);
			}
		}
		boolean trovato = false;
		if(eliminato == true) {
			for (int i = 0;i<IdElemento;i++) {
				if (elementi[i].getName() == name) {
					elementi[i] = elementi [i+1];
					trovato = true;
				}
				if (trovato == true) {
					elementi[i] = elementi [i+1];
				}
			}
			IdElemento--;
			return true;
		}else {
			return false;
		}
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck == true) {
			for (int i = 0;i<IdElemento;i++) {
				if (elementi[i] instanceof Source) {
					elementi[i].check(0,observer);
				}
			}
		}
	}
}
