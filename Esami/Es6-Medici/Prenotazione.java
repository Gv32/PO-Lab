package it.polito.med;

public class Prenotazione {

	String code;
	Paz paziente;
	String IDm;
	String data;
	String slot;
	boolean completa = false;

	public Prenotazione(String codice,Paz p,String id, String date,String s) {
		code = codice;
		paziente = p;
		IDm=id;
		data=date;
		slot = s;
	}

	public void setCompleta() {
		completa = true;
	}
	
	public Paz getPaziente() {
		return paziente;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getIDm() {
		return IDm;
	}
	
	public String getData() {
		return data;
	}
	
	public String getSlot() {
		return slot;
	}
}
