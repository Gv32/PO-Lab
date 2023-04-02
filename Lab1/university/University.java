package university;
import java.util.logging.Logger;


public class University {

// R1
	String NomeU;
	String NomeR, ConR;
	Studente[] studenti = new Studente[100];
	Corso[] corsi = new Corso[100];
	int ids = 10000;
	int idc = 10;
	
	
	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		//TODO: to be implemented
		NomeU = name;
	}
	
	
	public String getName(){
		//TODO: to be implemented
		return NomeU;
	}
	
	public void setRector(String first, String last){
		//TODO: to be implemented
		NomeR = first;
		ConR = last;
	}
	
	
	public String getRector(){
		//TODO: to be implemented
		String Rett = NomeR + " " + ConR;
		return Rett;
	}
	
// R2
	
	public int enroll(String first, String last){
		//TODO: to be implemented
		studenti[ids-10000] = new Studente();
		studenti[ids-10000].RNome(first);
		studenti[ids-10000].RCognome(last);
		ids++;
		int idr = ids-1;
		logger.info("New student enrolled: " + ids + ", " + first + " " + last);
		return idr;
	}
	
	
	public String student(int id){
		//TODO: to be implemented
		String R = id + " " + studenti[id-10000].GetNome() + " " + studenti[id-10000].GetCognome();
		return R;
	}
	
// R3
	
	public int activate(String title, String teacher){
		corsi[idc-10] = new Corso();
		corsi[idc-10].NCorso(title);
		corsi[idc-10].NDocente(teacher);
		idc++;
		int idr = idc-1;
		logger.info("New course activated: " + idc + ", " + title + " " + teacher);
		return idr;
	}
	
	
	public String course(int code){
		//TODO: to be implemented
		String R = code + " " + corsi[code-10].GetCorso() + " " + corsi[code-10].GetDocente();
		return R;
	}
	
// R4
	
	public void register(int studentID, int courseCode){
		//TODO: to be implemented
		int risposta = studenti[studentID-10000].NewCorso(courseCode);
		if (risposta == -1) {
			System.out.println("Impossibile registrarsi");
		}
		int risposta2 = corsi[courseCode-10].NewStudente(studentID);
		if (risposta2 == -1) {
			System.out.println("Impossibile registrarsi");
		}
		logger.info("Student " + studentID + " signed up for course " + courseCode);
	}
	
	
	public String listAttendees(int courseCode){
		//TODO: to be implemented
		String StudentiF = corsi[courseCode-10].AllSt(studenti);
		return StudentiF;
	}

	
	public String studyPlan(int studentID){
		//TODO: to be implemented
		String CorsiF = studenti[studentID-10000].AllCr(corsi);
		return CorsiF;
	}

// R5
	
	public void exam(int studentId, int courseID, int grade) {
		studenti[studentId-10000].AddExam(studentId, courseID, grade);
		corsi[courseID-10].SetExam(studentId, courseID, grade);
		logger.info("Student " + studentId + " took an exam in course " + courseID + " whit grade: " + grade);
	}

	
	public String studentAvg(int studentId) {
		String Stringa = studenti[studentId-10000].StampaMedia(studentId);
		return Stringa;
	}
	
	
	public String courseAvg(int courseId) {
		String Stringa = corsi[courseId-10].StampaMediaC(corsi[courseId-10].NomeCorso);
		return Stringa;
	}
	

// R6
	
	public String topThreeStudents() {
		PtStud[] punteggi = new PtStud[ids-10000];
		int contasuperi = 0;
		PtStud comodo = new PtStud();
		int i = 0;
		for (i = 0; i<ids-10000 ;i++) {
			int conta = studenti[i].NES();
			contasuperi = contasuperi + conta;
		}
		for (i = 0; i<ids-10000 ;i++) {
			punteggi[i] = new PtStud();
			float punteggio = studenti[i].getPunteggio();
			String nome = studenti[i].GetNome();
			String cognome = studenti[i].GetCognome();
			punteggi[i].SetPt(nome,cognome,punteggio);
		}
		for(int k= 0;k<ids-10000;k++) {
			for(int j = 0;j<ids-10000;j++) {
				if(punteggi[k].punteggio > punteggi[j].punteggio) {
					
					comodo = punteggi[k];
					punteggi[k] = punteggi[j];
					punteggi[j] = comodo;
					
				}
			}
		}
		String stringa = "";
		if(contasuperi > 2) {
			for (int j = 0;j<3;j++) {
				stringa = stringa + punteggi[j].GetNomeStud() + " " + punteggi[j].GetCognomeStud() + " : " + punteggi[j].GetPunteggioStud() + "\n";
			}
		}
		if(contasuperi < 3) {
			for (int j = 0;j<contasuperi;j++) {
				stringa = stringa + punteggi[j].GetNomeStud() + " " + punteggi[j].GetCognomeStud() + " : " + punteggi[j].GetPunteggioStud() + "\n";
			}
		}
		return stringa;
	}

// R7
    
    private final static Logger logger = Logger.getLogger("University");
    
}
