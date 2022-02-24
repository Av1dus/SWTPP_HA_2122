package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;

public class TryMoveIntegrationTest {

	User user1 = new User("Alice", "alice");
	User user2 = new User("Bob", "bob");

	Player redPlayer = null;
	Player blackPlayer = null;
	Game game = null;
	GameController controller;

	@Before
	public void setUp() throws Exception {
		controller = GameController.getInstance();
		controller.clear();

		int gameID = controller.startGame(user1, "", "xiangqi");

		game = controller.getGame(gameID);
		redPlayer = game.getPlayer(user1);

	}

	public void startGame() {
		controller.joinGame(user2, "xiangqi");
		blackPlayer = game.getPlayer(user2);
	}

	public void startGame(String initialBoard, boolean redNext) {
		startGame();

		game.setBoard(initialBoard);
		game.setNextPlayer(redNext ? redPlayer : blackPlayer);
	}

	public void assertMove(String move, boolean red, boolean expectedResult) {
		if (red)
			assertEquals(expectedResult, game.tryMove(move, redPlayer));
		else
			assertEquals(expectedResult, game.tryMove(move, blackPlayer));
	}

	public void assertGameState(String expectedBoard, boolean redNext, boolean finished, boolean redWon) {
		assertEquals(expectedBoard, game.getBoard());
		assertEquals(finished, game.isFinished());

		if (!game.isFinished()) {
			assertEquals(redNext, game.getNextPlayer() == redPlayer);
		} else {
			assertEquals(redWon, redPlayer.isWinner());
			assertEquals(!redWon, blackPlayer.isWinner());
		}
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 *******************************************/

	@Test
	public void exampleTest() {
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("e3-e4", true, true);
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);
	}

	// TODO: implement test cases of same kind as example here
	@Test
	public void soldierTest(){
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		
		assertMove("e3-e4", true, true); //RED
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);
		
