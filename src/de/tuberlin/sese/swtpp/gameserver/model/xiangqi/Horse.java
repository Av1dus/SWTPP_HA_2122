package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Horse extends Figures{
	public Horse(String p) {
		super(p, 's');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		return false;
	}

}
