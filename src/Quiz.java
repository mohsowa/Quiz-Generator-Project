import java.io.Serializable;
import java.util.ArrayList;
public class Quiz implements Serializable {
    private String quizName;
    private int quizID;
    private String quizType;
    private ArrayList<Question> questionList;
    public ArrayList<Student> studentList = new ArrayList<>();
    private String instructorUsername;


    public Quiz(){}

    public Quiz(String quizName,int quizID,String quizType,ArrayList<Question> questionList,String instructorUsername){
        this.quizName = quizName;
        this.quizID = quizID;
        this.quizType = quizType;
        this.questionList = questionList;
        this.instructorUsername = instructorUsername;
    }



    public String getQuizName() {
        return quizName;
    }

    public int getQuizID() {
        return quizID;
    }

    public String getQuizType(){
        return quizType;
    }


    public String getInstructorUsername() {
        return instructorUsername;
    }
    public ArrayList<Student> getstudentList() {
        return studentList;
    }

    public void setQuizType(String type){
         quizType = type;
    }


    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public String toString(){
        return "Quiz name: " + quizName + "Quiz ID: " + quizID + "Class ID: " +  "Quiz Type: " + quizType +
                "Time begin: " + "Duration: " ;

    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public boolean isStudentHasAQuiz(Student student){
        for (int i = 0 ; i < studentList.size(); i++){
            if(studentList.get(i).getUsername().equals(student.getUsername())){
                return true;
            }
        }
        return false;
    }



}
