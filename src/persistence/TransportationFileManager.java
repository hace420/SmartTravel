//------------------------------------------
// Assignment (2)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
package persistence;

import exceptions.*;
import java.io.*;
import java.util.Scanner;
import travel.*;

public class TransportationFileManager {
    private static final String TRANSPORT_FILE = "output/data/transportations.csv";

    public static void saveTransportations(Transportation[] transports, int transportCount) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSPORT_FILE))) {
            for (int i = 0; i < transportCount; i++) {
                Transportation t = transports[i];
                if (t == null) continue;

                if (t instanceof Bus) {
                    Bus b = (Bus) t;
                    pw.println("BUS;" + b.getTripId() + ";" + b.getCompanyName() + ";" +
                               b.getDepartureCity() + ";" + b.getArrivalCity() + ";" +
                               b.getBusCompany() + ";" + b.getNumberOfStops() + ";" + b.getBusCost());
                } else if (t instanceof Flight) {
                    Flight f = (Flight) t;
                    pw.println("FLIGHT;" + f.getTripId() + ";" + f.getCompanyName() + ";" +
                               f.getDepartureCity() + ";" + f.getArrivalCity() + ";" +
                               f.getAirlineName() + ";" + f.getLuggageAllowance() + ";" +
                               f.getTicketCost() + ";" + f.getLuggageCost());
                } else if (t instanceof Train) {
                    Train tr = (Train) t;
                    pw.println("TRAIN;" + tr.getTripId() + ";" + tr.getCompanyName() + ";" +
                               tr.getDepartureCity() + ";" + tr.getArrivalCity() + ";" +
                               tr.getTrainType().name() + ";" + tr.getSeatClass().name() + ";" +
                               tr.getTrainCost());
                }
            }
        }
    }

    public static int loadTransportations(Transportation[] transports) throws IOException {
        int count = 0;
        int maxId = 0;
        File file = new File(TRANSPORT_FILE);
        if (!file.exists()) return 0;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(";");
                if (tokens.length < 5) {
                    ErrorLogger.log("Invalid transport line (too few fields): " + line);
                    continue;
                }

                String type = tokens[0];
                String id = tokens[1];
                String company = tokens[2];
                String depart = tokens[3];
                String arrive = tokens[4];

                try {
                    Transportation t = null;
                    switch (type) {
                        case "BUS":
                            if (tokens.length != 8) {
                                ErrorLogger.log("Invalid Bus line (expected 8 fields): " + line);
                                continue;
                            }
                            String busCompany = tokens[5];
                            int stops;
                            double busCost;
                            try {
                                stops = Integer.parseInt(tokens[6]);
                                busCost = Double.parseDouble(tokens[7]);
                            } catch (NumberFormatException e) {
                                ErrorLogger.log("Invalid numeric data for BUS: " + line);
                                continue;
                            }
                            t = new Bus(id, company, depart, arrive, busCompany, stops, busCost);
                            break;

                        case "FLIGHT":
                            if (tokens.length != 9) {
                                ErrorLogger.log("Invalid Flight line (expected 9 fields): " + line);
                                continue;
                            }
                            String airline = tokens[5];
                            double luggageAllow, ticket, luggageCost;
                            try {
                                luggageAllow = Double.parseDouble(tokens[6]);
                                ticket = Double.parseDouble(tokens[7]);
                                luggageCost = Double.parseDouble(tokens[8]);
                            } catch (NumberFormatException e) {
                                ErrorLogger.log("Invalid numeric data for FLIGHT: " + line);
                                continue;
                            }
                            t = new Flight(id, company, depart, arrive, airline, luggageAllow, ticket, luggageCost);
                            break;

                        case "TRAIN":
                            if (tokens.length != 8) {
                                ErrorLogger.log("Invalid Train line (expected 8 fields): " + line);
                                continue;
                            }
                            TrainType trainType;
                            SeatClass seatClass;
                            double trainCost;
                            try {
                                trainType = TrainType.valueOf(tokens[5]);
                                seatClass = SeatClass.valueOf(tokens[6]);
                                trainCost = Double.parseDouble(tokens[7]);
                            } catch (IllegalArgumentException e) {
                                ErrorLogger.log("Invalid enum or numeric data for TRAIN: " + line);
                                continue;
                            }
                            t = new Train(id, company, depart, arrive, trainType, seatClass, trainCost);
                            break;

                        default:
                            ErrorLogger.log("Unknown transport type: " + type);
                            continue;
                    }

                    if (count >= transports.length) {
                        ErrorLogger.log("Transportation array full, cannot load more.");
                        break;
                    }
                    transports[count++] = t;

                    
                    
                        int numId = Integer.parseInt(id.substring(2));
                        if (numId > maxId) maxId = numId;
                    
                } catch (InvalidTransportDataException ex) {
                    ErrorLogger.log("Invalid transport data: " + ex.getMessage() + " | Line: " + line);
                }
            }
        }

        if (count > 0) {
            Transportation.updateTripId(maxId + 1);
        }
        return count;
    }
}