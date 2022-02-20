package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class MoveBoard {
	String representation;
	String[] repRows; 
	public MoveBoard(String board,String color, String[] rows){
		this.repRows = new String[rows.length];
		this.representation = this.repByPlayer(color, rows);
	}
	
	private String repByPlayer(String color,String[] rows){
		String board = "";
		
		if(color == "red"){
			for(int i=9;i> -1;i--){
				board += rows[i];
				this.repRows[9-i] = rows[9-i];
				if(i != 0)	board += "/";
				
			}
		}
		else {
			for(int i=0;i < 10;i++){
				board += rows[i];
				this.repRows[i] = rows[i];
					if(i != 9)	board += "/";
			}
		}
		
		return board;
	}
	
	public String toMainBoard() {
		String board = "";
		Figures fig = new Figures("null",'n');
		for(int i=0;i<10;i++)
		{
			board += fig.collapseRow(new StringBuilder(this.repRows[i])).toString();
			if(i != 9)	board += "/"; 
		}
		return board;
	}
}
