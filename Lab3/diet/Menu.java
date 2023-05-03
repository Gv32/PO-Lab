package diet;

import java.util.TreeMap;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {

	String nomeMenu;
	TreeMap<String, Number> dizElem = new TreeMap<>();
	Food cibi;
	
	public Menu(String name,Food robe) {
		nomeMenu = name;
		cibi = robe;
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
    	dizElem.put(recipe, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
    	dizElem.put(product,1);
		return this;
	}

	@Override
	public String getName() {
		return nomeMenu;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
	    double acc = 0;
	    double prop;
	    for (String key : dizElem.keySet()) {
	        if (cibi.DizRic.containsKey(key)) {
	            prop = cibi.DizRic.get(key).getCalories() * dizElem.get(key).doubleValue() / 100;
	            acc += prop;
	        } else if (cibi.DizPrec.containsKey(key)) {
	            prop = cibi.DizPrec.get(key).getCalories();
	            acc += prop;
	        }
	    }
	    
	    return acc;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
	    double acc = 0;
	    double prop;

	    for (String key : dizElem.keySet()) {
	        if (cibi.DizRic.containsKey(key)) {
	            prop = cibi.DizRic.get(key).getProteins() * dizElem.get(key).doubleValue() / 100;
	            acc += prop;
	        } else if (cibi.DizPrec.containsKey(key)) {
	            prop = cibi.DizPrec.get(key).getProteins();
	            acc += prop;
	        }
	    }
	    return acc;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double acc = 0;
	    double prop;
	    for (String key : dizElem.keySet()) {
	        if (cibi.DizRic.containsKey(key)) {
	            prop = cibi.DizRic.get(key).getCarbs() * dizElem.get(key).doubleValue() / 100;
	            acc += prop;
	        } else if (cibi.DizPrec.containsKey(key)) {
	            prop = cibi.DizPrec.get(key).getCarbs();
	            acc += prop;
	        }
	    }
	    
	    return acc;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double acc = 0;
	    double prop;
	    for (String key : dizElem.keySet()) {
	        if (cibi.DizRic.containsKey(key)) {
	            prop = cibi.DizRic.get(key).getFat() * dizElem.get(key).doubleValue() / 100;
	            acc += prop;
	        } else if (cibi.DizPrec.containsKey(key)) {
	            prop = cibi.DizPrec.get(key).getFat();
	            acc += prop;
	        }
	    }
	    return acc;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}