
/*
Purpose:	 
	This class reads and validates script files, ensuring the correct format and values.
	The validation of script files is done by FileReaderValidator.java before they are stored for marking.
Attributes:
	name: Name of the question (e.g., Q1, Q2).
	answer: The answer value (must be a combination of "A", "B", or "C").
	marked: A flag indicating if the question has been marked.
	markedBy: The name of the marker who marked it.
Methods:
	Functional Methods
		isValidValues(String values) : Ensures that answers consist only of "A", "B", or "C".
		readAndValidateFile(String filePath): Reads a script file, validates its format and contents, and creates a Script object. If a script contains invalid values, it sets them to "AAA".
		toFile(String input, String output): Reads a script file and writes the validated script information to a new output file.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderValidator {

    // Helper method to check if the values are valid (A, B, or C)
    private static boolean isValidValues(String values) {
         
    }
	
	/*
    read all questions in the a given script
	
	*/
    public static Script readAndValidateFile(String filePath) {
        
        File file = new File(filePath);
        //System.out.println(file.getName());
        if (!file.exists() || !file.canRead()) {
            System.out.println("The file does not exist or cannot be read: " + filePath);
            return null;
        }
 

        Script script = new Script(file.getName()); // Initialize the Script object
       
	   // WRITE THE REST OF CODE
    }


    public static void toFile(String input, String output)
    {
        String data  = readAndValidateFile(input).toString();

        try (FileWriter writer = new FileWriter(output)) {
            writer.write(data);
            System.out.println("Data successfully written to file: " + output);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
