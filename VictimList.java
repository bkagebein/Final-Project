// OLDVolunteerVictimGUI.java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
public class VictimList {
    //Master list of names to be selected from.
    List<Victim> listOfNames;
    //List of Students that are going to be picked from
    private List<String> allNames;
    // Set of names of the victims that have already been picked
    private Set<String> pickedVictims;

    //List of all students who are absent
    List<String> absent;
    //File where the names come from.
    File file;

    public VictimList() {
        this.listOfNames = new ArrayList<Victim>();
        this.absent = new ArrayList<String>();
        this.allNames = new ArrayList<>();
        this.pickedVictims = new HashSet<>();
        file = new File("Names.txt");
        try {
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    //Reads the names from the file, creates Victim objects based on the names
    //Stores the created victims in the listOfNames
    public void importNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listOfNames.add(createVictim(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
            e.printStackTrace();
        }
    }

    //This method adds a name to the file
    //Then adds the new name to the arrayList of names.
    public void addNametoFile(String name) {
        try (FileWriter writer = new FileWriter(file, true);) {
            writer.write(name);
            System.out.println("Data appended to the file. ");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        //Add name to the arrayList.
        this.listOfNames.add(createVictim(name));
    }

    //Removes a name from the file
    //Also removes the victim from the ArrayList.
    public void removeNamefromFile(Victim name) {
        //This looks really weird, but essentially it goes through the original file
        //Reads the names and writes those names to the dummy file,while searching for a specific name to remove.
        //Once it finds the name, it doesn't write the selected name to a file, and continues reading the file.
        //It then saves the temp file as the names file.
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(name.getName())) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
            System.out.println("Line Removed from file. ");
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }

        Path source = Paths.get("temp.txt");
        Path target = Paths.get(file.getName());

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File updated Successfully."); //This line can be removed if needed.
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
        this.listOfNames.remove(name);
    }

    //Adds a victim to the absent ArrayList.
    //Also removes them from the master list. (This can be changed if need be)
    public void updateAbsent(Victim vic) {
        if (vic.getIsAbsent()) {
            absent.add(vic.getName());
            listOfNames.remove(vic);
        } else {
            vic.setIsAbsent(true);
            absent.add(vic.getName());
            listOfNames.remove(vic);
        }
    }

    public String victimPicker() {
        List<String> possibleChoices = new ArrayList<>(this.allNames);
        possibleChoices.removeAll(this.pickedVictims);
        possibleChoices.removeAll(this.absent);
        if (possibleChoices.isEmpty()) {
            return null;
        } else {
            Collections.shuffle(possibleChoices);
            String pickedName = (String)possibleChoices.get(0);
            this.pickedVictims.add(pickedName);
            return pickedName;
        }
    }
    //Once all victims have been picked you can call this to reset the Victims
    public void resetList() {
        this.pickedVictims.clear();
        this.absent.clear();
    }

    //Displays all the elements of the ArrayList.
    public void displayVictims() {
        for (Victim vic : listOfNames) {
            System.out.println(vic.getName());
        }
    }

    //Displays all the elements of the absent ArrayList.
    public void displayAbsent() {
        for (String vic : absent) {
            System.out.println(vic);
        }
    }

    //Creates a victim object.
    public Victim createVictim(String name) {
        Victim vic = new Victim(name);
        return vic;
    }

    // Sets all the names into an array list of Strings
    public void setAllNames(){
        for (int i = 0; i < this.listOfNames.size(); i++) {
            this.allNames.add(this.listOfNames.get(i).name);
        }
    }


}
