package guiproject;

import guiproject.mainMap.MyBoy;
import guiproject.SimpleAudioPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FindDiamonQuest extends JFrame implements MouseListener , MouseMotionListener {
    
    private JPanel contentpane;
    private JLabel backgroundpane, oldmanpane, bookpane, crystalpane, helmetpane, keypane, necklacepane, scrollpane, scorepane;
    private JLabel   bookBox, crystalBox, helmetBox, keyBox, necklaceBox, scrollBox, scoreBox;
    private JLabel empty1,empty2;
    private JTextArea counttext,giventext;
    private DialogLabel dialog;
    private MyImageIcon3 oldmanhouseImg, oldmanImg, bookImg, crystalImg, helmetImg, keyImg, necklaceImg, scrollImg, scoreImg,boxImg;
    private MyImageIcon3 bookfoundImg, crystalfoundImg, helmetfoundImg, keyfoundImg, necklacefoundImg, scrollfoundImg,oldmanhouseImg2;
    private MyImageIcon3 bookshowImg, crystalshowImg, helmetshowImg, keyshowImg, necklaceshowImg, scrollshowImg;
    private JButton SkipButton,SkipButton2,exitButton;
    private MyButton bookButton,keyButton,crystalButton,helmetButton,necklaceButton,scrollButton;
    private int frameWidth = 1152, frameHeight = 768;
    private int count=0;
    private boolean drag=true;
    private MyBoy boy;
    private SimpleAudioPlayer nolanSound,backgorundSound; 


    public FindDiamonQuest(MyBoy b,SimpleAudioPlayer audio) {
        
        backgorundSound=audio;
        boy = b;
        setTitle("FindDiamondQuest");
        setBounds(0, 0, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        
        AddComponents();
        Oldmangame();

    }

    public void AddComponents() {
        try {
            nolanSound = new SimpleAudioPlayer("resources/soundtrack/q3.wav");
        } catch (Exception e) {
            
        } 
        nolanSound.play();
        oldmanhouseImg = new MyImageIcon3("resources/quest3/3oldmanhouse.jpg").resize(frameWidth, frameHeight);
        oldmanhouseImg2 = new MyImageIcon3("resources/quest3/oldmanhouse.jpg").resize(frameWidth, frameHeight);
        oldmanImg = new MyImageIcon3("resources/quest3/oldman.png").resize(435, 720);
        bookImg = new MyImageIcon3("resources/quest3/book_06b.png").resize(38, 38);
        crystalImg = new MyImageIcon3("resources/quest3/crystal_01b.png").resize(20, 20);
        helmetImg = new MyImageIcon3("resources/quest3/helmet_02b.png").resize(25,25);
        keyImg = new MyImageIcon3("resources/quest3/key_02a.png").resize(45, 45);
        necklaceImg = new MyImageIcon3("resources/quest3/necklace_02a.png").resize(20, 20);
        scrollImg = new MyImageIcon3("resources/quest3/scroll_01a.png").resize(33, 33);
        scoreImg = new MyImageIcon3("resources/quest3/showScore.png").resize(200, 62);
        boxImg = new MyImageIcon3("resources/quest3/box.png").resize(160, 160);
       
        bookfoundImg = new MyImageIcon3("resources/quest3/book_found.png").resize(90, 90);
        crystalfoundImg = new MyImageIcon3("resources/quest3/crystal_found.png").resize(70, 70);
        helmetfoundImg = new MyImageIcon3("resources/quest3/helmet_found.png").resize(65, 65);
        keyfoundImg = new MyImageIcon3("resources/quest3/key_found.png").resize(85, 85);
        necklacefoundImg = new MyImageIcon3("resources/quest3/necklace_found.png").resize(65, 65);
        scrollfoundImg = new MyImageIcon3("resources/quest3/scroll_found.png").resize(70, 70);
        
        bookshowImg = new MyImageIcon3("resources/quest3/book_show.png").resize(70, 70);
        crystalshowImg = new MyImageIcon3("resources/quest3/crystal_show.png").resize(70, 70);
        helmetshowImg = new MyImageIcon3("resources/quest3/helmet_show.png").resize(70, 70);
        keyshowImg = new MyImageIcon3("resources/quest3/key_show.png").resize(70, 70);
        necklaceshowImg = new MyImageIcon3("resources/quest3/necklace_show.png").resize(70, 70);
        scrollshowImg = new MyImageIcon3("resources/quest3/scroll_show.png").resize(70, 70);
        

        oldmanpane = new JLabel(oldmanImg);
        oldmanpane.setBounds(390,50, 435, 720);
        oldmanpane.setVisible(false);
        oldmanpane.setLayout(null);

        bookpane = new JLabel(bookImg);
        bookpane.setBounds(230, 600, 38, 38);
        bookpane.setVisible(false);
        bookpane.setLayout(null);

        crystalpane = new JLabel(crystalImg);
        crystalpane.setBounds(382,108, 25, 25);
        crystalpane.setVisible(false);
       crystalpane.setLayout(null);

        helmetpane = new JLabel(helmetImg);
        helmetpane.setBounds(875, 410, 25, 25);
        helmetpane.setVisible(false);
        helmetpane.setLayout(null);

        keypane = new JLabel(keyImg);
        keypane.setBounds(80, 78, 45,45);
        keypane.setVisible(false);
        keypane.setLayout(null);

        necklacepane = new JLabel(necklaceImg);
        necklacepane.setBounds(260, 265, 20, 20);
        necklacepane.setVisible(false);
        necklacepane.setLayout(null);

        scrollpane = new JLabel(scrollImg);
        scrollpane.setBounds(440, 290, 33, 33);
        scrollpane.setVisible(false);
        scrollpane.setLayout(null);

        scorepane = new JLabel(scoreImg);
        scorepane.setBounds(850, 25, 200, 62);
        scorepane.setVisible(false);
        scorepane.setLayout(null);
        
        bookBox = new JLabel(boxImg);
        bookBox.setBounds(40,500, 160, 160);
        bookBox.setVisible(false);
        bookBox.setLayout(null);

        crystalBox = new JLabel(boxImg);
        crystalBox.setBounds(210,500, 160, 160);
        crystalBox.setVisible(false);
       crystalBox.setLayout(null);

        helmetBox = new JLabel(boxImg);
        helmetBox.setBounds(400,500, 160, 160);
        helmetBox.setVisible(false);
        helmetBox.setLayout(null);

        keyBox = new JLabel(boxImg);
        keyBox.setBounds(580,500, 160, 160);
        keyBox.setVisible(false);
        keyBox.setLayout(null);

        necklaceBox = new JLabel(boxImg);
        necklaceBox.setBounds(760,500, 160, 160);
        necklaceBox.setVisible(false);
        necklaceBox.setLayout(null);

        scrollBox = new JLabel(boxImg);
        scrollBox.setBounds(940,500, 160, 160);
        scrollBox.setVisible(false);
        scrollBox.setLayout(null);

        empty1 = new JLabel();
        empty1.setBounds(485,68,210,220);
        empty1.setVisible(false);
        empty1.setLayout(null);
  
        
        empty2 = new JLabel();
        empty2.setBounds(405,288,390,220);
        empty2.setVisible(false);
        empty2.setLayout(null);
  
        
        backgroundpane = new JLabel();
        backgroundpane.setIcon(oldmanhouseImg);
        backgroundpane.setBounds(0, 0,frameWidth, frameHeight);
        backgroundpane.setLayout(null);

        exitButton  = new JButton("EXIT");
        exitButton.setBounds(920, 680, 95, 30);
        exitButton.setVisible(false);
        exitButton.setLayout(null);
        
        SkipButton2 = new JButton("DONE");
        SkipButton2.setBounds(920, 650, 95, 30);
        SkipButton2.setVisible(false);
        SkipButton2.setLayout(null);
        
        bookButton  = new MyButton(85,540,bookshowImg,empty1,empty2);
        bookButton.setVisible(false);
        crystalButton  = new MyButton(255,540,crystalshowImg,empty1,empty2);
        crystalButton.setVisible(false);
        keyButton  = new MyButton(445,540,keyshowImg,empty1,empty2);
        keyButton.setVisible(false);
        helmetButton  = new MyButton(625,540,helmetshowImg,empty1,empty2);
        helmetButton.setVisible(false);
        necklaceButton  = new MyButton(805,540,necklaceshowImg,empty1,empty2);
        necklaceButton.setVisible(false);
        scrollButton  = new MyButton(985,540,scrollshowImg,empty1,empty2);
        scrollButton.setVisible(false);
        
        giventext = new JTextArea("Drag all item to the oldman");
        giventext.setBounds(60, 50, 330, 100);
        giventext.setLayout(null);
        giventext.setBackground(new Color(0, 0, 0, 0));
        giventext.setForeground(Color.YELLOW);
        giventext.setFont(new Font("Segeo Script", Font.BOLD,25));
        giventext.setVisible(false);
        giventext.setEditable(false);
        
        backgroundpane.add(giventext, BorderLayout.CENTER);
        backgroundpane.add(empty1);
        backgroundpane.add(empty2);
        backgroundpane.add(bookpane);
        backgroundpane.add(crystalpane);
        backgroundpane.add(helmetpane);
        backgroundpane.add(keypane);
        backgroundpane.add(necklacepane);
        backgroundpane.add(scrollpane);
        backgroundpane.add(scorepane);
        backgroundpane.add(scorepane);
        backgroundpane.add(exitButton);
        backgroundpane.add(SkipButton2);
        backgroundpane.add(bookButton);
        backgroundpane.add(crystalButton);
        backgroundpane.add(keyButton);
        backgroundpane.add(helmetButton);
        backgroundpane.add(necklaceButton);
        backgroundpane.add(scrollButton);
        backgroundpane.add(bookBox);
        backgroundpane.add(crystalBox);
        backgroundpane.add(keyBox);
        backgroundpane.add(helmetBox);
        backgroundpane.add(necklaceBox);
        backgroundpane.add(scrollBox);
        backgroundpane.add(oldmanpane);
        

        contentpane.add(backgroundpane, BorderLayout.CENTER);
        validate();

    }
 
    public void Oldmangame() {
        //empty.setVisible(true);
        backgroundpane.setVisible(true);
        bookpane.setVisible(true);
        crystalpane.setVisible(true);
        helmetpane.setVisible(true);
        keypane.setVisible(true);
        necklacepane.setVisible(true);
        scrollpane.setVisible(true);
        scorepane.setVisible(true);
        SkipButton2.setVisible(true);

        bookpane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (bookpane.getIcon() == bookImg) {
                    bookpane.setBounds(210, 570, 90,90);
                    bookpane.setIcon(bookfoundImg);
                    setCount();

                }
            }
        });

        crystalpane.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (crystalpane.getIcon() == crystalImg) {
                    crystalpane.setBounds(360, 90, 70, 70);
                    crystalpane.setIcon(crystalfoundImg);
                    setCount();
                    
                }

            }
        });

        helmetpane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (helmetpane.getIcon() == helmetImg) {
                    helmetpane.setBounds(850, 385, 65, 65);
                    helmetpane.setIcon(helmetfoundImg);
                    setCount();
                    
                }

            }
        });

        keypane.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (keypane.getIcon() == keyImg) {
                    keypane.setBounds(65, 60, 85, 85);
                    keypane.setIcon(keyfoundImg);
                    setCount();
                }
            }
        });

        necklacepane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (necklacepane.getIcon() == necklaceImg) {
                    necklacepane.setBounds(250, 230, 65, 65);
                    necklacepane.setIcon(necklacefoundImg);
                    setCount();
                }
            }
        });

        scrollpane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               
                if (scrollpane.getIcon() == scrollImg) {
                    scrollpane.setBounds(420, 275, 70, 70);
                    scrollpane.setIcon(scrollfoundImg);
                    setCount();
                }
            }
        });
        counttext = new JTextArea("0/6");
        counttext.setBounds(935, 45, 330, 330);
        counttext.setLayout(null);
        counttext.setBackground(new Color(0, 0, 0, 0));
        counttext.setForeground(Color.YELLOW);
        counttext.setFont(new Font("Segeo Script", Font.BOLD, 20));
        contentpane.add(counttext, BorderLayout.CENTER);
        
           SkipButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ie) {
                if(count==6){
                     if (ie.getSource() == SkipButton2) {
                    bookpane.setVisible(false);
                    crystalpane.setVisible(false);
                    helmetpane.setVisible(false);
                    keypane.setVisible(false);
                    necklacepane.setVisible(false);
                    scrollpane.setVisible(false);
                    scorepane.setVisible(false);
                    counttext.setVisible(false);
                    SkipButton2.setVisible(false);
                    OldmansceneEnd();
                 }
              }
            }
        });
        contentpane.add(backgroundpane, BorderLayout.CENTER);
        validate();
    }
    
     public void OldmansceneEnd() {
         giventext.setVisible(true);
         empty1.setVisible(true);
         empty2.setVisible(true);
         oldmanpane.setIcon(oldmanhouseImg2);
        oldmanpane.setVisible(true);
        bookBox.setVisible(true);
        crystalBox.setVisible(true);
        keyBox.setVisible(true);
        helmetBox.setVisible(true);
        necklaceBox.setVisible(true);
        scrollBox.setVisible(true);
        bookButton.setVisible(true);
        crystalButton.setVisible(true);
        keyButton.setVisible(true);
        helmetButton.setVisible(true);
        necklaceButton.setVisible(true);
        scrollButton.setVisible(true);
        try {
            nolanSound.stop();
        } catch (Exception ex) {
           
        } 
        exitButton.setVisible(true);
       exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ie) {
                if(bookButton.getdragCount()==6){
                    if (ie.getSource() ==  exitButton) {
                         JOptionPane.showMessageDialog(contentpane,
                        " I'll give you a daimond for Thank you!!", "Bianca", JOptionPane.CLOSED_OPTION);
                        boy.updateItem(7, 1);
                        boy.decreseEnergy();
                        setVisible(false);
                    try {
                        backgorundSound.resumeAudio();
                        //nolanSound.stop();
                        } catch (Exception ex) {
                            
                        } 
                }
                }
            }
        });
    }

    public void setCount() {
        count++;
        counttext.setText(Integer.toString(count) + "/6");
        repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}

