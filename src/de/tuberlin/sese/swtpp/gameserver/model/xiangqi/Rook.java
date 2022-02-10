package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Rook extends Figures{
	public Rook(String p) {
		super(p, 'r');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		Pair dif = p.absDifference();
		if(dif.x > 0  && dif.y == 0){
			String row = this.expandRow(new StringBuilder(board.split("/")[9-p.s.y]));
			for(int i=p.s.x+1;i<p.e.x;i++)	if(!Character.isDigit(row.charAt(i)))return false;
			if(this.ownFigure(row.charAt(p.e.x)))return false;
		}else if(dif.x == 0 && dif.y > 0) {
			String column = this.getBoardCollumn(board, p.s.x);
			boolean reversed = false; 
			if(p.e.y < p.s.y){
				p.reverse();
				reversed = true;			}
			for(int i= 9-p.s.y+1;i<9-p.e.y;i++)if(!Character.isDigit(column.charAt(i)))return false;
			if(reversed){
				if(this.ownFigure(column.charAt(9-p.s.y))){
						p.reverse();
						return false;}else {p.reverse();}}
			else{return this.ownFigure(column.charAt(9-p.s.y));}
		}else{return false;}
		return true;
	}
}
