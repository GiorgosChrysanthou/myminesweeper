package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class CountDown {
    MineSweeperGUI mineSweeperGUI;
    private final int max_seconds = 999;
    private int seconds = max_seconds;
    private Timeline oneSecondsCounter;

    public CountDown(MineSweeperGUI mineSweeperGUI) {

        this.mineSweeperGUI = mineSweeperGUI;
        oneSecondsCounter = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (seconds >= 0) {
                    mineSweeperGUI.ChangeTime( seconds );
                    seconds--;
                }
            }
        }));
        oneSecondsCounter.setCycleCount(Timeline.INDEFINITE);
        oneSecondsCounter.playFromStart();
    }

    public void CountDownReset() {
        seconds = max_seconds;
        oneSecondsCounter.playFromStart();
    }

    public void CountDownStop() {
        oneSecondsCounter.stop();
    }

    public int GetMaxSeconds() {
        return max_seconds;
    }
}
