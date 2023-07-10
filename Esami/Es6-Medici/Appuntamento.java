package it.polito.med;

public class Appuntamento {
	
	String IDm;
	String data;
	String inizio;
	String fine;
	int durata;
	

	public Appuntamento(String id, String date, String start,String end,int t) {
		IDm=id;
		data=date;
		inizio=start;
		fine=end;
		durata=t;
	}
	
	public String getIDm() {
		return IDm;
	}
	
	public String getData() {
		return data;
	}
	
	public String getInizio() {
		return inizio;
	}

	public String getFine() {
		return fine;
	}
	
	public int getDurata() {
		return durata;
	}
}
