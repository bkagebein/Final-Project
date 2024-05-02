import java.util.*;
import java.util.stream.Collectors;

public class VictimManager {
    // List to store all possible names.
    private List<Victim> allNames;
    // Set to store unique names of victims already chosen.
    private Set<Victim> pickedVictims;


    // Constructor initializes the Victim Manager with a list of Victims.
    public VictimManager(List<Victim> names) {
        this.allNames = new ArrayList<>(names); // Initialize with provided victims.
        this.pickedVictims = new HashSet<>(); // Initialize empty set for victims.

        // Set initial score of 0 for each name.
    }

    public Victim getRandomVictim() {
        // Create a list of people not yet picked as victims.
        List<Victim> possibleChoices = new ArrayList<>(allNames);
        possibleChoices.removeAll(pickedVictims); // Remove already picked victims.


        // If no names are left, return null.
        if (possibleChoices.isEmpty()) {
            return null;
        }

        // Randomly shuffle the list of possible choices.
        Collections.shuffle(possibleChoices);
        // Pick the first name in the shuffled list.
        Victim pickedName = possibleChoices.get(0);
        if(pickedName.isAbsent()){

            while(pickedName.isAbsent()) {
                possibleChoices.remove(0);
                Collections.shuffle(possibleChoices);
                pickedName = possibleChoices.get(0);
            }

        }
        // Add this name to the set of picked victims to avoid repetition.
        pickedVictims.add(pickedName);
        return pickedName;
    }

    public void resetList() {
        // Clear the set of picked victims.
        pickedVictims.clear();


    }

    public void newDay(){
        for (Victim allName : allNames) {
            allName.setPresent();
        }
    }

    public void resetScores(){
        // Reset each name's score to 0.
        for (Victim allName : allNames) {
            allName.setScore(0);
        }
    }

    public void updateScore(Victim v, int points){
        for (Victim allName : allNames) {
            if (v.getName() == allName.getName()) {
                if (points > 0) {
                    allName.addToScore(points);
                }
                if (points < 0) {
                    allName.removeFrmScore((points * -1));
                }
            }
        }
    }

    public void addAbsence(Victim v){
        for (Victim allName : allNames) {
            if (v.getName() == allName.getName()) {
                allName.incrementAbsent();
            }
        }
    }
    public List<Victim> getAllVictims() {
        return allNames;
    }

    public List<String> getAllNames()  {
        ArrayList<String> names= new ArrayList();;
        for (Victim allName : allNames) {
            names.add(allName.getName());
        }
        return names;
    }

    public List<Integer> getAllScores(){
        ArrayList<Integer> scores= new ArrayList();
        for (Victim allName : allNames) {
            scores.add(allName.getScore());
        }
        return scores;
    }


    public List<Integer> getAllnumAbsences(){
        ArrayList<Integer> absences= new ArrayList();
        for (Victim allName : allNames) {
            absences.add(allName.getNumAbsent());
        }
        return absences;
    }


    public List<Victim> getAbsentVictims() {
        ArrayList<Victim> Absentees= new ArrayList();
        for (Victim allName : allNames) {
            if (allName.isAbsent()) {
                Absentees.add(allName);
            }
        }
        return Absentees;
    }
    public void manuPickVictim(String name){
        for (int i =0;i<allNames.size();i++){
            if (name == allNames.get(i).getName()) {
               pickedVictims.add(allNames.get(i));
            }
        }
    }
    public void addVictim(String name){
        Victim v = new Victim(name);
        allNames.add(v);
    }


}
