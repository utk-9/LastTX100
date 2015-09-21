import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import java.util.Iterator;
/**Game Class<p>
  * This class combnines all the primary components of the game.
  * It include the hero, enemies and the cannon balls. It also provides different functionality such as
  * checking for collision and updating and painting primary game components.
  * <p>
  * <b>Instance Variables: </b>
  * <p>
  * hero: hero instance
  * <p>
  * enemyList: list that contains all the enemy objects
  * <p>
  * cannonBallList: list that contains all the cannnoBall objects
  * <p>
  * animationList: list that containts all the animation objects
  * <p>
  * timeLastEnemyCreated: time when the last enemy was created
  * <p>
  * timeBetweenEnemies: time between creating of enemies
  * <p>
  * speed: speed of enemies
  * 
  * @author Utkarsh Lamba
 * */
public class Game{
  
  public ArrayList<Enemy> enemyList;
  public Hero hero;
  public ArrayList<CannonBall> cannonBallList;
  public ArrayList <Animation> animationList;
  private long timeLastEnemyCreated;
  private long timeBetweenEnemies;
  private int speed;
  
  /**Constructor:<p>
    * The constructor instantiates the hero and ArrayList objects. It sets the enemy speed 
    * and sets initial values for time variables.
    * 
    * @param countryPath path the text file containing the countries list
    * @param countryHeader file header to ensure file being read is correct
    * @param speed speed of the enemies
   * */
  public Game(String countryPath, String countryHeader, int speed){
    hero = new Hero (500, countryPath, countryHeader);
    enemyList = new ArrayList <Enemy> ();
    cannonBallList = new ArrayList<CannonBall>();
    animationList = new ArrayList <Animation>();

    this.speed = speed;
    timeLastEnemyCreated = 0;
    timeBetweenEnemies = 3000000000L;
  }
  
  /**Method public void updateEnemy()<p>
    * This method deletes and adds the enemy ships based on their position and time passed.
    * It also sets the lives left accordingly.
   * */
  public void updateEnemy (){
    for (Iterator <Enemy> iterator = enemyList.iterator(); iterator.hasNext();){
      Enemy e = iterator.next();
      if (e.getY()>555) {
        hero.lives -= 1;
        iterator.remove();
      }
    }      
    if ((System.nanoTime() - timeLastEnemyCreated) > timeBetweenEnemies){
      enemyList.add (new Enemy (speed));
      timeLastEnemyCreated = System.nanoTime();
    }
  }
  
  /**Method public void shoot()<p>
    * This method adds cannon balls to the list for being shot.
   * */
  public void shoot (){
    cannonBallList.add (new CannonBall (hero.getCannonCenter(), hero.getY()-2));
    FrameworkAPP.spaceDown = false;
  }
  
  /**Method public boolean checkCollision()<p>
    * This method is responsible for collision detection. It makes the necessary 
    * adjustments to the lists accordingly
    * 
    * @return true if collision occured
   * */
  public boolean checkCollision (){
   for (int i = 0; i <enemyList.size(); i++) {
        for (int m = 0 ; m < cannonBallList.size(); m++) {
          if (cannonBallList.get(m).getRect().intersects (enemyList.get(i).getRect())){
            animationList.add (new Animation(134, 134, 12, 45, false, 
                                            enemyList.get(i).getX()-50, enemyList.get(i).getY()-50, 
                                            150));
           enemyList.remove (i);
           cannonBallList.remove (m);
           hero.tempCoList.remove (hero.tempIndex);
           
           return true;
          }
        } 
   }
   return false;
  }
  
  public void explodeAll() {
    for (int i = 0; i< enemyList.size(); i++) {
     animationList.add(new Animation(134, 134, 12, 45, false, 
                                            enemyList.get(i).getX()-50, enemyList.get(i).getY()-50, 
                                            150));
     enemyList.remove(i);
     i-=1;
    }
    
  }
  
  /**Method public void updateExplosions()<p>
    * this method is responsible for removing the animations that have been played.
   * */
  public void updateExplosions()
    {
        for(int i = 0; i < animationList.size(); i++)
        {
          if(!animationList.get(i).active){
                animationList.remove(i);
          }            
        }
    }
  /**Method public void moveHero (int speed)<p>
    * This method moves the hero with the specified speed.
    * 
    * 
    * @param speed speed of the hero
   * */
  public void moveHero (int speed){
    hero.move(speed);
  }
}