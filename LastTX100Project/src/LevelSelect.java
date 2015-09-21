import javax.swing.*;
import java.awt.*;

/**LevelSelect Class<p>
  * This class displays the level selection screen.
  * <p>
  * <b>Instance Variables: </b>
  * <p>
  * lsbg: level selection backgroun image
  * 
  * @author Nikita Lizogubenko
 * */
public class LevelSelect extends JPanel
{ 
  private Image lsbg;
  
  /**Constructor<p>
    * The constructor loads the background image.
   * */
  public LevelSelect()
  {
   lsbg = (new ImageIcon ("resources\\images\\LevelSelectBg.png")).getImage();

  }
  
  /**Method public void paint (Graphics2D g)<p>
    * Paints the image on the screen
    * 
    * @param g graphics object used to paint on the screen
   * */
  public void paint (Graphics2D g) {
    g.drawImage (lsbg, 0, 0, null);
  }
}
