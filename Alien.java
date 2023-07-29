/*
Alien.java 
Elsie Wang
Alien class for each created alien stores frame images, velocity, row and column, x and y position, and point value
methods to move alien, detect if it is reaches edge, draw it and return values

"ALIENS":
- five rows of eleven "aliens" that move left and right as a group
- as levels progress, speed of aliens gets quicker
- bottom 2 rows - 10 pts
- middle 2 rows - 20 pts
- top row - 30 pts
- "aliens" move down each time they reach a screen edge
 */

// importing necessary packages
import java.awt.*;
import javax.swing.*;

public class Alien {
    private Image frame1; // stores alien's first frame 
    private Image frame2; // stores alien's second frame
    private int row, col, x, y, height, width, pts, vel;
    // need height and width variable because different aliens have different dimensions

    public Alien(int rowNum, int colNum, int velNum){
        vel = velNum; // velocity of alien 
        col = colNum; // col and row position of alien (used for alien type)
        row = rowNum;

        // different types of aliens corresponding to row #
        if (row == 0 || row == 1){
            pts = 10;
            frame1 = new ImageIcon("images/largeAlien1.png").getImage();
            frame2 = new ImageIcon("images/largeAlien2.png").getImage();
            height = 20;
            width = 24;
            x = 50 + (col*39); // calculates x and y position (50 - margin, 39 and 35 calculated to maintain even spacing)
            y = 235 - (row*35);
        }
        else if (row == 2 || row == 3){
            pts = 20;
            frame1 = new ImageIcon("images/mediumAlien1.png").getImage();
            frame2 = new ImageIcon("images/mediumAlien2.png").getImage();
            height = 20;
            width = 24;
            x = 50 + (col*39);
            y = 235 - (row*35);
        }
        else if (row == 4){
            pts = 30;
            frame1 = new ImageIcon("images/smallAlien1.png").getImage();
            frame2 = new ImageIcon("images/smallAlien2.png").getImage();
            height = 20;
            width = 20;
            x = 52 + (col*39);
            y = 235 - (row*35);
        }
    }

    public int points(){ // method to get point value of alien
        return pts;
    }

    public int x(){ // method to get x position of alien
        return x;
    }

    public int y(){ //method to get y position of alien
        return y;
    }

    public void move(int dir){ // moves alien or left or right depending on direction 
        x += dir*vel;
    }

    public void moveDown(boolean moveDown){ // moves alien down 
        if(moveDown){
            y += 10;
        }
    }

    public boolean edge(int dir){ // returns true if the alien reaches the edge
        if (x >= (512-30) || x <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Rectangle getRect(){ // returns the alien's Rectangle 
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g, int frameNum){ // alternates between drawing first and second frame of alien 
        if ((frameNum/25) % 2 == 0){
            g.drawImage(frame1, x, y, null);
        }
        else{
            g.drawImage(frame2, x, y, null);
        }
    }
}