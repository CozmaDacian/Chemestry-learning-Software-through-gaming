package Nivel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hashing_objects {
    
	public String s[]=new String [250];
	int nr_obiectex=2,nr_obiectey=2;
    BufferedImage Objects,ColorSprite;
    
    
    Hashing_objects(BufferedImage Objects,BufferedImage ColorSprite){
    	this.Objects=Objects;
    	for(int i=1;i<=250;i++) {
    		s[i]=new String();
    	   
           }
    }
    public Map<Integer,String>Text=new HashMap<>();
    public Map<Integer,BufferedImage>obj=new HashMap<>();
    public Map<Integer,Integer>Culori=new HashMap<>();
    
    public void read_objects() {
    
    int iteratii=Objects.getWidth()/nr_obiectex;
    
    
    int ct=0;
    for(int i=0;i<=iteratii;i++) {
    BufferedImage sub=Objects.getSubimage(i*48,0, 48, 48);
    obj.put(i, sub);
    }
    
    }
    
    	
    
    
   
    	

    	public void ReadFile(){
    		try {
                File myObj = new File("C:\\De ce eu Project X\\test.txt");
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    StringBuilder numberBuilder = new StringBuilder();
 
                    int i;
                    for ( i = 0; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (Character.isDigit(currentChar)) {
                            numberBuilder.append(currentChar);
                        } else if (numberBuilder.length() > 0) {
                            int extractedNumber = Integer.parseInt(numberBuilder.toString());
                            System.out.println("Extracted number: " + extractedNumber);
                            numberBuilder.setLength(0); // Reset StringBuilder
                            line=line.substring(i,line.length()-1);
                            Text.put(extractedNumber, line);
                        break;
                        }
                    }
               
                }
    		}
    	 
    	catch(FileNotFoundException e) {
    		
    		e.printStackTrace();
    		 System.out.println("File not found.");
    	}
    	
        }
       
    	
    }

    
    
    
    
    
    
    
    
    
	

