package Menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize_Class {

	public Deserialize_Class() {
		
	}
	public Load_Last_Checkpoint deserialize(String path_name) {
		Load_Last_Checkpoint checkpoint=null;
		 try (FileInputStream fileIn = new FileInputStream(path_name);
	             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
	            
	            // Read the object from the stream and cast it to SerializedClass
	           checkpoint = (Load_Last_Checkpoint) objectIn.readObject();
	            
	      
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Error during deserialization: " + e.getMessage());
	        }
	    
	return checkpoint;
	}
}
	

