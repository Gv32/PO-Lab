package diet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	String nomeRistorante;
	List<String> orari = new ArrayList<>();
	List<Menu> listaMenu = new ArrayList<>();
	
	List<Order> listaOrdini = new ArrayList<>();
	
	public Restaurant(String Nome, List<Order> l) {
		nomeRistorante = Nome;
		listaOrdini = l;
	}
	
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return nomeRistorante;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		for(String elem : hm) {
			orari.add(elem);
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		LocalTime checkTime = LocalTime.parse(time);
	    for (int i = 0; i < orari.size(); i += 2) {
	        LocalTime openTime = LocalTime.parse(orari.get(i));
	        LocalTime closeTime = LocalTime.parse(orari.get(i+1));
	        if (checkTime.isAfter(openTime) && checkTime.isBefore(closeTime)) {
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		listaMenu.add(menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		 for (Menu menu : listaMenu) {
			 if (menu.getName().equals(name)) {
				 return menu;
			 }
		 }
		 return null;
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		String s = "";
		
		Comparator<Order> byNameRes= Comparator.comparing(Order::GetNR);
		Comparator<Order> byNameCli = Comparator.comparing(Order::getNomeC); 
		Comparator<Order> byOrarioC = Comparator.comparing(Order::getTempoArrivo); 
		
		Collections.sort(listaOrdini, byNameRes.thenComparing(byNameCli).thenComparing(byOrarioC));
		
		for(Order o : listaOrdini) {
			if(o.GetNR() == nomeRistorante && o.getStatus() == status) {
				s = s + o.toString();
				s = s + "\n";
			}
		}
		return s;
	}
}
