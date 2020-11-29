package guiproject;

import guiproject.mainMap.MyBoy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Store extends JFrame {
    
    private JPanel contentpane;
    private JLabel backgroundpane;
    private JLabel vendorpane, showItempane,detailpane,canvaspane;
    private JTextArea detailtext , moneyArea,nametext,confirm,exit,warnmoney;
    private JTextField pricetext;
    private MyImageIconStore storeImg, vendorImg, bowImg, potionImg, staffSilverImg,swordRedImg,swordGoldImg,canvasImg,detailImg;
    private MyImageIconStore buttonImg,buttonPressImg;
    private JLabel buyButton,exitButton;
    private JSpinner amount; 
    private JComboBox catalogue;
    private int price,sum,boymoney,mark,amt;
    private String text;
    private int frameWidth = 1152, frameHeight = 768;
    private SpinnerModel value =  new SpinnerNumberModel(1,1,20,1);
    private MyBoy boy;
    private SimpleAudioPlayer shopSound, backgroundSound;
    
    public Store(MyBoy B,JTextArea a,SimpleAudioPlayer audio) {

        setTitle("STORE");
        setBounds(0, 0, frameWidth, frameHeight);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        backgroundSound = audio;
        boy = B;
        moneyArea = a;
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        
        AddComponents();

    }

    public void AddComponents() {
        
        try {
            shopSound = new SimpleAudioPlayer("resources/soundtrack/shop.wav");
        } catch (Exception ex) {} 
        
        shopSound.play();
        backgroundSound.pause();
        
        storeImg = new MyImageIconStore ("resources/store/store.jpg").resize(frameWidth, frameHeight);
        vendorImg = new MyImageIconStore ("resources/store/vendor.png").resize(525, 735);
        bowImg = new MyImageIconStore ("resources/store/bow.png").resize(150, 150);
        potionImg = new MyImageIconStore ("resources/store/potion.png").resize(150, 150);
        staffSilverImg = new MyImageIconStore ("resources/store/staff_silver.png").resize(150, 150);
        swordRedImg = new MyImageIconStore ("resources/store/sword_red.png").resize(150, 150);
        swordGoldImg = new MyImageIconStore ("resources/store/sword_gold.png").resize(150, 150);
        canvasImg = new MyImageIconStore ("resources/store/canvas.png").resize(672, 305);
        detailImg = new MyImageIconStore ("resources/store/detail.png").resize(672, 368);
        buttonImg = new MyImageIconStore ("resources/store/button.png").resize(150, 40);
        buttonPressImg = new MyImageIconStore ("resources/store/button_pressed.png").resize(150, 40);
        
        vendorpane = new JLabel();
        vendorpane.setIcon(vendorImg);
        vendorpane.setBounds(660,10,525,735);
        vendorpane.setVisible(true);
        vendorpane.setLayout(null);
        
        canvaspane = new JLabel(canvasImg);
        canvaspane.setBounds(35,400,672,305);
        canvaspane.setVisible(true);
        canvaspane.setLayout(null);
        
        detailpane = new JLabel(detailImg);
        detailpane.setBounds(35,10,672,368);
        detailpane.setVisible(true);
        detailpane.setLayout(null);
        
        showItempane = new JLabel();
        showItempane.setBounds(70,140,150,150);
        showItempane.setVisible(false);
        detailpane.add(showItempane);
        
        confirm = new JTextArea("CONFIRM");
        confirm.setBounds(280,205, 90, 22);
        confirm.setVisible(true);
        confirm.setLayout(null);
        confirm.setEditable(false);
        confirm.setBackground(new Color(0, 0, 0, 0));
        confirm.setForeground(Color.YELLOW);
        confirm.setFont(new Font("Segeo Script", Font.BOLD, 20));
        canvaspane.add(confirm);
        
        exit = new JTextArea("EXIT");
        exit.setBounds(1014,686, 50, 22);
        exit.setVisible(true);
        exit.setLayout(null);
        exit.setEditable(false);
        exit.setBackground(new Color(0, 0, 0, 0));
        exit.setForeground(Color.YELLOW);
        exit.setFont(new Font("Segeo Script", Font.BOLD, 20));
        
        exitButton  = new JLabel(buttonImg);
        exitButton.setIcon(buttonImg);
        exitButton.setBounds(960, 680, 150, 40);
        exitButton.setVisible(true);
        exitButton.setLayout(null);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    exitButton.setIcon(buttonPressImg);
                    backgroundSound.resumeAudio();
                    shopSound.stop();
                } catch (Exception ex) {} 
            }
            public void mouseReleased(MouseEvent e) {
                exitButton.setIcon(buttonImg);
                setVisible(false);
            }
        });
             
        pricetext = new JTextField();
        pricetext.setBounds(460, 100, 120, 40);
        pricetext.setLayout(null);
        pricetext.setBackground(Color.GRAY);
        pricetext.setForeground(Color.WHITE);
        pricetext.setFont(new Font("Segeo Script", Font.BOLD, 20));
        
        pricetext.setEditable(false);
        pricetext.setVisible(true);
        canvaspane.add(pricetext);
        
        amount = new JSpinner(value);
        amount.setBounds(310,100,70,40);
        amount.getEditor().getComponent(0).setBackground(Color.GRAY);
        amount.getEditor().getComponent(0).setForeground(Color.WHITE);
        amount.setFont(new Font("Segeo Script", Font.BOLD, 20));
        amount.setVisible(true);
        amount.addChangeListener(new ChangeListener() {  
        public void stateChanged(ChangeEvent e) {  
            amt = (int) ((JSpinner)e.getSource()).getValue();
            sum = amt* price;
            pricetext.setText("                   "+Integer.toString(sum));
            repaint();
            warnmoney.setVisible(false);
        }  
        });
        canvaspane.add(amount);
        
        buyButton  = new JLabel(buttonImg);
        buyButton.setIcon(buttonImg);
        buyButton.setBounds(250, 200, 150, 40);
        buyButton.setVisible(true);
        buyButton.setLayout(null);
        buyButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                buyButton.setIcon(buttonPressImg);
            }
            public void mouseReleased(MouseEvent e) {
                buyButton.setIcon(buttonImg);
                boymoney = boy.getMoney();
                boy.decreseMoney(sum);
                if(boymoney<sum){
                    warnmoney.setVisible(true);
                }
                else {
                    if(mark!=0) boy.updateItem(mark,amt);
                }
            }
        });
        canvaspane.add(buyButton);
        
        String[] item = {"SELECT ITEM","BOW","POTION","STAFF SILVER","SWORD RED","SWORD GOLD"};
        catalogue = new JComboBox(item);
        catalogue.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                switch (catalogue.getSelectedIndex()){
                    case 1 :{  
                            showItempane.setIcon(bowImg);
                            showItempane.setVisible(true);
                            amount.setVisible(true);
                            price = 700; mark=0;
                            nametext.setText("Bow");
                            nametext.setBounds(290, 19, 300, 300);
                            detailtext.setText(Integer.toString(price) + "  OBB / ITEM ");
                            detailtext.setBounds(350 , 190 , 300 , 300);
                            detailtext.setVisible(true);
                            nametext.setVisible(true);
                            pricetext.setText("                   "+Integer.toString(price));
                            repaint();
                            warnmoney.setVisible(false);
                            break;
                          }
                    case 2 :{  
                            showItempane.setIcon(potionImg);
                            showItempane.setVisible(true);
                            amount.setVisible(true);
                            price = 500;mark=1;
                            nametext.setText("Potion");
                            nametext.setBounds(290, 19, 300, 300);
                            detailtext.setText(Integer.toString(price) + "  OBB / ITEM ");
                            detailtext.setBounds(350 , 190 , 300 , 300);
                            detailtext.setVisible(true);
                            nametext.setVisible(true);
                            pricetext.setText("                   "+Integer.toString(price));
                            repaint();
                            warnmoney.setVisible(false);
                            break;
                          }
                    case 3 :{  
                            showItempane.setIcon(staffSilverImg);
                            showItempane.setVisible(true);
                            amount.setVisible(true);
                            price = 1000;mark=2;
                            nametext.setText("Staff Silver");
                            nametext.setBounds(250, 19, 300, 300);
                            detailtext.setText(Integer.toString(price) + "  OBB / ITEM ");
                            detailtext.setBounds(350 , 190 , 300 , 300);
                            detailtext.setVisible(true);
                            nametext.setVisible(true);
                            pricetext.setText("                   "+Integer.toString(price));
                            repaint();
                            warnmoney.setVisible(false);
                            break;
                          }
                    case 4 :{  
                            showItempane.setIcon(swordRedImg);
                            showItempane.setVisible(true);
                            amount.setVisible(true);
                            price = 3500;mark=3;
                            nametext.setText("Sword Red");
                            nametext.setBounds(250, 19, 300, 300);
                            detailtext.setText(Integer.toString(price) + "  OBB / ITEM ");
                            detailtext.setBounds(350 , 190 , 300 , 300);
                            detailtext.setVisible(true);
                            nametext.setVisible(true);
                            pricetext.setText("                   "+Integer.toString(price));
                            repaint();
                            warnmoney.setVisible(false);
                            break;
                          }
                    case 5 :{  
                            showItempane.setIcon(swordGoldImg);
                            showItempane.setVisible(true);
                            amount.setVisible(true);
                            price = 5000;mark=4;
                            nametext.setText("Sword Gold");
                            nametext.setBounds(250, 19, 300, 300);
                            detailtext.setText(Integer.toString(price) + "  OBB / ITEM ");
                            detailtext.setBounds(350 , 190 , 300 , 300);
                            detailtext.setVisible(true);
                            nametext.setVisible(true);
                            pricetext.setText("                   "+Integer.toString(price));
                            repaint();
                            warnmoney.setVisible(false);
                            break;
                          }
                }
            
            }
        } );
        
        warnmoney = new JTextArea("NOT ENOUGH MONEY !!");
        warnmoney.setBounds(220, 40, 400, 22);
        warnmoney.setLayout(null);
        warnmoney.setBackground(new Color(0, 0, 0, 0));
        warnmoney.setForeground(Color.WHITE);
        warnmoney.setFont(new Font("Segeo Script", Font.BOLD, 20));
        warnmoney.setVisible(false);
        canvaspane.add(warnmoney);
                
        catalogue.setBounds(80,100,150,40);
        catalogue.setBackground(Color.gray);
        catalogue.setForeground(Color.WHITE);
        catalogue.setFont(new Font("Segeo Script", Font.BOLD, 15));
        catalogue.setVisible(true);
        canvaspane.add(catalogue);
                
        nametext = new JTextArea();
        nametext.setLayout(null);
        nametext.setBackground(new Color(0, 0, 0, 0));
        nametext.setForeground(Color.WHITE);
        nametext.setFont(new Font("Segeo Script", Font.BOLD, 30));
        nametext.setVisible(false);
        detailpane.add(nametext);
        
        detailtext = new JTextArea();
        detailtext.setLayout(null);
        detailtext.setBackground(new Color(0, 0, 0, 0));
        detailtext.setForeground(Color.WHITE);
        detailtext.setFont(new Font("Segeo Script", Font.BOLD, 30));
        detailtext.setVisible(false);
        detailpane.add(detailtext);
        
        backgroundpane = new JLabel();
        backgroundpane.setIcon(storeImg);
        backgroundpane.setBounds(0, 0,frameWidth, frameHeight);
        backgroundpane.setLayout(null);
        
        backgroundpane.add(exit);
        backgroundpane.add(exitButton);
        backgroundpane.add(vendorpane);
        backgroundpane.add(canvaspane);
        backgroundpane.add(detailpane);
        
        contentpane.add(backgroundpane, BorderLayout.CENTER);
        validate();
  
    }
}
class MyImageIconStore extends ImageIcon {

    public MyImageIconStore(String fname) {
        super(fname);
    }

    public MyImageIconStore(Image image) {
        super(image);
    }

    public MyImageIconStore resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIconStore(newimg);
    }   
}