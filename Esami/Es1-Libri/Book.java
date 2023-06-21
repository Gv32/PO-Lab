package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Book {

	List<Topic> argomenti = new ArrayList<>();
	List<Question> domande = new ArrayList<>();
	List<TheoryChapter> CapT = new ArrayList<>();
	List<ExerciseChapter> CapE = new ArrayList<>();
	List<Assignment> Assegnati = new ArrayList<>();
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		boolean tv = false;
		Topic a = null;
		if(keyword == "" || keyword == null) {
			throw new BookException();
		}
		for(Topic t : argomenti) {
			if(keyword.compareTo(t.getKeyword())==0) {
				tv = true;
				a = t;
			}
		}
		if(!tv) {
			Topic nuovo = new Topic(keyword);
			argomenti.add(nuovo);
			return nuovo;
		}
	    return a;
	}

	public Question createQuestion(String question, Topic mainTopic) {
		Question q = new Question(question,mainTopic);
		domande.add(q);
        return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
		TheoryChapter nuovo = new TheoryChapter(title,numPages,text);
		CapT.add(nuovo);
        return nuovo;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter nuovo = new ExerciseChapter(title,numPages);
		CapE.add(nuovo);
        return nuovo;
	}

	public List<Topic> getAllTopics() {
        Set<Topic> set = new HashSet<>();
        List<Topic> l = new ArrayList<>();
        List<Topic> l2 = new ArrayList<>();

        for(TheoryChapter cap : CapT) {
        	l = cap.getTopics();
        	for(Topic t : l) {
        		set.add(t);
        	}
        }
        for(Topic t : set) {
        	l2.add(t);
        }
        
        Collections.sort(l2,new Comparator<Topic>() {
        	public int compare(Topic t1, Topic t2) {
        		return t1.getKeyword().compareTo(t2.getKeyword())
;        	}
        });
		return l2;
	}

	public boolean checkTopics() {
		List<Topic> l = new ArrayList<>();
		Set<Topic> Teoria = new HashSet<>();
		Set<Topic> Eserc = new HashSet<>();
		boolean tv = false;
		for(TheoryChapter C1 : CapT) {
			l = C1.getTopics();
			for(Topic t : l) {
				Teoria.add(t);
			}
		}
		for(ExerciseChapter C2 : CapE) {
			for(Question q : C2.getQuestion()) {
				Eserc.add(q.getMainTopic());
			}
		}
		for(Topic t1 : Eserc) {
			for(Topic t2 : Teoria) {
				if(t1 == t2) {
					tv = true;
				}
			}
			if(!tv) {
				return false;
			}
			tv = false;
		}
        return true;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment nuovo = new Assignment(ID,chapter);
		Assegnati.add(nuovo);
        return nuovo;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
    	Map<Long,List<Question>> mappa = new TreeMap<>();
    	long n;
    	for(Question e : domande) {
    		n = e.numAnswers();
    		if(mappa.containsKey(n)==false) {
    			List<Question> lista = new ArrayList<>();
    			lista.add(e);
    			mappa.put(n, lista);
    		}else {
    			mappa.get(n).add(e);
    		}
    	}
        return mappa;
    }

}
