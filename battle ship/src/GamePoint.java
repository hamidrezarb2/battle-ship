import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePoint extends JFrame {
    private int bPoint=0;
    private int rPoint=0;
    private Font defaultFont = new Font("tahoma", Font.BOLD, 60);
    private Font defaultFont2 = new Font("tahoma", Font.BOLD, 20);
    private int seconds=0;
    private boolean b1=true;
    ImageIcon bship=new ImageIcon("b8.jpg");
    Timer t;

    public GamePoint(Game mygame,Game enemyGame){
        setTitle("Points");
        setSize(200,205);
        setResizable(false);
        setLocation(0,0);
        JButton startGame = new JButton("Change");
        startGame.setFont(defaultFont2);
        add(startGame, BorderLayout.PAGE_END);
        startGame.setVisible(true);
        startGame.addActionListener(e -> {
            if((mygame.isVisible() && mygame.isFirstplacedone()&&mygame.isMoveisdone())||(enemyGame.isVisible() && enemyGame.isFirstplacedone()&&enemyGame.isMoveisdone())){
                JFrame bsh=new JFrame("are you ready(click on picture)");
                bsh.setSize(1400,700);
                bsh.setLocationRelativeTo(null);
                bsh.setResizable(false);
                bsh.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JButton ready=new JButton();
                ready.setIcon(bship);
                ready.addActionListener(e1 -> {
                    bsh.dispose();
                });
                bsh.add(ready);
                bsh.setVisible(true);
                if(mygame.isVisible() && mygame.isFirstplacedone()&&mygame.isMoveisdone())
                    b1=true;
                else if(enemyGame.isVisible() && enemyGame.isFirstplacedone()&&enemyGame.isMoveisdone())
                    b1=false;
                mygame.setVisible(false);
                enemyGame.setVisible(false);
                ready.addActionListener(e1 -> {
                    if(b1){
                        mygame.setVisible(false);
                        enemyGame.setVisible(true);
                        enemyGame.firstPaint=true;
                        enemyGame.repaint();
                        enemyGame.setMoveisdone(false);
                    }
                    else {
                        mygame.setVisible(true);
                        enemyGame.setVisible(false);
                        mygame.firstPaint=true;
                        mygame.repaint();
                        mygame.setMoveisdone(false);
                    }
                });
            }
            else {
                JOptionPane.showMessageDialog(enemyGame,
                        "please place make a move first",
                        "cant open",JOptionPane.ERROR_MESSAGE);
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        t=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                rPoint=mygame.score();
                bPoint=enemyGame.score();
                repaint();
                if(mygame.isFirstplacedone()&&enemyGame.isFirstplacedone()){
                    if(rPoint==0){
                        winner winner=new winner(false);
                        mygame.setEnabled(false);
                        enemyGame.setEnabled(false);
                        winner.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                mygame.dispose();
                                enemyGame.dispose();
                            }
                        });
                        t.stop();
                    }
                    if(bPoint==0){
                        winner winner=new winner(true);
                        mygame.setEnabled(false);
                        enemyGame.setEnabled(false);
                        winner.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                mygame.dispose();
                                enemyGame.dispose();
                            }
                        });
                        t.stop();
                    }
                }
            }
        });
        t.start();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.clearRect(0,0,g.getClipBounds().width,160);
        g.setFont(defaultFont);
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(rPoint),30,100);
        g.setColor(Color.RED);
        g.drawString(String.valueOf(bPoint),115,100);
        g.setColor(Color.BLACK);
        g.drawString(String.format("%02d:%02d",seconds/60,seconds%60),15,150);
    }
}
