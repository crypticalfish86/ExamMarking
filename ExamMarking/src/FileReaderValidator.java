
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
import java.util.Scanner;

public class FileReaderValidator {

    // Helper method to check if the values are valid (A, B, or C)
    private static boolean isValidValues(String values) {
        return values.matches("^[ABC]{3}$");
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
        try{

            Scanner scanner = new Scanner(file);

            //load the questions scanned from the file into a list
            String[] questions = new String[Script.getQuestionCount() + 1];
            int index = 0;
            while (scanner.hasNextLine()) {
                questions[index] = scanner.nextLine();
                index++;
            }

            /*turn the questions into question objects and add them into the script
            (ignoring the first index as that is "Question Values" text in the file*/
            for (int i = 1; i < questions.length; i++) {
                String[] question = questions[i].split(" ");
                String questionName = question[0];
                String questionAnswerValues = isValidValues(question[1]) ? question[1] : "AAA";
                script.addQuestion(i - 1, new Question(questionName, questionAnswerValues));
            }
            return script;

        }catch (Exception e) {

            System.err.println(e);
            return null;
        }
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
