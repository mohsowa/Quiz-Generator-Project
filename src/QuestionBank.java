public class QuestionBank extends  Quiz{
    private int profID;
    private Question[] questions;

    public QuestionBank (){}

    public QuestionBank (int profID, Question[] questions){
        this.profID = profID;
        this.questions = questions;
    }

    public int getProfID(){
        return profID;
    }

    public Question[] getQuestions() {
        return questions;
    }
}
