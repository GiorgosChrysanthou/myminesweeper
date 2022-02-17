package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class MineSweeperGUI extends Application  {

    String selected_size = "small";
    String selected_difficulty = "easy";
    final int S_SizeV=9, S_sizeH = 15, M_SizeV=12, M_sizeH = 20, L_SizeV=25, L_sizeH = 50;
    int SizeV = S_SizeV;
    int SizeH = S_sizeH;
    //for testing: Ea_Bombs=2
    final int Ea_Bombs=10, N_Bombs=16, H_Bombs=22, Ex_Bombs=28;
    int Percentage_Bombs = Ea_Bombs;
    final int btnSize = 25;
    final int top_settings_height = 50;
    boolean[][] Real_Mines;
    private Button[][] btn;
    static int empty_buttons_left;
    static int winScore;

    CountDown countDown;
    private Label time_v;

    private ArrayList<ScoreEntry> Scores;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mine Grid");


        Label spacer1 = new Label();
        Label spacer2 = new Label();
        Label spacer3 = new Label();
        Label spacer4 = new Label();
        spacer1.setPrefSize(5,top_settings_height);
        spacer2.setPrefSize(5,top_settings_height);
        spacer3.setPrefSize(5,top_settings_height);
        spacer4.setPrefSize(5,top_settings_height);


        Label Label_size = new Label("Pick size:");
        Label_size.setPrefHeight(top_settings_height/2);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Small", "Medium", "Large"
                );
        ComboBox comboBox = new ComboBox(options);
        comboBox.setStyle("-fx-font-size: 0.75em;");
        comboBox.setPrefHeight(top_settings_height/2);
        comboBox.getSelectionModel().selectFirst();
        VBox size_settings = new VBox(Label_size,comboBox);
        size_settings.setPrefHeight(top_settings_height);


        Label Label_difficulty = new Label("Pick Difficulty:");
        Label_difficulty.setPrefHeight(top_settings_height/2);
        ObservableList<String> options_2 =
                FXCollections.observableArrayList(
                        "Easy", "Normal", "Hard", "Expert"
                );
        ComboBox comboBox_2 = new ComboBox(options_2);
        comboBox_2.setStyle("-fx-font-size: 0.75em;");
        comboBox_2.setPrefHeight(top_settings_height/2);
        comboBox_2.getSelectionModel().selectFirst();
        VBox difficulty_settings = new VBox(Label_difficulty,comboBox_2);
        difficulty_settings.setPrefHeight(top_settings_height);


        Button new_game_button = new Button("   New\n  Game  ");
        new_game_button.setPrefHeight(top_settings_height);
        //new_game_button.setAlignment(Pos.CENTER);

        Label empty_buttons_left_l = new Label("Empty Buttons Left:");
        empty_buttons_left_l.setPrefHeight(top_settings_height/2);
        Label empty_buttons_left_v = new Label("###");
        empty_buttons_left_v.setPrefHeight(top_settings_height/2);
        VBox empty_buttons_left_labels = new VBox(empty_buttons_left_l,empty_buttons_left_v);
        empty_buttons_left_labels.setPrefHeight(top_settings_height);
        //empty_buttons_left_labels.setAlignment(Pos.CENTER_RIGHT);

        Label time_l = new Label("Time:");
        time_l.setPrefHeight(top_settings_height/2);
        time_v = new Label("###");
        time_v.setPrefHeight(top_settings_height/2);
        VBox time_labels = new VBox(time_l,time_v);
        time_labels.setPrefHeight(top_settings_height);
        countDown = new CountDown(this);

        HBox top_settings = new HBox(size_settings, spacer1, difficulty_settings, spacer2, new_game_button, spacer3, empty_buttons_left_labels, spacer4, time_labels);
        top_settings.setMaxSize(SizeH*btnSize, top_settings_height);
        top_settings.setMaxSize(SizeH*btnSize, top_settings_height);
        top_settings.setPrefSize(SizeH*btnSize, top_settings_height);


        new_game_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                countDown.CountDownReset();
                time_v.setStyle(null);
                if (comboBox.getValue()=="Small"){
                    selected_size="small";
                    SizeV = S_SizeV;
                    SizeH = S_sizeH;
                }
                else if (comboBox.getValue()=="Medium"){
                    selected_size="medium";
                    SizeV = M_SizeV;
                    SizeH = M_sizeH;
                }
                else if (comboBox.getValue()=="Large"){
                    selected_size="large";
                    SizeV = L_SizeV;
                    SizeH = L_sizeH;
                }
                if (comboBox_2.getValue()=="Easy"){
                    selected_difficulty="easy";
                    Percentage_Bombs =  Ea_Bombs;
                }
                else if (comboBox_2.getValue()=="Normal"){
                    selected_difficulty="normal";
                    Percentage_Bombs =  N_Bombs;
                }
                else if (comboBox_2.getValue()=="Hard"){
                    selected_difficulty="hard";
                    Percentage_Bombs =  H_Bombs;
                }
                else if (comboBox_2.getValue()=="Expert"){
                    selected_difficulty="expert";
                    Percentage_Bombs =  Ex_Bombs;
                }
                top_settings.setMaxSize(SizeH*btnSize, top_settings_height);
                top_settings.setMaxSize(SizeH*btnSize, top_settings_height);
                top_settings.setPrefSize(SizeH*btnSize, top_settings_height);
                GridPane MyGrid = Create_Grid(SizeV,SizeH,btnSize,empty_buttons_left_v);
                Changing_Scene(top_settings, MyGrid, btnSize, SizeV, SizeH, top_settings_height, primaryStage, Percentage_Bombs);
                empty_buttons_left_v.setText( ""+ empty_buttons_left );
            }
        });


        GridPane MyGrid = Create_Grid(SizeV,SizeH,btnSize,empty_buttons_left_v);
        Changing_Scene(top_settings, MyGrid, btnSize, SizeV, SizeH, top_settings_height, primaryStage, Percentage_Bombs);
        empty_buttons_left_v.setText( ""+ empty_buttons_left );

    }




    public GridPane Create_Grid(int SizeV,int SizeH,int btnSize,Label empty_buttons_left_v){
        GridPane MyGrid = new GridPane();
        MyGrid.setMaxSize(SizeH*btnSize, SizeV*btnSize);
        MyGrid.setMinSize(SizeH*btnSize, SizeV*btnSize);
        MyGrid.setPrefSize(SizeH*btnSize, SizeV*btnSize);
        btn = new Button[SizeV][SizeH];

        for (int i=0; i<SizeV; i++){
            for (int j=0; j<SizeH; j++){
                btn[i][j] = new Button(i+"."+j);
                btn[i][j].setStyle("-fx-text-fill: rgba(0,0,0,0); ");
                btn[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String[] btn_2d = (((Button)event.getSource()).getText()).split("\\.");
                        int btn_X = Integer.parseInt(btn_2d[0]);
                        int btn_Y = Integer.parseInt(btn_2d[1]);
                        OpenMineBox(btn_X,btn_Y, SizeV, SizeH, btn);
                        empty_buttons_left_v.setText( ""+ empty_buttons_left );
                        if (empty_buttons_left==0){
                            WinGame();
                        }
                    }
                });
                btn[i][j].setMinSize(btnSize,btnSize);
                btn[i][j].setMaxSize(btnSize,btnSize);
                MyGrid.add(btn[i][j], j, i);
            }
        }
        return MyGrid;
    }

    public void Changing_Scene(HBox top_settings, GridPane MyGrid,int btnSize, int SizeV, int SizeH, int top_settings_height, Stage primaryStage, int Percentage_Bombs){
        VBox vbox = new VBox(top_settings,MyGrid);

        Real_Mines = Changing_Real_Mines(SizeV, SizeH, Percentage_Bombs);
        Scene scene = new Scene(vbox, SizeH*btnSize, top_settings_height+ SizeV*btnSize );
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public boolean[][] Changing_Real_Mines(int SizeV, int SizeH, int Percentage_Bombs){
        empty_buttons_left = SizeV*SizeH - (int)(SizeV*SizeH*Percentage_Bombs/100);
        Random rndm = new Random();
        boolean[][] New_Real_Mines = new boolean[SizeV][SizeH];
        int autoRow, autoColumn;
        for (int q=0; q<SizeV; q++){
            for (int w=0; w<SizeH; w++){
                New_Real_Mines[q][w] = false;
            }
        }
        int bombs = (int)(SizeV*SizeH*Percentage_Bombs/100);
        for (int q=0; q<bombs; q++){
            autoRow = rndm.nextInt() % SizeV;
            if (autoRow < 0) { autoRow = autoRow * -1; }
            autoColumn = rndm.nextInt() % SizeH;
            if (autoColumn < 0) {	autoColumn = autoColumn * -1;	}

            if (New_Real_Mines[autoRow][autoColumn]==true){
                q--;
            }
            else{
                New_Real_Mines[autoRow][autoColumn]=true;
            }
        }
        return New_Real_Mines;
    }

    public void OpenMineBox(int row, int column, int SizeV, int SizeH, Button[][] btn){
        if (Real_Mines[row][column]){
            countDown.CountDownStop();
            for (int q=0; q<SizeV; q++){
                for (int w=0; w<SizeH; w++){
                    btn[q][w].setDisable(true);
                    if (Real_Mines[q][w]==true){
                        btn[q][w].setText("#");
                        btn[q][w].setStyle("-fx-background-color: #ff0000");
                    }
                }
            }
            return;
        }
        else{
            empty_buttons_left = empty_buttons_left - 1;
            int cheack9=0;
            int tempRow, tempCol;
            if ( ((row-1)>=0) && ((column-1)>=0) ){
                tempRow = row-1;
                tempCol = column-1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (row-1)>=0 ){
                tempRow = row-1;
                tempCol = column;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (row-1)>=0 && (column+1)<(SizeH) ){
                tempRow = row-1;
                tempCol = column+1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (column-1)>=0 ){
                tempRow = row;
                tempCol = column-1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (column+1)<(SizeH) ){
                tempRow = row;
                tempCol = column+1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (row+1)<(SizeV) && (column-1)>=0 ){
                tempRow = row+1;
                tempCol = column-1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (row+1)<(SizeV) ){
                tempRow = row+1;
                tempCol = column;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }
            if ( (row+1)<(SizeV) && (column+1)<(SizeH) ){
                tempRow = row+1;
                tempCol = column+1;
                if (Real_Mines[tempRow][tempCol]==true) {cheack9++;} }

            if (cheack9==0){
                btn[row][column].setDisable(true);
                btn[row][column].setText("");
                btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #000000;");

                if ( ((row-1)>=0) && ((column-1)>=0) ){
                    tempRow = row-1;
                    tempCol = column-1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (row-1)>=0 ){
                    tempRow = row-1;
                    tempCol = column;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (row-1)>=0 && (column+1)<(SizeH) ){
                    tempRow = row-1;
                    tempCol = column+1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (column-1)>=0 ){
                    tempRow = row;
                    tempCol = column-1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (column+1)<(SizeH) ){
                    tempRow = row;
                    tempCol = column+1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (row+1)<(SizeV) && (column-1)>=0 ){
                    tempRow = row+1;
                    tempCol = column-1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (row+1)<(SizeV) ){
                    tempRow = row+1;
                    tempCol = column;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }
                if ( (row+1)<(SizeV) && (column+1)<(SizeH) ){
                    tempRow = row+1;
                    tempCol = column+1;
                    if (btn[tempRow][tempCol].getText().contains(".")) {OpenMineBox(tempRow, tempCol, SizeV, SizeH, btn);} }

            }
            else{
                btn[row][column].setDisable(true);
                btn[row][column].setText(""+cheack9);
                if (cheack9==1){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #0000ff; -fx-font-size: 1.3em"); }
                if (cheack9==2){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #00ff00; -fx-font-size: 1.3em"); }
                if (cheack9==3){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #ff0000; -fx-font-size: 1.3em"); }
                if (cheack9==4){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #aa00ff; -fx-font-size: 1.3em"); }
                if (cheack9==5){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #902020; -fx-font-size: 1.3em"); }
                if (cheack9==6){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #00ffff; -fx-font-size: 1.3em"); }
                if (cheack9==7){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #000000; -fx-font-size: 1.3em"); }
                if (cheack9==8){ btn[row][column].setStyle("-fx-background-color: #707070; -fx-text-fill: #ffffff; -fx-font-size: 1.3em"); }


            }
        }
    }

    public void ChangeTime(int s){
        time_v.setText( (""+s) );
        if (s==0){
            TimeUp();
        }
    }

    public void TimeUp() {
        countDown.CountDownStop();
        time_v.setStyle("-fx-background-color: #6666bb;");
        for (int q=0; q<SizeV; q++){
            for (int w=0; w<SizeH; w++){
                btn[q][w].setDisable(true);
                if (Real_Mines[q][w]==true){
                    btn[q][w].setText("#");
                    btn[q][w].setStyle("-fx-background-color: #0000ff; -fx-text-fill: #000000;");
                }
            }
        }
    }

    public void WinGame(){
        countDown.CountDownStop();
        for (int q=0; q<SizeV; q++){
            for (int w=0; w<SizeH; w++){
                btn[q][w].setDisable(true);
                if (Real_Mines[q][w]==true){
                    btn[q][w].setText("#");
                    btn[q][w].setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000000;");
                }
            }
        }
        winScore = countDown.GetMaxSeconds() - Integer.parseInt(time_v.getText());
        String name_of_record = "";

        //Check if score is in 'top <5>'
        boolean its_new_record = All_Scoreboards.CheckRecord(selected_size, selected_difficulty, winScore);

        //Dialog: Show score to player, if score is good enough to enter top 5 ask for name
        if (its_new_record){
            TextInputDialog dialog1 = new TextInputDialog();
            dialog1.setTitle("You won!!");
            dialog1.setHeaderText("Size: " + selected_size + "\nDifficulty: " + selected_difficulty + "\nYour time: " + winScore);
            dialog1.setContentText("Enter Name: ");
            Optional<String> result = dialog1.showAndWait();
            if (result.isPresent()){
                name_of_record = result.get();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("You won!!");
            alert1.setHeaderText("Size: " + selected_size + "\nDifficulty: " + selected_difficulty + "\nYour time: " + winScore);
            alert1.setContentText(null);
            alert1.showAndWait();
        }

        //Update collection
        if (its_new_record){
            All_Scoreboards.addScoreEntry(selected_size, selected_difficulty, winScore, name_of_record);
        }

        //Dialog: Show score list
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Score List");
        alert2.setHeaderText("Size: " + selected_size + "\nDifficulty: " + selected_difficulty);

        //Show Records
        Scores = All_Scoreboards.getScores(selected_size, selected_difficulty);
        String allertString = "";
        for (int i=0; i<Scores.size(); i++) {
            allertString = allertString + Scores.get(i).getName() + ": " + Scores.get(i).getTime() +"\n";
        }
        alert2.setContentText("<Name>: <Score>\n"+allertString);
        alert2.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
