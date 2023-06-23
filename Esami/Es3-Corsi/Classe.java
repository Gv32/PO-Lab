package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.List;

public class Classe {
	Corso corso;
	List<Studente> studenti = new ArrayList<>();
	
	public Classe(Corso c, List<Studente> s) {
		corso = c;
		studenti = s;
	}

	public String getCorso() {
		return corso.getNome();
	}
	
	public List<Studente> getStudenti(){
		return studenti;
	}
	
	public void addStudent(Studente s) {
		studenti.add(s);
	}
	
	public boolean Piena() {
		if(studenti.size()==corso.getPosti()) {
			return true;
		}
		return false;
	}
}
