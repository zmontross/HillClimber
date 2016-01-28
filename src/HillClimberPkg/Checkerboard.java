package HillClimberPkg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Checkerboard {

	
	private static boolean colorFlag = false;
	
	
	public static Group make(int squares, double size){
		
		
		Group g = new Group();
		
		
		
		for(int i=0; i<squares; i++){
			for(int j=0; j<squares; j++){
				
				Rectangle r = new Rectangle((size*i), (size*j), size, size);
				
				r.setFill(toggleColor());
				
				g.getChildren().add(r);
			}
			
			// Offset colors
			if((squares%2) == 0){
				if(colorFlag == true){
					colorFlag = false;
				}
				else{
					colorFlag = true;
				}
			}
			
			
		}
		
		return g;
	}
	
	
	private static Paint toggleColor(){
		if(colorFlag){
			colorFlag = false;
			return Color.WHITE;
		}
		else{ 
			colorFlag = true;
			return Color.BLACK;
		}
	}
	
	
}
