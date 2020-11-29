package guiproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

class DialogLabel extends JLabel implements MouseListener {

    private JTextArea dialogText;
    private String fileName, speaker;
    private JButton ok;
    int dialogLabelWidth = 900, dialogLabelHeight = 210, dialogLabelX = 120, dialogLabelY = 500;
    int lineCount = 0;
    boolean wait = false;

    public DialogLabel() {
        addMouseListener(this);
        MyImageIcon dialogLabelImg = new MyImageIcon("resources/dialogLabelImg.png").resize(dialogLabelWidth, dialogLabelHeight);
        this.setIcon(dialogLabelImg);
        this.setBounds(dialogLabelX, dialogLabelY, dialogLabelWidth, dialogLabelHeight);
        this.setLayout(new FlowLayout(60, 80, 80));

        dialogText = new JTextArea();
        dialogText.setBounds(175, 500 + 80, 820, 120);
        dialogText.setBackground(new Color(0, 0, 0, 0));
        dialogText.setForeground(Color.white);
        dialogText.setFont(new Font("Garamond", Font.BOLD, 24));
        dialogText.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                Change();
               
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

        });
        this.add(dialogText);
        this.setVisible(false);
    }

    public void printDialog(String f) {
        
        fileName = f;
        wait = false;
        System.out.print("in");
        try {
            String s, t = "";

            Scanner fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNext()) {
               
                while (wait == false) {
                    
                    TimeUnit.MILLISECONDS.sleep(800);
                    s = fileScanner.nextLine();
                    if (lineCount == 0) t = s;
                    else t = t + "\n" + s;
                    dialogText.setText(t);
                    this.setVisible(true);
                    lineCount++;
                    if ((lineCount != 0) && (lineCount % 3 == 0)) {
                        wait = true;
                        lineCount = 0;
                    }
                }
                t = "";   
            }    
        } catch (Exception e) {

        }
        wait = true;  
        while(wait == true) {System.out.print("");}
        this.setVisible(false); 
    }

    public void Change() {
        wait = false;
    }

    public void Change2() {

    }

    public void mouseClicked(MouseEvent e) {
        Change();
       
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

}
