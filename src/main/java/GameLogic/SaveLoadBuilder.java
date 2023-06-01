package GameLogic;

import java.io.IOException;

/*
This is an interface to implement Builder pattern
 */
public interface SaveLoadBuilder {
	
	public void  handleContinent();
	public void  handleCountry();
	public void  handlePlayers();
	public void  handleFreeCards();
	public void  handleCurrentState();
	public void  handleCurrentPlayer();
	public void  setFile(String fileName) throws IOException;
	
}
