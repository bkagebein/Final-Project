import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Timer extends JPanel {
    JLabel timerLabel;
    JButton tenButton, thirtyButton, minuteButton, startButton, stopButton, clearButton, phoneAFriendButton;
    int counter, totalCounter;
    javax.swing.Timer swingTimer;
    Clip clip;

    public Timer() {
        setupUI();
        setupAudio();
        addActionListeners();
    }

    private void setupUI() {
        timerLabel = new JLabel("Timer: " + counter);
        tenButton = new JButton("10s");
        thirtyButton = new JButton("30s");
        minuteButton = new JButton("60s");
        startButton = new JButton("Start Timer");
        stopButton = new JButton("Stop Timer");
        clearButton = new JButton("Clear Timer");
        phoneAFriendButton = new JButton("Phone a Friend");

        this.add(timerLabel);
        this.add(tenButton);
        this.add(thirtyButton);
        this.add(minuteButton);
        this.add(startButton);
        this.add(stopButton);
        this.add(clearButton);
        this.add(phoneAFriendButton);
    }

    private void setupAudio() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("fart.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private void addActionListeners() {
        tenButton.addActionListener(this::addTime);
        thirtyButton.addActionListener(this::addTime);
        minuteButton.addActionListener(this::addTime);
        startButton.addActionListener(e -> startTimer());
        stopButton.addActionListener(e -> stopTimer());
        clearButton.addActionListener(e -> clearTimer());
        phoneAFriendButton.addActionListener(e -> phoneAFriend());
    }

    private void addTime(ActionEvent e) {
        int value = Integer.parseInt(((JButton) e.getSource()).getText().replaceAll("s", ""));
        totalCounter += value;
        counter = totalCounter;
        timerLabel.setText("Timer: " + counter);
        playSound();
    }

    private void startTimer() {
        if (swingTimer == null || !swingTimer.isRunning()) {
            swingTimer = new javax.swing.Timer(1000, e -> {
                if (counter <= 0) {
                    timerLabel.setText("Time's Up!");
                    stopTimer();
                    clearTimer(); // Reset the timer automatically when time is up
                } else {
                    counter--;
                    timerLabel.setText("Timer: " + counter);
                }
            });
            swingTimer.start();
        }
        playSound();
    }


    private void stopTimer() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }
        playSound();
    }

    private void clearTimer() {
        totalCounter = 0;
        counter = 0;
        timerLabel.setText("Timer: " + counter);
        if (swingTimer != null) {
            swingTimer.stop();
        }
        playSound();
    }

    private void phoneAFriend()  {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop(); // Pause the timer
                playSound();
            }
        }

    private void playSound() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
}

