package it.polito.oop.futsal;

public class Socio {

	int codice;
	String nome;
	String cognome;
	String telefono;

	public Socio(String N,String C, String T,int Cod) {
		nome = N;
		cognome = C;
		telefono = T;
		codice = Cod;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getTelefono() {
		return telefono;
	}
	public int getCode() {
		return codice;
	}
	
}
