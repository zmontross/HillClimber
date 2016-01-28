/*
 * Desired upgrades
 * 
 * -genRandPrimaryColor to include detection of color hues that are too dark.
 * 
 * -Resize grid (pixel size)
 * 		-- All HillSteps must be resized.
 *
 * -GUI: User-adjustable parameters
 * 		-- User-chosen color
 * 			--- ColorPicker object in new window which returns a Paint value.
 * 			--- Recalculate color progression array values.
 * 			--- Link weights array values to new color progression array values.
 * 
 * -Save colors and field configs.
 * 
 * -Fixed HillStep size with an HF used to display a close-up of a much larger region.
 * 		Think of how Metroid, Pokemon, Zelda, Fire Emblem, Two Brothers,
 * 			and old classic top-down RPGs handle large worlds!
 * 
 * -Set of buttons...
 * 		-- Select Algorithm
 * 		-- Select Starting Pos
 * 		-- Select Goal Pos (for appropriate algorithm)
 * 
 * -Timer to govern AI.
 * 		-- Runtime-adjustable frequency.
 * 		-- Slows-down AI to show its progress to user.
 * 		--Need to look into timeline so that I can do time stuff with JavaFX.
 * 
 * -AI
 * 		-- Timer to control algorithm-rate
 * 		-- Dropdown to select algorithm
 * 		-- "Set Start" btn to allow user to place start pos via mouse.
 * 		-- Algorithm-dependent "Set Goal" btn to allow user to place goal pos via mouse.
 * 		-- Algorithm-dependent "Set Max Traversible Steepness" to limit the delta-Weight which counts as a valid move.
 * 		-- LOCK HILL PARAMS ALTERATION WHILE RUNNING!!!!
 * 		-- Contains Timer and TimerTask objects(?). get/Set methods for checking isRunning status and changing rate of evaluations.
 *  
 * -HillField to keep track of statistics
 * 		-- All HillSteps whose booleans are true are kept track of in their own ArrayList<T>.
 * 		-- clearBooleans() methods to tell the field to reset only those steps whose booleans aren't false.
 * 		-- Record the index of the "Start Pos"
 * 		-- Record the index of the "Goal Pos"
 * 		-- (Generation-Algorithm dependent) Keep track of Global Maximum. (Algorithm-dependent) Show user AI's rating based on Max-vs-final weightings.
 * 
 * -New Class. Contains HillField, HillColorKey. Responsible for retaining data about the HillField. Clears flags. Etcetera.
 * 
 * 
 * */



package HillClimberPkg;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {

		
	
	private static final String APPTITLE = "Hill Climber";
	
	private static final int WINDOW_SIZE_WIDTH = 768 + 128;//1366;
	private static final int WINDOW_SIZE_HEIGHT = 768 + 16;
	
	Stage window;
	Scene sceneAITesting;

	VBox layoutAITesting;

	HillFieldContainer hfc = new HillFieldContainer(HillField.getSizePixelsDefault(), 20, 20);
	
	public static void main(String[] args){
		launch(args);
	}
	
	
	public void start(Stage primaryStage) throws Exception {

		window = new Stage();
		window = primaryStage;
		window.setTitle(APPTITLE);
		window.setWidth(WINDOW_SIZE_WIDTH);
		window.setHeight(WINDOW_SIZE_HEIGHT);
		window.setResizable(false);

		
		// layoutAITesting
		
		//layoutAITesting.getChildren().add(hfc);
		
		sceneAITesting = new Scene(hfc);

		window.setScene(sceneAITesting);
		window.show();
		
		
		
		
	} // END
	
	
	public void eventCloseProgram(){
		System.out.println("Closed program. Sad pandas.");
		window.close();
	} // END
	

	
	
} // END
