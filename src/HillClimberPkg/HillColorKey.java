package HillClimberPkg;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class HillColorKey extends ScrollPane{

	private static final double SIZE_VBOX_WIDTH = 45.0;			// Maximum width to use when using a VBox
	private static final double SIZE_HBOX_HEIGHT = 30.0;		// Maximum height to use when using an HBox

	private int colorFontSize = 18;								// Local value of the font used for the color weight numbers.
	
	private boolean isHorizontal = false;								// If true, this object will generate an HBox as opposed to a VBox. 					
	
	private HBox hbox;
	private VBox vbox;
	
	
	/**
	 * This is an extension of the ScrollPane class. This class provides either a horizontal or vertical color-weights-key.
	 * @param colorProg Color Progression Array produced by a HillField object. This is of type ArrayList<Paint>, and as such all values within are Paints.
	 * Default values used:
	 * {@link #isHorizontal} is set to false.
	 * */
	public HillColorKey(ArrayList<Paint> colorProg){
		this(colorProg, false);
	} // END
	
	/**
	 * This is an extension of the ScrollPane class. This class provides either a horizontal or vertical color-weights-key.
	 * @param colorProg Color Progression Array produced by a HillField object. This is of type ArrayList<Paint>, and as such all values within are Paints.
	 * @param isHorizontal If true, an HBox will be used rather than a VBox.
	 * */
	public HillColorKey(ArrayList<Paint> colorProg, boolean isHorizontal){

		this.isHorizontal = isHorizontal;
		
		if(isHorizontal){
			hbox = new HBox();
			this.hbox.setMinHeight(SIZE_HBOX_HEIGHT);
			this.setPrefViewportHeight(SIZE_HBOX_HEIGHT);
			this.setContent(hbox);
			
		}
		else{
			vbox = new VBox();
			this.vbox.setMinWidth(SIZE_VBOX_WIDTH);
			this.setPrefViewportWidth(SIZE_VBOX_WIDTH);
			this.setContent(vbox);
			
		}
		
		//this.setWidth(50.0);
		//this.setMinWidth(50.0);
		
		refresh(colorProg);
		
	} // END
	
	
	
	/**
	 * Clears and refreshes the displayed color-weights listing.
	 * @param colorProg Color Progression Array produced by a HillField object. This is of type ArrayList<Paint>, and as such all values within are Paints.
	 * */
	public void refresh(ArrayList<Paint> colorProg){
		System.out.println("[HillColorkey][Refresh]");
		if(this.isHorizontal){
			// Items are arrayed horizontally.
			
			this.hbox.setAlignment(Pos.TOP_LEFT);
			
			this.hbox.getChildren().clear();
			
			for(int i=0; i<colorProg.size(); i++){
				Label l = new Label();
				l.setFont(Font.font(this.colorFontSize));
				l.setTextFill(Color.WHITE);
				l.setBackground(new Background(new BackgroundFill(colorProg.get(i), CornerRadii.EMPTY, Insets.EMPTY)));
				l.setText(stringFormat(i));
				this.hbox.getChildren().add(l);
			} // for i
			
		}
		else{
			// items are arrayed vertically.
			
			this.vbox.setAlignment(Pos.TOP_CENTER);
			
			this.vbox.getChildren().clear();
			
			for(int i=0; i<colorProg.size(); i++){
				Label l = new Label();
				l.setFont(Font.font(this.colorFontSize));
				l.setTextFill(Color.WHITE);
				l.setBackground(new Background(new BackgroundFill(colorProg.get(i), CornerRadii.EMPTY, Insets.EMPTY)));
				//l.setText(" " + Integer.toString(i) + " ");
				l.setText(stringFormat(i));
				this.vbox.getChildren().add(l);
			} // for i
		}
		
	} // END
	
	
	/**
	 * Formats the weight-value strings used in the Labels added to the object's HBox or VBox.
	 * @param i A weight value.
	 * */
	private String stringFormat(int i){
		if(this.isHorizontal){			// Horizontal information
			String prePad = null;
			String postPad = "|";
			
			if(i<10){
				prePad = "__";		// " 1  2  ...  9 10 11 12 "
			}
			else{
				prePad = "_";
			}
			return prePad + Integer.toString(i) + postPad;
		}
		else{							// Vertical information
			String prePad = null;
			String postPad = "_";
			
			if(i<10){
				prePad = "___";		// "  1  "
			}
			else{
				prePad = "__";		// " 10  "
			}
			return prePad + Integer.toString(i) + postPad;
		}
	} // END
	

	/**
	 * Sets the size of the font used in the key's labels.
	 * @param fontSize Desired size of font.
	 * */
	public void setColorFontSize(int fontSize){
		
		System.out.println("[HillColorkey][setColorFontSize]");
		
		this.colorFontSize = fontSize;
		
		if(this.isHorizontal){
			for(int i=0; i<this.hbox.getChildrenUnmodifiable().size(); i++){
				((Labeled) this.hbox.getChildren().get(i)).setFont(Font.font(this.colorFontSize));
			} // for i
		}
		else{
			for(int i=0; i<this.vbox.getChildrenUnmodifiable().size(); i++){
				((Labeled) this.vbox.getChildren().get(i)).setFont(Font.font(this.colorFontSize));
			} // for i
		}
		
	} //END
	
	
	/**
	 * Sets the maximum width.
	 * @param width Desired width.
	 * */
	public void setKeyWidth(double width){
		this.setPrefViewportWidth(width);
	} // END
	
	
	/**
	 * Sets the maximum height.
	 * @param height Desired height.
	 * */
	public void setKeyHeight(double height){
		this.setPrefViewportHeight(height);
	} // END
	
	
	/**
	 * Returns the current font size.
	 * */
	public int getColorFontSize(){
		return this.colorFontSize;
	} //END

	public String toString(){
		return super.toString() + "\n" + "";
	} // END
	
} // END