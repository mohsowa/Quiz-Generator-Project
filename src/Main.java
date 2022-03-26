import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    // log in info
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Quiz> quizzes = new ArrayList<>();

    User current_user = null;
    Instructor current_instructor = null;
    Student current_student = null;

    // Student info
    ArrayList<Quiz> StudentActiveQuizzes = new ArrayList<>();
    ArrayList<Quiz> InstructorActiveQuizzes = new ArrayList<>();

    // instructor info
    static Quiz newActiveQuiz = null;
    static Quiz EditQuiz = null;
    static ArrayList<Integer> QuizzesIDList = new ArrayList<>();

    //during Quiz
    static int questionIndex = 1;
    static boolean goNextQuestion = false;
    static ArrayList<String> answerList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        ReadData();
        w_SignIn();
    }

    //////////////////// Log In Methods ////////////////////
    // sign in Window
    public void w_SignIn() {

        // srt Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();
        main_pane.setStyle("-fx-background-color: #1a4267;");
        main_pane.setPadding(new Insets(0, 150, 0, 0));

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(400, 200, 400, 200));

        pane.setStyle("-fx-background-color: #ffffff;");
        main_pane.setRight(pane);

        // Set Title
        Label l_title = new Label("\tSign In");
        l_title.setFont(Font.font(36));
        l_title.setTextFill(Color.rgb(26, 66, 103));
        pane.setTop(l_title);


        // set Fields
        GridPane gridField = new GridPane();
        gridField.setVgap(10);
        gridField.setHgap(10);
        gridField.setPadding(new Insets(50, 10, 10, 10));
        pane.setCenter(gridField);


        Label l_username = new Label("User Name");
        TextField t_username = new TextField();

        Label l_password = new Label("Password");
        PasswordField t_password = new PasswordField();

        gridField.add(l_username, 0, 0);
        gridField.add(t_username, 1, 0);
        gridField.add(l_password, 0, 1);
        gridField.add(t_password, 1, 1);

        // Error Message
        Text t_message = new Text();

        // sign in Button
        Button b_signIn = new Button("Sign in");
        b_signIn.setStyle("-fx-background-color: #1A4267FF;");
        b_signIn.setMinWidth(250);
        b_signIn.setTextFill(Color.rgb(255, 255, 255));

        b_signIn.setOnAction(e -> {
            if (t_username.getText().length() != 0) {
                if (t_password.getText().length() != 0) {
                    if (checkAccount(t_username.getText(), t_password.getText())) {
                        String accountType = current_user.getType();


                        if (accountType.equals("Student")) w_Student();
                        else if (accountType.equals("Instructor")) w_Instructor();


                        stage.close();
                    } else t_message.setText("Username or Password is incorrect");
                }
            }
            t_message.setText("Username or Password is incorrect");
        });

        // sign up Button
        Button b_signUp = new Button("Sign up");
        b_signUp.setStyle("-fx-background-color: #1A42674D;");
        b_signUp.setMinWidth(250);
        b_signUp.setOnAction(e -> {
            w_SignUp(); // open sign up window
        });

        // grid Button
        GridPane gridButtons = new GridPane();
        pane.setBottom(gridButtons);
        gridButtons.setVgap(5);
        gridButtons.setHgap(10);


        gridButtons.add(b_signIn, 0, 1);
        gridButtons.add(b_signUp, 0, 2);

        // Error Message
        t_message.setFill(Color.rgb(113, 5, 36));
        gridButtons.add(t_message, 0, 0);

        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign In");
        stage.show();
    }

    // Sign Up Window
    public void w_SignUp() {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();
        main_pane.setStyle("-fx-background-color: #1a4267;");
        main_pane.setPadding(new Insets(0, 150, 0, 0));

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(300, 200, 400, 200));

        pane.setStyle("-fx-background-color: #ffffff;");
        main_pane.setRight(pane);

        // Set Title
        Label l_title = new Label("\tSign Up");
        l_title.setFont(Font.font(36));
        l_title.setTextFill(Color.rgb(26, 66, 103));
        pane.setTop(l_title);

        // Error Message
        Text t_message = new Text();

        // set Fields
        GridPane gridField = new GridPane();
        gridField.setVgap(10);
        gridField.setHgap(10);
        gridField.setPadding(new Insets(50, 10, 10, 10));
        pane.setCenter(gridField);

        // First Name
        Label l_firstName = new Label("FirstName");
        TextField t_firstName = new TextField();
        gridField.add(l_firstName, 0, 0);
        gridField.add(t_firstName, 1, 0);

        // Last Name
        Label l_lastName = new Label("Last Name");
        TextField t_lastName = new TextField();
        gridField.add(l_lastName, 0, 1);
        gridField.add(t_lastName, 1, 1);

        // User Name
        Label l_username = new Label("User Name");
        TextField t_username = new TextField();
        gridField.add(l_username, 0, 2);
        gridField.add(t_username, 1, 2);

        // Password
        Label l_password = new Label("Password");
        PasswordField t_password = new PasswordField();
        gridField.add(l_password, 0, 3);
        gridField.add(t_password, 1, 3);

        // check Password
        Label l_password2 = new Label("Confirm Password");
        PasswordField t_password2 = new PasswordField();
        gridField.add(l_password2, 0, 4);
        gridField.add(t_password2, 1, 4);

        // Email
        Label l_email = new Label("Email Address");
        TextField t_email = new TextField();
        gridField.add(l_email, 0, 5);
        gridField.add(t_email, 1, 5);

        Label l_accountType = new Label("Account Type");
        ComboBox<String> c_accountType = new ComboBox<>();
        c_accountType.getItems().add("Student");
        c_accountType.getItems().add("Instructor");

        gridField.add(l_accountType, 0, 6);
        gridField.add(c_accountType, 1, 6);


        // Buttons
        Button b_register = new Button("Register");
        b_register.setStyle("-fx-background-color: #1A4267FF;");
        b_register.setMinWidth(275);
        b_register.setTextFill(Color.rgb(255, 255, 255));

        b_register.setOnAction(e -> {
            if (t_firstName.getText().length() != 0) {
                if (t_lastName.getText().length() != 0) {
                    if (t_username.getText().length() != 0 && usernameIsDigit(t_username.getText())) {
                        if (t_password.getText().length() != 0) {
                            if (t_password.getText().equals(t_password2.getText())) {
                                if (t_email.getText().length() != 0) {
                                    boolean isEmail = false;
                                    for (int i = 0; i < t_email.getText().length(); i++) {
                                        if (t_email.getText().charAt(i) == '@') {
                                            isEmail = true;
                                            break;
                                        }
                                    }
                                    if (isEmail) {
                                        if (c_accountType.getValue().length() != 0) {
                                            if (!checkAccount(t_username.getText(), t_password.getText())) {
                                                newAccount(c_accountType.getValue(), t_firstName.getText(), t_lastName.getText(), t_username.getText()
                                                        , t_password.getText(), t_email.getText());
                                                t_message.setText("Done, You Registered Successfully");
                                                t_message.setFill(Color.GREEN);
                                                saveData();
                                                stage.close();
                                            } else t_message.setText("The Account already Registered");
                                        } else t_message.setText("Account Type is not chosen");
                                    } else t_message.setText("This is not an Email");
                                } else t_message.setText("Email is empty.");
                            } else t_message.setText("The password is not match.");
                        } else t_message.setText("Password is empty.");
                    } else t_message.setText("UserName is empty Or is Contains Char.");
                } else t_message.setText("LastName is empty.");
            } else t_message.setText("FirstName is empty.");
        });


        GridPane gridButtons = new GridPane();
        gridButtons.setVgap(5);
        gridButtons.setHgap(10);
        pane.setBottom(gridButtons);

        gridButtons.add(b_register, 0, 1);


        gridButtons.add(t_message, 0, 0);
        t_message.setFill(Color.rgb(113, 5, 36));


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign Up");
        stage.show();
    }

    // creating a new account
    public void newAccount(String accountType, String firstName, String lastName, String username, String password, String email) {
        if (accountType.equals("Student")) {
            users.add(new Student(firstName, lastName, username, password, email));
        } else {
            users.add(new Instructor(firstName, lastName, username, password, email));
        }
    }

    // check account is in the list
    public boolean checkAccount(String UserName, String Password) {
        for (User user : users) {
            if (user.getUsername().equals(UserName) && (user.getPassword().equals(Password))) {
                current_user = user;
                if (user.getType().equals("Instructor")) {
                    current_instructor = (Instructor) user;
                } else {
                    current_student = (Student) user;
                }
                return true;
            }
        }
        return false;
    }

    // check if the username is a digit
    public Boolean usernameIsDigit(String userName) {
        for (int i = 0; i < userName.length(); i++) {
            if (!Character.isDigit(userName.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    //////////////////// Student ////////////////////

    // Student Window
    public void w_Student() {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text("Quizzes List");
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);

        // Welcome message
        Text welcome_message = new Text("|   Welcome Mr." + current_user.getLastName() + " | ");
        welcome_message.setFill(Color.WHITE);
        welcome_message.setFont(Font.font("", FontWeight.BOLD, 15));
        top_Pane.add(welcome_message, 1, 0);

        // Sign Out
        Button b_signOut = new Button("Sign Out");
        b_signOut.setTextFill(Color.WHITE);
        b_signOut.setStyle("-fx-background-color: #cd385c;");
        b_signOut.setOnAction(e -> {
            w_SignIn();
            stage.close();
        });
        top_Pane.add(b_signOut, 2, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setPadding(new Insets(100, 475, 100, 475));
        setCenter.setCenter(center);


        GridPane button_grid = new GridPane();

        button_grid.setVgap(10);
        button_grid.setHgap(10);


        getStudentActiveQuizzes(current_student);

        for (int i = 0; i < StudentActiveQuizzes.size(); i++) {
            Button b_quiz = new Button(StudentActiveQuizzes.get(i).getQuizName() + "  |  "+ StudentActiveQuizzes.get(i).getQuizType());
            b_quiz.setTextFill(Color.WHITE);
            b_quiz.setMinWidth(660);
            b_quiz.setMinHeight(70);
            button_grid.add(b_quiz, 0, i);

            if (StudentActiveQuizzes.get(i).getQuizType().equals("Enabled")) {
                b_quiz.setStyle("-fx-background-color: #2f9eb7;");
            } else {
                b_quiz.setStyle("-fx-background-color: #cd385c;");
            }

            int quizIndex = i;
            b_quiz.setOnAction(e -> {
                if (StudentActiveQuizzes.get(quizIndex).getQuizType().equals("Enabled")) {
                    w_Quiz(StudentActiveQuizzes.get(quizIndex));
                    b_quiz.setStyle("-fx-background-color: #cd385c;");
                    stage.close();
                    answerList.clear();
                }
            });

        }

        ScrollPane scrollPane = new ScrollPane();
        center.setCenter(scrollPane);
        scrollPane.setContent(button_grid);

        //////////////////


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Student");
        stage.show();
    }

    // Quiz Window
    public void w_Quiz(Quiz quiz) {
        answerList.clear();
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text(quiz.getQuizName());
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setStyle("-fx-border-color: #324255FF ;");
        setCenter.setCenter(center);

        ///////////////////

        // Center Top
        GridPane top_grid = new GridPane();
        top_grid.setPadding(new Insets(10, 10, 10, 10));
        top_grid.setVgap(5);

        center.setTop(top_grid);

        GridPane g_top = new GridPane();
        g_top.setPadding(new Insets(13, 0, 13, 20)); // Insets(TOP, Right, Bottom, Lift)
        g_top.setMinWidth(1600);
        g_top.setMinHeight(50);
        g_top.setVgap(50);
        g_top.setHgap(25);
        top_grid.add(g_top, 0, 0);
        g_top.setStyle("-fx-background-color: #1A4267FF;");


        Label l_QuizResult = new Label("Question");
        l_QuizResult.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizResult.setTextFill(Color.WHITE);
        g_top.add(l_QuizResult, 0, 0);


        TextField t_QuizResult = new TextField(questionIndex + "  /  " + quiz.getQuestionList().size()); // edit here
        t_QuizResult.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizResult.setAlignment(Pos.CENTER);
        t_QuizResult.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFFF;");
        t_QuizResult.setDisable(true);
        g_top.add(t_QuizResult, 1, 0);


        // Next Question
        Button b_nextQ = new Button("Next Question > ");
        b_nextQ.setMinWidth(1600);
        b_nextQ.setMinHeight(40);
        b_nextQ.setFont(Font.font("", FontWeight.BOLD, 16));
        b_nextQ.setTextFill(Color.WHITE);
        b_nextQ.setStyle("-fx-background-color: #1A4267FF;");
        top_grid.add(b_nextQ, 0, 1);

        center.setCenter(getQuestion_Grid(quiz.getQuestionList().get(questionIndex - 1), questionIndex));


        b_nextQ.setOnAction(e -> {
            if(goNextQuestion) {
                if (questionIndex < quiz.getQuestionList().size()) {
                    questionIndex += 1;
                    center.setCenter(getQuestion_Grid(quiz.getQuestionList().get(questionIndex - 1), questionIndex));
                    t_QuizResult.setText((questionIndex) + "  /  " + quiz.getQuestionList().size());
                    goNextQuestion=false;
                    if (questionIndex == quiz.getQuestionList().size()) {
                        b_nextQ.setText("SUBMIT");
                        b_nextQ.setStyle("-fx-background-color: #5ab380;");
                    }
                } else {
                    w_Result(quiz);
                    stage.close();
                }
            }

        });


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Quiz");
        stage.show();
    }
    public GridPane getQuestion_Grid(Question question, int Q_index) {
        // grid
        GridPane main_grid = new GridPane();
        main_grid.setHgap(10);
        main_grid.setVgap(10);
        main_grid.setPadding(new Insets(10, 10, 20, 50));

        // Main Question
        Text Main_Question = new Text("Q" + Q_index + ". " + question.getQuestion() + " ?");
        Main_Question.setFont(Font.font("", FontWeight.BOLD, 18));
        Main_Question.setFill(Color.rgb(26, 66, 103));
        main_grid.add(Main_Question, 0, 0);

        // print option
        GridPane option_grid = new GridPane();
        option_grid.setVgap(5);
        option_grid.setHgap(5);
        for (int i = 0; i < question.countOption(); i++) {
            Text t_option = new Text("Option " + (i + 1) + " | " + question.getOptions(i));
            t_option.setFont(Font.font(14));
            t_option.setFill(Color.rgb(50, 50, 50));
            option_grid.add(t_option, 0, i);
        }

        main_grid.add(option_grid, 0, 1);

        // set Answer
        Text t_answer = new Text("Answer");
        t_answer.setFont(Font.font("", FontWeight.BOLD, 14));
        t_answer.setFill(Color.rgb(26, 66, 103));
        main_grid.add(t_answer, 0, 3);

        ComboBox<String> c_AnswerOption = new ComboBox<>();
        c_AnswerOption.getItems().add("OPTION 1");
        c_AnswerOption.getItems().add("OPTION 2");
        c_AnswerOption.getItems().add("OPTION 3");
        c_AnswerOption.getItems().add("OPTION 4");

        main_grid.add(c_AnswerOption, 0, 4);

        Button b_save = new Button("Save answer");
        b_save.setStyle("-fx-background-color: #1A4267FF;");
        b_save.setTextFill(Color.WHITE);
        b_save.setFont(Font.font(12));
        b_save.setMinWidth(400);
        b_save.setOnAction(e -> {
            if (c_AnswerOption.getValue() != null) {


                switch (c_AnswerOption.getValue()) {
                    case "OPTION 1" -> answerList.add(question.getOptions(0));
                    case "OPTION 2" -> answerList.add(question.getOptions(1));
                    case "OPTION 3" -> answerList.add(question.getOptions(2));
                    case "OPTION 4" -> answerList.add(question.getOptions(3));
                }


                b_save.setStyle("-fx-background-color: #cd385c;");
                goNextQuestion = true;
                b_save.setDisable(true);
            }
        });
        main_grid.add(b_save, 0, 5);

        return main_grid;
    }

    // Result Window
    public void w_Result(Quiz quiz) {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text("Quiz Result");
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        setCenter.setMaxWidth(1000);
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setStyle("-fx-border-color: #324255FF ;");
        setCenter.setCenter(center);



        // Center Top
        GridPane top_grid = new GridPane();
        top_grid.setPadding(new Insets(10, 0, 10, 0));
        top_grid.setVgap(5);

        center.setCenter(top_grid);

        GridPane g_result = new GridPane();
        center.setTop(g_result);
        g_result.setPadding(new Insets(13, 0, 13, 20)); // Insets(TOP, Right, Bottom, Lift)
        g_result.setMinWidth(1000);
        g_result.setMinHeight(50);
        g_result.setVgap(50);
        g_result.setHgap(25);
        g_result.setStyle("-fx-background-color: #1A4267FF;");


        Label l_QuizResult = new Label("Result");
        l_QuizResult.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizResult.setTextFill(Color.WHITE);
        g_result.add(l_QuizResult, 0, 0);
        int result = 0 ;
        if(answerList.size()!=0){
            for (int i = 0; i < quiz.getQuestionList().size(); i++){
                if(quiz.getQuestionList().get(i).getCorrectAnswer().equals(answerList.get(i))){
                    result++;
                }
            }
        }


        TextField t_QuizResult = new TextField(result + "  /  " + quiz.getQuestionList().size());
        t_QuizResult.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizResult.setAlignment(Pos.CENTER);
        t_QuizResult.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFFF;");
        t_QuizResult.setDisable(true);
        g_result.add(t_QuizResult, 1, 0);

        // show Result
        GridPane show_result = new GridPane();
        show_result.setVgap(5);
        show_result.setHgap(5);
        show_result.setPadding(new Insets(10, 10, 10, 10));
        ScrollPane scrollPane = new ScrollPane();
        center.setCenter(scrollPane);
        scrollPane.setContent(show_result);

        //show_result.add
        for (int i = 0; i < quiz.getQuestionList().size(); i++) {
            GridPane g_question = new GridPane();
            show_result.add(g_question, 0, i);
            g_question.setVgap(10);
            g_question.setHgap(10);

            //Main Question
            Text l_mainQuestion = new Text("Question "+(i+1) +" | ");
            l_mainQuestion.setFont(Font.font("",FontWeight.BOLD,14));
            l_mainQuestion.setFill(Color.rgb(26,66,103));
            g_question.add(l_mainQuestion,0,0);

            Text t_mainQuestion = new Text(quiz.getQuestionList().get(i).getQuestion()+"?");
            t_mainQuestion.setFont(Font.font("",FontWeight.BOLD,14));
            t_mainQuestion.setFill(Color.rgb(26,66,103));
            g_question.add(t_mainQuestion,1,0);

            //Correct Answer
            Text l_correctAnswer = new Text("Correct Answer");
            l_correctAnswer.setFont(Font.font("",FontWeight.BOLD,14));
            l_correctAnswer.setFill(Color.rgb(26,66,103));
            g_question.add(l_correctAnswer,0,1);

            Text t_correctAnswer = new Text(quiz.getQuestionList().get(i).getCorrectAnswer());
            t_correctAnswer.setFont(Font.font("",FontWeight.BOLD,14));
            t_correctAnswer.setFill(Color.rgb(90,179,128));
            g_question.add(t_correctAnswer,1,1);

            //Your Answer


            Text l_Answer = new Text("Your Answer");
            l_Answer.setFont(Font.font("",FontWeight.BOLD,14));
            l_Answer.setFill(Color.rgb(26,66,103));
            g_question.add(l_Answer,0,2);

            Text t_Answer = new Text(answerList.get(i));
            t_Answer.setFont(Font.font("",FontWeight.BOLD,14));
            t_Answer.setFill(Color.rgb(205,56,92));
            g_question.add(t_Answer,1,2);





        }
        center.setCenter(scrollPane);


        // Print PDF
        Button b_print = new Button("PRINT");
        b_print.setPadding(new Insets(10, 10, 10, 10));
        b_print.setMinWidth(200);
        b_print.setMinHeight(10);
        b_print.setFont(Font.font("", FontWeight.BOLD, 12));
        b_print.setTextFill(Color.WHITE);
        b_print.setStyle("-fx-background-color: #5ab380;");
        g_result.add(b_print, 2, 0);

        b_print.setOnAction(e -> {
            PrinterJob PRINTER_BUTTON = PrinterJob.createPrinterJob();
            if (PRINTER_BUTTON != null) {
                PRINTER_BUTTON.printPage(show_result); // t_QuizResult is the "node" that is wanted to be printed
                PRINTER_BUTTON.endJob();
            }
        });

        // close
        Button b_close = new Button("CLOSE");
        b_close.setPadding(new Insets(10, 10, 10, 10));
        b_close.setMinWidth(200);
        b_close.setMinHeight(10);
        b_close.setFont(Font.font("", FontWeight.BOLD, 12));
        b_close.setTextFill(Color.WHITE);
        b_close.setStyle("-fx-background-color: #cd385c;");
        g_result.add(b_close, 3, 0);


        b_close.setOnAction(e -> {
            stage.close();
            w_Student();
        });


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Student");
        stage.show();
    }

    // Check Student
    public boolean isStudent(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getType().equals("Student")) return true;
        }
        return false;
    }

    // get Student by username
    public static Student getStudent(String username) {
        for (User user : users) {
            if (user.getType().equals("Student") && user.getUsername().equals(username)) {
                return (Student) user;
            }
        }
        return null;
    }

    // get Student Active Quizzed
    public void getStudentActiveQuizzes(Student user) {
        user = getStudent(user.getUsername());
        ArrayList<Quiz> quizzes_list = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            if (quiz.isStudentHasAQuiz(user)) {
                quizzes_list.add(quiz);
            }
        }
        StudentActiveQuizzes = quizzes_list;
    }

    //////////////////// Instructor ////////////////////

    // Instructor Window
    public void w_Instructor() {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text("Quizzes List");
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);

        // Welcome message
        Text welcome_message = new Text("|   Welcome Mr." + current_user.getLastName() + " | ");
        welcome_message.setFill(Color.WHITE);
        welcome_message.setFont(Font.font("", FontWeight.BOLD, 15));
        top_Pane.add(welcome_message, 1, 0);

        // Sign Out
        Button b_signOut = new Button("Sign Out");
        b_signOut.setTextFill(Color.WHITE);
        b_signOut.setStyle("-fx-background-color: #cd385c;");
        b_signOut.setOnAction(e -> {
            w_SignIn();
            stage.close();
        });
        top_Pane.add(b_signOut, 2, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setPadding(new Insets(100, 475, 100, 475));
        setCenter.setCenter(center);

        // set Top
        Button b_newQuiz = new Button("New Quiz");
        b_newQuiz.setPadding(new Insets(5, 5, 5, 5));
        b_newQuiz.setMinWidth(675);
        b_newQuiz.setMinHeight(40);
        b_newQuiz.setFont(Font.font("", FontWeight.BOLD, 12));
        b_newQuiz.setTextFill(Color.WHITE);
        b_newQuiz.setStyle("-fx-background-color: #5ab380;");
        b_newQuiz.setOnAction(e -> {
            w_newQuiz();
            stage.close();
        });
        center.setTop(b_newQuiz);

        GridPane button_grid = new GridPane();

        button_grid.setVgap(10);
        button_grid.setHgap(10);


        getInstructorActiveQuizzes(current_instructor);


        for (int i = 0; i < InstructorActiveQuizzes.size(); i++) {
            Button b_quiz = new Button(InstructorActiveQuizzes.get(i).getQuizName() + "");
            b_quiz.setTextFill(Color.WHITE);
            b_quiz.setStyle("-fx-background-color: #2f9eb7;");
            b_quiz.setMinWidth(670);
            b_quiz.setMinHeight(70);
            button_grid.add(b_quiz, 0, i);

            int quizIndex = i;
            b_quiz.setOnAction(e -> {
                EditQuiz = InstructorActiveQuizzes.get(quizIndex);
                w_EditQuiz();
            });

        }

        ScrollPane scrollPane = new ScrollPane();
        center.setCenter(scrollPane);
        scrollPane.setContent(button_grid);

        //////////////////


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Instructor");
        stage.show();
    }

    // new Quiz Window
    public void w_newQuiz() {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text("New Quiz");
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        setCenter.setMaxWidth(1000);
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setStyle("-fx-border-color: #324255FF ;");
        setCenter.setCenter(center);


        // Center Top
        GridPane top_grid = new GridPane();
        top_grid.setPadding(new Insets(10, 0, 10, 0));
        top_grid.setVgap(5);

        center.setCenter(top_grid);

        GridPane g_result = new GridPane();
        center.setTop(g_result);
        g_result.setPadding(new Insets(13, 0, 13, 20)); // Insets(TOP, Right, Bottom, Lift)
        g_result.setMinWidth(1000);
        g_result.setMinHeight(50);
        g_result.setVgap(10);
        g_result.setHgap(10);
        g_result.setStyle("-fx-background-color: #1A4267FF;");

        ////////
        newActiveQuiz = new Quiz(null, getNewQuizID(), null, null, current_instructor.getUsername());
        ////////

        // Quiz Name
        Label l_QuizName = new Label("Quiz Name");
        l_QuizName.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizName.setTextFill(Color.WHITE);
        g_result.add(l_QuizName, 0, 0);

        TextField t_QuizName = new TextField();
        t_QuizName.setMinWidth(800);
        t_QuizName.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizName.setAlignment(Pos.CENTER);
        t_QuizName.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_QuizName, 1, 0);

        //Quiz ID
        Label l_QuizID = new Label("Quiz ID");
        l_QuizID.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizID.setTextFill(Color.WHITE);
        g_result.add(l_QuizID, 0, 1);

        TextField t_QuizID = new TextField(newActiveQuiz.getQuizID() + "");
        t_QuizID.setMinWidth(800);
        t_QuizID.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizID.setDisable(true);
        t_QuizID.setAlignment(Pos.CENTER);
        t_QuizID.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_QuizID, 1, 1);

        //Instructor UserName
        Label l_instructor = new Label("Instructor UserName");
        l_instructor.setFont(Font.font("", FontWeight.BOLD, 16));
        l_instructor.setTextFill(Color.WHITE);
        g_result.add(l_instructor, 0, 2);

        TextField t_instructor = new TextField(current_instructor.getUsername());
        t_instructor.setMinWidth(800);
        t_instructor.setFont(Font.font("", FontWeight.BOLD, 14));
        t_instructor.setDisable(true);
        t_instructor.setAlignment(Pos.CENTER);
        t_instructor.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_instructor, 1, 2);

        //Quiz Type
        Label l_quizType = new Label("Quiz Type");
        l_quizType.setFont(Font.font("", FontWeight.BOLD, 16));
        l_quizType.setTextFill(Color.WHITE);
        g_result.add(l_quizType, 0, 4);

        ComboBox<String> t_quizType = new ComboBox<>();
        t_quizType.setMinWidth(800);
        t_quizType.getItems().add("Enabled");
        t_quizType.getItems().add("Disable");
        g_result.add(t_quizType, 1, 4);

        //Insert Students
        Label l_insertStudent = new Label("Insert Student");
        l_insertStudent.setFont(Font.font("", FontWeight.BOLD, 16));
        l_insertStudent.setTextFill(Color.WHITE);
        g_result.add(l_insertStudent, 0, 3);

        Button b_insertStudent = new Button("INSERT");
        b_insertStudent.setPadding(new Insets(10, 10, 10, 10));
        b_insertStudent.setMinWidth(800);
        b_insertStudent.setMinHeight(10);
        b_insertStudent.setFont(Font.font("", FontWeight.BOLD, 12));
        b_insertStudent.setTextFill(Color.WHITE);
        b_insertStudent.setStyle("-fx-background-color: #5ab380;");
        g_result.add(b_insertStudent, 1, 3);

        b_insertStudent.setOnAction(e -> {
            insertStudentsToQuiz("NEW QUIZ");
        });

        // Add Questions
        BorderPane main_p_newQuestion = new BorderPane();
        center.setCenter(main_p_newQuestion);
        ScrollPane scrollPane = new ScrollPane();
        main_p_newQuestion.setCenter(scrollPane);
        GridPane g_question = new GridPane();
        scrollPane.setContent(g_question);


        g_question.setVgap(10);
        g_question.setHgap(10);
        g_question.setStyle("-fx-background-color: #94e6f7;");

        /////
        ArrayList<Question> questions_list = new ArrayList<>();
        /////

        // new Question Button
        Button b_newQuestion = new Button("NEW QUESTION");
        b_newQuestion.setPadding(new Insets(5, 5, 5, 5));
        b_newQuestion.setMinWidth(1000);
        b_newQuestion.setMinHeight(40);
        b_newQuestion.setFont(Font.font("", FontWeight.BOLD, 12));
        b_newQuestion.setTextFill(Color.WHITE);
        b_newQuestion.setStyle("-fx-background-color: #5ab380;");
        main_p_newQuestion.setTop(b_newQuestion);


        b_newQuestion.setOnAction(e -> {
            if (questions_list.size() < 10) {

                Question question = new Question(null, null, null, null, null);
                questions_list.add(question);
                GridPane grid_question = new GridPane();
                grid_question.setMinWidth(1080);
                grid_question.setVgap(5);
                grid_question.setHgap(5);
                grid_question.setPadding(new Insets(10, 10, 10, 10));
                grid_question.setStyle("-fx-background-color: #1a4267;");
                g_question.add(grid_question, 0, questions_list.size());

                // main question
                Text l_main_question = new Text("Question | " + questions_list.size());
                l_main_question.setFont(Font.font("", FontWeight.BOLD, 12));
                l_main_question.setFill(Color.WHITE);
                grid_question.add(l_main_question, 0, 0);

                TextArea t_main_question = new TextArea();
                t_main_question.setMaxHeight(100);
                t_main_question.setMinWidth(850);
                t_main_question.setFont(Font.font(14));
                grid_question.add(t_main_question, 1, 0);

                ArrayList<String> options = new ArrayList<>();

                // option 1
                Text l_option_1 = new Text("OPTION 1");
                l_option_1.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_1.setFill(Color.WHITE);
                grid_question.add(l_option_1, 0, 1);

                TextField t_option_1 = new TextField();
                t_option_1.setFont(Font.font(14));
                grid_question.add(t_option_1, 1, 1);

                // option 2
                Text l_option_2 = new Text("OPTION 2");
                l_option_2.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_2.setFill(Color.WHITE);
                grid_question.add(l_option_2, 0, 2);

                TextField t_option_2 = new TextField();
                t_option_2.setFont(Font.font(14));
                grid_question.add(t_option_2, 1, 2);

                // option 3
                Text l_option_3 = new Text("OPTION 3");
                l_option_3.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_3.setFill(Color.WHITE);
                grid_question.add(l_option_3, 0, 3);

                TextField t_option_3 = new TextField();
                t_option_3.setFont(Font.font(14));
                grid_question.add(t_option_3, 1, 3);

                // option 4
                Text l_option_4 = new Text("OPTION 4");
                l_option_4.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_4.setFill(Color.WHITE);
                grid_question.add(l_option_4, 0, 4);

                TextField t_option_4 = new TextField();
                t_option_4.setFont(Font.font(14));
                grid_question.add(t_option_4, 1, 4);

                // Correct Ans
                Text l_correctAns = new Text("Correct Answer");
                l_correctAns.setFont(Font.font("", FontWeight.BOLD, 12));
                l_correctAns.setFill(Color.WHITE);
                grid_question.add(l_correctAns, 0, 5);

                ComboBox<String> t_correctAns = new ComboBox<>();
                question.setAnswer(t_correctAns.getValue());
                t_correctAns.setMinWidth(850);
                t_correctAns.getItems().add("OPTION 1");
                t_correctAns.getItems().add("OPTION 2");
                t_correctAns.getItems().add("OPTION 3");
                t_correctAns.getItems().add("OPTION 4");
                grid_question.add(t_correctAns, 1, 5);

                // difficulty
                Text l_difficulty = new Text("Difficulty");
                l_difficulty.setFont(Font.font("", FontWeight.BOLD, 12));
                l_difficulty.setFill(Color.WHITE);
                grid_question.add(l_difficulty, 0, 6);

                ComboBox<String> t_difficulty = new ComboBox<>();
                t_difficulty.setMinWidth(850);
                t_difficulty.getItems().add("Easy");
                t_difficulty.getItems().add("Medium");
                t_difficulty.getItems().add("Hard");
                grid_question.add(t_difficulty, 1, 6);

                // Ch
                Text l_chapter = new Text("Chapter");
                l_chapter.setFont(Font.font("", FontWeight.BOLD, 12));
                l_chapter.setFill(Color.WHITE);
                grid_question.add(l_chapter, 0, 7);

                TextField t_chapter = new TextField();
                t_chapter.setFont(Font.font(14));
                grid_question.add(t_chapter, 1, 7);

                // Save
                Text l_save = new Text("Save");
                l_save.setFont(Font.font("", FontWeight.BOLD, 12));
                l_save.setFill(Color.WHITE);
                grid_question.add(l_save, 0, 8);

                Button b_save = new Button("SAVE");
                b_save.setMinWidth(850);
                b_save.setMinHeight(40);
                b_save.setFont(Font.font("", FontWeight.BOLD, 12));
                b_save.setTextFill(Color.WHITE);
                b_save.setStyle("-fx-background-color: #5ab380;");
                grid_question.add(b_save, 1, 8);

                b_save.setOnAction(e2 -> {

                    question.setQuestion(t_main_question.getText());
                    options.add(t_option_1.getText());
                    options.add(t_option_2.getText());
                    options.add(t_option_3.getText());
                    options.add(t_option_4.getText());
                    question.setAnswer(t_correctAns.getValue());
                    question.setQuestionChapter(t_chapter.getText());
                    question.setQuestionDifficulty(t_difficulty.getValue());
                    question.setOptions(options);

                });

            }
        });

        // Save Quiz
        GridPane top_View = new GridPane();
        top_View.setPadding(new Insets(5, 5, 5, 5));
        top_View.setVgap(10);
        top_View.setHgap(10);
        setCenter.setTop(top_View);


        Button b_save = new Button("Save");
        b_save.setPadding(new Insets(10, 10, 10, 10));
        b_save.setMinWidth(150);
        b_save.setMinHeight(10);
        b_save.setFont(Font.font("", FontWeight.BOLD, 12));
        b_save.setTextFill(Color.WHITE);
        b_save.setStyle("-fx-background-color: #5ab380;");
        top_View.add(b_save, 0, 0);

        //Message
        Text t_message = new Text("Fill Quiz Details");
        t_message.setFont(Font.font("", FontWeight.BOLD, 16));
        t_message.setFill(Color.rgb(61, 61, 61));
        top_View.add(t_message, 1, 0);


        b_save.setOnAction(e -> {
            if (t_QuizName.getText().length() != 0) {
                newActiveQuiz.setQuizName(t_QuizName.getText());
                newActiveQuiz.setQuestionList(questions_list);
                newActiveQuiz.setQuizType(t_quizType.getValue());
                if (newActiveQuiz.getQuizName().length() != 0 && newActiveQuiz.getQuizType() != null && newActiveQuiz.getQuestionList() != null) {
                    quizzes.add(newActiveQuiz);
                    w_Instructor();
                    saveData();
                    stage.close();
                } else {
                    t_message.setText("Some Data is missing");
                    t_message.setFill(Color.rgb(205, 56, 92));
                }
            } else {
                t_message.setText("Quiz Name Is Not Assigned");
                t_message.setFill(Color.rgb(205, 56, 92));
            }
        });


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("New Quiz");
        stage.show();
    }
    public int getNewQuizID() {
        int id;
        while (true) {
            id = (int) (1 + (Math.random() * 100000));
            if (!QuizzesIDList.contains(id)) {
                QuizzesIDList.add(id);
                break;
            }
        }
        return id;
    }

    // new Quiz Window
    public void w_EditQuiz() {
        // set Stage
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();

        // Top pane
        GridPane top_Pane = new GridPane();
        top_Pane.setHgap(10);
        top_Pane.setVgap(10);
        main_pane.setTop(top_Pane);
        top_Pane.setMinHeight(50);
        top_Pane.setStyle("-fx-background-color: #1A4267FF;");
        top_Pane.setPadding(new Insets(10, 10, 20, 50));

        // page Title
        Text title = new Text(EditQuiz.getQuizName());
        title.setFont(Font.font("", FontWeight.BOLD, 24));
        title.setFill(Color.rgb(148, 230, 247));
        top_Pane.add(title, 0, 0);


        // set Center
        BorderPane setCenter = new BorderPane();
        setCenter.setMaxWidth(1000);
        main_pane.setCenter(setCenter);
        setCenter.setPadding(new Insets(100, 150, 100, 150));
        BorderPane center = new BorderPane();
        center.setStyle("-fx-border-color: #324255FF ;");
        setCenter.setCenter(center);


        // Center Top
        GridPane top_grid = new GridPane();
        top_grid.setPadding(new Insets(10, 0, 10, 0));
        top_grid.setVgap(5);

        center.setCenter(top_grid);

        GridPane g_result = new GridPane();
        center.setTop(g_result);
        g_result.setPadding(new Insets(13, 0, 13, 20)); // Insets(TOP, Right, Bottom, Lift)
        g_result.setMinWidth(1000);
        g_result.setMinHeight(50);
        g_result.setVgap(10);
        g_result.setHgap(10);
        g_result.setStyle("-fx-background-color: #1A4267FF;");


        // Quiz Name
        Label l_QuizName = new Label("Quiz Name");
        l_QuizName.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizName.setTextFill(Color.WHITE);
        g_result.add(l_QuizName, 0, 0);

        TextField t_QuizName = new TextField(EditQuiz.getQuizName());
        t_QuizName.setMinWidth(800);
        t_QuizName.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizName.setAlignment(Pos.CENTER);
        t_QuizName.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_QuizName, 1, 0);

        //Quiz ID
        Label l_QuizID = new Label("Quiz ID");
        l_QuizID.setFont(Font.font("", FontWeight.BOLD, 16));
        l_QuizID.setTextFill(Color.WHITE);
        g_result.add(l_QuizID, 0, 1);

        TextField t_QuizID = new TextField(EditQuiz.getQuizID() + "");
        t_QuizID.setMinWidth(800);
        t_QuizID.setFont(Font.font("", FontWeight.BOLD, 14));
        t_QuizID.setDisable(true);
        t_QuizID.setAlignment(Pos.CENTER);
        t_QuizID.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_QuizID, 1, 1);

        //Instructor UserName
        Label l_instructor = new Label("Instructor UserName");
        l_instructor.setFont(Font.font("", FontWeight.BOLD, 16));
        l_instructor.setTextFill(Color.WHITE);
        g_result.add(l_instructor, 0, 2);

        TextField t_instructor = new TextField(current_instructor.getUsername());
        t_instructor.setMinWidth(800);
        t_instructor.setFont(Font.font("", FontWeight.BOLD, 14));
        t_instructor.setDisable(true);
        t_instructor.setAlignment(Pos.CENTER);
        t_instructor.setStyle("-fx-text-inner-color: #003E4CFF; -fx-background-color: #FFFFFFA1;");
        g_result.add(t_instructor, 1, 2);

        //Quiz Type
        Label l_quizType = new Label("Quiz Type");
        l_quizType.setFont(Font.font("", FontWeight.BOLD, 16));
        l_quizType.setTextFill(Color.WHITE);
        g_result.add(l_quizType, 0, 4);

        ComboBox<String> t_quizType = new ComboBox<>();
        t_quizType.setValue(EditQuiz.getQuizType());
        t_quizType.setMinWidth(800);
        t_quizType.getItems().add("Enabled");
        t_quizType.getItems().add("Disable");
        g_result.add(t_quizType, 1, 4);

        //Insert Students
        Label l_insertStudent = new Label("Insert Student");
        l_insertStudent.setFont(Font.font("", FontWeight.BOLD, 16));
        l_insertStudent.setTextFill(Color.WHITE);
        g_result.add(l_insertStudent, 0, 3);

        Button b_insertStudent = new Button("INSERT");
        b_insertStudent.setPadding(new Insets(10, 10, 10, 10));
        b_insertStudent.setMinWidth(800);
        b_insertStudent.setMinHeight(10);
        b_insertStudent.setFont(Font.font("", FontWeight.BOLD, 12));
        b_insertStudent.setTextFill(Color.WHITE);
        b_insertStudent.setStyle("-fx-background-color: #5ab380;");
        g_result.add(b_insertStudent, 1, 3);

        b_insertStudent.setOnAction(e -> {
            insertStudentsToQuiz("Edit Quiz");
        });


        // Add Questions
        BorderPane main_p_newQuestion = new BorderPane();
        center.setCenter(main_p_newQuestion);
        ScrollPane scrollPane = new ScrollPane();
        main_p_newQuestion.setCenter(scrollPane);
        GridPane g_question = new GridPane();
        scrollPane.setContent(g_question);


        g_question.setVgap(10);
        g_question.setHgap(10);
        g_question.setStyle("-fx-background-color: #94e6f7;");

        /////
        ArrayList<Question> questions_list = EditQuiz.getQuestionList();
        /////

        // new Question Button
        Button b_newQuestion = new Button("NEW QUESTION");
        b_newQuestion.setPadding(new Insets(5, 5, 5, 5));
        b_newQuestion.setMinWidth(1000);
        b_newQuestion.setMinHeight(40);
        b_newQuestion.setFont(Font.font("", FontWeight.BOLD, 12));
        b_newQuestion.setTextFill(Color.WHITE);
        b_newQuestion.setStyle("-fx-background-color: #5ab380;");
        main_p_newQuestion.setTop(b_newQuestion);

        if (!questions_list.isEmpty()) {
            for (int i = 0; i < questions_list.size(); i++) {
                g_question.add(getQuestionGrid(questions_list.get(i)), 0, i);
            }
        }


        b_newQuestion.setOnAction(e -> {
            if (questions_list.size() < 10) {
                Question question = new Question(null, null, null, null, null);
                questions_list.add(question);
                GridPane grid_question = new GridPane();
                grid_question.setMinWidth(1080);
                grid_question.setVgap(5);
                grid_question.setHgap(5);
                grid_question.setPadding(new Insets(10, 10, 10, 10));
                grid_question.setStyle("-fx-background-color: #1a4267;");
                g_question.add(grid_question, 0, questions_list.size());

                // main question
                Text l_main_question = new Text("Question | " + questions_list.size());
                l_main_question.setFont(Font.font("", FontWeight.BOLD, 12));
                l_main_question.setFill(Color.WHITE);
                grid_question.add(l_main_question, 0, 0);

                TextArea t_main_question = new TextArea();
                t_main_question.setMaxHeight(100);
                t_main_question.setMinWidth(850);
                t_main_question.setFont(Font.font(14));
                grid_question.add(t_main_question, 1, 0);

                ArrayList<String> options = new ArrayList<>();

                // option 1
                Text l_option_1 = new Text("OPTION 1");
                l_option_1.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_1.setFill(Color.WHITE);
                grid_question.add(l_option_1, 0, 1);

                TextField t_option_1 = new TextField();
                t_option_1.setFont(Font.font(14));
                grid_question.add(t_option_1, 1, 1);

                // option 2
                Text l_option_2 = new Text("OPTION 2");
                l_option_2.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_2.setFill(Color.WHITE);
                grid_question.add(l_option_2, 0, 2);

                TextField t_option_2 = new TextField();
                t_option_2.setFont(Font.font(14));
                grid_question.add(t_option_2, 1, 2);

                // option 3
                Text l_option_3 = new Text("OPTION 3");
                l_option_3.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_3.setFill(Color.WHITE);
                grid_question.add(l_option_3, 0, 3);

                TextField t_option_3 = new TextField();
                t_option_3.setFont(Font.font(14));
                grid_question.add(t_option_3, 1, 3);

                // option 4
                Text l_option_4 = new Text("OPTION 4");
                l_option_4.setFont(Font.font("", FontWeight.BOLD, 12));
                l_option_4.setFill(Color.WHITE);
                grid_question.add(l_option_4, 0, 4);

                TextField t_option_4 = new TextField();
                t_option_4.setFont(Font.font(14));
                grid_question.add(t_option_4, 1, 4);

                // Correct Ans
                Text l_correctAns = new Text("Correct Answer");
                l_correctAns.setFont(Font.font("", FontWeight.BOLD, 12));
                l_correctAns.setFill(Color.WHITE);
                grid_question.add(l_correctAns, 0, 5);

                ComboBox<String> t_correctAns = new ComboBox<>();
                question.setAnswer(t_correctAns.getValue());
                t_correctAns.setMinWidth(850);
                t_correctAns.getItems().add("OPTION 1");
                t_correctAns.getItems().add("OPTION 2");
                t_correctAns.getItems().add("OPTION 3");
                t_correctAns.getItems().add("OPTION 4");
                grid_question.add(t_correctAns, 1, 5);

                // difficulty
                Text l_difficulty = new Text("Difficulty");
                l_difficulty.setFont(Font.font("", FontWeight.BOLD, 12));
                l_difficulty.setFill(Color.WHITE);
                grid_question.add(l_difficulty, 0, 6);

                ComboBox<String> t_difficulty = new ComboBox<>();
                t_difficulty.setMinWidth(850);
                t_difficulty.getItems().add("Easy");
                t_difficulty.getItems().add("Medium");
                t_difficulty.getItems().add("Hard");
                grid_question.add(t_difficulty, 1, 6);

                // Ch
                Text l_chapter = new Text("Chapter");
                l_chapter.setFont(Font.font("", FontWeight.BOLD, 12));
                l_chapter.setFill(Color.WHITE);
                grid_question.add(l_chapter, 0, 7);

                TextField t_chapter = new TextField();
                t_chapter.setFont(Font.font(14));
                grid_question.add(t_chapter, 1, 7);

                // Save
                Text l_save = new Text("Save");
                l_save.setFont(Font.font("", FontWeight.BOLD, 12));
                l_save.setFill(Color.WHITE);
                grid_question.add(l_save, 0, 8);

                Button b_save = new Button("SAVE");
                b_save.setMinWidth(850);
                b_save.setMinHeight(40);
                b_save.setFont(Font.font("", FontWeight.BOLD, 12));
                b_save.setTextFill(Color.WHITE);
                b_save.setStyle("-fx-background-color: #5ab380;");
                grid_question.add(b_save, 1, 8);

                b_save.setOnAction(e2 -> {
                    question.setQuestion(t_main_question.getText());
                    options.add(t_option_1.getText());
                    options.add(t_option_2.getText());
                    options.add(t_option_3.getText());
                    options.add(t_option_4.getText());
                    question.setAnswer(t_correctAns.getValue());
                    question.setQuestionChapter(t_chapter.getText());
                    question.setQuestionDifficulty(t_difficulty.getValue());
                    question.setOptions(options);

                });

            }
        });

        // Save Quiz
        GridPane top_View = new GridPane();
        top_View.setPadding(new Insets(5, 5, 5, 5));
        top_View.setVgap(10);
        top_View.setHgap(10);
        setCenter.setTop(top_View);


        Button b_save = new Button("Save");
        b_save.setPadding(new Insets(10, 10, 10, 10));
        b_save.setMinWidth(150);
        b_save.setMinHeight(10);
        b_save.setFont(Font.font("", FontWeight.BOLD, 12));
        b_save.setTextFill(Color.WHITE);
        b_save.setStyle("-fx-background-color: #5ab380;");
        top_View.add(b_save, 0, 0);

        //Message
        Text t_message = new Text("Fill Quiz Details");
        t_message.setFont(Font.font("", FontWeight.BOLD, 16));
        t_message.setFill(Color.rgb(61, 61, 61));
        top_View.add(t_message, 1, 0);


        b_save.setOnAction(e -> {
            if (t_QuizName.getText().length() != 0) {
                EditQuiz.setQuizName(t_QuizName.getText());
                EditQuiz.setQuestionList(questions_list);
                EditQuiz.setQuizType(t_quizType.getValue());
                if (EditQuiz.getQuizName().length() != 0 && EditQuiz.getQuizType() != null && EditQuiz.getQuestionList() != null) {
                    quizzes.remove(EditQuiz);
                    quizzes.add(EditQuiz);
                    saveData();
                    stage.close();
                } else {
                    t_message.setText("Some Data is missing");
                    t_message.setFill(Color.rgb(205, 56, 92));
                }
            } else {
                t_message.setText("Quiz Name Is Not Assigned");
                t_message.setFill(Color.rgb(205, 56, 92));
            }
        });


        // Window's Properties
        Scene scene = new Scene(main_pane, 1920, 1080);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Edit Quiz");
        stage.show();
    }
    public GridPane getQuestionGrid(Question question) {
        GridPane grid_question = new GridPane();
        grid_question.setMinWidth(1080);
        grid_question.setVgap(5);
        grid_question.setHgap(5);
        grid_question.setPadding(new Insets(10, 10, 10, 10));
        grid_question.setStyle("-fx-background-color: #1a4267;");

        // main question
        Text l_main_question = new Text("Question");
        l_main_question.setFont(Font.font("", FontWeight.BOLD, 12));
        l_main_question.setFill(Color.WHITE);
        grid_question.add(l_main_question, 0, 0);

        TextArea t_main_question = new TextArea(question.getQuestion());
        t_main_question.setMaxHeight(100);
        t_main_question.setMinWidth(850);
        t_main_question.setFont(Font.font(14));
        grid_question.add(t_main_question, 1, 0);

        ArrayList<String> options = new ArrayList<>();

        // option 1
        Text l_option_1 = new Text("OPTION 1");
        l_option_1.setFont(Font.font("", FontWeight.BOLD, 12));
        l_option_1.setFill(Color.WHITE);
        grid_question.add(l_option_1, 0, 1);

        TextField t_option_1 = new TextField(question.getOptions(0));
        t_option_1.setFont(Font.font(14));
        grid_question.add(t_option_1, 1, 1);

        // option 2
        Text l_option_2 = new Text("OPTION 2");
        l_option_2.setFont(Font.font("", FontWeight.BOLD, 12));
        l_option_2.setFill(Color.WHITE);
        grid_question.add(l_option_2, 0, 2);

        TextField t_option_2 = new TextField(question.getOptions(1));
        t_option_2.setFont(Font.font(14));
        grid_question.add(t_option_2, 1, 2);

        // option 3
        Text l_option_3 = new Text("OPTION 3");
        l_option_3.setFont(Font.font("", FontWeight.BOLD, 12));
        l_option_3.setFill(Color.WHITE);
        grid_question.add(l_option_3, 0, 3);

        TextField t_option_3 = new TextField(question.getOptions(2));
        t_option_3.setFont(Font.font(14));
        grid_question.add(t_option_3, 1, 3);

        // option 4
        Text l_option_4 = new Text("OPTION 4");
        l_option_4.setFont(Font.font("", FontWeight.BOLD, 12));
        l_option_4.setFill(Color.WHITE);
        grid_question.add(l_option_4, 0, 4);

        TextField t_option_4 = new TextField(question.getOptions(3));
        t_option_4.setFont(Font.font(14));
        grid_question.add(t_option_4, 1, 4);

        // Correct Ans
        Text l_correctAns = new Text("Correct Answer");
        l_correctAns.setFont(Font.font("", FontWeight.BOLD, 12));
        l_correctAns.setFill(Color.WHITE);
        grid_question.add(l_correctAns, 0, 5);

        ComboBox<String> t_correctAns = new ComboBox<>();
        t_correctAns.setValue(question.getAnswer());
        t_correctAns.setMinWidth(850);
        t_correctAns.getItems().add("OPTION 1");
        t_correctAns.getItems().add("OPTION 2");
        t_correctAns.getItems().add("OPTION 3");
        t_correctAns.getItems().add("OPTION 4");
        grid_question.add(t_correctAns, 1, 5);

        // difficulty
        Text l_difficulty = new Text("Difficulty");
        l_difficulty.setFont(Font.font("", FontWeight.BOLD, 12));
        l_difficulty.setFill(Color.WHITE);
        grid_question.add(l_difficulty, 0, 6);

        ComboBox<String> t_difficulty = new ComboBox<>();
        t_difficulty.setValue(question.getQuestionDifficulty());
        t_difficulty.setMinWidth(850);
        t_difficulty.getItems().add("Easy");
        t_difficulty.getItems().add("Medium");
        t_difficulty.getItems().add("Hard");
        grid_question.add(t_difficulty, 1, 6);

        // Ch
        Text l_chapter = new Text("Chapter");
        l_chapter.setFont(Font.font("", FontWeight.BOLD, 12));
        l_chapter.setFill(Color.WHITE);
        grid_question.add(l_chapter, 0, 7);

        TextField t_chapter = new TextField(question.getQuestionChapter());
        t_chapter.setFont(Font.font(14));
        grid_question.add(t_chapter, 1, 7);

        // Save
        Text l_save = new Text("Save");
        l_save.setFont(Font.font("", FontWeight.BOLD, 12));
        l_save.setFill(Color.WHITE);
        grid_question.add(l_save, 0, 8);

        Button b_save = new Button("SAVE");
        b_save.setMinWidth(850);
        b_save.setMinHeight(40);
        b_save.setFont(Font.font("", FontWeight.BOLD, 12));
        b_save.setTextFill(Color.WHITE);
        b_save.setStyle("-fx-background-color: #5ab380;");
        grid_question.add(b_save, 1, 8);

        b_save.setOnAction(e2 -> {
            question.setQuestion(t_main_question.getText());
            options.add(t_option_1.getText());
            options.add(t_option_2.getText());
            options.add(t_option_3.getText());
            options.add(t_option_4.getText());
            question.setOptions(options);
            question.setAnswer(t_correctAns.getValue());
            question.setQuestionChapter(t_chapter.getText());
            question.setQuestionDifficulty(t_difficulty.getValue());
        });
        return grid_question;
    }

    // insert Students to Quiz
    public void insertStudentsToQuiz(String operation) {
        Stage stage = new Stage();
        BorderPane main_pane = new BorderPane();
        main_pane.setPadding(new Insets(10, 10, 10, 10));

        //top grid
        GridPane g_insert = new GridPane();
        g_insert.setVgap(5);
        g_insert.setHgap(5);
        main_pane.setTop(g_insert);

        // Text insert
        TextField t_insert = new TextField();
        g_insert.add(t_insert, 0, 0);

        // view message
        Text t_message = new Text("Enter Student ID.");
        t_message.setFont(Font.font("", FontWeight.BOLD, 16));
        t_message.setFill(Color.rgb(61, 61, 61));
        g_insert.add(t_message, 0, 1);

        // View Student
        GridPane g_studentList = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        main_pane.setCenter(scrollPane);
        scrollPane.setContent(g_studentList);

        g_studentList.setVgap(5);
        g_studentList.setHgap(5);
        g_studentList.setPadding(new Insets(10, 10, 10, 10));


        if (operation.equals("NEW QUIZ")) {
            for (int i = 0; i < newActiveQuiz.getstudentList().size(); i++) {
                Text t_ViewStu = new Text(newActiveQuiz.getstudentList().get(i).getUsername());
                t_ViewStu.setFont(Font.font(16));
                t_ViewStu.setFill(Color.rgb(61, 61, 61));
                g_studentList.add(t_ViewStu, 0, i);
            }
        } else {
            for (int i = 0; i < EditQuiz.getstudentList().size(); i++) {
                Text t_ViewStu = new Text(EditQuiz.getstudentList().get(i).getUsername());
                t_ViewStu.setFont(Font.font(16));
                t_ViewStu.setFill(Color.rgb(61, 61, 61));
                g_studentList.add(t_ViewStu, 0, i);
            }
        }


        // inset Button
        Button b_insert = new Button("Insert");
        g_insert.add(b_insert, 0, 2);
        b_insert.setTextFill(Color.WHITE);
        b_insert.setMinWidth(475);
        b_insert.setStyle("-fx-background-color: #5ab380;");
        b_insert.setFont(Font.font("", FontWeight.BOLD, 16));

        b_insert.setOnAction(e -> {

            if (usernameIsDigit(t_insert.getText())) {
                if (isStudent(t_insert.getText())) {
                    if (operation.equals("NEW QUIZ")) {
                        newActiveQuiz.studentList.add(getStudent(t_insert.getText()));

                        for (int i = 0; i < newActiveQuiz.getstudentList().size(); i++) {
                            Text t_ViewStu = new Text(newActiveQuiz.getstudentList().get(i).getUsername());
                            t_ViewStu.setFont(Font.font(16));
                            t_ViewStu.setFill(Color.rgb(61, 61, 61));
                            g_studentList.add(t_ViewStu, 0, i);
                        }
                    } else {
                        EditQuiz.studentList.add(getStudent(t_insert.getText()));

                        for (int i = 0; i < EditQuiz.getstudentList().size(); i++) {
                            Text t_ViewStu = new Text(EditQuiz.getstudentList().get(i).getUsername());
                            t_ViewStu.setFont(Font.font(16));
                            t_ViewStu.setFill(Color.rgb(61, 61, 61));
                            g_studentList.add(t_ViewStu, 0, i);
                        }
                    }

                    t_message.setText(t_insert.getText() + " is Added.");
                    t_message.setFill(Color.rgb(90, 179, 128));
                    //view


                } else {
                    t_message.setText("User is not a student");
                    t_message.setFill(Color.rgb(205, 56, 92));
                }
            } else {
                t_message.setText("ID should be a Digit");
                t_message.setFill(Color.rgb(205, 56, 92));
            }

        });


        Scene scene = new Scene(main_pane, 500, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign In");
        stage.show();
    }

    // get Instructor Active Quizzes
    public void getInstructorActiveQuizzes(Instructor user) {
        ArrayList<Quiz> quizzes_list = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            if (quiz.getInstructorUsername().equals(user.getUsername())) {
                quizzes_list.add(quiz);
            }
        }
        InstructorActiveQuizzes = quizzes_list;
    }

    //////////////////// Save Data ////////////////////

    public static void ReadData() {
        try {
            ObjectInputStream in_users = new ObjectInputStream(new FileInputStream("users.dat"));
            users = (ArrayList<User>) in_users.readObject();
            in_users.close();
        } catch (Exception e) {
            try {
                new FileOutputStream("users.dat");  //Create new file if the does not exist
            } catch (IOException ex) {
                System.out.println(e);
            }
            System.out.println(e);
        }

        try {
            ObjectInputStream in_quizzes = new ObjectInputStream(new FileInputStream("quizzes.dat"));
            quizzes = (ArrayList<Quiz>) in_quizzes.readObject();
            in_quizzes.close();
        } catch (Exception e) {
            try {
                new FileOutputStream("quizzes.dat");  //Create new file if the does not exist
            } catch (IOException ex) {
                System.out.println(e);
            }
            System.out.println(e);
        }

        try {
            ObjectInputStream in_QuizzesIDList = new ObjectInputStream(new FileInputStream("QuizzesIDList.dat"));
            QuizzesIDList = (ArrayList<Integer>) in_QuizzesIDList.readObject();
            in_QuizzesIDList.close();
        } catch (Exception e) {
            try {
                new FileOutputStream("QuizzesIDList.dat");  //Create new file if the does not exist
            } catch (IOException ex) {
                System.out.println(e);
            }
            System.out.println(e);
        }

    }
    private void saveData() {
        try {
            ObjectOutputStream out_users = new ObjectOutputStream(new FileOutputStream("users.dat"));
            out_users.writeObject(users);
            out_users.close();

            ObjectOutputStream out_quizzes = new ObjectOutputStream(new FileOutputStream("quizzes.dat"));
            out_quizzes.writeObject(quizzes);
            out_quizzes.close();

            ObjectOutputStream out_quizID = new ObjectOutputStream(new FileOutputStream("QuizzesIDList.dat"));
            out_quizID.writeObject(QuizzesIDList);
            out_quizID.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
