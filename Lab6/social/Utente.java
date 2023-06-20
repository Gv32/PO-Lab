package social;

import java.util.ArrayList;
import java.util.List;

public class Utente {

	String UN;
	String Nome;
	String Cognome;
	List<Utente> Amici = new ArrayList<>();
	
	public Utente(String code, String name, String secondname) {
		UN = code;
		Nome = name;
		Cognome = secondname;
	}
	
	public String getcode() {
		return UN;
	}
	
	public String getnome() {
		return Nome;
	}
	
	public String getcognome() {
		return Cognome;
	}
	
	public void InserisciAmico(Utente amico) {
		Amici.add(amico);
	}
	
	public List<Utente> getAmici() {
		return Amici;
	}
	
	public int GNF() {
		return Amici.size();
	}
}
