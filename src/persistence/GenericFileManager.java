//------------------------------------------
// Assignment 3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package persistence;
import client.*;
import travel.*;
import java.util.*;
import java.io.*;

import interfaces.*;

public class GenericFileManager <T extends CsvPersistable>  {


    // save all data 
    public static <T extends CsvPersistable> void save(List<T> items, String filepath) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for (T item: items){
                bw.write(item.toCsvRow());
                bw.newLine();
            }
        }
    }

    // load all data
    public static <T extends CsvPersistable> List<T> load(String filepath, Class<T> clazz)throws IOException{
        List<T> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))){
            String line;
            while ((line  = br.readLine())!= null){                
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    T item = parseLine(line,clazz);
                    if (item != null){
                        items.add(item);
                    }
                } catch (Exception ex) {
                    ErrorLogger.log(ex.getMessage());
                    System.out.println("Error loading line check error logs");
                }

            }


        }
        return items;
    }

    public static <T extends CsvPersistable> T parseLine(String line, Class<T> clazz){
        String className = clazz.getSimpleName();
        Object obj =null;
         try {
            switch (className) {
                case "Client":
                    obj = Client.fromCsvRow(line);
                    break;
                case "Trip":
                    obj = Trip.fromCsvRow(line);
                    break;
                case "Transportation":
                    obj = Transportation.fromCsvRow(line);
                    break;
                case "Accommadation":
                    obj = Accommadation.fromCsvRow(line);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported class: " + className);
            }
        } catch (Exception ex) {
            
            ErrorLogger.log("Error parsing " + className + " line: " + line + " - " + ex.getMessage());
            System.out.println("Invalid data for " + className + " - skipping line");
            return null;
        }
        // ccast to correct class
        return clazz.cast(obj);
        

    }


    
    
}
