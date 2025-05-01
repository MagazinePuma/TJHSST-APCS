// Name: Dennis Tisin
// Date: 6/1
 
import java.util.*;
import java.io.*;
import java.util.TreeSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.ArrayList;

/* Resource classes and interfaces for 
 *              Graphs6: Dijkstra
 *              Graphs7: Dijkstra with Cities
 */

class Neighbor implements Comparable<Neighbor>
{
   //2 Neighbors are equal if and only if they have the same name
   //implement all methods needed for a HashSet and TreeSet to work with Neighbor objects
   private final wVertex target;
   private final double edgeDistance;
   
   public Neighbor(wVertex t, double d) {
      target = t;
      edgeDistance = d;
   }
   
   //add all methods needed for a HashSet and TreeSet to function with Neighbor objects
   //use only target, not distances, since a vertex can't have 2 neighbors that have the same target
   //.........
   // public wVertex getVertex(){
   //    return target;
   // }

   public wVertex getTarget(){
      return target;
   }
   
   public double getEdgeDistance(){
      return edgeDistance;
   }

   public int hashCode(){
      return target.hashCode();
   }

   public boolean equals(Object arg){
      if(arg instanceof Neighbor){
         return target.getName().equals(((Neighbor)arg).getTarget().getName());
      }
      return false;
   }

   public int compareTo(Neighbor other){
      return target.getName().compareTo(other.getTarget().getName());
   }
   
   public String toString()
   {
      return target.getName() + " " + edgeDistance;  
   }
}

 /**************************************************************/
class PQelement implements Comparable<PQelement> { 
//used just for a PQ, contains a wVertex and a distance, also previous that is used for Dijksra 7
//compareTo is using the distanceToVertex to order them such that the PriorityQueue works
//will be used by the priority queue to order by distance
 
   private wVertex vertex;
   private Double distanceToVertex; 
   private wVertex previous; //for Dijkstra 7
      
   public PQelement(wVertex v, double d) {
      vertex = v;
      distanceToVertex = d;
   }
   
   //getter and setter methods provided
   public wVertex getVertex() {
      return this.vertex;
   }
   
   public Double getDistanceToVertex() {
      return this.distanceToVertex;
   }
   
   public void setVertex(wVertex v) {
      this.vertex = v;
   }
   
   public void setDistanceToVertex(Double d) {
      this.distanceToVertex = d;
   }   
   
   public int compareTo(PQelement other) {
      //we assume no overflow will happen since distances will not go over the range of int
      return (int)(distanceToVertex - other.distanceToVertex);
   }
   
   public wVertex getPrevious()  //Dijkstra 7
   {
      return this.previous;
   }
   public void setPrevious(wVertex v) //Dijkstra 7
   {
      this.previous = v;
   } 
   
   //implement toString to match the sample output   
   public String toString()
   { 
      // String toReturn = "";
      // //your code here...
      // toReturn += getVertex() + " " + getDistanceToVertex();
      // return toReturn;
      if(distanceToVertex != Double.POSITIVE_INFINITY){
         return vertex.getName() + " " + distanceToVertex;
      } else {
         return "";
      }
   }
}

/********************* wVertexInterface ************************/
interface wVertexInterface 
{
   public String getName();
   
   public Set<Neighbor> getNeighbors();
   
   /*  adds to the neighbors set  
       called at the beginning of the lab*/
   public void addAdjacent(wVertex v, double d); 
     
    /*returns an arraylist of PQelements that store distanceToVertex to another wVertex  */
   public ArrayList<PQelement> getAlDistanceToVertex();
   
   //returns the PQelement that has the vertex equal to v
   public PQelement getPQelement(wVertex v);
   
   /*
   postcondition: returns null if wVertex v is not in the alDistanceToVertex
                  or the distance associated with that wVertex in case there is a PQelement that has v as wVertex
   */
   public Double getDistanceToVertex(wVertex v);
   
   /*
   precondition:  v is not null
   postcondition: - if the alDistanceToVertex has a PQelement that has the wVertex component equal to v
                  it updates the distanceToVertex component to d
                  - if the alDistanceToVertex has no PQelement that has the wVertex component equal to v,
                  then a new PQelement is added to the alDistanceToVertex using v and d   
   */
   public void setDistanceToVertex(wVertex v, double d); 
 
