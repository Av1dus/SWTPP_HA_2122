package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;
import de.tuberlin.sese.swtpp.gameserver.model.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.*;

public final class XiangqiGameTest {
	
	final User user1 = new User("Alice", "alice");
	final User user2 = new User("Bob", "bob");
	final User user3 = new User("Eve", "eve");
	
	Player redPlayer = null;
	Player blackPlayer = null;
	XiangqiGame game = null;
	GameController controller;
	
	@Before
	public void setUp() throws Exception {
		controller = GameController.getInstance();
		controller.clear();
		
		int gameID = controller.startGame(user1, "", "xiangqi");
		
		game = (XiangqiGame) controller.getGame(gameID);
		game.board = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		game.redPlayer = game.getPlayer(user1);
		redPlayer = game.getPlayer(user1);
	}
	
	public void startGame() {
		controller.joinGame(user2, "xiangqi");
		game.blackPlayer = game.getPlayer(user2);
		blackPlayer = game.getPlayer(user2);
	}

	
	@Test
	public void testWaitingGame() {
		assertEquals("Wait", game.getStatus());
		assertEquals("", game.gameInfo());
	}
	
	@Test
	public void testGameStarted() {
		assertEquals(game.getGameID(), controller.joinGame(user2, "xiangqi"));
		assertFalse(game.addPlayer(new Player(user3, game))); // no third player
		assertEquals("Started", game.getStatus());
		assertEquals("", game.gameInfo());
		assertTrue(game.isRedNext());
		assertFalse(game.didRedDraw());
		assertFalse(game.didBlackDraw());
		assertFalse(game.redGaveUp());
		assertFalse(game.blackGaveUp());
	}

	@Test
	public void testSetNextPlayer() {
		startGame();
		
		game.setNextPlayer(blackPlayer);
		
		assertFalse(game.isRedNext());
	}

	
	@Test
	public void testCallDrawBoth() {	
		// call draw before start
		assertFalse(game.callDraw(redPlayer));

		startGame();
		
		controller.callDraw(user1, game.getGameID());
		assertTrue(game.didRedDraw());
		assertFalse(game.didBlackDraw());
		assertEquals("red called draw", game.gameInfo());
		
		controller.callDraw(user2, game.getGameID());
		assertTrue(game.didBlackDraw());

		assertEquals("Draw", game.getStatus());
		assertEquals("draw game", game.gameInfo());
		
		// call draw after finish
		assertFalse(game.callDraw(redPlayer));
	}
	
	@Test
	public void testCallDrawBlack() {
		startGame();
		
		controller.callDraw(user2, game.getGameID());
		assertFalse(game.didRedDraw());
		assertTrue(game.didBlackDraw());
		assertEquals("black called draw", game.gameInfo());
	}

	@Test
	public void testGiveUpRed() {
		// try before start 
		assertFalse(game.giveUp(redPlayer));
		assertFalse(game.giveUp(blackPlayer));
		
		startGame();
		
		controller.giveUp(user1, game.getGameID());
		
		assertEquals("Surrendered", game.getStatus());
		assertEquals("red gave up", game.gameInfo());
		
		// try after finish
		assertFalse(game.giveUp(redPlayer));
		assertFalse(game.giveUp(blackPlayer));

	}
	
	@Test
	public void testGiveUpBlack() {
		startGame();
		
		controller.giveUp(user2, game.getGameID());
		
		assertEquals("Surrendered", game.getStatus());
		assertEquals("black gave up", game.gameInfo());
	}

	@Test
	public void testGetMinPlayers() {
		assertEquals(2, game.getMinPlayers());
	}
	
	@Test
	public void testGetMaxPlayers() {
		assertEquals(2, game.getMaxPlayers());
	}
	
	@Test
	public void testNextPlayerString() {
		startGame();
		
		assertEquals("r", game.nextPlayerString());
		
		game.setNextPlayer(blackPlayer);
		
		assertEquals("b", game.nextPlayerString());
	}

	@Test
	public void testFinish() {
		startGame();
		
		assertTrue(game.regularGameEnd(redPlayer));
		assertEquals("Finished", game.getStatus());
		assertEquals("red won", game.gameInfo());
		
		// test after finish
		assertFalse(game.regularGameEnd(redPlayer));
	}
	
	@Test
	public void testFinishBlack() {
		startGame();
		
		assertTrue(game.regularGameEnd(blackPlayer));
		assertEquals("Finished", game.getStatus());
		assertEquals("black won", game.gameInfo());
	}

	@Test
	public void testError() {
		assertFalse(game.isError());
		game.setError(true);
		assertTrue(game.isError());
		assertEquals("Error", game.getStatus());
		game.setError(false);
		assertFalse(game.isError());
	}
	
	@Test
	public void testSetBoard()
	{
		String testBoard = "9/rheagaehr/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		game.setBoard(testBoard);
		assertEquals(game.board,testBoard);
	}
	
	@Test
	public void testGetBoard()
	{
		assertEquals(game.board,game.getBoard());		
	}
	
	@Test
	public void testGetPlayerColor()
	{
		startGame();
		String red = "red";
		String black = "black"; 
		assertTrue(red.equals(game.getPlayerColor(game.redPlayer)));
		assertTrue(black.equals(game.getPlayerColor(game.blackPlayer)));
	}

	
	@Test
	public void testValidateMoveString()
	{
		String FFFFF = "\0\0\0\0\0";
		String TTTTT = "c0-c1";
		String FTTFT = "j0-k5";
		String length_F = "c2-d3 ";
		String TFTTF = "aa-aa";
		assertFalse(game.validateMoveString(FFFFF));
		assertTrue(game.validateMoveString(TTTTT));
		assertFalse(game.validateMoveString(FTTFT));
		assertFalse(game.validateMoveString(length_F));
		assertFalse(game.validateMoveString(TFTTF));
	}
	
	@Test
	public void testGetFigureFromField()
	{
		game.setBoard("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR");
		Figures fig = new General("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("e0","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("e0","red")).getIdentifier());
		
		fig = new Advisor("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("d0","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("d0","red")).getIdentifier());
		
		fig = new Elephant("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("c0","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("c0","red")).getIdentifier());
		
		fig = new Horse("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("b0","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("b0","red")).getIdentifier());
		
		fig = new Rook("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("a0","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("a0","red")).getIdentifier());
		
		fig = new Cannon("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("b2","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("b2","red")).getIdentifier());
		
		fig = new Soldier("red");
		assertEquals(fig.getPlayer(),(game.getFigureFromField("a3","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("a3","red")).getIdentifier());
		
		fig = new Figures("null",'n');
		assertEquals(fig.getPlayer(),(game.getFigureFromField("b3","red")).getPlayer()); 
		assertEquals(fig.getIdentifier(),(game.getFigureFromField("b3","red")).getIdentifier());
	}
	
	@Test
	public void testTryMove()
	{	
		startGame();
		String FFFFF = "\0\0\0\0\0";
		String TTTTT = "a3-a4";
		String FTTFT = "j0-k5";
		String length_F = "c2-d3 ";
		String TFTTF = "aa-aa";
		String falseSoldier = "a3-b4";
		assertTrue(game.tryMove(TTTTT,game.blackPlayer));
		game.setBoard("rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR");
		assertFalse(game.tryMove(FFFFF,game.blackPlayer));
		assertFalse(game.tryMove(falseSoldier,game.blackPlayer));
		assertFalse(game.tryMove(FTTFT,game.blackPlayer));
		assertFalse(game.tryMove(length_F,game.blackPlayer));
		assertFalse(game.tryMove(TFTTF,game.blackPlayer));
		
		
	}
}
