/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BG
 */
@Named(value = "bean")
@SessionScoped
public class Bean implements Serializable {

    ArrayList<Integer> Row;
    ArrayList<Integer> Col;
    //  cellpadding="0" cellspacing="0 0" align="center" border="5"
    //<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    public String[][] getPicsArr() {
        return picsArr;
    }

    public void setPicsArr(String[][] picsArr) {
        this.picsArr = picsArr;
    }
    public void setPicsArrString(String picsAr, int x, int y) {
        this.picsArr[x][y] = picsAr;
    }
    public String doTheThing(int x, int y){
        eng.klik(x, y);
        return null;
    }
    
    String[][] picsArr;
    Engine eng;
    public Bean() {
        picsArr = new String[8][8];
        eng = new Engine(this);
        eng.setGame();
        
        Row = new ArrayList();
        Col = new ArrayList();
        int i;
        for(i=0;i<8;i++){
            Row.add(i);
            Col.add(i);
        }
    }

    public ArrayList<Integer> getRow() {
        return Row;
    }

    public void setRow(ArrayList<Integer> Row) {
        this.Row = Row;
    }

    public ArrayList<Integer> getCol() {
        return Col;
    }

    public void setCol(ArrayList<Integer> Col) {
        this.Col = Col;
    }
   
    
}
