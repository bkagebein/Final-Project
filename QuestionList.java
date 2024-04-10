import java.io.*;
import java.util.*;

public class QuestionList {
    private List<Question> listOfQuestions;

    public QuestionList() {
        listOfQuestions = new ArrayList<>();
    }

    public void importQuestionsFromFile(String fileName) {
        File questionFile = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(questionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addQuestion(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
            e.printStackTrace();
        }
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
