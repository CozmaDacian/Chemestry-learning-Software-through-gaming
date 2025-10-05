package Stats;

import java.io.Serializable;

public class Property_enemy implements Serializable {
    public int x;
    public int y;
    public int type;
    public int number_of_enemies;
    public Property_enemy(int x,int y,int type,int number_of_enemies){
    	this.x=x;
    	this.y=y;
    	this.type=type;
    	this.number_of_enemies=number_of_enemies;
    }
    
}
