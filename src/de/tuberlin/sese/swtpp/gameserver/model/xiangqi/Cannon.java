package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Cannon extends Figures{
	public Cannon(String p) {
		super(p, 's');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		return false;
	}

}
