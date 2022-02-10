package de.tuberlin.sese.swtpp.gameserver.test.xiangqi;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Figures;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Pair;
import de.tuberlin.sese.swtpp.gameserver.model.xiangqi.Points;

import static org.junit.Assert.*;

import org.junit.Test;

public class FiguresTest {
	Figures fig = new Figures("color",'i');;
	@Test
	public void testFigures() {
		fig = new Figures("color",'i');
		assertTrue(fig.getPlayer().equals("color"));
		assertTrue(fig.getIdentifier().equals('i'));
	}

	@Test
	public void testIsValidMove() {
		Pair p1 = new Pair(0,0);
		Pair p2 = new Pair(0,0);
		Points p = new Points(p1,p2);
		String board = "somestring";
		assertFalse(fig.isValidMove(p, board));
	}

	@Test
	public void testApplyMove() {
		String board= "rheagaehr/9/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		String boardExpected = "1heagaehr/r8/1c5c1/s1s1s1s1s/9/9/S1S1S1S1S/1C5C1/9/RHEAGAEHR";
		Points p = new Points(new Pair(0,0),new Pair(0,1));
		String boardResult = fig.applyMove(p, board);
		assertTrue(boardExpected.equals(boardResult));
		
	}
	
	@Test
	public void testExpandRow()
	{
		StringBuilder row = new StringBuilder("9");
		StringBuilder row2 = new StringBuilder("\0\0\0\0");
		StringBuilder result = fig.expandRow(row);
		StringBuilder result2 = fig.expandRow(row2);
		assertEquals(result.toString(),"111111111");
		assertEquals(result2.toString(),"\0\0\0\0");
	}
	
	@Test
	public void testCollapseRow()
	{
		StringBuilder row = new StringBuilder("111111111");
		StringBuilder row2 = new StringBuilder("1\0\0\0\0");
		StringBuilder result = fig.collapseRow(row);
		StringBuilder result2 = fig.collapseRow(row2);
		assertEquals(result.toString(),"9");
		assertEquals(result2.toString(),"1\0\0\0\0");
	}

}
