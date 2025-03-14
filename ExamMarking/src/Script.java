/*
Purpose:	 
	This class is representing a single script (a set of questions) submitted for marking.
 
Attributes:
	name: Name of the question (e.g., Q1, Q2).
	answer: The answer value (must be a combination of "A", "B", or "C").
	marked: A flag indicating if the question has been marked.
	markedBy: The name of the marker who marked it.
Methods:
	Constructors
		Question() - Default constructor initializing empty values.
		Question(String name, String answer) - Constructor initializing name and answer.
		Question(String name, String answer, boolean marked, String markedBy) - Constructor allowing full initialization.
	Setters and Getters
		setName(String value): Setting the script's name.
		getName(): Getting the script's name.
		setMarked(boolean value): Setting whether the script is marked.
		getMarked(): Getting whether the script is marked.
		setQuestions(Question[] questions): Setting the list of questions in the script.
		getQuestions(): Getting the list of questions in the script.
		setQuestion(int ind, Question question): Setting a specific question in the script.
		getQuestion(int ind): Getting a specific question from the script.
	Functional Methods
		addQuestion(int ind, Question question): Adds a Question object to a specific index within the questions array in the Script class.
		findQuestion() - Finds the first unmarked question and marks the script if all are graded.
		toString(): Converts the question object to a string representation.


*/



public class Script {
    private static final int QUESTION_COUNT = 5; // Static variable to set the amount of questions
    private Question[] questions; // Array of questions
    private boolean marked = false;
    private String name= "";
	
	//------------------------------------------------------------
    // Constructors
    public Script() {
        // WRITE THE REST OF CODE
        questions = new Question[QUESTION_COUNT];
    }

    public Script(String name){
        // WRITE THE REST OF CODE
        this.name = name;
        questions = new Question[QUESTION_COUNT];
    }
    public Script(Question[] questions) {
        // WRITE THE REST OF CODE
        this.questions = questions;
    }

	//------------------------------------------------------------
	// GETTER AND SETTER
	
    public String getName(){
      return this.name;
    }

    public void setName(String value){
        this.name = value;
    }

    public boolean getMarked ()
    {
        return this.marked;
    }

    public void setMarked(boolean value)
    {
        this.marked = value;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }
    public void setQuestion(int ind, Question question){
        this.questions[ind] = question;
    }

    public static int getQuestionCount() {
        return QUESTION_COUNT;
    }
	//------------------------------------------------------------
	// This function return index of an unmarked question in the script, if all scrips are marked, return -1. 
    public int findQuestion()
    {
        return -1;
       
    }
    public Question getQuestion(int ind) {
        return questions[ind];
    }

    public void addQuestion(int ind, Question question){
        this.questions[ind] = question;
    }
    // toString method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Script: {"+this.getName() +"} { \n \t marked {"+ this.getMarked()+"} \n \t questions=[");
        for (int i = 0; i < questions.length; i++) {
            if (questions[i] != null)
            {
                sb.append("\n \t\t"+questions[i].toString());
                if (i < questions.length - 1) {
                    sb.append(", \t ");
                }
            }
        }
        sb.append("\n\t\t]\n}");
        return sb.toString();
    }

     

}
