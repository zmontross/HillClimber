
/*
 * Known bugs
 * 
 * "Moved to position" gives last position rather than new Current Position in console prints.
 * 
 * Algorithm will take long way around to get to a high point. Algorithm needs to choose the HIGHEST value seen, and maybe handle equivalencies from there.
 * 	Currently some other evaluation is made which results in occasionally improper behavior.
 * 
 * 
 * Eventual fixes
 * 
 * Making everything a one-dimensional Collection was silly. Use a collection of collections (i.e. ArrayList<> of ArrayList<>'s) for true row/column rep.
 * 	Makes the math easier. This means we don't have this weird distinction between indicies and x/y coordinates.
 * 
 * */



package HillClimberPkg;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HillFieldContainer extends BorderPane{
	
	private HillField hf;
	
	private HillColorKey hck;
	private Button btnStart;
	private Button btnStop;
	private Button btnSetStartingPos;
	private Button btnResetField;
	private ComboBox<Integer> cbTimeDelay;;
	private HBox hboxRightElements;
	private VBox vboxAIElements;
	
	private Label lblInformation;
	private ColorPicker cpicker;
	private Button btnRegenHF;
	private Button btnHFGradUp;
	private Button btnHFGradDown;
	private Button btnHFGradLeft;
	private Button btnHFGradRight;
	private HBox hboxBottomButtons;
	private VBox vboxBottomElements;
	
	private static int[] timeDelayValues = { 1, 10, 50, 100, 250, 500, 1000, 2000, 5000};
	
	private Random r;
	private int startingPos = -1;
	
	private boolean inProgress = false;
	private boolean isSettingStartingPos = false;
	
	private HillClimber climber;
	
	public HillFieldContainer(){
		this(HillField.getSizePixelsDefault(), HillField.getHillStepsDefault(), HillField.getHeightLimitDefault());
	} // END
	
	
	public HillFieldContainer(double size, int hillSteps, int heightLimit){
		
		vboxAIElements = new VBox(); 
		hboxRightElements = new HBox();
		hboxBottomButtons = new HBox();
		vboxBottomElements = new VBox();
		
		// Setting-up center of window's BorderPane
		hf = new HillField(size, hillSteps, heightLimit);
		hf.setOnMouseClicked(e -> eventHFMouseClick(e));
		this.setCenter(hf);
		
		
		// Setting-up right side of window's BorderPane
		hck = new HillColorKey(hf.getColorProgArray());
		hck.setOnMouseClicked(e -> eventUpdateHCK());
		
		btnStart = new Button("Start");
		btnStop = new Button("Stop");
		btnSetStartingPos = new Button("Set Starting Position");
		btnResetField = new Button("Reset Field");
		cbTimeDelay = new ComboBox<Integer>();
		
		btnStart.setOnAction(e -> eventAIStart());
		btnStop.setOnAction(e -> eventAIStop());
		btnSetStartingPos.setOnAction(e -> eventAISetStartingPos());
		btnResetField.setOnAction(e -> eventbtnResetField());
		for(int i=0; i<timeDelayValues.length; i++){
			cbTimeDelay.getItems().add(timeDelayValues[i]);
		} // for i
		cbTimeDelay.getSelectionModel().clearAndSelect(cbTimeDelay.getItems().size() - 1);
		cbTimeDelay.setOnAction(e -> eventcbTimeDelay());
		
		// All buttons are set to the width of their container node by simply allowing them to be as big as possible
		btnStart.setMaxWidth(Double.MAX_VALUE);
		btnStop.setMaxWidth(Double.MAX_VALUE);
		btnSetStartingPos.setMaxWidth(Double.MAX_VALUE);
		btnResetField.setMaxWidth(Double.MAX_VALUE);
		cbTimeDelay.setMaxWidth(Double.MAX_VALUE);
		
		vboxAIElements.getChildren().addAll(new Label(""),
											btnStart,
											new Label(""),
											new Label("Time Delay:"),
											cbTimeDelay,
											new Label(""),
											btnStop,
											new Label(""),
											new Label(""),
											btnSetStartingPos,
											new Label(""),
											new Label(""),
											btnResetField);
		hboxRightElements.getChildren().addAll(hck, vboxAIElements);
		this.setRight(hboxRightElements);
		
		
		
		
		// Setting-up bottom of window's BorderPane
		lblInformation = new Label();
		btnRegenHF = new Button("Randomize Field");
		btnHFGradUp = new Button("Generate Up-Gradient");
		btnHFGradDown = new Button("Generate Down-Gradient");
		btnHFGradLeft = new Button("Generate Left-Gradient");
		btnHFGradRight = new Button("Generate Right-Gradient");
		
		cpicker = new ColorPicker();
		
		btnRegenHF.setOnAction(e -> eventBtnRegenHF());
		btnHFGradUp.setOnAction(e -> eventBtnHFGradUp());
		btnHFGradDown.setOnAction(e -> eventBtnHFGradDown());
		btnHFGradLeft.setOnAction(e -> eventBtnHFGradLeft());
		btnHFGradRight.setOnAction(e -> eventBtnHFGradRight());
		cpicker.setValue((Color) this.hf.getColorPrimary());
		cpicker.setOnAction(e -> eventCpicker());
		
		hboxBottomButtons.getChildren().addAll(btnRegenHF, cpicker, new Label("  "), btnHFGradUp, btnHFGradDown, btnHFGradLeft, btnHFGradRight);
		hboxBottomButtons.setAlignment(Pos.CENTER);
		vboxBottomElements.getChildren().addAll(new Label(""),
												lblInformation,
												new Label(""),
												hboxBottomButtons,
												new Label(""));
		vboxBottomElements.setAlignment(Pos.CENTER);
		this.setBottom(vboxBottomElements);
		
		
		// Set the starting position to a random location.
		r = new Random();
		startingPos = r.nextInt(this.hf.getChildrenUnmodifiable().size());
		((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		updateInfoLabel();
		
		btnStop.setDisable(true);
		
	} // END
	
	
	public HillField getHillField(){
		return this.hf;
	} // END
	
	public HillColorKey getHillColorKey(){
		return this.hck;
	} // END
	
	public void setColorPrimary(Paint color){
		System.out.println("[HillFieldContainer][SetColorPrimary][Color: " + color.toString() + "]");
		this.hf.setColorPrimary(color);
		this.hck.refresh(this.hf.getColorProgArray());
	} // END
	
	
	private void updateInfoLabel(){
		if(this.startingPos == -1){
			this.lblInformation.setText("Current Starting Position: Not Set (origin 0,0 in top-left corner)");
		}
		else{
			int stepsPerRow = this.hf.getSizeHillSteps();
			int xpos = this.startingPos / stepsPerRow;
			int ypos = this.startingPos % stepsPerRow;
			this.lblInformation.setText("Current Starting Position: " + xpos + "," + ypos + " (origin 0,0 in top-left corner)");
		}
	} // END
	
	
	private void eventHFMouseClick(MouseEvent e){
		if(!this.inProgress){
			if(this.isSettingStartingPos){
				
				// Remove last starting position
				((HillStep) this.hf.getChildren().get(startingPos)).setStart(false);
				
				// Store new starting position
				this.startingPos = this.hf.getLastModifiedIndex();
				((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
				
				this.isSettingStartingPos = false;
				this.btnStart.setDisable(false);
				this.cbTimeDelay.setDisable(false);
				this.btnResetField.setDisable(false);
				this.btnRegenHF.setDisable(false);
			} // if(isSettingStartingPos)
		} // if(!this.inProgress)
		updateInfoLabel();
		
		if(e.getButton() == MouseButton.SECONDARY){
			System.out.println("Clicked on pos " + this.hf.getLastModifiedIndex() + " with height of " + ((HillStep) this.hf.getChildren().get(this.hf.getLastModifiedIndex())).getHeightVal());
		}
	} // END
	
	private void eventUpdateHCK(){
		this.hck.refresh(this.hf.getColorProgArray());
	} // END
	
	
	
	private void eventAIStart(){
		/*
		 * If startingPos was for some reason not initialized, do nothing.
		 * If we're in the process of setting the starting position, do nothing.
		 * Else, Start the AI's processing.
		 * */
		if(this.startingPos == -1){
			// Do nothing
		}
		else if(this.isSettingStartingPos){
			// Do nothing
		}
		else if(!this.inProgress){
			eventbtnResetField();
			this.inProgress = true;
			this.btnStart.setDisable(true);
			this.btnStop.setDisable(false);

			this.btnSetStartingPos.setDisable(true);
			this.btnResetField.setDisable(true);
			this.btnRegenHF.setDisable(true);
			
			// TODO AI stuff starts here.
			climber = new HillClimber("climber01");
			climber.start();
		}
		
	} // END
	
	private void eventAIStop(){
		/*
		 * If AI Testing is in progress, turn off flag
		 * */
		if(this.inProgress){
			this.inProgress = false;
			this.btnStart.setDisable(false);
			this.btnStop.setDisable(true);
			this.btnSetStartingPos.setDisable(false);
			this.btnResetField.setDisable(false);
			this.btnRegenHF.setDisable(false);
		} // if(this.inProgress)
	} // END
	
	private void eventAISetStartingPos(){
		/*
		 * If testing is not in progress, go ahead and allow a starting position change.
		 * */
		if(!this.inProgress){
			if(!this.isSettingStartingPos){
				this.isSettingStartingPos = true;
				this.btnStart.setDisable(true);
				this.btnStop.setDisable(true);
				this.cbTimeDelay.setDisable(true);
				this.btnResetField.setDisable(true);
				this.btnRegenHF.setDisable(true);
			} // if(!this.isSettingStartingPos)
			else{
				this.isSettingStartingPos = false;
				this.btnStart.setDisable(false);
				this.btnStop.setDisable(true);
				this.cbTimeDelay.setDisable(false);
				this.btnResetField.setDisable(false);
				this.btnRegenHF.setDisable(false);
			} // else
		} // if(!this.inProgress)
		
		updateInfoLabel();
		
	} // END

	private void eventbtnResetField(){
		/*
		 * If we're not in-progress, clear all flags on the field's HillStep objects.
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			
			for(int i=0; i< this.hf.getChildrenUnmodifiable().size(); i++){
				((HillStep) this.hf.getChildren().get(i)).resetFlags();
			} // for i

			//this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
			
		} // if(!this.inProgress)
		updateInfoLabel();
		
	} // END
	
	private void eventcbTimeDelay(){
		if(!this.isSettingStartingPos){
			if(this.inProgress){
				// TODO Pass the current value to the AI object.
			} // if(this.inProgress) 
		} // if(!this.isSettingStartingPos)
	} // END
	
	
	private void eventBtnRegenHF(){
		/*
		 * If we're not in-progress, refresh the entire field (colors remain the same)
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			this.hf.regenEntireField(this.hf.getSizeHillSteps(), this.hf.getHeightLimit());
			this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		} // if(!this.inProgress)
		updateInfoLabel();
		HillFieldUtils.print.heights(this.hf);
	} // END
	
	
	
	private void eventBtnHFGradUp(){
		/*
		 * If we're not in-progress, refresh the entire field (colors remain the same)
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			this.hf.regenEntireFieldGradient(this.hf.getSizeHillSteps(), this.hf.getHeightLimit(), HillFieldUtils.gradientDirection.up);
			this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		} // if(!this.inProgress)
		updateInfoLabel();
		HillFieldUtils.print.heights(this.hf);
	} // END
	
	private void eventBtnHFGradDown(){
		/*
		 * If we're not in-progress, refresh the entire field (colors remain the same)
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			this.hf.regenEntireFieldGradient(this.hf.getSizeHillSteps(), this.hf.getHeightLimit(), HillFieldUtils.gradientDirection.down);
			this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		} // if(!this.inProgress)
		updateInfoLabel();
		HillFieldUtils.print.heights(this.hf);
	} // END
	
	private void eventBtnHFGradLeft(){
		/*
		 * If we're not in-progress, refresh the entire field (colors remain the same)
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			this.hf.regenEntireFieldGradient(this.hf.getSizeHillSteps(), this.hf.getHeightLimit(), HillFieldUtils.gradientDirection.left);
			this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		} // if(!this.inProgress)
		updateInfoLabel();
		HillFieldUtils.print.heights(this.hf);
		
	} // END
	
	private void eventBtnHFGradRight(){
		/*
		 * If we're not in-progress, refresh the entire field (colors remain the same)
		 * */
		if(!this.inProgress){
			
			if(this.isSettingStartingPos){
				this.isSettingStartingPos = false;
			} // if(this.isSettingStartingPos)
			this.hf.regenEntireFieldGradient(this.hf.getSizeHillSteps(), this.hf.getHeightLimit(), HillFieldUtils.gradientDirection.right);
			this.startingPos = this.r.nextInt(this.hf.getChildrenUnmodifiable().size()+1);
			((HillStep) this.hf.getChildren().get(startingPos)).setStart(true);
		} // if(!this.inProgress)
		updateInfoLabel();
		HillFieldUtils.print.heights(this.hf);
	} // END
	

	private void eventCpicker(){
		this.setColorPrimary(this.cpicker.getValue());
	} // END
	
	
	
	
	public class HillClimber extends Thread{
		
		private int currentPos = 0;
		private Thread t;
		private String threadName;
		
		public HillClimber(String name){
			threadName = name;
			//System.out.println("Creating " + threadName);
		} // END
		
		public void run(){
			System.out.println("Running thread \"" + threadName + "\"");
			currentPos = startingPos;
			((HillStep)hf.getChildrenUnmodifiable().get(currentPos)).setChosen(true);
			
			while(inProgress){
				evaluate(this.currentPos);
			} // while(inProgress)
			
			
			System.out.println("Thread " + threadName + " exiting");
		} // END
		
		public void start(){
			System.out.println("Starting " + threadName);
			if(t == null){
				t = new Thread(this, threadName);
				t.start();
			}
		}
		
		
		
		private void evaluate(int position){
			int currentHeight = ((HillStep)hf.getChildrenUnmodifiable().get(position)).getHeightVal();
			int[] validMoves = HillFieldUtils.getValidMoves(hf, position);
			int[] validMovesHeights = {-1, -1, -1, -1};
			boolean[] isGreaterOrEqualCheck = {false, false, false, false};
			int numberOfAcceptableMoves = 0;
			int lastAcceptableIndexSeen = 0;
			
			for(int i=0; i<validMoves.length; i++){
				if(validMoves[i] != -1){
					validMovesHeights[i] = ((HillStep)hf.getChildrenUnmodifiable().get(validMoves[i])).getHeightVal();
				}
			} // for i
			
			
			System.out.println("\nCurrent position is " + position + " with height of " + currentHeight );
			System.out.println("\tValid Moves: " + validMoves[0] + ", " + validMoves[1] + ", " + validMoves[2] + ", " + validMoves[3] + " (Up/Dn/Lf/Rt)");
			System.out.println("\tHeights: " + validMovesHeights[0] + ", " + validMovesHeights[1] + ", " + validMovesHeights[2] + ", " + validMovesHeights[3] + " (Up/Dn/Lf/Rt)");
			System.out.println("\t\tNote, value '-1' indicates an invalid move.");
			
			
			
			for(int i=0; i<validMoves.length; i++){
				
				if(validMoves[i] != -1){
					((HillStep)hf.getChildrenUnmodifiable().get(validMoves[i])).setConsidered(true);
					System.out.println("\tEvaluated position " + validMoves[i] + " with height of " + validMovesHeights[i]);
					if(validMovesHeights[i] > currentHeight){
						isGreaterOrEqualCheck[i] = true;
						lastAcceptableIndexSeen = i;
					}
					else{
						isGreaterOrEqualCheck[i] = false;
					}
					
					try{
						Thread.sleep(cbTimeDelay.getValue());
					} catch(InterruptedException e){
						System.out.println("Thread " + threadName + " interrupted");
					}
					
					((HillStep)hf.getChildrenUnmodifiable().get(validMoves[i])).setConsidered(false);
				}
				
			} // for i
			
			
			for(int i=0; i<isGreaterOrEqualCheck.length; i++){
				if(isGreaterOrEqualCheck[i] == true){
					numberOfAcceptableMoves++;
				}
			} // for i
			
			
			if(numberOfAcceptableMoves == 0){
				System.out.println("\t\tFound the high ground!");
				System.out.println("\t\tHigh ground at position " + position + " with height of " + ((HillStep)hf.getChildrenUnmodifiable().get(position)).getHeightVal());
				((HillStep)hf.getChildrenUnmodifiable().get(position)).setGoal(true);
				eventAIStop();
			}
			else{
				this.currentPos = validMoves[lastAcceptableIndexSeen];
				((HillStep)hf.getChildrenUnmodifiable().get(currentPos)).setChosen(true);
				System.out.println("Moved to position " + position + " with height value of " + ((HillStep)hf.getChildrenUnmodifiable().get(position)).getHeightVal());
			}
		} // END
		
		
	} // END
	
	
	
	
} // END