   public String toString();  
 
}

class wVertex implements Comparable<wVertex>, wVertexInterface 
{ 
   public static final double NODISTANCE = Double.POSITIVE_INFINITY; //constant to be used in class
   private final String name;
   private Set<Neighbor> neighbors;  
   private ArrayList<PQelement> alDistanceToVertex; //should have no duplicates, enforced by the getter/setter methods

   /* constructor, accessors, modifiers  */ 
   public wVertex(String n){
      name = n;
      neighbors = new TreeSet<Neighbor>();
      alDistanceToVertex = new ArrayList<PQelement>();
   }
   
   public String getName() {
      return name;
   }

   public Set<Neighbor> getNeighbors() {
      return neighbors;
   }

   public void setDistanceToVertex(wVertex v, double d) {
      boolean hasVertex = false;
      for(int i = 0; i < alDistanceToVertex.size(); i++){
         if(v.equals(alDistanceToVertex.get(i).getVertex())){
            alDistanceToVertex.get(i).setDistanceToVertex(d);
            hasVertex = true;
         }
      }
      if(!hasVertex){
         alDistanceToVertex.add(new PQelement(v, d));
      }
   }

   public void setAlDistanceToVertex(ArrayList<PQelement> alDistanceToVertex) {
      this.alDistanceToVertex = alDistanceToVertex;
   }

   public void addAdjacent(wVertex v, double d) {
      neighbors.add(new Neighbor(v, d));
   }

   public ArrayList<PQelement> getAlDistanceToVertex() {
      return alDistanceToVertex;
   }

   public Double getDistanceToVertex(wVertex v) {
      Double distance = 0.0;
      for(int i = 0; i < alDistanceToVertex.size(); i++){
         if(v.equals(alDistanceToVertex.get(i).getVertex())){
            distance =  alDistanceToVertex.get(i).getDistanceToVertex();
         }
      }
      return distance;
   }

   public PQelement getPQelement(wVertex v) {
      for(int i = 0; i < alDistanceToVertex.size(); i++){
         if(v.equals(alDistanceToVertex.get(i).getVertex())){
            return alDistanceToVertex.get(i);
         }
      }
      return null;
   }
   
   /* 2 vertexes are equal if and only if they have the same name
      add all methods needed for a HashSet and TreeSet to function with Neighbor objects
      use only target, not distances, since a vertex can't have 2 neighbors that have the same target   
   */
   
   public int hashCode(){
      return name.hashCode();
   }

   public boolean equals(Object arg){
      if(arg instanceof wVertex){
         return name.equals(((wVertex)arg).getName());
      }
      return false;
   }

   public int compareTo(wVertex other){
      return name.compareTo(other.getName());
   }
   
   public String toString()
   { 
      String toReturn = name;
      toReturn += " "+ neighbors;
      toReturn += " List: " + alDistanceToVertex; 
      return toReturn;
   }
}

/*********************   Interface for Graphs 6:  Dijkstra ****************/
interface AdjListWeightedInterface 
{
   public Set<wVertex> getVertices();  
   public Map<String, wVertex> getVertexMap();  //this is just for codepost testing
   public wVertex getVertex(String vName);
   /* 
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(logn)
   */
   public void addVertex(String vName);
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
                     addEdge should work in O(1)
   */   
   public void addEdge(String source, String target, double d);
   public void minimumWeightPath(String vertexName); // Dijstra's algorithm
   public String toString();  
}  

 /***********************  Interface for Graphs 7:  Dijkstra with Cities   */
interface AdjListWeightedInterfaceWithCities 
{       
   public List<String> getShortestPathTo(wVertex vSource, wVertex target);
   public void readData(String vertexNames, String edgeListData) ;
}
 
/****************************************************************/ 
/**************** this is the graph  ****************************/
public class AdjListWeighted implements AdjListWeightedInterface//,AdjListWeightedInterfaceWithCities
{
   //we want our map to be ordered alphabetically by vertex name
   private Map<String, wVertex> vertexMap = new TreeMap<String, wVertex>();
   
   /* default constructor -- not needed!  */
  
   /* similar to AdjList, but handles distances (weights) and wVertex*/ 
   
