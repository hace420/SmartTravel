package persistence;

import java.io.IOException;
import java.io.PrintWriter;
import travel.*;


public class AccommodationFileManager {
    private static final String ACCOMM_FILE = "output/data/accommodations.csv";

    public static void saveAccommodations(Accommadation[] accommadations,int count)throws IOException{
        try {
            PrintWriter pw = new PrintWriter(ACCOMM_FILE);
            
            for (int i=0;i<count;i++){
                Accommadation a = accommadations[i];
                if (a instanceof Hostel) {
                    pw.println(
                        "HOSTEL;"+a.getAccommId()+";"+a.getName()+
                        ";"+a.getLocation()+";"+a.get

                    );
                }
            }
        } catch (Exception e) {
        }
        
    }
}
