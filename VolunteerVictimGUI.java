//import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.util.List;
//
//public class OLDVolunteerVictimGUI extends JFrame {
//
//    private OLDNameManager nameManager;
//    private JComboBox<String> volunteerDropdown;
//    private JLabel selectedVolunteerLabel; // Displays the selected volunteer's name
//    private JButton incrementVolunteerScoreButton; // Button to increment volunteer score
//    private JButton decrementVolunteerScoreButton; // Button to decrement volunteer score
//    private JLabel selectedVictimLabel; //  Displays the selected victim's name
//    private JButton incrementVictimScoreButton; // Button to increment victim score
//    private JButton decrementVictimScoreButton; //Button to decrement victim score
//    private JButton countAbsent; // Button to count absent
//    private JButton pickRandomVictimButton; // Button to pick a random victim
//    private JButton clearScores; // Button to clear scores
//    private JTable leaderboard;
//    private DefaultTableModel leaderboardModel;
//    private String currentVictim; // Tracks the currently selected victim
//
//    public OLDVolunteerVictimGUI(List<String> names) {
//        nameManager = new OLDNameManager(names);
//        setTitle("Volunteer & Victim Management");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Top Section - Volunteer Dropdown and Display
//        JPanel topPanel = new JPanel(new FlowLayout());
//        volunteerDropdown = new JComboBox<>(nameManager.getAllNames().toArray(new String[0]));
//        selectedVolunteerLabel = new JLabel(" "); // Initially empty
//        incrementVolunteerScoreButton = new JButton("+2");
//        decrementVolunteerScoreButton = new JButton("-2");
//        topPanel.add(new JLabel("Volunteers:"));
//        topPanel.add(volunteerDropdown);
//        topPanel.add(selectedVolunteerLabel);
//        topPanel.add(incrementVolunteerScoreButton);
//        topPanel.add(decrementVolunteerScoreButton);
//
//        // Middle Section - Victim Selection and Score Increment
//        JPanel middlePanel = new JPanel(new FlowLayout());
//        pickRandomVictimButton = new JButton("Pick Random Victim");
//        selectedVictimLabel = new JLabel(" "); //Label to display the chosen victim's name
//        incrementVictimScoreButton = new JButton("+1");
//        decrementVictimScoreButton = new JButton("-1");
//        countAbsent = new JButton("Mark Absent");
//        incrementVictimScoreButton.setEnabled(false); // Initially disabled
//        decrementVictimScoreButton.setEnabled(false);
//        countAbsent.setEnabled(false);
//        middlePanel.add(pickRandomVictimButton);
//        middlePanel.add(selectedVictimLabel); // Add label to panel
//        middlePanel.add(incrementVictimScoreButton);
//        middlePanel.add(decrementVictimScoreButton);
//        middlePanel.add(countAbsent);
//
//        // Bottom Section - Leaderboard
//        String[] columnNames = {"Name", "Score"};
//        leaderboardModel = new DefaultTableModel(columnNames, 0);
//        leaderboard = new JTable(leaderboardModel);
//        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboard);
//        updateLeaderboard();
//
//        //Creating timer panel
//        OLDTimer timerPanel = new OLDTimer();
//
//        //TESTING
//        JPanel clearPanel = new JPanel(new FlowLayout());
//        clearScores = new JButton("Clear Socres");
//        clearPanel.add(clearScores);
//
//        //organizing TOP - volunteers and victims grouped
//        JPanel topGroup = new JPanel(new FlowLayout());
//        JLabel spacing = new JLabel("                   ");
//        topGroup.add(topPanel,BorderLayout.NORTH);
//        topGroup.add(spacing,BorderLayout.CENTER);
//        topGroup.add(middlePanel,BorderLayout.SOUTH);
//
//        //organizing MID - timer and clearScores grouped
//        JPanel midGroup = new JPanel(new BorderLayout());
//        midGroup.add(timerPanel,BorderLayout.NORTH);
//        midGroup.add(clearPanel, BorderLayout.SOUTH);
//
//        //organizing BOTTOM - board and clearScores grouped
//        JPanel bottomGroup = new JPanel(new BorderLayout());
//        bottomGroup.add(leaderboardScrollPane, BorderLayout.SOUTH);
//
//        // Adding sections to the frame
//        add(topGroup,BorderLayout.NORTH);
//        add(midGroup,BorderLayout.CENTER);
//        add(bottomGroup, BorderLayout.SOUTH);
//
//        // Event Listeners
//        clearScores.addActionListener((ActionEvent e) -> {
//            nameManager.resetScores();
//            updateLeaderboard();
//        });
//
//        volunteerDropdown.addActionListener((ActionEvent e) -> {
//            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
//            selectedVolunteerLabel.setText(selectedVolunteer); // Display the selected volunteer's name
//            //appends volunteers to picked victim list so they wont be randomly selected!
//            this.nameManager.appendName(selectedVolunteer);
//        });
//
//        incrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
//            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
//            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
//                nameManager.updateScore(selectedVolunteer, 2);
//                updateLeaderboard();
//            }
//        });
//
//        decrementVolunteerScoreButton.addActionListener((ActionEvent e) -> {
//            String selectedVolunteer = (String) volunteerDropdown.getSelectedItem();
//            if (selectedVolunteer != null && !selectedVolunteer.trim().isEmpty()) {
//                nameManager.updateScore(selectedVolunteer, -2);
//                updateLeaderboard();
//            }
//        });
//
//        pickRandomVictimButton.addActionListener((ActionEvent e) -> {
//            currentVictim = nameManager.getRandomVictim();
//            if (currentVictim != null) {
//                selectedVictimLabel.setText(currentVictim); // Display the chosen victim's name
//                incrementVictimScoreButton.setEnabled(true);
//                decrementVictimScoreButton.setEnabled(true);
//                countAbsent.setEnabled(true);
//            } else {
//                JOptionPane.showMessageDialog(this, "All victims have been picked.");
//                //Resets list of picked victims to empty
//                nameManager.resetList();
//            }
//        });
//
//        incrementVictimScoreButton.addActionListener((ActionEvent e) -> {
//            if (currentVictim != null && !currentVictim.isBlank()) {
//                nameManager.updateScore(currentVictim, 1);
//                updateLeaderboard();
//                incrementVictimScoreButton.setEnabled(false); // Optionally disable after increment
//                decrementVictimScoreButton.setEnabled(false);
//                countAbsent.setEnabled(false);
//                currentVictim = ""; // Reset current victim to ensure re-selection is required for more points
//            }
//        });
//
//        decrementVictimScoreButton.addActionListener((ActionEvent e) -> {
//            if (currentVictim != null && !currentVictim.isBlank()) {
//                nameManager.updateScore(currentVictim, -1);
//                updateLeaderboard();
//                incrementVictimScoreButton.setEnabled(false); // Optionally disable after increment
//                decrementVictimScoreButton.setEnabled(false);
//                countAbsent.setEnabled(false);
//                currentVictim = ""; // Reset current victim to ensure re-selection is required for more points
//            }
//        });
//
//
//        countAbsent.addActionListener((ActionEvent e) -> {
//            if (currentVictim != null && !currentVictim.isBlank()) {
//                //nameManager.setAbsences(currentVictim);
//                incrementVictimScoreButton.setEnabled(false); // Optionally disable after increment
//                decrementVictimScoreButton.setEnabled(false);
//                countAbsent.setEnabled(false);
//                currentVictim = ""; // Reset current victim to ensure re-selection is required for more points
//            }
//        });
//
//    }
//
//    private void updateLeaderboard() {
//        leaderboardModel.setRowCount(0);
//        nameManager.getScores().forEach((name, score) -> leaderboardModel.addRow(new Object[]{name, score}));
//    }
//
//    public static void main(String[] args) {
//        List<String> names = List.of("Alice", "Bob", "Charlie", "Diana", "Edward");
//        SwingUtilities.invokeLater(() -> new OLDVolunteerVictimGUI(names).setVisible(true));
//    }
//}
