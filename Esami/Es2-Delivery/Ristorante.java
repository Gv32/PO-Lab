package delivery;

import java.util.ArrayList;
import java.util.List;

public class Ristorante {
	String nome;
	String cat;
	List<Piatto> piatti = new ArrayList<>();
	List<Integer> voto = new ArrayList<>();
	
	public Ristorante(String n, String c) {
		nome = n;
		cat = c;
	}

	public String getNome() {
		return nome;
	}
	
	public String getCategoria() {
		return cat;
	}
	
	public void addPiatto(String n, float p) {
		Piatto nuovo = new Piatto(n,p);
		piatti.add(nuovo);
	}
	
	public List<Piatto> getPiatti(){
		return piatti;
	}
	
	public void setVoto(int v) {
		if(v<=5 & v>=1) {
			voto.add(v);
		}
	}
	
	public List<Integer> getVoti(){
		return voto;
	}

}