package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */


public class Region {

	String nome;
	List<String> altitudini = new ArrayList<>();
	Map<String,Municipality> Comuni = new TreeMap<>();
	Map<String,MountainHut> Rifugi = new TreeMap<>();
	
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		nome = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return nome;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String elem : ranges) {
			altitudini.add(elem);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		String vet[] = new String[2];
		String vuota="0-INF";
		for(String elem : altitudini) {
			vet = elem.split("-");
			if (Integer.parseInt(vet[0]) <= altitude && Integer.parseInt(vet[1]) >= altitude){
				return elem;
			}
		}
		return vuota;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return Comuni.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return Rifugi.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		for(String elem : Comuni.keySet()) {
			if ( elem == name) {
				return Comuni.get(elem);
			}
		}
		Municipality comune = new Municipality(name,province, altitude);
		Comuni.put(name, comune);
		return comune;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		for(String elem : Rifugi.keySet()) {
			if(elem == name) {
				return Rifugi.get(elem);
			}
		}
		MountainHut Rifugio = new MountainHut(name, null, category, bedsNumber, municipality);
		Rifugi.put(name, Rifugio);
		return Rifugio;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		for(String elem : Rifugi.keySet()) {
			if(elem == name) {
				return Rifugi.get(elem);
			}
		}
		MountainHut Rifugio = new MountainHut(name, altitude, category, bedsNumber, municipality);
		Rifugi.put(name, Rifugio);
		return Rifugio;
	
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region Regione = new Region(name);
		List<String> FileC = new ArrayList<>();
		FileC = readData(file);
		
		String riga[] = new String[7];
		
		int conta = 0;
		
		for(String elem : FileC) {
			if(conta != 0) {
				riga=elem.split(";");
				Municipality a = Regione.createOrGetMunicipality(riga[1], riga[0], Integer.parseInt(riga[2]));
				if (riga[4] == "") {
					Regione.createOrGetMountainHut(riga[3], riga[5], Integer.parseInt(riga[6]), a);
				}else {
					Regione.createOrGetMountainHut(riga[3], Integer.parseInt(riga[4]), riga[5], Integer.parseInt(riga[6]), a);
				}
			}else {
				conta++;
			}
		}
		return Regione;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
	    return Comuni.values()
	            .stream()
	            .collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return Rifugi.values()
	            .stream()
	            .collect(Collectors.groupingBy(MountainHut::getProvince,Collectors.groupingBy(MountainHut::getMunicipalityName, Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
        Map<String, Long> countsByAltitudeRange = Rifugi.values().stream()
                .collect(Collectors.groupingBy(rifugio -> {
                    Integer altitude = rifugio.getAltitude().orElseGet(() ->
                            rifugio.getMunicipality().getAltitude());
                    return getAltitudeRange(altitude);
                }, Collectors.counting()));
        return countsByAltitudeRange;
    }

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
	    Map<String, Integer> mappa = Rifugi.values().stream()
	        .collect(Collectors.toMap(
	            MountainHut::getProvince, 
	            MountainHut::getBedsNumber,
	            Integer::sum)
	        );
	    return mappa;
	}
	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
	    Map<String, Optional<Integer>> countsByAltitudeRange = Rifugi.values().stream()
	        .collect(Collectors.groupingBy(rifugio -> getAltitudeRange(rifugio.getAltitude()
	                .orElseGet(() -> rifugio.getMunicipality().getAltitude())),
	                Collectors.mapping(MountainHut::getBedsNumber,
	                        Collectors.reducing(0, Integer::max))))
	        .entrySet().stream()
	        .collect(Collectors.toMap(Map.Entry::getKey, e -> Optional.ofNullable(e.getValue())));
	    return countsByAltitudeRange;
	}





	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
	    Map<Long, List<String>> result = Rifugi.values().stream()
	            .collect(Collectors.groupingBy(MountainHut::getMunicipalityName,
	                    Collectors.counting()))
	            .entrySet().stream()
	            .collect(Collectors.groupingBy(Map.Entry::getValue,
	                    Collectors.mapping(Map.Entry::getKey,
	                            Collectors.collectingAndThen(Collectors.toList(), list -> {
	                                Collections.sort(list);
	                                return list;
	                            }))));
	    return result;
	}


}
