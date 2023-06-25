package jobOffers;

public class Candidato {
	String nome;
	String[] skill;
	
	public Candidato (String n, String[] s) {
		nome = n;
		skill = s;
	}
	
	public String GetNome() {
		return nome;
	}
	
	public String[] GetSkills() {
		return skill;
	}
	
	
}