		assertMove("a6-a5", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2s1s1s1s/s8/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR", true, false, false);
		
		assertMove("e4-e5", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2s1s1s1s/s3S4/9/S1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);

		assertMove("a5-a4", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2s1s1s1s/4S4/s8/S1S3S1S/1C5C1/9/RHEAGAEHR", true, false, false);
		
		assertMove("e5-e6", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2s1S1s1s/9/s8/S1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);
		
		assertMove("a4-a3", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2s1S1s1s/9/9/s1S3S1S/1C5C1/9/RHEAGAEHR", true, false, false);
		
		assertMove("e6-d6", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2sS2s1s/9/9/s1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);
		
		assertMove("a3-a2", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2sS2s1s/9/9/2S3S1S/sC5C1/9/RHEAGAEHR", true, false, false);
		
		assertMove("d6-d5", true, false);//RED
		
		
		assertMove("d6-c6", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/9/9/2S3S1S/sC5C1/9/RHEAGAEHR", false, false, false);
		
		assertMove("a2-a1", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/9/9/2S3S1S/1C5C1/s8/RHEAGAEHR", true, false, false);
		
		assertMove("c3-c4", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/9/2S6/6S1S/1C5C1/s8/RHEAGAEHR", false, false, false);
		
		assertMove("a1-a0", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/9/2S6/6S1S/1C5C1/9/sHEAGAEHR", true, false, false);
		
		assertMove("i6-i5", true, false);//RED
		assertMove("c4-c5", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/2S6/9/6S1S/1C5C1/9/sHEAGAEHR", false, false, false);
		
		assertMove("\0\0\0\0\0", false, false); //BLACK
		assertMove("xxxxx", false, false); //BLACK
		assertMove("i0-i1", false, false);//BLACK
		assertMove("aa-bbb", false, false); //BLACK
		assertMove("aa-bb", false, false); //BLACK
		assertMove("a0-bb", false, false); //BLACK
		assertMove("a0-b0", false, true); //BLACK
		assertGameState("rheagaehr/9/1c5c1/2S3s1s/2S6/9/6S1S/1C5C1/9/1sEAGAEHR", true, false, false);
		
		assertMove("c5-c6", true, false);//RED
		assertMove("c6-d6", true, true);//RED
		assertGameState("rheagaehr/9/1c5c1/3S2s1s/2S6/9/6S1S/1C5C1/9/1sEAGAEHR", false, false, false);
	}
	
	@Test
	public void blackStartAndWin()
	{
		startGame("3g5/9/9/9/9/9/9/9/rr7/4G4", false); //black starts
		assertMove("a1-a0", false, true);
		
		assertGameState("3g5/9/9/9/9/9/9/9/1r7/r3G4", true, true, false);		
	}
	
	@Test
	public void redStartAndWin()
	{	
		
		startGame("3g5/RR7/9/9/9/9/9/9/5G3/9", true); //red starts
		
		assertMove("a8-a9", true, true);
		
		assertGameState("R2g5/1R7/9/9/9/9/9/9/5G3/9", false, true, true);		
	}
	
	@Test
	public void XiangqiGameTest()
	{
		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("d4-d5", true, false); //no figure		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("b2-b3", true, true); //CANNON
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("a0-a1", true, true); //ROOK		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("b0-a2", true, true); //HORSE		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("c0-a2", true, true); //ELEPHANT		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("d0-e1", true, true); //ADVISOR		
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("e0-e1", true, true); //GENERAL
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/4G4/RHEA1AEHR", false, false, false);
	
		
	}
	
	@Test
	public void SoldierTest_red()
	{	
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true);
		assertMove("e3-e3", true, false); 
		assertMove("e3-d3", true, false); 
		assertMove("e3-f3", true, false); 
		assertMove("e3-d2", true, false); 
		assertMove("e3-e2", true, false);
		assertMove("e3-f2", true, false);
		assertMove("e3-d4", true, false);
		assertMove("e3-f4", true, false);
		assertMove("e3-e4", true, true);
		assertGameState("rheagaehr/9/1c5c1/s1s1s1s1s/9/4S4/S1S3S1S/1C5C1/9/RHEAGAEHR", false, false, false);
		
	}
	
	@Test
	public void SoldierTest_black()
	{
		startGame("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", false);
		assertMove("e6-e6", false, false); 
		assertMove("e6-d6", false, false); 
		assertMove("e6-f6", false, false); 
		assertMove("e6-d7", false, false); 
		assertMove("e6-e7", false, false);
		assertMove("e6-f7", false, false);
		assertMove("e6-d5", false, false);
		assertMove("e6-f5", false, false);
		assertMove("e6-e5", false, true);
		assertGameState("rheagaehr/9/1c5c1/s1s3s1s/4s4/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR", true, false, false);
		startGame("3g5/9/9/9/9/4s4/9/9/9/5G3", false);
		assertMove("e4-e5", false, false);  
		assertMove("e4-d5", false, false); 
		assertMove("e4-f5", false, false);
		assertMove("e4-e4", false, false);
		game.debug=true;
		
		System.out.println(game.getBoard());
		System.out.println(game.getNextPlayer() == redPlayer);
		System.out.println(game.isFinished());
		System.out.println(redPlayer.isWinner());
		assertGameState("3g5/9/9/9/9/4s4/9/9/9/5G3", false, false, false);
		game.debug=false;
	}
	
	@Test
	public void ElephantTest()
	{	
		startGame("2e2g3/3s5/S2ss4/9/9/9/9/9/9/4G4", false);
		assertMove("c9-f4", false, false); //FFF
		assertMove("c9-f4", false, false); //FFT
		assertMove("c9-f4", false, false); //FTF
		assertMove("c9-f4", false, false); //FTT
		assertMove("c9-d4", false, false); //TFF
		
		assertMove("c9-d7", false, true); //TFT  -> FALSE RESOLUTION
		startGame("2e2g3/3s5/S2ss4/9/9/9/9/9/9/4G4", false);
		assertMove("c9-e7", false, false); //TTF
		assertMove("c9-a7", false, true); //TTT
		
	}
	
	@Test
	public void CannonTest()
	{
		
	}
}
