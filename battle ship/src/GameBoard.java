import javax.swing.*;

public class GameBoard {
    private int[][] board;// 0 = E   1 = Ship   2 = Destroy  3=at+no+sh    4=search none     5=search ship   6+min    7= part of ship    8=destroyed min
    private int[][] ships=new int[100][7];//x,y,dx,dy,state 0d1f2pd,kind
    //ship count
    private int counter=0;
    private int money=100;
    private GameBoard me;
    private boolean last_attack=true;
    public boolean first_place=false;
    public boolean move=false;
    private boolean type;
    public String name;
    public GameBoard(int w, int h, boolean type,String name) {
        this.name=name;
        this.type=type;
        me=this;
        //base map
        board = new int[h][w];
        for (int i = 0; i < 100; i++) {
            ships[i][0]=-1;
        }
    }
    //return game type   solo=true    dou=false
    public boolean getType(){
        return type;
    }
    //set money
    public void setmoney(int mo){
        this.money=mo;
    }
    //return width
    public void setShip(int count ,int x,int y){
        ships[count][x]=y;
    }
    public int getShip(int count ,int x){
        return ships[count][x];
    }
    public int getBoardWidth() {
        return board[0].length;
    }
    //return height
    public int getBoardHeight() {
        return board.length;
    }
    //return the location state
    public int getState(int x, int y) {
        return board[x][y];
    }
    //set location state
    public void setBoard(int x,int y,int z){
        this.board[x][y]=z;
    }
    //get ship if has location
    public int getShipsit(int y,int x){
        if(x<0)return -1;
        for (int i = 0; i < counter; i++) {
            if (x >= ships[i][1] && x < ships[i][1] + ships[i][2]) {
                if (y >= ships[i][0] && y < ships[i][0] + ships[i][3]) {
                    if(ships[i][6]==0)
                        return i;
                    return -1;
                }
            }
        }
        return -1;
    }
    //set ship information
    public void setship(int x,int y,int sx,int sy){
        ships[counter][0]=x;
        ships[counter][1]=y;
        ships[counter][2]=sx;
        ships[counter][3]=sy;
        ships[counter][6]=0;
        if(sx==1&&sy==1)ships[counter][5]=1;
        else if((sx==1 && sy==2)||(sx==2 && sy==1))ships[counter][5]=2;
        else if((sx==1 && sy==3)||(sx==3 && sy==1))ships[counter][5]=3;
        else if((sx==4 && sy==2)||(sx==2 && sy==4))ships[counter][5]=4;
        ships[counter][4]=1;
        counter++;
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                this.setBoard(x+j,y+i,1);
            }
        }
    }
    //buy option
    public void buyship(int x,int y,int sx,int sy){
        int mon=buyshipmoney(sx,sy);
        if(mon>money){
            JOptionPane.showMessageDialog(null,"dont have enough money"
            ,"EROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        money-=mon;
        ships[counter][0]=x;
        ships[counter][1]=y;
        ships[counter][2]=sx;
        ships[counter][3]=sy;
        if(sx==1&&sy==1)ships[counter][5]=1;
        else if((sx==1 && sy==2)||(sx==2 && sy==1))ships[counter][5]=2;
        else if((sx==1 && sy==3)||(sx==3 && sy==1))ships[counter][5]=3;
        else if((sx==4 && sy==2)||(sx==2 && sy==4))ships[counter][5]=4;
        ships[counter][4]=1;
        counter++;
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                this.setBoard(x+j,y+i,1);
            }
        }
    }
    //get ship informations
    public int getShips(int num,int sxy) {
        if(num>100)return -1;
        return ships[num][sxy];
    }
    //attack
    public void destroyship(int x, int y){
        if(getState(y,x)==6){
            setBoard(y,x,8);return;
        }
        for (int i = 0; i < counter; i++) {
            if(x>=ships[i][1]&&x<ships[i][1]+ships[i][2]){
                if(y>=ships[i][0]&&y<ships[i][0]+ships[i][3]){
                    setBoard(y,x,7);
                    ships[i][6]=1;
                    for (int j = ships[i][1]; j < ships[i][1]+ships[i][2]; j++) {
                        for (int k = ships[i][0]; k < ships[i][0]+ships[i][3]; k++) {
                            if(getState(k,j)==1)return;
                        }
                    }
                    ship_full_destroy(i);
                    return;
                }
            }
        }
        setBoard(y,x,3);
    }
    //ship is destroy or not
    public void ship_full_destroy(int i){
        ships[i][4]=0;
        ships[i][5]=0;
        ships[i][6]=2;
        for (int j = ships[i][1]; j < ships[i][1]+ships[i][2]; j++) {
            for (int k = ships[i][0]; k < ships[i][0]+ships[i][3]; k++) {
                setBoard(k,j,2);
            }
        }
    }
    //search option
    public void searchship(int x, int y){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if(board[y+i][x+j]<2)board[y+i][x+j]+=4;
            }
        }
    }
    //sell option
    public void sellship(int temp){
        this.money+=sellshipmoney(ships[temp][5]);
        for (int i = 0; i < ships[temp][2]; i++) {
            for (int j = 0; j < ships[temp][3]; j++) {
                this.setBoard(ships[temp][0]+j,ships[temp][1]+i,0);
            }
        }
        for (int i = temp; i < counter-1; i++) {
            for (int j = 0; j < 6; j++) {
                ships[i][j]=ships[i+1][j];
            }
        }
        ships[counter-1][1]=ships[counter-1][0]=-1000;
        counter--;
    }
    //ships number
    public int getCounter() {
        return counter;
    }
    //recalculate money after sell
    public int sellshipmoney(int kind){
        if(kind==1)return 4;
        else if(kind==2)return 8;
        else if(kind==3)return 12;
        else if(kind==4)return 40;
        return -1;
    }
    //recalculate money after buy
    public int buyshipmoney(int sx,int sy){
        if(sx==1&&sy==1)return 5;
        else if((sx==1 && sy==2)||(sx==2 && sy==1))return 10;
        else if((sx==1 && sy==3)||(sx==3 && sy==1))return 15;
        else if((sx==4 && sy==2)||(sx==2 && sy==4))return 50;
        return -1;
    }
    //get money
    public int getMoney() {
        return money;
    }
    //recalculate money after attack
    public void attackmoney(boolean kind){
        if(kind)money-=7;
        if(!kind)money-=1;
    }//true=big   false=normal
    //ship house number
    public int score(){
        int s=0;
        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardHeight(); j++) {
                if(getState(i,j)==1||getState(i,j)==5)s++;
            }
        }
        return s;
    }
}