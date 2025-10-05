package Nivel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import Stats.LevelStats;
import actiune_joc.Playing;
import main_game.CheckPoint;
import main_game.Enemies;
import main_game.Game;
import utilz.Obiecte;

public class Level_Manager {
   
	   public  BufferedImage level;
	    public BufferedImage img;
	    public int max_tiles=40;
		public BufferedImage sprite;
		private  Vector<Enemies>Enemy=new Vector<>();
		private int levelData[][]=new int [50][200];
		public BufferedImage levelSprite[]=new BufferedImage[100];
		private Game game;
		public LevelStats statistici=new LevelStats(0,0,0,0,0,0);
		int value_enemy_blue=48;
	    
		
		 public int ob_totale,ob_colectate;
		
	public Level_Manager(Game game,int nr_nivel,String Lvl_Data,String Lvl_Sprite) {
		
		this.game=game;
		  for(int i=0;i<50;i++) {
		    	 for(int j=0;j<200;j++)
		    		levelData[i][j]=0;
		     }
	     Get_Sprite(nr_nivel,Lvl_Sprite);
	     Get_LvlData(nr_nivel,Lvl_Data);
	   
	}
	   
	
	   public  int height() {
		   
		   return img.getHeight();
		   
	   }

	
		
		public void Get_Sprite(int level,String name) {
			
			String filePath=utilz.Constante.path_name;
			String folderName=filePath+"Level_Sprite/Level"+level+"/";
		   
		
		    BufferedImage sprites=game.loadImageFromFile(folderName+name);
		   
		    for(int j=0;j<5;j++) {
		    	for(int i=0;i<11;i++) {
		    		int index=j*11+i;
		    		if((i+1)*32<=sprites.getWidth() && (j+1)*32<=sprites.getHeight())
		    		{
		    			
		    		
		    		levelSprite[index]=sprites.getSubimage(i*32, j*32, 32,32);
		    		levelSprite[index]=game.getCaracter().resizeImage(levelSprite[index],64,64);
		    	}
		    	}
		    }
		    
		}
		public void InterpretCheckpoint(Playing play) {
			for(int j=0;j<img.getHeight();j++) {
				for(int i=0;i<img.getWidth();i++) {
					Color color=new Color(img.getRGB(i, j));
					int value_red=color.getRed();
					int value_blue=color.getBlue();
					int value_green=color.getGreen();
					int alpha=color.getAlpha();
				    int x=i*play.getGame().TILES_SIZE;
				    int y=j*play.getGame().TILES_SIZE;
					int hitbox_width=400;
					int hitbox_height=400;
					
					
					if(value_green==128 && value_blue==128 ) {
						
						play.checkpoints.add(new CheckPoint(x,y,hitbox_width,hitbox_height));
					}
					if(value_green==128 && value_blue==129) {
						System.out.print(i+" ");
						System.out.println(j);
						play.Final.add(new CheckPoint(x-200,y-100,hitbox_width,hitbox_height));
					}
		      }
			}
			}
		public void InterpretEnemies(int tileX,int tileY,Playing play) {
			int nr=0;
			for(int j=0;j<img.getHeight();j++) {
				for(int i=0;i<img.getWidth();i++) {
					Color color=new Color(img.getRGB(i, j));
					int value_red=color.getRed();
					int value_blue=color.getBlue();
					int value_green=color.getGreen();
					int alpha=color.getAlpha()-253;
					int x=i*Game.TILES_SIZE;
					int y=j*Game.TILES_SIZE;
		            if(value_green==80) {
		             int tip=value_blue;
		             int number = 0;
		             if (tip == 2)
		            	 number = 1;
		             else {
		            	 Random rand = new Random();
		                 number = rand.nextInt(1,4);
		             }
		             
		             Enemies enemy=new Enemies(x,y,tip,number,play.getGame());
		             play.enemies.add(enemy);
		            }
		
		
		}
			}
		}
		
		
		
		public int Get_Sprite_Index(int i,int j) {
			if(i==-1 || j==-1 )
			System.out.println(i+" "+j);
			return levelData[i][j];
		}
		
