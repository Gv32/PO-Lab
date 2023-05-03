package diet;

import java.util.TreeMap;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	String nomeRicetta;
	Food cibi;
	TreeMap<String, Number> dizElem = new TreeMap<>();
	
	public Recipe(String name, Food robe) {
		nomeRicetta = name;
		cibi = robe;
	}

	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		dizElem.put(material, quantity);
		return this;
	}

	@Override
	public String getName() {
		return nomeRicetta;
	}

	
	public double getCalories() {
		double acc=0;
		double prop;
		double peso=0;
		for(String key : dizElem.keySet()) {
			peso = peso + dizElem.get(key).doubleValue();
			prop = dizElem.get(key).doubleValue()/100;
			acc=acc+(cibi.DizPrime.get(key).getCalories()*prop);
		}
		return acc*100/peso;
	}
	

	@Override
	public double getProteins() {
		double acc=0;
		double prop=0;
		double peso=0;
		for(String key : dizElem.keySet()) {
			peso = peso + dizElem.get(key).doubleValue();
			prop = dizElem.get(key).doubleValue()/100;
			acc=acc+cibi.DizPrime.get(key).getProteins()*prop;
		}
		return acc*100/peso;
	}

	@Override
	public double getCarbs() {
		double acc=0;
		double prop;
		double peso=0;
		for(String key : dizElem.keySet()) {
			peso = peso + dizElem.get(key).doubleValue();
			prop = dizElem.get(key).doubleValue()/100;
			acc=acc+cibi.DizPrime.get(key).getCarbs()*prop;
		}
		return acc*100/peso;
	}

	@Override
	public double getFat() {
		double acc=0;
		double prop;
		double peso=0;
		for(String key : dizElem.keySet()) {
			peso = peso + dizElem.get(key).doubleValue();
			prop = dizElem.get(key).doubleValue()/100;
			acc=acc+cibi.DizPrime.get(key).getFat()*prop;
		}
		return acc*100/peso;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
}
