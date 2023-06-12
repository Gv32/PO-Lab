package clinic;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.io.Reader;
import java.io.BufferedReader;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	List<Pazienti> Paz = new ArrayList <>();
	List<Dottori> Dot = new ArrayList <>();
	Map<String,Integer> Diz = new TreeMap <>();
	
	public void addPatient(String first, String last, String ssn) {
		Pazienti comodo = new Pazienti(first,last,ssn);
		Paz.add(comodo);
	}


	public String getPatient(String ssn) throws NoSuchPatient {
		String stampa = "";
		boolean trovato = false;
		for (Pazienti elem : Paz) {
			if(elem.getCode().compareTo(ssn) == 0) {
				stampa = elem.getLastName() + " " + elem.getName() + " (" + elem.getCode() + ")";
				trovato = true;
			}
		}
		if (!trovato) {
			throw new NoSuchPatient();
		}
		return stampa;
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Dottori comodo = new Dottori(first,last,ssn,docID,specialization);
		Dot.add(comodo);
		Pazienti comodo2 = new Pazienti(first,last,ssn); 
		Paz.add(comodo2);
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		String stampa = "";
		boolean trovato = false;
		for (Dottori elem : Dot) {
			if(elem.getID() == docID) {
				stampa = elem.getLastName() + " " + elem.getName() + " (" + elem.getCode() + ") ["+elem.getID()+"]: "+elem.getsp();
				trovato = true;
			}
		}
		if (!trovato) {
			throw new NoSuchDoctor();
		}
		return stampa;
	}
	
	public Dottori RetDot(int codice) throws NoSuchDoctor {
		for (Dottori elem : Dot) {
			if (elem.getID() == codice) {
				return elem;
			}
		}
		throw new NoSuchDoctor();
	}
	
	public Pazienti RetPaz(String codice) throws NoSuchPatient {
		for (Pazienti elem : Paz) {
			if (elem.getCode().compareTo(codice) == 0) {
				return elem;
			}
		}
		throw new NoSuchPatient();
	}
	
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		boolean tv1 = false, tv2 = false;
		for (Dottori elem : Dot) {
			if (elem.getID() == docID) {
				tv1 = true;
			}
		}
		if(!tv1) {
			throw new NoSuchDoctor();
		}
		for(Pazienti elem : Paz) {
			if(elem.getCode().compareTo(ssn)==0) {
				tv2 = true;
			}
		}
		if(!tv2) {
			throw new NoSuchPatient();
		}
		
		Diz.put(ssn, (Integer) docID);
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		boolean tv1 = false,tv2 = false;
		for (Pazienti elem : Paz) {
			if(elem.getCode().compareTo(ssn)==0) {
				tv1 = true;
			}
		}
		if(!tv1) {
			throw new NoSuchPatient();
		}
		for (String elem : Diz.keySet()) {
			if(elem.compareTo(ssn)==0) {
				tv2=true;
				return Diz.get(ssn);
			}
		}
		throw new NoSuchDoctor();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Collection <String> Fisc = new ArrayList <>();
		boolean tv1 = false;
		for (Dottori d : Dot) {
			if(d.getID() == id) {
				tv1 = true;
			}
		}
		if(!tv1) {
			throw new NoSuchDoctor();
		}
		for (String i : Diz.keySet()) {
			if(Diz.get(i) == id) {
				Fisc.add(i);
			}
		}
		return Fisc;
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
	    BufferedReader br = new BufferedReader(reader);
	    String line;
	    int rowCount = 0;
	    
	    try {
	        while ((line = br.readLine()) != null) {
	            line = line.replace(" ", "");
	            String[] parts = line.split(";");
	            String recordType = parts[0];
	            switch (recordType) {
	                case "P":
	                    if (parts.length < 4) {
	                    	rowCount--;
	                        break;
	                    }                    
	                    String firstName = parts[1];
	                    String lastName = parts[2];
	                    String ssn = parts[3];                 
	                    Pazienti p = new Pazienti(firstName, lastName, ssn);
	                    Paz.add(p);
	                    break;
	                case "M":
	                    if (parts.length < 6) {
	                    	rowCount--;
	                        break;
	                    }    
	                    int id = Integer.parseInt(parts[1]);
	                    firstName = parts[2];
	                    lastName = parts[3];
	                    ssn = parts[4];
	                    String specialty = parts[5];	                    
	                    Dottori d = new Dottori(firstName, lastName, ssn, id, specialty);
	                    Dot.add(d);
	                    break;
	                default:
	                    throw new IOException();
	            }
	            rowCount++;
	        }
	    } finally {
	        br.close();
	    }
	    return rowCount;
	}



	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
	    BufferedReader br = new BufferedReader(reader);
	    String line;
	    int rowCount = 0;
	    try {
	        while ((line = br.readLine()) != null) {
	            try {
	                String[] parts = line.trim().split(";");
	                if (parts.length < 2) {
	                    listener.offending(line);
	                    continue;
	                }
	                String recordType = parts[0].trim();
	                switch (recordType) {
	                    case "P":
	                        if (parts.length < 4) {
	                            listener.offending(line);
	                            continue;
	                        }
	                       String firstName = parts[1].trim();
	                       String lastName = parts[2].trim();
	                       String ssn = parts[3].trim();	                        
	                       Pazienti p = new Pazienti(firstName,lastName,ssn);
	                       Paz.add(p);
	                       break;
	                    case "M":
	                        if (parts.length < 6) {
	                            listener.offending(line);
	                            continue;
	                        }
	                        int badgeID = Integer.parseInt(parts[1].trim());
	                        firstName = parts[2].trim();
	                        lastName = parts[3].trim();
	                        ssn = parts[4].trim();
	                        String specialty = parts[5].trim();	                        
	                        Dottori d = new Dottori(firstName,lastName,ssn,badgeID, specialty);
	                        Dot.add(d);
	                        break;
	                    default:
	                        listener.offending(line);
	                        continue;
	                }
	                rowCount++;
	            } catch (Exception e) {
	                listener.offending(line);
	            }
	        }
	    } finally {
	        br.close();
	    }

	    return rowCount;
	}

	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		Collection<Integer> lista = new ArrayList <>();
		List<Dottori> listd = new ArrayList <>();
		boolean tv1 = false;
		for(Dottori elem : Dot) {
			int x = elem.getID();
			for(Integer i : Diz.values()) {
				if (x == i) {
					tv1 = true;
				}
			}
			if(!tv1) {
				listd.add(elem);
			}
			tv1 = false;
		}
		
		Comparator <Dottori> comparatore = (dot1,dot2) -> {
			int comp = dot1.getLastName().compareTo(dot2.getLastName());
			if (comp != 0) {
				return comp;
			}
			comp = dot1.getName().compareTo(dot2.getName());
			return comp;
		};
		
		Collections.sort(listd, comparatore); 
		Integer num;
		for (Dottori dot : listd) {
			num = dot.getID();
			lista.add(num);
		}
		
		return lista;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		Map <Integer, Integer> mappa = new TreeMap <>();
		Collection<Integer> list = new ArrayList <>();
		int conta = 0;
		int contatot = 0;
		int contadot = 0;
		for (Dottori dot : Dot) {
			for(int i : Diz.values()) {
				if(dot.getID() == i) {
					conta++;
				}
			}
			mappa.put(dot.getID(), conta);
			conta = 0;
		}
		
		for (int dot : mappa.values()) {
			contadot++;
			contatot = contatot + dot;
		}
		
		float media = contatot / contadot;
		
		for (int i : mappa.keySet()) {
			if(mappa.get(i) > media) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		Map <Integer, Integer> mus = new TreeMap <>();
		SortedMap<Integer, Integer> ms = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer key1, Integer key2) {
                return Integer.compare(mus.get(key2), mus.get(key1));
            }
        });
		String nome = "", cognome = "";
		Collection<String> l = new ArrayList <>();
		int conta = 0;
		for (Dottori dot : Dot) {
			for(int i : Diz.values()) {
				if(dot.getID() == i) {
					conta++;
				}
			}
			mus.put(dot.getID(), conta);
			conta = 0;
		}
		ms.putAll(mus);
		for(Integer numBadge : ms.keySet()) {	
			for (Dottori d : Dot) {
				if (d.getID() == numBadge) {
					nome = d.getName();
					cognome = d.getLastName();
				}
			}
			String stringa =  String.format("%3d", ms.get(numBadge)) + " : "+numBadge+" "+cognome+" "+nome;
			l.add(stringa);
		}
		
		return l;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	
	private String getSpecializationByDoctorID(int doctorID) {
	    for (Dottori dottore : Dot) {
	        if (dottore.getID() == doctorID) {
	            return dottore.getsp();
	        }
	    }
	    return null;
	}
	
	public Collection<String> countPatientsPerSpecialization(){
	        Map<String, Integer> specializationCount = new TreeMap<>();
	        int id = 0;
	        for (String elem : Diz.keySet()) {
	        	for (Pazienti paz : Paz) {
	        		if(elem.compareTo(paz.getCode())==0) {
	        			id = Diz.get(elem);
	        		}
	        	}
	            String specializzazione = getSpecializationByDoctorID(id);
	            specializationCount.put(specializzazione, specializationCount.getOrDefault(specializzazione, 0) + 1);
	        }
	        
	        System.out.println(specializationCount);
	        
	        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(specializationCount.entrySet());
	        sortedList.sort(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()));

	       
	       List<String> result = new ArrayList<>();
	       for (Map.Entry<String, Integer> entry : sortedList) {
	           String formattedEntry = String.format("%3d - %s", entry.getValue(), entry.getKey());
	           result.add(formattedEntry);
	       }
	       
	       return result;
	    
	}

}
