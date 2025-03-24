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
    private String markerName = ""; //had to remove "hassan" as it failed autograder tests which required empty strings
    
    private List<Integer> remainingScripts = new ArrayList<>();
    
//------------------------------------------------------------

    // Constructors

    //Empty constructor for if you want to set values of stuff later
	public Marker(){

	}
    public Marker(ModuleCoordinator moduleCoordinator) {
        this.moduleCoordinator = moduleCoordinator;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime) {
        this.moduleCoordinator = moduleCoordinator;
        this.maximumScripts = maximumScripts;
        this.sleepTime = sleepTime;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime, String markerName) {
        this.moduleCoordinator = moduleCoordinator;
        this.maximumScripts = maximumScripts;
        this.sleepTime = sleepTime;
        this.markerName = markerName;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }
	
//------------------------------------------------------------
    // GETTER AND SETTER

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public int getMaximumScripts() {
        return maximumScripts;
    }

    public void setMaximumScripts(int maximumScripts) {
        this.maximumScripts = maximumScripts;
    }

    public ModuleCoordinator getModuleCoordinator() {
        return moduleCoordinator;
    }

    public void setModuleCoordinator(ModuleCoordinator moduleCoordinator) {
        this.moduleCoordinator = moduleCoordinator;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }


//------------------------------------------------------------
    

    // Mark the script at a given index
    public void markTheScript(int ind) throws InterruptedException {

        moduleCoordinator.scriptSemaphores[ind].acquire();
        Script script = moduleCoordinator.listScripts[ind];
        if (!script.getMarked()) {
            int questionIndex = script.findQuestion();
            Question question = script.getQuestion(questionIndex);
            question.setMarkedBy(this.markerName);
            script.checkAndUpdateAllQuestionsMarked();
        }
        moduleCoordinator.scriptSemaphores[ind].release();
    }

    //Runs through the remaining scripts marking one question from each until none are left
     public void run() {
        Random random = new Random();
        while (!remainingScripts.isEmpty()){
            int randomIndex = random.nextInt(remainingScripts.size());
            int scriptIndex = remainingScripts.remove(randomIndex);

            try{
                markTheScript(scriptIndex);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e);
            }
        }

     }

}
