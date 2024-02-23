import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VolunteerVictimGUI extends JFrame {

    private NameManager nameManager;
    private JComboBox<String> volunteerDropdown;
    private JLabel selectedVolunteerLabel; // Displays the selected volunteer's name
    private JButton incrementVolunteerScoreButton; // Button to increment volunteer score
    private JLabel selectedVictimLabel; //  Displays the selected victim's name
    private JButton incrementVictimScoreButton; // Button to increment victim score
    private JButton pickRandomVictimButton; // Button to pick a random victim
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
        topPanel.add(new JLabel("Volunteers:"));
        topPanel.add(volunteerDropdown);
        topPanel.add(selectedVolunteerLabel);
        topPanel.add(incrementVolunteerScoreButton);

        // Middle Section - Victim Selection and Score Increment
        JPanel middlePanel = new JPanel(new FlowLayout());
        pickRandomVictimButton = new JButton("Pick Random Victim");
        selectedVictimLabel = new JLabel(" "); //Label to display the chosen victim's name
        incrementVictimScoreButton = new JButton("+1");
        incrementVictimScoreButton.setEnabled(false); // Initially disabled
        middlePanel.add(pickRandomVictimButton);
        middlePanel.add(selectedVictimLabel); // Add label to panel
        middlePanel.add(incrementVictimScoreButton);

        // Bottom Section - Leaderboard
        String[] columnNames = {"Name", "Score"};
        leaderboardModel = new DefaultTableModel(columnNames, 0);
        leaderboard = new JTable(leaderboardModel);
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboard);
        updateLeaderboard();

        // Adding sections to the frame
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER); // Adjust to add middlePanel
        add(leaderboardScrollPane, BorderLayout.SOUTH);

        // Event Listeners
        volunteerDropdown.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            selectedVolunteerLabel.setText(selectedVolunteer); // Display the selected volunteer's name
        });

        incrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
                nameManager.updateScore(selectedVolunteer, 2);
                updateLeaderboard();
            }
        });

        pickRandomVictimButton.addActionListener((ActionEvent e) -> {
            currentVictim = nameManager.getRandomVictim();
            if (currentVictim != null) {
                selectedVictimLabel.setText(currentVictim); // Display the chosen victim's name
                incrementVictimScoreButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "All victims have been picked.");
            }
        });

        incrementVictimScoreButton.addActionListener((ActionEvent e) -> {
            if (currentVictim != null && !currentVictim.isBlank()) {
                nameManager.updateScore(currentVictim, 1);
                updateLeaderboard();
                incrementVictimScoreButton.setEnabled(false); // Optionally disable after increment
                currentVictim = ""; // Reset current victim to ensure re-selection is required for more points
            }
        });
    }

    private void updateLeaderboard() {
        leaderboardModel.setRowCount(0);
        nameManager.getScores().forEach((name, score) -> leaderboardModel.addRow(new Object[]{name, score}));
    }

    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana", "Edward");
        SwingUtilities.invokeLater(() -> new VolunteerVictimGUI(names).setVisible(true));
    }
}
