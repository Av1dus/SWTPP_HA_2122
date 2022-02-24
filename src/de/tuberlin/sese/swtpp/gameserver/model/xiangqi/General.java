package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class General extends Figures {

	public General(String p) {
		super(p, 'g');
	}

	private boolean isInBound(Pair target) {
		if (this.player == "red") // Red
			return target.y < 3 && target.x > 2 && target.x < 6;
		else if (this.player == "black") // Black
			return target.y > 6 && target.x > 2 && target.x < 6;
		else
			return false;
	}
	@Override
	public boolean isValidMove(Points p, String board) {
		Pair dif = p.absDifference();
		
		if (dif.x + dif.y != 1 && this.ownFigure(this.getFieldValue(p.e, board)))
			return false;
		else
			return this.isInBound(p.e);
	}
}
