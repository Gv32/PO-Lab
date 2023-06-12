package mountainhuts;

/**
 * Class representing a municipality that hosts a mountain hut.
 * It is a data class with getters for name, province, and altitude
 * 
 */
public class Municipality {
	String NomeC;
	String ProvC;
	Integer alt;
	
	public Municipality(String name, String province, Integer altitude) {
		NomeC = name;
		ProvC = province;
		alt = altitude;
	}

	public String getName() {
		return NomeC;
	}

	public String getProvince() {
		return ProvC;
	}

	public Integer getAltitude() {
		return alt;
	}

}
