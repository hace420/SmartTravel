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
                if (a instanceof Hostel) {
                    pw.println(
                        "HOSTEL;"+a.getAccommId()+";"+a.getName()+
                        ";"+a.getLocation()+";"+a.getPricePerNight()+";"+((Hostel) a).getFees()+
                        ";"+((Hostel) a).getNumberOfBeds()

                    );
                } else if (a instanceof Hotel){
                    pw.println(
                        "HOTEL;"+a.getAccommId()+";"+a.getName()+
                        ";"+a.getLocation()+";"+a.getPricePerNight()+";"+((Hotel) a).getServiceFees()+
                        ";"+((Hotel) a).getNumberOfStars()
                    );
                }
            }
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        
    }

    public static int loadAccommodations(Accommadation[] accommadations, int count)throws IOException {
        int idCount =0;
        int maxID=0;
        File file = new File(ACCOMM_FILE);
        if (!file.exists()) return 0;
        try (Scanner sc = new Scanner(file)){
            while (sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] tokens = line.split(";");
                if(!(tokens.length == 7)) { // validating if all information is present in string
                    ErrorLogger.log("Invalid accommodation line: " + line);
                    continue;
                }
                String type = tokens[0];
                String AccommID = tokens[1];
                String name = tokens[2];
                String location = tokens[3];
                double pricePerNight = Double.parseDouble(tokens[4]);
                try {
                    Accommadation a = null;
                    switch (type) {
                    case "HOTEL":
                        double serviceFees = Double.parseDouble(tokens[5]);
                        int numberOfStars = Integer.parseInt(tokens[6]);
                        a = new Hotel(AccommID, name, location, pricePerNight,numberOfStars,serviceFees);                        
                        break;
                    case "HOSTEL":
                        double fees = Double.parseDouble(tokens[5]);
                        int numberOfBeds = Integer.parseInt(tokens[6]);
                        a = new Hostel(AccommID,name,location,pricePerNight,numberOfBeds,fees);
                        break;
                    default:
                        ErrorLogger.log("Unknown transport type: " + type);
                        continue;
                }
                // Check if array has space
                    if (count >= accommadations.length) {
                        ErrorLogger.log("Accommodation array full, cannot load more.");
                        break;
                    }
                    
                    accommadations[count] = a;
                    count++;
                } catch (InvalidAccommodationDataException ex) {
                    ErrorLogger.log("Invalid Accommodation type"+ex.getMessage());
                }
                
            }
        } 
        return count;
      

        
    }
}
