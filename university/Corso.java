package university;

public class Corso {
	
	String NomeCorso,Docente;
	int NStudenti;
	int[] IDFreq = new int[100];
	String Alunni;
	Esame[] EsamiS = new Esame[1000];
	int IdS = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public void NDocente(String Nome) {
		Docente = Nome;
	}
	
	public void NCorso(String Nome) {
		NomeCorso = Nome;
	}
	
	public String GetDocente() {
		return Docente;
	}
	
	public String GetCorso() {
		return NomeCorso;
	}

	public int NewStudente(int id) {
		if(NStudenti >= 100) {
			System.out.println("Impossibile aggiungere lo studente, nessun posto disponibile");
			return -1;
		}
		IDFreq[NStudenti] = id;
		NStudenti++;
		return 1;
	}
	
	public String stampaCorso() {
		String Corso = NomeCorso + "," + Docente;
		return Corso;
	}
	
	public String AllSt(Studente[] studenti) {
		String Alunni = "";
		for (int i = 0; i < NStudenti;i++) {
			Alunni = Alunni + IDFreq[i] + " " + studenti[IDFreq[i]-10000].stampaStud() + "\n";
		}
		return Alunni;
	}
	
	public void SetExam(int id, int codC, int voto) {
		EsamiS[IdS] = new Esame();
		EsamiS[IdS].AExam(id, codC, voto);
		IdS = IdS + 1;
	}
	
	public String StampaMediaC(String id) {
		String esamiS;
		float somma = 0;
		if (IdS == 0) {
			esamiS = "No student has taken the exam in " + id;
			return esamiS;
		}else {
			for(int i = 0; i < IdS;i++) {
				somma = EsamiS[i].Voto + somma;
			}
			float media = somma / IdS;
			esamiS = "The average for the course " + id + " is: " + media;
			return esamiS;
		}
		
	}
	
}
