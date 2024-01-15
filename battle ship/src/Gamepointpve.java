import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gamepointpve extends JFrame {
    private int bPoint=0;
    private int rPoint=0;
    private Font defaultFont = new Font("tahoma", Font.BOLD, 60);
    private int seconds=0;
    private GameBoard my;
    Timer t;

    public Gamepointpve(GameBoard mygame,GameBoard enemyGame,Game game){
        my=mygame;
        setTitle("Points");
        setSize(200,170);
        setResizable(false);
        setLocation(0,0);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        t=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                rPoint=mygame.score();
                bPoint=enemyGame.score();
                repaint();
                if(mygame.first_place){
                    if(game.isMoveisdone()){
                        if(enemyGame.getMoney()<1){
                            Sell randomsel=new Sell(enemyGame);
                            randomsel.randomsell();
                            randomsel.dispose();
                        }
                        if(enemyGame.getMoney()<1){
                            winner winner=new winner(false);
                            game.setEnabled(false);
                            winner.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    game.dispose();
                                }
                            });
                            t.stop();
                        }else {
                            ATTACK atran=new ATTACK(mygame,enemyGame);
                            atran.atack_random();
                            atran.dispose();
                        }
                        game.firstPaint=true;
                        game.repaint();
                        game.setMoveisdone(false);
                    }
                    if(rPoint==0){
                            winner winner=new winner(false);
                        game.setEnabled(false);
                        winner.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                game.dispose();
                            }
                        });
                        t.stop();
                    }
                    if(bPoint==0){
                        winner winner=new winner(true);
                        game.setEnabled(false);
                        winner.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                game.dispose();
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
        g.clearRect(0,0,g.getClipBounds().width,g.getClipBounds().height);
        g.setFont(defaultFont);
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(rPoint),30,100);
        g.setColor(Color.RED);
        g.drawString(String.valueOf(bPoint),115,100);
        g.setColor(Color.BLACK);
        g.drawString(String.format("%02d:%02d",seconds/60,seconds%60),15,150);
    }
}
