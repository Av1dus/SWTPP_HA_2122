package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Advisor extends Figures{
	public Advisor(String p) {
		super(p, 'a');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		Pair dif = new Pair(0,0);
		dif = p.absDifference();
		if(player.equals("red") &&(p.s.x < 5 && dif.y != 0)) return false;
		else{if(p.s.x > 4 && dif.y != 0) return false;}
		if((dif.x == 1 && dif.y == 0) || (dif.x == 0 && dif.y == 1)) return true;
		return false;
	}

}
