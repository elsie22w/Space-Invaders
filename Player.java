/*
 Player.java 
 Elsie Wang
 Player class to store methods related to player movement (x, y positions, velocity, keys required, images), 
 graphics and shooting

 PLAYER:
- move ONLY along x-axis, starts at middle of screen
- can shoot projectile, consistent max shooting speed with cooldown (can't spam)
- constant velocity
 */

// importing necessary packages
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Player {
    public static int x;
    private int v, leftB, rightB, shootB;
    private int y = 420;

    Image playerPic;

    public Player(int leftp, int rightp, int shootp){
        // IMAGE
		playerPic = new ImageIcon("images/player.png").getImage();

        x = 256-(17/2); // calculates centre of screen
        v = 5; // velocity of player
        leftB = leftp; // left movement key ("A")
        rightB = rightp; // right movement key ("D")
        shootB = shootp; // shooting key (SPACE)
    }

    public void move(boolean [] keys){ // move method 
        if(keys[leftB] && x > 0){ // if left key is pressed and player is not moving off screen, moves to the left
            x -= v; 
        }
        if(keys[rightB] && x < (512-35)){ // same as above but movement to the right
            x += v;
        }
    }

    public int shoot(ArrayList<Bullet>bullets, boolean [] keys, int shootCoolDown){ 
        if(keys[shootB] && shootCoolDown <= 0){ 
            // if a shot can be taken, space pressed and cool down is complete x position is returned
            return x;
        }
        else{
            return -1;
        }
    }

    public Rectangle getRect(){ // method that returns Rectangle of bullet
        return new Rectangle(x, y, 30, 18);
    }

    public void draw(Graphics g){ // method that draws bullet onto screen
        g.drawImage(playerPic, x, y, null);
    }
}