package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Points {
	public Pair s;
	public Pair e;

	public Points(Pair s, Pair e) {
		this.s = s;
		this.e = e;
	}

	public Pair difference() {
		return new Pair(this.e.x - this.s.x, this.e.y - this.s.y);
	}

	public Pair absDifference() {
		return new Pair(Math.abs(this.e.x - this.s.x), Math.abs(this.e.y - this.s.y));
	}
	/*
	public String toString() {
		return "<(" + this.s.x + "," + this.s.y + "),(" + this.e.x + "," + this.e.y + ")>";
	}
	
	public void reverse() {
		Pair tmp = new Pair(this.s.x, this.s.y);
		this.s = this.e;
		this.e = tmp;
	}
	
	public int min(char coord) {
		if (coord == 'x') {
			if (this.s.x <= this.e.x)
				return this.s.x;
			return this.e.x;
		}
		if (coord == 'y') {
			if (this.s.y <= this.e.y)
				return this.s.y;
			return this.e.y;
		}

		return 0;
	}
	
	public int max(char coord) {
		if (coord == 'x') {
			System.out.println(this.s.x + " - " + this.e.x);
			if (this.s.x >= this.e.x)
				return this.s.x;
			return this.e.x;
		}
		if (coord == 'y') {
			System.out.println(this.s.y + " - " + this.e.y);
			if (this.s.y >= this.e.y)
				return this.s.y;
			return this.e.y;
		}

		return 0;
	}
	*/
}