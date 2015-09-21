import java.io.*;
import java.util.ArrayList;
/**ReadFile Class <p>
  * This is a helper class that contains a genreal method that reads String data from text files
  * into arrayLists. 
  * 
  * @author Nikita Lizogubenko
  * */
public class ReadFile
{
  /**Constructor<p>
    * Default constructor
   * */
  public ReadFile()
  {
  }

  /**Method public static ArrayList getArrayItems(String fileName, String header)<p>
    * This method read a text file into a String ArrayList.
    * 
    * @param fileName path of file to be read
    * @param header file header
    * @return list read from file
   * */
  public static ArrayList<String> getArrayItems(String fileName, String header)
  {

    String temp="";
    ArrayList<String> list = new ArrayList<String>();
 
    try {
      BufferedReader input = new BufferedReader (new FileReader(fileName));
      if (input.readLine().equals(header)){
        while (true)
        {
          temp = input.readLine();
          if(temp == null)
            break;
          list.add (temp);
        }
      }
      else {System.out.println("cant read"); }
    } catch (Exception e) {
        System.out.println (e.getMessage());
      }
    return list;
  }
  
}