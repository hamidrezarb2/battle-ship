import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Random;

public class firstplace extends JFrame {
    GameBoard board,eBoard;
    Rectangle[][] rectangles;
    private int cellSize;
    final int margin,marginx;
    private int tempX,tempY;
    private Random random=new Random();
    boolean wasPlayed = false;
    boolean firstPaint = true;
    final int[] shipsnum=new int[4];
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;
    private boolean randomplace=false;

    public firstplace(GameBoard board,GameBoard eBoard) {
        this.board = board;
        this.eBoard=eBoard;
        margin = 50;
        marginx=50;
        if(board.getBoardHeight()<25)cellSize = 40;
        if(board.getBoardWidth()>46)cellSize=36;
        if(24<board.getBoardHeight()&&board.getBoardHeight()<33)cellSize=30;
        if(board.getBoardHeight()>32)cellSize=19;
        setSize(board.getBoardWidth() * cellSize + marginx+ margin,
                board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setTitle(board.name+ " placeship");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
                    if (board.getState(tempX, tempY) != 0) {
                        tempX = tempY = -1;
                    }
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
    //place randomly
    public void random_place(){
        Random random=new Random();
        int i=0,j=0,z=0;
        //boat
        while (z<3){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,1,1)) {
                eBoard.setship(i,j,1,1);
                z++;
            }
        }
        //destroyer
        z=0;while (z<2){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,2,1)) {
                eBoard.setship(i,j,2,1);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,1,2)) {
                eBoard.setship(i,j,1,2);
                z++;
            }
        }
        //cruiser
        z=0;while (z<2){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,3,1)) {
                eBoard.setship(i,j,3,1);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,1,3)) {
                eBoard.setship(i,j,1,3);
                z++;
            }
        }
        //battle ship
        z=0;while (z<1){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,2,4)) {
                eBoard.setship(i,j,2,4);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,4,2)&&z<1) {
                eBoard.setship(i,j,4,2);
                z++;
            }
        }
    }
    //ask type of ship
    private void placeship(int x,int y){
        String[] options = new String[]{"Boat","Leteral Destoyer",
                "Vertical Destroyer", "Leteral Cruiser", "Vertical Cruiser",
                "Leteral Battleship", "Vertical Battleship"};
        setEnabled(false);
        int answer = JOptionPane.showOptionDialog(this,
          "what you want?", "Red Player",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        switch (answer){
            case 0:
                if(shipplace(x,y,1,1)&&shiplimit(0)){
                    board.setship(x,y,1,1);
                    shipsnum[0]++;
                    wasPlayed = true;
                }
                break;
            case 1:
                if(shipplace(x,y,2,1)&&shiplimit(1)){
                    board.setship(x,y,2,1);
                    shipsnum[1]++;
                    wasPlayed = true;
                }
                break;
            case 2:
                if(shipplace(x,y,1,2)&&shiplimit(1)){
                    board.setship(x,y,1,2);
                    shipsnum[1]++;
                    wasPlayed = true;
                }
                break;
            case 3:
                if(shipplace(x,y,3,1)&&shiplimit(2)){
                    board.setship(x,y,3,1);
                    shipsnum[2]++;
                    wasPlayed = true;
                }
                break;
            case 4:
                if(shipplace(x,y,1,3)&&shiplimit(2)){
                    board.setship(x,y,1,3);
                    shipsnum[2]++;
                    wasPlayed = true;
                }
                break;
            case 5:
                if(shipplace(x,y,4,2)&&shiplimit(3)){
                    board.setship(x,y, 4,2);
                    shipsnum[3]++;
                    wasPlayed = true;
                }
                break;
            case 6:
                if(shipplace(x,y,2,4)&&shiplimit(3)){
                    board.setship(x,y,2,4);
                    shipsnum[3]++;
                    wasPlayed = true;
                }
                break;
        }
        repaint();
        setEnabled(true);
        requestFocus();
        firstplaceisdone();
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
        }
        if (wasPlayed) {
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            int count=0;
            while (board.getShips(count,0)!=-1){
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
                count++;
            }
            currentX = currentY = oldCurrentX = oldCurrentY = -1;
            wasPlayed = false;
        } else {
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
    //if ship can't place in selected location
    private boolean shipplace(int x,int y,int sizey,int sizex){
        for (int i = 0; i < sizex; i++) {
            if(x+i>=board.getBoardWidth()){
                if(!randomplace)
                JOptionPane.showMessageDialog(this,
                "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            for (int j = 0; j < sizey; j++) {
                if(j+y>=board.getBoardHeight()){
                    if(!randomplace)
                        JOptionPane.showMessageDialog(this,
               "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(board.getState(x+i,y+j)!=0){
                    if(!randomplace)
                        JOptionPane.showMessageDialog(this,
                "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
    //if ship can't place in selected location in random place
    private boolean shipplacerandom(int x,int y,int sizey,int sizex){
        for (int i = 0; i < sizex; i++) {
            if(x+i>=eBoard.getBoardWidth()){
                if(!randomplace)
                    JOptionPane.showMessageDialog(this,
                            "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            for (int j = 0; j < sizey; j++) {
                if(j+y>=eBoard.getBoardHeight()){
                    if(!randomplace)
                        JOptionPane.showMessageDialog(this,
                                "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if(eBoard.getState(x+i,y+j)!=0){
                    if(!randomplace)
                        JOptionPane.showMessageDialog(this,
                                "cant place here","Warning",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
    //if you cant place more ship from selected kind
    private boolean shiplimit(int kind){
        if(kind==0&&shipsnum[kind]==3){
            if(!randomplace)
                JOptionPane.showMessageDialog(this,
            "cant place more boats","Warning",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(kind==1&&shipsnum[kind]==2){
            if(!randomplace)
                JOptionPane.showMessageDialog(this,
         "cant place more destroyer","Warning",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(kind==2&&shipsnum[kind]==2){
            if(!randomplace)
                JOptionPane.showMessageDialog(this,
          "cant place more cruiser","Warning",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(kind==3&&shipsnum[kind]==1){
            if(!randomplace)
                JOptionPane.showMessageDialog(this,
       "cant place more Battleship","Warning",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    //if placing the ship is done
    public boolean firstplaceisdone(){
        if(shipsnum[0]!=3)return false;
        if(shipsnum[1]!=2)return false;
        if(shipsnum[2]!=2)return false;
        if(shipsnum[3]!=1)return false;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if(board.getType()){
            randomplace=true;
            random_place();
            this.dispose();
        }
        return true;
    }
}