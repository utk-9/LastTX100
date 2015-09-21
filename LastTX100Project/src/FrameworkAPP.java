import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**FrameworkAPP Class<p>
  * This is the master class. It combines all the components of the game. 
  * It manages the games states and displays, runs and registers the correct elements 
  * based on the current game state. It also contains the main game loop and is responsible
  * for managing key input from the user.
  * <p>
  * <b>Instance Variables:</b>
  * <p>
  * gameCombine: Instance of GameCombine
  * <p>
  * mainMenu: Instance of MainMenu
  * <p>
  * highScores: Instance of Hishscores
  * <p>
  * instructions: Instance of Instructions
  * <p>
  * levelSelection: Instance of LevelSelect
  * <p>
  * learn: Instance of Learn
  * <p>
  * aDown, dDown, spaceDown, upDown, downDown, leftDown, rightDown: Static variables to keep track of
  * whether these keys are pressed of not
  * <p>
  * currentLevel: current level in the game
  * <p>
  * GameState: Manages the different game states such as playing, main menu, level selection etc
  * <p>
  * gameState: current state the game is in
  * 
  * 
  * @author Utkarsh Lamba
  * */
public class FrameworkAPP extends JPanel {
  
  private static enum GameState {PLAYING, MAIN_MENU, HIGH_SCORES, INSTRUCTIONS, LEVEL_SELECT, LEARNING, HAS_WON}
  private GameState gameState;
  
  private GameCombine gameCombine;
  private MainMenu mainMenu;
  private Highscores highScores;
  private Instruction instruction;
  private LevelSelect levelSelection;
  private Learn learn;
  
  public static boolean aDown= false;
  public static boolean dDown = false;
  public static boolean spaceDown = false;
  public static boolean upDown = false;
  public static boolean downDown = false;
  public static boolean leftDown = false;
  public static boolean rightDown = false;
  
  private int currentLevel;
  
