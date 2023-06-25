package jobOffers;
import java.util.*;

public class PostoLavoro {
	String ruolo;
	String[] skill;
	
	public PostoLavoro(String r,String[] s) {
		ruolo = r;
		skill = s;
	}
	
	public String GetRuolo(){
		return ruolo;
	}
	
	public String[] GetSkill() {
		return skill;
	}
}