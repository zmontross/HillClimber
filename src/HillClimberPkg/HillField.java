package HillClimberPkg;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HillField extends Group{

	private static final int HEIGHT_LIMIT_MAX = 99;					// Absolute limit for the weightLimit parameter. All weight values must not exceed this value.
	private static final int HEIGHT_LIMIT_MIN = 5;					// Absolute minimum for the weightLimit parameter.
	private static final int HEIGHT_LIMIT_DEFAULT = 10;				// Default value for weightLimit.
	private static final int HILLSTEPS_MAX = 99;						// Absolute limit for the squares parameter.
	private static final int HILLSTEPS_MIN = 4;						// Absolute minimum for the squares parameter.
	private static final int HILLSTEPS_DEFAULT = 10;					// Default value for the squares parameter.
	private static final double SIZE_PIXELS_DEFAULT = 600.0;				// Default value for the size (px) parameter.
	private static final Paint COLORPRIMARY_DEFAULT = Color.WHITE;
	
	private int lastModifiedIndex = -1;											// Index value of the last-clicked-upon HillStep contained within the Group node.
	private int heightLimit = HEIGHT_LIMIT_DEFAULT;					// Upper limit of weight values.
	private int hillSteps = HILLSTEPS_DEFAULT;							// Local value of the given input 'squares'.
	
	private double sizePixels = SIZE_PIXELS_DEFAULT;								// Local value N of the NxN size of the HillField in pixels. 						// Local value of the size of the generated HillSteps.
		
	private ArrayList<Paint> colorProgression = new ArrayList<Paint>();	// Used to store color values that are later passed to all HillStep objects based on their weight values.
	
	private Paint colorPrimary = COLORPRIMARY_DEFAULT;					// Used to create the Paint objects contained within colorProgression.
	
	
	
	
	/**
	 * HillField. An NxN grid of HillStep objects.
	 * Default values used:
	 * {@link #SIZE_PIXELS_DEFAULT}
	 * {@link #HILLSTEPS_DEFAULT}
	 * {@link #HEIGHT_LIMIT_DEFAULT}
	 * */
	public HillField(){
		this(SIZE_PIXELS_DEFAULT, HILLSTEPS_DEFAULT, HEIGHT_LIMIT_DEFAULT);
	} // END
	
	/**
	 * HillField. An NxN grid of HillStep objects.
	 * @param hillsteps Number of HillStep objects, N, on each side of the HillField. Limited by {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * @param heightLimit Highest allowed value for HillSteps' height values. Limited by {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public HillField(int hillSteps, int heightLimit){
		this(SIZE_PIXELS_DEFAULT, hillSteps, heightLimit);
	} // END
	
	/**
	 * HillField. An NxN grid of HillStep objects.
	 * @param sizePixels The size of the HillField in pixels. The size of each HillStep object is {@link #sizePixels} divided by {@link #hillSteps}
	 * @param hillsteps Number of HillStep objects, N, on each side of the HillField. Limited by {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * @param heightLimit Highest allowed value for HillSteps' height values. Limited by {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public HillField(double sizePixels, int hillSteps, int heightLimit){
		
		// Store given attributes' values.
		this.sizePixels = sizePixels;
		this.hillSteps = hillSteps;
		this.heightLimit = heightLimit;
		
		// Limit the heightLimit to the min/max bounds
		if(this.heightLimit > HEIGHT_LIMIT_MAX)
			this.heightLimit = HEIGHT_LIMIT_MAX;
		else if(this.heightLimit < HEIGHT_LIMIT_MIN)
			this.heightLimit = HEIGHT_LIMIT_MIN;
		
		// Limit the hillSteps to the min/max bounds
		if(this.hillSteps > HILLSTEPS_MAX)
			this.hillSteps = HILLSTEPS_MAX;
		else if(this.hillSteps < HILLSTEPS_MIN)
			this.hillSteps = HILLSTEPS_MIN;
		
		this.colorPrimary = COLORPRIMARY_DEFAULT;
		
		// HillSteps are created, given random height values, and then assigned their colors.
		populateHillSteps();
		HillFieldUtils.generation.heights.random(this);
		generateColorProgression();
		syncHeightColors();
		
		HillFieldUtils.print.heights(this);
		HillFieldUtils.print.colorProgression(this);
		
	} // END
	
	

	/*#############################################################################
	 * ################### - Get Methods - #########################################
	 * #############################################################################*/
	
	/**
	 * Returns the index of the HillStep within the one-dimensional Group which stores the HillStep objects.
	 * */
	public int getLastModifiedIndex(){
		return this.lastModifiedIndex;
	} // END

	/**
	 * Returns the pixel size of one side of the HillField.
	 * */
	public double getSizePixels(){
		return this.sizePixels;
	}
	
	/**
	 * Returns the number of squares (a.k.a. HillSteps) on one side of the grid. Value will be between {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * */
	public int getSizeHillSteps(){
		return this.hillSteps;
	} // END
	
	/**
	 * Returns the current height limit. Value will between {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public int getHeightLimit(){
		return this.heightLimit;
	} // END
	
	/**
	 * Returns the current value of the primary color.
	 * */
	public Paint getColorPrimary(){
		return this.colorPrimary;
	} // END

	/**
	 * Returns color progression ArrayList<Paint>.
	 * */
	public ArrayList<Paint> getColorProgArray(){
		return this.colorProgression;
	} // END
	
	
	
	/*#############################################################################
	 * ################### - Static Get Methods - ##################################
	 * #############################################################################*/
	
	/**
	 * Returns the default pixel size of one side of the HillField.
	 * */
	public static double getSizePixelsDefault(){
		return SIZE_PIXELS_DEFAULT;
	} // END
	
	/**
	 * Returns the absolute maximum value usable for the weight limit value.
	 * */
	public static int getHeightLimitMax(){
		return HEIGHT_LIMIT_MAX;
	} // END
	
	/**
	 * Returns the absolute minimum value usable for the weight limit value.
	 * */
	public static int getHeightLimitMin(){
		return HEIGHT_LIMIT_MIN;
	} // END
	
	/**
	 * Returns the default value for the height limit value.
	 * */
	public static int getHeightLimitDefault(){
		return HEIGHT_LIMIT_DEFAULT;
	} // END
	
	/**
	 * Returns the absolute maximum value usable for the squares value.
	 * */
	public static int getHillStepsMax(){
		return HILLSTEPS_MAX;
	} // END
	
	/**
	 * Returns the absolute minimum value usable for the squares value.
	 * */
	public static int getHillStepsMin(){
		return HILLSTEPS_MIN;
	} // END
	
	/**
	 * Returns the default value for the HillSteps value.
	 * */
	public static int getHillStepsDefault(){
		return HILLSTEPS_DEFAULT;
	} // END
	
	
	
	/*#############################################################################
	 * ################### - Set Methods - #########################################
	 * #############################################################################*/

	/**
	 * Sets the currently-used value of the primary tile color.
	 * The Color Progression Array, {@link #colorProgression}, is recalculated when this method is called.
	 * HillSteps contained within the HillField are then linked to the colors from the Color Progression Array corresponding to their Height value.
	 * @param color The value used for {@link #colorPrimary} from which {@link #colorProgression} will be generated.
	 * */
	public void setColorPrimary(Paint color){
		this.colorPrimary = color;
		generateColorProgression();
		syncHeightColors();
	} // END
	
	
	
	/*#############################################################################
	 * ################### - Miscellaneous Methods - ###############################
	 * #############################################################################*/
	
	/**
	 * Generates and refreshes the Color Progression Array, {@link #colorProgression}.
	 * */
	private void generateColorProgression(){
		System.out.println("[HillField] Color progression generated.");
		String colorStr, redHex, greenHex, blueHex;
		int redMax, greenMax, blueMax, redDec, greenDec, blueDec, redVal, greenVal, blueVal;
		
		this.colorProgression.clear();
		
		colorStr = this.colorPrimary.toString();
		
		// Color values, converted to a string, are shown in the format 0x12345678
		// 12, 34, 56 are the R,G,B values. The first two characters, "0x", are skipped.
		// The last two characters, 78, are the opacity value. By default these digits are set as "ff".
		redHex = colorStr.substring(2, 4);
		greenHex = colorStr.substring(4, 6);
		blueHex = colorStr.substring(6, 8);
		
		// RGB values are converted from a string to an integer.
		redMax = Integer.valueOf(redHex, 16);
		greenMax = Integer.valueOf(greenHex, 16);
		blueMax = Integer.valueOf(blueHex, 16);
		
		// The value by which successive colors in the progression will differ from previous and successive colors.
		redDec = redMax / this.heightLimit;
		greenDec = greenMax / this.heightLimit;
		blueDec = blueMax / this.heightLimit;
				
		// Using the max RGB values from the original color, and the decrement values we just calculated,
		//  the color progression's other colors are calculated from darkest to lightest.
		for(int weight=0; weight<this.heightLimit; weight++){
			redVal = redMax - (redDec * (this.heightLimit - weight));
			greenVal = greenMax - (greenDec * (this.heightLimit - weight));
			blueVal = blueMax - (blueDec * (this.heightLimit - weight));
			this.colorProgression.add(Color.rgb(redVal, greenVal, blueVal));
		} // for weight
		
		// The last color to be added is the original color from which the others were derived.
		colorProgression.add(this.colorPrimary);
		
	} // END

	/**
	 * Following the creation or refreshment of {@link #colorProgression}, or the creation or refreshment of the HillSteps' height values,
	 *  the colors of each HillStep are assigned based on their stored height value.
	 * */
	private void syncHeightColors(){
		System.out.println("[HillField] Heights and colors synced");
		for(int i=0; i<this.getChildrenUnmodifiable().size(); i++){
			int height = ((HillStep) this.getChildren().get(i)).getHeightVal();
			((HillStep) this.getChildren().get(i)).setColor( this.colorProgression.get(height) );
		}
	} // END

	/**
	 * Populates the HillField with generic HillStep objects.
	 * */
	private void populateHillSteps(){
		System.out.println("[HillField] HillSteps populated.");
		this.getChildren().clear();
		int hillStepsPerSide = this.getSizeHillSteps();
		double hillStepSize = this.getSizePixels() / hillStepsPerSide;
		
		// Generate HillSteps and populate the HillField.
		for(int col=0; col<this.getSizeHillSteps(); col++){
			// For each COLUMN
			for(int row=0; row<this.getSizeHillSteps(); row++){
				// For each ROW
				HillStep hs = new HillStep((hillStepSize*row), (hillStepSize*col), hillStepSize); // Xpos, Ypos, Xsize, Ysize 

				hs.setOnMouseClicked(e -> {
					this.lastModifiedIndex = this.getChildrenUnmodifiable().indexOf(hs);
				});
				this.getChildren().add(hs);
			} // for row
		} // for col
	} // END
	
	/**
	 * Scraps and recreates a new randomly-generated set of HillStep objects using the given parameters.
	 * @param hillsteps Number of HillStep objects, N, on each side of the HillField. Limited by {@link #HILLSTEPS_MIN} and {@link #HILLSTEPS_MAX}.
	 * @param heightLimit Highest allowed value for HillSteps' height values. Limited by {@link #HEIGHT_LIMIT_MIN} and {@link #HEIGHT_LIMIT_MAX}.
	 * */
	public void regenEntireField(int hillSteps, int heightLimit){
		System.out.println("[HillField] Entire HillField regenerated.");
		this.heightLimit = heightLimit;
		this.hillSteps = hillSteps;
		populateHillSteps();
		HillFieldUtils.generation.heights.random(this);
		setColorPrimary(this.colorPrimary);
	} // END
	
	
	public void regenEntireFieldGradient(int hillSteps, int heightLimit, HillFieldUtils.gradientDirection gdir){
		System.out.println("[HillField] Entire HillField regenerated as " + gdir.toString() + "gradient");
		this.heightLimit = heightLimit;
		this.hillSteps = hillSteps;
		populateHillSteps();
		HillFieldUtils.generation.heights.gradient(this, gdir);
		setColorPrimary(this.colorPrimary);
	} // END
	
	
	
	/**
	 * Overridden toString() method.
	 * */
	public String toString(){
		String s = "Size, Px  : " + this.getSizePixels() + "\t"
				+ "Steps/side: " + this.getSizeHillSteps() + "\t"
				+ "Step Color: " + this.getColorPrimary() + "\t"
				+ "Max Height: " + this.getHeightLimit() + "\t"
				+ "Clicked Last: " + this.getLastModifiedIndex();
		if( this.getLastModifiedIndex() > -1){
			s = s.concat("\t" + "Height: " + ((HillStep)this.getChildrenUnmodifiable().get(this.getLastModifiedIndex())).getHeightVal());
		}
		else{
			s = s.concat("\t" + "Height: -1");
		}
		
		return s;
		
		/*
		return this.getId() + "\n"
		+ "Size, Px  : " + this.getSizePixels() + "\n"
		+ "Steps/side: " + this.getSizeHillSteps() + "\n"
		+ "Step Color: " + this.getColorPrimary() + "\n"
		+ "Max Height: " + this.getHeightLimit() + "\n"
		+ "Clicked Last: " + this.getLastModifiedIndex() + "\n";*/
		
	} // END
} // END
