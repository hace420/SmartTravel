//------------------------------------------
// Assignment (3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package persistence;

import client.Client;
import exceptions.*;

import java.io.*;
import java.util.Scanner;

public class ClientFileManager {

    private static final String CLIENT_FILE = "output/data/clients.csv";

    public static void saveClients(Client[] clients, int clientCount) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(CLIENT_FILE));

        for (int i = 0; i < clientCount; i++) {

            Client c = clients[i];

            pw.println(c.getId() + ";" +c.getFirstName() + ";" +
                       c.getLastName() + ";" +c.getEmailAddress());
        }

        pw.close();
    }


    public static int loadClients(Client[] clients) throws IOException {

        int count = 0;
        int maxID = 0;

        File file = new File(CLIENT_FILE);

        if (!file.exists())
            return 0;

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            String[] tokens = line.split(";");

            if (tokens.length != 4) {
                ErrorLogger.log("Invalid client line: " + line);
                continue;
            }

            String id = tokens[0];
            String first = tokens[1];
            String last = tokens[2];
            String email = tokens[3];

            if (emailExists(clients, count, email)) {
                ErrorLogger.log("Duplicate email skipped: " + email);
                continue;
            }

            try {

                clients[count] = new Client(id, first, last, email);
                count++;

                int numericID = Integer.parseInt(id.substring(1));

                if (numericID > maxID)
                    maxID = numericID;

            }
            catch (InvalidClientDataException ex) {
                ErrorLogger.log("Invalid client data: " + ex.getMessage());
            }
            catch (NumberFormatException ex) {
                ErrorLogger.log("Invalid client ID format: " + id);
            }
        }

        sc.close();

        if (count > 0) {
        Client.updateNextID(maxID + 1);
}

        return count;
    }


    private static boolean emailExists(Client[] clients, int count, String email) {

        for (int i = 0; i < count; i++) {

            if (clients[i].getEmailAddress().equalsIgnoreCase(email))
                return true;
        }

        return false;
    }
}
