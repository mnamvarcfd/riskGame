
package GameLogic;

import java.util.*;

/**
 * This class creates the graph of the game.
 * This has singleton implementation.
 */
public class Graph {
    static public ArrayList<Country> adjList= new ArrayList<Country>();
    private static Graph gameGraph;

    /**
     * private constructor so that a object of public class Database cannot be created
     */
    private Graph() {
    }

    /**
     * Singleton implementation of Database class
     * @return The object of class Graph
     */
    public static Graph getInstance(){
        if(gameGraph==null)
            gameGraph= new Graph();
        return gameGraph;
    }

    public ArrayList<Country> getAdjList() {
        return adjList;
    }

    /**
     * This method is another implementation of the showMap command
     */
    public static void showMap()
    {

        for(Country country: adjList){
            System.out.print(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
            if(country.getOwner() == null){
                System.out.print(" Owner: None");
            }
            else{
                System.out.print(" Owner: "+country.getOwner());
            }
            System.out.println(" Armies: "+country.getNumberOfArmies());
        }
    }

    public ArrayList<Country> getNeighbourListAsCountries(Country country) {
        ArrayList<Integer> neighbourList = country.getNeighbours();
        ArrayList<Country> neighbourListAsCountry= new ArrayList<Country>();
        for (Integer neighbourNumber : neighbourList) {
            Country neighbour = Country.getCountryByNumber(neighbourNumber, gameGraph);
            neighbourListAsCountry.add(neighbour);
        }
        return neighbourListAsCountry;
    }

}
