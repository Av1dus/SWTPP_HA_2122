package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Cannon extends Figures{
	public Cannon(String p) {
		super(p, 'c');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		char dir = 'x';
		String line = "";
		Pair dif = p.absDifference();
		if (!((dif.x == 0 && dif.y > 0)||(dif.x > 0 && dif.y == 0))) return false;
		if(dif.y == 0){ line = this.expandRow(new StringBuilder(board.split("/")[9-p.e.y])).toString();	}
		else{	line = this.getBoardCollumn(board, p.s.x);	dir = 'y';}
		char targetField = line.charAt(p.e.y);
		line = line.substring(p.min(dir)+1,p.max(dir));
		System.out.println(!this.ownFigure(targetField));
		System.out.println(this.identifier);
		System.out.println(targetField);
		if(!this.ownFigure(targetField)){
			System.out.println(line);
			int counter = 0;
			if(Character.isDigit(targetField)){
				for(char l:line.toCharArray()) if(!Character.isDigit(l)) counter++;									
				if(counter == 0)return true;
			}
			else {		
				for(char l:line.toCharArray())if(!Character.isDigit(l)) counter++;				
				if(counter == 1)return true;
			}			
		}		
		return false;
	}
}
