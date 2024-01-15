import javafx.scene.effect.ImageInput;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private boolean gameType;// true = PvE   false= PvP
    private int gameWidth, gameHeight;
    private Font defaultFont = new Font("tahoma", Font.BOLD, 20);
    private JTextField heightTxt, widthTxt,p1txt,p2txt;
    private Main me;
    public GameBoard mygameBoard;
    public GameBoard enemygameBoard;
    public JButton startGame;
    public JToggleButton gamePvETypeBtn ,gamePvPTypeBtn;
    public JPanel topPnl;
    public JLabel widthLbl,heightLbl,p1name,p2name;
    public ImageIcon icon = new ImageIcon("b4.jpg");

    public Main() {
        setSize(1243, 700);
        setLocationRelativeTo(null);
        setTitle("Battleship");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        me = this;
        gameWidth = 15;
        gameHeight = 15;
        //pcture
        add(new JLabel(icon));
        pack();
        initTopPanel();
        initCenterPanel();
        initBotPanel();
        setVisible(true);
    }
    //start button
    private void initBotPanel() {
        startGame = new JButton("Start!!");
        startGame.setFont(defaultFont);
        add(startGame, BorderLayout.PAGE_END);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkSize()) {//if size is in range
                    if(!gameType){//for dou game
                        mygameBoard = new GameBoard(gameWidth, gameHeight, gameType,p1txt.getText());
                        enemygameBoard = new GameBoard(gameWidth, gameHeight, gameType,p2txt.getText());
                        Game mygame = new Game(mygameBoard,enemygameBoard);
                        Game enemygame = new Game(enemygameBoard,mygameBoard);
                        GamePoint gp=new GamePoint(mygame,enemygame);
                        enemygame.setVisible(false);
                        setVisible(false);
                        //back to start menu if game page closes
                        mygame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                setVisible(true);
                                gp.setVisible(false);
                            }
                        });
                        enemygame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                gp.setVisible(false);
                                setVisible(true);
                            }
                        });
                    }else{//for solo play
                        mygameBoard = new GameBoard(gameWidth, gameHeight, gameType,p1txt.getText());
                        enemygameBoard = new GameBoard(gameWidth, gameHeight, gameType,p2txt.getText());
                        Game mygame = new Game(mygameBoard,enemygameBoard);
                        Gamepointpve gp=new Gamepointpve(mygameBoard,enemygameBoard,mygame);
                        setVisible(false);
                        //back to start menu if game page closes
                        mygame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                setVisible(true);
                                gp.setVisible(false);
                            }
                        });
                    }
                } else {//if size isn't in range
                    JOptionPane.showMessageDialog(me,
                            "Enter number between 15 and 50!!"
                            , "Error"
                            , JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    //size is in range or not
    private boolean checkSize() {
        if (gameWidth == -1)//draw red rect if not
            widthTxt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        else
            widthTxt.setBorder(new JTextField().getBorder());
        if (gameHeight == -1)//draw red rect if not
            heightTxt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        else
            heightTxt.setBorder(new JTextField().getBorder());
        return gameWidth != -1 && gameHeight != -1;
    }
    //size and names
    private void initCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(2, 4, 10,10));
         widthLbl = new JLabel("Width");
        widthLbl.setFont(defaultFont);
        widthLbl.setHorizontalAlignment(SwingConstants.CENTER);
         heightLbl = new JLabel("Height");
        heightLbl.setFont(defaultFont);
        heightLbl.setHorizontalAlignment(SwingConstants.CENTER);

        p1name = new JLabel("p1 name");
        p1name.setFont(defaultFont);
        p1name.setHorizontalAlignment(SwingConstants.CENTER);
        p2name = new JLabel("p2 name");
        p2name.setFont(defaultFont);
        p2name.setHorizontalAlignment(SwingConstants.CENTER);
        //check changes that happend on texts
        DocumentListener isValidNumberListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int sizeTemp = -1;
                    String st=new String();
                    st=e.getDocument().getText(0, e.getDocument().getLength())+"";
                    if (isValidNumber(e.getDocument().getText(0, e.getDocument().getLength())))
                        sizeTemp = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    if (e.getDocument().getProperty("owner").equals("width"))
                        gameWidth = sizeTemp;
                    else if (e.getDocument().getProperty("owner").equals("height"))
                        gameHeight = sizeTemp;
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            public boolean isValidNumber(String str) {
                if (isNumber(str))
                    return isValid(str);
                return false;
            }
            //is a number on field
            public boolean isNumber(String str) {
                return str.matches("[\\d]+");
            }
            //is number in range
            public boolean isValid(String str) {
                int i = Integer.parseInt(str);
                return i >= 15 && i <= 50;
            }
        };

        widthTxt = new JTextField("15");
        widthTxt.getDocument().addDocumentListener(isValidNumberListener);
        widthTxt.setFont(defaultFont);
        widthTxt.setHorizontalAlignment(JTextField.CENTER);
        widthTxt.getDocument().putProperty("owner", "width");

        heightTxt = new JTextField("15");
        heightTxt.setFont(defaultFont);
        heightTxt.setHorizontalAlignment(JTextField.CENTER);
        heightTxt.getDocument().addDocumentListener(isValidNumberListener);
        heightTxt.getDocument().putProperty("owner", "height");

        p1txt=new JTextField("player1");
        p1txt.setFont(defaultFont);
        p1txt.setHorizontalAlignment(JTextField.CENTER);
        p1txt.getDocument().putProperty("owner", "p1");

        p2txt=new JTextField("player2");
        p2txt.setFont(defaultFont);
        p2txt.setHorizontalAlignment(JTextField.CENTER);
        p2txt.getDocument().putProperty("owner", "p2");

        centerPanel.add(widthLbl);
        centerPanel.add(heightLbl);
        centerPanel.add(p1name);
        centerPanel.add(p2name);
        centerPanel.add(widthTxt);
        centerPanel.add(heightTxt);
        centerPanel.add(p1txt);
        centerPanel.add(p2txt);

        centerPanel.setBorder(new EmptyBorder(450,650,50,150));
        add(centerPanel,BorderLayout.CENTER);


    }
    //solo or dou
    private void initTopPanel() {
        topPnl = new JPanel(new FlowLayout());
        gamePvETypeBtn = new JToggleButton("PvE");
        gamePvPTypeBtn = new JToggleButton("PvP");
        gamePvPTypeBtn.setSelected(true);
        gameType = false;
        topPnl.add(gamePvETypeBtn);
        topPnl.add(gamePvPTypeBtn);
        gamePvETypeBtn.setActionCommand("PvE");
        gamePvPTypeBtn.setActionCommand("PvP");
        gamePvETypeBtn.setFont(defaultFont);
        gamePvPTypeBtn.setFont(defaultFont);
        //a;ways one down and one up
        ActionListener gameTypeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("PvE")) {
                    gamePvPTypeBtn.setSelected(!gamePvPTypeBtn.isSelected());
                } else {
                    gamePvETypeBtn.setSelected(!gamePvETypeBtn.isSelected());
                }
                gameType = gamePvETypeBtn.isSelected();
            }
        };
        gamePvETypeBtn.addActionListener(gameTypeListener);
        gamePvPTypeBtn.addActionListener(gameTypeListener);
        add(topPnl, BorderLayout.PAGE_START);

    }
    public void paint(Graphics g){
        super.paint(g);
        startGame.repaint();
        gamePvPTypeBtn.repaint();
        gamePvETypeBtn.repaint();
        widthLbl.repaint();
        heightLbl.repaint();
        widthTxt.repaint();
        heightTxt.repaint();
        p1txt.repaint();
        p1name.repaint();
        p2name.repaint();
        p2txt.repaint();
    }
    public static void main(String[] args) {
        new Main();
    }

}