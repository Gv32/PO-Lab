package diet;

import java.time.LocalTime;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {
	
	Customer Cliente; 
	String RNome; 
	String Tempo;
	Takeaway cose;
	OrderStatus Status = OrderStatus.ORDERED;
	PaymentMethod Metodo = PaymentMethod.CASH;
	
	SortedMap<String, Number> dizElem = new TreeMap<>();
	
	public Order (Customer c, String nomeR, String time, List<String> orari) {
		Cliente = c;
		RNome=nomeR;
		String ricordaPrec="";
		int contaCicli=0;
		int flag=0;
		if (time.length()==4) {
			time='0'+time;
		}
		for (String elem : orari) {
			
			if (contaCicli%2 != 0) {
				if (time.compareTo(ricordaPrec)>0 && time.compareTo(elem)<0) {
					flag=1;
					Tempo=time;
				}
				ricordaPrec=elem;
			}
			
			else {
				ricordaPrec=elem;
			}
			contaCicli++;
		}
		
		if (flag==0) {
			
			if (time.compareTo(orari.get(0))<0) {
				Tempo=orari.get(0);
			}
			
			else if (time.compareTo(orari.get(orari.size()-1))>0) {
				Tempo=orari.get(0);
			}
			else {
				contaCicli=0;
				for (String elem : orari) {
					if (contaCicli%2==0 && time.compareTo(elem)<0 && flag==0) {
						Tempo=elem;
						flag=1;
						
					}
					contaCicli++;
				}
			}
			
		}
		
	}

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}
	
	@Override
	public String toString() {
		String stringa = "";
		if(Tempo.length() == 4) {
			Tempo = "0" + Tempo;
		}
		stringa = RNome + ", " + Cliente.getFirstName() + " " + Cliente.getLastName() + " : (" + Tempo + "):";
		for (String key : dizElem.keySet()) {
			stringa = stringa + "\n\t" + key + "->" + dizElem.get(key);
		}
		return stringa;
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		Metodo = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return Metodo;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		Status = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return Status;
	}
	
	public String GetNR() {
		return RNome;
	}

	public String getNomeC() {
		return Cliente.getFirstName();
	}
	
	public String getTempoArrivo() {
		return Tempo;
	}
	
	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		dizElem.put(menu, quantity);
		return this;
	}
	
}
