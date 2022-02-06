package de.tuberlin.sese.swtpp.gameserver.model;

public class Figures {
	String player;
	Character identifier;
	public Figures(String p,char i){
		this.player = p;
		this.identifier = Character.toLowerCase(i);
	}
	
	public boolean isValidMove(Points p,String board)
	{
		return false;
	}
	
	public String applyMove(Points p, String board)
	{		
	  String[] rows = board.split("/");
	  StringBuilder startRow = new StringBuilder(expandRow(new StringBuilder(rows[p.s.x])));
	  StringBuilder endRow = new StringBuilder(expandRow(new StringBuilder(rows[p.e.x])));
	  char stone = startRow.charAt(p.s.x);
	  startRow.setCharAt(p.s.x,'1');
	  endRow.setCharAt(p.e.x,stone);
	  rows[p.s.y] = collapseRow(new StringBuilder(startRow)).toString();
	  rows[p.e.y] = collapseRow(new StringBuilder(endRow)).toString();
	  
	  String board2 = String.join("/",rows);
	  System.out.println(board);
	  System.out.println(board2);
		
	  return board;
	}

	private StringBuilder expandRow(StringBuilder row)
    {
    StringBuilder ret = new StringBuilder();
     for(char c:row.toString().toCharArray())
      {
          int empty = c-48;
          if(empty> 0 && empty <= 9)
          {
              for(int i=0;i<empty;i++)
              {
                  ret.append('1');
              }
          }
          else
          {
              ret.append(c);
          }
      }
	return ret;
    }
    
    private StringBuilder collapseRow(StringBuilder row)
    {
    StringBuilder ret = new StringBuilder();
    int count = 0;
     for(int i=0;i<row.length();i++)
     {
        char c = row.charAt(i);
          int empty = c-48;
          if(empty> 0 && empty <= 9)
          {
              count++;
          }
          else
          {
              char countC = (char)(count+48);
              if (count > 0)
              {
                ret.append(countC);    
              }
              ret.append(c);
              count = 0;
          }
      }
      if(count != 0)
      {
        char countC = (char)(count+48);
        ret.append(countC);  
      }
	return ret;
    }
}
