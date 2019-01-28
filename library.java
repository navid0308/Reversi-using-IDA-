package assignment2;

import java.util.LinkedList;
import java.util.Queue;

public class library{
    public static int possible(String[][] board,String color,int x,int y)
    {
        int counter=0;
        
        if(!board[x][y].equalsIgnoreCase("x"))
            return counter;
        
        String opp=null;
        if(color.equalsIgnoreCase("w")){opp="b";}
        else if(color.equalsIgnoreCase("b")){opp="w";}
        
        int temp=0;
        int i=x+1,j=y+1;
        while(i<8&&j<8&&board[i][j].equalsIgnoreCase(opp)){temp++;i++;j++;}
        if(i<8&&j<8&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0; 
        
        i=x+1;j=y-1;
        while(i<8&&j>=0&&board[i][j].equalsIgnoreCase(opp)){temp++;i++;j--;}
        if(i<8&&j>=0&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x-1;j=y+1;
        while(i>=0&&j<8&&board[i][j].equalsIgnoreCase(opp)){temp++;i--;j++;}
        if(i>=0&&j<8&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x-1;j=y-1;
        while(i>=0&&j>=0&&board[i][j].equalsIgnoreCase(opp)){temp++;i--;j--;}
        if(i>=0&&j>=0&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x-1;j=y;
        while(i>=0&&board[i][j].equalsIgnoreCase(opp)){temp++;i--;}
        if(i>=0&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x+1;j=y;
        while(i<8&&board[i][j].equalsIgnoreCase(opp)){temp++;i++;}
        if(i<8&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x;j=y-1;
        while(j>=0&&board[i][j].equalsIgnoreCase(opp)){temp++;j--;}
        if(j>=0&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        temp=0;
        
        i=x;j=y+1;
        while(j<8&&board[i][j].equalsIgnoreCase(opp)){temp++;j++;}
        if(j<8&&board[i][j].equalsIgnoreCase(color)){counter+=temp;}
        
        if(counter>0)
            counter++;
        
        return counter;
    }
    
    public static void put(String[][] board,int x,int y,String color)
    {
        String opp=null;
        
        board[x][y]=color;
        
        if(color.equalsIgnoreCase("w"))
            opp="b";
        else if(color.equalsIgnoreCase("b"))
            opp="w";
        
        Queue temp=new LinkedList();
        
        for(int i=x+1,j=y+1;i<8&&j<8;i++,j++)
        {
            if(board[i][j].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(j);
            }
            else if(board[i][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int i=x+1,j=y-1;i<8&&j>=0;i++,j--)
        {
            if(board[i][j].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(j);
            }
            else if(board[i][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int i=x-1,j=y+1;i>=0&&j<8;i--,j++)
        {
            if(board[i][j].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(j);
            }
            else if(board[i][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--)
        {
            if(board[i][j].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(j);
            }
            else if(board[i][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int i=x-1;i>=0;i--)
        {
            if(board[i][y].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(y);
            }
            else if(board[i][y].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();

        for(int i=x+1;i<8;i++)
        {
            if(board[i][y].equalsIgnoreCase(opp))
            {
                temp.add(i);
                temp.add(y);
            }
            else if(board[i][y].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int j=y-1;j>=0;j--)
        {
            if(board[x][j].equalsIgnoreCase(opp))
            {
                temp.add(x);
                temp.add(j);
            }
            else if(board[x][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
        
        for(int j=y+1;j<8;j++)
        {
            if(board[x][j].equalsIgnoreCase(opp))
            {
                temp.add(x);
                temp.add(j);
            }
            else if(board[x][j].equalsIgnoreCase(color))
            {
                reverse(board,temp,color);
                break;
            }
            else
                break;
        }
        temp.clear();
    }
    
    private static void reverse(String[][] board,Queue r,String color)
    {
        while(!r.isEmpty())
        {
            int x=(int)r.remove();
            int y=(int)r.remove();
            if(color.equalsIgnoreCase("w"))
                color="W";
            else if(color.equalsIgnoreCase("b"))
                color="B";
            board[x][y]=color;
        }
    }
    
    public static boolean exc(String[][] board,String s)
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(possible(board, s, i, j)>0)
                    return false;
            }
        }
        return true;
    }
    
    public static void restore(String[][] board, Queue backup)
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(!backup.isEmpty())
                    board[i][j]=(String)backup.remove();
            }
        }
    }
    
    public static Queue takeBackup(String[][] board)
    {
        Queue backup=new LinkedList();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                backup.add(board[i][j]);
            }
        }
        return backup;
    }
    
    public static int countColor(String[][] board,String color)
    {
        int count=0;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j].equalsIgnoreCase(color))
                    count++;
            }
        }
        return count;
    }
    
    public static void delay(int x)
    {
        try{
            Thread.sleep(x);
        }catch(InterruptedException ex){}
    }
}