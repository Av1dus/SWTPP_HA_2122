package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Points{ 
	  public Pair s; 
	  public Pair e; 
	  public Points(Pair s, Pair e) { 
	    this.s = s; 
	    this.e = e; 
	  }
	  public Pair difference() {
		 return new Pair(this.e.x-this.s.x,this.e.y-this.s.y);
	  }
	  public Pair absDifference() {
			 return new Pair(Math.abs(this.e.x-this.s.x),Math.abs(this.e.y-this.s.y));
		  }
} 