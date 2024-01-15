import javax.swing.*;
import java.util.Random;

public class RANDOM_PLACE extends JFrame {
    GameBoard board,eBoard;
    private Random random=new Random();
    private boolean randomplace=true;
    public RANDOM_PLACE(GameBoard board,GameBoard eBoard) {
        this.board = board;
        this.eBoard=eBoard;
        random_place(this.board);
        firstplaceisdone();
    }
    public void random_place(GameBoard eBoard){
        Random random=new Random();
        int i,j,z=0;
        while (z<3){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,1,1,eBoard)) {
                eBoard.setship(i,j,1,1);
                z++;
            }
        }
        z=0;while (z<2){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,2,1,eBoard)) {
                eBoard.setship(i,j,2,1);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,1,2,eBoard)) {
                eBoard.setship(i,j,1,2);
                z++;
            }
        }
        z=0;while (z<2){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,3,1,eBoard)) {
                eBoard.setship(i,j,3,1);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(z<2&&shipplacerandom(i,j,1,3,eBoard)) {
                eBoard.setship(i,j,1,3);
                z++;
            }
        }
        z=0;while (z<1){
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,2,4,eBoard)) {
                eBoard.setship(i,j,2,4);
                z++;
            }
            j=random.nextInt(eBoard.getBoardWidth());
            i=random.nextInt(eBoard.getBoardHeight());
            if(shipplacerandom(i,j,4,2,eBoard)&&z<1) {
                eBoard.setship(i,j,4,2);
                z++;
            }
        }
    }
    private boolean shipplacerandom(int x,int y,int sizey,int sizex,GameBoard eBoard){
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
    public void firstplaceisdone(){
        if(board.getType()){
            random_place(eBoard);
        }
    }
}