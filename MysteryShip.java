/*
 MysteryShip.java 
 Elsie Wang
 MysteryShip class to store coordinates, point value and direction. Methods to move and return values. 

UFO/ MYSTERY SHIP:
- "mystery ship" will occasionally move across top of the screen and give bonus points
- mystery ships scoring - depends on how many times the player has shot, increments through the list below:
100, 50, 50, 100, 150, 100, 100, 50, 300, 100, 100, 100, 50, 150, 100, 50 
 */

import java.awt.*;
import javax.swing.*;

public class MysteryShip {
    int x, y, pts, frameNum, startFrame;
    int dir = 1;
    boolean back = false;
    int []score = {0, 100, 50, 50, 100, 150, 100, 100, 50, 300, 100, 100, 100, 50, 150, 100, 50 }; 
    // array that ship's point value loops through
    Sound sounds;

    Image mysteryShipPic;
    public MysteryShip(int frameNum, int startFrame){
        y = 20;
        mysteryShipPic = new ImageIcon("images/mysteryShip.png").getImage(); // loading image
        sounds = new Sound(); // new sound class instance
    }

    public boolean move(){ // returns if it's entire path is done 
        pts = score[((SpaceInvadersPanel.shots)%16)]; // finds point value
        if (x >= (600)){ // if it reaches the right then it is on it's way back 
            sounds.playShipSound(); // starts playing sound again 
            back = true;
            dir = -1;
        }
        frameNum ++;
        if (back){
            if (x >= -32){ 
                if (frameNum >= (startFrame+ 170)){ // delay 170 "frames", until it starts heading back 
                    x += dir*3;
                }
            }
            if (x <= -32){ // once the entire ship is off the screen
                return true;
            }
        }
        else if(!back){ // moving towards the right 
            x += dir*3;
        }
        return false;
    }

    public int startFrame(){ // returns starting frame
        return startFrame;
    }

    public Rectangle getRect(){ // returns rectangle
        return new Rectangle(x, y, 32, 14);
    }

    public void draw(Graphics g){ // draws ship onto screen 
        g.drawImage(mysteryShipPic, x, y, null);
    }

}
