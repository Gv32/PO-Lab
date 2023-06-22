package delivery;

public class Ordini {

	String[] Piatti;
	int[] quantita;
	String nomeCliente;
	String nomeRistorante;
	int tempoConsegna;
	int distanza;
	boolean fatto = false;
	
	public Ordini(String p[], int[] q, String nomeC, String nomeR, int tempo, int dist) {
		Piatti = p;
		quantita = q;
		nomeCliente = nomeC;
		nomeRistorante = nomeR;
		tempoConsegna = tempo;
		distanza = dist;
	}
	
	public void setFatto() {
		fatto = true;
	}
	
	public int getDistanza() {
		return distanza;
	}
	
	public int GetTempoConsegna() {
		return tempoConsegna;
	}
	
	public boolean GetFatto() {
		return fatto;
	}
	
	public String getNRistorante() {
		return nomeRistorante;
	}
}
