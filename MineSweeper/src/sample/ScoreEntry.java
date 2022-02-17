package sample;

public class ScoreEntry {
    private static int counter = 1;
    private int id;
    private String size;
    private String difficulty;
    private int Time;
    private String Name;

    public ScoreEntry(String size, String difficulty, int Time, String Name){
        id = counter;
        counter++;
        this.size = size;
        this.difficulty = difficulty;
        this.Time = Time;
        this.Name = Name;
    }

    public int getId() {
        return id;
    }
    public String getSize() {
        return size;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public int getTime() {
        return Time;
    }
    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "ScoreEntry{" +
                "id=" + id +
                ", size=" + size +
                ", difficulty=" + difficulty +
                ", Time=" + Time +
                '}';
    }

} // end of class
