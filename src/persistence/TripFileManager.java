//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package persistence;

import client.Client;
import exceptions.InvalidTripDataException;
import travel.*;

import java.io.*;
import java.util.Scanner;

public class TripFileManager {

    private static final String TRIP_FILE = "output/data/trips.csv";

    public static void saveTrips(Trip[] trips, int tripCount, Client[] clients, int clientCount,
                                 Transportation[] transports, int transportCount,
                                 Accommadation[] accommodations, int accommodationCount) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRIP_FILE))) {
            for (int i = 0; i < tripCount; i++) {
                Trip t = trips[i];
                if (t == null) continue;

                String clientId = findClientId(t.getClient(), clients, clientCount);
                String accommodationId = "null";
                 if (!(t.getAccommadation() == null)) {
                    accommodationId =  findAccommodationId(t.getAccommadation(), accommodations, accommodationCount);
                } 
                                        
                String transportId =  "null";
                if (!(t.getTransportation() == null)) {
                    transportId = findTransportId(t.getTransportation(), transports, transportCount);
                }
                                     

                pw.println(t.getTripId() + ";" + clientId + ";" + accommodationId + ";" + transportId + ";" +
                           t.getDestination() + ";" + t.getDuration() + ";" + t.getBasePrice());
            }
        }
    }

    public static int loadTrips(Trip[] trips, Client[] clients, int clientCount,
                                Transportation[] transports, int transportCount,
                                Accommadation[] accommodations, int accommodationCount) throws IOException {
        int count = 0;
        int maxId = 0;
        File file = new File(TRIP_FILE);
        if (!file.exists()) return 0;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(";");
                if (tokens.length != 7) {
                    ErrorLogger.log("Invalid trip line (wrong field count): " + line);
                    continue;
                }

                String tripId = tokens[0];
                String clientId = tokens[1];
                String accommodationId = tokens[2];
                String transportId = tokens[3];
                String destination = tokens[4];
                String durationStr = tokens[5];
                String basePriceStr = tokens[6];

                int duration;
                double basePrice;
                try {
                    duration = Integer.parseInt(durationStr);
                    basePrice = Double.parseDouble(basePriceStr);
                } catch (NumberFormatException e) {
                    ErrorLogger.log("Invalid numeric data in trip line: " + line);
                    continue;
                }

                Client client = findClientById(clientId, clients, clientCount);
                if (client == null) {
                    ErrorLogger.log("Client not found for trip: " + tripId);
                    continue;
                }

                Accommadation accommodation = null;
                if (!"null".equals(accommodationId)) {
                    accommodation = findAccommodationById(accommodationId, accommodations, accommodationCount);
                    if (accommodation == null) {
                        ErrorLogger.log("Accommodation not found for trip: " + tripId + " – proceeding without accommodation.");
                    }
                }

                Transportation transport = null;
                if (!"null".equals(transportId)) {
                    transport = findTransportById(transportId, transports, transportCount);
                    if (transport == null) {
                        ErrorLogger.log("Transport not found for trip: " + tripId + " – proceeding without transport.");
                    }
                }

                try {
                   
                    Trip t = new Trip(tripId, destination, duration, basePrice, client, transport, accommodation);

                    if (count >= trips.length) {
                        ErrorLogger.log("Trip array full, cannot load more.");
                        break;
                    }
                    trips[count++] = t;

                    // Extract numeric part for ID tracking
                    try {
                        int numId = Integer.parseInt(tripId.substring(1));
                        if (numId > maxId) maxId = numId;
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        ErrorLogger.log("Invalid trip ID format: " + tripId);
                    }

                } catch (InvalidTripDataException e) {
                    ErrorLogger.log("Invalid trip data: " + e.getMessage() + " | Line: " + line);
                }
            }
        }

        if (count > 0) {
            Trip.updateNextId(maxId + 1);   
        }
        return count;
    }

    // ----- Helper methods -----
    private static String findClientId(Client client, Client[] clients, int clientCount) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i] == client) return clients[i].getClientID();
        }
        return "null";
    }

    private static String findAccommodationId(Accommadation accommodation, Accommadation[] accommodations, int accommodationCount) {
        for (int i = 0; i < accommodationCount; i++) {
            if (accommodations[i] == accommodation) return accommodations[i].getAccommId();
        }
        return "null";
    }

    private static String findTransportId(Transportation transport, Transportation[] transports, int transportCount) {
        for (int i = 0; i < transportCount; i++) {
            if (transports[i] == transport) return transports[i].getTripId();
        }
        return "null";
    }

    private static Client findClientById(String id, Client[] clients, int clientCount) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientID().equals(id)) return clients[i];
        }
        return null;
    }

    private static Accommadation findAccommodationById(String id, Accommadation[] accommodations, int accommodationCount) {
        for (int i = 0; i < accommodationCount; i++) {
            if (accommodations[i].getAccommId().equals(id)) return accommodations[i];
        }
        return null;
    }

    private static Transportation findTransportById(String id, Transportation[] transports, int transportCount) {
        for (int i = 0; i < transportCount; i++) {
            if (transports[i].getTripId().equals(id)) return transports[i];
        }
        return null;
    }
}