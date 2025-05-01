// Name: Dennis Tislin
// Date: 4/13

public class HeapSort
{	
   public static int N;   //9 or 100

   public static void main(String[] args)
   {
      /* Phase 2 by itself: Given a heap, sort it. Do this part first. */
      N = 9;  
      double heap[] = {-1,99,80,85,17,30,84,2,16,1};  // size of array = N+1
      
      display(heap);
      sort(heap);
      display(heap);
      System.out.println(isSorted(heap));
      
      /* Phases 1 and 2:  Generate 100 random numbers, make a heap, sort it.  */
      // N = 100; //4;
      // double[] heap = new double[N + 1];  // size of array = N+1
      // heap = createRandom(heap);
      // // double[] heap = {-1.0, 7.2, 3.4, 6.4, 9.9};  //a special case because 9.9 is already at the end
      // display(heap);
      // makeHeap(heap, N);
      // display(heap); 
      // sort(heap);
      // display(heap);
      // System.out.println( isSorted(heap) );
   }
   
	//******* methods in Phase 2 by itself ******************************************
   public static void display(double[] array)
   {
      for(int k = 1; k < array.length; k++)
         System.out.print(array[k] + "    ");
      System.out.println("\n");	
   }
   
   public static void sort(double[] array)
   {
      /* enter your code here */
      for(int i = array.length - 1; i > 1; i--){
         swap(array, i, 1);
         heapDown(array, 1, i - 1);
      }
      
   
      if(array[1] > array[2])   //just an extra swap, if needed.
         swap(array, 1, 2);
   }
  
   public static void swap(double[] array, int a, int b)
   {
      double bSwap = array[b];
      array[b] = array[a];
      array[a] = bSwap;
   }
   
   //it's a max-heap. Parents are larger than each child.
   public static void heapDown(double[] array, int k, int lastIndex)
   {
      int maxChildInd = k;
      if(k * 2 + 1 <= lastIndex && array[k * 2 + 1] > array[maxChildInd]){
         maxChildInd = k * 2 + 1;
      }
      if(k * 2 <= lastIndex && array[k * 2] > array[maxChildInd]){
         maxChildInd = k * 2;
      }
      if(k != maxChildInd){
         swap(array, k, maxChildInd);
         heapDown(array, maxChildInd, lastIndex);
      }
      // if(k * 2 + 1 <= lastIndex){
      //    if(array[k * 2] >= array[k] && array[k * 2] > array[k * 2 + 1]){
      //       swap(array, k, k * 2);
      //       //heapDown(array, k, lastIndex);
      //    } else {
      //       swap(array, k, k * 2 + 1);
      //       //heapDown(array, k, lastIndex);
      //    }
      // } else if(k * 2 <= lastIndex) {
      //    if(array[k] <= array[k * 2]){
      //       swap(array, k, k * 2);
      //       //heapDown(array, k, lastIndex);
      //    }
      // } else {
      //    return;
      // }
      // heapDown(array, k, lastIndex);
   }
   
   public static boolean isSorted(double[] arr)
   {
      if(arr.length > 1){
         for(int i = 1; i < arr.length; i++){
            if(arr[i] < arr[i-1]) {
               return false;
            }
         }
         return true;
         // for(int i = 1; i < arr.length; i++){
         //    if(i * 2 + 1 < arr.length){
         //       if(arr[i] < arr[i * 2] || arr[i] < arr[i * 2 + 1]){
         //          return false;
         //       }
         //    } else if(i * 2 < arr.length){
         //       if(arr[1] < arr[i * 2]){
         //          return false;
         //       }
         //    }
         // }
         // return true;
      } else if(arr.length == 1) {
         return true;
      } else {
         return false;
      }
   }
   
   //****** methods in Phase 1 *******************************************

  	//Generate 100 random numbers (between 1 and 100, formatted to 2 decimal places)
   //Post-condition:  array[0] == -1, the rest of the array is random
   public static double[] createRandom(double[] array)
   {  
      array[0] = -1;   //because heaps don't use index 0
      for(int i = 1; i < N; i++){
         array[i] = (int)(Math.random() * 9900 + 100) / 100.0;
      }
      return array;
   }
   
   //turn the random array into a heap
   //Post-condition:  array[0] == -1, the rest of the array is in heap-order
   public static void makeHeap(double[] array, int lastIndex)
   {
      for(int i = lastIndex; i > lastIndex / 2; i--){
         heapDown(array, i, lastIndex);
      }
      for(int i = lastIndex / 2; i > 0; i--){
         heapDown(array, i, lastIndex);
      }
   }
}

