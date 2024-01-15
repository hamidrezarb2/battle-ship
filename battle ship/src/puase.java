import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Random;

public class puase extends JFrame {
    ImageIcon icon[]=new ImageIcon[4];

    public int counter = 1;
    public JLabel[] j=new JLabel[4];
    public puase() {
        setSize(1900,1000);
        //init pictures
        icon[0] = new ImageIcon("b5.jpg");
        icon[1] = new ImageIcon("b6.jpg");
        icon[2] = new ImageIcon("b7.jpg");
        icon[3] = new ImageIcon("b9.jpg");
        j[0]=new JLabel(icon[0]);
        j[1]=new JLabel(icon[1]);
        j[2]=new JLabel(icon[2]);
        j[3]=new JLabel(icon[3]);
        add(j[0]);
        pack();
        setTitle("close to resume");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        this.setVisible(true);
        //change picture every 3 seconds
        Timer t=new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                add(j[counter%4]);
                pack();
                repaint();
            }
        });
        t.start();
    }
}