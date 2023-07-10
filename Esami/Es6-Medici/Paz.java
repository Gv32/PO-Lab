package it.polito.med;

public class Paz {

	String code;
	String nome;
	String cognome;
	boolean acc = false;
	

	public Paz(String id,String name,String surname) {
		code = id;
		nome = name;
		cognome = surname;
	}

	public String getCode() {
		return code;
	}
	
	public void setAccept() {
		acc=true;
	}
	
	public boolean getAcc() {
		return acc;
	}
}
