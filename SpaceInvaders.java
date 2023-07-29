/*
 * SpaceInvaders.java 
 * Simple Game Assignment ICS4U-02
 * Elsie Wang
 * Executable java file that runs the Space Invaders game. 
 * 
 * This version of Space Invaders is a simple game, in which the player can move left and right and shoot. 5 rows of 
 * aliens move back and forth everytime they touch an edge, they will all move collectively. Randomly, the aliens will shoot 
 * down bullets, if the player is hit, they lose a life (start with 3, each completed level gain 1 with max 3 lives). 
 * Once all the aliens are killed, the next level begins. If the player loses all their lives, or an alien reaches 
 * the "moon", the player loses (game over). In this version, there are 3 levels, each one increases alien speeds
 * at a higher frequency. (for more game details refer to "Game Mechanics.txt")
*/

// imports necessary packages
import javax.swing.*;

public class SpaceInvaders extends JFrame{
	SpaceInvadersPanel game = new SpaceInvadersPanel(); // creates a new instance of SpaceInvadersPanel
		
    public SpaceInvaders() {
		super("Space Invaders"); // changes the window name 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when the window is closed, the program is exited 
		add(game); 
		setResizable(false); // sets the screen to be not resizable 
		pack(); 
		setVisible(true); // sets the window to be visible 
    }
    
    public static void main(String[] arguments) {
		SpaceInvaders frame = new SpaceInvaders(); 
    }
}
