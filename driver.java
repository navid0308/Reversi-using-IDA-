package assignment2;
import java.util.Queue;
public class Assignment2{

    private static board myBoard;
    private static String coordinates=null;
    private static float timer=0;
    private static void ComputersTurn(boolean showPrev)
    {
        if(!showPrev)
        {
            coordinates="";
            timer=0;
        }
        myBoard.update(false,myBoard.Opponent);
        if(!library.exc(board.board,myBoard.Opponent))
        {
            myBoard.HighLight(0);
            myBoard.updateB("Thinking...","");
            
            library.delay(300);
            Queue temp=computer.compute(board.board,myBoard.Opponent,myBoard.Player);
            
            if(!temp.isEmpty())
            {
                int cx=(int)temp.remove();
                int cy=(int)temp.remove();
                coordinates+="("+(cx+1)+","+(cy+1)+")";
                timer+=computer.getTime();
                myBoard.updateB(coordinates+" selected after "+timer+"s","Depth Reached:"+computer.getDepth());
                library.put(board.board,cx,cy,myBoard.Opponent);
            }
            if(library.exc(board.board,myBoard.Player))
            {
                myBoard.updateB("You have no legal moves.","Computer's turn.");
                ComputersTurn(true);
            }
        }
        else
        {
            if(library.exc(board.board,myBoard.Player))
                myBoard.result();
            else
                myBoard.updateB("Computer has no legal moves.","Your turn.");
        }
        myTurn();
    }
    
    private static void myTurn()
    {
        myBoard.update(true,myBoard.Player);
        if(!library.exc(board.board,myBoard.Player))
        {
            myBoard.HighLight(1);
            
            while(!myBoard.hasSelected()){library.delay(300);}
            Queue temp=myBoard.getSelection();
            //library.delay(300);
            //Queue temp=computer.compute(board.board,myBoard.Player,myBoard.Opponent);
            if(!temp.isEmpty())
            {
                int cx=(int)temp.remove();
                int cy=(int)temp.remove();

                library.put(board.board,cx,cy,myBoard.Player);
                
                myBoard.setXY(-1,-1);
            }
            if(library.exc(board.board,myBoard.Opponent))
            {
                myBoard.updateB("Computer has no legal moves.","Your turn.");
                myTurn();
            }
        }
        else
        {
            if(library.exc(board.board,myBoard.Opponent))
                myBoard.result();
            else
                myBoard.updateB("You have no legal moves.","Computer's turn.");
        }
        ComputersTurn(false);
    }
    
    public static void main(String[] args) {
        myBoard=new board();
        myBoard.myFrame.setVisible(true);
        if(((int)(Math.random()*100+Math.random()*100))%2==0)
            ComputersTurn(false);
        else
            myTurn();
    }
}
