package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Pair{ 
	  public final int x; 
	  public final int y; 
	  public Pair(int x, int y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	  public String toString() {
		  return "<"+this.x+","+this.y+">";
	  }
} 
