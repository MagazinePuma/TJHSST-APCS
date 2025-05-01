 // Name: Dennis Tislin    
 // Date: 3/12

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Iterator;

interface PolynomialInterface
{
   public void makeTerm(Integer exp, Integer coef);
   public Map<Integer, Integer> getMap();
   public double evaluateAt(double x);
   
   //precondition: both polynomials are in standard form
   //postcondition: terms with zero disappear. If all terms disappear (the size is zero), 
   //               add pair (0,0).
   public Polynomial add(Polynomial other);
   
   //precondition: both polynomials are in standard form
   //postcondition: terms with zero disappear. If all terms disappear (the size is zero), 
   //               add pair (0,0)
   public Polynomial multiply(Polynomial other);
   public String toString();
}

class Polynomial implements PolynomialInterface
{
   private Map<Integer, Integer> map;

   public Polynomial(){
      map = new TreeMap<Integer, Integer>();
   }

   public void makeTerm(Integer exp, Integer coef){
      map.put(exp, coef);
   }

   public Map<Integer, Integer> getMap(){
      return map;
   }

   public Polynomial add(Polynomial other){
      int coAdd = 0;
      Polynomial add = new Polynomial();
      Set<Integer> keys = map.keySet();
      Set<Integer> otherKeys = other.getMap().keySet();
      for(int exp : keys){
         for(int otherExp : otherKeys){
            if(exp == otherExp){
               coAdd += map.get(exp) + other.getMap().get(otherExp);
            }
         }
         add.makeTerm(exp, coAdd);
      }
      for(int otherExp : otherKeys){
         if(!add.getMap().containsKey(otherExp)){
            add.getMap().put(otherExp, other.getMap().get(otherExp));
         }
      }
      return add; 
   }

   public double evaluateAt(double x){
      double result = 0;
      Set<Integer> expKeys = map.keySet();
      for(int exp : expKeys){
         result += map.get(exp) * Math.pow(x, exp);
      }
      return result;
   }

   public Polynomial multiply(Polynomial other){
      Polynomial product = new Polynomial();
      Set<Integer> keys = map.keySet();
      Set<Integer> otherKeys = other.getMap().keySet();
      for(int exp : keys){
         for(int otherExp : otherKeys){
            int exponent = exp + otherExp;
            int coef = map.get(exp) * other.getMap().get(otherExp);
            if(product.getMap().containsKey(exponent)){
               coef += product.getMap().get(exponent);
            }
            product.getMap().put(exponent, coef); 
         }
      }
      return product;
   }


   public String toString(){
      String ret = "";
      NavigableSet<Integer> exp = ((TreeMap)map).descendingKeySet(); 
      for(int key : exp){
         String powerX = "";
         if(key != 0) {
            powerX = "x" + (key==1?"":"^" + key);
         }

         String coeff = "" + map.get(key);;
         if(map.get(key) == 1) {
            coeff = key==0?"1":"";
         } else if(map.get(key) == -1) {
            coeff = key==0?"-1":"-";
         }

         String plusStr = " + ";
         if(ret.length() < 1) {
            plusStr = "";
         }
         ret += plusStr + (coeff.equals("0")?"":coeff + powerX);
      }
      return ret;
   }

   // public String toString2(){
   //    String ret = "";
   //    NavigableSet<Integer> exp = ((TreeMap)map).descendingKeySet(); 
   //    if(map.size() != 0){
   //       for(int key : exp){
   //          if(key == 0){
   //             ret += map.get(key);// + " + ";
   //          } else if(key == 1){
   //             if(map.get(key) == 1 || map.get(key) == -1){
   //                if(map.get(key) == 1){
   //                   ret += map.get(key) + "x + ";
   //                } else {
   //                   ret += map.get(key) + " -x + ";
   //                }
   //             } else {
   //                ret += map.get(key) + "x + ";
   //             }
   //          } else {
   //             if(map.get(key) != 1 || map.get(key) != -1){
   //                ret += map.get(key) + "x^" + key + " + ";
   //             } else if(map.get(key) == 1) {
   //                ret += "x^" + key + " + ";
   //             } else {
   //                ret += "-x^" + key + " + ";
   //             }
   //          }
   //       }
   //    } else {
   //       return "0";
   //    }
   //    if(ret.contains("0")){
   //       if(ret.indexOf("0") == 0){
   //          if(ret.indexOf("x") == 1){
   //             ret = ret.substring(3);
   //          } else {
   //             ret = ret.substring(2);
   //          }
   //       } else {
   //          if(ret.indexOf("x") - ret.indexOf(0) == 1){
   //             ret = ret.substring(0, ret.indexOf("0")) + ret.substring(ret.indexOf("x") + 1);
   //          } else {
   //             ret = ret.substring(0, ret.indexOf("0")) + ret.substring(ret.indexOf(0) + 1);
   //          }
   //       }
   //    }
   //    return ret.substring(0, ret.length() - 1);
   //    // Iterator<Integer> it = exp.iterator();
   //    // while(it.hasNext()){
   //    //    ret += map.get(it.next()) + "x^" + it;
   //    //    if(it.hasNext()){
   //    //       ret += "+";
   //    //    }
   //    // }
   //    // for(int i = 0; i < ret.length(); i++){
   //    //    if(ret.charAt(i) == "^" && ret.charAt(i + 1) == "0"){
   //    //       ret = ret.substring(0, i - 1) + ret.substring(i + 2);
   //    //    } else if(ret.charAt(i) == "0" && ret.charAt(i + 1) == "x"){
   //    //       ret = ret.substring(0, i) + ret.substring(i + 4);
   //    //    }
   //    // }
   //    // return ret;
   // }
}