/*
	<You do not need to change this class.>
Purpose:
	This class is representing a single question in a script, storing its name, answer, marking status, and the marker who graded it.
 
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
		setName(String name): Setting the question name.
		getName(): Getting the question name.
		setAnswer(String answer): Setting the answer for the question.
		getAnswer(): Getting the answer of the question.
		setMarked(boolean marked): Setting question as marked/unmarked.
		getMarked(): Getting whether the question is marked.
		setMarkedBy(String markedBy): Setting the marker's name.
		getMarkedBy(): Getting the marker's name.
	Functional Methods
		toString(): Converts the question object to a string representation.
*/

public class Question {
    private String name; // The name of the question (e.g., Q1, Q2, etc.)
    private String answer; // Section 1 value (AAA, to CCC)
    private boolean marked;
    private String markedBy;

    // Default Constructor
    public Question() {
        this.name = "";
        this.answer = "";
        this.marked = false;
        this.markedBy = "";
    }

    // Constructor with name and answer
    public Question(String name, String answer) {
        this.name = name;
        setAnswer(answer);
        this.marked = false;
        this.markedBy = "";
    }

    // Constructor with all fields
    public Question(String name, String answer, boolean marked, String markedBy) {
        this.name = name;
        setAnswer(answer);
        this.marked = marked;
        this.markedBy = markedBy;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if (answer != null && answer.matches("[ABC]{3}")) {
            this.answer = answer;
        } else {
            throw new IllegalArgumentException("Answer must be exactly three characters, each being A, B, or C.");
        }
    }

    public boolean getMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public String getMarkedBy() {
        return markedBy;
    }

    /*Mark a question (to be called by a Marker object when marking)*/
    public void setMarkedBy(String markedBy) {
        this.marked = true;
        this.markedBy = markedBy;
    }
 
    // toString method
    @Override
    public String toString() {
        return "Question{" +
                "name='" + this.getName() + '\'' +
                ", answer='" + this.getAnswer() + '\'' +
                ", marked=" + this.getMarked() +
                ", marked By='" + this.getMarkedBy() + '\'' +
                '}';
    }

    
}
