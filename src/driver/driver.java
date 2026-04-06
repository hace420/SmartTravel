//------------------------------------------
// Assignment (3)
// Question: ()
// Written by: (Christian Buckley 40329967)
//------------------------------------------
// Driver forSmart Travel program includs menu drien interface and testing scenario with hard coded values
package driver;
import client.*;
import exceptions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import persistence.*;
import travel.*;
import visualization.DashboardGenerator;
import service.*;

public class driver {
    private static SmartTravelService service;
    
    public static void main(String[] args) {

        service = new SmartTravelService();
        Scanner in = new Scanner(System.in);
        menuDriven(in);

        
    
    }
//----------------------------------
// MENU DRIVEN 
//----------------------------------

    public static void menuDriven(Scanner in){
        int choice=-1;
        do {
            System.out.println("\n===== SMART TRAVEL SYSTEM =====");
            System.out.print("\nPLEASE NOTE BEFORE ANY DATA IS CREATED USE MENU OPTION 7 TO LOAD DATA, ANY DATA THAT IS CRETAED BEFORE PREVIOUS DATA IS LOADED WILL BE LOST\n");
            System.out.print("\n1. Client Management");
            System.out.print("\n2. Trip Management");
            System.out.print("\n3. Transportation Management");
            System.out.print("\n4. Accommodation Management");
            System.out.print("\n5. Additional Operations");
            System.out.print("\n6. Advanced Analytics");
            System.out.print("\n7. List All Data Summary ");
            System.out.print("\n8. Load All Data ");
            System.out.print("\n9. Save All Data ");
            System.out.print("\n10. Run Predefined Scenario ");
            System.out.print("\n11. Generate Dashboard ← HTML + charts ");
            System.out.print("\n0. Exit");
            
            boolean validInput = false;
            while (!validInput){
                try {
                    System.out.println("\nChoice: ");
                    choice = in.nextInt();
                    in.nextLine();
                    validInput = true;
                } catch (InputMismatchException ex) {
                    System.out.println("Error: Enter valid input (0-10)");
                    in.nextLine(); 
                }
            }
         
            

            switch (choice) {
                case 1: clientMenu(in); break;
                case 2: tripMenu(in); break;
                case 3: transportationMenu(in); break;
                case 4: accommodationMenu(in); break;
                case 5: additionalMenu(in); break;
                case 6: advancedMenu(in);break;
                case 7: listTrips();break;
                case 8: service.loadAllData();break;
                case 9: service.saveAllData();break;
                case 10: predefinedScenerio(in);break;
                case 11: generateDashboard();break;
            }

        } while (choice != 0);

        
    }
    //----------------------------------
    //CLIENT MENU
    //----------------------------------

