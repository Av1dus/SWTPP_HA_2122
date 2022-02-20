package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Soldier extends Figures {

	public Soldier(String p) {
		super(p, 's');
	}

	@Override
	public boolean isValidMove(Points p, String board) {
		Pair dif = new Pair(0, 0);
		dif = p.absDifference();
		if (this.ownFigure(this.getFieldValue(p.e, board)))
			return false;
		if (p.s.y >= 5) {
			if ((dif.x == 1 && dif.y == 0) || (dif.x == 0 && dif.y == 1))
				return true;
		} else {
			if (dif.x == 0 && dif.y == 1) {
				return true;
			} else if ((dif.x == 1 && dif.y == 0)) {
				return false;
			}
		}
		return false;
	}
}
