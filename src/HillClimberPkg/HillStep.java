/*
 * Desired upgrades
 * 
 * */

package HillClimberPkg;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class HillStep extends Group{

	private static final int DEFAULT_HEIGHTVAL = 0;
	private static final Paint DEFAULT_COLOR = Color.GREY;
	
	private static final double SIZE_MULT = 0.1875; 

	private static final Paint COLOR_CONSIDERED = Color.AQUA;
	private static final Paint COLOR_CHOSEN = Color.HOTPINK;
	private static final Paint COLOR_START = Color.YELLOW;
	private static final Paint COLOR_END = Color.ORANGERED;
	
	
	private Rectangle background;
	private Rectangle considered;
	private Rectangle chosen;
	private Rectangle start;
	private Rectangle goal;
	private int heightVal;
	private double size;
	
	
	
	/**
	 * This is the most basic unit of our HillField.
	 * @param x x-coordinate used for background rectangle.
	 * @param y y-coordinate used for background rectangle.
	 * @param size Width and height used for the background rectangle.
	 * Default values used:
	 * {@link #DEFAULT_HEIGHTVAL}
	 * {@link #DEFAULT_COLOR}
	 * */
	public HillStep(double x, double y, double size){
		this(x, y, size, DEFAULT_HEIGHTVAL, DEFAULT_COLOR);
	}
	
	/**
	 * This is the most basic unit of our HillField.
	 * @param x x-coordinate used for background rectangle.
	 * @param y y-coordinate used for background rectangle.
	 * @param size Width and height used for the background rectangle.
	 * @param heightVal HillStep objects have a "height". Note: This is not the "pixel height" of the object.
	 * @param color Background-rectangle's fill color which corresponds to its weight value.
	 * */
	public HillStep(double x, double y, double size, int heightVal, Paint color){
		
		this.size = size;
		double subSize = size * SIZE_MULT;
		double xPosLeft = x + (size / 2) - subSize;
		double xPosRight = x + (size / 2);
		double yPosTop = y + (size / 2) - subSize;
		double yPosBtm = y + (size / 2);
		
		background = new Rectangle(x, y, size, size);
		background.setFill(color);
		background.setVisible(true);
		
		considered = new Rectangle(xPosLeft, yPosTop, subSize, subSize);
		considered.setFill(COLOR_CONSIDERED);
		considered.setVisible(false);
		
		chosen = new Rectangle(xPosRight, yPosTop, subSize, subSize);
		chosen.setFill(COLOR_CHOSEN);
		chosen.setVisible(false);
		
		start = new Rectangle(xPosLeft, yPosBtm, subSize, subSize);
		start.setFill(COLOR_START);
		start.setVisible(false);
		
		goal = new Rectangle(xPosRight, yPosBtm, subSize, subSize);
		goal.setFill(COLOR_END);
		goal.setVisible(false);
		
		
		this.getChildren().addAll(background, considered, chosen, start, goal);

		setHeightVal(heightVal);
		
	} // END
	
	// ##### Get #####
	public Boolean getConsidered(){
		return this.considered.isVisible();
	} // END
	
	public boolean getChosen(){
		return this.chosen.isVisible();
	} // END
	
	public Boolean getStart(){
		return this.start.isVisible();
	} // END
	
	public boolean getGoal(){
		return this.goal.isVisible();
	} // END
	
	public int getHeightVal(){
		return this.heightVal;
	} // END
	
	public double getSize(){
		return this.size;
	}
	
	
	// ##### Set #####
	public void setConsidered(boolean b){
		this.considered.setVisible(b);
	} // END
		
	public void setChosen(boolean b){
		this.chosen.setVisible(b);
	} // END
	
	public void setStart(boolean b){
		this.start.setVisible(b);
	} // END
		
	public void setGoal(boolean b){
		this.goal.setVisible(b);
	} // END
	
	public void setColor(Paint color){
		this.background.setFill(color);
	} // END
	
	public void setHeightVal(int heightVal){
		this.heightVal = heightVal;
	} // END
	
	public void resetFlags(){
		this.setConsidered(false);
		this.setChosen(false);
		this.setStart(false);
		this.setGoal(false);
	} // END
	
	

	
	
	// ##### toString #####
	public String toString(){
		return super.toString() +
				"\n Considered=" + considered.visibleProperty().get() +
				"\n Chosen=" + chosen.visibleProperty().get() +
				"\n Start=" + chosen.visibleProperty().get() +
				"\n Goal=" + chosen.visibleProperty().get() +
				"\n Bkgnd=" + background.getFill() +
				"\n Weight=" + heightVal;
	} // END
	
} // END