    public static void clientMenu(Scanner in){
    int choiceClient = 0;
    do {
        System.out.println("\n===== CLIENT MANAGEMENT =====");
        System.out.println("1. Add Client");
        System.out.println("2. Edit Client");
        System.out.println("3. Delete Client");
        System.out.println("4. List Clients");
        System.out.println("5. Quit");
        System.out.print("Choice: ");
        choiceClient = in.nextInt();
        in.nextLine(); 

        switch (choiceClient) {
            case 1: // add client
                System.out.print("Enter first name: ");
                String first = in.nextLine();
                System.out.print("Enter last name: ");
                String last = in.nextLine();
                System.out.print("Enter email: ");
                String email = in.nextLine();

                try {
                    service.addClient(first, last, email);
                    System.out.println("Client added successfully!");
                } catch (InvalidClientDataException ex) {
                    System.out.println("Error: "+ex.getMessage());
                } catch (DuplicateEmailException ex){
                    System.out.println("Error: "+ex.getMessage());
                }
                
                
                
                break;

            case 2: // edit client
                listClients();
                System.out.println("Select client index you would like to edit: ");
                int choice = 0;
                choice = in.nextInt();
                in.nextLine();
                // checking if choice is valid
                if (choice < 0 || choice >= service.getClientCount()){
                    System.out.println("Invalid index");
                    break;
                }
                Client c = service.getClients().get(choice); 
                            
                System.out.println("Would you like to edit 1. First name 2. Last name 3. Email address");
                int choice2 = 0;
                choice2 = in.nextInt();
                in.nextLine();

                switch (choice2) {
                    case 1 :
                        String f;
                        System.out.println("Enter first name: ");
                        f = in.nextLine();
                        try {
                            c.setFirstName(f);
                        } catch (InvalidClientDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                            ErrorLogger.log("Error: "+ex.getMessage());
                        }                      
                        break;

                    case 2:
                         String l;
                        System.out.println("Enter last name: ");
                        l = in.nextLine();
                        try {
                            c.setLastName(l);
                        } catch (InvalidClientDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                            ErrorLogger.log("Error: "+ex.getMessage());
                        }
                        break;

                    case 3:
                         String e;
                        System.out.println("Enter email address: ");
                        e = in.nextLine();
                        try {
                            if (service.duplicateEmailCheck(e)) {
                            throw new DuplicateEmailException("Email "+e+" is already registered to a client");
                            }
                            c.setEmailAddress(e);
                        } catch (InvalidClientDataException  | DuplicateEmailException ex) {
                            System.out.println("Error: "+ex.getMessage());
                            ErrorLogger.log("Error: "+ex.getMessage());
                        } 
                       
                        break;
                    default:
                        System.out.println("Invalid option!");;
                }
                break;

            case 3: // remove client
                listClients();
                System.out.println("Select client index you would like to delete: ");
                int choice3 = 0;
                choice3 = in.nextInt();
                in.nextLine();

                // checking if choice is valid
                if (choice3 < 0 || choice3 >= service.getClientCount()){
                    System.out.println("Invalid index");
                    break;
                }
                Client client = service.getClients().get(choice3); 
                String cId = client.getId();
                try {
                    service.deleteClient(cId); 
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: "+ex.getMessage());
                                    
                }
                break;

            case 4: // list clients
                listClients();
                break;

            case 5: // exit menu
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

    } while (choiceClient != 5);
    } 
  

    //----------------------------------
    // TRIP MENU
    //----------------------------------
    public static void tripMenu(Scanner in){
        int tripChoice =0;
        do { 
            System.out.println("===== TRIP MANAGEMENT =====");
            System.out.println("1. Create trip");
            System.out.println("2.Edit trip");
            System.out.println("3.Remove a trip");
            System.out.println("4.List all trips");
            System.out.println("5.List all trips for a specific client");
            System.out.println("6.Exit");
            tripChoice = 0;
            tripChoice = in.nextInt();
            in.nextLine();

            switch (tripChoice) {
                case 1:
                    // --- Trip destination, duration, price ---
                    System.out.print("Enter trip destination: ");
                    String destination = in.nextLine();
                    System.out.print("Enter trip duration (days): ");
                    int duration = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter base price per night: ");
                    double basePrice = in.nextDouble();
                    in.nextLine();

                    // --- Select client ---
                    listClients();  
                    System.out.print("Select client index: ");
                    int clientIndex = in.nextInt();
                    in.nextLine();

                    // Validate client index
                    if (clientIndex < 0 || clientIndex >= service.getClientCount()) {
                        System.out.println("Invalid client index.");
                        break;  // exit case 1
                    }
                    Client selectedClient = service.getClients().get(clientIndex);
                    String clientId = selectedClient.getId();

                    // --- Select Transport (optional) ---
                    Transportation transport = null;
                    boolean transportAdded = false; // used to tell user is trip was created without accommadation
                    String transportId = null;  // will be set if transport added
                    System.out.print("Do you want to add transportation? (y/n): ");
                    String addTransport = in.nextLine();
                    if (addTransport.equalsIgnoreCase("y")) {
                        System.out.println("Select transportation type: 1. Flight 2. Train 3. Bus");
                        int transportType = in.nextInt();
                        in.nextLine();

                        System.out.print("Enter company name: ");
                        String company = in.nextLine();
                        System.out.print("Enter departure city: ");
                        String depart = in.nextLine();
                        System.out.print("Enter arrival city: ");
                        String arrive = in.nextLine();

                        switch (transportType) {
                            case 1: // Flight
                                System.out.print("Enter airline name: ");
                                String airline = in.nextLine();
                                System.out.print("Enter luggage allowance: ");
                                double luggage = in.nextDouble();
                                System.out.print("Enter ticket cost: ");
                                double ticketCost = in.nextDouble();
                                System.out.print("Enter luggage cost: ");
                                double luggageCost = in.nextDouble();
                                in.nextLine();
                                try {
                                    transport = new Flight(company, depart, arrive, airline, luggage, ticketCost, luggageCost);
                                    service.addTransportation(transport);
                                    transportId = transport.getId();
                                    transportAdded = true;
                                } catch (InvalidTransportDataException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                    ErrorLogger.log("Error: " + ex.getMessage()); 
                                }
                                break;

                            case 2: // Train
                                System.out.println("Enter train type (1. High-speed 2. Long-Distance 3. Economy)");
                                int type = in.nextInt();
                                TrainType trainType = TrainType.ECONOMY;
                                switch (type) {
                                    case 1: trainType = TrainType.HIGH_SPEED; break;
                                    case 2: trainType = TrainType.LONG_DISTANCE; break;
                                    case 3: trainType = TrainType.ECONOMY; break;
                                    default: System.out.println("Invalid option, defaulting to Economy.");
                                }
                                System.out.println("Enter seat class (1. First class 2. Business 3. Economy)");
                                int seat = in.nextInt();
                                SeatClass seatclass = SeatClass.ECONOMY;
                                switch (seat) {
                                    case 1: seatclass = SeatClass.FIRST_CLASS; break;
                                    case 2: seatclass = SeatClass.BUSINESS; break;
                                    case 3: seatclass = SeatClass.ECONOMY; break;
                                    default: System.out.println("Invalid option, defaulting to Economy.");
                                }
                                System.out.print("Enter train cost: ");
                                double cost = in.nextDouble();
                                in.nextLine();
                                transport = new Train(company, depart, arrive, trainType, seatclass, cost);
                                service.addTransportation(transport);
                                transportAdded = true;
                                transportId = transport.getId();
                                break;

                            case 3: // Bus
                                System.out.print("Enter bus company name: ");
                                String busName = in.nextLine();
                                System.out.print("Enter number of stops: ");
                                int stops = in.nextInt();
                                in.nextLine();
                                double busCost = 20 + stops;
                                
                                try {
                                    transport = new Bus(company, depart, arrive, busName, stops, busCost);
                                    service.addTransportation(transport);
                                    transportId = transport.getId();
                                    transportAdded = true;
                                } catch (InvalidTransportDataException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                    ErrorLogger.log("Error: " + ex.getMessage()); 
                                }
                                break;

                            default:
                                System.out.println("Invalid transport type.");
                                break;
                        }
                    }

                    // --- Select Accommodation (optional) ---
                    Accommadation accommodation = null;
                    boolean accommAdded = false; // used to tell user is trip was created without accommadation
                    String accommodationId = null;  // will be set if accommodation added
                    System.out.print("Do you want to add accommodation? (y/n): ");
                    String answer = in.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.print("Enter company name: ");
                        String name = in.nextLine();
                        System.out.print("Enter price per night: ");
                        double price = in.nextDouble();
                        in.nextLine();
                        System.out.print("Enter location: ");
                        String location = in.nextLine();

                        System.out.println("Choose accommodation type: 1. Hotel  2. Hostel");
                        int accChoice = in.nextInt();
                        in.nextLine();
                       

                        switch (accChoice) {
                            case 1: // Hotel
                                System.out.print("Enter number of stars (1-5): ");
                                int stars = in.nextInt();
                                in.nextLine();
                                System.out.print("Enter service fees (one-time): ");
                                double fees = in.nextDouble();
                                in.nextLine();
                                try {
                                    accommodation = new Hotel(name, location, price, stars, fees);
                                    service.addAccommodation(accommodation);
                                    accommodationId = accommodation.getId();
                                    accommAdded = true;
                                } catch (InvalidAccommodationDataException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                    ErrorLogger.log("Error: " + ex.getMessage()); 
                                }
                                break;

                            case 2: // Hostel
                                System.out.print("Enter number of beds: ");
                                int beds = in.nextInt();
                                in.nextLine();
                                System.out.print("Enter additional fees: ");
                                double fee = in.nextDouble();
                                in.nextLine();
                                try {
                                    accommodation = new Hostel(name, location, price, beds, fee);
                                    service.addAccommodation(accommodation);
                                    accommodationId = accommodation.getId();
                                    accommAdded = true;
                                } catch (InvalidAccommodationDataException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                    ErrorLogger.log("Error: " + ex.getMessage()); 
                                }
                                break;

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    }

                    // --- Create the trip using the service ---
                    try {
                        service.createTrip(destination, duration, basePrice, clientId, transportId, accommodationId);
                        System.out.println("Trip successfully created!");
                        if (!accommAdded && answer.equalsIgnoreCase("y")){
                            System.out.println("Note: Accommodation could not be added due to the error above.");
                        }
                        if (!transportAdded && addTransport.equalsIgnoreCase("y")){
                            System.out.println("Note: Transportation could not be added due to the error above.");
                        }
                    } catch (InvalidTripDataException | EntityNotFoundException ex) {
                        System.out.println("Error creating trip: " + ex.getMessage());
                        
                    }
                    break;
            case 2:
                // edit trip 
                listTrips();

                    System.out.print("Select trip index to edit: ");
                    int editIndex = in.nextInt();
                    in.nextLine();
                    // Validate index
                    if (editIndex < 0 || editIndex >= service.getTripCount()) {
                        System.out.println("Invalid trip index.");
                        break;
                    }                

                    

                    System.out.println("Edit options:");
                    System.out.println("1. Destination");
                    System.out.println("2. Duration");
                    System.out.println("3. Base Price");
                    System.out.println("4. Change Client");
                    System.out.println("5. Cancel");

                    int editChoice = in.nextInt();
                    in.nextLine();
                    Trip tripToEdit = service.getTrips().get(editIndex);
                    boolean updated = false;

                    switch (editChoice) {
                        

                        case 1:
                            System.out.print("Enter new destination: ");
                            String newDest = in.nextLine();
                            tripToEdit.setDestination(newDest);
                            updated = true;
                            break;

                        case 2:
                            System.out.print("Enter new duration: ");
                            int newDuration = in.nextInt();
                            in.nextLine();
                            try {
                                tripToEdit.setDuration(newDuration);
                                updated = true;
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                                ErrorLogger.log("Error: " + ex.getMessage()); 
                            }
                            
                            break;

                        case 3:
                            System.out.print("Enter new base price: ");
                            double newPrice = in.nextDouble();
                            in.nextLine();
                            try {
                                tripToEdit.setBasePrice(newPrice);
                                updated = true;
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: "+ex.getMessage());
                                ErrorLogger.log("Error: " + ex.getMessage()); 
                            }
                            
                            break;

                        case 4:
                            listClients();
                            System.out.print("Select new client index: ");
                            int newClientIndex = in.nextInt();
                            in.nextLine();
                             // Validate index
                                if (newClientIndex < 0 || newClientIndex >= service.getClientCount()) {
                                    System.out.println("Invalid client index.");
                                    break;
                                }  
                            Client client = service.getClients().get(newClientIndex);
                            try {
                                tripToEdit.setClient(client);
                                updated = true;
                                System.out.println("Client updated successfully!");
                            } catch (InvalidTripDataException ex) {
                                System.out.println("Error: " + ex.getMessage());
                                ErrorLogger.log("Error: " + ex.getMessage()); 
                                break;
                            }

                           
                            break;

                        case 5: // exit sub menu
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                    if (updated){
                    System.out.println("Trip updated successfully.");
                    }
                   
                    break;
            case 3: // delete trip 
                listTrips();
                System.out.println("Select trip you would like to delete by entering the trips index number");
                int index = in.nextInt();
                in.nextLine();
                if (index < 0 || index >= service.getTripCount()){
                    System.out.println("Invalid index");
                }
                Trip trip = service.getTrips().get(index);
                String tripIdToRemove = trip.getId();
                try {
                    service.deleteTrip(tripIdToRemove);
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error:"+ex.getMessage());
                }
                System.out.println("Trip Deleted successfully");
                break;

            case 4: // list trips 
                listTrips();break;
            case 5:   
            listClients();
            System.out.println("Enter index of client to search for");
            int searchIndex = in.nextInt();
            in.nextLine();
            if (searchIndex < 0 || searchIndex >= service.getClientCount()){
                    System.out.println("Invalid index");
                    break;
                }
            boolean found = false;
            Client selectedClient1 = service.getClients().get(searchIndex);
            

            System.out.println("Trips found for client "+ selectedClient1);
            for (Trip t : service.getTrips()){
                
                if (t.getClient().equals(selectedClient1)) {
                    try {
                        System.out.println("Destonation: " + t.getDestination()+
                                        "\nDuration: " + t.getDuration()+
                                        "\nPrice: "+ t.calculateTotalCost(t.getDuration()));
                                        found = true;
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Error: "+ex.getMessage());
                        ErrorLogger.log("Error: " + ex.getMessage()); 
                    }
                    
                }
            }
            if (!found){
                System.out.println("No trips found for that client");
            }
            break;
            default:
                    System.out.println("Invalid entry!");;

            
            }
            
        } while (tripChoice !=6);

   
    }
    

    //----------------------------------
    // TRANSPORTATION MENU
    //----------------------------------
    public static void transportationMenu(Scanner in){
        System.out.println("1. Add a transportation option");
        System.out.println("2. Remove a transportation option");
        System.out.println("3. List transportations by type");
        int menuChoice = in.nextInt();
        in.nextLine();
        switch (menuChoice) {
            case 1:
                // --- adding trips ---
                    System.out.println("Select trip you would like to add transportation to:");
                    listTrips();
                    int tripIndex = in.nextInt();
                    in.nextLine();
                    if (tripIndex < 0 || tripIndex >= service.getTripCount()){
                    System.out.println("Invalid index");
                    break;
                    }


                    Trip selectedTrip = service.getTrips().get(tripIndex);
                    Transportation transport = null;

                    // --- choosing tranport type --- 
                    System.out.println("Select transportation type: 1. Flight 2. Train 3. Bus");
                    int transportType = in.nextInt();
                    in.nextLine();

                    System.out.print("Enter company name: ");
                    String company = in.nextLine();
                    System.out.print("Enter departure city: ");
                    String depart = in.nextLine();
                    System.out.print("Enter arrival city: ");
                    String arrive = in.nextLine();

                switch (transportType) {
                    case 1:
                        // flight - attributes
                        System.out.print("Enter airline name: ");
                        String airline = in.nextLine();
                        System.out.print("Enter luggage allowance: ");
                        double luggage = in.nextDouble();
                        System.out.print("Enter ticket cost: ");
                        double ticketCost = in.nextDouble();
                        System.out.print("Enter luggage cost: ");
                        double luggageCost = in.nextDouble();
                        in.nextLine();
                        try {
                            transport = new Flight(company, depart, arrive, airline, luggage, ticketCost, luggageCost);
                            service.addTransportation(transport);
                        } catch (InvalidTransportDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                            ErrorLogger.log("Error: " + ex.getMessage()); 
                        }
                        
                        break;

                    case 2:
                        // train - attributes
                        System.out.println("Enter train type (1. High-speed 2. Long-Distance 3. Economy)");
                        int type = in.nextInt();
                        TrainType trainType = TrainType.ECONOMY;
                        switch (type) {
                            case 1: trainType = TrainType.HIGH_SPEED; break;
                            case 2: trainType = TrainType.LONG_DISTANCE; break;
                            case 3: trainType = TrainType.ECONOMY; break;
                        }
                        System.out.println("Enter seat class (1. First class 2. Business 3. Economy)");
                        int seat = in.nextInt();
                        SeatClass seatclass = SeatClass.ECONOMY;
                        switch (seat) {
                            case 1: seatclass = SeatClass.FIRST_CLASS; break;
                            case 2: seatclass = SeatClass.BUSINESS; break;
                            case 3: seatclass = SeatClass.ECONOMY; break;
                        }
                        System.out.println("Enter train cost:");
                        double cost = in.nextDouble();
                        in.nextLine();
                        
                        transport = new Train(company, depart, arrive, trainType, seatclass, cost);
                        service.addTransportation(transport);
                        
                        break;

                    case 3:
                        // bus - attributes
                        System.out.print("Enter bus company name: ");
                        String busName = in.nextLine();
                        System.out.print("Enter number of stops: ");
                        int stops = in.nextInt();
                        System.out.print("Enter bus cost (base 20$ + 1$ per stop): ");
                        double busCost = in.nextDouble();
                        in.nextLine();
                        
                        try {
                            transport = new Bus(company, depart, arrive, busName, stops, busCost);
                            service.addTransportation(transport);
                        } catch (InvalidTransportDataException ex) {
                            System.out.println("Error: "+ex.getMessage());
                            ErrorLogger.log("Error: " + ex.getMessage()); 
                        }
                        
                        break;

                        default:
                        System.out.println("Invalid transportation type!");
                        break;
                    }

                    

                    // adding transport to trip
                    if (transport != null) {
                    selectedTrip.setTransportation(transport);
                    System.out.println("Transportation added to trip successfully!");
                    } else {
                        System.out.println("Transportation could not be created.");
                    }
                   
                    break;
            case 2:
                // remove a transport option
                System.out.println("Select index of trip you would like to remove transportation from");
                listTrips();
                int tripRemoveIndex = in.nextInt();
                in.nextLine();
                if (tripRemoveIndex < 0 || tripRemoveIndex >= service.getTripCount()){
                    System.out.println("Invalid index");
                    break;
                    }
                Trip tripToRemoveTransport = service.getTrips().get(tripRemoveIndex);

                if (tripToRemoveTransport.getTransportation() == null) {
                    System.out.println("This trip has no transportation assigned.");break;                    
                }

                Transportation toRemove = tripToRemoveTransport.getTransportation();
                String id = toRemove.getId();

                
                // removing tranportation option from specified trip
                tripToRemoveTransport.setTransportation(null);
                
                try {
                    service.removeTransportation(id);
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }

                System.out.println("Transportation removed from trip successfully!");
                break;
            case 3:
                // Listing options
                if (service.getTransportations().size() == 0) {
                System.out.println("No transportation options available.");
                break;
                }

                System.out.println("Select type to list:");
                System.out.println("1. Flight");
                System.out.println("2. Train");
                System.out.println("3. Bus");

                int typeChoice = in.nextInt();
                in.nextLine();
                if (typeChoice < 1 || typeChoice > 3) {
                    System.out.println("Invalid selection.");
                    break;
                }
                boolean found = false;
                int transportCount = service.getTransportations().size();

                for (int i = 0; i < transportCount; i++){
                    Transportation temp = service.getTransportations().get(i);
                    switch (typeChoice) {
                        case 1 :
                            // search for flight
                            if (temp instanceof Flight){
                                System.out.println(temp);
                                found = true;
                            }
                             break;
                        case 2:
                            // search for Train
                            if (temp instanceof Train){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                        case 3:
                            // search for bus
                            if (temp instanceof Bus){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                            
                        
                        default:
                            System.out.println("Invalid selection.");break;
                    }
                }
                if (!found){
                    System.out.println("No transportation of selected type found.");
                }
                break;
                
                
            default:
                System.out.println("Invalid selection.");break;
        }
       

    }    
    
    //----------------------------------
    // ACCOMMODATION MENU
    //----------------------------------
    public static void accommodationMenu(Scanner in){
    System.out.println("1. Add a acommodation option");
    System.out.println("2. Remove a accommodation option");
    System.out.println("3. List accommodations by type");
    int menuChoice = in.nextInt();
    in.nextLine();
    switch (menuChoice) {
        case 1:
            // adding accommodation
            
                if (service.getTripCount() == 0) {
                    System.out.println("No trips available to add accommodation.");
                    break; // or return if you want to exit the menu entirely
                }

                System.out.println("Select trip you would like to add accommodation to:");
                listTrips();
                int tripIndex = in.nextInt();
                in.nextLine();

                if (tripIndex < 0 || tripIndex >= service.getTripCount()) {
                    System.out.println("Invalid index.");
                    break;
                }

                Trip selectedTrip = service.getTrips().get(tripIndex);
                Accommadation acc = null;

                System.out.print("Enter company name: ");
                String name = in.nextLine();
                System.out.print("Enter price per night: ");
                double price = in.nextDouble();
                in.nextLine();
                System.out.print("Enter location: ");
                String location = in.nextLine();

                System.out.println("Choose accommodation type:");
                System.out.println("1. Hotel");
                System.out.println("2. Hostel");
                int accChoice = in.nextInt();
                in.nextLine();

                switch (accChoice) {
                    case 1: // Hotel
                        System.out.print("Enter number of stars (1-5): ");
                        int stars = in.nextInt();
                        in.nextLine();
                        System.out.print("Enter service fees (one-time): ");
                        double fees = in.nextDouble();
                        in.nextLine();
                        try {
                            acc = new Hotel(name, location, price, stars, fees);
                            service.addAccommodation(acc);
                        } catch (InvalidAccommodationDataException ex) {
                            System.out.println("Error: " + ex.getMessage());
                            ErrorLogger.log("Error: " + ex.getMessage()); 
                        }
                        break;

                    case 2: // Hostel
                        System.out.print("Enter number of beds: ");
                        int beds = in.nextInt();
                        in.nextLine();
                        System.out.print("Enter additional fees: ");
                        double fee = in.nextDouble();
                        in.nextLine();
                        try {
                            acc = new Hostel(name, location, price, beds, fee);
                            service.addAccommodation(acc);
                        } catch (InvalidAccommodationDataException ex) {
                            System.out.println("Error: " + ex.getMessage());
                            ErrorLogger.log("Error: " + ex.getMessage()); 
                        }
                        break;

                    default:
                        System.out.println("Invalid option.");
                        break;
                }

                if (acc != null) {
                    selectedTrip.setAccommadation(acc);
                    System.out.println("Accommodation added to trip successfully!");
                } else {
                    System.out.println("Accommodation could not be created.");
                }
                break;
        case 2:
            // remove accommadation
            if (service.getAccommodations().size() ==0){
                System.out.println("There are no accommadation available to remove");break;
            }
            System.out.println("Enter index of trip you would like the accommdation to be removed from");
            listTrips();
            int accRemoveIndex = in.nextInt();
            in.nextLine();
            if (accRemoveIndex < 0 || accRemoveIndex >= service.getTripCount()) {
                    System.out.println("Invalid index.");
                    break;
                }

            Trip tripRemoveAcc = service.getTrips().get(accRemoveIndex);
            if (tripRemoveAcc.getAccommadation() == null){
                System.out.println("There is no accommadation assigned to this trip");break;
            }
            Accommadation accTemp = tripRemoveAcc.getAccommadation();
            String accommId = accTemp.getId();
            
                // removing tranportation option from specified trip
                try {
                    service.removeAccommodation(accommId);
                    
                    tripRemoveAcc.setAccommadation(null); // remove from trip
                    System.out.println("Accommodation removed from trip successfully!");
                } catch (EntityNotFoundException ex) {
                    System.out.println("Error: Accommodation could not be removed from system.");
                }
                break;
        case 3:
             // Listing options
             if (service.getAccommodations().size() == 0) {
                System.out.println("No accommadation options available.");
                break;
                }

                System.out.println("Select type to list:");
                System.out.println("1. Hotel");
                System.out.println("2. Hostel");
               

                int typeChoice = in.nextInt();
                in.nextLine();
                boolean found = false;
                if (typeChoice != 1 && typeChoice != 2) {
                System.out.println("Invalid selection.");
                break;
                }
                int accommadationCount = service.getAccommodations().size();


                for (int i =0; i < accommadationCount ;i++){
                    Accommadation temp = service.getAccommodations().get(i);
                    switch (typeChoice) {
                        case 1:
                            // searching for hotels
                            if (temp instanceof Hotel){
                                System.out.println(temp);
                                found = true;
                            }                            
                            break;
                        case 2: 
                            // searching for Hostels
                            if (temp instanceof Hostel){
                                System.out.println(temp);
                                found = true;
                            }
                            break;
                        default:
                            System.out.println("Invalid selection.");break;
                    }
                }
                if (!found){
                    System.out.println("No accommadation of selected type found.");
                }
                break;
        default:
           System.out.println("Invalid selection.");break;
    }

    }   
    //----------------------------------
    // ADDITIONAL MENU
    //----------------------------------
    public static void additionalMenu(Scanner in){
    int choice = 0;
    do {
        System.out.println("===== ADDITIONAL OPERATIONS =====");
        System.out.println("1. Display the most expensive trip");
        System.out.println("2. Calculate and display total cost of a trip");
        System.out.println("3. Create a deep copy of the transportation array");
        System.out.println("4. Create a deep copy of the accommodation array");
        System.out.println("5. Return to main menu");
        System.out.print("Choice: ");
        choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
                // Show most expensive trip
                if (service.getTripCount() == 0) {
                    System.out.println("No trips available.");
                    break;
                }

                
                Trip mostExpensive = null;
                double highestCost = 0;

                for (Trip trips : service.getTrips()) {
                    try {
                        double cost = trips.calculateTotalCost(trips.getDuration());
                        if (cost > highestCost) {
                            highestCost = cost;
                            mostExpensive = trips;
                        }
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Warning: Could not calculate cost for trip " + ex.getMessage());
                        ErrorLogger.log("Error: " + ex.getMessage()); 
                    }
                }

                if (mostExpensive == null) {
                    System.out.println("No trips could be evaluated (all calculations failed).");
                } else {
                    System.out.println("Most expensive trip:\n" + mostExpensive);
                    System.out.println("With a cost of: " + highestCost);
                }
                break;
            case 2:
                // calculate cost of selected trip
                if(service.getTripCount() == 0){
                    System.out.println("No trips available.");
                    break;
                }
                System.out.println("Enter index of trip you would like to calculate cost for");
                listTrips();
                int indexChoice = in.nextInt();
                in.nextLine();
                if (indexChoice < 0 || indexChoice >= service.getTripCount()){
                    System.out.println("Invalid choice");break;
                }
                Trip temp = service.getTrips().get(indexChoice);
                try {
                    System.out.println(temp.calculateTotalCost(temp.getDuration()));
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error: "+ex.getMessage());
                    ErrorLogger.log("Error: " + ex.getMessage()); 
                }
                
                break;
            case 3:
               // Deep copy of transportation list
                List<Transportation> copyTransportations = new ArrayList<>();

                for (Transportation original : service.getTransportations()) {
                    if (original instanceof Flight) {
                        Flight flightTemp = (Flight) original;
                        copyTransportations.add(new Flight(flightTemp));
                    } else if (original instanceof Train) {
                        Train trainTemp = (Train) original;
                        copyTransportations.add(new Train(trainTemp));
                    } else if (original instanceof Bus) {
                        Bus busTemp = (Bus) original;
                        copyTransportations.add(new Bus(busTemp));
                    }
                } 
                System.out.println("Transportation array deep-copied.");
                break;
            case 4:
                // deep copy accommadations array
                
                List<Accommadation> copyAccommadations = new ArrayList<>();
                
                for (Accommadation original : service.getAccommodations()){
                    if (original instanceof Hotel){
                        Hotel hotelTemp = (Hotel) original;
                        copyAccommadations.add(new Hotel(hotelTemp));
                    } else if (original instanceof Hostel){
                        Hostel hostelTemp = (Hostel) original;
                        copyAccommadations.add(new Hostel(hostelTemp));
                    }
                }
                System.out.println("Accommadations array deep-copied.");
                break;
            case 5:
                //exit 
                System.out.println("Returning to main menu...");
                break;


            default:
                System.out.println("Invalid selection.");break;
        }

    } while (choice != 5);
    }
// ---- Advanced Analytics ----

public static void advancedMenu(Scanner in){
    int choice =0;
   
    do {
    System.out.println("7.1 Trips by Destination");
    System.out.println("7.2 Trips by Cost Range");
    System.out.println("7.3 Top Clients by spending");
    System.out.println("7.4 Recent Trips");
    System.out.println("7.5 Smart Sort Collections ");
    System.out.println("7.6 Back to main menu");
    System.out.println("Enter choice (1-6): ");
    choice = in.nextInt();
    in.nextLine();

    switch (choice) {
        case 1:
            System.out.println("Enter destination to filter by: ");
            String destination = in.nextLine();
            
            List<Trip> destTrips = service.getTripRepo().filter(new Predicate<Trip>() { // cretaed annonymous inner class to pass predicate (if destination match) to filter method
                public boolean test(Trip t){
                    return t.getDestination().equalsIgnoreCase(destination);
                }
            });
            if (destTrips.isEmpty()){
                System.out.println("No trips found with that destination");

            } else {
                for (Trip t : destTrips){
                    System.out.println(t);
                }
            }
            break;
        case 2:
            System.out.println("Enter minimum price to filter by: ");
            double min = in.nextDouble();
            in.nextLine();
            System.out.println("Enter maximum price to filter by: ");
            double max = in.nextDouble();
            in.nextLine();

            List<Trip> costTrips = service.getTripRepo().filter(new Predicate<Trip>() {
                public boolean test(Trip t){
                    double price =0;
                    try {
                        price = t.calculateTotalCost(t.getDuration());
                        return price >= min && price <= max;
                    } catch (InvalidAccommodationDataException ex) {
                        ErrorLogger.log(ex.getMessage());
                        return false;
                        
                    }
                    
                    
                }
            });
            if (costTrips.isEmpty()){
                System.out.println("No trips found with that price range");

            } else {
                for (Trip t : costTrips){
                    System.out.println(t);
                }
            }
            break;
        case 3:
            List<Client> sortedClients = service.getClientRepo().getSorted();
            System.out.println("Enter number of clients to display by filtering by most spent: ");
            int num = in.nextInt();
            in.nextLine();

            if (num == 0 || num > sortedClients.size()){
                System.out.println("Invalid number. Must be between 1 and "+ sortedClients.size());
                break;
            };

            System.out.println("Top "+num+" clients : ");
            for (int i = 0 ; i < num ; i++){
                System.out.println((i+1)+". "+sortedClients.get(i));
            }

            break;
        case 4:
            System.out.println("Enter number of recent trips to show: ");
            int opt = in.nextInt();
            in.nextLine();

            if (opt > service.getRecentTrips().size()){
                System.out.println("There are only "+service.getRecentTrips().size()+" trips in the recent trip list");
                break;
            }
            service.getRecentTrips().printRecent(opt);      
            break;
        case 5:

        System.out.println("Client by total spending: ");
        for (Client c :  service.getClientRepo().getSorted()){
            System.out.println(c.getFirstName()+" "+c.getLastName()+" total spent: "+c.getTotalSpent());
        }

        System.out.println("Trips by total cost: ");
        for (Trip t : service.getTripRepo().getSorted()){
            System.out.println(t.getId()+" "+t.getDestination()+"  with a value of: "+t.getTotalCost());
        }

        System.out.println("Transportations by base price ");
        for (Transportation t : service.getTransportRepo().getSorted()){
            System.out.println(t.getId()+" "+t.getBasePrice());
        }

        System.out.println("Accommodations by price per night: ");
        for (Accommadation a : service.getAccommodationRepo().getSorted()){
            System.out.println(a.getId()+" "+a.getPricePerNight());
        }
            break;
        case 6:
            System.out.println("Returning to main menu");
            break;    
        default:
            System.out.println("Invalid choice entered.");
            break;
    }

    } while(choice !=6);



}





// --- HELPER METHODS ---
    // method for cleint menu to list clients
    public static void listClients() {
    System.out.println("Clients list:");
    List<Client> clients = service.getClients();  // get the actual list from the service
    for (int i = 0; i < clients.size(); i++) {
        System.out.println("Index: " + i + "\n" + clients.get(i));
    }
}

    // mehtod for trip menu to list all trips
   public static void listTrips() {
    int count = service.getTripCount();
    if (count == 0) {
        System.out.println("No trips available.");
        return;
    }

    System.out.println("Trips list:\n");
    List<Trip> trips = service.getTrips();

    for (int i = 0; i < count; i++) {
        Trip t = trips.get(i);  // get the trip at index i
        try {
            System.out.println("\nIndex: "+i + ". Destination: " + t.getDestination() +
                " | Duration: " + t.getDuration() +
                " | Client: " + t.getClient().getFirstName() + " " + t.getClient().getLastName()
                + " | Total Cost: " +t.calculateTotalCost(t.getDuration()));
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        if (t.getTransportation() != null) {
            System.out.println("\nTransportation Details:\n" + t.getTransportation());
        } else {
            System.out.println("Transportation: None");
        }

        if (t.getAccommadation() != null) {
            System.out.println("\nAccommodation Details:\n\n" + t.getAccommadation());
        } else {
            System.out.println("Accommodation: None");
        }
    }
}

    

// --- deep copy transportation array ---
    public static Transportation[] copyTransportationArray(Transportation[] original) {

    if (original == null) {
        return null;
    }

    Transportation[] copy = new Transportation[original.length];

    for (int i = 0; i < original.length; i++) {

        if (original[i] != null) {

            if (original[i] instanceof Flight) {
                copy[i] = new Flight((Flight) original[i]);

            } else if (original[i] instanceof Train) {
                copy[i] = new Train((Train) original[i]);

            } else if (original[i] instanceof Bus) {
                copy[i] = new Bus((Bus) original[i]);
            }
        }
    }

    return copy;
  }

    //----------------------------------
    //  generation charts and HTML
    //----------------------------------


        private static void generateDashboard() {
            if (service.getTripCount() == 0) {
                System.out.println("No trips available to generate charts.");
                return;
            }
                try {
                    DashboardGenerator.generateDashboard(service);
                    System.out.println("Dashboard generated successfully!");
                } catch (IOException e) {
                    System.out.println("Error generating dashboard: " + e.getMessage());
                    ErrorLogger.log("Dashboard generation error: " + e.getMessage());
                }
        }

  
    //----------------------------------
    // Hard Coded testing scenerio 
    //----------------------------------

    public static void predefinedScenerio(Scanner in){
        System.out.println("=== Predefined Testing Scenario ===");

        // --- Step 1: Create clients ---
        Client c1 = null;
        try {
           c1 = new Client("Alice", "Smith", "alice@gmail.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        Client c2 =null;
        try {
            c2 = new Client("Bob", "Bobertson", "bob@outlook.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        Client c3 = null;
        try {
            c3  = new Client("Chris", "Williams", "chris@live.com");
        } catch (InvalidClientDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        
       

        Client[] testClientArray = new Client[3];  // --- step 4 creating arrays ----
        testClientArray[0] = c1;
        testClientArray[1] = c2;
        testClientArray[2] = c3;
        int testClientCount = 3;

        // --- Step 1: Create transportation ---
        Flight f1 = null;
        try {
            f1 = new Flight("airCanada", "Montreal", "Paris", "airCanada", 20, 150, 30);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Flight f2 =null;
        try {
            f2 = new Flight("WestJet", "Toronto", "New York", "WestJet", 25, 180, 40);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        

        Train t1 = new Train("Exo", "laval", "Toronto", TrainType.HIGH_SPEED, SeatClass.FIRST_CLASS, 120);
        Train t2 = new Train("ViaRail", "Moncton", "Detroit", TrainType.LONG_DISTANCE, SeatClass.ECONOMY, 80);

        Bus b1 = null;
        try {
            b1 = new Bus("Exo", "Terrebbonne", "Montreal", "Exo", 3, 23);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Bus b2 = null;
        try {
           b2 = new Bus("BusCO", "montreal", "laval", "BusCO", 5, 25);
        } catch (InvalidTransportDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Transportation[] tesTransportationsArray = new Transportation[7]; // --- step 4 creating arrays ----
        tesTransportationsArray[0] = f1; 
        tesTransportationsArray[1] = f2;
        tesTransportationsArray[2] = t1; 
        tesTransportationsArray[3] = t2;
        tesTransportationsArray[4] = b1; 
        tesTransportationsArray[5] = b2;
        int testTransportCount = 7;

        // --- Step 1: Create accommodations ---

        Hotel h1 = null;
        try {
            h1= new Hotel("Hilton", "Paris", 100, 4, 20);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Hotel h2 = null;
        try {
          h2  = new Hotel("Imperia", "Montreal", 120, 5, 25);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Hostel hs1 = null;
        try {
            hs1= new Hostel("Auberge du Plateau", "Paris", 50, 3, 10);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        Hostel hs2 = null;
        try {
          hs2 = new Hostel("HostelCo", "Toronto", 60, 4, 15);
        } catch (InvalidAccommodationDataException ex) {
            System.out.println("Error: " + ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Accommadation[] testAccommadations = new Accommadation[4]; // --- step 4 creating arrays ----
        testAccommadations[0] = h1; 
        testAccommadations[1] = h2;
        testAccommadations[2] = hs1; 
        testAccommadations[3] = hs2;
        int testAccommadationCount = 4;

        // --- Step 1: Create trips ---
        Trip tr1 = null;
        try {
           tr1 = new Trip("Paris", 5, 200, c1, f1, h1);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Trip tr2 = null;
        try {
            tr2 = new Trip("Toronto", 4, 150, c2, t1, hs1);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }

        Trip tr3 = null;
        try {
           tr3 = new Trip("Montreal", 6, 180, c3, b1, h2);
        } catch (InvalidTripDataException ex) {
            System.out.println("Error:" +ex.getMessage());
            ErrorLogger.log("Error: " + ex.getMessage()); 
        }
        
       
         

        Trip[] testTripArray = new Trip[3]; // --- step 4 creating arrays ----
        testTripArray[0] = tr1; 
        testTripArray[1] = tr2; 
        testTripArray[2] = tr3;
        int testTripCount = 3;

        // --- Display all created objects --- 
        System.out.println("\n--- Clients ---");
        for (int i = 0; i < testClientCount; i++) {
            System.out.println("\n"+testClientArray[i]);
        }
        System.out.println("\n--- Trips ---");
        for (int i = 0; i < testTripCount; i++) {
            System.out.println(testTripArray[i]);
        }
        System.out.println("\n--- Transportations ---");
        for (int i = 0; i < testTransportCount; i++) System.out.println(tesTransportationsArray[i]);

        System.out.println("\n--- Accommodations ---");
        for (int i = 0; i < testAccommadationCount; i++) System.out.println(testAccommadations[i]);

        // --- testing equals method ---

        // Compare objects from different classes.
        System.out.println("----------------------testing equals method--------------------------");
        System.out.println("Client vs Trip:\n");
        System.out.println(c1.toString()+"\n"+tr1.toString());
        System.out.println("\nequals method on these 2 object is: "+c1.equals(tr1));   

        // Compare objects of the same class with different attributes
        System.out.println("\nTwo Flights with different attributes:");
        System.out.println(f1.toString()+"\n"+f2.toString());
        System.out.println("\nequals method on these 2 object is: "+f1.equals(f2)); 

        // Compare objects of the same class with identical attributes
          Flight f3 = null;
          try {
              f3 = new Flight("WestJet", "Toronto", "New York", "WestJet", 25, 180, 40);// creating copy of f2 to compare against
          } catch (InvalidTransportDataException ex) {
          System.out.println("Error:" +ex.getMessage());
          ErrorLogger.log("Error: " + ex.getMessage()); 
          }
       
        tesTransportationsArray[6] = f3;
        System.out.println("\nTwo Flights with identical attributes:");
        System.out.println(f2.toString()+"\n"+f3.toString());
        System.out.println("\nequals method on these 2 object is: "+f2.equals(f3));

        // --- Demonstrating Polymorphism ---
        
        System.out.println("\n--- Polymorphic Total Cost Calculations ---");

            for (int i = 0; i < testTripArray.length; i++) {
                Trip trip = testTripArray[i];

                Transportation transport = trip.getTransportation();
                Accommadation accommodation = trip.getAccommadation();

                double transportCost = transport.calculateTotalCost(trip.getDuration());
                double accommodationCost = 0;
                try {
                    accommodationCost = accommodation.calculateTotalCost(trip.getDuration());
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                    ErrorLogger.log("Error: " + ex.getMessage()); 
                }

                double totalCost = 0;
                try {
                    totalCost = trip.calculateTotalCost(trip.getDuration()); 
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                    ErrorLogger.log("Error: " + ex.getMessage()); 
                }

                System.out.println("\nTrip to " + trip.getDestination() +
                                " for client " + trip.getClient().getFirstName());

                System.out.println("transport" + " -> Cost: " + transportCost);
                System.out.println("accommodation" + " -> Cost: " + accommodationCost);
                System.out.println(trip.toString());
                
            }

        // --- Displaying most Expensive "test" trip ---
        System.out.println("\n------ Displaying most Expensive test trip ------");
        Trip expensiveTrip = testTripArray[0];
                double highestCost = 0;
                try {
                    expensiveTrip.calculateTotalCost(expensiveTrip.getDuration());
                } catch (InvalidAccommodationDataException ex) {
                    System.out.println("Error:" +ex.getMessage());
                    ErrorLogger.log("Error: " + ex.getMessage()); 
                }
                for (int i =0; i < testTripCount;i++){
                    double tempCost = 0;
                    try {
                        testTripArray[i].calculateTotalCost(testTripArray[i].getDuration());
                    } catch (InvalidAccommodationDataException ex) {
                        System.out.println("Error:" +ex.getMessage());
                        ErrorLogger.log("Error: " + ex.getMessage()); 
                    }
                    if (tempCost > highestCost){
                        highestCost =tempCost;
                        expensiveTrip = testTripArray[i];
                    }
                }
                System.out.println("Most expensive trip: \n" + expensiveTrip.toString());
                System.out.println("With a cost of: " + highestCost);       
        
        
        // --- deep copy of the transportation array. Modify at least one object in the copied array Display both arrays to demonstrate that the original array is unchanged. ---
        System.out.println("\n------ deep copy of the transportation array ------");
        Transportation[] copyTransportations = new Transportation[testTransportCount];
                for (int i =0;i<testTransportCount;i++){
                    if (tesTransportationsArray[i] instanceof Flight){
                        Flight flightTemp = (Flight) tesTransportationsArray[i];
                        copyTransportations[i] = new Flight(flightTemp);
                    } else if (tesTransportationsArray[i] instanceof Train){
                        Train trainTemp = (Train) tesTransportationsArray[i];
                        copyTransportations[i] = new Train(trainTemp);
                    }else if (tesTransportationsArray[i] instanceof Bus){
                        Bus busTemp = (Bus) tesTransportationsArray[i];
                        copyTransportations[i] = new Bus(busTemp);
                    }

                }
             
                copyTransportations[1].setArrivalCity("Berlin");
                copyTransportations[1].setDepartureCity("Vancouver");
                copyTransportations[1].setCompanyName("AirplaneCo");
                System.out.println("\n------Original-------");
                for (int i = 0; i < testTransportCount; i++) System.out.println(tesTransportationsArray[i]); // display original array
                System.out.println("\n------Modified-------");
                for (int i = 0; i < testTransportCount; i++) System.out.println(copyTransportations[i]);    // display modified array
                




    }









    }