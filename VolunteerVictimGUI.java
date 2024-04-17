import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VolunteerVictimGUI extends JFrame {

    private NameManager nameManager;
    private JComboBox<String> volunteerDropdown;
    private JLabel selectedVolunteerLabel; // Displays the selected volunteer's name
    private JButton incrementVolunteerScoreButton; // Button to increment volunteer score
    private JButton decrementVolunteerScoreButton; // Button to decrement volunteer score
    private JTextField numberOfVictimsField;
    private JLabel numberOfVictimsLabel;
    private JList<String> selectedVictimsList; // To display multiple victims
    private DefaultListModel<String> selectedVictimsModel; // Model to manage victims in JList
    private JButton addButton; // Button to add victims
    private JButton removeButton; // Button to remove selected victim

    private JLabel selectedVictimLabel; //  Displays the selected victim's name
    private JButton incrementVictimScoreButton; // Button to increment victim score
    private JButton decrementVictimScoreButton; //Button to decrement victim score
    private JButton countAbsent; // Button to count absent
    private JButton pickRandomVictimButton; // Button to pick a random victim
    private JButton clearScores; // Button to clear scores
    private JTable leaderboard;
    private DefaultTableModel leaderboardModel;
    private String currentVictim; // Tracks the currently selected victim

    public VolunteerVictimGUI(List<String> names) {
        nameManager = new NameManager(names);
        setTitle("Volunteer & Victim Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
        JScrollPane victimsScrollPane = new JScrollPane(selectedVictimsList); // Add scroll pane for the list
        victimsScrollPane.setPreferredSize(new Dimension(200, 100)); // Set preferred size for the scroll pane
        addButton = new JButton("Add Victim");
        removeButton = new JButton("Remove Victim");

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
        // Bottom Section - Leaderboard
        String[] columnNames = {"Name", "Score"};
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

        //organizing BOTTOM - board and clearScores grouped
        JPanel bottomGroup = new JPanel(new BorderLayout());
        bottomGroup.add(leaderboardScrollPane, BorderLayout.SOUTH);

        // Adding sections to the frame
        add(topGroup,BorderLayout.NORTH);
        add(midGroup,BorderLayout.CENTER);
        add(bottomGroup, BorderLayout.SOUTH);

        // Event Listeners
        clearScores.addActionListener((ActionEvent e) -> {
            nameManager.resetScores();
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
                nameManager.updateScore(selectedVolunteer, 2);
                updateLeaderboard();
            }
        });

        decrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
                nameManager.updateScore(selectedVolunteer, -2);
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
                nameManager.updateScore(victim, 1);
            }
            updateLeaderboard();
        });

        decrementVictimScoreButton.addActionListener((ActionEvent e) -> {
            List<String> selectedVictims = selectedVictimsList.getSelectedValuesList();
            for (String victim : selectedVictims) {
                nameManager.updateScore(victim, -1);
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
                // nameManager.setAbsences(victim); // Uncomment this if needed
            }
            selectedVictimsList.clearSelection();
        });

    }

    private void updateLeaderboard() {
        leaderboardModel.setRowCount(0);
        nameManager.getScores().forEach((name, score) -> leaderboardModel.addRow(new Object[]{name, score}));
    }

    public static void main(String[] args) {
        String fileName = "Names.txt";
        List<String> names = readNamesFromFile(fileName);
        SwingUtilities.invokeLater(() -> new VolunteerVictimGUI(names).setVisible(true));
    }

    public static List<String> readNamesFromFile(String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line.trim()); // Add each line to the list, trimming whitespace
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }
}
