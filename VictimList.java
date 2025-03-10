import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
public class VictimList {
    //Master list of names to be selected from.
    List<Victim> listOfNames;
    //List of all students who are absent
    File file;

    public VictimList() {
        listOfNames = new ArrayList<Victim>();
        file = new File("Names.txt");
        try{
            if(!file.exists()) {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    //Reads the names from the file, creates Victim objects based on the names
    //Stores the created victims in the listOfNames
    public void importNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                String[] tempProcessing = line.split(",");
                int numAbsent = Integer.parseInt(tempProcessing[1]);
                int score = Integer.parseInt(tempProcessing[2]);
                int numPicked = Integer.parseInt(tempProcessing[3]);
                listOfNames.add(createVictim(tempProcessing[0], numAbsent, score, numPicked));
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
    public void addNametoFile(Victim vic) {
        try (FileWriter writer = new FileWriter(file, true);){
            writer.write(vic.toString() + "\n");
            System.out.println("Data appended to the file. ");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        //Add name to the arrayList.
        this.listOfNames.add(vic);
    }

    //Removes a name from the file
    //Also removes the victim from the ArrayList.
    public void removeNamefromFile(Victim name) {
        //This looks really weird, but essentially it goes through the original file
        //Reads the names and writes those names to the dummy file,while searching for a specific name to remove.
        //Once it finds the name, it doesn't write the selected name to a file, and continues reading the file.
        //It then saves the temp file as the names file.
        try(BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if(!currentLine.equals(name.toString())) {
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



    //Displays all the elements of the ArrayList.
    public void displayVictims() {
        for(Victim vic : listOfNames) {
            System.out.println(vic.getName());
        }
    }

    //Returns an ArrayList of strings that contain the victim names.
    public List<String> getStringList() {
        List<String> StringList = new ArrayList<String>();
        for(Victim vic : listOfNames) {
            StringList.add(vic.getName());
        }
        return StringList;
    }

    //Creates a victim object.
    public Victim createVictim(String name, int numAbsent, int score, int numPicked) {
        Victim vic = new Victim(name, numAbsent, score, numPicked);
        return vic;
    }

    public Victim getVictim(int index) {
        return this.listOfNames.get(index);
    }

}
