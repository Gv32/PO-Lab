package it.polito.oop.elective;

public class Corso {
	String nome;
	int posti;
	
	public Corso(String n,int p) {
		nome = n;
		posti = p;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getPosti() {
		return posti;
	}
}
