package HillClimberPkg;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HillFieldUtils {

	
	public static int[] getValidMoves(HillField hf, int position){
		
		//System.out.println("[HillFieldUtils][GetValidMoves][Position: " + position + "]");
		
		int[] validMoves = {-1, -1, -1, -1};
		
		int hillSteps = (int) hf.getSizeHillSteps();
		
		int upIndex = position - hillSteps;
		int downIndex = position + hillSteps;
		int leftIndex = position - 1;
		int rightIndex = position + 1;
		
		if(upIndex >= 0){
			validMoves[0] = upIndex;
		}
		
		if(downIndex < (hf.getSizeHillSteps() * hf.getSizeHillSteps())){
			validMoves[1] = downIndex;
		}

		if((leftIndex / hillSteps) == (position / hillSteps)){
			validMoves[2] = leftIndex;
		}
		
		if((rightIndex / hillSteps) == (position / hillSteps)){
			validMoves[3] = rightIndex;
		}
		
		return validMoves;
	} // END
	
	
	
	public static class generation{
		
		public static class heights{
			
			public static void random(HillField hf){
				//System.out.println("[HillFieldUtils][Generation][Heights][Random][Height Limit: " + hf.getHeightLimit() + "]");
				
				Random r = new Random();
				for(int i=0; i<(hf.getChildrenUnmodifiable().size()); i++){
					int randHeight = r.nextInt(hf.getHeightLimit()+1);
					( (HillStep) hf.getChildren().get(i)).setHeightVal(randHeight);
				} // For i
			} // END
			
			public static void manual(HillField hf){
				// TODO Implement alternative manual field generation.
				//System.out.println("[HillFieldUtils][Generation][Heights][Manual]");
				//System.out.println("Feature not yet implemented");
			} // END
			
			
			public static void gradient(HillField hf, gradientDirection gdir){
				
				int[] heightProgression = new int[hf.getHeightLimit()];
				int difference = hf.getSizeHillSteps() - heightProgression.length;
				int length = heightProgression.length;
				int maxRows = (hf.getChildrenUnmodifiable().size() - 1) / hf.getSizeHillSteps();
				
				for(int i=0; i<length; i++){
					heightProgression[i] = i;
				}
				
				
				switch(gdir){
				
					case up:
						for(int i=0; i<hf.getChildrenUnmodifiable().size(); i++){
							
							int row = i / hf.getSizeHillSteps();
							
							if(row < difference){
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[length - 1]);
							}
							else{
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[maxRows - row]);
							}
							/*
							 * 0		5		if maxRows=20 and difference=15, maxRows-difference=5
							 * 1		5
							 * 2		5
							 * 3		5
							 * 4		5
							 * 5		5
							 * 6		5
							 * 7		5
							 * 8		5
							 * 9		5 
							 * 10		5
							 * 11		5
							 * 12		5
							 * 13		5		5 because row-difference < difference
							 * 14		5		5 = 19 - 14
							 * 15		4		4 = 19 - 15
							 * 16		3		3 = 19 - 16
							 * 17		2		2 = 19 - 17
							 * 18		1		1 = 19 - 18
							 * 19		0		0 = 19 - 19
							 * */
						} // END
						break;
						
					case down:
						for(int i=0; i<hf.getChildrenUnmodifiable().size(); i++){
							
							int row = i / hf.getSizeHillSteps();
							
							if(row > length){
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[length - 1]);
							}
							else{
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[row]);
							}
								/*
								 * 0		0
								 * 1		1
								 * 2		2
								 * 3		3
								 * 4		4		<-	heightProgression[index]
								 * 5		5			index = row
								 * 6		6
								 * 7		7
								 * 8		8
								 * 9		9		<- row >= length
								 * 10		9
								 * 1		9
								 * 12		9
								 * 13		9
								 * 14		9
								 * 15		9
								 * 16		9
								 * 17		9
								 * 18		9
								 * 19		9
								 * */
						} // END
						break;
						
					case left:
						for(int i=0; i<hf.getChildrenUnmodifiable().size(); i++){
							
							int col = i % hf.getSizeHillSteps();
							
							if(col < difference){
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[length - 1]);
							}
							else{
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[maxRows - col]);
							}
							
						} // for i
						break;
						
					case right:
						for(int i=0; i<hf.getChildrenUnmodifiable().size(); i++){
							
							int col = i % hf.getSizeHillSteps();
							
							if(col > length){
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[length - 1]);
							}
							else{
								( (HillStep) hf.getChildren().get(i)).setHeightVal(heightProgression[col]);
							}
						} // for i
						break;
				} // switch(gdir)
				
			} // END
			
			
			
		} // END
		
		
		public static class colors{
			
			public static void random(HillField hf){
				//System.out.println("[HillFieldUtils][Generation][Colors][Random]");
				Paint color = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
				hf.setColorPrimary(color);
			} // END
		} // END
		
		
	} // END
	
	
	public static class print{
		/**
		 * Prints the Integer array containing the weight values for each HillStep to console.
		 * */
		public static void heights(HillField hf){
			
			System.out.println("[HillFieldUtils][Print][Heights]");
			System.out.println("\tHeight map");
			
			System.out.print("     ");
			for(int col=0; col<hf.getSizeHillSteps(); col++){
				if(col/10 == 0){
					System.out.print(col + "  ");
				}
				else{
					System.out.print(col + " ");
				}
			} // for col
			
			System.out.print("\n     ");
			for(int col=0; col<hf.getSizeHillSteps(); col++){
				System.out.print("|" + "  ");
			} // for col
			System.out.print("\n" + " " + 0 + " - ");
			
			
			int currentRow = 0;
			int previousRow = 0;
			for(int i=0; i<hf.getChildrenUnmodifiable().size(); i++){
				
				currentRow = i / hf.getSizeHillSteps();
				
				if(currentRow != previousRow){
					previousRow = currentRow;
					
					if(currentRow/10 == 0){
						System.out.print("\n" + " " + currentRow + " - ");
					}
					else{
						System.out.print("\n" + currentRow + " - ");
					}
					
					
				}
				
				int heightVal = ((HillStep) hf.getChildren().get(i)).getHeightVal();
				
				if(heightVal/10 == 0){
					System.out.print(heightVal + "  ");
				}
				else{
					System.out.print(heightVal + " ");
				}
				
			} // for i
			System.out.println();
		} // END
		
		
		
		/**
		 * Prints the Paint array of color values used for each HillStep to console.
		 * */
		public static void colorProgression(HillField hf){
			System.out.println("[HillFieldUtils][Print][ColorProgArray]");
			
			String padding = "  ";
			System.out.println("\tColor Progression Array");
			for(int i=0; i<hf.getColorProgArray().size(); i++){
				if(i < 10){
					padding = "  ";
				}
				else{
					padding = " ";
				}
				
				if(i == (hf.getColorProgArray().size() - 1)){
					System.out.println("\t" + padding + i + " -> " + hf.getColorProgArray().get(i).toString());
				}
				else{
					System.out.println("\t" + padding + i + " -> " + hf.getColorProgArray().get(i).toString());
				}
			} // for i
			
			System.out.println("\n");
			
		} // END
	} // END
	
	
	public enum gradientDirection{
		up,
		down,
		left,
		right
	} // END
	
	
} // END
