package it.polito.med;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MedManager {

	List<Spec> spec = new ArrayList<>();
	List<Dottore> dot = new ArrayList<>();
	List<Appuntamento> app = new ArrayList<>();
	Map<String,List<String>> slotp = new TreeMap<>();
	int NApp = 0;
	List<Paz> paz = new ArrayList<>();
	List<Prenotazione> pren = new ArrayList<>();
	String Data="";
	
	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
		boolean tv = false;
		for(String s : specialities) {
			for(Spec sp : spec) {
				if(s.compareTo(sp.getNome())==0) {
					tv = true;
				}
			}
			if(!tv) {
				spec.add(new Spec(s));
			}
		}
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		Collection<String> lista = new ArrayList<>();
		for(Spec s : spec) {
			lista.add(s.getNome());
		}
		return lista;
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		boolean tv = false;
		for(Spec s : spec) {
			if(s.getNome().compareTo(speciality)==0) {
				tv = true;
			}
		}
		if(!tv) {
			throw new MedException();
		}
		tv = false;
		for(Dottore d : dot) {
			if(d.getID().compareTo(id)==0) {
				tv = true;
			}
		}
		if(tv) {
			throw new MedException();
		}
		Dottore nuovo = new Dottore(id,name,surname,speciality);
		dot.add(nuovo);
	}

	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		Collection<String> lista = new ArrayList<>();
		for(Dottore d : dot) {
			if(d.getSpec().getNome().compareTo(speciality)==0) {
				lista.add(d.getID());
			}
		}
		return lista;
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		String nuovo = "";
		for(Dottore d : dot) {
			if(d.getID().compareTo(code)==0){
				nuovo = d.getNome();
			}
		}
		return nuovo;
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		String nuovo = "";
		for(Dottore d : dot) {
			if(d.getID().compareTo(code)==0){
				nuovo = d.getCognome();
			}
		}
		return nuovo;
	}

	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public int addDailySchedule(String code, String date, String start, String end, int duration) {
		Appuntamento nuovo = new Appuntamento(code,date,start,end,duration);
		app.add(nuovo);
		String s = start.split(":")[0];
		String f = end.split(":")[0];
		String s1 = start.split(":")[1];
		String f1 = end.split(":")[1];
		int minuti2 = (Integer.parseInt(f)-Integer.parseInt(s))*60+(Integer.parseInt(f1)-Integer.parseInt(s1));
		int si = Integer.parseInt(s);
		int fi = Integer.parseInt(f);
		int diff = fi-si;
		int minuti = diff*60;
		return minuti2/duration;
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
		List<String> IDs = new ArrayList<>();
	    Map<String, List<String>> mappa = new TreeMap<>();
	    for (Appuntamento t : app) {
	        if (t.getData().compareTo(date) == 0) {
	            for (Dottore m : dot) {
	                if (m.getID() == t.getIDm() && m.getSpec().getNome().equals(speciality)) {
	                    String oraAttuale = t.getInizio();
	                    String oraPassata = t.getInizio();
	                    List<String> lista = mappa.computeIfAbsent(m.getID(), k -> new ArrayList<>());
	                    boolean flag = false;
	                    while (!flag) {
	                        int val = Integer.parseInt(oraAttuale.split(":")[1]) + t.getDurata();
	                        int hours = Integer.parseInt(oraAttuale.split(":")[0]);
	                        int minutes = val % 60;
	                        String v = String.format("%02d", minutes);
	                        int nextHour = hours + (val / 60);
	                        oraPassata = oraAttuale;
	                        oraAttuale = String.format("%02d", nextHour) + ":" + v;
	                        String ins = oraPassata + "-" + oraAttuale;
	                        lista.add(ins);
	                        if (oraAttuale.equals(t.getFine())) {
	                            flag = true;
	                        }
	                    }
	                }
	            }
	        }
	    }
	    return mappa;
	}

	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		boolean tv = false;
		for(Dottore d : dot) {
			if(d.getID().compareTo(code)==0) {
				tv = true;
			}
		}
		if(!tv) {
			throw new MedException();
		}
		tv = false;
		for(Appuntamento a: app) {
			if(a.getData().compareTo(date)==0 && a.getIDm().compareTo(code)==0) {
				tv = true;
			}
		}
		if(!tv) {
			throw new MedException();
		}
		tv = false;
		for(Appuntamento s: app) {
			if(s.getIDm().compareTo(code)==0 && s.getDurata() == (Integer.parseInt(slot.split("-")[1].split(":")[0])-Integer.parseInt(slot.split("-")[0].split(":")[0]))*60+(Integer.parseInt(slot.split("-")[1].split(":")[1])-Integer.parseInt(slot.split("-")[0].split(":")[1]))) {
				tv = true;
			}
		}
		if(!tv) {
			throw new MedException();
		}
		tv = false;
		String SApp = "";
		SApp = "A-"+NApp;
		NApp++;
		Paz nuovo = new Paz(ssn,name,surname);
		paz.add(nuovo);
		Prenotazione pn = new Prenotazione(SApp,nuovo,code,date,slot);
		pren.add(pn);
		return SApp;
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		String s = "";
		for(Prenotazione p : pren) {
			if(p.getCode().compareTo(idAppointment)==0) {
				s = p.getIDm();
			}
		}
		return s;
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		String s = "";
		for(Prenotazione p : pren) {
			if(p.getCode().compareTo(idAppointment)==0) {
				s = p.getPaziente().getCode();
			}
		}
		return s;
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		String s = "";
		for(Prenotazione p : pren) {
			if(p.getCode().compareTo(idAppointment)==0) {
				s = p.getSlot().split("-")[0];
			}
		}
		return s;
	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		String s = "";
		for(Prenotazione p : pren) {
			if(p.getCode().compareTo(idAppointment)==0) {
				s = p.getData();
			}
		}
		return s;
		
	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		Collection<String> r = new ArrayList<>();
		for(Prenotazione p : pren) {
			if(p.getIDm().compareTo(code)==0 && p.getData().compareTo(date)==0) {
				String s = p.getSlot().split("-")[0] + "=" + p.getPaziente().getCode();
				r.add(s);
			}
		}
		return r;
	}

	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		Data = date;
		int nPren=0;
		for(Prenotazione p : pren) {
			if(p.getData().compareTo(Data)==0) {
				nPren = nPren + 1;
			}
		}
		return nPren;
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	public void accept(String ssn) {
		for(Prenotazione p:pren) {
			if(p.getPaziente().getCode().compareTo(ssn)==0) {
				p.getPaziente().setAccept();;
			}
		}
	}
	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		String s = "";
		boolean trovato = false;
		for (Prenotazione a : pren) {
			if (a.getIDm().compareTo(code) == 0 && a.getPaziente().getAcc()==true && a.completa==false && a.getData().compareTo(Data)==0 && trovato!=true) {
				s = a.getCode();
				trovato = true;
			}
		}
		if (trovato == false) return null;
		return s;

	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {
		boolean tv = false;
		for(Prenotazione p : pren) {
			if(p.getCode().compareTo(appId)==0 && p.getData().compareTo(Data)==0 && p.getIDm().compareTo(code)==0 && p.getPaziente().getAcc()==true) {
				tv = true;
				p.setCompleta();
			}
		}
		if(!tv) {
			throw new MedException();
		}
	}

	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		int contatotali = 0;
		int contaacc = 0;
		for(Prenotazione p : pren) {
			if(p.getIDm().compareTo(code)==0 && p.getData().compareTo(date)==0) {
				if(p.getPaziente().acc==true) {
					contaacc++;
					contatotali++;
				}else contatotali++;
			}
		}
		return (double)contaacc/contatotali;
		
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		Map<String, Double> mappa = dot.stream().collect(Collectors.toMap(Dottore::getID,m -> {double conta = pren.stream().filter(app -> app.getIDm().equals(m.getID())).count();
		double contaSlot = app.stream().filter(t -> t.getIDm().equals(m.getID())).mapToDouble(t -> {
                                        String oraIn = t.getInizio().split(":")[0];
                                        String oraFin = t.getFine().split(":")[0];
                                        String minIn = t.getInizio().split(":")[1];
                                        String minFin = t.getFine().split(":")[1];
                                        int minutiTot = (Integer.parseInt(oraFin) - Integer.parseInt(oraIn)) * 60 +(Integer.parseInt(minFin) - Integer.parseInt(minIn));
                                        return minutiTot / t.getDurata();
                                    }).sum();
                            return conta / contaSlot;
                        },(a, b) -> a,TreeMap::new));
        return mappa;
	}
	
	
	
}
