import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;

/**Hero Class<p>
  * This is the hero sprite class. It manages the positioning of the cannon, updates the 
  * country and displays all the components.
  * <p>
  * <b>Instance Variables: </b>
  * x: x coordinate of cannon
  * <p>
  * Y: final variable to set y coordinate of hero
  * <p>
  * width: width of the cannon
  * <p>
  * heroCannon: cannon image
  * <p>
  * countries: list of countries
  * <p>
  * temoCoList: temporary list of countries that is changed and manipulated
  * <p>
  * currentIndex: index of current country in the list
  * <p>
  * tempIndex: index of currentCountry in the temporary list
  * <p>
  * currentCountry: current country on the screen
  * 
  * @author Utkarsh Lamba
 * */
public class Hero {
 
  private int x;
  private static final int Y = 440;
  private int width;
  private Image heroCannon;  
  public ArrayList<String> countries;
  public ArrayList<String> tempCoList;
  public int currentIndex;
  public int tempIndex;
  public int lives;  
  public String currentCountry;
  
  /**Contructor<p>
    * The countructor sets the starting x coordinate of the cannon, loads the image and the lists, randomly
    * sets the current country and the index variables accordingly.
    * 
    * @param x: starting x coordintate
    * @param countryFileName path to list of countries
    * @param header country text file header
   * */
  public Hero (int x, String countryFileName, String header){
    this.x = x;
    ImageIcon ii = new ImageIcon ("resources\\images\\cannon.png");
    width = ii.getIconWidth();
    lives = 5;
    
    heroCannon = ii.getImage();
    
    countries = ReadFile.getArrayItems(countryFileName, header);
    tempCoList = ReadFile.getArrayItems(countryFileName, header);
    
    currentCountry = countries.get ((int)(Math.random()*countries.size()));
    currentIndex = countries.indexOf (currentCountry);
    tempIndex = countries.indexOf (currentCountry);
  }
  
  /**method public void move (int speed)<p>
    * This method is responsible for moving the cannon based on the specified speed.
   * */
  public void move(int speed){
    if (speed>0 && x<(800-width-2)) x+=speed;
    else {
      if (speed<0 && x>5) x+= speed;
    }
  }
 
  /**Mehtod public void paint (Graphics2D g)<p>
    * This method paint the hero and the coutnry on the screen
    * 
    * @param g graphics object used to paint on the screen
   * */
  public void paint (Graphics2D g){
    g.drawImage(heroCannon, x, Y, null);
    drawCenteredString(currentCountry, width, x, Y+93, g);
  }
  
  /**Method public void drawCenteredString(String s, int w, int startX, int h, Graphics2D g)<p>
    * This method is responsible fo drawing the country string in centered alignment.
    * 
    * @param s string to be painted
    * @param w width of the area
    * @param startX staring x cordinate of the painting area
    * @param g graphics object used to paint
   * */
  public void drawCenteredString(String s, int w, int startX, int h, Graphics2D g) {
    FontMetrics fm = g.getFontMetrics();
    int x = (w - fm.stringWidth(s)) / 2;
    g.drawString(s, startX + x, h);
  }
  
  /**Method public void changeCountry ()<p>
    * This method is responsible for changing and setting the country randomly.
   * */
  public void changeCountry (){
     currentCountry = tempCoList.get ((int)(Math.random()*tempCoList.size()));
     currentIndex = countries.indexOf (currentCountry); 
     tempIndex = tempCoList.indexOf (currentCountry);
  }
  
  /**Mehtod public int getX()<p>
    * Return the x coordinate of the cannon.
    * 
    * @return x coordinate of the cannon
   * */
  public int getX(){
    return this.x;
  }
  
  /**Mehtod public int getX()<p>
    * Return the Y coordinate of the cannon.
    * 
    * @return Y coordinate 
   * */
  public int getY(){
   return Y; 
  }
  
  /**Method public int getCannonCenter() 
    * Calculates and returns the center of the cannon.
    * 
    * @return center x coordinate of the cannon
   * */
  public int getCannonCenter (){
    return x+ width/2;
  }

}