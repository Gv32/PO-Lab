package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	String NomeR;
	Optional<Integer> alt;
	String cat;
	Integer bed;
	Municipality mun;
	
	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		NomeR = name;
		alt = Optional.ofNullable(altitude);
		cat = category;
		bed = bedsNumber;
		mun = municipality;
	}

	public String getName() {
		return NomeR;
	}

	public Optional<Integer> getAltitude() {
		return alt;
	}

	public String getCategory() {
		return cat;
	}

	public Integer getBedsNumber() {
		return bed;
	}

	public Municipality getMunicipality() {
		return mun;
	}
	
	public String getProvince() {
		return mun.getProvince();
	}
	
	public String getMunicipalityName() {
		return mun.getName();
	}
}
