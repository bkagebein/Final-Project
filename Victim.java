public class Victim {
    //File reference string will include name,numAbsent,score,numPicked.
    //Name of the victim
    String name;
    //Tracks the number of times a person is absent.
    int numAbsent;
    //Variable that manages a victims score.
    int score;
    //Tracks the number of times a victim is picked.
    int numPicked;

    public Victim(String name, int numAbsent, int score, int numPicked) {
        this.name = name;
        this.numAbsent = numAbsent;
        this.score = score;
        this.numPicked = numPicked;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumAbsent() {
        return numAbsent;
    }

    public void setNumAbsent(int numAbsent) {
        this.numAbsent = numAbsent;
    }

    public int getNumPicked() {
        return numPicked;
    }

    public void setNumPicked(int numPicked) {
        this.numPicked = numPicked;
    }

    public String toString(){
        return name+"," + String.valueOf(numAbsent) + "," + String.valueOf(score) + "," + String.valueOf(numPicked);
    }
}
