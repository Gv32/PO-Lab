package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TheoryChapter {
	String titolo;
	int pagine;
	String testo;
	List<Topic> arg = new ArrayList<>();
	
	public TheoryChapter(String title, int n, String text) {
		titolo = title;
		pagine = n;
		testo = text;
	}
	
    public String getText() {
		return testo;
	}

    public void setText(String newText) {
    	testo = newText;
    }

    public List<Topic> recursive(Topic argomento, List<Topic> lista){
    	if (argomento.getSubTopics().isEmpty()==true) {
    		return lista;
    	}
    	
    	for (Topic elem : argomento.getSubTopics()) {
    		lista = recursive(elem,lista);
    		lista.add(elem);
    	}
    	return lista;
    }
    
    public List<Topic> getTopics() {
		Set <Topic> set = new HashSet <>();
		
		List <Topic> lista2 = new ArrayList<>();
		
		for (Topic elem : arg) {
			
			List <Topic> listrec = new ArrayList<>();
			set.add(elem);
			listrec = recursive(elem,listrec);
			System.out.println(listrec);
			for (Topic elem2 : listrec) {
				set.add(elem2);
			}
			System.out.println(set);
			
		}
		
		for(Topic elem : set) {
			lista2.add(elem);
		}
		
		Collections.sort(lista2,
				(t1,t2)->{
					return t1.getKeyword().compareTo(t2.getKeyword());
				});
		
        return lista2;
	}

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
    
    public void addTopic(Topic topic) {
    	arg.add(topic);
    }
    
}
