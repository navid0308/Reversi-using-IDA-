package assignment2;

import java.util.*;

public class computer{
    private static final int consider=4, prefer=8, override=64;
    private static double time;
    private static int Depth;
    public static Queue compute(String[][] board,String myColor,String opp)
    {
        time=System.currentTimeMillis();
        Depth=0;
        Queue options=getOptions(board,myColor,opp);
        Queue Q=new LinkedList();
        int max_x,max_y;
        int max=0;
        boolean starter=true;
        while(!options.isEmpty())
        {
            int x=(int)options.remove();
            int y=(int)options.remove();
            int count=(int)options.remove();
            
            if(library.possible(board,myColor,x,y)>0)
            {
                if(starter||max<count||(max==count&&((int)(Math.random()*100+Math.random()*100))%2==0))
                {
                    starter=false;
                    Q.clear();
                    max=count;
                    max_x=x;
                    max_y=y;
                    Q.add(max_x);
                    Q.add(max_y);
                }
            }
        }
        //System.out.println(max);
        return Q;
    }
    
    private static Queue getOptions(String[][] board,String myColor,String opp)
    {
        Queue options=new LinkedList();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(library.possible(board,myColor,i,j)>0)
                {
                    options.add(i);
                    options.add(j);
                    Queue backup=library.takeBackup(board);
                    options.add(findWeight(board,myColor,opp,i,j,0,false,0));
                    library.restore(board, backup);
                }
            }
        }
        return options;
    }
    
    private static int findWeight(String board[][],String color,String opp,int X,int Y,int count,boolean isOpp,int depth)
    {
        library.put(board, X, Y, color);
        isOpp=!isOpp;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(library.possible(board,opp,i,j)>0)
                {
                    if(isOpp)
                    {
                        count=count-strategy(board,opp,color,i,j)-library.possible(board,opp,i,j);
                    }
                    else
                    {
                        count=count+strategy(board,opp,color,i,j)+library.possible(board,opp,i,j);
                    }
                    
                    if((System.currentTimeMillis()-time<5000))
                    {
                        depth++;
                        if(depth>Depth)
                            Depth=depth;
                        findWeight(board,opp,color,i,j,count,isOpp,depth);
                    }
                }
            }
        }
        return count;
    }
    
    private static int strategy(String[][] board,String color,String opp,int x,int y)
    {
        return (numOfTurnsNow(board,color)+getSpecialPoints(board,color,opp,x,y)+(library.countColor(board,color)-library.countColor(board,opp)));
    }
    
    private static int numOfTurnsNow(String[][] board,String color)
    {
        int turns=0;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(library.possible(board, color,i,j)>0)
                    turns++;
            }
        }
        return turns;
    }
    
    private static int getSpecialPoints(String[][] board,String color,String opp,int x,int y)
    {
        if((x==7&&y==7)||(x==7&&y==0)||(x==0&&y==7)||(x==0&&y==0))
            return override;
        if(onTheEdge(x,y)&&safeEdge(board,color,opp,x,y))
            return prefer;
        if(safe(board,opp,x,y))
            return consider;
        return 0;
    }
    
    private static boolean safeEdge(String[][] board,String color,String opp,int x,int y)
    {
        if(((x==1&&y==1)||(x==1&&y==6)||(x==6&&y==1)||(x==6&&y==6))&&cornerTaken(board,x,y))
            return true;
        if(x!=0&&x!=7&&y!=0&&y!=7)
            return false;
        boolean result=true;
        if(x==0||x==7)
        {
            Queue Q=library.takeBackup(board);
            library.put(board,x,y,color);
            for(int i=1;i<7;i++)
            {
                if(board[x][i-1].equalsIgnoreCase(color)&&board[x][i].equalsIgnoreCase("x")&&board[x][i+1].equalsIgnoreCase(color))
                    result=false;
            }
            for(int i=0;i<7;i++)
            {
                if(board[x][i].equalsIgnoreCase(color)&&board[x][i+1].equalsIgnoreCase(opp))
                    result=false;
                else if(board[x][i+1].equalsIgnoreCase(color)&&board[x][i].equalsIgnoreCase(opp))
                    result=false;
            }
            if(library.possible(board,opp,x,0)>0||library.possible(board,opp,x,7)>0)
                result=false;
            library.restore(board, Q);
        }
        else if(y==0||y==7)
        {
            Queue Q=library.takeBackup(board);
            library.put(board,x,y,color);
            for(int i=1;i<7;i++)
            {
                if(board[i-1][y].equalsIgnoreCase(color)&&board[i][y].equalsIgnoreCase("x")&&board[i+1][y].equalsIgnoreCase(color))
                    result=false;
            }
            for(int i=0;i<7;i++)
            {
                if(board[i][y].equalsIgnoreCase(color)&&board[i+1][y].equalsIgnoreCase(opp))
                    result=false;
                else if(board[i+1][y].equalsIgnoreCase(color)&&board[i][x].equalsIgnoreCase(opp))
                    result=false;
            }
            if(library.possible(board,opp,0,y)>0||library.possible(board,opp,7,y)>0)
                result=false;
            library.restore(board, Q);
        }
        return result;
    }
    
    private static boolean safe(String[][] board,String opp,int x,int y)
    {
        boolean [] safetyCheck=new boolean[8];
        for(int i=0;i<8;i++)safetyCheck[i]=true;
        
        for(int i=y+1;i<8&&!board[x][i].equalsIgnoreCase("x");i++)
        {
            if(board[x][i].equalsIgnoreCase(opp))
                safetyCheck[0]=false;
        }
        for(int i=x+1,j=y+1;i<8&&j<8&&!board[i][j].equalsIgnoreCase("x");i++,j++)
        {
            if(board[i][j].equalsIgnoreCase(opp))
                safetyCheck[1]=false;
        }
        for(int i=x+1;i<8&&!board[i][y].equalsIgnoreCase("x");i++)
        {
            if(board[i][y].equalsIgnoreCase(opp))
                safetyCheck[2]=false;
        }
        for(int i=x+1,j=y-1;i<8&&j>=0&&!board[i][j].equalsIgnoreCase("x");i++,j--)
        {
            if(board[i][j].equalsIgnoreCase(opp))
                safetyCheck[3]=false;
        }
        for(int i=y-1;i>=0&&!board[x][i].equalsIgnoreCase("x");i--)
        {
            if(board[x][i].equalsIgnoreCase(opp))
                safetyCheck[4]=false;
        }
        for(int i=x-1,j=y-1;i>=0&&j>=0&&!board[i][j].equalsIgnoreCase("x");i--,j--)
        {
            if(board[i][j].equalsIgnoreCase(opp))
                safetyCheck[5]=false;
        }
        for(int i=x-1;i>=0&&!board[i][y].equalsIgnoreCase("x");i--)
        {
            if(board[i][y].equalsIgnoreCase(opp))
                safetyCheck[6]=false;
        }
        for(int i=x-1,j=y+1;i>=0&&j<8&&!board[i][j].equalsIgnoreCase("x");i--,j++)
        {
            if(board[i][j].equalsIgnoreCase(opp))
                safetyCheck[7]=false;
        }
        return checkSC(safetyCheck);
    }
    
    private static boolean checkSC(boolean sc[])
    {
        if(sc[0]&&sc[1]&&sc[2])
            return true;
        else if(sc[1]&&sc[2]&&sc[3])
            return true;
        else if(sc[2]&&sc[3]&&sc[4])
            return true;
        else if(sc[3]&&sc[4]&&sc[5])
            return true;
        else if(sc[4]&&sc[5]&&sc[6])
            return true;
        else if(sc[5]&&sc[6]&&sc[7])
            return true;
        else if(sc[6]&&sc[7]&&sc[0])
            return true;
        else if(sc[7]&&sc[0]&&sc[1])
            return true;
        return false;
    }
    
    private static boolean cornerTaken(String[][] board,int i,int j)
    {
        if(!board[0][0].equalsIgnoreCase("x")&&((i==1&&j==1)||(i==1&&j==0)||(i==0&&j==1)))
            return true;
        else if(!board[0][7].equalsIgnoreCase("x")&&((i==0&&j==6)||(i==1&&j==7)||(i==1&&j==6)))
            return true;
        else if(!board[7][0].equalsIgnoreCase("x")&&((i==6&&j==0)||(i==6&&j==1)||(i==7&&j==1)))
            return true;
        else if(!board[7][7].equalsIgnoreCase("x")&&((i==6&&j==6)||(i==7&&j==6)||(i==6&&j==7)))
            return true;
        return false;
    }
    
    public static int getDepth()
    {
        return Depth;
    }
    
    public static boolean isAroundCorner(int i,int j)
    {
        return ((i==1&&j==1)||(i==1&&j==6)||(i==6&&j==1)||(i==6&&j==6)||(i==7&&j==1)||(i==7&&j==6)||(i==6&&j==7)||(i==1&&j==0)||(i==0&&j==1)||(i==0&&j==6)||(i==1&&j==7)||(i==6&&j==0));
    }
    
    public static boolean onTheEdge(int x,int y)
    {
        return (x==7||y==7||x==0||y==0);
    }
    
    public static double getTime()
    {
        return (System.currentTimeMillis()-time)/1000;
    }
}
