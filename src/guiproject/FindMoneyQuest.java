package guiproject;

import guiproject.mainMap.MyBoy;
import guiproject.SimpleAudioPlayer;
import java.awt.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FindMoneyQuest extends JFrame implements MouseListener {

    private JPanel contentpane;
    private MyImageIcon backgroundImg;
    private JLabel backgroundpane;
    private JTextArea moneyArea;
    private int frameWidth = 1152, frameHeight = 768;
    SimpleAudioPlayer biancaSound, backgorundSound;
    MyBoy boy;

    static String files[] = {"resources/quest1/set1/1.PNG", "resources/quest1/set1/222.PNG", "resources/quest1/set1/333.PNG",
        "resources/quest1/set1/4.PNG", "resources/quest1/set1/555.PNG", "resources/quest1/set1/666.PNG", "resources/quest1/set1/7.PNG", "resources/quest1/set1/8.PNG"};

    static String files2[] = {"resources/quest1/set2/21.PNG", "resources/quest1/set2/22.PNG", "resources/quest1/set2/23.PNG",
        "resources/quest1/set2/24.PNG", "resources/quest1/set2/25.PNG", "resources/quest1/set2/26.PNG", "resources/quest1/set2/27.PNG", "resources/quest1/set2/28.PNG"};
    static String files3[] = {"resources/quest1/set3/31.PNG", "resources/quest1/set3/32.PNG", "resources/quest1/set3/33.PNG",
        "resources/quest1/set3/34.PNG", "resources/quest1/set3/35.PNG", "resources/quest1/set3/36.PNG", "resources/quest1/set3/37.PNG", "resources/quest1/set3/38.PNG"};
    static JButton buttons[], SkipButton, ExitButton;
    ImageIcon closedIcon;
    int numButtons;
    MyImageIcon icons[],questpaneImg, okbuttonImg;
    Timer myTimer;
    int numClicks = 0;
    int oddClickIndex = 0;
    int currentIndex = 0;
    int matched = 0;
    int boymoney = 0;
    boolean CheckOpend = false;
    int pass = 0;

    public FindMoneyQuest(MyBoy b, JTextArea ar, SimpleAudioPlayer audio,int round) {
 
        questpaneImg = new MyImageIcon("resources/store/canvas.png").resize(1100, 200);
        okbuttonImg = new MyImageIcon("resources/store/button.png").resize(100, 40);
        backgorundSound = audio;
        setTitle("FindMoneyQuest");
        setBounds(150, 50, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addMouseListener(this);
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        
        boy = b;
        moneyArea = ar;
        round++;
        setBounds(0, 0, frameWidth, frameHeight);
        setTitle("Help me play this game!");
        setLayout(new GridLayout(4, 4));
        getContentPane().setBackground(new Color(149, 89, 29, 20));
     
        closedIcon = new ImageIcon("");
        numButtons = files.length * 2;
        buttons = new JButton[numButtons];
        icons = new MyImageIcon[numButtons];
         if (round % 3 == 0) {
            for (int i = 0, j = 0; i < files.length; i++) {
                icons[j] = new MyImageIcon(files3[i]).resize(200, 150);
                buttons[j] = new JButton("");
                buttons[j].addActionListener(new ImageButtonListener());
                buttons[j].setIcon(closedIcon);
                add(buttons[j++]);

                icons[j] = icons[j - 1];
                buttons[j] = new JButton("");
                buttons[j].addActionListener(new ImageButtonListener());
                buttons[j].setIcon(closedIcon);
                add(buttons[j++]); 

            }
        } else if (round % 3 == 1) {
            for (int i = 0, j = 0; i < files.length; i++) {
                icons[j] = new MyImageIcon(files2[i]).resize(200, 150);
                buttons[j] = new JButton("");
                buttons[j].addActionListener(new ImageButtonListener());
                buttons[j].setIcon(closedIcon);
                add(buttons[j++]);

                icons[j] = icons[j - 1];
                buttons[j] = new JButton("");
                buttons[j].addActionListener(new ImageButtonListener());
                buttons[j].setIcon(closedIcon);
                add(buttons[j++]); 

            }}
            else {  
                for (int i = 0, j = 0; i < files.length; i++) {
            icons[j] = new MyImageIcon(files[i]).resize(200, 150);
            buttons[j] = new JButton("");
            buttons[j].addActionListener(new ImageButtonListener());
            buttons[j].setIcon(closedIcon);
            add(buttons[j++]);
            
            icons[j] = icons[j - 1];
            buttons[j] = new JButton("");
            buttons[j].addActionListener(new ImageButtonListener());
            buttons[j].setIcon(closedIcon);
            add(buttons[j++]);

        }      
        }

        Random gen = new Random();
        for (int i = 0; i < numButtons; i++) {
            int rand = gen.nextInt(numButtons);
            MyImageIcon temp = icons[i];
            icons[i] = icons[rand];
            icons[rand] = temp;
        }
        SkipButton = new JButton("SKIP");
        SkipButton.setBounds(730, 500, 95, 30);
        SkipButton.setVisible(false);
        SkipButton.setLayout(null);

        setVisible(true);
        validate();
        try {
            biancaSound = new SimpleAudioPlayer("resources/soundtrack/q1.wav");
            biancaSound.play();
        } catch (Exception e) {

        }
        myTimer = new Timer(900, new TimerListener());
    }

    public void mouseClicked(MouseEvent me) {
        
    }
    public void mousePressed(MouseEvent me) {
        
    }
    public void mouseReleased(MouseEvent me) {
        
    }
    public void mouseEntered(MouseEvent me) {
        
    }
    public void mouseExited(MouseEvent me) {
        
    }

    private class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            buttons[currentIndex].setIcon(closedIcon);
            buttons[oddClickIndex].setIcon(closedIcon);

            myTimer.stop();
            matched--;
        }
    }

    private class ImageButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (myTimer.isRunning()) {
                return;
            }

            numClicks++;

            for (int i = 0; i < numButtons; i++) {
                if (e.getSource() == buttons[i]) {
                    buttons[i].setIcon(icons[i]);
                    currentIndex = i;
                }
            }

            if (numClicks % 2 == 0) {
                if (currentIndex == oddClickIndex) {
                    numClicks--;
                    return;
                }
                if (icons[currentIndex] != icons[oddClickIndex]) {

                    myTimer.start();
                }
            } else {
                matched++;
                oddClickIndex = currentIndex;
                Color clr1 = new Color(139, 69, 19);

            }
            if (numClicks > 35) {
                JOptionPane.showMessageDialog(contentpane,
                        "You take too much time!!", "Bianca", JOptionPane.CLOSED_OPTION);
                setVisible(false);
                try {
                    backgorundSound.resumeAudio();
                    biancaSound.stop();
                } catch (Exception ex) {}
                
            } else if (matched == 9) {
                
                JOptionPane.showMessageDialog(contentpane,
                        "Good job!! I'll give you a bit of money for thank you!", "Bianca", JOptionPane.CLOSED_OPTION);
                boy.increaseMoney(3500);
                boy.decreseEnergy();
                setVisible(false);
                try {
                    backgorundSound.resumeAudio();
                    biancaSound.stop();
                } catch (Exception ex) {

                }
            }
        }
    }
}
