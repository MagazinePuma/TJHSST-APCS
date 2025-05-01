//Name: Dennis Tislin
//Date: 5/10

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeMap;

import org.w3c.dom.NameList;

import java.util.Set;

interface AdjacencyMatrix
{
   public int[][] getGrid();
   public int[][] readGrid(String fileName);
   public boolean isNeighbor(int from, int to);
   public int countEdges();
   public List<Integer> getNeighbors(int source);
   public String showAllNeighbors();
   public String toString();  //returns the grid as a String
}

interface WithNames
{
   public void readNames(String fileName);
   public Map<String, Integer> getNamesAndNumbers();
   public String toStringNamesAndNumbers();  // each line contains number-name, ex: 0-Pendleton
   public boolean isNeighbor(String from, String to);
}
  
interface Warshall
{    
   public int countReachables();
   public boolean isReachable(String from, String to);  
   public List<String> getReachables(String from);
   public String toStringReachability(); //displays the reachability matrix with 2 spaces in front of each value
   public void allPairsReachability();   // Warshall's Algorithm. fills the reachability matrix                                  
}

interface Floyd
{
   public int getCost(int from, int to);
   public int getCost(String from, String to);
   public void allPairsWeighted();  //Floyd's Algorithm
}

/***********************  the graph  ******************/
public class AdjMat implements AdjacencyMatrix, WithNames, Warshall, Floyd
{
   private int[][] grid = null;
   private int[][] reachability = null;   //adjacency matrix representation
   private Map<String, Integer> namesAndNumbers = null;    // maps name to number
   private ArrayList<String> nameList = null;  //reverses the map, index-->name
   //private int[][] reachability = null; //reachability matrix for Warshall, cost matrix for Floyd
 
 /*  write constructors, accessor methods, and instance methods   */

   public AdjMat(String fn){
      readGrid(fn);
   }

   public int[][] readGrid(String fn){
      Scanner scan = null;
      int totalRows = -1;
      int totalCols = -1;
      try {
         scan = new Scanner(new File(fn));
         totalRows = scan.nextInt();
         if(totalRows > 0) {
            String[] lineArray = null;
            if(scan.hasNextLine()){
               // Skip first line with number of rows
               scan.nextLine();
               String line = scan.nextLine();
               lineArray = line.split(" ");
               totalCols = lineArray.length;
            }
            if(totalCols > 0) {
               grid = new int[totalRows][totalCols];
               if(lineArray != null) {
                  for(int j=0; j<lineArray.length; j++) {
                     grid[0][j] = Integer.parseInt(lineArray[j]);
                  }
               }
               for(int i=1; i<totalRows; i++) {
                  String line = scan.nextLine();
                  String[] lineArr = line.split(" ");
                  if(lineArr != null) {
                     for(int j=0; j<lineArr.length; j++) {
                        grid[i][j] = Integer.parseInt(lineArr[j]);
                     }
                  }
               }
            }
         }
      } catch(FileNotFoundException e) {
         e.printStackTrace(System.out);
      } catch(NumberFormatException nfe) {
         nfe.printStackTrace(System.out);
      }
      return grid;
   }

   public int[][] getGrid(){
      return grid;
   }

   public int countEdges(){
      int count = 0;
      for(int[] row : grid){
         for(int col : row){
            if(col == 1){
               count++;
            }
         }
      }
      return count;
   }

   public boolean isNeighbor(int from, int to){
      return grid[from][to] == 1 ? true : false;  
   }

   @Override
   public List<Integer> getNeighbors(int source) {
      ArrayList<Integer> neighbors = new ArrayList<Integer>();
      int[] arr = grid[source];
      for(int i = 0; i < arr.length; i++){
         if(arr[i] == 1){
            neighbors.add(i);
         }
      }
      return neighbors;
   }
   
   @Override
   public String showAllNeighbors() {
      String neighbors = "";
      for(int row = 0; row < grid.length; row++){
         neighbors += row + ": [";
         for(int col = 0; col < grid[row].length; col++){
            if(grid[row][col] == 1){
               neighbors += col + ", ";
            }
         }
         neighbors = neighbors.substring(0, neighbors.length() - 2);
         neighbors += "]\n";
      }
     //neighbors += "]";
      return neighbors;
   }

   public String toString(){
      String gridStr = "";
      for(int[] row : grid){
         for(int col : row){
            gridStr += col + " ";
         }
         gridStr += "\n";
      }
      return gridStr;
   }
   
   
   /**************  implement the WithNames interface ************/
   
   // public AdjMat(String mapFn, String listFn){
   //    readGrid(mapFn);
   //    readNames(listFn);
   // }

   public void readNames(String fn){
      Scanner file = null;
      try {
         file = new Scanner(new File(fn));
      } catch(FileNotFoundException e) {
         e.printStackTrace(System.out);
      }
      int lines = file.nextInt();
      file.nextLine();
      for(int i = 0; i < lines; i++){
         if(nameList == null) {
            nameList = new ArrayList<String>();
         }
         nameList.add(file.nextLine());
      } 
   }

   @Override
   public Map<String, Integer> getNamesAndNumbers() {
      namesAndNumbers = new TreeMap<String, Integer>();
      for(int i = 0; i < nameList.size(); i++){
         if(nameList == null) {
            nameList = new ArrayList<String>();
         }
         namesAndNumbers.put(nameList.get(i), i);
      }
      return namesAndNumbers;
   }

