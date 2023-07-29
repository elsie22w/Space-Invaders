/*
Bullet.java 
Elsie Wang
Bullet class that stores x, y, velocity and direction of each bullet. Includes methods to move bullet, check what it hits,
draws it and returns values. 
*/

// importing necessary packages
import java.awt.*;
import java.util.ArrayList;

public class Bullet { 
    private int x, y, v, dir;

    public Bullet(int vel, String type, int xBul, int yBul){
        x = xBul;
        y = yBul;
        v = vel;
        if (type == "PLAYER"){ // if the bullet is from the player, it travels upwards
            dir = -1;
        }
        else if (type == "ALIEN"){ // if the bullet is from the player, it travels downwards
            dir = 1;
        }
    }

    public void move(){ // moves according to respective direction
        y += dir*v;
    }

    public int x(){ // returns x position 
        return x;
    }

    public int y(){ // returns y position 
        return y;
    }

    public int checkHitBunker(ArrayList<Bullet> bullets, ArrayList<Bunker> bunkers){ // checks if bunker is hit
        if (bunkers.size() > 0){ // regardless of direction of bullet checks if it hits bunker
            for (int i = 0; i < bunkers.size(); i++){ // checks each bunker segment 
                if (bunkers.get(i).getRect().contains(x,y)){
                    return i; // returns bunker that collided
                }
            }
        }
        return -1;
    }

    public int checkHitAliens(ArrayList<Bullet> bullets, ArrayList<Alien> aliens){ // returns which alien (if any) is hit by bullet
        if (dir == -1){ // below only applies to bullets shot by player
            for (int i = 0; i < aliens.size(); i++){
                if ((aliens.get(i).getRect()).contains(x,y)){
                    return i; 
                }
            }
        }
        return -1; 
    }

    public boolean checkHitMysteryShip(ArrayList<Bullet> bullets, ArrayList<MysteryShip> mysteryShips){ 
        // checks if mystery ship was shot by player
        if (dir == -1){
            if (mysteryShips.size() > 0){
                if(mysteryShips.get(0).getRect().contains(x,y)){
                    return true;
                }
            }
        }
        return false; 
    }

    public boolean checkHitPlayer(ArrayList<Bullet> bullets, Player player){
        // checks if player was hit by alien
        if (dir == 1){
            if(player.getRect().contains(x,y)){
                return true;
            }
        }
        return false;
    }

    public Rectangle getRect(){ // returns Rectangle
        return new Rectangle(x, y, 3, 15);
    }

    public void draw(Graphics g){ // draws bullet onto screen 
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 3, 15);
    }
}
