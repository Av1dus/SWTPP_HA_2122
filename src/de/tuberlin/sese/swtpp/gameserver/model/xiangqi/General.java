package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class General extends Figures{

	public General(String p) {
		super(p, 's');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		return false;
	}
}
