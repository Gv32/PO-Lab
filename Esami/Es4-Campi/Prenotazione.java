package it.polito.oop.futsal;

public class Prenotazione {

	String inizio;
	int NCampo;
	int NSocio;
	
	public Prenotazione(String in,int nc, int ns) {
		inizio = in;
		NCampo = nc;
		NSocio = ns;
	}

	public String getInizio() {
		return inizio;
	}
	
	
	public int getCampo() {
		return NCampo;
	}
	
	public int getSocio() {
		return NSocio;
	}
	
}
