// Name: Dennis Tislin
// Date: 5/19
 
import java.util.*;
import java.io.*;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Queue;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/**************** Graphs 3: EdgeList *****/
interface VertexInterface
{
   public String getName();
   public HashSet<Vertex> getAdjacencies();
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    because adjacencies is a HashSet, this method should operate in O(1)
   */
   public void addAdjacent(Vertex v);
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString(); 
 
} 
 
/*************************************************************/
class Vertex implements VertexInterface, Comparable<Vertex> //2 vertexes are equal if and only if they have the same name
{
   private final String name;
   private HashSet<Vertex> adjacencies;

  /* enter your code here  */

   public Vertex(String v){
      name = v;
      adjacencies = new HashSet<Vertex>(); 
   }

   public String getName(){
      return name;
   }
   
   public HashSet<Vertex> getAdjacencies(){
      return adjacencies;
   }

   public void addAdjacent(Vertex v){
      adjacencies.add(v);
   }

   public String toString(){
      String ret = name + " [";
      for(Object v : adjacencies){
         ret += ((Vertex)v).name + " ";
      }
      ret = ret.substring(0, ret.length() - 1);
      if(!ret.contains("[")){
         ret += "[";
      }
      ret += "]";
      return ret;
   }

   public int compareTo(Vertex other)
   {
      return name.compareTo(other.getName());
   }

   public boolean equals(Object arg){
      if(arg instanceof Vertex){
         return name.equals(((Vertex)arg).getName());
      }
      return false; 
   }

   public int hashCode(){
      return name.hashCode();
   }  
}   

/*************************************************************/
interface AdjListInterface 
{
   public Set<Vertex> getVertices();
   public Vertex getVertex(String vName);
   public Map<String, Vertex> getVertexMap();  //this is just for codepost testing
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName);
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(log n)
   */
   public void addEdge(String source, String target); 
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString(); 

}

  
/********************** Graphs 4: DFS and BFS *****/
interface DFS_BFS
{
   public String depthFirstSearch(String name);
   public String breadthFirstSearch(String name);
   /*   extra credit  */
  // public String depthFirstRecur(String name);
  // public List<Vertex> depthFirstRecurHelper(Vertex v, List<Vertex> reachable);
}

/****************** Graphs 5: Edgelist with Cities *****/
interface EdgeListWithCities
{
   public void readData(String cities, String edges) throws FileNotFoundException;
   public int edgeCount();
   public int vertexCount();
   public boolean isReachable(String source, String target);
   public boolean isStronglyConnected(); //return true if every vertex is reachable from every 
                                          //other vertex, otherwise false 
}


/*************  start the Adjacency-List graph  *********/
public class AdjList implements AdjListInterface, DFS_BFS, EdgeListWithCities
{
   //we want our map to be ordered alphabetically by vertex name
   private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
   /* constructor is not needed because of the instantiation above */
  
   /* enter your code here  */
   public Set<Vertex> getVertices(){
      Set<Vertex> verticies = new HashSet<Vertex>();
      Set<String> keys = vertexMap.keySet();
      for(String key : keys){
         verticies.add(vertexMap.get(key));
      }
      return verticies;
   }

   public Vertex getVertex(String vName){
      return vertexMap.get(vName);
   }

   public Map<String, Vertex> getVertexMap(){
      return vertexMap;
   }

   public void addVertex(String vName){
      if(vertexMap.get(vName) == null){
         vertexMap.put(vName, new Vertex(vName));
      } else {
         return;
      }
   }

   public void addEdge(String source, String target){
      // vertexMap.put(source, vertexMap.get(target));
      vertexMap.get(source).addAdjacent(vertexMap.get(target));
   }
 
   public String toString(){
      String graph = "";
      Set<String> keys = vertexMap.keySet();
      for(String key : keys){
         graph += vertexMap.get(key) + "\n";   // .getName()   key + " [" +     "]" +
      }
      return graph;
   }

   public String depthFirstSearch(String name) {
      ArrayList<String> reachables = new ArrayList<String>();
      Stack<String> edges = new Stack<String>();
      edges.push(name);
      while(!edges.isEmpty()){
      if(!reachables.contains(edges.peek())){
         String popped = edges.pop();
         String vName = "" + vertexMap.get(popped);
         if(vName.indexOf("]") != vName.indexOf("[") + 1){
            vName = vName.substring(vName.indexOf("[") + 1, vName.indexOf("]"));
            String[] arr = vName.split(" ");
            for(String str : arr){
               edges.push(str);
            }
         }
         reachables.add(popped);
      } else {
         edges.pop();
      }
      }
      String ret = "";
      for(String str : reachables){
         ret += str + " ";
      }
      return ret.substring(0, ret.length() - 1);
   }

   public String breadthFirstSearch(String name) {
      ArrayList<String> reachables = new ArrayList<String>();
      Queue<String> edges = new LinkedList<String>();
      edges.add(name);
      while(!edges.isEmpty()){
      if(!reachables.contains(edges.peek())){
         String removed = edges.remove();
         String vName = "" + vertexMap.get(removed);
         if(vName.indexOf("]") != vName.indexOf("[") + 1){
            vName = vName.substring(vName.indexOf("[") + 1, vName.indexOf("]"));
            String[] arr = vName.split(" ");
            for(String str : arr){
               edges.add(str);
            }
         }
         reachables.add(removed);
      } else {
         edges.remove();
      }
      }
      String ret = "";
      for(String str : reachables){
         ret += str + " ";
      }
      return ret.substring(0, ret.length() - 1);
   }

   
   public void readData(String cities, String edges) throws FileNotFoundException {
      Scanner cit = null;
      Scanner edg = null;
      try {
         cit = new Scanner(new File(cities));
         edg = new Scanner(new File(edges));
      } catch(FileNotFoundException e) {
         e.printStackTrace(System.out);
      }
      while(cit.hasNextLine()){
         String city = cit.nextLine();
         vertexMap.put(city, new Vertex(city));
      }
      while(edg.hasNextLine()){
         String[] arr = edg.nextLine().split(" ");
         addEdge(arr[0], arr[1]);
      }
   }
   
   public int edgeCount() {
      int count = 0;
      Set<String> keys = vertexMap.keySet();
      for(String key : keys){
         String vert = "" + vertexMap.get(key);
         if(vert.indexOf("]") != vert.indexOf("[") + 1){
            vert = vert.substring(vert.indexOf("[") + 1, vert.indexOf("]"));
            String[] arr = vert.split(" ");
            count += arr.length;
         }
      }
      return count;
   }
   
   public int vertexCount() {
      return vertexMap.keySet().size();
   }

   public boolean isReachable(String source, String target) {
      return depthFirstSearch(source).contains(target);
   }

   public boolean isStronglyConnected() {
      for(Vertex v : getVertices()){
         String dfs = depthFirstSearch(v.getName());
         for(Vertex newV : getVertices()){
            if(!dfs.contains(newV.getName())){
               return false;
            }
         }
      }
      return true;
   //    Set<String> keys = vertexMap.keySet();
   //    for(String key : keys){
   //       String vert = "" + vertexMap.get(key);
   //       if(vert.indexOf("]") != vert.indexOf("[") + 1){
   //          vert = vert.substring(vert.indexOf("[") + 1, vert.indexOf("]"));
   //          String[] arr = vert.split(" ");
   //          for(int i = 0; i < arr.length; i++){
   //             if(isReachable(key, arr[i])){
   //                return false;
   //             }
   //          }
   //       }
   //    }
   //    return true;
   }
}


