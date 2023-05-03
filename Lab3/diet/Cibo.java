package diet;

public class Cibo implements NutritionalElement{
	
	String nome;
	double Calorie;
	double Proteine;
	double Carboidrati;
	double Grassi;
	boolean centog;
	
	public Cibo(String name, double cal, double prot, double carb, double gras, boolean cento){
		nome = name;
		Calorie = cal;
		Proteine = prot;
		Carboidrati = carb;
		Grassi = gras;
		centog = cento;
	}
	
	@Override
	public String getName() {
		return nome;
	}

	@Override
	public double getCalories() {
		return Calorie;
	}

	@Override
	public double getProteins() {
		return Proteine;
	}

	@Override
	public double getCarbs() {
		return Carboidrati;
	}

	@Override
	public double getFat() {
		return Grassi;
	}

	@Override
	public boolean per100g() {
		return centog;
	}

	

}