  /**Constructor
    * The constructor makes the Jpanel and instantiates the other global instance variables. It sets the 
    * initial game state to main menu (where the game starts) and the starting level to 1. It adds a key listener
    * to the panel which handles all the key input. Different inputs are handled based on the game state. It 
    * performs various game functions based on the key input. It is responsible for chaning game screens, pausing,
    * quiting and performing all other game state specific functions. Finally, 
    * it ensures the panel has focus.
   * */
  private FrameworkAPP (){
    super();
    
    gameState = GameState.MAIN_MENU;
    
    mainMenu= new MainMenu ();
    instruction = new Instruction();
    levelSelection = new LevelSelect();
    highScores = new Highscores();
    learn = new Learn();
    
    
    currentLevel = 1;
    addKeyListener (new KeyListener() {
      public void keyPressed (KeyEvent e){
        int code = e.getKeyCode();
        
        switch (gameState) {
          case PLAYING:
            if (gameCombine.isRunning){
            if (code == KeyEvent.VK_D) dDown = true;
            else if (code == KeyEvent.VK_A) aDown = true;
            else if (code == KeyEvent.VK_SPACE)  spaceDown = true;
            else if (code == KeyEvent.VK_UP) upDown = true;
            else if (code == KeyEvent.VK_RIGHT)rightDown = true;
            else if (code == KeyEvent.VK_DOWN) downDown = true;
            else if (code == KeyEvent.VK_LEFT) leftDown  =true;
          }
            if (code == KeyEvent.VK_P) {
              
              if (gameCombine.isRunning) {
                gameCombine.sp.pauseStartTime = System.nanoTime(); 
              }
              else {
                gameCombine.sp.pauseTime += System.nanoTime() - gameCombine.sp.pauseStartTime;
              }
              gameCombine.isRunning = !gameCombine.isRunning; 
              releaseAllKeys();
            }
            else if (code == KeyEvent.VK_ESCAPE) {
              int answer = JOptionPane.showConfirmDialog 
                (null, "Are you sure you want to quit current game?",
                 "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                gameState = GameState.MAIN_MENU;
              }
            }
            
            break;
          case MAIN_MENU:
            if (code == KeyEvent.VK_I) gameState = GameState.INSTRUCTIONS;
            else if (code == KeyEvent.VK_P) { 
              if (highScores.userName == null)
                highScores.userName = JOptionPane.showInputDialog ("Enter your user name: ");
              if (highScores.userName.equals("")) {
                highScores.userName=null;
               JOptionPane.showMessageDialog (null, "You must enter a username", "Username",JOptionPane.ERROR_MESSAGE); 
              }
              if (highScores.userName!= null) gameState = GameState.LEVEL_SELECT;
            }
            else if (code == KeyEvent.VK_H) gameState = GameState.HIGH_SCORES;
            else if (code == KeyEvent.VK_ESCAPE) {
              int answer = JOptionPane.showConfirmDialog 
                (null, "Are you sure you want to exit game?",
                 "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                highScores.save();
                System.exit(0);
              }
            }
            else if (code == KeyEvent.VK_Y){
              try{
                Runtime.getRuntime().exec("hh.exe help\\Help.chm");
              } catch (Exception re) {}
            }
            break;
          case INSTRUCTIONS:
            if (code == KeyEvent.VK_ESCAPE) gameState = GameState.MAIN_MENU;
            break;
          case HIGH_SCORES:
            if (code == KeyEvent.VK_ESCAPE) gameState = GameState.MAIN_MENU; 
            else if (code == KeyEvent.VK_P) highScores.runPrintProcedure();
            break;
          case LEVEL_SELECT:
            if (code == KeyEvent.VK_E) {
            currentLevel = 1;
            learn.level = 1;
            gameState = GameState.LEARNING;
            learn.time = 60;
            learn.countDown();
          }
            else if (code == KeyEvent.VK_M) 
            {
              currentLevel = 2;
              learn.level = 2;
              gameState = GameState.LEARNING;
              learn.time = 75;
              learn.countDown();
            }
            else if (code == KeyEvent.VK_H) 
            {
              currentLevel = 3;
              learn.level = 3;
              gameState = GameState.LEARNING; 
              learn.time = 90;
              learn.countDown();
            } 
            else if (code == KeyEvent.VK_ESCAPE) gameState = GameState.MAIN_MENU;
            break;
          case LEARNING:
            learn.timer.cancel();
            setCurrentLevel();
            if (code == KeyEvent.VK_S) gameState = GameState.PLAYING;
          case HAS_WON:
            if (code == KeyEvent.VK_ESCAPE) gameState = GameState.MAIN_MENU;
        }
      }
      
      public void keyTyped (KeyEvent y) {}
      public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();
        switch (gameState){
          case PLAYING:
            if (gameCombine.isRunning){
            if (code == KeyEvent.VK_D) dDown = false;
            else if (code == KeyEvent.VK_A) aDown = false;
            else if (code == KeyEvent.VK_SPACE)  spaceDown = false;
            else if (code == KeyEvent.VK_UP) upDown = false;
            else if (code == KeyEvent.VK_RIGHT)  rightDown = false;
            else if (code == KeyEvent.VK_DOWN)downDown = false;
            else if (code == KeyEvent.VK_LEFT)  leftDown  =false;
          }
            break;
        }
      }
    });      
    this.setFocusable (true);  
  }
  
  /**Method public void releaseAllKeys()<p>
    * This static method sets all the in gameplay keys to not pressed.
   * */
  public static void releaseAllKeys () {
    dDown = false;
    aDown = false;
    spaceDown = false;
    upDown = false;
    rightDown = false;
    downDown = false;
    leftDown  =false;
  }
  
  /**Method public void performAction()<p>
    * This method gives functionality to the the in gameplay keys. It performs 
    * specific functions when the keys are pressed
   * */
  public void performAction (){
    if (dDown) gameCombine.game.moveHero(10);
    if (aDown) gameCombine.game.moveHero(-10);
    if (spaceDown)  gameCombine.checkMatch();
    if (upDown) {
      gameCombine.sp.selectedCapital = gameCombine.sp.optionPositions[0];
      gameCombine.sp.selection = 0;
    }
    if (rightDown){
      gameCombine.sp.selectedCapital = gameCombine.sp.optionPositions[1];
      gameCombine.sp.selection = 1;
    }
    if (downDown){
      gameCombine.sp.selectedCapital = gameCombine.sp.optionPositions[2];
      gameCombine.sp.selection = 2;
    }
    if (leftDown) {
      gameCombine.sp.selectedCapital = gameCombine.sp.optionPositions[3];
      gameCombine.sp.selection = 3;
    }   
  }
  
  /**Method public void paint (Graphics g)<p>
    * This method is responsible for painting all the different elements on the screen. It determines
    * the corrent components to paint based on the current game state.
    * 
    * @param g Graphics object used to paint on the screen.
   * */
  public void paint (Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    
    switch (gameState) {
      case PLAYING:
        gameCombine.paint(g);
        if (!gameCombine.isRunning) g.drawImage (gameCombine.pauseBg, 0 , 0, null);
        break;
      case MAIN_MENU:
        mainMenu.paint(g2d);
        break;
      case INSTRUCTIONS:
        instruction.paint(g2d);
        break;
      case HIGH_SCORES:
        highScores.paint(g2d);  
        break;
      case LEVEL_SELECT:
        levelSelection.paint (g2d);
        break;
      case LEARNING:
        learn.paint (g2d);
        break;
      case HAS_WON:
        g2d.drawImage (gameCombine.hasWon, 0, 0, null);
        g2d.setFont (new Font ("ArialBlack", Font.BOLD, 15));
        g2d.setColor (Color.WHITE);
        g2d.drawString ("Exit (Esc)", 900, 500);
        break;
    }
  }
  
  /**Method public void playGame()<p>
    * This is the main game loop. It updates all the components of the game, 
    * repaints and adds the delay to the game thread. Different components are
    * updated based on the current game state.
   * */
  public void playGame (){
    while (true) {
      switch (gameState) {
        case PLAYING:
          if (gameCombine.game.hero.tempCoList.size()==0){
          gameCombine.game.explodeAll();
          gameCombine.updateGame();
          if (gameCombine.game.animationList.size() == 0){
            highScores.updateHighScores (currentLevel, gameCombine.sp.gameTime);
          if (currentLevel!=3){
            int answer = JOptionPane.showConfirmDialog 
              (this, "Congratulations, you have destroyed major enemies from all countries in this zone! Proceed to next level?",
               "Proceed?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
              goToNextLevel();
              learn.countDown();
              gameState = GameState.LEARNING;
            }
            else {
              gameState = GameState.MAIN_MENU;
            }
          }
          else {
            gameState = GameState.HAS_WON;
          }
        }
      }
          else if (gameCombine.game.hero.lives == 0) {
            int answer = JOptionPane.showConfirmDialog 
              (this, "Your base has been destroyed by enemies! Retry level?",
               "Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
              releaseAllKeys();
              setCurrentLevel();
            }
            else {
              gameState = GameState.MAIN_MENU;
            }
          }
          else {
            if (gameCombine.isRunning) gameCombine.updateGame(); 
          }
          performAction();
          break;
        case MAIN_MENU:
          //
          break;
        case INSTRUCTIONS:
          //
          break;
        case HIGH_SCORES:
          //
          break;
        case LEVEL_SELECT:
          //
          break;
        case LEARNING:
          if (learn.time == 0) {setCurrentLevel(); gameState = GameState.PLAYING;}
      }
      repaint();
      try {
        Thread.sleep (17);
      } catch (InterruptedException e) {
      }
    }
    
  }
  
  /**Method public void goToNextLevel()<p>
    * This method progresses the game to the next level and makes the necessary resets to
    * various game variables.
   * */
  public void goToNextLevel (){
    if (currentLevel == 1) {
      currentLevel = 2;
      learn.level=2;
      learn.time = 90;
    }
    else if (currentLevel == 2) {      
      currentLevel = 3;
      learn.level = 3;
      learn.time = 120;
    }
    else {
      currentLevel = 1;
      learn.level = 1;
      learn.time = 60;
    }
  }
  
  /**Method public void setCurrentLevel()<p>
    * This method sets the current level of the game based on the currentLevel variable.
   * */
  public void setCurrentLevel () {
    if (currentLevel == 1) {
      gameCombine = new GameCombine ("resources\\files\\WesternEuropeCountries.txt",
                             "resources\\files\\WesternEuropeCapitals.txt",
                             "Western Europe Countries:", "Western Europe Capitals:", 4);
    }
    else if (currentLevel == 2) {
      gameCombine = new GameCombine ("resources\\files\\SouthAmericaCountries.txt",
                             "resources\\files\\SouthAmericaCapitals.txt",
                             "South America Countries:", "South America Capitals:", 5);
    }
    else {
      gameCombine = new GameCombine ("resources\\files\\AsiaCountries.txt",
                             "resources\\files\\AsiaCapitals.txt",
                             "Asia Countries:", "Asia Capitals:", 7);
    }
  }
  
  /**Method public static void main (String [] args)<p>
    * This is the main method. It is shows the splash screen and runs the
    * actual game. 
    * @param args String parameters 
   * */
  public static void main (String [] args) {
    JFrame jf = new JFrame("FinalTX100");
    
    FrameworkAPP fw = new FrameworkAPP ();
    
    jf.add (fw);
    
    jf.setSize(1024,576);
    jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    jf.setResizable (false);
    jf.setVisible(false);
    jf.setLocationRelativeTo(null);
    
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JFrame splash = new JFrame ();
    JPanel panel = new JPanel();
    JLabel l = new JLabel(new ImageIcon ("resources\\images\\splashGIF.gif"));
    splash.add(l);
    splash.setUndecorated(true);
    splash.setFocusable (true);
    splash.setSize (1024, 576);
    splash.setUndecorated(true);
    splash.setVisible (true);
    splash.setLocationRelativeTo(null);
    
    try { Thread.sleep (5000);} catch (Exception e){}
    splash.dispatchEvent(new WindowEvent(splash, WindowEvent.WINDOW_CLOSING));
    
    splash.setFocusable(false);
    
    jf.setVisible (true);
    fw.playGame();
    
  } 
  
}