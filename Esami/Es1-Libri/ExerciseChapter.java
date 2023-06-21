package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExerciseChapter {
	String titolo;
	int pagine;
	List<Question> domande = new ArrayList<>();
	
	public ExerciseChapter(String t, int p) {
		titolo = t;
		pagine = p;
	}
	
	
    public List<Topic> getTopics() {
    	Set<Topic> arg = new HashSet<>();
    	List<Topic> l2 = new ArrayList<>();
    	for(Question q : domande) {
    		arg.add(q.getMainTopic());
    	}
    	
    	for(Topic t : arg) {
    		l2.add(t);
    	}
    	
    	Collections.sort(l2,new Comparator<Topic>() {
    		public int compare(Topic t1, Topic t2) {
    			return t1.getKeyword().compareTo(t2.getKeyword());
    		}
    	});
    	
        return l2;
	};
	

    public String getTitle() {
        return titolo;
    }

    public void setTitle(String newTitle) {
    	titolo = newTitle;
    }

    public int getNumPages() {
        return pagine;
    }
    
    public void setNumPages(int newPages) {
    	pagine = newPages;
    }
    

	public void addQuestion(Question question) {
		domande.add(question);
	}	
	
	public List<Question> getQuestion() {
		return domande;
	}


	public List<Question> GetDomande() {
		return domande;
	}
}
