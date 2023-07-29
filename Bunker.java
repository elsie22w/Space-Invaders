/*
Bunker.java 
Elsie Wang
Bunker class that stores x, y, bunker number, different states of bunkers and current state. 
Methods to draw and get values stored by bunker class.
*/

import java.awt.*;

public class Bunker {
    int x, y, bunkerNum;
    
    private int [][] bunkerPx;
    private int [][][] bunkerStates;
    private int state;

    public Bunker(int xPos, int newState){
        x = xPos;
        y = 360;
        state = newState;

        bunkerStates = new int[][][] { // 3D array that stores various states of segments
            {{1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1}},

            {{1,1,0,1,1,1,1,1,1,1},
            {0,1,1,0,0,0,1,1,1,1},
            {0,1,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,0,1,1,1},
            {0,0,0,1,1,1,1,1,1,1},
            {0,0,1,1,1,1,1,1,1,0},
            {1,1,1,1,0,0,0,0,1,1},
            {1,1,1,1,1,1,0,0,1,1},
            {1,1,1,1,1,1,1,1,0,1}},

            {{1,1,0,1,1,0,0,1,1,1},
            {0,0,0,0,0,0,1,1,0,1},
            {0,0,1,0,1,1,1,1,1,0},
            {0,0,0,0,1,1,1,1,0,0},
            {0,0,1,1,1,1,0,0,0,0},
            {0,0,0,0,1,1,1,1,1,1},
            {0,0,1,1,1,1,1,1,1,0},
            {1,1,0,0,0,0,0,0,1,0},
            {1,1,0,0,0,1,0,0,1,0},
            {1,1,0,0,0,1,1,1,0,1}},

            {{1,1,0,0,0,0,0,1,1,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,1,1,0},
            {0,0,0,0,0,1,0,1,0,0},
            {0,0,1,1,0,1,0,0,0,0},
            {0,0,0,0,0,1,1,0,0,1},
            {0,0,1,0,1,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0},
            {1,1,0,0,0,0,0,1,0,1}}};
            
        bunkerPx = bunkerStates[state];
    }

    public int state(){ // returns state
        return state;
    }

    public int x(){ // returns x position 
        return x;
    }

    public Rectangle getRect(){ // returns Rectangle
        return new Rectangle(x, 360, 20, 20);
    }

    public void draw(Graphics g){ // draws bunker 
        bunkerPx = bunkerStates[state];
        g.setColor(Color.GREEN);
        for (int col = 0; col < 10; col++){
            for (int row = 0; row < 10; row++){ // draws each 10x10 bunker segment 
                if (bunkerPx[col][row] == 1){ // if that pixel exists, (==1) then draw it 
                    g.fillRect(x + (row*2), y, 2, 2);
                }
            }
            y += 2; // increases y by "pixel" height
        }
        y = 360; // starts again from "top"


    }
}
