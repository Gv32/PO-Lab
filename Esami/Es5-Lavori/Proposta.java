package jobOffers;

public class Proposta {
	String nome;
	String [] posizioni;
	
	public Proposta (String n, String[] p) {
		nome = n;
		posizioni = p;
	}
	
	public String GetNome() {
		return nome;
	}
	
	public String[] GetPosizioni() {
		return posizioni;
	}
}