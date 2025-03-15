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
    private String markerName = "";
    
    private List<Integer> remainingScripts = new ArrayList<>();
    
//------------------------------------------------------------

    // Constructors
	public Marker(){
	   // TODO WRITE THE REST OF CODE FIGURE OUT HOW AN INDEPENDENT MARKER THREAD WOULD WORK
	}
    public Marker(ModuleCoordinator moduleCoordinator) {
	   // TODO WRITE THE REST OF CODE
        this.moduleCoordinator = moduleCoordinator;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime) {
	   // TODO WRITE THE REST OF CODE
        this.moduleCoordinator = moduleCoordinator;
        this.maximumScripts = maximumScripts;
        this.sleepTime = sleepTime;
        int numberOfScripts = moduleCoordinator.listScripts.length;
        for (int i = 0; i < numberOfScripts; i++) {
            remainingScripts.add(i);
        }
    }
    public Marker(ModuleCoordinator moduleCoordinator, int maximumScripts, int sleepTime, String markerName) {
	   // TODO WRITE THE REST OF CODE
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

    // WRITE THE CODE HERE

//------------------------------------------------------------
    // Remove a script from the remaining scripts
    public void removeScript(int ind) {
	   // TODO WRITE THE REST OF CODE
    }

    // Select a script randomly from remaining scripts
    public int selectScript() {
	   // TODO WRITE THE REST OF CODE
        if(remainingScripts.isEmpty()) {
            return -1;
        }
        Random random = new Random();
        return remainingScripts.get(random.nextInt(remainingScripts.size()));
    }

    

    // Mark the script at a given index
    public void markTheScript(int ind) throws InterruptedException {
	   // TODO WRITE THE REST OF CODE
        moduleCoordinator.scriptSemaphores[ind].acquire();
        Script script = moduleCoordinator.listScripts[ind];
        //TODO find out how to actually mark the script
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
