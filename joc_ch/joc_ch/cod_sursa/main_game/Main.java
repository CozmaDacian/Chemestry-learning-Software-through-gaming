package main_game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFrame;

public class Main {
 
	
	public static void main(String[] args) {
		String default_arg1 = "C:/De ce Eu project X/";
		if (args.length ==0)
		utilz.Constante.path_name = default_arg1;
				else {
			if (args.length ==1)
					utilz.Constante.path_name = args[0];
			if (args.length==2) {
				utilz.Constante.nr_nivele = 2;
			}
		}
		System.setProperty("awt.mouse.clickInterval", "1");
		Game game=new Game();
    	
	
	}
	}
	
