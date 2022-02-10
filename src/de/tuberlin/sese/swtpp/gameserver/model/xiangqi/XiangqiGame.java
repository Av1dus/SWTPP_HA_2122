package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

import de.tuberlin.sese.swtpp.gameserver.model.*;
//TODO: more imports from JVM allowed here


import java.io.Serializable;
public class XiangqiGame extends Game implements Serializable{
	

	
	/**
	 *
	 */
	private static final long serialVersionUID = 5424778147226994452L;

	/************************
	 * member
	 ***********************/

	// just for better comprehensibility of the code: assign red and black player
	public Player blackPlayer;
	public Player redPlayer;
	public String board;
	public String[] boardRows;

	// internal representation of the game state
	// TODO: insert additional game data here

	/************************
	 * constructors
	 ***********************/

	public XiangqiGame() {
		super();
		//this.board = "RHEAGAEHR/9/1C5C1/S1S1S1S1S/9/9/s1s1s1s1s/1c5c1/9/rheagaehr";
		this.board = "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		this.boardRows = getBoardRows();

		// TODO: initialization of game state can go here
	}

	public String getType() {
		return "xiangqi";
	}

	/*******************************************
	 * Game class functions already implemented
	 ******************************************/

	@Override
	public boolean addPlayer(Player player) {
		if (!started) {
			players.add(player);

			// game starts with two players
			if (players.size() == 2) {
				started = true;
				this.redPlayer = players.get(0);
				this.blackPlayer= players.get(1);
				nextPlayer = redPlayer;
			}
			return true;
		}

		return false;
	}

	@Override
	public String getStatus() {
		if (error)
			return "Error";
		if (!started)
			return "Wait";
		if (!finished)
			return "Started";
		if (surrendered)
			return "Surrendered";
		if (draw)
			return "Draw";

		return "Finished";
	}

	@Override
	public String gameInfo() {
		String gameInfo = "";

		if (started) {
			if (blackGaveUp())
				gameInfo = "black gave up";
			else if (redGaveUp())
				gameInfo = "red gave up";
			else if (didRedDraw() && !didBlackDraw())
				gameInfo = "red called draw";
			else if (!didRedDraw() && didBlackDraw())
				gameInfo = "black called draw";
			else if (draw)
				gameInfo = "draw game";
			else if (finished)
				gameInfo = blackPlayer.isWinner() ? "black won" : "red won";
		}

		return gameInfo;
	}

	@Override
	public String nextPlayerString() {
		return isRedNext() ? "r" : "b";
	}

	@Override
	public int getMinPlayers() {
		return 2;
	}

	@Override
	public int getMaxPlayers() {
		return 2;
	}

	@Override
	public boolean callDraw(Player player) {

		// save to status: player wants to call draw
		if (this.started && !this.finished) {
			player.requestDraw();
		} else {
			return false;
		}

		// if both agreed on draw:
		// game is over
		if (players.stream().allMatch(Player::requestedDraw)) {
			this.draw = true;
			finish();
		}
		return true;
	}

	@Override
	public boolean giveUp(Player player) {
		if (started && !finished) {
			if (this.redPlayer == player) {
				redPlayer.surrender();
				blackPlayer.setWinner();
			}
			if (this.blackPlayer == player) {
				blackPlayer.surrender();
				redPlayer.setWinner();
			}
			surrendered = true;
			finish();

			return true;
		}

		return false;
	}

	/* ******************************************
	 * Helpful stuff
	 ***************************************** */

	/**
	 *
	 * @return True if it's red player's turn
	 */
	public boolean isRedNext() {
		return nextPlayer == redPlayer;
	}

	/**
	 * Ends game after regular move (save winner, finish up game state,
	 * histories...)
	 *
	 * @param winner player who won the game
	 * @return true if game was indeed finished
	 */
	public boolean regularGameEnd(Player winner) {
		// public for tests
		if (finish()) {
			winner.setWinner();
			winner.getUser().updateStatistics();
			return true;
		}
		return false;
	}

	public boolean didRedDraw() {
		return redPlayer.requestedDraw();
	}

	public boolean didBlackDraw() {
		return blackPlayer.requestedDraw();
	}

	public boolean redGaveUp() {
		return redPlayer.surrendered();
	}

	public boolean blackGaveUp() {
		return blackPlayer.surrendered();
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 ******************************************/

	@Override
	public void setBoard(String state) {
		// Note: This method is for automatic testing. A regular game would not start at some artificial state.
		//       It can be assumed that the state supplied is a regular board that can be reached during a game.
		// TODO: implement
		this.board = state;
	}

	@Override
	public String getBoard() {
		// TODO: implement
		//return "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		return this.board;
	}
	
	public String[] getBoardRows() {
		Figures fig = new Figures("null",'n');
		String[] rows = this.board.split("/");
		for(int i=0;i<rows.length;i++)
		{
			rows[i] = fig.expandRow(new StringBuilder(rows[i])).toString();
		}
		return rows;
	}
	
	public void updateBoardRows() {
		this.boardRows = getBoardRows();
	}
	
	public Figures getFigureFromField(String field,String playerColor)
	{
		Pair p = getFieldValue(field);		
		char f = boardRows[9-p.y].charAt(p.x);
		Figures fig;
		switch(Character.toLowerCase(f)) {
			case 'g': fig = new General(playerColor);break; 
			case 'a': fig = new Advisor(playerColor);break;
			case 'e': fig = new Elephant(playerColor);break;
			case 'h': fig = new Horse(playerColor);break;
			case 'r': fig = new Rook(playerColor);break;
			case 'c': fig = new Cannon(playerColor);break;
			case 's': fig = new Soldier(playerColor);break;
			default: fig = new Figures("null",'n');break;
		}
		return fig; 
	}
	
	public Pair getFieldValue(String field)
	{ 
		Pair p = new Pair(field.charAt(0)- 97,Integer.parseInt(String.valueOf(field.charAt(1))));
		return p;	
	}

	public String getPlayerColor(Player player){
		if(player.equals(redPlayer)){
			return "red";
		}
		return "black";
	}
	
	public boolean validateMoveString(String moveString)
	{
		if(moveString.length() != 5) return false;
		char[] msLetter = moveString.toCharArray();
		boolean[] conditions = {
				(msLetter[0] > 96 && msLetter[0] < 106),
				(msLetter[1] > 47 && msLetter[1] < 58),
				(msLetter[2] == 45),
				(msLetter[3] > 96 && msLetter[3] < 106),
				(msLetter[4] > 47 && msLetter[4] < 58)};
		for(int i=0;i<conditions.length;i++)
		{
			if(!conditions[i])
			{	
				return false;
			}
		}
		return true;
	}
	

	@Override
	public boolean tryMove(String moveString, Player player) {
		// TODO: implement
		System.out.println(moveString);
		if(!validateMoveString(moveString)) return false;
		String[] fields = moveString.split("-");
		String playerColor = getPlayerColor(player);
		Figures figure = getFigureFromField(fields[0],playerColor);
		Points p = new Points(getFieldValue(fields[0]),getFieldValue(fields[1]));
		boolean valid = figure.isValidMove(p, this.board);
		System.out.println("tryMove is valid? "+valid);
		if(valid)
		{
			this.board = figure.applyMove(p, board);
			updateBoardRows();
		}
		return valid;
	}

}
