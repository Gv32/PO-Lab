package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {
	
	Food f;
	SortedMap<String, Restaurant> DizRes = new TreeMap<>();
	List<Customer> DizCli = new ArrayList<>();
	
	List<Order> ListOrd = new ArrayList<>();
	
	
	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		f = food;
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant rest = new Restaurant(restaurantName, ListOrd);
		DizRes.put(restaurantName, rest);
		return rest;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return DizRes.keySet();
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer cl = new Customer(firstName,lastName);
		cl.setPhone(phoneNumber);
		cl.SetEmail(email);
		DizCli.add(cl);
		return cl;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		
		Comparator<Customer> bySurname = Comparator.comparing(Customer::getLastName);
		Comparator<Customer> byName = Comparator.comparing(Customer::getFirstName);
		 
		Collections.sort(DizCli, bySurname.thenComparing(byName));
		return DizCli;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Order o = new Order(customer,restaurantName,time,DizRes.get(restaurantName).orari);
		ListOrd.add(o);
		return o;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		int i=0;
		String ricorda="";
		List<Restaurant> listaAperti= new ArrayList<>();
		
		for (String key : DizRes.keySet()) {
			for (String orario : DizRes.get(key).orari) {
				if(i%2!=0) {
					if(ricorda.compareTo(time)<=0 && orario.compareTo(time)>0) {
						listaAperti.add(DizRes.get(key));
					}
					i+=1;
				}
				else {
					ricorda=orario;
					i+=1;
				}
			}
		}
		return listaAperti;
	}
}
