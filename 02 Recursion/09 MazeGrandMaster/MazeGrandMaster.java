// Name: Dennis Tislin
// Date: 10/27

import java.util.*;
import java.io.*;

public class MazeGrandMaster {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next() + ".txt"); // append the .txt
      m.display();

      System.out.println("Options: ");
      System.out.println("1: Length of the shortest path\n\tIf no path exists, say so.");
      System.out.println(
            "2: Length of the shortest path\n\tList the shortest path\n\tDisplay the shortest path\n\tIf no path exists, say so.");
      System.out.print("Please make a selection: ");

      m.solve(sc.nextInt());
   }
}

class Maze {
   // constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';

   // instance fields
   private char[][] maze;
   private int startRow, startCol;

   /**
    * Constructor.
    * Creates a "deep copy" of the array.
    * We use this in Codepost.
    */
   public Maze(char[][] m) {
      maze = m;
      for (int r = 0; r < maze.length; r++) {
         for (int c = 0; c < maze[0].length; c++) {
            if (maze[r][c] == START) // identify start
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   }

   /**
    * Write this one-arg constructor.
    * Use a try-catch block.
    * Use next(), not nextLine()
    * Search the maze to find the location of 'S'
    */
   public Maze(String filename) {
      Scanner infile = null;
      try {
         infile = new Scanner(new File(filename));
      } catch (Exception e) {
         System.out.println("File not found");
      }

      if (infile != null) {
         int rows = infile.nextInt();
         int columns = infile.nextInt();
         infile.nextLine();
         maze = new char[rows][columns];
         for (int i = 0; i < rows; i++) {
            String row = infile.next();
            for (int j = 0; j < columns; j++) {
               maze[i][j] = row.charAt(j);
               if (maze[i][j] == START) {
                  startRow = i;
                  startCol = j;
               }
            }
         }
      }
   }

   public char[][] getMaze() {
      return maze;
   }

   public void display() {
      if (maze == null)
         return;
      for (int a = 0; a < maze.length; a++) {
         for (int b = 0; b < maze[0].length; b++) {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }

   public void solve(int n) {
      switch (n) {
         case 1:
            int shortestPath = findShortestLengthPath(startRow, startCol);
            if (shortestPath < 999)
               System.out.println("Shortest path is " + shortestPath);
            else
               System.out.println("No path exists.");
            display();
            break;

         case 2:
            String strShortestPath = findShortestPath(startRow, startCol);
            if (strShortestPath.length() != 0) {
               System.out.println("Shortest length path is: " + getPathLength(strShortestPath));
               System.out.println("Shortest path is: " + strShortestPath);
               markPath(strShortestPath);
               display(); // display solved maze
            } else
               System.out.println("No path exists.");
            break;
         default:
            System.out.println("File not found");
      }
   }

   /*
    * MazeGrandMaster 1
    * recur until you find E, then return the shortest path
    * returns 999 if it fails
    * precondition: Start can't match with Exit
    */
   public int findShortestLengthPath(int r, int c) {

      boolean pathFound = false;
      int minVal = 999;

      if (r >= maze.length || r < 0 || c >= maze[0].length || c < 0 || maze[r][c] == WALL || maze[r][c] == TEMP) {
         return 999;
      }

      if (maze[r][c] == EXIT) {
         return 0;
      }

      if (maze[r][c] == DOT || maze[r][c] == START) {

         if (maze[r][c] == DOT) {
            maze[r][c] = TEMP;
         }

         int countUp = 1;
         int countDown = 1;
         int countRight = 1;
         int countLeft = 1;

         countUp += findShortestLengthPath(r + 1, c);
         countDown += findShortestLengthPath(r - 1, c);
         countRight += findShortestLengthPath(r, c + 1);
         countLeft += findShortestLengthPath(r, c - 1);

         minVal = Math.min(Math.min(Math.min(countUp, countDown), countRight), countLeft);

         if (pathFound && maze[r][c] == TEMP) {
            maze[r][c] = PATH;
         } else if (!pathFound && maze[r][c] == TEMP) {
            maze[r][c] = DOT;
         }

      }

      return minVal;

   }

   /*
    * MazeGrandMaster 2
    * recur until you find E, then build the path with (r,c) locations
    * and the number of steps, e.g. ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
    * ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)
    * 
    * as you build, choose the shortest path at each step
    * returns empty String if there is no path
    * precondition: Start can't match with Exit
    */
   public String findShortestPath(int r, int c) {
      boolean pathFound = false;
      String path = "";
      int minVal = 999;

      if (r >= maze.length || r < 0 || c >= maze[0].length || c < 0 || maze[r][c] == WALL || maze[r][c] == TEMP) {
         return "";
      }

      if (maze[r][c] == EXIT) {
         return "((" + r + "," + c + "),0)" ;
      }

      if (maze[r][c] == DOT || maze[r][c] == START) {

         if (maze[r][c] == DOT) {
            maze[r][c] = TEMP;
         }
         
         String shortestPath = findShortestPath(r + 1, c);
         int shortestCount = getPathLength(shortestPath) + 1;

         String pathDown = findShortestPath(r - 1, c);
         int countDown = getPathLength(pathDown) + 1;
         if (countDown < shortestCount) {
            shortestPath = pathDown;
            shortestCount = countDown;
         }

         String pathRight = findShortestPath(r, c + 1);
         int countRight = getPathLength(pathRight) + 1;
         if (countRight < shortestCount) {
            shortestPath = pathRight;
            shortestCount = countRight;
         }

         String pathLeft = findShortestPath(r, c - 1);
         int countLeft = getPathLength(pathLeft) + 1;
         if (countLeft < shortestCount) {
            shortestPath = pathLeft;
            shortestCount = countLeft;
         }

         path = "((" + r + "," + c + ")," + shortestCount + ")" + "," + shortestPath;

         

         if (pathFound && maze[r][c] == TEMP) {
            maze[r][c] = PATH;
            
         } else if (!pathFound && maze[r][c] == TEMP) {
            maze[r][c] = DOT;
         }

      }

      return path;
   }

   /**
    * MazeGrandMaster 2
    * returns the length, i.e., third number when the format of the strPath is
    * "((3,4),10),((3,5),9),..."
    * returns 999 if the string is empty
    * precondition: strPath is either empty or follows the format above
    */
   public int getPathLength(String strPath) {
      if(strPath.length() > 0) {
         int start = strPath.indexOf("),");
         String truncatedString = strPath.substring(start+2);
         String numberString = truncatedString.substring(0, truncatedString.indexOf(")"));
         return Integer.parseInt(numberString);
      }
      return 999;
   }

   /**
    * MazeGrandMaster 2
    * recursive method that takes a String created by findShortestPath(r, c)
    * in the form of ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
    * ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),
    * ((4,7),0) and marks the actual path in the maze
    * precondition: the String is either an empty String or one that
    * has the format shown above
    * the (r,c) must be correct for this method to work
    */
   public void markPath(String strPath) {
      if (strPath.equals(""))
         return;
      /* enter your code below */

      String[] splitStr = strPath.split("(|,|)");
      int r = Integer.parseInt(splitStr[2]);
      int c = Integer.parseInt(splitStr[4]);
      if (maze[r][c] != START && maze[r][c] != EXIT) {
         maze[r][c] = PATH;
      }
      if (strPath.length() > 12) {
         markPath(strPath.substring(strPath.indexOf("),(") + 2));
      }
   }
}

/*************************************************************
 * ----jGRASP exec: java MazeGrandMaster_teacher
 * Enter the maze's filename (no .txt): maze1
 * WWWWWWWW
 * W....W.W
 * WW.W...W
 * W....W.W
 * W.W.WW.E
 * S.W.WW.W
 * W......W
 * WWWWWWWW
 * 
 * Options:
 * 1: Length of the shortest path
 * If no path exists, say so.
 * 2: Length of the shortest path
 * List the shortest path
 * Display the shortest path
 * If no path exists, say so.
 * Please make a selection: 1
 * Shortest path is 10
 * WWWWWWWW
 * W....W.W
 * WW.W...W
 * W....W.W
 * W.W.WW.E
 * S.W.WW.W
 * W......W
 * WWWWWWWW
 * 
 * 
 * ----jGRASP: operation complete.
 * 
 ******************************************************************/
/**************************************************************
 * ----jGRASP exec: java MazeGrandMaster_teacher
 * Enter the maze's filename (no .txt): maze1
 * WWWWWWWW
 * W....W.W
 * WW.W...W
 * W....W.W
 * W.W.WW.E
 * S.W.WW.W
 * W......W
 * WWWWWWWW
 * 
 * Options:
 * 1: Length of the shortest path
 * If no path exists, say so.
 * 2: Length of the shortest path
 * List the shortest path
 * Display the shortest path
 * If no path exists, say so.
 * Please make a selection: 2
 * Shortest length path is: 10
 * Shortest path is:
 * ((5,0),10),((5,1),9),((6,1),8),((6,2),7),((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)
 * WWWWWWWW
 * W....W.W
 * WW.W...W
 * W....W.W
 * W.W.WW*E
 * S*W.WW*W
 * W******W
 * WWWWWWWW
 * 
 * 
 * ----jGRASP: operation complete.
 * 
 ******************************************/