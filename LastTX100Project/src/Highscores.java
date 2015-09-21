import javax.swing.*;
import java.awt.Color;
import java.util.Collections;
import java.awt.*;
import java.util.Scanner;

import java.util.ArrayList;
import java.io.*;

/**Class Highscores<p>
  * This class manages the highscores. It saves the user names and highscores and reads from them 
  * to keep records. It paints the highscors on the screen, updates the lists and formats the highscores
  * to the correct format.
  * <p>
  * <b>Instance Variables:</b>
  * <p>
  * printer: printer instance
  * <p>
  * hsLevel1, hsLevel1Names: level 1 highscores and user names
  * <p>
  * hsLevel2, hsLevel2NamesL level 2 highscores and user names
  * <p>
  * hsLevel3, hsLevel4NamesL level 4 highscores and user names
  * <p>
  * bg: highscores background image
  * <p>
  * icon: printer icon image
  * 
  * @author Nikita Lizogubenko
 * */
public class Highscores 
{
  private Printer printer;
  
  ArrayList<Long> hsLevel1;
  ArrayList<String> hsLevel1Names;
  
  ArrayList <Long> hsLevel2;
  ArrayList <String> hsLevel2Names;
  
  ArrayList <Long> hsLevel3;
  ArrayList <String> hsLevel3Names;
  
  public String userName = null;
  
  public Image bg;
  public Image icon;
  
  /**Constructor<p>
    * The constructor loads all the highscores files and images and instantiates the printer.
   * */
  public Highscores (){
    hsLevel1 = getHighscores ("saves\\Level 1 highscores.txt", "Level 1 highscores:");
    hsLevel2 = getHighscores ("saves\\Level 2 highscores.txt", "Level 2 highscores:");
    hsLevel3 = getHighscores ("saves\\Level 3 highscores.txt", "Level 3 highscores:");
    
    hsLevel1Names = ReadFile.getArrayItems ("saves\\Level 1 Usernames.txt", "Level 1 Usernames:");
    hsLevel2Names = ReadFile.getArrayItems ("saves\\Level 2 Usernames.txt", "Level 2 Usernames:");
    hsLevel3Names = ReadFile.getArrayItems ("saves\\Level 3 Usernames.txt", "Level 3 Usernames:");
    
    bg = (new ImageIcon ("resources\\images\\hsBg.png")).getImage();
    icon = (new ImageIcon("resources\\images\\printIcon.png")).getImage();
    
    printer = new Printer (hsLevel1Names, hsLevel1, hsLevel2Names, hsLevel2, hsLevel3Names, hsLevel3);
  }
  
  /**Method public static ArrayList getHighscores(String fileName, String header)<p>
    * This method is responsible for reading the long values in highscore files
    * 
    * @param fileName path to file containing highscores
    * @param header highscores file header
    * 
    * @return list containg the read highscores
   * */
  public static ArrayList<Long> getHighscores(String fileName, String header)
  {
    long temp=0;
    ArrayList<Long> list = new ArrayList<Long>();
 
    try {
      Scanner input = new Scanner (new File(fileName));
      if (input.nextLine().equals(header)){
      while (input.hasNextLong()){
          temp = input.nextLong();
          list.add (temp);
        }
      }
    }catch (Exception e) {
        System.out.println (e.getMessage());
      }
    return list;
  }
  
  /**Method public void runPrintProcedure()<p>
    * Prints the highscores
   * */
  public void runPrintProcedure (){
    printer.refresh (hsLevel1Names, hsLevel1, hsLevel2Names, hsLevel2, hsLevel3Names, hsLevel3);
   printer.printPlease(); 
  }
  
  /**Method public void paint (Graphics2D g)<p>
    * This method paints all the highscores and background on the screen.
   * */
  public void paint (Graphics2D g){
    g.drawImage (bg, 0, 0, null);
    g.setFont (new Font ("ArialBlack", Font.BOLD, 20));
    g.drawString ("HISHSCORES", 460, 100);
    g.setFont (new Font ("ArialBlack", Font.BOLD, 14));
    g.drawString ("LEVEL 1", 230, 150);
    g.drawString ("LEVEL 2", 500, 150);
    g.drawString ("LEVEL 3", 730, 150);
    int y = 185;
    for (int i = 0; i < hsLevel1.size(); i ++){
      g.drawString (hsLevel1Names.get(i), 200, y);
      g.drawString (SecondaryPanel.formatTime(hsLevel1.get(i)), 300, y);
      y+=25;
    }
    y = 185;
    for (int i = 0; i < hsLevel2.size(); i ++){
      g.drawString (hsLevel2Names.get(i), 460, y);
      g.drawString (SecondaryPanel.formatTime(hsLevel2.get(i)), 560, y);
      y+=25;
    }
    y = 185;
    for (int i = 0; i < hsLevel3.size(); i ++){
      g.drawString (hsLevel3Names.get(i), 690, y);
      g.drawString (SecondaryPanel.formatTime(hsLevel3.get(i)), 790, y);
      y+=25;
    }
    g.drawImage (icon, 480, 470, null);
    g.drawString ("Print (P)", 530, 490);
  }
  
