//// OLDTimer.java
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//public class OLDTimer extends JPanel {
//    //Label that displays the remaining time.
//    private JLabel timerLabel;
//    //Button that sets the timer to 10 seconds
//    private JButton tenButton;
//    //Button that sets the timer to 30 seconds
//    private JButton thirtyButton;
//    //Button that sets the timer to 60 seconds
//    private JButton minuteButton;
//    //Button that starts the timer
//    private JButton StartButton;
//    //button that stops the timer
//    private JButton StopButton;
//    //This variable will display the remain time.
//    private int counter;
//    //This is the timer object.
//    private javax.swing.Timer timer;
//    public void OLDTimer() {
//        //initialize the counter to 0
//        counter = 0;
//
//        //instantiate Labels and buttons.
//        timerLabel = new JLabel("Timer: " + counter);
//        tenButton = new JButton("10s");
//        thirtyButton = new JButton("30s");
//        minuteButton = new JButton("60s");
//        StartButton = new JButton("Start Timer");
//        StopButton = new JButton("Stop Timer");
//
//        //Add label to panel
//        this.add(timerLabel);
//        this.add(tenButton);
//        this.add(thirtyButton);
//        this.add(minuteButton);
//        this.add(StartButton);
//        this.add(StopButton);
//
//
//        //create button action listeners
//        tenButton.addActionListener((ActionEvent e) -> {
//            setCounter(10);
//            timerLabel.setText("Timer: 10");
//        });
//
//        thirtyButton.addActionListener((ActionEvent e) -> {
//            setCounter(30);
//            timerLabel.setText("Timer: 30");
//        });
//
//        minuteButton.addActionListener((ActionEvent e) -> {
//            setCounter(60);
//            timerLabel.setText("Timer: 60");
//        });
//
//        StartButton.addActionListener(e ->StartTimer());
//        StopButton.addActionListener(e->StopTimer());
//    }
//    //Setter for Counter variable.
//    public void setCounter(int counter) {
//        this.counter = counter;
//    }
//
//    //Function that will start the timer. Every second the counter variable will decrement.
//    private void StartTimer() {
//        timer = new javax.swing.Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(counter <= 1) {
//                    StopTimer();
//                }
//                counter--;
//                timerLabel.setText("Timer: " + counter);
//                if(counter == 0) {
//                    timerLabel.setText("Time's Up!");
//                    StopTimer();
//                }
//            }
//        });
//        timer.start();
//    }
//    //Function that stops the timer.
//    private void StopTimer() {
//        if(timer != null && timer.isRunning()) {
//            timer.stop();
//        }
//    }
//
//}
