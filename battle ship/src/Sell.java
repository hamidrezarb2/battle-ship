import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sell extends JFrame {
    GameBoard board;
    Rectangle[][] rectangles;
    private int cellSize;
    final int margin,marginx;
    private int tempX,tempY;
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;
    private int shipcurrent=-1,oldshipcurrent=-1,oldoldship=-1,tempship=-1;
    private boolean firstPaint=true;
    public Sell(GameBoard board) {
        this.board = board;
        margin = 50;
        marginx=50;
        if(board.getBoardHeight()<25)cellSize = 40;
        if(board.getBoardWidth()>46)cellSize=36;
         if(24<board.getBoardHeight()&&board.getBoardHeight()<33)cellSize=30;
         if(board.getBoardHeight()>32)cellSize=19;
        setSize(board.getBoardWidth() * cellSize + marginx+ margin,
                board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setTitle(board.name+" Sell");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//do nothing
        setResizable(false);
        initTable();
        this.setVisible(true);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                tempX = -1;
                tempY = -1;
                if (e.getX() >= marginx && e.getY() >= margin &&
                        e.getX() <= cellSize * board.getBoardWidth() + marginx - 4 &&
                        e.getY() <= cellSize * board.getBoardHeight() + margin - 4) {
                    tempX = (e.getY() - margin) / cellSize;
                    tempY = (e.getX() - marginx) / cellSize;
                    if (board.getState(tempX, tempY) != 1) tempX = tempY = -1;
                }
                if (tempX != currentX || tempY != currentY) {
                    oldCurrentX = currentX;
                    oldCurrentY = currentY;
                    currentX = tempX;
                    currentY = tempY;
                }
                //if mouse is on a ship
                tempship=board.getShipsit(currentX,currentY);
                if(shipcurrent!=tempship){
                    oldshipcurrent=shipcurrent;
                }
                shipcurrent=tempship;
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (shipcurrent!=-1) {
                    board.sellship(shipcurrent);
                    firstPaint=true;
                    oldshipcurrent=-1;
                    repaint();
                }
            }
        });

    }

    private void initTable() {
        rectangles = new Rectangle[board.getBoardHeight()][board.getBoardWidth()];
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                rectangles[i][j] = new Rectangle(j * cellSize + marginx,
                        i * cellSize + margin, cellSize, cellSize);
            }
        }
    }
    //sell randomly
    public void randomsell(){//if is more than 1 ship sell first one
        if(board.getCounter()>1)board.sellship(board.getCounter()-1);
    }
    @Override
    public void paint(Graphics g) {
        if (firstPaint) {
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    g.setColor(Color.BLACK);
                    g.drawRect(rectangles[i][j].x, rectangles[i][j].y,
                            rectangles[i][j].width, rectangles[i][j].height);
                    g.setColor(Color.WHITE);
                    g.fillRect(rectangles[i][j].x+2, rectangles[i][j].y+2,
                            rectangles[i][j].width-4, rectangles[i][j].height-4);
                }
            }
            for (int count = 0; count < board.getCounter(); count++)  {
                g.setColor(Color.WHITE);
                g.fillRect(board.getShips(count, 1) * cellSize + marginx + 2,
                        board.getShips(count, 0) * cellSize + margin + 2,
                        board.getShips(count, 2) * cellSize - 4,
                        board.getShips(count, 3) * cellSize - 4);
                if (board.getShips(count, 4) == 1)
                    g.setColor(new Color(101, 67, 33));
                g.fillRoundRect(board.getShips(count, 1) * cellSize + marginx + 3,
                        board.getShips(count, 0) * cellSize + margin + 3,
                        board.getShips(count, 2) * cellSize - 6,
                        board.getShips(count, 3) * cellSize - 6,
                        30, 30);
            }
            firstPaint = false;
        }
        else {
            if(shipcurrent!=-1){
                g.setColor(Color.YELLOW);
                g.fillRoundRect(board.getShips(shipcurrent, 1) * cellSize + marginx + 3,
                        board.getShips(shipcurrent, 0) * cellSize + margin + 3,
                        board.getShips(shipcurrent, 2) * cellSize - 6,
                        board.getShips(shipcurrent, 3) * cellSize - 6,
                        30, 30);
            }
            if(oldshipcurrent!=-1){
                g.setColor(new Color(101, 67, 33));
                g.fillRoundRect(board.getShips(oldshipcurrent, 1) * cellSize + marginx + 3,
                        board.getShips(oldshipcurrent, 0) * cellSize + margin + 3,
                        board.getShips(oldshipcurrent, 2) * cellSize - 6,
                        board.getShips(oldshipcurrent, 3) * cellSize - 6,
                        30, 30);
            }
            currentX = currentY = oldCurrentX = oldCurrentY = -1;
        }
    }
}