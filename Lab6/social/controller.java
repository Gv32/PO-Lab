package social;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class controller {
	
	public controller(Social m, SocialGui w) {
		// quando si preme sul buttone
		w.login.addActionListener(
				evt -> {
					String codice = w.id.getText();
					
					//aggiorna la wiew
					boolean trovato = false;
					for (Utente elem : m.utenti) {
						if (codice.compareTo(elem.getcode())==0) {
							trovato = true;
						}
					}
					
					w.aggiorna(trovato,codice,m);
				}
				);
		
		
		
		w.id.addKeyListener( new KeyAdapter() {
			
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String codice = w.id.getText();
					
					//aggiorna la wiew
					boolean trovato = false;
					for (Utente elem : m.utenti) {
						if (codice.compareTo(elem.getcode())==0) {
							trovato = true;
						}
					}
					
					w.aggiorna(trovato,codice,m);
					
				}
			}
			}
			
		);
		
		
	}
}
