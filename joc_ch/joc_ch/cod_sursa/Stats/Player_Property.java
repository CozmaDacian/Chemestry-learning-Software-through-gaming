package Stats;

import java.io.Serializable;

public class Player_Property implements Serializable {
  
	public int xoffset;
	public int yoffset;
	public int x;
	public int y;
	public int culoare_caracter;
	public Player_stats stats;
	public Player_Property(int x,int y,int xoffset,int yoffset,int culoare_caracter,Player_stats stats){
	this.x=x;
	this.y=y;
	this.xoffset=xoffset;
	this.yoffset=yoffset;
	this.culoare_caracter=culoare_caracter;
	this.stats=stats;
	}
}
