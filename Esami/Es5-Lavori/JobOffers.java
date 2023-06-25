package jobOffers; 
import java.util.*;


public class JobOffers  {
	
	List <String> capacita = new ArrayList<>();
	List <PostoLavoro> lavori = new ArrayList<>();
	List <Candidato> candidati = new ArrayList<>();
	List <Proposta> proposte = new ArrayList<>();
	List <Consulente> consulenti = new ArrayList<>();
	List <Punteggio> punteggi = new ArrayList<>();

//R1
	public int addSkills (String... skills) {
		boolean trovato = false;
		for(String skil : skills) {
			for (String c : capacita) {
				if (c.compareTo(skil)==0) {
					trovato = true;
				}
			}
			if (trovato == false) {
				capacita.add(skil);
			}
			trovato = false;
		}
		
		return capacita.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		// controlli
		
		for (PostoLavoro l : lavori) {
			if (l.GetRuolo().compareTo(position)==0) {
				throw new JOException(position);
			}
		}
		
		boolean trovato = false;
		String s = "";
		for (String skill : skillLevels) {
			s = skill.split(":")[0];
			
			for (String c : capacita) {
				if (s.compareTo(c)==0) {
					trovato = true;
				}
			}
			
			if (trovato == false) {
				throw new JOException(skill);
			}
			trovato = false;
		}
		
		int numero ;
		for (String skill : skillLevels) {
			s = skill.split(":")[1];
			numero = Integer.parseInt(s);
			if (numero < 4 || numero > 8) {
				throw new JOException(skill);
			}
		}
		
		PostoLavoro pl = new PostoLavoro(position,skillLevels) ;
		lavori.add(pl);
		
		int media = 0;
		int conta = skillLevels.length;
		int acc=0;
		for (String ss : skillLevels) {
			s = ss.split(":")[1];
			numero = Integer.parseInt(s);
			acc = acc + numero;
		}
		
		media = acc/conta;
		return media;
		
		
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		//controlli
		for(Candidato c : candidati) {
			if (name.compareTo(c.GetNome())==0) {
				throw new JOException(c.GetNome());
			}
		}
		
		boolean trovato = false;
		String s = "";
		for (String skill : skills) {
			s = skill.split(":")[0];
			
			for (String c : capacita) {
				if (s.compareTo(c)==0) {
					trovato = true;
				}
			}
			
			if (trovato == false) {
				throw new JOException(skill);
			}
			trovato = false;
		}
		
		
		Candidato c = new Candidato(name,skills);
		candidati.add(c);
		
		return skills.length;
		
		
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		//controlli
		boolean trovato = false;
		
		for (Candidato c : candidati) {
			if(c.GetNome().compareTo(candidate)==0) {
				trovato = true;
			}
		}
		
		if (trovato == false) {
			throw new JOException(candidate);
		}
		
		trovato = false;
		for (String p1 : positions) {
			
			for (PostoLavoro p2: lavori) {
				
				if (p1.compareTo(p2.GetRuolo())==0) {
					trovato = true;
				}
			}
			
			if (trovato == false) {
				throw new JOException(p1);
			}
			trovato = false;
		}
		
		String [] skillRic;
		trovato = false;
		
		for (Candidato c : candidati) {
			
			if (c.GetNome().compareTo(candidate)==0) {
				
				for (String p : positions) {
					
					for(PostoLavoro l : lavori) {
						if (l.GetRuolo().compareTo(p)==0) {
							
							skillRic = l.GetSkill();
							
							for(String elem : skillRic) {
								
								for (String elem2 : c.GetSkills()) {
									
									if (elem.split(":")[0].compareTo(elem2)==0) {
										trovato = true;
									}
								}
								
								if (trovato == false) {
									throw new JOException(c.GetNome());
								}
								
								trovato = false;
							}
						}
					}
				}
			}
		}
		
		Proposta p1 = new Proposta( candidate,  positions);
		proposte.add(p1);
		
		List<String> lista = new ArrayList<>();
		String stringa = "";
		for (String p : positions) {
			
			
			stringa = candidate+":"+p;
			lista.add(stringa);
			
			
		}
		
		lista.sort( (s1,s2) -> {
			String nomeCandidato1 = s1.split(":")[0];
			String nomeCandidato2 = s2.split(":")[0];
			String lavoroCandidato1 = s1.split(":")[1];
			String lavoroCandidato2 = s2.split(":")[1];
			
			if (nomeCandidato1.compareTo(nomeCandidato2)==0) {
				return lavoroCandidato1.compareTo(lavoroCandidato2);
			}
			
			return nomeCandidato1.compareTo(nomeCandidato2);
		});
		
		return lista;
		
		
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		TreeMap<String, List<String>> mappa = new TreeMap();
		
		
		
		for (PostoLavoro pl : lavori ) {
			
			List<String> lista = new ArrayList <>();
			
			for (Proposta p : proposte) {
				
				for (String pos : p.GetPosizioni()) {
					
					if (pos.compareTo(pl.GetRuolo())==0) {
						lista.add(p.GetNome());
					}
				}
			}
			
			if (lista.size()!=0) {
				lista.sort((s1,s2) -> {
					return s1.compareTo(s2);
				});
				
				mappa.put(pl.GetRuolo(), lista);
			}
		}
		
		return mappa;
		
		
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		for (Consulente c : consulenti) {
			if (c.GetNome().compareTo(name)==0) {
				throw new JOException(c.GetNome());
			}
		}
		boolean trovato = false;
		for (String s1 : skills) {
			
			for (String s2 : capacita) {
				
				if (s1.compareTo(s2)==0) {
					trovato = true;
				}
			}
			
			if (trovato == false) {
				throw new JOException(s1);
			}
			trovato = false;
		}
		
		Consulente c = new Consulente (name,skills);
		consulenti.add(c);
		
		return skills.length;
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		boolean trovato = false;
		
		for (Consulente c : consulenti) {
			if(c.GetNome().compareTo(consultant)==0) {
				trovato = true;
			}
		}
		
		if (trovato == false) {
			throw new JOException(consultant);
		}
		
		trovato = false;
		for(Candidato c : candidati) {
			if(c.GetNome().compareTo(candidate)==0) {
				trovato = true;
			}
		}
		
		if (trovato == false) {
			throw new JOException(candidate);
		}
		
		trovato = false;
		for(Consulente c : consulenti) {
			
			if (c.GetNome().compareTo(consultant)==0) {
				
				String[] skills = c.GetSkills();
				
				for ( Candidato cand : candidati) {
					if (cand.GetNome().compareTo(candidate)==0) {
						String[] skills2 = cand.GetSkills();
						
						for (String s:skills2) {
							
							for (String s2 : skills) {
								
								if (s.compareTo(s2)==0) {
									trovato = true;
								}
							}
							
							if (trovato == false) {
								throw new JOException(consultant);
							}
							trovato = false;
						}
					}
				}
			}
		}
		
		String stringa = "";
		int n = 0;
		for (String s: skillRatings) {
			stringa = s.split(":")[1];
			n = Integer.parseInt(stringa);
			
			if (n<4 || n> 10) {
				throw new JOException(s);
			}
		}
		
		Punteggio p = new Punteggio (consultant, candidate, skillRatings);
		punteggi.add(p);
		
		int media = 0, conta = 0, acc = 0;
		for (String s: skillRatings) {
			stringa = s.split(":")[1];
			n = Integer.parseInt(stringa);
			
			conta++;
			acc = acc+n;
		}
		
		media = acc/conta;
		
		return media;
	}
	
//R4
	public List<String> discardApplications() {
		 List<String> scarti = new ArrayList <>();
		 String stringa;
		 boolean flag = false;
		 boolean flag2 = false;
		 String[] puntiCand = null;
		 String[] lavoriCand = null;
		 for (Candidato c : candidati) {
			 
			 for(Punteggio p : punteggi) {
				 if (p.GetCandidato().compareTo(c.GetNome())==0) {
					 puntiCand = p.GetValutazioni(); 
					 flag2 = true;
				 }
			 }
			 
			 if (flag2 == false) {
				 puntiCand = null;
				 
			 }
			 flag2 = false;
			 
			 for (Proposta p : proposte) {
				 if (p.GetNome().compareTo(c.GetNome())==0) {
					 if (puntiCand == null) {
						 for (String elem : p.GetPosizioni()) {
							 stringa = c.GetNome()+":"+elem;
							 scarti.add(stringa);
						 }
					 }
					 lavoriCand = p.GetPosizioni();
					 flag = true;
				 }
			 }
			 
			 if (flag == false) {
				 lavoriCand = null;
			 }
			 flag = false;
			 
			 if (lavoriCand!=null && puntiCand!=null) {
			 
				 for (String lavoro1 : lavoriCand) {
					 
					 for (PostoLavoro lavoro2 : lavori) {
						 
						 if(lavoro1.compareTo(lavoro2.GetRuolo())==0) {
							 
							 for (String punto1 : puntiCand) {
								 
								 for (String punto2 : lavoro2.GetSkill()) {
									 
									 if (punto1.split(":")[0].compareTo(punto2.split(":")[0])==0) {
										 
										 int n1 = Integer.parseInt(punto1.split(":")[1]);
										 int n2 = Integer.parseInt(punto2.split(":")[1]);
										 
										 if (n1<n2) {
											 stringa = c.GetNome()+":"+lavoro2.GetRuolo();
											 scarti.add(stringa);
										 }
									 }
								 }
							 }
						 }
					 }
					 
				 }
			 }
		 }
		 
		 scarti.sort((s1,s2) -> {
			 return s1.compareTo(s2);
		 });
		 
		 return scarti;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		List<String> lista = new ArrayList<>();
		String [] skillsC=null;
		String [] skillsP=null;
		List<String> scartati = null;
		String lavoro = "";
		
		boolean trovato = false;
		boolean scartato = false;
		
		for (PostoLavoro pl : lavori) {
			if (pl.GetRuolo().compareTo(position)==0) {
				skillsP = pl.GetSkill();
				
			}
		}
		
		for(Proposta p : proposte) {
			
			for (String l : p.GetPosizioni()) {
				
				if (l.compareTo(position)==0) {
			
					for (Candidato c : candidati) {
						if (c.GetNome().compareTo(p.GetNome())==0) {
							skillsC = c.GetSkills();
						}
					}
					
					for ( String s2 : skillsP) {
						
						for(String s : skillsC) {
							
							if (s.compareTo(s2.split(":")[0])==0) {
								trovato = true;
							}
						}
						
						if (trovato == false) {
							scartato = true;
						}
						
						trovato = false;
						
					}
					
					if (scartato == false) {
						scartati = this.discardApplications();
						
						for (String stringa : scartati) {
							if (stringa.split(":")[0].compareTo(p.GetNome())==0 && stringa.split(":")[1].compareTo(position)==0) {
								scartato = true;
							}
						}
						
						if (scartato == false) {
							lista.add(p.GetNome());
						}
					}
					scartato = false;
				}
			}
		}
		
		lista.sort((s1,s2) ->{
			return s1.compareTo(s2);
		});
		
		return lista;
	}
	

	
}

		