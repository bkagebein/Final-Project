import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuestionList  {
    public  List<Question> listOfQuestions;

    String filename = "Question List.txt";

    public ArrayList<String> importQuestionsFromFile(String fileName) {
        ArrayList<String> questions = new ArrayList<>();
        Scanner fileScan;
        try
        {
            fileScan = new Scanner(new File(fileName), StandardCharsets.UTF_8);
        } catch (IOException e)
        {
            return questions;
        }

        while (fileScan.hasNext())
        {
            questions.add(fileScan.nextLine());
        }
        return questions;

    }

    public void addQuestion(String questionText) {
        Question question = new Question(questionText);
        listOfQuestions.add(question);
    }

    public void removeQuestion(Question question) {
        listOfQuestions.remove(question);
    }

    public Question getQuestion(int index) {
        if (index >= 0 && index < listOfQuestions.size()) {
            return listOfQuestions.get(index);
        } else {
            return null;
        }
    }

    public void displayQuestions() {
        for (Question question : listOfQuestions) {
            System.out.println(question.getQuestionText());
        }
    }

    public void updateQuestion(int index, String newQuestionText) {
        if (index >= 0 && index < listOfQuestions.size()) {
            Question question = listOfQuestions.get(index);
            question.setQuestionText(newQuestionText);
        }
    }

    public void clearQuestions() {
        listOfQuestions.clear();
    }

    public void exportQuestionsToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Question question : listOfQuestions) {
                writer.println(question.getQuestionText());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}
