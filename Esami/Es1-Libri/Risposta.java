package it.polito.oop.books;

public class Risposta {
	String rs;
	boolean gs;
	
	public Risposta(String r,boolean g) {
		rs = r;
		gs = g;
	}

	public String GetRisposta() {
		return rs;
	}
	
	public boolean GetC() {
		return gs;
	}
	
}
