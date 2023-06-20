package social;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.util.*;
import javax.swing.*;


public class SocialGui extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;
	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	public JLabel completo ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;
	

	public SocialGui(Social m){
		
		
		setTitle("Login");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450,700);
		setVisible(true);
		
		//setLayout(new BorderLayout());
		name = new JLabel("Inserisci codice");
		
		id = new JTextField(20);
		
		login = new JButton("login");
		
		completo = new JLabel();
		
		friends = new JList<>();
		String [] vett = new String[1];
		vett[0] = "Friends";
		friends.setListData(vett);
		
		setLayout(new FlowLayout());
		add(name);
		add(id);
		add(login);
		
		add(friends);
		
		controller c = new controller(m,this);
		
		
	}
	
	public void aggiorna(boolean trovato, String codice, Social m) {
		
		remove(name);
		remove(friends);
		int i=0;
		List <String> lista = new ArrayList <>();
		String [] vett2 = new String[0];
		String nome="",cognome="";
		if (trovato == true) {
			for (Utente elem : m.utenti ) {
				if (elem.getcode().compareTo(codice)==0) {
					nome = elem.getnome();
					cognome = elem.getcognome();
					for (Utente u : elem.getAmici()) {
						lista.add(u.getcode());
						i++;
					}
					String [] vett = new String[i];
					i = 0;
					for (String ele : lista) {
						vett [i] = ele;
						i++;
					}
					
					String stringa = nome + " " +cognome;
					name = new JLabel(stringa);
					add(name);
					
					add(friends);
					friends.setListData(vett);
					
				}
			}
			
			
		}
		
		else {
			JDialog finestra = new JDialog();
			finestra.setTitle("Errore");
			finestra.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			finestra.setSize(400,700);
			finestra.setVisible(true);
			String stringa = "The user code is invalid!";
			name = new JLabel(stringa);
			
			
			login = new JButton("OK");
			
			finestra.setLayout(new FlowLayout());
		    finestra.add(name);
		    finestra.getRootPane().setDefaultButton(login);
		    finestra.add(login);
			
			
			
			
		}
	}


}
