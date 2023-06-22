package delivery;

public class Piatto {
	String nome;
	float prezzo;
	
	public Piatto(String n, float p) {
		nome = n;
		prezzo = p;
	}
	
	public String getNome() {
		return nome;
	}
	
	public float getPrezzo() {
		return prezzo;
	}

}
