import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VolunteerVictimGUI extends JFrame {

    private int questionCounter = -1;
    private ArrayList<String> questions;
    public ArrayList<Victim> victims;
    private NameManager nameManager;
    private JComboBox<String> volunteerDropdown;
    private JLabel selectedVolunteerLabel; // Displays the selected volunteer's name
    private JButton incrementVolunteerScoreButton; // Button to increment volunteer score
    private JButton decrementVolunteerScoreButton; // Button to decrement volunteer score
    private JTextField numberOfVictimsField;
    private JLabel numberOfVictimsLabel;
    private JList<String> selectedVictimsList; // To display multiple victims
    private DefaultListModel<String> selectedVictimsModel; // Model to manage victims in JList
    private DefaultListModel<String> questionPanelModel; // model to manage questions in Jlist
    private JButton addButton; // Button to add victims
    private JButton removeButton; // Button to remove selected victim


    private JButton nextQuestion;
    private JButton prevQuestion;

    private JLabel selectedVictimLabel; //  Displays the selected victim's name
    private JButton incrementVictimScoreButton; // Button to increment victim score
    private JButton decrementVictimScoreButton; //Button to decrement victim score
    private JButton countAbsent; // Button to count absent
    private JButton pickRandomVictimButton; // Button to pick a random victim
    private JButton clearScores; // Button to clear scores
    private JTable leaderboard;
    private DefaultTableModel leaderboardModel;
    private String currentVictim; // Tracks the currently selected victim

    public VolunteerVictimGUI(ArrayList<Victim> names) {
        this.victims = names;
        nameManager = new NameManager(victims);
        setTitle("Volunteer & Victim Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call the method to write victims to a file
                updateFile(victims);
            }
        });

        //Question panel
        JLabel questionLabel = new JLabel("Loading Questions",JLabel.CENTER);
        questionLabel.setPreferredSize(new Dimension(1, 1));
        questionLabel.setFont(new Font("Serif", Font.BOLD, 69));
        System.out.println(new File(".").getAbsolutePath());
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        String fileName = "Question List.txt";
        this.questions = importQuestionsFromFile(fileName);


        // Top Section - Volunteer Dropdown and Display
        JPanel topPanel = new JPanel(new FlowLayout());
        volunteerDropdown = new JComboBox<>(nameManager.getAllNames().toArray(new String[0]));
        selectedVolunteerLabel = new JLabel(" "); // Initially empty
        incrementVolunteerScoreButton = new JButton("+2");
        decrementVolunteerScoreButton = new JButton("-2");
        topPanel.add(new JLabel("Volunteers:"));
        topPanel.add(volunteerDropdown);
        topPanel.add(selectedVolunteerLabel);
        topPanel.add(incrementVolunteerScoreButton);
        topPanel.add(decrementVolunteerScoreButton);

        // Middle Section - Victim Selection and Score Increment
        JPanel middlePanel = new JPanel(new FlowLayout());
        pickRandomVictimButton = new JButton("Pick Random Victim");
        numberOfVictimsField = new JTextField(5);
        numberOfVictimsLabel = new JLabel("Enter Number of Victims:");
        selectedVictimsModel = new DefaultListModel<>();
        selectedVictimsList = new JList<>(selectedVictimsModel); // Initialize JList with the model
        //Question panel
        questionPanelModel = new DefaultListModel<>();
        JScrollPane victimsScrollPane = new JScrollPane(selectedVictimsList); // Add scroll pane for the list
        victimsScrollPane.setPreferredSize(new Dimension(200, 100)); // Set preferred size for the scroll pane
        addButton = new JButton("Add Victim");
        removeButton = new JButton("Remove Victim");


        nextQuestion = new JButton("Next Question");
        prevQuestion = new JButton("Prev. Question");
        prevQuestion.setEnabled(false);

        incrementVictimScoreButton = new JButton("+1");
        decrementVictimScoreButton = new JButton("-1");
        countAbsent = new JButton("Mark Absent");
        middlePanel.add(numberOfVictimsLabel);
        middlePanel.add(numberOfVictimsField);
        middlePanel.add(pickRandomVictimButton);
        middlePanel.add(victimsScrollPane); // Add the scroll pane containing the list
        middlePanel.add(addButton);
        middlePanel.add(removeButton);
        middlePanel.add(incrementVictimScoreButton);
        middlePanel.add(decrementVictimScoreButton);
        middlePanel.add(countAbsent);
        middlePanel.add(questionLabel);
        middlePanel.add(nextQuestion);
        middlePanel.add(prevQuestion);
        //middlePanel.add(questionsList);
        // Bottom Section - Leaderboard
        String[] columnNames = {"Name", "Score", "Absences"};  // Added "Absences" column
        leaderboardModel = new DefaultTableModel(columnNames, 0);
        leaderboard = new JTable(leaderboardModel);
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboard);
        updateLeaderboard();

        //Creating timer panel
        Timer timerPanel = new Timer();

        //TESTING
        JPanel clearPanel = new JPanel(new FlowLayout());
        clearScores = new JButton("Clear Scores");
        clearPanel.add(clearScores);

        //organizing TOP - volunteers and victims grouped
        JPanel topGroup = new JPanel(new FlowLayout());
        JLabel spacing = new JLabel("                   ");
        topGroup.add(topPanel,BorderLayout.NORTH);
        topGroup.add(spacing,BorderLayout.CENTER);
        topGroup.add(middlePanel,BorderLayout.SOUTH);

        //organizing MID - timer and clearScores grouped
        JPanel midGroup = new JPanel(new BorderLayout());
        midGroup.add(timerPanel,BorderLayout.NORTH);
        midGroup.add(clearPanel, BorderLayout.SOUTH);
        midGroup.add(questionLabel, BorderLayout.CENTER);
        midGroup.add(nextQuestion, BorderLayout.EAST);
        midGroup.add(prevQuestion, BorderLayout.WEST);
        //midGroup.add(questionsList, BorderLayout.CENTER);

        //organizing BOTTOM - board and clearScores grouped
        JPanel bottomGroup = new JPanel(new BorderLayout());
        bottomGroup.add(leaderboardScrollPane, BorderLayout.SOUTH);

        // Adding sections to the frame
        add(topGroup,BorderLayout.NORTH);
        add(midGroup,BorderLayout.CENTER);
        add(bottomGroup, BorderLayout.SOUTH);

        // Event Listeners
        nextQuestion.addActionListener((ActionEvent e) -> {
            if (this.questionCounter + 1 < questions.size()) {
                this.questionCounter = questionCounter + 1;
                questionLabel.setText("<html>" + questions.get(this.questionCounter) + "</html>");
                if (questionCounter > 0 ) {
                    prevQuestion.setEnabled(true);
                }
                if (this.questionCounter + 1 >= questions.size()) {
                    nextQuestion.setEnabled(false);
                    prevQuestion.setEnabled(true);
                }
            } else {
                nextQuestion.setEnabled(false);

            }
        });

        prevQuestion.addActionListener((ActionEvent e) -> {
            if (this.questionCounter - 1 >= 0) {
                this.questionCounter = questionCounter - 1;
                questionLabel.setText("<html>" + questions.get(this.questionCounter) + "</html>");
                if (questionCounter < questions.size() ) {
                    nextQuestion.setEnabled(true);
                }
                if (this.questionCounter - 1 < 0) {

                    prevQuestion.setEnabled(false);
                }
            } else {
                prevQuestion.setEnabled(false);
                nextQuestion.setEnabled(true);
            }
        });

        clearScores.addActionListener((ActionEvent e) -> {
            nameManager.resetScores(victims);
            updateLeaderboard();
        });

        volunteerDropdown.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            selectedVolunteerLabel.setText(selectedVolunteer); // Display the selected volunteer's name
            //appends volunteers to picked victim list so they wont be randomly selected!
            this.nameManager.appendName(selectedVolunteer);
        });

        incrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
                nameManager.updateScore(victims, selectedVolunteer, 2);
                updateLeaderboard();
            }
        });

        decrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
                nameManager.updateScore(victims, selectedVolunteer, -2);
                updateLeaderboard();
            }
        });

        pickRandomVictimButton.addActionListener((ActionEvent e) -> {
            selectedVictimsModel.clear();
            int numberOfVictims;
            try{
                numberOfVictims = Integer.parseInt(numberOfVictimsField.getText());
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Enter a valid number of victims.");
                return;
            }
            for(int i = 0; i < numberOfVictims; i++){
                currentVictim = nameManager.getRandomVictim(); // Assuming this method picks a random victim
                if (currentVictim != null) {
                    selectedVictimsModel.addElement(currentVictim); // Add the picked victim to the JList
                    incrementVictimScoreButton.setEnabled(true);
                    decrementVictimScoreButton.setEnabled(true);
                    countAbsent.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "All victims have been picked.");
                    // Resets list of picked victims to empty
                    nameManager.resetList();
                }
            }
        });


        incrementVictimScoreButton.addActionListener((ActionEvent e) -> {
            List<String> selectedVictims = selectedVictimsList.getSelectedValuesList();
            for (String victim : selectedVictims) {
                nameManager.updateScore(victims, victim, 1);
            }
            updateLeaderboard();
        });

        decrementVictimScoreButton.addActionListener((ActionEvent e) -> {
            List<String> selectedVictims = selectedVictimsList.getSelectedValuesList();
            for (String victim : selectedVictims) {
                nameManager.updateScore(victims, victim, -1);
            }
            updateLeaderboard();
        });

        addButton.addActionListener((ActionEvent e) -> {
            String victim = JOptionPane.showInputDialog(this, "Enter Victim's Name:");
            if (victim != null && !victim.trim().isEmpty()) {
                selectedVictimsModel.addElement(victim.trim());
            }
        });

        removeButton.addActionListener((ActionEvent e) -> {
            int selectedIdx = selectedVictimsList.getSelectedIndex();
            if (selectedIdx != -1) {
                selectedVictimsModel.removeElementAt(selectedIdx);
            }
        });


        countAbsent.addActionListener((ActionEvent e) -> {
            List<String> selectedVictims = selectedVictimsList.getSelectedValuesList();
            for (String victim : selectedVictims) {
                nameManager.incrementAbsence(victims, victim);  // Increment absences
            }
            updateLeaderboard();
            selectedVictimsList.clearSelection();  // Clear selection
        });


    }

    private void updateLeaderboard() {
        leaderboardModel.setRowCount(0);
        nameManager.getScores().forEach((name, score) -> {
            Integer absences = nameManager.getAbsences().getOrDefault(name, 0);  // Get absences
            leaderboardModel.addRow(new Object[]{name, score, absences});  // Add absences to the row
        });
    }

    public static void main(String[] args) {
        ArrayList<Victim> victimsTemp = null;

        try {
            victimsTemp = ReadVictimsFromFile.readVictimsFromFile("Names.txt");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not read victims from the file.", "Error", JOptionPane.ERROR_MESSAGE);
            victimsTemp = new ArrayList<>(); // Provide a default empty list if there's an error
        }

        // Declare a final variable for use in the lambda
        final ArrayList<Victim> victims = victimsTemp;

        // Now we can safely pass victims to the GUI
        SwingUtilities.invokeLater(() -> new VolunteerVictimGUI(victims).setVisible(true));
    }





    public static ArrayList<String> importQuestionsFromFile(String fileName) {
        ArrayList<String> questions = new ArrayList<>();
        try (Scanner fileScan = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            while (fileScan.hasNextLine()) {
                questions.add(fileScan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void updateFile(ArrayList<Victim> victims){
        ReadVictimsFromFile.writeVictimsToFile(victims, "Names.txt");
    }
}
