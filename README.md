# Space-Invaders
Recreation of Space Invaders, an arcade game in which the player can move left and right and shoot upwards to defeat the slowly descending rows of aliens. After all aliens are defeated, the aliens respawn lower and the game's pace increases.

**GAME MECHANICS:** 5 rows of aliens move back and forth every time they touch an edge - they will all move collectively. Randomly, the aliens will shoot down bullets. If the player is hit, they lose a life (start with 3, each completed level gain 1 with max 3 lives). Once all the aliens are killed, the next level begins. If the player loses all their lives, or an alien reaches the "moon", the player loses (game over). In this version, there are 3 levels, each one increases alien speeds at a higher frequency. 


**SPECIFIC GAME DETAILS:**
 -
GAME MECHANICS:
- pass level -> + 1 life from remaining, max 3 lives
- as levels progress, speed gets quicker
- new levels, progression of speed is quicker 

ALIENS:
- randomly shoot down missiles 
- when the player gets hit, game pauses for a small amount of time

BUNKERS:
- each time they get hit (by player or aliens), they deteriorate further where they were hit

LEVEL PROGRESSION:
- As aliens are defeated, their movement speed up
- Defeat all aliens: new aliens spawn, lower down the screen

PLAYER:
- move along the x-axis
- can shoot projectiles, consistent max speed (can't spam)
- have 3 lives 

ALIENS:
- five rows of eleven aliens that move left and right as a group
- aliens move down each time they reach a screen edge

UFO/MYSTERY SHIP:
- mystery ship will occasionally move across the top of the screen and hitting it gives bonus points

SCORING:
- bottom 2 rows - 10 pts
- middle 2 rows - 20 pts
- top row - 30 pts
- mystery ships - depends on how many times the player has shot, increments through the list below:
  
   [100, 50, 50, 100, 150, 100, 100, 50, 300, 100, 100, 100, 50, 150, 100, 50] 
