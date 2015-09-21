import java.awt.*;
import javax.swing.*;
/**Instructions Class<p>
  * This class displays the instruction on the screen.
  * <p>
  * <b>Instance Variables: </b>
  * <p>
  * img: instructions image
  * @author Nikita Lizogubenko
 * */
public class Instruction 
{
  public Image img;
  
  /**Constructor<p>
    * The constructor loads the instructions image
   * */
  public Instruction(){
   img = (new ImageIcon ("resources\\images\\InstBg.png")).getImage();
  }
  
  /**Method public void paint (Graphics2D g)<p>
    * Paints the image on the screen
    * 
    * @param g graphics object used to paint on the screen
   * */
  public void paint (Graphics2D g){
    g.drawImage (img, 0, 0, null);
    g.setFont (new Font ("ArialBlack", Font.BOLD, 18));
    g.setColor (Color.BLACK);
    g.drawString ("Please see \"Help\" for more information", 77, 255);
}
}