class MyImageIcon3 extends ImageIcon {

    public MyImageIcon3(String fname) {
        super(fname);
    }

    public MyImageIcon3(Image image) {
        super(image);
    }

    public MyImageIcon3 resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon3(newimg);
    }
}
class MyButton extends JButton implements MouseListener, MouseMotionListener
{
    private int       curX   , curY  ;
    private int       width  =70, height =70;   
    private static int       dragCount=0;
    private boolean   drag=true;
    private JLabel ept1,ept2;
    public MyButton(int x,int y,MyImageIcon3 im ,JLabel top,JLabel low)				
    { 
        curX=x;
        curY=y;
        ept1=top;
        ept2=low;
	setBounds(curX, curY, width, height);
	setIcon(im);
        setLayout(null);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
	addMouseListener( this );
	addMouseMotionListener( this );
    }

    public void mousePressed( MouseEvent e )	{ }
    public void mouseReleased( MouseEvent e )	{ 
        if (this.getBounds().intersects(ept1.getBounds())||this.getBounds().intersects(ept2.getBounds())){
            setVisible(false); setdragCount();
        }
    }
    public void mouseEntered( MouseEvent e )	{ }	
    public void mouseExited( MouseEvent e )	{ }
    public void mouseMoved( MouseEvent e )	{ }
    public void mouseClicked( MouseEvent e )	{
    }
    public void mouseDragged( MouseEvent e )	
    { 
	if ( drag )
	{
            curX =curX + e.getX();
            curY =curY + e.getY();
            Container p = getParent();
            if (curX < 0)  curX = 0;
            if (curY < 0)  curY = 0;
            if (curX + width  > p.getWidth())   curX = p.getWidth() - width;
            if (curY + height > p.getHeight())  curY = p.getHeight() - height;
            
            setLocation(curX, curY);
        }
    }
    public void setdragCount(){
           dragCount++;
   }
    public int getdragCount(){
           return dragCount;
   }
};
