//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package persistence;

import exceptions.InvalidAccommodationDataException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import travel.*;


public class AccommodationFileManager {
    private static final String ACCOMM_FILE = "output/data/accommodations.csv";

    public static void saveAccommodations(Accommadation[] accommadations,int count)throws IOException{
        try (PrintWriter pw = new PrintWriter(ACCOMM_FILE)) {
            
            for (int i=0;i<count;i++){
                Accommadation a = accommadations[i];
                if (a == null) continue;
                if (a instanceof Hostel) {
                    pw.println(
                        "HOSTEL;"+a.getId()+";"+a.getName()+
                        ";"+a.getLocation()+";"+a.getPricePerNight()+";"+((Hostel) a).getFees()+
                        ";"+((Hostel) a).getNumberOfBeds()

                    );
                } else if (a instanceof Hotel){
                    pw.println(
                        "HOTEL;"+a.getId()+";"+a.getName()+
                        ";"+a.getLocation()+";"+a.getPricePerNight()+";"+((Hotel) a).getServiceFees()+
                        ";"+((Hotel) a).getNumberOfStars()
                    );
                }
            }
        } catch (InvalidAccommodationDataException ex) {
            ErrorLogger.log("Error invalid data saved."+ex.getMessage());
        }
        
    }

    public static int loadAccommodations(Accommadation[] accommadations, int count)throws IOException {
        int loadedCount = count;
        int maxId = 0;
        File file = new File(ACCOMM_FILE);
        if (!file.exists()) return 0;
        try (Scanner sc = new Scanner(file)){
            while (sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] tokens = line.split(";");
                if(tokens.length != 7) {
                    ErrorLogger.log("Invalid accommodation line: " + line);
                    continue;
                }
                String type = tokens[0];
                String AccommID = tokens[1];
                String name = tokens[2];
                String location = tokens[3];
                double pricePerNight;
                try {
                    pricePerNight = Double.parseDouble(tokens[4]);
                } catch (NumberFormatException ex) {
                    ErrorLogger.log("Invalid price per night stored in line: "+ex.getMessage());
                    continue;
                }
                
                try {
                    Accommadation a = null;
                    switch (type) {
                    case "HOTEL":
                        double serviceFees;
                        int numberOfStars;
                        try {
                            serviceFees = Double.parseDouble(tokens[5]);                        
                            numberOfStars = Integer.parseInt(tokens[6]);
                        } catch (NumberFormatException ex) {
                            ErrorLogger.log("Error reading data from file numbers not stored correctly."+ex.getMessage());
                            continue;
                        }
                        a = new Hotel(AccommID, name, location, pricePerNight,numberOfStars,serviceFees);                        
                        break;
                    case "HOSTEL":
                        double fees;
                        int numberOfBeds;
                        try {
                            fees = Double.parseDouble(tokens[5]);
                            numberOfBeds = Integer.parseInt(tokens[6]);
                        } catch (NumberFormatException ex) {
                            ErrorLogger.log("Error reading data from file numbers not stored correctly."+ex.getMessage());
                            continue;
                        }
                        a = new Hostel(AccommID,name,location,pricePerNight,numberOfBeds,fees);
                        break;
                    default:
                        ErrorLogger.log("Unknown Accommodation type: " + type);
                        continue;
                    }
                    if (loadedCount >= accommadations.length) {
                        ErrorLogger.log("Accommodation array full, cannot load more.");
                        break;
                    }
                    
                    accommadations[loadedCount] = a;
                    loadedCount++;
                    try {
                        int numId = Integer.parseInt(AccommID.substring(1));
                        if (numId > maxId) maxId = numId;
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ErrorLogger.log("Invalid accommodation ID format: " + AccommID);
                    }
                } catch (InvalidAccommodationDataException ex) {
                    ErrorLogger.log("Invalid Accommodation type"+ex.getMessage());
                }
            }
        }
        if (loadedCount > count) {
            
             Accommadation.updateAccommId(maxId + 1);
        }
        return loadedCount;
    }
}