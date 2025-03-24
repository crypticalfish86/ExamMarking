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
import java.util.Random;
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
    public ModuleCoordinator()
    {
        this.setMaximumScripts(10);
        this.setSleepTime(1);
        
        for (int i = 0; i < maximumScripts; i++) {
            scriptSemaphores[i] = new Semaphore(1, false); 
        }      
        
    }

    public ModuleCoordinator(int sleepTime)
    {
        this.setMaximumScripts(10);
        this.setSleepTime(sleepTime);
        for (int i = 0; i < maximumScripts; i++) {
            scriptSemaphores[i] = new Semaphore(1, false); 
        }      
        
    }
    
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
    
    public  void setMaximumScripts(int maximumScripts){
        ModuleCoordinator.maximumScripts = maximumScripts;
        listScripts = new Script[maximumScripts];
        scriptSemaphores = new Semaphore[maximumScripts];
    }
    public int getMaximumScripts(){
        return ModuleCoordinator.maximumScripts;
    }

    public int getSleepTime (){
        return this.sleepTime;
    }
    public void setSleepTime (int value){
        this.sleepTime = value;
    } 
  
    public int getMarkersCount() {
        return markersCount;
    }

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

        //initialise a directory file and list of filepaths that contain "scripts" in them
        File directory = new File(dirPath);
        String[] scriptFilePaths = directory.list((dir, name) -> name.contains("_scripts"));

        //check if there are "_scripts" files found in that directory
        if (scriptFilePaths == null || scriptFilePaths.length == 0) {
            System.out.println(NO_FILES_FOUND_MSG);
            return;
        }


        //trim the list so that it doesn't go over the maximum
        String[] trimmedScriptFilePaths;
        if (scriptFilePaths.length > maximumScripts) {
            trimmedScriptFilePaths = new String[maximumScripts];
            System.arraycopy(scriptFilePaths, 0, trimmedScriptFilePaths, 0, maximumScripts);
        }
        else {
            trimmedScriptFilePaths = scriptFilePaths;
        }


        //create an initial list of script objects (with null values)
        Script[] initialScriptList = new Script[trimmedScriptFilePaths.length];
        for (int i = 0; i < initialScriptList.length; i++) {
            initialScriptList[i] = FileReaderValidator.readAndValidateFile(dirPath + File.separator + trimmedScriptFilePaths[i]);
        }


        //remove null values from the script array and assign the script objects to "listScripts" attribute
        int nonNullCount = 0;
        for (int i = 0; i < initialScriptList.length; i++) {
            if (initialScriptList[i] != null) {
                nonNullCount++;
            }
        }
        listScripts = new Script[nonNullCount];
        int index = 0;
        for (Script script : initialScriptList) {
            if (script != null) {
                listScripts[index] = script;
                index++;
            }
        }
        System.out.println(SCRIPTS_LOADED_MSG);
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

        //instantiate marker threads
        Thread[] markerThreads = new Thread[markersCount];
        for (int i = 0; i < markerThreads.length; i++) {
            markerThreads[i] = new Thread(new Marker(this, maximumScripts, sleepTime));
        }


        //start marking
        for (int i = 0; i < markerThreads.length; i++) {
            markerThreads[i].start();
        }


        //join all threads with this thread
        try{
            for (int i = 0; i < markerThreads.length; i++) {
                markerThreads[i].join();
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
 


}