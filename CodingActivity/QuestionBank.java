import java.util.*;
import java.io.*;

class Logger{
    private static Logger logger;
    private PrintWriter writer;

    Logger(String fileName){
        try {
            FileWriter fw = new FileWriter(fileName,true);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {}
    }
    public static synchronized Logger getLogger(){
        if(logger == null)
            logger = new Logger("oaesLog.log");
        return logger;    
    }
    public void log(String string){
        writer.println(string);
    }
}

abstract class Question{
    String description;
    int marks;
    String taxonomy;
    String question_type;
    String subject;

    abstract void questionType();
    Logger logger = Logger.getLogger();

    void setDescription(String description){
        this.description=description;
        System.out.println("Setting the Question Description");
        logger.log("Setting the Question Description");
    }
    void setMarks(int marks){
        this.marks = marks;
        System.out.println("Setting the Question Marks");
        logger.log("Setting the Question Marks");
    }
    void setSubject(String subject){
        this.subject = subject;
        System.out.println("Setting the Question Subject");
        logger.log("Setting the Question Subject");
    }
    void setTaxonomy(String taxonomy){
        this.taxonomy = taxonomy;
        System.out.println("Setting the Question Taxonomy");
        logger.log("Setting the Question Taxonomy");
    }
}

class MCQ extends Question{
    Logger logger = Logger.getLogger();
    void questionType(){
        question_type = "MCQ";
        System.out.println("Define logic for MCQ option input ");
        logger.log("Define logic for MCQ option input ");
    }
}

class FillInTheBlanks extends Question{
    Logger logger = Logger.getLogger();
    void questionType(){
        question_type = "FillInTheBlanks";
        System.out.println("Define logic for FillInTheBlanks option input ");
        logger.log("Define logic for FillInTheBlanks option input ");
    }
}

class MatchTheFollowing extends Question{
    Logger logger = Logger.getLogger();
    void questionType(){
        question_type = "MatchTheFollowing";
        System.out.println("Define logic for MatchTheFollowing option input ");
        logger.log("Define logic for MatchTheFollowing option input ");
    }
}

class QuestionFactory{
    Logger logger = Logger.getLogger();
    Question createQuestion(String question_type){

        System.out.println("Creating a new question");
        logger.log("Creating a new question");

        Question question=null;
        if(question_type=="MCQ")
            question=new MCQ();
        else if(question_type=="FillInTheBlanks")
            question = new FillInTheBlanks();
        else if(question_type=="MatchTheFollowing")
            question = new MatchTheFollowing();
        
        return question;
    }
}

class QuestionManager{

    Logger logger = Logger.getLogger();

    QuestionFactory qfactory;
    Question question;

    public QuestionManager(){
        qfactory = new QuestionFactory();
    }

    void setAttributes(String question_type, int marks, String subject, String taxonomy, String description){
        question = qfactory.createQuestion(question_type);
        question.setMarks(marks);
        question.setSubject(subject);
        question.setTaxonomy(taxonomy);
        question.setDescription(description);

        question.questionType();
        System.out.println("Question Created - "+description+ " - " +question_type+" - "+subject+" - "+taxonomy+" - "+marks);
        logger.log("Question Created - "+description+ " - " +question_type+" - "+subject+" - "+taxonomy+" - "+marks);
    }
}

public class QuestionBank{
    public static void main(String[] args){

        QuestionManager m = new QuestionManager();
        m.createQuestion("MCQ",5,"Design Patterns","Memory","Q1");
        m.createQuestion("FillInTheBlanks",1,"Data Modelling","Analytical","Q2");
        m.createQuestion("MatchTheFollowing",4,"Software Systems","Memory","Q3");

    }
}