import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Class GameCombine
 * <p>
 * The GameCombin class is the class that brings all the components of the game together.
 * It contains Game and SecondaryPanel. It provides functionality for checking if the correct capital is 
 * selected, update the game components and paint the correct elements on screen.
 * <p>
 * <b>Instance Variables:</b>
 * sp: SecondaryPanel instance
 * <p>
 * game: Game instance
 * <p>
 * isRunning: whether or not the game is current running (paused or not)
 * <p>
 * dangerZone: Hero's base image
 * <p>
 * textBg: text background image
 * <p>
 * pauseBg: screen image when game is paused
 * <p>
 * hasWon: final winner screen image
 * 
 * @author Utkarsh Lamba
 * */

public class GameCombine{
  
  public SecondaryPanel sp;
  public Game game;
  public Image background;
  public boolean isRunning;
  public Image dangerZone;
  public Image textBg;
  public Image pauseBg;
  public Image hasWon;
  
  /**Constructor<p>
    * The constructor loads all the images, instantiates game  and sp, ensure all the keys are released initially,
    * sets the gameStartTime, puts the game is running mode and updates the capital options.
    * 
    * @param countryPath path to file containing countries list
    * @param capitalPath path to file containing capitals list
    * @param countryHeader country file header
    * @param capitalHeader capital file header
    * @param enemySpeed speed of the enemies
   * */
  public GameCombine(String countryPath, String capitalPath, String countryHeader, String capitalHeader, int enemySpeed) {
    super();
    background = (new ImageIcon("resources\\images\\background2.png")).getImage();
    dangerZone = (new ImageIcon("resources\\images\\dangerZone.png")).getImage();
    textBg = (new ImageIcon("resources\\images\\textBg.png")).getImage();
    pauseBg = (new ImageIcon ("resources\\images\\pause.png")).getImage();
    hasWon = (new ImageIcon ("resources\\images\\finished.png")).getImage();
    
    game = new Game (countryPath, countryHeader, enemySpeed);
    sp = new SecondaryPanel(game, capitalPath, capitalHeader);
    
    FrameworkAPP.releaseAllKeys();
    
    sp.gameStartTime = System.nanoTime();
    isRunning = true;
    sp.updateOptions();
  }
  
  /**Method public void checkMatch ()<p>
    * This method checks if the correct capital is selected by the player
   * */
  public void checkMatch () {
    if (sp.capitals.indexOf (sp.selectedCapital) == game.hero.currentIndex) {
      game.shoot();
    }
  }
  
  /**Method public void updateGame()<p>
    * This method update the hero, enemies, cannon balls and explosions.
   * */
  public void updateGame (){
            if (game.checkCollision() && game.hero.tempCoList.size() > 0){
              sp.enemyKilled += 1;
              game.hero.changeCountry();
              sp.updateOptions();
            }
            sp.gameTime = System.nanoTime() - sp.gameStartTime - sp.pauseTime;
            game.updateEnemy(); 
            for (Enemy e: game.enemyList) {
              e.move(1);
            }
            for (CannonBall cb : game.cannonBallList) {
              cb.move(); 
            } 
            game.updateExplosions();          
  }
   
  /**Method public void paint (Graphics g)<p>
    * This method is responsible for painting all the components of the actual game (in gameplay) on the screen
   * */
    public void paint(Graphics g){
      g.drawImage (background, 0, 0, null);
      g.drawImage (dangerZone, 0, 455, null);
      g.drawImage (textBg, game.hero.getX() - 10, 520, null);
      g.setFont (new Font ("Arial", Font.BOLD, 14));
      Graphics2D g2d = (Graphics2D) g;
      game.hero.paint (g2d);
      for (Enemy e: game.enemyList) {
        e.paint (g2d);
      }
      for (CannonBall cb:game.cannonBallList){
        cb.paint(g2d); 
      }
      for (int i = 0; i<game.animationList.size(); i++){
        game.animationList.get(i).paint (g2d); 
      }
      sp.paint(g2d, game.hero.lives);
    }
  }