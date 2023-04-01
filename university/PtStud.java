package university;

public class PtStud {
	
	String nome,cognome;
	float punteggio;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void SetPt(String name, String cogn, float punteggio2) {
		nome = name;
		cognome = cogn;
		punteggio = punteggio2;
	}
	
	public String GetNomeStud() {
		return nome;
	}
	
	public String GetCognomeStud() {
		return cognome;
	}
	
	public float GetPunteggioStud() {
		return punteggio;
	}
	
}
