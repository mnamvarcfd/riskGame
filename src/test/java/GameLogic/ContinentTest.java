package GameLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * It contains different Junit tests for the methods defined in {@link GameLogic.Continent}
 * 
 * @see GameLogic.Continent
 * @author birjotsingh17
 * 
 *
 */
public class ContinentTest {
	static GamePlay gamePlay;

	/**
	 * This method runs before every test method and creates an object for GamePlay and also loads a map
	 * 
	 * @throws Exception can throw an exception to caller method
	 */
	@Before
	public void before() throws Exception {
		 gamePlay= GamePlay.getInstance();
		 gamePlay.editMap("map.map");
	}

	/**
	 * This method runs after each and every test method and clears the instance changed by the test method
	 * @throws Exception throws Exception to caller method
	 */
	@After
	public void after() throws Exception {
	    gamePlay.getGraphObj().getAdjList().clear();
		gamePlay = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	/**
	 * This is a jUnit test for {@link GameLogic.Continent#checkExistenceOfContinent(String)}
	 */
	@Test
	public  void checkExistence() {
		assertFalse(Continent.checkExistenceOfContinent("Bir"));
	}
	
	/**
	 * This is a jUnit test for {@link GameLogic.Continent#checkExistenceOfContinent(String)}
	 */
	@Test
	public  void checkExistence2() {
		assertTrue(Continent.checkExistenceOfContinent("Europe"));
	}
	
	/**
	 * This is a jUnit test for {@link GameLogic.Continent#checkExistenceOfContinent(String)}
	 */
	@Test
	public  void checkExistence3() {
		assertFalse(Continent.checkExistenceOfContinent("    "));
	}

	/**
	 * This is a jUnit test for {@link GameLogic.Continent#addContinent(String, Integer)}
	 */
	@Test
	public void addContinent(){
		Continent.addContinent("Kammulaska", 8);
		assertTrue(Database.getInstance().getContinentList().contains(Continent.getContinentByName("kAMMUlasKa")));
	}

	/**
	 * This is a jUnit test for {@link GameLogic.Continent#removeContinent(String, Graph)}
	 */
    @Test
    public void removeContinent(){
        Continent.removeContinent("Europe", gamePlay.getGraphObj());
        Continent continent= Continent.getContinentByName("Europe");
        if(continent == null){
            assertTrue(true);
        }
        else{
            assertFalse(true);
        }
    }
}
