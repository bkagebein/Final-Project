import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ReadVictimsFromFile {
    static public int numberOfVictims = 0;
    //private static final String fileName = "studentinfo.txt";
    private static final String filePath = "Names.txt";


    public static ArrayList<Victim> readVictimsFromFile(String file_name) throws IOException {
        ArrayList<Victim> victims = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        do {
            String[] data = line.split(",");

            String name = data[0];

            int score = Integer.parseInt(data[1]);
            int numAbsent = Integer.parseInt(data[2]);
            int numberOfPicks = Integer.parseInt(data[3]);

            Victim victim = new Victim(name, score, numAbsent, numberOfPicks);
            victim.setScore(score);
            victim.setNumAbsent(numAbsent);
            victim.setNumPicked(numberOfPicks);
            line = reader.readLine();
            victims.add(victim);

            numberOfVictims++;
        } while (line != null);

        reader.close();
        return victims;
    }

    public static void writeVictimsToFile(ArrayList<Victim> victims, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Victim victim : victims) {
                String victimLine = String.format("%s,%d,%d,%d",
                        victim.name,
                        victim.score,
                        victim.numAbsent,
                        victim.numPicked);

                writer.write(victimLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public static int getNumberOfVictims() {
        return numberOfVictims;
    }
}
