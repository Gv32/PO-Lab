package diet;


public class Customer {
	
	String nome;
	String Cognome;
	String Telefono;
	String Email;
	
	public Customer(String name, String Surname) {
		nome = name;
		Cognome = Surname;
	}
	
	public String getLastName() {
		return Cognome;
	}
	
	public String getFirstName() {
		return nome;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public String getPhone() {
		return Telefono;
	}
	
	public void SetEmail(String email) {
		Email = email;
	}
	
	public void setPhone(String phone) {
		Telefono = phone;
	}
	
	@Override
	public String toString() {
		String stringa = "";
		stringa = stringa + nome + " " + Cognome;
		return stringa;
	}
	
}
