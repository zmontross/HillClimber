package HillClimberPkg;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ColorGrid {

	
	private static final Paint C1 = Color.GOLD;
	private static final Paint C2 = Color.GOLDENROD;
	private static final Paint C3 = Color.DARKGOLDENROD;
	private static final Paint C4 = Color.GRAY;
	
	
	private int coordX = 0;
	private int coordY = 0;
	private int index = 0;
	
	public Group grid = new Group();
	
	
		
	public ColorGrid(int squares, double size){
		
		double squareSize = size / squares;
		
		for(int i=0; i<squares; i++){
			// For each COLUMN
			
			for(int j=0; j<squares; j++){
				// For each ROW
				
				Rectangle r = new Rectangle((squareSize*j), (squareSize*i), squareSize, squareSize); // Xpos, Ypos, Xsize, Ysize
								
				r.setFill(C4);
				
				r.addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>(){
							@Override
							public void handle(MouseEvent event) {
								// Record coordinates of clicked rectangle
								index = grid.getChildrenUnmodifiable().indexOf(r);
								coordX = index / squares;
								coordY = index % squares;
								
								// Modify fill colors of adjacent (valid) rectangles.
								/*
								if(coordX == 0){
									// If coordinate is on LEFT edge
								}
								else if(coordX == (squares - 1)){
									// If coordinate is on RIGHT edge
								}
								else if(coordY == 0){
									// If coordinate is on TOP edge
								}
								else if(coordY == (squares - 1)){
									// If coordinate is on BOTTOM edge
								}
								else{
									// Coordinate is not on an edge
								} */
								
								
								r.setFill(C1);
								
								// Not having bounds checking was annoying me...
								if((index+1) < grid.getChildrenUnmodifiable().size()){
									((Shape) grid.getChildren().get(index + 1 )).setFill(C2);										
								}
								if((index - 1) >= 0)  {
									((Shape) grid.getChildren().get(index - 1 )).setFill(C2);
								}
								if((index + squares) < grid.getChildren().size()){
									((Shape) grid.getChildren().get(index + squares )).setFill(C2);
								}
								if((index - squares) >= 0){
									((Shape) grid.getChildren().get(index - squares )).setFill(C2);
								}
								
								
								if((index+2) < grid.getChildrenUnmodifiable().size()){
									((Shape) grid.getChildren().get(index + 2 )).setFill(C3);
								}
								if((index-2) >= 0){
									((Shape) grid.getChildren().get(index - 2 )).setFill(C3);
								}
								if((index + squares + squares) < grid.getChildrenUnmodifiable().size()){
									((Shape) grid.getChildren().get(index + squares + squares)).setFill(C3);
								}
								if((index - squares - squares ) >= 0){
									((Shape) grid.getChildren().get(index - squares - squares )).setFill(C3);
								}
								if((index + squares + 1) < grid.getChildrenUnmodifiable().size()){
									((Shape) grid.getChildren().get(index + squares + 1)).setFill(C3);
								}
								if((index + squares + 1) < grid.getChildrenUnmodifiable().size()){
									((Shape) grid.getChildren().get(index + squares - 1)).setFill(C3);
								}
								if((index - squares + 1) >= 0){
									((Shape) grid.getChildren().get(index - squares + 1)).setFill(C3);
								}
								if((index - squares - 1) >= 0){
									((Shape) grid.getChildren().get(index - squares - 1)).setFill(C3);
								}
								
							} // END
					} // END
				);	
				grid.getChildren().add(r);
			} // for j
		} // for i
		
		( (Shape) grid.getChildren().get(0) ).setFill(Color.RED);
		((Shape) grid.getChildren().get(squares - 1)).setFill(Color.ORANGE);
		((Shape) grid.getChildren().get(grid.getChildrenUnmodifiable().size() - squares)).setFill(Color.YELLOW);
		((Shape) grid.getChildren().get(grid.getChildrenUnmodifiable().size() - 1)).setFill(Color.GREEN);
		
	} // END


	
	public String toString(){
		return Integer.toString(coordX) + ',' + Integer.toString(coordY);
	}
	
} // END
