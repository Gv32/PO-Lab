package it.polito.med;

public class Dottore {

	String ID;
	String nome;
	String cognome;
	Spec spec;
	
	public Dottore(String code,String name,String surname,String s) {
		ID = code;
		nome = name;
		cognome = surname;
		spec = new Spec(s);
	}

	public String getID() {
		return ID;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public Spec getSpec() {
		return spec;
	}
	
}
