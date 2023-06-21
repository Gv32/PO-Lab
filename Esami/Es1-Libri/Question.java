package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question {
	String testo;
	Topic arg;
	List<Risposta> risposte = new ArrayList<>();	
	
	public Question(String t, Topic to) {
		testo = t;
		arg = to;
	}
	
	public String getQuestion() {
		return testo;
	}
	
	public Topic getMainTopic() {
		return arg;
	}

	public void addAnswer(String answer, boolean correct) {
		Risposta ris = new Risposta(answer, correct);
		risposte.add(ris);
	}
	
    @Override
    public String toString() {
    	String st = testo + "("+arg+")";
        return st;
    }

	public long numAnswers() {
	    return risposte.size();
	}

	public Set<String> getCorrectAnswers() {
		Set<String> corrette = new HashSet<>();
		for(Risposta r : risposte) {
			if(r.GetC() == true) {
				corrette.add(r.GetRisposta());
			}
		}
		return corrette;
	}

	public Set<String> getIncorrectAnswers() {
		Set<String> noncorrette = new HashSet<>();
		for(Risposta r : risposte) {
			if(r.GetC() == false) {
				noncorrette.add(r.GetRisposta());
			}
		}
		return noncorrette;
	}
}
