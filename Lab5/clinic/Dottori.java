package clinic;

public class Dottori {

	String nome;
	String cognome;
	String codF;
	int num;
	String spec;
	
	public Dottori(String name, String Surname, String Code,int ID, String sp) {
		nome = name;
		cognome = Surname;
		codF = Code;
		num = ID;
		spec = sp;
	}
	
	public String getName() {
		return nome;
	}
	
	public String getLastName() {
		return cognome;
	}
	
	public String getCode() {
		return codF;
	}
	
	public int getID() {
		return num;
	}
	
	public String getsp() {
		return spec;
	}
}
