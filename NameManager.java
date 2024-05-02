import java.util.*;
import java.util.stream.Collectors;

public class NameManager {
    // List to store all possible names.
    private List<String> allNames;
    // Set to store unique names of victims already chosen.
    private Set<String> pickedVictims;
    // Map to store scores associated with each name.
    private Map<String, Integer> absences; //Map to store absences (names will be removed from allNames)
    private Map<String, Integer> scores;

    // Constructor initializes the NameManager with a list of names.
    public NameManager(List<String> names) {
        this.allNames = new ArrayList<>(names); // Initialize with provided names.
        this.pickedVictims = new HashSet<>(); // Initialize empty set for victims.
        this.scores = new HashMap<>(); // Initialize empty map for scores.
        this.absences = new HashMap<>();
        // Set initial score of 0 for each name.
        for (String name : names) {
            scores.put(name, 0);
            absences.put(name, 0);  // Initialize absences
        }
    }

    // Returns a random victim's name that hasn't been picked yet.
    public String getRandomVictim() {
        // Create a list of names not yet picked as victims.
        List<String> possibleChoices = new ArrayList<>(allNames);
        possibleChoices.removeAll(pickedVictims); // Remove already picked victims.
        possibleChoices.removeAll(getAbsentNames());  // Remove absent names

        // If no names are left, return null.
        if (possibleChoices.isEmpty()) {
            pickedVictims.clear();
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

    // Resets data, clearing picked victims
    public void resetList() {
        // Clear the set of picked victims.
        pickedVictims.clear();
        absences.clear();
    }

    // Resets data, settings all scores to 0
    public void resetScores(){
        // Reset each name's score to 0.
        for (String name : scores.keySet()) {
            scores.put(name, 0);
        }
    }

    // Updates the score for a given name.
    public void updateScore(String name, int points) {
        // if points result in score going below 0, don't add points
        if (scores.get(name) <= 0 && points < 0) {
            scores.put(name, 0);
        } else if (scores.get(name) + points < 0) {
            scores.put(name, 0);
        } else {
            scores.put(name, scores.getOrDefault(name, 0) + points);
        }
    }

    public void incrementAbsence(String name) {
        absences.put(name, absences.getOrDefault(name, 0) + 1);  // Increment absence
    }

    public List<String> getAllNames() {
        return allNames;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public Map<String, Integer> getAbsences() {
        return absences;  // Get absences
    }

    public void appendName(String name) {
        pickedVictims.add(name);
    }

    public List<String> getAbsentNames() {
        return absences.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
