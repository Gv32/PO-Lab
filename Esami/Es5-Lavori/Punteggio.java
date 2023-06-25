package jobOffers;

public class Punteggio {
	String consulente;
	String candidato;
	String [] valutazioni;
	
	public Punteggio (String cons, String can, String[] v) {
		consulente = cons;
		candidato = can;
		valutazioni = v;
	}
	
	public String GetConsulente() {
		return consulente;
	}
	
	public String GetCandidato() {
		return candidato;
	}
	
	public String[] GetValutazioni() {
		return valutazioni;
	}
}