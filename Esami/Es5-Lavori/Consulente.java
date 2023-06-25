package jobOffers;

public class Consulente {
	String nome;
	String[] skills;
	
	public Consulente (String n, String[] s) {
		nome = n;
		skills = s;
	}
	
	public String GetNome() {
		return nome;
	}
	
	public String[] GetSkills() {
		return skills;
	}
}