  /**Method updateHishScores (int level, long time)<p>
    * This method updates the highscore lists. It is responsible for maintaining top 10 
    * highscores for each level.
    * 
    * @param level game level
    * @param time time (score) in the current game
   * */
  public void updateHighScores (int level, long time) {
    if (level == 1) {
      if (hsLevel1.size() <10) { 
        hsLevel1.add (time); 
        Collections.sort (hsLevel1);
        int i = hsLevel1.indexOf (time);
        hsLevel1Names.add (i, userName);
        
      }
      else {
        OUTER:
        for (int i = 0; i<hsLevel1.size(); i++){
          if (time < hsLevel1.get(i)) {
            for (int j = hsLevel1.size()-1; j>i;j--){
              hsLevel1.set(j, hsLevel1.get(j-1));
              hsLevel1Names.set(j, hsLevel1Names.get(j-1));
            }
            hsLevel1.set (i, time);
            hsLevel1Names.set (i, userName);
            break OUTER;
          }
        }        
      }
    }
    else if (level == 2){     
      if (hsLevel2.size() <10) { 
        hsLevel2.add (time); 
        Collections.sort (hsLevel2);
        int i = hsLevel2.indexOf (time);
        hsLevel2Names.add (i, userName);
      }
      else {
        OUTER:
        for (int i = 0; i<hsLevel2.size(); i++){
          if (time < hsLevel2.get(i)) {  
            for (int j = hsLevel2.size()-1; j>i;j--){
              hsLevel2.set(j, hsLevel2.get(j-1));
              hsLevel2Names.set(j, hsLevel2Names.get(j-1));
            }
            hsLevel2.set (i, time); 
            hsLevel2Names.set (i, userName);
            break OUTER;
          }
        }        
      }
    }
    else{
      if (hsLevel3.size() <10) { 
        hsLevel3.add (time); 
        Collections.sort (hsLevel3);
        int i = hsLevel3.indexOf (time);
        hsLevel3Names.add (i, userName);
      }
      else {
        OUTER:
        for (int i = 0; i<hsLevel3.size(); i++){
          if (time < hsLevel3.get(i)) {
            for (int j = hsLevel3.size()-1; j>i;j--){
              hsLevel3.set(j, hsLevel3.get(j-1));
              hsLevel3Names.set(j, hsLevel3Names.get(j-1));
            }
            hsLevel3.set (i, time);
            hsLevel3Names.set (i, userName);
            break OUTER;
          }
        }        
      }
    }
  }

  /**Method public void output (String fileName, ArrayList list, String header)<p>
    * This method is responsible for saving the highscore lists.
    * 
    * @param fileName path of file to which scores are saved
    * @param list list to be saved
    * @param header file header
   * */
  public void output (String fileName, ArrayList list, String header) {
    try {
      PrintWriter pr = new PrintWriter (new File (fileName));
      pr.println(header);
      for (Object o: list ) {
          pr.println (o);
        }
        pr.close();
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog (null, e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  
  /**Method public void save()<p>
    * This mehtod saves all the lists.
   * */
  public void save(){
    output ("saves\\Level 1 highscores.txt", hsLevel1, "Level 1 highscores:");
    output ("saves\\Level 2 highscores.txt", hsLevel2, "Level 2 highscores:");
    output ("saves\\Level 3 highscores.txt", hsLevel3, "Level 3 highscores:");
    
    output ("saves\\Level 1 Usernames.txt", hsLevel1Names, "Level 1 Usernames:");
    output ("saves\\Level 2 Usernames.txt", hsLevel2Names, "Level 2 Usernames:");
    output ("saves\\Level 3 Usernames.txt", hsLevel3Names, "Level 3 Usernames:");
  }
  
}