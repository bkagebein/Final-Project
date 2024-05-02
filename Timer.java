import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Timer extends JPanel {
    JLabel timerLabel;
    JButton tenButton, thirtyButton, minuteButton, startButton, stopButton, clearButton, phoneAFriendButton, resumeButton;
    int counter, totalCounter;
    javax.swing.Timer swingTimer;

    // Map to store audio clips
    private Map<String, Clip> soundClips = new HashMap<>();

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
        resumeButton = new JButton("Resume Time");


        this.add(timerLabel);
        this.add(tenButton);
        this.add(thirtyButton);
        this.add(minuteButton);
        this.add(startButton);
        this.add(stopButton);
        this.add(resumeButton);
        this.add(clearButton);
        this.add(phoneAFriendButton);
    }

    private void setupAudio() {
        String[] soundFileNames = {
                "AddTime.wav",
                "ClearTime.wav",
                "PhoneaFriend.wav",
                "ResumeTime.wav",
                "Start.wav",
                "StopTimer.wav"
        };

        System.out.println("Current working directory: " + new File(".").getAbsolutePath());

        for (String fileName : soundFileNames) {
            try {
                File audioFile = new File(fileName);
                if (!audioFile.exists()) {
                    System.err.println("File does not exist: " + audioFile.getAbsolutePath());
                    continue;
                }
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                soundClips.put(fileName, clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                System.err.println("Error loading sound file: " + fileName);
                ex.printStackTrace();
            }
        }
    }

    private void addActionListeners() {
        tenButton.addActionListener(e -> {
            addTime(e);
            playSound("AddTime.wav");
        });
        thirtyButton.addActionListener(e -> {
            addTime(e);
            playSound("AddTime.wav");
        });
        minuteButton.addActionListener(e -> {
            addTime(e);
            playSound("AddTime.wav");
        });
        startButton.addActionListener(e -> {
            startTimer();
            playSound("Start.wav");
        });
        stopButton.addActionListener(e -> {
            stopTimer();
            playSound("StopTimer.wav");
        });
        resumeButton.addActionListener(e -> {
            resumeTimer();
            playSound("ResumeTime.wav");
        });
        clearButton.addActionListener(e -> {
            clearTimer();
            playSound("ClearTime.wav");
        });
        phoneAFriendButton.addActionListener(e -> {
            phoneAFriend();
            playSound("PhoneaFriend.wav");

        });
    }
    private void resumeTimer() {
        if (swingTimer != null && !swingTimer.isRunning() && counter > 0) {
            swingTimer.start(); // Resume the timer
        }
    }
    private void addTime(ActionEvent e) {
        int value = Integer.parseInt(((JButton) e.getSource()).getText().replaceAll("s", ""));
        totalCounter += value;
        counter = totalCounter;
        timerLabel.setText("Timer: " + counter);
        playSound("AddTime.wav"); // Assuming you want the add time sound here
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
            playSound("Start.wav"); // Assuming you want the start sound here
        }
    }

    private void stopTimer() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
            playSound("StopTimer.wav"); // Assuming you want the stop sound here
        }
    }

    private void clearTimer() {
        totalCounter = 0;
        counter = 0;
        timerLabel.setText("Timer: " + counter);
        if (swingTimer != null) {
            swingTimer.stop();
        }
        playSound("ClearTime.wav"); // Assuming you want the clear sound here
    }

    private void phoneAFriend() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop(); // Pause the timer
            playSound("PhoneaFriend.wav"); // Assuming you want the phone a friend sound here
        }
    }

    private void playSound(String soundFileName) {
        // Stop any currently playing clips before starting a new one
        for (Clip c : soundClips.values()) {
            if (c.isRunning()) {
                c.stop();
                c.setFramePosition(0);
            }
        }

        // Play the requested clip
        Clip clip = soundClips.get(soundFileName);
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();     // Start playing
        } else {
            System.err.println("Audio clip not found: " + soundFileName);
        }
    }
}
