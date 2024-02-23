import java.util.*;

public class NameManager {
    // List to store all possible names.
    private List<String> allNames;
    // Set to store unique names of victims already chosen.
    private Set<String> pickedVictims;
    // Map to store scores associated with each name.
    private Map<String, Integer> scores;

    // Constructor initializes the NameManager with a list of names.
    public NameManager(List<String> names) {
        this.allNames = new ArrayList<>(names); // Initialize with provided names.
        this.pickedVictims = new HashSet<>(); // Initialize empty set for victims.
        this.scores = new HashMap<>(); // Initialize empty map for scores.
        // Set initial score of 0 for each name.
        for (String name : names) {
            scores.put(name, 0);
        }
    }

    // Returns a random victim's name that hasn't been picked yet.
    public String getRandomVictim() {
        // Create a list of names not yet picked as victims.
        List<String> possibleChoices = new ArrayList<>(allNames);
        possibleChoices.removeAll(pickedVictims); // Remove already picked victims.

        // If no names are left, return null.
        if (possibleChoices.isEmpty()) {
            return null;
        }

        // Randomly shuffle the list of possible choices.
        Collections.shuffle(possibleChoices);
        // Pick the first name in the shuffled list.
        String pickedName = possibleChoices.get(0);
        // Add this name to the set of picked victims to avoid repetition.
        pickedVictims.add(pickedName);
        return pickedName;
    }

    // Resets all data, clearing picked victims and setting all scores to 0.
    public void resetData() {
        // Reset each name's score to 0.
        for (String name : scores.keySet()) {
            scores.put(name, 0);
        }
        // Clear the set of picked victims.
        pickedVictims.clear();
    }

    // Updates the score for a given name.
    public void updateScore(String name, int points) {
        // Add the given points to the current score of the name.
        scores.put(name, scores.getOrDefault(name, 0) + points);
    }

    // Returns the list of all names.
    public List<String> getAllNames() {
        return allNames;
    }

    // Returns the map of scores for all names.
    public Map<String, Integer> getScores() {
        return scores;
    }
}
