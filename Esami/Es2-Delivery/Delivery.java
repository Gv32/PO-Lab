package delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class Delivery {
	// R1
	List<Categoria> categorie = new ArrayList<>();
	List<Ristorante> ristoranti = new ArrayList<>();
	List<Ordini> ordini = new ArrayList<>();
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		for(Categoria c : categorie) {
			if(c.getNome().compareTo(category)==0) {
				throw new DeliveryException();
			}
		}
		Categoria nuova = new Categoria(category);
		categorie.add(nuova);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		List<String> l2 = new ArrayList<>();
		for(Categoria c : categorie) {
			l2.add(c.getNome());
		}
		return l2;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		boolean tv = false;
		for(Categoria c : categorie) {
			if(c.getNome().compareTo(category)==0) {
				tv = true;
			}
		}
		if(!tv) {
			throw new DeliveryException();
		}
		Ristorante nuovo = new Ristorante(name,category);
		ristoranti.add(nuovo);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		List<String> l2 = new ArrayList();
		for(Ristorante c : ristoranti) {
			if(c.getCategoria().compareTo(category)==0) {
				l2.add(c.getNome());
			}
		}
		Collections.sort(l2,new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
        return l2;
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		boolean tv = false;
		for(Ristorante r : ristoranti) {
			if(r.getNome().compareTo(restaurantName)==0) {
				for(Piatto p : r.getPiatti()) {
					if(p.getNome().compareTo(name)==0) {
						tv = true;
					}
				}
				if(!tv) {
					r.addPiatto(name,price);
				}
				else {
					throw new DeliveryException();
				}
			}
			tv = false;
		}
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String,List<String>> mappa = new TreeMap<>();
		for(Ristorante r : ristoranti) {
			List<String> l2 = new ArrayList<>();
			for(Piatto p : r.getPiatti()) {
				if(p.getPrezzo() <= maxPrice && p.getPrezzo() >= minPrice) {
					l2.add(p.getNome());
				}
			}
			if(l2.size()>0) {
				mappa.put(r.getNome(), l2);
			}
		}
		return mappa;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		List<String> l2 = new ArrayList<>();
		for(Ristorante r : ristoranti) {
			if(r.getNome().compareTo(restaurantName)==0) {
				for(Piatto p : r.getPiatti()) {
					l2.add(p.getNome());
				}
			}
		}
		Collections.sort(l2,new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
        return l2;
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		Set<String> set = new HashSet<>();
		List<String> l2 = new ArrayList<>();
		for(Ristorante r : ristoranti) {
			if(r.getCategoria().compareTo(category)==0) {
				for(Piatto p : r.getPiatti()) {
					set.add(p.getNome());
				}
			}
		}
		for(String s : set) {
			l2.add(s);
		}
		Collections.sort(l2,new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		return l2;
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		Ordini nuovo = new Ordini(dishNames,quantities,customerName,restaurantName,deliveryTime,deliveryDistance);
		ordini.add(nuovo);
		return ordini.size();
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		List<Integer> l2 = new ArrayList<>();
		int i = 1;
		for(Ordini o : ordini) {
			if (o.getDistanza()<=maxDistance && o.GetTempoConsegna()==deliveryTime && o.GetFatto()==false) {
        		l2.add(i);
        		o.setFatto();
        	}
			if(i==maxOrders) {
				break;
			}
			i++;
		}
        return l2;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		int conta = 0;
		for(Ordini o : ordini) {
			if(o.GetFatto()==false) {
				conta++;
			}
		}
        return conta;
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		for(Ristorante r : ristoranti) {
			if(r.getNome().compareTo(restaurantName)==0) {
				r.setVoto(rating);
			}
		}
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        Map <String,Float> mappa = new TreeMap <>();
        List <Integer> valutazioni = null;
        float n=0,acc=0,media=0;
        
        for (Ristorante ris : ristoranti) {
          valutazioni = ris.getVoti();
          
          n = valutazioni.size();
          for(Integer elem : valutazioni) {
            acc=acc+elem;
          }
          
          if (n!=0) {
            media = acc/n;
            mappa.put(ris.getNome(), media);
          }
          acc=0;
          
        }
        
        
        SortedMap <String,Float> sortedMap = new TreeMap<>(
            (s1,s2) -> {
              float m1 = mappa.get(s1);
              float m2 = mappa.get(s2);
              return Float.compare(m2, m1);
            });
        
        
        sortedMap.putAll(mappa);
        
        System.out.println(mappa);
        System.out.println(sortedMap);
        
        List<String> finito = new ArrayList<>();
        for(String elem : sortedMap.keySet()) {
          finito.add(elem);
        }
        
        return finito;
  }
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		Map<String,Long> mappa = new TreeMap<>();
		int conta = 0;
		for(Categoria c : categorie) {
			for(Ordini o : ordini) {
				for(Ristorante r : ristoranti) {
					if(r.getNome().compareTo(o.getNRistorante())==0 && c.getNome().compareTo(r.getCategoria())==0) {
						conta ++;
					}
				}
			}
			mappa.put(c.getNome(), (long) conta);
			conta = 0;
		}
        return mappa;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
		Map<String,Float> mappa = new TreeMap<>();
        List <Integer> valutazioni = null;
        float n=0,acc=0,media=0;
        
        for (Ristorante ris : ristoranti) {
          valutazioni = ris.getVoti();
          
          n = valutazioni.size();
          for(Integer elem : valutazioni) {
            acc=acc+elem;
          }
          
          if (n!=0) {
            media = acc/n;
            mappa.put(ris.getNome(), media);
          }
          acc=0;
          
        }
        
        
        SortedMap <String,Float> sortedMap = new TreeMap<>(
            (s1,s2) -> {
              float m1 = mappa.get(s1);
              float m2 = mappa.get(s2);
              return Float.compare(m2, m1);
            });
        
        
        sortedMap.putAll(mappa);
        
        String s = "";
        for(String elem:sortedMap.keySet()) {
        	s = s + elem;
        	break;
        }
        return s;
        
        
	}
}
