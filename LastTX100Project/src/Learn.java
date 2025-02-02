import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;

/**Learn<p>
 * This class is responsible for the country/capital learning component of the game. 
  * It displays the countries of the current continent on the screen for a limited amount of time.
  * It also runs a countd down timer that indicates how much time user has left to memorize the capitals.
  * <p>
  * westernEurope: Weatern Europe countries and capitals image
  * <p>
  * southAmerica: South America countries and capitals image
  * <p>
  * aisa: Asia countries and capitals
  * <p>
  * level: current game level;
  * <p>
  * time: time in seconds
  * <p>
  * timer: timer object
  * <p>
  * tt: timer task object
  * 
  * @author Utkarsh Lamba
 * */
public class Learn {
  
  public Image westernEurope;
  public Image southAmerica;
  public Image asia;
  public Image button;
  public int time;
  public int level;
  public Timer timer;
  public TimerTask tt;
  
  /**Constructor<p>
    * The constructor loads all the images and sets the default time to 60 seconds
   * */
  public Learn () {
    westernEurope = (new ImageIcon ("resources\\images\\weLearn.png")).getImage(); 
    southAmerica= (new ImageIcon ("resources\\images\\saLearn.png")).getImage(); 
    asia = (new ImageIcon ("resources\\images\\aLearn.png")).getImage(); 
    button = (new ImageIcon ("resources\\images\\skipButton.png")).getImage(); 
    time = 60;
  }
  
  /**Method public void paint (Graphics2D g)<p>
    * This method paints the correct continent's image and the time
    * based on the current level in the game.
    * 
    * @param g graphics object used to paint on the screen
   * */
  public void paint (Graphics2D g) {
    if (level == 1) g.drawImage(westernEurope, 0, 0, null);
    else if (level == 2) g.drawImage(southAmerica, 0, 0, null);
    else g.drawImage(asia, 0, 0, null);
    g.setFont (new Font ("ArialBlack", Font.BOLD, 20));
    g.drawString ("Skip (S)", 110, 75);
    g.drawString ("Time Left: " + Integer.toString(time), 750, 75);
  }
  
  /**Method public void countDown()<p>
    * This method runs the countdown using the timer its timer task.
   * */
  public void countDown() {
    timer = new Timer();
    tt = new TimerTask() {
      public void run() {
        time--;
        if (time == 0)
          timer.cancel();
      }};
    timer.scheduleAtFixedRate(tt, 1000, 1000);
  }
  
}
