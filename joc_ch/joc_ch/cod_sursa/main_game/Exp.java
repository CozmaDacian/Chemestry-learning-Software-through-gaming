package main_game;

import java.util.HashMap;
import java.util.Map;

public class Exp {
      
	  public int level;
	  public int XP[]=new int [25] ;
	  private Game game;
	  public Exp(Game game)
	  {
      this.game=game;	
	  this.setMap();
     }
	 
	  private int Check_LVLUP(int level) {
		 
		  if(game.XP-XP[level]>0) {
		  game.XP-=XP[level];
			level++;
		  return Check_LVLUP(level);
			  
		  
	      }
		  else
			  return level;
		  
		 
	  }
	  
	 private void setMap() {
		 
		for(int i=1;i<=25;i++) {
		XP[i]=500*i/2;	
		}
		 
	 }
}
