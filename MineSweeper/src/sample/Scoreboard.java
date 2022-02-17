package sample;

import java.util.ArrayList;

public class Scoreboard {

    private  ArrayList<ScoreEntry> Scores = new ArrayList<>();

    public  boolean CheckRecord(int winScore){
        if (Scores.size() < 5) {
            return true;
        }
        for (ScoreEntry entry : Scores){
            if (entry.getTime() >= winScore){
                return true;
            }
        }
        return false;
    }

    public void addScoreEntry(ScoreEntry ScoreEntry) {
        int pos = 0;
        for(int i=0; i<Scores.size(); i++){
            if (Scores.get(i).getTime() < ScoreEntry.getTime()){
                pos +=1;
            }
        }
        Scores.add(pos,ScoreEntry);
        if (Scores.size() >5 ){
            Scores.remove(5);
        }

    }

    public ArrayList<ScoreEntry> getScores() {
        return Scores;
    }

} // end of class
