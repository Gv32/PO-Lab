package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

	List<Corso> corsi = new ArrayList<>();
	List<Studente> studenti = new ArrayList<>();
	List<Classe> classi = new ArrayList<>();
	List<Notifier> notificatori = new ArrayList<>();
    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        Corso c = new Corso(name,availablePositions);
        corsi.add(c);
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
    	SortedSet<String> set = new TreeSet<>();
    	for(Corso c : corsi) {
    		set.add(c.getNome());
    	}
        return set;
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id,double gradeAverage){
        boolean tv = false;
    	for(Studente s : studenti) {
        	if(s.getID().compareTo(id)==0) {
        		tv = true;
        		s.setMedia(gradeAverage);
        	}
        }
    	if(!tv) {
    		Studente nuovo = new Studente(id,gradeAverage);
    		studenti.add(nuovo);
    	}
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
    	Collection<String> l2 = new ArrayList<>();
    	for(Studente s : studenti) {
    		l2.add(s.getID());
    	}
        return l2;
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
    	Collection<String> l2 = new ArrayList<>();
    	for(Studente s : studenti) {
    		if(s.getMedia()>=inf && s.getMedia()<=sup) {
    			l2.add(s.getID());
    		}
    	}
        return l2;
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
    	boolean tv1 = false;
    	boolean tv2 = false;
    	if(courses.size()<1 || courses.size()>3) {
    		throw new ElectiveException();
    	}
    	for(Studente s : studenti) {
    		if(s.getID().compareTo(id)==0) {
    			tv1 = true;
    		}
    	}
    	if(!tv1) {
    		throw new ElectiveException();
    	}
    	for(String s:courses) {
    		for(Corso c : corsi) {
    			if(s.compareTo(c.getNome())==0) {
    				tv2 = true;
    			}
    		}
    		if(tv2) {
    			tv2 = false;
    		}else {
    			throw new ElectiveException();
    		}
    	}
    	for(Studente s : studenti) {
    		if(s.getID().compareTo(id)==0) {
    			s.setPreferenze(courses);
    			for(Notifier n : notificatori) {
    				n.requestReceived(id);
    			}
    		}
    	}
        return courses.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
    	Map<String,List<Long>> mappa = new TreeMap<>();
    	int i = 0;
    	for(Corso c : corsi) {
    		List<Long> l2 = new ArrayList<>();
    		int primo = 0,secondo = 0,terzo = 0;
    		for(Studente s : studenti) {
    			i = 0;
    			for(String st : s.getPreferenze()) {
    				if(i==0) {
    					if(c.getNome().compareTo(st)==0) {
    						primo++;
    					}
    				}else if(i==1) {
    					if(c.getNome().compareTo(st)==0) {
    						secondo++;
    					}
    					
    				}else if(i==2) {
    					if(c.getNome().compareTo(st)==0) {
    						terzo++;
    					}
    				}
    				i++;
    			}
    		}
    		l2.add((long)primo);
    		l2.add((long)secondo);
    		l2.add((long)terzo);
    		mappa.put(c.getNome(), l2);
    		primo = 0;
    		secondo = 0;
    		terzo = 0;
    	}
        return mappa;
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
		for(Corso c:corsi) {
			List<Studente> studenti = new ArrayList<>();
			Classe clas = new Classe(c,studenti);
			classi.add(clas);
		}
		List<Studente> s = new ArrayList<>();
		for(Studente stud : studenti) {
			s.add(stud);
		}
		Collections.sort(s,new Comparator<Studente>() {
			public int compare(Studente s1, Studente s2) {
				return Double.compare(s2.getMedia(), s1.getMedia());
			}
		});
		for(Studente stud:s) {
			for(String preferenze :stud.getPreferenze()) {
				for(Classe c : classi) {
					if(preferenze.compareTo(c.getCorso())==0 && c.Piena()==false && stud.getAssegnato()==false) {
						c.addStudent(stud);
						stud.setAssegnato();
						for(Notifier n : notificatori) {
							n.assignedToCourse(stud.getID(), preferenze);
						}
					}
				}
			}
		}
		long na=0;
		for(Studente stud : studenti) {
			if(stud.getAssegnato()==false) {
				na++;
			}
		}
		return na;
	}

    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
    	Map<String,List<String>> mappa = new TreeMap<>();
    	for(Classe c : classi) {
    		List<String> stud = new ArrayList<>();
    		for(Studente s : c.getStudenti()) {
    			stud.add(s.getID());
    		}
    		mappa.put(c.getCorso(), stud);
    	}
        return mappa;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
    	notificatori.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
    	double presente = 0;
    	double totali = 0;
    	for(Studente s : studenti) {
    		int i = 0;
    		for(String st : s.getPreferenze()) {
    			if(i == choice-1) {
    				for(Classe c : classi) {
    					for(Studente st2 : c.getStudenti()) {
    						if(st2.getID().compareTo(s.getID())==0 && c.getCorso().compareTo(st)==0) {
    							presente = presente + 1;						
    						}
    					}
    				}
    			}
    			i++;
    		}
    	}
    	double t = presente/studenti.size();
        return t;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
    	List<String> stud = new ArrayList<>();
        for(Studente s: studenti) {
        	if(s.getAssegnato()==false) {
        		stud.add(s.getID());
        	}
        }
    	return stud;
    }
    
    
}
