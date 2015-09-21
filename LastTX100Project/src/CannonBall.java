import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**CannonBall Class 
  * <p>
  * This class is a sprite for the cannon balls. It contians the positions variables of the
  * cannnoballs and provides functionality of painting the cannon balls and to check 
  * for collision. 
  * <p>
  * <b>Instance Variables:</b>
  * <p>
  * x: x coordinate of the cannon ball
  * <p>
  * y: y coordinate of the cannon ball
  * <p>
  * width: width of the cannon ball
  * <p>
  * height: height of the cannon ball
  * <p>
  * image: cannon ball image
  * 
  * @author Utkarsh Lamba
 * */
public class CannonBall {
 
  int x;
  int y;
  int width;
  int height;
  Image image;
  
 /**Constructor public CannonBall(int x, int y) <p>
   * The constructor sets the initial x and y values, loads the cannon ball image
   * and initializes the height and weight variables.
   * 
   * @param x x cordinate
   * @param y y coordinate
  * */
  public CannonBall (int x, int y) {
    this.x = x;
    this.y = y;
    ImageIcon ii = new ImageIcon("resources\\images\\CannonBall.png");
    this.image = ii.getImage();
    this.width = ii.getIconWidth();
    this.height = ii.getIconHeight();
  }  
  
  /**Method public void move() <p>
    * Move the cannon ball up by decresing the y value by 20
    * 
   * */
  public void move(){
      y -= 20;
  }
  
  /**Method public void paint(Graphics2D g2d)<p>
    * Paint the cannon ball on the screen on the specified x and y values.
    * @param g2d The graphcis object used for painting
   * */
  public void paint (Graphics2D g2d){
    g2d.drawImage(image, x, y, null);
  }
  
  /**Method public Rectangle getRect()<p>
    * Creates a rectangle for collision detection
    * @return rectangle the surrounds the cannon ball
   * */
  public Rectangle getRect(){
    return new Rectangle (x, y, width, height);
  }
}