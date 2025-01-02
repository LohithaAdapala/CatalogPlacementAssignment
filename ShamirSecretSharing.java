import org.json.JSONObject;
import java.math.BigInteger;

import java.util.HashMap;
import java.util.Map;

public class ShamirSecretSharing {

    public static void main(String[] args) {
        
       String jsonInput = "{"
        + "\"keys\": {"
        + "\"n\": 10,"
        + "\"k\": 7"
        + "},"
        + "\"1\": {"
        + "\"base\": \"6\","
        + "\"value\": \"13444211440455345511\""
        + "},"
        + "\"2\": {"
        + "\"base\": \"15\","
        + "\"value\": \"aed7015a346d63\""
        + "},"
        + "\"3\": {"
        + "\"base\": \"15\","
        + "\"value\": \"6aeeb69631c227c\""
        + "},"
        + "\"4\": {"
        + "\"base\": \"16\","
        + "\"value\": \"e1b5e05623d881f\""
        + "},"
        + "\"5\": {"
        + "\"base\": \"8\","
        + "\"value\": \"316034514573652620673\""
        + "},"
        + "\"6\": {"
        + "\"base\": \"3\","
        + "\"value\": \"2122212201122002221120200210011020220200\""
        + "},"
        + "\"7\": {"
        + "\"base\": \"3\","
        + "\"value\": \"20120221122211000100210021102001201112121\""
        + "},"
        + "\"8\": {"
        + "\"base\": \"6\","
        + "\"value\": \"20220554335330240002224253\""
        + "},"
        + "\"9\": {"
        + "\"base\": \"12\","
        + "\"value\": \"45153788322a1255483\""
        + "},"
        + "\"10\": {"
        + "\"base\": \"7\","
        + "\"value\": \"1101613130313526312514143\""
        + "}"
        + "}";
        /*String jsonInput = "{"
        + "\"keys\": {"
        + "\"n\": 4,"
        + "\"k\": 3"
        + "},"
        + "\"1\": {"
        + "\"base\": \"10\","
        + "\"value\": \"4\""
        + "},"
        + "\"2\": {"
        + "\"base\": \"2\","
        + "\"value\": \"111\""
        + "},"
        + "\"3\": {"
        + "\"base\": \"10\","
        + "\"value\": \"12\""
        + "},"
        + "\"6\": {"
        + "\"base\": \"4\","
        + "\"value\": \"213\""
        + "}"
        + "}";*/
       
        JSONObject jsonObject = new JSONObject(jsonInput);
        
       

      
        Map<Integer, Integer> xyMap = new HashMap<>();

        
        for (String key : jsonObject.keySet()) {
            if (!key.equals("keys")) {
                JSONObject root = jsonObject.getJSONObject(key);
                int base = Integer.parseInt(root.getString("base"));
                String valueEncoded = root.getString("value");

               
                int xValue = Integer.parseInt(key);
                int yDecoded = decodeYValue(valueEncoded, base);

                
                xyMap.put(xValue, yDecoded);
            }
        }

       
        System.out.println("Decoded (x, y) pairs:");
        for (Map.Entry<Integer, Integer> entry : xyMap.entrySet()) {
            System.out.println("x = " + entry.getKey() + ", y = " + entry.getValue());
        }

        int[] x = new int[xyMap.size()];
        int[] y = new int[xyMap.size()];
        int index = 0;

       
        for (Map.Entry<Integer, Integer> entry : xyMap.entrySet()) {
            x[index] = entry.getKey();
            y[index] = entry.getValue();
            index++;
        }

        double constantTerm = lagrangeInterpolation(x, y, 0);
        System.out.println("Constant term c: " + constantTerm);
    }

   
    public static int decodeYValue(String valueEncoded, int base) {
       
        BigInteger decodedValue = new BigInteger(valueEncoded, base);
        
        return decodedValue.intValue();
    }

   
    public static double lagrangeInterpolation(int[] x, int[] y, double X) {
        double result = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            double term = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    term = term * (X - x[j]) / (x[i] - x[j]);
                }
            }
            result += term;
        }
        return result;
    }
}