   @Override
   public boolean isNeighbor(String from, String to) {
      //return (namesAndNumbers==null || grid == null)?false:grid[namesAndNumbers.get(from)][namesAndNumbers.get(to)] == 1? true : false;
      if(grid != null){
         return grid[nameList.indexOf(from)][nameList.indexOf(to)] == 1 ? true : false;
      } else {
         return false;
      }
   }

   @Override
   public String toStringNamesAndNumbers() {
      String ret = "";
      Set<String> keys = namesAndNumbers.keySet();
      for(String key : keys){
         ret += namesAndNumbers.get(key) + "-" + key;
         ret += "\n";
      }
      return ret;
   }
   
      
   /************  implement the Warshall interface ************/

   public AdjMat(String fileMatrix, String fileNames){
      grid = readGrid(fileMatrix);
      reachability = readGrid(fileMatrix);
      namesAndNumbers = new TreeMap<String, Integer>();
      nameList = new ArrayList<String>();
      readNames(fileNames);
   }

   public void reachabilityGrid(){
      if(grid != null){
         for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[row].length; col++){
               if(grid[row][col] == 1){
                  int[] newRow = grid[col];
                  for(int newCol = 0; newCol < newRow.length; newCol++){
                     if(newRow[newCol] == 1){
                        reachability[row][newCol] = 1;
                     }
                  }
               }
            }
         }
      }
   }

   public int countReachables(){
      int count = 0;
      for(int[] row : reachability){
         for(int col : row){
            if(col == 1){
               count++;
            }
         }
      }
      return count;
   }

   public boolean isReachable(String from, String to){
      if(getReachables(from).contains(to)){
         return true;
      } else {
         return false;
      }
   }
   
   public List<String> getReachables(String from){
      ArrayList<String> reachables = new ArrayList<String>();
      int[] fromArr = reachability[nameList.indexOf(from)];  
      for(int i = 0; i < fromArr.length; i++){
         if(fromArr[i] == 1){
            reachables.add(nameList.get(i));
         }
      }
      // for(int id : fromArr){
      //    if(id == 1){
      //       reachables.add(nameList.get(id));
      //    }
      // }
      return reachables;
   }

   public String toStringReachability(){
      String mat = "";
      for(int[] row : reachability){
         for(int col : row){
            mat += col + " ";
         }
         mat += "\n";
      }
      return mat;
   }

   public void allPairsReachability(){
      reachabilityGrid();
      for(int i = 0; i < reachability.length; i++){
         System.out.print(nameList.get(i) + "--> ");
         List<String> reachables = getReachables(nameList.get(i));
         System.out.print(reachables);
         System.out.print("\n");
      }
      // for(int r = 0; r < reachability.length; r++){
      //    System.out.print(nameList.get(r) + "--> [");
      //    for(int c = 0; c < reachability[r].length;c++){
      //       if(reachability[r][c] == 1){
      //          System.out.print(nameList.get(c) + ", ");
      //       }
      //    }
      //    System.out.print("]");
      //    System.out.print("\n");
      // }
   }
        
   /*************  implement the Floyd interface  *********/
   public void allPairsWeighted(){
      if(grid != null){
         for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[row].length; col++){
               int[] newRow = grid[col];
               for(int newCol = 0; newCol < newRow.length; newCol++){
                  reachability[col][newCol] = Math.min(reachability[col][newCol], grid[col][row] + grid[row][newCol]);  //newRow[newCol]
               }
            }
         }
      }
      // if(grid != null){
      //    for(int row = 0; row < grid.length; row++){
      //       for(int col = 0; col < grid[row].length; col++){
      //          if(row == col){
      //             reachability[row][col] = 0;
      //          } else if(grid[row][col] != 9999){
      //             int[] newRow = grid[col];
      //             for(int newCol = 0; newCol < newRow.length; newCol++){
      //                if(newRow[newCol] != 9999){
      //                   reachability[col][newCol] = Math.min(reachability[col][newCol], grid[col][row] + grid[row][newCol]);  //newRow[newCol]
      //                }
      //             }
      //          }
      //       }
      //    }
      // }
   }

   public int getCost(int from, int to){
      if(from == to){
         return 0;
      }
      int cost = reachability[from][to];
      int[] row = reachability[from];
      for(int c = 0; c < row.length; c++){
         if(row[c] != 9999 && row[c] < cost){
            int[] newRow = reachability[c];
            if(newRow[to] != 9999 && newRow[to] < cost){
               if(row[c] + newRow[to] < cost){
                  cost = row[c] + newRow[to]; 
               }
            }
         }
      }
      return cost;
   }

   public int getCost(String from, String to){
      if(from.equals(to)){
         return 0;
      }
      int cost = reachability[nameList.indexOf(from)][nameList.indexOf(to)];
      int[] row = reachability[nameList.indexOf(from)];
      for(int c = 0; c < row.length; c++){
         if(row[c] != 9999 && row[c] < cost){
            int[] newRow = reachability[c];
            if(newRow[nameList.indexOf(to)] != 9999 && newRow[nameList.indexOf(to)] < cost){
               if(row[c] + newRow[nameList.indexOf(to)] < cost){
                  cost = row[c] + newRow[nameList.indexOf(to)]; 
               }
            }
         }
      }
      return cost;
   }
}