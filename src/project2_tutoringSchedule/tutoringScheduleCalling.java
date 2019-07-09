package project2_tutoringSchedule;
import java.util.*;
import java.io.*;


public class tutoringScheduleCalling {

	public static void main(String[] args) {
		//Arraylists to keep track of validity and help print info 
		ArrayList<String> days = new ArrayList<String>(Arrays.asList("X", "M", "T", "W", "H", "F"));
		ArrayList<String> weekdays = new ArrayList<String>(Arrays.asList("Invalid", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
		ArrayList<String> times = new ArrayList<String>(Arrays.asList("XXXX", "0900", "1000", "1100","1200","1300","1400","1500","1600","1700","1800","1900", "2000", "2100" ));
		ArrayList<String> standardTimes = new ArrayList<String>(Arrays.asList("XXXX", "9 am", "10 am", "11 am","12 pm","1 pm","2 pm","3 pm","4 pm", "5 pm","6 pm","7 pm", "8 pm", "9 pm" ));
		
		//CHART creating the 2D array  
		int rows = times.size();
		int columns = days.size();
		String[][] chart = new String[rows][columns];
		for (int f=0;f<rows;f++) {
			for(int l =0; l<columns;l++) {
				chart[f][l]="|";
			}
		}
		//label
		chart[0][0] = "D/T";
		
		//iterate - print out weekdays  columns
		for (int x=1; x<weekdays.size(); x++) {
				chart[0][x] = "|"+weekdays.get(x)+"                             ";
			}
		//iterate - print out time rows 
		for (int y=1; y<times.size(); y++) {
			chart[y][0] = "{"+times.get(y)+ "}   ";
		}
		
		
		//initiate scanner to ask what files to analyze
		Scanner reader = new Scanner(System.in); 
		System.out.println("Which tutoring preeference dataset would you like to run (1-8)? ");
		int input = reader.nextInt(); 
		//check for validity (if file is there 
		while (input<=0 || input>8) {
			System.out.println("Invalid Dataset Number");
			System.out.println("Which test dataset would you like to run (1-8)? ");
			input = reader.nextInt(); 
		}
		
		reader.close();
		//store file paths in arraylist to access 
		ArrayList<String> paths = new ArrayList<String>(Arrays.asList("proj3_set1.txt","proj3_set2.txt", 
				"proj3_set3.txt", "proj3_set4.txt", "proj3_set5.txt", 
				"proj3_set6.txt", "proj3_set7.txt", "proj3_set8.txt"));
		
		//file object to open up file 
		File relfile = new File(paths.get(input-1));
		//finding and storing absolute path 
		String abspath = relfile.getAbsolutePath();
		//to store raw data 
		ArrayList<String> fileData	= new ArrayList<String>();
		//to store corrected data 
		ArrayList<String> correctData = new ArrayList<String>();
		
		//try-except to open up file 
		try {
			Scanner fileReader = new Scanner (new File(abspath));
			
			//split records by ) 
			fileReader.useDelimiter("\\)");
			//read and add back parenthesis 
			while(fileReader.hasNext()) {
				fileData.add(fileReader.next()+ ")");
			}
			
			fileReader.close();	
		}
		//catch file not found error  
		catch(FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
		//instantiate MatchDelimiters class to find delimiter errors 
		MatchDelimiters matchCheck = new MatchDelimiters();
		for (int i=0; i<fileData.size(); i++) {
			
			if (matchCheck.isMatched(fileData.get(i)) == true) {
				//store if valid 
				String workingData = fileData.get(i);
				
				correctData.add(workingData);
			}
		
		}
		
		
		//SPLITUP DATA
		//iterate through records 
		for (int s1=0; s1<correctData.size(); s1++) {
			//System.out.println();
			String correctDataIn = correctData.get(s1);
			//Change the delimiter after each time so i can separate timings with the same day 
			correctDataIn = correctData.get(s1).replaceAll("]", "-");
			
			//separate by delimiters to keep the data (analyze)
			String [] sepData = correctDataIn.split("\\(|\\)|\\<|\\>|\\[|\\-");
			//instantiate studentTimes object to store individual info
			StudentTimes currentSt = new StudentTimes();
			//iterate through separated data 
			for(int e=0; e<sepData.length; e++) {
				//System.out.println(sepData[e]);
				
					//first index is the name of the student 
					String name = sepData[1];
					//setter method 
					currentSt.setName(name);;
					
					//use the arraylists(time/day) to check id the information neeeds to bee processed  
					if (days.contains(sepData[e]) || times.contains(sepData[e])) {
						//set the day and time (no need for separation -- the method distinguishes between the 2 types 
						currentSt.dayTime(sepData[e]);
						//if not the initial values 
						if(currentSt.getTime()!=0 && currentSt.getDay()!=0) {
							//store info in respected position of the 2D array 
							chart[currentSt.getTime()][currentSt.getDay()] += "*";
							chart[currentSt.getTime()][currentSt.getDay()] += currentSt.getName();
							chart[currentSt.getTime()][currentSt.getDay()] += "";	
							
						}
					}		
			}	
		}
		
		
		
		//format the schedule
		for(int i = 0; i<rows; i++) {
		    for(int j = 0; j<columns; j++) {
		    	if (chart[i][j].length()!=55) {
		    		int spaces = 55-chart[i][j].length();
		    		String space = " ";
		    		for (int k=0; k<spaces; k++) {
		    			chart[i][j] += space;
		    		}
		    	}
		    }
		}
		
		//print out the schedule
		System.out.println("This is the schedule:");
		for(int i = 0; i<rows; i++) {
		    for(int j = 0; j<columns; j++) {
		        System.out.print(chart[i][j]+"      ");
		    }
		    System.out.println();
		}
		
		
		//Finding the best time slot
		int pplCount;
		int max=0;
		int maxRow = 0;
		int maxCol = 0;
		for(int m = 0; m<rows; m++) {
		    for(int n = 0; n<columns; n++) {
		    	String[] amount = chart[m][n].split("\\*");
		    	pplCount = amount.length; 
		    			if (pplCount > max) {
			    			max = pplCount; 
			    			maxRow = m;
			    			maxCol = n;
			    		}
		    			//IF THE SAME CHECK TO SEE EARLIEST TIME
			    		if (pplCount == max) {
			    			
			    			if (m < maxRow) {
			    				maxRow = m;
			    				maxCol = n;
			    			}
			    		
			    			else if (n < maxCol) {
			    				maxRow = m;
			    				maxCol = n;
			    			}
			    		}else {
			    			continue;
			    		}
			    	}
		    		
	}
	
	System.out.println();
	System.out.println("The Best Time Slot is: ");
	System.out.println(weekdays.get(maxCol)+ "s at " + standardTimes.get(maxRow));
	
		
		
		
		
		
		

	}

}
