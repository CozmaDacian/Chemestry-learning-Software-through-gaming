package Menu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import Stats.LevelStats;
import Stats.Player_Property;
import Stats.Player_stats;
import Stats.Property_enemy;
import main_game.Caracter;
import main_game.Enemies;
import main_game.Game;
import utilz.Obiecte;

public class Load_Last_Checkpoint implements Serializable{
    
	
	private int FrecventeIteme[],FrecventeSkills[];
	private int level_data_inventar[][];
	private Player_Property property;
	private LevelStats level;
	private Vector<Property_enemy> enemy1=new Vector<>();
	private Vector<Obiecte> obiecte=new Vector<>();
	public Load_Last_Checkpoint(Game game){
		load_class_elements(game);
		this.Serialize_last_checkpoint(utilz.Constante.path_name+"Save/1.json");
	}
	
	
	
	
	
	public void load_class_elements(Game game) {
		this.FrecventeIteme=game.getInventar().getFrecventeItems();
		this.level_data_inventar=game.getInventar().lvlData;
		this.FrecventeSkills=game.getTree().Freq;
	    this.obiecte=game.getPlay().getObjects();
	    this.level=game.getPlay().Levels.peek().statistici;
	    
		Vector<Enemies> enemy=game.getPlay().enemies;
		
		for(int i=0;i<enemy.size();i++) {
			int x=(int) enemy.get(i).getX();
			int y=(int) enemy.get(i).getY();
			int type=enemy.get(i).type;
			int number_of_enemies=enemy.get(i).number_of_enemies;
			
			Property_enemy inamic=new Property_enemy(x,y,type,number_of_enemies); 
			enemy1.add(inamic);
		}
	    Caracter caracter=game.getCaracter();
		int x_caracter=caracter.getX();
		int y_caracter=caracter.getY();
		int x_offset=caracter.getxLvlOffset();
		int y_offset=caracter.getyLvlOffset();
		int cul_car=caracter.cul_car;
		Player_stats stats=game.getCaracter().getStatistici();
		property=new Player_Property(x_caracter,y_caracter,x_offset,y_offset,cul_car,stats);
		
		
	}
	
	public void Serialize_last_checkpoint(String filePath) {
		
		File file = new File(filePath);
        
		try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
            return; // Exit the program if file creation fails
        }
		
		 try (FileOutputStream fileOut = new FileOutputStream(filePath);
	             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

	            objectOut.writeObject(this);
	            System.out.println("Object serialized and written to file successfully.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public void initialize_variables(Game game) {
		
		
		
		// Salvam doar campurile relevante din joc precum ar fi inamicii ramasi, itemele din joc
		// Salvam numarul itemelor ramase pozitiile acestora, offseturile  
		
	    game.getInventar().setFrecventeItems(this.FrecventeIteme);
		game.getInventar().lvlData=this.level_data_inventar;
	    game.getPlay().Levels.peek().statistici=this.level;
		game.getTree().Freq=this.FrecventeSkills;
		Caracter caracter=game.getCaracter();
		game.getPlay().getObjects().removeAllElements();
		for(int i=0;i<obiecte.size();i++)
			game.getPlay().getObjects().add(obiecte.get(i));
		caracter.setX(property.x);
		caracter.setY(property.y);
	    caracter.setxLvlOffset(property.xoffset);
	    caracter.setyLvlOffset(property.yoffset);
	    caracter.cul_car=property.culoare_caracter;
	    
	   
	   //caracter.getStatistici().setRemaining_health(property.stats.getRemaining_health());
		caracter.setStatistici(this.property.stats);
		
		int size=game.getPlay().enemies.size()-1;
		for(int i=0;i<enemy1.size();i++) {
			Property_enemy prop=enemy1.get(i);
		if(size>=0) {
			size--;
			
				Enemies enemy=game.getPlay().enemies.get(i);
		      enemy.setX(prop.x);;
		      enemy.setY(prop.y);
		      enemy.number_of_enemies=prop.number_of_enemies;
		      enemy.type=prop.type;
		}
		else
		{
			Enemies enemy=new Enemies(prop.x,prop.y,prop.type,prop.number_of_enemies,game);
			
			game.getPlay().enemies.add(enemy);
		}
	
			
		}
	}
	
	
}