		public Vector<Obiecte> Add_Objects(){
			Vector<Obiecte>obj=new Vector<Obiecte>();
			for(int j=0;j<img.getHeight();j++) {
				for(int i=0;i<img.getWidth();i++) {
					Color color=new Color(img.getRGB(i, j));
					int value_red=color.getRed();
					int value_blue=color.getBlue();
					int value_green=color.getGreen();
				    
					if(value_green==200) {
					      
						int tip=value_blue;
						
						int x=i*Game.TILES_SIZE;
						int y=j*Game.TILES_SIZE;
						Obiecte obiecte=new Obiecte(tip,x,y);
						obj.add(obiecte);
					}
				}
			}
			statistici.obiecte_totale=obj.size();
			System.out.println(obj.size());
			return obj;
		}
		
		public int[][]  Get_LvlData(int level,String name) {
			String filePath=utilz.Constante.path_name;
			String folderName=filePath+"Level_Sprite/Level"+level+"/";
			 img=game.loadImageFromFile(folderName+name);
			
			
			
			for(int j=0;j<img.getHeight();j++) {
				for(int i=0;i<img.getWidth();i++) {
					Color color=new Color(img.getRGB(i, j));
					int value_red=color.getRed();
					int value_blue=color.getBlue();
					int value_green=color.getGreen();
					
					
					if(value_red>=48)
						value_red=0;
					
					
					
					
					
					levelData[j][i]=value_red;
				}
			}
			
		
			
			
			return levelData;
		}
     
      
		
		public void draw(Graphics2D g2d) {
			     
			// calculam blocul caracterului
			int xScreen = game.getCaracter().getX() / Game.TILES_SIZE;
			int yScreen = game.getCaracter().getY() / Game.TILES_SIZE;

			// Enable anti-aliasing
			((Graphics2D) g2d).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
			// ofset de maxim un ecran in tileuri
			int max_tiles_drawn=Game.GAME_WIDTH/Game.TILES_SIZE;
			int startX = xScreen - 1;
			int endX = xScreen;
			int topY = yScreen;
			int bottomY = yScreen - 1;
            int value;
            int max_tile_left=0;
            int max_tile_right;
            if(bottomY<0)
            	bottomY=0;
            // calculam cate tile_uri trebe desenate in stanga celui curent
            if(xScreen-max_tiles_drawn<0) {
             max_tile_left=0;	
            }
            else
             max_tile_left=xScreen-max_tiles_drawn;
           
            // calculam cate tileuri trebe desenate in dreapta celui curent
            if(xScreen+max_tiles_drawn>img.getWidth()) {
            	max_tile_right=img.getWidth();
            }
            else
            	max_tile_right=xScreen+max_tiles_drawn+1;
            
            
            int xoffset=game.getCaracter().getxLvlOffset();
            int yoffset=game.getCaracter().getyLvlOffset();
			for (int i = topY, j = bottomY; i >= 0 || j < img.getHeight(); i--, j++) 
			for(int row_jos=startX, row_sus=endX;row_jos>=max_tile_left || row_sus<max_tile_right;row_jos--,row_sus++) {
		       
				// le desenam atat in stanga cat si in dreapta pentru a scapa de tile offseturi
	           if(i>=0) {
					
					if(row_sus<max_tile_right) {
					   value=Get_Sprite_Index(i,row_sus);
					  
					   g2d.drawImage(levelSprite[value],row_sus*Game.TILES_SIZE-xoffset,i*Game.TILES_SIZE-yoffset,null);
					   
					}
					if(row_jos>=max_tile_left) {
						 value=Get_Sprite_Index(i,row_jos);
						   g2d.drawImage(levelSprite[value],row_jos*Game.TILES_SIZE-xoffset,i*Game.TILES_SIZE-yoffset,null);
					}
					}
				if(j<img.getHeight()) {
					
					if(row_sus<max_tile_right) {
					   value=Get_Sprite_Index(j,row_sus);
					 
					   g2d.drawImage(levelSprite[value],row_sus*Game.TILES_SIZE-xoffset,j*Game.TILES_SIZE-yoffset,null);
					   
					}
					if(row_jos>=max_tile_left) {
						   value=Get_Sprite_Index(j,row_jos);
						  
						   g2d.drawImage(levelSprite[value],row_jos*Game.TILES_SIZE-xoffset,j*Game.TILES_SIZE-yoffset,null);
					}
					}
			
				
			}
		       
		       
		        
		        
		}
			
		




		public int[][] getLevelData() {
			return levelData;
		}
		
		
		
	}


