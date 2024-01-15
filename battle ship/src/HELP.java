import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Random;

public class HELP extends JFrame {
    final Font defaultFont2 = new Font("tahoma", Font.BOLD, 20);

    public HELP() {
        setSize(700,700);
        setLocationRelativeTo(null);
        setTitle("Help");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        //init label for every kinf of state
        JLabel j0=new JLabel("        means this location is empty");
        JLabel j1=new JLabel("        means this location is part of a ship");
        JLabel j2=new JLabel("        means this location is part of a destroyed ship");
        JLabel j3=new JLabel("        means this location is under enemys attack");
        JLabel j5=new JLabel("        means this location was part of a ship at last search");
        JLabel j4=new JLabel("        means this location was empty at last search");
        JLabel j6=new JLabel("        means this location is a destroyed part of a ship");
        JLabel j7=new JLabel("with attak option you can destroy enemys ships");
        JLabel j8=new JLabel("with search option you can search enemys map");
        JLabel j9=new JLabel("with sell option you can sell your own ships");
        JLabel j10=new JLabel("with buy option you can buy every kind if ships");
        JLabel j11=new JLabel("with place option you can place ships in your map for first time");
        JLabel j12=new JLabel("with random option you can place your ships or attack randomly");
        JLabel j13=new JLabel("money is based on 100 and every move has a cost");
        JLabel j14=new JLabel("you have two different type of attack and 4 different type of ships");
        j0.setFont(defaultFont2);
        j4.setFont(defaultFont2);
        j1.setFont(defaultFont2);
        j2.setFont(defaultFont2);
        j3.setFont(defaultFont2);
        j5.setFont(defaultFont2);
        j6.setFont(defaultFont2);
        j7.setFont(defaultFont2);
        j8.setFont(defaultFont2);
        j9.setFont(defaultFont2);
        j10.setFont(defaultFont2);
        j11.setFont(defaultFont2);
        j12.setFont(defaultFont2);
        j13.setFont(defaultFont2);
        j14.setFont(defaultFont2);
        setLayout(new GridLayout(15,1));
        add(j0);
        add(j1);
        add(j2);
        add(j3);
        add(j4);
        add(j5);
        add(j6);
        add(j7);
        add(j8);
        add(j9);
        add(j10);
        add(j11);
        add(j12);
        add(j13);
        add(j14);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.setColor(Color.BLACK);
        g.drawRect(12,48,40, 40);
        g.drawRect(12,91,40, 40);
        g.drawRect(12,132,40, 40);
        g.drawRect(12,175,40, 40);
        g.drawRect(12,217,40, 40);
        g.drawRect(12,261,40, 40);
        g.drawRect(12,305,40, 40);
        g.setColor(Color.white);
        g.fillRect(12,48,40, 40);
        g.setColor(Color.white);
        g.fillRect(12,305,40, 40);
        g.fillRect(12,261,40, 40);
        g.fillRect(12,217,40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(12,91,40, 40);
        g.fillRect(12,132,40, 40);
        g.fillRect(12,175,40, 40);
        g.fillRoundRect(12 ,217,40,40,60, 60);
        g.setColor(new Color(101,67,33));
        g.fillRoundRect(12 ,91,40,40,60, 60);
        g.fillRoundRect(12 ,261,40,40,60, 60);
        g.setColor(Color.red);
        g.fillRoundRect(12 ,305,40,40,60, 60);
        g.fillRoundRect(12,132,40, 40,60,60);

    }

}