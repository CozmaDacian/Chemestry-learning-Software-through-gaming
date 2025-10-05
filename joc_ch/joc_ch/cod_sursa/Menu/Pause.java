package Menu;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import main_game.Game;
import states.GameState;
import states.metode;

public class Pause implements metode{
    private BufferedImage background;
    int x_start=600,y_start=150;
    boolean first_time=true;
    private PauseButton resume,save,load,quit,menu;
    private Vector<PauseButton>butoane=new Vector<>();
    private Game game;
    public Pause(Game game ) {
    	this.game=game;
    	InitializeButtons();
    }

    private void InitializeButtons() {
    	String path_file=utilz.Constante.path_name;
        background=game.loadImageFromFile(path_file+"MenuItems/Pause_Buttons.png");
       if(background==null) {
    	   System.out.println(true);
       }
        int height = 50;
        int width = 215;
        int offset_panou=25;
        int offset_blocks=88;
        int offset=height+offset_panou;
        int offsetx=60;
        int contor=0;
        // Assuming PauseButton is a class and you need to instantiate each button
        resume = new PauseButton(x_start+offsetx,y_start+offset_blocks+offset*0,width,height,"resume");
        load = new PauseButton(x_start+offsetx,y_start+offset_blocks+offset*1,width,height,"load");
        menu = new PauseButton(x_start+offsetx,y_start+offset_blocks+offset*2,width,height,"menu");
        quit = new PauseButton(x_start+offsetx,y_start+offset_blocks+offset*3,width,height,"quit");
        
        // Add buttons to the vector
        butoane.add(resume);
        butoane.add(load);
    
        butoane.add(menu);
        butoane.add(quit);
        
    }


	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
		game.getPlay().draw(g);
		
		g.drawImage(background, x_start, y_start, null);
		
	}
    public void modify_image_when_inside(PauseButton button,boolean inside) {
    	int x=button.getX()-this.x_start;
    	int y=button.getY()-this.y_start;
    	int width=button.getWidth();
    	int height=button.getHeight();
    	int value_blue;
    	int searched_value;
    	
    	if(inside) {
    		searched_value=4;
    		value_blue=120;
    	}
    	else
    	{
    		searched_value=120;
    		value_blue=0;
    	}
    
    	for(int i=x;i<=x+width;i++) {
    	
    		for(int j=y;j<=y+height;j++) {
    			 int rgba = background.getRGB(i, j);
		            

		            // Extract red, green, blue, and alpha components
		            int red = (rgba >> 16) & 0xFF;
		            int green = (rgba >> 8) & 0xFF;
		            int blue = rgba & 0xFF;
		            int alpha = (rgba >> 24) & 0xFF;
                   
		             
		             
		              if(inside) {
	 	              if(blue<=searched_value) {
		            	 
		            	  blue=value_blue;
		              }
		              }
		              else
		            	  if(blue==searched_value) {
			            	
			            	  blue=value_blue;
			              }
		              Color color=new Color(red,green,blue,alpha);
		              background.setRGB(i,j, color.getRGB());
    		}
    		
    	}
   
    }

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

    public void mouseMoved(MouseEvent e) {
    	int x=e.getX();
		int y=e.getY();
    	for(PauseButton buton: butoane) {
    		
    		if(!buton.getBounds().contains(x,y))
    		{
    			if(buton.inside==true)
    		
    		this.modify_image_when_inside(buton, false);
    	   
    	   
    		buton.inside=false;
    	      }
    	}
    	for(PauseButton buton: butoane) {
    		if(buton.getBounds().contains(x,y)) {
    		
    		if(buton.inside==false)
    	    this.modify_image_when_inside(buton, true);	
    		buton.inside=true;
    	   
    		}
    	}
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int x=e.getX();
		int y=e.getY();
		if(resume.getBounds().contains(x,y))
			GameState.setCurrentOption(GameState.PLAYING);
		if(load.getBounds().contains(x, y)) {
			Deserialize_Class classa=new Deserialize_Class();
			Load_Last_Checkpoint point=classa.deserialize(utilz.Constante.path_name+"Save/1.json");
			point.initialize_variables(game);
			GameState.setCurrentOption(GameState.PLAYING);

		}
	   if(menu.getBounds().contains(x,y)) {
		   game.getMenu().AddButtons();
		   GameState.setCurrentOption(GameState.MENU);
	   }
       if(quit.getBounds().contains(x,y)) {
    	   System.exit(0);
       }
	}
    
       
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			GameState.setCurrentOption(GameState.PLAYING);
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
	
}
