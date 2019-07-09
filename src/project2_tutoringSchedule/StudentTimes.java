package project2_tutoringSchedule;
import java.util.*;

public class StudentTimes {
	//Store times for each weekday
	//keep track of where the values will go in the 2D array 
	int timeIndex;
	int dayIndex;
	
	//ARRAYLISTS translates to 2D array position through indexing ie. 1st index = "M" also the first index of the 2D row
	ArrayList<String> days = new ArrayList<String>(Arrays.asList("X", "M", "T", "W", "H", "F"));
	ArrayList<String> times = new ArrayList<String>(Arrays.asList("XXXX", "0900", "1000", "1100","1200","1300","1400","1500","1600","1700","1800","1900", "2000", "2100" ));
	
	//name 
	String name = "";
	
	
	
	
	//set the name of student 
	public StudentTimes() {
	
	}

	//get the name of student 
	public void setName(String name) {
		this.name = name;
	}
	
	
	//get the name of student 
	public String getName() {
		return name;
	}
	
	
	
		//information sensitive will store weekday letters or time string (index of the arraylist above) 
		public void dayTime(String info) {
			if (days.contains(info)) {
				String day = info;
				dayIndex = days.indexOf(day);
				
			}
			if (times.contains(info)) {
				String time = info;
				timeIndex = times.indexOf(time);
				
			}
		}
		
		//getters
		public int getTime() {
			return timeIndex;
		}
		
		public int getDay() {
			return dayIndex;
		}
		
		
		
		
		

}
