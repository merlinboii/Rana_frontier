package guiproject;

import guiproject.mainMap.MyBoy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;

public class Dragon extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane, box, startButton;
    private JLabel dragonLabel, questionpane;
    private MyImageIcon backgroundImg, wingUpImg, wingMidImg, wingDownImg, questionImg, buttonImg1, buttonImg2;
    JButton btn;
    MyBoy boy;
    private int frameWidth = 1152, frameHeight = 768;
    private int dragonCurX = 280, dragonCurY = 20;
    private int dragonWidth = 600, dragonHeight = 556;
    private Thread dragonThread;
    private boolean dragonMove = true, dragonUp = true;
    private int speed = 1000;
    private SimpleAudioPlayer backgroundSound, dragonSound;
    private boolean correct = false, Clicked = false;
    private JTextArea text, textQuestion;

    //////////////////////////////////////////////////////////////////////////
    public Dragon(MyBoy b, SimpleAudioPlayer s) {

        backgroundSound = s;
        setTitle("Dragon");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        boy = b;
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        AddComponents();
        setDragonThread();
        checkSwordGold();
    }

    //////////////////////////////////////////////////////////////////////////
    public void AddComponents() {
        try {
            dragonSound = new SimpleAudioPlayer("resources/soundtrack/forestd.wav");
        } catch (Exception ex) {
        }
        dragonSound.play();
        backgroundSound.pause();

        backgroundImg = new MyImageIcon("resources/dragon/darkforest.jpg").resize(frameWidth, frameHeight);
        wingUpImg = new MyImageIcon("resources/dragon/3.png").resize(dragonWidth, dragonHeight);
        wingMidImg = new MyImageIcon("resources/dragon/2.png").resize(dragonWidth, dragonHeight);
        wingDownImg = new MyImageIcon("resources/dragon/1.png").resize(dragonWidth, dragonHeight);
        questionImg = new MyImageIcon("resources/dragon/canvas.png").resize(900, 300);
        buttonImg1 = new MyImageIcon("resources/dragon/button.png").resize(100, 50);
        buttonImg2 = new MyImageIcon("resources/dragon/button_pressed.png").resize(100, 50);

        text = new JTextArea();
        text.setBounds(70, 50, 500, 100);
        text.setEditable(false);
        text.setText("YOU MUST ANSWER THE QUESTION!!!!");
        text.setBackground(new Color(0, 0, 0, 0));
        text.setForeground(Color.yellow);
        text.setFont(new Font("Garamond", Font.BOLD, 25));

        textQuestion = new JTextArea();
        textQuestion.setBounds(100, 50, 700, 300);
        textQuestion.setEditable(false);
        textQuestion.setText("What is always coming  but never arrives?");
        textQuestion.setBackground(new Color(0, 0, 0, 0));
        textQuestion.setForeground(Color.white);
        textQuestion.setFont(new Font("Garamond", Font.BOLD, 30));

        startButton = new JLabel();
        startButton.setBounds(950, 620, 100, 50);
        startButton.setIcon(buttonImg1);
        startButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
                Clicked = true;
                dragonMove = false;
                dragonLabel.setVisible(false);
                startButton.setVisible(false);
                checkAns();
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
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        questionpane = new JLabel();
        questionpane.setIcon(questionImg);
        questionpane.setBounds(150, 250, 900, 300);
        questionpane.setLayout(null);

        JRadioButton radio1 = new JRadioButton("Money");
        radio1.setBounds(60, 120, 125, 65);
        radio1.setFont(new Font("Garamond", Font.BOLD, 20));

        JRadioButton radio2 = new JRadioButton("Grade A");
        radio2.setBounds(220, 120, 125, 65);
        radio2.setFont(new Font("Garamond", Font.BOLD, 20));

        JRadioButton radio3 = new JRadioButton("Tomorrow");
        radio3.setBounds(380, 120, 125, 65);
        radio3.setFont(new Font("Garamond", Font.BOLD, 20));

        JRadioButton radio4 = new JRadioButton("Time");
        radio4.setBounds(550, 120, 125, 65);
        radio4.setFont(new Font("Garamond", Font.BOLD, 20));

        JRadioButton radio5 = new JRadioButton("Soulmate");
        radio5.setBounds(720, 120, 115, 65);
        radio5.setFont(new Font("Garamond", Font.BOLD, 20));

        questionpane.add(radio1);
        questionpane.add(radio2);
        questionpane.add(radio3);
        questionpane.add(radio4);
        questionpane.add(radio5);
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);
        group.add(radio4);
        group.add(radio5);

        box = new JLabel();
        box.setBounds(520, 400, 800, 200);
        box.setLayout(null);

        dragonLabel = new JLabel(wingUpImg);
        dragonLabel.setBounds(dragonCurX, dragonCurY, dragonWidth, dragonHeight);

        btn = new JButton("Answer");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (radio1.isSelected()) {
                    JOptionPane.showMessageDialog(drawpane, "    It's wrong ", "Dragon", JOptionPane.CLOSED_OPTION);
                    setVisible(false);
                } else if (radio2.isSelected()) {
                    JOptionPane.showMessageDialog(drawpane, "    It's wrong ", "Dragon", JOptionPane.CLOSED_OPTION);
                    setVisible(false);

                } else if (radio3.isSelected()) {
                    JOptionPane.showMessageDialog(drawpane, "    It's correct!! \n You can cut down the wood in this forest. ", "Dragon", JOptionPane.CLOSED_OPTION);
                    drawpane.setVisible(false);
                    correct = true;
                    boy.dragonPass();
                    boy.updateItem(5, 1);
                    setVisible(false);
                } else if (radio4.isSelected()) {
                    JOptionPane.showMessageDialog(drawpane, "    It's wrong ", "Dragon", JOptionPane.CLOSED_OPTION);
                    setVisible(false);
                } else if (radio5.isSelected()) {
                    JOptionPane.showMessageDialog(drawpane, "   It's wrong ", "Dragon", JOptionPane.CLOSED_OPTION);
                    setVisible(false);
                }
                try {
                    dragonSound.stop();
                    backgroundSound.resumeAudio();
                } catch (Exception ex) {
                }
            }
        });
        btn.setBounds(390, 200, 100, 40);
        questionpane.add(btn);

        drawpane.add(box);
        drawpane.add(dragonLabel);
        drawpane.add(questionpane);
        drawpane.add(startButton);
        drawpane.add(text);

        startButton.setVisible(true);
        questionpane.setVisible(false);
        contentpane.add(drawpane, BorderLayout.CENTER);

        validate();
    }

    public void setDragonThread() {
        dragonThread = new Thread() {
            public void run() {
                while (dragonMove) {
                    if (dragonUp) {
                        moveUp();
                    } else if (!dragonUp) {
                        moveDown();
                    }

                    repaint();

                    try {
                        Thread.sleep(190);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        dragonThread.start();
    }

    public void moveUp() {
        dragonCurY = dragonCurY - 30;
        if (dragonLabel.getIcon() == wingUpImg) {
            dragonLabel.setIcon(wingMidImg);
        } else if (dragonLabel.getIcon() == wingMidImg) {
            dragonLabel.setIcon(wingDownImg);
        } else if (dragonLabel.getIcon() == wingDownImg) {
            dragonLabel.setIcon(wingUpImg);
        }
        if (dragonCurY <= 10) {
            dragonCurY = 10;
            dragonUp = false;
        }
        dragonLabel.setLocation(dragonCurX, dragonCurY);

    }

    public void moveDown() {
        dragonCurY = dragonCurY + 30;
        if (dragonLabel.getIcon() == wingUpImg) {
            dragonLabel.setIcon(wingMidImg);
            dragonLabel.setLocation(dragonCurX, dragonCurY);
        } else if (dragonLabel.getIcon() == wingMidImg) {
            dragonLabel.setIcon(wingDownImg);
            dragonLabel.setLocation(dragonCurX, dragonCurY);
        } else if (dragonLabel.getIcon() == wingDownImg) {
            dragonLabel.setIcon(wingUpImg);
            dragonLabel.setLocation(dragonCurX, dragonCurY);
        }
        if (dragonCurY >= 300) {
            dragonCurY = 300;
            dragonUp = true;
        }
        dragonLabel.setLocation(dragonCurX, dragonCurY);

    }

    public void checkSwordGold() {
        try {
            if (boy.getItem(4) && boy.getItem(6) ) {

            } else if  ( boy.getItem(4)) {
                boy.decreseEnergy();
                JOptionPane.showMessageDialog(contentpane, "    You don't have any axe", "Dragon said", JOptionPane.CLOSED_OPTION);

                dragonSound.stop();
                backgroundSound.resumeAudio();
                setVisible(false);
            }else if  ( boy.getItem(6)) {
                boy.decreseEnergy();
                JOptionPane.showMessageDialog(contentpane, "    You cannot fight against me \n      You dont't have the sword.", "Dragon said", JOptionPane.CLOSED_OPTION);
                dragonSound.stop();
                backgroundSound.resumeAudio();
                setVisible(false);
            }
            
            else{
                boy.decreseEnergy();
                JOptionPane.showMessageDialog(contentpane, "    You don't have anything.\n                       GO AWAY!!!", "Dragon said", JOptionPane.CLOSED_OPTION);
                dragonSound.stop();
                backgroundSound.resumeAudio();
                setVisible(false);
            }
        } catch (Exception e) {
        }
    }

    public void checkAns() {
        drawpane.add(questionpane);
        questionpane.add(textQuestion);
        questionpane.setVisible(true);
        textQuestion.setVisible(true);
    }
}
