package de.tuberlin.sese.swtpp.gameserver.model.xiangqi;

public class Elephant extends Figures{
	public Elephant(String p) {
		super(p, 'e');
	}
	
	@Override
	public boolean isValidMove(Points p,String board){
		Pair dif = p.absDifference();
		System.out.println("AbsDiffElephat: "+dif.toString());
		if(dif.x != 2 && dif.y != 2) return false;
		
		Pair middle = this.meanField(p);
		System.out.println(middle.toString());
		String meanRow = board.split("/")[middle.y];
		char value = this.expandRow(new StringBuilder(meanRow)).charAt(middle.x);
		System.out.println("Middle: "+middle);
		System.out.println("MeanRow: "+meanRow);
		System.out.println("Value: "+value);
		if(this.charIsFigure(value))return false;			
		if(p.e.y >4)return false;
		return true;
	}
	public Pair meanField(Points p) {
		Pair dif = new Pair((p.e.x-p.s.x)/2,(p.e.y-p.s.y)/2);
		
		return new Pair(p.s.x+dif.x,p.s.y+dif.y);
	}
}
