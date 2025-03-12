/*
Purpose:
	This class handles the marking process for scripts, running as a separate thread.
	Note: Multiple Marker threads might be created for grading.

Attributes:
	markerName: Name of the marker.
	sleepTime: The delay between marking actions.
	remainingScripts: List of scripts yet to be marked.
	maximumScripts: The total number of scripts.
Methods:
	Constructors
		Marker() - Default constructor.
		Marker(ModuleCoordinator moduleCoordinator) - Assigns a coordinator.
		Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime) - Allows setting max scripts and sleep time.
		Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime, String markerName) - Assigns all attributes including the marker's name.
	Setters and Getters
		setMarkerName(String markerName): Setting the name of the marker.
		getMarkerName(): Getting the name of the marker.
		setSleepTime(int value): Setting the sleep time for marking.
		getSleepTime(): Getting the sleep time for marking.
		setMaximumScripts(int value): Setting the max scripts a marker can handle.
		getMaximumScripts(): Getting the max scripts a marker can handle.
	Functional Methods
		removeScript(int ind) - Removes a script from the list of remaining scripts.
		selectScript() - Randomly selects an unmarked script.
		lockPendingTransaction(int ind), unlockPendingTransaction(int ind) - Uses semaphores to manage access to scripts.
		markTheScript(int ind) - Marks the selected question in a script.
		run() - The main method executed when a Marker thread starts.
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Marker implements Runnable {

    private ModuleCoordinator moduleCoordinator;
	private int maximumScripts;
	private int sleepTime;
    private String markerName = "Hassan";
    
    private List<Integer> remainingScripts = new ArrayList<>();
    
//------------------------------------------------------------

    // Constructors
	public Marker(){
	   // WRITE THE REST OF CODE
	}
    public Marker(ModuleCoordinator moduleCoordinator) {
	   // WRITE THE REST OF CODE
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime) {
	   // WRITE THE REST OF CODE
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime, String markerName) {
	   // WRITE THE REST OF CODE
    }
	
//------------------------------------------------------------
    // GETTER AND SETTER 
 
 	// WRITE THE CODE HERE

//------------------------------------------------------------
    // Remove a script from the remaining scripts
    public void removeScript(int ind) {
	   // WRITE THE REST OF CODE
    }

    // Select a script randomly from remaining scripts
    public int selectScript() {
	   // WRITE THE REST OF CODE
        Random random = new Random();
        return random.nextInt(0, remainingScripts.size());
    }

    

    // Mark the script at a given index
    public void markTheScript(int ind) throws InterruptedException {
	   // WRITE THE REST OF CODE
    }

     public void run() {

     }
}
