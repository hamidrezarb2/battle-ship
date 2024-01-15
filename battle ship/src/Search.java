import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Search extends JFrame {
    GameBoard board;
    Rectangle[][] rectangles;
    private int cellSize;
    final Font defaultFont2 = new Font("tahoma", Font.BOLD, 20);
    final int margin,marginx;
    private int tempX,tempY;
    private Search me;
    boolean firstPaint = true;
    boolean attackisdone=false;
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;

    public Search(GameBoard board,GameBoard eboard) {
        this.board = board;
        margin = 50;
        marginx=50;
        me=this;
        if(board.getBoardHeight()<25)cellSize = 40;
        if(board.getBoardWidth()>46)cellSize=36;
         if(24<board.getBoardHeight()&&board.getBoardHeight()<33)cellSize=30;
         if(board.getBoardHeight()>32)cellSize=19;
        setSize(board.getBoardWidth() * cellSize + marginx+ margin,
                board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setTitle(eboard.name+" searching "+board.name+" map");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        initTable();
        JLabel explain=new JLabel();
        explain.setFont(defaultFont2);
        this.add(explain,BorderLayout.PAGE_END);
        this.setVisible(true);
        repaint();
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
                    if (board.getState(tempX, tempY) == 4) {
                            explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is empty , or enemy has placed a ship here after your search");
                    }else if (board.getState(tempX, tempY) == 5) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " it was part of a ship at last search, you can destroy it from option->Attack");
                    }else if (board.getState(tempX, tempY) == 2) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is part of a destroyed ship");
                    }else if (board.getState(tempX, tempY) == 3) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is under your attack");
                    }else if (board.getState(tempX, tempY) == 7) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is a destroyed part of a ship");
                    }
                    if (board.getState(tempX, tempY)/2==2){
                        tempX = tempY = -1;
                    }
                    explain.repaint();
                }
                if (tempX != currentX || tempY != currentY) {
                    oldCurrentX = currentX;
                    oldCurrentY = currentY;
                    currentX = tempX;
                    currentY = tempY;
                    repaint();
                }
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentX != -1 && currentY != -1&& !attackisdone &&
                        board.getState(currentX,currentY)<6) {
                    int d=JOptionPane.showConfirmDialog(me,"you sure?");
                    if(d==0){
                        if(5> eboard.getMoney()){
                            JOptionPane.showMessageDialog(null,"dont have enough money"
                                    ,"EROR",JOptionPane.ERROR_MESSAGE);
                        }else {
                            eboard.setmoney(eboard.getMoney()-5);
                            board.searchship(currentY,currentX);
                            repaint();
                            attackisdone=true;
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        }
                    }
                }else if(attackisdone) JOptionPane.showMessageDialog(me,"you have alreay searched","EROR",JOptionPane.ERROR_MESSAGE);
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
    @Override
    public void paint(Graphics g) {
        if (firstPaint) {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    g.drawRect(rectangles[i][j].x, rectangles[i][j].y,
                            rectangles[i][j].width, rectangles[i][j].height);
                }
            }
            firstPaint = false;
        } else {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    if(board.getState(i,j)==0)continue;
                    else if (board.getState(i, j) == 3) {
                        g.setColor(Color.BLUE);
                        g.fillRect(rectangles[i][j].x + 2,
                                rectangles[i][j].y + 2,
                                rectangles[i][j].width - 4,
                                rectangles[i][j].height - 4);
                    }
                    else if (board.getState(i, j) == 4) {
                        g.setColor(Color.white);
                        g.fillRect(rectangles[i][j].x +2,
                                rectangles[i][j].y + 2,
                                rectangles[i][j].width - 4,
                                rectangles[i][j].height - 4);
                        g.setColor(Color.BLUE);
                        g.fillRoundRect(rectangles[i][j].x + 3,
                                rectangles[i][j].y + 3,
                                rectangles[i][j].width - 6,
                                rectangles[i][j].height - 6,
                                30, 30);
                    }
                    else if (board.getState(i, j) == 5) {
                        g.setColor(Color.white);
                        g.fillRect(rectangles[i][j].x +2,
                                rectangles[i][j].y + 2,
                                rectangles[i][j].width - 4,
                                rectangles[i][j].height - 4);
                        g.setColor(new Color(101,67,33));
                        g.fillRoundRect(rectangles[i][j].x + 3,
                                rectangles[i][j].y + 3,
                                rectangles[i][j].width - 6,
                                rectangles[i][j].height - 6,
                                30, 30);
                    }
                }
            }
            int count = 0;
            while (board.getShips(count, 0) != -1) {
                if (board.getShips(count, 4) == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(board.getShips(count, 1) * cellSize + marginx + 2,
                            board.getShips(count, 0) * cellSize + margin + 2,
                            board.getShips(count, 2) * cellSize - 4,
                            board.getShips(count, 3) * cellSize - 4);
                    g.setColor(Color.RED);
                    g.fillRoundRect(board.getShips(count, 1) * cellSize + marginx + 3,
                            board.getShips(count, 0) * cellSize + margin + 3,
                            board.getShips(count, 2) * cellSize - 6,
                            board.getShips(count, 3) * cellSize - 6,
                            30, 30);
                }
                count++;
            }

            g.setColor(Color.YELLOW);
            if (currentX != -1 && board.getState(currentX, currentY)<2)
                g.fillRect(rectangles[currentX][currentY].x + 2,
                        rectangles[currentX][currentY].y + 2,
                        rectangles[currentX][currentY].width - 4,
                        rectangles[currentX][currentY].height - 4);
            g.setColor(Color.WHITE);
            if (oldCurrentX != -1 && board.getState(oldCurrentX, oldCurrentY)<2)
                g.fillRect(rectangles[oldCurrentX][oldCurrentY].x + 2,
                        rectangles[oldCurrentX][oldCurrentY].y + 2,
                        rectangles[oldCurrentX][oldCurrentY].width - 4,
                        rectangles[oldCurrentX][oldCurrentY].height - 4);


        }
    }
}