package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class Calculation {

    /*
     * 此方法为每一个文件都建立一个top100 url的小顶堆。这样同时有
     * 
     */
    public static void calculate(HashMap<String, Integer> map, PriorityQueue<Entry<String, Integer>> minHeap) {
        for (Entry<String, Integer> entry : map.entrySet())
         {
             if(minHeap.size()<100)
             {
                 minHeap.add(entry);
             }
             else
             {  
                 if(entry.getValue()>minHeap.peek().getValue())
                  {
                       minHeap.poll();
                       minHeap.add(entry);
                  }
             } 

         }  
       
       map.clear();//每次要清理hashmap
  }


/*
  用HashMap对小文件的url进行频率统计.
*/
  public  HashMap<String,Integer> countUrl(HashMap<String,Integer> map,String txt)
  {    
     String url = "D:/input/"+txt;

     try
      {
     
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(url)),"UTF-8"));
        
        String lineTxt = null;

        while ((lineTxt = br.readLine()) != null) {
            
            String[] names = lineTxt.split(",");
            for (String name : names) {
                if (map.keySet().contains(name)) {
                    map.put(name, (map.get(name) + 1));
                } else {
                    map.put(name, 1);
                }
            }
        }
        br.close();
        
      }
     catch(Exception e)
     {
        System.err.println("read errors :" + e);
     }
      
       return map;
  }
  
  

/*
通过对hashmap进行重载，取余50
*/

public static int hashCode(String url)
{
   return url.hashCode()%50;
}


//对堆进行归并
public static  PriorityQueue<Entry<String,Integer>> merge(PriorityQueue<Entry<String,Integer>> heap1,PriorityQueue<Entry<String,Integer>> heap2)
{
   if(heap1.peek().getValue()<heap2.peek().getValue())
   {
       heap1.poll();
       heap1.add(heap2.poll());
   }
   else
   {
       heap2.poll();
   }

   return heap1;
}

public static void main(String[] args) 
{
    
}

}