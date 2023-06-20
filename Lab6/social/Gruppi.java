package social;

import java.util.ArrayList;
import java.util.List;

public class Gruppi {

	String nome;
	List<Utente> partecipanti = new ArrayList<>();

	public Gruppi(String name){
		nome = name;
	}
	
	public String getName() {
		return nome;
	}
	
	public void addP(Utente nome) {
		partecipanti.add(nome);
	}
	
	public List<Utente> getP(){
		return partecipanti;
	}
	
	public int getNP() {
		return partecipanti.size();
	}
}
