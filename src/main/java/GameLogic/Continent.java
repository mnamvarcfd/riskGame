 package GameLogic;

import java.util.ArrayList;

/**
 * This class maintains all the data members and methods related to each continent.
 */
public class Continent {
	
    String name, color;
    Integer number,controlValue;
    String owner = "";
   
   /**
    *  
    * @param number Number of the continent
    * @param name Name of the continent
    * @param controlValue An integer corresponding to the control value of the continent
    * @param color Name of the color
    */
    public Continent(Integer number, String name, Integer controlValue, String color) {
        this.number= number;
        this.name = name;
        this.color = color;
        this.controlValue = controlValue;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getControlValue() {
        return controlValue;
    }

    public void setControlValue(Integer controlValue) {
        this.controlValue = controlValue;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getOwner() {
    	return owner;
    }
    public void setOwner(String owner) {
    	this.owner = owner;
    }

    /**
	 * This checks whether a continent exist or not in Database.continentList
	 *
	 * @param continentToCheck Name of continent to be checked
	 * @return true(If continent is found) or false(IF continent is not found)
	 */
    public static boolean checkExistenceOfContinent(String continentToCheck) {
		for (Continent singleContinent : Database.getInstance().getContinentList()) {
			if (singleContinent.getName().equalsIgnoreCase(continentToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This adds a new continent to the Database.continentList
	 *
	 * @param continentName Name of the continent to be added
	 * @param controlValue The continents control value in Integer form
	 * @return true(If the continent is not present) or false(If continent name is invalid or if the control value is null)
	 */
	public static boolean addContinent(String continentName, Integer controlValue) {
		if (controlValue > 0 && controlValue!=null) {
			if (continentName.trim().length() == 0) {
				return false;
			}
			if (!Continent.checkExistenceOfContinent(continentName)) {
				Continent newContinent = new Continent(Database.getInstance().getContinentList().size() + 1,
						continentName, controlValue, "");
				Database.getInstance().getContinentList().add(newContinent);
				return true;
			}
			else{
				System.out.println("Continent "+continentName+" already exists");
			}
			return false;
		} else {
			System.out.println("Please enter a valid control value");
			return false;
		}
	}

	/**
	 * if the entered continent exists, it removes all the countries under that continent and after that removes that continent from the Database.continentList
	 *
	 * @param continentToRemove Name of the continent to be removed
	 * @param gameGraph game graph
	 * @return true(If the continent is present and is successfully removed) or false(If the continent is absent from the list)
	 */
	public static boolean removeContinent(String continentToRemove, Graph gameGraph) {
		Integer serialNumberOfContinentToRemove = -1;
		Continent continent = null;
		for (Continent singleContinent : Database.getInstance().getContinentList()) {
			if (singleContinent.getName().equalsIgnoreCase(continentToRemove)) {
				serialNumberOfContinentToRemove = singleContinent.getNumber();
				continent = singleContinent;
			}
		}
		if (serialNumberOfContinentToRemove == -1) {
			System.out.println("Continent: " + continentToRemove + " not found in the map!");
			return false;
		}
		ArrayList<String> countriesInContinentToRemove = new ArrayList<String>();
		for (Country country : gameGraph.getAdjList()) {
			if (country.getInContinent() == serialNumberOfContinentToRemove) {
				countriesInContinentToRemove.add(country.getName());
			}
		}

		// Start deleting all the countries in this continent
		for (String country : countriesInContinentToRemove) {
			Country.removeCountry(country, gameGraph);
		}
		Database.getInstance().getContinentList().remove(continent);
		return true;
	}

	
	/**
	 * This creates and returns list of countries in a continent
	 * @param continent It is the object of the class Continent
	 * @param gameGraph It is the object of the class Graph
	 * @return ArrayList of countries in a continent
	 */
	public static ArrayList<Country> getCountryList(Continent continent, Graph gameGraph) {
		ArrayList<Country> countryList = new ArrayList<Country>();
		for (Country country : gameGraph.getAdjList()) {
			Continent continentOfThisCountry = Continent.getContinentById(country.getInContinent());
			if (continentOfThisCountry.getName().equalsIgnoreCase(continent.getName())) {
				countryList.add(country);
			}
		}
		return countryList;
	}

	/**
	 * This provides with the instance at which a particular continent is present in Database.continentList  using continentNumber
	 *
	 * @param continentNumber The number of the continent as an Integer
	 * @return instance for continentNumber
	 */
	public static Continent getContinentById(Integer continentNumber) {
		Continent continent = null;
		for (Continent continent1 : Database.getInstance().getContinentList()) {
			if (continent1.getNumber() == continentNumber) {
				return continent1;
			}
		}
		return continent;
	}

	/**
	 * This provides with the instance at which a particular continent is present in Database.continentList using continentName 
	 *
	 * @param continentName The name of the continent
	 * @return instance for continentName
	 */
	public static Continent getContinentByName(String continentName) {
		Continent continent = null;
		for (Continent continent1 : Database.getInstance().getContinentList()) {
			if (continent1.getName().equalsIgnoreCase(continentName)) {
				return continent1;
			}
		}
		return continent;
	}

	/**
	 * This method checks if a given continent belongs to a player
	 * 
	 * @param playerName The name of the player 
	 * @param continentName The name of the continent to be checked
	 * @param gameGraph It is the object of the class Graph
	 * @return true(If the continent belongs to the particular player)  or false(If the continent does not belong to the player mentioned) 
	 */
	public static boolean continentBelongToPlayer(String playerName, String continentName, Graph gameGraph) {
		Continent continentInQuestion = Continent.getContinentByName(continentName);

		for(Country country: gameGraph.getAdjList()){
			if(country.getInContinent() == continentInQuestion.number){
				if(! country.owner.equalsIgnoreCase(playerName)){
					return false;
				}
			}
		}

		return true;
	}
    
	
	/**
	 * This updates the owner of the continent
	 * @param gameGraph It is an object of the class Graph
	 */
	
    public static void updateContinentOwner(Graph gameGraph) {
    	
    	for(Continent continentItr : Database.continentList) {
    		
    		String tempOwner = "";
    		boolean continentHasOwner = true;
    		
    		for(Country countryItr : gameGraph.getAdjList()) {
    			
    			if(countryItr.getInContinent() == continentItr.getNumber()) {
    				
    				if(tempOwner.isEmpty()){
    					tempOwner = countryItr.getOwner();
    				}
    				else if(tempOwner.equals(countryItr.getOwner())) {
    					continue;
    				}
    				else {
    					continentHasOwner = false;
    					break;
    				}
    			}
    		}
    		
    		if(continentHasOwner)
				continentItr.setOwner(tempOwner);
			else
				continentItr.setOwner("");
    		
    	}
    }

}
