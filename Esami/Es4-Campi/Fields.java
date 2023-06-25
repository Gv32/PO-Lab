package it.polito.oop.futsal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
	
	List<Campo> campi = new  ArrayList<>();
	List<Socio> soci = new  ArrayList<>();
	List<Prenotazione> prenotazioni = new ArrayList<>();
	int totcampi = 0;
	int totsoci = 0;
	String Apertura = "";
	String Chiusura = "";
	    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    
	
    public void defineFields(Features... features) throws FutsalException {
    	for(Features f : features) {
    		if(f.indoor==true) {
    			totcampi++;
    			Campo nuovo = new Campo(f,totcampi);
    			campi.add(nuovo);
    		}else {
    			if(f.indoor!=true && (f.heating==true || f.ac==true)) {
    				throw new FutsalException();
    			}else {
    				totcampi++;
        			Campo nuovo = new Campo(f,totcampi);
        			campi.add(nuovo);
    			}
    		}
    	}
    }
    
    public long countFields() {
        return totcampi;
    }

    public long countIndoor() {
    	int conta = 0;
    	for(Campo c : campi) {
    		if(c.getCoperto()==true) {
    			conta++;
    		}
    	}
        return conta;
    }
    
    public String getOpeningTime() {
        return Apertura;
    }
    
    public void setOpeningTime(String time) {
    	Apertura = time;
    }
    
    public String getClosingTime() {
        return Chiusura;
    }
    
    public void setClosingTime(String time) {
    	Chiusura = time;
    }

    public int newAssociate(String first, String last, String mobile) {
    	totsoci=totsoci+1;
    	Socio nuovo = new Socio(first,last,mobile,totsoci);
    	soci.add(nuovo);
        return totsoci;
    }
    
    public String getFirst(int associate) throws FutsalException {
    	if(associate > soci.size() || associate <= 0) {
    		throw new FutsalException();
    	}
    	String st = "";
    	for(Socio s :soci) {
    		if(s.getCode()==associate) {
    			st = s.getNome();
    		}
    	}
        return st;
    }
    
    public String getLast(int associate) throws FutsalException {
    	if(associate > soci.size() || associate <= 0) {
    		throw new FutsalException();
    	}
    	String st = "";
    	for(Socio s :soci) {
    		if(s.getCode()==associate) {
    			st = s.getCognome();
    		}
    	}
        return st;
    }
    
    public String getPhone(int associate) throws FutsalException {
    	if(associate > soci.size() || associate <= 0) {
    		throw new FutsalException();
    	}
    	String st = "";
    	for(Socio s :soci) {
    		if(s.getCode()==associate) {
    			st = s.getTelefono();
    		}
    	}
        return st;
    }
    
    public int countAssociates() {
        return totsoci;
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	
    	if(field > campi.size() || field <= 0) {
    		throw new FutsalException();
    	}
    	
    	if(associate > soci.size() || associate <= 0) {
    		throw new FutsalException();
    	}
    	
    	for(Prenotazione p : prenotazioni) {
    		if(p.getCampo()==field && p.getInizio().compareTo(time)==0) {
    			throw new FutsalException();
    		}
    	}
    	
    	String[] orari = Apertura.split(":");
    	String[] pr = time.split(":");
    	if(orari[1].compareTo(pr[1])!=0) {
    		throw new FutsalException();
    	}
    	
    	Prenotazione nuovo = new Prenotazione(time,field,associate);
    	prenotazioni.add(nuovo);
    	
    }

    public boolean isBooked(int field, String time) {
    	boolean tv = false;
    	for(Prenotazione p : prenotazioni) {
    		if(p.getCampo()==field && p.getInizio().compareTo(time)==0) {
    			tv = true;
    		}
    	}
        return tv;
    }
    

    public int getOccupation(int field) {
    	int conta = 0;
    	for(Prenotazione p : prenotazioni) {
    		if(p.getCampo()==field) {
    			conta = conta + 1;
    		}
    	}
        return conta;
    }
    
    public List<FieldOption> findOptions(String time, Features required){
    	Map<Integer,Integer> mappa = new TreeMap<>();
    	List<Integer> corrisp = new ArrayList<>();
    	List<Integer> liberi = new ArrayList<>();
    	List<FieldOption> finale = new ArrayList<>();
    	boolean noBuono = false;
    	for(Campo c : campi) {
    		if(required.indoor==true) {
    			if(c.getCoperto()!=true) {
    				noBuono = true;
    			}
    		}
    		if(required.ac==true) {
    			if(c.getCondizionato()!=true) {
    				noBuono = true;
    			}
    		}
    		if(required.heating==true) {
    			if(c.getRiscaldato()!=true) {
    				noBuono = true;
    			}
    		}
    		if(!noBuono) {
    			corrisp.add(c.getCode());
    		}
    		noBuono = false;
    	}
    	boolean tv = false;
    	for(int i : corrisp) {
    		tv = false;
    		for(Prenotazione p : prenotazioni) {
    			if(p.getCampo() == i && p.getInizio().compareTo(time)==0) {
    				tv = true;
    			}
    		}
    		if(!tv) {
    			liberi.add(i);
    		}
    	}
    	for(int i : liberi) {
    		mappa.put(i, getOccupation(i));
    	}
    	
    	Map<Integer,Integer> sortedMap = new TreeMap<>(
                (s1,s2) -> {
                    int m1 = mappa.get(s1);
                    int m2 = mappa.get(s2);
                    if(Integer.compare(m2, m1)==0) {
                    	return Integer.compare(s1,s2); 
                    }else {
                    	return Integer.compare(m2, m1);
                    }
                  });
    	
    	sortedMap.putAll(mappa);
    	for(Integer i : sortedMap.keySet()) {
    		finale.add(new FOI(i,sortedMap.get(i)));
    	}
        return finale;
    }
    
    public long countServedAssociates() {
        int tot = 0;
    	for(Socio s : soci) {
    		for(Prenotazione p : prenotazioni) {
    			if(p.getSocio()==s.getCode()) {
    				tot++;
    				break;
    			}
    		}
    	}
    	return tot;
    }
    
    public Map<Integer,Long> fieldTurnover() {
    	Map<Integer,Long> mappa = new TreeMap<>();
    	for(Campo c : campi) {
    		Long tot = (long) 0;
    		for(Prenotazione p : prenotazioni) {
    			if(p.getCampo()==c.getCode()) {
    				tot++;
    			}
    		}
    		mappa.put(c.getCode(), tot);
    	}
        return mappa;
    }
    
    public double occupation() {
    	double tC = prenotazioni.size();
    	String[] orA = Apertura.split(":");
    	String[] orC = Chiusura.split(":");
    	
    	int a1 = Integer.parseInt(orA[0]);
    	int a2 = Integer.parseInt(orC[0]);
    	
    	double t = a2-a1;
    	
    	double b = t*campi.size();
    	
    	double finale = tC/b;
        return finale;
    }
    
 }
