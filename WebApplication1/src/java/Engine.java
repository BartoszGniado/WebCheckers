


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Engine   {

   private static final int ROWS = 8;
   private int DepthMax=4;
   

   int act=0;
   int oldx=1;
   int oldy=1;
   Bean bean;
  
   
    public Engine(Bean b) {
   //     real = new board();
     bean = b;

      
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
             String s;
            real.Grid[row][col]=new Pole(row,col);
            if((row+col)%2==0){
                s="images/white.png"; 
            }
            else{
                s="images/black.png";
            }
            bean.setPicsArrString(s, row, col);
         //   picsArr[row][col] = s; // and assign into the array
            real.GridList.add(real.Grid[row][col]);
         }
      }
     // setGame();
   }

   
   Pion Active;
   class board {
   Pole[][] Grid;
   ArrayList<Pole> GridList;
   HashMap<Pole,Pion> kto;   // get:ktory pion na tym polu
   ArrayList<Pole> ActiveCanMove   ;
   ArrayList<Pion> Blacks         ;
   ArrayList<Pion> BlacksCanMove   ;
   ArrayList<Pion> BlacksCanFight ;
   ArrayList<Pion> Whites       ;
   ArrayList<Pion> WhitesCanMove  ;
   ArrayList<Pion> WhitesCanFight ;
   HashMap<String,ArrayList> PlayerCanMove ;
   HashMap<String,ArrayList> PlayerCanFight;
   board(){
           Grid=new Pole[ROWS][ROWS];
    GridList=new ArrayList();
    kto=new HashMap();    // get:ktory pion na tym polu
    ActiveCanMove    =new ArrayList();
    Blacks           =new ArrayList();
    BlacksCanMove    =new ArrayList();
    BlacksCanFight   =new ArrayList();
    Whites           =new ArrayList();
    WhitesCanMove    =new ArrayList();
    WhitesCanFight   =new ArrayList();
    PlayerCanMove = new HashMap();
    PlayerCanFight = new HashMap();
       PlayerCanMove.put("black", BlacksCanMove);
       PlayerCanMove.put("white", WhitesCanMove);    
       PlayerCanFight.put("black", BlacksCanFight);
       PlayerCanFight.put("white", WhitesCanFight);
   }
   }
   board real = new board();
   
    
    Boolean GhostsEnable= true;
    ArrayList<Pole> skad= new ArrayList();
    ArrayList<Pion> kogoBijemy = new ArrayList();
      ArrayList<Ghost> ghosts = new ArrayList();
   class Ghost{
       Pole gdzie;
       String col;
       int type;
       Ghost(Pole g, String c, int t){
       gdzie=g;
       col=c;
       type=t;
       }
   }
      Pion GhostMaker=new Pion();
   public void summon(){
       ImageIcon IMG;
     //  cutMenuItem.setEnabled(true);
       if(GhostsEnable){
        for ( Ghost g : ghosts )
        {
     //       IMG = createImageIcon("images/"+g.col+"Ghost.png");
     //        buttonGrid[g.gdzie.x][g.gdzie.y].setIcon(IMG);
             bean.setPicsArrString("images/"+g.col+"Ghost.png", g.gdzie.x, g.gdzie.y);
        }
  //      IMG = createImageIcon("images/ablackonblack.png");
  //           buttonGrid[GhostMaker.x][GhostMaker.y].setIcon(IMG);
         bean.setPicsArrString("images/ablackonblack.png",GhostMaker.x, GhostMaker.y);
       }
   }
   public void egzorcism(){
   //    cutMenuItem.setEnabled(false);
       for ( Ghost g : ghosts )
           g.gdzie.print();
       real.Grid[GhostMaker.x][GhostMaker.y].print();
       ghosts.clear();
   }

    public void setGame(){
       int x,y;
       //System.out.print("\nGO\n");

       for(x=0;x<8;x++)
           for(y=0;y<8;y++)
           {
               real.Grid[x][y].print();
               if(real.Grid[x][y].color=="black"&&(x<3||x>4))
                   if(x<3)
                       real.Blacks.add(new Pion(x,y));
                   else if(x>4)
                       real.Whites.add(new Pion(x,y));
           }
   //    real.kto.get(real.Grid[2][1]).move(3, 0);
 //      real.Grid[2][5].print();
      for(Pion p:real.Whites)
      {
          p.CanMove();
          p.CanFight(0);
      }
      
   }
   
   public class Pole {
    int x;
       int y;
       String color; // 0-white 1-black
       int taken=0;
       int active=0;
       int Temp;
       board plansza=real;
        public Pole(int xt, int yt){
            x=xt;
            y=yt;
            Temp=0;
            if((x+y)%2==0)
                color="white";
            else
                color="black";
        }
        public Pole(Pole p){
            x=p.x;
            y=p.y;
            color=p.color;
            taken=p.taken;
            Temp=1;
        }
        public void print(){
            //movemove(x,y);
            if(Temp==0){
            ImageIcon IMG;
            String s;
            if (taken==0)
                s="images/"+color+".png";
            else
                if(active==0)
                    if(plansza.kto.get(this).type==0)
                            s="images/"+plansza.kto.get(this).player+"on"+color+".png";
                        else
                            s="images/"+plansza.kto.get(this).player+"Q"+color+".png";
                else
                    if(plansza.kto.get(this).type==0)
                            s="images/a"+plansza.kto.get(this).player+"on"+color+".png";
                       else
                           s="images/a"+plansza.kto.get(this).player+"Q"+color+".png";
        //    IMG = createImageIcon(s);
        //    buttonGrid[x][y].setIcon(IMG);
             bean.setPicsArrString(s, x, y);
         
            }
        }
    }
   
   public class Pion{
     int x;
     int y;
     int active;
     int type;
     board plansza;
     String player;
     public Pion(){};
       public Pion(Pion p,board b){
           plansza=b;
           player=p.player;
           type=p.type;
           x=p.x;
           y=p.y;
           plansza.Grid[x][y].taken=1;
           plansza.kto.put(plansza.Grid[x][y], this);
       }
       public Pion(Pole p, String col, int t){
           plansza=real;
           x=p.x;
           y=p.y;
           type=t;
           player=col;
           plansza.Grid[x][y].taken=1;
           plansza.kto.put(plansza.Grid[x][y], this);
           plansza.Grid[x][y].print();
       }
       public Pion(int xt, int yt){
           x=xt;
           plansza=real;
           y=yt;
           type=0;
           if(x<3){
               player="black";
            //   Blacks.add(this);
           }
           else{
               player="white";
            //   Whites.add(this);
           }
           plansza.Grid[x][y].taken=1;
           plansza.kto.put(plansza.Grid[x][y], this);
           plansza.Grid[x][y].print();
       }
       public void destroy(){
           plansza.Grid[x][y].taken=0;
           plansza.Grid[x][y].print();
           if(player=="black")
               plansza.Blacks.remove(this);
           else
               plansza.Whites.remove(this);
           
       }
       public void move(int newX,int newY){
           Pole p=plansza.Grid[x][y];
           p.active=0;
           p.taken=0;
           p.print();
           plansza.kto.remove(p);
           x=newX;
           y=newY;
                   plansza.Grid[x][y].taken=1;
                   plansza.kto.put(plansza.Grid[x][y], this);
                   plansza.Grid[x][y].print();
       }
       public void WhereCanMove(){
           int dx;
           int i;
           if(player=="black")
               dx=1;
           else
               dx=-1;
           if(plansza.PlayerCanFight.get(player).isEmpty())
           {
               if(type==0)
               {
                if(y>0)
                    if(plansza.Grid[x+dx][y-1].taken==0)
                            plansza.ActiveCanMove.add(plansza.Grid[x+dx][y-1]);
                if(y<7)
                     if(plansza.Grid[x+dx][y+1].taken==0)
                         plansza.ActiveCanMove.add(plansza.Grid[x+dx][y+1]);
               }
               if(type==1)
               {
                   for(i=1;i<8;i++)
                   {
                       if( x+i>7 || y+i>7)
                           break;
                       if(plansza.Grid[x+i][y+i].taken==1)
                           break;
                       if(plansza.Grid[x+i][y+i].taken==0)
                           plansza.ActiveCanMove.add(plansza.Grid[x+i][y+i]);
                   }
                   for(i=1;i<8;i++)
                   {
                       if( x-i<0 || y+i>7)
                           break;
                       if(plansza.Grid[x-i][y+i].taken==1)
                           break;
                       if(plansza.Grid[x-i][y+i].taken==0)
                           plansza.ActiveCanMove.add(plansza.Grid[x-i][y+i]);
                   }
                   for(i=1;i<8;i++)
                   {
                       if( x+i>7 || y-i<0)
                           break;
                       if(plansza.Grid[x+i][y-i].taken==1)
                           break;
                       if(plansza.Grid[x+i][y-i].taken==0)
                           plansza.ActiveCanMove.add(plansza.Grid[x+i][y-i]);
                   }
                   for(i=1;i<8;i++)
                   {
                       if( x-i<0 || y-i<0)
                           break;
                       if(plansza.Grid[x-i][y-i].taken==1)
                           break;
                       if(plansza.Grid[x-i][y-i].taken==0)
                           plansza.ActiveCanMove.add(plansza.Grid[x-i][y-i]);
                   }
               }
           }else
               CanFight(1);
       }
       public void CanFight(int where){
           int i,j;               // TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO 
               
           if(type==0)
                for(i=1;i>-3;i=i-2)
                    for(j=1;j>-3;j=j-2)
                        if  (   x+2*i>=0 && x+2*i<=7
                                &&
                                y-2*j>=0 && y-2*j<=7
                                &&
                                plansza.Grid[x+i][y-j].taken==1 
                                && 
                                plansza.kto.get(plansza.Grid[x+i][y-j]).player!=player 
                                &&
                                plansza.Grid[x+2*i][y-2*j].taken==0
                            )
                            if(where==1)
                                plansza.ActiveCanMove.add(plansza.Grid[x+2*i][y-2*j]);
                            else
                                plansza.PlayerCanFight.get(player).add(this);
       if(type==1)
               {
                   int can=0;
                   for(i=1,can=0;i<8;i++)
                   {
                       if( x+i>7 || y+i>7)
                           break;
                       if(plansza.Grid[x+i][y+i].taken==1
                               &&plansza.kto.get(plansza.Grid[x+i][y+i]).player==player)
                           break;
                       if(can==1 && plansza.Grid[x+i][y+i].taken==1)
                           break;
                       if(plansza.Grid[x+i][y+i].taken==1
                               &&plansza.kto.get(plansza.Grid[x+i][y+i]).player!=player)
                           can=1;
                       
                       if(can==1 && plansza.Grid[x+i][y+i].taken==0)
                           if(where==1)
                           plansza.ActiveCanMove.add(plansza.Grid[x+i][y+i]);
                           else
                                plansza.PlayerCanFight.get(player).add(this);
                   }
                   for(i=1,can=0;i<8;i++)
                   {
                       if( x-i<0 || y+i>7)
                           break;
                       if(plansza.Grid[x-i][y+i].taken==1
                               &&plansza.kto.get(plansza.Grid[x-i][y+i]).player==player)
                           break;
                       if(can==1 && plansza.Grid[x-i][y+i].taken==1)
                           break;
                       if(plansza.Grid[x-i][y+i].taken==1
                               &&plansza.kto.get(plansza.Grid[x-i][y+i]).player!=player)
                            can=1;
                       
                       if(can==1 && plansza.Grid[x-i][y+i].taken==0)
                           if(where==1)
                           plansza.ActiveCanMove.add(plansza.Grid[x-i][y+i]);
                           else
                                plansza.PlayerCanFight.get(player).add(this);
                   }
                   for(i=1,can=0;i<8;i++)
                   {
                       if( x+i>7 || y-i<0)
                           break;
                       if(plansza.Grid[x+i][y-i].taken==1
                               &&plansza.kto.get(plansza.Grid[x+i][y-i]).player==player)
                           break;
                       if(can==1 && plansza.Grid[x+i][y-i].taken==1)
                           break;
                       if(plansza.Grid[x+i][y-i].taken==1
                               &&plansza.kto.get(plansza.Grid[x+i][y-i]).player!=player)
                           can=1;
                       if(can==1 && plansza.Grid[x+i][y-i].taken==0)
                           if(where==1)
                           plansza.ActiveCanMove.add(plansza.Grid[x+i][y-i]);
                           else
                                plansza.PlayerCanFight.get(player).add(this);
                   }
                   for(i=1,can=0;i<8;i++)
                   {
                       if( x-i<0 || y-i<0)
                           break;
                       if(plansza.Grid[x-i][y-i].taken==1
                               &&plansza.kto.get(plansza.Grid[x-i][y-i]).player==player)
                           break;
                       if(can==1 && plansza.Grid[x-i][y-i].taken==1)
                           break;
                       if(plansza.Grid[x-i][y-i].taken==1
                               &&plansza.kto.get(plansza.Grid[x-i][y-i]).player!=player)
                           can=1;
                       if(can==1 && plansza.Grid[x-i][y-i].taken==0)
                           if(where==1)
                           plansza.ActiveCanMove.add(plansza.Grid[x-i][y-i]);
                           else
                                plansza.PlayerCanFight.get(player).add(this);
                   }
                   
               }
           
       }
       public void CanMove(){
           int dx;
           
            if(player=="black")
                dx=1;
            else
                dx=-1;
            if( y>0 && x+dx>=0 && x+dx<=7 )
                if(plansza.Grid[x+dx][y-1].taken==0 && !plansza.PlayerCanMove.get(player).contains(this))
                    plansza.PlayerCanMove.get(player).add(this);
             if( y<7 && x+dx>=0 && x+dx<=7 )
                 if(plansza.Grid[x+dx][y+1].taken==0 && !plansza.PlayerCanMove.get(player).contains(this))
                     plansza.PlayerCanMove.get(player).add(this);
           if(type==1)
           {
               if( y>0 && x-dx>=0 && x-dx<=7 )
                if(plansza.Grid[x-dx][y-1].taken==0 && !plansza.PlayerCanMove.get(player).contains(this))
                    plansza.PlayerCanMove.get(player).add(this);
               if( y<7 && x-dx>=0 && x-dx<=7 )
                 if(plansza.Grid[x-dx][y+1].taken==0 && !plansza.PlayerCanMove.get(player).contains(this))
                     plansza.PlayerCanMove.get(player).add(this);
           }
       }
           
    }//Pion ENDs
   
   public void klik(int x,int y){
 //   movemove(x,y);
       System.out.println("x: "+x+"y: "+y);
 egzorcism();
       if(real.Grid[x][y].taken==1)
       {
//           real.kto.get(real.Grid[x][y]).Click();
           if(real.Grid[x][y].active==1)
           {
               real.Grid[x][y].active=0;
               real.ActiveCanMove.clear();
               act=0;
               real.Grid[x][y].print();
           }
           else
           {
               if(act==0)
               { 
                   skad.clear();
                    kogoBijemy.clear();
// TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO 
                   if(real.WhitesCanFight.isEmpty())
                   {
                        if(real.WhitesCanMove.contains(real.kto.get(real.Grid[x][y])))
                        {
                             real.Grid[x][y].active=1;
      //                       real.kto.get(real.Grid[x][y]).WhereCanMove();
                             Active=real.kto.get(real.Grid[x][y]);
                             skad.add(real.Grid[x][y]);
                             Active.WhereCanMove();
                             act=1;
                             real.Grid[x][y].print(); 
                        }
                   }
                   else
                   {
                       if(real.WhitesCanFight.contains(real.kto.get(real.Grid[x][y])))
                        {
                             real.Grid[x][y].active=1;
      //                       real.kto.get(real.Grid[x][y]).WhereCanMove();
                             Active=real.kto.get(real.Grid[x][y]);
                             skad.add(real.Grid[x][y]);
                             Active.WhereCanMove();
                             act=1;
                             real.Grid[x][y].print(); 
                        }
                   }
               }
           } 
       }
       else
       {
       if(act==1)
        {
             if(real.ActiveCanMove.contains(real.Grid[x][y]))
                 {
                  
                 
                  if(real.WhitesCanFight.contains(Active))
                  {
                      if(Active.type==0){
                        real.kto.get(real.Grid[(x+Active.x)/2][(y+Active.y)/2]).destroy();
                        kogoBijemy.add(real.kto.get(real.Grid[(x+Active.x)/2][(y+Active.y)/2]));
                      }else
                      {
                          int signX=(x-Active.x)/Math.abs(Active.x-x);
                          int signY=(y-Active.y)/Math.abs(Active.y-y);
                          int i;
                          for(i=1;;i++)
                              if(real.Grid[Active.x+i*signX][Active.y+i*signY].taken==1){
                                  real.kto.get(real.Grid[Active.x+i*signX][Active.y+i*signY]).destroy();
                                  kogoBijemy.add(real.kto.get(real.Grid[Active.x+i*signX][Active.y+i*signY]));
                                  break;
                              }
                      }
                        Active.move(x, y);
                        real.ActiveCanMove.clear();
                        Active.CanFight(1);
                       // buttonreal.Grid[Active.x][Active.y].doClick();
                      
                  }
                  else
                  {
                      Active.move(x, y);
                     real.ActiveCanMove.clear();
                  }
                    
                 
                  act=0;
                  ////System.out.print("\nM\n");
//                  if (real.ActiveCanMove.isEmpty())
//                      SwingUtilities.invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                AI();
//                            }
//                        });
                    if (real.ActiveCanMove.isEmpty())
                         AI();  
                 }
        }
           
           //    //System.out.print("\n.\n");
        }
       
      // //System.out.print("- X:"+x+" Y:"+y+" : ");
      //  //System.out.print(" act:"+Grid[x][y].active+" - taken:"+Grid[x][y].taken+" - col:"+Grid[x][y].color+"-\n");
    }
   
   public void AI(){
       for(Pion p:real.Whites)
           if(p.x==0){
               p.type=1;
                real.Grid[p.x][p.y].print();
           }
       
    
       
       AIv2();
       
       real.BlacksCanMove.clear();
       real.BlacksCanFight.clear();
       for(Pion p:real.Blacks)
       {
          p.CanMove();
          p.CanFight(0);
       }
       if(real.BlacksCanMove.isEmpty()){
           System.err.println("---END---");
           return;
       }
       
       //----------------------------------------------------------------------------------
      
       real.ActiveCanMove.clear();
           
       //----------------------------------------------------------------------------------
       for(Pion p:real.Blacks)
           if(p.x==7){
               p.type=1;
               real.Grid[p.x][p.y].print();
           }
       summon();
       real.WhitesCanMove.clear();
       real.WhitesCanFight.clear();
       
       for(Pion p:real.Whites)
      {
          p.CanMove();
          p.CanFight(0);
      }
       
       
   }
 
   public void AIv2() {
       Pion chosen=new Pion();
       int whereX;
       int whereY;
       int i,j;
       int rec=100;
       ArrayList<Pion> choose1 = new ArrayList();
       ArrayList<Pole> choose2 = new ArrayList();

       real.BlacksCanMove.clear();
       real.BlacksCanFight.clear();
       for(Pion p:real.Blacks)
       {
          p.CanMove();
          p.CanFight(0);
       }
       if(real.BlacksCanMove.isEmpty()){
           System.err.println("---END---");
           return;
       }
//       //System.out.print(real.BlacksCanMove.size()+"\n");
if(real.BlacksCanFight.isEmpty())
       {
           //System.out.print("\n nie ma bicia");
       for (i=0;i<real.BlacksCanMove.size();i++)
       {
           Pion p=real.BlacksCanMove.get(i);
           p.WhereCanMove();
           for (Pole g:real.ActiveCanMove)
           {
               //System.out.print("\n symulowac bedziem x"+p.x+" y"+p.y+" na "+g.x +" "+ g.y);
               simulateBlack s = new simulateBlack( 0 , real , "white" , p , g.x , g.y , 0 );
               s.run();
               
               if(rec>s.wynik){
                   rec=s.wynik;
                   choose1.clear();
                   choose2.clear();
               }
               if(rec==s.wynik){
                   choose1.add(p);
                   choose2.add(g);
               }
               ////System.out.print(s.wynik);
           }
           real.ActiveCanMove.clear();
           ////System.out.print(" ");
       }
       
   //    //System.out.print("\n");
       Random r=new Random();
       j=r.nextInt(choose1.size());
       chosen=choose1.get(j);
       whereX=choose2.get(j).x;
       whereY=choose2.get(j).y;
       choose1.clear();
       choose2.clear();
       ghosts.add(new Ghost(real.Grid[chosen.x][chosen.y], "black",chosen.type));
    //   ghosts.put(real.Grid[chosen.x][chosen.y], "black");
      // ghosts.add(real.Grid[chosen.x][chosen.y]);
       chosen.move(whereX, whereY);
       GhostMaker=chosen;
  }else{
    //System.out.print("\n bicie");
    real.ActiveCanMove.clear();
    Pion p;
    do{
    //    //System.out.print(real.BlacksCanFight.size());
    rec=100;
    for (i=0;i<real.BlacksCanFight.size();i++)
       {
        if(real.ActiveCanMove.isEmpty())  { 
            p=real.BlacksCanFight.get(i);
           p.CanFight(1);
        }else{  
            p=chosen; 
            i=100;
            // po co i=100?
        }
           for (Pole g:real.ActiveCanMove)
           {
               //System.out.print("\n symulowac bedziem x"+p.x+" y"+p.y+" na "+g.x +" "+ g.y);
               simulateBlack s = new simulateBlack( 0 , real , "white" , p , g.x , g.y , 1 );
               s.run();
               
               if(rec>s.wynik){
                   rec=s.wynik;
                   choose1.clear();
                   choose2.clear();
               }
               if(rec==s.wynik){
                   choose1.add(p);
                   choose2.add(g);
               }
               ////System.out.print(s.wynik);
           }
           real.ActiveCanMove.clear();
           ////System.out.print(" ");
       }
       
       ////System.out.print("\n");
       Random r=new Random();
       j=r.nextInt(choose1.size());
       chosen=choose1.get(j);
       whereX=choose2.get(j).x;
       whereY=choose2.get(j).y;
       choose1.clear();
       choose2.clear();
       if(chosen.type==0){
           ghosts.add(new Ghost(real.Grid[(whereX+chosen.x)/2][(whereY+chosen.y)/2], "white",chosen.type));
        //   ghosts.put(real.Grid[(whereX+chosen.x)/2][(whereY+chosen.y)/2], "white");
        //   ghosts.add(real.Grid[(whereX+chosen.x)/2][(whereY+chosen.y)/2]);
          real.kto.get(real.Grid[(whereX+chosen.x)/2][(whereY+chosen.y)/2]).destroy();
       }
        else
        {
            int signX=(whereX-chosen.x)/Math.abs(chosen.x-whereX);
            int signY=(whereY-chosen.y)/Math.abs(chosen.y-whereY);

            for(i=1;;i++)
                if(real.Grid[chosen.x+i*signX][chosen.y+i*signY].taken==1){
                    ghosts.add(new Ghost(real.Grid[chosen.x+i*signX][chosen.y+i*signY], "white",chosen.type));
                //    ghosts.put(real.Grid[chosen.x+i*signX][chosen.y+i*signY], "white");
              //      ghosts.add(real.Grid[chosen.x+i*signX][chosen.y+i*signY]);
                    real.kto.get(real.Grid[chosen.x+i*signX][chosen.y+i*signY]).destroy();
                    break;
                }
        }
       ghosts.add(new Ghost(real.Grid[chosen.x][chosen.y], "black",chosen.type));
   //    ghosts.put(real.Grid[chosen.x][chosen.y], "black");
     //  ghosts.add(real.Grid[chosen.x][chosen.y]);
       chosen.move(whereX, whereY);
       GhostMaker=chosen;
       real.ActiveCanMove.clear();
       chosen.CanFight(1);
       chosen.CanFight(0);
    }while(!real.ActiveCanMove.isEmpty());
       }
}

   class simulateBlack {
       int Depth;
       int i,j;
       board virtual;
       String turn; 
       String turnNext;
       int bicie;
       int wynik;
       Pion Ruchacz;
       simulateBlack(int DepthOld , board b, String BorW, Pion skad, int gdzieX, int gdzieY, int bit){
           Depth=DepthOld+1;
           wynik=0;
           turn=BorW;
           if(turn=="black") turnNext="white";  
           else turnNext="black";
           bicie=bit;
           virtual=new board();
            for (i=0;i<8;i++)
            for (j=0;j<8;j++)
                virtual.Grid[i][j]=new Pole(b.Grid[i][j]);
           for (Pion p:b.Blacks)
               virtual.Blacks.add(new Pion(p,virtual));
           for (Pion p:b.Whites)
               virtual.Whites.add(new Pion(p,virtual));
           Ruchacz=virtual.kto.get(virtual.Grid[skad.x][skad.y]);
           ////System.out.print("-g"+virtual.Grid[skad.x][skad.y].taken+"g-");
           if(bicie==1)
           {
               if(turn=="black")
                    wynik++;
                else
                    wynik--;
           //    //System.out.print("--"+Ruchacz.type+"--");
               
               if(Ruchacz.type==0)
                     virtual.kto.get(virtual.Grid[(gdzieX+Ruchacz.x)/2][(gdzieY+Ruchacz.y)/2]).destroy();
                   else
                   {
                       int signX=(gdzieX-Ruchacz.x)/Math.abs(Ruchacz.x-gdzieX);
                       int signY=(gdzieY-Ruchacz.y)/Math.abs(Ruchacz.y-gdzieY);
                       int i;
                       for(i=1;;i++)
                           if(virtual.Grid[Ruchacz.x+i*signX][Ruchacz.y+i*signY].taken==1){
                               virtual.kto.get(virtual.Grid[Ruchacz.x+i*signX][Ruchacz.y+i*signY]).destroy();
                               break;
                           }
                   }
           }
           Ruchacz.move(gdzieX, gdzieY);
           /*
           for (i=0;i<8;i++)
            for (j=0;j<8;j++)
            {
                if(j==0)
                    //System.out.print('\n');
                if(virtual.Grid[i][j].taken==0)
                    //System.out.print(". ");
                else
                    if(virtual.kto.get(virtual.Grid[i][j]).player=="black")
                         //System.out.print("b ");
                       else
                         //System.out.print("w ");
                
            }
                //System.out.print("\n");
            */
       }
      // TreeSet<Integer> set=new TreeSet();
       class MinMax{
           TreeSet<Integer> set;
           MinMax(){
               set=new TreeSet();
           }
           public void put(int q){
               set.add(q);
           }
           public void print(){
           //System.out.print("\nset: "+set);
            }
           public int take(String pl){
               if(pl=="white")
                   return set.last();
               else
                   return set.first();
           }
           public void clear(){
               set.clear();
           }
       }
           
       public void run(){
           MinMax set=new MinMax();
           
           int flag=0;
           
               //System.out.print("\n\t Symulacja "+Depth+" rozpoczeta z wynikiem "+wynik);
            if (bicie==1){
                 //System.out.print("\n\t\tW synulacji było bicie");
                virtual.ActiveCanMove.clear();
                Ruchacz.CanFight(1);
                if(!virtual.ActiveCanMove.isEmpty()){
                    simulateBlack[] s= new simulateBlack[virtual.ActiveCanMove.size()];
                    for(Pole g:virtual.ActiveCanMove){
                         s[virtual.ActiveCanMove.indexOf(g)]=new simulateBlack(Depth-1,virtual,turn,Ruchacz, g.x, g.y ,1);
                        s[virtual.ActiveCanMove.indexOf(g)].run();
                        
                       // set.put(5);
                        flag=1;   
                    }
                    for(Pole g:virtual.ActiveCanMove){
                        
                        set.put(s[virtual.ActiveCanMove.indexOf(g)].wynik);
                        
                    }
                    virtual.ActiveCanMove.clear();
                    wynik+=set.take(turn);
                   // set.print();
                  //  //System.out.print(set.take(turn)+turn);
                }
                    ;
                //BREAK req - flag
            }
            if(Depth<DepthMax)
           {
            if(flag==0){//not else
                //System.out.print("\n\t\tW symulacji flaga jest 0");
                if(turn=="white")
                for(Pion p:virtual.Whites)
                 {
                    p.CanMove();
                    p.CanFight(0);
                 }
                else
                for(Pion p:virtual.Blacks)
                 {
                    p.CanMove();
                    p.CanFight(0);
                 }
                ArrayList<Pion> listMoves = virtual.PlayerCanMove.get(turn);
                if(virtual.PlayerCanFight.get(turn).isEmpty()){
                    //System.out.print("\n\t\t\t nie ma bicia");
                    for(i=0;i<virtual.PlayerCanMove.get(turn).size();i++){
                        Pion p=listMoves.get(i);
                        p.WhereCanMove();
                        for (Pole g:virtual.ActiveCanMove)
                        {
                            //System.out.print("\n\t\t\t symulowac bedziem "+turn+" x"+p.x+" y"+p.y+" na "+g.x +" "+ g.y);
                        simulateBlack s=new simulateBlack(Depth,virtual,turnNext,p, g.x, g.y ,0);
                        s.run();
                        
                          
                 //       //System.out.print(s.wynik);
                        set.put(s.wynik);
                        }
                        virtual.ActiveCanMove.clear();
                    }
           //         set.print();
                    if(!set.set.isEmpty())
                    wynik+=set.take(turn);
                    
                }else{
                    //System.out.print("\n\t\t\t jest bicie");
                    ArrayList<Pion> listFight = virtual.PlayerCanFight.get(turn);
                    for(i=0;i<virtual.PlayerCanFight.get(turn).size();i++){
                        Pion p=listFight.get(i);
                        p.WhereCanMove();
                        for (Pole g:virtual.ActiveCanMove)
                        {
                            //System.out.print("\n\t\t\t symulowac bedziem "+turn+" x"+p.x+" y"+p.y+" na "+g.x +" "+ g.y);
                        simulateBlack s=new simulateBlack(Depth,virtual,turnNext,p, g.x, g.y ,1);
                        s.run();
                        
                 //       //System.out.print(s.wynik);
                        set.put(s.wynik);
                        }
                        virtual.ActiveCanMove.clear();
                    }
             //       set.print();
                    if(!set.set.isEmpty())
                    wynik+=set.take(turn);
                }
            }
            //System.out.print("\n\t Symulacja "+Depth+" zakonczona z wynikiem "+ wynik+"\n");
           }
           
       }
   }
    
}
