package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Soldier extends Figures {

	public Soldier(String p) {
		super(p, 's');
	}

	@Override
	public boolean isValidMove(Points p, String board) {
		Pair dif = p.absDifference();
		boolean valid_move_pre_river = (dif.x == 0 && dif.y == 1);
		boolean valid_move_post_river = valid_move_pre_river || (dif.x == 1 && dif.y == 0);
		
		if (this.ownFigure(this.getFieldValue(p.e, board)))
			return false;
		
		if (this.player.equals("red")) {
			if (p.s.y < 5) {
				return valid_move_pre_river;
			} else {
				return valid_move_post_river;
			}
		} else if (this.player.equals("black")) {
			if (p.s.y > 4) {
				return valid_move_pre_river;
			} else {
				return valid_move_post_river;
			}
		}
		
		return false;
	}
}
