package university;

public class Studente {
	
	String NomeS,CognomeS;
	int ICorsi = 0;
	int[] CorsiF = new int[25];
	String CorsiR;
	Esame[] esami = new Esame[20];
	int IdE = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public void RNome(String Nome) {
		NomeS = Nome;
	}
	
	public void RCognome(String Cognome) {
		CognomeS = Cognome;
	}
	
	public String GetNome() {
		return NomeS;
	}
	
	public String GetCognome() {
		return CognomeS;
	}

	public int NewCorso(int IDCorso) {
		if(ICorsi >= 25) {
			System.out.println("Impossibile aggiungere il corso, troppi corsi selezionati");
			return -1;
		}
		CorsiF[ICorsi] = IDCorso;
		ICorsi++;
		return 1;
	}
	
	public String stampaStud() {
		String Studente = NomeS + " " + CognomeS;
		return Studente;
	}
	
	public String AllCr(Corso[] corsi) {
		String CorsiR = "";
		for (int i = 0; i < ICorsi;i++) {
			CorsiR = CorsiR + CorsiF[i] + "," +corsi[CorsiF[i]-10].stampaCorso() + "\n";
		}
		return CorsiR;
	}
	
	public void AddExam(int id, int codC, int voto) {
		esami[IdE] = new Esame();
		esami[IdE].AExam(id, codC, voto);
		IdE = IdE + 1;
	}
	
	public String StampaMedia(int id) {
		String esamiS;
		float somma = 0;
		if (IdE == 0) {
			esamiS = "Student " + id + " hasn't taken any exams";
			return esamiS;
		}else {
			for(int i = 0; i < IdE;i++) {
				somma = esami[i].Voto + somma;
			}
			float media = somma / IdE;
			esamiS = "Student " + id + " : " + media;
			return esamiS;
		}
	}
	
	public float getPunteggio() {
		float punteggio = 0;
		if (IdE == 0) {
			return punteggio;
		}else {
			int somma = 0;
			for(int i = 0; i < IdE;i++) {
				somma = esami[i].Voto + somma;
			}
			int media = somma / IdE;
			punteggio = media + (IdE/ICorsi) * 10; 
			return punteggio;
			
		}
		
	}
	
}
