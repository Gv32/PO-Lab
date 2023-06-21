package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Topic {
	
	String chiave;
	List<Topic> sottoArg = new ArrayList<>();

	public Topic(String key) {
		chiave = key;
	}
	
	public String getKeyword() {
        return chiave;
	}
	
	@Override
	public String toString() {
	    return chiave;
	}

	public boolean addSubTopic(Topic topic) {
		boolean tv = false;
		for(Topic t : sottoArg) {
			if(t == topic) {
				tv = true;
				return false;
			}
		}
		if(!tv) {
			sottoArg.add(topic);
			return true;
		}else {
			return false;
		}
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
		List <Topic> l2 = new ArrayList<>();
		for(Topic elem : sottoArg) {
			l2.add(elem);
		}
		Collections.sort(l2, new Comparator<Topic>() {
			public int compare(Topic t1, Topic t2) {
				return t1.getKeyword().compareTo(t2.getKeyword());
			}
		});
        return l2;
	}
}
