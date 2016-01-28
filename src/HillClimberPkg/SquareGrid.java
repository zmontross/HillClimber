package HillClimberPkg;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SquareGrid {

	
	
	
	private static final Paint[] grays = {	
		Color.DIMGRAY,
		Color.DARKGRAY,
		Color.GRAY,
		Color.LIGHTGRAY,
		Color.DARKSLATEGRAY,
		Color.SLATEGRAY,
		Color.LIGHTSLATEGRAY
	};
	
	private int grayIndex = 0;
	
	public String mouseOverCoord;
	
	public Group grid = new Group();
	
		
	public SquareGrid(int squares, double size){
		
		double squareSize = size / squares;
		
		for(int i=0; i<squares; i++){
			
			for(int j=0; j<squares; j++){
				
				Rectangle r = new Rectangle((squareSize*i), (squareSize*j), squareSize, squareSize);
								
				r.setFill(grays[grayIndex]);
				grayIndex++;
				if(grayIndex > (grays.length - 1)){
					grayIndex = 0;
				}
				
				
				/*
				r.addEventHandler(MouseEvent.MOUSE_ENTERED,
						new EventHandler<MouseEvent>(){
							@Override
							public void handle(MouseEvent event) {
								// TODO Auto-generated method stub
								int index = grid.getChildrenUnmodifiable().indexOf(r);
								int coordX = index / squares;
								int coordY = index % squares;
								
								mouseOverCoord = Integer.toString(coordX) + ',' + Integer.toString(coordY);
								
								//System.out.println("X|Y: " + coordX + "|" + coordY);
							} // END
					} // END
				);
				*/
				
				
				r.addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>(){
							@Override
							public void handle(MouseEvent event) {
								// TODO Auto-generated method stub
								int index = grid.getChildrenUnmodifiable().indexOf(r);
								int coordX = index / squares;
								int coordY = index % squares;
								
								mouseOverCoord = Integer.toString(coordX) + ',' + Integer.toString(coordY);
								
								System.out.println("X|Y: " + coordX + "|" + coordY);
							} // END
					} // END
				);
				
				
				
				grid.getChildren().add(r);
			}
			
		}
		
		
		grid.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						System.out.println("\tGRID Event triggered!");
					} // END
			} // END
		);
		
	} // EnD
	
} // END
