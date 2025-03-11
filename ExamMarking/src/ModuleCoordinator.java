/*
Purpose:
This class is responsible for managing and coordinating the marking of scripts. It loads scripts, tracks their status, and initiates the marking process using multiple markers (threads).

Attributes:
	maximumScripts: Maximum number of scripts that can be processed.
	sleepTime: Time delay between marking actions.
	markersCount: Number of markers available for marking.
	listScripts: An array to store Script objects.
	scriptSemaphores: Semaphores to synchronize marking.
Methods:
	Constructors
		ModuleCoordinator() - Default constructor initializing 10 scripts.
		ModuleCoordinator(int sleepTime) - Constructor allowing a custom sleep time.
		ModuleCoordinator(int sleepTime, int maximumScripts) - Allows setting both sleep time and the max number of scripts.
	Setters and Getters
		setMaximumScripts(int maximumScripts): Setting max scripts in the coordinator.
		getMaximumScripts(): Getting max scripts in the coordinator.
		setSleepTime(int value): Setting sleep time for markers.
		getSleepTime(): Getting sleep time for markers.
		setMarkersCount(int markersCount): Setting the number of markers.
		getMarkersCount(): Getting the number of markers.
		setListScripts(Script[] listScripts): Setting the list of scripts.
		getListScripts(): Getting the list of scripts.
	Functional Methods
		toString() - Converts object state to a string for display.
		toFile(String filePath) - Saves the current state to a file.
		loadAllScripts(String dirPath) - Loads all scripts from a directory and validates them using FileReaderValidator.
		checkScriptsAreMarked() - Checks if all scripts are marked and prints missing scripts.
		startMarking() - Creates Marker objects and starts the marking process in multiple threads.
 */
import java.io.File;
import java.util.concurrent.Semaphore;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

class ModuleCoordinator {
    private static final String NO_FILES_FOUND_MSG = "No files found in directory: ";
    private static final String LOAD_SCRIPT_FAILED_MSG = "Failed to load script from file: ";
    private static final String SOME_SCRIPTS_MISSSING_MSG = " some scripts are not marked!!! \n";
    private static final String SCRIPTS_LOADED_MSG = " scripts loaded successfully";

    
    private int sleepTime = 1;
    static  int maximumScripts = 10;
    private int markersCount = 5; 
    
    public Script[] listScripts ;
    public Semaphore[] scriptSemaphores; // Ensures scripts are marked without conflicts.
     
	//------------------------------------------------------------
    // Constructor
    /**
     * Default constructor with default values for sleep time and maxScripts(not used in the coursework)
     * */
    public ModuleCoordinator()
    {
        this.setMaximumScripts(10);
        this.setSleepTime(1);
        
        for (int i = 0; i < maximumScripts; i++) {
            scriptSemaphores[i] = new Semaphore(1, false); 
        }      
        
    }

    /**
     * Default constructor with default values for  maxScripts(not used in the coursework)
     * */
    public ModuleCoordinator(int sleepTime)
    {
        this.setMaximumScripts(10);
        this.setSleepTime(sleepTime);
        for (int i = 0; i < maximumScripts; i++) {
            scriptSemaphores[i] = new Semaphore(1, false); 
        }      
        
    }

    /**
     * Constructor that initialises a module coordinator (for use in the coursework)
     * */
    public ModuleCoordinator(int sleepTime , int maximumScripts)
    {
        this.setSleepTime(sleepTime);
        this.setMaximumScripts(maximumScripts);
        listScripts = new Script[maximumScripts];
        scriptSemaphores = new Semaphore[maximumScripts];
        for (int i = 0; i < maximumScripts; i++) {
            scriptSemaphores[i] = new Semaphore(1, false); 
        }      
        
        
    }
	
	//------------------------------------------------------------
    // GETTER AND SETTER 

    /**
     * Sets the maximum scripts
     * @param maximumScripts
     * The total script count to be marked
     */
    public  void setMaximumScripts(int maximumScripts){
        ModuleCoordinator.maximumScripts = maximumScripts;
        listScripts = new Script[maximumScripts];
        scriptSemaphores = new Semaphore[maximumScripts];
    }

    /**
     * gets the maximum scripts
     * @return
     * The total script count to be marked
     */
    public int getMaximumScripts(){
        return ModuleCoordinator.maximumScripts;
    }

    /**
     * gets the sleep time
     * @return
     * The sleep time in milliseconds
     */
    public int getSleepTime (){
        return this.sleepTime;
    }

    /**
     * sets the sleep time
     * @param value
     * The sleep time in milliseconds
     */
    public void setSleepTime (int value){
        this.sleepTime = value;
    }

    /**
     * gets the total number of markers (threads marking scripts)
     * @return
     * The total number of markers (threads marking scripts)
     */
    public int getMarkersCount() {
        return markersCount;
    }

    /**
     * sets the total number of markers (threads marking scripts)
     * @param markersCount
     * The total number of markers (threads marking scripts)
     */
    public void setMarkersCount(int markersCount) {
        this.markersCount = markersCount;
    }

    public Script[] getListScripts() {
        return listScripts;
    }
    public void setListScripts(Script[] listScripts) {
        this.listScripts = listScripts;
    }
	//------------------------------------------------------------
	
    // toString method
    @Override
    public String toString() {
        this.checkScriptsAreMarked();
        StringBuilder sb = new StringBuilder("");
        if (listScripts != null)
            for (Script script : listScripts) {
                if (script != null) {
                    sb.append(script.toString()).append("\n");
                }
            }
        return sb.toString();
         
    }


    public void toFile(String filePath ) {
        String data  = this.toString();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
            System.out.println("Data successfully written to file: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
         
    }   
   
	//------------------------------------------------------------

    
    /* 
    Method to load all scripts from a directory
    The method loops through all files and selects those that contain "_scripts" in their name.
	Ensures proper format and values!
	It ensures that the number of scripts loaded does not exceed maximumScripts.
    Use FileReaderValidator class to load a script please
    */
    public void loadAllScripts(String dirPath) {
       // WRITE THE REST OF CODE
        System.out.println(ind + SCRIPTS_LOADED_MSG);
    }


    private void checkScriptsAreMarked(){
        for (int i = 0; i < maximumScripts; i ++)
            {
                if (listScripts[i]!= null) {
                    int j = listScripts[i].findQuestion();
                    if (j == -1)
                        listScripts[i].setMarked(true);
                    else    
                        {
                            System.out.println(SOME_SCRIPTS_MISSSING_MSG);
                            return;
                        }
                }
            }
    }

    // Method to start marking a specific script
    public void startMarking() {

         // WRITE THE REST OF CODE

    }
 


}