package AIDeliver.com.example.AIDeliver.common.util.tools;

import java.util.HashMap;
import java.util.Map;

public class ZipCode {


   public Map<String, Integer> calTable;
   public ZipCode(){
       calTable = new HashMap<>() {
           //key: fake sender zipcode-receiver zip code; value: distance
           {put("10000-20000", 10);}
           {put("10000-30000", 20);}
           {put("10000-40000", 30);}
           {put("10000-50000", 40);}
           {put("10000-60000", 50);}
       };
   }




//         HashMap<Coordinate, Integer> calTable = new HashMap<>() {
//             {
//                 put(new Coordinate("10000", "20000"), 10);
//             }

//             ;

//             {
//                 put(new Coordinate("10000", "30000"), 20);
//             }

//             ;

//             {
//                 put(new Coordinate("10000", "40000"), 30);
//             }

//             ;

//             {
//                 put(new Coordinate("10000", "50000"), 40);
//             }

//             ;

//             {
//                 put(new Coordinate("10000", "60000"), 50);
//             }



//         };
    }

