package clinic;

public class Pazienti {

	String nome;
	String cognome;
	String codF;
	
	public Pazienti(String name, String Surname, String Code) {
		nome = name;
		cognome = Surname;
		codF = Code;
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
	
}
