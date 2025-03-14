/*
<You do not need to change this class.>	 
Purpose:	 
	This class acts as the entry point for the program, managing the coordination of script marking.
	
	main(String[] args)
		Creates a ModuleCoordinator object.
		Loads scripts from the "./script" directory.
		Starts the marking process using multiple threads.
		Saves the final marking state to "Teacher_Out81.txt".
--------------------------------------
Overall functionality of the project is as follow:
	Loading Scripts:
		Main.java starts execution.
		ModuleCoordinator loads scripts from a folder using FileReaderValidator.
	Marking Scripts:
		ModuleCoordinator initializes multiple Marker threads.
		Each Marker selects scripts randomly and marks questions.
		Marker threads use semaphores to ensure scripts are marked without conflict.
	Saving Results:
		After marking, results are saved in a file (toFile method in ModuleCoordinator and FileReaderValidator).		
		
		
Components
	ModuleCoordinator.java - Manages script loading, marking coordination, and thread execution.
	Marker.java - Represents individual markers that process scripts in parallel.
	Script.java - Stores details of a single script and its associated questions.
	Question.java - Represents an individual question within a script.
	FileReaderValidator.java - Reads and validates script files, ensuring data integrity.
	Main.java - The entry point of the program that initializes and runs the system.
	
Funcionality
	Script Loading - ModuleCoordinator loads script files using FileReaderValidator.
	Thread Initialization - Multiple Marker threads are created for grading.
	Script Selection & Marking - Each marker picks a script, marks ungraded questions, and records results.
	Synchronization - Semaphores prevent multiple markers from grading the same question simultaneously.
	Saving Results - The final grading results are saved in a file for review.
	
*/
public class Main {

    public static void main(String[] args) throws InterruptedException {
      // Ask manager to start
      
      ModuleCoordinator coordinator = new ModuleCoordinator(1,83);  //sleepTime and Scripts count
      coordinator.loadAllScripts("./script"); // Load all scripts from the directory
      coordinator.startMarking(); // Start the game
    }
}
