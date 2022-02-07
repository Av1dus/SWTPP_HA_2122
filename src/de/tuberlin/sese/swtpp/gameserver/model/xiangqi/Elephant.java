package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Elephant extends Figures{
	public Elephant(String p) {
		super(p, 's');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		Pair dif = p.absDifference();
		if(dif.x != 2 && dif.y != 2) return false;
		if(true){
			if(p.e.x > 4) return false;
		}else {if(p.e.x < 5) return false;}
		return false;
	}

}
