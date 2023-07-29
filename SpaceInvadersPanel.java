/*
 * SpaceInvadersPanel.java 
 * Elsie Wang
 * 
 * Primary java game file. Runs methods (move, paints, actions performed) and holds variables that need to be 
 * accessed by various methods.
*/

// imports necessary packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class SpaceInvadersPanel extends JPanel implements KeyListener, MouseListener, ActionListener{	
	// FONTS
	int frameCnt = 0;
	Font fontLocal = null;

	// majority of variables, instances of classes, etc. are private to prevent user access
	// boolean arrays
	private boolean []keys; // keeps track of what keys are being pressed

	// ArrayLists (arrays that need to be able to have elements removed, primarily Rectangles so they aren't being 
	// checked for collisions/contains and aren't drawn onto screen)
	private ArrayList<Alien>aliens; 
	private ArrayList<Bullet>bullets;

	private ArrayList<Alien>remAliens; // ArrayLists to store elements to be removed 
    private ArrayList<Bunker>remBunkers;
	private ArrayList<Bullet>remBullet;

	private ArrayList<MysteryShip>mysteryShips; 
	private ArrayList<Bunker>bunkers;

	// counters
	Timer timer; // new timer
	static int frameNum; // counts amount of "frames" passed
	static int shootCoolDown; // keeps track of cool down between player shots (25 frames)
	static int shots; // keeps track of number of shots taken (used for mystery ship score)
	static int score; // keeps track of the current score 
	private int highscore; // current highscore 
	private boolean newHighscore = false; // if the current run beats the old highscore

    static Player player; // new player
	static int lives = 3; // keeps track of lives

	static String screen = "INTRO"; // keeps track of what the current screen is, starts with intro screen

	private int dir = 1; // direction of aliens (stored in this class because all aliens move same direction)
	private int vel = 10; // velocity of aliens
	private int freq = 1000; // frequency of the change in velocity of aliens
	private int lvl = 1; // current level

	private int curMouseX; // current mouse's x position 
	private int curMouseY; // current mouse's y position
	private boolean mbPressed; // stores if a mouse button is being pressed
	
	private boolean moveDown = false; // stores if the aliens should be/are moving down

	// IMAGES
	private Image livesPic;
	private Image []alienPics; // array of all the necessary alien images (used for intro screen)
	private Image titlePic;
	private Image bgPic;

	Sound sounds; // Sound class for sound effects
	
	public SpaceInvadersPanel(){
		String fName = "PressStart2P-vaV7.ttf"; // importing font file

    	InputStream is = SpaceInvadersPanel.class.getResourceAsStream(fName);
    	try{ // try and catch to creating a new local font (size 15)
    		fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(15f);
    	}
    	catch(IOException ex){
    		System.out.println(ex);	
    	}
    	catch(FontFormatException ex){
    		System.out.println(ex);	
    	}

		// loading images
		livesPic = new ImageIcon("images/player.png").getImage();
		titlePic = new ImageIcon("images/title.png").getImage();
		bgPic = new ImageIcon("images/space.png").getImage();
		alienPics = new Image[] {
			new ImageIcon("images/smallAlien1.png").getImage(), 
			new ImageIcon("images/mediumAlien1.png").getImage(),
			new ImageIcon("images/largeAlien1.png").getImage(), 
			new ImageIcon("images/mysteryShip.png").getImage()};

		sounds = new Sound();
		
		keys = new boolean[KeyEvent.KEY_LAST+1]; // boolean array of key pressed

		player = new Player(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE); // Player with parameters of keys for each movement (A - left, D - right, SPACE - shoot)
		
		setPreferredSize(new Dimension(512, 448)); // sets size of window
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);

		// starts timer after everything is loaded in
		timer = new Timer(20, this); // "frames per second", (ie. how often graphics are drawn in)
		timer.start();
	}

	public void startLevel(int lvl){
		frameNum = 0; 
		shootCoolDown = 25; // can only shoot at most once every 25 "frames"
		shots = 0; 
		if (lives <= 2){ // if you get to new level, increase lives by one (max 3)
			lives ++;
		}
		dir = 1;
		vel = 10;

		aliens = new ArrayList<Alien>();
		remAliens = new ArrayList<Alien>();
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 11; j++){
				aliens.add(new Alien(i, j, vel)); // adds 5 rows of 11 aliens 
			}
		}

		bullets = new ArrayList<Bullet>(); 
		remBullet = new ArrayList<Bullet>();

		mysteryShips = new ArrayList<MysteryShip>();

		bunkers = new ArrayList<Bunker>();
		remBunkers = new ArrayList<Bunker>();
		for (int i = 60; i <= 390; i += 110){ // 60 is margin, 110 space between beginning of bunker groups
			for (int j = 0; j < 60; j += 20){ // 20 is length of each square piece
				bunkers.add(new Bunker(i+j, 0));
				// adds 4 groups of 3 bunkers (seperated as square pieces because of how they can be destroyed)
				// i is x position based on 4 groups, j is x position based on each square pieces' start
			}
		}
	}

	public void move(){ // move method: controls movement of elements and everything caused by movements
		if (screen == "GAME"){ // elements are only able to move once "game" starts running	
			player.move(keys); // player moves depending on key pressed
			int bulX = player.shoot(bullets, keys, shootCoolDown);
			if (bulX != -1){
				bullets.add(new Bullet(10, "PLAYER", bulX+15, 420));
				shootCoolDown = 25;
            	shots ++;
				sounds.playShotSound();
			}

			if (lives <= 0){ // if no lives are remaining then gameover
				screen = "GAMEOVER";
				sounds.playDeathSound();
			}

			if (aliens.size() == 0){ // if no aliens remain then player won level
				sounds.playBeatLevelSound();
				screen = "WON";
				lvl ++;
				if(lvl == 1){ // different "frequency" (how often velocity increases) depending on level, speeds up more often in harder levels
					freq = 1000;
				}
				else if(lvl == 2){
					freq = 700;
				}
				else if (lvl == 3){
					freq = 500;
				}
				else if (lvl == 4){ // last level, screen is set to done and finale song is played
					sounds.playWinSound();
					screen = "DONE";
				}
			}

			if(frameNum % freq == 0){ // velocity increases once frequency is met
				vel ++;
			}
			
			if (frameNum % 25 == 0){ // "staggered" movement 
				if (moveDown){ // if alien reached edge:
					for (int j = 0; j < aliens.size(); j++){
						aliens.get(j).moveDown(moveDown); // every alien moves down
					}
					moveDown = false;
				}
				else{
					for (int j = 0; j < aliens.size(); j++){
						aliens.get(j).move(dir); // moves every alien in correct direction
						// (1/80 is frequency of each alien shooting)
						if (Util.randint(1,80) == 1){ // gets random int from 0 to 80, if it is 1: 
							// adds bullet with position of alien 
							bullets.add(new Bullet(10, "ALIEN", aliens.get(j).x()+10, aliens.get(j).y())); 
						}
					}
					
					for (int j = 0; j < aliens.size(); j++){
						// if any alien has reached edge, dir changes to opposite direction
						if(aliens.get(j).edge(dir)){
							if (aliens.get(j).y() >= 340){ // if alien reaches "moon" then game is over
								screen = "GAMEOVER";
							}
							dir *= -1;
							moveDown = true;
							break; // stops once one alien is found that touches the edge
						}
					}
				}

			}
			if (mysteryShips.size() > 0){ // if a mystery ship exists, moves it
				if (mysteryShips.get(0).move()){
					mysteryShips.remove(0);
				}
			}
	
			if(frameNum % 800 == 0){ // creates a new mystery ship every 800 "frames", starts sound
				sounds.playShipSound();
				mysteryShips.add(new MysteryShip(frameNum, frameNum));
			}
	
			for (int b = 0; b < bullets.size(); b++){ // moves every bullet and checks if it hits something
				bullets.get(b).move();

				int i = bullets.get(b).checkHitBunker(bullets, bunkers);
				if (i != -1){ // check if it hits a bunker
					remBullet.add(bullets.get(b));
					shootCoolDown = 0;
					if (bunkers.get(i).state() == 3){ // if the bunker segment is on last "state", adds it to remove arraylist
                        remBunkers.add(bunkers.get(i));
                    }
                    else{
						int xb = bunkers.get(i).x();
						int curState = bunkers.get(i).state();
						bunkers.set(i, new Bunker(xb, curState+ 1)); // otherwise, state worsens by one
                    }
				}

				i = bullets.get(b).checkHitAliens(bullets, aliens); 
				if (i != -1){ // checks if player bullet hits an alien, if it does removes bullet and alien and increases score
					remBullet.add(bullets.get(b));
					remAliens.add(aliens.get(i));
                    score += aliens.get(i).points();
                    shootCoolDown = 0; // resets cooldown 
				}

				if (bullets.get(b).checkHitMysteryShip(bullets, mysteryShips)){ // checks if bullet hits ship
					sounds.playShipShotSound();
					remBullet.add(bullets.get(b));
					score += mysteryShips.get(0).pts;
                    mysteryShips.remove(0); // removes mystery ship
				}

				if (bullets.get(b).checkHitPlayer(bullets, player)){ // checks if alien hits player
					remBullet.add(bullets.get(b));
					sounds.playDeathSound();
					lives --;
              	 	Util.delay(500); // delay after player loses life
                	Player.x = 75; // resets player position 
					
				}

				if (remBullet != null){ // removes all elements that have been hit 
					bullets.removeAll(remBullet);
				}
				if (remAliens != null){
					aliens.removeAll(remAliens);
				}
				if (remBunkers != null){
					bunkers.removeAll(remBunkers);
				}
				
			}
		}

	}
		
	@Override
	public void keyPressed(KeyEvent ke){ 
		int key = ke.getKeyCode();
		keys[key] = true; // sets the current key pressed to be true 
	}

	@Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false; // when key is released, set that key to false
	}

	@Override
	public void keyTyped(KeyEvent ke){}

	@Override
	public void	mouseClicked(MouseEvent e){}
	@Override
	public void	mouseEntered(MouseEvent e){}
	@Override
	public void	mouseExited(MouseEvent e){}

	@Override
	public void	mousePressed(MouseEvent e){
		mbPressed = true; // when mouse is pressed (held)
	}
	@Override
 	public void	mouseReleased(MouseEvent e){
		mbPressed = false; // when mouse is released set to false
	}

	@Override
	public void paint(Graphics g){ // method that "paints" all necessary graphics 
		g.setFont(fontLocal);
		
		if (screen == "INTRO"){ // on intro screen 
			g.drawImage(bgPic, 0, 0, null);

			// creates rectangle for start button
			Rectangle startBttn = new Rectangle(100, 350, 312, 50);
			Graphics2D g2d = (Graphics2D)g; // used to be able to draw rectangles using Rectangle class

			g.setColor(Color.WHITE);
			g.drawImage(titlePic, 165, 20, null);
			g2d.draw(startBttn); // drawing start button onto screen

			g.drawString("PLAY SPACE INVADERS", 120, 385);
			if (startBttn.contains(curMouseX, curMouseY)){ // if mouse is on start button
				g.setColor(Color.GREEN); // when mouse is hovering over button, it turns green
				g2d.draw(startBttn);
				g.drawString("PLAY SPACE INVADERS", 120, 385);

				g.setColor(Color.WHITE);
				if (mbPressed){ // when start button is pressed then game "starts"
					sounds.playGameStartSound();
					screen = "GAME"; 
					startLevel(lvl);
				}
			}

			if (frameNum >= 20){ // 20 frame delay from starting program
				String [] points = {"10", "20", "40", "???"}; // array of points needed to be displayed
				for(int i = 0; i < 4; i++){  // image and respective point value appears successively
					if (frameNum >= (20+(20*i))){ // 20 (initial delay) + (20*i) (frame when each point starts to be shown)
						// draws images right aligned 
						g.drawImage(alienPics[i], 200 - alienPics[i].getWidth(null), 160 + (i*40), null);
						if(frameNum >= 20+10+(20*i)){ // draws point values 
							g.drawString(" = " + points[i] +  " PTS", 200, 177 + (i*40));
						}
					}
				}
			}

			g.drawString("HIGHSCORE: ", 190, 120);
			String highscoreString = String.valueOf(highscore);
			g.drawString(highscoreString, (512/2) - (highscoreString.length()*6), 140);
			// draws high score centre aligned (6 is approximate px length of each character)
			
		}

		else if (screen == "GAME"){ // on game screen 
			g.drawImage(bgPic, 0, 0, null);
			player.draw(g);

			for (int j = 0; j < aliens.size(); j++){ // draws all aliens
				aliens.get(j).draw(g, frameNum);
			}

			for (int b = 0; b < bullets.size(); b++){ // draws all bullets
				bullets.get(b).draw(g);
			}

			if (mysteryShips.size() > 0){ // draws mystery ship (if exists)
				mysteryShips.get(0).draw(g);
			}

			for (int i = 0; i < bunkers.size(); i ++){ // draws all bunkers
				bunkers.get(i).draw(g);
			}

			// displays current score
			g.setColor(Color.WHITE);
			g.drawString("SCORE", 15, 30);
			g.setColor(Color.GREEN);
			g.drawString("" + score, 95, 30); 

			// displays highscore
			g.setColor(Color.WHITE);
			g.drawString("HIGH SCORE", 15, 55);
			if (newHighscore){ // if current highscore is beat, highscore changes to be green and is updated throughout run
				g.setColor(Color.GREEN);
			}
			g.drawString("" + highscore, 170, 55);

			// displays lives remaining
			g.setColor(Color.WHITE);
			g.drawString("LIVES", 300, 28);
			g.setColor(Color.GREEN);
			for (int life = 0; life < lives; life++){
				g.drawImage(livesPic, 380 + (45*life), 10, null);
			}

			g.setColor(Color.WHITE);
			g.drawString("LEVEL: " + lvl, 380, 55);

		}

		else if (screen == "GAMEOVER"){ // on gameover screen 
			g.drawImage(bgPic, 0, 0, null);
			g.setColor(Color.WHITE);

			// displays "GAME OVER"
			g.drawString("GAME OVER", 190, 170);

			// displays score gotten in game
			g.drawString("YOUR SCORE WAS:", 150, 270);
			String scoreString = String.valueOf(score);
			g.drawString(scoreString, (512/2) - (scoreString.length()*6), 290);

			// "button"/rectangle for retrying the game
			Rectangle retryBttn = new Rectangle(178, 200, 160, 40);
			g.setColor(Color.WHITE);
			Graphics2D g2d = (Graphics2D)g;
			g2d.draw(retryBttn);
			g.drawString("PLAY AGAIN", 190, 230);
			if (retryBttn.contains(curMouseX, curMouseY)){
				g.setColor(Color.GREEN); // turns green when hovered over
				g2d.draw(retryBttn);
				g.drawString("PLAY AGAIN", 190, 230);
				g.setColor(Color.WHITE);
				if (mbPressed){
					sounds.playGameStartSound(); // when pressed, sound is played and game is restart from level 1
					screen = "GAME";
					lvl = 1;
					lives = 3;
					score = 0;
					startLevel(lvl);
				}
			}

		}
		// when level is beat
		else if (screen == "WON"){
			startLevel(lvl); // starts next level automatically
			screen = "GAME";
		}
		else if (screen == "DONE"){
			g.drawImage(bgPic, 0, 0, null);
			g.setColor(Color.WHITE);
			g.drawString("YOU WIN!", 210, 150);

			// "button"/rectangle for restarting the game
			Rectangle restartBttn = new Rectangle(178, 200, 160, 40);
			g.setColor(Color.WHITE);
			Graphics2D g2d = (Graphics2D)g;
			g2d.draw(restartBttn);
			g.drawString("PLAY AGAIN", 185, 230);

			// displays score of that run
			g.drawString("YOUR SCORE WAS:", 150, 270);
			String scoreString = String.valueOf(score);
			g.drawString(scoreString, (512/2) - (scoreString.length()*6), 290); // centres score

			if (restartBttn.contains(curMouseX, curMouseY)){
				g.setColor(Color.GREEN); // turns green when hovered over
				g2d.draw(restartBttn);
				g.drawString("PLAY AGAIN", 185, 230);
				g.setColor(Color.WHITE);
				if (mbPressed){ // when restart is pressed, game is set to level 1 
					sounds.playGameStartSound();
					screen = "GAME";
					lvl = 1;
					lives = 3;
					score = 0;
					startLevel(lvl);
				}
			}
			
		}
    }

	@Override
	public void actionPerformed(ActionEvent e){
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		curMouseX = mouse.x - offset.x; // calculates current mouse x and y positions considering offset
		curMouseY = mouse.y - offset.y;
		frameNum ++; // frame counter increases by 1 
		shootCoolDown --; // shoot cool down decreases by 1
		move(); // calls move method
		repaint(); // repaints all elements

		try{ 
			Scanner fin = new Scanner(new BufferedReader(new FileReader("highscore.txt")));	
			highscore = fin.nextInt();
			if (score > highscore){ // if current score is higher than highscore stored in file...
				newHighscore = true;
				highscore = score;
				try{
					PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt")));
					fout.print(highscore); // updates highscore on highscore.txt
					fout.close();
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
	}

}