package it.polito.oop.books;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Assignment {

	String matricola;
	ExerciseChapter compito;
	Map<Question,List<String>> risposte = new HashMap<>();
	
	public Assignment(String id,ExerciseChapter test ) {
		matricola = id;
		compito = test;
	}
	
    public String getID() {
        return matricola;
    }

    public ExerciseChapter getChapter() {
        return compito;
    }
    
    public double addResponse(Question q,List<String> answers) {
	    risposte.put(q, answers);
	    double punteggio;
	    Set<String> corrette = new HashSet<>();
	    Set<String> incorrette = new HashSet<>();
	    double tot=0,FP=0,FN=0;
	    boolean trovato = false;
	    for (Question elem : compito.GetDomande() ) {
	 	   
	 	   if (elem.getQuestion().compareTo(q.getQuestion())==0) {
	 		   corrette = elem.getCorrectAnswers();
	 		   incorrette = elem.getIncorrectAnswers();
	 		   tot = corrette.size() + incorrette.size();
	 		   
	 		   for (String elem2 : answers) {
	 			   for (String elem3 : corrette) {
	 				   if (elem2.compareTo(elem3)==0) {
	 					   trovato = true;
	 				   }
	 			   }
	 			   if (trovato == false) {
						   FP++;
					   }
					   trovato = false;
	 		   }	   
	 		   for (String elem2 : corrette) {
	 			   for (String elem3 : answers) {
	 				   if (elem2.compareTo(elem3)==0) {
	 					   trovato = true;
	 				   }	 
	 			   }
	 			   if (trovato == false) {
						   FN++;
					   }
					   trovato = false;
	 		   }
	    }
	 }  
    punteggio = (tot-FP-FN)/tot;
    return punteggio;
 }
    
    public double totalScore() {
    	double accumula=0;
        for (Question q : risposte.keySet()) {
        	accumula = accumula + this.addResponse(q, risposte.get(q));
        }
        
        return accumula;
    }

}