   public Set<wVertex> getVertices() {
      Set<wVertex> vertices = new TreeSet<wVertex>();
      for(String key : vertexMap.keySet()){
         vertices.add(vertexMap.get(key));
      }
      return vertices;
   }

   public Map<String, wVertex> getVertexMap() {
      return vertexMap;
   }

   public wVertex getVertex(String vName) {
      return vertexMap.get(vName);
   }

   public void addVertex(String vName) {
      if(!vertexMap.keySet().contains(vName)){
         vertexMap.put(vName, new wVertex(vName));
      }
   }

   public void addEdge(String source, String target, double d) {
      addVertex(source);
      addVertex(target);
      vertexMap.get(source).addAdjacent(new wVertex(target), d);         //new wVertex(target)
   }

   public void minimumWeightPath(String vertexName) {
      int nIndex = 0;
      PriorityQueue<PQelement> elements = new PriorityQueue<PQelement>();
      ArrayList<PQelement> neighbors = new ArrayList<PQelement>();
      // wVertex vertex = vertexMap.get(vertexName);
      // if(vertex != null){
         // PQelement source = new PQelement(vertex, 0.0);
      if(vertexMap.get(vertexName) != null){
         for(String key : vertexMap.keySet()){
            if(key.equals(vertexName)){
               // neighbors.add(source);
               // vertexMap.get(key).setDistanceToVertex(vertexMap.get(key), 0.0);
               neighbors.add(new PQelement(vertexMap.get(vertexName), 0.0));
               //elements.add(new PQelement(vertexMap.get(vertexName), 0.0));
            } else {
               // neighbors.add(new PQelement(vertexMap.get(key), Double.POSITIVE_INFINITY));
               // vertexMap.get(key).setDistanceToVertex(vertexMap.get(key), Double.POSITIVE_INFINITY);
               neighbors.add(new PQelement(vertexMap.get(key), Double.POSITIVE_INFINITY));
               //elements.add(new PQelement(vertexMap.get(vertexName), Double.POSITIVE_INFINITY));
            }
         }
         // elements.add(source);
         elements.add(new PQelement(vertexMap.get(vertexName), 0.0));
         while(!elements.isEmpty()){
            PQelement prev = elements.remove();
            Set<Neighbor> neigh = prev.getVertex().getNeighbors();
            for(Neighbor n : neigh){
               for(int i = 0; i < neighbors.size(); i++){
                  if(n.getTarget().equals(neighbors.get(i).getVertex())){
                     nIndex = i;
                  }
               }
               if(n != null){
                  double totalD = prev.getDistanceToVertex() + n.getEdgeDistance();
                  if(totalD < neighbors.get(nIndex).getDistanceToVertex()){
                     neighbors.get(nIndex).setDistanceToVertex(totalD);
                  }
                  elements.add(neighbors.get(nIndex));
                  // wVertex nElement = n.getTarget();
                  // if(prev.getVertex().getDistanceToVertex(prev.getVertex()) + n.getEdgeDistance() < vertexMap.get(vertexName).getDistanceToVertex(nElement)){
                  //    vertexMap.get(vertexName).setDistanceToVertex(nElement, prev.getDistanceToVertex() + n.getEdgeDistance());
                  //    neighbors.set(neighbors.indexOf(nElement.getPQelement(nElement)), new PQelement(n.getTarget(), prev.getDistanceToVertex() + n.getEdgeDistance()));
                  //    if(elements.contains(nElement.getPQelement(nElement))){
                  //       elements.remove(nElement.getPQelement(nElement));
                  //    }
                  //    elements.add(new PQelement(nElement, prev.getDistanceToVertex() + n.getEdgeDistance()));
                  // }
               }
            }
         }
      }
      getVertex(vertexName).setAlDistanceToVertex(neighbors);
      // }
   }
   
   public String toString()
   {
      String strResult = "";
      for(String vName: vertexMap.keySet())
      {
         strResult += vertexMap.get(vName) + "\n"; 
      }
      return strResult;
   }
   
   /*  Graphs 7 has two more methods */
   public List<String> getShortestPathTo(wVertex target) 
   {
      return null;
   }  
     
   public void readData(String vertexNames, String edgeListData) 
   {
      
   }
}