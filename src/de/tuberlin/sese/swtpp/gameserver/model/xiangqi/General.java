package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class General extends Figures {
		public char opponentGeneral;
	public General(String p,char gen) {
		super(p, 'g');
		this.opponentGeneral = gen;
	}

	private boolean isInBound(Pair target) {
		if (this.player == "red") // Red
			return target.y < 3 
					&& target.x > 2
					&& target.x < 6;
		else  // Black
			return target.y > 6 
					&& target.x > 2 
					&& target.x < 6;
	}
	@Override
	public boolean isValidMove(Points p, String board) { 
		if((char)(p.e.x + 48 +'0') == this.opponentGeneral)return false; 
		Pair dif = p.absDifference();
		
		if (dif.x + dif.y != 1 && this.ownFigure(this.getFieldValue(p.e, board)))
			return false;
		else
			return this.isInBound(p.e);
	}
}
