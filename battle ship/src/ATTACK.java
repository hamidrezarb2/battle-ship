import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//
public class ATTACK extends JFrame {
    GameBoard board,eboard;
    Rectangle[][] rectangles;
    private int cellSize;
    final int margin,marginx;
    private Random random=new Random();
    private int tempX,tempY;
    private ATTACK me;
    boolean firstPaint = true;
    boolean attackisdone=false;
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;

    public ATTACK(GameBoard board,GameBoard eboard) {
        this.board = board;
        this.eboard = eboard;
        margin = 50;
        marginx=50;
        if(board.getBoardHeight()<25)cellSize = 40;
        if(board.getBoardWidth()>46)cellSize=36;
        if(24<board.getBoardHeight()&&board.getBoardHeight()<33)cellSize=30;
        if(board.getBoardHeight()>32)cellSize=19;
        setSize(board.getBoardWidth() * cellSize + marginx+ margin,
                board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setTitle(eboard.name+" attacking "+board.name+" map");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        initTable();
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
                    if (board.getState(tempX, tempY)/2==1||board.getState(tempX, tempY)==7||board.getState(tempX, tempY)==8) {
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
                if(eboard.getMoney()<1){//break if player has no money
                    JOptionPane.showMessageDialog(null,"you are out of money");
                    ATTACK.super.dispose();
                }
                //if mouse clicked in board
                if (currentX != -1 && currentY != -1&& !attackisdone &&
                        board.getState(currentX,currentY)/2!=1) {
                    //ask attack type
                    String[] options = new String[]{"NORMAL ATTACK:1","BIG ATTACK:7","vertical line attack:30","horizenical line attack:30"};
                    int d = JOptionPane.showOptionDialog(me,
                            "what you want?", eboard.name,
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    //normal attack
                    if(d==0&&eboard.getMoney()>=1){
                        eboard.attackmoney(false);
                        board.destroyship(currentY,currentX);
                        repaint();
                        if(board.getState(currentX,currentY)==3){
                            attackisdone=true;
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        }
                        if(board.getState(currentX,currentY)==8){
                            attackisdone=true;
                            eboard.destroyship(currentY,currentX);
                            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        }
                    }
                    //big attack
                    if(d==1&&eboard.getMoney()>=7){
                        eboard.attackmoney(true);
                        repaint();
                        attackisdone=true;
                        if(currentX+1<board.getBoardWidth()){
                            if(board.getState(currentX+1,currentY)%6==1)attackisdone=false;
                            board.destroyship(currentY,currentX+1);
                            if(board.getState(currentX+1,currentY)==8){
                                eboard.destroyship(currentY,currentX);
                            }
                        }
                        if(currentY+1<board.getBoardHeight()){
                            if(board.getState(currentX,currentY+1)%6==1)attackisdone=false;
                            board.destroyship(currentY+1,currentX);
                            if(board.getState(currentX,currentY+1)==8){
                                eboard.destroyship(currentY,currentX);
                            }
                        }
                        if(currentY-1>-1){
                            if(board.getState(currentX,currentY-1)%6==1)attackisdone=false;
                            board.destroyship(currentY-1,currentX);
                            if(board.getState(currentX,currentY-1)==8){
                                eboard.destroyship(currentY,currentX);
                            }
                        }
                        if(currentX-1>-1){
                            if(board.getState(currentX-1,currentY)%6==1)attackisdone=false;
                            board.destroyship(currentY,currentX-1);
                            if(board.getState(currentX-1,currentY)==8){
                                eboard.destroyship(currentY,currentX);
                            }
                        }
                        if(board.getState(currentX,currentY)%6==1)attackisdone=false;
                        board.destroyship(currentY,currentX);
                        if(board.getState(currentX,currentY)==8){
                            eboard.destroyship(currentY,currentX);
                        }
                        if(attackisdone)setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                    else if(d==1&&eboard.getMoney()<7)JOptionPane.showMessageDialog(me,"you dont have enough money","EROR",JOptionPane.ERROR_MESSAGE);
                    if(d==2&&eboard.getMoney()>=30){
                        eboard.setmoney(eboard.getMoney()-30);
                        repaint();
                        attackisdone=true;
                        for (int i = 0; i < board.getBoardHeight(); i++) {
                            if(board.getState(i,currentY)%6==1)attackisdone=false;
                            board.destroyship(currentY,i);
                            if(board.getState(i,currentY)==8){
                                eboard.destroyship(currentY,i);
                            }
                        }
                        if(attackisdone)setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                    else if(d==2&&eboard.getMoney()<30)JOptionPane.showMessageDialog(me,"you dont have enough money","EROR",JOptionPane.ERROR_MESSAGE);
                    if(d==3&&eboard.getMoney()>=30){
                        eboard.setmoney(eboard.getMoney()-30);
                        repaint();
                        attackisdone=true;
                        for (int i = 0; i < board.getBoardWidth(); i++) {
                            if(board.getState(currentX,i)%6==1)attackisdone=false;
                            board.destroyship(i,currentX);
                            if(board.getState(currentX,i)==8){
                                eboard.destroyship(i,currentX);
                            }
                        }
                        if(attackisdone)setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    }
                    else if(d==3&&eboard.getMoney()<30)JOptionPane.showMessageDialog(me,"you dont have enough money","EROR",JOptionPane.ERROR_MESSAGE);
                }else if(attackisdone) JOptionPane.showMessageDialog(me,"you have alreay attacked","EROR",JOptionPane.ERROR_MESSAGE);
            }
        });

    }
    //attack randomlly
    public void atack_random(){
        //flag for having house with 7 state with ship neighbor and flag1 for right shot
        boolean flag=false,flag1;
        a:
        do{
            aa:
            do {
                flag1 = false;
                aaa:
                for (int k = 0; k < board.getBoardWidth(); k++) {
                    for (int l = 0; l < board.getBoardHeight(); l++) {
                        if (board.getState(l,k) == 7) {
                            if ((l+1<board.getBoardHeight())&&(board.getState( l + 1,k) == 0 || board.getState( l + 1,k) == 1)) {
                                board.destroyship(k, l + 1);
                                flag = board.getState( l + 1,k) == 3;
                                this.eboard.attackmoney(false);
                                flag1 = true;
                            } else if (l-1>=0&&(board.getState(l - 1,k) == 0 || board.getState(l - 1,k) == 1)) {
                                board.destroyship(k, l - 1);
                                flag = board.getState(l - 1,k) == 3;
                                this.eboard.attackmoney(false);
                                flag1 = true;
                            } else if ((k+1<board.getBoardWidth())&&(board.getState(l,k + 1) == 0 || board.getState(l,k + 1) == 1)) {
                                board.destroyship(k + 1, l);
                                flag = board.getState(l,k + 1) == 3;
                                this.eboard.attackmoney(false);
                                flag1 = true;
                            } else if (k-1>=0&&(board.getState(l,k - 1) == 0 || board.getState(l,k - 1) == 1) ){
                                board.destroyship(k - 1, l);
                                flag = board.getState(l,k - 1) == 3;
                                this.eboard.attackmoney(false);
                                flag1 = true;
                            }
                            if(flag1&&flag){
                                break a;
                            }
                            if(flag1&&!flag){
                                break aaa;
                            }
                        }
                    }
                }
                if(!flag1)break;
            } while (true);
            aa:
            do{
                int j = random.nextInt(board.getBoardWidth());
                int i = random.nextInt(board.getBoardHeight());
                if (board.getState(i, j) / 2 != 1 && board.getState(i, j) != 7) {
                    board.destroyship(j, i);
                    this.eboard.attackmoney(false);
                }
                if (board.getState(i, j) == 3) break a;
                else break aa;
            } while (true) ;
        }while (true);
        this.dispose();
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
                    else if (board.getState(i, j) == 8) {
                        g.setColor(Color.black);
                        g.fillRect(rectangles[i][j].x +2,
                                rectangles[i][j].y + 2,
                                rectangles[i][j].width - 4,
                                rectangles[i][j].height - 4);
                        g.setColor(Color.magenta);
                        g.fillRoundRect(rectangles[i][j].x + 3,
                                rectangles[i][j].y + 3,
                                rectangles[i][j].width - 6,
                                rectangles[i][j].height - 6,
                                30, 30);
                    }
                    else if (board.getState(i, j) == 7) {
                        g.setColor(Color.red);
                        g.fillRoundRect(rectangles[i][j].x + 6,
                                rectangles[i][j].y + 6,
                                rectangles[i][j].width - 12,
                                rectangles[i][j].height - 12,
                                30, 30);
                    }
                }
            }
            int count = 0;
            while (board.getShips(count, 0) != -1) {
                if (board.getShips(count, 4) == 0) {
                    g.setColor(Color.blue);
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
            if (currentX != -1 )
                if(board.getState(currentX, currentY)<2||board.getState(currentX, currentY)==6)
                    g.fillRect(rectangles[currentX][currentY].x + 2,
                            rectangles[currentX][currentY].y + 2,
                            rectangles[currentX][currentY].width - 4,
                            rectangles[currentX][currentY].height - 4);
            g.setColor(Color.WHITE);
            if (oldCurrentX != -1)
                if(board.getState(oldCurrentX, oldCurrentY)<2||board.getState(oldCurrentX, oldCurrentY)==6)
                    g.fillRect(rectangles[oldCurrentX][oldCurrentY].x + 2,
                            rectangles[oldCurrentX][oldCurrentY].y + 2,
                            rectangles[oldCurrentX][oldCurrentY].width - 4,
                            rectangles[oldCurrentX][oldCurrentY].height - 4);

        }
    }
}