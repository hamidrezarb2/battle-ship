import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Random;

public class winner extends JFrame {
    ImageIcon icon[]=new ImageIcon[2];

    public JLabel[] j=new JLabel[2];
    public winner(boolean w) {//f:p1    true:p2
        setSize(400,400);
        setLocationRelativeTo(null);
        //init pictures
        icon[0] = new ImageIcon("p1.png");
        icon[1] = new ImageIcon("p2.png");
        //p1 win
        j[0]=new JLabel(icon[0]);
        //p2 win
        j[1]=new JLabel(icon[1]);
        if(w)add(j[0]);
        else add(j[1]);
        pack();
        setTitle("victory");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new winner(false);
        new winner(true);

    }
}