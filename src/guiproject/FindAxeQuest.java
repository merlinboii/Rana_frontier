package guiproject;

import guiproject.mainMap.MyBoy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FindAxeQuest extends JDialog {
    private MyImageIcon boyLImg[],boyRImg[];
    private JPanel contentpane;
    private JLabel drawpane, startButton, point;
    private Boy boy;
    private MyImageIcon backgroundImg, bombImg, axeImg, buttonImg1, buttonImg2, pointBar;
    private int frameWidth = 1152, frameHeight = 768;
    private Thread axThread[], bombThread[];
    private int count = -1;
    private Random xRan, speedRan;
    private Thread startThread;
    private JTextArea scoreText,text;
    private SimpleAudioPlayer backgroundSound,quest2Sound;
    private MyBoy b;
    private boolean last;

    
    public FindAxeQuest(MyBoy boy,SimpleAudioPlayer s) {

        backgroundSound=s;
        b = boy;
        axThread = new Thread[15];
        bombThread = new Thread[15];

        setTitle("Lake");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        this.setLayout(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();
    }
    
    synchronized public void UpdateScore(int s){
        count += s;
    }
    void start() {

        startThread = new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < axThread.length; i++) {
                        axThread[i].start();
                        bombThread[i].start();
                        Thread.sleep(3000);
                    }
                } catch (Exception ex) {
                }
            }
        };
        startThread.start();
    }

    public void AddComponents() {
        
        try {
            quest2Sound = new SimpleAudioPlayer("resources/soundtrack/q2.wav");
        } catch (Exception ex) {} 
        quest2Sound.play();
        backgroundSound.pause();
        
        backgroundImg = new MyImageIcon("resources/quest2/background.jpg").resize(frameWidth, frameHeight);
        buttonImg1 = new MyImageIcon("resources/quest2/button.png").resize(100,50);
        buttonImg2 = new MyImageIcon("resources/quest2/button_pressed.png").resize(100,50);
        pointBar = new MyImageIcon("resources/quest2/pointBar.png").resize(300, 80);

        drawpane = new JLabel();

        boy = new Boy();
        
        text = new JTextArea();
        text.setBounds(70, 50, 250, 100);
        text.setEditable(false);
        text.setText("Collect the axes and\ndon't touch the bomb!");
        text.setBackground(new Color(0, 0, 0, 0));
        text.setForeground(Color.white);
        text.setFont(new Font("Garamond", Font.BOLD, 24));
        
        scoreText = new JTextArea();
        scoreText.setBounds(75, 55, 250, 70);
        scoreText.setEditable(false);
        scoreText.setText("Score : " + 0);
        scoreText.setBackground(new Color(0, 0, 0, 0));
        scoreText.setForeground(Color.white);
        scoreText.setFont(new Font("Segoe Script", Font.BOLD, 30));
        scoreText.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
                if(last==false) boy.setIcon(boyRImg[4]);
                else boy.setIcon(boyLImg[4]);
            }

            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyChar()=='a') {
                    boy.moveLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyChar()=='d') {
                    boy.moveRight();
                }
            }
        });
        point = new JLabel();
        point.setBounds(750, 20, 300, 150);
        point.setIcon(pointBar);
        drawpane.setBounds(0, 0, frameWidth, frameHeight);
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        startButton = new JLabel();
        startButton.setBounds(950, 620,100,50);
        startButton.setIcon(buttonImg1);
        startButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
                if (count == -1) {
                    boy.setVisible(true);
                    scoreText.setText("Score : " + 0);
                    count = 0;
                    start();
                } else if (count != -1 && startThread.isAlive() == false) {
                    if(count>=10){
                        int input = JOptionPane.showConfirmDialog(contentpane, "You got an axe!", "", JOptionPane.OK_OPTION);
                        b.updateItem(6, 1);
                        
                         try {
                            quest2Sound.stop();
                             backgroundSound.resumeAudio();
                        } catch (Exception ex) {} 
                        setVisible(false);
                    }
                    else{
                        int input2 = JOptionPane.showConfirmDialog(contentpane, "You lose!", "", JOptionPane.OK_OPTION);
                        try {
                   quest2Sound.stop();
                    backgroundSound.resumeAudio();
                        } catch (Exception ex) {} 
                        setVisible(false);
                    }
            }
            }
            public void mousePressed(MouseEvent me) {
                startButton.setIcon(buttonImg2);  
            }

            public void mouseReleased(MouseEvent me) {
                startButton.setIcon(buttonImg1);
            }

            public void mouseEntered(MouseEvent me) {

            }

            public void mouseExited(MouseEvent me) {

            }
        });
        point.add(scoreText);
        drawpane.add(text);
        drawpane.add(point);
        drawpane.add(boy);
        drawpane.add(startButton);
        xRan = new Random();
        speedRan = new Random();
        contentpane.add(drawpane, BorderLayout.CENTER);
        for (int i = 0; i < axThread.length; i++) {
            int x = xRan.nextInt(1052);
            int s = speedRan.nextInt(200) + 200;
            axThread[i] = new Thread(new AxeThread(x, s));
        }
        for (int i = 0; i < axThread.length; i++) {
            int x = xRan.nextInt(1052);
            int s = speedRan.nextInt(200) + 200;
            bombThread[i] = new Thread(new BombThread(x, s));
        }
        validate();
    }

    class AxeThread extends JLabel implements Runnable {

        private boolean axeMove = true;
        private int speed;
        private int X, Y;

        AxeThread(int x, int s) {
            speed = s;
            X = x;
            Y = -100;
            axeImg = new MyImageIcon("resources/quest2/axe.png").resize(100, 100);
            this.setBounds(X, Y, 100, 100);
            this.setIcon(axeImg);
            this.setVisible(true);
            drawpane.add(this);
        }

        public void run() {
            this.setVisible(true);

            while (axeMove == true) {

                Y = Y + 50;
                this.setLocation(X, Y);
                repaint();
                if (this.getBounds().intersects(boy.getBounds())) {
                    UpdateScore(1);
                    scoreText.setText("Score : " + count);
                    axeMove = false;
                    this.setVisible(false);
                }
                if (this.getBounds().y > frameHeight) {
                    axeMove = false;
                    this.setVisible(false);
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class BombThread extends JLabel implements Runnable {

        private boolean bombMove = true;
        private int speed;
        private int X, Y;

        BombThread(int x, int s) {
            speed = s;
            X = x;
            Y = -120;
            bombImg = new MyImageIcon("resources/quest2/bomb.png").resize(100, 120);
            this.setBounds(X, Y, 100, 120);
            this.setIcon(bombImg);
            this.setVisible(true);
            drawpane.add(this);
        }

        public void run() {

            while (bombMove == true) {
                Y = Y + 50;
                this.setLocation(X, Y);
                repaint();
                if (this.getBounds().intersects(boy.getBounds())) {
                    if (count > 0) {
                        UpdateScore(-1);
                    }
                    scoreText.setText("Score : " + count);
                    bombMove = false;
                    this.setVisible(false);
                }
                if (this.getBounds().y > frameHeight) {
                    bombMove = false;
                    this.setVisible(false);
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Boy extends JLabel {
 
        private int curX = 200,step=0;
        private boolean horizontalMove, verticalMove;

        public Boy() {
            boyLImg = new MyImageIcon[6];
            boyRImg = new MyImageIcon[6];
            for(int i=0;i<6;i++)  boyLImg[i] = new MyImageIcon("resources/actors/L"+(i+1)+".png").resize(150, 248);
            for(int i=0;i<6;i++)  boyRImg[i] = new MyImageIcon("resources/actors/R"+(i+1)+".png").resize(150, 248);
            this.setIcon(boyRImg[4]);
            this.setBounds(curX, 450, 150, 248);
            this.setVisible(false);
        }
        public void moveLeft() {
            last=true;
            if (curX - 10 >= 0) {
                curX = curX - 10;
                this.setLocation(curX, 450);
                repaint();
            }
            if(step<=5){
                this.setIcon(boyLImg[step]);
                repaint();
                step++;
            }else{
                step=0;
                this.setIcon(boyLImg[step]);
                repaint();
                step++;
            }
        }

        public void moveRight() {
            last=false;
            if (curX + 10 <= frameWidth - 150) {
                curX = curX + 10;
                this.setLocation(curX, 450);
                repaint();
            }
            if(step<=5){
                this.setIcon(boyRImg[step]);
                repaint();
                step++;
            }else{
                step=0;
                this.setIcon(boyRImg[step]);
                repaint();
                step++;
            }
        }
    }
}
