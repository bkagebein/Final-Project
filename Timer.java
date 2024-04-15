// Timer.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Timer extends JPanel {
    JLabel timerLabel;
    JLabel displayTimerLabel;
    JButton startButton;
    JButton stopButton;
    JButton resetButton;
    final Clip[] clip = {null};
    final int[] seconds = {0};
    final javax.swing.Timer[] timer = {null};

    public Timer() {
        timerLabel = new JLabel("Timer:");
        displayTimerLabel = new JLabel("00:00");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");

        this.add(timerLabel);
        this.add(displayTimerLabel);
        this.add(startButton);
        this.add(stopButton);
        this.add(resetButton);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("fart.wav"));
            clip[0] = AudioSystem.getClip();
            clip[0].open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip[0] != null) {
                    clip[0].stop();
                    clip[0].setFramePosition(0);
                    clip[0].start();
                }
                if (timer[0] == null) {
                    timer[0] = new javax.swing.Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            seconds[0]++;
                            displayTimerLabel.setText(String.format("%02d:%02d", seconds[0] / 60, seconds[0] % 60));
                        }
                    });
                    timer[0].start();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip[0] != null) {
                    clip[0].stop();
                    clip[0].setFramePosition(0);
                    clip[0].start();
                }
                if (timer[0] != null) {
                    timer[0].stop();
                    timer[0] = null;
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip[0] != null) {
                    clip[0].stop();
                    clip[0].setFramePosition(0);
                    clip[0].start();
                }
                seconds[0] = 0;
                displayTimerLabel.setText("00:00");
                if (timer[0] != null) {
                    timer[0].stop();
                    timer[0] = null;
                }
            }
        });
    }
}
