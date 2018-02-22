package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        CSVReader reader = new CSVReader(new StringReader(csvString));
        //List<String[]> full = reader.readAll();
        //Iterator<String[]> iterator = full.iterator();

        JSONObject jsonObject = new JSONObject();

        // INSERT YOUR CODE HERE
        JSONArray colHeaders = new JSONArray();
        JSONArray rowHeaders = new JSONArray();
        JSONArray data = new JSONArray();

        //adding the names to the column heads
        colHeaders.add("ID"); colHeaders.add("Total"); colHeaders.add("Assignment 1"); colHeaders.add("Assignment 2"); colHeaders.add("Exam 1");

        jsonObject.put("colHeaders", colHeaders); jsonObject.put("rowHeaders", rowHeaders); jsonObject.put("data", data);

        CSVParser parser = new CSVParser();
        BufferedReader bReader = new BufferedReader(new StringReader(csvString));
        try {
            String line = bReader.readLine();
            while((line = bReader.readLine()) != null){
                    String[] parsedData = parser.parseLine(line);
                    rowHeaders.add(parsedData[0]);
                    JSONArray rows = new JSONArray();
                    rows.add(new Long(parsedData[1]));
                    rows.add(new Long(parsedData[2]));
                    rows.add(new Long(parsedData[3]));
                    rows.add(new Long(parsedData[4]));
                    data.add(rows);
            }
        }
        
        catch(IOException e) { return e.toString(); }
        return jsonObject.toString();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            
            //creating arrays to store info
            JSONArray cols = (JSONArray) jsonObject.get("colHeaders");
            JSONArray rows = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray datas = (JSONArray)jsonObject.get("data");
            
            // INSERT YOUR CODE HERE
            int j = 0;
            int counter = 1;
            
            //for loop to print the column headers
            for(int i = 0; i < cols.size(); i++){
                if(i != cols.size()-1){
                    writer.append("\""+cols.get(i)+"\",");
                    System.out.print("\""+cols.get(i)+"\",");
                }
                else{
                    writer.append("\""+cols.get(i)+"\"");
                    System.out.print("\""+cols.get(i)+"\"");
                }
            }
            writer.append("\n");
            System.out.println();
            
            /*
            for loop to print the rows with a nested while and for loop to print the data per each row
            */
            for(int i = 0; i < rows.size(); i++){
                writer.append("\""+rows.get(i)+"\",");
                System.out.print("\""+rows.get(i)+"\",");
                while(j < counter){
                    JSONArray part = (JSONArray)datas.get(j);
                    for(int k = 0; k < part.size(); k++){
                        if(k != part.size()-1){
                            writer.append("\""+part.get(k)+"\",");
                            System.out.print("\""+part.get(k)+"\",");
                        }
                        else{
                            writer.append("\""+part.get(k)+"\"");
                            System.out.print("\""+part.get(k)+"\"");
                        }
                    }
                    j++;
                }
                counter++;
                writer.append("\n");
                System.out.println();
            }
            
            results += writer.toString();
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}