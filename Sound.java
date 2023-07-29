/*
 Sound.java 
 Elsie Wang
 Sound class that loads all sounds and has methods to play them.
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

class Sound extends JFrame implements ActionListener
{
  private SoundEffect shotSound, beatLevelSound, deathSound, gameStartSound, shipShotSound, winSound, shipSound;
  
  public Sound()
  {
    // imports all sound files
    shotSound = new SoundEffect("sounds/shot.wav");
    beatLevelSound = new SoundEffect("sounds/beatLevel.wav");	
    deathSound = new SoundEffect("sounds/death.wav");
    gameStartSound = new SoundEffect("sounds/gameStart.wav");
    shipShotSound = new SoundEffect("sounds/shipShot.wav");
    winSound = new SoundEffect("sounds/win.wav");
    shipSound = new SoundEffect("sounds/ship.wav");
 
  }
  
  public void actionPerformed(ActionEvent ae){
  }

  public void playShotSound(){ // plays sound for player shooting 
    shotSound.stop();
    shotSound.play();
  }

  public void playBeatLevelSound(){ // plays sound for when a level is beaten
    beatLevelSound.stop();
    beatLevelSound.play();
  }

  public void playDeathSound(){ // plays sound for when player dies
    deathSound.stop();
    deathSound.play();
  }

  public void playGameStartSound(){ // plays sound for start button 
    gameStartSound.stop();
    gameStartSound.play();
  }

  public void playShipSound(){ // plays sound for when mystery ship appears
    shipSound.stop();
    shipSound.play();
  }

  public void playShipShotSound(){ // plays sound for when the ship is shot by player
    shipShotSound.stop();
    shipShotSound.play();
  }

  public void playWinSound(){ // plays sound for when entire game is won 
    winSound.stop();
    winSound.play();
  }

  	
  public static void main(String args[]){
  	new Sound().setVisible(true);
  }
}

class SoundEffect{
    private Clip c;
    public SoundEffect(String filename){
        setClip(filename);
    }
    public void setClip(String filename){
        try{
            File f = new File(filename);
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
        } catch(Exception e){ System.out.println("error"); }
    }
    public void play(){
        c.setFramePosition(0);
        c.start();
    }
    public void stop(){
        c.stop();
    }
}