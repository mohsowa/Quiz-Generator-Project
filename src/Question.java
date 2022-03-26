import java.util.ArrayList;

public class Question extends QuestionBank{
    private String question;
    private String questionChapter;
    private String questionDifficulty;
    private ArrayList<String> options;
    private String answer;

    public Question(){}

    public Question(String question,String questionChapter,String questionDifficulty,ArrayList<String> options,String answer){
        this.question = question;
        this.questionChapter = questionChapter;
        this.questionDifficulty = questionDifficulty;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }

    public String getQuestionChapter() {
        return questionChapter;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public String getOptions(int index) {
        return options.get(index);
    }

    public int countOption(){
        return options.size();
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public void setQuizType(String type) {
        super.setQuizType(type);
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void setQuestionChapter(String questionChapter) {
        this.questionChapter = questionChapter;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }


    public String getCorrectAnswer() {
            if(answer.equals("OPTION 1")){
                return options.get(0);
            }else if(answer.equals("OPTION 2")){
                return options.get(1);
            }else if(answer.equals("OPTION 3")){
                return options.get(2);
            }else if(answer.equals("OPTION 4")){
                return options.get(3);
            }else return null;
    }

    public String getOption_o(String option) {
        System.out.println("here");
        if(option.equals("OPTION 1")){
            return options.get(0);
        }else if(option.equals("OPTION 2")){
            return options.get(1);
        }else if(option.equals("OPTION 3")){
            return options.get(2);
        }else if(option.equals("OPTION 4")){
            return options.get(3);
        }else return "null";
    }
}
