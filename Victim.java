public class Victim {
    //Name of the victim
    String name;
    //Variable that manages the absent system. True if absent.
    Boolean isAbsent;
    //Variable that manages a victims score.
    int score;

    public Victim(String name) {
        this.name = name;
        isAbsent = false;
        score = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAbsent() {
        return this.isAbsent;
    }

    public void setIsAbsent(Boolean absent) {
        this.isAbsent = absent;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
