import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {
    final Font defaultFont2 = new Font("tahoma", Font.BOLD, 20);
    final Font defaultFont3 = new Font("tahoma", Font.TRUETYPE_FONT, 20);
    GameBoard board,eboard;//ypur map,enemy map
    Rectangle[][] rectangles,rectangles2;//base map
    private int cellSize;
    final int margin,marginx;
    final JMenuBar menuBar;
    final JMenu menu;
    private String name=new String();
    private boolean firstplacedone=false,moveisdone=false;
    public boolean firstPaint;
    private int currentX = -1, currentY = -1, oldCurrentX = -1, oldCurrentY = -1;//mouse location
    public JMenuItem mo;
    ImageIcon bship=new ImageIcon("bship.jpg");

    public Game(GameBoard board, GameBoard eboard) {
        this.name=board.name;
        this.board = board;
        this.eboard=eboard;
        //map graphic
        margin = 40;
        marginx=105;
        firstPaint=true;
        if(board.getBoardHeight()<22)cellSize = 40;
        if(board.getBoardWidth()>46)cellSize=36;
        if(21<board.getBoardHeight()&&board.getBoardHeight()<29)cellSize=30;
        if(board.getBoardHeight()>32)cellSize=17;
        setSize(board.getBoardWidth() * 2 * cellSize + 2*marginx+ margin,
                board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(50,50,1,1));
        setTitle(board.name);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        initTable();
        //pictures
        JLabel ship2=new JLabel(bship);
        ship2.setBounds(board.getBoardWidth()*cellSize+marginx-7,0,101,570);
        JLabel ship1=new JLabel(bship);
        add(ship2);add(ship1);
        ship1.setVisible(false);
        //init option menu
        menuBar=new JMenuBar();
        menu=new JMenu("options");
        menu.setVisible(true);
        menuBar.setVisible(true);
        mo=new JMenuItem("$="+board.getMoney());
        mo.setEnabled(false);
        mo.setFont(defaultFont2);
        JMenuItem Attack=new JMenuItem("Attack");
        Attack.setFont(defaultFont2);
        JMenuItem Puase=new JMenuItem("Puase");
        Puase.setFont(defaultFont2);
        JMenuItem Sell=new JMenuItem("Sell");
        Sell.setFont(defaultFont2);
        JMenuItem random_place=new JMenuItem("random" );
        random_place.setFont(defaultFont3);
        JMenuItem Search=new JMenuItem("Search");
        Search.setFont(defaultFont2);
        JMenuItem Buy=new JMenuItem("Buy");
        Buy.setFont(defaultFont2);
        JMenuItem firstplace=new JMenuItem("place");
        firstplace.setFont(defaultFont2);
        JMenuItem help=new JMenuItem("help");
        help.setFont(defaultFont2);
        //add option menu
        menu.add(mo);
        menu.add(Attack);
        menu.add(Search);
        menu.add(Sell);
        menu.add(Buy);
        menu.add(firstplace);
        menu.add(random_place);
        menu.add(help);
        menu.add(Puase);
        menu.setFont(defaultFont2);
        menuBar.add(menu);
        //init menu
        this.setJMenuBar(menuBar);
        menuBar.setVisible(true);
        menu.setVisible(true);
        //explaining the map
        JLabel explain=new JLabel();
        explain.setFont(defaultFont2);
        this.add(explain,BorderLayout.PAGE_END);
        //menu actionlistener
        Attack.addActionListener(e -> {
            if (board.getMoney() > 1) {
                if (firstplacedone && !moveisdone) {
                    ATTACK Attackoption = new ATTACK(eboard, board);
                    setVisible(false);
                    moveisdone = true;
                    board.move = true;
                    Attackoption.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            setVisible(true);
                            firstPaint = true;
                            setmo();
                        }
                    });
                } else if (!firstplacedone) JOptionPane.showMessageDialog(this,
                        "please place your ships first",
                        "cant open", JOptionPane.ERROR_MESSAGE);
                else if (moveisdone) JOptionPane.showMessageDialog(this,
                        "SORRY NOT YOUR TURN PLEASE CHANGE",
                        "cant open", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"you are out of money");
            }
        });
        Puase.addActionListener(e -> {
            puase Attackoption = new puase();
            setVisible(false);
            Attackoption.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setVisible(true);
                    firstPaint = true;
                }
            });
        });
        Search.addActionListener(e -> {
            if(firstplacedone&&!moveisdone){
                Search Searchoption=new Search(eboard,board);
                setVisible(false);
                board.move=true;
                moveisdone=true;
                Searchoption.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                        firstPaint=true;
                    }
                });
                setmo();
            }
            else if(!firstplacedone)JOptionPane.showMessageDialog(this,
                    "please place your ships first",
                    "cant open",JOptionPane.ERROR_MESSAGE);
            else if(moveisdone)JOptionPane.showMessageDialog(this,
                    "SORRY NOT YOUR TURN PLEASE CHANGE",
                    "cant open",JOptionPane.ERROR_MESSAGE);
        });
        Sell.addActionListener(e -> {
            if(firstplacedone){
                Sell Selloption=new Sell(board);
                setVisible(false);
                Selloption.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                        firstPaint=true;
                        setmo();
                    }
                });
            }
            else if(!firstplacedone) JOptionPane.showMessageDialog(this,
                    "please place your ships first",
                    "cant open",JOptionPane.ERROR_MESSAGE);
            else if(moveisdone)JOptionPane.showMessageDialog(this,
                    "SORRY NOT YOUR TURN PLEASE CHANGE",
                    "cant open",JOptionPane.ERROR_MESSAGE);
        });
        Buy.addActionListener(e -> {
            if(firstplacedone){
                Buy Buyoption=new Buy(board);
                setVisible(false);
                Buyoption.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                        firstPaint=true;
                        setmo();
                    }
                });
            }
            else if(!firstplacedone) JOptionPane.showMessageDialog(this,
                    "please place your ships first",
                    "cant open",JOptionPane.ERROR_MESSAGE);
            else if(moveisdone)JOptionPane.showMessageDialog(this,
                    "SORRY NOT YOUR TURN PLEASE CHANGE",
                    "cant open",JOptionPane.ERROR_MESSAGE);
        });
        firstplace.addActionListener(e -> {
            if(!firstplacedone){
                firstplace firstplaceoption=new firstplace(board,eboard);
                setVisible(false);
                if (!board.getType()){
                    moveisdone=true;
                    board.move=true;
                }
                firstplaceoption.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        setVisible(true);
                        firstPaint=true;
                        firstplacedone=true;
                        board.first_place=true;
                    }
                });
                firstplace.setEnabled(false);
            }
            else if(firstplacedone)JOptionPane.showMessageDialog(this,
                    "ships are already placed",
                    "cant open",JOptionPane.ERROR_MESSAGE);
            else if(moveisdone)JOptionPane.showMessageDialog(this,
                    "SORRY NOT YOUR TURN PLEASE CHANGE",
                    "cant open",JOptionPane.ERROR_MESSAGE);
        });
        help.addActionListener(e -> {
            HELP Helpoption=new HELP();
            setVisible(false);
            Helpoption.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setVisible(true);
                    firstPaint=true;
                }
            });
        });
        random_place.addActionListener(e -> {
            if(!firstplacedone){
                RANDOM_PLACE firstplaceoption=new RANDOM_PLACE(board,eboard);
                if (!board.getType()){
                    moveisdone=true;
                    board.move=true;
                }
                firstPaint=true;
                repaint();
                firstplacedone=true;
                board.first_place=true;
                firstplace.setEnabled(false);
            }
            else if(moveisdone)JOptionPane.showMessageDialog(this,
                    "SORRY NOT YOUR TURN PLEASE CHANGE",
                    "cant open",JOptionPane.ERROR_MESSAGE);
            else {
                if(board.getMoney()<1){
                    Sell randomsel=new Sell(board);
                    randomsel.randomsell();
                    randomsel.dispose();
                }
                if(board.getMoney()<1){
                    JOptionPane.showMessageDialog(null,"you loose","winner",JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }else {
                    ATTACK atran=new ATTACK(eboard,board);
                    atran.atack_random();
                    atran.dispose();
                    moveisdone = true;
                    board.move = true;
                    firstPaint=true;
                    repaint();
                }
            }
        });
      //  pack();
        this.setVisible(true);
        //mouse action listener
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int tempX = -1;
                int tempY = -1;
                //if mouse is in map
                if (e.getX() >= marginx &&
                        e.getY() >= margin &&
                        e.getX() <= cellSize * board.getBoardWidth() + marginx - 4 &&
                        e.getY() <= cellSize * board.getBoardHeight() + margin - 4) {
                    tempX = (e.getY() - margin) / cellSize;
                    tempY = (e.getX() - marginx) / cellSize;
                    //explain mouse location
                    if (board.getState(tempX, tempY) == 0||board.getState(tempX, tempY) == 4) {
                        if(firstplacedone)
                            explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is empty , you can place a ship here form option->Buy");
                        else
                            explain.setText("your map is empty , you can place your ships form option->place");
                    }else if (board.getState(tempX, tempY) == 1||board.getState(tempX, tempY) == 5) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is part of a ship , you can sell it from option->Sell");
                    }else if (board.getState(tempX, tempY) == 2) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is part of a destroyed ship");
                    }else if (board.getState(tempX, tempY) == 3) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is under enemy's attack");
                    }else if (board.getState(tempX, tempY) == 7) {
                        explain.setText("matrix " + (tempX+1) + ":" + (tempY+1) + " is a destroyed part of a ship");
                    }else if (board.getState(tempX, tempY) == 6) {
                        explain.setText("matrix " + (tempX + 1) + ":" + (tempY + 1) + " is a min");
                    }else if (board.getState(tempX, tempY) == 8) {
                        explain.setText("matrix " + (tempX + 1) + ":" + (tempY + 1) + " is a destroyed mine");
                    }
                    if (board.getState(tempX, tempY) != 0){
                        tempX = tempY = -1;
                    }
                    explain.repaint();
                }
                //if mouse location hast changed or is onboard
                if (tempX != currentX || tempY != currentY) {
                    oldCurrentX = currentX;
                    oldCurrentY = currentY;
                    currentX = tempX;
                    currentY = tempY;
                    repaint();
                }
            }

        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            }
        });
        repaint();
    }
    private void initTable() {//set location of the matrix rectangels
        rectangles = new Rectangle[board.getBoardHeight()][board.getBoardWidth()];
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                rectangles[i][j] = new Rectangle(j * cellSize + marginx, i * cellSize + margin,
                        cellSize, cellSize);
            }
        }
        rectangles2 = new Rectangle[eboard.getBoardHeight()][eboard.getBoardWidth()];
        for (int i = 0; i < eboard.getBoardHeight(); i++) {
            for (int j = 0; j < eboard.getBoardWidth(); j++) {
                rectangles2[i][j] = new Rectangle((j+board.getBoardHeight()) * cellSize + 2*marginx-1, i * cellSize + margin,
                        cellSize, cellSize);
            }
        }
    }
    public boolean isFirstplacedone() {
        return firstplacedone;
    }
    public int score(){
        return board.score();
    }
    public void setMoveisdone(boolean moveisdone) {
        this.moveisdone = moveisdone;
    }
    public boolean isMoveisdone() {
        return moveisdone;
    }
    public void paint(Graphics g) {
        if (firstPaint) {
            super.paint(g);
            //clear map
            g.setColor(Color.white);
            g.drawRect(0,0,getWidth(),getHeight());
            //set rectangels stroke
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            //show rectangels
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    g.setColor(Color.black);
                    g.drawRect(rectangles[i][j].x, rectangles[i][j].y,
                            rectangles[i][j].width, rectangles[i][j].height);
                    g.setColor(Color.white);
                    g.fillRect(rectangles[i][j].x+2, rectangles[i][j].y+2,
                            rectangles[i][j].width-4, rectangles[i][j].height-4);
                    g.setColor(Color.black);
                    g.drawRect(rectangles2[i][j].x, rectangles2[i][j].y,
                            rectangles2[i][j].width, rectangles2[i][j].height);
                    g.setColor(Color.white);
                    g.fillRect(rectangles2[i][j].x+2, rectangles2[i][j].y+2,
                            rectangles2[i][j].width-4, rectangles2[i][j].height-4);
                }
            }
            //show ship
            for (int count = 0; count < board.getCounter(); count++) {
                g.setColor(Color.BLUE);
                g.fillRect(board.getShips(count,1)*cellSize+marginx+2,
                        board.getShips(count,0)*cellSize+margin+2,
                        board.getShips(count,2)*cellSize-4,
                        board.getShips(count,3)*cellSize-4);
                if(board.getShips(count,4)==1){
                    g.setColor(new Color(101,67,33));
                    g.fillRoundRect(board.getShips(count,1)*cellSize+marginx+3,
                            board.getShips(count,0)*cellSize+margin+3,
                            board.getShips(count,2)*cellSize-6,
                            board.getShips(count,3)*cellSize-6,
                            30,30);
                }
                else if(board.getShips(count,4)==0){
                    g.setColor(Color.RED);
                    g.fillRoundRect(board.getShips(count,1)*cellSize+marginx+3,
                            board.getShips(count,0)*cellSize+margin+3,
                            board.getShips(count,2)*cellSize-6,
                            board.getShips(count,3)*cellSize-6,
                            30,30);
                }
            }
            //show enemys ship
            for (int count = 0; count < eboard.getCounter(); count++) {
                if(eboard.getShips(count,4)==0){
                    g.setColor(Color.BLUE);
                    g.fillRect((eboard.getShips(count,1)+board.getBoardHeight())*cellSize+2*marginx+1,
                            eboard.getShips(count,0)*cellSize+margin+2,
                            eboard.getShips(count,2)*cellSize-4,
                            eboard.getShips(count,3)*cellSize-4);
                    g.setColor(Color.RED);
                    g.fillRoundRect((eboard.getShips(count,1)+board.getBoardHeight())*cellSize+2*marginx+3,
                            eboard.getShips(count,0)*cellSize+margin+3,
                            eboard.getShips(count,2)*cellSize-6,
                            eboard.getShips(count,3)*cellSize-6,
                            30,30);
                }
            }
            //paint houses of map
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    if(board.getState(i,j)==0);
                    else if (board.getState(i, j) == 3) {
                        g.setColor(Color.BLUE);
                        g.fillRect(rectangles[i][j].x + 2,
                                rectangles[i][j].y + 2,
                                rectangles[i][j].width - 4,
                                rectangles[i][j].height - 4);
                    }
                    else if (board.getState(i, j) == 7) {
                        g.setColor(Color.red);
                        g.fillRoundRect(rectangles[i][j].x + 6,
                                rectangles[i][j].y + 6,
                                rectangles[i][j].width - 12,
                                rectangles[i][j].height - 12,
                                30, 30);
                    }
                    else if (board.getState(i, j) == 6) {
                        g.setColor(Color.white);
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
                    if (eboard.getState(i, j) == 3) {
                        g.setColor(Color.BLUE);
                        g.fillRect(rectangles2[i][j].x + 2,
                                rectangles2[i][j].y + 2,
                                rectangles2[i][j].width - 4,
                                rectangles2[i][j].height - 4);
                    }
                    else if (eboard.getState(i, j) == 4) {
                        g.setColor(Color.white);
                        g.fillRect(rectangles2[i][j].x +2,
                                rectangles2[i][j].y + 2,
                                rectangles2[i][j].width - 4,
                                rectangles2[i][j].height - 4);
                        g.setColor(Color.BLUE);
                        g.fillRoundRect(rectangles2[i][j].x + 3,
                                rectangles2[i][j].y + 3,
                                rectangles2[i][j].width - 6,
                                rectangles2[i][j].height - 6,
                                30, 30);
                    }
                    else if (eboard.getState(i, j) == 5) {
                        g.setColor(Color.white);
                        g.fillRect(rectangles2[i][j].x +2,
                                rectangles2[i][j].y + 2,
                                rectangles2[i][j].width - 4,
                                rectangles2[i][j].height - 4);
                        g.setColor(new Color(101,67,33));
                        g.fillRoundRect(rectangles2[i][j].x + 3,
                                rectangles2[i][j].y + 3,
                                rectangles2[i][j].width - 6,
                                rectangles2[i][j].height - 6,
                                30, 30);
                    }
                    else if (eboard.getState(i, j) == 8) {
                        g.setColor(Color.black);
                        g.fillRect(rectangles2[i][j].x +2,
                                rectangles2[i][j].y + 2,
                                rectangles2[i][j].width - 4,
                                rectangles2[i][j].height - 4);
                        g.setColor(Color.magenta);
                        g.fillRoundRect(rectangles2[i][j].x + 3,
                                rectangles2[i][j].y + 3,
                                rectangles2[i][j].width - 6,
                                rectangles2[i][j].height - 6,
                                30, 30);
                    }
                    else if (eboard.getState(i, j) == 7) {
                        g.setColor(Color.red);
                        g.fillRoundRect(rectangles2[i][j].x + 6,
                                rectangles2[i][j].y + 6,
                                rectangles2[i][j].width - 12,
                                rectangles2[i][j].height - 12,
                                30, 30);
                    }
                }
            }
            firstPaint = false;
        }
        //mouse move paint
        g.setColor(Color.YELLOW);
        if (currentX != -1) {
            g.fillRect(rectangles[currentX][currentY].x + 2, rectangles[currentX][currentY].y + 2,
                    rectangles[currentX][currentY].width - 4, rectangles[currentX][currentY].height - 4);
        }
        g.setColor(Color.WHITE);
        if (oldCurrentX != -1)
            g.fillRect(rectangles[oldCurrentX][oldCurrentY].x + 2, rectangles[oldCurrentX][oldCurrentY].y + 2,
                    rectangles[oldCurrentX][oldCurrentY].width - 4, rectangles[oldCurrentX][oldCurrentY].height - 4);
    }
    public void setmo(){//repaint money in menu
        mo.setText("$="+board.getMoney());
    }
}