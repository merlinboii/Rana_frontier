package guiproject;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static javax.swing.JList.VERTICAL;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class mainMap extends JFrame implements KeyListener {

    private JPanel contentpane, elements[];
    private JDialog inventory;
    private JLabel inventorypane, storypane, backgroundpane, moneyBar, energyBar, dayBar, actors, endpane,
            inventoryItemPane, dialog, namegamepane, startButton, exitButton, useButton, dropButton, kinPane, bowPane, irinPane, filmPane;
    private MyImageIcon islandMapImg, dialogLabelImg, boyImg, boyLImg[], boyRImg[], boyFImg[], boyBImg[], moneyBarImg, energyBarImg, dayBarImg,
            inventoryImg, AImg, BImg, CImg, backgroundImg, buttonstartImg, buttonstartpressImg, gameoverImg,
            namegameImg, exitButtonImg, useButtonImg, dropButtonImg, exitButtonImg2, useButtonImg2, dropButtonImg2, kinImg, bowImg, irinImg, filmImg;
    private JTextArea dialogText, moneyText, energyText, dayText, endname;
    private JList inventoryList;
    private MyBoy boy;
    private MyPlace place[] = new MyPlace[13];
    private String e, m;
    private int frameWidth = 1152, frameHeight = 768, collisionCount = 0, eventCase;
    private boolean checkQ3 = false, checkQ2 = false, checkDP = false;
    private boolean intersec = false, next = true, left = true, right = true, up = true, down = true, isOpenInventory = false, soundCase[];
    private DialogLabel firstDialog;
    private SimpleAudioPlayer audioPlayer;
    private JButton okbutton;
    private int numdays = 10;
    private Vector vector;
    private Reminder dayCount;
    private SimpleAudioPlayer backgroundSound, biancaSound, nolanSound, ASound, shopSound, BSound, CSound;
    private SimpleAudioPlayer lakeSound, dolceSound, wesleySound, op1Sound, stephanSound, op2Sound, thuderSound, endSound, forestSound;
    private int round = 0, selected = 0, last;
    private Thread endThread;

    public static void main(String[] args) {
        new mainMap();
    }

    public mainMap() {

        setTitle("Main Application ");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());

        AddComponents();
        validate();
        //prologue();
        start();
    }

    public void start() {
        storypane.setIcon(backgroundImg);
        storypane.setVisible(true);
        namegamepane.setVisible(true);
        startButton.setVisible(true);
        backgroundpane.setVisible(true);
    }

    public void AddComponents() {
        soundCase = new boolean[13];
        for (int i = 0; i < soundCase.length; i++) {
            soundCase[i] = false;
        }
        try {
            backgroundSound = new SimpleAudioPlayer("resources/soundtrack/map.wav");
        } catch (Exception e) {
        }
        backgroundSound.play();
        backgroundImg = new MyImageIcon("resources/prologue/Imgur.jpg").resize(frameWidth, frameHeight);
        namegameImg = new MyImageIcon("resources/prologue/name.png").resize(950, 500);
        buttonstartImg = new MyImageIcon("resources/prologue/button.png").resize(300, 120);
        buttonstartpressImg = new MyImageIcon("resources/prologue/button _pressed.png").resize(300, 120);
        dialogLabelImg = new MyImageIcon("resources/dialogLabelImg.png").resize(900, 210);
        islandMapImg = new MyImageIcon("resources/map/map.jpg").resize(frameWidth, frameHeight);
        boyImg = new MyImageIcon("resources/actors/boy5.png").resize(30, 50);
        moneyBarImg = new MyImageIcon("resources/map/moneyBarImg.png").resize(180, 50);
        energyBarImg = new MyImageIcon("resources/map/energyBarImg.png").resize(180, 50);
        dayBarImg = new MyImageIcon("resources/map/dayBarImg.png").resize(170, 55);
        inventoryImg = new MyImageIcon("resources/map/inventory.png").resize(500, 380);
        exitButtonImg = new MyImageIcon("resources/map/exit.png").resize(150, 60);
        exitButtonImg2 = new MyImageIcon("resources/map/exit2.png").resize(150, 60);
        gameoverImg = new MyImageIcon("resources/map/gameover.jpg").resize(frameWidth, frameHeight);
        kinImg = new MyImageIcon("resources/ending/kin.png").resize(250, 250);
        bowImg = new MyImageIcon("resources/ending/bow.png").resize(250, 250);
        irinImg = new MyImageIcon("resources/ending/irin.png").resize(250, 250);
        filmImg = new MyImageIcon("resources/ending/film.png").resize(250, 250);
        boyLImg = new MyImageIcon[6];
        boyRImg = new MyImageIcon[6];
        boyFImg = new MyImageIcon[6];
        boyBImg = new MyImageIcon[6];
        for (int i = 0; i < 6; i++) {
            boyLImg[i] = new MyImageIcon("resources/actors/L" + (i + 1) + ".png").resize(30, 50);
        }
        for (int i = 0; i < 6; i++) {
            boyRImg[i] = new MyImageIcon("resources/actors/R" + (i + 1) + ".png").resize(30, 50);
        }
        for (int i = 0; i < 6; i++) {
            boyBImg[i] = new MyImageIcon("resources/actors/B" + (i + 1) + ".png").resize(30, 50);
        }
        for (int i = 0; i < 6; i++) {
            boyFImg[i] = new MyImageIcon("resources/actors/F" + (i + 1) + ".png").resize(30, 50);
        }
        AImg = new MyImageIcon("resources/map/A.png").resize(30, 50);
        BImg = new MyImageIcon("resources/map/B.png").resize(30, 50);
        CImg = new MyImageIcon("resources/map/C.png").resize(30, 50);
        storypane = new JLabel();
        firstDialog = new DialogLabel();
        dialog = new JLabel();
        dialog.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                dialog.setVisible(false);
                namegamepane.setVisible(false);
                startButton.setVisible(false);
                storypane.setVisible(false);
                for (int i = 0; i < soundCase.length; i++) {
                    try {
                        if (soundCase[i] == true) {
                            switch (i) {
                                case 0:
                                    lakeSound.stop();
                                    soundCase[0] = false;
                                    break;
                                case 1:
                                    forestSound.stop();
                                    soundCase[1] = false;
                                    break;
                                case 2:
                                    wesleySound.stop();
                                    soundCase[2] = false;
                                    break;
                                case 3:
                                    dolceSound.stop();
                                    soundCase[3] = false;
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    biancaSound.stop();
                                    soundCase[5] = false;
                                    break;
                                case 6:
                                    break;
                                case 7:
                                    break;
                                case 8:
                                    break;
                                case 9:
                                    ASound.stop();
                                    soundCase[9] = false;
                                    break;
                                case 10:
                                    BSound.stop();
                                    soundCase[10] = false;
                                    break;
                                case 11:
                                    CSound.stop();
                                    soundCase[11] = false;
                                    break;
                                case 12:
                                    break;
                            }
                            backgroundSound.resumeAudio();
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        });
        kinPane = new JLabel();
        kinPane.setBounds(440, 250 + 768, 250, 250);
        kinPane.setIcon(kinImg);
        bowPane = new JLabel();
        bowPane.setBounds(440, 610 + 768, 250, 250);
        bowPane.setIcon(bowImg);
        irinPane = new JLabel();
        irinPane.setBounds(440, 970 + 768, 250, 250);
        irinPane.setIcon(irinImg);
        filmPane = new JLabel();
        filmPane.setBounds(440, 1330 + 768, 250, 250);
        filmPane.setIcon(filmImg);

        MyImageIcon endBackground = new MyImageIcon("resources/prologue/white7.jpg").resize(1152, 3200);
        endpane = new JLabel();
        endpane.setBounds(0, 0, 1152, 3200);
        endpane.setLayout(null);
        endpane.setIcon(endBackground);
        endpane.setVisible(false);
        endname = new JTextArea();
        endname.setBounds(350, 100 + 768, 820, 2500);
        endname.setBackground(new Color(0, 0, 0, 0));
        endname.setFont(new Font("Calibri", Font.BOLD, 24));
        endname.setForeground(Color.white);
        endname.setText("                             Final Project \n\n                                     by\n\n\n\n\n\n\n\n\n\n\n\n"
                + "                  Kaori  Takase  6113128\n\n\n\n\n\n\n\n\n\n\n\n"
                + "             Parinee  Suknijarun  6113135\n\n\n\n\n\n\n\n\n\n\n\n"
                + "        Apichaya  Amornchaikul  6113188\n\n\n\n\n\n\n\n\n\n\n\n"
                + "      Parichaya  Thanawuthikrai  6113295");
        endpane.add(endname);
        endpane.add(kinPane);
        endpane.add(bowPane);
        endpane.add(irinPane);
        endpane.add(filmPane);
        dialog.setIcon(dialogLabelImg);
        dialog.setBounds(120, 500, 900, 210);
        dialog.setLayout(new FlowLayout(60, 90, 75));
        dialog.setVisible(false);
        dialogText = new JTextArea();
        dialogText.setBounds(120, 500, 820, 120);
        dialogText.setBackground(new Color(0, 0, 0, 0));
        dialogText.setFont(new Font("Garamond", Font.BOLD, 24));
        dialogText.setForeground(Color.white);
        dialogText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                dialog.setVisible(false);
                namegamepane.setVisible(false);
                startButton.setVisible(false);
                storypane.setVisible(false);
                for (int i = 0; i < soundCase.length; i++) {
                    try {
                        if (soundCase[i] == true) {
                            switch (i) {
                                case 0:
                                    lakeSound.stop();
                                    soundCase[0] = false;
                                    break;
                                case 1:
                                    forestSound.stop();
                                    soundCase[1] = false;
                                    break;
                                case 2:
                                    wesleySound.stop();
                                    soundCase[2] = false;
                                    break;
                                case 3:
                                    dolceSound.stop();
                                    soundCase[3] = false;
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    biancaSound.stop();
                                    soundCase[5] = false;
                                    break;
                                case 6:
                                    break;
                                case 7:
                                    break;
                                case 8:
                                    break;
                                case 9:
                                    ASound.stop();
                                    soundCase[9] = false;
                                    break;
                                case 10:
                                    BSound.stop();
                                    soundCase[10] = false;
                                    break;
                                case 11:
                                    CSound.stop();
                                    soundCase[11] = false;
                                    break;
                                case 12:
                                    break;
                            }
                            backgroundSound.resumeAudio();
                        }
                    } catch (Exception ex) {
                    }
                }

            }
        });
        dialog.add(dialogText);

        storypane = new JLabel();
        storypane.setBounds(0, 0, frameWidth, frameHeight);
        storypane.setLayout(null);
        storypane.setVisible(false);

        inventory = new JDialog();
        inventory.setBounds(315, 150, 515, 417);
        inventory.setLayout(null);
        inventory.setVisible(false);
        inventory.setModal(true);
        inventorypane = new JLabel();
        inventorypane.setBounds(0, 0, 500, 380);
        inventorypane.setLayout(null);
        inventorypane.setIcon(inventoryImg);
        inventory.add(inventorypane);

        exitButton = new JLabel();
        exitButton.setBounds(335, 205, 150, 60);
        exitButton.setIcon(exitButtonImg);
        exitButton.setVisible(true);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                exitButton.setIcon(exitButtonImg);
                inventory.setVisible(false);
            }
        });

        inventorypane.add(exitButton);

        JPanel panel = new JPanel();
        vector = new Vector();
        panel.setForeground(Color.black);
        panel.setBackground(Color.white);

        MyImageIcon axeImg = new MyImageIcon("resources/actors/axe.png").resize(50, 50);
        MyImageIcon woodImg = new MyImageIcon("resources/actors/wood.png").resize(50, 50);
        MyImageIcon diamondImg = new MyImageIcon("resources/actors/diamond.png").resize(50, 50);
        MyImageIcon potionImg = new MyImageIcon("resources/actors/potion.png").resize(50, 50);
        MyImageIcon bowImg = new MyImageIcon("resources/actors/bow.png").resize(50, 50);
        MyImageIcon redSwordImg = new MyImageIcon("resources/actors/sword_red.png").resize(50, 50);
        MyImageIcon goldSwordImg = new MyImageIcon("resources/actors/sword_gold.png").resize(50, 50);
        MyImageIcon silverStaffImg = new MyImageIcon("resources/actors/staff_silver.png").resize(50, 50);

        elements = new JPanel[8];
        elements[0] = new JPanel(new FlowLayout(FlowLayout.LEFT));   // NEW
        elements[0].add(new JLabel(bowImg));
        elements[0].add(new JLabel("An old bow"));

        elements[1] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[1].add(new JLabel(potionImg));
        elements[1].add(new JLabel("Health potion"));

        elements[2] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[2].add(new JLabel(silverStaffImg));
        elements[2].add(new JLabel("Silver staff for nothing"));

        elements[3] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[3].add(new JLabel(redSwordImg));
        elements[3].add(new JLabel("A rusty red sword"));

        elements[4] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[4].add(new JLabel(goldSwordImg));
        elements[4].add(new JLabel("A rare sharp gold sword"));

        elements[5] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[5].add(new JLabel(axeImg));
        elements[5].add(new JLabel("Axe"));

        elements[6] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[6].add(new JLabel(woodImg));
        elements[6].add(new JLabel("Some wood for boat"));

        elements[7] = new JPanel(new FlowLayout(FlowLayout.LEFT));  // NEW
        elements[7].add(new JLabel(diamondImg));
        elements[7].add(new JLabel("A precious diamond"));

        inventoryList = new JList();
        inventoryList.setCellRenderer(new CustomCellRenderer());
        inventoryList.setListData(vector);

        inventoryList.setBorder(new LineBorder(Color.black));
        inventoryList.setBounds(17, 175, 250, 250);

        inventoryList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selected = inventoryList.getSelectedIndex();
            }

        });

        JScrollPane scrollPane = new JScrollPane();

        inventorypane.add(inventoryList);
        scrollPane.setBounds(17, 115, 250, 250);

        scrollPane.setViewportView(inventoryList);
        inventoryList.setLayoutOrientation(JList.VERTICAL);
        inventorypane.add(scrollPane);

        backgroundpane = new JLabel();
        backgroundpane.setIcon(islandMapImg);
        backgroundpane.setBounds(0, 0, frameWidth, frameHeight);
        backgroundpane.setLayout(null);

        backgroundpane.add(firstDialog);
        backgroundpane.add(endpane);
        backgroundpane.add(storypane);

        boy = new MyBoy(35, 60, 700, 500);
        dayCount = new Reminder(4);
        backgroundpane.add(boy);

        namegamepane = new JLabel();
        namegamepane.setIcon(namegameImg);
        namegamepane.setBounds(135, 30, 950, 500);
        namegamepane.setVisible(false);

        startButton = new JLabel();
        startButton.setIcon(buttonstartImg);
        startButton.setBounds(415, 530, 300, 120);
        startButton.setLayout(null);
        startButton.setVisible(false);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startButton.setIcon(buttonstartpressImg);
                startButton.setBounds(415, 530, 300, 120);
                startButton.setVisible(true);
            }

            public void mouseExited(MouseEvent e) {
                startButton.setIcon(buttonstartImg);
                startButton.setVisible(true);
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == startButton) {
                    dialog.setVisible(false);
                    namegamepane.setVisible(false);
                    startButton.setVisible(false);
                    storypane.setVisible(false);
                    storypane.remove(startButton);
                }
            }
        });

        storypane.add(namegamepane);
        storypane.add(dialog);
        storypane.add(startButton);

        moneyText = new JTextArea();
        moneyText.setBounds(140, 85, 200, 200);
        moneyText.setForeground(Color.white);
        moneyText.setEditable(false);
        m = " " + boy.getMoney();
        moneyText.setText(m);
        moneyText.setBackground(new Color(0, 0, 0, 0));
        moneyText.setFont(new Font("Segoe Script", Font.BOLD, 20));
        backgroundpane.add(moneyText);

        energyText = new JTextArea();
        energyText.setBounds(140, 145, 200, 200);
        energyText.setForeground(Color.white);
        energyText.setEditable(false);
        e = "" + boy.getEnergy();
        energyText.setText(e);
        energyText.setBackground(new Color(0, 0, 0, 0));
        energyText.setFont(new Font("Segoe Script", Font.BOLD, 20));
        backgroundpane.add(energyText);

        dayText = new JTextArea();
        dayText.setBounds(80, 10, 50, 50);
        dayText.setForeground(Color.white);
        dayText.setEditable(false);
        e = "" + dayCount.getDay();
        dayText.setText(e);
        System.out.println(e);
        dayText.setBackground(new Color(0, 0, 0, 0));
        dayText.setFont(new Font("Segoe Script", Font.BOLD, 20));

        moneyBar = new JLabel();
        moneyBar.setIcon(moneyBarImg);

        moneyBar.setLayout(null);
        moneyBar.setBounds(50, 0, 200, 200);

        energyBar = new JLabel();
        energyBar.setIcon(energyBarImg);
        energyBar.setBounds(47, 60, 200, 200);
        energyBar.setLayout(null);

        dayBar = new JLabel();
        dayBar.setIcon(dayBarImg);
        dayBar.setBounds(930, 70, 200, 50);
        dayBar.setLayout(null);
        dayBar.add(dayText);

        backgroundpane.add(energyBar);
        backgroundpane.add(moneyBar);
        backgroundpane.add(dayBar);

        String placeName[] = {"lake", "forest", "repairer", "port", "shop", "rich", "home", "oldman", "others", "A", "B", "C", "boat"};

        place[0] = new MyPlace(placeName[0], 1, 170, 180, 665, 50);
        place[1] = new MyPlace(placeName[1], 2, 400, 180, 280, 25);
        place[2] = new MyPlace(placeName[2], 3, 155, 175, 905, 215);
        place[3] = new MyPlace(placeName[3], 4, 210, 170, 820, 125);
        place[4] = new MyPlace(placeName[4], 5, 170, 120, 473, 500);
        place[5] = new MyPlace(placeName[5], 6, 210, 160, 140, 363);
        place[6] = new MyPlace(placeName[6], 7, 140, 110, 870, 435);
        place[7] = new MyPlace(placeName[7], 8, 105, 65, 650, 350);
        place[8] = new MyPlace(placeName[8], 9, 245, 132, 412, 288);
        place[9] = new MyPlace(placeName[9], 10, 50, 70, 460, 210);//Leon(A)
        place[10] = new MyPlace(placeName[10], 11, 40, 60, 380, 500); //Edward(B)
        place[11] = new MyPlace(placeName[11], 12, 40, 60, 800, 350);//Anette(C)
        place[12] = new MyPlace(placeName[12], 13, 70, 38, 1035, 183);
        backgroundpane.add(place[12]);
        place[12].setVisible(false);
        for (int i = 0; i <= 11; i++) {
            backgroundpane.add(place[i]);
        }
        contentpane.add(backgroundpane, BorderLayout.CENTER);
        backgroundpane.setVisible(true);
        //validate();    
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'I' || e.getKeyChar() == 'i') {
            if (isOpenInventory) {
                inventory.setVisible(false);
                isOpenInventory = false;
            } else {
                isOpenInventory = true;

                exitButton.setIcon(exitButtonImg);
                inventory.setVisible(true);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        collision();
        if (intersec == false) {
            collisionCount = 0;
            up = true;
            left = true;
            right = true;
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
            if (intersec == true) {
                collisionCount++;
            }
            if (collisionCount == 1) {
                left = false;
            }
            boy.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
            if (intersec == true) {
                collisionCount++;
            }
            if (collisionCount == 1) {
                right = false;
            }
            boy.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
            if (intersec == true) {
                collisionCount++;
            }
            if (collisionCount == 1) {
                up = false;
            }
            boy.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
            if (intersec == true) {
                collisionCount++;
            }
            if (collisionCount == 1) {
                down = false;
            }
            boy.moveDown();
        }

    }

    public void keyReleased(KeyEvent e) {
        if (last == 0) {
            boy.setIcon(boyBImg[4]);
        } else if (last == 1) {
            boy.setIcon(boyFImg[4]);
        } else if (last == 2) {
            boy.setIcon(boyLImg[4]);
        } else if (last == 3) {
            boy.setIcon(boyRImg[4]);
        }
    }

    public int collision() {
        int i;
        for (i = 0; i <= 8; i++) {
            if (boy.getBounds().intersects(place[i].getBounds())) {
                intersec = true;

                break;
            } else {
                intersec = false;
            }
        }
        return i;
    }

    public void prologue() {

        try {
            op1Sound = new SimpleAudioPlayer("resources/soundtrack/op1.wav");
        } catch (Exception e) {
        }
        backgroundSound.pause();
        op1Sound.play();

        MyImageIcon img[] = new MyImageIcon[12];
        img[0] = new MyImageIcon("resources/prologue/1.jpg").resize(frameWidth, frameHeight);
        img[1] = new MyImageIcon("resources/prologue/2.jpg").resize(frameWidth, frameHeight);
        img[2] = new MyImageIcon("resources/prologue/3.jpg").resize(frameWidth, frameHeight);
        img[3] = new MyImageIcon("resources/prologue/4.jpg").resize(frameWidth, frameHeight);
        img[4] = new MyImageIcon("resources/prologue/5.jpg").resize(frameWidth, frameHeight);
        img[5] = new MyImageIcon("resources/prologue/6.jpg").resize(frameWidth, frameHeight);
        img[6] = new MyImageIcon("resources/prologue/7.jpg").resize(frameWidth, frameHeight);
        img[7] = new MyImageIcon("resources/prologue/8.jpg").resize(frameWidth, frameHeight);
        img[8] = new MyImageIcon("resources/prologue/9.jpg").resize(frameWidth, frameHeight);
        img[9] = new MyImageIcon("resources/prologue/10.jpg").resize(frameWidth, frameHeight);
        img[10] = new MyImageIcon("resources/prologue/11.jpg").resize(frameWidth, frameHeight);
        img[11] = new MyImageIcon("resources/prologue/op.jpg").resize(frameWidth, frameHeight);

        try {
            storypane.setVisible(true);
            storypane.setIcon(img[0]);
            firstDialog.printDialog("resources/prologue/dialog.txt");
            
            storypane.setIcon(img[0]);
            firstDialog.printDialog("resources/prologue/dialog2.txt");
            
            storypane.setIcon(img[2]);
            firstDialog.printDialog("resources/prologue/dialog3.txt");
            

            storypane.setIcon(img[3]);
            firstDialog.printDialog("resources/prologue/dialog4.txt");
            
            storypane.setIcon(img[4]);
            firstDialog.printDialog("resources/prologue/dialog5.txt");
            fade(1);
            op2Sound = new SimpleAudioPlayer("resources/soundtrack/op2.wav");
            op1Sound.stop();
            op2Sound.play();

            storypane.setIcon(img[5]);//sea
            TimeUnit.MILLISECONDS.sleep(2000);

            storypane.setIcon(img[6]);//ship
            firstDialog.printDialog("resources/prologue/dialog6.txt");
           
            storypane.setIcon(img[7]);//actor
            firstDialog.printDialog("resources/prologue/dialog7.txt");
           
            storypane.setIcon(img[8]);
            TimeUnit.MILLISECONDS.sleep(2000);
            thuderSound = new SimpleAudioPlayer("resources/soundtrack/thuder.wav");
            op2Sound.stop();
            thuderSound.play();
            
            storypane.setIcon(img[9]);
            firstDialog.printDialog("resources/prologue/dialog8.txt");
            
            storypane.setIcon(img[10]);//shipdead
            firstDialog.printDialog("resources/prologue/dialog9.txt");
            
            thuderSound.stop();
            fade(0);      
            dialogText.setText("My boat was broke...\n" +
            "Fortunately I still alive.\n");
            repaint();
            dialogText.setVisible(true);
            dialog.setVisible(true);
            TimeUnit.MILLISECONDS.sleep(3000);
            stephanSound = new SimpleAudioPlayer("resources/soundtrack/stephan.wav");
            stephanSound.play();
            
            storypane.setIcon(img[11]);//stephan
            dialogText.setText("(Stephan) : Oh...A castaway again? \n" +
                            "You are not the first person who lost in here.\n" +
                            "This place is RANA island.");
            repaint();
            TimeUnit.MILLISECONDS.sleep(6000);
            dialog.setVisible(false);
            fade(1);
            stephanSound.stop();
            backgroundSound.resumeAudio();

        } catch (Exception e) {

        }
    }

    public void NameCradit() {
        endpane.setVisible(true);
        for (int i = 0; i >= -2432; i--) {
            endpane.setBounds(0, i, 1152, 3200);
            repaint();
            try {
                sleep(7);
            } catch (InterruptedException ex) {
                Logger.getLogger(mainMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void end() {
        try {
            endSound = new SimpleAudioPlayer("resources/soundtrack/ending.wav");
        } catch (Exception e) {
        }
        backgroundSound.pause();
        endSound.play();

        MyImageIcon img[] = new MyImageIcon[6];
        img[0] = new MyImageIcon("resources/ending/end1.jpg").resize(frameWidth, frameHeight);
        img[1] = new MyImageIcon("resources/ending/end2.jpg").resize(frameWidth, frameHeight);
        img[2] = new MyImageIcon("resources/ending/end3.jpg").resize(frameWidth, frameHeight);
        img[3] = new MyImageIcon("resources/ending/end4.jpg").resize(frameWidth, frameHeight);
        img[4] = new MyImageIcon("resources/ending/end5.jpg").resize(frameWidth, frameHeight);
        try {
            storypane.setVisible(true);
            fade(1);
            storypane.setIcon(img[0]);//1
            firstDialog.printDialog("resources/ending/end1.txt");
            fade(0);
            storypane.setIcon(img[1]);//2
            TimeUnit.MILLISECONDS.sleep(4000);
            storypane.setIcon(img[2]);//3
            firstDialog.printDialog("resources/ending/end2.txt");
            fade(0);
            storypane.setIcon(img[3]);//4
            TimeUnit.MILLISECONDS.sleep(5000);
            fade(0);
            NameCradit();

            endSound.stop();
            dialog.setVisible(false);
        } catch (Exception ex) {
        }
        storypane.setVisible(false);

    }

    class MyPlace extends JLabel implements MouseListener {

        private String placeName;
        private int width, height, event, X, Y;
        private boolean intersec;
        MyImageIcon placeImg, placeImg2;
        private boolean firstmeet = false, secondmeet = false;

        MyPlace(String n, int e, int w, int h, int x, int y) {
            placeName = n;
            width = w;
            height = h;
            event = e;
            X = x;
            Y = y;

            addMouseListener(this);
            placeImg = new MyImageIcon("resources/map/" + placeName + ".png").resize(width, height);
            placeImg2 = new MyImageIcon("resources/map/" + placeName + "2.png").resize(width, height);
            setIcon(placeImg);
            setLayout(null);
            setBounds(X, Y, width, height);
        }

        public void mouseClicked(MouseEvent e) {

            System.out.println("CASE = " + event);
            //{"lake", "forest", "repairer", "port", "shop", "rich", "home", "oldman", "others", "A", "B", "C"};
            if (event == 13) {
                endThread = new Thread() {
                    public void run() {
                        end();
                    }
                };
                endThread.start();
            }
            if (boy.getBounds().intersects(this.getBounds())) {

                switch (event) {
                    case 1: { /////LAKEEEEEEEE

                        MyImageIcon img[] = new MyImageIcon[1];
                        img[0] = new MyImageIcon("resources/quest2/2lake.jpg").resize(frameWidth, frameHeight + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        try {
                            lakeSound = new SimpleAudioPlayer("resources/soundtrack/lake.wav");
                        } catch (Exception ex) {
                        }
                        backgroundSound.pause();
                        lakeSound.play();
                        soundCase[0] = true;

                        dialog.setVisible(true);
                        if (!checkQ2) {
                            dialogText.setText("(God) : Hello sir! Are you looking for an axe? \n"
                                    + "People who cut down the trees always forget and leave their axes here. \n"
                                    + "Maybe…you can pick one of them if you don’t mind. Go find it.");
                            int input = JOptionPane.showConfirmDialog(contentpane, "Do this quest ?", "", JOptionPane.YES_NO_OPTION);
                            if (input == 0) {
                                try {
                                    lakeSound.stop();
                                    soundCase[0] = false;
                                    new FindAxeQuest(boy, backgroundSound);
                                    storypane.setVisible(false);
                                } catch (Exception ex) {
                                }

                            } else {

                                try {
                                    lakeSound.stop();
                                    soundCase[0] = false;
                                    backgroundSound.resumeAudio();
                                } catch (Exception ex) {
                                }

                                storypane.setVisible(false);
                            }
                        } else {
                            dialogText.setText("(God) : Hi.. \nYou already got the axe. That's Great!");
                        }
                        break;
                    }
                    case 2: {////FOREST
                        MyImageIcon img[] = new MyImageIcon[2];
                        img[0] = new MyImageIcon("resources/dragon/darkforest.jpg").resize(frameWidth, frameHeight + 100);
                        img[1] = new MyImageIcon("resources/dragon/forest.jpg").resize(frameWidth, frameHeight + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        dialog.setVisible(true);
                        if (!checkDP) {
                            dialogText.setText("(laughing)HE HE HE HE HE HE HE HE HE HE!!!!\n"
                                    + "(Dragon) : You don't have any word. You can't fight against me!\n"
                                    + "GO AWAY!!!!!!!!!!!");
                            int input = JOptionPane.showConfirmDialog(contentpane, "Let's GO!", "", JOptionPane.YES_NO_OPTION);
                            if (input == 0) {
                                backgroundSound.pause();
                                new Dragon(boy, backgroundSound);
                                storypane.setVisible(false);
                                dialog.setVisible(false);
                            } else {
                                storypane.setVisible(false);
                                dialog.setVisible(false);
                            }

                        } else {
                            storypane.setIcon(img[1]);
                            try {
                                forestSound = new SimpleAudioPlayer("resources/soundtrack/forest.wav");
                            } catch (Exception ex) {
                            }
                            backgroundSound.pause();
                            forestSound.play();
                            soundCase[1] = true;
                            dialogText.setText("You already have the wood.");
                        }
                        break;
                    }
                    case 3: {///REPAIRER
                        place[3].secondMeet();
                        MyImageIcon img[] = new MyImageIcon[2];
                        img[0] = new MyImageIcon("resources/repairer/repairer.jpg").resize(frameWidth, frameHeight + 100);
                        img[1] = new MyImageIcon("resources/end.jpg").resize(frameWidth, frameHeight + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        try {
                            wesleySound = new SimpleAudioPlayer("resources/soundtrack/repaier.wav");
                        } catch (Exception ex) {
                        }
                        backgroundSound.pause();
                        wesleySound.play();
                        soundCase[2] = true;
                        dialog.setVisible(true);
                        if (!firstmeet) {
                            place[2].firstMeet();
                            dialogText.setText("(Wesley) : I have never seen you before.\n"
                                    + "Who are you? Are you a human?");
                        } else if (place[2].firstMeet()) {
                            dialogText.setText("(Wesley) : What do you want? \nUmm…You want me to build a ship for you? "
                                    + "We must have an exchange. \nBring me something that you think it’s worth enough.");
                            int input = JOptionPane.showConfirmDialog(contentpane, "DO you wantto build a boat?", "", JOptionPane.YES_NO_OPTION);
                            if (input == 0) {
                                if (boy.getItem(5) && boy.getItem(6) && boy.getItem(7)) {
                                    dialogText.setText("(Wesley) : Finally...This is your ship \n " + "Good luck and Bonvoyage");
                                    storypane.setIcon(img[1]);
                                    place[12].setVisible(true);
                                } else if ((boy.getItem(7) || boy.getItem(6)) && boy.getItem(5) == false) {
                                    dialogText.setText("(Wesley) : Sorry, I can't build the ship for you. \nYou must bring me the materials.");
                                } //DAIMOND //AXE
                                else if (boy.getItem(5) && boy.getItem(6)) {
                                    dialogText.setText("(Wesley) : I want diamond. If you want me to build the ship.\nYou must bring me a diamond first.");
                                } //WOOD //AXE
                                else {
                                    JOptionPane.showMessageDialog(contentpane, "    YOU DON'T HAVE ANYTHIN!!G\n                       GO AWAY!!!", "WARNING", JOptionPane.CLOSED_OPTION);
                                }

                            } else {
                                dialogText.setText("(Wesley) : See ya!");
                            }
                        }
                        break;
                    }
                    case 4: { /////PORT

                        MyImageIcon img[] = new MyImageIcon[1];
                        img[0] = new MyImageIcon("resources/port/harbor.jpg").resize(frameWidth, frameHeight + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        dialog.setVisible(true);
                        if (!firstmeet) {
                            place[3].firstMeet();
                            dialogText.setText("(Dolce) : Hey , Is it the first time for us? \n"
                                    + "Let me introduce myself.\nI am a harbour master,You can call me “Dolce”");

                        } else if (place[3].firstMeet() && !secondmeet) {
                            // place[3].secondMeet(); 
                            dialogText.setText("(Dolce) : Are you looking for a craftsman ?\nHis house is around here!! His name is Wesley");

                        } else if (place[3].secondMeet()) {
                            dialogText.setText("(Dolce) : You see Wesley? It was good!");
                        }
                        try {
                            dolceSound = new SimpleAudioPlayer("resources/soundtrack/harbor.wav");
                        } catch (Exception ex) {
                        }
                        backgroundSound.pause();
                        dolceSound.play();
                        soundCase[3] = true;
                        break;
                    }
                    case 5: {//////STORE

                        new Store(boy, moneyText, backgroundSound);
                        break;
                    }
                    case 6: {
                        //RICH BIG HOUSE == FIND MONEY QUEST
                        try {
                            biancaSound = new SimpleAudioPlayer("resources/soundtrack/richg.wav");
                        } catch (Exception ex) {
                        }
                        backgroundSound.pause();
                        biancaSound.play();
                        soundCase[5] = true;

                        try {
                            //RICH BIG HOUSE == FIND MONEY QUEST
                            dialog.setVisible(false);
                            MyImageIcon img[] = new MyImageIcon[1];
                            img[0] = new MyImageIcon("resources/quest1/richgirlhouse1.png").resize(frameWidth, frameHeight + 100);
                            startButton.setVisible(false);
                            storypane.setIcon(img[0]);
                            storypane.setVisible(true);
                            dialog.setVisible(true);
                            dialogText.setText("(Bianca) : Oh I'm Bianca.\nI'm playing a game and it's so hard !!!\nCan you help me pass it?? ");
                            int input = JOptionPane.showConfirmDialog(contentpane, "Do this quest ?", "", JOptionPane.YES_NO_OPTION);
                            if (input == 0) {
                                round++;
                                dialogText.setText("(Bianca) : Woww. I will give you some money for thank you.");
                                biancaSound.pause();
                                new FindMoneyQuest(boy, moneyText, backgroundSound, round);
                                startButton.setVisible(true);
                                storypane.setVisible(false);
                            } else {
                                dialogText.setText("(Bianca) : Errrr!! Such a stupid boy");
                                startButton.setVisible(true);
                                dialog.setVisible(false);
                                storypane.setVisible(false);
                                biancaSound.stop();
                                backgroundSound.resumeAudio();
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(mainMap.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case 7: {

                        int input = JOptionPane.showConfirmDialog(contentpane, "Sleep to skip the day ?", "", JOptionPane.YES_NO_OPTION);
                        if (input == JOptionPane.YES_OPTION) {
                            if (dayCount.getDay() > 1) {
                                numdays--;
                                dayText.setText("" + numdays);
                                if (boy.getEnergy() < 10) {
                                    boy.increseEnergy();
                                }
                                backgroundpane.repaint();
                                dayBar.repaint();

                                System.out.printf("skip = %d\n", numdays);
                                break;
                            } else {
                                System.out.printf("day final %d \n", numdays);
                                dayText.setText("" + numdays);
                                repaint();

                                storypane.setIcon(gameoverImg);
                                storypane.setVisible(true);
                                storypane.repaint();

                                JOptionPane.showMessageDialog(contentpane,
                                        "Rana island sunk to the bottom of the ocean \n\n                             You died!", "Game over", JOptionPane.CLOSED_OPTION);
                                System.exit(0);
                            }
                        } else {
                            break;
                        }
                    }
                    case 8: {

                        MyImageIcon img[] = new MyImageIcon[2];
                        img[0] = new MyImageIcon("resources/quest3/oldman.png").resize(1152, 768).resize(435, 720);
                        img[1] = new MyImageIcon("resources/quest3/oldmanhouse.jpg").resize(1152, 768);

                        dialog.setVisible(true);
                        storypane.setIcon(img[1]);
                        storypane.setVisible(true);
                        if (!checkQ3) {
                            dialogText.setText("(Nolan) : Oh...Hello Boy! I’m Nolan.\nI haven’t been able to find something for long time.\nCould you help me find my stuff?..");
                            int input = JOptionPane.showConfirmDialog(contentpane, "Do this quest ?", "", JOptionPane.YES_NO_OPTION);
                            if (input == 0) {
                                backgroundSound.pause();
                                new FindDiamonQuest(boy, backgroundSound);

                                storypane.setVisible(false);
                                dialog.setVisible(false);
                            } else {

                                dialog.setVisible(false);
                                storypane.setVisible(false);
                            }
                        } else {

                            dialogText.setText("(Nolan) : Hi.. \nThank you for helping");
                        }
                        break;
                    }
                    case 9: {
                        break;
                    }
                    case 10: {
                        try {
                            MyImageIcon img[] = new MyImageIcon[1];
                            img[0] = new MyImageIcon("resources/charactor/A.jpg").resize(frameWidth, frameHeight + 100);
                            storypane.setVisible(true);
                            storypane.setIcon(img[0]);

                            dialog.setVisible(true);
                            if (!firstmeet) {
                                place[9].firstMeet();
                                dialogText.setText("(Leon) : Are you human ?! How did you come here!\n"
                                        + "My name is Leon. ");

                            } else if (place[9].firstMeet() && !checkDP) {

                                dialogText.setText("(Leon) : You are come here again! \nYou want to go the forest? Really?\n"
                                        + "...don’t forget to bring the weapons. ");

                            } else if (checkDP) {
                                dialogText.setText("(Leon) : You can win dragon? not bad.");

                            }

                            ASound = new SimpleAudioPlayer("resources/soundtrack/A.wav");

                        } catch (Exception ex) {
                        }
                        backgroundSound.pause();
                        ASound.play();
                        soundCase[9] = true;
                        break;
                    }
                    case 11: {

                        MyImageIcon img[] = new MyImageIcon[1];
                        img[0] = new MyImageIcon("resources/charactor/B.jpg").resize(1152, 768 + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        try {
                            BSound = new SimpleAudioPlayer("resources/soundtrack/B.wav");
                        } catch (Exception ex) {
                        }
                        dialog.setVisible(true);
                        if (!firstmeet) {
                            place[10].firstMeet();
                            dialogText.setText("(Edward) : Hello my name is Edward. I work as a doctor in this island \nNice to meet you.\n"
                                    + "So.. What's your name?");
                        } else if (place[10].firstMeet()) {
                            dialogText.setText("(Edward) : You should think carefully before using your money.");
                        }
                        backgroundSound.pause();
                        BSound.play();
                        soundCase[10] = true;
                        break;
                    }
                    case 12: {

                        MyImageIcon img[] = new MyImageIcon[1];
                        img[0] = new MyImageIcon("resources/charactor/C.jpg").resize(1152, 768 + 100);
                        storypane.setVisible(true);
                        storypane.setIcon(img[0]);
                        try {
                            CSound = new SimpleAudioPlayer("resources/soundtrack/C.wav");
                        } catch (Exception ex) {
                        }
                        dialog.setVisible(true);
                        if (!firstmeet) {
                            place[11].firstMeet();
                            dialogText.setText("(Anette) : Hi , You are Aden! I know you.\nHow did you come? It’s fantastic!\n"
                                    + "Hmm...I have not introduced my self I'am Anette\n");

                        } else if (place[11].firstMeet() && !secondmeet) {

                            dialogText.setText("(Anette) : Are you going to ask Wesley to help?\nI think he want something worther than money!!!");
                            place[11].secondMeet();

                        } else if (place[11].secondMeet() && checkQ3) {
                            dialogText.setText("(Anette) : We met again! Aden.\n"
                                    + "Are you enjoy this place here?\n"
                                    + "I have to go good luck!");
                        }
                        backgroundSound.pause();
                        CSound.play();
                        soundCase[11] = true;
                        break;
                    }
                }
            }
        }

        boolean firstMeet() {
            firstmeet = true;
            return firstmeet;
        }

        boolean secondMeet() {
            secondmeet = true;
            return secondmeet;
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            grow(1);
        }

        public void mouseExited(MouseEvent e) {
            grow(0);
        }

        public void grow(int g) {
            if (g == 0) {
                this.setIcon(placeImg);
            } else {
                setIcon(placeImg2);
            }
        }

    }

    class MyBoy extends JLabel {

        private int width, height, X, Y;
        private int energy = 10;
        private int money = 0;
        private int[] item = {0, 0, 0, 0, 0, 0, 0, 0};
        private int step = 0;

        MyBoy(int w, int h, int x, int y) {
            width = w;
            height = h;
            X = x;
            Y = y;
            boyFImg = new MyImageIcon[6];
            boyBImg = new MyImageIcon[6];
            boyLImg = new MyImageIcon[6];
            boyRImg = new MyImageIcon[6];
            for (int i = 0; i < 6; i++) {
                boyLImg[i] = new MyImageIcon("resources/actors/L" + (i + 1) + ".png").resize(35, 58);
            }
            for (int i = 0; i < 6; i++) {
                boyRImg[i] = new MyImageIcon("resources/actors/R" + (i + 1) + ".png").resize(35, 58);
            }
            for (int i = 0; i < 6; i++) {
                boyBImg[i] = new MyImageIcon("resources/actors/B" + (i + 1) + ".png").resize(35, 58);
            }
            for (int i = 0; i < 6; i++) {
                boyFImg[i] = new MyImageIcon("resources/actors/F" + (i + 1) + ".png").resize(35, 58);
            }
            setIcon(boyFImg[4]);
            setBounds(X, Y, 35, 58);
        }

        public int getMoney() {
            return money;
        }

        public int getEnergy() {
            return energy;
        }

        public void increaseMoney(int m) {
            money += m;
            moneyText.setText(Integer.toString(money));
            backgroundpane.repaint();
            moneyBar.repaint();
            System.out.println("Give Money : " + money);
        }

        public void decreseMoney(int m) {
            if (money >= m) {
                money -= m;
            }
            moneyText.setText(Integer.toString(money));
            backgroundpane.repaint();
            moneyBar.repaint();
            System.out.println("Money : " + money + " m = " + m); //Check money
        }

        public void updateItem(int i, int amt) {
            switch (i) {
                case 0: {
                    item[0] += amt;
                    System.out.println("Bow : " + item[0]);
                    vector.addElement(elements[0]);
                    break;//Bow
                }
                case 1: {
                    item[1] += amt;
                    System.out.println("Potion : " + item[1]);
                    vector.addElement(elements[1]);
                    break;//Potion
                }
                case 2: {
                    item[2] += amt;
                    System.out.println("Staff Silver : " + item[2]);
                    vector.addElement(elements[2]);
                    break;//StaffSilver
                }
                case 3: {
                    item[3] += amt;
                    System.out.println("Sword Red : " + item[3]);
                    vector.addElement(elements[3]);
                    break;//SwordRed
                }
                case 4: {
                    item[4] += amt;
                    System.out.println("Sword Gold : " + item[4]);
                    vector.addElement(elements[4]);
                    break;//SwordGold
                }
                case 5: {
                    item[5] = 1;
                    System.out.println("Wood : " + item[5]);
                    vector.addElement(elements[6]);
                    break;//WOOD
                }
                case 6: {
                    item[6] = 1;
                    System.out.println("Axe : " + item[6]);
                    vector.addElement(elements[5]);
                    checkQ2 = true;
                    break;//AXE
                }
                case 7: {
                    item[7] = 1;
                    System.out.println("Diamond : " + item[7]);
                    vector.addElement(elements[7]);
                    checkQ3 = true;
                    break; //DIAMOND
                }
            }
            inventoryList.setListData(vector);
            repaint();
        }

        boolean getItem(int i) {
            boolean have = false;
            if (item[i] >= 1) {
                have = true;
            }
            return have;
        }

        public void dragonPass() {
            checkDP = true;
        }

        public void decreseEnergy() {
            int a = energy - 1;
            if (a < 0) {
                energy = 0;// GAME OVER
                System.out.println("ENERGY : " + energy);
                energyText.setText(Integer.toString(energy));
                energyBar.repaint();
                backgroundpane.repaint();
            } else {
                energy--;
                System.out.println("ENERGY : " + energy);
                energyText.setText(Integer.toString(energy));
                energyBar.repaint();
                backgroundpane.repaint();
            }
        }

        public void increseEnergy() {
            energy += 3;
            System.out.println("ENERGY : " + energy);
            energyText.setText(Integer.toString(energy));
            energyText.repaint();
        }

        public void moveUp() {
            last = 0;
            if (up == true && Y - 5 >= 170) {
                Y = Y - 5;
                this.setLocation(X, Y);
                System.out.println(X + "  " + Y);
            }
            if (step <= 5) {
                this.setIcon(boyBImg[step]);
                repaint();
                step++;
            } else {
                step = 0;
                this.setIcon(boyBImg[step]);
                repaint();
                step++;
            }

        }

        public void moveDown() {
            last = 1;
            if (down == true && Y - 5 <= 625) {
                Y = Y + 5;
                this.setLocation(X, Y);
                System.out.println(X + "  " + Y);
            }
            if (step <= 5) {
                this.setIcon(boyFImg[step]);
                repaint();
                step++;
            } else {
                step = 0;
                this.setIcon(boyFImg[step]);
                repaint();
                step++;
            }
        }

        public void moveLeft() {
            last = 2;
            if (left == true && X - 5 >= 100) {
                X = X - 5;
                this.setLocation(X, Y);
                System.out.println(X + "  " + Y);
            }
            if (step <= 5) {
                this.setIcon(boyLImg[step]);
                repaint();
                step++;
            } else {
                step = 0;
                this.setIcon(boyLImg[step]);
                repaint();
                step++;
            }
        }

        public void moveRight() {
            last = 3;
            if (right == true && X + 5 <= 1030) {
                X = X + 5;
                this.setLocation(X, Y);
            }
            if (step <= 5) {
                this.setIcon(boyRImg[step]);
                repaint();
                step++;
            } else {
                step = 0;
                this.setIcon(boyRImg[step]);
                repaint();
                step++;
            }
        }
    }

    class CustomCellRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            Component component = (Component) value;
            component.setBackground(isSelected ? Color.yellow : Color.white);
            component.setForeground(isSelected ? Color.black : Color.black);
            return component;
        }
    }

    public void fade(int type) {
        storypane.setVisible(true);
        MyImageIcon fade[] = new MyImageIcon[7];
        fade[0] = new MyImageIcon("resources/prologue/white1.jpg").resize(1152, 768);
        fade[1] = new MyImageIcon("resources/prologue/white2.jpg").resize(1152, 768);
        fade[2] = new MyImageIcon("resources/prologue/white3.jpg").resize(1152, 768);
        fade[3] = new MyImageIcon("resources/prologue/white4.jpg").resize(1152, 768);
        fade[4] = new MyImageIcon("resources/prologue/white5.jpg").resize(1152, 768);
        fade[5] = new MyImageIcon("resources/prologue/white6.jpg").resize(1152, 768);
        fade[6] = new MyImageIcon("resources/prologue/white7.jpg").resize(1152, 768);
        try {
            if (type == 1) { //black to white
                for (int i = 6; i >= 0; i--) {
                    storypane.setIcon(fade[i]);
                    TimeUnit.MILLISECONDS.sleep(70);
                }
            } else if (type == 0) { //white to black
                for (int i = 1; i <= 60; i++) {
                    storypane.setIcon(fade[i]);
                    TimeUnit.MILLISECONDS.sleep(70);
                }
            }
        } catch (Exception e) {
        }
    }

    public class Reminder {

        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds * 60 * 1000);
        }

        private int getDay() {
            return numdays;
        }

        class RemindTask extends TimerTask {

            public void run() {

                if (dayCount.getDay() == 1) {
                    numdays--;
                    System.out.printf("day final %d \n", numdays);
                    dayText.setText("" + numdays);
                    storypane.repaint();

                    storypane.setIcon(gameoverImg);
                    storypane.setVisible(true);
                    storypane.repaint();
                    JOptionPane.showMessageDialog(contentpane,
                            "Rana island sunk to the bottom of the ocean \n\n                             You died!", "     Game over", JOptionPane.CLOSED_OPTION);
                    timer.cancel();
                    System.exit(0);
                } else if (dayCount.getDay() > 1) {
                    numdays--;
                    dayText.setText("" + numdays);
                    System.out.printf("%d\n", numdays);
                    storypane.repaint();
                    JOptionPane.showMessageDialog(contentpane,
                            "You should go to bed!!", "Warning", JOptionPane.CLOSED_OPTION);
                    new Reminder(4);
                    if (boy.getEnergy() < 10) {
                        boy.increseEnergy();
                    }
                     backgroundpane.repaint();
                     dayBar.repaint();
                     //new Reminder(4);
                }
            }
        }
    }

}

class SimpleAudioPlayer {

    Long currentFrame;
    Clip clip;
    String status;
    AudioInputStream audioInputStream;
    String filePath;

    public SimpleAudioPlayer(String f) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        filePath = f;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
        //start the clip 
        clip.start();
        status = "play";
    }

    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame
                = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already "
                    + "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

}

class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}
