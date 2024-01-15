import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.util.ArrayList;

public class Buy extends JFrame {
    GameBoard board;
    Rectangle[][] rectangles;
    private int cellSize;
    final int margin,marginx;
    private int tempX,tempY;
    final int[] shipsnum=new int[4];
    final Font defaultFont2 = new Font("tahoma", Font.BOLD, 20);
    boolean wasPlayed = false,minplaced=false;
    boolean firstPaint = true;
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;
    public Buy(GameBoard board) {
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
        setTitle(board.name+" shop");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        initTable();
        JLabel explain=new JLabel();
        explain.setFont(defaultFont2);
        this.add(explain,BorderLayout.PAGE_END);
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
                    if (board.getState(tempX, tempY) == 0||board.getState(tempX, tempY) == 4) {
                        explain.setText("your map is empty , you can place a ship here by click");
                    }else if (board.getState(tempX, tempY) == 1||board.getState(tempX, tempY) == 5) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is part of a ship");
                    }else if (board.getState(tempX, tempY) == 2) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is part of a destroyed ship");
                    }else if (board.getState(tempX, tempY) == 3) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is under enemy's attack");
                    }else if (board.getState(tempX, tempY) == 7) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is a destroyed part of a ship");
                    }
                    if (board.getState(tempX, tempY) != 0&&board.getState(tempX, tempY) != 4){
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
                if (currentX != -1 && currentY != -1) {
                    placeship(tempX,tempY);
                }
            }
        });

    }

    private void placeship(int x,int y){
        String[] options = new String[]{"Boat:5","Leteral Destoyer:10",
                "Vertical Destroyer:10", "Leteral Cruiser:15", "Vertical Cruiser:15",
                "Leteral Battleship:50", "Vertical Battleship:50","min:2"};
        setEnabled(false);
        int answer = JOptionPane.showOptionDialog(this,
                "what you want?", "Red Player",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        switch (answer){
            case 0:
                if(shipplace(x,y,1,1)){
                    board.buyship(x,y,1,1);
                    shipsnum[0]++;
                    wasPlayed = true;
                }
                break;
            case 1:
                if(shipplace(x,y,2,1)){
                    board.buyship(x,y,2,1);
                    shipsnum[1]++;
                    wasPlayed = true;
                }
                break;
            case 2:
                if(shipplace(x,y,1,2)){
                    board.buyship(x,y,1,2);
                    shipsnum[1]++;
                    wasPlayed = true;
                }
                break;
            case 3:
                if(shipplace(x,y,3,1)){
                    board.buyship(x,y,3,1);
                    shipsnum[2]++;
                    wasPlayed = true;
                }
                break;
            case 4:
                if(shipplace(x,y,1,3)){
                    board.buyship(x,y,1,3);
                    shipsnum[2]++;
                    wasPlayed = true;
                }
                break;
            case 5:
                if(shipplace(x,y,4,2)){
                    board.buyship(x,y, 4,2);
                    shipsnum[3]++;
                    wasPlayed = true;
                }
                break;
            case 6:
                if(shipplace(x,y,2,4)){
                    board.buyship(x,y,2,4);
                    shipsnum[3]++;
                    wasPlayed = true;
                }
                break;
            case 7:
                if(shipplace(x,y,1,1)){
                    board.setBoard(x,y,6);
                    board.setmoney(board.getMoney()-2);
                    minplaced=true;
                    currentX=-1;
                }
                break;
        }
        repaint();
        setEnabled(true);
        requestFocus();
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
                    if(board.getState(i,j)==3)g.fillRoundRect(rectangles[i][j].x+3, rectangles[i][j].y+3,
                            rectangles[i][j].width-6, rectangles[i][j].height-6,30,30);
                    if (board.getState(i,j)==6){
                        g.setColor(Color.magenta);
                        g.fillRoundRect(rectangles[i][j].x+3, rectangles[i][j].y+3,
                                rectangles[i][j].width-6, rectangles[i][j].height-6,30,30);
                        g.setColor(Color.BLACK);
                    }
                }
            }
            for (int count = 0; count < board.getCounter(); count++) {
                g.setColor(Color.WHITE);
                g.fillRect(board.getShips(count,1)*cellSize+marginx+2,
                        board.getShips(count,0)*cellSize+margin+2,
                        board.getShips(count,2)*cellSize-4,
                        board.getShips(count,3)*cellSize-4);
                g.setColor(new Color(101,67,33));
                g.fillRoundRect(board.getShips(count,1)*cellSize+marginx+3,
                        board.getShips(count,0)*cellSize+margin+3,
                        board.getShips(count,2)*cellSize-6,
                        board.getShips(count,3)*cellSize-6,
                        30,30);
            }
            firstPaint = false;
        }
        if (wasPlayed) {
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            for (int count = 0; count < board.getCounter(); count++) {
                g.setColor(Color.WHITE);
                g.fillRect(board.getShips(count,1)*cellSize+marginx+2,
                        board.getShips(count,0)*cellSize+margin+2,
                        board.getShips(count,2)*cellSize-4,
                        board.getShips(count,3)*cellSize-4);
                g.setColor(new Color(101,67,33));
                g.fillRoundRect(board.getShips(count,1)*cellSize+marginx+3,
                        board.getShips(count,0)*cellSize+margin+3,
                        board.getShips(count,2)*cellSize-6,
                        board.getShips(count,3)*cellSize-6,
                        30,30);
            }
            currentX = currentY = oldCurrentX = oldCurrentY = -1;
            wasPlayed = false;
        } else if (minplaced){
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    if (board.getState(i,j)==6){
                        g.setColor(Color.white);
                        g.fillRect(rectangles[i][j].x+2, rectangles[i][j].y+2,
                                rectangles[i][j].width-4, rectangles[i][j].height-4);
                        g.setColor(Color.magenta);
                        g.fillRoundRect(rectangles[i][j].x+3, rectangles[i][j].y+3,
                                rectangles[i][j].width-6, rectangles[i][j].height-6,30,30);
                    }
                }
            }
            minplaced=false;
        }else {
            g.setColor(Color.YELLOW);
            if (currentX != -1)
                g.fillRect(rectangles[currentX][currentY].x + 2,
                        rectangles[currentX][currentY].y + 2,
                        rectangles[currentX][currentY].width - 4,
                        rectangles[currentX][currentY].height - 4);
            g.setColor(Color.WHITE);
            if (oldCurrentX != -1)
                g.fillRect(rectangles[oldCurrentX][oldCurrentY].x + 2,
                        rectangles[oldCurrentX][oldCurrentY].y + 2,
                        rectangles[oldCurrentX][oldCurrentY].width - 4,
                        rectangles[oldCurrentX][oldCurrentY].height - 4);
        }

    }
    private boolean shipplace(int x,int y,int sizey,int sizex){
        for (int i = 0; i < sizex; i++) {
            if(x+i>=board.getBoardWidth()){
                JOptionPane.showMessageDialog(this,
                        "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            for (int j = 0; j < sizey; j++) {
                if(j+y>=board.getBoardHeight()){
                    JOptionPane.showMessageDialog(this,
                            "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(board.getState(x+i,y+j)!=0){
                    JOptionPane.showMessageDialog(this,
                            "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
}