package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class All_Scoreboards {


    public static HashMap<String, Scoreboard> All_Scores = new HashMap<>();

    public static boolean CheckRecord(String selected_size, String selected_difficulty, int winScore){
        String data_key = selected_size + "_" + selected_difficulty;
        if (All_Scores.containsKey(data_key)) {
            return All_Scores.get(data_key).CheckRecord(winScore);
        }
        else{
            Scoreboard newScoreboard = new Scoreboard();
            All_Scores.put(data_key, newScoreboard);
            return true;
        }

    }

    public static void addScoreEntry(String selected_size, String selected_difficulty, int winScore, String name_of_record) {
        String data_key = selected_size + "_" + selected_difficulty;
        ScoreEntry newEntry = new ScoreEntry(selected_size, selected_difficulty, winScore, name_of_record);
        All_Scores.get(data_key).addScoreEntry(newEntry);
    }

    public static ArrayList<ScoreEntry> getScores(String selected_size, String selected_difficulty) {
        String data_key = selected_size + "_" + selected_difficulty;
        return All_Scores.get(data_key).getScores();
    }


} // end of class

