package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;

public class Studente {

	String ID;
	double media;
	List<String> preferenze = new ArrayList<>();
	boolean assegnato = false;
	
	public Studente(String nome,double md) {
		ID = nome;
		media = md;
	}
	
	public void setMedia(double md) {
		media = md;
	}

	public String getID() {
		return ID;
	}
	
	public double getMedia() {
		return media;
	}
	
	public void setPreferenze(List<String> pref) {
		preferenze = pref;
	}
	
	public List<String> getPreferenze(){
		return preferenze;
	}
	
	public void setAssegnato() {
		assegnato = true;
	}
	
	public boolean getAssegnato() {
		return assegnato;
	}
}
