package social;

import java.util.*;


public class Social {
	
	List<Utente> utenti = new ArrayList<>();
	List<Gruppi> grp = new ArrayList<>();
	
	
	
	public void addPerson(String code, String name, String surname)
			throws PersonExistsException {
		boolean trovato = false;
		Utente persona = new Utente(code,name,surname);
		for (Utente elem : utenti) {
			if(elem.getcode().compareTo(code)==0) {
				trovato = true;
			}
		}
		if(trovato == true) {
			throw new PersonExistsException();
		}else {
			utenti.add(persona);
		}

	}


	public String getPerson(String code) throws NoSuchCodeException {
		String stringa = "";
		boolean trovato = false;
		for(Utente elem : utenti) {
			if(elem.getcode().compareTo(code)==0) {
				trovato = true;
				stringa = elem.getcode() + " " + elem.getnome() + " " + elem.getcognome();
			}
		}
		if(!trovato) {
			throw new NoSuchCodeException();
		}
		return stringa;
	}


	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {
		boolean tv1 = false, tv2 = false;
		Utente u1 = null, u2 = null;
		for(Utente elem1 : utenti) {
			if(elem1.getcode().compareTo(codePerson1)==0) {
				tv1 = true;
				u1 = elem1;
			}
		}
		for(Utente elem2 : utenti) {
			if(elem2.getcode().compareTo(codePerson2)==0) {
				tv2 = true;
				u2 = elem2;
			}
		}
		if(tv1 == false || tv2 == false) {
			throw new NoSuchCodeException();
		}else {
			u1.InserisciAmico(u2);
			u2.InserisciAmico(u1);
		}
	}


	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {
		Collection<String> collezione = new ArrayList<>();
		boolean trovato = false;
		for(Utente pers:utenti) {
			if(pers.getcode().compareTo(codePerson)==0) {
				trovato=true;
				for(Utente p:pers.getAmici()) {
					collezione.add(p.getcode());
				}
			}
		}
		if(!trovato) {
			throw new NoSuchCodeException();
		}else {
			return collezione;
		}
	}


	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {
		Collection<String> collezione = new ArrayList<>();
		boolean trovato = false;
		for(Utente pers:utenti) {
			if(pers.getcode().compareTo(codePerson)==0) {
				trovato=true;
				for(Utente p:pers.getAmici()) {
					for(Utente p2 : p.getAmici()) {
						if(p2.getcode().compareTo(codePerson)!=0) {
							collezione.add(p2.getcode());
						}
					}
				}
			}
		}
		if(!trovato) {
			throw new NoSuchCodeException();
		}else {
			return collezione;
		}
	}

	
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
		Set<String> collezione = new HashSet<>();
		Collection<String> collezione2 = new ArrayList<>();
		boolean trovato = false;
		for(Utente pers:utenti) {
			if(pers.getcode().compareTo(codePerson)==0) {
				trovato=true;
				for(Utente p:pers.getAmici()) {
					for(Utente p2 : p.getAmici()) {
						if(p2.getcode().compareTo(codePerson)!=0) {
							collezione.add(p2.getcode());
						}
					}
				}
			}
		}
		if(!trovato) {
			throw new NoSuchCodeException();
		}else {
			collezione2 = collezione;
			return collezione2;
		}
	}


	public void addGroup(String groupName) {
		Gruppi gruppo = new Gruppi(groupName);
		grp.add(gruppo);
	}


	public Collection<String> listOfGroups() {
		Collection<String> lista = new ArrayList<>();
		for (Gruppi g : grp) {
			lista.add(g.getName());
		}
		return lista;
	}


	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		boolean tv1 = false, tv2 = false;
		Utente ut = null;
		for(Utente u : utenti) {
			if(u.getcode().compareTo(codePerson)==0) {
				ut = u;
				tv1 = true;
			}
		}
		if(tv1 == true) {
			for(Gruppi g : grp) {
				if(g.getName().compareTo(groupName)==0) {
					g.addP(ut);
					tv2 = true;
				}
			}
			if(!tv2) {
				throw new NoSuchCodeException();
			}
		}else {
			throw new NoSuchCodeException();
		}
	}


	public Collection<String> listOfPeopleInGroup(String groupName) {
		Collection<String> lista= new ArrayList<>();
		List<Utente> l2 = new ArrayList<>();
		for(Gruppi g:grp) {
			if(g.getName().compareTo(groupName)==0) {
				l2 = g.getP();
			}
		}
		for(Utente u:l2) {
			lista.add(u.getcode());
		}
		return lista;
	}


	public String personWithLargestNumberOfFriends() {
		String persona = "";
		int amici=0;
		for(Utente u:utenti) {
			if(u.GNF() >= amici) {
				amici = u.GNF();
				persona = u.getcode();
			}
		}
		return persona;
	}


	public String personWithMostFriendsOfFriends() {
		String persona = "";
		int amici=0;
		int max = 0;
		for(Utente u:utenti) {
			for(Utente u2 : u.getAmici()) {
				amici = amici + u2.GNF();
			}
			if(amici >= max) {
				max = amici;
				persona = u.getcode();
			}
			amici = 0;
		}
		return persona;
	}


	public String largestGroup() {
		int max = 0;
		String lista = "";
		for(Gruppi g : grp) {
			if(g.getNP()>=max) {
				max = g.getNP();
				lista = g.getName();
			}
		}
		return lista;
	}


	public String personInLargestNumberOfGroups() {
		String nome = "";
		int max = 0;
		int conta = 0;
		for(Utente u : utenti) {
			for(Gruppi g : grp) {
				for(Utente u2 : g.getP()) {
					if(u.getcode().compareTo(u2.getcode())==0) {
						conta = conta + 1;
					}
				}
			}
			if(conta > max) {
				max = conta;
				nome = u.getcode();
			}
			conta = 0;
		}
		return nome;
	}
}