package assignment2;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;

public final class board{
    
    JFrame myFrame;
    JButton[][] one;
    JPanel fullBoard;
    String Player,Opponent;
    public static String[][] board;
    private static final JLabel banner1=new JLabel("Welcome",JLabel.CENTER);
    private static final JLabel banner2=new JLabel("to Reversi",JLabel.CENTER);
    private static final JLabel scores[]=new JLabel[2];
    private static int X=-1,Y=-1;
    
    public board()
    {
        if(((int)(Math.random()*100+Math.random()*100))%2==0)
        {
            Player="w";
            Opponent="b";
        }
        else
        {
            Player="b";
            Opponent="w";
        }
        guiInit();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                one[i][j]=new JButton();
                one[i][j].setPreferredSize(new Dimension(40,40));
                one[i][j].setToolTipText("("+(i+1)+","+(j+1)+")");
                
                addFeedBack(one[i][j],i,j);
                
                if(i==3 && j==4)
                    board[i][j]="b";
                else if(i==3 && j==3)
                    board[i][j]="w";
                else if(i==4 && j==3)
                    board[i][j]="b";
                else if(i==4 && j==4)
                    board[i][j]="w";
                else
                    board[i][j]="x";
            
                fullBoard.add(one[i][j]);
            }
        }
    }
    
    public void HighLight(int x)
    {
        scores[0].setBorder(null);
        scores[1].setBorder(null);
        if(x==1)
            scores[x].setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, Color.GREEN));
        else
            scores[x].setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, Color.RED));
    }
    
    private void guiInit()
    {
        myFrame=new JFrame("Reversi by Navid & Fahim");
        one=new JButton[8][8];
        board=new String[8][8];
        fullBoard=new JPanel(new GridLayout(8,8));
        banner1.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        banner2.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        scores[0]=new JLabel("",JLabel.CENTER);
        scores[1]=new JLabel("",JLabel.CENTER);
        scores[0].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        scores[1].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        myFrame.setLocationByPlatform(true);
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel temp=new JPanel();
        temp.setLayout(new GridLayout(1,2));
        temp.add(scores[1]);
        temp.add(scores[0]);
        JPanel temp2=new JPanel();
        temp2.setLayout(new GridLayout(2,1));
        temp2.add(banner1);
        temp2.add(banner2);
        myFrame.getContentPane().add(temp,BorderLayout.PAGE_START);
        myFrame.getContentPane().add(fullBoard,BorderLayout.CENTER);
        myFrame.getContentPane().add(temp2,BorderLayout.PAGE_END);
    }
    
    public boolean hasSelected()
    {
        return (X!=-1&&Y!=-1);
    }
    
    public Queue getSelection()
    {
        Queue temp=new LinkedList();
        temp.add(X);
        temp.add(Y);
        
        return temp;
    }
    
    public void setXY(int h,int i)
    {
        X=h;
        Y=i;
    }
    
    private void addFeedBack(JButton button,int x,int y)
    {
        button.addActionListener((ActionEvent e) -> {
            if(library.possible(board, Player, x, y)>0)
            {
                setXY(x,y);
            }
            else
            {
                updateB("Illegal Move!","");
            }
        });
    }
    
    private void getScores()
    {
        if(Player.equalsIgnoreCase("w"))
        {
            scores[1].setText("You(W):"+library.countColor(board,"w"));
            scores[0].setText("Computer(B):"+library.countColor(board,"b"));
        }
        else
        {
            scores[1].setText("You(B):"+library.countColor(board,"b"));
            scores[0].setText("Computer(W):"+library.countColor(board,"w"));
        }
    }
    
    public void updateB(String s1,String s2)
    {
        banner1.setText(s1);
        banner2.setText(s2);
        myFrame.pack();
    }
    
    public void update(boolean user,String color)
    {
        getScores();

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                
                if(board[i][j].equals("B"))
                {
                    board[i][j]="b";
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/black.gif").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/black.png").getAbsolutePath())));
                    
                }
                else if(board[i][j].equals("W"))
                {
                    board[i][j]="w";
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/white.gif").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/white.png").getAbsolutePath())));
                }
                else if(board[i][j].equals("b"))
                {
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/black.png").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/black.png").getAbsolutePath())));
                }
                else if(board[i][j].equals("w"))
                {
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/white.png").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/white.png").getAbsolutePath())));
                }
                else if(user && library.possible(board,color,i,j)>0)
                {
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/possible.png").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/possible.gif").getAbsolutePath())));
                }
                else
                {
                    one[i][j].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/blank.png").getAbsolutePath())));
                    one[i][j].setRolloverIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(new File("images/blank.png").getAbsolutePath())));
                }
            }
        }
        myFrame.pack();
    }
    
    public void result()
    {
        int computer=library.countColor(board,Opponent),you=library.countColor(board,Player);
        String r="";
        if(computer>you)
            r="Game over! You:"+you+" Computer:"+computer+" You Lose!";
        else if(you>computer)
            r="Game over! You:"+you+" Computer:"+computer+" You Win!";
        else if(you==computer)
            r="Game over! You:"+you+" Computer:"+computer+" Draw!";
        
        long t=System.currentTimeMillis()+1000*60;
        while(System.currentTimeMillis()<t)
        {
            updateB(r,"This window will automatically close in "+((t-System.currentTimeMillis())/1000+"s"));
        }
        System.exit(0);
    }
}